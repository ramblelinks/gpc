package com.ramblelinks.golfprocompanion.domain;

public class PlayerInvitation {
	
	private int playerinvitation_id;
	private Invitation hostInv;
	private Player guestPlayer;
	private String status;
	private String description;
	
	
	public int getPlayerinvitation_id() {
		return playerinvitation_id;
	}
	public void setPlayerinvitation_id(int playerinvitation_id) {
		this.playerinvitation_id = playerinvitation_id;
	}
	public Invitation getHostInv() {
		return hostInv;
	}
	public void setHostInv(Invitation hostInv) {
		this.hostInv = hostInv;
	}
	public Player getGuestPlayer() {
		return guestPlayer;
	}
	public void setGuestPlayer(Player guestPlayer) {
		this.guestPlayer = guestPlayer;
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
