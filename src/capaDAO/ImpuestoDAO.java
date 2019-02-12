package capaDAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import capaModelo.Usuario;
import capaConexion.ConexionBaseDatos;
import capaModelo.Impuesto;
import capaModelo.AgrupadorMenu;
import capaModelo.Tienda;
import org.apache.log4j.Logger;
import com.mysql.jdbc.ResultSetMetaData;
/**
 * Clase que se encarga de implementar todo lo relacionado con la base de datos de la entidad impuestos
 * @author JuanDavid
 *
 */
public class ImpuestoDAO {
	
/**
 * M�todo que se encarga de retonar todos los impuestos parametrizado en el sistema.
 * @return Retorna un arrayList con tipos de datos gen�ricos.
 */
	public static ArrayList obtenerImpuesto(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList impuestos = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from impuesto";
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
				impuestos.add(fila);
				
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
		return(impuestos);
		
	}
	
	/**
	 * Metodo que se encarga de retornar el valor del porcentaje de un impuesto dado un idImpuesto determinado.
	 * @param idImpuesto
	 * @return
	 */
	public static double obtenerImpuesto(int idImpuesto, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		double porcImpuesto = 0;
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select valorporcentaje from impuesto where idimpuesto = " + idImpuesto;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
						
			while(rs.next()){
				porcImpuesto = rs.getDouble("valorporcentaje");
				
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
		return(porcImpuesto);
		
	}
	
	/**
	 * M�todo de la capa DAO que se encarga de insertar una entidad impuesto.
	 * @param impuesto Recibe un objeto de tipo impuesto del cual se extrae la informaci�n para la inserci�n.
	 * @return Se retorna un valor entero con el idimpuesto creado en la base de datos
	 */
	public static int insertarImpuesto(Impuesto impuesto, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		int idImpuestoIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into impuesto (descripcion, valorporcentaje) values ('" + impuesto.getDescripcion() + "', " + impuesto.getValorPorcentaje() + ")"; 
			if(auditoria)
			{
				logger.info(insert);
			}
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idImpuestoIns=rs.getInt(1);
				if(auditoria)
				{
					logger.info("id impuesto insertada en bd " + idImpuestoIns);
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
		return(idImpuestoIns);
	}
	
	/**
	 * M�todo que se encarga de eliminar un determinado en impuesto, teniendo en cuenta el idimpuesto pasado como par�metro
	 * @param idImpuesto Se recibe como par�metro el idimpuesto que se desea eliminar, teniendo en cuenta que es la 
	 * clave primar�a de la tabla.
	 * @return Se retorna un valor booleano que indica si el resultado del proceso fue satisfactorio o no.
	 */
	public static boolean eliminarImpuesto(int idImpuesto, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from impuesto where idimpuesto = " + idImpuesto; 
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
	 * M�todo de la capa DAO que se encarga de editar un impuesto ya existente.
	 * @param impuesto Recibe como par�metro un objeto de la entidad impuesto con base en el cual se realiza la modificaci�n
	 * @return Se retorna un valor booleano indicando si el proceso fue o no satisfactorio
	 */
	public static boolean EditarImpuesto(Impuesto impuesto, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update impuesto set descripcion = '" + impuesto.getDescripcion() + "' , valorporcentaje = " + impuesto.getValorPorcentaje() + " where idimpuesto = " + impuesto.getIdImpuesto() ; 
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
}
