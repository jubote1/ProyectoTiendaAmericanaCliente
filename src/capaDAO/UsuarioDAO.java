package capaDAO;
import capaModelo.TipoEmpleado;
import capaModelo.Usuario;

import capaConexion.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
import org.apache.log4j.Logger;

import com.mysql.jdbc.ResultSetMetaData;
/**
 * Clase que se encarga de implementar toda la interacción con la base de datos para la entidad Usuario.
 * @author JuanDavid
 *
 */
public class UsuarioDAO {

	/**
	 * Método que se encarga de validar la existencia y de un usuario y su contraseña en la base de datos.
	 * @param usuario Se recibe como parámetro un objeto MOdelo Usuario, el cual trae la información base para la validación,
	 * autenticación del usuario.
	 * @return Se retorna un valor booleano que indica si el proceso de autenticación es satifactorio o no.
	 */
	public static int validarUsuario(Usuario usuario, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		int cantidad=0;
		int idUsuario = 0;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select nombre_largo, id, administrador, idtipoempleado,tipoinicio from usuario where nombre = '" + usuario.getNombreUsuario() + "' and password = '" + usuario.getContrasena()+"'";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			
			while(rs.next()){
				cantidad++;
				idUsuario = rs.getInt("id");
				String nombreUsuario = rs.getString("nombre_largo");
				String administrador = rs.getString("administrador");
				String tipoInicio = rs.getString("tipoinicio");
				usuario.setNombreLargo(nombreUsuario);
				usuario.setIdUsuario(idUsuario);
				if(administrador.equals(new String("S")))
				{
					usuario.setAdministrador(true);
				}
				else
				{
					usuario.setAdministrador(false);
				}
				usuario.setTipoInicio(tipoInicio);
				break;
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(idUsuario);
		
	}
	
	/**
	 * Método que se encarga de validar si un usuario existe o no en la base de datos
	 * @param usuario Recibe como parámetro un objeto Modelo Usuario con base en el cual se realiza la consulta.
	 * @return Se retorna un valor booleano con base en el cual se realiza la validación del usuario en base de datos
	 * 
	 */
	public static String validarAutenticacion(Usuario usuario, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		String resultado = "";
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select administrador from usuario where nombre = '" + usuario.getNombreUsuario() + "'";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				
				try{
					resultado = rs.getString(1);
					
				}catch(Exception e){
					
					
				}
				rs.close();
				stm.close();
				con1.close();
			}
		}catch (Exception e){
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(resultado);
	}
	
