package com.mastercom.report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.log4j.Logger;

import com.mastercom.excelWriter.WriteExcel;

public class MetaSolveReconcilationReportJAR {

	/**
	 * @param args
	 */
	
	
	private static Logger logger = Logger.getRootLogger();
	private String livePath = "D:/";
	//private String livePath = "/IBM/apps/wqms/Viznet/ORDERFILES/";
	Connection conn,connWqms = null;
	
	public void CheckMetasolve() throws SQLException{
		String url = "";
		String urlWqms = "";
		PreparedStatement pst = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//url = "jdbc:oracle:thin:@172.16.32.36:1521:EBUM6PRD";
			//conn = DriverManager.getConnection(url,"ranjit","vsnl_12345");
			
			conn = DriverManager.getConnection(
			        "jdbc:oracle:thin:@172.16.32.36:1521:EBUM6PRD", "ranjit", 
			        "vsnl_12345");
			
		} catch (ClassNotFoundException e) {
			System.err.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
			logger.info(e.getMessage());
		}

		try {
			
		SimpleDateFormat simpledateformat = new SimpleDateFormat("dd-MMM-yyyy");
		Date startDate = new Date();
		String jarSeachTime = simpledateformat.format(startDate);
		//#########################
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
				+ " and trunc(t.actual_release_date) in ('14-Oct-2014')";
		pst =conn.prepareStatement(checkQuery);
		ResultSet getOrderIDResuletSet = pst.executeQuery();
		String orderIdsForToday = "";
		while(getOrderIDResuletSet.next()){
			selectOrderIDList.add(getOrderIDResuletSet.getString(3));
			orderIdsForToday += "'" + getOrderIDResuletSet.getString(3) + "'";
			orderIdsForToday +=",";
		}
		orderIdsForToday  += "''";
		orderIdsForToday = orderIdsForToday.replaceAll(",''","");
		String selectOrdeIdFromGinvoice = "select * from asap.wqms_fcopf_ginvoice_details where document_number in("+orderIdsForToday+")";
		PreparedStatement selectOrdeIdFromGinvoicePst = conn.prepareStatement(selectOrdeIdFromGinvoice);
		ResultSet selectOrdeIdFromGinvoicePstResultSet = selectOrdeIdFromGinvoicePst.executeQuery();
		//#####################
		ArrayList<String> ginvoiceOrderIds = new ArrayList<String>();
		while(selectOrdeIdFromGinvoicePstResultSet.next()){
			ginvoiceOrderIds.add(selectOrdeIdFromGinvoicePstResultSet.getString(1));
		}
		Iterator<String> ginvoiceOrderIdsListIterator = ginvoiceOrderIds.listIterator();
		String  wqmsIDQueyString = "";
		while(ginvoiceOrderIdsListIterator.hasNext())
		{
			wqmsIDQueyString +="'"+ginvoiceOrderIdsListIterator.next()+"'";
			wqmsIDQueyString +=",";
		}
		wqmsIDQueyString +="''";
		//wqmsIDQueyString = wqmsIDQueyString.replaceAll(",''","");
		wqmsIDQueyString = wqmsIDQueyString.replaceAll(",''","");
		
		try {
			if(conn !=null){
				conn.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		fetchDB2Connection Db2Conn = new fetchDB2Connection();
		connWqms = Db2Conn.getDB2Connection();
		String wqmsIDQuery = "select order_id,circuit_id,wqms_id,created_date,modified_date,Source,Order_status from wqms.wqms_order_details " +
				"where order_id in ("+wqmsIDQueyString+") and Source='Metasolv' order by created_date desc";
		
		PreparedStatement wqmsIDQueyPst = connWqms.prepareStatement(wqmsIDQuery);
		ResultSet wqmsIDQueyRst = wqmsIDQueyPst.executeQuery();
		//#####################
		ArrayList<String> wqmsOrderIDList = new ArrayList<String>();
		while(wqmsIDQueyRst.next())
		{
			wqmsOrderIDList.add(wqmsIDQueyRst.getString(1));
		}
		
		String errorLogQuery = "select application_identifier1 as order_id,created_date,error_short_msg,error_code from wqms.bpm_error_log "+
								"where application_identifier1 in ("+wqmsIDQueyString+")order by created_date desc"; 
		
		PreparedStatement errorLogQueryPst = connWqms.prepareStatement(errorLogQuery);
		ResultSet errorLogQueryRst = errorLogQueryPst.executeQuery();
		//###########################
		HashMap<String,String> errorLogQueryList =  new HashMap<String,String>();
		while(errorLogQueryRst.next())
		{
			errorLogQueryList.put(errorLogQueryRst.getString(1),errorLogQueryRst.getString(3));
		}
		
		
//		File file = new File("D:/ExcelRepot.xls");
//	    WorkbookSettings wbSettings = new WorkbookSettings();
//
//	    wbSettings.setLocale(new Locale("en", "EN"));
//
//	    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
//	    workbook.createSheet("Total WQMS ID", 0);
//	    WritableSheet excelSheet = workbook.getSheet(0);
	// createLabel(excelSheet);
	   // addCaption(excelSheet, 0, 0, "Total Order ID");
	   // addCaption(excelSheet, 1, 0, "Integration Order ID");
	   // addCaption(excelSheet, 2, 0, "WQMS Order ID");
	   // addCaption(excelSheet, 3, 0, "Error Order ID");
	   // createContent(excelSheet);
	 WriteExcel test = new WriteExcel();
	    test.setOutputFile("D:/ExcelReportfinal.xls");
	    //test.write();
	    //addLabel(excelSheet, 0, 1, "Boring text,hello ");
	      // Second column
	     // addLabel(excelSheet, 1, 1, "Another text");
	     // addLabel(excelSheet, 2, 1, "Another text");
		/////////////////Writing CSV /////////////////////////
//		File metasolveReconfile = new File(livePath +"ExcelReport.csv");
//		logger.info("After Change");
//		// if file doesnt exists, then create it
//		if (!metasolveReconfile.exists()) {
//			metasolveReconfile.createNewFile();
//			logger.info("New file Created :" + livePath + "");
//		} else {
//			logger.error(livePath + "File is already available , can't be created ,so overwriting the data");
//		}
//		
//		FileWriter metasolveReconfw = new FileWriter(metasolveReconfile.getAbsoluteFile());
//		BufferedWriter metasolveReconbw = new BufferedWriter(metasolveReconfw);
//		metasolveReconbw.write("Total Order ID\n");
//		Iterator<String> selectOrderIDListList = selectOrderIDList.listIterator();
//		int selectOrderIDCounter = 0;
//		while (selectOrderIDListList.hasNext()) {
//			selectOrderIDCounter ++;
//			metasolveReconbw.write(selectOrderIDListList.next()+"\n");
//			// addLabel(excelSheet, 0, selectOrderIDCounter,selectOrderIDListList.next());
//		}
//		//metasolveReconbw.write(",");
//		metasolveReconbw.write("Integration Order ID\n");
//		Iterator<String> ginvoiceOrderIdsListIteratorWrite = ginvoiceOrderIds.listIterator();
//		int ginvoiceOrderIdsCounter = 0;
//		while (ginvoiceOrderIdsListIteratorWrite.hasNext()) {
//			ginvoiceOrderIdsCounter ++;
//			metasolveReconbw.write(ginvoiceOrderIdsListIteratorWrite.next()+"\n");
//		//addLabel(excelSheet, 1, ginvoiceOrderIdsCounter, ginvoiceOrderIdsListIteratorWrite.next());
//		}
//		//metasolveReconbw.write(",");
//		metasolveReconbw.write("WQMS Order ID\n");
//		Iterator<String> wqmsOrderIDListIterator = wqmsOrderIDList.listIterator();
//		int wqmsOrderIDCounter = 1;
//		while (wqmsOrderIDListIterator.hasNext()) {
//			wqmsOrderIDCounter ++;
//			//addLabel(excelSheet, 1, wqmsOrderIDCounter, wqmsOrderIDListIterator.next());
//			metasolveReconbw.write(wqmsOrderIDListIterator.next()+"\n");
//		}
//		//metasolveReconbw.write(",");
//		metasolveReconbw.write("Error Order ID\n");
//		int errorEntryCounter =1;
//		for(Map.Entry<String, String> errorEntry : errorLogQueryList.entrySet()){
//          //  System.out.println(errorEntry.getKey() +" :: "+ errorEntry.getValue());
//			errorEntryCounter ++;
//			//addLabel(excelSheet, 1, errorEntryCounter, ginvoiceOrderIdsListIteratorWrite.next());
//            metasolveReconbw.write(errorEntry.getKey() +", "+ errorEntry.getValue()+"\n");
//            //if you uncomment below code, it will throw java.util.ConcurrentModificationException
//            //studentGrades.remove("Alan");
//        }
		  test.write(selectOrderIDList,ginvoiceOrderIds,wqmsOrderIDList,errorLogQueryList);
		//metasolveReconbw.close();
		////////////////////////////////////////////////////////////////////
			//workbook.write();
		   // workbook.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				
				connWqms.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		
	}
	
	 private WritableCellFormat timesBoldUnderline;
	  private WritableCellFormat times;
	  private String inputFile;
	  
	public void setOutputFile(String inputFile) {
	  this.inputFile = inputFile;
	  }

	  public void write() throws IOException, WriteException {
	    File file = new File(inputFile);
	    WorkbookSettings wbSettings = new WorkbookSettings();

	    wbSettings.setLocale(new Locale("en", "EN"));

	    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
	    workbook.createSheet("Report", 0);
	    WritableSheet excelSheet = workbook.getSheet(0);
	    addLabel(excelSheet, 0, 1, "Boring text,hello " + 1);
	    //createLabel(excelSheet);
	   // createContent(excelSheet);

	    workbook.write();
	    workbook.close();
	  }

	  private void createLabel(WritableSheet sheet)
	      throws WriteException {
	    // Lets create a times font
	    WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
	    // Define the cell format
	    times = new WritableCellFormat(times10pt);
	    // Lets automatically wrap the cells
	    times.setWrap(true);

	    // create create a bold font with unterlines
	    WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
	        UnderlineStyle.SINGLE);
	    timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
	    // Lets automatically wrap the cells
	    timesBoldUnderline.setWrap(true);

	    CellView cv = new CellView();
	    cv.setFormat(times);
	    cv.setFormat(timesBoldUnderline);
	    cv.setAutosize(true);

	    // Write a few headers
	  //  addCaption(sheet, 0, 0, "Header 1");
	   // addCaption(sheet, 1, 0, "This is another header");
	    

	  }

	  private void createContent(WritableSheet sheet) throws WriteException,
	      RowsExceededException {

	      // First column
	      addLabel(sheet, 0, 1, "Boring text,hello " + 1);
	      // Second column
	      addLabel(sheet, 1, 1, "Another text");
	      addLabel(sheet, 2, 1, "Another text");
	  }

	  private void addCaption(WritableSheet sheet, int column, int row, String s)
	      throws RowsExceededException, WriteException {
	    Label label;
	    label = new Label(column, row, s, timesBoldUnderline);
	    sheet.addCell(label);
	  }

	  private void addLabel(WritableSheet sheet, int column, int row, String s)
	      throws WriteException, RowsExceededException {
	    Label label;
	    label = new Label(column, row, s, times);
	    sheet.addCell(label);
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
