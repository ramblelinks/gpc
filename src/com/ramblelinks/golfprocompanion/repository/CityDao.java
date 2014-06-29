package com.ramblelinks.golfprocompanion.repository;

import java.util.List;

import com.ramblelinks.golfprocompanion.domain.City;


public interface CityDao {
	
	//public List<String> getCities();
	
	public List<City> getCities(String query);

}
