package com.ramblelinks.golfprocompanion.repository;

import java.util.List;
import com.ramblelinks.golfprocompanion.domain.GolfCourseHolesMap;

public interface GolfCourseHolesDao {


	public List<GolfCourseHolesMap> getcoursedetails(String p_golfCourse_id,
			String p_teetype, String p_gender, int p_player_id);
	

}
