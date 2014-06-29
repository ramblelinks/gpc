package com.ramblelinks.golfprocompanion.service;

import java.util.List;

import com.ramblelinks.golfprocompanion.domain.City;
import com.ramblelinks.golfprocompanion.domain.GolfClub;
import com.ramblelinks.golfprocompanion.domain.GolfCourse;
import com.ramblelinks.golfprocompanion.domain.GolfCourseDetails;
import com.ramblelinks.golfprocompanion.domain.GolfCourseHolesMap;
import com.ramblelinks.golfprocompanion.domain.Player;
import com.ramblelinks.golfprocompanion.domain.PlayerInvitation;
import com.ramblelinks.golfprocompanion.domain.PlayerResult;
import com.ramblelinks.golfprocompanion.domain.SearchResult;
import com.ramblelinks.golfprocompanion.domain.State;
import com.ramblelinks.golfprocompanion.view.RegisterPlayer;

public interface PlayerManager {
	
	public int validateEmail(String p_email );
	
	public int validateUserName(String p_user ); 

	public String addPlayer(RegisterPlayer registerPlayer);
	
	public int resetPassword(String username, String password);
	
	public int addPlayerDetail(RegisterPlayer registerPlayer);
	
	public int addPlayerLogin(RegisterPlayer registerPlayer);
	
	public Player getPlayerInfo(int p_player_id);
	
	public Player getPlayer(String username, String password);
	
	public List<State> getStates();
	
	public List<PlayerResult> getPlayerResults(int playerId);
	
	public List<SearchResult> searchCompanions(int player_id,String handicap,String gender, String age, String city, String zipcode, 
			//String playDateFrom,String playDateTo,String timeMeasure, 
			String radius, 
			//String golfcourse_id, 
			String p_latitiude, String p_longitude);
	
	public List<GolfClub> getGolfClubs(int p_stateId);
	
	public List<GolfCourse> getGolfCourses(int p_cityId, String p_zipcode, String p_radius, float p_lat, float p_long);
	
	public int createInvitation(int hostPlayer_id, int rcvPlayer_id, int p_golfcourse_id, String p_playDate, String p_playTime, String p_status, String p_description);
	
	public List<PlayerInvitation> getPlayerInvitation(int p_player_id);
	
	public String updateInvitation(int invitation_id, int player_id, String p_status, String p_description);
	
	public List<PlayerInvitation> getSentInvitations(int player_id);
	
	public List<City> getCities(String query);
	
	public int setPlayerRating(int reviewer_id, int reviwee_id, int rating, String comments);
	
	public List<PlayerInvitation> getAcceptedExpiredInvitations(int p_player_id);
	
	public List<GolfCourse> getGolfCourses(String p_zipcode);
	
	public List<GolfCourseDetails> getTeeTypes(int p_golfcourseid, String p_gender);
	
	public List<GolfCourseHolesMap> getcoursedetails(String golfCourse_id,String teetype,String gender, int player_id);
	
	public String addPlayerScorecard(int p_playerId, int p_golfCourseId, int p_golfcoursedetailId, String p_DatePlayed, String p_playerScores, String p_TimePlayed);
	
	public List<GolfCourseHolesMap> getPlayerScoreCard(int p_playerResult_id);
	
	public String updateScorecard (int playerResult_id, String p_playerScores);
	
	public List<PlayerResult> getPlayerResultsByCourse(int playerId, int golfCourseId);
	
	public List<PlayerResult> getTotalStats(int playerId);
	
	public List<PlayerResult> getAllScores (int player_id);
	
	public int updatePlayeProfile(Player pp);
	
	public int getPageHitCount(String pageName);
	
	public int updatePageHitCount(String pageName, int pageCount);
}
