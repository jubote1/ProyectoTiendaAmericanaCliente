package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import capaConexion.ConexionBaseDatos;
import capaModelo.InventariosTemporal;
import capaModelo.ItemInventario;

public class InventariosTemporalDAO {
	
	public static void insertarInventariosTemporal(InventariosTemporal invTemp, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into inventarios_temporal (fecha_sistema, tipo_inventario, iditem, cantidad) values ('" + invTemp.getFechaSistema() + "', '" + invTemp.getTipoInventario() + "' , " + invTemp.getIdItem() + " , " + invTemp.getCantidad() + ")"; 
			if(auditoria)
			{
				logger.info(insert);
			}
			stm.executeUpdate(insert);
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
			
		}
		
	}
	
	
	public static void limpiarTipoInventariosTemporal(String fechaSistema, String tipoInventario, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from inventarios_temporal where fecha_sistema = '"+ fechaSistema + "' and tipo_inventario = '" + tipoInventario + "'";
			if(auditoria)
			{
				logger.info(delete);
			}
			stm.executeUpdate(delete);
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
			
		}
	}
	
	public static boolean existeInventariosTemporal(String fechaSistema, String tipoInventario, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from inventarios_temporal where fecha_sistema = '"+ fechaSistema + "' and tipo_inventario = '" + tipoInventario + "'";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next())
			{
				respuesta = true; 
				break;
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
			
		}
		return(respuesta);
	}
	
	

}
