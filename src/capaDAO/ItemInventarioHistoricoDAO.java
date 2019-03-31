package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.mysql.jdbc.ResultSetMetaData;

import capaConexion.ConexionBaseDatos;

public class ItemInventarioHistoricoDAO {
	
	public static void realizarInventarioHistorico(String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
				
		try
		{
			Statement stm = con1.createStatement();
			Statement stm2 = con1.createStatement();
			String consulta = "select iditem,cantidad from item_inventario";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idItem = 0;
			double cantidad;
			String insert;
			while(rs.next()){
				idItem = rs.getInt("iditem");
				cantidad = rs.getDouble("cantidad");
				insert = "insert into item_inventario_historico (iditem, cantidad,fecha) values (" +idItem + " , " + cantidad + " , '" + fecha + "')"; 
				stm2.executeUpdate(insert);
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
				
	}
	
	public static double obtenerItemInventarioHistorico(int idItem, String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		double cantidad = 0;	
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select cantidad from item_inventario_historico where iditem = " + idItem + " and fecha = '" + fecha + "'" ;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				cantidad = rs.getDouble("cantidad");
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
		return(cantidad);	
	}

}
