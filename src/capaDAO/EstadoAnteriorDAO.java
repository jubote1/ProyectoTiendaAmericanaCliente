package capaDAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import capaModelo.Usuario;
import capaConexion.ConexionBaseDatos;
import capaModelo.Estado;
import capaModelo.EstadoAnterior;
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
public class EstadoAnteriorDAO {
	
/**
 * Método que se encarga de retonar todos los productos parametrizado en el sistema.
 * @return Retorna un arrayList con tipos de datos genéricos.
 */
	public static ArrayList<EstadoAnterior> obtenerEstadosAnteriores(int idEstado)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<EstadoAnterior> estadosAnteriores = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idestado, a.idestado_anterior, b.descripcion descripcion from estado_anterior a , estado b where a.idestado =" + idEstado + " and a.idestado_anterior = b.idestado";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idEstadoAnterior;
			String descripcion;
			while(rs.next()){
				idEstadoAnterior = rs.getInt("idestado_anterior");
				descripcion = rs.getString("descripcion");
				EstadoAnterior estAnt= new EstadoAnterior(idEstado, idEstadoAnterior, descripcion);
				estadosAnteriores.add(estAnt);
				
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
		return(estadosAnteriores);
		
	}
	
	
		
	
	public static boolean insertarEstadoAnterior(EstadoAnterior estadoAnt)
	{
		Logger logger = Logger.getLogger("log_file");
		int idEstadoIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into estado_anterior (idestado, idestado_anterior) values(" + estadoAnt.getIdEstado() + " , " + estadoAnt.getIdEstadoAnterior() + ")";
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
	
	
	public static boolean eliminarEstadoAnterior(EstadoAnterior estadoAnt)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from estado_anterior where idestado = " + estadoAnt.getIdEstado() + " and idestado_anterior = " + estadoAnt.getIdEstadoAnterior(); 
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
