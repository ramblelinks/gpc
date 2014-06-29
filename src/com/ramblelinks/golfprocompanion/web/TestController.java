package com.ramblelinks.golfprocompanion.web;



import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.ramblelinks.golfprocompanion.domain.City;
import com.ramblelinks.golfprocompanion.domain.GolfCourse;
import com.ramblelinks.golfprocompanion.domain.JqGridData;
import com.ramblelinks.golfprocompanion.domain.PlayerResult;
import com.ramblelinks.golfprocompanion.domain.User;
import com.ramblelinks.golfprocompanion.service.ImplementUserManager;
import com.ramblelinks.golfprocompanion.service.PlayerManager;
import com.ramblelinks.golfprocompanion.utilites.DropDown;



@Controller
@RequestMapping("/test")
public class TestController {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private final PlayerManager playerManager;
	
	@Autowired
	public TestController(PlayerManager playerManager) {
		this.playerManager = playerManager;
		
	}

	@RequestMapping(value="/t",method=RequestMethod.GET)
	public String test(Model model)
	{
		model.addAttribute("golfclub", this.playerManager.getGolfClubs(40));
		return "try";
	}
	
/*	@RequestMapping(value="/golfcourse/{golfclub}", method=RequestMethod.GET, produces="application/json")
	public  @ResponseBody List<GolfCourse> getGolfCourses(@PathVariable int golfclub) {		
		//logger.info("golf club selected is : " + golfclub);
		List<GolfCourse> gc = this.playerManager.getGolfCourses(golfclub);
		return gc;
	}*/
	
	@RequestMapping(value="city",method=RequestMethod.GET,produces="application/json")
	public @ResponseBody List<DropDown> getCities()
	{		
		List<City> c = this.playerManager.getCities("");
		List<DropDown> ddl = new ArrayList<DropDown>();
		DropDown dd;
		
		for (City cl : c) {
			dd= new DropDown();
			dd.setLabel(cl.getCity_name());
			dd.setValue(cl.getCity_id());
			ddl.add(dd);
		}
		return ddl;
	}
	
	@RequestMapping(value="/dd",method=RequestMethod.GET,produces="application/json")
	public @ResponseBody List<DropDown> getDropDown(){
		List<DropDown> ddL = new ArrayList<DropDown> ();
		DropDown dd = new DropDown();
		
		dd.setValue(1);
		dd.setLabel("One");
		ddL.add(dd);
		
		dd = new DropDown();
		dd.setValue(2);
		dd.setLabel("Two");
		ddL.add(dd);
		
		return ddL;
	}
	
	@RequestMapping(value="/stats",method=RequestMethod.GET,produces="application/json")
	public @ResponseBody JqGridData<?> getStats()
	{
		JqGridData<PlayerResult> pr = new JqGridData<PlayerResult>(1, 1, 1, this.playerManager.getTotalStats(38));
		return pr;
	}
	
	@RequestMapping(value="/g",method=RequestMethod.GET,produces="application/json")
	public @ResponseBody JqGridData<?> jsonTest()
	{
		//String retVal;	
		ImplementUserManager um = new ImplementUserManager();
		List<User> ul = um.getUser();
		 logger.info("um size is: " + ul.size());
		
		
		
		int numberOfRows = 1;
	    int pageNumber = 1; // This example will only every show a single page.
	    int totalNumberOfRecords =  1; // All in there are 8 records in our dummy data object
		
		JqGridData<User> gridData = new JqGridData<User>(numberOfRows, pageNumber, totalNumberOfRecords,ul );
		//retVal ="";
		return gridData;
	}
	
	
/*	@RequestMapping(value="/gc", method=RequestMethod.GET, produces="application/json")
	public  @ResponseBody JqGridData<?> getGolfCourses() {		
		
		List<GolfCourse> gc = this.playerManager.getGolfCourses(23);
		logger.info("Inside test golf courses " + gc.size());
		int numberOfRows = gc.size();
	    int pageNumber = 1; // This example will only every show a single page.
	    int totalNumberOfRecords =  gc.size(); // All in there are 8 records in our dummy data object
		JqGridData<GolfCourse> gridData = new JqGridData<GolfCourse>(numberOfRows, pageNumber, totalNumberOfRecords,gc );
		return gridData;
		//return this.playerManager.getGolfCourses(golfclub);
	}
	*/
	
	@RequestMapping(value="/pl",method=RequestMethod.GET)
	public @ResponseBody JqGridData<?> getPlayerLasFiveRounds(){
		
		List<PlayerResult> pr = this.playerManager.getPlayerResults(38);
		

		logger.info("Inside test golf courses " + pr.size());
		int numberOfRows = pr.size();
	    int pageNumber = 1; // This example will only every show a single page.
	    int totalNumberOfRecords =  pr.size(); // All in there are 8 records in our dummy data object
		JqGridData<PlayerResult> gridData = new JqGridData<PlayerResult>(numberOfRows, pageNumber, totalNumberOfRecords,pr );
		return gridData;
	}
	
}
