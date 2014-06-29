 package com.ramblelinks.golfprocompanion.validator;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ramblelinks.golfprocompanion.utilites.StringManupilations;
import com.ramblelinks.golfprocompanion.view.RegisterPlayer;
import com.ramblelinks.golfprocompanion.view.ScorecardPage;

@Service
public class CompanionValidator implements Validator {
	
	/** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private static final String DATE_PATTERN = 
    "(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)";

	@Override
	public boolean supports(Class<?> clazz) {
		return RegisterPlayer.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		ScorecardPage sp = (ScorecardPage) obj;
		
		if (sp == null){
			errors.rejectValue("scoreCard", "error.not-specified",null,"Value Required");
		}
								
			StringManupilations sm = new StringManupilations();
			
			
			if (sp.getZipcode().isEmpty() && sp.getCity().isEmpty())
			{
				errors.rejectValue("zipcode", "error.not-specified",null,"Either zipcode or city needs to be provided.");
			}
			
			if (!sp.getCity().isEmpty() && (Integer.parseInt(sp.getCity_id()) == -9 || sp.getCity_id().isEmpty()))
			{
				errors.rejectValue("city", "error.not-specified",null,"Please select an appropriate city.");
			}
				
						
			if (!sp.getZipcode().isEmpty()) {
				//logger.info("Zipcode not empty");
				if (!sm.RegexHarnes("^\\d{5}$", sp.getZipcode()))
				{
					errors.rejectValue("zipcode", "error.not-specified",null,"Correct format for zip code is #####");
				}
			}						
			
			if (!sp.getPlaydate().isEmpty()){
				if (!sm.RegexDateHarnes(DATE_PATTERN, sp.getPlaydate())) {
					//	logger.info("pattern mateched");
					errors.rejectValue("playdate", "date.format");
					}
				else
				{
					DateTimeFormatter sdf = DateTimeFormat.forPattern("MM/dd/yyyy");
					DateTime play_date = sdf.parseDateTime(sp.getPlaydate());						
					DateTime now = new DateTime();
					Period age = new Period(play_date,now);	
					logger.info("Difference is: "+ age.getDays());
					if (age.getDays() <= 0 ){
						//errors.rejectValue("playdate", "playdate.invalid");
						errors.rejectValue("playdate", "error.not-specified",null,"Date played cannot be in future");
					}
				}
			}											
	}	
}
