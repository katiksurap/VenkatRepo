package com.mastercom.sql.connection;

import java.sql.*;

public class SQLManager {
	public SQLManager() {
	}

	public static Connection getConnection() throws SQLException {
		Connection conn =null;
		try {
			Class.forName(HardcodedValues.DB2_DATABASE_DRIVER);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
		conn = DriverManager.getConnection(HardcodedValues.DB2_DATABASE_CONNECT_URL + "/"
				+ HardcodedValues.DATABASE_NAME,
				HardcodedValues.DATABASE_USERNAME,
				HardcodedValues.DATABASE_PASSWORD);
		System.out.println(HardcodedValues.DB2_DATABASE_CONNECT_URL + "/"
				+ HardcodedValues.DATABASE_NAME
				+ HardcodedValues.DATABASE_USERNAME
				+ HardcodedValues.DATABASE_PASSWORD);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return conn;
	}




	public static void closeAll(Connection conn, Statement stmt,
			ResultSet result, boolean commit) throws SQLException {
		try {
			if (result != null)
				result.close();
			if (stmt != null)
				stmt.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		if (conn != null) {
			if (commit)
				conn.commit();
			try {
				conn.close();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
	}
}
