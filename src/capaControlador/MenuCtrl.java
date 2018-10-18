package capaControlador;

import capaDAO.ConfiguracionMenuDAO;
import capaDAO.ConfiguracionMesaDAO;
import capaModelo.ConfiguracionMenu;
import capaModelo.ConfiguracionMesa;

public class MenuCtrl {
	private boolean auditoria;
	public MenuCtrl()
	{
		this.auditoria = auditoria;
	}
	public  int insertarConfiguracionMenu(ConfiguracionMenu confMenu)
	{
		int idConfMenuIns = ConfiguracionMenuDAO.insertarConfiguracionMenu(confMenu, auditoria);
		return(idConfMenuIns);
		
	}
	
	public  boolean eliminarConfiguracionMenu(ConfiguracionMenu confMenu)
	{
		boolean respuesta = ConfiguracionMenuDAO.eliminarConfiguracionMenu(confMenu, auditoria);
		return(respuesta);
		
	}
	
	public  ConfiguracionMenu[][] obtenerConfMenu(int multimenu)
	{
		ConfiguracionMenu[][] confMenu = ConfiguracionMenuDAO.obtenerConfMenu(multimenu, auditoria);
		return(confMenu);
	}
	
	/**
	 * M�todo que encarga de retornar la configuraci�n de los puntos y mesas configuradas en el sistema
	 * y se encarga de retornarlo en un arreglo bidimensional, para este caso es la capa de l�gica de negocio que para nuestro fin no hay
	 * @return Se retorna un arreglo bidimensional con la configuraci�n de puntos y mesas.
	 */
	public  ConfiguracionMesa[][] obtenerConfMesa()
	{
		ConfiguracionMesa[][] confMesa = ConfiguracionMesaDAO.obtenerConfMesa(auditoria);
		return(confMesa);
	}
	
	/**
	 * M�todo que en encarga en capa controladora de la inserci�n de una nueva configuraci�n Mesa
	 * @param confMesa, se recibe un objeto de tipo configuracionMesa para con base en este realizar la inserci�n.
	 * @return Se retorna un entero con el id retornado en lainserci�n de la tabla.
	 */
	public int insertarConfiguracionMesa(ConfiguracionMesa confMesa)
	{
		int id =  ConfiguracionMesaDAO.insertarConfiguracionMesa(confMesa, auditoria);
		return(id);
	}
/**
 * M�todo Metodo que se encarga en capa controladora de la eliminaci�n de una configuracion Mesa
 * @param confMesa se recibe la configuraci�n de mesa que desea eliminar
 * @return Se retorna un valor booleano con el resultado del proceso.
 */
	public boolean eliminarConfiguracionMesa(ConfiguracionMesa confMesa)
	{
		boolean resultado = ConfiguracionMesaDAO.eliminarConfiguracionMesa(confMesa, auditoria);
		return(resultado);
	}
}
