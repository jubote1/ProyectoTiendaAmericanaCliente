package capaDAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import capaModelo.Usuario;
import capaConexion.ConexionBaseDatos;
import capaModelo.Impuesto;
import capaModelo.ImpuestoProducto;
import capaModelo.MenuAgrupador;
import capaModelo.ProductoIncluido;
import capaModelo.Tienda;
import org.apache.log4j.Logger;
import com.mysql.jdbc.ResultSetMetaData;
/**
 * Clase que se encarga de implementar todo lo relacionado con la base de datos de la entidad Producto Incluido
 * @author JuanDavid
 *
 */
public class ProductoIncluidoDAO {
	
/**
 * Método que se encarga de retonar todos los  productos incluidos , dado un id producto determinado parametrizado en el sistema.
 * @return Retorna un arrayList con tipos de datos genéricos.
 */
	public static ArrayList obtenerProductosIncluidos(int idProducto)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList productoIncluidos = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idproducto_incluido, b.descripcion, c.descripcion, a.cantidad, a.precio  from producto_incluido a, producto b, producto c where a.idproductoincluye = b.idproducto and a.idproductoincluido = c.idproducto and a.idproductoincluye = " + idProducto;
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
			int numeroColumnas = rsMd.getColumnCount();
			
			while(rs.next()){
				String [] fila = new String[numeroColumnas];
				for(int y = 0; y < numeroColumnas; y++)
				{
					fila[y] = rs.getString(y+1);
				}
				productoIncluidos.add(fila);
				
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.info(e.toString());
			System.out.println(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(productoIncluidos);
		
	}
	
	/**
	 * Método de la capa DAO que se encarga de insertar una entidad Producto Incluido
	 * @param productoIncluido Recibe un objeto de tipo Producto Incluido del cual se extrae la información para la inserción.
	 * @return Se retorna un valor entero con el idproducto_incluido creado en la base de datos
	 */
	public static int insertarProductoIncluido(ProductoIncluido productoIncluido)
	{
		Logger logger = Logger.getLogger("log_file");
		int idProductoIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into producto_incluido (idproductoincluye, idproductoincluido,cantidad, precio) values (" + productoIncluido.getIdproductoincluye() + ", " + productoIncluido.getIdproductoincluido() + " , " + productoIncluido.getCantidad() + " , '" + productoIncluido.getPrecio() + "')"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idProductoIns=rs.getInt(1);
				logger.info("id producto incluido en bd " + idProductoIns);
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
	 * Método que se encarga de eliminar un determinado producto Incluido, teniendo en cuenta el idproducto_incluido pasado como parámetro
	 * @param idproducto_incluido Se recibe como parámetro el idimpuesto que se desea eliminar, teniendo en cuenta que es la 
	 * clave primaría de la tabla.
	 * @return Se retorna un valor booleano que indica si el resultado del proceso fue satisfactorio o no.
	 */
	public static boolean eliminarProductoIncluido(int idproducto_incluido)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from producto_incluido where idproducto_incluido = " + idproducto_incluido; 
			logger.info(delete);
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
	
	
}
