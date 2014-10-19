package com.mastercom.excelWriter;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.Pattern;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;


public class WriteExcel {

  private WritableCellFormat timesBoldUnderline;
  private WritableCellFormat times;
  private String inputFile;
  private static org.apache.log4j.Logger logger = Logger.getLogger("com.second");
  
  public WriteExcel(){
	  
  }
  
  
public void setOutputFile(String inputFile) {
  this.inputFile = inputFile;
  }

  public void write(ArrayList<String> selectOrderIDList ,ArrayList<String> ginvoiceOrderIds,ArrayList<String> wqmsOrderIDList ,HashMap<String,String> errorLogQueryList) throws IOException, WriteException {
    File file = new File(inputFile);
    WorkbookSettings wbSettings = new WorkbookSettings();

    wbSettings.setLocale(new Locale("en", "EN"));

    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
    workbook.createSheet("Metasolve Count", 0);
    WritableSheet excelFirstSheet = workbook.getSheet(0);
    createLabelFirstrow(excelFirstSheet);
    createFirstContent(excelFirstSheet,selectOrderIDList,ginvoiceOrderIds,wqmsOrderIDList,errorLogQueryList);
    workbook.createSheet("Detail Report", 1);
    WritableSheet excelSheet = workbook.getSheet(1);
    createLabel(excelSheet);
    createContent(excelSheet,selectOrderIDList,ginvoiceOrderIds,wqmsOrderIDList,errorLogQueryList);
    logger.info("Added 2 Sheets in Excel Sheet ");
    workbook.write();
    workbook.close();
  }
  private void createLabelFirstrow(WritableSheet sheet)
	      throws WriteException {
	    // Lets create a times font
	    WritableFont times12pt = new WritableFont(WritableFont.TIMES, 12);
	    // Define the cell format
	    times = new WritableCellFormat(times12pt);
	    // Lets automatically wrap the cells
	    times.setWrap(true);

	    // create create a bold font with unterlines
	    WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, false,
	        UnderlineStyle.NO_UNDERLINE);
	    timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
	    // Lets automatically wrap the cells
	  //  timesBoldUnderline.setWrap(true);
	    timesBoldUnderline.setBackground(Colour.YELLOW, Pattern.SOLID);
	    CellView cv = new CellView();
	    cv.setFormat(times);
	    cv.setFormat(timesBoldUnderline);
	    cv.setAutosize(true);
	   // cv.setFormat(CellFormat)
	    // Write a few headers
	    addCaption(sheet, 6, 7, "Metasolve Total Order ID Status");
	   // addCaption(sheet, 7, 7, "               ");
	    sheet.mergeCells(6, 7, 7, 7);
	    addCaption(sheet, 6, 8, "Total Order ID");
	    addCaption(sheet, 6, 9, "Integration Order ID");
	    addCaption(sheet, 6, 10, "WQMS Order ID");
		 addCaption(sheet, 6, 11, "Error Order ID");

	  }
  
  private void createFirstContent(WritableSheet sheet,ArrayList<String> selectOrderIDList,ArrayList<String> ginvoiceOrderIds,ArrayList<String> wqmsOrderIDList ,HashMap<String,String> errorLogQueryList) throws WriteException,
  RowsExceededException {

		 addLabel(sheet,7,8,""+selectOrderIDList.size());
		 addLabel(sheet,7,9,""+ginvoiceOrderIds.size());
		 addLabel(sheet,7,10,""+wqmsOrderIDList.size());
		 addLabel(sheet,7,11,""+errorLogQueryList.size());
	}

  private void createLabel(WritableSheet sheet)
      throws WriteException {
    // Lets create a times font
    WritableFont times10pt = new WritableFont(WritableFont.TIMES, 11);
    // Define the cell format
    times = new WritableCellFormat(times10pt);
    // Lets automatically wrap the cells
    //times.setWrap(true);

    // create create a bold font with unterlines
    WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 11, WritableFont.BOLD, false,
        UnderlineStyle.SINGLE);
    timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
    // Lets automatically wrap the cells
   // timesBoldUnderline.setWrap(true);
    timesBoldUnderline.setBackground(Colour.YELLOW, Pattern.NONE);
    CellView cv = new CellView();
    cv.setFormat(times);
    cv.setFormat(timesBoldUnderline);
    cv.setAutosize(true);

    // Write a few headers
    addCaption(sheet, 0, 0, "Total Order ID");
    addCaption(sheet, 1, 0, "Integration Order ID");
    addCaption(sheet, 2, 0, "WQMS Order ID");
	 addCaption(sheet, 3, 0, "Error Order ID");

  }
 
  private void createContent(WritableSheet sheet,ArrayList<String> selectOrderIDList,ArrayList<String> ginvoiceOrderIds,ArrayList<String> wqmsOrderIDList ,HashMap<String,String> errorLogQueryList) throws WriteException,
      RowsExceededException {

      // First column
//	  Iterator<String> selectOrderIDListList = selectOrderIDList.listIterator();
		for(int count = 1 ; count<selectOrderIDList.size();count++){
			//System.out.println("Element count "+count +"="+selectOrderIDList.get(count));
			//selectOrderIDCounter++
			 addLabel(sheet,0,count,selectOrderIDList.get(count));
		}
		for(int count = 1 ; count<ginvoiceOrderIds.size();count++){
			//System.out.println("Element count "+count +"="+ginvoiceOrderIds.get(count));
			//selectOrderIDCounter++
			 addLabel(sheet,1,count,ginvoiceOrderIds.get(count));
		}
		for(int count = 1 ; count<wqmsOrderIDList.size();count++){
			//System.out.println("Element count "+count +"="+wqmsOrderIDList.get(count));
			//selectOrderIDCounter++
			 addLabel(sheet,2,count,wqmsOrderIDList.get(count));
		}
		int errorEntryCounter =0;
		for(Map.Entry<String, String> errorEntry : errorLogQueryList.entrySet()){
          //  System.out.println(errorEntry.getKey() +" :: "+ errorEntry.getValue());
			errorEntryCounter ++;
			 addLabel(sheet,3,errorEntryCounter,errorEntry.getKey() +", Reason :"+ errorEntry.getValue());
		}

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

  public static void main(String[] args) throws WriteException, IOException {
    WriteExcel test = new WriteExcel();
    test.setOutputFile("D:/ORDERFILES/lars.xls");
    test.write(null,null,null,null);
    System.out.println("Please check the result file under c:/temp/lars.xls ");
  }
}
