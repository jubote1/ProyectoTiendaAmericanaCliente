package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.mysql.jdbc.ResultSetMetaData;

import capaConexion.ConexionBaseDatos;
import capaModelo.Cliente;
import capaModelo.Tienda;

public class PedidoDAO {
	
	public static int InsertarEncabezadoPedido(int idtienda, int idcliente, String fechaPedido, String user)
	{
		Logger logger = Logger.getLogger("log_file");
		int idPedidoInsertado = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		Date fechaTemporal = new Date();
		DateFormat formatoFinal = new SimpleDateFormat("yyyy-MM-dd");
		String fechaPedidoFinal ="";
		try
		{
			fechaTemporal = new SimpleDateFormat("dd/MM/yyyy").parse(fechaPedido);
			fechaPedidoFinal = formatoFinal.format(fechaTemporal);
			
		}catch(Exception e){
			logger.error(e.toString());
			return(0);
		}
		
		
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into pedido (idtienda,idcliente,fechapedido, usuariopedido) values (" + idtienda + ", " + idcliente + ", '" + fechaPedidoFinal  + "' , '" + user + "')"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idPedidoInsertado=rs.getInt(1);
				System.out.println(idPedidoInsertado);
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
		return(idPedidoInsertado);
	}
	
	/**
	 * SE TIENE PENDIENTE EL MANEJO DE LOS IMPUESTOS Y EL TIEMPO PEDIDO
	 * @param idpedido
	 * @param tiempopedido
	 * @return
	 */
	public static boolean finalizarPedido(int idpedido,  double tiempopedido, int idTipoPedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			double valorTotal = 0;
			Statement stm = con1.createStatement();
			String consulta = "select sum(valorTotal) from detalle_pedido where idpedidotienda = " + idpedido + " " ; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				valorTotal = rs.getDouble(1);
				break;
			}
			String update = "update pedido set total_bruto =" + valorTotal* 0.92 + " , impuesto = " + valorTotal * 0.08 + " , total_neto =" + valorTotal + " , idtipopedido =" + idTipoPedido + " where idpedidotienda = " + idpedido;
			logger.info(update);
			stm.executeUpdate(update);
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
			return(false);
		}
		return(true);
	}
	
	
	public static boolean eliminarPedido(int idPedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from pedido  where idpedido = " + idPedido ;
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
	
	public static ArrayList obtenerPedidosTable(String fechaPedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList pedidos = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idpedidotienda, a.fechapedido, concat_ws(' ', b.nombre,  b.apellido) as nombres, c.descripcion as tipopedido, b.direccion from pedido a, cliente b, tipo_pedido c  where a.idcliente = b.idcliente and a.idtipopedido = c.idtipopedido and fechapedido = '" + fechaPedido + "'";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
			int numeroColumnas = rsMd.getColumnCount();
			
			while(rs.next()){
				String [] fila = new String[numeroColumnas];
				for(int y = 0; y < numeroColumnas; y++)
				{
					fila[y] = rs.getString(y+1);
				}
				pedidos.add(fila);
				
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.info(e.toString());
			System.out.println(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(pedidos);
		
	}
	
	/**
	 * Método que permite retornar los pedidos del sistema de acuerdo a los parámetros de tipo de pedido y fecha
	 * @param idTipoPedido valor para filtrar ciertos tipos de pedido dentro de la consulta
	 * @param fechaPedido fecha de apertura para la cual se realiza el pedido
	 * @return Se retornar un ArrayList con todos los pedidos que cumplen las condiciones indicadas
	 */
	public static ArrayList obtenerPedidosPorTipo(int idTipoPedido, String fechaPedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList pedidos = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idpedidotienda, a.fechapedido, concat_ws(' ', b.nombre,  b.apellido) as nombres, c.descripcion as tipopedido, b.direccion from pedido a, cliente b, tipo_pedido c  where a.idcliente = b.idcliente and a.idtipopedido = c.idtipopedido and a.idtipopedido = " + idTipoPedido + " and fechapedido = '" + fechaPedido + "'";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
			int numeroColumnas = rsMd.getColumnCount();
			
			while(rs.next()){
				String [] fila = new String[numeroColumnas];
				for(int y = 0; y < numeroColumnas; y++)
				{
					fila[y] = rs.getString(y+1);
				}
				pedidos.add(fila);
				
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.info(e.toString());
			System.out.println(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(pedidos);
		
	}

}
