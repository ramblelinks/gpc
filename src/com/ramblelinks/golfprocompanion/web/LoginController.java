package com.ramblelinks.golfprocompanion.web;


import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.ramblelinks.golfprocompanion.domain.JqGridData;
import com.ramblelinks.golfprocompanion.domain.Player;
import com.ramblelinks.golfprocompanion.service.PlayerManager;
import com.ramblelinks.golfprocompanion.validator.LoginValidator;
import com.ramblelinks.golfprocompanion.validator.RetrieveValidator;
import com.ramblelinks.golfprocompanion.view.LoginPage;


@Controller
@RequestMapping(value = "/login")
public class LoginController {		
	
	protected final Log logger = LogFactory.getLog(getClass());
	private final PlayerManager playerManager;
	private final LoginValidator lv;
	private final RetrieveValidator rv;
	
	@Autowired
	public LoginController(PlayerManager playerManager, LoginValidator lv, RetrieveValidator rv) {			
		this.playerManager = playerManager;			
		this.lv = lv;
		this.rv = rv;		
	}
	
	
	@RequestMapping(method=RequestMethod.GET)	
	public String getCreateForm(Model model, HttpSession session, HttpServletRequest request) {
		// check to see if the session is already active if so redirect to Home else login
		Player p = (Player) session.getAttribute("player");
		String ipAddress = request.getHeader("X-FORWARDED-FOR");  
		if (ipAddress == null) {  
		   ipAddress = request.getRemoteAddr();  
		}
		logger.info("IP Address: " + ipAddress);
		if (p!= null) {
			return "home";
		}
		else
		{
		model.addAttribute("loginpage",new LoginPage());
		int pageCount;
		pageCount = this.playerManager.getPageHitCount("Login");
		pageCount = pageCount + 1;
		@SuppressWarnings("unused")
		int retCode = this.playerManager.updatePageHitCount("Login", pageCount);
		return "login";
		}
	}
	
	@RequestMapping(value="/retrieve", method=RequestMethod.GET)
	public String getRetriveForm(Model m)
	{
		m.addAttribute("loginpage",new LoginPage());
		return "retrieve";
	}
	
	@RequestMapping(value="/find", method=RequestMethod.GET)
	public @ResponseBody String checkuserName(HttpServletRequest req)
	{
		logger.info("Value passed is: " + req.getParameter("username"));
		int count = this.playerManager.validateUserName(req.getParameter("username"));
		if (count > 0)
		{
			return "valid";
		}
		else
		{
			return "invalid";
		}
	}
	
	
	@RequestMapping(value="/resetvalidation", method=RequestMethod.GET)
	public @ResponseBody JqGridData<?> resetValidation(LoginPage resetPass, BindingResult result)
	{
		logger.info("username in reset: " + resetPass.getUsername());
		logger.info("password  in reset: " + resetPass.getPasswd());
				
		rv.validate(resetPass, result);
		List<ObjectError> obj;
	    obj = result.getAllErrors();
	    if (result.hasErrors())
	    {		    			    	
	    	JqGridData<ObjectError> errorData = new JqGridData<ObjectError>(0, 0, 0,null,obj );
	    	return errorData;
	    }
		else
		{
			return null;
		}
	}
	
	@RequestMapping(value="/reset", method=RequestMethod.POST)
	public @ResponseBody String resetPassword(@ModelAttribute("loginpage") LoginPage resetPass, BindingResult result)
	{			
		rv.validate(resetPass, result);		
		if (result.hasErrors())
		{			
			return "retrieve";
		}
		else
		{
		int count = this.playerManager.resetPassword(resetPass.getUsername(), resetPass.getPasswd());
		if (count > 0)
			{
				return "Success";
			}
		else
		{
			return "Fail";
		}
		}
	}
	
	@RequestMapping(method=RequestMethod.POST)	
	public String loginAction(@ModelAttribute("loginpage") LoginPage playerLogin, SessionStatus status, HttpSession session, BindingResult result)
	{  
		String username, password;
		
		lv.validate(playerLogin, result);
		
		if (result.hasErrors())
		{
			return "login";
		}
		else
		{
			username = playerLogin.getLogin_name();
			password = playerLogin.getLogin_password();
			//logger.info("Player username is: " + username);
			//logger.info("Player password is: " + password);	
			Player player =  this.playerManager.getPlayer(username, password);
			session.setAttribute("player", player);	
			logger.info("Player is: " + player.getFirst_name());
			if (player.getPlayer_id() > 0){		
				return "redirect:/gohome/s";		
			}
			else
			{
				return "login";
			}	 
		}
	}
}
