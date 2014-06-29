package com.ramblelinks.golfprocompanion.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.ramblelinks.golfprocompanion.repository.CityDao;
import com.ramblelinks.golfprocompanion.repository.GolfClubDao;
import com.ramblelinks.golfprocompanion.repository.GolfCourseDao;
import com.ramblelinks.golfprocompanion.repository.GolfCourseDetailsDao;
import com.ramblelinks.golfprocompanion.repository.GolfCourseHolesDao;
import com.ramblelinks.golfprocompanion.repository.InvitationDao;
import com.ramblelinks.golfprocompanion.repository.PageDao;
import com.ramblelinks.golfprocompanion.repository.PlayerDao;
import com.ramblelinks.golfprocompanion.repository.PlayerDetailDao;
import com.ramblelinks.golfprocompanion.repository.PlayerInvitationDao;
import com.ramblelinks.golfprocompanion.repository.PlayerLoginDao;
import com.ramblelinks.golfprocompanion.repository.PlayerResultsDao;
import com.ramblelinks.golfprocompanion.repository.SearchRequestDao;
import com.ramblelinks.golfprocompanion.repository.StateDao;
import com.ramblelinks.golfprocompanion.view.RegisterPlayer;

@Service
public class ImplementPlayerManager implements PlayerManager{

	@Autowired
	private PlayerDao playerDao;
	@Autowired
	private PlayerDetailDao playerDetailDao;
	@Autowired
	private PlayerLoginDao playerLoginDao;
	@Autowired
	private StateDao stateDao; 
	@Autowired
	private PlayerResultsDao playerResultsDao;
	@Autowired
	private SearchRequestDao searchRequest;
	@Autowired
	private GolfClubDao golfClubDao;
	@Autowired
	private GolfCourseDao golfCourseDao;
	@Autowired
	private InvitationDao invitationDao;
	@Autowired
	private PlayerInvitationDao playerInvitationDao;
	@Autowired
	private CityDao cityDao;
	
	@Autowired
	private GolfCourseDetailsDao golfCourseDetailsDao;
	@Autowired
	private GolfCourseHolesDao golfCourseHolesDao;
	@Autowired
	private PageDao pd;
	


	public void setRegisterPlayerDao(PlayerDao playerDao) {
		this.playerDao = playerDao;
	}

	@Override
	public String addPlayer(RegisterPlayer registerPlayer) {
		return this.playerDao.addPlayer(registerPlayer);
	}

	@Override
	public Player getPlayer(String username, String password) {		
		return this.playerDao.getPlayer(username, password);
	}

	@Override
	public int addPlayerDetail(RegisterPlayer registerPlayer) {
		return this.playerDetailDao.addPlayerDetail(registerPlayer);
	}

	@Override
	public int addPlayerLogin(RegisterPlayer registerPlayer) {
		return this.playerLoginDao.addPlayerLogin(registerPlayer);
	}

	public void setPlayerDetailDao(PlayerDetailDao playerDetailDao) {
		this.playerDetailDao = playerDetailDao;
	}

	public void setPlayerLoginDao(PlayerLoginDao playerLoginDao) {
		this.playerLoginDao = playerLoginDao;
	}

	@Override
	public List<State> getStates() {
		return stateDao.getStateList(); 
	}
	
	public void setStateDao (StateDao stateDao){
		this.stateDao = stateDao;
	}

	@Override
	public List<PlayerResult> getPlayerResults(int playerId) {
		return this.playerResultsDao.getPlayerLastFiveResults(playerId);
	}

	public void setPlayerResultsDao(PlayerResultsDao playerResultsDao) {
		this.playerResultsDao = playerResultsDao;
	}

	@Override
	public List<SearchResult> searchCompanions(int player_id,String handicap,String gender, String age, String city, String zipcode, 
			//String playDateFrom,String playDateTo,String timeMeasure, 
			String radius, 
			//String golfcourse_id, 
			String p_latitude, String p_longitude) {
		return this.searchRequest.createSearchRequest(player_id, handicap, gender, age, city, zipcode, radius, p_latitude, p_longitude) ;
	}



	public void setGolfClubDao(GolfClubDao golfClubDao) {
		this.golfClubDao = golfClubDao;
	}

	public void setGolfCourseDao(GolfCourseDao golfCourseDao) {
		this.golfCourseDao = golfCourseDao;
	}

	@Override
	public List<GolfClub> getGolfClubs(int p_stateId) {
		return this.golfClubDao.getGolfClubs(p_stateId);
	}

	@Override
	public List<GolfCourse> getGolfCourses(int p_cityId, String p_zipcode, String p_radius, float p_lat, float p_long) {
		return this.golfCourseDao.getGolfCourses(p_cityId, p_zipcode, p_radius, p_lat, p_long);
	}

