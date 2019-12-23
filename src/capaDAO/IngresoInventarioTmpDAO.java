package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.mysql.jdbc.ResultSetMetaData;

import capaConexion.ConexionBaseDatos;

public class IngresoInventarioTmpDAO {
	
/**
 * Método que retorna los despachos temporales para un día determinado.
 * @param fecha
 * @param auditoria
 * @return Retorna un ArrayList con los valores enteros de los iddespacho de los días en cuestión.
 */
	public static ArrayList<Integer> obtenerInventarioPreIngresar(String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<Integer> despachos = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.iddespacho from ingreso_inventario_tmp a where a.fecha_sistema = '" + fecha + "' and a.estado = 'PREINGRESADO'";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
			int idDespacho;
			
			while(rs.next()){
				idDespacho = rs.getInt("iddespacho");
				despachos.add(idDespacho);
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			System.out.println(e.toString());
			logger.info(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(despachos);
		
	}
	
	
	/**
	 * Método que retorna la observación de un determinado despacho con el fin de poderla desplegar en pantalla para que lo puedan ver
	 * @param idDespacho
	 * @param auditoria
	 * @return
	 */
	public static String obtenerObsInventarioPreIngresar(int idDespacho,  boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		String observacion = "";
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.observacion from ingreso_inventario_tmp a where a.iddespacho = " + idDespacho;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			
			while(rs.next()){
				observacion  = rs.getString("observacion");
				break;
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			System.out.println(e.toString());
			logger.info(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(observacion);
		
	}
	
	
	/**
	 * Método que se encarga del borrado del encabezado de un preingreso de inventario de bodega
	 * @param idDespacho
	 * @param auditoria
	 * @return
	 */
	public static boolean borrarIngresoInventarioTmp(int idDespacho, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from ingreso_inventario_tmp where iddespacho = '" + idDespacho + "'";
			if(auditoria)
			{
				logger.info(delete);
			}
			stm.executeUpdate(delete);
			respuesta = true;
			stm.close();
			con1.close();
		}catch (Exception e){
			respuesta = false;
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
	 * Método que actualiza el estado de un ingreso temporal de inventario proveniente de bodega
	 * @param idDespacho
	 * @param estado
	 * @param auditoria
	 * @return Retorna un valor booleano con el resultado del proceso.
	 */
	public static boolean cambiarEstadoIngresoInventarioTmp(int idDespacho, String estado,  boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		
		try
		{
			Statement stm = con1.createStatement();
			String update = "update ingreso_inventario_tmp set estado = '" + estado + "' where iddespacho = '" + idDespacho + "'";
			if(auditoria)
			{
				logger.info(update);
			}
			stm.executeUpdate(update);
			respuesta = true;
			stm.close();
			con1.close();
		}catch (Exception e){
			respuesta = false;
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

}
