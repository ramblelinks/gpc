package com.ramblelinks.golfprocompanion.repository;

import java.util.List;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ramblelinks.golfprocompanion.domain.GolfCourseDetails;

@Repository
public class JdbcGolfCourseDetailsDao implements GolfCourseDetailsDao{
	
	/** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private JdbcTemplate jdbcTemplate;
    //private SimpleJdbcCall procGetGolfCourse;
    
    @Autowired
    public void init(DataSource dataSource)
    {
    	this.jdbcTemplate =  new JdbcTemplate(dataSource);
    	/*this.procGetGolfCourse = new SimpleJdbcCall(dataSource)
    									.withProcedureName("gm_getGolfCourse");
        this.procGetGolfCourse.setAccessCallParameterMetaData(false);*/
    }
    
   
    
     @Transactional(readOnly=true)
	public List<GolfCourseDetails> getTeeTypes(int p_golfcourseid, String p_gender) {
    	/*this.procGetGolfCourse.returningResultSet("golfcourse", new GolfCourseMapper());		
		Map out = this.procGetGolfCourse.execute(p_zipcode);    	
    	return (List<GolfCourse>) out.get("golfcourse");*/
    	logger.info("Getting TeeTypes for Zipcode : " + p_golfcourseid);
    	String sql = "select golfcoursedetail_id, tee_type from gm_golfcoursedetails gcd where golfcourse_id = ? and gender  = ?";    	
    	return this.jdbcTemplate.query(sql, new Object[] {p_golfcourseid, p_gender}, new GolfCourseDetailsMapper());    	
	}
    
    private static class GolfCourseDetailsMapper implements RowMapper<GolfCourseDetails>
    {

		@Override
		public GolfCourseDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			GolfCourseDetails gd = new GolfCourseDetails();	
			gd.setGolfcoursedetail_id(rs.getInt("golfcoursedetail_id"));
			gd.setTee_type(rs.getString("tee_type"));			
			return gd;
		}
    	
    }

}
