package com.ramblelinks.golfprocompanion.repository;

import java.util.List;

import com.ramblelinks.golfprocompanion.domain.GolfCourseHolesMap;
import com.ramblelinks.golfprocompanion.domain.PlayerResult;

public interface PlayerResultsDao {

	public List<PlayerResult> getPlayerLastFiveResults(int playerId);
	
	public List<PlayerResult> getPlayerResultsByCourse(int playerId, int golfCourseId);
	
	public String addPlayerScorecard(int p_playerId, int p_golfCourseId, int p_golfcoursedetailId, String p_DatePlayed, String p_playerScores, String p_TimePlayed);
		
	public List<GolfCourseHolesMap> getScoreCard(int p_playerResult_id);
	
	public String updateScoreCard(int playerResult_id, String playerScore);
	
	public List<PlayerResult> getTotalStats(int playerId);
	
	public List<PlayerResult> getAllScores(int player_id);
}
