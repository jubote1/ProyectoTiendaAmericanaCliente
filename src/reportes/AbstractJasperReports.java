package reportes;

import java.sql.Connection;
import java.util.Map;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JRException;

public abstract class AbstractJasperReports {
	
	private static JasperReport report;
	private static JasperPrint reportFilled;
	private static JasperViewer viewer;
	
	public static void createReport (Connection con, String path, Map parametro, boolean imprimir)
	{
		try{
				report = (JasperReport) JRLoader.loadObjectFromFile(path);
				reportFilled = JasperFillManager.fillReport(report, parametro, con);
				if (imprimir)
				{
					JasperPrintManager.printReport(reportFilled, false);
					
				}
				
		}catch(JRException ex){
			System.out.println(ex.toString());
		}
	}

	public static void showViewer()
	{
		viewer = new JasperViewer(reportFilled, false);
		viewer.setVisible(true);
	}
	
	public static void exportToPDF(String destination)
	{
			try{
				JasperExportManager.exportReportToPdfFile(reportFilled, destination);
			}catch (JRException ex)
			{
				System.out.println(ex.toString());
			}
	}
}
