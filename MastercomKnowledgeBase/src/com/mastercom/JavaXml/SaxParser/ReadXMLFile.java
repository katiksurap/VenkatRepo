package com.mastercom.JavaXml.SaxParser;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
 
public class ReadXMLFile {
 
	
   public HashMap<String,String>  getElements(String fileName) {
 final HashMap<String,String> newHashAMap =  new HashMap<String, String>();
 //final TreeMap<String, String> myMap = new TreeMap<String, String>();
 //String first = myMap.firstEntry().getValue();
 //String firstOther = myMap.get(myMap.firstKey());
    try {
 
	SAXParserFactory factory = SAXParserFactory.newInstance();
	SAXParser saxParser = factory.newSAXParser();
 
	DefaultHandler handler = new DefaultHandler() {
 
	boolean bfname = false;
	boolean blname = false;
	boolean bnname = false;
	boolean bsalary = false;
	String firstElement = "";
	String secondElement = "";
	public void startElement(String uri, String localName,String qName, 
                Attributes attributes) throws SAXException {
 
		//System.out.println("Start Element :" + qName);
 
		if (qName.equalsIgnoreCase("KnowledgeTagName")) {
			bfname = true;
		}
 
		if (qName.equalsIgnoreCase("KnowledgeTagDesc")) {
			blname = true;
		}
 
 
	}
 
	public void endElement(String uri, String localName,
		String qName) throws SAXException {
 
		//System.out.println("End Element :" + qName);
 
	}
 
	public void characters(char ch[], int start, int length) throws SAXException {
 
		
		if (bfname  ) {
			//System.out.println("First Name : " + new String(ch, start, length));
			if(!new String(ch, start, length).equalsIgnoreCase("") && new String(ch, start, length).trim().length() > 1){
				firstElement = new String(ch, start, length);
			}
			
			bfname = false;
		}
 
		if (blname) {
			System.out.println("Last Name : " + new String(ch, start, length));
			if(!new String(ch, start, length).equalsIgnoreCase("") && new String(ch, start, length).trim().length() > 1){
			secondElement =  new String(ch, start, length);
			}
			blname = false;
		}
		if(firstElement.trim().length() > 1 && secondElement.trim().length() > 1){
			
			firstElement = firstElement.replaceAll("\n","<br>");
			firstElement = firstElement.replaceAll("\r","<br>");
			firstElement = firstElement.replaceAll("\t","<br>");
			secondElement = secondElement.replaceAll("Non-Break-Space","<br>");
			//////////////////////////////////////////////////
			secondElement = secondElement.replaceAll("\n","<br>");
			secondElement = secondElement.replaceAll("\r","<br>");
			secondElement = secondElement.replaceAll("\t","<br>");
			secondElement = secondElement.replaceAll("Non-Break-Space","<br>");
			/////////////////////////////////////////////////
			System.out.println( firstElement+"-------"+secondElement);
			newHashAMap.put(firstElement, secondElement);
		}
		
		//myMap.put(firstElement, secondElement);
//System.out.println(newHashAMap.size());
	}
 
     };
 
       saxParser.parse("D:/Office Data/WQMS/OSS_Workspace/MastercomKnowledgeBase/WebContent/mastecomWeb/"+fileName+".xml", handler);
 
     } catch (Exception e) {
       e.printStackTrace();
     }
    return newHashAMap;
   }
 
   public static void main(String args[]){
	   ReadXMLFile readXMLFile = new ReadXMLFile();
	   HashMap<String,String> newHashAMap   = readXMLFile.getElements("wqms");
	   for (Map.Entry<String, String> entry : newHashAMap.entrySet()) {
		    String key = entry.getKey();
		    Object value = entry.getValue();
		    System.out.println(key+"------"+value);
		    // ...
		}
   }
}