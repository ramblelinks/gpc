package com.ramblelinks.golfprocompanion.domain;

import sun.security.util.Password;

public class PlayerLogin {
	
	private int player_id;
	private String login_name;
	private Password login_password;
	
	public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public Password getLogin_password() {
		return login_password;
	}
	public void setLogin_password(Password login_password) {
		this.login_password = login_password;
	}
	

}
