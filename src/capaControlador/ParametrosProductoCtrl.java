package capaControlador;

import java.util.ArrayList;

import capaDAO.ImpuestoDAO;
import capaDAO.ImpuestoProductoDAO;
import capaDAO.ItemInventarioDAO;
import capaDAO.MenuAgrupadorDAO;
import capaDAO.ProductoDAO;
import capaModelo.Impuesto;
import capaModelo.ImpuestoProducto;
import capaModelo.ItemInventario;
import capaModelo.MenuAgrupador;
import capaModelo.Producto;

/**
 * Clase de la capa Controlador que busca agrupar desde la capa de Negocio el comportamiento de los objetos relacionados con los productos
 * del sistema
 * @author JuanDavid
 *
 */
public class ParametrosProductoCtrl {
	
	//IMPUESTO
	
	/**
	 * Método de capa controladora para el retorno de los impuestos
	 * @return ArrayList con tipo de datos genérico de impuestos.
	 */
	public ArrayList<Object> obtenerImpuestos()
	{
		ArrayList<Object>  impuestos = ImpuestoDAO.obtenerImpuesto();
		return(impuestos);
	}
	
	/**
	 * Método de capa controladora encargado de la inserción de un nuevo impuesto
	 * @param impuesto Se recibe el objeto de la entidad impuesto con los datos para insertar
	 * @return Se retorna valor entero con el idimpuesto creado en el sistema.
	 */
	public int insertarImpuesto(Impuesto impuesto)
	{
		int idImpuesto = ImpuestoDAO.insertarImpuesto(impuesto);
		return(idImpuesto);
	}
	
	/**
	 * Método que se encarga de la edición de un impuesto desde la capa de controlador.
	 * @param impuesto Se recibe como parámetro un objeto de la entidad impuesto que tendrá los datos a modificar.
	 * @return Se retorna un valor booleano que indica el resultado del proceso
	 */
	public boolean editarImpuesto(Impuesto impuesto)
	{
		boolean respuesta  = ImpuestoDAO.EditarImpuesto(impuesto);
		return(respuesta);
	}
	/**
	 * Método de la capa controladora que se encarga de eliminar un determinado impuesto
	 * @param idImpuesto Se recibe el idimpuesto que se desea eliminar
	 * @return Se retorna un valor booleano que indica el resultado del proceso
	 */
	public boolean eliminarImpuesto(int idImpuesto)
	{
		boolean respuesta = ImpuestoDAO.eliminarImpuesto(idImpuesto);
		return(respuesta);
	}

	
	//IMPUESTO_X_PRODUCTO
	
		/**
		 * Método de capa controladora para el retorno de los impuestos por producto
		 * @return ArrayList con tipo de datos genérico de impuestos.
		 */
		public ArrayList<Object> obtenerImpuestosProducto(int idImpuestoProducto)
		{
			ArrayList<Object>  impuestosProducto = ImpuestoProductoDAO.obtenerImpuestosProducto(idImpuestoProducto);
			return(impuestosProducto);
		}
		
		/**
		 * Método de capa controladora encargado de la inserción de un nuevo impuesto por producto
		 * @param impuesto por Producto Se recibe el objeto de la entidad impuesto por producto con los datos para insertar
		 * @return Se retorna valor entero con el idimpuestoProducto creado en el sistema.
		 */
		public int insertarImpuestoProducto(ImpuestoProducto impuestoProducto)
		{
			int idImpuestoProducto = ImpuestoProductoDAO.insertarImpuestoProducto(impuestoProducto);
			return(idImpuestoProducto);
		}
		
		
		/**
		 * Método de la capa controladora que se encarga de eliminar un determinado impuesto por producto
		 * @param idImpuestoProducto Se recibe el idimpuesto por producto que se desea eliminar
		 * @return Se retorna un valor booleano que indica el resultado del proceso
		 */
		public boolean eliminarImpuestoProducto(int idImpuestoProducto)
		{
			boolean respuesta = ImpuestoProductoDAO.eliminarImpuestoProducto(idImpuestoProducto);
			return(respuesta);
		}

	
	//ITEM INVENTARIO
	

