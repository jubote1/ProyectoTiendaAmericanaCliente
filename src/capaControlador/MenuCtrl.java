package capaControlador;

import capaDAO.ConfiguracionMenuDAO;
import capaDAO.ConfiguracionMesaDAO;
import capaModelo.ConfiguracionMenu;
import capaModelo.ConfiguracionMesa;

public class MenuCtrl {
	
	public static int insertarConfiguracionMenu(ConfiguracionMenu confMenu)
	{
		int idConfMenuIns = ConfiguracionMenuDAO.insertarConfiguracionMenu(confMenu);
		return(idConfMenuIns);
		
	}
	
	public static boolean eliminarConfiguracionMenu(ConfiguracionMenu confMenu)
	{
		boolean respuesta = ConfiguracionMenuDAO.eliminarConfiguracionMenu(confMenu);
		return(respuesta);
		
	}
	
	public static ConfiguracionMenu[][] obtenerConfMenu(int multimenu)
	{
		ConfiguracionMenu[][] confMenu = ConfiguracionMenuDAO.obtenerConfMenu(multimenu);
		return(confMenu);
	}
	
	/**
	 * Método que encarga de retornar la configuración de los puntos y mesas configuradas en el sistema
	 * y se encarga de retornarlo en un arreglo bidimensional, para este caso es la capa de lógica de negocio que para nuestro fin no hay
	 * @return Se retorna un arreglo bidimensional con la configuración de puntos y mesas.
	 */
	public  ConfiguracionMesa[][] obtenerConfMesa()
	{
		ConfiguracionMesa[][] confMesa = ConfiguracionMesaDAO.obtenerConfMesa();
		return(confMesa);
	}
	
	/**
	 * Método que en encarga en capa controladora de la inserción de una nueva configuración Mesa
	 * @param confMesa, se recibe un objeto de tipo configuracionMesa para con base en este realizar la inserción.
	 * @return Se retorna un entero con el id retornado en lainserción de la tabla.
	 */
	public int insertarConfiguracionMesa(ConfiguracionMesa confMesa)
	{
		int id =  ConfiguracionMesaDAO.insertarConfiguracionMesa(confMesa);
		return(id);
	}
/**
 * Método Metodo que se encarga en capa controladora de la eliminación de una configuracion Mesa
 * @param confMesa se recibe la configuración de mesa que desea eliminar
 * @return Se retorna un valor booleano con el resultado del proceso.
 */
	public boolean eliminarConfiguracionMesa(ConfiguracionMesa confMesa)
	{
		boolean resultado = ConfiguracionMesaDAO.eliminarConfiguracionMesa(confMesa);
		return(resultado);
	}
}
