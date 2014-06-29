package com.ramblelinks.golfprocompanion.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.ramblelinks.golfprocompanion.domain.City;
import com.ramblelinks.golfprocompanion.domain.GolfCourse;
import com.ramblelinks.golfprocompanion.domain.GolfCourseHolesMap;
import com.ramblelinks.golfprocompanion.domain.JqGridData;
import com.ramblelinks.golfprocompanion.domain.Player;
import com.ramblelinks.golfprocompanion.domain.PlayerResult;
import com.ramblelinks.golfprocompanion.service.PlayerManager;
import com.ramblelinks.golfprocompanion.validator.AnalysisValidator;
import com.ramblelinks.golfprocompanion.view.HomePage;
import com.ramblelinks.golfprocompanion.view.ScorecardPage;



@Controller
@RequestMapping("/analysis")
public class AnalysisController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final PlayerManager playerManager;
	private final AnalysisValidator av;
	
	private Player p;
	private int playerResult_Id;
	
	private int numberOfRows;
    private int pageNumber;
    private int totalNumberOfRecords; 
	
	
	@Autowired
	public AnalysisController(PlayerManager playerManager,AnalysisValidator av) {
		this.playerManager = playerManager;	
		this.av = av;
	}
		
	@RequestMapping(value="/{result_id}",method=RequestMethod.GET)
	public String getPlayerScorecard(Model model, SessionStatus status, HttpSession session,@PathVariable int result_id){
		p = (Player) session.getAttribute("player");
		if (p==null)
		{
			return "redirect:/login";			
		}
		int pageCount;
		pageCount = this.playerManager.getPageHitCount("Analysis");
		pageCount = pageCount + 1;
		@SuppressWarnings("unused")
		int retCode = this.playerManager.updatePageHitCount("Analysis", pageCount);		
		playerResult_Id = result_id;
		model.addAttribute("playerResult", playerResult_Id);
		return "analysis";
	}
	
	
	@RequestMapping(value="/homeredirect",method=RequestMethod.GET)
	public @ResponseBody JqGridData<?> getPlayerScorecard(){
				
		logger.info("in get scorecard Player result passed: " + playerResult_Id + " for player: " + p.getPlayer_id());		
		if (playerResult_Id != -9 )
		{
			List<GolfCourseHolesMap> gchm = this.playerManager.getPlayerScoreCard(playerResult_Id);
			numberOfRows = gchm.size();
		    pageNumber = 1; 
		    totalNumberOfRecords =  gchm.size();
	    
		    JqGridData<GolfCourseHolesMap> gridData = new JqGridData<GolfCourseHolesMap>(numberOfRows, pageNumber, totalNumberOfRecords,gchm );
		    return gridData;
	    }
		else
		{
			return null;
		}
		
	}
	
	
	@RequestMapping(value="/homeredirectvalidate", method=RequestMethod.GET, produces="application/json")	
	public  @ResponseBody JqGridData<?> ValidatePage(ScorecardPage sp, BindingResult result) {
		logger.info("in homeredirectvalidate with zip and golf course as: "+sp.getZipcode() + sp.getGolfcourse());
		    this.av.validate(sp, result);
		    List<ObjectError> obj;
		    obj = result.getAllErrors();
		    logger.info("Obj size is: "+obj.size());
		    if (result.hasErrors())
		    {		    	
		    	logger.info("Analysis Page has errors");
		    	JqGridData<GolfCourseHolesMap> errorData = new JqGridData<GolfCourseHolesMap>(numberOfRows, pageNumber, totalNumberOfRecords,null,obj );
		    	return errorData;
		    }
		    return null;		
	}
	
	@RequestMapping(value="/result/{golfCourse_id}", method=RequestMethod.GET)	
	public @ResponseBody List<PlayerResult> getPlayerResults(@PathVariable int golfCourse_id  ) {	
		logger.info("Inside get playe results for player" + p.getPlayer_id() + " for course " + golfCourse_id);
		List<PlayerResult> pr = this.playerManager.getPlayerResultsByCourse(p.getPlayer_id(), golfCourse_id); 
			return pr;
	}
	
	@RequestMapping(value="/golfcourse/city", method=RequestMethod.GET, produces="application/json")
	public  @ResponseBody List<GolfCourse> getGolfCoursesByCity(HomePage hp) {		
		logger.info("city selected is : " + hp.getCity_id() + " radius is: " + hp.getRadius());
		List<GolfCourse> gc = this.playerManager.getGolfCourses(Integer.parseInt(hp.getCity_id()),hp.getZipcode(),hp.getRadius(),0,0);
		return gc;
	}
		
	@RequestMapping(value="/golfcourse/zipcode", method=RequestMethod.GET, produces="application/json")
	public  @ResponseBody List<GolfCourse> getGolfCoursesByZipcode(HomePage hp) {		
		logger.info("zipcode is : " + hp.getZipcode() + " city is: " + hp.getCity_id() + " lat: " + hp.getLatitude() + " radius: " + hp.getRadius() );
		List<GolfCourse> gc = this.playerManager.getGolfCourses(-9,hp.getZipcode(),hp.getRadius(),Float.parseFloat(hp.getLatitude()),Float.parseFloat(hp.getLongitude()));
		return gc;
	}
	
	@RequestMapping(value="/cities",method=RequestMethod.GET, produces="application/json")
	public @ResponseBody List<City> getCities(@RequestParam("term") String query)
	{		
		return this.playerManager.getCities(query);
	}
	

	
	
	@RequestMapping(value="/view/{result_id}", method=RequestMethod.GET)	
	public  @ResponseBody JqGridData<?> getHandicap(@PathVariable int result_id ) {
		    	    		    
		List<GolfCourseHolesMap> gchm = this.playerManager.getPlayerScoreCard(result_id);
		numberOfRows = gchm.size();
	    pageNumber = 1; 
	    totalNumberOfRecords =  gchm.size();
    
	    JqGridData<GolfCourseHolesMap> gridData = new JqGridData<GolfCourseHolesMap>(numberOfRows, pageNumber, totalNumberOfRecords,gchm );
	    return gridData;
	}
	
	@RequestMapping(value="/stats",method=RequestMethod.GET,produces="application/json")
	public @ResponseBody JqGridData<?> getStats()
	{
		
		List<PlayerResult> pr = this.playerManager.getTotalStats(p.getPlayer_id());
		
		numberOfRows = pr.size();
	    pageNumber = 1; 
	    totalNumberOfRecords =  pr.size();
	    
		JqGridData<PlayerResult> griddata = new JqGridData<PlayerResult>(numberOfRows, pageNumber, totalNumberOfRecords,pr );
		return griddata;
	}
	
	@RequestMapping(value="/allscores",method=RequestMethod.GET,produces="application/json")
	public @ResponseBody JqGridData<?> getAllScores()
	{
		
		List<PlayerResult> pr = this.playerManager.getAllScores(p.getPlayer_id());
		
		numberOfRows = pr.size();
	    pageNumber = 1; 
	    totalNumberOfRecords =  pr.size();
	    
		JqGridData<PlayerResult> griddata = new JqGridData<PlayerResult>(numberOfRows, pageNumber, totalNumberOfRecords,pr );
		return griddata;
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)	
	public  @ResponseBody String saveScore(HttpServletRequest req) {			
			logger.info("player result req : " + req.getParameter("playerResult_id"));
			int pr_id;
			try
			{
			 pr_id = Integer.parseInt(req.getParameter("playerResult_id"));
			}
			catch(Exception ex)
			{
				pr_id = 0;
			}
			//String playerScore = c1 + ',' + c2 + ',' + c3 + ',' + c4 + ',' + c5 + ',' + c6 + ',' + c7 + ',' + c8 + ',' + c9 + ',' + c10 + ',' + c11 + ',' + c12 + ',' + c13 + ',' + c14 + ',' + c15 + ',' + c16 + ',' + c17 + ',' + c18;
			
			String playerScore = req.getParameter("col_1") + ',' + req.getParameter("col_2") + ',' + req.getParameter("col_3") + ',' + req.getParameter("col_4") + ',' + req.getParameter("col_5") + ',' + req.getParameter("col_6") + ',' + req.getParameter("col_7") 
								+ ',' + req.getParameter("col_8") + ',' + req.getParameter("col_9") + ',' + req.getParameter("col_10") + ',' 
								+ req.getParameter("col_11") + ',' + req.getParameter("col_12") + ',' + req.getParameter("col_13") + ',' 
								+ req.getParameter("col_14") + ',' + req.getParameter("col_15") + ',' + req.getParameter("col_16") + ',' + req.getParameter("col_17") + ',' + req.getParameter("col_18");
			
			logger.info("Check http Request options + " + playerScore);
			
			//if (pr_id != 0){
				logger.info("Inside update scorecard");
				return this.playerManager.updateScorecard(pr_id, playerScore);
		//	}
	}
	
	
	
}
