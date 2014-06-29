package com.ramblelinks.golfprocompanion.domain;

import java.sql.Time;
import java.util.Date;

public class SearchRequest {

	private int searchrequest_id;
	private int player_id;
	private int golfcourse_id;
	private Date search_date;
	private String search_time_measure ;
	private Time search_time;
	private int min_handicap;
	private int max_handicap;
	private String search_zipcode ;
	private int min_search_radius;
	private int max_search_radius;
	private int min_age;
	private int max_age;
	private String gender;
	public int getSearchrequest_id() {
		return searchrequest_id;
	}
	public void setSearchrequest_id(int searchrequest_id) {
		this.searchrequest_id = searchrequest_id;
	}
	public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public int getGolfcourse_id() {
		return golfcourse_id;
	}
	public void setGolfcourse_id(int golfcourse_id) {
		this.golfcourse_id = golfcourse_id;
	}
	public Date getSearch_date() {
		return search_date;
	}
	public void setSearch_date(Date search_date) {
		this.search_date = search_date;
	}
	public String getSearch_time_measure() {
		return search_time_measure;
	}
	public void setSearch_time_measure(String search_time_measure) {
		this.search_time_measure = search_time_measure;
	}
	public Time getSearch_time() {
		return search_time;
	}
	public void setSearch_time(Time search_time) {
		this.search_time = search_time;
	}
	public int getMin_handicap() {
		return min_handicap;
	}
	public void setMin_handicap(int min_handicap) {
		this.min_handicap = min_handicap;
	}
	public int getMax_handicap() {
		return max_handicap;
	}
	public void setMax_handicap(int max_handicap) {
		this.max_handicap = max_handicap;
	}
	public String getSearch_zipcode() {
		return search_zipcode;
	}
	public void setSearch_zipcode(String search_zipcode) {
		this.search_zipcode = search_zipcode;
	}
	public int getMin_search_radius() {
		return min_search_radius;
	}
	public void setMin_search_radius(int min_search_radius) {
		this.min_search_radius = min_search_radius;
	}
	public int getMax_search_radius() {
		return max_search_radius;
	}
	public void setMax_search_radius(int max_search_radius) {
		this.max_search_radius = max_search_radius;
	}
	public int getMin_age() {
		return min_age;
	}
	public void setMin_age(int min_age) {
		this.min_age = min_age;
	}
	public int getMax_age() {
		return max_age;
	}
	public void setMax_age(int max_age) {
		this.max_age = max_age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	  
	 
	
}
