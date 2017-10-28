package com.hoon.smart_home.command;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Generator {

	public static void dataGenerator() {
		String query = "insert into sensordata values(?,?,?)";

		String url = "jdbc:mysql://127.0.0.1:jdbc?user=root&password=1234";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/smartdb", "root", "root");
			conn.setAutoCommit( false );
			pstmt = conn.prepareStatement(query);
			int num = 0;
			double temperature = 0.0;
			int moisture = 0;
			double b = 0.0;
			double a = 0.0;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = null;
			int i = 0;
			int total = 0;
			while (true) {
				total++;
				i++;
				date = new Date();
				num = (int) (Math.random() * 5) + 1;
				a = Math.random();
				switch (num) {
				case 1:
					temperature = 28 + a;
					temperature = Math.round(temperature * 1000000d) / 1000000d;
					moisture = (int) (Math.random() * 100) + 1;
					break;
				case 2:
					temperature = 29 + a;
					temperature = Math.round(temperature * 1000000d) / 1000000d;
					moisture = (int) (Math.random() * 100) + 1;
					break;
				case 3:
					temperature = 30 + a;
					temperature = Math.round(temperature * 1000000d) / 1000000d;
					moisture = (int) (Math.random() * 100) + 1;
					break;
				case 4:
					temperature = 31 + a;
					temperature = Math.round(temperature * 1000000d) / 1000000d;
					moisture = (int) (Math.random() * 100) + 1;
					break;
				case 5:
					temperature = 32 + a;
					temperature = Math.round(temperature * 1000000d) / 1000000d;
					moisture = (int) (Math.random() * 100) + 1;
					break;
				}
				
				pstmt.setDouble(1, temperature);
                pstmt.setInt(2, moisture) ;
                pstmt.setString(3,sdf.format(date));

                // addBatch에 담기
                pstmt.addBatch();
                 
                // 파라미터 Clear
                pstmt.clearParameters();
                if( (i % 100000) == 0){
                    i=0;
                    // Batch 실행
                    pstmt.executeBatch() ;
                    // Batch 초기화
                    pstmt.clearBatch();
                    // 커밋
                    conn.commit() ;
                    System.out.println("Data added = " + total);
                }
                
                
                if(total == 10000000) {
                	pstmt.executeBatch() ;
                    conn.commit() ;
                	break;
                }
			}

			System.out.println("Insert 1000000 data");
		} catch (Exception e) {
			e.printStackTrace();
			try {
                conn.rollback() ;
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
		}finally{
            if (pstmt != null) try {pstmt.close();pstmt = null;} catch(SQLException ex){}
            if (conn != null) try {conn.close();conn = null;} catch(SQLException ex){}
        }
	}

	public static void main(String[] args) {
		dataGenerator();

	}
}
