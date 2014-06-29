package com.ramblelinks.golfprocompanion.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ramblelinks.golfprocompanion.utilites.StringManupilations;
import com.ramblelinks.golfprocompanion.view.HomePage;

@Service
public class HomeValidator implements Validator{

	/** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
 
	 
	@Override
	public boolean supports(Class<?> arg) {
		return HomePage.class.equals(arg);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		HomePage hp = (HomePage) obj;
		
		if (hp == null){
			errors.rejectValue("homePage", "error.not-hpecified",null,"Value Required");
		}
		
		StringManupilations sm = new StringManupilations();
		
		
		if (hp.getZipcode().isEmpty() && hp.getCity().isEmpty())
		{
			errors.rejectValue("zipcode", "error.not-hpecified",null,"Either zipcode or city needs to be provided.");
		}
		
		if (!hp.getCity().isEmpty() && (Integer.parseInt(hp.getCity_id()) == -9 || hp.getCity_id().isEmpty()))
		{
			errors.rejectValue("city", "error.not-hpecified",null,"Please select an appropriate city.");
		}
			
					
		if (!hp.getZipcode().isEmpty()) {
			//logger.info("Zipcode not empty");
			if (!sm.RegexHarnes("^\\d{5}$", hp.getZipcode()))
			{
				errors.rejectValue("zipcode", "error.not-hpecified",null,"Correct format for zip code is #####");
			}
		}						
				
	}

}
