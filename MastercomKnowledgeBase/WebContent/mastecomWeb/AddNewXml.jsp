<%@page import="java.io.IOException"%>
<%@page import="java.io.FileWriter"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
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
String PrjType =request.getParameter("PrjType");
String tagname =request.getParameter("tagname");
String tagdesc =request.getParameter("tagdesc");
System.out.println("Context Path"+request.getContextPath());
//request.
out.println(request.getContextPath());
String files;
File folder = new File(request.getContextPath());
File[] listOfFiles = folder.listFiles();
out.println(listOfFiles);
String Path ="D:/Office Data/WQMS/OSS_Workspace/MastercomKnowledgeBase/WebContent/mastecomWeb/"+PrjType+".xml";
BufferedReader br = new BufferedReader(new FileReader(Path));
String updateLine = "";
String xmlLine = "";
while ((updateLine = br.readLine()) != null) {
	System.out.println("updateLine"+updateLine);
	//out.println(updateLine);
	xmlLine +=updateLine;
}
//String firstTag = "<Knowledge>";
String tagName ="<KnowledgeTagName>"+tagname+"</KnowledgeTagName>";
String tagDesc ="<KnowledgeTagDesc>"+tagdesc+"</KnowledgeTagDesc>";
//String endTag ="</Knowledge>";
String finalXML =  tagName + tagDesc ;
System.out.print("---------"+updateLine);
String updateLinefinalXML = xmlLine.trim();
updateLinefinalXML = updateLinefinalXML.replaceAll("</Knowledge>","");
String upFinalXML = updateLinefinalXML + finalXML + "</Knowledge>";
//upFinalXML = upFinalXML.replaceAll("\\", "");
try
{
    String filename= "D:/Office Data/WQMS/OSS_Workspace/MastercomKnowledgeBase/WebContent/mastecomWeb/"+PrjType+".xml";
    FileWriter fw = new FileWriter(filename,false); //the true will append the new data
    upFinalXML = upFinalXML.replaceAll("\n","Non-Break-Space");
    upFinalXML = upFinalXML.replaceAll("\r","Non-Break-Space");
    upFinalXML = upFinalXML.replaceAll("&","Non-Break-Space");
    //upFinalXML = upFinalXML.replaceAll("\t","&break");
    fw.write(upFinalXML);//appends the string to the file
    fw.close();
}
catch(IOException ioe)
{
    System.err.println("IOException: " + ioe.getMessage());
}finally{
	
}
response.sendRedirect("createNewSOP.jsp?new SOP is Added");  
%>
</body>
</html>