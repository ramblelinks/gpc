package com.ramblelinks.golfprocompanion.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ModelAndView;


import com.ramblelinks.golfprocompanion.domain.Player;
import com.ramblelinks.golfprocompanion.domain.State;
import com.ramblelinks.golfprocompanion.service.PlayerManager;
import com.ramblelinks.golfprocompanion.validator.PlayerProfileValidator;
//import com.ramblelinks.golfprocompanion.view.PlayerProfile;

@Controller
@RequestMapping("/profile")
public class ProfileController {

	protected final Log logger = LogFactory.getLog(getClass());	
	
	private final PlayerManager playerManager;
	private final PlayerProfileValidator pfv;
	private Player p;
	
	@Autowired
	public ProfileController(PlayerManager playerManager, PlayerProfileValidator pfv) {		
		this.playerManager = playerManager;
		this.pfv = pfv;		
	}	
	
	@ModelAttribute("state")
	public List<State> populateStates(){		
		return this.playerManager.getStates();
	}
	
	@InitBinder
	public void initBinder(HttpServletRequest req, ServletRequestDataBinder binder) {
	    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	    sdf.setLenient(false);
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, false));	    
	}
	
	@RequestMapping(value="/fetch", method=RequestMethod.GET)
	public String Profile(HttpSession session, Model m)
	{									
		p = (Player) session.getAttribute("player");		
		if (p==null)
		{
			return "redirect:/login";			
		}
		logger.info("Email Notifications is: " + p.getEmail_notifications());	
		m.addAttribute("cmdProfile", p);
		m.addAttribute("haserrors","N");
		return "playerprofile";		
	}

/*	@RequestMapping(value="/fetch", method=RequestMethod.GET)
	public ModelAndView Profile(HttpSession session)
	{									
		p = (Player) session.getAttribute("player");		
		logger.info("Email Notifications is: " + p.getEmail_notifications());		
		//m.addAttribute("state", populateStates());
		return new ModelAndView("playerprofile","cmdProfile",p);
		//m.addAttribute("editprofile", pp);
		//return "playerprofile";		
	}	*/

	@RequestMapping(value="/fetch", method={RequestMethod.PUT, RequestMethod.POST})
	public String editPlayer(@ModelAttribute("cmdProfile") Player updPlayer, BindingResult result, Model m, HttpSession session)
	{
		this.pfv.validate(updPlayer, result);
		if (result.hasErrors())
		{
			updPlayer.setFirst_name(p.getFirst_name());
			updPlayer.setMiddle_name(p.getMiddle_name());
			updPlayer.setLast_name(p.getLast_name());
			m.addAttribute("haserrors","Y");
			return "playerprofile";
		}
		m.addAttribute("haserrors","N");
		//session object can be changed and need to destroy the previous Session and add a new one
		logger.info("Player id: " + updPlayer.getPlayer_id() + 
				"street address value passed: " + updPlayer.getStreet_address1() + 
				" date of birth: " + updPlayer.getDate_of_birth()
				+ " lat: " + updPlayer.getLatitude() + 
				"long: " + updPlayer.getLongitude()+
				"phone number: " + updPlayer.getPd().getPhone_number());		
		
		int retVal = this.playerManager.updatePlayeProfile(updPlayer);
		Player upPlayer = new Player();
		if (retVal != -9) 
		{
			upPlayer = this.playerManager.getPlayerInfo(p.getPlayer_id());
			session.removeAttribute("player");
			session.setAttribute("player", upPlayer);
		}		
		m.addAttribute("cmdProfile", upPlayer);
		m.addAttribute("message","Player profile updated succesfully!!");
		logger.info("Ret value is: " + retVal);
		return "playerprofile";
	}
	
	
/*	@RequestMapping(value="/fetch", method=RequestMethod.POST)
	public ModelAndView editPlayer(@ModelAttribute("cmdProfile") Player pp, BindingResult result, HttpSession session)
	{
		//session object can be changed and need to destroy the previous Session and add a new one
		logger.info(" street address " + pp.getStreet_address1() + 
				" date of birth " + pp.getDate_of_birth() +
				" phone id " + pp.getPhone_id()
				);
		//m.addAttribute("playerprofile", pp);
		//pp.setPlayer_id(p.getPlayer_id());
		int retVal = this.playerManager.updatePlayeProfile(pp);
		if (retVal != -9) 
		{
			session.removeAttribute("player");
			session.setAttribute("player", this.playerManager.getPlayerInfo(pp.getPlayer_id()));
		}
	//	logger.info("Ret value is: " + retVal);
		//return "playerprofile";
		return new ModelAndView("playerprofile","command",this.playerManager.getPlayerInfo(p.getPlayer_id()));
	}*/
		
}
