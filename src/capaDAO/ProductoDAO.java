package capaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import capaModelo.Usuario;
import capaConexion.ConexionBaseDatos;
import capaModelo.Impuesto;
import capaModelo.AgrupadorMenu;
import capaModelo.Producto;
import capaModelo.Tienda;
import org.apache.log4j.Logger;
import com.mysql.jdbc.ResultSetMetaData;
/**
 * Clase que se encarga de implementar todo lo relacionado con la base de datos de la entidad producto
 * @author JuanDavid
 *
 */
public class ProductoDAO {
	
/**
 * Método que se encarga de retonar todos los productos parametrizado en el sistema.
 * @return Retorna un arrayList con tipos de datos genéricos.
 */
	public static ArrayList obtenerProducto(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList productos = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from producto";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
			int numeroColumnas = rsMd.getColumnCount();
			
			while(rs.next()){
				String [] fila = new String[numeroColumnas];
				for(int y = 0; y < numeroColumnas; y++)
				{
					fila[y] = rs.getString(y+1);
				}
				productos.add(fila);
				
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.info(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(productos);
		
	}
	
	/**
	 * Método en capa de acceso a datos que se encarga de retornar el precio de pila de un producto determinado
	 * @param idProducto Se recibe como parámetro el idProducto al cual se le desea consultar el precio de pila
	 * @return se retorna un valor double con precio.
	 */
	public static double obtenerPrecioPilaProducto(int idProducto, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		double precioRetorno = 0;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select precio1 from producto where idproducto = " + idProducto;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				
				precioRetorno = rs.getDouble("precio1");
				
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.info(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(precioRetorno);
		
	}
	
	public static double obtenerPrecioProducto(int idProducto, String precio, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		double precioRetorno = 0;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select "+ precio +" from producto where idproducto = " + idProducto;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				
				precioRetorno = rs.getDouble(precio);
				
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.info(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(precioRetorno);
		
	}
	
	public static double obtenerPrecioProducto(String precio, int idProducto, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		double precioRetorno = 0;
		String consulta = "";
		try
		{
			Statement stm = con1.createStatement();
			consulta = "select " + precio + " from producto where idproducto = " + idProducto;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				
				precioRetorno = rs.getDouble(precio);
				
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.info(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(precioRetorno);
		
	}
	
	public static Producto obtenerProducto(int idProducto, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		Producto producto = new Producto();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from producto where idproducto = " + idProducto;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
			int numeroColumnas = rsMd.getColumnCount();
			String descripcion = "";
			String impresion = "";
			String textoBoton = "";
			String colorBoton = "";
			int idPreguntaForzada1 = 0;
			int idPreguntaForzada2 = 0;
			int idPreguntaForzada3 = 0;
			int idPreguntaForzada4 = 0;
			int idPreguntaForzada5 = 0;
			Double precio1,precio2,precio3, precio4, precio5, precio6, precio7, precio8, precio9, precio10;
			String impresionComanda = "";
			String tipoProducto= "";
			String tamano = "";
			int modificadorCon;
			int modificadorSin;
			byte[] imagen = null;
			int intModConPregunta = 0;
			boolean modConPregunta = false;
			while(rs.next()){
				
				descripcion = rs.getString("descripcion");
				impresion = rs.getString("impresion");
				textoBoton = rs.getString("textoboton");
				colorBoton = rs.getString("colorboton");
				idPreguntaForzada1 = rs.getInt("idpreguntaforzada1");
				idPreguntaForzada2 = rs.getInt("idpreguntaforzada2");
				idPreguntaForzada3 = rs.getInt("idpreguntaforzada3");
				idPreguntaForzada4 = rs.getInt("idpreguntaforzada4");
				idPreguntaForzada5 = rs.getInt("idpreguntaforzada5");
				precio1 = rs.getDouble("precio1");
				precio2 = rs.getDouble("precio2");
				precio3 = rs.getDouble("precio3");
				precio4 = rs.getDouble("precio4");
				precio5 = rs.getDouble("precio5");
				precio6 = rs.getDouble("precio6");
				precio7 = rs.getDouble("precio7");
				precio8 = rs.getDouble("precio8");
				precio9 = rs.getDouble("precio9");
				precio10 = rs.getDouble("precio10");
				impresionComanda = rs.getString("impresion_comanda");
				tipoProducto = rs.getString("tipo_producto");
				tamano = rs.getString("tamano");
				modificadorCon = rs.getInt("modificadorcon");
				modificadorSin = rs.getInt("modificadorsin");
				try
				{
					imagen = rs.getBytes("imagen");
				}catch(Exception e)
				{
					imagen = null;
				}
				intModConPregunta = rs.getInt("mod_con_pregunta");
				if(intModConPregunta == 1)
				{
					modConPregunta = true;
				}else
				{
					modConPregunta = false;
				}
				producto = new Producto(idProducto, descripcion, impresion, textoBoton, colorBoton, idPreguntaForzada1, idPreguntaForzada2,  idPreguntaForzada3, idPreguntaForzada4, idPreguntaForzada5, precio1, precio2,precio3,precio4,precio5,precio6,precio7,precio8,precio9,precio10, impresionComanda, tipoProducto, tamano, modConPregunta ); 
				producto.setImagen(imagen);
				producto.setModificadorSin(modificadorSin);
				producto.setModificadorCon(modificadorCon);
				break;
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.info(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(producto);
		
	}
	
	
	
	/**
	 * Método que se encarga de retornar todos los productos parametrizados en el sistema,y con esto trabajar en pantalla
	 * @param auditoria Se recibe parámetro de auditoría para definir si se guarda o no trazas
	 * @return Se retorna un ArrayList con el listado de productos que se tienen parametrizados en el sistema.
	 */
	public static ArrayList<Producto> obtenerProductos(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<Producto> productos = new ArrayList();
		Producto producto = new Producto();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from producto";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
			int numeroColumnas = rsMd.getColumnCount();
			int idProducto = 0;
			String descripcion = "";
			String impresion = "";
			String textoBoton = "";
			String colorBoton = "";
			int idPreguntaForzada1 = 0;
			int idPreguntaForzada2 = 0;
			int idPreguntaForzada3 = 0;
			int idPreguntaForzada4 = 0;
			int idPreguntaForzada5 = 0;
			Double precio1,precio2,precio3, precio4, precio5, precio6, precio7, precio8, precio9, precio10;
			String impresionComanda = "";
			String tipoProducto= "";
			String tamano = "";
			int modificadorCon;
			int modificadorSin;
			byte[] imagen = null;
			int intModConPregunta = 0;
			boolean modConPregunta = false;
			while(rs.next()){
				idProducto = rs.getInt("idproducto");
				descripcion = rs.getString("descripcion");
				impresion = rs.getString("impresion");
				textoBoton = rs.getString("textoboton");
				colorBoton = rs.getString("colorboton");
				idPreguntaForzada1 = rs.getInt("idpreguntaforzada1");
				idPreguntaForzada2 = rs.getInt("idpreguntaforzada2");
				idPreguntaForzada3 = rs.getInt("idpreguntaforzada3");
				idPreguntaForzada4 = rs.getInt("idpreguntaforzada4");
				idPreguntaForzada5 = rs.getInt("idpreguntaforzada5");
				precio1 = rs.getDouble("precio1");
				precio2 = rs.getDouble("precio2");
				precio3 = rs.getDouble("precio3");
				precio4 = rs.getDouble("precio4");
				precio5 = rs.getDouble("precio5");
				precio6 = rs.getDouble("precio6");
				precio7 = rs.getDouble("precio7");
				precio8 = rs.getDouble("precio8");
				precio9 = rs.getDouble("precio9");
				precio10 = rs.getDouble("precio10");
				impresionComanda = rs.getString("impresion_comanda");
				tipoProducto = rs.getString("tipo_producto");
				tamano = rs.getString("tamano");
				modificadorCon = rs.getInt("modificadorcon");
				modificadorSin = rs.getInt("modificadorsin");
				try
				{
					imagen = rs.getBytes("imagen");
				}catch(Exception e)
				{
					imagen = null;
				}
				intModConPregunta = rs.getInt("mod_con_pregunta");
				if(intModConPregunta == 1)
				{
					modConPregunta = true;
				}else
				{
					modConPregunta = false;
				}
				producto = new Producto(idProducto, descripcion, impresion, textoBoton, colorBoton, idPreguntaForzada1, idPreguntaForzada2,  idPreguntaForzada3, idPreguntaForzada4, idPreguntaForzada5, precio1, precio2,precio3,precio4,precio5,precio6,precio7,precio8,precio9,precio10, impresionComanda, tipoProducto, tamano, modConPregunta ); 
				producto.setImagen(imagen);
				producto.setModificadorSin(modificadorSin);
				producto.setModificadorCon(modificadorCon);
				productos.add(producto);
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.info(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(productos);
		
	}
	
	/**
	 * Método de la capa DAO que se encarga de insertar una entidad producto.
	 * @param impuesto Recibe un objeto de tipo producto del cual se extrae la información para la inserción.
	 * @return Se retorna un valor entero con el idproducto creado en la base de datos
	 */
	public static int insertarProducto(Producto producto, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		int idProductoIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		int intModConPregunta = 0;
		if(producto.isModConPregunta())
		{
			intModConPregunta = 1;
		}
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into producto (descripcion, impresion, textoboton, colorboton,idpreguntaforzada1,idpreguntaforzada2, idpreguntaforzada3,idpreguntaforzada4, idpreguntaforzada5, precio1, precio2, precio3, precio4, precio5, precio6, precio7, precio8, precio9 , precio10, impresion_comanda, tipo_producto, tamano, imagen, mod_con_pregunta) values ('" + producto.getDescripcion() + "', '" + producto.getImpresion() + "', '" + producto.getTextoBoton() + "', '" + producto.getColorBoton() + "' , " + producto.getIdPreguntaForzada1() + " , " + producto.getIdPreguntaForzada2() + " , " + producto.getIdPreguntaForzada3() + " , " + producto.getIdPreguntaForzada4() + " , " + producto.getIdPreguntaForzada5() 
			+ " , " + producto.getPrecio1() + " , " + producto.getPrecio2() + " , " + producto.getPrecio3() + " , " + producto.getPrecio4() + " , " + producto.getPrecio5() + " , " + producto.getPrecio6() + " , " + producto.getPrecio7() + " , "  + producto.getPrecio8() + " , " + producto.getPrecio9() + " , " + producto.getPrecio10() + " , '" + producto.getImpresionComanda() + "' , '" + producto.getTipoProducto() + "' , '" + producto.getTamano() + "' , " + producto.getImagen() + " , " + intModConPregunta + " )"; 
			if(auditoria)
			{
				logger.info(insert);
			}
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idProductoIns=rs.getInt(1);
				if(auditoria)
				{
					logger.info("id producto insertada en bd " + idProductoIns);
				}
				
	        }
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
			return(0);
		}
		return(idProductoIns);
	}
	
	/**
	 * Método que se encarga de eliminar un determinado en producto, teniendo en cuenta el idproducto pasado como parámetro
	 * @param idProducto Se recibe como parámetro el idproducto que se desea eliminar, teniendo en cuenta que es la 
	 * clave primaría de la tabla.
	 * @return Se retorna un valor booleano que indica si el resultado del proceso fue satisfactorio o no.
	 */
	public static boolean eliminarProducto(int idProducto, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from producto where idproducto = " + idProducto; 
			if(auditoria)
			{
				logger.info(delete);
			}
			stm.executeUpdate(delete);
			respuesta = true;
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
				respuesta = false;
			}catch(Exception e1)
			{
			}
			
		}
		return(respuesta);
	}
	
	/**
	 * Método de la capa DAO que se encarga de editar un producto ya existente.
	 * @param producto Recibe como parámetro un objeto de la entidad producto con base en el cual se realiza la modificación
	 * @return Se retorna un valor booleano indicando si el proceso fue o no satisfactorio
	 */
	public static boolean EditarProducto(Producto producto, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		int intModConPregunta = 0;
		if(producto.isModConPregunta())
		{
			intModConPregunta = 1;
		}
		try
		{
			Statement stm = con1.createStatement();
			String update = "update producto set mod_con_pregunta = " + intModConPregunta + " , descripcion = '" + producto.getDescripcion() + "' , impresion = '" + producto.getImpresion() + "' , textoboton = '" + producto.getTextoBoton() + "' , colorboton = '" 
			+ producto.getColorBoton() + "' , idpreguntaforzada1 = " + producto.getIdPreguntaForzada1() + " , idpreguntaforzada2 = " + producto.getIdPreguntaForzada2() + " , idpreguntaforzada3 = " + producto.getIdPreguntaForzada3() + " , idpreguntaforzada4 = " + producto.getIdPreguntaForzada4() 
			+  " , idpreguntaforzada5 = " + producto.getIdPreguntaForzada5() + " , precio1 = " + producto.getPrecio1() + " , precio2 = " + producto.getPrecio2() + " , precio3 =  " + producto.getPrecio3() + " , precio4 =  " + producto.getPrecio4()
			+ " , precio5 = " + producto.getPrecio5() + " , precio6 =  " + producto.getPrecio6() + " , precio7 = " + producto.getPrecio7() + " , precio8 =  " + producto.getPrecio8() + " , precio9 =  " + producto.getPrecio9() + " , precio10 =  " + producto.getPrecio10() + " , impresion_comanda =  '" + producto.getImpresionComanda() + "' , tipo_producto = '" +producto.getTipoProducto() + "' , tamano = '" + producto.getTamano() + "' , imagen = ? where idproducto = " + producto.getIdProducto() ; 
			PreparedStatement actualiz = null;
			actualiz = con1.prepareStatement(update);
			actualiz.setBytes(1, producto.getImagen());
			if(auditoria)
			{
				logger.info(update);
			}
			actualiz.executeUpdate();
			
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
			return(false);
		}
		return(true);
	}
	
	public static boolean EditarCantProductoCon(int idProducto, int cantidad, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update producto set modificadorcon = " + cantidad + " where idproducto =" + idProducto ;
			if(auditoria)
			{
				logger.info(update);
			}
			stm.executeUpdate(update);
			
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
			return(false);
		}
		return(true);
	}
	
	public static boolean EditarCantProductoSin(int idProducto, int cantidad, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update producto set modificadorsin = " + cantidad + " where idproducto =" + idProducto ;
			if(auditoria)
			{
				logger.info(update);
			}
			stm.executeUpdate(update);
			
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
			return(false);
		}
		return(true);
	}
	
	public static boolean tieneModConPregunta(int idProducto, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = false;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String select = "select mod_con_pregunta from producto where idproducto =" + idProducto ;
			int intModConPregunta = 0;
			if(auditoria)
			{
				logger.info(select);
			}
			ResultSet rs = stm.executeQuery(select);
			while(rs.next())
			{
				intModConPregunta = rs.getInt("mod_con_pregunta");
				if(intModConPregunta == 1)
				{
					respuesta = true;
				}else
				{
					respuesta = false;
				}
			}
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
			
		}
		return(respuesta);
	}
	
	
}
