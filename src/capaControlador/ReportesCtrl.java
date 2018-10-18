package capaControlador;

import capaDAO.ParametrosDAO;
import capaDAO.ReportesDAO;
import capaDAO.TiendaDAO;
import capaModelo.FechaSistema;

public class ReportesCtrl {
	
	private boolean auditoria;
	public ReportesCtrl(boolean auditoria)
	{
		this.auditoria = auditoria;
	}
	
	public void generarReporteVentasDiario()
	{
		String ruta = ParametrosDAO.retornarValorAlfanumerico("REPORTEVENTASDIARIO", auditoria);
		ReportesDAO.generarReporteVentasDiario(ruta);
	}

	public void generarFactura(int idPedido)
	{
		String ruta = ParametrosDAO.retornarValorAlfanumerico("REPORTEFACTURA", auditoria);
		ReportesDAO.generarFactura(ruta, idPedido);
	}
	
	public void generarReporteInventarioCon()
	{
		String ruta = ParametrosDAO.retornarValorAlfanumerico("REPORTEINVCONSUMIDO", auditoria);
		FechaSistema fecha = TiendaDAO.obtenerFechasSistema( auditoria);
		ReportesDAO.generarReporteInventarioCon(ruta, fecha.getFechaApertura());
	}
	
	public void generarReporteInventarioAct()
	{
		String ruta = ParametrosDAO.retornarValorAlfanumerico("REPORTEINVACTUAL", auditoria);
		ReportesDAO.generarReporteInventarioAct(ruta);
	}
	
	public void generarReporteCaja()
	{
		String ruta = ParametrosDAO.retornarValorAlfanumerico("REPORTECAJA", auditoria);
		FechaSistema fecha = TiendaDAO.obtenerFechasSistema( auditoria);
		ReportesDAO.generarReporteCaja(ruta, fecha.getFechaApertura());
	}
	
	public void generarReporteCajaDet()
	{
		String ruta = ParametrosDAO.retornarValorAlfanumerico("REPORTECAJADETALLADO", auditoria);
		FechaSistema fecha = TiendaDAO.obtenerFechasSistema( auditoria);
		ReportesDAO.generarReporteCajaDetallado(ruta, fecha.getFechaApertura());
	}
}
