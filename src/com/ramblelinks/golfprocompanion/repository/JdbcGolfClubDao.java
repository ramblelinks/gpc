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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.ramblelinks.golfprocompanion.domain.GolfClub;


@Repository
public class JdbcGolfClubDao implements GolfClubDao{

	/** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    
    private SimpleJdbcCall procGetGolfClub;
    
    
    @Autowired
    public void init(DataSource dataSource)
    {    
    	this.procGetGolfClub = new SimpleJdbcCall(dataSource)
    									.withProcedureName("gm_getgolfclub")
    									.declareParameters(new SqlParameter("p_stateid", Types.INTEGER));
        this.procGetGolfClub.setAccessCallParameterMetaData(false);
    }
	
    @Override
	@Transactional(readOnly=true)
	public List<GolfClub> getGolfClubs(int p_stateId) {
		
		this.procGetGolfClub.returningResultSet("golfclubs", new GolfClubMapper());	
		SqlParameterSource in = new MapSqlParameterSource()
        							.addValue("p_stateid", p_stateId, Types.INTEGER);	
		Map<?,?> out = this.procGetGolfClub.execute(in);    	
    	return (List<GolfClub>) out.get("golfclubs");	
	}
	
	private static class GolfClubMapper implements RowMapper<GolfClub> {	
			
		@Override
		public GolfClub mapRow(ResultSet rs, int rowNum) throws SQLException {
			GolfClub gc = new GolfClub();
			
			gc.setGolfclub_id(rs.getInt("golfclub_id"));
			gc.setClub_name(rs.getString("club_name"));
			return gc;
		}
	}

}
