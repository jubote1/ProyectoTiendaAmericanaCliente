package capaControlador;

import java.awt.Window;

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
	
	public void generarReporteVentasDiario(Window ventanaPadre)
	{
		String ruta = ParametrosDAO.retornarValorAlfanumerico("REPORTEVENTASDIARIO", auditoria);
		ReportesDAO.generarReporteVentasDiario(ruta, ventanaPadre);
	}

	public void generarFactura(int idPedido)
	{
		String ruta = ParametrosDAO.retornarValorAlfanumerico("REPORTEFACTURA", auditoria);
		ReportesDAO.generarFactura(ruta, idPedido);
	}
	
	public void generarReporteInventarioCon(Window ventanaPadre)
	{
		String ruta = ParametrosDAO.retornarValorAlfanumerico("REPORTEINVCONSUMIDO", auditoria);
		FechaSistema fecha = TiendaDAO.obtenerFechasSistema( auditoria);
		ReportesDAO.generarReporteInventarioCon(ruta, fecha.getFechaApertura(), ventanaPadre);
	}
	
	public void generarReporteInventarioAct(Window ventanaPadre)
	{
		String ruta = ParametrosDAO.retornarValorAlfanumerico("REPORTEINVACTUAL", auditoria);
		ReportesDAO.generarReporteInventarioAct(ruta, ventanaPadre);
	}
	
	public void generarReporteCaja(Window ventanaPadre)
	{
		String ruta = ParametrosDAO.retornarValorAlfanumerico("REPORTECAJA", auditoria);
		FechaSistema fecha = TiendaDAO.obtenerFechasSistema( auditoria);
		ReportesDAO.generarReporteCaja(ruta, fecha.getFechaApertura(), ventanaPadre);
	}
	
	public void generarReporteCajaDet(Window ventanaPadre)
	{
		String ruta = ParametrosDAO.retornarValorAlfanumerico("REPORTECAJADETALLADO", auditoria);
		FechaSistema fecha = TiendaDAO.obtenerFechasSistema( auditoria);
		ReportesDAO.generarReporteCajaDetallado(ruta, fecha.getFechaApertura(), ventanaPadre	);
	}
}
