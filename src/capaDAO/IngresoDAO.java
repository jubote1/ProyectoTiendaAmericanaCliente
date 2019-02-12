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
import capaModelo.Tienda;
import capaModelo.TipoEmpleado;

import org.apache.log4j.Logger;
import com.mysql.jdbc.ResultSetMetaData;
/**
 * Clase que se encarga de implementar todo lo relacionado con la base de datos de la entidad impuestos por producto
 * @author JuanDavid
 *
 */
public class IngresoDAO {
	
/**
 * Método que se encarga de retornar en un ArrayList los tipos de empleados definidos en el sistema.
 * @return
 */
	public static ArrayList obtenerIngresos(String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList ingresos = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from ingreso  where fecha = '" + fecha + "'";
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
				ingresos.add(fila);
				
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
		return(ingresos);
		
	}
	
	
	public static ArrayList<Ingreso> obtenerIngresosObj(String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<Ingreso> ingresos = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from ingreso  where fecha = '" + fecha + "'";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idIngreso;
			double valorIngreso;
			String descripcion;
			Ingreso ingTemp = new Ingreso(0,0,"", "");
			while(rs.next()){
				idIngreso = rs.getInt("idingreso");
				valorIngreso = rs.getDouble("valoringreso");
				descripcion = rs.getString("descripcion");
				ingTemp = new Ingreso(idIngreso, valorIngreso, fecha, descripcion);
				ingresos.add(ingTemp);
				
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
		return(ingresos);
		
	}
	
	
		

	public static int insertarIngreso(Ingreso ingIns, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		int idIngresoIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into ingreso (valoringreso, fecha, descripcion) values (" + ingIns.getValorIngreso() + " , '" + ingIns.getFecha()+ "' , '" + ingIns.getDescripcion() + "')"; 
			if(auditoria)
			{
				logger.info(insert);
			}
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idIngresoIns=rs.getInt(1);
				if(auditoria)
				{
					logger.info("id ingreso insertada en bd " + idIngresoIns);
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
		return(idIngresoIns);
	}
	

	public static boolean eliminarIngreso(int idIngreso, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from ingreso where idingreso = " + idIngreso; 
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
	
	
	public static boolean editarIngreso(Ingreso ing, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = false;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update ingreso set descripcion = '" + ing.getDescripcion()  +"' , valoringreso = "+ ing.getValorIngreso() + " where idingreso = " + ing.getIdIngreso();
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
	
	public static double TotalizarIngreso(String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = false;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		double totalIngresos = 0;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select sum(valoringreso) from ingreso where fecha = '" + fecha + "'";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next())
			{
				try{
					totalIngresos = rs.getDouble(1);
				}catch(Exception e)
				{
					totalIngresos = 0;
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
		return(totalIngresos);
	}
		
}
