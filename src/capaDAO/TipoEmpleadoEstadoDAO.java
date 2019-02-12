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
import capaModelo.AgrupadorMenu;
import capaModelo.Producto;
import capaModelo.Tienda;
import org.apache.log4j.Logger;
import com.mysql.jdbc.ResultSetMetaData;
/**
 * Clase que se encarga de implementar todo lo relacionado con la base de datos de la entidad producto
 * @author JuanDavid
 *
 */
public class TipoEmpleadoEstadoDAO {
	
	public static ArrayList<Estado> obtenerEstadosTipoEmpleado(int idTipoEmpleado, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<Estado> estados = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.* from estado a, tipo_empleado_estados b where a.idestado = b.idestado and b.idtipoempleado = " + idTipoEmpleado;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idestado, idTipoPedido;
			String descripcion, descripcionCorta;
			boolean estInicial = false;
			boolean estFinal = false;
			boolean impresion = false;
			boolean rutaDomicilio = false;
			boolean entregaDomicilio = false;
			int intEstInicial, intEstFinal, intImpresion, intRutaDomicilio, intEntregaDomicilio;
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
				intEntregaDomicilio = rs.getInt("ruta_domicilio");
				intRutaDomicilio = rs.getInt("entrega_domicilio");
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
				if(intEntregaDomicilio == 1)
				{
					entregaDomicilio = true;
				}
				if(intRutaDomicilio == 1)
				{
					rutaDomicilio = true;
				}
				Estado est = new Estado(idestado, descripcion, descripcionCorta, idTipoPedido, "", estInicial, estFinal, colorr, colorg, colorb, impresion,rutaDomicilio, entregaDomicilio);
				estados.add(est);
				
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
		return(estados);
		
	}
	
		
	
	public static boolean insertarTipoEmpleadoEstado(int idTipoEmpleado, int idEstado, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		int idEstadoIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into tipo_empleado_estados (idtipoempleado, idestado) values(" + idTipoEmpleado + " , " + idEstado + ")";
			if(auditoria)
			{
				logger.info(insert);
			}
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
	
	
	public static boolean eliminarTipoEmpleadoEstado(int idTipoEmpleado, int idEstado, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from tipo_empleado_estados where idestado = " + idEstado + " and idtipoempleado = " + idTipoEmpleado; 
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
