package capaDAO;
import capaModelo.TipoEmpleado;
import capaModelo.Usuario;

import capaConexion.ConexionBaseDatos;
import java.sql.Connection;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.sql.ResultSet;
import org.apache.log4j.Logger;

import com.mysql.jdbc.ResultSetMetaData;
/**
 * Clase que se encarga de implementar toda la interacci�n con la base de datos para la entidad Usuario.
 * @author JuanDavid
 *
 */
public class UsuarioDAO {

	/**
	 * M�todo que se encarga de validar la existencia y de un usuario y su contrase�a en la base de datos.
	 * @param usuario Se recibe como par�metro un objeto MOdelo Usuario, el cual trae la informaci�n base para la validaci�n,
	 * autenticaci�n del usuario.
	 * @return Se retorna un valor booleano que indica si el proceso de autenticaci�n es satifactorio o no.
	 */
	public static int validarUsuario(Usuario usuario, String servidor, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1;
		con1 = con.obtenerConexionBDLocal();
		int cantidad=0;
		int idUsuario = 0;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select nombre_largo, id, administrador, idtipoempleado,tipoinicio, ingreso, caducado from usuario where nombre = '" + usuario.getNombreUsuario() + "' and password = '" + usuario.getContrasena()+"'";
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
				int idTipoEmpleado = rs.getInt("idtipoempleado");
				int caducado = rs.getInt("caducado");
				usuario.setIdTipoEmpleado(idTipoEmpleado);
				usuario.setNombreLargo(nombreUsuario);
				usuario.setIdUsuario(idUsuario);
				usuario.setCaducado(caducado);
				int ingreso = rs.getInt("ingreso");
				usuario.setIngreso(ingreso);
				if(ingreso == 0)
				{
					darIngresoEmpleado(idUsuario, auditoria);
				}
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
	 * M�todo que se encarga de validar si un usuario existe o no en la base de datos
	 * @param usuario Recibe como par�metro un objeto Modelo Usuario con base en el cual se realiza la consulta.
	 * @return Se retorna un valor booleano con base en el cual se realiza la validaci�n del usuario en base de datos
	 * 
	 */
	public static String validarAutenticacion(Usuario usuario, String servidor,  boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1;
		if(servidor.equals(new String("")))
		{
			con1 = con.obtenerConexionBDLocal();
		}else
		{
			con1 = con.obtenerConexionBDGeneral(servidor);
		}
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
	 * M�todo que se encarga de validar si un usuario existe o no en la base de datos
	 * @param usuario Recibe como par�metro un objeto Modelo Usuario con base en el cual se realiza la consulta.
	 * @return Se retorna un valor booleano con base en el cual se realiza la validaci�n del usuario en base de datos
	 * 
	 */
	public static Usuario validarAutenticacionRapida(String claveRapida, String servidor,  boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1;
		con1 = con.obtenerConexionBDLocal();
		Usuario usuario = new Usuario(0,"","","", 0, "", false);
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from usuario where claverapida = '" + claveRapida + "'";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				
				try{
					int idUsuario = rs.getInt("id");
					usuario.setIdUsuario(rs.getInt("id"));
					usuario.setNombreUsuario(rs.getString("nombre"));
					usuario.setNombreLargo(rs.getString("nombre_largo"));
					usuario.setIdTipoEmpleado(rs.getInt("idtipoempleado"));
					usuario.setTipoInicio(rs.getString("tipoinicio"));
					int ingreso = rs.getInt("ingreso");
					int caducado = rs.getInt("caducado");
					usuario.setIngreso(ingreso);
					usuario.setCaducado(caducado);
					String administrador = rs.getString("administrador");
					if(administrador.equals(new String("S")))
					{
						usuario.setAdministrador(true);
					}else
					{
						usuario.setAdministrador(false);
					}
					if(ingreso == 0)
					{
						darIngresoEmpleado(idUsuario, auditoria);
					}
					break;
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
		return(usuario);
	}
	
	/**
	 * M�todo que se encarga de darle marca a un empleado qeu se loguea por primera vez en el d�a en un d�a determinado.
	 * @param idUsuario
	 * @param auditoria
	 */
	public static void darIngresoEmpleado(int idUsuario, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update usuario set ingreso = 1 where id = " + idUsuario;
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
			
		}
	}
	
	public static boolean asignarClaveRapida(Usuario usuario, String claveRapida, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta; 
		try
		{
			Statement stm = con1.createStatement();
			String update = "update usuario set claverapida = '"+ claveRapida +"' , caducado = 0 where id = " + usuario.getIdUsuario();
			if(auditoria)
			{
				logger.info(update);
			}
			stm.executeUpdate(update);
			stm.close();
			con1.close();
			respuesta = true;
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
	
	public static void darSalidaTodosEmpleados(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update usuario set ingreso = 0";
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
			
		}
	}
	
	/**
	 * M�todo que se encarga de la validaci�n de un idUsuario determinado est� o no creado en el sistema.
	 * @param idUsuario par�metro con base en la cual se valida o no la existencia del usuario en base de datos
	 * @return Un valor booleano con la validaci�n o no de la existencia del usuario.
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
	 * M�todo que se encarga de obtener en un ArrayList los empleados del sistema
	 * @return
	 */
	public static ArrayList obtenerEmpleadosGeneral(String bdGeneral, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDGeneral(bdGeneral);
		ArrayList empleados = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select id,nombre, nombre_largo, administrador, tipoinicio  from empleado ";
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
	
	public static ArrayList obtenerEmpleadosGeneralFiltroNom(String filtroNombre , String bdGeneral, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDGeneral(bdGeneral);
		ArrayList empleados = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select id,nombre, nombre_largo, administrador, tipoinicio  from empleado where nombre_largo like '%" + filtroNombre + "%'";
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
	
	public static ArrayList obtenerEmpleados( boolean auditoria)
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
	 * M�todo qeu se encarga de insertar un empleado en el sistema
	 * @param empleado Se recibe un objeto de tipo usuario con la informaci�n del empleado que termina siendo un autor del sistema
	 * @return Se retorna un entero con id asignado por el sistema en la inserci�n.
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
	
	public static int insertarEmpleadoGeneral(Usuario empleado, String bdGeneral, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		int idEmpleadoIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDGeneral(bdGeneral);
		String administrador = "N";
		try
		{
			if(empleado.isAdministrador())
			{
				administrador = "S" ;
			}
			Statement stm = con1.createStatement();
			String insert = "insert into empleado (nombre, password,  nombre_largo,administrador, idtipoempleado, tipoinicio) values ('" + empleado.getNombreUsuario() + "' , '" + empleado.getContrasena() + "' , '" + empleado.getNombreLargo() + "' , '" + administrador + "', " + empleado.getidTipoEmpleado() + " , '" + empleado.getTipoInicio() + "')"; 
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
			System.out.println(e.toString());
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
	 * M�todo que se encarga de eliminar un empleado con base en el idEmppleado recibido como p�rametro
	 * @param idEmpleado se recibe el idempleado el cual es el identificador �nico a nivel de base de datos
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
	
	public static boolean editarEmpleadoGeneral(Usuario empleadoEdi, String bdGeneral, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = false;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDGeneral(bdGeneral);
		try
		{
			String administrador = "N";
			if(empleadoEdi.isAdministrador())
			{
				administrador = "S";
			}
			Statement stm = con1.createStatement();
			String update = "update empleado set nombre = '" + empleadoEdi.getNombreUsuario()  +"' , nombre_largo = '"+ empleadoEdi.getNombreLargo() +"' , administrador ='"+ administrador +"' , idtipoempleado= "+ empleadoEdi.getidTipoEmpleado() +", tipoinicio = '"+ empleadoEdi.getTipoInicio()+ "'  where id = " + empleadoEdi.getIdUsuario();
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
	
	
	public static Usuario obtenerEmpleadoGeneral(int idEmpleado, String bdGeneral, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDGeneral(bdGeneral);
		Usuario empConsulta = new Usuario(0, "","","",0,"", false);
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from empleado where id = " + idEmpleado;
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
			System.out.println(e.toString() + e.getMessage());
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
			String consulta = "select * from usuario a, tipo_empleado b where a.idtipoempleado = b.idtipoempleado and b.es_domiciliario = 1 and a.ingreso = 1" ;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			String nombre = "", nombreLargo ="", tipoInicio = "";
			String strAdmin;
			boolean administrador = false;
			int idTipoEmpleado = 0, idEmpleado = 0;
			int estadoDomiciliario = 0;
			while(rs.next()){
				nombre = rs.getString("nombre");
				nombreLargo = rs.getString("nombre_largo");
				tipoInicio = rs.getString("tipoinicio");
				strAdmin = rs.getString("administrador");
				idEmpleado = rs.getInt("id");
				estadoDomiciliario = rs.getInt("estadodomiciliario");
				if(strAdmin.equals(new String("S")))
				{
					administrador = true;
				}
				idTipoEmpleado = rs.getInt("idtipoempleado");
				empConsulta = new Usuario(idEmpleado, nombre,"", nombreLargo, idTipoEmpleado,tipoInicio, administrador);
				empConsulta.setEstadoDomiciliario(estadoDomiciliario);
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
	
	public static void salidaDomiciliario(int idEmpleado, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = false;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update usuario set estadodomiciliario = 1 where id = " + idEmpleado;
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
	}
	
	
	public static void entradaDomiciliario(int idEmpleado, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = false;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update usuario set estadodomiciliario = 0 where id = " + idEmpleado;
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
	}
	
	
	public static boolean actualizarUltimoIngreso(Usuario usuario, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = false;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		//Realizamos la formaci�n del campo de fecha de hoy
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaHoy = new Date();
		try {
			//NO es necesario ya fechaHoy tiene la fecha actual
			fechaHoy = sdf.parse(sdf.format(new Date()));
			System.out.println("fecha hoy"+ fechaHoy);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String fechaActual = sdf.format(fechaHoy);
		try
		{
			Statement stm = con1.createStatement();
			String update = "update usuario set ultimo_ingreso  = '"+ fechaActual +"' where nombre =  '" + usuario.getNombreUsuario() + "' and password = '" + usuario.getContrasena()+"'";
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
	
	/**
	 * Actualizaci�n de �ltimo ingreso al sistema tomando como base la clave r�pida
	 * @param usuario
	 * @param auditoria
	 * @return
	 */
	public static boolean actualizarUltimoIngresoCR(String claveRapida, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = false;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		//Realizamos la formaci�n del campo de fecha de hoy
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaHoy = new Date();
		try {
			//NO es necesario ya fechaHoy tiene la fecha actual
			fechaHoy = sdf.parse(sdf.format(new Date()));
			System.out.println("fecha hoy"+ fechaHoy);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String fechaActual = sdf.format(fechaHoy);
		try
		{
			Statement stm = con1.createStatement();
			String update = "update usuario set ultimo_ingreso  = '"+ fechaActual +"' where claverapida = '" + claveRapida + "'";
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
	
	/**
	 * M�todo que se encarga de retornar en un booleano la validaci�n si un determinado usuario es o no administrador.
	 * @param idUsuario del usuario del cual se desea validar la condici�n de administrador o no.
	 * @param auditoria
	 * @return
	 */
	public static boolean validarEsAdministrador(int idUsuario, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false; 
		try
		{
			Statement stm = con1.createStatement();
			String select = "SELECT * FROM usuario a, tipo_empleado b WHERE a.idtipoempleado = b.idtipoempleado AND b.es_administrador = 1 AND a.id = " + idUsuario;
			ResultSet rs;
			if(auditoria)
			{
				logger.info(select);
			}
			rs = stm.executeQuery(select);
			while(rs.next())
			{
				respuesta = true;
				break;
			}
			rs.close();
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
