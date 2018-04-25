package capaDAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import capaModelo.Usuario;
import capaConexion.ConexionBaseDatos;
import capaModelo.Estado;
import capaModelo.EstadoAnterior;
import capaModelo.EstadoPosterior;
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
public class EstadoPosteriorDAO {
	
/**
 * Método que se encarga de retonar todos los productos parametrizado en el sistema.
 * @return Retorna un arrayList con tipos de datos genéricos.
 */
	public static ArrayList<EstadoPosterior> obtenerEstadosPos(int idEstado)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<EstadoPosterior> estadosPosteriores = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta =  "select a.idestado, a.idestado_posterior, b.descripcion_corta descripcion from estado_posterior a , estado b where a.idestado =" + idEstado + " and a.idestado_posterior = b.idestado";;
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idEstadoPosterior;
			String descripcion;
			while(rs.next()){
				idEstadoPosterior = rs.getInt("idestado_posterior");
				descripcion = rs.getString("descripcion");
				EstadoPosterior estAnt= new EstadoPosterior(idEstado, idEstadoPosterior, descripcion);
				estadosPosteriores.add(estAnt);
				
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
		return(estadosPosteriores);
		
	}
	
	
		
	
	
	
	public static boolean insertarEstado(EstadoPosterior estadoPos)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into estado_posterior (idestado, idestado_posterior) values(" + estadoPos.getIdEstado() + " , " + estadoPos.getIdEstadoPosterior() + ")";
			logger.info(insert);
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
	
	
	public static boolean eliminarEstadoPosterior(EstadoPosterior estadoPos)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from estado_posterior where idestado = " + estadoPos.getIdEstado() + " and idestado_posterior = " + estadoPos.getIdEstadoPosterior(); 
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
	
	
}
