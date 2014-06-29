package com.ramblelinks.golfprocompanion.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Player {

	 private int player_id;
	 private String first_name;
	 private String middle_name;
	 private String last_name ;
	 @DateTimeFormat(pattern = "MM/dd/yyyy") 
	 private Date date_of_birth;
	 private String street_address1;
	 private String street_address2;
	 private String city;
	 private int state_id;
	 private String zip_code;
	 private int country_id;
	 private int phone_id;
	 private String email_address;	 
	 private int default_golfcourse_id;
	 private String gender;
	 private String review_rating;	  
	 private String email_notifications;
	 private boolean emailnotifs=false;
	 private String handicap_index;
	 private float latitude;
	 private float longitude;
	 private int fav_brand_id;
	 private String fav_golf_player;
	 private PlayerDetail pd;
	 private String occupation;
	 
	 public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getMiddle_name() {
		return middle_name;
	}
	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public Date getDate_of_birth() {
		return date_of_birth;
	}
	public void setDate_of_birth(Date date_of_birth) {
		this.date_of_birth = date_of_birth;
	}
	public String getStreet_address1() {
		return street_address1;
	}
	public void setStreet_address1(String street_address1) {
		this.street_address1 = street_address1;
	}
	public String getStreet_address2() {
		return street_address2;
	}
	public void setStreet_address2(String street_address2) {
		this.street_address2 = street_address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getState_id() {
		return state_id;
	}
	public void setState_id(int state_id) {
		this.state_id = state_id;
	}
	public String getZip_code() {
		return zip_code;
	}
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}
	public int getCountry_id() {
		return country_id;
	}
	public void setCountry_id(int country_id) {
		this.country_id = country_id;
	}
	public int getPhone_id() {
		return phone_id;
	}
	public void setPhone_id(int phone_id) {
		this.phone_id = phone_id;
	}
	public String getEmail_address() {
		return email_address;
	}
	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}
	public int getDefault_golfcourse_id() {
		return default_golfcourse_id;
	}
	public void setDefault_golfcourse_id(int default_golfcourse_id) {
		this.default_golfcourse_id = default_golfcourse_id;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getReview_rating() {
		return review_rating;
	}
	public void setReview_rating(String review_rating) {
		this.review_rating = review_rating;
	}
	public String getEmail_notifications() {
		return email_notifications;
	}
	public void setEmail_notifications(String email_notifications) {
		this.email_notifications = email_notifications;
	}
	public String getHandicap_index() {
		return handicap_index;
	}
	public void setHandicap_index(String handicap_index) {
		this.handicap_index = handicap_index;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public int getFav_brand_id() {
		return fav_brand_id;
	}
	public void setFav_brand_id(int fav_brand_id) {
		this.fav_brand_id = fav_brand_id;
	}
	public String getFav_golf_player() {
		return fav_golf_player;
	}
	public void setFav_golf_player(String fav_golf_player) {
		this.fav_golf_player = fav_golf_player;
	}
	public void setEmailnotifs(boolean emailnotifs) {
		this.emailnotifs = emailnotifs;
	}
	public boolean isEmailnotifs() {
		return emailnotifs;
	}
	public void setPd(PlayerDetail pd) {
		this.pd = pd;
	}
	public PlayerDetail getPd() {
		return pd;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getOccupation() {
		return occupation;
	}
	 
}
