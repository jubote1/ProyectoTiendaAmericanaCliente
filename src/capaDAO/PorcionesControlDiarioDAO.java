package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.mysql.jdbc.ResultSetMetaData;

import capaConexion.ConexionBaseDatos;
import capaModelo.ItemInventario;
import capaModelo.PorcionesControlDiario;

public class PorcionesControlDiarioDAO {
	
	
	public static PorcionesControlDiario obtenerPorcionesControlDiario(String fecha,boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		PorcionesControlDiario estActual = new PorcionesControlDiario(fecha,0,0,0,0,false);
		try
		{
			//Vamos a verificar si existe control de porciones para el d�a en cuesti�n
			Statement stm = con1.createStatement();
			String consulta = "select * from porciones_control_diario where fecha_control = '" + fecha + "'";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			boolean existeControl = false;
			String fechaControl = "";
			int porcion = 0;
			int porcionGaseosa = 0;
			int porcionEmpleado = 0;
			int porcionDesecho = 0;
			int facturado = 0;
			boolean fact = false;
			while(rs.next()){
				existeControl = true;
				porcion = rs.getInt("porcion");
				porcionGaseosa = rs.getInt("porcion_gaseosa");
				porcionEmpleado = rs.getInt("porcion_empleado");
				porcionDesecho = rs.getInt("porcion_desecho");
				facturado = rs.getInt("facturado");
				if(facturado == 1)
				{
					fact = true;
				}
			}
			estActual = new PorcionesControlDiario(fecha, porcion, porcionGaseosa, porcionEmpleado, porcionDesecho,fact);
			//Validamos si no se retorno registro en cuyo caso se debe hacer la inserci�n
			if(!existeControl)
			{
				String insert = "insert into porciones_control_diario (fecha_control) values ('"+fecha+"')" ;
				stm.executeUpdate(insert);
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
		return(estActual);
		
	}
	
	//AUMENTO
	
	public static boolean aumentarPorcion(String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update porciones_control_diario set porcion = porcion +1  where fecha_control ='" + fecha +"'";
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
	
	public static boolean aumentarPorcionGaseosa(String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update porciones_control_diario set porcion_gaseosa = porcion_gaseosa +1  where fecha_control ='" + fecha +"'";
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
	
	public static boolean aumentarPorcionEmpleado(String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update porciones_control_diario set porcion_empleado = porcion_empleado +1  where fecha_control ='" + fecha +"'";
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

	public static boolean aumentarPorcionDesecho(String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update porciones_control_diario set porcion_desecho = porcion_desecho +1  where fecha_control ='" + fecha +"'";
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
	
	//DISMINUIR
	
	public static boolean disminuirPorcion(String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update porciones_control_diario set porcion = porcion  - 1  where fecha_control ='" + fecha +"'";
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
	
	public static boolean disminuirPorcionGaseosa(String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update porciones_control_diario set porcion_gaseosa = porcion_gaseosa - 1  where fecha_control ='" + fecha +"'";
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
	
	public static boolean disminuirPorcionEmpleado(String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update porciones_control_diario set porcion_empleado = porcion_empleado - 1  where fecha_control ='" + fecha +"'";
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

	public static boolean disminuirPorcionDesecho(String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update porciones_control_diario set porcion_desecho = porcion_desecho - 1   where fecha_control ='" + fecha +"'";
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
	
	public static boolean facturarPorciones(String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update porciones_control_diario set facturado = 1   where fecha_control ='" + fecha +"'";
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
