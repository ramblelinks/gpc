package com.ramblelinks.golfprocompanion.repository;

import java.util.List;

import com.ramblelinks.golfprocompanion.domain.SearchResult;

public interface SearchRequestDao {
	
	public List<SearchResult> createSearchRequest(int player_id,String handicap,String gender, String age, String city, String zipcode, 
			//String playDateFrom,String playDateTo,String timeMeasure, 
			String radius, 
			//String golfcourse_id, 
			String p_latitiude, String p_longitude);

}
