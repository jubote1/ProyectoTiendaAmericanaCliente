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
import capaModelo.Tienda;
import org.apache.log4j.Logger;
import com.mysql.jdbc.ResultSetMetaData;
/**
 * Clase que se encarga de implementar todo lo relacionado con la base de datos de la entidad impuestos por producto
 * @author JuanDavid
 *
 */
public class ImpuestoProductoDAO {
	
/**
 * M�todo que se encarga de retonar todos los impuestos por producto, dado un id producto determinado parametrizado en el sistema.
 * @return Retorna un arrayList con tipos de datos gen�ricos.
 */
	public static ArrayList obtenerImpuestosProducto(int idImpuestoProducto)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList impuestosProducto = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from impuesto_x_producto where idproducto = " + idImpuestoProducto;
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
				impuestosProducto.add(fila);
				
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
		return(impuestosProducto);
		
	}
	
	/**
	 * M�todo de la capa DAO que se encarga de insertar una entidad impuesto por producto.
	 * @param impuesto Recibe un objeto de tipo impuesto del cual se extrae la informaci�n para la inserci�n.
	 * @return Se retorna un valor entero con el idimpuesto creado en la base de datos
	 */
	public static int insertarImpuestoProducto(ImpuestoProducto impuestoProducto)
	{
		Logger logger = Logger.getLogger("log_file");
		int idImpuestoIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into impuesto_x_producto (idproducto, idimpuesto) values (" + impuestoProducto.getIdProducto() + ", " + impuestoProducto.getIdImpuesto() + ")"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idImpuestoIns=rs.getInt(1);
				logger.info("id impuesto insertada en bd " + idImpuestoIns);
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
	 * M�todo que se encarga de eliminar un determinado en impuesto, teniendo en cuenta el idImpuestoProducto pasado como par�metro
	 * @param idImpuestoProducto Se recibe como par�metro el idimpuesto que se desea eliminar, teniendo en cuenta que es la 
	 * clave primar�a de la tabla.
	 * @return Se retorna un valor booleano que indica si el resultado del proceso fue satisfactorio o no.
	 */
	public static boolean eliminarImpuestoProducto(int idImpuestoProducto)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from impuesto_x_producto where idimpuesto_producto = " + idImpuestoProducto; 
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
