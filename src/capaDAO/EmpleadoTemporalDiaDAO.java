package capaDAO;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.mysql.jdbc.ResultSetMetaData;

import capaConexion.ConexionBaseDatos;
import capaModelo.EmpleadoEvento;
import capaModelo.EmpleadoTemporalDia;

public class EmpleadoTemporalDiaDAO {
	
	/**
	 * Método creado para la inserción de un ingreso de un empleado temporal en un día determinado del sistema
	 * @param empTemporal
	 * @param auditoria
	 * @return
	 */
	public static boolean InsertarEmpleadoTemporalDia(EmpleadoTemporalDia empTemporal, boolean auditoria )
	{
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		String insert = "insert into empleado_temporal_dia (id,identificacion, nombre, telefono, empresa, fecha_sistema, horaingreso, horasalida, idempresa) values (" + empTemporal.getId() + " , '" + empTemporal.getIdentificacion() + "' , '" + empTemporal.getNombre() + "' , '" + empTemporal.getTelefono() + "' , '" + empTemporal.getEmpresa() + "' , '" + empTemporal.getFechaSistema() + "' , '" + empTemporal.getHoraIngreso()+ "' , '" + empTemporal.getHoraSalida() + "' , " + empTemporal.getIdEmpresa() + ")";
		Statement stm;
		try
		{
			stm = con1.createStatement();
			stm.executeUpdate(insert);
			return(true);
		}
		catch (Exception e){
			System.out.println(e.toString());
			try
			{
				con1.close();
				return(false);
			}catch(Exception e1)
			{
				return(false);
			}
			
		}
		
	}
	
	
	/**
	 * Método que se encarga de realizar la actualización de un empleado temporal y retornar un valor booleano confirmando la edición.
	 * @param empTemporal
	 * @param auditoria
	 * @return
	 */
	public static boolean editarEmpleadoTemporalDia(EmpleadoTemporalDia empTemporal, boolean auditoria )
	{
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		String update = "update empleado_temporal_dia set identificacion = '"+ empTemporal.getIdentificacion() +"', nombre = '"+ empTemporal.getNombre() +"' , telefono = '" + empTemporal.getTelefono() + "' , empresa = '"+ empTemporal.getEmpresa() +"' , horaingreso = '" + empTemporal.getHoraIngreso() +"' , horasalida = '" + empTemporal.getHoraSalida()+ "' , idempresa = " + empTemporal.getIdEmpresa()+  " where id = " + Integer.toString(empTemporal.getId()) + " and fecha_sistema = '" + empTemporal.getFechaSistema() + "'";
		Statement stm;
		try
		{
			stm = con1.createStatement();
			stm.executeUpdate(update);
			return(true);
		}
		catch (Exception e){
			System.out.println(e.toString());
			try
			{
				con1.close();
				return(false);
			}catch(Exception e1)
			{
				return(false);
			}
			
		}
		
	}
	
	
	
	
	/**
	 * Método que dada una identificación nos permite retornar la última entidad EmpleadoTemporalDia ingresada en el
	 * sistema
	 * @param identificacion
	 * @return
	 */
	public static EmpleadoTemporalDia consultarEmpleadoTemporal(String identificacion, boolean auditoria )
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		String select  = "select *  from empleado_temporal_dia where identificacion = '" + identificacion + "' order by fecha_sistema desc";
		if(auditoria)
		{
			logger.info(select);
		}
		Statement stm;
		EmpleadoTemporalDia empRespuesta = new EmpleadoTemporalDia(0,"", "", "", "", "", 0);
		try
		{
			stm = con1.createStatement();
			ResultSet rs = stm.executeQuery(select);
			String evento = "";
			int id, idEmpresa;
			String nombre, telefono, empresa;
			while(rs.next())
			{
				id = rs.getInt("id");
				nombre = rs.getString("nombre");
				telefono = rs.getString("telefono");
				empresa = rs.getString("empresa");
				idEmpresa = rs.getInt("idempresa");
				empRespuesta = new EmpleadoTemporalDia(id, identificacion,  nombre, telefono, empresa,
						"", idEmpresa);
				break;
			}
			rs.close();
			stm.close();
			con1.close();
	
			
		}
		catch (Exception e){
			System.out.println(e.toString());
			try
			{
				con1.close();
				
			}catch(Exception e1)
			{
				
			}
			
		}
		//Realizamos las validaciones de lo que retornamos
		
