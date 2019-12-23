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
	
	public static int insertarDetallePedido(DetallePedido detPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		int idDetalleInsertado = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into detalle_pedido (idpedidotienda,idproducto,cantidad, valorunitario,valortotal,observacion, iddetalle_pedido_master, iddetalle_modificador) values (" + detPedido.getIdPedidoTienda() + ", " + detPedido.getIdProducto() + ", " + detPedido.getCantidad() + " , " + detPedido.getValorUnitario() + " , " + detPedido.getValorTotal() + " , '" + detPedido.getObservacion() + "' , " + detPedido.getIdDetallePedidoMaster() +" , " + detPedido.getIdDetalleModificador() + ")"; 
			if(auditoria)
			{
				logger.info(insert);
			}
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

	public static boolean eliminarDetallePedido(int idDetallePedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		int idDetalleInsertado = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from detalle_pedido  where iddetalle_pedido = " + idDetallePedido ;
			if(auditoria)
			{
				logger.info(delete);
			}
			stm.executeUpdate(delete);
			delete = "delete from detalle_pedido  where iddetalle_pedido_master = " + idDetallePedido;
			if(auditoria)
			{
				logger.info(delete);
			}
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
	
	public static boolean anularDetallePedido(int idDetallePedido, int idMotivoAnulacion, String observacion, String usuarioAnulacion,  String usuarioAutorizo,   boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		
		try
		{
			Statement stm = con1.createStatement();
			String update = "update detalle_pedido set idmotivoanulacion =" + idMotivoAnulacion + " , obs_anulacion ='" + observacion + "' , usuario_anulacion = '" + usuarioAnulacion + "' , usuario_aut_anulacion = '" + usuarioAutorizo + "' where iddetalle_pedido = " + idDetallePedido ;
			if(auditoria)
			{
				logger.info(update);
			}
			stm.executeUpdate(update);
			update = "update detalle_pedido set idmotivoanulacion = " + idMotivoAnulacion + " , obs_anulacion ='" + observacion + "' , usuario_anulacion = '" + usuarioAnulacion + "' , usuario_aut_anulacion = '" + usuarioAutorizo +"'  where iddetalle_pedido_master = " + idDetallePedido;
			if(auditoria)
			{
				logger.info(update);
			}
			stm.executeUpdate(update);
			stm.close();
			con1.close();
			return(true);
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
			return(false);
		}
		
		
	}
	
	/**
	 * Método que realiza la anulación de un detalle pedido puntual, es decir diferente al no puntual que anula el detalle master y sus hijos
	 * @param idDetallePedido
	 * @param idMotivoAnulacion
	 * @param auditoria
	 * @return
	 */
	public static boolean anularDetallePedidoPuntual(int idDetallePedido, int idMotivoAnulacion, String observacion, String usuario, String usuarioAutorizo,  boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		
		try
		{
			Statement stm = con1.createStatement();
			String update = "update detalle_pedido set idmotivoanulacion =" + idMotivoAnulacion + " , obs_anulacion ='" + observacion + "' , usuario_anulacion = '" + usuario + "' , usuario_aut_anulacion = '" + usuarioAutorizo +"' where iddetalle_pedido = " + idDetallePedido ;
			if(auditoria)
			{
				logger.info(update);
			}
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
	
	public static boolean eliminarDetallesPedido(int idPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		int idDetalleInsertado = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from detalle_pedido  where idpedidotienda = " + idPedido ;
			if(auditoria)
			{
				logger.info(delete);
			}
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
	
	
	public static boolean AnularDetallesPedido(int idPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		int idDetalleInsertado = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		
		try
		{
			Statement stm = con1.createStatement();
			String update = "update detalle_pedido set cantidad = cantidad*-1 , valortotal = valortotal*-1 where idpedidotienda = " + idPedido ;
			if(auditoria)
			{
				logger.info(update);
			}
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
	
	public static boolean ActualizarImpuestoDetallesPedido(int idDetallePedido, double valorImpuesto, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		
		try
		{
			Statement stm = con1.createStatement();
			String update = "update detalle_pedido set valorimpuesto = " + valorImpuesto +" where iddetalle_pedido = " + idDetallePedido ;
			if(auditoria)
			{
				logger.info(update);
			}
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
	
	public static boolean validarDetalleMaster(int idDetallePedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select iddetalle_pedido from detalle_pedido  where iddetalle_pedido_master = " + idDetallePedido ;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			while (rs.next())
			{
				respuesta = true;
				break;
			}
			consulta = "select iddetalle_pedido from detalle_pedido  where iddetalle_pedido = " + idDetallePedido + " and iddetalle_pedido_master =  0";
			if(auditoria)
			{
				logger.info(consulta);
			}
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
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
			
		}
		return(respuesta);
		
	}
	
	
	public static ArrayList<DetallePedido> obtenerDetallePedido(int idPedido, boolean auditoria)
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
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idDetallePedido,idProducto,idDetallePedidoMaster, idDetalleModificador, idMotivoAnulacion;
			double cantidad,valorUnitario,valorTotal;
			String observacion;
			DetallePedido detPedTemp;
			String descargoInventario = "";
			String estado = "";
			int contDetPedido = 0;
			while (rs.next())
			{
				idDetallePedido = rs.getInt("iddetalle_pedido");
				idProducto = rs.getInt("idproducto");
				idDetallePedidoMaster = rs.getInt("iddetalle_pedido_master");
				cantidad = rs.getDouble("cantidad");
				valorUnitario = rs.getDouble("valorunitario");
				valorTotal = rs.getDouble("valortotal");
				observacion = rs.getString("observacion");
				idDetalleModificador = rs.getInt("iddetalle_modificador");
				descargoInventario = rs.getString("descargo_inventario");
				if(idDetallePedidoMaster == 0)
				{
					contDetPedido++;
				}
				try
				{
					idMotivoAnulacion = rs.getInt("idmotivoanulacion");
				}catch(Exception e)
				{
					idMotivoAnulacion = 0;
				}	
				if(idMotivoAnulacion > 0)
				{
					estado = "A";
				}
				else
				{
					estado = "";
				}
				detPedTemp = new DetallePedido(idDetallePedido,  idPedido, idProducto, cantidad, valorUnitario,
						valorTotal, observacion, idDetallePedidoMaster, descargoInventario,estado,contDetPedido);
				detPedTemp.setIdDetalleModificador(idDetalleModificador);
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
	
	/**
	 * Método que devolverá los detalles pedidos de un pedido que tienen la marcación de dev_inventario_siempre en S
	 * @param idPedido
	 * @param auditoria
	 * @return
	 */
	public static ArrayList<DetallePedido> obtenerDetallePedidoReintegro(int idPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		ArrayList<DetallePedido> detallesPedido = new ArrayList();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.* from detalle_pedido a, producto b  where a.idproducto = b.idproducto and b.dev_inventario_siempre = 'S' and a.idpedidotienda = " + idPedido ;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idDetallePedido,idProducto,idDetallePedidoMaster, idDetalleModificador, idMotivoAnulacion;
			double cantidad,valorUnitario,valorTotal;
			String observacion;
			DetallePedido detPedTemp;
			String descargoInventario = "";
			String estado = "";
			int contDetPedido = 0;
			while (rs.next())
			{
				idDetallePedido = rs.getInt("iddetalle_pedido");
				idProducto = rs.getInt("idproducto");
				idDetallePedidoMaster = rs.getInt("iddetalle_pedido_master");
				cantidad = rs.getDouble("cantidad");
				valorUnitario = rs.getDouble("valorunitario");
				valorTotal = rs.getDouble("valortotal");
				observacion = rs.getString("observacion");
				idDetalleModificador = rs.getInt("iddetalle_modificador");
				descargoInventario = rs.getString("descargo_inventario");
				if(idDetallePedidoMaster == 0)
				{
					contDetPedido++;
				}
				try
				{
					idMotivoAnulacion = rs.getInt("idmotivoanulacion");
				}catch(Exception e)
				{
					idMotivoAnulacion = 0;
				}	
				if(idMotivoAnulacion > 0)
				{
					estado = "A";
				}
				else
				{
					estado = "";
				}
				detPedTemp = new DetallePedido(idDetallePedido,  idPedido, idProducto, cantidad, valorUnitario,
						valorTotal, observacion, idDetallePedidoMaster, descargoInventario,estado,contDetPedido);
				detPedTemp.setIdDetalleModificador(idDetalleModificador);
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
	
	/**
	 * Método que se encarga de devolver un arrayList con los detalles pedidos asociados según el idetallepedido y si este es master
	 * traerá todo su grupo.
	 * @param idDetPedido Se recibe como parámetro el idDetalelPedido que incluirá el detalle de este si es master
	 * @param idPedido Se recibe el identificador del Pedido.
	 * @return Se retornará un ArrayList con los detallesPedidos que cumplan los parámetros enviados de idDetallePedido y master.
	 */
	public static ArrayList<DetallePedido> obtenerDetallePedidoMaster(int idDetPedido, int idPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		ArrayList<DetallePedido> detallesPedido = new ArrayList();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from detalle_pedido  where iddetalle_pedido = " + idDetPedido + " or iddetalle_pedido_master = " + idDetPedido ;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idDetallePedido,idProducto,idDetallePedidoMaster, idDetalleModificador,idMotivoAnulacion;
			double cantidad,valorUnitario,valorTotal;
			String observacion;
			DetallePedido detPedTemp;
			String descargoInventario = "";
			String estado = "";
			int contDetPedido = 0;
			while (rs.next())
			{
				idDetallePedido = rs.getInt("iddetalle_pedido");
				idProducto = rs.getInt("idproducto");
				idDetallePedidoMaster = rs.getInt("iddetalle_pedido_master");
				cantidad = rs.getDouble("cantidad");
				valorUnitario = rs.getDouble("valorunitario");
				valorTotal = rs.getDouble("valortotal");
				observacion = rs.getString("observacion");
				idDetalleModificador = rs.getInt("iddetalle_modificador");
				descargoInventario = rs.getString("descargo_inventario");
				if(idDetallePedidoMaster == 0)
				{
					contDetPedido++;
				}
				try
				{
					idMotivoAnulacion = rs.getInt("idmotivoanulacion");
				}catch(Exception e)
				{
					idMotivoAnulacion = 0;
				}	
				if(idMotivoAnulacion > 0)
				{
					estado = "A";
				}
				else
				{
					estado = "";
				}
				detPedTemp = new DetallePedido(idDetallePedido,  idPedido, idProducto, cantidad, valorUnitario,
						valorTotal, observacion, idDetallePedidoMaster, descargoInventario, estado, contDetPedido);
				detPedTemp.setIdDetalleModificador(idDetalleModificador);
				detallesPedido.add(detPedTemp);
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
			
		}
		return(detallesPedido);
		
	}
	
	public static ArrayList<DetallePedido> obtenerDetallePedidoPintarMaster(int idPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		ArrayList<DetallePedido> detallesPedido = new ArrayList();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.iddetalle_pedido, a.idproducto, b.descripcion, b.tipo_producto , b.tamano, b.impresion,  a.iddetalle_pedido_master, a.cantidad, a.valorunitario, a.valortotal, a.observacion, a.iddetalle_modificador, a.descargo_inventario, a.idmotivoanulacion from detalle_pedido a, producto b where a.idproducto = b.idproducto and a.idpedidotienda = " + idPedido + " and a.idmotivoanulacion IS NULL and a.iddetalle_pedido_master = 0 "  ;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idDetallePedido,idProducto,idDetallePedidoMaster, idDetalleModificador, idMotivoAnulacion;
			double cantidad,valorUnitario,valorTotal;
			String observacion;
			DetallePedido detPedTemp;
			String descripcionProducto = "";
			String tipoProducto = "";
			String tamano = "";
			String descCortaProducto = "";
			String descargoInventario = "";
			String estado = "";
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
				idDetalleModificador = rs.getInt("iddetalle_modificador");
				descargoInventario = rs.getString("descargo_inventario");
				try
				{
					idMotivoAnulacion = rs.getInt("idmotivoanulacion");
				}catch(Exception e)
				{
					idMotivoAnulacion = 0;
				}	
				if(idMotivoAnulacion > 0)
				{
					estado = "A";
				}
				else
				{
					estado = "";
				}
				detPedTemp = new DetallePedido(idDetallePedido,  idPedido, idProducto, cantidad, valorUnitario,
						valorTotal, observacion, idDetallePedidoMaster, descripcionProducto, tipoProducto, tamano, descCortaProducto);
				detPedTemp.setIdDetalleModificador(idDetalleModificador);
				detPedTemp.setEstado(estado);
				detallesPedido.add(detPedTemp);
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
			
		}
		return(detallesPedido);
		
	}
	
	public static ArrayList<DetallePedido> obtenerDetallePedidoPintar(int idPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		ArrayList<DetallePedido> detallesPedido = new ArrayList();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.iddetalle_pedido, a.idproducto, b.descripcion, b.tipo_producto , b.tamano, b.impresion,  a.iddetalle_pedido_master, a.cantidad, a.valorunitario, a.valortotal, a.observacion, a.iddetalle_modificador, a.descargo_inventario, a.idmotivoanulacion from detalle_pedido a, producto b where a.idproducto = b.idproducto and a.idpedidotienda = " + idPedido + " and a.idmotivoanulacion IS NULL "  ;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idDetallePedido,idProducto,idDetallePedidoMaster, idDetalleModificador, idMotivoAnulacion;
			double cantidad,valorUnitario,valorTotal;
			String observacion;
			DetallePedido detPedTemp;
			String descripcionProducto = "";
			String tipoProducto = "";
			String tamano = "";
			String descCortaProducto = "";
			String descargoInventario = "";
			String estado = "";
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
				idDetalleModificador = rs.getInt("iddetalle_modificador");
				descargoInventario = rs.getString("descargo_inventario");
				try
				{
					idMotivoAnulacion = rs.getInt("idmotivoanulacion");
				}catch(Exception e)
				{
					idMotivoAnulacion = 0;
				}	
				if(idMotivoAnulacion > 0)
				{
					estado = "A";
				}
				else
				{
					estado = "";
				}
				detPedTemp = new DetallePedido(idDetallePedido,  idPedido, idProducto, cantidad, valorUnitario,
						valorTotal, observacion, idDetallePedidoMaster, descripcionProducto, tipoProducto, tamano, descCortaProducto);
				detPedTemp.setIdDetalleModificador(idDetalleModificador);
				detPedTemp.setEstado(estado);
				detallesPedido.add(detPedTemp);
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
			
		}
		return(detallesPedido);
		
	}
	
	public static ArrayList<DetallePedido> obtenerDetallePedidoAnuladoPintar(int idPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		ArrayList<DetallePedido> detallesPedido = new ArrayList();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.iddetalle_pedido, a.idproducto, b.descripcion, b.tipo_producto , b.tamano, b.impresion,  a.iddetalle_pedido_master, a.cantidad, a.valorunitario, a.valortotal, a.observacion, a.iddetalle_modificador, a.descargo_inventario, a.idmotivoanulacion from detalle_pedido a, producto b where a.idproducto = b.idproducto and a.idpedidotienda = " + idPedido + " and a.idmotivoanulacion IS NOT NULL "  ;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idDetallePedido,idProducto,idDetallePedidoMaster, idDetalleModificador, idMotivoAnulacion;
			double cantidad,valorUnitario,valorTotal;
			String observacion;
			DetallePedido detPedTemp;
			String descripcionProducto = "";
			String tipoProducto = "";
			String tamano = "";
			String descCortaProducto = "";
			String descargoInventario = "";
			String estado = "";
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
				idDetalleModificador = rs.getInt("iddetalle_modificador");
				descargoInventario = rs.getString("descargo_inventario");
				try
				{
					idMotivoAnulacion = rs.getInt("idmotivoanulacion");
				}catch(Exception e)
				{
					idMotivoAnulacion = 0;
				}	
				if(idMotivoAnulacion > 0)
				{
					estado = "A";
				}
				else
				{
					estado = "";
				}
				detPedTemp = new DetallePedido(idDetallePedido,  idPedido, idProducto, cantidad, valorUnitario,
						valorTotal, observacion, idDetallePedidoMaster, descripcionProducto, tipoProducto, tamano, descCortaProducto);
				detPedTemp.setIdDetalleModificador(idDetalleModificador);
				detPedTemp.setEstado(estado);
				detallesPedido.add(detPedTemp);
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
			
		}
		return(detallesPedido);
		
	}
	
	public static DetallePedido obtenerUnDetallePedido(int idDetalle, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		DetallePedido detPedTemp = new DetallePedido();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from detalle_pedido  where iddetalle_pedido = " + idDetalle ;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idDetallePedido,idProducto,idDetallePedidoMaster, idMotivoAnulacion;
			double cantidad,valorUnitario,valorTotal;
			String observacion;
			String descargoInventario = "";
			int idPedido;
			String estado = "";
			int contDetPedido = 0;
			while (rs.next())
			{
				idPedido = rs.getInt("idpedidotienda");
				idDetallePedido = rs.getInt("iddetalle_pedido");
				idProducto = rs.getInt("idproducto");
				idDetallePedidoMaster = rs.getInt("iddetalle_pedido_master");
				cantidad = rs.getDouble("cantidad");
				valorUnitario = rs.getDouble("valorunitario");
				valorTotal = rs.getDouble("valortotal");
				observacion = rs.getString("observacion");
				descargoInventario = rs.getString("descargo_inventario");
				if(idDetallePedidoMaster == 0)
				{
					contDetPedido++;
				}
				try
				{
					idMotivoAnulacion = rs.getInt("idmotivoanulacion");
				}catch(Exception e)
				{
					idMotivoAnulacion = 0;
				}	
				if(idMotivoAnulacion > 0)
				{
					estado = "A";
				}
				else
				{
					estado = "";
				}
				detPedTemp = new DetallePedido(idDetallePedido,  idPedido, idProducto, cantidad, valorUnitario,
						valorTotal, observacion, idDetallePedidoMaster, descargoInventario, estado, contDetPedido);
				break;
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
		return(detPedTemp);
		
	}
	
	
	public static int obtenerIdDetalleMaster(int idDetallePedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		int idMaster = 0;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select iddetalle_pedido_master from detalle_pedido  where iddetalle_pedido = " + idDetallePedido ;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			while (rs.next())
			{
				idMaster = rs.getInt("iddetalle_pedido_master");
				break;
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
		return(idMaster);
		
	}
	
	/**
	 * Método que se encarga de marcar un detalle pedido como descargado en el inventario
	 * @param idDetallePedido Se recibe como parámetro el idDetallePedido que se marcará como descargado del inventario
	 * @return un valor de tipo booleano indicando si la marcación se pudo realizar o no.
	 */
	public static boolean descargarDetallePedido(int idDetallePedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		
		try
		{
			Statement stm = con1.createStatement();
			String update = "update detalle_pedido set descargo_inventario = 'S'  where iddetalle_pedido = " + idDetallePedido ;
			if(auditoria)
			{
				logger.info(update);
			}
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
	
	/**
	 * Crear método que se encarga de verificar si un pedido que fue reabierto y está siendo finalizado esta completamente anulado
	 * si esto es así devuelve un valor boolean de verdadero
	 * @param idPedido
	 * @param auditoria
	 * @return
	 */
	public static boolean verificarPedidoReabiertoAnulado(int idPedido,boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		try
		{
			Statement stm = con1.createStatement();
			String select = "select count(*) from detalle_pedido  where idpedidotienda= " + idPedido + " and idmotivoanulacion IS NULL " ;
			if(auditoria)
			{
				logger.info(select);
			}
			ResultSet rs = stm.executeQuery(select);
			int cantidad = 0;
			while(rs.next())
			{
				cantidad = rs.getInt(1);
				break;
			}
			if(cantidad == 0)
			{
				respuesta = true;
			}else 
			{
				respuesta = false;
			}
				
			rs.close();
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
	
	public static boolean multiplicarDetallePedido(int idDetaTratar, int multiplicador , boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<DetallePedido> detalleMult =  obtenerDetallePedidoMaster(idDetaTratar, 0, auditoria);
		Statement stm;
		try
		{
			stm = con1.createStatement();
			for(int i = 0; i < detalleMult.size(); i ++)
			{
				DetallePedido detTemp = detalleMult.get(i);
				detTemp.setCantidad(multiplicador*detTemp.getCantidad());
				detTemp.setValorTotal(detTemp.getValorUnitario()*detTemp.getCantidad());
				String update = "update detalle_pedido set cantidad = " + detTemp.getCantidad() + " , valortotal = " + detTemp.getValorTotal() +" where iddetalle_pedido = " + detTemp.getIdDetallePedido() ;
				if(auditoria)
				{
					logger.info(update);
				}
				stm.executeUpdate(update);
			}
			stm.close();
			con1.close();
		}catch(Exception e)
		{
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
		return(true);
	}
	
}
