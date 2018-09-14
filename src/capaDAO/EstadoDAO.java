package capaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import capaModelo.Usuario;
import capaConexion.ConexionBaseDatos;
import capaModelo.Estado;
import capaModelo.Impuesto;
import capaModelo.MenuAgrupador;
import capaModelo.Producto;
import capaModelo.Tienda;
import org.apache.log4j.Logger;
import com.mysql.jdbc.ResultSetMetaData;
/**
 * Clase que se encarga de implementar todo lo relacionado con la base de datos de la entidad producto
 * @author JuanDavid
 *
 */
public class EstadoDAO {
	
/**
 * Método que se encarga de retonar todos los productos parametrizado en el sistema.
 * @return Retorna un arrayList con tipos de datos genéricos.
 */
	public static ArrayList obtenerEstado()
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList estados = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idestado, a.descripcion, b.descripcion, b.idtipopedido, a.descripcion_corta from estado a, tipo_pedido b where a.idtipopedido = b.idtipopedido";
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
				estados.add(fila);
				
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
		return(estados);
		
	}
	
	//Realizamos modificación para detectar si el pedido está en un estado final o no, para de esta manera
	//tomar unas u otras decisiones al momento del despliegue de los tiempos de un pedido.
	public static ArrayList obtenerHistoriaEstadoPedido(int idPedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList historiaEstados = new ArrayList();
		int idEstado = 0;
		try
		{
			Estado estadoPedido = PedidoDAO.obtenerEstadoPedido(idPedido);
			//En este punto ya obtenenido el idEstado, nos disponemos a verificar si es un estado final o no.
			boolean esEstFinal = EstadoDAO.esEstadoFinal(estadoPedido.getIdestado());
			System.out.println(idPedido + " " + esEstFinal);
			Statement stm = con1.createStatement();
			String consulta = "select a.fechacambio, IFNULL(b.descripcion, 'Tomando Pedido'), IFNULL(c.descripcion, 'Tomando Pedido')  from cambios_estado_pedido a left outer join  estado b on a.idestadoanterior = b.idestado left outer join estado c on a.idestadoposterior = c.idestado where a.idpedidotienda = " + idPedido + " order by fechacambio asc";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
			int numeroColumnas = rsMd.getColumnCount() ;
			
			while(rs.next()){
				String [] fila = new String[numeroColumnas + 1];
				for(int y = 0; y < numeroColumnas; y++)
				{
					fila[y] = rs.getString(y+1);
				}
				fila[numeroColumnas] = Boolean.toString(esEstFinal);
				historiaEstados.add(fila);
				
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
		return(historiaEstados);
		
	}
	
	public static boolean esEstadoFinal(int idEstado)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = false;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from estado where estado_final = 1 and  idestado = " + idEstado; 
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
				respuesta = false;
			}catch(Exception e1)
			{
			}
			
		}
		return(respuesta);
	}
	
	public static ArrayList<Estado> obtenerTodosEstado()
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<Estado> estados = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from estado";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idestado, idTipoPedido;
			String descripcion, descripcionCorta;
			boolean estInicial = false;
			boolean estFinal = false;
			boolean impresion = false;
			int intEstInicial, intEstFinal, intImpresion;
			int colorr=0, colorg=0, colorb=0;
			while(rs.next()){
				idestado = rs.getInt("idestado");
				descripcion = rs.getString("descripcion");
				descripcionCorta = rs.getString("descripcion_corta");
				idTipoPedido = rs.getInt("idtipopedido");
				intEstInicial = rs.getInt("estado_inicial");
				intEstFinal = rs.getInt("estado_final");
				colorr = rs.getInt("colorr");
				colorg = rs.getInt("colorg");
				colorb = rs.getInt("colorb");
				intImpresion = rs.getInt("impresion");
				if(intImpresion == 1)
				{
					impresion = true;
				}
				if(intEstInicial == 1)
				{
					estInicial = true;
				}
				if(intEstFinal == 1)
				{
					estFinal = true;
				}
				Estado est = new Estado(idestado, descripcion, descripcionCorta, idTipoPedido, "", estInicial, estFinal, colorr, colorg, colorb, impresion);
				estados.add(est);
				
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
		return(estados);
		
	}
	
	/**
	 * Método que se encarga de retornar los estados que tiene asignados un tipo de empleado para ver en la ventana de comanda de pedidos
	 * @param idTipoPedido Se recibe como parámetro un valor entero con base
	 * @return un Arraylist con objetos de tipo estado, de acuerdo al idtipoempleado.
	 */
	
	
	
	public static Estado obtenerEstado(int idEstado)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		Estado estado = new Estado();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from estado where idestado = " + idEstado;
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idTipoPedido = 0;
			String descripcion = "";
			String descripcionCorta = "";
			boolean estInicial = false;
			boolean estFinal = false;
			boolean impresion = false;
			int intEstInicial, intEstFinal, intImpresion;	
			int colorr=0, colorg=0, colorb=0;
			byte[] imagen = null;
			while(rs.next()){
				idTipoPedido = rs.getInt("idtipopedido");
				descripcion = rs.getString("descripcion");
				descripcionCorta = rs.getString("descripcion_corta");
				intEstInicial = rs.getInt("estado_inicial");
				intEstFinal = rs.getInt("estado_final");
				intImpresion = rs.getInt("impresion");
				colorr = rs.getInt("colorr");
				colorg = rs.getInt("colorg");
				colorb = rs.getInt("colorb");
				imagen = rs.getBytes("imagen");
				if(intImpresion == 1)
				{
					impresion = true;
				}
				if(intEstInicial == 1)
				{
					estInicial = true;
				}
				if(intEstFinal == 1)
				{
					estFinal = true;
				}
				estado = new Estado(idEstado, descripcion, descripcionCorta,idTipoPedido, "", estInicial, estFinal,colorr,colorg,colorb, impresion);
				estado.setImagen(imagen);
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
		return(estado);
		
	}
	
	
	public static int insertarEstado(Estado estado)
	{
		Logger logger = Logger.getLogger("log_file");
		int idEstadoIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			int estadoInicial = 0;
			int estadoFinal = 0;
			int impresion = 0;
			if(estado.isImpresion())
			{
				impresion = 1;
			}
			if(estado.isEstadoFinal())
			{
				estadoFinal = 1;
			}
			if(estado.isEstadoInicial())
			{
				estadoInicial= 1;
			}
			String insert = "insert into estado (descripcion, descripcion_corta, estado_inicial, estado_final, colorr, colorg, colorb, idtipopedido, impresion, imagen) values('" + estado.getDescripcion() + "' , '" + estado.getDescripcionCorta() + "', "+ estadoInicial + " ," + estadoFinal + " , " + estado.getColorr() + " , " + estado.getColorg() + " , " + estado.getColorb() + ", " + estado.getIdTipoPedido() +", " + impresion + " , " + estado.getImagen() +")";
			System.out.println(insert);
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idEstadoIns=rs.getInt(1);
				logger.info("id estado insertada en bd " + idEstadoIns);
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
		return(idEstadoIns);
	}
	
	
	public static boolean eliminarEstado(int idestado)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from estado where idestado = " + idestado; 
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
	
	
	public static boolean editarEstado(Estado estado)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			int estadoInicial = 0;
			int estadoFinal = 0;
			int impresion = 0;
			if(estado.isImpresion())
			{
				impresion = 1;
			}
			if(estado.isEstadoFinal())
			{
				estadoFinal = 1;
			}
			if(estado.isEstadoInicial())
			{
				estadoInicial= 1;
			}
			Statement stm = con1.createStatement();
			String update = "update estado set descripcion = '" + estado.getDescripcion() + "' , descripcion_corta = '" + estado.getDescripcionCorta() + "' , estado_inicial =" + estadoInicial + " , estado_final = " + estadoFinal + ", colorr =  " + estado.getColorr() + " , colorg =" + estado.getColorg() + " , colorb = " + estado.getColorb() + " , impresion =" + impresion + " , imagen = ? where idEstado = " + estado.getIdestado();  
			PreparedStatement actualiz = null;
			actualiz = con1.prepareStatement(update);
			actualiz.setBytes(1, estado.getImagen());
			logger.info(update);
			actualiz.executeUpdate();
			
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
	
	public static boolean tieneEstadoInicial( int idTipoPedido, int idEstado)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from estado a where estado_inicial = 1 and a.idtipopedido = " + idTipoPedido + " and a.idestado != " + idEstado;
			logger.info(consulta);
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
	
	
	public static boolean tieneEstadoFinal( int idTipoPedido, int idEstado)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from estado a where estado_final = 1 and a.idtipopedido = " + idTipoPedido + " and a.idestado != " + idEstado;
			logger.info(consulta);
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
	
	public static int obtenerEstadoFinal( int idTipoPedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		int estadoFinal = 0;
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select idestado from estado  where estado_final = 1 and idtipopedido = " + idTipoPedido;
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
						
			while(rs.next()){
				estadoFinal = rs.getInt("idestado");
				break;
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
		return(estadoFinal);
		
	}
	
	public static int obtenerEstadoInicial( int idTipoPedido)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		int estadoInicial = 0;
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select idestado from estado  where estado_inicial = 1 and idtipopedido = " + idTipoPedido;
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
						
			while(rs.next()){
				estadoInicial = rs.getInt("idestado");
				break;
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
		return(estadoInicial);
		
	}
	
	/**
	 * Método que se encarga dado un tipo de pedido y un estado determinar si dicho pedido es o no inicial
	 * @param idTipoPedido el tipo de pedido del cual se desea confirmar la información
	 * @param idEstado idEstado del cual se desea confirmar la información de si es un estado inicial
	 * @return Se retorna un valor booleano que indica si la combinación de estado y tipo de pedido corresponde a un estado inicial
	 */
	public static boolean esEstadoInicial(int idTipoPedido, int idEstado)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from estado where idtipopedido =" + idTipoPedido + " and idestado = " + idEstado + " and estado_inicial = 1";
			logger.info(consulta);
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
	
	/**
	 * Método que se encarga dado un tipo de pedido y un estado determinar si dicho pedido es o no inicial
	 * @param idTipoPedido el tipo de pedido del cual se desea confirmar la información
	 * @param idEstado idEstado del cual se desea confirmar la información de si es un estado inicial
	 * @return Se retorna un valor booleano que indica si la combinación de estado y tipo de pedido corresponde a un estado inicial
	 */
	public static boolean esEstadoFinal(int idTipoPedido, int idEstado)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from estado where idtipopedido =" + idTipoPedido + " and idestado = " + idEstado + " and estado_final = 1";
			logger.info(consulta);
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
}