	/**
	 * Método que se encarga de la validación de un idUsuario determinado está o no creado en el sistema.
	 * @param idUsuario parámetro con base en la cual se valida o no la existencia del usuario en base de datos
	 * @return Un valor booleano con la validación o no de la existencia del usuario.
	 */
	public static boolean validarExistenciaUsuario(int idUsuario, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		int cantidad=0;
		boolean respuesta = false;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from usuario where id = " + idUsuario;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			
			while(rs.next()){
				respuesta = true;
				break;
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(respuesta);
		
	}
	
	/**
	 * Método que se encarga de obtener en un ArrayList los empleados del sistema
	 * @return
	 */
	public static ArrayList obtenerEmpleados(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList empleados = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select id,nombre, nombre_largo, administrador, tipoinicio  from usuario ";
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
				empleados.add(fila);
				
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
		return(empleados);
		
	}
	
	
		
	/**
	 * Método qeu se encarga de insertar un empleado en el sistema
	 * @param empleado Se recibe un objeto de tipo usuario con la información del empleado que termina siendo un autor del sistema
	 * @return Se retorna un entero con id asignado por el sistema en la inserción.
	 */
	public static int insertarEmpleado(Usuario empleado, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		int idEmpleadoIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		String administrador = "N";
		try
		{
			if(empleado.isAdministrador())
			{
				administrador = "S" ;
			}
			Statement stm = con1.createStatement();
			String insert = "insert into usuario (nombre, password,  nombre_largo,administrador, idtipoempleado, tipoinicio) values ('" + empleado.getNombreUsuario() + "' , '" + empleado.getContrasena() + "' , '" + empleado.getNombreLargo() + "' , '" + administrador + "', " + empleado.getidTipoEmpleado() + " , '" + empleado.getTipoInicio() + "')"; 
			if(auditoria)
			{
				logger.info(insert);
			}
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idEmpleadoIns=rs.getInt(1);
				logger.info("id Empleado insertada en bd " + idEmpleadoIns);
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
		return(idEmpleadoIns);
	}
	
	/**
	 * Método que se encarga de eliminar un empleado con base en el idEmppleado recibido como párametro
	 * @param idEmpleado se recibe el idempleado el cual es el identificador único a nivel de base de datos
	 * @return se retorna un valor booleano con el resultado del proceso.
	 */
	public static boolean eliminarEmpleado(int idEmpleado, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from usuario where itipoempleado = " + idEmpleado; 
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
	
	
	public static boolean editarEmpleado(Usuario empleadoEdi, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = false;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			String administrador = "N";
			if(empleadoEdi.isAdministrador())
			{
				administrador = "S";
			}
			Statement stm = con1.createStatement();
			String update = "update usuario set nombre = '" + empleadoEdi.getNombreUsuario()  +"' , nombre_largo = '"+ empleadoEdi.getNombreLargo() +"' , administrador ='"+ administrador +"' , idtipoempleado= "+ empleadoEdi.getidTipoEmpleado() +", tipoinicio = '"+ empleadoEdi.getTipoInicio()+ "'  where id = " + empleadoEdi.getIdUsuario();
			if(auditoria)
			{
				logger.info(update);
			}
			stm.executeUpdate(update);
			respuesta = true;
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			respuesta = false;
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
			
		}
		return(respuesta);
	}
	
	public static Usuario obtenerEmpleado(int idEmpleado, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		Usuario empConsulta = new Usuario(0, "","","",0,"", false);
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from usuario where id = " + idEmpleado;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			String nombre = "", nombreLargo ="", tipoInicio = "";
			String strAdmin;
			boolean administrador = false;
			int idTipoEmpleado = 0;
			while(rs.next()){
				nombre = rs.getString("nombre");
				nombreLargo = rs.getString("nombre_largo");
				tipoInicio = rs.getString("tipoinicio");
				strAdmin = rs.getString("administrador");
				if(strAdmin.equals(new String("S")))
				{
					administrador = true;
				}
				idTipoEmpleado = rs.getInt("idtipoempleado");
				empConsulta = new Usuario(idEmpleado, nombre,"", nombreLargo, idTipoEmpleado,tipoInicio, administrador);
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
		return(empConsulta);
		
	}
	
	/**
	 * 
	 * @param auditoria
	 * @return
	 */
	public static ArrayList<Usuario> obtenerDomiciliarios( boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		Usuario empConsulta = new Usuario(0, "","","",0,"", false);
		ArrayList<Usuario> domiciliarios = new ArrayList();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from usuario a, tipo_empleado b where a.idtipoempleado = b.idtipoempleado and b.es_domiciliario = 1" ;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			String nombre = "", nombreLargo ="", tipoInicio = "";
			String strAdmin;
			boolean administrador = false;
			int idTipoEmpleado = 0, idEmpleado = 0;
			while(rs.next()){
				nombre = rs.getString("nombre");
				nombreLargo = rs.getString("nombre_largo");
				tipoInicio = rs.getString("tipoinicio");
				strAdmin = rs.getString("administrador");
				idEmpleado = rs.getInt("id");
				if(strAdmin.equals(new String("S")))
				{
					administrador = true;
				}
				idTipoEmpleado = rs.getInt("idtipoempleado");
				empConsulta = new Usuario(idEmpleado, nombre,"", nombreLargo, idTipoEmpleado,tipoInicio, administrador);
				domiciliarios.add(empConsulta);
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
		return(domiciliarios);
		
	}
	
}
