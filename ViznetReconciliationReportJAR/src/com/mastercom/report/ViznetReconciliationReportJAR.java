/**
 * 
 */
package com.mastercom.report;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mastercom.viznet.ViznetOrderProcessJAR;

/**
 * @author Mastercom123
 * 
 */
public class ViznetReconciliationReportJAR {

	/**
	 * @param args
	 */
	private static Logger logger = Logger.getRootLogger();
	private String livePath = "D:/ORDERFILES/Reconcilation/CHANGED/";
	//private String livePath = "/IBM/apps/wqms/Viznet/ORDERFILES/";
	Connection conn = null;

	public void accessFilesFromLocal() {
		
		logger.info("Thread.Wait for 10 minutes for making sure the Updated files copied to /IBM/apps/wqms/Viznet/ORDERFILES/");
//		try {
//			Thread.sleep(600000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			logger.error(e.getMessage());
//		}
		try {
			SimpleDateFormat sdfFolder = new SimpleDateFormat("dd-MMM-yyyy");
			Date dateFolder = new Date();
			String timeFolder = sdfFolder.format(dateFolder);
			logger.info(" Started reading all files for Count : In Method accessFilesFromLocal() :");
			String files;
			File folder = new File(livePath+ timeFolder + "/");
			File[] listOfFiles = folder.listFiles();
			logger.info("In Folder: " + livePath + " Searching for files Got :"+listOfFiles.length+" files");
			HashMap<String, String> finalArrayMap = new HashMap<String, String>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (int i = 0; i < listOfFiles.length; i++) {
				if (listOfFiles[i].isFile()) {
					files = listOfFiles[i].getName();
					String[] readFileByLineArray = readFileByLine(livePath
							+ timeFolder + "/" + files, files);
					if (files.startsWith("ALL_CEASED_CIRCUITS")) {
						// logger.info("ALL_CEASED_CIRCUITS :: Total Records :"+readFileByLineArray[0]+" , In WQMS :"+readFileByLineArray[1]+", Failed :"+readFileByLineArray[2]);
						if (readFileByLineArray.length > 3) {
							finalArrayMap.put("Ceased", ""
									+ readFileByLineArray[0] + ":"
									+ readFileByLineArray[1] + ":"
									+ readFileByLineArray[2] + ":"
									+ readFileByLineArray[3]);
						} else {
							finalArrayMap.put("Ceased", ""
									+ readFileByLineArray[0] + ":"
									+ readFileByLineArray[1] + ":"
									+ readFileByLineArray[2]);
						}
					} else if (files.startsWith("ALL_COMMISSIONED_CIRCUITS")) {
						// logger.info("ALL_COMMISSIONED_CIRCUITS :: Total Records :"+readFileByLineArray[0]+" , In WQMS :"+readFileByLineArray[1]+", Failed :"+readFileByLineArray[2]);
						if (readFileByLineArray.length > 3) {
							finalArrayMap.put("Commission", ""
									+ readFileByLineArray[0] + ":"
									+ readFileByLineArray[1] + ":"
									+ readFileByLineArray[2] + ":"
									+ readFileByLineArray[3]);
						} else {
							finalArrayMap.put("Commission", ""
									+ readFileByLineArray[0] + ":"
									+ readFileByLineArray[1] + ":"
									+ readFileByLineArray[2]);
						}
					} else if (files.startsWith("ALL_TERMINATED_CIRCUITS")) {
						// logger.info("ALL_TERMINATED_CIRCUITS :: Total Records :"+readFileByLineArray[0]+" , In WQMS :"+readFileByLineArray[1]+", Failed :"+readFileByLineArray[2]);
						if (readFileByLineArray.length > 3) {
							finalArrayMap.put("Termination", ""
									+ readFileByLineArray[0] + ":"
									+ readFileByLineArray[1] + ":"
									+ readFileByLineArray[2] + ":"
									+ readFileByLineArray[3]);
						} else {
							finalArrayMap.put("Termination", ""
									+ readFileByLineArray[0] + ":"
									+ readFileByLineArray[1] + ":"
									+ readFileByLineArray[2]);
						}
					} else if (files.startsWith("ALL_UNCEASED_CIRCUITS")) {
						// logger.info("ALL_UNCEASED_CIRCUITS :: Total Records :"+readFileByLineArray[0]+" , In WQMS :"+readFileByLineArray[1]+", Failed :"+readFileByLineArray[2]);
						if (readFileByLineArray.length > 3) {
							finalArrayMap.put("Unceaced", ""
									+ readFileByLineArray[0] + ":"
									+ readFileByLineArray[1] + ":"
									+ readFileByLineArray[2] + ":"
									+ readFileByLineArray[3]);
						} else {
							finalArrayMap.put("Unceaced", ""
									+ readFileByLineArray[0] + ":"
									+ readFileByLineArray[1] + ":"
									+ readFileByLineArray[2]);
						}
					} else if (files
							.startsWith("CHANGE_ORDER_ENQUIRIES_COMPLETED")) {
						// logger.info("CHANGE_ORDER_ENQUIRIES_COMPLETED :: Total Records :"+readFileByLineArray[0]+" , In WQMS :"+readFileByLineArray[1]+", Failed :"+readFileByLineArray[2]);
						if (readFileByLineArray.length > 3) {
							finalArrayMap.put("COEC", ""
									+ readFileByLineArray[0] + ":"
									+ readFileByLineArray[1] + ":"
									+ readFileByLineArray[2] + ":"
									+ readFileByLineArray[3]);
						} else {
							finalArrayMap.put("COEC", ""
									+ readFileByLineArray[0] + ":"
									+ readFileByLineArray[1] + ":"
									+ readFileByLineArray[2]);
						}
					} else if (files
							.startsWith("CHANGE_ORDER_ENQUIRIES_ENTERED")) {
						// logger.info("CHANGE_ORDER_ENQUIRIES_ENTERED :: Total Records :"+readFileByLineArray[0]+" , In WQMS :"+readFileByLineArray[1]+", Failed :"+readFileByLineArray[2]);
						// finalArrayMap.put("COEE",""+readFileByLineArray[0]+":"+readFileByLineArray[1]+":"+readFileByLineArray[2]);
						if (readFileByLineArray.length > 3) {
							finalArrayMap.put("COEE", ""
									+ readFileByLineArray[0] + ":"
									+ readFileByLineArray[1] + ":"
									+ readFileByLineArray[2] + ":"
									+ readFileByLineArray[3]);
						} else {
							finalArrayMap.put("COEE", ""
									+ readFileByLineArray[0] + ":"
									+ readFileByLineArray[1] + ":"
									+ readFileByLineArray[2]);
						}
					} else if (files.startsWith("CIRCUIT_ENTERED_TODAY")) {
						// logger.info("CIRCUIT_ENTERED_TODAY :: Total Records :"+readFileByLineArray[0]+" , In WQMS :"+readFileByLineArray[1]+", Failed :"+readFileByLineArray[2]);
						if (readFileByLineArray.length > 3) {
							finalArrayMap.put("Circuit", ""
									+ readFileByLineArray[0] + ":"
									+ readFileByLineArray[1] + ":"
									+ readFileByLineArray[2] + ":"
									+ readFileByLineArray[3]);
						} else {
							finalArrayMap.put("Circuit", ""
									+ readFileByLineArray[0] + ":"
									+ readFileByLineArray[1] + ":"
									+ readFileByLineArray[2]);
						}
					}
					// }
				}
				// System.out.println("************************************************************************");
			}
			// //////////////////////Mail Iteration/////////////////////////
			String tableHeader = "<table border='1'><tr>";
			String tabledata = "";
			String csvHeader = "";
			for (Map.Entry<String, String> entry : finalArrayMap.entrySet()) {
				String ViznetName = entry.getKey();
				tableHeader = tableHeader
						+ "<td style='background-color: rgb(212, 232, 250);'>"
						+ ViznetName + "</td>";
				String ViznetData = entry.getValue().replaceAll("null", "");
				csvHeader = csvHeader + ViznetName + ",";
				// System.out.println("ViznetName="+ViznetName+":ViznetData"+ViznetData);
				String ViznetDataArray[] = ViznetData.split(":");
				// tabledata += "<tr><td>";
				for (int ViznetDataArrayCounter = 0; ViznetDataArrayCounter < ViznetDataArray.length; ViznetDataArrayCounter++) {
					// tabledata +=
					// ""+ViznetDataArray[ViznetDataArrayCounter]+"";
				}
				// tabledata += "</td></tr>";
				logger.info("ViznetName=" + ViznetName + ":ViznetData"
						+ ViznetData);
			}
			tableHeader = tableHeader + "</tr><tr>";
			String csvDataArray[] = new String[4];
			for (Map.Entry<String, String> entry : finalArrayMap.entrySet()) {
				// String ViznetName = entry.getKey();
				// tableHeader = tableHeader + "<tr><td>" + ViznetName +
				// "</td>";
				String ViznetData = entry.getValue().replaceAll("null", "");
				// System.out.println("ViznetName="+ViznetName+":ViznetData"+ViznetData);
				String ViznetDataArray[] = ViznetData.split(":");
				tabledata += "<td nowrap>";
				String faieldone = "";
				for (int ViznetDataArrayCounter = 0; ViznetDataArrayCounter < ViznetDataArray.length; ViznetDataArrayCounter++) {
					tabledata += "" + ViznetDataArray[ViznetDataArrayCounter]
							+ "<br>";
					if (ViznetDataArrayCounter == 0) {
						csvDataArray[0] += ViznetDataArray[ViznetDataArrayCounter]
								+ ",";
					} else if (ViznetDataArrayCounter == 1) {
						csvDataArray[1] += ViznetDataArray[ViznetDataArrayCounter]
								+ ",";
					} else if (ViznetDataArrayCounter == 2) {
						csvDataArray[2] += ViznetDataArray[ViznetDataArrayCounter]
								+ ",";
					}
					// faieldone +=
					// else if (ViznetDataArrayCounter == 3 ){
					// csvDataArray[2] +=
					// ViznetDataArray[ViznetDataArrayCounter] + "\n";
					// }
					// csvData += ViznetDataArray[ViznetDataArrayCounter] + "";
				}
				tabledata += "</td>";
				// csvData += ",";
				// logger.info("ViznetName="+ViznetName+":ViznetData"+ViznetData);
			}
			// System.out.println("tableHeader= "+tableHeader);
			// System.out.println("tabledata"+tabledata);
			tabledata = tableHeader + tabledata + "</tr></table>";
			// System.out.println(tabledata);
			logger.info("Final Table : " + tabledata);
			String csvFinalData = csvHeader
					+ "\n"
					+ csvDataArray[0].replaceAll("null", "")
					+ "\n"
					+ csvDataArray[1].replaceAll("null", "")
					+ "\n"
					+ csvDataArray[2].replaceAll("<br>", "").replaceAll("null",
							"") + "\n";
			// System.out.println(csvFinalData);
			// //////////////////////Writing Process
			SimpleDateFormat sdfCalendar = new SimpleDateFormat("yyyy-MM-dd");
			Date dateCalendar = new Date();
			String timeCalendar = sdf.format(dateCalendar);
			// System.out.println("time"+time);
			// ///////////////////////////////////////////////////////////////////////////
			File file = new File(livePath + timeFolder+"//Mail_Attchment_"
					+ timeCalendar + ".xls");
			logger.info("After Change");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
				logger.info("New file Created :" + livePath + "Mail_"
						+ timeCalendar);
			} else {
				logger.error(livePath
						+ "Mail_"
						+ timeCalendar
						+ "File is already available , can't be created ,so overwriting the data");
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for (int finalCounter = 1; finalCounter < 2; finalCounter++) {
				// logger.info("Line Number "+finalCounter+" = "+writeMap[finalCounter]);
				bw.write(tabledata);
				// if (finalCounter != lineCounter){
				// bw.newLine();
				// }
			}
			bw.close();
			// /////////////////////////////////////////////////////////////////////////////
			File fileFormat = new File(livePath + timeFolder+"/Mail_Format_" + timeCalendar
					+ ".csv");
			logger.info("After Change");
			// if file doesnt exists, then create it
			if (!fileFormat.exists()) {
				fileFormat.createNewFile();
				logger.info("New file Created :" + livePath + "MailFormat_"
						+ timeCalendar);
			} else {
				logger.error(livePath
						+ "MailFormat_"
						+ timeCalendar
						+ "File is already available , can't be created ,so overwriting the data");
			}

			FileWriter fwFormat = new FileWriter(fileFormat.getAbsoluteFile());
			BufferedWriter bwFormat = new BufferedWriter(fwFormat);
			for (int finalCounter = 1; finalCounter < 2; finalCounter++) {
				// logger.info("Line Number "+finalCounter+" = "+writeMap[finalCounter]);
				bwFormat.write(csvFinalData);
				// if (finalCounter != lineCounter){
				// bw.newLine();
				// }
			}
			bwFormat.close();
			logger.info("*****File Writing Done****************");
			// ////////////////////////////////////////////////////////////////////
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(e.getMessage());
			e.printStackTrace();
		}
	}

