<%@page import="java.util.Map"%><%@page import="java.util.HashMap"%><%@page import="com.mastercom.JavaXml.SaxParser.ReadXMLFile"%><%
String prjType = request.getParameter("prjType");
String KnowledgeType = request.getParameter("KnowledgeType");
ReadXMLFile readXMLFile = new ReadXMLFile();
HashMap<String,String> newHashAMap   = readXMLFile.getElements(prjType.trim());
for(Map.Entry<String, String> entry : newHashAMap.entrySet()) {
  //  System.out.println(entry.getKey());
  // System.out.println(entry.getKey()+"-------------"+entry.getValue());
   if(KnowledgeType.trim().equalsIgnoreCase(entry.getKey().trim())){
	   out.println(entry.getValue());
   }
}
%>