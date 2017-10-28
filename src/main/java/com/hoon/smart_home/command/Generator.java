package com.hoon.smart_home.command;

import java.sql.DriverManager;

public class Connection {

	public static void main(String[] args) {

		String url = "jdbc:mysql://127.0.0.1:jdbc?user=root&password=1234";
		java.sql.Connection conn = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection( "jdbc:mysql://127.0.0.1:3306","root","1234");
			System.out.println("Connect success");
		}catch(Exception e){
			System.out.println("Connect fail");
			e.printStackTrace();
		}
	}
}
