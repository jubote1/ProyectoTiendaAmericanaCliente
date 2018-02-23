package capaControlador;

import java.util.ArrayList;

import capaDAO.ImpuestoDAO;
import capaDAO.ItemInventarioDAO;
import capaDAO.MenuAgrupadorDAO;
import capaModelo.Impuesto;
import capaModelo.ItemInventario;
import capaModelo.MenuAgrupador;

/**
 * Clase de la capa Controlador que busca agrupar desde la capa de Negocio el comportamiento de los objetos relacionados con los productos
 * del sistema
 * @author JuanDavid
 *
 */
public class ParametrosProductoCtrl {
	
	//IMPUESTO
	
	/**
	 * M�todo de capa controladora para el retorno de los impuestos
	 * @return ArrayList con tipo de datos gen�rico de impuestos.
	 */
	public ArrayList<Object> obtenerImpuestos()
	{
		ArrayList<Object>  impuestos = ImpuestoDAO.obtenerImpuesto();
		return(impuestos);
	}
	
	/**
	 * M�todo de capa controladora encargado de la inserci�n de un nuevo impuesto
	 * @param impuesto Se recibe el objeto de la entidad impuesto con los datos para insertar
	 * @return Se retorna valor entero con el idimpuesto creado en el sistema.
	 */
	public int insertarImpuesto(Impuesto impuesto)
	{
		int idImpuesto = ImpuestoDAO.insertarImpuesto(impuesto);
		return(idImpuesto);
	}
	
	/**
	 * M�todo que se encarga de la edici�n de un impuesto desde la capa de controlador.
	 * @param impuesto Se recibe como par�metro un objeto de la entidad impuesto que tendr� los datos a modificar.
	 * @return Se retorna un valor booleano que indica el resultado del proceso
	 */
	public boolean editarImpuesto(Impuesto impuesto)
	{
		boolean respuesta  = ImpuestoDAO.EditarImpuesto(impuesto);
		return(respuesta);
	}
	/**
	 * M�todo de la capa controladora que se encarga de eliminar un determinado impuesto
	 * @param idImpuesto Se recibe el idimpuesto que se desea eliminar
	 * @return Se retorna un valor booleano que indica el resultado del proceso
	 */
	public boolean eliminarImpuesto(int idImpuesto)
	{
		boolean respuesta = ImpuestoDAO.eliminarImpuesto(idImpuesto);
		return(respuesta);
	}

	
	//ITEM INVENTARIO
	

	/**
	 * M�todo de capa controladora para el retorno de los items inventarios
	 * @return ArrayList con tipo de datos gen�rico de items inventarios.
	 */
	public ArrayList<Object> obtenerItemsInventarios()
	{
		ArrayList<Object>  items = ItemInventarioDAO.obtenerItemInventario();
		return(items);
	}
	
	/**
	 * M�todo de capa controladora encargado de la inserci�n de un nuevo item inventario
	 * @param impuesto Se recibe el objeto de la entidad item inventario con los datos para insertar
	 * @return Se retorna valor entero con el iditem creado en el sistema.
	 */
	public int insertarItemInventario(ItemInventario item)
	{
		int idItem = ItemInventarioDAO.insertarItemInventario(item);
		return(idItem);
	}
	
	/**
	 * M�todo que se encarga de la edici�n de un item inventario desde la capa de controlador.
	 * @param item Se recibe como par�metro un objeto de la entidad item inventario que tendr� los datos a modificar.
	 * @return Se retorna un valor booleano que indica el resultado del proceso
	 */
	public boolean editarItemInventario(ItemInventario item)
	{
		boolean respuesta  = ItemInventarioDAO.EditarItemInventario(item);
		return(respuesta);
	}
	/**
	 * M�todo de la capa controladora que se encarga de eliminar un determinado item inventario
	 * @param iditem Se recibe el idtem que se desea eliminar
	 * @return Se retorna un valor booleano que indica el resultado del proceso
	 */
	public boolean eliminarItemInventario(int idItem)
	{
		boolean respuesta = ItemInventarioDAO.eliminarItemInventario(idItem);
		return(respuesta);
	}
	
}
