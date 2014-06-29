package com.ramblelinks.golfprocompanion.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ramblelinks.golfprocompanion.domain.Player;
import com.ramblelinks.golfprocompanion.domain.PlayerDetail;
import com.ramblelinks.golfprocompanion.view.RegisterPlayer;


@Repository
public class JdbcPlayerDao implements PlayerDao{

	/** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    
    private SimpleJdbcCall procPlayer;
    private SimpleJdbcCall procUpdatePlayer;
    private SimpleJdbcCall procUpdatePass;
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public void init (DataSource dataSource)
    {    	     
    	this.procPlayer = new SimpleJdbcCall(dataSource);
    	this.procPlayer.setAccessCallParameterMetaData(false);
    	
    	this.procUpdatePlayer = new SimpleJdbcCall(dataSource);
    	this.procUpdatePlayer.setAccessCallParameterMetaData(false);
    	
    	this.procUpdatePass = new SimpleJdbcCall(dataSource);
    	this.procUpdatePass.setAccessCallParameterMetaData(false);
    	
   	 	this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional
    @Override
	public String addPlayer(RegisterPlayer registerPlayer) {
    	logger.info("Inside add player JDBC impl: " + registerPlayer.getFname());
		int playerId;    	
		Date dob;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
		try {
			dob = sdf.parse(registerPlayer.getDob());		
		logger.info("Inside add player JDBC impl: " + dob);
		this.procPlayer.withFunctionName("gm_createPlayer");
		
		this.procPlayer.declareParameters(new SqlOutParameter("RETURN", Types.INTEGER),
                								new SqlParameter("p_firstname", Types.VARCHAR),
												new SqlParameter("p_middlename", Types.VARCHAR),
												new SqlParameter("p_lastname", Types.VARCHAR),
												new SqlParameter("p_dob", Types.DATE),
												new SqlParameter("p_address1", Types.VARCHAR),
												new SqlParameter("p_address2", Types.VARCHAR),
												new SqlParameter("p_city", Types.VARCHAR),
												new SqlParameter("p_stateid", Types.INTEGER),
												new SqlParameter("p_countryid", Types.INTEGER),
												new SqlParameter("p_zipcode", Types.VARCHAR),
												new SqlParameter("p_emailaddress", Types.VARCHAR),
												new SqlParameter("p_golfcourseid", Types.INTEGER),
												new SqlParameter("p_gender", Types.VARCHAR),
												new SqlParameter("p_emailnotification", Types.VARCHAR),
												new SqlParameter("p_latitude", Types.FLOAT),
												new SqlParameter("p_longitude", Types.FLOAT)												
												);
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		if (registerPlayer.getEmailnotification().isEmpty())
		{
			registerPlayer.setEmailnotification("N");
		}
		
		SqlParameterSource in; 
		in = params.addValue("p_firstname", registerPlayer.getFname(),Types.VARCHAR);
		in = params.addValue("p_middlename", registerPlayer.getMname(),Types.VARCHAR);
		in = params.addValue("p_lastname", registerPlayer.getLname(),Types.VARCHAR);
		in = params.addValue("p_dob", dob,Types.DATE);
		in = params.addValue("p_address1", registerPlayer.getAddressOne(),Types.VARCHAR);
		in = params.addValue("p_address2", registerPlayer.getAddressTwo(),Types.VARCHAR);
		in = params.addValue("p_city", registerPlayer.getCity(),Types.VARCHAR);
		in = params.addValue("p_stateid", registerPlayer.getStateId(),Types.INTEGER);
		in = params.addValue("p_countryid", 4,Types.INTEGER);
		in = params.addValue("p_zipcode", registerPlayer.getZipcode(),Types.VARCHAR);
		in = params.addValue("p_emailaddress", registerPlayer.getEmailaddress(),Types.VARCHAR);
		in = params.addValue("p_golfcourseid", null,Types.INTEGER);
		in = params.addValue("p_gender", registerPlayer.getGender(),Types.VARCHAR);
		in = params.addValue("p_emailnotification", registerPlayer.getEmailnotification(),Types.VARCHAR);		
		in = params.addValue("p_latitude", Float.parseFloat(registerPlayer.getLatitude()),Types.FLOAT);
		in = params.addValue("p_longitude", Float.parseFloat(registerPlayer.getLongitude()),Types.FLOAT);		
		playerId = procPlayer.executeFunction(int.class,in);		
		logger.info("player id created is : " + playerId);
								
		registerPlayer.setPlayer_id(playerId);
				
		return "Player registered successfully. Login to connect with golf companion's";
		} catch (ParseException e) {
			e.printStackTrace();
			registerPlayer.setPlayer_id(-9);
			return "Input Parsing error";
		}	
		catch (Exception e) {
			registerPlayer.setPlayer_id(-9);
			return e.getMessage();
		}
		
	}
    
    @Override
    @Transactional(readOnly = true)
	public Player getPlayer(String username, String password){
    	
		logger.info("Getting player!");
        // need to remove hard coding for country id
		try{
        String sql = "select p.player_id, first_name, middle_name,last_name," +
        		"date_of_birth,street_address1,street_address2,city,state_id,zip_code," +
        		"country_id,phone_id,email_address,default_golfcourse_id,gender," +
        		"review_rating,email_notifications,handicap_index,latitude,longitude," +
        		"fav_brand_id,fav_golf_player " +
        		"from gm_players p " +
        		"	JOIN gm_playerlogins l " +
        		"	ON p.player_id = l.player_id " +        		
        		"		and l.login_name = ? " +
        		"		and l.login_password = md5(?)";
        Player player = this.jdbcTemplate.queryForObject(sql, new Object[]{username,password}, new PlayerMapper());
        if (player.getEmail_notifications() != null)
        {
        	if (player.getEmail_notifications().equals("Y"))        
		    {
		    	player.setEmailnotifs(true);
		    }
        }
        player.setPd(new PlayerDetail());
        return player;
		}catch (final EmptyResultDataAccessException e) {
			return null;
		}
		
	}
    
    private static class PlayerMapper implements RowMapper<Player> {

        public Player mapRow(ResultSet rs, int rowNum) throws SQLException {
            Player pl = new Player();
            pl.setPlayer_id(rs.getInt("player_id"));
            pl.setFirst_name(rs.getString("first_name"));
            pl.setMiddle_name(rs.getString("middle_name"));
            pl.setLast_name(rs.getString("last_name"));
            pl.setDate_of_birth(rs.getDate("date_of_birth"));
            pl.setStreet_address1(rs.getString("street_address1"));
            pl.setStreet_address2(rs.getString("street_address2"));
            pl.setCity(rs.getString("city"));
            pl.setState_id(rs.getInt("state_id"));
            pl.setZip_code(rs.getString("zip_code"));
            pl.setCountry_id(rs.getInt("country_id"));
            pl.setPhone_id(rs.getInt("phone_id"));
            pl.setEmail_address(rs.getString("email_address"));
            pl.setDefault_golfcourse_id(rs.getInt("default_golfcourse_id"));
            pl.setGender(rs.getString("gender"));
            pl.setReview_rating(rs.getString("review_rating"));
            pl.setEmail_notifications(rs.getString("email_notifications"));
            pl.setHandicap_index(rs.getString("handicap_index"));
            pl.setLatitude(rs.getFloat("latitude"));
            pl.setLongitude(rs.getFloat("longitude"));
            pl.setFav_brand_id(rs.getInt("fav_brand_id"));
            pl.setFav_golf_player(rs.getString("fav_golf_player"));
            		                
            return pl;              
        } 
 }

	@Override
	@Transactional
	public int setPlayerRating(int reviewer_id, int reviwee_id, int rating, String comments) {
		
		int playerReview_id;
		try
		{
		this.procPlayer.withProcedureName("gm_updateplayerrating");
		logger.info("input given to proc " + reviewer_id + " " + reviwee_id+ " " + comments);
		this.procPlayer.declareParameters(		new SqlParameter("p_reviewer_id", Types.INTEGER),
												new SqlParameter("p_reviewee_id", Types.INTEGER),												
												new SqlParameter("p_comments", Types.VARCHAR),
												new SqlParameter("p_rating", Types.INTEGER),												
												new SqlOutParameter("p_playerreview_id", Types.INTEGER)
												);
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		SqlParameterSource in; 
		in = params.addValue("p_reviewer_id", reviewer_id,Types.INTEGER);
		in = params.addValue("p_reviewee_id", reviwee_id,Types.INTEGER);		
		in = params.addValue("p_comments", comments,Types.VARCHAR);
		in = params.addValue("p_rating", rating,Types.INTEGER);
		
				
		Map<?,?> out = procPlayer.execute(in);
		
		playerReview_id = (Integer) out.get("p_playerreview_id");
		return playerReview_id;
		}
		catch
		(Exception e)
		{
			logger.info(e.getStackTrace());
			return -9;
		}
	}

	@Transactional(readOnly=true)
	@Override
	public int validateEmail(String p_email) {

	        String sql = "select count(*) from gm_players where upper(email_address) = ?";
	        
	        int emailCount = this.jdbcTemplate.queryForInt(sql,new Object[]{p_email.toUpperCase()});
	           
	        return emailCount;
	        				
	}

	@Transactional(readOnly=true)
	@Override
	public int validateUserName(String p_user) {
        String sql = "select count(*) from gm_playerlogins where upper(login_name) = ?";
        
        int loginCount = this.jdbcTemplate.queryForInt(sql,new Object[]{p_user.toUpperCase()});
           
        return loginCount;
	}

	@Transactional
	@Override
	public int resetPassword(String username, String password) {
		this.procUpdatePass.withProcedureName("gm_fnUpdatePassword")
		.declareParameters(new SqlParameter("p_loginName", Types.VARCHAR))
		.declareParameters(new SqlParameter("p_password", Types.VARCHAR))
		.declareParameters(new SqlOutParameter("p_rowcount", Types.INTEGER));

	MapSqlParameterSource in = new MapSqlParameterSource()
					.addValue("p_loginName", username, Types.VARCHAR)
					.addValue("p_password", password, Types.VARCHAR);
						

	Map<?,?> out = this.procUpdatePass.execute(in);
	int ret = (Integer) out.get("p_rowcount");
	return ret;		
	}

	@Override
	@Transactional(readOnly=true)
	public Player getPlayerInfo(int p_player_id) {
		try{
	        String sql = "select p.player_id, first_name, middle_name,last_name,date_of_birth,street_address1,street_address2,city,state_id,zip_code,country_id,phone_id,email_address,default_golfcourse_id,gender,review_rating,email_notifications,handicap_index,latitude,longitude,fav_brand_id,fav_golf_player from gm_players p where p.player_id = ?";
	        Player player = this.jdbcTemplate.queryForObject(sql, new Object[]{p_player_id}, new PlayerMapper());
	        if (player.getEmail_notifications() != null)
	        {
		        if (player.getEmail_notifications().equals("Y"))
		        {
		        	player.setEmailnotifs(true);
		        }
	        }
	        player.setPd(new PlayerDetail());
	        return player;
			}catch (final EmptyResultDataAccessException e) {
				return null;
			}	}

	@Override
	@Transactional
	public int updatePlayerInfo(Player updatePlayer) {
		int retVal;
		Date dob;		
		//SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
		try {
			dob = updatePlayer.getDate_of_birth();		
		logger.info("Inside update player JDBC impl: " + dob);
		this.procUpdatePlayer.withFunctionName("gm_updatePlayer");
		
		this.procUpdatePlayer.declareParameters(
												new SqlParameter("p_player_id", Types.INTEGER),
          //      								new SqlParameter("p_firstname", Types.VARCHAR),
			//									new SqlParameter("p_middlename", Types.VARCHAR),
				//								new SqlParameter("p_lastname", Types.VARCHAR),
												new SqlParameter("p_dob", Types.DATE),
												new SqlParameter("p_address1", Types.VARCHAR),
												new SqlParameter("p_address2", Types.VARCHAR),
												new SqlParameter("p_city", Types.VARCHAR),
												new SqlParameter("p_stateid", Types.INTEGER),												
												new SqlParameter("p_zipcode", Types.VARCHAR),
		//										new SqlParameter("p_emailaddress", Types.VARCHAR),
			//									new SqlParameter("p_golfcourseid", Types.INTEGER),
			//									new SqlParameter("p_gender", Types.VARCHAR),
												new SqlParameter("p_emailnotification", Types.VARCHAR),
												new SqlParameter("p_latitude", Types.FLOAT),
												new SqlParameter("p_longitude", Types.FLOAT),
												new SqlOutParameter("p_retVal", Types.INTEGER)
												);
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		if (!updatePlayer.isEmailnotifs())
		{
			updatePlayer.setEmail_notifications("N");
		}
		else
		{
			updatePlayer.setEmail_notifications("Y");
		}
		
		logger.info("after setting the notifs" + updatePlayer.getEmail_notifications());
		SqlParameterSource in; 
		in = params.addValue("p_player_id", updatePlayer.getPlayer_id(),Types.INTEGER);
		//in = params.addValue("p_firstname", updatePlayer.getFirst_name(),Types.VARCHAR);
		//in = params.addValue("p_middlename", updatePlayer.getMiddle_name(),Types.VARCHAR);
		//in = params.addValue("p_lastname", updatePlayer.getLast_name(),Types.VARCHAR);
		in = params.addValue("p_dob", dob,Types.DATE);
		in = params.addValue("p_address1", updatePlayer.getStreet_address1(),Types.VARCHAR);
		in = params.addValue("p_address2", updatePlayer.getStreet_address2(),Types.VARCHAR);
		in = params.addValue("p_city", updatePlayer.getCity(),Types.VARCHAR);
		in = params.addValue("p_stateid", updatePlayer.getState_id(),Types.INTEGER);
		//in = params.addValue("p_countryid", 4,Types.INTEGER);
		in = params.addValue("p_zipcode", updatePlayer.getZip_code(),Types.VARCHAR);
		//in = params.addValue("p_emailaddress", updatePlayer.getEmail_address(),Types.VARCHAR);
		//in = params.addValue("p_golfcourseid", null,Types.INTEGER);
		//in = params.addValue("p_gender", updatePlayer.getGender(),Types.VARCHAR);
		in = params.addValue("p_emailnotification", updatePlayer.getEmail_notifications(),Types.VARCHAR);		
		in = params.addValue("p_latitude", updatePlayer.getLatitude(),Types.FLOAT);
		in = params.addValue("p_longitude", updatePlayer.getLongitude(),Types.FLOAT);		
		retVal = procUpdatePlayer.executeFunction(int.class,in);		
		logger.info("player id created is : " + retVal);					
				
		return retVal;
		} catch (Exception e) {
			e.printStackTrace();		
			return -9;
		}
	
	}



}