		return(empRespuesta);
	}
	
	public static EmpleadoTemporalDia consultarEmpleadoTemporalFecha(int id, String fechaSistema, boolean auditoria )
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		String select  = "select *  from empleado_temporal_dia where id = '" + id + "' and fecha_sistema = '" + fechaSistema + "'";
		if(auditoria)
		{
			logger.info(select);
		}
		Statement stm;
		EmpleadoTemporalDia empRespuesta = new EmpleadoTemporalDia(0,"", "", "", "", "", 0);
		try
		{
			stm = con1.createStatement();
			ResultSet rs = stm.executeQuery(select);
			String evento = "";
			String identificacion;
			String nombre, telefono, empresa;
			String horaIngreso;
			String horaSalida;
			int idEmpresa;
			while(rs.next())
			{
				idEmpresa = rs.getInt("idempresa");
				identificacion = rs.getString("identificacion");
				nombre = rs.getString("nombre");
				telefono = rs.getString("telefono");
				empresa = rs.getString("empresa");
				horaIngreso = rs.getString("horaingreso");
				horaSalida = rs.getString("horasalida");
				empRespuesta = new EmpleadoTemporalDia(id, identificacion,  nombre, telefono, empresa,
						"", idEmpresa);
				empRespuesta.setHoraIngreso(horaIngreso);
				empRespuesta.setHoraSalida(horaSalida);
				break;
			}
			rs.close();
			stm.close();
			con1.close();
	
			
		}
		catch (Exception e){
			System.out.println(e.toString());
			try
			{
				con1.close();
				
			}catch(Exception e1)
			{
				
			}
			
		}
		//Realizamos las validaciones de lo que retornamos
		
		return(empRespuesta);
	}
	
	
	public static boolean eliminarEmpleadoTemporalFecha(int id, String fechaSistema, boolean auditoria )
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		String delete  = "delete from empleado_temporal_dia where id = '" + id + "' and fecha_sistema = '" + fechaSistema + "'";
		if(auditoria)
		{
			logger.info(delete);
		}
		Statement stm;
		boolean respuesta = false;
		try
		{
			stm = con1.createStatement();
			stm.executeUpdate(delete);
			respuesta = true;			
			stm.close();
			con1.close();
	
			
		}
		catch (Exception e){
			System.out.println(e.toString());
			respuesta = false;
			try
			{
				con1.close();
				
			}catch(Exception e1)
			{
				
			}
			
		}
		//Realizamos las validaciones de lo que retornamos
		
		return(respuesta);
	}
	
	/**
	 * Método que valida si los empleados temporales de un determinado día todos se han dado hora de salida, sino enviará un false  como retorno.
	 * @param fechaSistema
	 * @param auditoria
	 * @return
	 */
	public static boolean validarHoraSalidaEmpTemporal(String fechaSistema, boolean auditoria )
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		String select  = "select * from empleado_temporal_dia where fecha_sistema = '" + fechaSistema + "' and horasalida =''";
		if(auditoria)
		{
			logger.info(select);
		}
		Statement stm;
		boolean respuesta = true;
		try
		{
			stm = con1.createStatement();
			ResultSet rs = stm.executeQuery(select);
			while(rs.next())
			{
				respuesta = false;
				break;
			}
			rs.close();
			stm.close();
			con1.close();
	
			
		}
		catch (Exception e){
			System.out.println(e.toString());
			respuesta = false;
			try
			{
				con1.close();
				
			}catch(Exception e1)
			{
				
			}
			
		}
		//Realizamos las validaciones de lo que retornamos
		
		return(respuesta);
	}
	
	
	/**
	 * Método que nos retornará si un empleado temporal se a dado o no ingreso como temporal, realizamos cambio
	 * para retornar un String con el nombre del empleado temporal si es que existe, sino se retora un String vacío
	 * @param idUsuario
	 * @param fecha
	 * @param auditoria
	 * @return
	 */
	public static String ingresoEmpleadoTemporal(int idUsuario, String fecha, boolean auditoria )
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		String select  = "select nombre  from empleado_temporal_dia where id = " + idUsuario + " and fecha_sistema = '" + fecha + "'";
		String respuesta = "";
		if(auditoria)
		{
			logger.info(select);
		}
		Statement stm;
		try
		{
			stm = con1.createStatement();
			ResultSet rs = stm.executeQuery(select);
			while(rs.next())
			{
				respuesta = rs.getString("nombre");
				break;
			}
			rs.close();
			stm.close();
			con1.close();
	
			
		}
		catch (Exception e){
			System.out.println(e.toString());
			try
			{
				con1.close();
				
			}catch(Exception e1)
			{
				
			}
			
		}
		//Realizamos las validaciones de lo que retornamos
		
		return(respuesta);
	}
	
	
	/**
	 * Método que retorna un listado de los empleados temporales que se dieron ingreso en un determinado día en el sistema
	 * @param fecha
	 * @param auditoria
	 * @return
	 */
	public static ArrayList<EmpleadoTemporalDia> obtenerEmpleadoTemporalFecha(String fecha, boolean auditoria )
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		String select  = "select a.*, b.nombre_largo  from empleado_temporal_dia a, usuario b where a.id = b.id and a.fecha_sistema = '" + fecha + "' order by a.id asc";
		ArrayList<EmpleadoTemporalDia> empleadosTemp = new ArrayList();
		if(auditoria)
		{
			logger.info(select);
		}
		Statement stm;
		EmpleadoTemporalDia empRespuesta = new EmpleadoTemporalDia(0,"", "", "", "", "", 0);
		try
		{
			stm = con1.createStatement();
			ResultSet rs = stm.executeQuery(select);
			String evento = "";
			int id, idEmpresa;
			String nombre, telefono, empresa, identificacion, usuario;
			String horaIngreso, horaSalida;
			while(rs.next())
			{
				id = rs.getInt("id");
				idEmpresa = rs.getInt("idempresa");
				usuario = rs.getString("nombre_largo");
				nombre = rs.getString("nombre");
				identificacion = rs.getString("identificacion");
				telefono = rs.getString("telefono");
				empresa = rs.getString("empresa");
				horaIngreso = rs.getString("horaingreso");
				horaSalida = rs.getString("horasalida");
				empRespuesta = new EmpleadoTemporalDia(id, identificacion, usuario + " " + nombre, telefono, empresa,
					fecha, idEmpresa);
				empRespuesta.setHoraIngreso(horaIngreso);
				empRespuesta.setHoraSalida(horaSalida);
				empleadosTemp.add(empRespuesta);
			}
			rs.close();
			stm.close();
			con1.close();
	
			
		}
		catch (Exception e){
			System.out.println(e.toString());
			try
			{
				con1.close();
				
			}catch(Exception e1)
			{
				
			}
			
		}
		//Realizamos las validaciones de lo que retornamos
		
		return(empleadosTemp);
	}


}
