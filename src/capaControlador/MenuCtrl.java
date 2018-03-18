package capaControlador;

import capaDAO.ConfiguracionMenuDAO;
import capaModelo.ConfiguracionMenu;

public class MenuCtrl {
	
	public static int insertarConfiguracionMenu(ConfiguracionMenu confMenu)
	{
		int idConfMenuIns = ConfiguracionMenuDAO.insertarConfiguracionMenu(confMenu);
		return(idConfMenuIns);
		
	}
	
	public static ConfiguracionMenu[][] obtenerConfMenu(int multimenu)
	{
		ConfiguracionMenu[][] confMenu = ConfiguracionMenuDAO.obtenerConfMenu(multimenu);
		return(confMenu);
	}

}
