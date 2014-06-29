package com.ramblelinks.golfprocompanion.domain;

import java.sql.Time;
import java.util.Date;


public class Invitation {
	
	private int invitation_id;
	private Player player;
	private Date playDate;
	private Time playTime;
	private GolfCourse golfCourse;
	private String status;
	private String description;
	
	
	public int getInvitation_id() {
		return invitation_id;
	}
	public void setInvitation_id(int invitation_id) {
		this.invitation_id = invitation_id;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public Date getPlayDate() {
		return playDate;
	}
	public void setPlayDate(Date playDate) {
		this.playDate = playDate;
	}
	public Time getPlayTime() {
		return playTime;
	}
	public void setPlayTime(Time playTime) {
		this.playTime = playTime;
	}
	public GolfCourse getGolfCourse() {
		return golfCourse;
	}
	public void setGolfCourse(GolfCourse golfCourse) {
		this.golfCourse = golfCourse;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
