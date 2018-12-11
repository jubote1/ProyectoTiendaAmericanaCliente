package capaDAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import capaModelo.Usuario;
import capaModelo.ConfiguracionMesa;
import capaConexion.ConexionBaseDatos;
import capaModelo.ConfiguracionMenu;
import capaModelo.EleccionForzada;
import capaModelo.Impuesto;
import capaModelo.AgrupadorMenu;
import capaModelo.Tienda;
import org.apache.log4j.Logger;
import com.mysql.jdbc.ResultSetMetaData;
/**
 * Clase que se encarga de implementar todo lo relacionado con la base de datos de la entidad configuracion_mesa
 * @author JuanDavid
 *
 */
public class ConfiguracionMesaDAO {
	
	/**
	 * Método que se encarga de retornar en un arreglo las configuraciones de las mesas y en que punto se encuentra configurada
	 * @return Se retorna un arreglo bidimensional de objetos tipo ConfiguracionMesa.
	 */
	public static ConfiguracionMesa[][] obtenerConfMesa(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ConfiguracionMesa[][] confMenu = new ConfiguracionMesa[8][8];
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from configuracion_mesa";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			
			//Inicializamos en null las diversas posiciones de la respuesta
			for(int i = 0; i<8; i++)
			{
				for(int j = 0; j < 8; j++)
				{
					confMenu[i][j] = null;
				}
			}
			//Posteriormente realizamos el llenado del arreglo
			int idConfiguracion,fila,columna, mesa;
			while(rs.next()){
				//recuperamos los valores del fila y columna
				fila = rs.getInt("fila");
				columna = rs.getInt("columna");
				//validamos los valores de fila y columna
				if((fila >= 0)&&(columna >= 0))
				{
					mesa = rs.getInt("mesa");
					idConfiguracion = rs.getInt("idconfiguracion");
					ConfiguracionMesa confMesa = new ConfiguracionMesa(idConfiguracion, fila, columna, mesa);
					confMenu[fila][columna] = confMesa;
				}
								
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
		return(confMenu);
		
	}
	


	public static int insertarConfiguracionMesa(ConfiguracionMesa confMesa, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		int idConfMesaIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into configuracion_mesa ( fila, columna, mesa) values (" + confMesa.getFila() + ", " + confMesa.getColumna() + " , " + confMesa.getMesa() +  ")"; 
			if(auditoria)
			{
				logger.info(insert);
			}
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idConfMesaIns=rs.getInt(1);
				logger.info("id configuracion Mesa en bd " + idConfMesaIns);
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
		return(idConfMesaIns);
	}
	
	public static boolean eliminarConfiguracionMesa(ConfiguracionMesa confMesa, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from configuracion_mesa where fila = " + confMesa.getFila() + " and columna = " + confMesa.getColumna() + " and mesa =" + confMesa.getMesa();
			if(auditoria)
			{
				logger.info(delete);
			}
			stm.executeUpdate(delete);
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
