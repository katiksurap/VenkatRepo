package com.mastercom.report;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.mastercom.excelWriter.WriteExcel;

public class MetaSolveReconcilationReportJAR {

	/**
	 * @param args
	 */

	private static org.apache.log4j.Logger logger = Logger
			.getLogger("com.second");
	//private String livePath = "D:/ORDERFILES/Reconcilation/";
	private String livePath = "/IBM/apps/wqms/Viznet/ORDERFILES/";
	Connection conn, connWqms = null;

	public void CheckMetasolve() throws SQLException {

		SimpleDateFormat sdateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.S");
		Date sDate = new Date();
		String jarStarttime = sdateformat.format(sDate);
		logger.info("*********************************************************************");
		logger.info("MetaSolve Jar File Has Started for Date :" + jarStarttime);
		PreparedStatement pst = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@172.16.32.36:1521:EBUM6PRD", "ranjit",
					"vsnl_12345");
			logger.info("Got Metasolve Connection" + conn);
		} catch (ClassNotFoundException e) {
			System.err.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
			logger.error(e.getMessage());
		}

		try {

			Calendar cal = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			cal.add(Calendar.DATE, -1);
			String jarSeachTime = dateFormat.format(cal.getTime());
			// #########################
			ArrayList<String> selectOrderIDList = new ArrayList<String>();
			String checkQuery = "select sr.project_identification as copf_id,"
					+ " sr.related_pon as Service_id,"
					+ " t.document_number,"
					+ " t.task_number,"
					+ " t.task_type,"
					+ " sr.supplement_type,"
					+ " t.task_status"
					+ " from serv_req sr, task t"
					+ " where sr.document_number = t.document_number"
					+ " and t.task_type = 'GINVOICE'"
					+ " and t.task_status !='Pending'"
					+ " and sr.organization_id = '01'"
					+ " and nvl(sr.supplement_type,2)!= 1"
					+ " AND NOT EXISTS (SELECT 1 FROM CUST_ACCT CA , asap.CUSTOMER_LOOKUP CL WHERE CA.CUST_ACCT_ID = SR.CUST_ACCT_ID"
					+ " and upper(ca.company_name) = upper(cl.company_name))"
					+ " and trunc(t.actual_release_date) in ('" + jarSeachTime
					+ "')";
			pst = conn.prepareStatement(checkQuery);
			logger.info("1. Metasolve Query = " + checkQuery);
			ResultSet getOrderIDResuletSet = pst.executeQuery();
			String orderIdsForToday = "";
			logger.info("1. Metasolve Query Resultset = "
					+ getOrderIDResuletSet);
			while (getOrderIDResuletSet.next()) {
				selectOrderIDList.add(getOrderIDResuletSet.getString(3));
				orderIdsForToday += "'" + getOrderIDResuletSet.getString(3)
						+ "'";
				orderIdsForToday += ",";
			}

			orderIdsForToday += "''";
			orderIdsForToday = orderIdsForToday.replaceAll(",''", "");

			String selectOrdeIdFromGinvoice = "select * from asap.wqms_fcopf_ginvoice_details where document_number in("
					+ orderIdsForToday + ")";
			PreparedStatement selectOrdeIdFromGinvoicePst = conn
					.prepareStatement(selectOrdeIdFromGinvoice);
			logger.info("2. Metasolve Query = " + selectOrdeIdFromGinvoice);
			ResultSet selectOrdeIdFromGinvoicePstResultSet = selectOrdeIdFromGinvoicePst
					.executeQuery();
			// #####################
			logger.info("2. Metasolve Query ResultSet = "
					+ selectOrdeIdFromGinvoicePstResultSet);
			ArrayList<String> ginvoiceOrderIds = new ArrayList<String>();
			while (selectOrdeIdFromGinvoicePstResultSet.next()) {
				ginvoiceOrderIds.add(selectOrdeIdFromGinvoicePstResultSet
						.getString(1));
			}
			Iterator<String> ginvoiceOrderIdsListIterator = ginvoiceOrderIds
					.listIterator();
			String wqmsIDQueyString = "";
			while (ginvoiceOrderIdsListIterator.hasNext()) {
				wqmsIDQueyString += "'" + ginvoiceOrderIdsListIterator.next()
						+ "'";
				wqmsIDQueyString += ",";
			}
			wqmsIDQueyString += "''";
			wqmsIDQueyString = wqmsIDQueyString.replaceAll(",''", "");

			try {
				if (conn != null) {
					conn.close();
					logger.info("metasolve connection closed");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				logger.error("exception while connection closed"
						+ e.getMessage());
			}

			fetchDB2Connection Db2Conn = new fetchDB2Connection();
			connWqms = Db2Conn.getDB2Connection();
			String wqmsIDQuery = "select order_id,circuit_id,wqms_id,created_date,modified_date,Source,Order_status from wqms.wqms_order_details "
					+ "where order_id in ("
					+ wqmsIDQueyString
					+ ") and Source='Metasolv' order by created_date desc";

			PreparedStatement wqmsIDQueyPst = connWqms
					.prepareStatement(wqmsIDQuery);
			logger.info("3.WQMS Query = " + wqmsIDQuery);
			ResultSet wqmsIDQueyRst = wqmsIDQueyPst.executeQuery();
			// #####################
			logger.info("3.WQMS Query = " + wqmsIDQueyRst);
			ArrayList<String> wqmsOrderIDList = new ArrayList<String>();
			while (wqmsIDQueyRst.next()) {
				wqmsOrderIDList.add(wqmsIDQueyRst.getString(1));
			}

			String errorLogQuery = "select application_identifier1 as order_id,created_date,error_short_msg,error_code from wqms.bpm_error_log "
					+ "where application_identifier1 in ("
					+ wqmsIDQueyString
					+ ")order by created_date desc";

			PreparedStatement errorLogQueryPst = connWqms
					.prepareStatement(errorLogQuery);
			logger.info("4.WQMS First Query = " + errorLogQuery);
			ResultSet errorLogQueryRst = errorLogQueryPst.executeQuery();
			// ###########################
			logger.info("4. Error Log errorLogQueryRst" + errorLogQueryRst);
			HashMap<String, String> errorLogQueryList = new HashMap<String, String>();
			while (errorLogQueryRst.next()) {
				if(!errorLogQueryRst.getString(3).contains("no WQMS Id")){
				errorLogQueryList.put(errorLogQueryRst.getString(1),errorLogQueryRst.getString(3));
			}
			}

			WriteExcel test = new WriteExcel();
			SimpleDateFormat sdfFolder = new SimpleDateFormat("dd-MMM-yyyy");
			Date dateFolder = new Date();
			String timeFolder = sdfFolder.format(dateFolder);
			File fileFolder = new File((livePath + timeFolder));
			if (!fileFolder.exists()) {
				if (fileFolder.mkdir()) {
					// System.out
					// .println("Directory is created with Date for Metasolve: "
					// + timeFolder);
					logger.info("Directory is created with Date for Metasolve: "
							+ timeFolder);
				} else {
					// System.out
					// .println("Failed to create directory with Date for MetaSolve aleady its there :"
					// + timeFolder);
					logger.info("Failed to create directory with Date for MetaSolve aleady its there :"
							+ timeFolder);
				}
			} else {
				// System.out.println("Folder MetaSolve aleady its there :"
				// + timeFolder);
				logger.info("Failed to create directory with Date for MetaSolve aleady its there :"
						+ timeFolder);
			}
			logger.info("Total Order ID in Main Metasolve table = "
					+ selectOrderIDList.size());
			logger.info("Total Order ID in Integration table = "
					+ ginvoiceOrderIds.size());
			logger.info("Total Order ID in WQMS table = "
					+ wqmsOrderIDList.size());
			logger.info("Total Order ID in WQMS.Error Log table = "
					+ errorLogQueryList.size());
			test.setOutputFile(livePath + timeFolder
					+ "/MetasolveReconcilation.xls");
			test.write(selectOrderIDList, ginvoiceOrderIds, wqmsOrderIDList,
					errorLogQueryList);
			logger.info("MetaSolve File Report has been done " + livePath
					+ timeFolder + "/MetasolveReconcilation.xls");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("exception in Metasolve" + e.getMessage());
		} finally {
			try {
				logger.info("WQMS connection closed");
				connWqms.close();
			} catch (Exception e2) {
				// TODO: handle exception
				logger.info("error while closing WQMS connection"
						+ e2.getMessage());
				e2.printStackTrace();
			}

		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MetaSolveReconcilationReportJAR metaSolve = new MetaSolveReconcilationReportJAR();
		try {
			metaSolve.CheckMetasolve();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
