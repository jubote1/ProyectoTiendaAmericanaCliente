package capaControlador;

import java.util.ArrayList;

import capaDAO.EleccionForzadaDAO;
import capaDAO.ImpuestoDAO;
import capaDAO.ImpuestoProductoDAO;
import capaDAO.ItemInventarioDAO;
import capaDAO.ItemInventarioProductoDAO;
import capaDAO.AgrupadorMenuDAO;
import capaDAO.PreguntaDAO;
import capaDAO.ProductoDAO;
import capaDAO.ProductoIncluidoDAO;
import capaDAO.ProductoModificadorConDAO;
import capaDAO.ProductoModificadorSinDAO;
import capaModelo.EleccionForzada;
import capaModelo.Impuesto;
import capaModelo.ImpuestoProducto;
import capaModelo.ItemInventario;
import capaModelo.ItemInventarioProducto;
import capaModelo.AgrupadorMenu;
import capaModelo.Pregunta;
import capaModelo.Producto;
import capaModelo.ProductoEleccionPrecio;
import capaModelo.ProductoIncluido;
import capaModelo.ProductoModificadorCon;
import capaModelo.ProductoModificadorSin;

/**
 * Clase de la capa Controlador que busca agrupar desde la capa de Negocio el comportamiento de los objetos relacionados con los productos
 * del sistema
 * @author JuanDavid
 *
 */
public class ParametrosProductoCtrl {
	
	
	private boolean auditoria;
	public ParametrosProductoCtrl(boolean auditoria)
	{
		this.auditoria = auditoria;
	}
	//IMPUESTO
	
	/**
	 * Método de capa controladora para el retorno de los impuestos
	 * @return ArrayList con tipo de datos genérico de impuestos.
	 */
	public ArrayList<Object> obtenerImpuestos()
	{
		ArrayList<Object>  impuestos = ImpuestoDAO.obtenerImpuesto(auditoria);
		return(impuestos);
	}
	
	/**
	 * Método de capa controladora encargado de la inserción de un nuevo impuesto
	 * @param impuesto Se recibe el objeto de la entidad impuesto con los datos para insertar
	 * @return Se retorna valor entero con el idimpuesto creado en el sistema.
	 */
	public int insertarImpuesto(Impuesto impuesto)
	{
		int idImpuesto = ImpuestoDAO.insertarImpuesto(impuesto, auditoria);
		return(idImpuesto);
	}
	
	/**
	 * Método que se encarga de la edición de un impuesto desde la capa de controlador.
	 * @param impuesto Se recibe como parámetro un objeto de la entidad impuesto que tendrá los datos a modificar.
	 * @return Se retorna un valor booleano que indica el resultado del proceso
	 */
	public boolean editarImpuesto(Impuesto impuesto)
	{
		boolean respuesta  = ImpuestoDAO.EditarImpuesto(impuesto, auditoria);
		return(respuesta);
	}
	/**
	 * Método de la capa controladora que se encarga de eliminar un determinado impuesto
	 * @param idImpuesto Se recibe el idimpuesto que se desea eliminar
	 * @return Se retorna un valor booleano que indica el resultado del proceso
	 */
	public boolean eliminarImpuesto(int idImpuesto)
	{
		boolean respuesta = ImpuestoDAO.eliminarImpuesto(idImpuesto, auditoria);
		return(respuesta);
	}

	
	//IMPUESTO_X_PRODUCTO
	
		/**
		 * Método de capa controladora para el retorno de los impuestos por producto
		 * @return ArrayList con tipo de datos genérico de impuestos.
		 */
		public ArrayList<Object> obtenerImpuestosProducto(int idImpuestoProducto)
		{
			ArrayList<Object>  impuestosProducto = ImpuestoProductoDAO.obtenerImpuestosProducto(idImpuestoProducto, auditoria);
			return(impuestosProducto);
		}
		
		/**
		 * Método de capa controladora encargado de la inserción de un nuevo impuesto por producto
		 * @param impuesto por Producto Se recibe el objeto de la entidad impuesto por producto con los datos para insertar
		 * @return Se retorna valor entero con el idimpuestoProducto creado en el sistema.
		 */
		public int insertarImpuestoProducto(ImpuestoProducto impuestoProducto)
		{
			int idImpuestoProducto = ImpuestoProductoDAO.insertarImpuestoProducto(impuestoProducto, auditoria);
			return(idImpuestoProducto);
		}
		
		
		/**
		 * Método de la capa controladora que se encarga de eliminar un determinado impuesto por producto
		 * @param idImpuestoProducto Se recibe el idimpuesto por producto que se desea eliminar
		 * @return Se retorna un valor booleano que indica el resultado del proceso
		 */
		public boolean eliminarImpuestoProducto(int idImpuestoProducto)
		{
			boolean respuesta = ImpuestoProductoDAO.eliminarImpuestoProducto(idImpuestoProducto, auditoria);
			return(respuesta);
		}

