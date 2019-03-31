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
	
	
	public static boolean insertarPedidoDescuento(PedidoDescuento descuento, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into pedido_descuento (idpedido, descuentopesos, descuentoporcentaje, observacion, usuario) values (" + descuento.getIdpedido() + ", " + descuento.getDescuentoPesos() + " , " + descuento.getDescuentoPorcentaje() +  ", '" + descuento.getObservacion() + "' , '" + descuento.getUsuario() + "')"; 
			if(auditoria)
			{
				logger.info(insert);
			}
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
	
	
	public static boolean eliminarPedidoDescuento(int idPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from pedido_descuento where idpedido = " + idPedido; 
			if(auditoria)
			{
				logger.info(delete);
			}
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
	
	public static PedidoDescuento obtenerPedidoDescuento(int idPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		PedidoDescuento descuento = new PedidoDescuento(0,0,0,"","", 0,0, "");
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from pedido_descuento where idpedido = " + idPedido; 
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			double descuentoPesos = 0, descuentoPorcentaje = 0;
			String observacion = "";
			while(rs.next())
			{
				descuentoPesos = rs.getDouble("descuentopesos");
				descuentoPorcentaje = rs.getDouble("descuentoPorcentaje");
				observacion = rs.getString("observacion");
			}
			descuento = new PedidoDescuento(idPedido, descuentoPesos, descuentoPorcentaje, observacion, "", 0, 0, "");
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
	
	public static ArrayList<PedidoDescuento> obtenerPedidoDescuentoFecha(String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<PedidoDescuento> descuentosFecha = new ArrayList();
		PedidoDescuento descuento = new PedidoDescuento(0,0,0,"","",0,0, "");
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.*, b.fechapedido, (b.total_neto + a.descuentopesos) as valorinicial, b.total_neto as valorfinal, ((a.descuentopesos/(b.total_neto + a.descuentopesos))* 100) as porcentaje from pedido_descuento a, pedido b where a.idpedido = b.idpedidotienda and b.fechapedido = '" + fecha + "'"; 
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idPedido = 0;
			double descuentoPesos = 0, descuentoPorcentaje = 0;
			String observacion = "";
			double valorInicial = 0, valorFinal = 0;
			String usuario;
			while(rs.next())
			{
				idPedido = rs.getInt("idpedido");
				descuentoPesos = rs.getDouble("descuentopesos");
				descuentoPorcentaje = rs.getDouble("porcentaje");
				observacion = rs.getString("observacion");
				valorInicial = rs.getDouble("valorinicial");
				valorFinal = rs.getDouble("valorfinal");
				usuario = rs.getString("usuario");
				descuento = new PedidoDescuento(idPedido, descuentoPesos, descuentoPorcentaje, observacion, fecha, valorInicial, valorFinal, usuario);
				descuentosFecha.add(descuento);
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
		return(descuentosFecha);
	}
	
	/**
	 * Método que retorna los descuentos en un rango de días, para por ejemplo envíar el reporte semanal luego del cierre de semana
	 * @param fechaAnterior
	 * @param fechaActual
	 * @param auditoria
	 * @return
	 */
	public static ArrayList<PedidoDescuento> obtenerPedidoDescuentoRango(String fechaAnterior, String fechaActual, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<PedidoDescuento> descuentosFecha = new ArrayList();
		PedidoDescuento descuento = new PedidoDescuento(0,0,0,"", "", 0,0, "");
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.*, b.fechapedido, (b.total_neto + a.descuentopesos) as valorinicial, b.total_neto as valorfinal, ((a.descuentopesos/(b.total_neto + a.descuentopesos))* 100) as porcentaje   from pedido_descuento a, pedido b where a.idpedido = b.idpedidotienda and b.fechapedido >= '" + fechaAnterior + "' and b.fechapedido <= '" + fechaActual + "'"; 
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idPedido = 0;
			double descuentoPesos = 0, descuentoPorcentaje = 0;
			String observacion = "";
			String fechaDescuento = "";
			double valorInicial = 0, valorFinal = 0;
			String usuario;
			while(rs.next())
			{
				idPedido = rs.getInt("idpedido");
				descuentoPesos = rs.getDouble("descuentopesos");
				descuentoPorcentaje = rs.getDouble("porcentaje");
				observacion = rs.getString("observacion");
				fechaDescuento = rs.getString("fechapedido");
				valorInicial = rs.getDouble("valorinicial");
				valorFinal = rs.getDouble("valorfinal");
				usuario = rs.getString("usuario");
				descuento = new PedidoDescuento(idPedido, descuentoPesos, descuentoPorcentaje, observacion, fechaDescuento, valorInicial, valorFinal, usuario);
				descuentosFecha.add(descuento);
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
		return(descuentosFecha);
	}
	
	public static double obtenerTotalPedidoDescuento(int idPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		double total = 0;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select sum(descuentopesos) as total from pedido_descuento where idpedido = " + idPedido; 
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			
			while(rs.next())
			{
				total = rs.getDouble("total");
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
		return(total);
	}
	
	public static boolean existePedidoDescuento(int idPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from pedido_descuento where idpedido = " + idPedido; 
			if(auditoria)
			{
				logger.info(consulta);
			}
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
