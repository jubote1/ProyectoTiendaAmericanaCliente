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
	public static Tienda obtenerTienda()
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		Tienda tien = new Tienda();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from tienda";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				int idTienda = rs.getInt("idtienda");
				String nombre = rs.getString("nombretienda");
				String urlcontact = rs.getString("urlcontact");
				tien = new Tienda(idTienda, nombre, urlcontact);
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
	
	public static boolean ActualizarFechaSistemaApertura(String fecha)
	{
		boolean respuesta = false;
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update tienda set fecha_apertura = '" +fecha +"'";
			logger.info(update);
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
	
	public static boolean actualizarFechaUltimoCierre()
	{
		boolean respuesta = false;
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update tienda set fecha_ultimo_cierre = DATE_ADD(fecha_ultimo_cierre, INTERVAL 1 DAY)";
			logger.info(update);
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
	
	public static FechaSistema obtenerFechasSistema()
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		FechaSistema fechaSistema = new FechaSistema();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select fecha_apertura, fecha_ultimo_cierre from tienda";
			logger.info(consulta);
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
	
	
	
}
