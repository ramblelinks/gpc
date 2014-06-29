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
public class JdbcPlayerDetailDao implements PlayerDetailDao{

	
	/** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    
    private SimpleJdbcCall procCreatePlayer;
    
    @Autowired
    public void init (DataSource dataSource)
    {    	     
    	this.procCreatePlayer = new SimpleJdbcCall(dataSource).withFunctionName("gm_createplayerdetail");
    }
	@Transactional
	public int addPlayerDetail(RegisterPlayer registerPlayer) {
		
		int playerDetailId;    	
		logger.info("Inside add player detail dao " + registerPlayer.getPlayer_id());		
		this.procCreatePlayer.setAccessCallParameterMetaData(false);
		this.procCreatePlayer.declareParameters(new SqlOutParameter("RETURN", Types.INTEGER),
                								new SqlParameter("p_player_id", Types.INTEGER),
												new SqlParameter("p_phonenumber", Types.BIGINT),
												new SqlParameter("p_phonetype", Types.VARCHAR)												
												);
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		SqlParameterSource in; 
		in = params.addValue("p_player_id", registerPlayer.getPlayer_id(),Types.INTEGER);
		in = params.addValue("p_phonenumber", registerPlayer.getPhoneId(),Types.BIGINT);
		in = params.addValue("p_phonetype", registerPlayer.getPhoneType(),Types.VARCHAR);
			
		playerDetailId = procCreatePlayer.executeFunction(int.class,in);		
		logger.info("player id created is : " + playerDetailId);
		
		return playerDetailId;
	}

}
