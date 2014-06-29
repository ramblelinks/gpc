package com.ramblelinks.golfprocompanion.repository;

import java.util.List;

import com.ramblelinks.golfprocompanion.domain.GolfCourse;

public interface GolfCourseDao {

	public List<GolfCourse> getGolfCourses(int p_cityId, String p_zipcode, String radius, float p_lat, float p_long);
	
	public List<GolfCourse> getGolfCourses(String p_zipcode);
}
