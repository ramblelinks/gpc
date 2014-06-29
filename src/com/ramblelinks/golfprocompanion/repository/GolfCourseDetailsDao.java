package com.ramblelinks.golfprocompanion.repository;

import java.util.List;

import com.ramblelinks.golfprocompanion.domain.GolfCourseDetails;

public interface GolfCourseDetailsDao {

	public List<GolfCourseDetails> getTeeTypes(int p_golfcourseid, String p_gender);
	

}
