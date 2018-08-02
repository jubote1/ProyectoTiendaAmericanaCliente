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
import capaModelo.Estado;
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
//		try
//		{
//			fechaTemporal = new SimpleDateFormat("dd/MM/yyyy").parse(fechaPedido);
//			fechaPedidoFinal = formatoFinal.format(fechaTemporal);
//			
//		}catch(Exception e){
//			logger.error(e.toString());
//			System.out.println(e.toString());
//			return(0);
//		}
		
		
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into pedido (idtienda,idcliente,fechapedido, usuariopedido) values (" + idtienda + ", " + idcliente + ", '" + fechaPedido  + "' , '" + user + "')"; 
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
			System.out.println(e.toString());
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
			double valorImpuesto = 0;
			Statement stm = con1.createStatement();
			String consulta = "select sum(valorTotal), sum(valorimpuesto) from detalle_pedido where idpedidotienda = " + idpedido + " and cantidad >= 0 " ; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				valorTotal = rs.getDouble(1);
				valorImpuesto = rs.getDouble(2);
				break;
			}
			String update = "update pedido set total_bruto =" + (valorTotal - valorImpuesto) + " , impuesto = " + valorImpuesto + " , total_neto =" + valorTotal + " , idtipopedido =" + idTipoPedido + " where idpedidotienda = " + idpedido;
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
	
	public static double obtenerTotalBrutoPedido(int idpedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		double valorTotal = 0;
		try
		{
			
			Statement stm = con1.createStatement();
			String consulta = "select sum(valorTotal) from detalle_pedido where idpedidotienda = " + idpedido + " and cantidad >= 0 " ; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				valorTotal = rs.getDouble(1);
				break;
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
		return(valorTotal );
	}
	
	/**
	 * Método que se encagará de la actualización de los estados de un pedido y adicionalmente dejará el log con el fin de poder conocer estado de un pedido
	 * @param idPedido el idpedidotienda del pedido a al cual se le va a cambiar el estado.
	 * @param idEstadoAnterior
	 * @param idEstadoPosterior
	 * @return
	 */
	public static boolean ActualizarEstadoPedido(int idPedido, int idEstadoAnterior, int idEstadoPosterior)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			double valorTotal = 0;
			Statement stm = con1.createStatement();
			String insert = "insert into cambios_estado_pedido (idpedidotienda, idestadoanterior, idestadoposterior) values (" + idPedido + " , " + idEstadoAnterior + " , " + idEstadoPosterior + ")" ; 
			logger.info(insert);
			stm.executeUpdate(insert);
			String update = "update pedido set idestado =" + idEstadoPosterior + " where idpedidotienda= " + idPedido ;
			logger.info(update);
			stm.executeUpdate(update);
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
			String delete = "delete from pedido  where idpedidotienda = " + idPedido ;
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
	
	public static boolean anularPedido(int idPedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		
		try
		{
			Statement stm = con1.createStatement();
			String update = "update pedido set total_neto = total_neto*-1  where idpedidotienda = " + idPedido ;
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
	
	
	public static ArrayList obtenerPedidosTable(String fechaPedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList pedidos = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idpedidotienda, a.fechapedido, concat_ws(' ', b.nombre,  b.apellido) as nombres, c.descripcion as tipopedido, d.descripcion_corta as estado, b.direccion, a.idtipopedido, a.idestado, '' from pedido a, cliente b, tipo_pedido c, estado d  where a.idestado = d.idestado and a.idcliente = b.idcliente and a.idtipopedido = c.idtipopedido and fechapedido = '" + fechaPedido + "'";
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
			String consulta = "select a.idpedidotienda, a.fechapedido, concat_ws(' ', b.nombre,  b.apellido) as nombres, c.descripcion as tipopedido, d.descripcion_corta as estado, b.direccion, a.idtipopedido, a.idestado, '' from pedido a, cliente b, tipo_pedido c, estado d  where a.idestado = d.idestado and a.idcliente = b.idcliente and a.idtipopedido = c.idtipopedido and a.idtipopedido = " + idTipoPedido + " and fechapedido = '" + fechaPedido + "'";
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
	
	public static Estado obtenerEstadoPedido(int idPedidoTienda)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		Estado estadoPedido = new Estado(0, "", "", 0, "", false, false, 0, 0, 0, false);
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idestado, b.descripcion, c.descripcion as desc_tipo, a.idtipopedido  from pedido a, estado b, tipo_pedido c   where  a.idtipopedido = c.idtipopedido and a.idestado = b.idestado and a.idtipopedido = b.idtipopedido and idpedidotienda = " + idPedidoTienda + "";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idEstado = 0;
			int idTipoPedido = 0;
			String descEstado = "";
			String descTipo = "";
			while(rs.next()){
				idEstado = rs.getInt("idestado");
				idTipoPedido = rs.getInt("idtipopedido");
				descEstado = rs.getString("descripcion");
				descTipo = rs.getString("desc_tipo");
			}
			estadoPedido = new Estado(idEstado,descEstado, descEstado, idTipoPedido, "", false, false, 0, 0, 0, false);
			estadoPedido.setTipoPedido(descTipo);
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
		return(estadoPedido);
		
	}
	
	public static Cliente obtenerClientePedido(int idPedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		Cliente cliente = new Cliente(0, "", "", "");
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select b.idcliente, b.nombre, b.apellido, b.telefono  from pedido a, cliente b  where  a.idcliente = b.idcliente and  a.idpedidotienda = " + idPedido + "";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idCliente = 0;
			String nombre = "";
			String apellido = "";
			String telefono = "";
			while(rs.next()){
				idCliente = rs.getInt("idcliente");
				nombre = rs.getString("nombre");
				apellido = rs.getString("apellido");
				telefono = rs.getString("telefono");
				cliente = new Cliente(idCliente, nombre, apellido, telefono);
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
		return(cliente);
		
	}
	
	public static int obtenerTipoPedido(int idPedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		int idTipoPedido = 0;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idtipopedido  from pedido a  where  a.idpedidotienda = " + idPedido + "";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			
			while(rs.next()){
				idTipoPedido = rs.getInt("idtipopedido");
				
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
		return(idTipoPedido);
		
	}
	
	public static boolean validarEstadosFinalesPedido(String fecha)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idpedidotienda from pedido a where a.fechapedido = '" + fecha + "' " 
			+ "and a.idestado in(select b.idestado from estado b  where b.estado_final <> 1)" ;
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next())
			{
				return(respuesta=true);
			}
			stm.close();
			con1.close();
			return(respuesta = false);
		}
		catch (Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
			return(respuesta = false);
		}
		
		
	}
	
	public static ArrayList obtenerTotalesPedidosPorTipo(String fechaPedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList totalPorTipo = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select b.descripcion, sum(a.total_neto) from pedido a , tipo_pedido b where a.idtipopedido = b.idtipopedido and fechapedido = '" + fechaPedido +"' group by b.descripcion ";
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
				totalPorTipo.add(fila);
				
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
		return(totalPorTipo);
		
	}
	

}
