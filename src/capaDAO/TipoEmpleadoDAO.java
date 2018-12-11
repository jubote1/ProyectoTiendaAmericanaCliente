package capaDAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import capaModelo.Usuario;
import capaConexion.ConexionBaseDatos;
import capaModelo.Impuesto;
import capaModelo.ImpuestoProducto;
import capaModelo.AgrupadorMenu;
import capaModelo.Tienda;
import capaModelo.TipoEmpleado;

import org.apache.log4j.Logger;
import com.mysql.jdbc.ResultSetMetaData;
/**
 * Clase que se encarga de implementar todo lo relacionado con la base de datos de la entidad impuestos por producto
 * @author JuanDavid
 *
 */
public class TipoEmpleadoDAO {
	
/**
 * Método que se encarga de retornar en un ArrayList los tipos de empleados definidos en el sistema.
 * @return
 */
	public static ArrayList obtenerTipoEmpleado(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList tiposEmpleado = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from tipo_empleado ";
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
				tiposEmpleado.add(fila);
				
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
		return(tiposEmpleado);
		
	}
	
	
	public static ArrayList<TipoEmpleado> obtenerTipoEmpleadoObj(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<TipoEmpleado> tiposEmpleado = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from tipo_empleado ";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int cajero = 0, domiciliario = 0, administrador = 0, hornero = 0, cocinero = 0;	
			boolean esCajero = false, esDomiciliario = false, esAdministrador = false, esHornero = false, esCocinero = false;	
			int idTipoEmpleado = 0;
			String descripcion = "";
			TipoEmpleado tipEmp;
			while(rs.next()){
				idTipoEmpleado = rs.getInt("idtipoempleado");
				descripcion = rs.getString("descripcion");
				cajero = rs.getInt("es_cajero");
				domiciliario = rs.getInt("es_domiciliario");
				administrador = rs.getInt("es_administrador");
				hornero = rs.getInt("es_hornero");
				cocinero = rs.getInt("es_cocinero");
				if(cajero == 1)
				{
					esCajero = true;
				}
				if(domiciliario == 1)
				{
					esDomiciliario = true;
				}
				if(administrador == 1)
				{
					esAdministrador = true;
				}
				if(hornero == 1)
				{
					esHornero = true;
				}
				if(cocinero == 1)
				{
					esCocinero = true;
				}
				tipEmp = new TipoEmpleado(idTipoEmpleado, descripcion, esCajero, esDomiciliario, esAdministrador, esHornero, esCocinero);
				tiposEmpleado.add(tipEmp);
				
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
		return(tiposEmpleado);
		
	}
	
	
		
/**
 * Método que se encarga de la inserción de los nuevos tipos de empleados definidos en el sistema
 * @param tipoEmpleado Se recibe como parámetro un objeto de la clase TipoEmpleado con los datos del tipo empleado
 * a  insertar.
 * @return Se retorna un valor con idtipoempleado asignado por el sistema.
 */
	public static int insertarTipoEmpleado(TipoEmpleado tipoEmpleado, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		int idTipoEmpleadoIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			int cajero = 0, domiciliario = 0, administrador = 0, hornero = 0, cocinero = 0;
			if(tipoEmpleado.isCajero())
			{
				cajero = 1;
			}
			if(tipoEmpleado.isDomiciliario())
			{
				domiciliario = 1;
			}
			if(tipoEmpleado.isAdministrador())
			{
				administrador = 1;
			}
			if(tipoEmpleado.isHornero())
			{
				hornero = 1;
			}
			if(tipoEmpleado.isCocinero())
			{
				cocinero = 1;
			}
			Statement stm = con1.createStatement();
			String insert = "insert into tipo_empleado (descripcion, es_cajero, es_domiciliario, es_administrador, es_hornero, es_cocinero) values ('" + tipoEmpleado.getDescriTipoEmpleado() + "' , " + cajero + " , " + domiciliario + " ," + administrador + ", " + hornero + " , " + cocinero + ")"; 
			if(auditoria)
			{
				logger.info(insert);
			}
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idTipoEmpleadoIns=rs.getInt(1);
				if(auditoria)
				{
					logger.info("id impuesto insertada en bd " + idTipoEmpleadoIns);
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
		return(idTipoEmpleadoIns);
	}
	
	/**
	 * Método que se encarga de eliminar un determinado en impuesto, teniendo en cuenta el idImpuestoProducto pasado como parámetro
	 * @param idImpuestoProducto Se recibe como parámetro el idimpuesto que se desea eliminar, teniendo en cuenta que es la 
	 * clave primaría de la tabla.
	 * @return Se retorna un valor booleano que indica si el resultado del proceso fue satisfactorio o no.
	 */
	public static boolean eliminarTipoEmpleado(int idTipoEmpleado, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from tipo_empleado where idtipoempleado = " + idTipoEmpleado; 
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
	
	
	public static boolean editarTipoEmpleado(TipoEmpleado tipoEmpleadoEdi, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = false;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			int cajero = 0, domiciliario = 0, administrador = 0, hornero = 0, cocinero = 0;
			if(tipoEmpleadoEdi.isCajero())
			{
				cajero = 1;
			}
			if(tipoEmpleadoEdi.isDomiciliario())
			{
				domiciliario = 1;
			}
			if(tipoEmpleadoEdi.isAdministrador())
			{
				administrador = 1;
			}
			if(tipoEmpleadoEdi.isHornero())
			{
				hornero = 1;
			}
			if(tipoEmpleadoEdi.isCocinero())
			{
				cocinero = 1;
			}
			Statement stm = con1.createStatement();
			String update = "update tipo_empleado set descripcion = '" + tipoEmpleadoEdi.getDescriTipoEmpleado()  +"' , es_cajero = "+ cajero +" , es_domiciliario ="+ domiciliario+" , es_administrador= "+ administrador +", es_hornero = "+ hornero+", es_cocinero=" + cocinero + " where idtipoempleado = " + tipoEmpleadoEdi.getIdTipoEmpleado();
			if(auditoria)
			{
				logger.info(update);
			}
			stm.executeUpdate(update);
			respuesta = true;
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			respuesta = false;
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
	 * Método que se encarga de retornar un valor booleano que indica si un determinado tipo de empleado es de tipo domiciliario
	 * si o no.
	 * @param idTipoEmpleado Se recibe como parámetro el tipo de d empleado del cual se busca saber si es domiciliario o no
	 * @param auditoria Se recibe parámetro que indica si deben o no haber trazas para el proceso.
	 * @return
	 */
	public static boolean esDomicilario(int idTipoEmpleado, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from tipo_empleado where es_domiciliario = 1 and idtipoempleado = " + idTipoEmpleado;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				return(true);
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.info(e.toString());
			try
			{
				con1.close();
				return(false);
			}catch(Exception e1)
			{
			}
		}
		return(false);
		
	}
	
}
