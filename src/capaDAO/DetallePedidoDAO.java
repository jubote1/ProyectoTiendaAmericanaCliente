package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import capaConexion.ConexionBaseDatos;
import capaModelo.DetallePedido;

public class DetallePedidoDAO {
	
	public static int insertarDetallePedido(DetallePedido detPedido)
	{
		Logger logger = Logger.getLogger("log_file");
		int idDetalleInsertado = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into detalle_pedido (idpedidotienda,idproducto,cantidad, valorunitario,valortotal,observacion, iddetalle_pedido_master) values (" + detPedido.getIdPedidoTienda() + ", " + detPedido.getIdProducto() + ", " + detPedido.getCantidad() + " , " + detPedido.getValorUnitario() + " , " + detPedido.getValorTotal() + " , '" + detPedido.getObservacion() + "' , " + detPedido.getIdDetallePedidoMaster() + ")"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idDetalleInsertado=rs.getInt(1);
				
	        }
	        rs.close();
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
		return(idDetalleInsertado);
	}

	public static boolean eliminarDetallePedido(int idDetallePedido)
	{
		Logger logger = Logger.getLogger("log_file");
		int idDetalleInsertado = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from detalle_pedido  where iddetalle_pedido = " + idDetallePedido ;
			logger.info(delete);
			stm.executeUpdate(delete);
			delete = "delete from detalle_pedido  where iddetalle_pedido_master = " + idDetallePedido;
			logger.info(delete);
			stm.executeUpdate(delete);
			stm.close();
			con1.close();
			return(true);
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
		
		
	}
	
	public static boolean anularDetallePedido(int idDetallePedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		
		try
		{
			Statement stm = con1.createStatement();
			String delete = "update detalle_pedido set cantidad = cantidad*-1, valortotal = valortotal*-1  where iddetalle_pedido = " + idDetallePedido ;
			logger.info(delete);
			stm.executeUpdate(delete);
			delete = "update detalle_pedido set cantidad = cantidad*-1, valortotal = valortotal*-1  where iddetalle_pedido_master = " + idDetallePedido;
			logger.info(delete);
			stm.executeUpdate(delete);
			stm.close();
			con1.close();
			return(true);
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
		
		
	}
	
	public static boolean eliminarDetallesPedido(int idPedido)
	{
		Logger logger = Logger.getLogger("log_file");
		int idDetalleInsertado = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from detalle_pedido  where idpedidotienda = " + idPedido ;
			logger.info(delete);
			stm.executeUpdate(delete);
			stm.close();
			con1.close();
			return(true);
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
		
		
	}
	
	public static boolean AnularDetallesPedido(int idPedido)
	{
		Logger logger = Logger.getLogger("log_file");
		int idDetalleInsertado = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		
		try
		{
			Statement stm = con1.createStatement();
			String update = "update detalle_pedido set cantidad = cantidad*-1 , valortotal = valortotal*-1 where idpedidotienda = " + idPedido ;
			logger.info(update);
			stm.executeUpdate(update);
			stm.close();
			con1.close();
			return(true);
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
		
		
	}
	
	public static boolean ActualizarImpuestoDetallesPedido(int idDetallePedido, double valorImpuesto)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		
		try
		{
			Statement stm = con1.createStatement();
			String update = "update detalle_pedido set valorimpuesto = " + valorImpuesto +" where iddetalle_pedido = " + idDetallePedido ;
			logger.info(update);
			stm.executeUpdate(update);
			stm.close();
			con1.close();
			return(true);
		}
		catch (Exception e){
			logger.error(e.toString());
			try
			{
				respuesta = false;
				con1.close();
			}catch(Exception e1)
			{
			}
			return(respuesta);
		}
		
		
	}
	
	public static boolean validarDetalleMaster(int idDetallePedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select iddetalle_pedido from detalle_pedido  where iddetalle_pedido_master = " + idDetallePedido ;
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while (rs.next())
			{
				respuesta = true;
				break;
			}
			consulta = "select iddetalle_pedido from detalle_pedido  where iddetalle_pedido = " + idDetallePedido + " and iddetalle_pedido_master =  0";
			logger.info(consulta);
			rs = stm.executeQuery(consulta);
			while (rs.next())
			{
				respuesta = true;
				break;
			}
			stm.close();
			con1.close();
			
		}
		catch (Exception e){
			logger.error(e.toString());
			System.out.println(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
			
		}
		return(respuesta);
		
	}
	
	
	public static ArrayList<DetallePedido> obtenerDetallePedido(int idPedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		ArrayList<DetallePedido> detallesPedido = new ArrayList();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from detalle_pedido  where idpedidotienda = " + idPedido ;
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idDetallePedido,idProducto,idDetallePedidoMaster;
			double cantidad,valorUnitario,valorTotal;
			String observacion;
			DetallePedido detPedTemp;
			while (rs.next())
			{
				idDetallePedido = rs.getInt("iddetalle_pedido");
				idProducto = rs.getInt("idproducto");
				idDetallePedidoMaster = rs.getInt("iddetalle_pedido_master");
				cantidad = rs.getDouble("cantidad");
				valorUnitario = rs.getDouble("valorunitario");
				valorTotal = rs.getDouble("valortotal");
				observacion = rs.getString("observacion");
				detPedTemp = new DetallePedido(idDetallePedido,  idPedido, idProducto, cantidad, valorUnitario,
						valorTotal, observacion, idDetallePedidoMaster);
				detallesPedido.add(detPedTemp);
			}
			
			rs.close();
			stm.close();
			con1.close();
			
		}
		catch (Exception e){
			logger.error(e.toString());
			System.out.println(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
			
		}
		return(detallesPedido);
		
	}
	
	public static ArrayList<DetallePedido> obtenerDetallePedidoPintar(int idPedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		ArrayList<DetallePedido> detallesPedido = new ArrayList();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.iddetalle_pedido, a.idproducto, b.descripcion, b.tipo_producto , b.tamano, b.impresion,  a.iddetalle_pedido_master, a.cantidad, a.valorunitario, a.valortotal, a.observacion from detalle_pedido a, producto b where a.idproducto = b.idproducto and idpedidotienda = " + idPedido ;
			System.out.println(consulta);
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idDetallePedido,idProducto,idDetallePedidoMaster;
			double cantidad,valorUnitario,valorTotal;
			String observacion;
			DetallePedido detPedTemp;
			String descripcionProducto = "";
			String tipoProducto = "";
			String tamano = "";
			String descCortaProducto = "";
			while (rs.next())
			{
				idDetallePedido = rs.getInt("iddetalle_pedido");
				idProducto = rs.getInt("idproducto");
				idDetallePedidoMaster = rs.getInt("iddetalle_pedido_master");
				cantidad = rs.getDouble("cantidad");
				valorUnitario = rs.getDouble("valorunitario");
				valorTotal = rs.getDouble("valortotal");
				observacion = rs.getString("observacion");
				descripcionProducto = rs.getString("descripcion");
				tipoProducto = rs.getString("tipo_producto");
				tamano = rs.getString("tamano");
				descCortaProducto = rs.getString("impresion");
				detPedTemp = new DetallePedido(idDetallePedido,  idPedido, idProducto, cantidad, valorUnitario,
						valorTotal, observacion, idDetallePedidoMaster, descripcionProducto, tipoProducto, tamano, descCortaProducto);
				detallesPedido.add(detPedTemp);
			}
			
			rs.close();
			stm.close();
			con1.close();
			
		}
		catch (Exception e){
			logger.error(e.toString());
			System.out.println(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
			
		}
		return(detallesPedido);
		
	}
	
	
}
