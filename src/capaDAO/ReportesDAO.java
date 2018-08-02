package capaDAO;

import java.util.HashMap;
import java.util.Map;

import capaConexion.ConexionBaseDatos;
import reportes.AbstractJasperReports;

public class ReportesDAO {
	
	public static void generarReporteVentasDiario(String rutaReporte)
	{
		//Acci�n para la generaci�n de reporte general de ventas
		ConexionBaseDatos conexion = new ConexionBaseDatos();
		AbstractJasperReports.createReport(conexion.obtenerConexionBDLocal(), rutaReporte, null, false);
		AbstractJasperReports.showViewer();
	}
	
	public static void generarFactura(String rutaReporte, int idPedido)
	{
		//Acci�n para la generaci�n de reporte general de ventas
		Map parametro = new HashMap();
		parametro.put("idPedido", idPedido);
		ConexionBaseDatos conexion = new ConexionBaseDatos();
		AbstractJasperReports.createReport(conexion.obtenerConexionBDLocal(), rutaReporte, parametro, true);
		AbstractJasperReports.showViewer();
	}


}