	public boolean executeQueryCheck(String orderID, String order_typeDOC,
			String queryType) throws SQLException {
		boolean status = false;
		PreparedStatement pst = null;
		String url = "";
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			url = "jdbc:db2://10.209.19.193:60025/WQMSDB";
			conn = DriverManager.getConnection(url, "bpminst", "D@t@p0wer");
		} catch (ClassNotFoundException e) {
			// System.err.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
			logger.info(e.getMessage());
		}

		try {
			String validatateQuery = "select created_date,modified_date from wqms.wqms_order_details "
					+ "where order_id=? and " + "Source='Viznet' ";
			if (queryType.trim().equalsIgnoreCase("All_Ceased_Circuits")) {
				validatateQuery = validatateQuery
						+ "and Order_Doc_Type in ('CANCELLED','ON HOLD')";
			} else if (queryType.trim().equalsIgnoreCase(
					"All_Commissioned_Circuits")) {
				validatateQuery = validatateQuery
						+ "and Order_Doc_Type = 'Commissioned'";
			} else if (queryType.trim().equalsIgnoreCase(
					"All_Terminated_Circuits")) {
				validatateQuery = validatateQuery
						+ "and Order_Doc_Type = 'Termination' and Order_Sub_Doc_Type = 'Purely Termination'";
			} else if (queryType.trim().equalsIgnoreCase(
					"All_Unceased_Circuits")) {
				validatateQuery = validatateQuery
						+ "and Order_Doc_Type = 'Under Provisioning' and Order_Sub_Doc_Type = null";
			} else if (queryType.trim().equalsIgnoreCase("COEC")) {
				validatateQuery = validatateQuery
						+ "and Order_Doc_Type = 'Shifting and BSO Changes'";
			} else if (queryType.trim().equalsIgnoreCase("COEE")) {
				validatateQuery = validatateQuery
						+ "and Order_Doc_Type = 'Termination' and Order_Sub_Doc_Type = 'Under Termination'";
			} else if (queryType.trim().equalsIgnoreCase(
					"Circuit_Entered_Today")) {
				validatateQuery = validatateQuery
						+ "and Order_Doc_Type  in  ('Under Provisioning','Commissioned') and Order_Sub_Doc_Type is not null";
			}
			validatateQuery = validatateQuery + " order by created_date desc";
			pst = conn.prepareStatement(validatateQuery);
			pst.setString(1, orderID);
			logger.info("Query :" + orderID + " = " + validatateQuery + "");
			// //////////should do for the ceased
			// pst.setString(2,order_typeDOC);
			ResultSet rst = pst.executeQuery();
			String created_date = "";
			String modified_date = "";
			while (rst.next()) {
				created_date = rst.getString(1);
				modified_date = rst.getString(2);
			}
			Calendar cal = Calendar.getInstance();
			// cal.setTime(new
			// SimpleDateFormat("dd-MMM-yyyy").parse(created_date));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String time = sdf.format(date);
			// System.out.println("time"+time);

			if ((created_date != null && created_date.trim().length() > 0)
					|| (modified_date != null && modified_date.trim().length() > 0)) {
				String[] createdDateArray = created_date.trim().split(" ");
				if (createdDateArray[0].trim().equalsIgnoreCase(time.trim())) {
					status = true;
				}
			}
			if ((modified_date != null && modified_date.trim().length() > 0)) {
				String[] modifiedDateArray = modified_date.trim().split(" ");
				if (modifiedDateArray[0].trim().equalsIgnoreCase(time.trim())) {
					status = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return status;
	}

	public String[] checkInBpmErrorLogTable(String orderIdIndex)
			throws SQLException {
		String[] bmpErrorStatus = new String[2];
		bmpErrorStatus[0] = "false";
		try {
			String bpmErrorQuery = "select application_identifier2,created_date,error_short_msg from wqms.bpm_error_log where application_identifier2 =?";
			PreparedStatement pstCheckinBpmLogTable = conn
					.prepareStatement(bpmErrorQuery);
			pstCheckinBpmLogTable.setString(1, orderIdIndex);
			ResultSet bpmResultSet = pstCheckinBpmLogTable.executeQuery();
			Calendar cal = Calendar.getInstance();
			// cal.setTime(new
			// SimpleDateFormat("dd-MMM-yyyy").parse(created_date));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String time = sdf.format(date);
			String ErrorParseDate = "";
			String errorMsg = "";
			bmpErrorStatus[0] = "false";
			bmpErrorStatus[1] = "Not Processed - as per Requirement";
			// bpmResultSet.beforeFirst();
			while (bpmResultSet.next()) {
				ErrorParseDate = bpmResultSet.getString(2);
				String[] errorParseDateArray = ErrorParseDate.split(" ");
				if (errorParseDateArray[0].trim().equalsIgnoreCase(time)) {
					bmpErrorStatus[0] = "true";
					bmpErrorStatus[1] = bpmResultSet.getString(3);

				} else {
					bmpErrorStatus[0] = "false";
					bmpErrorStatus[1] = "Not Processed - as per Requirement";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return bmpErrorStatus;
	}

	public String[] getOrderAndCircuitAndEnquriesAndCompletedID(
			String csvFileToRead, int orderIdIndex, int enquiryTypeIndex,
			String OrderID, String conditionField, String queryType) {
		BufferedReader br = null;
		boolean reprocessStatus = false;
		String line = "";
		String splitBy = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
		String updateLine = "";
		int updateLineCounter = 0;
		String[] enquiriesArray = new String[4];
		try {
			logger.info("Starting File reading for file :" + csvFileToRead + "");
			// System.out.println("Starting File reading for file :"+csvFileToRead+"");
			br = new BufferedReader(new FileReader(csvFileToRead));
			HashMap<String, String> hashArrMap = new HashMap<String, String>();
			int successCounter = 0;
			int failureCounter = 0;
			int totalCounter = 0;
			while ((updateLine = br.readLine()) != null) {
				updateLineCounter++;
				// System.out.println(updateLine);
				if (updateLine.contains(",")) {
					String[] mycontainsArray = updateLine.trim().split(splitBy);
					if (mycontainsArray.length > 1) {
						if (!mycontainsArray[orderIdIndex].trim()
								.equalsIgnoreCase("Order ID")
								&& !mycontainsArray[enquiryTypeIndex].trim()
										.equalsIgnoreCase(conditionField)) {
							if ((mycontainsArray[orderIdIndex] != null && mycontainsArray[orderIdIndex]
									.trim().length() > 0)
									&& (mycontainsArray[enquiryTypeIndex] != null && mycontainsArray[enquiryTypeIndex]
											.trim().length() > 0)) {
								totalCounter++;
								hashArrMap.put(mycontainsArray[orderIdIndex],
										mycontainsArray[enquiryTypeIndex]);
								// System.out.println(""+OrderID+" = "+mycontainsArray[orderIdIndex]);
								// System.out.println(""+conditionField+" = "+mycontainsArray[enquiryTypeIndex]);
								boolean returnStatus = executeQueryCheck(
										mycontainsArray[orderIdIndex],
										mycontainsArray[enquiryTypeIndex],
										queryType);
								// System.out.println("returnStatus"+returnStatus);
								if (returnStatus) {
									successCounter++;
									enquiriesArray[3] += mycontainsArray[orderIdIndex]
											+ "- Successfully Processed<br>\n";
								} else {
									String[] bpmErrorLogStatus = checkInBpmErrorLogTable(mycontainsArray[orderIdIndex]);
									if (bpmErrorLogStatus[0].trim()
											.equalsIgnoreCase("true")) {
										failureCounter++;
										enquiriesArray[3] += mycontainsArray[orderIdIndex]
												+ "- Failed Reason -"
												+ bpmErrorLogStatus[1]
												+ "<br>\n";
									} else {
										enquiriesArray[3] += mycontainsArray[orderIdIndex]
												+ "- Failed Reason -"
												+ bpmErrorLogStatus[1]
												+ "<br>\n";
									}
								}
							}
						}
					}
				}
			}
			enquiriesArray[0] = "Total Records - " + totalCounter;
			enquiriesArray[1] = "IN WQMS - " + successCounter;
			enquiriesArray[2] = "Failed - " + failureCounter + "";
			// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return enquiriesArray;
	}

	public String[] readFileByLine(String csvFileToRead, String onlyFilename) {
		logger.info("Got File so , in Method readFileByLine()");
		BufferedReader br = null;
		String line = "";
		String splitBy = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
		String[] statusArr = new String[4];
		try {
			logger.info("Starting File reading for file :" + csvFileToRead + "");
			br = new BufferedReader(new FileReader(csvFileToRead));
			String[] writeMap = new String[5000];
			HashMap<Integer, Integer> modifyCount = new HashMap<Integer, Integer>();
			int countArray[] = new int[5000];
			int lineCounter = 0;
			int srnoCount = 0;
			String updateLine = "";
			int updateLineCounter = 0;
			logger.info("Before Modification");
			if (onlyFilename.startsWith("CHANGE_ORDER_ENQUIRIES_COMPLETED")) {
				// HashMap<String,Integer> hashArrMap = new HashMap<String,
				// Integer>();
				int orderIdIndex = 0;
				int enquiryTypeIndex = 0;
				while ((line = br.readLine()) != null) {
					lineCounter++;
					int commaCountbefore = 0;
					// System.out.println(line);
					logger.info("Line Number " + lineCounter + " = "
							+ line.trim() + "");
					int numberOfCommas = 0;
					String myarray[] = new String[5000];
					if (line.trim().contains("SR. No")) {
						srnoCount++;
						numberOfCommas = line.replaceAll("[^,]", "").length();
						myarray = line.trim().split(splitBy);
						int myarrayCountCounter = 0;
						for (int myarrayCounter = 0; myarrayCounter < myarray.length; myarrayCounter++) {
							myarrayCountCounter++;
							if (myarray[myarrayCounter]
									.equalsIgnoreCase("Enquiry Type")) {
								// hashArrMap.put("Circuit Status",
								// myarrayCounter);
								enquiryTypeIndex = myarrayCounter;
							}
							if (myarray[myarrayCounter]
									.equalsIgnoreCase("Order id")) {
								// hashArrMap.put("orderid", myarrayCounter);
								orderIdIndex = myarrayCounter;
							}
						}
						break;
					}
				}
				statusArr = getOrderAndCircuitAndEnquriesAndCompletedID(
						csvFileToRead, orderIdIndex, enquiryTypeIndex,
						"Order id", "Enquiry Type", "COEC");
			} else if (onlyFilename
					.startsWith("CHANGE_ORDER_ENQUIRIES_ENTERED")) {
				int orderIdIndex = 0;
				int enquiryTypeIndex = 0;
				while ((line = br.readLine()) != null) {
					lineCounter++;
					int commaCountbefore = 0;
					// System.out.println(line);
					logger.info("Line Number " + lineCounter + " = "
							+ line.trim() + "");
					int numberOfCommas = 0;
					String myarray[] = new String[5000];
					if (line.trim().contains("SR. No")) {
						srnoCount++;
						numberOfCommas = line.replaceAll("[^,]", "").length();
						myarray = line.trim().split(splitBy);
						int myarrayCountCounter = 0;
						for (int myarrayCounter = 0; myarrayCounter < myarray.length; myarrayCounter++) {
							myarrayCountCounter++;
							if (myarray[myarrayCounter]
									.equalsIgnoreCase("Enquiry Type")) {
								// hashArrMap.put("Circuit Status",
								// myarrayCounter);
								enquiryTypeIndex = myarrayCounter;
							}
							if (myarray[myarrayCounter]
									.equalsIgnoreCase("Order id")) {
								// hashArrMap.put("orderid", myarrayCounter);
								orderIdIndex = myarrayCounter;
							}
						}
					}
				}
				// ////////////////////for
				statusArr = getOrderAndCircuitAndEnquriesAndCompletedID(
						csvFileToRead, orderIdIndex, enquiryTypeIndex,
						"Order id", "Enquiry Type", "COEE");
			} else if (onlyFilename.startsWith("ALL_CEASED_CIRCUITS")) {
				int orderIdIndex = 0;
				int ceasedTypeIndex = 0;
				while ((line = br.readLine()) != null) {
					lineCounter++;
					// System.out.println(line);
					logger.info("Line Number " + lineCounter + " = "
							+ line.trim() + "");
					int numberOfCommas = 0;
					String myarray[] = new String[5000];
					if (line.trim().contains("SR. No")) {
						srnoCount++;
						numberOfCommas = line.replaceAll("[^,]", "").length();
						myarray = line.trim().split(splitBy);
						int myarrayCountCounter = 0;
						for (int myarrayCounter = 0; myarrayCounter < myarray.length; myarrayCounter++) {
							myarrayCountCounter++;
							if (myarray[myarrayCounter]
									.equalsIgnoreCase("Order id")) {
								// hashArrMap.put("orderid", myarrayCounter);
								orderIdIndex = myarrayCounter;
							}
							if (myarray[myarrayCounter]
									.equalsIgnoreCase("Cease Type")) {
								// hashArrMap.put("Circuit Status",
								// myarrayCounter);
								ceasedTypeIndex = myarrayCounter;
							}
						}
					}
				}
				statusArr = getOrderAndCircuitAndEnquriesAndCompletedID(
						csvFileToRead, orderIdIndex, ceasedTypeIndex,
						"Order id", "Cease Type", "All_Ceased_Circuits");
			} else if (onlyFilename.startsWith("ALL_COMMISSIONED_CIRCUITS")) {
				int orderIdIndex = 0;
				int commissionedIndex = 0;
				while ((line = br.readLine()) != null) {
					lineCounter++;
					// System.out.println(line);
					logger.info("Line Number " + lineCounter + " = "
							+ line.trim() + "");
					int numberOfCommas = 0;
					String myarray[] = new String[5000];
					if (line.trim().contains("SR. No")) {
						srnoCount++;
						numberOfCommas = line.replaceAll("[^,]", "").length();
						myarray = line.trim().split(splitBy);
						int myarrayCountCounter = 0;
						for (int myarrayCounter = 0; myarrayCounter < myarray.length; myarrayCounter++) {
							myarrayCountCounter++;
							if (myarray[myarrayCounter]
									.equalsIgnoreCase("Order id")) {
								// hashArrMap.put("orderid", myarrayCounter);
								orderIdIndex = myarrayCounter;
							}
							if (myarray[myarrayCounter]
									.equalsIgnoreCase("Cct status")) {
								// hashArrMap.put("Circuit Status",
								// myarrayCounter);
								commissionedIndex = myarrayCounter;
							}
						}
					}

				}
				statusArr = getOrderAndCircuitAndEnquriesAndCompletedID(
						csvFileToRead, orderIdIndex, commissionedIndex,
						"Order id", "Cct status", "All_Commissioned_Circuits");
			} else if (onlyFilename.startsWith("ALL_TERMINATED_CIRCUITS")) {
				int orderIdIndex = 0;
				int terminatedIndex = 0;
				while ((line = br.readLine()) != null) {
					lineCounter++;
					// System.out.println(line);
					logger.info("Line Number " + lineCounter + " = "
							+ line.trim() + "");
					int numberOfCommas = 0;
					String myarray[] = new String[5000];
					if (line.trim().contains("SR. No")) {
						srnoCount++;
						numberOfCommas = line.replaceAll("[^,]", "").length();
						myarray = line.trim().split(splitBy);
						int myarrayCountCounter = 0;
						for (int myarrayCounter = 0; myarrayCounter < myarray.length; myarrayCounter++) {
							myarrayCountCounter++;
							if (myarray[myarrayCounter]
									.equalsIgnoreCase("Order id")) {
								// hashArrMap.put("orderid", myarrayCounter);
								orderIdIndex = myarrayCounter;
							}
							if (myarray[myarrayCounter]
									.equalsIgnoreCase("Cct status")) {
								// hashArrMap.put("Circuit Status",
								// myarrayCounter);
								terminatedIndex = myarrayCounter;
							}
						}
					}
				}
				statusArr = getOrderAndCircuitAndEnquriesAndCompletedID(
						csvFileToRead, orderIdIndex, terminatedIndex,
						"Order id", "Cct status", "All_Terminated_Circuits");
			} else if (onlyFilename.startsWith("ALL_UNCEASED_CIRCUITS")) {
				int orderIdIndex = 0;
				int ccStatusIndex = 0;
				while ((line = br.readLine()) != null) {
					lineCounter++;
					int commaCountbefore = 0;
					// System.out.println(line);
					logger.info("Line Number " + lineCounter + " = "
							+ line.trim() + "");
					int numberOfCommas = 0;
					String myarray[] = new String[5000];
					if (line.trim().contains("SR. No")) {
						srnoCount++;
						numberOfCommas = line.replaceAll("[^,]", "").length();
						myarray = line.trim().split(splitBy);
						int myarrayCountCounter = 0;
						for (int myarrayCounter = 0; myarrayCounter < myarray.length; myarrayCounter++) {
							myarrayCountCounter++;
							if (myarray[myarrayCounter]
									.equalsIgnoreCase("Order id")) {
								// hashArrMap.put("orderid", myarrayCounter);
								orderIdIndex = myarrayCounter;
							}
							if (myarray[myarrayCounter]
									.equalsIgnoreCase("Cease Type")) {
								// hashArrMap.put("Circuit Status",
								// myarrayCounter);
								ccStatusIndex = myarrayCounter;
							}
						}//
					}
				}
				statusArr = getOrderAndCircuitAndEnquriesAndCompletedID(
						csvFileToRead, orderIdIndex, ccStatusIndex, "Order id",
						"Cct status", "All_Unceased_Circuits");
			} else if (onlyFilename.startsWith("CIRCUIT_ENTERED_TODAY")) {
				int orderIdIndex = 0;
				int circuitStatusIndex = 0;
				while ((line = br.readLine()) != null) {
					lineCounter++;
					int commaCountbefore = 0;
					// System.out.println(line);
					logger.info("Line Number " + lineCounter + " = "
							+ line.trim() + "");
					int numberOfCommas = 0;
					String myarray[] = new String[5000];
					if (line.trim().contains("SR. No")) {
						srnoCount++;
						numberOfCommas = line.replaceAll("[^,]", "").length();
						myarray = line.trim().split(splitBy);
						int myarrayCountCounter = 0;
						for (int myarrayCounter = 0; myarrayCounter < myarray.length; myarrayCounter++) {
							myarrayCountCounter++;
							if (myarray[myarrayCounter]
									.equalsIgnoreCase("Order id")) {
								// hashArrMap.put("orderid", myarrayCounter);
								orderIdIndex = myarrayCounter;
							}
							if (myarray[myarrayCounter]
									.equalsIgnoreCase("Circuit Status")) {
								// hashArrMap.put("Circuit Status",
								// myarrayCounter);
								circuitStatusIndex = myarrayCounter;
							}
						}
					}
				}
				statusArr = getOrderAndCircuitAndEnquriesAndCompletedID(
						csvFileToRead, orderIdIndex, circuitStatusIndex,
						"Order id", "Circuit Status", "Circuit_Entered_Today");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.info(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					logger.info(e.getMessage());
				}
			}
		}
		return statusArr;
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		ViznetReconciliationReportJAR viznetRecon = new ViznetReconciliationReportJAR();
		ViznetOrderProcessJAR viZnetOrderprocess = new ViznetOrderProcessJAR();
		SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		Date startDate = new Date();
		String jarStarttime = simpledateformat.format(startDate);
		logger.info("*********************************************************************");
		logger.info("Jar File Has Started for Date :"+jarStarttime);
		//viZnetOrderprocess.accessFilesFromLocal();
		viznetRecon.accessFilesFromLocal();
		Date endDate = new Date();
		String jarEndtime = simpledateformat.format(endDate);
		logger.info("Jar File Completed at"+jarEndtime);
		

	}

}
