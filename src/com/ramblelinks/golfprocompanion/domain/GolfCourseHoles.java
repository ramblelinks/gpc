package com.ramblelinks.golfprocompanion.domain;

public class GolfCourseHoles {
   
	private int golfcoursehole_id;
	private GolfCourseDetails golfCourseDetail;	
	private int tee_number;
	private int total_yards;
	private int par;
	private int handicap;
	
	public int getGolfcoursehole_id() {
		return golfcoursehole_id;
	}
	public void setGolfcoursehole_id(int golfcoursehole_id) {
		this.golfcoursehole_id = golfcoursehole_id;
	}
	public GolfCourseDetails getGolfCourseDetail() {
		return golfCourseDetail;
	}
	public void setGolfCourseDetail(GolfCourseDetails golfCourseDetail) {
		this.golfCourseDetail = golfCourseDetail;
	}
	public int getTee_number() {
		return tee_number;
	}
	public void setTee_number(int tee_number) {
		this.tee_number = tee_number;
	}
	public int getTotal_yards() {
		return total_yards;
	}
	public void setTotal_yards(int total_yards) {
		this.total_yards = total_yards;
	}
	public int getPar() {
		return par;
	}
	public void setPar(int par) {
		this.par = par;
	}
	public int getHandicap() {
		return handicap;
	}
	public void setHandicap(int handicap) {
		this.handicap = handicap;
	}

	
   
}
