package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.mysql.jdbc.ResultSetMetaData;

import capaConexion.ConexionBaseDatos;
import capaModelo.ItemInventario;
import capaModelo.ModificadorInventario;

public class ModificadorInventarioDAO {
	
	public static int insertarIngresosInventarios(ArrayList <ModificadorInventario> ingresos, String fecha, boolean auditoria )
	{
		Logger logger = Logger.getLogger("log_file");
		int idIngresoInv = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			//Realizamos la inserción del IdInventario
			Statement stm = con1.createStatement();
			String insert = "insert into ingreso_inventario (fecha_sistema) values ('" + fecha + "')"; 
			if(auditoria)
			{
				logger.info(insert);
			}
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idIngresoInv=rs.getInt(1);
				if(auditoria)
				{
					logger.info("id Ingreso inventario insertada en bd " + idIngresoInv);
				}
				
	        }
			//Realizamos el insert de los modificadores de inventario
			if(idIngresoInv > 0)
			{
				String update = "";
				for(int i = 0; i < ingresos.size();i++)
				{
					ModificadorInventario modTemp = ingresos.get(i);
					insert = "insert into ingreso_inventario_detalle (idingreso_inventario,iditem,cantidad) values (" + idIngresoInv +  "," +modTemp.getIdItem() + "," + modTemp.getCantidad()+ ")";
					logger.info(insert);
					stm.executeUpdate(insert);
					//Luego del insert actualizamos la cantidad del item de inventario retirado
					update = "update item_inventario set cantidad = cantidad + " + modTemp.getCantidad() + " where iditem = " + modTemp.getIdItem();
					logger.info(update);
					stm.executeUpdate(update);
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
		return(idIngresoInv);
	}
	
	
	/**
	 * Método que se encarga de retornar el total ingresado de un determinado item de inventario en una fecha determinada
	 * @param idItem
	 * @param fecha
	 * @param auditoria
	 * @return
	 */
	public static double ingresadoItemInvenario(int idItem, String fecha, boolean auditoria )
	{
		Logger logger = Logger.getLogger("log_file");
		int idIngresoInv = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		double cantidad = 0;
		try
		{
			//Realizamos la inserción del IdInventario
			Statement stm = con1.createStatement();
			String consulta = "select sum(cantidad) from ingreso_inventario a, ingreso_inventario_detalle b where a.fecha_sistema ='" + fecha + "' and b.iditem = " + idItem + " AND a.idingreso_inventario = b.idingreso_inventario"; 
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next())
			{
				cantidad = rs.getDouble(1);
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
		return(cantidad);
	}
	
	
	/**
	 * Método que se encarga de retornar el total ingresado de un determinado item de inventario en una fecha determinada
	 * @param idItem
	 * @param fecha
	 * @param auditoria
	 * @return
	 */
	public static double retiradoItemInvenario(int idItem, String fecha, boolean auditoria )
	{
		Logger logger = Logger.getLogger("log_file");
		int idIngresoInv = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		double cantidad = 0;
		try
		{
			//Realizamos la inserción del IdInventario
			Statement stm = con1.createStatement();
			String consulta = "select sum(b.cantidad) from retiro_inventario a, retiro_inventario_detalle b where a.idretiro_inventario = b.idretiro_inventario and a.fecha_sistema ='" + fecha + "' and b.iditem = " + idItem; 
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next())
			{
				cantidad = rs.getDouble(1);
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
		return(cantidad);
	}
	
		
	/**
	 * Método que se encargará en la inserción de los consumos de un pedido determinado, sean positivos es decir gastos o negativos reintegros de inventario
	 * @param consumo Arraylist con objetos de tipo modificadorInventario que se descontarán del inventario
	 * @param idPedido el idPedido del cual se está registrando la información.
	 * @return
	 */
	public static boolean insertarConsumoInventarios(ArrayList <ModificadorInventario> consumos, int idPedido, boolean auditoria )
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			//Realizamos la inserción del IdInventario
			Statement stm = con1.createStatement();
			String update = "", insert = "";
				for(int i = 0; i < consumos.size();i++)
				{
					ModificadorInventario modTemp = consumos.get(i);
					insert = "insert into consumo_inventario_pedido (idpedido,iditem,cantidad) values (" + idPedido+  "," +modTemp.getIdItem() + "," + modTemp.getCantidad()+ ")";
					if(auditoria)
					{
						logger.info(insert);
					}
					stm.executeUpdate(insert);
					//Luego del insert actualizamos la cantidad del item de inventario retirado
					update = "update item_inventario set cantidad = cantidad - " + modTemp.getCantidad() + " where iditem = " + modTemp.getIdItem();
					if(auditoria)
					{
						logger.info(update);
					}
					stm.executeUpdate(update);
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
			return(false);
		}
		return(true);
	}
	
	
	public static int insertarRetirosInventarios(ArrayList <ModificadorInventario> retiros, String fecha, boolean auditoria )
	{
		Logger logger = Logger.getLogger("log_file");
		int idRetiroInv = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			//Realizamos la inserción del IdInventario
			Statement stm = con1.createStatement();
			String insert = "insert into retiro_inventario (fecha_sistema) values ('" + fecha + "')"; 
			if(auditoria)
			{
				logger.info(insert);
			}
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idRetiroInv=rs.getInt(1);
				if(auditoria)
				{
					logger.info("id retiro inventario insertada en bd " + idRetiroInv);
				}
				
	        }
			//Realizamos el insert de los modificadores de inventario
			if(idRetiroInv > 0)
			{
				String update = "";
				for(int i = 0; i < retiros.size();i++)
				{
					ModificadorInventario modTemp = retiros.get(i);
					insert = "insert into retiro_inventario_detalle (idretiro_inventario,iditem,cantidad) values (" +idRetiroInv +  "," +modTemp.getIdItem() + "," + modTemp.getCantidad()+ ")";
					if(auditoria)
					{
						logger.info(insert);
					}
					stm.executeUpdate(insert);
					//Luego del insert actualizamos la cantidad del item de inventario retirado
					update = "update item_inventario set cantidad = cantidad - " + modTemp.getCantidad() + " where iditem = " + modTemp.getIdItem();
					if(auditoria)
					{
						logger.info(update);
					}
					stm.executeUpdate(update);
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
		return(idRetiroInv);
	}
	
	public static int insertarVarianzaInventarios(ArrayList <ModificadorInventario> varianzas, String fecha, boolean auditoria )
	{
		Logger logger = Logger.getLogger("log_file");
		int idInvVarianza = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			//Realizamos la inserción del IdInventario
			Statement stm = con1.createStatement();
			String insert = "insert into inventario_varianza (fecha_sistema) values ('" + fecha + "')"; 
			if(auditoria)
			{
				logger.info(insert);
			}
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idInvVarianza=rs.getInt(1);
				if(auditoria)
				{
					logger.info("id inventario varianza insertada en bd " + idInvVarianza);
				}
				
	        }
			//Realizamos el insert de los modificadores de inventario
			if(idInvVarianza > 0)
			{
				String update = "";
				for(int i = 0; i < varianzas.size();i++)
				{
					ModificadorInventario modTemp = varianzas.get(i);
					insert = "insert into item_inventario_varianza (idinventario_varianza,iditem,cantidad) values (" +idInvVarianza +  "," +modTemp.getIdItem() + "," + modTemp.getCantidad()+ ")";
					if(auditoria)
					{
						logger.info(insert);
					}
					stm.executeUpdate(insert);
					//Luego del insert actualizamos la cantidad del item de inventario
					update = "update item_inventario set cantidad =  " + modTemp.getCantidad() + " where iditem = " + modTemp.getIdItem();
					if(auditoria)
					{
						logger.info(update);
					}
					stm.executeUpdate(update);
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
		return(idInvVarianza);
	}

	public static boolean seIngresoVarianza(String fecha, boolean auditoria)
	{
		boolean respuesta = false;
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList itemsInventario = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from inventario_varianza where fecha_sistema = '"+fecha+"'";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			
			
			while(rs.next()){
				respuesta = true;
				break;
				
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
		return(respuesta);
	}
	
	
	/*
	 * Método que se encargada de obtener todos aquellos productos que descuentan un determinado item de inventario
	 */
	public static ArrayList obtenerProductoDescuentoItemInventario(int idItem,boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList productosDescuentan = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select b.descripcion, a.cantidad from item_inventario_x_producto a, producto b where a.idproducto = b.idproducto and a.iditem = " + idItem;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
			int numeroColumnas = rsMd.getColumnCount();
			
			while(rs.next()){
				String [] fila = new String[numeroColumnas];
				for(int y = 0; y < numeroColumnas; y++)
				{
					fila[y] = rs.getString(y+1);
				}
				productosDescuentan.add(fila);
				
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
		return(productosDescuentan);
		
	}
	
	
	public static ArrayList obtenerIngresosItemInventario(int idItem,String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList productosDescuentan = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idingreso_inventario, a.fecha_real, a.fecha_sistema, b.cantidad from ingreso_inventario a, ingreso_inventario_detalle b where a.idingreso_inventario = b.idingreso_inventario and b.iditem = " + idItem + " and a.fecha_sistema = '" + fecha + "'";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
			int numeroColumnas = rsMd.getColumnCount();
			
			while(rs.next()){
				String [] fila = new String[numeroColumnas];
				for(int y = 0; y < numeroColumnas; y++)
				{
					fila[y] = rs.getString(y+1);
				}
				productosDescuentan.add(fila);
				
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
		return(productosDescuentan);
		
	}
	
	
	public static ArrayList obtenerRetirosItemInventario(int idItem,String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList productosDescuentan = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idretiro_inventario, a.fecha_real, a.fecha_sistema, b.cantidad from retiro_inventario a, retiro_inventario_detalle b where a.idretiro_inventario = b.idretiro_inventario and b.iditem = " + idItem + " and a.fecha_sistema = '" + fecha + "'";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
			int numeroColumnas = rsMd.getColumnCount();
			
			while(rs.next()){
				String [] fila = new String[numeroColumnas];
				for(int y = 0; y < numeroColumnas; y++)
				{
					fila[y] = rs.getString(y+1);
				}
				productosDescuentan.add(fila);
				
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
		return(productosDescuentan);
		
	}
	
	
	public static double obtenerConsumoItemInventario(int idItem,String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		double consumo = 0;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select sum(cantidad) from consumo_inventario_pedido a, pedido b where a.idpedido = b.idpedidotienda and " + 
					" b.fechapedido = '"  + fecha + "' and a.iditem = " + idItem;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			
			while(rs.next()){
				consumo = rs.getDouble(1);
				
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
		return(consumo);
		
	}
	
	
	public static double obtenerVarianzaItemInventario(int idItem,String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		double cantidad = 0;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select b.cantidad from inventario_varianza a, item_inventario_varianza b where a.idinventario_varianza = b.idinventario_varianza and " + 
					" a.fecha_sistema = '"  + fecha + "' and b.iditem = " + idItem;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			
			while(rs.next()){
				cantidad = rs.getDouble(1);
				
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
	
	
	public static ArrayList obtenerPedidosDescItemInventario(int idItem,String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList pedidosDescItemInv = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select d.idpedido, d.cantidad from consumo_inventario_pedido d where d.iditem = " + idItem + " and d.idpedido in (select a.idpedidotienda from producto c, pedido a, detalle_pedido b  where a.idpedidotienda = b.idpedidotienda\r\n" + 
					"and b.idproducto = c.idproducto and a.fechapedido  = '"+ fecha +"' and b.idproducto in (select b.idproducto from item_inventario_x_producto a, producto b where a.idproducto = b.idproducto and a.iditem = " + idItem+ ")) ";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
			int numeroColumnas = rsMd.getColumnCount();
			
			while(rs.next()){
				String [] fila = new String[numeroColumnas];
				for(int y = 0; y < numeroColumnas; y++)
				{
					fila[y] = rs.getString(y+1);
				}
				pedidosDescItemInv.add(fila);
				
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
		return(pedidosDescItemInv);
		
	}
	
	
	public static ArrayList obtenerPedidosAnulItemInventario(int idItem,String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList pedidosDescItemInv = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idpedidotienda from producto c, pedido a, detalle_pedido b  where a.idpedidotienda = b.idpedidotienda " + 
					"and b.idproducto = c.idproducto and a.fechapedido  = '"+ fecha +"' and b.idmotivoanulacion IS NOT NULL and b.idproducto in (select b.idproducto from item_inventario_x_producto a, producto b where a.idproducto = b.idproducto and a.iditem = " + idItem+ ") ";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
			int numeroColumnas = rsMd.getColumnCount();
			
			while(rs.next()){
				String [] fila = new String[numeroColumnas];
				for(int y = 0; y < numeroColumnas; y++)
				{
					fila[y] = rs.getString(y+1);
				}
				pedidosDescItemInv.add(fila);
				
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
		return(pedidosDescItemInv);
		
	}


}