		//PRODUCTO_INCLUIDO
			/**
			 * Método de capa controladora para el retorno de los productos incluidos
			 * @return ArrayList con tipo de datos genérico de productos incluidos.
			 */
			public ArrayList<Object> obtenerProductosIncluidos(int idproductoincluido)
			{
				ArrayList<Object>  productosIncluidos = ProductoIncluidoDAO.obtenerProductosIncluidos(idproductoincluido, auditoria);
				return(productosIncluidos);
			}
			
			/**
			 * Método de capa controladora encargado de la inserción de un nuevo producto incluido
			 * @param producto Incluido Se recibe el objeto de la entidad Producto Incluido con los datos para insertar
			 * @return Se retorna valor entero con el idproducto_incluido creado en el sistema.
			 */
			public int insertarProductoIncluido(ProductoIncluido productoIncluido)
			{
				int idproducto_incluido = ProductoIncluidoDAO.insertarProductoIncluido(productoIncluido, auditoria);
				return(idproducto_incluido);
			}
			
			
			/**
			 * Método de la capa controladora que se encarga de eliminar un determinado producto Incluido
			 * @param idproducto_incluido Se recibe el idproducto_incluido que se desea eliminar
			 * @return Se retorna un valor booleano que indica el resultado del proceso
			 */
			public boolean eliminarProductoIncluido(int idproducto_incluido)
			{
				boolean respuesta = ProductoIncluidoDAO.eliminarProductoIncluido(idproducto_incluido, auditoria);
				return(respuesta);
			}

		
		//ITEM_INVENTARIO_X_PRODUCTO
		
			/**
			 * Método de capa controladora para el retorno de los items inventario por producto
			 * @return ArrayList con tipo de datos genérico de items inventario producto.
			 */
			public ArrayList<Object> obtenerItemsInventarioProducto(int idProducto)
			{
				ArrayList<Object>  itemsProducto = ItemInventarioProductoDAO.obtenerItemsInventarioProducto(idProducto, auditoria);
				return(itemsProducto);
			}
			
			/**
			 * Método de capa controladora encargado de la inserción de un nuevo impuesto por producto
			 * @param impuesto por Producto Se recibe el objeto de la entidad impuesto por producto con los datos para insertar
			 * @return Se retorna valor entero con el idimpuestoProducto creado en el sistema.
			 */
			public int insertarItemInventarioProducto(ItemInventarioProducto itemProducto)
			{
				int idItemProducto = ItemInventarioProductoDAO.insertarItemInventarioProducto(itemProducto, auditoria);
				return(idItemProducto);
			}
			
			
			/**
			 * Método de la capa controladora que se encarga de eliminar un determinado item inventario por producto
			 * @param idItemProducto Se recibe el id item por producto que se desea eliminar
			 * @return Se retorna un valor booleano que indica el resultado del proceso
			 */
			public boolean eliminarItemInventarioProducto(int idItemProducto)
			{
				boolean respuesta = ItemInventarioProductoDAO.eliminarItemInventarioProducto(idItemProducto, auditoria);
				return(respuesta);
			}

	
	//ITEM INVENTARIO
	

	/**
	 * Método de capa controladora para el retorno de los items inventarios
	 * @return ArrayList con tipo de datos genérico de items inventarios.
	 */
	public ArrayList<Object> obtenerItemsInventarios()
	{
		ArrayList<Object>  items = ItemInventarioDAO.obtenerItemInventario(auditoria);
		return(items);
	}
	
	public ArrayList<ItemInventario> obtenerItemsInventariosObj()
	{
		ArrayList<ItemInventario>  items = ItemInventarioDAO.obtenerItemInventarioObj(auditoria);
		return(items);
	}
	
	
	/**
	 * Método de capa controladora encargado de la inserción de un nuevo item inventario
	 * @param impuesto Se recibe el objeto de la entidad item inventario con los datos para insertar
	 * @return Se retorna valor entero con el iditem creado en el sistema.
	 */
	public int insertarItemInventario(ItemInventario item)
	{
		int idItem = ItemInventarioDAO.insertarItemInventario(item, auditoria);
		return(idItem);
	}
	
