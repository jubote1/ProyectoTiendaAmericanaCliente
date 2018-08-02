package capaControlador;

import capaDAO.ParametrosDAO;
import capaDAO.ReportesDAO;

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
}
