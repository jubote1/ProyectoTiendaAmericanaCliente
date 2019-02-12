package capaDAO;

import java.awt.Window;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import capaConexion.ConexionBaseDatos;
import reportes.AbstractJasperReports;

public class ReportesDAO {
	
	public static void generarReporteVentasDiario(String rutaReporte, Window ventanaPadre)
	{
		//Acción para la generación de reporte general de ventas
		ConexionBaseDatos conexion = new ConexionBaseDatos();
		AbstractJasperReports.createReport(conexion.obtenerConexionBDLocal(), rutaReporte, null, false,true, ventanaPadre);
		//AbstractJasperReports.showViewer();
	}
	
	public static void generarFactura(String rutaReporte, int idPedido)
	{
		//Acción para la generación de reporte general de ventas
		Map parametro = new HashMap();
		parametro.put("idPedido", idPedido);
		ConexionBaseDatos conexion = new ConexionBaseDatos();
		AbstractJasperReports.createReport(conexion.obtenerConexionBDLocal(), rutaReporte, parametro, true, true, null);
		//AbstractJasperReports.showViewer();
	}
	
	public static void generarReporteInventarioCon(String rutaReporte, String fecha, Window ventanaPadre)
	{
		//Acción para la generación de reporte general de ventas
		Map parametro = new HashMap();
		parametro.put("fecha", fecha);
		ConexionBaseDatos conexion = new ConexionBaseDatos();
		AbstractJasperReports.createReport(conexion.obtenerConexionBDLocal(), rutaReporte, parametro, false, true, ventanaPadre);
		//AbstractJasperReports.showViewer();
	}

	public static void generarReporteInventarioAct(String rutaReporte, Window ventanaPadre)
	{
		//Acción para la generación de reporte general de ventas
		Map parametro = new HashMap();
		ConexionBaseDatos conexion = new ConexionBaseDatos();
		AbstractJasperReports.createReport(conexion.obtenerConexionBDLocal(), rutaReporte, parametro, false, true, ventanaPadre);
		//AbstractJasperReports.showViewer();
	}
	
	public static void generarReporteCaja(String rutaReporte, String fecha, Window ventanaPadre)
	{
		//Acción para la generación de reporte general de ventas
		Map parametro = new HashMap();
		parametro.put("fecha", fecha);
		ConexionBaseDatos conexion = new ConexionBaseDatos();
		AbstractJasperReports.createReport(conexion.obtenerConexionBDLocal(), rutaReporte, parametro, false, true, ventanaPadre);
		//AbstractJasperReports.showViewer();
	}

	public static void generarReporteCajaDetallado(String rutaReporte, String fecha, Window ventanaPadre)
	{
		//Acción para la generación de reporte general de ventas
		Map parametro = new HashMap();
		parametro.put("fecha", fecha);
		ConexionBaseDatos conexion = new ConexionBaseDatos();
		AbstractJasperReports.createReport(conexion.obtenerConexionBDLocal(), rutaReporte, parametro, false,true, ventanaPadre);
		//AbstractJasperReports.showViewer();
		
	}

	
}
