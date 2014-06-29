package com.ramblelinks.golfprocompanion.domain;

public class State 
{

	private int stateId;
	private String stateCode;
	private String stateName;
	
	public int getStateId() {
		return stateId;
	}
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	
	public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Code: " + stateCode+ ";");
        buffer.append("Name: " + stateName+ ";");
        buffer.append("Id: " + stateId);
        return buffer.toString();
    }
	
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getStateName() {
		return stateName;
	}

}
