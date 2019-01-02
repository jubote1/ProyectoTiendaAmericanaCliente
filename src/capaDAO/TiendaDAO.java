package capaDAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import capaModelo.Usuario;
import capaConexion.ConexionBaseDatos;
import capaModelo.FechaSistema;
import capaModelo.Tienda;
import org.apache.log4j.Logger;
/**
 * Clase que se encarga de implementar todo lo relacionado con la base de datos de la entidad tienda.
 * @author JuanDavid
 *
 */
public class TiendaDAO {
	
/**
 * Método que se encarga de retornar todas las entidades Tiendas definidas en la base de datos
 * @return Se retorna un ArrayList con todas las entidades Tiendas definidas en la base de datos.
 */
	public static Tienda obtenerTienda(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		Tienda tien = new Tienda();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from tienda";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			String direccion, telefono, razonSocial, tipoContribuyente,resolucion, fechaResolucion, ubicacion,identificacion, fechaApertura, fechaUltimoCierre;
			long numeroInicialResolucion;
			long numeroFinalResolucion;
			while(rs.next()){
				int idTienda = rs.getInt("idtienda");
				String nombre = rs.getString("nombretienda");
				String urlcontact = rs.getString("urlcontact");
				direccion = rs.getString("direccion");
				telefono = rs.getString("telefono");
				razonSocial = rs.getString("razon_social");
				tipoContribuyente = rs.getString("tipo_contribuyente");
				resolucion = rs.getString("resolucion");
				fechaResolucion = rs.getString("fecha_resolucion");
				ubicacion = rs.getString("ubicacion");
				identificacion = rs.getString("identificacion");
				fechaApertura = rs.getString("fecha_apertura");
				fechaUltimoCierre = rs.getString("fecha_ultimo_cierre");
				numeroInicialResolucion = rs.getLong("numinicialresolucion");
				numeroFinalResolucion = rs.getLong("numfinalresolucion");
				tien = new Tienda(idTienda, nombre, urlcontact, direccion, telefono, razonSocial, tipoContribuyente, resolucion, fechaResolucion, ubicacion, identificacion, fechaApertura, fechaUltimoCierre, numeroInicialResolucion, numeroFinalResolucion);
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
		return(tien);
		
	}
	
	public static boolean ActualizarFechaSistemaApertura(String fecha, boolean auditoria)
	{
		boolean respuesta = false;
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update tienda set fecha_apertura = '" +fecha +"'";
			if(auditoria)
			{
				logger.info(update);
			}
			stm.executeUpdate(update);
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.info(e.toString());
			try
			{
				con1.close();
				return false;
			}catch(Exception e1)
			{
			}
		}
		return(true);
	}
	
	public static boolean actualizarFechaUltimoCierre(boolean auditoria)
	{
		boolean respuesta = false;
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update tienda set fecha_ultimo_cierre = DATE_ADD(fecha_ultimo_cierre, INTERVAL 1 DAY)";
			if(auditoria)
			{
				logger.info(update);
			}
			stm.executeUpdate(update);
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.info(e.toString());
			try
			{
				con1.close();
				return false;
			}catch(Exception e1)
			{
			}
		}
		return(true);
	}
	
	public static FechaSistema obtenerFechasSistema(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		FechaSistema fechaSistema = new FechaSistema();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select fecha_apertura, fecha_ultimo_cierre from tienda";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			String fechaApertura = "";
			String fechaUltimoCierre = "";
			while(rs.next()){
				
				fechaApertura = rs.getString("fecha_apertura");
				fechaUltimoCierre = rs.getString("fecha_ultimo_cierre");
				
			}
			fechaSistema = new FechaSistema(fechaApertura, fechaUltimoCierre);
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
		return(fechaSistema);
	}
	
	public static boolean actualizarTienda(Tienda tienda, boolean auditoria)
	{
		boolean respuesta = false;
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update tienda set nombretienda  = '" + tienda.getNombretienda() + "' , urlcontact = '" + tienda.getUrlContact() + "' , direccion = '"+ tienda.getDireccion() + "' , telefono = '" + tienda.getTelefono() + "' , razon_social = '" + tienda.getRazonSocial() + "' , tipo_contribuyente = '" + tienda.getTipoContribuyente() + "' , resolucion = '" + tienda.getResolucion() + "' , fecha_resolucion = '" + tienda.getFechaResolucion() + "' , ubicacion = '" + tienda.getUbicacion() + "' , identificacion = '" + tienda.getIdentificacion() + "'";
			if(auditoria)
			{
				logger.info(update);
			}
			stm.executeUpdate(update);
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.info(e.toString());
			try
			{
				con1.close();
				return false;
			}catch(Exception e1)
			{
			}
		}
		return(true);
	}
	
	public static String verificarNumResolucion(int numInicial, int numFinal, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		String respuesta = "";
		int cantidad = 0 ;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select count(*) from pedido where idpedidotienda >= " + numInicial + " and idpedidotienda <= " + numFinal;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				try
				{
					cantidad = rs.getInt(0);
				}catch(Exception e)
				{
					cantidad = 0;
				}
				
				
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
		if(cantidad == 0)
		{
			return("OK");
		}
		else
		{
			return("Existen pedidos entre el rango seleccionado");
		}
		
	}
	
	public static String actualizarResolucionTienda(int numInicial, int numFinal, boolean auditoria)
	{
		String  respuesta = "";
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "ALTER TABLE pedido AUTO_INCREMENT = " + numInicial;
			logger.info(update);
			stm.executeUpdate(update);
			update = "update tienda set numinicialresolucion = " + numInicial + " , numfinalresolucion = " + numFinal;
			if(auditoria)
			{
				logger.info(update);
			}
			stm.executeUpdate(update);
 			stm.close();
			con1.close();
			respuesta = "OK";
		}catch (Exception e){
			logger.info(e.toString());
			try
			{
				con1.close();
				respuesta = respuesta + "Error al fijar el idPedidoTienda en base de datos";
			}catch(Exception e1)
			{
			}
		}
		return(respuesta);
	}
	
	/**
	 * Método en la capa DAO que se encarga de retornar el idTeinda del sistema con el fin de fijarlo y tenerlo como parámetro
	 * @param auditoria
	 * @return
	 */
	public static int obtenerIdTienda(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		int idTienda = 0;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select idtienda from tienda";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			String direccion, telefono, razonSocial, tipoContribuyente,resolucion, fechaResolucion, ubicacion,identificacion, fechaApertura, fechaUltimoCierre;
			while(rs.next()){
				idTienda = rs.getInt("idtienda");
				
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
		return(idTienda);
		
	}
	
}
