package com.mastercom.excelWriter;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
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
    workbook.createSheet("Report", 0);
    WritableSheet excelSheet = workbook.getSheet(0);
    createLabel(excelSheet);
    createContent(excelSheet,selectOrderIDList,ginvoiceOrderIds,wqmsOrderIDList,errorLogQueryList);

    workbook.write();
    workbook.close();
  }

  private void createLabel(WritableSheet sheet)
      throws WriteException {
    // Lets create a times font
    WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
    // Define the cell format
    times = new WritableCellFormat(times10pt);
    // Lets automatically wrap the cells
    times.setWrap(true);

    // create create a bold font with unterlines
    WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
        UnderlineStyle.SINGLE);
    timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
    // Lets automatically wrap the cells
    timesBoldUnderline.setWrap(true);

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
		int selectOrderIDCounter = 0;
		System.out.println("length"+selectOrderIDList);
		for(int count = 1 ; count<selectOrderIDList.size();count++){
			System.out.println("Element count "+count +"="+selectOrderIDList.get(count));
			//selectOrderIDCounter++
			 addLabel(sheet,0,count,selectOrderIDList.get(count));
		}
		for(int count = 1 ; count<ginvoiceOrderIds.size();count++){
			System.out.println("Element count "+count +"="+ginvoiceOrderIds.get(count));
			//selectOrderIDCounter++
			 addLabel(sheet,1,count,ginvoiceOrderIds.get(count));
		}
		for(int count = 1 ; count<wqmsOrderIDList.size();count++){
			System.out.println("Element count "+count +"="+wqmsOrderIDList.get(count));
			//selectOrderIDCounter++
			 addLabel(sheet,2,count,wqmsOrderIDList.get(count));
		}
		int errorEntryCounter =0;
		for(Map.Entry<String, String> errorEntry : errorLogQueryList.entrySet()){
          //  System.out.println(errorEntry.getKey() +" :: "+ errorEntry.getValue());
			errorEntryCounter ++;
			 addLabel(sheet,3,errorEntryCounter,errorEntry.getKey() +", Reason :"+ errorEntry.getValue());
		}
//		while (selectOrderIDListList.hasNext()) {
//			selectOrderIDCounter ++;
//			 System.out.println("selectOrderIDCounter"+selectOrderIDCounter);
//			//metasolveReconbw.write(selectOrderIDListList.next()+"\n");
//			 //addLabel(excelSheet, 0, selectOrderIDCounter,selectOrderIDListList.next());
//			 addLabel(sheet,selectOrderIDCounter, 0, "Boring text,hello " + 1);
//			
//		}
     // addLabel(sheet, 0, 1, "Boring text,hello " + 1);
      // Second column
    // addLabel(sheet, 1, 1, "Another text");
    // addLabel(sheet, 2, 1, "Another text");
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
