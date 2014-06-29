package com.ramblelinks.golfprocompanion.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

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

import com.ramblelinks.golfprocompanion.domain.GolfCourseHolesMap;


@Repository
public class JdbcGolfCourseHolesDao implements GolfCourseHolesDao{
	
	/** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private SimpleJdbcCall procgetGolfCourseHoles;    
  
    
    @Autowired
    public void init(DataSource dataSource)
    {
    	this.procgetGolfCourseHoles = new SimpleJdbcCall(dataSource)
		.withProcedureName("gm_getcourseholedetails")
		.declareParameters(new SqlParameter("p_golfcourse_id", Types.INTEGER))
    	.declareParameters(new SqlParameter("p_golfcoursedetail_id", Types.INTEGER))
    	.declareParameters(new SqlParameter("p_gender", Types.VARCHAR))
    	.declareParameters(new SqlParameter("p_player_id", Types.INTEGER));
        this.procgetGolfCourseHoles.setAccessCallParameterMetaData(false);    	    	    	    	
    }
    
    @Transactional
    @Override
	public List<GolfCourseHolesMap> getcoursedetails(String p_golfCourse_id,
			String p_teetype, String p_gender, int p_player_id) {
    	try{
    	int golfcourse_id = Integer.parseInt(p_golfCourse_id);
    	int teetype = Integer.parseInt(p_teetype);    	
    	logger.info("Golf Course "+ golfcourse_id );
    	logger.info("Tee Type "+ teetype);
    	logger.info("Gender "+p_gender);     	
    	//String sql = "select MAX(CASE WHEN tee_number = 1 THEN gch.par ELSE NULL END) par1,MAX(CASE WHEN tee_number = 2 THEN gch.par ELSE NULL END) par2,MAX(CASE WHEN tee_number = 3 THEN gch.par ELSE NULL END) par3,MAX(CASE WHEN tee_number = 4 THEN gch.par ELSE NULL END) par4,MAX(CASE WHEN tee_number = 5 THEN gch.par ELSE NULL END) par5,MAX(CASE WHEN tee_number = 6 THEN gch.par ELSE NULL END) par6,MAX(CASE WHEN tee_number = 7 THEN gch.par ELSE NULL END) par7,MAX(CASE WHEN tee_number = 8 THEN gch.par ELSE NULL END) par8,MAX(CASE WHEN tee_number = 9 THEN gch.par ELSE NULL END) par9,MAX(CASE WHEN tee_number = 10 THEN gch.par ELSE NULL END) par10,MAX(CASE WHEN tee_number = 11 THEN gch.par ELSE NULL END) par11,MAX(CASE WHEN tee_number = 12 THEN gch.par ELSE NULL END) par12,MAX(CASE WHEN tee_number = 13 THEN gch.par ELSE NULL END) par13,MAX(CASE WHEN tee_number = 14 THEN gch.par ELSE NULL END) par14,MAX(CASE WHEN tee_number = 15 THEN gch.par ELSE NULL END) par15,MAX(CASE WHEN tee_number = 16 THEN gch.par ELSE NULL END) par16,MAX(CASE WHEN tee_number = 17 THEN gch.par ELSE NULL END) par17,MAX(CASE WHEN tee_number = 18 THEN gch.par ELSE NULL END) par18 from gm_golfcourses gcr,gm_golfcoursedetails gcd,gm_golfcourseholes gch where gcd.golfcourse_id = gcr.golfcourse_id and gcr.golfcourse_id = ? and gch.golfcoursedetail_id = ? and gender = ? and gch.golfcoursedetail_id = gcd.golfcoursedetail_id UNION select MAX(CASE WHEN tee_number = 1 THEN gch.total_yards ELSE NULL END) total_yard1,MAX(CASE WHEN tee_number = 2 THEN gch.total_yards ELSE NULL END) total_yard2,MAX(CASE WHEN tee_number = 3 THEN gch.total_yards ELSE NULL END) total_yard3,MAX(CASE WHEN tee_number = 4 THEN gch.total_yards ELSE NULL END) total_yard4,MAX(CASE WHEN tee_number = 5 THEN gch.total_yards ELSE NULL END) total_yard5,MAX(CASE WHEN tee_number = 6 THEN gch.total_yards ELSE NULL END) total_yard6,MAX(CASE WHEN tee_number = 7 THEN gch.total_yards ELSE NULL END) total_yard7,MAX(CASE WHEN tee_number = 8 THEN gch.total_yards ELSE NULL END) total_yard8,MAX(CASE WHEN tee_number = 9 THEN gch.total_yards ELSE NULL END) total_yard9,MAX(CASE WHEN tee_number = 10 THEN gch.total_yards ELSE NULL END) total_yard10,MAX(CASE WHEN tee_number = 11 THEN gch.total_yards ELSE NULL END) total_yard11,MAX(CASE WHEN tee_number = 12 THEN gch.total_yards ELSE NULL END) total_yard12,MAX(CASE WHEN tee_number = 13 THEN gch.total_yards ELSE NULL END) total_yard13,MAX(CASE WHEN tee_number = 14 THEN gch.total_yards ELSE NULL END) total_yard14,MAX(CASE WHEN tee_number = 15 THEN gch.total_yards ELSE NULL END) total_yard15,MAX(CASE WHEN tee_number = 16 THEN gch.total_yards ELSE NULL END) total_yard16,MAX(CASE WHEN tee_number = 17 THEN gch.total_yards ELSE NULL END) total_yard17,MAX(CASE WHEN tee_number = 18 THEN gch.total_yards ELSE NULL END) total_yard18 from gm_golfcourses gcr,gm_golfcoursedetails gcd,gm_golfcourseholes gch where gcd.golfcourse_id = gcr.golfcourse_id and gcr.golfcourse_id = ? and gch.golfcoursedetail_id = ? and gender = ? and gch.golfcoursedetail_id = gcd.golfcoursedetail_id UNION select MAX(CASE WHEN tee_number = 1 THEN gch.handicap ELSE NULL END) handicap_1,MAX(CASE WHEN tee_number = 2 THEN gch.handicap ELSE NULL END) handicap_2,MAX(CASE WHEN tee_number = 3 THEN gch.handicap ELSE NULL END) handicap_3,MAX(CASE WHEN tee_number = 4 THEN gch.handicap ELSE NULL END) handicap_4,MAX(CASE WHEN tee_number = 5 THEN gch.handicap ELSE NULL END) handicap_5,MAX(CASE WHEN tee_number = 6 THEN gch.handicap ELSE NULL END) handicap_6,MAX(CASE WHEN tee_number = 7 THEN gch.handicap ELSE NULL END) handicap_7,MAX(CASE WHEN tee_number = 8 THEN gch.handicap ELSE NULL END) handicap_8,MAX(CASE WHEN tee_number = 9 THEN gch.handicap ELSE NULL END) handicap_9,MAX(CASE WHEN tee_number = 10 THEN gch.handicap ELSE NULL END) handicap_10,MAX(CASE WHEN tee_number = 11 THEN gch.handicap ELSE NULL END) handicap_11,MAX(CASE WHEN tee_number = 12 THEN gch.handicap ELSE NULL END) handicap_12,MAX(CASE WHEN tee_number = 13 THEN gch.handicap ELSE NULL END) handicap_13,MAX(CASE WHEN tee_number = 14 THEN gch.handicap ELSE NULL END) handicap_14,MAX(CASE WHEN tee_number = 15 THEN gch.handicap ELSE NULL END) handicap_15,MAX(CASE WHEN tee_number = 16 THEN gch.handicap ELSE NULL END) handicap_16,MAX(CASE WHEN tee_number = 17 THEN gch.handicap ELSE NULL END) handicap_17,MAX(CASE WHEN tee_number = 18 THEN gch.handicap ELSE NULL END) handicap_18 from gm_golfcourses gcr,gm_golfcoursedetails gcd,gm_golfcourseholes gch where gcd.golfcourse_id = gcr.golfcourse_id and gcr.golfcourse_id = ? and gch.golfcoursedetail_id = ? and gender = ? and gch.golfcoursedetail_id = gcd.golfcoursedetail_id ";    	
    	//return this.jdbcTemplate.query(sql, new Object[] {golfcourse_id,teetype,p_gender}, new GolfCourseHolesMapMapper());
    	this.procgetGolfCourseHoles.returningResultSet("golfcourseholes", new GolfCourseHolesMapMapper());	
    	MapSqlParameterSource params = new MapSqlParameterSource();
		SqlParameterSource in;
        in = params.addValue("p_golfcourse_id", golfcourse_id, Types.INTEGER);
        in = params.addValue("p_golfcoursedetail_id", teetype, Types.INTEGER);
        in = params.addValue("p_gender", p_gender, Types.VARCHAR);
        in = params.addValue("p_player_id", p_player_id, Types.INTEGER);
		Map<?,?> out = this.procgetGolfCourseHoles.execute(in);  
		logger.info("After proc call"); 
    	return (List<GolfCourseHolesMap>) out.get("golfcourseholes");
    	}
    	catch(Exception e)
    	{
    		logger.info("Error in getting course details for scorecard " + e.getMessage());
    		return new ArrayList<GolfCourseHolesMap>();
    	}
	}
    
    
   
    
    private static class GolfCourseHolesMapMapper implements RowMapper<GolfCourseHolesMap>
    {

		@Override
		public GolfCourseHolesMap mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			GolfCourseHolesMap gch = new GolfCourseHolesMap();	
			gch.setCol_0(rs.getString("col_0"));
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
			gch.setPlayerResult_id(0);
			return gch;
		}
    	
    }


}
