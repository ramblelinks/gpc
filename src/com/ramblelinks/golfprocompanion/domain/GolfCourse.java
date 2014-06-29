package com.ramblelinks.golfprocompanion.domain;

import java.util.Date;

public class GolfCourse {

	
	  private int golfcourse_id ;
	  private GolfClub golfClub;
	  private String course_name ;
	  private int holes ;
	  private int par ;
	  private String course_type ;
	  private String  course_architect;
	  private Date open_date ;
	  private String guest_policy ;
	  private int weekday_price ;
	  private int weekend_price ;
	  private int twilight_price ;
	  private String  fairway_grass;
	  private String green_grass;
	  private String currency;
	  private String file_course_id;
	
	  public int getGolfcourse_id() {
		return golfcourse_id;
	}
	public void setGolfcourse_id(int golfcourse_id) {
		this.golfcourse_id = golfcourse_id;
	}
	public GolfClub getGolfClub() {
		return golfClub;
	}
	public void setGolfClub(GolfClub golfClub) {
		this.golfClub = golfClub;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	
	public int getHoles() {
		return holes;
	}
	public void setHoles(int holes) {
		this.holes = holes;
	}
	public int getPar() {
		return par;
	}
	public void setPar(int par) {
		this.par = par;
	}
	public String getCourse_type() {
		return course_type;
	}
	public void setCourse_type(String course_type) {
		this.course_type = course_type;
	}
	public String getCourse_architect() {
		return course_architect;
	}
	public void setCourse_architect(String course_architect) {
		this.course_architect = course_architect;
	}
	public Date getOpen_date() {
		return open_date;
	}
	public void setOpen_date(Date open_date) {
		this.open_date = open_date;
	}
	public String getGuest_policy() {
		return guest_policy;
	}
	public void setGuest_policy(String guest_policy) {
		this.guest_policy = guest_policy;
	}
	public int getWeekday_price() {
		return weekday_price;
	}
	public void setWeekday_price(int weekday_price) {
		this.weekday_price = weekday_price;
	}
	public int getWeekend_price() {
		return weekend_price;
	}
	public void setWeekend_price(int weekend_price) {
		this.weekend_price = weekend_price;
	}
	public int getTwilight_price() {
		return twilight_price;
	}
	public void setTwilight_price(int twilight_price) {
		this.twilight_price = twilight_price;
	}
	public String getFairway_grass() {
		return fairway_grass;
	}
	public void setFairway_grass(String fairway_grass) {
		this.fairway_grass = fairway_grass;
	}
	public String getGreen_grass() {
		return green_grass;
	}
	public void setGreen_grass(String green_grass) {
		this.green_grass = green_grass;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getFile_course_id() {
		return file_course_id;
	}
	public void setFile_course_id(String file_course_id) {
		this.file_course_id = file_course_id;
	}
	
	  
}
