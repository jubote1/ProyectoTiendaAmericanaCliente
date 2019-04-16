package capaDAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import capaModelo.Usuario;
import capaConexion.ConexionBaseDatos;
import capaModelo.Impuesto;
import capaModelo.AgrupadorMenu;
import capaModelo.ImprimirAdm;
import capaModelo.Tienda;
import org.apache.log4j.Logger;
import com.mysql.jdbc.ResultSetMetaData;
/**
 * Clase que se encarga de implementar todo lo relacionado con la base de datos de la entidad impuestos
 * @author JuanDavid
 *
 */
public class ImprimirAdmDAO {
	

	public static ArrayList<ImprimirAdm> pendientesImpresion(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<ImprimirAdm> impresiones = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from imprimir_adm order by idimprimir asc";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idImpresion;
			String imprimir;
			while(rs.next()){
				idImpresion = rs.getInt("idimpresion");
				imprimir = rs.getString("imprimir");
				ImprimirAdm colaImp = new ImprimirAdm(idImpresion,imprimir);
				impresiones.add(colaImp);
				
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			System.out.println("ERROR CONSULTA IMPRESION " + e.toString());
			logger.info(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(impresiones);
		
	}
	

	public static boolean borrarImpresion(int idImpresion, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;	
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from impresion_adm where idimpresion " + idImpresion;
			if(auditoria)
			{
				logger.info(delete);
			}
			stm.executeUpdate(delete);
			respuesta = true;
			stm.close();
			con1.close();
		}catch (Exception e){
			System.out.println("ERROR BORRAR IMPRESIÓN " + e.toString());
			logger.info(e.toString());
			try
			{
				respuesta = false;
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(respuesta);
		
	}
	

	public static int insertarImpresion(String impresion, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		int idImpresionIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into imprimir_adm (imprimir) values ('" + impresion + "')"; 
			if(auditoria)
			{
				logger.info(insert);
			}
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idImpresionIns=rs.getInt(1);
				if(auditoria)
				{
					logger.info("id impresion insertada en bd " + idImpresionIns);
				}
				
	        }
			stm.close();
			con1.close();
		}
		catch (Exception e){
			System.out.println("ERROR DE LA INSERCIÓN " + e.toString());
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
			return(0);
		}
		return(idImpresionIns);
	}

}
