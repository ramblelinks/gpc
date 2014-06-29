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
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ramblelinks.golfprocompanion.domain.GolfCourse;
import com.ramblelinks.golfprocompanion.domain.GolfCourseHolesMap;
import com.ramblelinks.golfprocompanion.domain.PlayerResult;

@Repository
public class JdbcPlayerResultsDao implements PlayerResultsDao{

	
	/** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());    
    private SimpleJdbcCall procGetPlayerResults;
    private SimpleJdbcCall procAddPlayerScorecard;
    private SimpleJdbcCall procUpdateScorecard;
    private SimpleJdbcCall procGetScorecard;
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public void init (DataSource dataSource)
    {    
    	this.procGetPlayerResults = new SimpleJdbcCall(dataSource)
    									.withProcedureName("gm_getPlayerLastRounds")
    									.declareParameters(new SqlParameter("p_playerid", Types.INTEGER))
    									.declareParameters(new SqlParameter("p_golfCourse_id", Types.INTEGER))
    									.declareParameters(new SqlParameter("p_lastSeq", Types.INTEGER));
        this.procGetPlayerResults.setAccessCallParameterMetaData(false);
        
        this.procAddPlayerScorecard = new SimpleJdbcCall(dataSource);		  
        this.procAddPlayerScorecard.setAccessCallParameterMetaData(false);
        
        this.procGetScorecard = new SimpleJdbcCall(dataSource);
        this.procGetScorecard.setAccessCallParameterMetaData(false);        
        
        this.procUpdateScorecard = new SimpleJdbcCall(dataSource);
        this.procUpdateScorecard.setAccessCallParameterMetaData(false);
        
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
     
    @Override
	@Transactional(readOnly=true)
    public List<PlayerResult> getPlayerLastFiveResults(int playerId) {
    	logger.info("Player id passed is: " + playerId); 
    	SqlParameterSource in = new MapSqlParameterSource()
        							.addValue("p_playerid", playerId, Types.INTEGER)
    								.addValue("p_golfCourse_id", null, Types.INTEGER)
    								.addValue("p_lastSeq", 5, Types.INTEGER);
		this.procGetPlayerResults.returningResultSet("playerresult", new PlayerResultMapper());
    	Map<?,?> out = this.procGetPlayerResults.execute(in);    	
    	return (List<PlayerResult>) out.get("playerresult");		
    }
    
    private static class PlayerResultMapper implements RowMapper<PlayerResult> {

		 //protected final Log logger = LogFactory.getLog(getClass());
	        public PlayerResult mapRow(ResultSet rs, int rowNum) throws SQLException {
	            	        
	        	// set golf course information
	        	GolfCourse gc = new GolfCourse();
	        	gc.setCourse_name(rs.getString("course_name"));
	        	
	        	PlayerResult pr = new PlayerResult();
	            //logger.info("Player result id: " + rs.getInt("playerResult_id"));
	            pr.setPlayerResult_id(rs.getInt("playerResult_id"));
	            pr.setPlayer_id(rs.getInt("player_id"));
	            pr.setGolfCourse_id(rs.getInt("golfCourse_id"));
	            pr.setDate_played(rs.getDate("date_played"));
	            pr.setTotal_score(rs.getInt("total_score"));
	            pr.setEagles(rs.getInt("eagles"));
	            pr.setBirdies(rs.getInt("birdies"));
	            pr.setPars(rs.getInt("pars"));
	            pr.setBogeys(rs.getInt("bogeys"));
	            pr.setDoublebogeys(rs.getInt("doubleBogeys"));
	            pr.setDoublebogeyplus(rs.getInt("doubleBogeyPlus"));
	            pr.setNet_score(rs.getInt("net_score"));
	            pr.setTee_type(rs.getString("tee_type"));
	            pr.setGolfCourse(gc);
	            return pr;
	        }
    }

    @Transactional
	@Override
	public String addPlayerScorecard(int p_playerId, int p_golfCourseId,
			int p_golfcoursedetailId, String p_DatePlayed, String p_playerScores, String p_TimePlayed) {
    	
    		
		logger.info("Inputs: " + p_playerId + " gc: " + p_golfCourseId + " gcd: " + p_golfcoursedetailId + " date: " + p_DatePlayed + " Scores: " + p_playerScores);
    	this.procAddPlayerScorecard.withProcedureName("gm_fnPopulatePlayerResults")
									.declareParameters(new SqlParameter("p_playerId", Types.INTEGER))
									.declareParameters(new SqlParameter("p_golfCourseId", Types.INTEGER))
									.declareParameters(new SqlParameter("p_golfcoursedetailId", Types.INTEGER))
									.declareParameters(new SqlParameter("p_datePlayed", Types.VARCHAR))
									.declareParameters(new SqlParameter("p_playerScores", Types.VARCHAR))
									.declareParameters(new SqlParameter("p_timePlayed", Types.VARCHAR))
									.declareParameters(new SqlOutParameter("p_retMessage", Types.VARCHAR));
									;
		
    	MapSqlParameterSource in = new MapSqlParameterSource()
									.addValue("p_playerId", p_playerId, Types.INTEGER)
									.addValue("p_golfCourseId", p_golfCourseId, Types.INTEGER)															
									.addValue("p_golfcoursedetailId", p_golfcoursedetailId, Types.INTEGER)
									.addValue("p_datePlayed", p_DatePlayed, Types.VARCHAR)
									.addValue("p_playerScores", p_playerScores, Types.VARCHAR)
									.addValue("p_timePlayed", p_TimePlayed, Types.VARCHAR);
    	
    	Map<?,?> out = this.procAddPlayerScorecard.execute(in);
		String ret =(String) out.get("p_retMessage");		
    	logger.info("Return value for score card add: " + ret);
		return ret;
    		
	}

    @Transactional
	@Override
	public List<GolfCourseHolesMap> getScoreCard(int p_playerResult_id) {
		// TODO Auto-generated method stub
		this.procGetScorecard.withProcedureName("gm_getPlayerRoundDetails")
							.declareParameters(new SqlParameter("p_playerResultId", Types.INTEGER));
		
		MapSqlParameterSource in = new MapSqlParameterSource()
									.addValue("p_playerResultId", p_playerResult_id, Types.INTEGER);
		
		this.procGetScorecard.returningResultSet("player_scorecard", new GolfCourseHolesMapMapper());
		
		Map<?,?> out = this.procGetScorecard.execute(in);  		
    	return (List<GolfCourseHolesMap>) out.get("player_scorecard");			
	}
	
	private static class GolfCourseHolesMapMapper implements RowMapper<GolfCourseHolesMap>
    {

		@Override
		public GolfCourseHolesMap mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			GolfCourseHolesMap gch = new GolfCourseHolesMap();	
			gch.setCol_0(rs.getString("row_name"));
			gch.setCol_1(rs.getInt("col_1"));
			gch.setCol_2(rs.getInt("col_2"));
			gch.setCol_3(rs.getInt("col_3"));
			gch.setCol_4(rs.getInt("col_4"));
			gch.setCol_5(rs.getInt("col_5"));
			gch.setCol_6(rs.getInt("col_6"));
			gch.setCol_7(rs.getInt("col_7"));
			gch.setCol_8(rs.getInt("col_8"));
			gch.setCol_9(rs.getInt("col_9"));
			gch.setout1(rs.getInt("out1"));
			gch.setCol_10(rs.getInt("col_10"));
			gch.setCol_11(rs.getInt("col_11"));
			gch.setCol_12(rs.getInt("col_12"));
			gch.setCol_13(rs.getInt("col_13"));
			gch.setCol_14(rs.getInt("col_14"));
			gch.setCol_15(rs.getInt("col_15"));
			gch.setCol_16(rs.getInt("col_16"));
			gch.setCol_17(rs.getInt("col_17"));
			gch.setCol_18(rs.getInt("col_18"));	
			gch.setout2(rs.getInt("out2"));
			gch.setPlayerResult_id(rs.getInt("p_playerResultId"));
			return gch;
		}
    	
    }

	@Transactional
	@Override
	public String updateScoreCard(int playerResult_id, String playerScore) {
		
		this.procUpdateScorecard.withProcedureName("gm_fnUpdatePlayerResults")
									.declareParameters(new SqlParameter("p_playerResult_Id", Types.INTEGER))
									.declareParameters(new SqlParameter("p_playerScores", Types.VARCHAR))
									.declareParameters(new SqlOutParameter("p_retMessage", Types.VARCHAR));
		
		MapSqlParameterSource in = new MapSqlParameterSource()
								.addValue("p_playerResult_Id", playerResult_id, Types.INTEGER)
								.addValue("p_playerScores", playerScore, Types.VARCHAR);
		
		
		Map<?,?> out = this.procUpdateScorecard.execute(in);
		String ret =(String) out.get("p_retMessage");
		
    	
		return ret;
	}

	@Transactional(readOnly=true)
	@Override
	public List<PlayerResult> getPlayerResultsByCourse(int playerId, int golfCourseId) {
		logger.info("Inside get player results" + playerId);
		SqlParameterSource in = new MapSqlParameterSource()
								.addValue("p_playerid", playerId, Types.INTEGER)
								.addValue("p_golfCourse_id", golfCourseId, Types.INTEGER)
								.addValue("p_lastSeq", null, Types.INTEGER);
		this.procGetPlayerResults.returningResultSet("playerresult", new PlayerResultMapper());
		Map<?,?> out = this.procGetPlayerResults.execute(in);    	
		return (List<PlayerResult>) out.get("playerresult");
	}


	
	@Override
	@Transactional(readOnly=true)
	public List<PlayerResult> getTotalStats(int playerId) {
		String sql = "select player_id, player_id, null course_name, null playerResult_id,null golfCourse_id, null date_played, null total_score,null net_score, null tee_type, " +
					" sum(pars) pars, sum(birdies) birdies, sum(eagles) eagles, sum(bogeys) bogeys, sum(bogeyplus) doubleBogeyPlus, sum(doublebogeys) doubleBogeys " +
					 "from gm_playerResults where player_id = ? group by player_id";
		List<PlayerResult> playerResults = this.jdbcTemplate.query(sql, new PlayerResultMapper(), new Object[]{playerId});
		return playerResults;		
	}


	@Override
	@Transactional(readOnly=true)
	public List<PlayerResult> getAllScores(int player_id) {
		String sql = "select pr.playerResult_id, pr.player_id, gc.golfcourse_id, CAST(pr.date_played as date) date_played,  gc.course_name, pr.tee_type, " +
					"AVG(pr.total_score) total_score, avg(pr.eagles) eagles, avg(pr.birdies) birdies, avg(pr.pars) pars, avg(pr.bogeys) bogeys, avg(pr.doubleBogeys) doublebogeys, "+ 
					"avg(pr.bogeyPlus) doubleBogeyPLus, avg(pr.net_score) net_score  "+
					"from gm_playerResults pr "+
					"	Join gm_golfCourses gc "+
					"	ON pr.golfCourse_id = gc.golfCourse_id " +
					"where pr.player_id = ? " +
					"GROUp BY pr.playerResult_id, pr.player_id, gc.golfcourse_id, CAST(pr.date_played as date),  gc.course_name, pr.tee_type " +
					"ORDER BY CAST(pr.date_played as date)";
List<PlayerResult> playerResults = this.jdbcTemplate.query(sql, new PlayerResultMapper(), new Object[]{player_id});
return playerResults;
	}
    
    /*
	@Transactional(readOnly = true)
	public List<PlayerResults> getPlayerLastFiveResults(int playerId) {
		logger.info("Getting states!");
        // need to remove hard coding for country id
        String sql = "WITH playerLastFiveRounds AS ( " +
					"select *, row_number() OVER (partition by player_id order by date_played desc) seq " +
					"from gm_playerResults "+
					"where player_id = ? " +
					") " +
					"SELECT  playerResult_id, player_id, golfCourse_id, date_played, total_Score, eagles, birdies, " +
					"pars, bogeys, bogeyplus doubleBogeyPlus, handicap_differential, "+
					"	net_score, tee_type, doubleBogeys "+
					"FROM playerLastFiveRounds "+
					"WHERE seq <=?";
        List<PlayerResults> playerResults = this.jdbcTemplate.query(sql, new PlayerResultMapper(), new Object[]{20,5});
        return playerResults;
	}
	
	 private static class PlayerResultMapper implements RowMapper<PlayerResults> {

		 //protected final Log logger = LogFactory.getLog(getClass());
	        public PlayerResults mapRow(ResultSet rs, int rowNum) throws SQLException {
	            
	        	PlayerResults pr = new PlayerResults();
	            //logger.info("Player result id: " + rs.getInt("playerResult_id"));
	            pr.setPlayerResult_id(rs.getInt("playerResult_id"));
	            pr.setPlayer_id(rs.getInt("player_id"));
	            pr.setGolfCourse_id(rs.getInt("golfCourse_id"));
	            pr.setDate_played(rs.getDate("date_played"));
	            pr.setTotal_score(rs.getInt("total_score"));
	            pr.setEagles(rs.getInt("eagles"));
	            pr.setBirdies(rs.getInt("birdies"));
	            pr.setPars(rs.getInt("pars"));
	            pr.setBogeys(rs.getInt("bogeys"));
	            pr.setDoublebogeys(rs.getInt("doubleBogeys"));
	            pr.setDoublebogeyplus(rs.getInt("doubleBogeyPlus"));
	            pr.setNet_score(rs.getInt("net_score"));
	            pr.setTee_type(rs.getString("tee_type"));	           
	            return pr;
	        }
	 }
	*/
	

}
