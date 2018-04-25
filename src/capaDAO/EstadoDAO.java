package capaDAO;

import java.sql.Connection;

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
			
			while(rs.next()){
				idestado = rs.getInt("idestado");
				descripcion = rs.getString("descripcion");
				descripcionCorta = rs.getString("descripcion_corta");
				idTipoPedido = rs.getInt("idtipopedido");
				Estado est = new Estado(idestado, descripcion, descripcionCorta, idTipoPedido);
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
						
			while(rs.next()){
				idTipoPedido = rs.getInt("idtipopedido");
				descripcion = rs.getString("descripcion");
				descripcionCorta = rs.getString("descripcion_corta");
				estado = new Estado(idEstado, descripcion, descripcionCorta,idTipoPedido);
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
			String insert = "insert into estado (descripcion, descripcion_corta) values('" + estado.getDescripcion() + "' , '" + estado.getDescripcionCorta() + "')";
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
			Statement stm = con1.createStatement();
			String update = "update estado set descripcion = '" + estado.getDescripcion() + "' , descripcion_corta = '" + estado.getDescripcionCorta() + "' " + "where idEstado = " + estado.getIdestado();  
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
}
