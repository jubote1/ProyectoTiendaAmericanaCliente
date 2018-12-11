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
import capaModelo.AgrupadorMenu;
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
 * M�todo que se encarga dado un multimenu de retornar los menus que se tienen para esta configuraci�n.
 * @param multimenu, valor entero que identifica el multimenu y con base en el cual se realiza el retorna los men�s
 * @return Se retornar� un arreglo de dos dimensiones con objetos de tipo ConfiguracionMenu, dado un multimenu quien se encarga de 
 * agruparlos.
 */
	public static ConfiguracionMenu[][] obtenerConfMenu(int multimenu, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ConfiguracionMenu[][] confMenu = new ConfiguracionMenu[6][6];
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from configuracion_menu  where multimenu = " + multimenu;
			if(auditoria)
			{
				logger.info(consulta);
			}
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
	 * M�todo que permite la inseci�n de una configuraci�n Men� determinada
	 * @param confMenu se recibo como parametro un objeto de tipo COnfiguracionMenu el cual tiene los valores para la inserci�n 
	 * en base de datos 
	 * @return Se retorna un entero con el idConfiguracionMenu creado en la base de datos en caso de que este proceso termine
	 * correctamente.
	 */
	public static int insertarConfiguracionMenu(ConfiguracionMenu confMenu, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		int idConfMenuIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into configuracion_menu (multimenu, menu, fila, columna, idproducto) values (" + confMenu.getMultimenu() + ", " + confMenu.getMenu() + " , " + confMenu.getFila() + " , " + confMenu.getColumna() + " , " + confMenu.getIdProducto() + ")"; 
			if(auditoria)
			{
				logger.info(insert);
			}
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
	
	public static boolean eliminarConfiguracionMenu(ConfiguracionMenu confMenu, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from configuracion_menu where multimenu = " + confMenu.getMultimenu() + " and menu = " + confMenu.getMenu();
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
	
	//Estos m�todos se analizar�n para saber si siguen o no.
	/**
	 * M�todo que se encarga de eliminar una determinada eleccion_Forzada, teniendo en cuenta el ideleccion_forzada pasado como par�metro
	 * @param idEleccionForzada Se recibe como par�metro el idEleccion_Forzada que se desea eliminar, teniendo en cuenta que es la 
	 * clave primar�a de la tabla.
	 * @return Se retorna un valor booleano que indica si el resultado del proceso fue satisfactorio o no.
	 */
	public static boolean eliminarEleccionForzada(int idEleccionForzada, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from eleccion_forzada where ideleccion_forzada = " + idEleccionForzada; 
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
	
	/**
	 * M�todo de la capa DAO que se encarga de editar una EleccionForzada ya existente.
	 * @param EleccionForzada Recibe como par�metro un objeto de la entidad EleccionForzada con base en el cual se realiza la modificaci�n
	 * @return Se retorna un valor booleano indicando si el proceso fue o no satisfactorio
	 */
	public static boolean EditarEleccionForzada(EleccionForzada eleccionForzada, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update eleccion_forzada set idproducto = " + eleccionForzada.getIdProducto() + " , idpregunta = " + eleccionForzada.getIdPregunta() + " , precio = '" + eleccionForzada.getPrecio() + "' , estado = " + eleccionForzada.getEstado()  + " where ideleccion_forzada = " + eleccionForzada.getIdEleccionForzada() ; 
			if(auditoria)
			{
				logger.info(update);
			}
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
	
	/**
	 * M�todo que se encarga de retornar un arreglo con valores booleanos qeu indican si un Multimenu tiene o no tiene elementos lo anterior
	 * con el objetivo de tomar una acci�n al momento de pintar los multimenus en la pantalla de toma de peddios
	 * @param auditoria Se recibe valor booleano que indica si  se debe o no guardar auditor�a de las sentencias de base de datos.
	 * @return Se retorna un arreglo booleano indicando si un multimen� tiene o no elementos.
	 */
	public static boolean[] retornarSihayMultimenu( boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean[] siHayMenus = new boolean[7];
	
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from configuracion_menu";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			//Inicializamos el arreglo con false
			for(int i = 1; i <= 6; i++)
			{
				siHayMenus[i] = false;
			}
			//Posteriormente realizamos el llenado del arreglo
			int multimenu;
			while(rs.next()){
				//recuperamos los valores del fila y columna
				multimenu = rs.getInt("multimenu");
				if(multimenu <=6)
				{
					siHayMenus[multimenu] = true;
				}
				//validamos los valores de fila y columna
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
		return(siHayMenus);
	}
	
	/**
	 * M�todo qeu se encarga de retornar los nombres de los multimen�s para ser desplegados en pantalla para la toma de pedidos.
	 * @param auditoria
	 * @return
	 */
	public static String[] retornarNombresMultimenu( boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		String[] nombresMenus = new String[7];
	
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from nombre_multimenu order by idmultimenu";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			//Posteriormente realizamos el llenado del arreglo
			String nombreMenu = "";
			int multimenu = 0;
			while(rs.next()){
				//recuperamos los valores del nombre Menu
				nombreMenu = rs.getString("nombre");
				multimenu = rs.getInt("idmultimenu");
			    nombresMenus[multimenu] = nombreMenu;
				//validamos los valores de fila y columna
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
		return(nombresMenus);
	}
}
