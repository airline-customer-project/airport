package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlConnection {
	public Statement mysql = null;
	
	private static MysqlConnection instance = new MysqlConnection();
	
	public static MysqlConnection getInstance() {
		return instance;
	}
	
	public MysqlConnection() {
		String driver = "com.mysql.cj.jdbc.Driver";
		String dburl = "jdbc:mysql://database-1.cj8n7ylferyf.ap-northeast-2.rds.amazonaws.com:3306/airport";
        String dbUser = "admin";
        String dbpasswd = "rhror123";
        
     
        try {
        	Class.forName(driver);
        	
			Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
			
			mysql = conn.createStatement();
			
		} catch (SQLException e) {

            System.out.println("SQL Error : " + e.getMessage());

        } catch (ClassNotFoundException e1) {

            System.out.println("[JDBC Connector Driver 오류 : " + e1.getMessage() + "]");

        } finally {
        	
        }
	}
}
