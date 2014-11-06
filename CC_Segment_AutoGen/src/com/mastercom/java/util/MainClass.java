package com.mastercom.java.util;

import java.io.File;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class MainClass implements ErrorHandler {
  public static void main(String args[]) throws Exception {
    DocumentBuilderFactory builderFactory = DocumentBuilderFactory
        .newInstance();
    builderFactory.setNamespaceAware(true);
    builderFactory.setValidating(true);
    builderFactory.setIgnoringElementContentWhitespace(true);

    DocumentBuilder builder = null;
    builder = builderFactory.newDocumentBuilder();
    builder.setErrorHandler(new MainClass());
    Document xmlDoc = builder.parse(new File("D:/Office Data/WQMS/OSS_Workspace/CC_Segment_AutoGen/src/SapSfdcSegmentParse.xml"));
    DocumentType doctype = xmlDoc.getDoctype();
    System.out.println("DOCTYPE node:\n" + doctype);
    listNodes(xmlDoc, "");
  }

  static void listNodes(Document node, String indent) {
	  NodeList nList = node.getElementsByTagName("SfdcSegment");
	    for (int i = 0; i < nList.getLength(); i++) {
	        Node nNode = nList.item(i);
	       NamedNodeMap attributes =  nNode.getAttributes();
            Node attrMain = attributes.getNamedItem("description");
            if(attrMain != null) {
                 if(attrMain.getNodeValue().trim().equalsIgnoreCase("CSO")){
                	 System.out.println("Main Attibute ----"+attrMain.getNodeValue());
	        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	            Element eElement = (Element) nNode;
	            NodeList fieldNodes = eElement.getElementsByTagName("SfdcServiceManagementCategory");
	            for(int j = 0; j < fieldNodes.getLength(); j++) {
	                Node fieldNode = fieldNodes.item(j);
	                NamedNodeMap attributes_1 = fieldNode.getAttributes();
	                Node attr = attributes_1.getNamedItem("description");
	                if(attr != null) {
	                    if(attr.getNodeValue().trim().equalsIgnoreCase("(blank)")){
	                    	System.out.println("----"+attr.getNodeValue());
	                    	System.out.println("-----"+fieldNode.getTextContent());
	                    }
	                }
	            }
	        }
	    }
	    }
	    }
  }

  public void fatalError(SAXParseException spe) throws SAXException {
    System.out.println("Fatal error at line " + spe.getLineNumber());
    System.out.println(spe.getMessage());
    throw spe;
  }

  public void warning(SAXParseException spe) {
    System.out.println("Warning at line " + spe.getLineNumber());
    System.out.println(spe.getMessage());
  }

  public void error(SAXParseException spe) {
    System.out.println("Error at line " + spe.getLineNumber());
    System.out.println(spe.getMessage());
  }
}
