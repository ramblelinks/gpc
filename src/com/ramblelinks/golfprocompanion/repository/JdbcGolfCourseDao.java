package com.ramblelinks.golfprocompanion.repository;

import java.util.List;
import java.util.Map;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ramblelinks.golfprocompanion.domain.GolfCourse;
import com.ramblelinks.golfprocompanion.utilites.StringManupilations;

@Repository
public class JdbcGolfCourseDao implements GolfCourseDao{
	
	/** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
    
    private JdbcTemplate jdbcTemplate;
    

    //private JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall procGetGolfCourse;
    private StringManupilations sm;
    
    @Autowired
    public void init(DataSource dataSource, StringManupilations sm)
    {
    	this.jdbcTemplate =  new JdbcTemplate(dataSource);
    	this.sm = new StringManupilations();
    	this.procGetGolfCourse = new SimpleJdbcCall(dataSource)
    									.withProcedureName("gm_getgolfcourses")
    									.declareParameters(new SqlParameter("p_cityid", Types.INTEGER))
    									.declareParameters(new SqlParameter("p_zipcode", Types.VARCHAR))
    									.declareParameters(new SqlParameter("p_lat", Types.DOUBLE))
    									.declareParameters(new SqlParameter("p_long", Types.DOUBLE))
    									.declareParameters(new SqlParameter("p_minradius", Types.INTEGER))
    									.declareParameters(new SqlParameter("p_maxradius", Types.INTEGER));
        this.procGetGolfCourse.setAccessCallParameterMetaData(false);
    }
    
    @Transactional(readOnly=true)
	public List<GolfCourse> getGolfCourses(int p_cityId, String p_zipcode, String p_radius, float p_lat, float p_long) {
    	
    	String delimiter = "-";
		String minRadius, maxRadius;		
		Map<String,String> splitMap;
				
		splitMap = sm.split(p_radius, delimiter);
		minRadius = splitMap.get("minValue");
		maxRadius = splitMap.get("maxValue");
		
		logger.info("input Values " + p_cityId + " Zip: " + p_zipcode + " minR: " + minRadius + " MaxR: " + maxRadius + " lat:" + p_lat + " long:" + p_long);
    	
    	this.procGetGolfCourse.returningResultSet("golfcourse", new GolfCourseMapper());
    	SqlParameterSource in = new MapSqlParameterSource()
								.addValue("p_cityid", p_cityId, Types.INTEGER)
								.addValue("p_zipcode", p_zipcode, Types.VARCHAR)
								.addValue("p_lat", p_lat, Types.DOUBLE)
								.addValue("p_long", p_long, Types.DOUBLE)
								.addValue("p_minradius", minRadius, Types.INTEGER)
								.addValue("p_maxradius", maxRadius, Types.INTEGER)
								;
    	
		Map out = this.procGetGolfCourse.execute(in);    	
		logger.info("After the proc execution");
    	return (List<GolfCourse>) out.get("golfcourse");
    	
    	
    	//String sql = "select * from gm_golfCourses where golfclub_id = ? or ? = -9";
    	/*String sql = "select gcr.* " + 
					" from gm_golfCourses gcr " +
					"      JOIN gm_golfClubs gcl " +
					"	ON gcr.golfClub_id = gcl.golfclub_id " +
					"      JOIN gm_cities c		" +
					"	ON c.city_name = gcl.city " +
					"	WHERE (c.city_id = ? AND ? <> -9) " +
					"   OR (gcl.postal_code =? AND ? IS NOT NULL)";
    	return this.jdbcTemplate.query(sql, new Object[] {p_cityId, p_cityId, p_zipcode, p_zipcode}, new GolfCourseMapper());
	*/}
    
    @Transactional(readOnly=true)
	public List<GolfCourse> getGolfCourses(String p_zipcode) {
    	/*this.procGetGolfCourse.returningResultSet("golfcourse", new GolfCourseMapper());		
		Map out = this.procGetGolfCourse.execute(p_zipcode);    	
    	return (List<GolfCourse>) out.get("golfcourse");*/
    	logger.info("Getting Golf Courses for Zipcode : " + p_zipcode);
    	String sql = "select * from gm_golfCourses gcr,gm_golfclubs gcb where gcr.golfclub_id = gcb.golfclub_id and gcb.postal_code = ?";    	
    	return this.jdbcTemplate.query(sql, new Object[] {p_zipcode}, new GolfCourseMapper());    	
	}
    
    private static class GolfCourseMapper implements RowMapper<GolfCourse>
    {

		@Override
		public GolfCourse mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			GolfCourse gc = new GolfCourse();
			gc.setGolfcourse_id(rs.getInt("golfcourse_id"));
			gc.setCourse_name(rs.getString("course_name"));
			return gc;
		}
    	
    }

}
