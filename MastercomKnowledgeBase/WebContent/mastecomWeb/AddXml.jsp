<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
System.out.println("Context Path"+request.getContextPath());
//request.
out.println(request.getContextPath());
String files;
File folder = new File(request.getContextPath());
File[] listOfFiles = folder.listFiles();
out.println(listOfFiles);
String Path ="D:\Office Data\WQMS\OSS_Workspace\MastercomKnowledgeBase\WebContent\mastecomWeb\projectXMLs\ces.xml";

%>
</body>
</html>