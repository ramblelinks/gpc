package com.ramblelinks.golfprocompanion.domain;

import java.util.Date;

public class PlayerResult {
	
	private int playerResult_id;
	private GolfCourse golfCourse;
	private int player_id;
	private int golfCourse_id;
	private Date date_played;
	private int total_score;
	private int eagles;
	private int birdies;
	private int pars;
	private int bogeys;
	private int doublebogeys;
	private int doublebogeyplus;
	private int handicap_differential;
	private int net_score;
	private String tee_type;
	
	
	public int getPlayerResult_id() {
		return playerResult_id;
	}
	public void setPlayerResult_id(int playerResult_id) {
		this.playerResult_id = playerResult_id;
	}
	public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public int getGolfCourse_id() {
		return golfCourse_id;
	}
	public void setGolfCourse_id(int golfCourse_id) {
		this.golfCourse_id = golfCourse_id;
	}
	public Date getDate_played() {
		return date_played;
	}
	public void setDate_played(Date date_played) {
		this.date_played = date_played;
	}
	public int getTotal_score() {
		return total_score;
	}
	public void setTotal_score(int total_score) {
		this.total_score = total_score;
	}
	public int getEagles() {
		return eagles;
	}
	public void setEagles(int eagles) {
		this.eagles = eagles;
	}
	public int getBirdies() {
		return birdies;
	}
	public void setBirdies(int birdies) {
		this.birdies = birdies;
	}
	public int getPars() {
		return pars;
	}
	public void setPars(int pars) {
		this.pars = pars;
	}
	public int getBogeys() {
		return bogeys;
	}
	public void setBogeys(int bogeys) {
		this.bogeys = bogeys;
	}
	public int getDoublebogeys() {
		return doublebogeys;
	}
	public void setDoublebogeys(int doublebogeys) {
		this.doublebogeys = doublebogeys;
	}
	public int getDoublebogeyplus() {
		return doublebogeyplus;
	}
	public void setDoublebogeyplus(int doublebogeyplus) {
		this.doublebogeyplus = doublebogeyplus;
	}
	public int getHandicap_differential() {
		return handicap_differential;
	}
	public void setHandicap_differential(int handicap_differential) {
		this.handicap_differential = handicap_differential;
	}
	public int getNet_score() {
		return net_score;
	}
	public void setNet_score(int net_score) {
		this.net_score = net_score;
	}
	public String getTee_type() {
		return tee_type;
	}
	public void setTee_type(String tee_type) {
		this.tee_type = tee_type;
	}
	public void setGolfCourse(GolfCourse golfCourse) {
		this.golfCourse = golfCourse;
	}
	public GolfCourse getGolfCourse() {
		return golfCourse;
	}
	
	

}
