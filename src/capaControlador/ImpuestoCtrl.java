package capaControlador;

import java.util.ArrayList;

import capaDAO.DetallePedidoDAO;
import capaDAO.DetallePedidoImpuestoDAO;
import capaDAO.ImpuestoDAO;
import capaDAO.ImpuestoProductoDAO;
import capaModelo.DetallePedido;
import capaModelo.DetallePedidoImpuesto;
import capaModelo.ImpuestoProducto;

public class ImpuestoCtrl {
	
	private boolean auditoria;
	public ImpuestoCtrl(boolean auditoria)
	{
		this.auditoria = auditoria;
	}
	public boolean liquidarImpuestosPedido(int idPedido)
	{
		boolean respuesta = true;
		ArrayList<DetallePedido> detPedido = DetallePedidoDAO.obtenerDetallePedido(idPedido, auditoria);
		//Acumular total si se tienen varios impuestos
		double totalImpuesto;
		//Valor para un impuesto en el cálculo parcial
		double impuestoParcial;
		//valor total del item del pedido con base en el cual se cálcula el impeusto
		double valorTotal;
		//Porcentaje que se recupera para aplicar a un impuesto.
		double porcImpuesto; 
		for(int i = 0; i < detPedido.size();i++)
		{
			totalImpuesto = 0;
			DetallePedido detPed = detPedido.get(i);
			valorTotal = detPed.getValorTotal();
			ArrayList<ImpuestoProducto> impProd = ImpuestoProductoDAO.obtenerImpuestosProductoObj(detPed.getIdProducto(), auditoria);
			for(int j = 0; j < impProd.size(); j++)
			{
				porcImpuesto = ImpuestoDAO.obtenerImpuesto(impProd.get(j).getIdImpuesto(), auditoria);
				impuestoParcial = porcImpuesto * (valorTotal/100);
				totalImpuesto = totalImpuesto + impuestoParcial;
				DetallePedidoImpuesto detPedImp = new DetallePedidoImpuesto(idPedido,detPed.getIdDetallePedido(),impProd.get(j).getIdImpuesto(),impuestoParcial);
				DetallePedidoImpuestoDAO.insertarDetallePedidoImpuesto(detPedImp, auditoria);
			}
			//Se lanza la actualización para el detalle pedido con el total del impuesto
			DetallePedidoDAO.ActualizarImpuestoDetallesPedido(detPed.getIdDetallePedido(), totalImpuesto, auditoria);
		}
		return(respuesta);
	}
	
	//Desarrollamos un método que recibe rango de fechas y tipo de item de inventario
	public ArrayList obtenerCierreSemanalInsumos(String fechaActual, String fechaAnterior, String tipoItemInventario)
	{
		ArrayList respuesta = new ArrayList();
		return(respuesta);
	}

}
