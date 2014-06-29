package com.ramblelinks.golfprocompanion.repository;

import java.sql.Types;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ramblelinks.golfprocompanion.view.RegisterPlayer;

@Repository
public class JdbcPlayerLoginDao implements PlayerLoginDao{

	
	/** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    
    private SimpleJdbcCall procCreatePlayer;
    
    
    @Autowired
    public void init (DataSource dataSource)
    {    	     
    	this.procCreatePlayer = new SimpleJdbcCall(dataSource).withFunctionName("gm_createlogin");    
    }
    
	@Transactional
	public int addPlayerLogin(RegisterPlayer registerPlayer) {
		
		int playerLoginId;    	
		
		this.procCreatePlayer.setAccessCallParameterMetaData(false);
		this.procCreatePlayer.declareParameters(new SqlOutParameter("RETURN", Types.INTEGER),
                								new SqlParameter("p_player_id", Types.INTEGER),
												new SqlParameter("p_username", Types.VARCHAR),
												new SqlParameter("p_password", Types.VARCHAR)												
												);
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		SqlParameterSource in; 
		in = params.addValue("p_player_id", registerPlayer.getPlayer_id(),Types.INTEGER);
		in = params.addValue("p_username", registerPlayer.getEmailaddress(),Types.VARCHAR);
		in = params.addValue("p_password", registerPlayer.getPasswd(),Types.VARCHAR);
			
		playerLoginId = procCreatePlayer.executeFunction(int.class,in);		
		logger.info("player id created is : " + playerLoginId);
		
		return playerLoginId;
	}
	
	/*@Transactional(readOnly=true)
	public int getPlayerId(String username, String password){
		int playerId;
		
		this.procValidateLogin.setAccessCallParameterMetaData(false);
		this.procValidateLogin.declareParameters(new SqlOutParameter("RETURN", Types.INTEGER),				
				new SqlParameter("p_username", Types.VARCHAR),
				new SqlParameter("p_password", Types.VARCHAR)												
				);
        MapSqlParameterSource params = new MapSqlParameterSource();
        
        SqlParameterSource in; 
        logger.info("Player username is: " + username);
    	logger.info("Player password is: " + password);
		in = params.addValue("p_username", username,Types.VARCHAR);
		in = params.addValue("p_password", password,Types.VARCHAR);
		
		playerId = procValidateLogin.executeFunction(int.class, in);
		logger.info("player id is: " + playerId);
		
		return playerId;
		
	}*/

}
