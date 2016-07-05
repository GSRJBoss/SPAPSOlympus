package bo.com.spaps.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelGenerateReport {

	private CellStyle cs = null;
	private CellStyle csBold = null;
	private CellStyle csTop = null;
	private CellStyle csRight = null;
	private CellStyle csBottom = null;
	private CellStyle csLeft = null;
	private CellStyle csTopLeft = null;
	private CellStyle csTopRight = null;
	private CellStyle csBottomLeft = null;
	private CellStyle csBottomRight = null;
	Logger log = Logger.getLogger(ExcelGenerateReport.class);

	public static void main(String[] args) {

		ExcelGenerateReport myReport = new ExcelGenerateReport();
		myReport.createExcel();
	}

	public void createExcel() {
		try {
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet("My Excel Report");
			/*
			 * wb.setPrintArea( 0, //sheet index 0, //start column 5, //end
			 * column 0, //start row 5 //end row );
			 */

			sheet.getPrintSetup().setLandscape(true);
			sheet.getPrintSetup().setPaperSize(
					XSSFPrintSetup.LEGAL_PAPERSIZE);
			sheet.setDisplayGridlines(true);

			// Setup some styles that we need for the Cells
			setCellStyles(wb);

			// Get current Date and Time
			Date date = new Date(System.currentTimeMillis());
			DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");

			// Set Column Widths
			sheet.setColumnWidth(0, 1000);
			sheet.setColumnWidth(1, 2500);
			sheet.setColumnWidth(2, 2500);
			sheet.setColumnWidth(3, 2500);
			sheet.setColumnWidth(4, 2500);
			sheet.setColumnWidth(5, 4000);
			sheet.setColumnWidth(6, 4000);
			sheet.setColumnWidth(7, 2000);
			sheet.setColumnWidth(8, 2000);
			sheet.setColumnWidth(9, 2000);
			sheet.setColumnWidth(10, 2000);
			sheet.setColumnWidth(11, 2000);
			sheet.setColumnWidth(12, 2000);
			sheet.setColumnWidth(13, 2000);
			sheet.setColumnWidth(14, 2000);
			sheet.setColumnWidth(15, 2000);

			// Set Header Information
			Header header = sheet.getHeader();
			header.setLeft("*** ORIGINAL COPY ***");
			header.setCenter(HSSFHeader.font("Arial", "Bold")
					+ HSSFHeader.fontSize((short) 14) + "LIBRO DE VENTA");
			header.setRight(df.format(date));

			// Set Footer Information with Page Numbers
			Footer footer = sheet.getFooter();
			footer.setRight("Page " + HeaderFooter.page() + " of "
					+ HeaderFooter.numPages());

			int rowIndex = 0;
			rowIndex = insertHeaderInfo(sheet, rowIndex);
			rowIndex = insertDetailInfo(sheet, rowIndex);
			/*
			 * rowIndex = 47 * 1; rowIndex = insertHeaderInfo(sheet, rowIndex);
			 */
			/*rowIndex = insertDetailInfo(sheet, rowIndex);*/

			/*
			 * rowIndex = 47 * 2; rowIndex = insertHeaderInfo(sheet, rowIndex);
			 */
			/*rowIndex = insertDetailInfo(sheet, rowIndex);*/

			// Write the Excel file
			FileOutputStream fileOut = null;
			File folder = new File("archivo");
			if (!folder.exists()) {
				folder.mkdirs();
				System.out.println(folder.getAbsolutePath());
			}
			fileOut = new FileOutputStream("archivo/myReport.xlsx");
			wb.write(fileOut);
			fileOut.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void setCellStyles(XSSFWorkbook  wb) {

		// font size 10
		Font f = wb.createFont();
		f.setFontHeightInPoints((short)7);

		// Simple style
		cs = wb.createCellStyle();
		cs.setFont(f);

		// Bold Fond
		Font bold = wb.createFont();
		bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		bold.setFontHeightInPoints((short) 7);

		// Bold style
		csBold = wb.createCellStyle();
		csBold.setBorderBottom(CellStyle.BORDER_THIN);
		csBold.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		csBold.setFont(bold);

		// Setup style for Top Border Line
		csTop = wb.createCellStyle();
		csTop.setBorderTop(CellStyle.BORDER_THIN);
		csTop.setTopBorderColor(IndexedColors.BLACK.getIndex());
		csTop.setFont(f);

		// Setup style for Right Border Line
		csRight = wb.createCellStyle();
		csRight.setBorderRight(CellStyle.BORDER_THIN);
		csRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
		csRight.setFont(f);

		// Setup style for Bottom Border Line
		csBottom = wb.createCellStyle();
		csBottom.setBorderBottom(CellStyle.BORDER_THIN);
		csBottom.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		csBottom.setFont(f);

		// Setup style for Left Border Line
		csLeft = wb.createCellStyle();
		csLeft.setBorderLeft(CellStyle.BORDER_THIN);
		csLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		csLeft.setFont(f);

		// Setup style for Top/Left corner cell Border Lines
		csTopLeft = wb.createCellStyle();
		csTopLeft.setBorderTop(CellStyle.BORDER_THIN);
		csTopLeft.setTopBorderColor(IndexedColors.BLACK.getIndex());
		csTopLeft.setBorderLeft(CellStyle.BORDER_THIN);
		csTopLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		csTopLeft.setFont(f);

		// Setup style for Top/Right corner cell Border Lines
		csTopRight = wb.createCellStyle();
		csTopRight.setBorderTop(CellStyle.BORDER_THIN);
		csTopRight.setTopBorderColor(IndexedColors.BLACK.getIndex());
		csTopRight.setBorderRight(CellStyle.BORDER_THIN);
		csTopRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
		csTopRight.setFont(f);

		// Setup style for Bottom/Left corner cell Border Lines
		csBottomLeft = wb.createCellStyle();
		csBottomLeft.setBorderBottom(CellStyle.BORDER_THIN);
		csBottomLeft.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		csBottomLeft.setBorderLeft(CellStyle.BORDER_THIN);
		csBottomLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		csBottomLeft.setFont(f);

		// Setup style for Bottom/Right corner cell Border Lines
		csBottomRight = wb.createCellStyle();
		csBottomRight.setBorderBottom(CellStyle.BORDER_THIN);
		csBottomRight.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		csBottomRight.setBorderRight(CellStyle.BORDER_THIN);
		csBottomRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
		csBottomRight.setFont(f);
	}

	private int insertHeaderInfo(XSSFSheet sheet, int index) {

		int rowIndex = index;
		Row row = null;
		Cell c = null;

		rowIndex++;

		row = sheet.createRow(rowIndex);
		c = row.createCell(1);
		c.setCellValue("PERIODO:");
		c.setCellStyle(cs);

		rowIndex++;
		row = sheet.createRow(rowIndex);
		c = row.createCell(1);
		c.setCellValue("AÑO:");
		c.setCellStyle(cs);
		c = row.createCell(2);
		c.setCellStyle(cs);
		c = row.createCell(3);
		c.setCellStyle(csTopLeft);
		c = row.createCell(4);
		c.setCellValue("MES:");
		c.setCellStyle(csLeft);
		c = row.createCell(5);
		c.setCellStyle(csTopLeft);
		c = row.createCell(6);
		c.setCellStyle(csLeft);

		rowIndex++;
		row = sheet.createRow(rowIndex);
		c = row.createCell(1);
		c.setCellStyle(cs);
		c = row.createCell(2);
		c.setCellStyle(cs);
		c = row.createCell(3);
		c.setCellStyle(csTop);
		c = row.createCell(4);
		c.setCellStyle(cs);
		c = row.createCell(5);
		c.setCellStyle(csTop);
		c = row.createCell(6);
		c.setCellStyle(cs);

		rowIndex++;
		row = sheet.createRow(rowIndex);
		c = row.createCell(1);
		c.setCellValue("NOMBRE O \t\t RAZON SOCIAL:");

		c.setCellStyle(cs);
		c = row.createCell(2);
		c.setCellStyle(cs);
		c = row.createCell(3);
		c.setCellStyle(csTopLeft);
		c = row.createCell(4);
		c.setCellValue("NIT:");
		c.setCellStyle(csLeft);
		c = row.createCell(5);
		c.setCellStyle(csTopLeft);
		c = row.createCell(6);
		c.setCellStyle(csLeft);

		rowIndex++;
		row = sheet.createRow(rowIndex);
		c = row.createCell(1);
		c.setCellStyle(cs);
		c = row.createCell(2);
		c.setCellStyle(cs);
		c = row.createCell(3);
		c.setCellStyle(csTop);
		c = row.createCell(4);
		c.setCellStyle(cs);
		c = row.createCell(5);
		c.setCellStyle(csTop);
		c = row.createCell(6);
		c.setCellStyle(cs);

		rowIndex = rowIndex + 3;
		row = sheet.createRow(rowIndex);
		c = row.createCell(0);
		c.setCellValue("NRO.");
		c.setCellStyle(csTopLeft);
		
		c = row.createCell(1);
		c.setCellValue("FECHA \t\tFACTURA");
		c.setCellStyle(csTopLeft);
		
		c = row.createCell(2);
		c.setCellValue("NRO. \t\tFACTURA");
		c.setCellStyle(csTopLeft);
		
		c = row.createCell(3);
		c.setCellValue("NRO. \t\tAUTORIZACION");
		c.setCellStyle(csTopLeft);
		
		c = row.createCell(4);		
		c.setCellValue("ESTADO");
		c.setCellStyle(csTopLeft);
		
		c = row.createCell(5);
		c.setCellValue("NIT/CI \t\tCLIENTE");	
		c.setCellStyle(csTopLeft);
		
		c = row.createCell(6);
		c.setCellValue("NOMBRE O \t\t RAZON SOCIAL");
		c.setCellStyle(csTopLeft);
		
		c = row.createCell(7);
		c.setCellValue("IMPORTE \t\t TOTAL DE \t\t VENTA");
		c.setCellStyle(csTopLeft);
		
		c = row.createCell(8);
		c.setCellValue("IMPORTE \t\t ICE/IEHD \t\t TASAS");
		c.setCellStyle(csTopLeft);
		
		c = row.createCell(9);
		c.setCellValue("EXPORTACIONES \t\t Y OPERACIONES");
		c.setCellStyle(csTopLeft);
		
		c = row.createCell(10);
		c.setCellValue("VENTAS \t\t GRABADAS \t\t TASA CERO");
		c.setCellStyle(csTopLeft);
		
		c = row.createCell(11);
		c.setCellValue("SUB TOTAL");
		c.setCellStyle(csTopLeft);
		
		c = row.createCell(12);
		c.setCellValue("DESCUENTOS \t\t Y \t\t BONIFICACIONES");
		c.setCellStyle(csTopLeft);
		
		c = row.createCell(13);
		c.setCellValue("IMPORTE \t\t BASE PARA \t\t DEBITO FISCAL");
		c.setCellStyle(csTopLeft);
		
		c = row.createCell(14);
		c.setCellValue("DEBITO \t\t FISCAL");
		c.setCellStyle(csTopLeft);
		
		c = row.createCell(15);
		c.setCellValue("CODIGO \t\t CONTROL");
		c.setCellStyle(csTopLeft);

		return rowIndex;

	}

	private int insertDetailInfo(XSSFSheet sheet, int index) {
		
		/*System.out.println("listFactura : "+listFactura.size());*/

		int rowIndex = 0;
		Row row = null;
		Cell c = null;
		
		/*for (int i = 0; i < listFactura.size(); i++) {
			System.out.println(listFactura.get(i).toString());

			rowIndex = index + i;
			row = sheet.createRow(rowIndex);
			c = row.createCell(0);
			c.setCellValue(""+listFactura.get(i).getId());
			c.setCellStyle(cs);
			
			SimpleDateFormat sf= new SimpleDateFormat("dd/MM/yyyy");
			String date = sf.format(listFactura.get(i).getFechaFactura());
			c = row.createCell(1);
			c.setCellValue(date);
			c.setCellStyle(cs);
			
			c = row.createCell(2);
			c.setCellValue(listFactura.get(i).getNumeroFactura());
			c.setCellStyle(cs);
			
			c = row.createCell(3);
			c.setCellValue(listFactura.get(i).getNumeroAutorizacion());
			c.setCellStyle(cs);
			
			c = row.createCell(4);
			c.setCellValue(listFactura.get(i).getEstado());
			c.setCellStyle(cs);
			
			c = row.createCell(5);
			c.setCellValue(listFactura.get(i).getNitCi());
			c.setCellStyle(cs);
			
			c = row.createCell(6);
			c.setCellValue(listFactura.get(i).getNombreFactura());
			c.setCellStyle(cs);

			c = row.createCell(7);
			c.setCellValue(""+listFactura.get(i).getTotalFacturado());
			c.setCellStyle(cs);
			
			c = row.createCell(8);
			c.setCellValue(""+listFactura.get(i).getImporteICE());
			c.setCellStyle(cs);
			
			c = row.createCell(9);
			c.setCellValue(""+listFactura.get(i).getImporteExportaciones());
			c.setCellStyle(cs);
			
			c = row.createCell(10);
			c.setCellValue(""+listFactura.get(i).getImporteVentasGrabadasTasaCero());
			c.setCellStyle(cs);
			
			c = row.createCell(11);
			c.setCellValue(""+listFactura.get(i).getImporteSubTotal());
			c.setCellStyle(cs);
			
			c = row.createCell(12);
			c.setCellValue(""+listFactura.get(i).getImporteDescuentosBonificaciones());
			c.setCellStyle(cs);
			
			c = row.createCell(13);
			c.setCellValue(""+listFactura.get(i).getImporteBaseDebitoFiscal());
			c.setCellStyle(cs);
			
			c = row.createCell(14);
			c.setCellValue(""+listFactura.get(i).getDebitoFiscal());
			c.setCellStyle(cs);
			
			c = row.createCell(15);
			c.setCellValue(""+listFactura.get(i).getCodigoControl());
			c.setCellStyle(cs);
		}*/
		
		for (int i = 1; i < 35; i++) {

			rowIndex = index + i;
			row = sheet.createRow(rowIndex);
			c = row.createCell(0);
			c.setCellValue(i);
			c.setCellStyle(cs);
			c = row.createCell(1);
			c.setCellValue(10 + i);
			c.setCellStyle(cs);
			c = row.createCell(2);
			c.setCellValue("ITEM" + i);
			c.setCellStyle(cs);
			c = row.createCell(3);
			c.setCellValue("My ITEM" + i + " Decscription");
			c.setCellStyle(cs);
			c = row.createCell(4);
			c.setCellValue(1.11 * i);
			c.setCellStyle(cs);
		}
		System.out.println("FINALIZO EL FOR" +row.getRowNum());
		return rowIndex;
	}
}