package com.hoon.smart_home.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hoon.smart_home.common.exeception.AnalysisException;
import com.hoon.smart_home.service.dao.RepositoryDao;
import com.hoon.smart_home.service.interfaces.AbstractService;

public class DefaultService extends AbstractService{
	private final static Logger logger = LogManager.getLogger(DefaultService.class);
	private RepositoryDao repositoryDao = null;
	
	public DefaultService() throws AnalysisException {
		super();
		repositoryDao = new RepositoryDao();
	}

	@Override
	protected void create() {
		repositoryDao.createtables();
		logger.info("Create table");
	}

	@Override
	protected String getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void insertData() {
		// TODO Auto-generated method stub
		
	}
}
