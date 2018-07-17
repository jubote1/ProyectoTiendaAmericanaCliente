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
	
	public ArrayList obtenerItemInventarioIngresar()
	{
		ArrayList itemsIngresar = ItemInventarioDAO.obtenerItemInventarioIngresar();
		return(itemsIngresar);
	}
	
	public int insertarIngresosInventarios(ArrayList <ModificadorInventario> ingresos, String fecha )
	{
		int idIngreso = ModificadorInventarioDAO.insertarIngresosInventarios(ingresos, fecha);
		return(idIngreso);
	}
	
	public int insertarRetirosInventarios(ArrayList <ModificadorInventario> retiros, String fecha )
	{
		int idRetiro = ModificadorInventarioDAO.insertarRetirosInventarios(retiros, fecha);
		return(idRetiro);
	}

	public int insertarPedidoEspecial(PedidoEspecial ped)
	{
		int idPedidoNuevo = PedidoEspecialDAO.insertarPedidoEspecial(ped);
		return(idPedidoNuevo);
	}
	
	public boolean eliminarItemInventario(int idPedidoEspecial)
	{
		boolean respuesta = PedidoEspecialDAO.eliminarItemInventario(idPedidoEspecial);
		return(respuesta);
	}
	
	public ArrayList obtenerPedidosEspeciales(String fecha)
	{
		ArrayList pedidosEspeciales = PedidoEspecialDAO.obtenerPedidosEspeciales(fecha);
		return(pedidosEspeciales);
	}
	
	public ArrayList obtenerItemInventarioResumen(String fecha)
	{
		ArrayList itemInventarioResumen = ItemInventarioDAO.obtenerItemInventarioResumen(fecha);
		return(itemInventarioResumen);
	}
	
	public ArrayList obtenerItemInventarioVarianza(String fecha)
	{
		ArrayList itemInventarioVarianza = ItemInventarioDAO.obtenerItemInventarioVarianza(fecha);
		return(itemInventarioVarianza);
	}
	public int insertarVarianzaInventarios(ArrayList <ModificadorInventario> varianzas, String fecha )
	{
		int idInvVarianza = ModificadorInventarioDAO.insertarVarianzaInventarios(varianzas, fecha);
		return(idInvVarianza);
	}
	
	public  boolean seIngresoVarianza(String fecha)
	{
		boolean respuesta = ModificadorInventarioDAO.seIngresoVarianza(fecha);
		return(respuesta);
	}
	
	public boolean descontarInventarioPedido(int idPedido)
	{
		ArrayList<DetallePedido> detallesPedido = DetallePedidoDAO.obtenerDetallePedido(idPedido);
		ArrayList<ModificadorInventario> descInventario = new ArrayList();
		ArrayList<ModificadorInventario> modsInv = new ArrayList();
		for(int i = 0; i < detallesPedido.size(); i++)
		{
			DetallePedido detTemp = detallesPedido.get(i);
			modsInv = ItemInventarioProductoDAO.obtenerItemsInventarioProducto(detTemp.getIdProducto(), detTemp.getCantidad());
			boolean respuesta = ModificadorInventarioDAO.insertarConsumoInventarios(modsInv, idPedido);
		}
		return(true);
	}
	
	public boolean reintegrarInventarioPedido(int idPedido)
	{
		ArrayList<DetallePedido> detallesPedido = DetallePedidoDAO.obtenerDetallePedido(idPedido);
		ArrayList<ModificadorInventario> descInventario = new ArrayList();
		ArrayList<ModificadorInventario> modsInv = new ArrayList();
		for(int i = 0; i < detallesPedido.size(); i++)
		{
			DetallePedido detTemp = detallesPedido.get(i);
			modsInv = ItemInventarioProductoDAO.obtenerItemsInventarioProducto(detTemp.getIdProducto(), detTemp.getCantidad()*-1);
			boolean respuesta = ModificadorInventarioDAO.insertarConsumoInventarios(modsInv, idPedido);
		}
		return(true);
	}
}
