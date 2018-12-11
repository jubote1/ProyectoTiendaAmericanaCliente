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
import capaModelo.EstadoPedido;
import capaModelo.Pedido;
import capaModelo.Tienda;

public class PedidoDAO {
	
	public static int InsertarEncabezadoPedido(int idtienda, int idcliente, String fechaPedido, String user, boolean auditoria)
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
			Statement stm = con1.createStatement();
			String insert = "insert into pedido (idtienda,idcliente,fechapedido, usuariopedido) values (" + idtienda + ", " + idcliente + ", '" + fechaPedido  + "' , '" + user + "')"; 
			if(auditoria)
			{
				logger.info(insert);
			}
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idPedidoInsertado=rs.getInt(1);
				if(auditoria)
				{
					logger.info(idPedidoInsertado);
				}
				
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
	public static boolean finalizarPedido(int idpedido,  double tiempopedido, int idTipoPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			double valorTotal = 0;
			double valorImpuesto = 0;
			Statement stm = con1.createStatement();
			String consulta = "select sum(valorTotal), sum(valorimpuesto) from detalle_pedido where idpedidotienda = " + idpedido + " and cantidad >= 0  and idmotivoanulacion IS NULL"; 
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				valorTotal = rs.getDouble(1);
				valorImpuesto = rs.getDouble(2);
				break;
			}
			String update = "update pedido set total_bruto =" + (valorTotal - valorImpuesto) + " , impuesto = " + valorImpuesto + " , total_neto =" + valorTotal + " , idtipopedido =" + idTipoPedido + " where idpedidotienda = " + idpedido;
			if(auditoria)
			{
				logger.info(update);
			}
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
	
