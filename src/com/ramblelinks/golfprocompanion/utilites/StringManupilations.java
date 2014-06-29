package com.ramblelinks.golfprocompanion.utilites;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class StringManupilations {
	
	public Map<String,String> split(String value, String delimiter){
		
		String firstValue, secondValue;
		Map<String,String> retValue = new HashMap<String, String>();
		firstValue = value.substring(0, value.indexOf(delimiter));
		secondValue = value.substring(value.indexOf(delimiter) + 1, value.length());
		retValue.put("minValue", firstValue);		
		retValue.put("maxValue", secondValue);				
		return retValue;
	}
	
	public boolean RegexHarnes(String p, String m){
		
		boolean found = false;
		Pattern pattern = Pattern.compile(p);
		Matcher matcher = pattern.matcher(m);
		while(matcher.find())
		{
			found = true;
		}		
		return found;
	}
	
	
	public boolean RegexDateHarnes(String p, String m){
		
		//boolean found = false;
		Pattern pattern = Pattern.compile(p);
		Matcher matcher = pattern.matcher(m);
		if(matcher.matches()){
			 
			 matcher.reset();
		 
			 if(matcher.find()){
		 
		         String day = matcher.group(2);
			     String month = matcher.group(1);
			     int year = Integer.parseInt(matcher.group(3));
		 
			     if (day.equals("31") && 
				  (month.equals("4") || month .equals("6") || month.equals("9") ||
		                  month.equals("11") || month.equals("04") || month .equals("06") ||
		                  month.equals("09"))) {
					return false; // only 1,3,5,7,8,10,12 has 31 days
			     } else if (month.equals("2") || month.equals("02")) {
		                  //leap year
				  if(year % 4==0){
					  if(day.equals("30") || day.equals("31")){
						  return false;
					  }else{
						  return true;
					  }
				  }else{
				         if(day.equals("29")||day.equals("30")||day.equals("31")){
						  return false;
				         }else{
						  return true;
					  }
				  }
			      }else{				 
				return true;				 
			      }
			   }else{
		    	      return false;
			   }		  
		     }else{
			  return false;
		     }			    
		   }	
}
