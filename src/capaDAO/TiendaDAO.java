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
			String direccion, telefono, razonSocial, tipoContribuyente,resolucion, fechaResolucion, ubicacion,identificacion, fechaApertura, fechaUltimoCierre, puntoVenta;
			long numeroInicialResolucion;
			long numeroFinalResolucion;
			int deltaNumeracion;
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
				puntoVenta = rs.getString("puntoventa");
				deltaNumeracion = rs.getInt("delta_numeracion");
				tien = new Tienda(idTienda, nombre, urlcontact, direccion, telefono, razonSocial, tipoContribuyente, resolucion, fechaResolucion, ubicacion, identificacion, fechaApertura, fechaUltimoCierre, numeroInicialResolucion, numeroFinalResolucion, puntoVenta,deltaNumeracion);
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
	
	public static boolean actualizarFechaUltimoCierre(String fecha, boolean auditoria)
	{
		boolean respuesta = false;
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			//String update = "update tienda set fecha_ultimo_cierre = DATE_ADD(fecha_ultimo_cierre, INTERVAL 1 DAY)";
			// 01-05/2019-JDBD
			//Se realiza cambio para prevenir error inconveniente de avance de fecha de último cierre y mala generación de la reportería
			String update = "update tienda set fecha_ultimo_cierre = '"+fecha+"'";
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
	
	public static boolean disminuirFechaUltimoCierre(boolean auditoria)
	{
		boolean respuesta = false;
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update tienda set fecha_ultimo_cierre = DATE_SUB(fecha_ultimo_cierre, INTERVAL 1 DAY)";
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
	
	
	public static boolean fijarFechaCierreCierre(String fecha, boolean auditoria)
	{
		boolean respuesta = false;
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update tienda set fecha_apertura = '"+fecha+"'";
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
			String update = "update tienda set nombretienda  = '" + tienda.getNombretienda() + "' , urlcontact = '" + tienda.getUrlContact() + "' , direccion = '"+ tienda.getDireccion() + "' , telefono = '" + tienda.getTelefono() + "' , razon_social = '" + tienda.getRazonSocial() + "' , tipo_contribuyente = '" + tienda.getTipoContribuyente() + "' , resolucion = '" + tienda.getResolucion() + "' , fecha_resolucion = '" + tienda.getFechaResolucion() + "' , ubicacion = '" + tienda.getUbicacion() + "' , identificacion = '" + tienda.getIdentificacion() + "' , puntoventa =  '" + tienda.getPuntoVenta() + "' , delta_numeracion = " + tienda.getDeltaNumeracion();
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
	
	/**
	 * Método que se encarga de obtener el estado actual de la tienda
	 * @param auditoria
	 * @return
	 */
	public static boolean retornarEstadoTienda(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean estado = false;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select estado from tienda";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int intEstado = 0;
			while(rs.next()){
				intEstado = rs.getInt("estado");
				if(intEstado == 0)
				{
					estado = false;
				}else if(intEstado == 1)
				{
					estado = true;
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
		return(estado);
	}
	
	
	/**
	 * Método que se encarga de activar la tienda, cambiando el estado en la entidad tienda
	 * @param auditoria
	 * @return
	 */
	public static boolean activarEstadoTienda(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		try
		{
			Statement stm = con1.createStatement();
			String update = "update tienda set estado = 1";
			if(auditoria)
			{
				logger.info(update);
			}
			stm.executeUpdate(update);
			respuesta = true;
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
		return(respuesta);
	}
	
	/**
	 * Método que se encarga de desactivar la tienda, cambiando el estado en la entidad tienda
	 * @param auditoria
	 * @return
	 */
	public static boolean desactivarEstadoTienda(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		try
		{
			Statement stm = con1.createStatement();
			String update = "update tienda set estado = 0";
			if(auditoria)
			{
				logger.info(update);
			}
			stm.executeUpdate(update);
			respuesta = true;
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
		return(respuesta);
	}
	
	
	public static int retornarSecuenciaFacturacion(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		int secuenciaAct = 0;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "SELECT AUTO_INCREMENT FROM information_schema.`TABLES` WHERE table_schema ='tiendaamericana' AND TABLE_NAME = 'pedido'";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				secuenciaAct = rs.getInt("AUTO_INCREMENT");
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.info(e.toString());
			try
			{
				secuenciaAct = 0;
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(secuenciaAct);
	}
	
}
