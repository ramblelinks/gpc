package com.ramblelinks.golfprocompanion.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Errors;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ramblelinks.golfprocompanion.domain.Player;
import com.ramblelinks.golfprocompanion.domain.PlayerLogin;
import com.ramblelinks.golfprocompanion.service.PlayerManager;
import com.ramblelinks.golfprocompanion.view.LoginPage;

@Service
public class LoginValidator implements Validator {
	
	/** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private PlayerManager pm;

    @Autowired
    public LoginValidator(PlayerManager pm)
    {
    	this.pm = pm;
    }
    
	@Override
	public boolean supports(Class<?> clazz) {
		return PlayerLogin.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		LoginPage p = (LoginPage) obj;
		if (p == null){
			errors.rejectValue("playerLogin", "error.not-specified",null,"Value Required");
		}
		
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login_name",
				"required.login_name", "login name is required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login_password",
					"required.login_password", "password is required.");
			
			if (!p.getLogin_name().isEmpty() && !p.getLogin_password().isEmpty())
			{
				Player player =  this.pm.getPlayer(p.getLogin_name(), p.getLogin_password());
				if (player ==null )
				{
					errors.rejectValue("login_errors", "invalid.login");
				}
			}
	}

	
}
