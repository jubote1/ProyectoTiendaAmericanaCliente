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
	
	/**
	 * Fue necesario realizar cambios dado que se estaban liquidando mal los impuestos, en cuento a que se 
	 * debe de tener el subtotal como Valor del item/ 1 + porcentaje/100 del impuesto.
	 * @param idPedido
	 * @return
	 */
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
				//Cambiamos la formula del impuesto parcial en cuanto es el valor total - valor total / 1.0 + valor del impuesto/100
				//Lo anterior sale de una regla de tres sencilla
				impuestoParcial = (valorTotal) - (valorTotal / (1 + (porcImpuesto/100)));
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
