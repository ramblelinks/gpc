 package com.ramblelinks.golfprocompanion.validator;

import java.text.SimpleDateFormat;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Errors;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ramblelinks.golfprocompanion.domain.Player;
import com.ramblelinks.golfprocompanion.utilites.StringManupilations;

@Service
public class PlayerProfileValidator implements Validator {
	
	/** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String DATE_PATTERN = 
    "(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)";

   /* private final PlayerManager pm;
    
    @Autowired
    public PlayerProfileValidator(PlayerManager pm)
    {
    	this.pm = pm;
    }*/
    
	@Override
	public boolean supports(Class<?> clazz) {
		return Player.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		Player rp = (Player) obj;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		
		if (rp == null){
			errors.rejectValue("Player", "error.not-specified",null,"Value Required");
		}
					
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email_address",
				"required.emailaddress", "Field name is required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "zip_code", 
				"required.zipcode", "Field name is required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(
				errors, "date_of_birth", "required.dob","Field name is required.");
			
			if (rp.getState_id()==-9)
			{
				errors.rejectValue("state", "required.stateid");
			}
			
			StringManupilations sm = new StringManupilations();
			
						
			if (!rp.getZip_code().isEmpty()) {
				if (!sm.RegexHarnes("^\\d{5}$", rp.getZip_code()))
				{
					errors.rejectValue("zip_code", "format.zipcode");
				}
			}
										
				if (rp.getDate_of_birth().toString() != ""){
					
					if (!sm.RegexDateHarnes(DATE_PATTERN, sdf.format(rp.getDate_of_birth()) )) {
						//	logger.info("pattern mateched");
						errors.rejectValue("date_of_birth", "date.format");
						}
					else
					{			
						DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy");
						DateTime dob = dtf.parseDateTime(sdf.format(rp.getDate_of_birth()));						
						DateTime now = new DateTime();
						Period age = new Period(dob, now);	
						if (age.getYears() <= 0 ){
							errors.rejectValue("date_of_birth", "dob.invalid");
						}
						logger.info("Age is: " + age.getYears() + " months: " + age.getMonths());
					}
				}
			
			
			if (!rp.getEmail_address().isEmpty() && !sm.RegexHarnes(EMAIL_PATTERN, rp.getEmail_address())){
				errors.rejectValue("email_address", "email.invalid");
			}
						
			
		/*	if (!rp.getEmail_address().isEmpty() && sm.RegexHarnes(EMAIL_PATTERN, rp.getEmail_address()))
			{
				if (this.pm.validateEmail(rp.getEmail_address()) > 0 ) 
				{
					errors.rejectValue("email_address", "email.exists");
				}
				
			}*/
			
			
	}

	
}
