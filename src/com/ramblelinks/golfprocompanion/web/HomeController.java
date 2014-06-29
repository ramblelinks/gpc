package com.ramblelinks.golfprocompanion.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.ramblelinks.golfprocompanion.domain.JqGridData;
import com.ramblelinks.golfprocompanion.domain.Player;
import com.ramblelinks.golfprocompanion.domain.PlayerInvitation;
import com.ramblelinks.golfprocompanion.domain.PlayerResult;
import com.ramblelinks.golfprocompanion.service.PlayerManager;
import com.ramblelinks.golfprocompanion.view.HomePage;


@Controller
@RequestMapping("/gohome")
public class HomeController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final PlayerManager playerManager;		
	private Player p;
	
	private int numberOfRows;
    private int pageNumber;
    private int totalNumberOfRecords; 
	
	@Autowired
	public HomeController(PlayerManager playerManager) {
		this.playerManager = playerManager;					
	}

	@RequestMapping(value="/s",method=RequestMethod.GET)
	public String main(Model model, SessionStatus status, HttpSession session){
		String hdrMsg;
		int pageCount;
		pageCount = this.playerManager.getPageHitCount("Home");
		pageCount = pageCount + 1;
		@SuppressWarnings("unused")
		int retCode = this.playerManager.updatePageHitCount("Home", pageCount);
		p = (Player) session.getAttribute("player");
		if (p==null) {
			return "redirect:/login";
		}
		int invCount = this.playerManager.getPlayerInvitation(p.getPlayer_id()).size();
		model.addAttribute("homepage",new HomePage());
		model.addAttribute("golfclub", this.playerManager.getGolfClubs(p.getState_id()));
		model.addAttribute("sentInvites", this.playerManager.getSentInvitations(p.getPlayer_id()));
		if (invCount == 0) {
			hdrMsg = "No Pending invites";
		}
		else
		{
			hdrMsg = "Pending invites " + invCount; 
		}
		model.addAttribute("username", p.getFirst_name());
		model.addAttribute("numInv",hdrMsg );
		session.setAttribute("uName", p.getFirst_name());
		session.setAttribute("hdr", hdrMsg);
		return "home";
	}
	
	@RequestMapping(value="/sentinvitations",method=RequestMethod.GET)	
	public @ResponseBody JqGridData<?> getSentInvitations(){				
		//logger.info("Inside Json call for invitations");
		List<PlayerInvitation> pr = this.playerManager.getSentInvitations(p.getPlayer_id()); 
		numberOfRows = pr.size();
	    pageNumber = 1; 
	    totalNumberOfRecords =  pr.size(); 

	    JqGridData<PlayerInvitation> gridData = new JqGridData<PlayerInvitation>(numberOfRows, pageNumber, totalNumberOfRecords,pr );		
		return gridData;
	}
	
	@RequestMapping(value="/invitations",method=RequestMethod.GET)	
	public @ResponseBody JqGridData<?> getInvitations(){				
		//logger.info("Inside Json call for invitations");
		List<PlayerInvitation> pr = this.playerManager.getPlayerInvitation(p.getPlayer_id()); 
		numberOfRows = pr.size();
	    pageNumber = 1; 
	    totalNumberOfRecords =  pr.size(); 

	    JqGridData<PlayerInvitation> gridData = new JqGridData<PlayerInvitation>(numberOfRows, pageNumber, totalNumberOfRecords,pr );		
		return gridData;
	}
	
	
	@RequestMapping(value="/playerresults",method=RequestMethod.GET)	
	public @ResponseBody JqGridData<?> getLastFiveRounds(){				
		//logger.info("Inside Json call for last five rounds");
		List<PlayerResult> pr = this.playerManager.getPlayerResults(p.getPlayer_id()); 
		numberOfRows = pr.size();
	    pageNumber = 1; 
	    totalNumberOfRecords =  pr.size(); 
	    
	    JqGridData<PlayerResult> gridData = new JqGridData<PlayerResult>(numberOfRows, pageNumber, totalNumberOfRecords,pr );		
		return gridData;
	}
	
	
	
	@RequestMapping(value="/inviteresponse/{status}/{invitation_id}", method=RequestMethod.POST)
	public @ResponseBody String inviteUpdate(@PathVariable String status, @PathVariable int invitation_id)
	{
		logger.info("Invitation for player : " +  p.getPlayer_id() +" is " + invitation_id + " Status " + status);
		String ret = this.playerManager.updateInvitation(invitation_id, p.getPlayer_id(), status, status + " invitaion");
		return ret;
	}
	
}
