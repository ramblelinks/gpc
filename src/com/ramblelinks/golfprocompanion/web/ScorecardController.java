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
import com.ramblelinks.golfprocompanion.domain.GolfCourseDetails;
import com.ramblelinks.golfprocompanion.domain.GolfCourseHolesMap;
import com.ramblelinks.golfprocompanion.domain.JqGridData;
import com.ramblelinks.golfprocompanion.domain.Player;
import com.ramblelinks.golfprocompanion.service.PlayerManager;
import com.ramblelinks.golfprocompanion.validator.ScorecardValidator;
import com.ramblelinks.golfprocompanion.view.HomePage;
import com.ramblelinks.golfprocompanion.view.ScorecardPage;


@Controller
@RequestMapping("/scorecard")
public class ScorecardController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final PlayerManager playerManager;
	private final ScorecardValidator sv;
	
	private Player p;
	
	private int numberOfRows;
    private int pageNumber;
    private int totalNumberOfRecords; 
	
	
	@Autowired
	public ScorecardController(PlayerManager playerManager, ScorecardValidator sv) {
		this.playerManager = playerManager;	
		this.sv = sv;		
	}
		
	@RequestMapping(value="/main",method=RequestMethod.GET)
	public String getPlayerScorecard(Model model, SessionStatus status, HttpSession session){
		p = (Player) session.getAttribute("player");
		if (p==null)
		{
			return "redirect:/login";			
		}
		int pageCount;
		pageCount = this.playerManager.getPageHitCount("Scorecard");
		pageCount = pageCount + 1;
		int retCode = this.playerManager.updatePageHitCount("Scorecard", pageCount);
		return "scorecard";
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
	
	/*@RequestMapping(value="/golfcourse/{zipcode}", method=RequestMethod.GET, produces="application/json")
	public  @ResponseBody List<GolfCourse> getGolfCourses(@PathVariable String zipcode) {		
		logger.info("zipcode selected is : " + zipcode);
		List<GolfCourse> gc = this.playerManager.getGolfCourses(zipcode);
		logger.info("Done getting Golf Courses for Zipcode : " + zipcode);
		return gc;
	}
	*/
	
	@RequestMapping(value="/teetype/{golfcourse}", method=RequestMethod.GET, produces="application/json")
	public  @ResponseBody List<GolfCourseDetails> getTeeTypes(@PathVariable int golfcourse) {		
		logger.info("golfcourse selected is : " + golfcourse);
		List<GolfCourseDetails> gd = this.playerManager.getTeeTypes(golfcourse, p.getGender());
		logger.info("Done getting Tee Types for Golfcourse : " + golfcourse);
		return gd;
	}
	
	@RequestMapping(value="/srch", method=RequestMethod.GET)	
	public  @ResponseBody JqGridData<?> getCourseDetails(ScorecardPage sp, BindingResult result) {
		    //logger.info("Comes inside with searlize object" + sp.getTeetype());
		    //logger.info("Comes inside with searlize object" + p.getGender());
		    //logger.info("Comes inside with searlize object" + p.getPlayer_id());
		    
//		    this.sv.validate(sp, result);
//		    
//		    if (result.hasErrors())
//		    {		    	
//		    	logger.info("Scorecard has errors");
//		    	List<ObjectError> obj;
//			    obj = result.getAllErrors();
//			    JqGridData<GolfCourseHolesMap> errorData = new JqGridData<GolfCourseHolesMap>(numberOfRows, pageNumber, totalNumberOfRecords,null,obj );
//		    	return errorData;		    
//		    }
		    
			List<GolfCourseHolesMap> courseDetails = this.playerManager.getcoursedetails(sp.getGolfcourse(),sp.getTeetype(),p.getGender(),p.getPlayer_id());			
			numberOfRows = courseDetails.size();
		    pageNumber = 1; 
		    totalNumberOfRecords =  courseDetails.size(); 
		    
			JqGridData<GolfCourseHolesMap> gridData = new JqGridData<GolfCourseHolesMap>(numberOfRows, pageNumber, totalNumberOfRecords,courseDetails );
			return gridData;
	}
	
	
	//Testing the method
	
	@RequestMapping(value="/srchtest", method=RequestMethod.GET, produces="application/json")	
	public  @ResponseBody JqGridData<?> ValidatePage(ScorecardPage sp, BindingResult result) {
		   // ScorecardPage sp = new ScorecardPage();		     
		   // sp.setZipcode("921");
		   // sp.setPlaydate("10/02/2013");
		    //logger.info("Comes inside with searlize object" + sp.getTeetype());
		    //logger.info("Comes inside with searlize object" + p.getGender());
		    //logger.info("Comes inside with searlize object" + p.getPlayer_id());
		    
		    this.sv.validate(sp, result);
		    List<ObjectError> obj;
		    obj = result.getAllErrors();
		    if (result.hasErrors())
		    {		    	
		    	logger.info("Scorecard has errors");
		    	JqGridData<GolfCourseHolesMap> errorData = new JqGridData<GolfCourseHolesMap>(numberOfRows, pageNumber, totalNumberOfRecords,null,obj );
		    	return errorData;
		    }
		    return null;		
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
			
			if (pr_id != 0){
				logger.info("Inside update scorecard");
				return this.playerManager.updateScorecard(pr_id, playerScore);
			}
			else
			{
				int golfCourse_id = Integer.parseInt(req.getParameter("gc_id"));
				int golfCourseDetail_id = Integer.parseInt(req.getParameter("gcd_id"));
				String play_date = req.getParameter("date_played");
				String play_time =  req.getParameter("time_played");
				logger.info("play date: " +  play_date + "Play Time: " + play_time);
				return this.playerManager.addPlayerScorecard(p.getPlayer_id(),  golfCourse_id , golfCourseDetail_id, play_date, playerScore, play_time);
			}		    		    	
	}
	
	
	
}
