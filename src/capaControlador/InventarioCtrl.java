package capaControlador;

import java.util.ArrayList;

import capaDAO.DetallePedidoDAO;
import capaDAO.ItemInventarioDAO;
import capaDAO.ItemInventarioProductoDAO;
import capaDAO.ModificadorInventarioDAO;
import capaDAO.PedidoEspecialDAO;
import capaModelo.DetallePedido;
import capaModelo.ModificadorInventario;
import capaModelo.PedidoEspecial;

public class InventarioCtrl {
	private boolean auditoria;
	public InventarioCtrl(boolean auditoria)
	{
		this.auditoria = auditoria;
	}
	public ArrayList obtenerItemInventarioIngresar()
	{
		ArrayList itemsIngresar = ItemInventarioDAO.obtenerItemInventarioIngresar(auditoria);
		return(itemsIngresar);
	}
	
	public int insertarIngresosInventarios(ArrayList <ModificadorInventario> ingresos, String fecha )
	{
		int idIngreso = ModificadorInventarioDAO.insertarIngresosInventarios(ingresos, fecha, auditoria);
		return(idIngreso);
	}
	
	public int insertarRetirosInventarios(ArrayList <ModificadorInventario> retiros, String fecha )
	{
		int idRetiro = ModificadorInventarioDAO.insertarRetirosInventarios(retiros, fecha, auditoria);
		return(idRetiro);
	}

	public int insertarPedidoEspecial(PedidoEspecial ped)
	{
		int idPedidoNuevo = PedidoEspecialDAO.insertarPedidoEspecial(ped, auditoria);
		return(idPedidoNuevo);
	}
	
	public boolean eliminarItemInventario(int idPedidoEspecial)
	{
		boolean respuesta = PedidoEspecialDAO.eliminarItemInventario(idPedidoEspecial, auditoria);
		return(respuesta);
	}
	
	public ArrayList obtenerPedidosEspeciales(String fecha)
	{
		ArrayList pedidosEspeciales = PedidoEspecialDAO.obtenerPedidosEspeciales(fecha, auditoria);
		return(pedidosEspeciales);
	}
	
	public  int obtenerCantItemInventario( )
	{
		int cantItems = ItemInventarioDAO.obtenerCantItemInventario(auditoria);
		return(cantItems);
		
	}
	
	public ArrayList obtenerItemInventarioResumen(String fecha)
	{
		ArrayList itemInventarioResumen = ItemInventarioDAO.obtenerItemInventarioResumen(fecha, auditoria);
		return(itemInventarioResumen);
	}
	
	public ArrayList obtenerItemInventarioVarianza(String fecha)
	{
		ArrayList itemInventarioVarianza = ItemInventarioDAO.obtenerItemInventarioVarianza(fecha, auditoria);
		return(itemInventarioVarianza);
	}
	public int insertarVarianzaInventarios(ArrayList <ModificadorInventario> varianzas, String fecha )
	{
		int idInvVarianza = ModificadorInventarioDAO.insertarVarianzaInventarios(varianzas, fecha, auditoria);
		return(idInvVarianza);
	}
	
	public  boolean seIngresoVarianza(String fecha)
	{
		boolean respuesta = ModificadorInventarioDAO.seIngresoVarianza(fecha, auditoria);
		return(respuesta);
	}
	
	public boolean descontarInventarioPedido(int idPedido)
	{
		ArrayList<DetallePedido> detallesPedido = DetallePedidoDAO.obtenerDetallePedido(idPedido, auditoria);
		ArrayList<ModificadorInventario> descInventario = new ArrayList();
		ArrayList<ModificadorInventario> modsInv = new ArrayList();
		for(int i = 0; i < detallesPedido.size(); i++)
		{
			DetallePedido detTemp = detallesPedido.get(i);
			//Validamos que el detallePedido no haya sido descargado al inventario en cuyo caso realizremos el descargo al inventario
			if(detTemp.getDescargoInventario().equals(new String("N")))
			{
				modsInv = ItemInventarioProductoDAO.obtenerItemsInventarioProducto(detTemp.getIdProducto(), detTemp.getCantidad(), auditoria);
				boolean respuesta = ModificadorInventarioDAO.insertarConsumoInventarios(modsInv, idPedido, auditoria);
				descargarDetallePedido(detTemp.getIdDetallePedido());
			}
		}
		return(true);
	}
	
	
	
	public boolean reintegrarInventarioPedido(int idPedido)
	{
		ArrayList<DetallePedido> detallesPedido = DetallePedidoDAO.obtenerDetallePedido(idPedido, auditoria);
		ArrayList<ModificadorInventario> descInventario = new ArrayList();
		ArrayList<ModificadorInventario> modsInv = new ArrayList();
		for(int i = 0; i < detallesPedido.size(); i++)
		{
			DetallePedido detTemp = detallesPedido.get(i);
			//Validamos que el detallePedido no haya sido descargado al inventario en cuyo caso realizremos el descargo al inventario
			if(detTemp.getDescargoInventario().equals(new String("S")))
			{
				modsInv = ItemInventarioProductoDAO.obtenerItemsInventarioProducto(detTemp.getIdProducto(), detTemp.getCantidad()*-1, auditoria);
				boolean respuesta = ModificadorInventarioDAO.insertarConsumoInventarios(modsInv, idPedido, auditoria);
				
			}
		}
		return(true);
	}
	
	/**
	 * Método que se encargará de reintegrar al inventario dado un idDetallePedido específico, validando si este descontó o no
	 * inventario
	 * @param idDetallePedido Se recibe parámetro el idDetallePedido que posiblemente se va a descontar en inventario
	 * @param idPedido Se recibe el idPedido sobre el cual se va a realizar el reintegro del idDetallePedido
	 * @return
	 */
	public boolean reintegrarInventarioDetallePedido(int idDetallePedido, int idPedido)
	{
		ArrayList<DetallePedido> detallesPedido = DetallePedidoDAO.obtenerDetallePedidoMaster(idDetallePedido, idPedido, auditoria);
		ArrayList<ModificadorInventario> descInventario = new ArrayList();
		ArrayList<ModificadorInventario> modsInv = new ArrayList();
		for(int i = 0; i < detallesPedido.size(); i++)
		{
			DetallePedido detTemp = detallesPedido.get(i);
			//Validamos que el detallePedido no haya sido descargado al inventario en cuyo caso realizremos el descargo al inventario
			if(detTemp.getDescargoInventario().equals(new String("S")))
			{
				modsInv = ItemInventarioProductoDAO.obtenerItemsInventarioProducto(detTemp.getIdProducto(), detTemp.getCantidad()*-1, auditoria);
				boolean respuesta = ModificadorInventarioDAO.insertarConsumoInventarios(modsInv, idPedido, auditoria);
				
			}
		}
		return(true);
	}
	
	public boolean descargarDetallePedido(int idDetallePedido)
	{
		boolean respuesta = DetallePedidoDAO.descargarDetallePedido(idDetallePedido, auditoria);
		return(respuesta);
	}
}
