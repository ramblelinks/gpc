package com.ramblelinks.golfprocompanion.view;


public class RegisterPlayer {	

    private int player_id;
    
    private String fname;
    private String latitude;
    private String longitude;
    private String mname;
    
    private String lname;
    private String addressOne;
    private String addressTwo;
    private String city;
    
    private String zipcode;
    private String phoneId;
    private String phoneType;
    private String gender;
    private String dob;
    private String occupation;
    private String country;
    
    private String emailaddress;
    private String passwd;
    private String confirmpasswd;
    
    private String emailnotification;
    
    private String confirmregistration;
//    private LargeObject photo; 
    
    public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getAddressOne() {
		return addressOne;
	}

	public void setAddressOne(String addressOne) {
		this.addressOne = addressOne;
	}

	public String getAddressTwo() {
		return addressTwo;
	}

	public void setAddressTwo(String addressTwo) {
		this.addressTwo = addressTwo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	private int stateId; 

    public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public void setFname(String str) {
        fname = str;
    }

    public String getFname() {
        return fname;
    }

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getOccupation() {
		return occupation;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountry() {
		return country;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneId(String phoneId) {
		this.phoneId = phoneId;
	}

	public String getPhoneId() {
		return phoneId;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setConfirmpasswd(String confirmpasswd) {
		this.confirmpasswd = confirmpasswd;
	}

	public String getConfirmpasswd() {
		return confirmpasswd;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public int getPlayer_id() {
		return player_id;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setEmailnotification(String emailnotification) {
		this.emailnotification = emailnotification;
	}

	public String getEmailnotification() {
		return emailnotification;
	}

	public void setConfirmregistration(String confirmregistration) {
		this.confirmregistration = confirmregistration;
	}

	public String getConfirmregistration() {
		return confirmregistration;
	}

	/*public void setPhoto(LargeObject photo) {
		this.photo = photo;
	}

	public LargeObject getPhoto() {
		return photo;
	}*/

}
