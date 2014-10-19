<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Process</title>
</head>
<body>
<%
Logger logger = Logger.getRootLogger();
String tataid = request.getParameter("tataid");
String ticketno = request.getParameter("ticketno");
String choosetype = request.getParameter("choosetype");
ArrayList<String> wqmsidList = new ArrayList<String>();
Connection connection = null;
String url ="";
ArrayList<String> arrList = new ArrayList<String>();
try {
	
	 Class.forName("com.ibm.db2.jcc.DB2Driver");
	 url = "jdbc:db2://10.209.19.193:60025/WQMSDB";
	 connection = DriverManager.getConnection(url, "bpminst", "D@t@p0wer");
} catch (ClassNotFoundException e) {
	System.err.println("Where is your Oracle JDBC Driver?");
	e.printStackTrace();
	return;
}
try {
	connection.setAutoCommit(false);
if(choosetype.equalsIgnoreCase("single")){
	String sinngleid = request.getParameter("wqmsidsin");
	wqmsidList.add(sinngleid);
	
}else if(choosetype.equalsIgnoreCase("multiple")){
	String wqmsid = request.getParameter("wqmsidmul");
	String []wqmsIdArr = wqmsid.trim().split("\r\n");
	for(int wqmsIdArrCounter = 0 ;wqmsIdArrCounter<wqmsIdArr.length;wqmsIdArrCounter++){
		if(wqmsIdArr[wqmsIdArrCounter] != null && wqmsIdArr[wqmsIdArrCounter].trim().length() > 0){
			wqmsidList.add(wqmsIdArr[wqmsIdArrCounter]);
		}
	}
	
}
	for(int wqmsidListCounter = 0 ;wqmsidListCounter<wqmsidList.size();wqmsidListCounter++){
		String queryStatusfinal ="";
		String query1 ="UPDATE WQMS.WQMS_ORDER_DETAILS SET ORDER_STATUS = 'Terminated in System', WQMS_STEP_NAME = 'Terminated in System' WHERE WQMS_ID = '"+wqmsidList.get(wqmsidListCounter)+"'";
		PreparedStatement pst = connection.prepareStatement(query1);
		int status =pst.executeUpdate();
		connection.commit();
		if(status  > 0 ){
			queryStatusfinal = ""+wqmsidList.get(wqmsidListCounter)+"= Terminated";
			String query2 = "INSERT INTO WQMS.WQMS_ORDER_REMARKS_TRANSACTION_LOG (WQMS_ID,TXN_NAME,USER,TXN_TIME,REMARKS,TEAM_NAME) VALUES ( '"+wqmsidList.get(wqmsidListCounter)+"', 'Termination', '"+tataid+"', CURRENT_DATE,'Terminated-SDP Ticket Number"+ticketno+"','bpmadmin')";
		    PreparedStatement pstInsert = connection.prepareStatement(query2);
		    int finstat = pstInsert.executeUpdate();
		    connection.commit();
		    if(finstat > 0){
		    	queryStatusfinal = queryStatusfinal + ":Logged";
		    }
		}
		arrList.add(queryStatusfinal);
	}


} catch (Exception e) {
	System.err.println("Connection Failed! Check output console");
	e.printStackTrace();
}
finally {
	try {
		if (connection != null)
			connection.close();
	} catch (SQLException e) {
          e.printStackTrace();
	}
}
String responseHTML = "<div style=''>";

for(int displayCounter = 0 ;displayCounter<arrList.size(); displayCounter++){
	responseHTML += arrList.get(displayCounter)+"<br>";
	logger.info(arrList.get(displayCounter));
}
responseHTML +="</div>";
response.sendRedirect("index.jsp?responseHTML="+responseHTML);

%>
</body>
</html>