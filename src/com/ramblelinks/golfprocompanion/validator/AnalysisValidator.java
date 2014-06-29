 package com.ramblelinks.golfprocompanion.validator;


import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;
import org.springframework.validation.Errors;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ramblelinks.golfprocompanion.utilites.StringManupilations;
import com.ramblelinks.golfprocompanion.view.RegisterPlayer;
import com.ramblelinks.golfprocompanion.view.ScorecardPage;

@Service
public class AnalysisValidator implements Validator {
	
	/** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
       

	@Override
	public boolean supports(Class<?> clazz) {
		return RegisterPlayer.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		ScorecardPage sp = (ScorecardPage) obj;
		logger.info("In scorecard Validate");
		
		if (sp == null){
			errors.rejectValue("scoreCard", "error.not-specified",null,"Value Required");
		}
								
			StringManupilations sm = new StringManupilations();
			
			
			if (sp.getZipcode().isEmpty() && sp.getCity().isEmpty())
			{
				logger.info("Zipcode is empty");
				errors.rejectValue("zipcode", "error.not-specified",null,"Either zipcode or city needs to be provided.");
			}
			
			if (!sp.getCity().isEmpty() && (Integer.parseInt(sp.getCity_id()) == -9 || sp.getCity_id().isEmpty()))
			{
				errors.rejectValue("city", "error.not-specified",null,"Please select an appropriate city.");
			}		
			
			if (Integer.parseInt(sp.getGolfcourse()) == -9)
			{
				errors.rejectValue("golfcourse", "error.not-specified",null,"Golf Course must be selected.");
			}
			
			if (!sp.getZipcode().isEmpty()) {
				//logger.info("Zipcode not empty");
				if (!sm.RegexHarnes("^\\d{5}$", sp.getZipcode()))
				{
					errors.rejectValue("zipcode", "error.not-specified",null,"Correct format for zip code is #####");
				}
			}						
																
	}	
}
