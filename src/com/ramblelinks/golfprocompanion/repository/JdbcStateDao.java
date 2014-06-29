package com.ramblelinks.golfprocompanion.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
//import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ramblelinks.golfprocompanion.domain.State;

@Repository
public class JdbcStateDao implements StateDao {

	/** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());

    private JdbcTemplate jdbcTemplate;
	
    @Autowired
    public void init (DataSource dataSource)
    {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    @Transactional(readOnly = true)
    @Cacheable("States")
	public List<State> getStateList() {
		logger.info("Getting states!");
        // need to remove hard coding for country id
        String sql = "select state_id, state_code, state_name from gm_states where country_id = 4";
        List<State> states = this.jdbcTemplate.query(sql, new StateMapper());
        return states;
		
	}
	
	 private static class StateMapper implements RowMapper<State> {

	        public State mapRow(ResultSet rs, int rowNum) throws SQLException {
	            State st = new State();
	            st.setStateId(rs.getInt("state_id"));
	            st.setStateCode(rs.getString("state_code"));
	            st.setStateName(rs.getString("state_name"));
	           
	            return st;
	        }
	 }
	
}
