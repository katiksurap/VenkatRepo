package com.mastercom.wqms;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MetasolveWQMSOrderMonitoring {
	public static void main(String args[]) {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@172.16.32.36:1521:EBUM6PRD", "ranjit",
					"vsnl_12345");

			String nul = "0";
			// step3 create the statement object
			String countQuery[] = new String[4];
			countQuery[0] = "select Count(*) from ASAP.WQMS_FCOPF_GINVOICE_DETAILS  where STATUS is null and created_date>=to_date(Current_date)";
			countQuery[1] = "select Count(*) from ASAP.WQMS_FCOPF_GINVOICE_DETAILS  where STATUS='Failed' and created_date>=to_date(Current_date)";
			countQuery[2] = "select Count(*) from ASAP.WQMS_FCOPF_GINVOICE_DETAILS  where STATUS='Success' and created_date>=to_date(Current_date)";
			countQuery[3] = "select Count(*) from ASAP.WQMS_FCOPF_GINVOICE_DETAILS  where STATUS='WSFail' and created_date>=to_date(Current_date)";

			SimpleDateFormat simpledateformat = new SimpleDateFormat(
					"dd-MM-yyyy HH:mm");
			Date startDate = new Date();
			String jarStarttime = simpledateformat.format(startDate);
			System.out.println("WQMS Record Status in MetaSolve DB at "
					+ jarStarttime + "\n");

			// Null Count Check
			for (String querLoopValues : countQuery) {
				PreparedStatement getConnFornull = con
						.prepareStatement(querLoopValues);
				ResultSet getCountForNullResultset = getConnFornull
						.executeQuery();

				while (getCountForNullResultset.next()) {
					nul = getCountForNullResultset.getString(1);
					if (querLoopValues.contains("Failed")) {
						System.out
								.println("FAILED COUNT :" + nul + "\n");

					} else if (querLoopValues.contains("null")) {
						System.out.println("NULL COUNT :" + nul + "\n");

					} else if (querLoopValues.contains("WSFail")) {
						System.out
								.println("WSFAIL COUNT :" + nul + "\n");

					} else if (querLoopValues.contains("Success")) {
						System.out.println("SUCCESS COUNT :" + nul
								+ "\n");

					}
				}
				getConnFornull = null;
				getCountForNullResultset = null;

			}

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}

	}
}