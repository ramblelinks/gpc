package com.ramblelinks.golfprocompanion.web;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ramblelinks.golfprocompanion.domain.State;
import com.ramblelinks.golfprocompanion.service.PlayerManager;
import com.ramblelinks.golfprocompanion.validator.RegisterPlayerValidator;
import com.ramblelinks.golfprocompanion.view.RegisterPlayer;

@Controller
@RequestMapping(value="/register")
public class AddPlayerController {

	/* for Autowiring we need to create the bean class object as private final
	and we need to set the object in the constructor of the class*/
	//private final StateManager stateManager;
	private final PlayerManager playerManager;
	private final RegisterPlayerValidator playerValidator;
	
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	public AddPlayerController(PlayerManager playerManager, RegisterPlayerValidator playerValidtor) {
		logger.info("inside Add Player Controller Constructor");
		//this.stateManager = stateManager;
		this.playerManager = playerManager;
		this.playerValidator = playerValidtor;
	}
		
	@ModelAttribute("state")	
	public List<State> populateStates(){		
		return this.playerManager.getStates();
	}
	
	@RequestMapping(method=RequestMethod.GET)	
	public String getCreateForm(Model model) {		
		int pageCount;
		pageCount = this.playerManager.getPageHitCount("Registration");
		pageCount = pageCount + 1;
		int retCode = this.playerManager.updatePageHitCount("Registration", pageCount);
		model.addAttribute("registerplayer",new RegisterPlayer());		
		return "signup";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String addPlayer(@ModelAttribute("registerplayer") RegisterPlayer registerPlayer, BindingResult result)
	{	
		String retMessage;
		this.playerValidator.validate(registerPlayer, result);
		if (result.hasErrors()){
			return "signup";
		}
		@SuppressWarnings("unused")
		int playerDetailId, playerLoginId;
		logger.info("Player name is: " + registerPlayer.getFname());
		retMessage = this.playerManager.addPlayer(registerPlayer);
		logger.info(retMessage);
		if (registerPlayer.getPlayer_id() != -9 )
		{
			if (!registerPlayer.getPhoneId().isEmpty()){
				playerDetailId = this.playerManager.addPlayerDetail(registerPlayer);
			}
			else
			{
				playerDetailId = 0;
			}		
			playerLoginId = this.playerManager.addPlayerLogin(registerPlayer);
			return "redirect:/login";
		}
		else
		{
			return "signup";
		}
		
		
	}
		
}
