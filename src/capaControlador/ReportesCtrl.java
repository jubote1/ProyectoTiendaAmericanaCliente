package capaControlador;

import capaDAO.ParametrosDAO;
import capaDAO.ReportesDAO;
import capaDAO.TiendaDAO;
import capaModelo.FechaSistema;

public class ReportesCtrl {
	
	public void generarReporteVentasDiario()
	{
		String ruta = ParametrosDAO.retornarValorAlfanumerico("REPORTEVENTASDIARIO");
		ReportesDAO.generarReporteVentasDiario(ruta);
	}

	public void generarFactura(int idPedido)
	{
		String ruta = ParametrosDAO.retornarValorAlfanumerico("REPORTEFACTURA");
		ReportesDAO.generarFactura(ruta, idPedido);
	}
	
	public void generarReporteInventarioCon()
	{
		String ruta = ParametrosDAO.retornarValorAlfanumerico("REPORTEINVCONSUMIDO");
		FechaSistema fecha = TiendaDAO.obtenerFechasSistema();
		ReportesDAO.generarReporteInventarioCon(ruta, fecha.getFechaApertura());
	}
	
	public void generarReporteInventarioAct()
	{
		String ruta = ParametrosDAO.retornarValorAlfanumerico("REPORTEINVACTUAL");
		ReportesDAO.generarReporteInventarioAct(ruta);
	}
	
	public void generarReporteCaja()
	{
		String ruta = ParametrosDAO.retornarValorAlfanumerico("REPORTECAJA");
		FechaSistema fecha = TiendaDAO.obtenerFechasSistema();
		ReportesDAO.generarReporteCaja(ruta, fecha.getFechaApertura());
	}
	
	public void generarReporteCajaDet()
	{
		String ruta = ParametrosDAO.retornarValorAlfanumerico("REPORTECAJADETALLADO");
		FechaSistema fecha = TiendaDAO.obtenerFechasSistema();
		ReportesDAO.generarReporteCajaDetallado(ruta, fecha.getFechaApertura());
	}
}
