package com.ramblelinks.golfprocompanion.repository;

import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Repository
public class JdbcInvitationDao implements InvitationDao{
	
	protected final Log logger = LogFactory.getLog(getClass());
	private SimpleJdbcCall invitationProc;
		
	private SimpleJdbcCall createInvite;

	@Autowired
	public void init(DataSource dataSource){
		this.invitationProc = new SimpleJdbcCall(dataSource);
		this.createInvite = new SimpleJdbcCall(dataSource);
		this.createInvite.setAccessCallParameterMetaData(false);
		this.invitationProc.setAccessCallParameterMetaData(false);
	}
	
	@Override
	@Transactional
	public int createInvitation(int hostPlayer_id, int rcvPlayer_id, int p_golfcourse_id, String p_playDate, String p_playTime, String p_status, String p_description) {
		
		
		Date playDate;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		try {
			playDate = sdf.parse(p_playDate);
		
		
		logger.info("JDBC create invitation: " + rcvPlayer_id + " for date " + playDate);
		this.createInvite.withProcedureName("gm_createinvitation")
		 				   .declareParameters(new SqlParameter("p_hostPlayer_Id", Types.INTEGER))
		                   .declareParameters(new SqlParameter("p_rcvPlayer_id", Types.INTEGER))								 
		                   .declareParameters(new SqlParameter("p_playDate", Types.DATE))
		                   .declareParameters(new SqlParameter("p_playTime", Types.VARCHAR))
		                   .declareParameters(new SqlParameter("p_golfCourse_Id", Types.INTEGER))
						   .declareParameters(new SqlParameter("p_status", Types.VARCHAR))
						   .declareParameters(new SqlParameter("p_description", Types.VARCHAR))
						   .declareParameters(new SqlOutParameter("p_invitation_id", Types.INTEGER))
						   .declareParameters(new SqlOutParameter("p_retmessage", Types.VARCHAR));
		
		MapSqlParameterSource in = new MapSqlParameterSource()
										.addValue("p_hostPlayer_Id", hostPlayer_id, Types.INTEGER)
										.addValue("p_rcvPlayer_id", rcvPlayer_id, Types.INTEGER)										
										.addValue("p_playDate", playDate, Types.DATE)
										.addValue("p_playTime", p_playTime, Types.VARCHAR)
										.addValue("p_golfCourse_Id", p_golfcourse_id, Types.INTEGER)										
										.addValue("p_status", p_status, Types.VARCHAR)
										.addValue("p_description", p_description, Types.VARCHAR);										
		
		Map<?,?> out = this.createInvite.execute(in);
		
		return (Integer) out.get("p_invitation_id");
		} catch (ParseException e) {
			e.printStackTrace();
			return -9;
		}				
	}

	@Override
	@Transactional
	public String updateInvitation(int invitation_id, int player_id,
			String p_status, String p_description) {
		this.invitationProc.withProcedureName("gm_updateinvitation")
			.declareParameters(new SqlParameter("p_invitation_id", Types.INTEGER))
			.declareParameters(new SqlParameter("p_player_id", Types.INTEGER))		   								 		   
		   .declareParameters(new SqlParameter("p_status", Types.VARCHAR))
		   .declareParameters(new SqlParameter("p_description", Types.VARCHAR))		   
		   .declareParameters(new SqlOutParameter("p_retmessage", Types.VARCHAR));

		MapSqlParameterSource in = new MapSqlParameterSource()
						.addValue("p_invitation_id", invitation_id, Types.INTEGER)
						.addValue("p_player_id", player_id, Types.INTEGER)															
						.addValue("p_status", p_status, Types.VARCHAR)
						.addValue("p_description", p_description, Types.VARCHAR)
						.addValue("p_description", p_description, Types.VARCHAR);
							

		Map<?,?> out = this.invitationProc.execute(in);
		String ret =(String) out.get("p_retmessage");
		return ret;
	}
	
	
}
