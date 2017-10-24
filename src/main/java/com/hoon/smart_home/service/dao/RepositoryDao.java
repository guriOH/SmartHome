package com.hoon.smart_home.service.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hoon.smart_home.app.Application;

@Repository
public class RepositoryDao {
	private final static Logger logger = LogManager.getLogger(RepositoryDao.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void createtables() {
		String query = "CREATE TABLE sensor_data_table("
				+" temperature VARCHAR(32) NOT NULL, "
				+" moisture VARCHAR(32) NOT NULL)";
		logger.info("Query = "+query);
		jdbcTemplate.execute(query);
	}
}
