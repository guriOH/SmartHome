package com.hoon.smart_home.interfaces;

import java.sql.Connection;

public abstract class AbstractRepositoryManager {

	public abstract Connection getConnection();
	public abstract Connection getConnection(String id,String password);
	public abstract boolean isRepositoryExist(String p_dbName);	
	
}
