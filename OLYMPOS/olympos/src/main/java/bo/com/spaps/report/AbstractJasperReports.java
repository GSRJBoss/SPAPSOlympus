package bo.com.spaps.report;

import java.sql.Connection;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * 
 * @author David.Ticlla.Felipe
 *
 */
public abstract class AbstractJasperReports {
	
	private static JasperReport report;
	private static JasperPrint reportFilled;
	private static JasperViewer viewer;
	
	public static void createReport( Connection conn,Map<String, Object> parameters, String path){
		try{
			report = ( JasperReport) JRLoader.loadObjectFromFile(path);
			reportFilled = JasperFillManager.fillReport(report, parameters, conn);
		}catch(JRException ex){
			ex.printStackTrace();
		}
	}
	
	public static void showViewer(){
		viewer = new JasperViewer(reportFilled);
		viewer.setVisible(true);
	}
	
	public static void exportToPdf( String sourceFileName){
		try{
			JasperExportManager.exportReportToPdfFile(reportFilled,sourceFileName);
		}catch(JRException ex){
			ex.printStackTrace();
		}
	}

}
