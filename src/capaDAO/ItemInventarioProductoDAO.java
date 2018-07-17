package capaDAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import capaModelo.Usuario;
import capaConexion.ConexionBaseDatos;
import capaModelo.Impuesto;
import capaModelo.ImpuestoProducto;
import capaModelo.ItemInventarioProducto;
import capaModelo.MenuAgrupador;
import capaModelo.ModificadorInventario;
import capaModelo.Tienda;
import org.apache.log4j.Logger;
import com.mysql.jdbc.ResultSetMetaData;
/**
 * Clase que se encarga de implementar todo lo relacionado con la base de datos de la entidad impuestos por producto
 * @author JuanDavid
 *
 */
public class ItemInventarioProductoDAO {
	
/**
 * Método que se encarga de retonar todos los items de inventario por producto, dado un id producto determinado parametrizado en el sistema.
 * @return Retorna un arrayList con tipos de datos genéricos.
 */
	public static ArrayList obtenerItemsInventarioProducto(int idProducto)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList itemsProducto = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.iditem_producto , c.descripcion, a.iditem, b.nombre_item, a.cantidad from item_inventario_x_producto a, item_inventario b, producto c where a.idproducto = c.idproducto and a.iditem = b.iditem and a.idproducto = " + idProducto;
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
				itemsProducto.add(fila);
				
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
		return(itemsProducto);
		
	}
	
	/**
	 * Método que se encarga de retornar un ArrayList con modificaodres de producto asociados a un idproducto determinado y una cantidad
	 * @param idProducto
	 * @return
	 */
	public static ArrayList<ModificadorInventario> obtenerItemsInventarioProducto(int idProducto, double cantidad)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<ModificadorInventario> modInvProducto = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.iditem,  a.cantidad from item_inventario_x_producto a where a.idproducto = " + idProducto;
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idItem = 0;
			double cantidadItem = 0;
			while(rs.next()){
				idItem = rs.getInt("iditem");
				cantidadItem = rs.getDouble("cantidad");
				ModificadorInventario modInv = new ModificadorInventario(idItem, cantidadItem*cantidad);
				modInvProducto.add(modInv);
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
		return(modInvProducto);
		
	}
	
	/**
	 * Método de la capa DAO que se encarga de insertar una entidad item inventario por producto.
	 * @param impuesto Recibe un objeto de tipo item inventari por producto del cual se extrae la información para la inserción.
	 * @return Se retorna un valor entero con el iditem_producto creado en la base de datos
	 */
	public static int insertarItemInventarioProducto(ItemInventarioProducto itemProducto)
	{
		Logger logger = Logger.getLogger("log_file");
		int idItemIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into item_inventario_x_producto (idproducto, iditem, cantidad) values (" + itemProducto.getIdProducto() + ", " + itemProducto.getIdItem() + " , " + itemProducto.getCantidad() + ")"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idItemIns=rs.getInt(1);
				logger.info("id impuesto insertada en bd " + idItemIns);
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
		return(idItemIns);
	}
	
	/**
	 * Método que se encarga de eliminar un determinado item inventario producto, teniendo en cuenta el idItemProducto pasado como parámetro
	 * @param idItemProducto Se recibe como parámetro el idItemProducto que se desea eliminar, teniendo en cuenta que es la 
	 * clave primaría de la tabla.
	 * @return Se retorna un valor booleano que indica si el resultado del proceso fue satisfactorio o no.
	 */
	public static boolean eliminarItemInventarioProducto(int idItemProducto)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from item_inventario_x_producto where iditem_producto = " + idItemProducto; 
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
