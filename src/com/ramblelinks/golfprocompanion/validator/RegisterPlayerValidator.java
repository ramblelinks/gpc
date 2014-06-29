 package com.ramblelinks.golfprocompanion.validator;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Errors;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ramblelinks.golfprocompanion.service.PlayerManager;
import com.ramblelinks.golfprocompanion.utilites.StringManupilations;
import com.ramblelinks.golfprocompanion.view.RegisterPlayer;

@Service
public class RegisterPlayerValidator implements Validator {
	
	/** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String DATE_PATTERN = 
    "(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)";

    private final PlayerManager pm;
    
    @Autowired
    public RegisterPlayerValidator(PlayerManager pm)
    {
    	this.pm = pm;
    }
    
	@Override
	public boolean supports(Class<?> clazz) {
		return RegisterPlayer.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		RegisterPlayer rp = (RegisterPlayer) obj;
		if (rp == null){
			errors.rejectValue("registerPlayer", "error.not-specified",null,"Value Required");
		}
		
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fname",
				"required.fname", "Field name is required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lname",
					"required.lname", "Field name is required.");
			//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "addressOne",
				//"required.addressOne", "Field name is required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailaddress",
				"required.email", "Field name is required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwd",
					"required.Password", "Field name is required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmpasswd",
				"required.confirmPassword", "Field name is required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", 
				"required.gender", "Field name is required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "zipcode", 
				"required.zipcode", "Field name is required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(
				errors, "dob", "required.dob","Field name is required.");
			
			if (rp.getStateId()==-9)
			{
				errors.rejectValue("state", "required.state");
			}
			
			StringManupilations sm = new StringManupilations();
			
			if (sm.RegexHarnes("[0-9[\\W]]", rp.getFname())) {		
				errors.rejectValue("fname", "onlycharacters.fname");
			}
			
			if (sm.RegexHarnes("[0-9[\\W]]", rp.getLname())) {
					errors.rejectValue("lname", "onlycharacters.lname");
				}
			
			if (!rp.getZipcode().isEmpty()) {
				if (!sm.RegexHarnes("^\\d{5}$", rp.getZipcode()))
				{
					errors.rejectValue("zipcode", "format.zipcode");
				}
			}
			
			if (!rp.getPhoneId().isEmpty()) {
				if (!sm.RegexHarnes("^\\d{9}$", rp.getPhoneId()))
				{
					errors.rejectValue("phoneId", "format.phone");
				}
			}
			
			
				if (rp.getDob() != ""){
					
					if (!sm.RegexDateHarnes(DATE_PATTERN, rp.getDob())) {
						//	logger.info("pattern mateched");
						errors.rejectValue("dob", "date.format");
						}
					else
					{
						DateTimeFormatter sdf = DateTimeFormat.forPattern("MM/dd/yyyy");
						DateTime dob = sdf.parseDateTime(rp.getDob());						
						DateTime now = new DateTime();
						Period age = new Period(dob, now);	
						if (age.getYears() <= 0 ){
							errors.rejectValue("dob", "dob.invalid");
						}
						//logger.info("Age is: " + age.getYears() + " months: " + age.getMonths());
					}
				}
			
			
			if (!rp.getEmailaddress().isEmpty() && !sm.RegexHarnes(EMAIL_PATTERN, rp.getEmailaddress())){
				errors.rejectValue("emailaddress", "email.invalid");
			}
			
			if (!rp.getPasswd().equals(rp.getConfirmpasswd())){
				errors.rejectValue("confirmpasswd", "notmatch.password");
			}
			
			if (!rp.getEmailaddress().isEmpty() && sm.RegexHarnes(EMAIL_PATTERN, rp.getEmailaddress()))
			{
				if (this.pm.validateEmail(rp.getEmailaddress()) > 0 ) 
				{
					errors.rejectValue("emailaddress", "email.exists");
				}
				
			}
			
			
	}

	
}