	public static double obtenerTotalBrutoPedido(int idpedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		double valorTotal = 0;
		try
		{
			
			Statement stm = con1.createStatement();
			String consulta = "select sum(valorTotal) from detalle_pedido where idpedidotienda = " + idpedido + " and cantidad >= 0 and idmotivoanulacion IS NULL" ; 
			if(auditoria)
			{
				logger.info(consulta);
			}
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
	public static boolean ActualizarEstadoPedido(int idPedido, int idEstadoAnterior, int idEstadoPosterior, String usuario, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			double valorTotal = 0;
			Statement stm = con1.createStatement();
			String insert = "insert into cambios_estado_pedido (idpedidotienda, idestadoanterior, idestadoposterior, usuario) values (" + idPedido + " , " + idEstadoAnterior + " , " + idEstadoPosterior + " , '" + usuario + "')" ; 
			if(auditoria)
			{
				logger.info(insert);
			}
			stm.executeUpdate(insert);
			String update = "update pedido set idestado =" + idEstadoPosterior + " where idpedidotienda= " + idPedido ;
			if(auditoria)
			{
				logger.info(update);
			}
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
	
	/**
	 * Método que en la capaDAO se encarga de la actualización del pedido con un cliete determinado esto debido a qu que un cliente puede ser asociado cuando ya el pedido fuera entregado
	 * @param idPedido
	 * @param idCliente
	 * @param auditoria
	 * @return
	 */
	public static boolean actualizarClientePedido(int idPedido, int idCliente, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update pedido set idcliente =" + idCliente + " where idpedidotienda= " + idPedido ;
			if(auditoria)
			{
				logger.info(update);
			}
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
	
	
	/**
	 * 
	 * @param idPedido
	 * @param idDomiciliario
	 * @param auditoria
	 * @return
	 */
	public static boolean actualizarDomiciliarioPedido(int idPedido, int idDomiciliario, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update pedido set iddomiciliario =" + idDomiciliario + " where idpedidotienda= " + idPedido ;
			if(auditoria)
			{
				logger.info(update);
			}
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
	
	public static boolean quitarDomiciliarioPedido(int idPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update pedido set iddomiciliario = '' where idpedidotienda= " + idPedido ;
			if(auditoria)
			{
				logger.info(update);
			}
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
	
	public static boolean eliminarPedido(int idPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from pedido  where idpedidotienda = " + idPedido ;
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
	
	public static boolean anularPedido(int idPedido, int idMotivoAnulacion, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		
		try
		{
			Statement stm = con1.createStatement();
			String update = "update pedido set total_neto = 0 , idmotivoanulacion = " + idMotivoAnulacion + " where idpedidotienda = " + idPedido ;
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
	 * Método que se encarga de quitar la anulación de un pedido.
	 * @param idPedido
	 * @param idMotivoAnulacion
	 * @param auditoria
	 * @return
	 */
	public static boolean quitarAnulacionPedido(int idPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		
		try
		{
			Statement stm = con1.createStatement();
			String update = "update pedido set idmotivoanulacion = null where idpedidotienda = " + idPedido ;
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
	 * Método de capa DAO que retorna un arrayList todos los pedidos dada una fecha determinada, con este método se tiene
	 * la base para la pantalla transaccional, incluye los pedidos que están en un estado final.
	 * @param fechaPedido Se recibe como parámetro la fecha para consultar los pedidos.
	 * @return Se retorna un ArrayList con todos los pedidos del sistema para el parámetro de fecha.
	 */
	public static ArrayList obtenerPedidosTableConFinales(String fechaPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList pedidos = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			//En esta consulta incluimos los pedidos anulados como se puede ver no tiene la condición idmotivoanulacion IS NULL
			String consulta = "select a.idpedidotienda, a.fechapedido, concat_ws(' ', b.nombre,  b.apellido) as nombres, c.descripcion as tipopedido, d.descripcion_corta as estado, b.direccion, a.idtipopedido, a.idestado, e.nombre_largo, '' from estado d, cliente b, tipo_pedido c, pedido a left outer join usuario e on a.iddomiciliario = e.id where a.idestado = d.idestado and a.idcliente = b.idcliente and a.idtipopedido = c.idtipopedido and fechapedido = '" + fechaPedido + "' order by a.fechainsercion desc";
			System.out.println(consulta);
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
				pedidos.add(fila);
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
		return(pedidos);
		
	}
	
	/**
	 * Método de capa DAO que retorna un arrayList todos los pedidos dada una fecha determinada, con este método se tiene
	 * la base para la pantalla transaccional, se extraen los pedidos que están en un estado final
	 * @param fechaPedido Se recibe como parámetro la fecha para consultar los pedidos.
	 * @return Se retorna un ArrayList con todos los pedidos del sistema para el parámetro de fecha.
	 */
	public static ArrayList obtenerPedidosTable(String fechaPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList pedidos = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idpedidotienda, a.fechapedido, concat_ws(' ', b.nombre,  b.apellido) as nombres, c.descripcion as tipopedido, d.descripcion_corta as estado, b.direccion, a.idtipopedido, a.idestado, e.nombre_largo, '' from estado d, cliente b, tipo_pedido c, pedido a left outer join usuario e on a.iddomiciliario = e.id  where a.idestado = d.idestado and a.idcliente = b.idcliente and d.estado_final <> 1 and a.idmotivoanulacion IS NULL and a.idtipopedido = c.idtipopedido and fechapedido = '" + fechaPedido + "' order by a.fechainsercion desc";
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
				pedidos.add(fila);
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
		return(pedidos);
		
	}
	
	/**
	 * Método que permite retornar los pedidos del sistema de acuerdo a los parámetros de tipo de pedido y fecha, se excluyen 
	 * los pedido que están en estado final
	 * @param idTipoPedido valor para filtrar ciertos tipos de pedido dentro de la consulta
	 * @param fechaPedido fecha de apertura para la cual se realiza el pedido
	 * @return Se retornar un ArrayList con todos los pedidos que cumplen las condiciones indicadas
	 */
	public static ArrayList obtenerPedidosPorTipo(int idTipoPedido, String fechaPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList pedidos = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idpedidotienda, a.fechapedido, concat_ws(' ', b.nombre,  b.apellido) as nombres, c.descripcion as tipopedido, d.descripcion_corta as estado, b.direccion, a.idtipopedido, a.idestado, e.nombre_largo, '' from estado d, cliente b, tipo_pedido c, pedido a left outer join usuario e on a.iddomiciliario = e.id  where a.idestado = d.idestado and a.idcliente = b.idcliente and d.estado_final <> 1 and a.idmotivoanulacion IS NULL and a.idtipopedido = c.idtipopedido and a.idtipopedido = " + idTipoPedido + " and fechapedido = '" + fechaPedido + "' order by a.fechainsercion desc";
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
				pedidos.add(fila);
				
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
		return(pedidos);
		
	}
	
	public static ArrayList obtenerPedidosEmpacadosDomicilio(String fechaPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList pedidos = new ArrayList();
		//En este punto y de manera temporal vamos a quemar el idEstado del producto empacado para domicilios
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idpedidotienda, a.fechapedido, concat_ws(' ', b.nombre,  b.apellido) as nombres, c.descripcion as tipopedido, d.descripcion_corta as estado, b.direccion, a.idtipopedido, a.idestado, b.latitud, b.longitud, '' from pedido a, cliente b, tipo_pedido c, estado d  where a.idestado = d.idestado and a.idcliente = b.idcliente and d.estado_final <> 1 and a.idmotivoanulacion IS NULL and a.idtipopedido = c.idtipopedido and a.idestado = " + "3" + " and fechapedido = '" + fechaPedido + "' order by a.fechainsercion desc";
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
				pedidos.add(fila);
				
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
		return(pedidos);
		
	}
	
	public static Estado obtenerEstadoPedido(int idPedidoTienda, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		Estado estadoPedido = new Estado(0, "", "", 0, "", false, false, 0, 0, 0, false,false, false);
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idestado, b.descripcion, c.descripcion as desc_tipo, a.idtipopedido, b.imagen  from pedido a, estado b, tipo_pedido c   where  a.idtipopedido = c.idtipopedido and a.idestado = b.idestado and a.idtipopedido = b.idtipopedido and idpedidotienda = " + idPedidoTienda + "";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idEstado = 0;
			int idTipoPedido = 0;
			String descEstado = "";
			String descTipo = "";
			byte[] imagen = null;
			while(rs.next()){
				idEstado = rs.getInt("idestado");
				idTipoPedido = rs.getInt("idtipopedido");
				descEstado = rs.getString("descripcion");
				descTipo = rs.getString("desc_tipo");
				imagen = rs.getBytes("imagen");
				
			}
			estadoPedido = new Estado(idEstado,descEstado, descEstado, idTipoPedido, "", false, false, 0, 0, 0, false, false,false);
			estadoPedido.setImagen(imagen);
			estadoPedido.setTipoPedido(descTipo);
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
		return(estadoPedido);
		
	}
	
	public static ArrayList<EstadoPedido> obtenerEstadoPedidosFecha(String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<EstadoPedido> estadosPedidos = new ArrayList();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idpedidotienda, a.idestado, b.estado_final from pedido a, estado b  where a.idestado = b.idestado and a.fechapedido = '" + fecha + "'";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idPedidoTienda = 0;
			int idEstado = 0;
			int intEstadoFinal = 0;
			boolean estadoFinal = false;
			EstadoPedido estPedido = new EstadoPedido(0,0,false);
			while(rs.next()){
				idPedidoTienda = rs.getInt("idpedidotienda");
				idEstado = rs.getInt("idestado");
				intEstadoFinal = rs.getInt("estado_final");
				if(intEstadoFinal == 1)
				{
					estadoFinal = true;
				}
				estPedido = new EstadoPedido(idPedidoTienda, idEstado, estadoFinal);
				estadosPedidos.add(estPedido);
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
		return(estadosPedidos);
		
	}
	
	public static Cliente obtenerClientePedido(int idPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		Cliente cliente = new Cliente();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select b.idcliente, b.nombre, b.apellido, b.telefono , b.direccion from pedido a, cliente b  where  a.idcliente = b.idcliente and  a.idpedidotienda = " + idPedido + "";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idCliente = 0;
			String nombre = "";
			String apellido = "";
			String telefono = "";
			String direccion = "";
			while(rs.next()){
				idCliente = rs.getInt("idcliente");
				nombre = rs.getString("nombre");
				apellido = rs.getString("apellido");
				telefono = rs.getString("telefono");
				direccion = rs.getString("direccion");
				cliente = new Cliente(idCliente, telefono,nombre, apellido);
				cliente.setDireccion(direccion);
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
		return(cliente);
		
	}
	
	public static int obtenerTipoPedido(int idPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		int idTipoPedido = 0;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idtipopedido  from pedido a  where  a.idpedidotienda = " + idPedido + "";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			
			while(rs.next()){
				idTipoPedido = rs.getInt("idtipopedido");
				
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
		return(idTipoPedido);
		
	}
	
	/**
	 * Método que sirve para la validación si hay pedidos en estados no finales, este método en la condición de base de datos
	 * también valida que el pedid este anulado, pues en este caso puede quedar en un estado que no sea intermedio.
	 * @param fecha Recibe una fecha determinada dado que se corre como parte de validación del cierre.
	 * @return un valor booleano que indica un true si hay pedidos pendientes o un false en caso de no ser así.
	 */
	public static boolean validarEstadosFinalesPedido(String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idpedidotienda from pedido a where a.idmotivoanulacion IS NULL and a.fechapedido = '" + fecha + "' " 
			+ "and a.idestado in(select b.idestado from estado b  where b.estado_final <> 1)" ;
			if(auditoria)
			{
				logger.info(consulta);
			}
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
	
	/**
	 * Método que retorna los totales por tipo de pedido de acuerdo a las facturas, para informe antes de cierre.
	 * @param fechaPedido Se recibe como parámetro la fecha del sistema.
	 * @return se retornar un ArrayList con los totales por tipo de pedido
	 */
	public static ArrayList obtenerTotalesPedidosPorTipo(String fechaPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList totalPorTipo = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select b.descripcion, sum(a.total_neto) from pedido a , tipo_pedido b where a.idmotivoanulacion IS NULL and  a.idtipopedido = b.idtipopedido and fechapedido = '" + fechaPedido +"' group by b.descripcion ";
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
				totalPorTipo.add(fila);
				
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
		return(totalPorTipo);
		
	}
	
	/**
	 * Método que sirve como base para desplegar los pedidos qeu se ven en la comanda de pedidos, cabe destacar que 
	 * se filtran los pedidos que estén eliminados, dado que estos en caso de que sean elimados no se deben de ver.
	 * @param fechaPedido
	 * @param idTipoEmpleado
	 * @return
	 */
	public static ArrayList obtenerPedidosVentanaComanda(String fechaPedido, int idTipoEmpleado, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList pedidos = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "";
			if(idTipoEmpleado != 0)
			{
				consulta = "select 'false', a.idpedidotienda, a.fechapedido, concat_ws(' ', b.nombre,  b.apellido) as nombres, c.descripcion as tipopedido, d.descripcion_corta as estado, b.direccion, a.idtipopedido, a.idestado, e.nombre_largo, '' from estado d, cliente b, tipo_pedido c, pedido AS a LEFT JOIN usuario AS e on a.iddomiciliario = e.id  where d.estado_final = 0 and c.esdomicilio = 1 and a.idmotivoanulacion IS NULL and a.idestado = d.idestado and a.idcliente = b.idcliente and a.idtipopedido = c.idtipopedido and fechapedido = '" + fechaPedido + "' and a.idestado in (select e.idestado from tipo_empleado_estados e where e.idtipoempleado =" + idTipoEmpleado +") order by tipopedido , a.idpedidotienda desc";
			}
			else
			{
				consulta = "select 'false', a.idpedidotienda, a.fechapedido, concat_ws(' ', b.nombre,  b.apellido) as nombres, c.descripcion as tipopedido, d.descripcion_corta as estado, b.direccion, a.idtipopedido, a.idestado, e.nombre_largo, '' from estado d, cliente b, tipo_pedido c, pedido AS a LEFT JOIN usuario AS e on a.iddomiciliario = e.id  where d.estado_final = 0 and c.esdomicilio = 1 and a.idmotivoanulacion IS NULL and a.idestado = d.idestado and a.idcliente = b.idcliente and a.idtipopedido = c.idtipopedido and fechapedido = '" + fechaPedido + "'  order by  a.idpedidotienda desc";
			}
			System.out.println(consulta);
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
				pedidos.add(fila);
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
		return(pedidos);
		
	}
	
	
	/**
	 * Este método me mostrará los pedidos para un domiciliario teniendo como premisa que se dispone a sair a entregar los pedidos.
	 * @param fechaPedido
	 * @param idTipoEmpleado
	 * @param idDomiciliario
	 * @param auditoria
	 * @return
	 */
	public static ArrayList obtenerPedidosVentanaComandaDom(String fechaPedido, int idTipoEmpleado, int idDomiciliario, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList pedidos = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			//La consulta original en su primera parte trae los pedidos para salir a entregar, el segundo trae los pedidos que están en ruta y el tercero trae los pedidos entregados, vamos a modificar que solo traiga lo disponible para salir a llevar.
//			String consulta = " (select 'false',a.idpedidotienda, a.fechapedido, concat_ws(' ', b.nombre,  b.apellido) as nombres, c.descripcion as tipopedido, d.descripcion_corta as estado, b.direccion, a.idtipopedido, a.idestado, e.nombre_largo, '' from estado d, cliente b, tipo_pedido c, pedido a left outer join usuario e on a.iddomiciliario = e.id  where d.estado_final = 0 and c.esdomicilio = 1 and a.idmotivoanulacion IS NULL and a.idestado = d.idestado and a.idcliente = b.idcliente and a.idtipopedido = c.idtipopedido and fechapedido = '" + fechaPedido + "' and a.idestado in (select e.idestado from tipo_empleado_estados e where e.idtipoempleado =" + idTipoEmpleado +")) "
//					+ " UNION " +
//					"(select 'false',a.idpedidotienda, a.fechapedido, concat_ws(' ', b.nombre,  b.apellido) as nombres, c.descripcion as tipopedido, d.descripcion_corta as estado, b.direccion, a.idtipopedido, a.idestado, e.nombre_largo, '' from estado d, cliente b, tipo_pedido c, pedido a left outer join usuario e on a.iddomiciliario = e.id where d.estado_final = 0 and c.esdomicilio = 1 and a.idmotivoanulacion IS NULL and a.idestado = d.idestado and a.idcliente = b.idcliente and a.idtipopedido = c.idtipopedido and fechapedido = '" + fechaPedido + "' and d.ruta_domicilio = 1 and a.iddomiciliario = " + idDomiciliario  + ")"
//					+ " UNION " +
//					"(select 'false',a.idpedidotienda, a.fechapedido, concat_ws(' ', b.nombre,  b.apellido) as nombres, c.descripcion as tipopedido, d.descripcion_corta as estado, b.direccion, a.idtipopedido, a.idestado, e.nombre_largo, '' from estado d, cliente b, tipo_pedido c, pedido a left outer join usuario e on a.iddomiciliario = e.id where d.estado_final = 0 and c.esdomicilio = 1 and a.idmotivoanulacion IS NULL and a.idestado = d.idestado and a.idcliente = b.idcliente and a.idtipopedido = c.idtipopedido and fechapedido = '" + fechaPedido + "' and d.entrega_domicilio = 1 and a.iddomiciliario = " + idDomiciliario + ") order by idpedidotienda desc";
			String consulta = " select 'false',a.idpedidotienda, a.fechapedido, concat_ws(' ', b.nombre,  b.apellido) as nombres, c.descripcion as tipopedido, d.descripcion_corta as estado, b.direccion, a.idtipopedido, a.idestado, e.nombre_largo, '' from estado d, cliente b, tipo_pedido c, pedido a left outer join usuario e on a.iddomiciliario = e.id  where d.estado_final = 0 and c.esdomicilio = 1 and a.idmotivoanulacion IS NULL and a.idestado = d.idestado and a.idcliente = b.idcliente and a.idtipopedido = c.idtipopedido and fechapedido = '" + fechaPedido + "' and a.idestado in (select e.idestado from tipo_empleado_estados e where e.idtipoempleado =" + idTipoEmpleado +") order by idpedidotienda desc";
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
				pedidos.add(fila);
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
		return(pedidos);
		
	}
	
	/**
	 * Método que trae para un domiciliario determinado domiciliaros los pedidos que tiene en ruta
	 * @param fechaPedido
	 * @param idTipoEmpleado
	 * @param idDomiciliario
	 * @param auditoria
	 * @return
	 */
	public static ArrayList obtenerPedidosVentanaComandaDomEnRuta(String fechaPedido, int idTipoEmpleado, int idDomiciliario, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList pedidos = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			//La consulta original en su primera parte trae los pedidos para salir a entregar, el segundo trae los pedidos que están en ruta y el tercero trae los pedidos entregados, vamos a modificar que solo traiga lo disponible para salir a llevar.
			String consulta ="select 'false',a.idpedidotienda, a.fechapedido, concat_ws(' ', b.nombre,  b.apellido) as nombres, c.descripcion as tipopedido, d.descripcion_corta as estado, b.direccion, a.idtipopedido, a.idestado, e.nombre_largo, '' from estado d, cliente b, tipo_pedido c, pedido a left outer join usuario e on a.iddomiciliario = e.id where d.estado_final = 0 and c.esdomicilio = 1 and a.idmotivoanulacion IS NULL and a.idestado = d.idestado and a.idcliente = b.idcliente and a.idtipopedido = c.idtipopedido and fechapedido = '" + fechaPedido + "' and d.ruta_domicilio = 1 and a.iddomiciliario = " + idDomiciliario  + " order by idpedidotienda desc";
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
				pedidos.add(fila);
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
		return(pedidos);
		
	}
	
	public static ArrayList ObtenerPedidosVentanaComandaHistorial(String fechaPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList pedidos = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta;
			consulta = "select 'false', a.idpedidotienda, a.fechapedido, concat_ws(' ', b.nombre,  b.apellido) as nombres, c.descripcion as tipopedido, d.descripcion_corta as estado, b.direccion, a.idtipopedido, a.idestado, e.nombre_largo, '' from estado d, cliente b, tipo_pedido c, pedido a left outer join usuario e on a.iddomiciliario = e.id where d.estado_final = 1 and c.esdomicilio = 1 and a.idmotivoanulacion IS NULL and a.idestado = d.idestado and a.idcliente = b.idcliente and a.idtipopedido = c.idtipopedido and fechapedido = '" + fechaPedido + "' order by a.idpedidotienda desc";
			
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
				pedidos.add(fila);
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
		return(pedidos);
		
	}
	
	public static ArrayList obtenerPedidosVentanaComandaTipPed(String fechaPedido, int idTipoEmpleado, int idTipoPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList pedidos = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta;
			if(idTipoEmpleado != 0)
			{
				consulta = "select 'false', a.idpedidotienda, a.fechapedido, concat_ws(' ', b.nombre,  b.apellido) as nombres, c.descripcion as tipopedido, d.descripcion_corta as estado, b.direccion, a.idtipopedido, a.idestado, e.nombre_largo, '' from estado d, cliente b, tipo_pedido c, pedido a left outer join usuario e on a.iddomiciliario = e.id where d.estado_final = 0 and c.esdomicilio = 1 and a.idmotivoanulacion IS NULL and a.idestado = d.idestado and a.idcliente = b.idcliente and a.idtipopedido = c.idtipopedido and fechapedido = '" + fechaPedido + "' and a.idtipopedido = " + idTipoPedido + " and a.idestado in (select e.idestado from tipo_empleado_estados e where e.idtipoempleado =" + idTipoEmpleado +") order by tipopedido , a.idpedidotienda desc";
			}
			else
			{
				consulta = "select 'false', a.idpedidotienda, a.fechapedido, concat_ws(' ', b.nombre,  b.apellido) as nombres, c.descripcion as tipopedido, d.descripcion_corta as estado, b.direccion, a.idtipopedido, a.idestado, e.nombre_largo, '' from estado d, cliente b, tipo_pedido c, pedido a left outer join usuario e on a.iddomiciliario = e.id where d.estado_final = 0 and c.esdomicilio = 1 and a.idmotivoanulacion IS NULL and a.idestado = d.idestado and a.idcliente = b.idcliente and a.idtipopedido = c.idtipopedido and fechapedido = '" + fechaPedido + "' and a.idtipopedido = " + idTipoPedido +" order by a.idpedidotienda desc";
			}
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
				pedidos.add(fila);
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
		return(pedidos);
		
	}
	
	public static Pedido obtenerPedido(int idPedidoTienda, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		Pedido pedRsta = new Pedido();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idpedidotienda, a.fechapedido, concat_ws(' ', b.nombre,  b.apellido) as nombres, c.descripcion as tipopedido, d.descripcion_corta as estado, b.direccion, a.idtipopedido, a.idestado, a.total_bruto, a.total_neto, a.impuesto from pedido a, cliente b, tipo_pedido c, estado d  where a.idestado = d.idestado and a.idcliente = b.idcliente and a.idtipopedido = c.idtipopedido and a.idpedidotienda = " + idPedidoTienda;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
					
			while(rs.next()){
				String fechaPed = rs.getString("fechapedido");
				pedRsta.setFechapedido(fechaPed);
				pedRsta.setIdpedidotienda(idPedidoTienda);
				String nombreCliente = rs.getString("nombres");
				pedRsta.setNombreCliente(nombreCliente);
				String tipoPedido = rs.getString("tipopedido");
				pedRsta.setTipoPedido(tipoPedido);
				String direccion = rs.getString("direccion");
				pedRsta.setDirCliente(direccion);
				double totalBruto = rs.getDouble("total_bruto");
				pedRsta.setValorbruto(totalBruto);
				double totalNeto = rs.getDouble("total_neto");
				pedRsta.setValorneto(totalNeto);
				double impuesto = rs.getDouble("impuesto");
				pedRsta.setImpuesto(impuesto);
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
		return(pedRsta);
		
	}

}
