package qutils;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ibmi.Result;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import qJob.QJob;

public class ResultSetToExcel {

	private XSSFWorkbook workbook = new XSSFWorkbook();
	private XSSFSheet sheet ;
	private String heading = "";
	private int rowCount = -1;
	private final String dirLocation = "downloads";
	public String name = "";
	public String errorMessage = "";
	
	public ResultSetToExcel()
	{
		QJob.getCurrentJob().getIoOperation().createDir(this.dirLocation);
	}
	

	public void loadTable(Result[] catalogResult) {
		for (int i = 0; i <= catalogResult.length; i++) {
			
			loadTable(catalogResult[i]);

		}
	}

	public void loadTable(Result catalogResult) {
		this.heading = catalogResult.getHeading();
		if(this.heading.isEmpty()) this.heading = "Result";
		loadTable(catalogResult.getRs());
		catalogResult.reset();

	}

	public void loadTable(ResultSet rs) {
		if (rs == null)
			return;

		ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
		loadData(rs, data);

		if (data.size() > 0) {
			sheet = workbook.createSheet(this.heading);
			if (writeColumns(rs)) {
					writeData(data);
					writeExcel();
			}
		}

	}

	private boolean loadData(ResultSet rs, ObservableList<ObservableList<String>> data) {
		boolean output = true;

		try {

			while (rs.next()) {
				ObservableList<String> row = FXCollections.observableArrayList();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					row.add((rs.getString(i) == null ? "" : rs.getString(i)));
				}
	
				data.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
			 
			this.errorMessage = e.getMessage();
			output = false;
		}

		return output;
	}

	private boolean writeColumns(ResultSet rs) {
		boolean output = true;
		Row row = sheet.createRow(++rowCount);
		try {
			int columnCount = -1;
			int i = 0;
			for (i = 0; i < rs.getMetaData().getColumnCount(); i++) {
				//System.out.println(rs.getMetaData().getColumnTypeName(i + 1));
				Cell cell = row.createCell(++columnCount);
				cell.setCellValue(rs.getMetaData().getColumnName(i + 1));
			}
			output = (i == 0 ? false : true);

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			this.errorMessage = sqle.getMessage();
			output = false;
		}
		return output;
	}
	
	private boolean writeData(ObservableList<ObservableList<String>> data) {
		boolean output = true;
		for(ObservableList<String> dataRow : data )
		{
			Row row = sheet.createRow(++rowCount);
			int columnCount = -1;
			for(String column : dataRow)
			{
				Cell cell = row.createCell(++columnCount);
				cell.setCellValue(column);
			}
		}
 

		return output;
	}
	
	private boolean writeExcel()
	{
		boolean output = true;
		  String tempName =this.dirLocation+File.separator+ new Date().toString()+".xlsx";
		
		 // String tempName =  new Date().toString()+".xlsx";
			tempName = tempName.replaceAll(" ", "_").replaceAll(":", "_");
		  
	       try (FileOutputStream outputStream = new FileOutputStream(tempName)) {
	            workbook.write(outputStream);
	            this.name = tempName;
	        }
	       catch(Exception e)
	       {
				e.printStackTrace();
				this.errorMessage = e.getMessage();
				output = false;
	       }
	       return output;
	}

}
