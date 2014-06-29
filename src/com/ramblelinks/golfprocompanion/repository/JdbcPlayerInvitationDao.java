package com.ramblelinks.golfprocompanion.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ramblelinks.golfprocompanion.domain.GolfCourse;
import com.ramblelinks.golfprocompanion.domain.Invitation;
import com.ramblelinks.golfprocompanion.domain.Player;
import com.ramblelinks.golfprocompanion.domain.PlayerInvitation;

@Repository
public class JdbcPlayerInvitationDao implements PlayerInvitationDao{

	
	protected final Log logger = LogFactory.getLog(getClass());
	private SimpleJdbcCall invitationProc; 
	private JdbcTemplate jdbcTemplate;
	
	
	@Autowired
	public void init(DataSource dataSource){
		this.invitationProc = new SimpleJdbcCall(dataSource);
		this.invitationProc.setAccessCallParameterMetaData(false);
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<PlayerInvitation> getInvitations(int p_player_id) {
		
		
		this.invitationProc.withProcedureName("gm_getInvitation")
		   					.declareParameters(new SqlParameter("p_player_id", Types.INTEGER));
		MapSqlParameterSource in = new MapSqlParameterSource()
									.addValue("p_player_id", p_player_id, Types.INTEGER);
		
		this.invitationProc.returningResultSet("playerinvitations", new PlayerInvitationMapper());
		Map<?,?> out = this.invitationProc.execute(in);
		return ((List<PlayerInvitation>) out.get("playerinvitations")); 
		
	}		
	
	private class PlayerInvitationMapper implements RowMapper<PlayerInvitation>
	{

		@Override
		public PlayerInvitation mapRow(ResultSet rs, int rowNum) throws SQLException {			
			PlayerInvitation pi = new PlayerInvitation();
			Player hostPlayer = new Player();
			GolfCourse invCourse = new GolfCourse();
			Invitation iv = new Invitation();
			Player guestPlayer = new Player();
			
			guestPlayer.setPlayer_id(rs.getInt("guest_playerId"));
			guestPlayer.setFirst_name(rs.getString("guest_fName"));
			guestPlayer.setLast_name(rs.getString("guest_lName"));
			guestPlayer.setHandicap_index(rs.getString("guest_handicap"));
			
			
			hostPlayer.setPlayer_id(rs.getInt("host_player_id"));
			hostPlayer.setFirst_name(rs.getString("host_first_name"));
			hostPlayer.setLast_name(rs.getString("host_last_name"));
			hostPlayer.setHandicap_index(rs.getString("host_handicap"));
			
			invCourse.setGolfcourse_id(rs.getInt("golfcourse_id"));
			invCourse.setCourse_name(rs.getString("course_name"));
			
			iv.setGolfCourse(invCourse);
			iv.setInvitation_id(rs.getInt("invitation_id"));
			iv.setPlayDate(rs.getDate("playdate"));
			iv.setPlayTime(rs.getTime("playtime"));
			iv.setStatus(rs.getString("mainInviteStatus"));
			iv.setPlayer(hostPlayer);
			
			
			pi.setPlayerinvitation_id(rs.getInt("playerInvitation_id"));
			pi.setStatus(rs.getString("playerInviteStatus"));
			pi.setDescription(rs.getString("playerInviteDesc"));
			pi.setHostInv(iv);	
			pi.setGuestPlayer(guestPlayer);
			
			return pi;
		}
	}



	@Override
	@Transactional(readOnly=true)
	public List<PlayerInvitation> getSentInvitations(int p_player_id) {
		
    	String sql = "select pi.playerInvitation_id," + 
				     "   iv.playdate," +
				     "   iv.playtime," +
				     "   iv.invitation_id , " +
				     "   iv.status mainInviteStatus," +
				     "   pi.status playerInviteStatus, " +
				     "   pi.description playerInviteDesc," +
				     "   pRcv.player_id guest_playerId," +
				     "   pRcv.first_name guest_fName," +
				     "   pRcv.last_name guest_lName," +
				     "   pRcv.handicap_index guest_handicap," +
				     "   gc.golfcourse_id, " +
				     " pHost.player_id host_player_id, " +
				 	 " pHost.first_name host_first_name, " +
				 	 " pHost.last_name host_last_name, " +
				 	 "pHost.handicap_index host_handicap, " +
				     "   gc.course_name " +
				  "from gm_playerInvitations pi " +
				  "	JOIN gm_invitations iv " +
				  "	ON pi.invitation_id = iv.invitation_id " +
				  "	JOIN gm_players pRcv " +
				  "	ON pRcv.player_id = pi.player_id " +
				  " JOIN gm_players pHost " +
				  "	ON pHost.player_id = iv.player_id " +
				  "	JOIN gm_golfCourses gc " +
				  "	ON gc.golfcourse_id = iv.golfCourse_id " +
				  "WHERE iv.player_id = ? " +
				  "AND iv.playdate > current_date AND iv.status != 'CANCELLED'" + 
				  "OR (iv.playDate = current_date and  iv.playTime > current_time)"; 				  
    	return this.jdbcTemplate.query(sql, new Object[] {p_player_id}, new PlayerInvitationMapper());    			
	}

	@Override
	@Transactional(readOnly=true)
	public List<PlayerInvitation> getAcceptedExpiredInvitations(int p_player_id) {
		
		String sql = "select pi.playerInvitation_id," + 
	     "   iv.playdate," +
	     "   iv.playtime," +
	     "   iv.invitation_id , " +
	     "   iv.status mainInviteStatus," +
	     "   pi.status playerInviteStatus, " +
	     "   pi.description playerInviteDesc," +
	     "   pRcv.player_id guest_playerId," +
	     "   pRcv.first_name guest_fName," +
	     "   pRcv.last_name guest_lName," +
	     "   pRcv.handicap_index guest_handicap," +
	     "   gc.golfcourse_id, " +
	     " pHost.player_id host_player_id, " +
	 	 " pHost.first_name host_first_name, " +
	 	 " pHost.last_name host_last_name, " +
	 	 "pHost.handicap_index host_handicap, " +
	     "   gc.course_name " +
	  "from gm_playerInvitations pi " +
	  "	JOIN gm_invitations iv " +
	  "	ON pi.invitation_id = iv.invitation_id " +
	  "	JOIN gm_players pRcv " +
	  "	ON pRcv.player_id = pi.player_id " +
	  " JOIN gm_players pHost " +
	  "	ON pHost.player_id = iv.player_id " +
	  "	JOIN gm_golfCourses gc " +
	  "	ON gc.golfcourse_id = iv.golfCourse_id " +
	  "WHERE iv.player_id = ? " +
	  "AND iv.playdate <= current_date AND pi.status = 'ACCEPTED'"; 				  
return this.jdbcTemplate.query(sql, new Object[] {p_player_id}, new PlayerInvitationMapper());
	}

}