	/**
	 * Método que se encarga de la edición de un item inventario desde la capa de controlador.
	 * @param item Se recibe como parámetro un objeto de la entidad item inventario que tendrá los datos a modificar.
	 * @return Se retorna un valor booleano que indica el resultado del proceso
	 */
	public boolean editarItemInventario(ItemInventario item)
	{
		boolean respuesta  = ItemInventarioDAO.EditarItemInventario(item, auditoria);
		return(respuesta);
	}
	/**
	 * Método de la capa controladora que se encarga de eliminar un determinado item inventario
	 * @param iditem Se recibe el idtem que se desea eliminar
	 * @return Se retorna un valor booleano que indica el resultado del proceso
	 */
	public boolean eliminarItemInventario(int idItem)
	{
		boolean respuesta = ItemInventarioDAO.eliminarItemInventario(idItem, auditoria);
		return(respuesta);
	}
	
	//PRODUCTO
	

		/**
		 * Método de capa controladora para el retorno de los productos
		 * @return ArrayList con tipo de datos genérico de productos.
		 */
		public ArrayList<Object> obtenerProductos()
		{
			ArrayList<Object>  productos = ProductoDAO.obtenerProducto( auditoria);
			return(productos);
		}
		
		/**
		 * Método de capa controladora encargado de la inserción de un nuevo producto
		 * @param producto Se recibe el objeto de la entidad producto con los datos para insertar
		 * @return Se retorna valor entero con el idproducto creado en el sistema.
		 */
		public int insertarProducto(Producto producto)
		{
			int idProducto = ProductoDAO.insertarProducto(producto, auditoria);
			return(idProducto);
		}
		
		
		public boolean tieneModConPregunta(int idProducto)
		{
			boolean respuesta = ProductoDAO.tieneModConPregunta(idProducto, auditoria);
			return(respuesta);
		}
		
		/**
		 * Método que se encarga de retornar para la consulta la información de un producto
		 * con base en el idproducto recibido como parámetro.
		 * @param idProducto se obtiene como parámetro el idproducto con base en la que se realiza la consulta del producto
		 * y se retorna
		 * @return se retorna un objeto de clase producto.
		 */
		public double obtenerPrecioPilaProducto(int idProducto)
		{
			double precioProducto = ProductoDAO.obtenerPrecioPilaProducto(idProducto, auditoria);
			return(precioProducto);
		}
		
		public double obtenerPrecioProducto(int idProducto, String precio)
		{
			double precioProducto = ProductoDAO.obtenerPrecioProducto(idProducto,precio, auditoria);
			return(precioProducto);
		}
		
		
		/**
		 * Método de la clase controlador que se encarga de dados unas elecciones y idproducto, verificar este producto
		 * que precio tiene para las elecciones y retornarlo como un valor double
		 * @param elecciones ArrayList con las elecciones disponibles
		 * @param idProducto del cual se quiere retornar el valor el precio
		 * @return Se retorna valor double con el valor del precio encontrado para el producto en el listado de EleccionesForzadas.
		 */
		public double obtenerPrecioEleccion(ArrayList<EleccionForzada> elecciones, int idProducto)
		{
			double  douPrecio = 0;
			EleccionForzada EleForzadaRet;
			String strPrecio = "";
			for(int i = 0 ; i < elecciones.size(); i++ )
			{
				EleForzadaRet = elecciones.get(i);
				if(EleForzadaRet.getIdProducto() == idProducto)
				{
					strPrecio = EleForzadaRet.getPrecio();
					douPrecio = obtenerPrecioProducto(strPrecio, idProducto);
					break;
				}
				
			}
			return(douPrecio);
		}
		
		/**
		 * Método que se encarga de retonar el precio de un preoducto de acuerdo  a lo parámetros enviado
		 * @param precio se recibe como un string el precio que se desea retornar.
		 * @param idProducto Se recibe el idProducto del cual se desea realizar la consulta
		 * @return Se retorna un valor double con el precio retornado en la consulta
		 */
		public double obtenerPrecioProducto(String precio, int idProducto)
		{
			double precioProducto = ProductoDAO.obtenerPrecioProducto(precio, idProducto, auditoria);
			return(precioProducto);
		}
		
		/**
		 * Método que encarga en la capa controladora de retornar la consulta de un producto con base en el parámetro
		 * ingresado
		 * @param idProducto con en el cual se realiza la consulta del producto
		 * @return un objeto de la clase Producto con la inforación del producto
		 */
		public Producto obtenerProducto(int idProducto)
		{
			Producto producto = ProductoDAO.obtenerProducto(idProducto, auditoria);
			return(producto);
		}
		