	/**
	 * Método de capa controladora para el retorno de los items inventarios
	 * @return ArrayList con tipo de datos genérico de items inventarios.
	 */
	public ArrayList<Object> obtenerItemsInventarios()
	{
		ArrayList<Object>  items = ItemInventarioDAO.obtenerItemInventario();
		return(items);
	}
	
	/**
	 * Método de capa controladora encargado de la inserción de un nuevo item inventario
	 * @param impuesto Se recibe el objeto de la entidad item inventario con los datos para insertar
	 * @return Se retorna valor entero con el iditem creado en el sistema.
	 */
	public int insertarItemInventario(ItemInventario item)
	{
		int idItem = ItemInventarioDAO.insertarItemInventario(item);
		return(idItem);
	}
	
	/**
	 * Método que se encarga de la edición de un item inventario desde la capa de controlador.
	 * @param item Se recibe como parámetro un objeto de la entidad item inventario que tendrá los datos a modificar.
	 * @return Se retorna un valor booleano que indica el resultado del proceso
	 */
	public boolean editarItemInventario(ItemInventario item)
	{
		boolean respuesta  = ItemInventarioDAO.EditarItemInventario(item);
		return(respuesta);
	}
	/**
	 * Método de la capa controladora que se encarga de eliminar un determinado item inventario
	 * @param iditem Se recibe el idtem que se desea eliminar
	 * @return Se retorna un valor booleano que indica el resultado del proceso
	 */
	public boolean eliminarItemInventario(int idItem)
	{
		boolean respuesta = ItemInventarioDAO.eliminarItemInventario(idItem);
		return(respuesta);
	}
	
	//PRODUCTO
	

		/**
		 * Método de capa controladora para el retorno de los productos
		 * @return ArrayList con tipo de datos genérico de productos.
		 */
		public ArrayList<Object> obtenerProductos()
		{
			ArrayList<Object>  productos = ProductoDAO.obtenerProducto();
			return(productos);
		}
		
		/**
		 * Método de capa controladora encargado de la inserción de un nuevo producto
		 * @param producto Se recibe el objeto de la entidad producto con los datos para insertar
		 * @return Se retorna valor entero con el idproducto creado en el sistema.
		 */
		public int insertarProducto(Producto producto)
		{
			int idProducto = ProductoDAO.insertarProducto(producto);
			return(idProducto);
		}
		
		/**
		 * Método que se encarga de retornar para la consulta la información de un producto
		 * con base en el idproducto recibido como parámetro.
		 * @param idProducto se obtiene como parámetro el idproducto con base en la que se realiza la consulta del producto
		 * y se retorna
		 * @return se retorna un objeto de clase producto.
		 */
		public Producto obtenerProducto(int idProducto)
		{
			Producto producto = ProductoDAO.obtenerProducto(idProducto);
			return(producto);
		}
		
		/**
		 * Método que se encarga de la edición de un producto desde la capa de controlador.
		 * @param item Se recibe como parámetro un objeto de la entidad producto que tendrá los datos a modificar.
		 * @return Se retorna un valor booleano que indica el resultado del proceso
		 */
		public boolean editarProducto(Producto producto)
		{
			boolean respuesta  = ProductoDAO.EditarProducto(producto);
			return(respuesta);
		}
		/**
		 * Método de la capa controladora que se encarga de eliminar un determinado producto
		 * @param idproducto Se recibe el idproducto que se desea eliminar
		 * @return Se retorna un valor booleano que indica el resultado del proceso
		 */
		public boolean eliminarProducto(int idProducto)
		{
			boolean respuesta = ProductoDAO.eliminarProducto(idProducto);
			return(respuesta);
		}
	
}
