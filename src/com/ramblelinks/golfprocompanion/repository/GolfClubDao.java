package com.ramblelinks.golfprocompanion.repository;

import java.util.List;

import com.ramblelinks.golfprocompanion.domain.GolfClub;

public interface GolfClubDao {
	
	public List<GolfClub> getGolfClubs(int p_stateId);

}
