package com.hoon.smart_home.repository;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hoon.smart_home.repository.AbstractRDBMSRepositoryManager;

@Repository
public class MySQLRepsotioryManager extends AbstractRDBMSRepositoryManager{

	@Autowired
	private JdbcTemplate jdbctemplate;
	
	public MySQLRepsotioryManager() {
	}
	
	private void insert() {

		/*String query = "";
		return jdbctemplate.query(query, );*/
	}
	@Override
	public Connection getConnection() {
		//String url = "jdbc:mysql://127.0.0.1:jdbc?user=root&password=1234";
		Connection conn = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection( "jdbc:mysql://127.0.0.1:3306","root","1234");
			System.out.println("Connect success");
		}catch(Exception e){
			System.out.println("Connect fail");
			e.printStackTrace();
		}
		return conn;
	}

	@Override
	public boolean isRepositoryExist(String p_dbName) {

		return false;
	}

	@Override
	public Connection getConnection(String id, String password) {
		Connection conn = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection( "jdbc:mysql://127.0.0.1:3306",id,password);
			System.out.println("Connect success");
		}catch(Exception e){
			System.out.println("Connect fail");
			e.printStackTrace();
		}
		return conn;
	}

}
