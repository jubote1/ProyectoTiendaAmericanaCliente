package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.mysql.jdbc.ResultSetMetaData;

import capaConexion.ConexionBaseDatos;
import capaModelo.Impuesto;
import capaModelo.PedidoDescuento;

public class PedidoDescuentoDAO {
	
	
	public static boolean insertarPedidoDescuento(PedidoDescuento descuento)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into pedido_descuento (idpedido, descuentopesos, descuentoporcentaje) values (" + descuento.getIdpedido() + ", " + descuento.getDescuentoPesos() + " , " + descuento.getDescuentoPorcentaje() + ")"; 
			logger.info(insert);
			stm.executeUpdate(insert);
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
	
	
	public static boolean eliminarPedidoDescuento(int idPedido)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from pedido_descuento where idpedido = " + idPedido; 
			logger.info(delete);
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
	
	public static PedidoDescuento obtenerPedidoDescuento(int idPedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		PedidoDescuento descuento = new PedidoDescuento(0,0,0);
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from pedido_descuento where idpedido = " + idPedido; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			double descuentoPesos = 0, descuentoPorcentaje = 0;
			while(rs.next())
			{
				descuentoPesos = rs.getDouble("descuentopesos");
				descuentoPorcentaje = rs.getDouble("descuentoPorcentaje");
			}
			descuento = new PedidoDescuento(idPedido, descuentoPesos, descuentoPorcentaje);
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
		return(descuento);
	}
	
	public static boolean existePedidoDescuento(int idPedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from pedido_descuento where idpedido = " + idPedido; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next())
			{
				respuesta = true;
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
			
		}
		return(respuesta);
	}
	
}