		/**
		 * Método que se encarga de la edición de un producto desde la capa de controlador.
		 * @param item Se recibe como parámetro un objeto de la entidad producto que tendrá los datos a modificar.
		 * @return Se retorna un valor booleano que indica el resultado del proceso
		 */
		public boolean editarProducto(Producto producto)
		{
			boolean respuesta  = ProductoDAO.EditarProducto(producto, auditoria);
			return(respuesta);
		}
		
		public boolean actualizarImagenProducto(int idProducto, byte[] imagen)
		{
			boolean respuesta  = ProductoDAO.actualizarImagenProducto(idProducto, imagen, auditoria);
			return(respuesta);
		}
		/**
		 * Método de la capa controladora que se encarga de eliminar un determinado producto
		 * @param idproducto Se recibe el idproducto que se desea eliminar
		 * @return Se retorna un valor booleano que indica el resultado del proceso
		 */
		public boolean eliminarProducto(int idProducto)
		{
			boolean respuesta = ProductoDAO.eliminarProducto(idProducto, auditoria);
			return(respuesta);
		}
	
		
		
		//ELECCIÓN FORZADA
		
		/**
		 * Método de capa controladora para el retorno de las elecciones forzadas
		 * @return ArrayList con tipo de datos genérico de elección forzada.
		 */
		public ArrayList<Object> obtenerEleccionForzadas(int idPregunta)
		{
			ArrayList<Object>  eleccionesForzadas = EleccionForzadaDAO.obtenerEleccionesForzadas(idPregunta, auditoria);
			return(eleccionesForzadas);
		}
		
		/**
		 * Método de la capa controladora que se encarga de retornar las elecciones forzadas dada una pregunta  y el retorno
		 * se realiza en un arrayList de objetos tipo Eleccion Forzada
		 * @param idPreConsulta se recibe como parámetro el idPregunta con base en el cual se realiza el retorno de la información.
		 * @return Se retorna un ArrayList 
		 */
		public ArrayList<EleccionForzada> obtEleccionesForzadas(int idPreConsulta)
		{
			ArrayList<EleccionForzada>  eleccionesForzadas = EleccionForzadaDAO.obtEleccionesForzadas(idPreConsulta, auditoria);
			return(eleccionesForzadas);
		}
		
		
		/**
		 * Método de capa controladora encargado de la inserción de una nueva elección forzada
		 * @param Eleccion Forzada Se recibe el objeto de la entidad ElecciónForzada con los datos para insertar
		 * @return Se retorna valor entero con el ideleccion_forzada creado en el sistema.
		 */
		public int insertarEleccionForzada(EleccionForzada eleccion)
		{
			int idEleccion = EleccionForzadaDAO.insertarEleccionForzada(eleccion, auditoria);
			return(idEleccion);
		}
		
		/**
		 * Método que se encarga de la edición de una eleccion Forzada desde la capa de controlador.
		 * @param EleccionFOrzada Se recibe como parámetro un objeto de la entidad EleccionForzada que tendrá los datos a modificar.
		 * @return Se retorna un valor booleano que indica el resultado del proceso
		 */
		public boolean editarEleccionForzada(EleccionForzada eleccion)
		{
			boolean respuesta  = EleccionForzadaDAO.EditarEleccionForzada(eleccion, auditoria);
			return(respuesta);
		}
		/**
		 * Método de la capa controladora que se encarga de eliminar una determinada EleccionForzada
		 * @param idEleccion Se recibe el idEleccion que se desea eliminar
		 * @return Se retorna un valor booleano que indica el resultado del proceso
		 */
		public boolean eliminarEleccionForzada(int idEleccion)
		{
			boolean respuesta = EleccionForzadaDAO.eliminarEleccionForzada(idEleccion, auditoria);
			return(respuesta);
		}
		
		
		public ArrayList<ProductoEleccionPrecio> obtenerProductoEleccionPrecio()
		{
			ArrayList<ProductoEleccionPrecio> prodElePrecio = EleccionForzadaDAO.obtenerProductoEleccionPrecio(auditoria);
			return(prodElePrecio);
		}
		
		//PREGUNTA
		
