package com.mastercom.viznet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class ViznetOrderProcessJAR {

	private static Logger logger = Logger.getRootLogger();
	
//	private String livePath = "D:/ORDERFILES/Reconcilation/UNCHANGED/";
//	private String newPathFilename = "D:/ORDERFILES/Reconcilation/CHANGED/";
//	private String newPathFilenameReport = "D:/ORDERFILES/Reconcilation/CHANGED/";
//	
	private String livePath = "/IBM/apps/wqms/Viznet/ORDERFILES/";
	private String newPathFilename = "/IBM/apps/wqms/Viznet/EventDir/";
	private String newPathFilenameReport = "/IBM/apps/wqms/Viznet/ORDERFILES/";
	
	public void accessFilesFromLocal() throws InterruptedException {
//		try {
//			Thread.sleep(600000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			logger.error(e.getMessage());
//		}
		logger.info("Started Method of Accessing files  : In Method accessFilesFromLocal() :");
		String files;
		File folder = new File(livePath);
		File[] listOfFiles = folder.listFiles();
		Calendar cal = Calendar.getInstance(); 
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		Date date = new Date();
		String time = sdf.format(date);
		logger.info("In Folder: "+livePath+" Searching for files ");
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				files = listOfFiles[i].getName();
				//if(files.startsWith("CHANGE_ORDER_ENQUIRIES_COMPLETED") || files.startsWith("CHANGE_ORDER_ENQUIRIES_ENTERED")){
					if(files.endsWith(time.replaceAll("-","_")+".csv")){
						logger.info("File Name : "+files+"");
						readFileByLine(livePath + files, files,time);
					}
					//else{
						//logger.info("No files found for Date :"+time+"");
					//}
				//}
			}
			//System.out.println("************************************************************************");
		}
		///////////////////////////////////////////
	}
	
	public void readFileByLine(String csvFileToRead,String onlyFilename,String time) {
		logger.info("Got File so , in Method readFileByLine()");
		BufferedReader br = null;
		String line = "";
		try {
			logger.info("Starting File reading for file :"+csvFileToRead+"");
			br = new BufferedReader(new FileReader(csvFileToRead));
			String []writeMap = new String[5000];
			HashMap<Integer,Integer> modifyCount = new HashMap<Integer, Integer>();
			int countArray[] = new int[5000];
			int lineCounter = 0;
			int srnoCount = 0;
			logger.info("Before Modification");
			while ((line = br.readLine()) != null) {
				lineCounter ++;
				int commaCountbefore = 0;
				//System.out.println(line);
				logger.info("Line Number "+lineCounter+" = "+line.trim()+"");
				int numberOfCommas = 0;
				if(line.trim().contains("SR. No")){
					 srnoCount ++;
					 numberOfCommas = line.replaceAll("[^,]","").length(); 
					 if(srnoCount == 1){
						 if(numberOfCommas > 0){
							 logger.info("Line 1 = "+numberOfCommas+"");
							 modifyCount.put(1, numberOfCommas);	 
						 }
					 }
					 writeMap[lineCounter] = line.trim();
				} else {
					if(line.trim().contains(",")){
						 line =  line.replaceAll("\\.00","");
					}	
					writeMap[lineCounter] = line.trim();
				}
				
				 //writeMap[lineCounter] = line.trim();
				countArray[lineCounter] = numberOfCommas;
				commaCountbefore = lineCounter -1 ;
				if(lineCounter > 1){
					if(numberOfCommas > 0){
						logger.info("Noarmal row :Required "+commaCountbefore+" = "+numberOfCommas+"");
						modifyCount.put(commaCountbefore, numberOfCommas);
					}
				}
				if(lineCounter > 1){
					if(line.trim().contains("NO RECORDS") || line.trim().contains("EMPTY")){
						if(countArray[commaCountbefore] > 0){
							logger.info("Row with No records&EMPLY :Required "+lineCounter+" = "+countArray[commaCountbefore]+"");
							modifyCount.put(lineCounter, countArray[commaCountbefore]);
							
						}
					}
				}
			}
			
			for (Map.Entry<Integer, Integer> entry : modifyCount.entrySet()) {
								if(entry.getValue() > 0){
					int key = entry.getKey();
				    int value = entry.getValue();
				    String commaVal = "";
				    
				    for (int writemapCounter = 0 ;writemapCounter<value;writemapCounter++){
				    	commaVal += ",";
			    	}
				    writeMap[key] = writeMap[key] + commaVal;
				}
			}
			
			int commaCountTobeCompared = 0 ;
			
			if(onlyFilename.startsWith("ALL_CEASED_CIRCUITS")){
				commaCountTobeCompared = 21;
			} else if(onlyFilename.startsWith("ALL_COMMISSIONED_CIRCUITS")){
				commaCountTobeCompared = 47 ;
			} else if(onlyFilename.startsWith("ALL_TERMINATED_CIRCUITS")){
				commaCountTobeCompared = 29;
			} else if(onlyFilename.startsWith("ALL_UNCEASED_CIRCUITS")){
				commaCountTobeCompared = 21;
			} else if(onlyFilename.startsWith("CHANGE_ORDER_ENQUIRIES_COMPLETED") || onlyFilename.startsWith("CHANGE_ORDER_ENQUIRIES_ENTERED")){
				commaCountTobeCompared = 46;
			} else if(onlyFilename.startsWith("CIRCUIT_ENTERED_TODAY")){
				commaCountTobeCompared = 56;
			} 
			
			
			for (int finalappenderCount = 1 ; finalappenderCount <=lineCounter ; finalappenderCount++){
				int finallynumberOfCommas = 0 ;
				finallynumberOfCommas = writeMap[finalappenderCount].replaceAll("[^,]","").length();
				//System.out.println(finallynumberOfCommas + "---"+writeMap[finalappenderCount]);
				logger.info("Line Number : "+finalappenderCount+" : finallynumberOfCommas:"+finallynumberOfCommas);
			    if(finallynumberOfCommas < commaCountTobeCompared){
			    	String lastcomma = "";
			    	int difference = commaCountTobeCompared - finallynumberOfCommas ;
			    	if (difference > 0){
			    		for( int writeMapCounter = 0;writeMapCounter< difference ;writeMapCounter++){
			    			lastcomma += ",";
			    		}
			    		writeMap[finalappenderCount] = writeMap[finalappenderCount] + lastcomma;
			    	}
			    }
			}
			
			File file = new File(newPathFilename+onlyFilename);
			logger.info("After Change");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
				logger.info("New file Created :"+newPathFilename+onlyFilename);
			}else{
				logger.error(newPathFilename+onlyFilename+"File is already available , can't be created ,so overwriting the data");
			}
 
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			for(int finalCounter = 1;finalCounter<=lineCounter;finalCounter++){
				logger.info("Line Number "+finalCounter+" = "+writeMap[finalCounter]);
		    	bw.write(writeMap[finalCounter]);
		    	if (finalCounter != lineCounter){
		    	bw.newLine();
		    	}
		    }
			bw.close();
			File fileFolder = new File(newPathFilenameReport+time);
			if (!fileFolder.exists()) {
				if (fileFolder.mkdir()) {
					System.out.println("Directory is created with Date : "+time);
				} else {
					System.out.println("Failed to create directory with Date :"+time);
				}
			}
		 
			File fnew = new File(newPathFilenameReport+time+"/"+onlyFilename);
			copyFile(file,fnew);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					 logger.error(e.getMessage());
				}
			}
		}
	}
	
	public static void copyFile(File sourceFile, File destFile) throws IOException {

		  if (!destFile.exists()) {
		    destFile.createNewFile();
		  }
		  FileInputStream fIn = null;
		  FileOutputStream fOut = null;
		  FileChannel source = null;
		  FileChannel destination = null;
		  try {
		    fIn = new FileInputStream(sourceFile);
		    source = fIn.getChannel();
		    fOut = new FileOutputStream(destFile);
		    destination = fOut.getChannel();
		    long transfered = 0;
		    long bytes = source.size();
		    while (transfered < bytes) {
		      transfered += destination.transferFrom(source, 0, source.size());
		      destination.position(transfered);
		    }
		  } finally {
		    if (source != null) {
		      source.close();
		    } else if (fIn != null) {
		      fIn.close();
		    }
		    if (destination != null) {
		      destination.close();
		    } else if (fOut != null) {
		      fOut.close();
		    }
		  }
		
	}
	
	public static void main(String args[]) throws InterruptedException {
		ViznetOrderProcessJAR viznetTest = new ViznetOrderProcessJAR();
		logger.info("Jar File Started");
		viznetTest.accessFilesFromLocal();
	}
}
