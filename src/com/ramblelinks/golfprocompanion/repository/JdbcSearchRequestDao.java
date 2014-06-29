package com.ramblelinks.golfprocompanion.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ramblelinks.golfprocompanion.domain.GolfCourse;
import com.ramblelinks.golfprocompanion.domain.Player;
import com.ramblelinks.golfprocompanion.domain.SearchResult;
import com.ramblelinks.golfprocompanion.utilites.StringManupilations;

@Repository
public class JdbcSearchRequestDao implements SearchRequestDao{

	/** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
	private SimpleJdbcCall procSearch;
	private StringManupilations sm;
	
	@Autowired
	 public void init (DataSource dataSource, StringManupilations sm){
		this.procSearch = new SimpleJdbcCall(dataSource).withProcedureName("gm_createsearchrequest");
		this.sm = sm;
	}
		
	@Override
	@Transactional
	public List<SearchResult> createSearchRequest(int player_id,String handicap,String gender, String age, String city, String zipcode, 
			//String playDateFrom,String playDateTo,String timeMeasure, 
			String radius, 
			//String golfcourse_id, 
			String p_latitiude, String p_longitude) {	
				
		//Date searchDateFrom, searchDateTo;
		//SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		try {
			//searchDateFrom = sdf.parse(playDateFrom);
			//searchDateTo = sdf.parse(playDateTo);
		
		String delimiter = "-";
		String minAge, maxAge, minRadius, maxRadius,minHandicap, maxHandicap;		
		Map<String,String> splitMap;
		
		splitMap = sm.split(age, delimiter);		
		minAge = splitMap.get("minValue");
		maxAge = splitMap.get("maxValue");
		
		splitMap = sm.split(radius, delimiter);
		minRadius = splitMap.get("minValue");
		maxRadius = splitMap.get("maxValue");
		
		splitMap = sm.split(handicap, delimiter);
		minHandicap = splitMap.get("minValue");
		maxHandicap = splitMap.get("maxValue");
		
		//logger.info("a: " + minAge + " b: " + maxAge + " c: " + minRadius + " d: " + maxRadius +  " f: " + minHandicap + " g: " + maxHandicap + " h: " + golfcourse_id );
		
		this.procSearch.setAccessCallParameterMetaData(false);
		this.procSearch.declareParameters(new SqlParameter("p_player_id", Types.INTEGER),
												//new SqlParameter("p_golfcourse_id", Types.VARCHAR),
												//new SqlParameter("p_from_date", Types.DATE),
												//new SqlParameter("p_to_date", Types.DATE),
												//new SqlParameter("p_time_measure", Types.VARCHAR),
												//new SqlParameter("p_search_time", Types.TIME),
												new SqlParameter("p_min_handicap", Types.INTEGER),
												new SqlParameter("p_max_handicap", Types.INTEGER),
												new SqlParameter("p_min_radius", Types.INTEGER),
												new SqlParameter("p_max_radius", Types.INTEGER),
												new SqlParameter("p_city", Types.VARCHAR),
												new SqlParameter("p_zipcode", Types.VARCHAR),												
												new SqlParameter("p_min_age", Types.INTEGER),
												new SqlParameter("p_max_age", Types.INTEGER),
												new SqlParameter("p_gender", Types.VARCHAR),
												new SqlParameter("p_latitude", Types.FLOAT),
												new SqlParameter("p_longitude", Types.FLOAT)
												);
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		SqlParameterSource in; 
		in = params.addValue("p_player_id", player_id);
		//in = params.addValue("p_golfcourse_id", golfcourse_id);
		//in = params.addValue("p_from_date", searchDateFrom);
		//in = params.addValue("p_to_date", searchDateTo);
		//in = params.addValue("p_time_measure", timeMeasure);
		in = params.addValue("p_min_handicap", Integer.parseInt(minHandicap));
		in = params.addValue("p_max_handicap", Integer.parseInt(maxHandicap));
		in = params.addValue("p_min_radius", Integer.parseInt(minRadius));
		in = params.addValue("p_max_radius", Integer.parseInt(maxRadius));
		in = params.addValue("p_city", city);
		in = params.addValue("p_zipcode", zipcode);
		in = params.addValue("p_min_age", Integer.parseInt(minAge));
		in = params.addValue("p_max_age", Integer.parseInt(maxAge));
		in = params.addValue("p_gender", gender);
		in = params.addValue("p_latitude", Float.parseFloat(p_latitiude));
		in = params.addValue("p_longitude", Float.parseFloat(p_longitude));
		
		logger.info("After Mapping input paramters.");
		
		this.procSearch.returningResultSet("searchresults", new SearchMapper());	
		
		logger.info("After exeucting proc.");
		
		Map out = this.procSearch.execute(in);    			
		//searchRequestId = procCreatePlayer.executeFunction(int.class,in);		
		logger.info("Result returned : " + out.size());		
		return (List<SearchResult>) out.get("searchresults");		
		} 
		catch (Exception ex )
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	private static class SearchMapper implements RowMapper<SearchResult>{
		//protected final Log logger = LogFactory.getLog(getClass());
		@Override
		public SearchResult mapRow(ResultSet rs, int rowNum)
				throws SQLException {

			SearchResult sr = new SearchResult();
			GolfCourse gc = new GolfCourse();
			Player p = new Player();
			
			//gc.setGolfcourse_id(rs.getInt("golfcourse_id"));
			//gc.setCourse_name(rs.getString("course_name"));
			
			//Float rating = rs.getFloat("review_rating");
			//if (rs.wasNull())
		//	{
		//		rating = null;
		//	}
			
			p.setPlayer_id(rs.getInt("player_id"));
			p.setFirst_name(rs.getString("first_name"));
			p.setLast_name(rs.getString("last_name"));
			p.setGender(rs.getString("gender"));
			p.setReview_rating(rs.getString("review_rating"));
			p.setHandicap_index(rs.getString("handicap_index"));
			p.setCity(rs.getString("city"));
			
			
		//	sr.setSearchresult_id(rs.getInt("searchresult_id"));
			sr.setGolfcourses(gc);
			sr.setPlayers(p);
			sr.setPlayer_age(rs.getInt("playerAge"));
		//	sr.setFrom_date(rs.getDate("from_date"));
			//sr.setTo_date(rs.getDate("to_date"));
		//	sr.setPlaying_time_measure(rs.getString("playing_time_measure"));
			
			return sr;
		}
		
	}



}