		/**
		 * Método de capa controladora para el retorno de las elecciones forzadas
		 * @return ArrayList con tipo de datos genérico de preguntas.
		 */
		public ArrayList<Object> obtenerPreguntas()
		{
			ArrayList<Object>  preguntas = PreguntaDAO.obtenerPreguntas(auditoria);
			return(preguntas);
		}	
		
		
		/**
		 * Método de la capa controladora que se encarga de retornar una Pregunta con base en el idPregunta enviado
		 * como parámetro.
		 * @param idPregunta
		 * @return Se retorna un objeto del tipo Pregunta
		 */
		public Pregunta obtenerPregunta(int idPregunta)
		{
			Pregunta pregunta = PreguntaDAO.obtenerPregunta(idPregunta, auditoria);
			return(pregunta);
		}
		
		
				/**
				 * Método de capa controladora encargado de la inserción de una nueva pregunta
				 * @param Pregunta Se recibe el objeto de la entidad pregunta con los datos para insertar
				 * @return Se retorna valor entero con el idpregunta creado en el sistema.
				 */
				public int insertarPregunta(Pregunta pregunta)
				{
					int idPregunta = PreguntaDAO.insertarPregunta(pregunta, auditoria);
					return(idPregunta);
				}
				
				/**
				 * Método que se encarga de la edición de una Pregunta desde la capa de controlador.
				 * @param pregunta Se recibe como parámetro un objeto de la entidad pregunta que tendrá los datos a modificar.
				 * @return Se retorna un valor booleano que indica el resultado del proceso
				 */
				public boolean editarPregunta(Pregunta pregunta)
				{
					boolean respuesta  = PreguntaDAO.EditarPregunta(pregunta, auditoria);
					return(respuesta);
				}
				/**
				 * Método de la capa controladora que se encarga de eliminar una determinada Pregunta
				 * @param idPregunta Se recibe el idpregunta que se desea eliminar
				 * @return Se retorna un valor booleano que indica el resultado del proceso
				 */
				public boolean eliminarPregunta(int idPregunta)
				{
					boolean respuesta = PreguntaDAO.eliminarPregunta(idPregunta, auditoria);
					return(respuesta);
				}

				public ArrayList<ProductoIncluido> obtenerProductosIncluidos(int idProducto, double cantidad)
				{
					ArrayList<ProductoIncluido> prodIncluidosPedido = ProductoIncluidoDAO.obtenerProductosIncluidos(idProducto, cantidad, auditoria);
					return(prodIncluidosPedido);
							
				}
				
				//Implementación de métodos para los modificadores de producto
				
				public ArrayList obtenerProdModificadorCon(int idProducto)
				{
					ArrayList modCon = ProductoModificadorConDAO.obtenerProdModificadorCon(idProducto, auditoria);
					return(modCon);
				}
				
				public ArrayList<Producto> obtenerProdModificadorConFalta(int idProd)
				{
					ArrayList<Producto> modConFalta = ProductoModificadorConDAO.obtenerProdModificadorConFalta(idProd, auditoria);
					return(modConFalta);
				}
				
				public void insertarProdModificadoCon(ProductoModificadorCon prodMod)
				{
					 ProductoModificadorConDAO.insertarProdModificadoCon(prodMod, auditoria);
				}
				
				public boolean eliminarProdModifidadorCon(ProductoModificadorCon prodConEli)
				{
					boolean respuesta = ProductoModificadorConDAO.eliminarProdModifidadorCon(prodConEli, auditoria);
					return(respuesta);
				}
				
				public ArrayList obtenerProdModificadorSin(int idProducto)
				{
					ArrayList modSin = ProductoModificadorSinDAO.obtenerProdModificadorSin(idProducto, auditoria);
					return(modSin);
				}
				
				public ArrayList<Producto> obtenerProdModificadorSinFalta(int idProd)
				{
					ArrayList<Producto> modSinFalta = ProductoModificadorSinDAO.obtenerProdModificadorSinFalta(idProd, auditoria);
					return(modSinFalta);
				}
				
				public void insertarProdModificadoSin(ProductoModificadorSin prodMod)
				{
					 ProductoModificadorSinDAO.insertarProdModificadoSin(prodMod, auditoria);
				}
				
				public boolean eliminarProdModifidadorSin(ProductoModificadorSin prodSinEli)
				{
					boolean respuesta = ProductoModificadorSinDAO.eliminarProdModifidadorSin(prodSinEli, auditoria);
					return(respuesta);
				}
				
				public boolean EditarCantProductoCon(int idProducto, int cantidad)
				{
					boolean respuesta = ProductoDAO.EditarCantProductoCon(idProducto, cantidad, auditoria);
					return(respuesta);
				}
				
				public boolean EditarCantProductoSin(int idProducto, int cantidad)
				{
					boolean respuesta = ProductoDAO.EditarCantProductoSin(idProducto, cantidad, auditoria);
					return(respuesta);
				}
				
				public ArrayList<Producto> obtenerProductosCompleto()
				{
					ArrayList<Producto> productos = ProductoDAO.obtenerProductos(auditoria);
					return(productos);
				}
}
