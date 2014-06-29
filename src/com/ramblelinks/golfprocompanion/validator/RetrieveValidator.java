package com.ramblelinks.golfprocompanion.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ramblelinks.golfprocompanion.domain.PlayerLogin;
import com.ramblelinks.golfprocompanion.view.LoginPage;

@Service
public class RetrieveValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {		
		return PlayerLogin.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		LoginPage p = (LoginPage) obj;
		if (p == null){
			errors.rejectValue("playerLogin", "error.not-specified",null,"Value Required");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwd",
				"required.Password", "Password is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmpasswd",
			"required.confirmPassword", "Confirm password is required.");
		
		if (!p.getPasswd().equals(p.getConfirmpasswd())){
			errors.rejectValue("confirmpasswd", "error.not-hpecified",null,"Password and Conform password is not match!");
			//errors.rejectValue("confirmpasswd", "notmatch.password");
		}
		
	}

	
	
}
