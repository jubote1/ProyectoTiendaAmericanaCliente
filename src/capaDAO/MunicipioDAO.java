package capaDAO;

import java.sql.Connection;
import org.apache.log4j.Logger;

import com.mysql.jdbc.ResultSetMetaData;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import capaModelo.Impuesto;
import capaModelo.Municipio;
import capaConexion.ConexionBaseDatos;

/**
 * Clase que se encarga de la implementación de toda la interacción con la base de datos para la entidad Municipio.
 * @author JuanDavid
 *
 */
public class MunicipioDAO {
	
	/**
	 * Método que se encarga de retornar la información de todos los municipios definidos en el sistema.
	 * @return Se retorna un ArrayList con todos los municipios definidos en el sistema
	 */
	public static ArrayList obtenerMunicipios(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList municipios = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from municipio";
			if(auditoria)
			{
				logger.info(consulta);
			}logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
			int numeroColumnas = rsMd.getColumnCount();
			
			while(rs.next()){
				String [] fila = new String[numeroColumnas];
				for(int y = 0; y < numeroColumnas; y++)
				{
					fila[y] = rs.getString(y+1);
				}
				municipios.add(fila);
				
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.error(e.toString());
			
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(municipios);
		
	}
	
	
	/**
	 * Método de la capa DAO que se encarga de retornar los Municipios del sistema en un arrayList de objetos de tipo Municipio
	 * @return Un ArrayList con los Municipios.
	 */
	public static ArrayList<Municipio> obtenerMunicipiosObjeto(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<Municipio> municipios = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from municipio";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idMunicipio;
			String municipio;
			while(rs.next()){
				idMunicipio = rs.getInt("idmunicipio");
				municipio = rs.getString("nombre");
				Municipio mun = new Municipio(idMunicipio,municipio);
				municipios.add(mun);
				
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
		return(municipios);
		
	}
	
	/**
	 * Método qeu se encarga de retornar el id de un Municipio dado el nombre de un Munipio
	 * @param municipio Se recibe como parámetro un String con el nombre del Municipio.
	 * @return Se retorna un entero con el id del municipio según el nombre del Municipio enviado como parámetro.
	 */
	public static int obteneridMunicipio(String municipio, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		int idmunicipio=0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select idmunicipio from municipio where nombre = '"+ municipio + "'";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				idmunicipio = rs.getInt("idmunicipio");
				break;
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(idmunicipio);
	}
	
	public static Municipio obtenerMunicipio(int idmunicipio, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		String nombreMunicipio= "";
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		Municipio municipio = new Municipio(0,"");
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select nombre from municipio where idmunicipio = '"+ idmunicipio + "'";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				nombreMunicipio = rs.getString("nombre");
				municipio = new Municipio (idmunicipio, nombreMunicipio);
				break;
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(municipio);
	}
	
	/**
	 * Método de la capa DAO que permite la inserción de objetos de tipo municipio
	 * @param municipio Se recibe como parámetro un objeto de l capaa municipio
	 * @return se retorna un valor intero con el id municipio insertado.
	 */
	public static int insertarMunicipio(Municipio municipio, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		int idMunicipioIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into municipio (nombre) values ('" + municipio.getNombre() + "'" + ")"; 
			if(auditoria)
			{
				logger.info(insert);
			}
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idMunicipioIns=rs.getInt(1);
				if(auditoria)
				{
					logger.info("id municipio insertada en bd " + idMunicipioIns);
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
		return(idMunicipioIns);
	}
	
	
	/**
	 * Método que se encarga en la capa DAO de la eliminación de la entidad Municpio
	 * @param idMunicipio se retorna valor qeu hace las veces de clave único de la table
	 * @return Se retorna booleano que indica el resultado del proceso.
	 */
	public static boolean eliminarMunicipio(int idMunicipio, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from municipio where idmunicipio = " + idMunicipio; 
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
	 * Método de la capa DAO que se encarga de editar un Municipio según la información pasada como parámetro.
	 * @param municipio Se recibe como parámetro un objeto de la entidad Municipio.
	 * @return Se retorna un valor booleano indicando el resultado del proceso.
	 */
	public static boolean editarMunicipio(Municipio municipio, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update municipio set nombre = '" + municipio.getNombre() + "'" + " where idmunicipio = " + municipio.getIdmunicipio() ; 
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
