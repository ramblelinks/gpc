package com.ramblelinks.golfprocompanion.domain;

public class GolfCourseDetails {
	
	private GolfCourse golfCourse;
	private float slope_rating;
	private String tee_type;
	private int total_yards;
	private float course_rating;
	private String gender;
	private String tee_name;
	private int par_for_tee;
	
	private int golfcoursedetail_id;
	public int getGolfcoursedetail_id() {
		return golfcoursedetail_id;
	}
	public void setGolfcoursedetail_id(int golfcoursedetail_id) {
		this.golfcoursedetail_id = golfcoursedetail_id;
	}
	public GolfCourse getGolfCourse() {
		return golfCourse;
	}
	public void setGolfCourse(GolfCourse golfCourse) {
		this.golfCourse = golfCourse;
	}
	public float getSlope_rating() {
		return slope_rating;
	}
	public void setSlope_rating(float slope_rating) {
		this.slope_rating = slope_rating;
	}
	public String getTee_type() {
		return tee_type;
	}
	public void setTee_type(String tee_type) {
		this.tee_type = tee_type;
	}
	public int getTotal_yards() {
		return total_yards;
	}
	public void setTotal_yards(int total_yards) {
		this.total_yards = total_yards;
	}
	public float getCourse_rating() {
		return course_rating;
	}
	public void setCourse_rating(float course_rating) {
		this.course_rating = course_rating;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getTee_name() {
		return tee_name;
	}
	public void setTee_name(String tee_name) {
		this.tee_name = tee_name;
	}
	public int getPar_for_tee() {
		return par_for_tee;
	}
	public void setPar_for_tee(int par_for_tee) {
		this.par_for_tee = par_for_tee;
	}

	
	

}
