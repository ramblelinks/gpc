package com.ramblelinks.golfprocompanion.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ramblelinks.golfprocompanion.domain.City;
import com.ramblelinks.golfprocompanion.domain.State;



@Repository
public class JdbcCityDao implements CityDao{

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void init(DataSource dataSource)
	{
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	/*@Override
	@Transactional(readOnly=true)
	public List<String> getCities() {
		
		String sql = "select c.city_name || ', ' || s.state_name cityState " + 
		"from gm_cities c " + 
		"JOIN gm_states s " +
		"ON c.state_id = s.state_id " +
	    "order by s.state_name, c.city_name ";
		
		return this.jdbcTemplate.query(sql, new StringMapper());		
	}

	private static class StringMapper implements RowMapper<String>
	{

		@Override
		public String mapRow(ResultSet rs, int rowNum) throws SQLException {
			String s;			
			s= rs.getString("cityState");
			return s;
		}
		
	}
*/
	
	@Override
	@Transactional(readOnly=true)
	public List<City> getCities(String query) {
		
		String sql = "select c.city_id, c.city_name , s.state_name, s.state_id " + 
		"from gm_cities c " + 
		"JOIN gm_states s " +
		"ON c.state_id = s.state_id " +
		"WHERE lower(c.city_name) like '" + query.toLowerCase() + "%'" +
	    "order by s.state_name, c.city_name ";
		
		return this.jdbcTemplate.query(sql, new StringMapper());		
	}

	private static class StringMapper implements RowMapper<City>
	{

		@Override
		public City mapRow(ResultSet rs, int rowNum) throws SQLException {
			City c= new City();	
			
			State s = new State();
			
			s.setStateId(rs.getInt("state_id"));
			s.setStateName(rs.getString("state_name"));
			
			c.setCity_id(rs.getInt("city_id"));
			c.setCity_name(rs.getString("city_name"));
			c.setState(s);
			return c;
		}
		
	}
}
