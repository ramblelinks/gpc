package com.ramblelinks.golfprocompanion.domain;

import java.sql.Time;
import java.util.Date;

public class SearchResult {
	
	private int searchresult_id;
	private SearchRequest searchrequest;
	private Player players;
	private GolfCourse golfcourses;
	private Date from_date;
	private Date to_date;
	private Time playing_time;	
	private String playing_time_measure;
	private int player_handicap;
	private int player_age;
	
	
	public Date getFrom_date() {
		return from_date;
	}
	public void setFrom_date(Date from_date) {
		this.from_date = from_date;
	}
	public Date getTo_date() {
		return to_date;
	}
	public void setTo_date(Date to_date) {
		this.to_date = to_date;
	}
	public int getSearchresult_id() {
		return searchresult_id;
	}
	public void setSearchresult_id(int searchresult_id) {
		this.searchresult_id = searchresult_id;
	}
	public SearchRequest getSearchrequest() {
		return searchrequest;
	}
	public void setSearchrequest(SearchRequest searchrequest) {
		this.searchrequest = searchrequest;
	}
	public Player getPlayers() {
		return players;
	}
	public void setPlayers(Player players) {
		this.players = players;
	}
	public GolfCourse getGolfcourses() {
		return golfcourses;
	}
	public void setGolfcourses(GolfCourse golfcourses) {
		this.golfcourses = golfcourses;
	}
	public Time getPlaying_time() {
		return playing_time;
	}
	public void setPlaying_time(Time playing_time) {
		this.playing_time = playing_time;
	}
	public int getPlayer_handicap() {
		return player_handicap;
	}
	public void setPlayer_handicap(int player_handicap) {
		this.player_handicap = player_handicap;
	}
	public int getPlayer_age() {
		return player_age;
	}
	public void setPlayer_age(int player_age) {
		this.player_age = player_age;
	}
	public void setPlaying_time_measure(String playing_time_measure) {
		this.playing_time_measure = playing_time_measure;
	}
	public String getPlaying_time_measure() {
		return playing_time_measure;
	}	  

	  
}
