package com.mastercom.report;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class fetchDB2Connection {

	/**
	 * @param args
	 * @throws SQLException
	 */
	public Connection getDB2Connection() throws SQLException{
		String url = "";
		Connection conn = null;
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			url = "jdbc:db2://10.209.19.193:60025/WQMSDB";
			conn = DriverManager.getConnection(url, "bpminst", "D@t@p0wer");
		
		} catch (ClassNotFoundException e) {
			System.err.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
		}
		
		return conn;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
