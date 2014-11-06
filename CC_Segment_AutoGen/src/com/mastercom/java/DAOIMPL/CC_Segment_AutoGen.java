/**
 * 
 */
package com.mastercom.java.DAOIMPL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.apache.log4j.Logger;

import com.mastercom.java.DAO.SapSfdcIntegrationDAO;
import com.mastercom.java.util.Timer;
import com.mastercom.sql.connection.HardcodedValues;
import com.mastercom.sql.connection.SQLManager;

/**
 * @author Mastecom139
 *
 */

public class CC_Segment_AutoGen implements Serializable {
	
	private static Logger logger = Logger.getRootLogger();
	
	public HashMap<Boolean,ArrayList<SapSfdcIntegrationDAO>> setSapSfdcIntegrationBackUp() throws IOException{
		Connection db2Connection =null;
		HashMap<Boolean,ArrayList<SapSfdcIntegrationDAO>> sfdcMethodResult = new HashMap<Boolean, ArrayList<SapSfdcIntegrationDAO>>();
		
		try {
			File file = new File(HardcodedValues.scriptupload+"/"+HardcodedValues.scriptuploadFilename+".csv");
			logger.info("After Change");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
				logger.info("New file Created :"+HardcodedValues.scriptupload);
			}else{
				logger.error(HardcodedValues.scriptupload+"File is already available , can't be created ,so overwriting the data");
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			SQLManager sm = new SQLManager();
			db2Connection = sm.getConnection();
			System.out.println("conection "+db2Connection);
			String sapSfdcIntegrationquery = "select * from BPMLDAP.CES_SAP_SFDC_INTEGRATION";
			PreparedStatement sapSfdcPst = db2Connection.prepareStatement(sapSfdcIntegrationquery);
			ResultSet sapSfdcPstResultSet = sapSfdcPst.executeQuery();
			System.out.println("select * from BPMLDAP.CES_SAP_SFDC_INTEGRATION");
			ArrayList<SapSfdcIntegrationDAO> sapSfdcPstResultSetArrayList =  new ArrayList<SapSfdcIntegrationDAO>();
			bw.write("SAP_CODE,CUSTOMER_NAME,GROUP_NAME,SFDC_SEGMENT,REGION,COLLECTION_SEGMENT,CC_SEGMENT,STATUS,CAM_L3,CAM_L2,CAM_L1,RECOVERY_LEAD,NAM,ASM,RM,NATIONAL_SALES_HEAD,ACTIVE_STATUS,CIRCUIT_STATUS,CUSTOMER_PRIORITY,CUID,CREATION_DATE,DUNNING_LEVEL,CITY,STATE,ADDRESS,PIN_CODE,LAST_UPDATED_DATE,IS_SFDC_SAP_CODE,FLAG");
			bw.newLine();
			while (sapSfdcPstResultSet.next()){
				SapSfdcIntegrationDAO sapSfdcIntegrationDAO = new SapSfdcIntegrationDAO();
				String SAP_CODE = sapSfdcPstResultSet.getString("SAP_CODE");
				sapSfdcIntegrationDAO.setSap_code(SAP_CODE);
				bw.write(SAP_CODE+",");
				sapSfdcIntegrationDAO.setCustomer_name(sapSfdcPstResultSet.getString("CUSTOMER_NAME"));
				bw.write(sapSfdcPstResultSet.getString("CUSTOMER_NAME")+",");
				sapSfdcIntegrationDAO.setGroup_name(sapSfdcPstResultSet.getString("GROUP_NAME"));
				bw.write(sapSfdcPstResultSet.getString("GROUP_NAME").replaceAll(",", "")+",");
				sapSfdcIntegrationDAO.setSfdc_segment(sapSfdcPstResultSet.getString("SFDC_SEGMENT"));/////
				bw.write(sapSfdcPstResultSet.getString("SFDC_SEGMENT")+",");
				sapSfdcIntegrationDAO.setRegion(sapSfdcPstResultSet.getString("REGION"));
				bw.write(sapSfdcPstResultSet.getString("REGION")+",");
				sapSfdcIntegrationDAO.setCollection_segment(sapSfdcPstResultSet.getString("COLLECTION_SEGMENT"));///////
				bw.write(sapSfdcPstResultSet.getString("COLLECTION_SEGMENT")+",");
				sapSfdcIntegrationDAO.setCc_segment(sapSfdcPstResultSet.getString("CC_SEGMENT"));
				bw.write(sapSfdcPstResultSet.getString("CC_SEGMENT")+",");
				sapSfdcIntegrationDAO.setStatus(sapSfdcPstResultSet.getString("STATUS"));
				bw.write(sapSfdcPstResultSet.getString("STATUS")+",");
				sapSfdcIntegrationDAO.setCam_l3(sapSfdcPstResultSet.getString("CAM_L3"));
				bw.write(sapSfdcPstResultSet.getString("CAM_L3")+",");
				sapSfdcIntegrationDAO.setCam_l2(sapSfdcPstResultSet.getString("CAM_L2"));
				bw.write(sapSfdcPstResultSet.getString("CAM_L2")+",");
				sapSfdcIntegrationDAO.setCam_l1(sapSfdcPstResultSet.getString("CAM_L1"));
				bw.write(sapSfdcPstResultSet.getString("CAM_L1")+",");
				sapSfdcIntegrationDAO.setRecovey_lead(sapSfdcPstResultSet.getString("RECOVERY_LEAD"));
				bw.write(sapSfdcPstResultSet.getString("RECOVERY_LEAD")+",");
				sapSfdcIntegrationDAO.setNam(sapSfdcPstResultSet.getString("NAM"));
				bw.write(sapSfdcPstResultSet.getString("NAM")+",");
				sapSfdcIntegrationDAO.setAsm(sapSfdcPstResultSet.getString("ASM"));
				bw.write(sapSfdcPstResultSet.getString("ASM")+",");
				sapSfdcIntegrationDAO.setRm(sapSfdcPstResultSet.getString("RM"));
				bw.write(sapSfdcPstResultSet.getString("RM")+",");
				sapSfdcIntegrationDAO.setNation_sales_head(sapSfdcPstResultSet.getString("NATIONAL_SALES_HEAD"));
				bw.write(sapSfdcPstResultSet.getString("NATIONAL_SALES_HEAD")+",");
				sapSfdcIntegrationDAO.setActive_status(sapSfdcPstResultSet.getString("ACTIVE_STATUS"));
				bw.write(sapSfdcPstResultSet.getString("ACTIVE_STATUS")+",");
				sapSfdcIntegrationDAO.setCircuit_status(sapSfdcPstResultSet.getString("CIRCUIT_STATUS"));
				bw.write(sapSfdcPstResultSet.getString("CIRCUIT_STATUS")+",");
				sapSfdcIntegrationDAO.setCustomer_priority(sapSfdcPstResultSet.getString("CUSTOMER_PRIORITY"));
				bw.write(sapSfdcPstResultSet.getString("CUSTOMER_PRIORITY")+",");
				sapSfdcIntegrationDAO.setCuid(sapSfdcPstResultSet.getString("CUID")+",");
				bw.write(sapSfdcPstResultSet.getString("CUID")+",");
				sapSfdcIntegrationDAO.setCreation_date(sapSfdcPstResultSet.getString("CREATION_DATE"));
				bw.write(sapSfdcPstResultSet.getString("CREATION_DATE")+",");
				sapSfdcIntegrationDAO.setDunning_level(sapSfdcPstResultSet.getString("DUNNING_LEVEL"));
				bw.write(sapSfdcPstResultSet.getString("DUNNING_LEVEL")+",");
				sapSfdcIntegrationDAO.setCity(sapSfdcPstResultSet.getString("CITY"));
				bw.write(sapSfdcPstResultSet.getString("CITY")+",");
				sapSfdcIntegrationDAO.setState(sapSfdcPstResultSet.getString("STATE"));
				bw.write(sapSfdcPstResultSet.getString("STATE")+",");
				sapSfdcIntegrationDAO.setAddress(sapSfdcPstResultSet.getString("ADDRESS"));
				bw.write(sapSfdcPstResultSet.getString("ADDRESS")+",");
				sapSfdcIntegrationDAO.setPin_code(sapSfdcPstResultSet.getString("PIN_CODE"));
				bw.write(sapSfdcPstResultSet.getString("PIN_CODE")+",");
				sapSfdcIntegrationDAO.setLast_updated_date(sapSfdcPstResultSet.getString("LAST_UPDATED_DATE"));
				bw.write(sapSfdcPstResultSet.getString("LAST_UPDATED_DATE")+",");
				sapSfdcIntegrationDAO.setIs_sfdc(sapSfdcPstResultSet.getString("IS_SFDC_SAP_CODE"));
				bw.write(sapSfdcPstResultSet.getString("IS_SFDC_SAP_CODE")+",");
				sapSfdcIntegrationDAO.setFlag(sapSfdcPstResultSet.getString("FLAG"));
				bw.write(sapSfdcPstResultSet.getString("FLAG"));
				sapSfdcPstResultSetArrayList.add(sapSfdcIntegrationDAO);
		    	bw.newLine();
			}
			bw.close();
			sfdcMethodResult.put(true, sapSfdcPstResultSetArrayList);
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				db2Connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return sfdcMethodResult;
	}
	
	public ArrayList<SapSfdcIntegrationDAO> getFinalCCSegment(HashMap<Boolean,ArrayList<SapSfdcIntegrationDAO>> hashMapValue){
		ArrayList<SapSfdcIntegrationDAO> getFinalCCSegmentResult = new ArrayList<SapSfdcIntegrationDAO>();
		
		try {
			 
			File file = new File("D:/Office Data/WQMS/OSS_Workspace/CC_Segment_AutoGen/src/SapSfdcSegmentParse.xml");
		 
			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
		                             .newDocumentBuilder();
		 
			Document doc = dBuilder.parse(file);
		 
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			
			for(Map.Entry<Boolean,ArrayList<SapSfdcIntegrationDAO>> entry : hashMapValue.entrySet()){
				//System.out.println("Key"+entry.getKey());
				//System.out.println("Key"+entry.getValue());
				getFinalCCSegmentResult = (ArrayList<SapSfdcIntegrationDAO>)entry.getValue();
				System.out.println("Size Of Collection ="+getFinalCCSegmentResult.size());
				for(int g= 0 ; g< getFinalCCSegmentResult.size();g++)
				{
					//System.out.println(getFinalCCSegmentResult.get(g));
					SapSfdcIntegrationDAO sapSfdcDetailsList = getFinalCCSegmentResult.get(g);
					
					//sapSfdcDetailsList.getSfdc_segment();
					//sapSfdcDetailsList.getCollection_segment();
					System.out.println("1"+sapSfdcDetailsList.getCollection_segment());
					System.out.println("2"+sapSfdcDetailsList.getCustomer_priority());
					
				}
			}
			getFinalCCSegmentFromXML(doc,"");
		 
		 
		    } catch (Exception e) {
			System.out.println(e.getMessage());
		    }
		
		return getFinalCCSegmentResult;
		
	}
	
	public String getFinalCCSegmentFromXML(Document node, String indent) {
		  NodeList nList = node.getElementsByTagName("SfdcSegment");
		    for (int i = 0; i < nList.getLength(); i++) {
		        Node nNode = nList.item(i);
		       NamedNodeMap attributes =  nNode.getAttributes();
	            Node attrMain = attributes.getNamedItem("description");
	            if(attrMain != null) {
	                 if(attrMain.getNodeValue().trim().equalsIgnoreCase("CSO")){
	                	 //System.out.println("Main Attibute ----"+attrMain.getNodeValue());
		        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		            Element eElement = (Element) nNode;
		            NodeList fieldNodes = eElement.getElementsByTagName("SfdcServiceManagementCategory");
		            for(int j = 0; j < fieldNodes.getLength(); j++) {
		                Node fieldNode = fieldNodes.item(j);
		                NamedNodeMap attributes_1 = fieldNode.getAttributes();
		                Node attr = attributes_1.getNamedItem("description");
		                if(attr != null) {
		                    if(attr.getNodeValue().trim().equalsIgnoreCase("(blank)")){
		                    	//System.out.println("----"+attr.getNodeValue());
		                    	//System.out.println("-----"+fieldNode.getTextContent());
		                    }
		                }
		            }
		        }
		    }
		    }
		    }
		    return "";
	  }
	
	public static void main(String args[]) throws IOException{
		CC_Segment_AutoGen cc_segment = new CC_Segment_AutoGen();
		Timer timer = new Timer();

		timer.start();

		HashMap<Boolean,ArrayList<SapSfdcIntegrationDAO>> sfdcMethodResult  = cc_segment.setSapSfdcIntegrationBackUp();
		cc_segment.getFinalCCSegment(sfdcMethodResult);
		timer.end();

//		String totalTime = timer.getTotalTime();
//		System.out.println("totalTime"+totalTime);
//		for(Map.Entry<Boolean,ArrayList<SapSfdcIntegrationDAO>> entry : sfdcMethodResult.entrySet()){
//			System.out.println("Key"+entry.getKey());
//			System.out.println("Key"+entry.getValue());
//		}
	}
	
}
