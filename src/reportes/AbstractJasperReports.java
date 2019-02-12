package reportes;

import java.awt.Frame;
import java.awt.Window;
import java.sql.Connection;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFrame;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JRException;

public abstract class AbstractJasperReports {
	
	private static JasperReport report;
	private static JasperPrint reportFilled;
	private static JasperViewer viewer;
	
	public static void createReport (Connection con, String path, Map parametro, boolean imprimir, boolean esJFrame, Window ventanaPadre)
	{
		try{
				report = (JasperReport) JRLoader.loadObjectFromFile(path);
				reportFilled = JasperFillManager.fillReport(report, parametro, con);
				if (imprimir)
				{
					JasperPrintManager.printReport(reportFilled, false);
					
				}
				if(esJFrame)
				{
					JDialog frame = new JDialog((JDialog) ventanaPadre, true);
					frame.getContentPane().add(new JRViewer(reportFilled));
					frame.pack();
					frame.setVisible(true);
					frame.setSize(500, 500);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
