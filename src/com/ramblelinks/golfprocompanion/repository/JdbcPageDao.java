package com.ramblelinks.golfprocompanion.repository;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class JdbcPageDao implements PageDao {
	
	/** Logger for this class and subclasses */
    protected final Log logger = LogFactory.getLog(getClass());
       
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public void init(DataSource dataSource)
    {
    	this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

	@Override
	@Transactional(readOnly=true)
	public int getPageHitCount(String pageName) {
	        String sql = "Select hitcount from gm_pagehits where pageName = ?";
	        int pageCount = this.jdbcTemplate.queryForInt(sql, new Object[]{pageName});	        	        
		return pageCount;
	}

	@Override	
	public int updatePageHitCount(String pageName, int pageCount) {
		String sql = "UPDATE gm_pageHits " +
				"SET hitCount = ? " +
				"WHERE pageName = ?";
		this.jdbcTemplate.update(sql,new Object[]{pageCount,pageName});
		return 0;
	}

	
}
