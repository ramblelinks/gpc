package com.ramblelinks.golfprocompanion.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.ramblelinks.golfprocompanion.domain.City;
import com.ramblelinks.golfprocompanion.domain.GolfCourse;
import com.ramblelinks.golfprocompanion.domain.JqGridData;
import com.ramblelinks.golfprocompanion.domain.Player;
//import com.ramblelinks.golfprocompanion.domain.PlayerInvitation;
import com.ramblelinks.golfprocompanion.domain.SearchResult;
import com.ramblelinks.golfprocompanion.service.PlayerManager;
import com.ramblelinks.golfprocompanion.validator.HomeValidator;
import com.ramblelinks.golfprocompanion.view.HomePage;


@Controller
@RequestMapping("/companion")
public class CompanionController {
	
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final PlayerManager playerManager;	
	private final HomeValidator hv;
	private Player p;
	
	private int numberOfRows;
    private int pageNumber;
    private int totalNumberOfRecords; 
    
    @Autowired
	public CompanionController(PlayerManager playerManager, HomeValidator hv) {
		this.playerManager = playerManager;			
		this.hv = hv;
	}

	@RequestMapping(value="/main",method=RequestMethod.GET)
	public String getCompanion(Model model, SessionStatus status, HttpSession session)
	{
		p = (Player) session.getAttribute("player");
		if (p==null)
		{
			return "redirect:/login";			
		}
		int pageCount;
		pageCount = this.playerManager.getPageHitCount("Companion");
		pageCount = pageCount + 1;
		int retCode = this.playerManager.updatePageHitCount("Companion", pageCount);		
		return "profile";
	}
	
	/*
	 * Commented out as no rating are going to be implemented
	 * @RequestMapping(value="/rate", method=RequestMethod.POST)
	public @ResponseBody String setPlayerRating(@RequestParam(value="reviewee_id")int reviewee_id,
												@RequestParam(value="rating")int rating,
												@RequestParam(value="comments")String comments )
	{
		logger.info("inside set player rating " + reviewee_id);
		int playerReview_id = this.playerManager.setPlayerRating(p.getPlayer_id(), reviewee_id, rating, comments);
		return "Called " + playerReview_id;
	}*/
	
	
	@RequestMapping(value="/cities",method=RequestMethod.GET, produces="application/json")
	public @ResponseBody List<City> getCities(@RequestParam("term") String query)
	{		
		return this.playerManager.getCities(query);
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
	
	@RequestMapping(value="/validatesearch", method=RequestMethod.GET, produces="application/json")	
	public  @ResponseBody JqGridData<?> ValidatePage(HomePage hp, BindingResult result) {		  		    
		    this.hv.validate(hp, result);
		    List<ObjectError> obj;
		    obj = result.getAllErrors();
		    if (result.hasErrors())
		    {		    			    	
		    	JqGridData<SearchResult> errorData = new JqGridData<SearchResult>(numberOfRows, pageNumber, totalNumberOfRecords,null,obj );
		    	return errorData;
		    }
		    return null;		
	}
	
	@RequestMapping(value="/srch", method=RequestMethod.POST)
	public  @ResponseBody JqGridData<?> searchPlayersObj(HomePage hp) {		
				
			List<SearchResult> srl = this.playerManager.searchCompanions(p.getPlayer_id(), hp.getHandicap(), hp.getGender(), hp.getAge(), hp.getCity(), hp.getZipcode(), hp.getRadius(), hp.getLatitude(), hp.getLongitude());
			
			numberOfRows = srl.size();
		    pageNumber = 1; 
		    totalNumberOfRecords =  srl.size(); 
		    logger.info("Num of players searched: " + numberOfRows);
/*		    for (int i=0; i<srl.size(); i++)
		    {
		    	SearchResult sr = srl.get(i);
		    	//logger.info(sr.getPlayers().getReview_rating());
		    }
*/			JqGridData<SearchResult> gridData = new JqGridData<SearchResult>(numberOfRows, pageNumber, totalNumberOfRecords,srl );
			return gridData;			
	}
	
	
	@RequestMapping(value="/playerinvite", method=RequestMethod.POST)
	public @ResponseBody String invitePlayers(@RequestParam("player_id")int invite_player,
											  @RequestParam("golfcourse")int golfcourse,
											  @RequestParam("playdate")String playDate,
											  @RequestParam("teetime")String teeTime)
	{
	logger.info("request sent is: " + golfcourse + " player " + invite_player + " playDate " + playDate + " playTime: " + teeTime);
		int ret = this.playerManager.createInvitation(p.getPlayer_id(), invite_player, golfcourse, playDate, teeTime, "PENDING", "Create Invitation");
		
		if (ret == -9) {
			return "Invitation request could not be sent succesfully to the player " + this.playerManager.getPlayerInfo(invite_player).getFirst_name() ;
		}
		else
		{
			return "Invitation request sent succesfully to the player " + this.playerManager.getPlayerInfo(invite_player).getFirst_name() ;
		}		
	}
		
}
