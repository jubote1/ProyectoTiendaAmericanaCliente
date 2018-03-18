package capaDAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import capaModelo.Usuario;
import capaConexion.ConexionBaseDatos;
import capaModelo.ConfiguracionMenu;
import capaModelo.EleccionForzada;
import capaModelo.Impuesto;
import capaModelo.MenuAgrupador;
import capaModelo.Tienda;
import org.apache.log4j.Logger;
import com.mysql.jdbc.ResultSetMetaData;
/**
 * Clase que se encarga de implementar todo lo relacionado con la base de datos de la entidad eleccion_forzada
 * @author JuanDavid
 *
 */
public class ConfiguracionMenuDAO {
	
/**
 * Método que se encarga dado un multimenu de retornar los menus que se tienen para esta configuración.
 * @param multimenu, valor entero que identifica el multimenu y con base en el cual se realiza el retorna los menús
 * @return Se retornará un arreglo de dos dimensiones con objetos de tipo ConfiguracionMenu, dado un multimenu quien se encarga de 
 * agruparlos.
 */
	public static ConfiguracionMenu[][] obtenerConfMenu(int multimenu)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ConfiguracionMenu[][] confMenu = new ConfiguracionMenu[6][6];
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from configuracion_menu  where multimenu = " + multimenu;
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			
			//Inicializamos en null las diversas posiciones de la respuesta
			for(int i = 0; i<6; i++)
			{
				for(int j = 0; j < 6; j++)
				{
					confMenu[i][j] = null;
				}
			}
			//Posteriormente realizamos el llenado del arreglo
			int fila, columna, menu, idConMenu, idproducto;
			while(rs.next()){
				//recuperamos los valores del fila y columna
				fila = rs.getInt("fila");
				columna = rs.getInt("columna");
				//validamos los valores de fila y columna
				if((fila >= 0)&&(columna >= 0))
				{
					menu = rs.getInt("menu");
					idConMenu = rs.getInt("idconfiguracionmenu");
					idproducto = rs.getInt("idproducto");
					ConfiguracionMenu confMenuTemp = new ConfiguracionMenu(idConMenu,multimenu,menu,fila, columna, idproducto);
					confMenu[fila][columna] = confMenuTemp;
				}
								
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
		return(confMenu);
		
	}
	

	/**
	 * Método que permite la inseción de una configuración Menú determinada
	 * @param confMenu se recibo como parametro un objeto de tipo COnfiguracionMenu el cual tiene los valores para la inserción 
	 * en base de datos 
	 * @return Se retorna un entero con el idConfiguracionMenu creado en la base de datos en caso de que este proceso termine
	 * correctamente.
	 */
	public static int insertarConfiguracionMenu(ConfiguracionMenu confMenu)
	{
		Logger logger = Logger.getLogger("log_file");
		int idConfMenuIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into configuracion_menu (multimenu, menu, fila, columna, idproducto) values (" + confMenu.getMultimenu() + ", " + confMenu.getMenu() + " , " + confMenu.getFila() + " , " + confMenu.getColumna() + " , " + confMenu.getIdProducto() + ")"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idConfMenuIns=rs.getInt(1);
				logger.info("id configuracion Menu en bd " + idConfMenuIns);
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
		return(idConfMenuIns);
	}
	
	
	//Estos métodos se analizarán para saber si siguen o no.
	/**
	 * Método que se encarga de eliminar una determinada eleccion_Forzada, teniendo en cuenta el ideleccion_forzada pasado como parámetro
	 * @param idEleccionForzada Se recibe como parámetro el idEleccion_Forzada que se desea eliminar, teniendo en cuenta que es la 
	 * clave primaría de la tabla.
	 * @return Se retorna un valor booleano que indica si el resultado del proceso fue satisfactorio o no.
	 */
	public static boolean eliminarEleccionForzada(int idEleccionForzada)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from eleccion_forzada where ideleccion_forzada = " + idEleccionForzada; 
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
	
	/**
	 * Método de la capa DAO que se encarga de editar una EleccionForzada ya existente.
	 * @param EleccionForzada Recibe como parámetro un objeto de la entidad EleccionForzada con base en el cual se realiza la modificación
	 * @return Se retorna un valor booleano indicando si el proceso fue o no satisfactorio
	 */
	public static boolean EditarEleccionForzada(EleccionForzada eleccionForzada)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update eleccion_forzada set idproducto = " + eleccionForzada.getIdProducto() + " , idpregunta = " + eleccionForzada.getIdPregunta() + " , precio = '" + eleccionForzada.getPrecio() + "' , estado = " + eleccionForzada.getEstado()  + " where ideleccion_forzada = " + eleccionForzada.getIdEleccionForzada() ; 
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
