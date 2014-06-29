package com.ramblelinks.golfprocompanion.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;


import com.ramblelinks.golfprocompanion.view.FaqPage;

@Controller
@RequestMapping("/faq")
public class FaqController {
	
	protected Log logger = LogFactory.getLog(getClass());
	
	@RequestMapping(method=RequestMethod.GET)
	public String getFaqPage(Model m) throws ParseException
	{
		FaqPage fp = new FaqPage();		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");		
		fp.setDob(sdf.parse("10/18/1980"));
		m.addAttribute("faqdate",fp);
		return "faq";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String setFaqPage(@ModelAttribute("faqdate") FaqPage fp)
	{
		logger.info("Value passed is: " + fp.getDob());
		return "faq";
	}

}
