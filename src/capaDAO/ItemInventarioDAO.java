package capaDAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import capaModelo.Usuario;
import capaConexion.ConexionBaseDatos;
import capaModelo.Impuesto;
import capaModelo.ItemInventario;
import capaModelo.MenuAgrupador;
import capaModelo.Tienda;
import org.apache.log4j.Logger;
import com.mysql.jdbc.ResultSetMetaData;
/**
 * Clase que se encarga de implementar todo lo relacionado con la base de datos de la entidad item inventario
 * @author JuanDavid
 *
 */
public class ItemInventarioDAO {
	
/**
 * Método que se encarga de retonar todos los items inventario parametrizado en el sistema.
 * @return Retorna un arrayList con tipos de datos genéricos.
 */
	public static ArrayList obtenerItemInventario()
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList itemsInventario = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from item_inventario";
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
				itemsInventario.add(fila);
				
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
		return(itemsInventario);
		
	}
	
	/**
	 * Método de la capa DAO que se encarga de insertar una entidad item inventario.
	 * @param impuesto Recibe un objeto de tipo item inventario del cual se extrae la información para la inserción.
	 * @return Se retorna un valor entero con el iditem creado en la base de datos
	 */
	public static int insertarItemInventario(ItemInventario item)
	{
		Logger logger = Logger.getLogger("log_file");
		int idImpuestoIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into item_inventario (nombre_item, unidad_medida) values ('" + item.getNombreItem() + "', '" + item.getUnidadMedida() + "')"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idImpuestoIns=rs.getInt(1);
				logger.info("id item insertada en bd " + idImpuestoIns);
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
		return(idImpuestoIns);
	}
	
	/**
	 * Método que se encarga de eliminar un determinado en item inventario, teniendo en cuenta el idItem pasado como parámetro
	 * @param idItem Se recibe como parámetro el iditem que se desea eliminar, teniendo en cuenta que es la 
	 * clave primaría de la tabla.
	 * @return Se retorna un valor booleano que indica si el resultado del proceso fue satisfactorio o no.
	 */
	public static boolean eliminarItemInventario(int idItem)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from item_inventario where iditem = " + idItem; 
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
	
	/**
	 * Método de la capa DAO que se encarga de editar un item inventario ya existente.
	 * @param impuesto Recibe como parámetro un objeto de la entidad item inventario con base en el cual se realiza la modificación
	 * @return Se retorna un valor booleano indicando si el proceso fue o no satisfactorio
	 */
	public static boolean EditarItemInventario(ItemInventario item)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update item_inventario set nombre_item = '" + item.getNombreItem() + "' , unidad_medida = '" + item.getUnidadMedida() + "' where iditem = " + item.getIdItem() ; 
			logger.info(update);
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
}
