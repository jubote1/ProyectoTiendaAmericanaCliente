package capaDAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import capaModelo.Usuario;
import capaConexion.ConexionBaseDatos;
import capaModelo.Impuesto;
import capaModelo.ImpuestoProducto;
import capaModelo.Ingreso;
import capaModelo.AgrupadorMenu;
import capaModelo.Egreso;
import capaModelo.Tienda;
import capaModelo.TipoEmpleado;

import org.apache.log4j.Logger;
import com.mysql.jdbc.ResultSetMetaData;
/**
 * Clase que se encarga de implementar todo lo relacionado con la base de datos de la entidad impuestos por producto
 * @author JuanDavid
 *
 */
public class EgresoDAO {
	
/**
 * Método que se encarga de retornar en un ArrayList los tipos de empleados definidos en el sistema.
 * @return
 */
	public static ArrayList obtenerEgresos(String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList egresos = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from egreso  where fecha = '" + fecha + "'";
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
				egresos.add(fila);
				
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
		return(egresos);
		
	}
	
	
	public static ArrayList<Egreso> obtenerEgresosObj(String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<Egreso> egresos = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from egreso  where fecha = '" + fecha + "'";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idEgreso;
			double valorEgreso;
			String descripcion;
			Egreso egrTemp = new Egreso(0,0,"", "");
			while(rs.next()){
				idEgreso = rs.getInt("idegreso");
				valorEgreso = rs.getDouble("valoregreso");
				descripcion = rs.getString("descripcion");
				egrTemp = new Egreso(idEgreso, valorEgreso, fecha, descripcion);
				egresos.add(egrTemp);
				
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
		return(egresos);
		
	}
	
	/**
	 * Método que se encarga desde la capa DAO de retornar todos los egresos para una semana determinada en el sistema.
	 * @param fechaInferior rango inferior para la consulta de egresos vales
	 * @param fechaSuperior rango superior para la consulta de egresos vales
	 * @param auditoria Parámetro que determina la generación o no de logs
	 * @return Un arrayList con objetos tipoEgreso
	 */
	public static ArrayList<Egreso> obtenerEgresosSemana(String fechaInferior, String fechaSuperior, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<Egreso> egresos = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from egreso  where fecha >= '" + fechaInferior + "' and fecha <= '" + fechaSuperior+"'";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idEgreso;
			double valorEgreso;
			String descripcion;
			String fecha;
			Egreso egrTemp = new Egreso(0,0,"", "");
			while(rs.next()){
				idEgreso = rs.getInt("idegreso");
				valorEgreso = rs.getDouble("valoregreso");
				descripcion = rs.getString("descripcion");
				fecha = rs.getString("fecha");
				egrTemp = new Egreso(idEgreso, valorEgreso, fecha, descripcion);
				egresos.add(egrTemp);
				
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
		return(egresos);
		
	}
		

	public static int insertarEgreso(Egreso egrIns, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		int idEgresoIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into egreso (valoregreso, fecha, descripcion) values (" + egrIns.getValorEgreso() + " , '" + egrIns.getFecha()+ "' , '" + egrIns.getDescripcion() + "')"; 
			if(auditoria)
			{
				logger.info(insert);
			}
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idEgresoIns=rs.getInt(1);
				if(auditoria)
				{
					logger.info("id egreso insertada en bd " + idEgresoIns);
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
		return(idEgresoIns);
	}
	

	public static boolean eliminarEgreso(int idEgreso, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from egreso where idegreso = " + idEgreso; 
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
	
	
	public static boolean editarEgreso(Egreso egr, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = false;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update egreso set descripcion = '" + egr.getDescripcion()  +"' , valoregreso = "+ egr.getValorEgreso() + " where idegreso = " + egr.getIdEgreso();
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
	
	
	public static double TotalizarEgreso(String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = false;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		double totalEgresos = 0;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select sum(valoregreso) from egreso where fecha = '" + fecha + "'";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next())
			{
				try{
					totalEgresos = rs.getDouble(1);
				}catch(Exception e)
				{
					totalEgresos = 0;
					System.out.println("SE TUVO ERROR TOTALIZANDO LOS EGRESOS " + e.toString());
				}
			}
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
		return(totalEgresos);
	}
	
		
}