	@Override
	public int createInvitation(int hostPlayer_id, int rcvPlayer_id, int p_golfcourse_id, String p_playDate, String p_playTime, String p_status, String p_description) {		
		return this.invitationDao.createInvitation(hostPlayer_id, rcvPlayer_id,p_golfcourse_id,p_playDate,p_playTime,p_status,p_description);
	}

	public void setInvitationDao(InvitationDao invitationDao) {
		this.invitationDao = invitationDao;
	}
	

	public void setPlayerInvitationDao(PlayerInvitationDao playerInvitationDao) {
		this.playerInvitationDao = playerInvitationDao;
	}

	@Override
	public List<PlayerInvitation> getPlayerInvitation(int p_player_id) {
		return this.playerInvitationDao.getInvitations(p_player_id);
	}

	@Override
	public String updateInvitation(int invitation_id, int player_id, String p_status, String p_description) {	
		return this.invitationDao.updateInvitation(invitation_id, player_id, p_status, p_description);
	}

	/*@Override
	public List<String> getCities() {
		return this.cityDao.getCities();
	}*/
	
	@Override
	public List<City> getCities(String query) {
		return this.cityDao.getCities(query);
	}
	
	public void setCityDao(CityDao cityDao) {
		this.cityDao = cityDao;
	}

	@Override
	public List<PlayerInvitation> getSentInvitations(int player_id) {
		return this.playerInvitationDao.getSentInvitations(player_id);
	}

	@Override
	public int setPlayerRating(int reviewer_id, int reviwee_id, int rating,
			String comments) {
		return this.playerDao.setPlayerRating(reviewer_id, reviwee_id, rating, comments);
	}

	@Override
	public List<PlayerInvitation> getAcceptedExpiredInvitations(int p_player_id) {
		return this.playerInvitationDao.getAcceptedExpiredInvitations(p_player_id);
	}

	@Override
	public List<GolfCourse> getGolfCourses(String p_zipcode) {
		return this.golfCourseDao.getGolfCourses(p_zipcode);
	}
	
	@Override
	public List<GolfCourseDetails> getTeeTypes(int p_golfcourseid, String p_gender) {
		return this.golfCourseDetailsDao.getTeeTypes(p_golfcourseid, p_gender);
	}

	@Override
	public List<GolfCourseHolesMap> getcoursedetails(String p_golfCourse_id,
			String p_teetype, String p_gender, int p_player_id) {		
		return this.golfCourseHolesDao.getcoursedetails(p_golfCourse_id,p_teetype,p_gender, p_player_id);
	}

	@Override
	public String addPlayerScorecard(int p_playerId, int p_golfCourseId,
			int p_golfcoursedetailId, String p_DatePlayed, String p_playerScores, String p_TimePlayed) {
		return this.playerResultsDao.addPlayerScorecard(p_playerId, p_golfCourseId, p_golfcoursedetailId, p_DatePlayed, p_playerScores, p_TimePlayed) ;
	}

	@Override
	public List<GolfCourseHolesMap> getPlayerScoreCard(int p_playerResult_id) {
		return this.playerResultsDao.getScoreCard(p_playerResult_id);
	}

	@Override
	public String updateScorecard(int playerResult_id, String p_playerScores) {
		return this.playerResultsDao.updateScoreCard(playerResult_id, p_playerScores);
	}

	@Override
	public List<PlayerResult> getPlayerResultsByCourse(int playerId, int golfCourseId) {		
		return this.playerResultsDao.getPlayerResultsByCourse(playerId,golfCourseId);
	}

	@Override
	public int validateEmail(String p_email) {		
		return this.playerDao.validateEmail(p_email);
	}

	@Override
	public int validateUserName(String p_user) {
		return this.playerDao.validateUserName(p_user);
	}

	@Override
	public int resetPassword(String username, String password) {
		return this.playerDao.resetPassword(username, password);
	}

	@Override
	public List<PlayerResult> getTotalStats(int playerId) {
		return this.playerResultsDao.getTotalStats(playerId);
	}

	@Override
	public List<PlayerResult> getAllScores(int player_id) {
		return this.playerResultsDao.getAllScores(player_id);
	}

	@Override
	public Player getPlayerInfo(int p_player_id) {
		return this.playerDao.getPlayerInfo(p_player_id);
}

	@Override
	public int updatePlayeProfile(Player pp) {
		return this.playerDao.updatePlayerInfo(pp);
	}

	@Override
	public int getPageHitCount(String pageName) {
		return this.pd.getPageHitCount(pageName);
	}

	@Override
	public int updatePageHitCount(String pageName, int pageCount) {
		return this.pd.updatePageHitCount(pageName, pageCount);
	}


}
