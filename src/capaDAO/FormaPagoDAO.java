package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import capaModelo.FormaPago;
import capaConexion.ConexionBaseDatos;

/**
 * Clase que se encarga de implementar los m�todos que se encargar�n de la interacci�n de base de datos con la entidad
 * FormaPago
 * @author JuanDavid
 *
 */
public class FormaPagoDAO {
	
	/**
	 * M�todo que se encarga de insertar una forma de pago en la base de datos con base en la informaci�n recibida
	 * como par�metro
	 * @param forma Se recibe objeto de Modelo FormaPago que contiene los valores de la entidad a insertar en la base de datos.
	 * 
	 * @return Se retorna el id de forma de pago retornado en la inserci�n de la entidad en la base de datos.
	 */
	public static int insertarFormaPago(FormaPago forma, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		int idFormaPagoIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into forma_pago (nombre, tipoformapago) values ('" + forma.getNombre() + "', '" + forma.getTipoforma() + "')"; 
			if(auditoria)
			{
				logger.info(insert);
			}
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idFormaPagoIns =rs.getInt(1);
				if(auditoria)
				{
					logger.info("Id forma pago insertada en bd " + idFormaPagoIns);
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
		return(idFormaPagoIns);
	}

	/**
	 * M�todo que se encarga de la eliminaci�n de una Forma de pago con base en la informaci�n de par�metros enviada.
	 * @param idFormaPago Se recibe como par�metro el idformapago que se desea eliminar.
	 */
	public static void eliminarFormaPago(int idFormaPago, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from forma_pago  where idforma_pago = " + idFormaPago; 
			if(auditoria)
			{
				logger.info(delete);
			}
			stm.executeUpdate(delete);
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
	 * M�todo que se encarga de retornar la informacion de una forma de pago con base en el par�metro recibido.
	 * @param idFormaPago Se recibe como par�metro un entero con el id forma de pago
	 * @return Se retorna un objeto Modelo Forma de pago con la informaci�n de la forma de pago cosultada.
	 */
	public static FormaPago retornarFormaPago(int idFormaPago, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		FormaPago forma = new FormaPago(0,"", "");
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select idforma_pago,nombre, tipoformapago from  forma_pago  where idforma_pago = " + idFormaPago; 
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idforma = 0;
			String nombr = "";
			String tipoforma = "";
			while(rs.next()){
				idforma = rs.getInt("idforma_pago");
				nombr = rs.getString("nombre");
				tipoforma = rs.getString("tipoformapago");
				break;
			}
			forma = new FormaPago(idforma, nombr, tipoforma);
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
		return(forma);
	}

	/**
	 * M�todo que se encarga de editar una forma de pago con base en los par�metros recibidos
	 * @param forma Se recibe como par�metro un objeto Modelo Forma Pago con los valores base para la modificaci�n.
	 * @return se retorna un valor tipo String con el resultado del proceso.
	 */
	public static String editarFormaPago(FormaPago forma, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		String resultado = "";
		try
		{
			Statement stm = con1.createStatement();
			String update = "update forma_pago set nombre ='" + forma.getNombre() + "', tipoformapago =  '" + forma.getTipoforma() + "' where idforma_pago = " + forma.getIdformapago(); 
			if(auditoria)
			{
				logger.info(update);
			}
			stm.executeUpdate(update);
			resultado = "exitoso";
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
			resultado = "error";
		}
		return(resultado);
	}
	
	/**
	 * M�todo que se encarga de retornar todas las formas pago definidas en base de datos
	 * @return Se retorna un ArrayList con todos los objetos Forma Pago definidos en el sistema.
	 */
	public static ArrayList<FormaPago> obtenerFormasPago(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<FormaPago> formaspago = new ArrayList();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idforma_pago, a.nombre, a.tipoformapago from forma_pago a";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idformapago;
			String nombre;
			String tipoformapago;
			while(rs.next()){
				idformapago = rs.getInt("idforma_pago");
				nombre = rs.getString("nombre");
				tipoformapago = rs.getString("tipoformapago");
				FormaPago forma = new FormaPago( idformapago,nombre, tipoformapago);
				formaspago.add(forma);
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
		return(formaspago);
		
	}
	
	

}
