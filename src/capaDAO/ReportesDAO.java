package capaDAO;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import capaConexion.ConexionBaseDatos;
import reportes.AbstractJasperReports;

public class ReportesDAO {
	
	public static void generarReporteVentasDiario(String rutaReporte)
	{
		//Acci�n para la generaci�n de reporte general de ventas
		ConexionBaseDatos conexion = new ConexionBaseDatos();
		AbstractJasperReports.createReport(conexion.obtenerConexionBDLocal(), rutaReporte, null, false,true);
		//AbstractJasperReports.showViewer();
	}
	
	public static void generarFactura(String rutaReporte, int idPedido)
	{
		//Acci�n para la generaci�n de reporte general de ventas
		Map parametro = new HashMap();
		parametro.put("idPedido", idPedido);
		ConexionBaseDatos conexion = new ConexionBaseDatos();
		AbstractJasperReports.createReport(conexion.obtenerConexionBDLocal(), rutaReporte, parametro, true, true);
		//AbstractJasperReports.showViewer();
	}
	
	public static void generarReporteInventarioCon(String rutaReporte, String fecha)
	{
		//Acci�n para la generaci�n de reporte general de ventas
		Map parametro = new HashMap();
		parametro.put("fecha", fecha);
		ConexionBaseDatos conexion = new ConexionBaseDatos();
		AbstractJasperReports.createReport(conexion.obtenerConexionBDLocal(), rutaReporte, parametro, false, true);
		//AbstractJasperReports.showViewer();
	}

	public static void generarReporteInventarioAct(String rutaReporte)
	{
		//Acci�n para la generaci�n de reporte general de ventas
		Map parametro = new HashMap();
		ConexionBaseDatos conexion = new ConexionBaseDatos();
		AbstractJasperReports.createReport(conexion.obtenerConexionBDLocal(), rutaReporte, parametro, false, true);
		//AbstractJasperReports.showViewer();
	}
	
	public static void generarReporteCaja(String rutaReporte, String fecha)
	{
		//Acci�n para la generaci�n de reporte general de ventas
		Map parametro = new HashMap();
		parametro.put("fecha", fecha);
		ConexionBaseDatos conexion = new ConexionBaseDatos();
		AbstractJasperReports.createReport(conexion.obtenerConexionBDLocal(), rutaReporte, parametro, false, true);
		//AbstractJasperReports.showViewer();
	}

	public static void generarReporteCajaDetallado(String rutaReporte, String fecha)
	{
		//Acci�n para la generaci�n de reporte general de ventas
		Map parametro = new HashMap();
		parametro.put("fecha", fecha);
		ConexionBaseDatos conexion = new ConexionBaseDatos();
		AbstractJasperReports.createReport(conexion.obtenerConexionBDLocal(), rutaReporte, parametro, false,true);
		//AbstractJasperReports.showViewer();
		
	}

	
}
