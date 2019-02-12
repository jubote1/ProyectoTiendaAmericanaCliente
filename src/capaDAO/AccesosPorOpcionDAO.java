package capaDAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import capaModelo.Usuario;
import capaConexion.ConexionBaseDatos;
import capaModelo.AccesosPorMenu;
import capaModelo.AccesosPorOpcion;
import capaModelo.AgrupadorMenu;
import capaModelo.Tienda;
import org.apache.log4j.Logger;
import com.mysql.jdbc.ResultSetMetaData;
/**
 * Clase que se encarga de implementar todo lo relacionado con la base de datos de la entidad tienda.
 * @author JuanDavid
 *
 */
public class AccesosPorOpcionDAO {
	
/**
 * Método que se encarga de retornar todas las entidades Tiendas definidas en la base de datos
 * @return Se retorna un ArrayList con todas las entidades Tiendas definidas en la base de datos.
 */
	public static ArrayList obtenerAccesosPorOpcion(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList accesosPorOpcion = new ArrayList();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idaccesoopcion, b.descripcion,  from accesos_por_menu a, tipo_empleado b, agrupador_menu c where a.idtipoempleado = b.idtipoempleado and a.idagrupadormenu = c.idmenuagrupador";
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
				accesosPorOpcion.add(fila);
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
		return(accesosPorOpcion);
		
	}
	
	/**
	 * Método que retorna un ArrayList con todos los Agrupadores Menús definidos en el sistema en un arraylist
	 * @param auditoria
	 * @return
	 */
	public static ArrayList<AccesosPorOpcion> obtenerAccesosPorOpcionObj(boolean auditoria, int idTipUsu)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<AccesosPorOpcion> accesosPorOpcion = new ArrayList();
		AccesosPorOpcion accPorOpcion = new AccesosPorOpcion(0,"",0);
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from accesos_por_opcion where idtipousuario =" + idTipUsu;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idAccesoOpcion,idTipoEmpleado;
			String codigoPantalla;
			while(rs.next()){
				
				idAccesoOpcion = rs.getInt("idaccesoopcion");
				codigoPantalla = rs.getString("codpantalla");
				idTipoEmpleado = rs.getInt("idtipousuario");
				accPorOpcion = new AccesosPorOpcion(idAccesoOpcion, codigoPantalla, idTipoEmpleado);
				accesosPorOpcion.add(accPorOpcion);
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
		return(accesosPorOpcion);
		
	}
	
	public static ArrayList<AccesosPorMenu> obtenerAccesosPorMenuUsuario( String nombreUsuario ,boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<AccesosPorMenu> accesosPorMenu = new ArrayList();
		AccesosPorMenu accPorMenu = new AccesosPorMenu(0,0,0);
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.* from accesos_por_menu a, tipo_empleado b, usuario c where c.idtipoempleado = b.idtipoempleado and a.idtipoempleado = b.idtipoempleado and c.nombre = '" + nombreUsuario + "'" ;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
			int numeroColumnas = rsMd.getColumnCount();
			int idAccesoMenu,idUsuario, idAgrupadorMenu;
			while(rs.next()){
				
				idAccesoMenu = rs.getInt("idaccesomenu");
				idUsuario= rs.getInt("idtipoempleado");
				idAgrupadorMenu = rs.getInt("idagrupadormenu");
				accPorMenu = new AccesosPorMenu(idAccesoMenu, idUsuario, idAgrupadorMenu);
				accesosPorMenu.add(accPorMenu);
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
		return(accesosPorMenu);
		
	}
	
	public static int insertarAccesosPorMenu(AccesosPorMenu acceso, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		int idAccesoMenuIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into accesos_por_menu (idtipoempleado,idagrupadormenu) values (" + acceso.getidTipoEmpleado() + ", " + acceso.getIdAgrupadorMenu() + ")"; 
			if(auditoria)
			{
				logger.info(insert);
			}
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idAccesoMenuIns=rs.getInt(1);
				if(auditoria)
				{
					logger.info("id menu agrupador insertada en bd " + idAccesoMenuIns);
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
		return(idAccesoMenuIns);
	}
	
	public static boolean eliminarAccesosPorMenu(int idAccesoMenu, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from accesos_por_menu where idaccesomenu = " +  idAccesoMenu; 
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
	
}
