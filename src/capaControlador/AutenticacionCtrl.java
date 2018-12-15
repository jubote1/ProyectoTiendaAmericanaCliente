package capaControlador;

import capaModelo.AccesosPorBoton;
import capaModelo.AccesosPorMenu;
import capaModelo.AccesosPorOpcion;
import capaModelo.AgrupadorMenu;
import capaModelo.Usuario;
import interfazGrafica.Sesion;

import java.util.ArrayList;

import capaDAO.AccesosPorBotonDAO;
import capaDAO.AccesosPorMenuDAO;
import capaDAO.AccesosPorOpcionDAO;
import capaDAO.AgrupadorMenuDAO;
import capaDAO.UsuarioDAO;

/**
 * Clase AutenticacionCtrl tiene como objetivo hacer las veces de Controlador para la autenticación de usuarios
 * en el aplicatiov
 * @author Juan David Botero Duque
 * @
 */
public class AutenticacionCtrl {
	
	
	private static AutenticacionCtrl instance;
	private boolean auditoria;
	
	//singleton controlador
	public static AutenticacionCtrl getInstance(){
		if(instance == null){
			instance = new AutenticacionCtrl(false);
		}
		return instance;
	}
	
	public AutenticacionCtrl(boolean auditoria)
	{
		this.auditoria = auditoria;
	}
	
	/**
	 * 
	 * @param usuario El usuario de logueo de la aplicación
	 * @param contrasena Contraseña asociada al usuario que se está logueando
	 * @return Se retona un valor booleano indicando si el usuario y contraseña corresponde con alguien logueado
	 * en al aplicación
	 */
	public boolean autenticarUsuario(Usuario usuario){
		int resultado = 0;
		boolean respuesta = false;
		resultado = UsuarioDAO.validarUsuario(usuario , auditoria);
		if(resultado > 0)
		{
			respuesta = true;
		}
		return(respuesta);
	}
	
	/**
	 * 
	 * @param usuario Se recibe el usuario de aplicación con el fin de validar si el usuario pasado como parámetro está
	 * o no logueado en la aplicación
	 * @return Se retorna un valor booleano indicando si el usuario se encuentra o no logueado en el aplicativo.
	 */
	public String validarAutenticacion(String usuario)
	{
		Usuario usu = new Usuario(usuario);
		String resultado = UsuarioDAO.validarAutenticacion(usu, auditoria);
		return(resultado);
	}
	
	/**
	 * Método Desarrollo por la capa de presentación
	 * @return
	 */
	public ArrayList<Object> obtenerMenusAgrupador()
	{
		ArrayList<Object>  menus = AgrupadorMenuDAO.obtenerMenusAgrupador(auditoria);
		return(menus);
	}
	
	public ArrayList<AgrupadorMenu> obtenerMenusAgrupadorObj()
	{
		ArrayList<AgrupadorMenu> agrMenus = AgrupadorMenuDAO.obtenerMenusAgrupadorObj(auditoria);
		return(agrMenus);
	}
	
	public int insertarMenuAgrupador(AgrupadorMenu menu)
	{
		int idMenu = AgrupadorMenuDAO.insertarMenuAgrupador(menu, auditoria);
		return(idMenu);
	}
	
	public boolean editarMenuAgrupador(AgrupadorMenu menu)
	{
		boolean respuesta  = AgrupadorMenuDAO.EditarMenuAgrupador(menu, auditoria);
		return(respuesta);
	}
	
	public boolean eliminarMenuAgrupador(int idMenu)
	{
		boolean respuesta = AgrupadorMenuDAO.eliminarMenuAgrupador(idMenu, auditoria);
		return(respuesta);
	}
	
	//Acciones para la entidad AccesosPorMenu
	
	public ArrayList obtenerAccesosAgrupador()
	{
		ArrayList accesos = AccesosPorMenuDAO.obtenerAccesosPorMenu(auditoria);
		return(accesos);
	}
	
	public ArrayList<AccesosPorMenu> obtenerAccesosPorMenuObj()
	{
		ArrayList<AccesosPorMenu> accesos = AccesosPorMenuDAO.obtenerAccesosPorMenuObj(auditoria);
		return(accesos);
	}
	
	public int insertarAccesosPorMenu(AccesosPorMenu acceso)
	{
		int idAccesoIns = AccesosPorMenuDAO.insertarAccesosPorMenu(acceso, auditoria);
		return(idAccesoIns);
	}

	public boolean eliminarAccesosPorMenu(int idAccesoMenu)
	{
		boolean respuesta = AccesosPorMenuDAO.eliminarAccesosPorMenu(idAccesoMenu, auditoria);
		return(respuesta);
	}
	
	public ArrayList<AccesosPorMenu> obtenerAccesosPorMenuUsuario( String nombreUsuario )
	{
		ArrayList<AccesosPorMenu> accesos = AccesosPorMenuDAO.obtenerAccesosPorMenuUsuario(nombreUsuario, auditoria);
		return(accesos);
	}
	
	//Creamos un método para validar el acceso del usuario a un menú determinado
	public boolean validarAccesoMenu(int menu, ArrayList<AccesosPorMenu> accesos )
	{
		boolean tieneAcceso = false;
		AccesosPorMenu accesoTemp;
		for(int i = 0; i < accesos.size(); i++)
		{
			accesoTemp = accesos.get(i);
			if(accesoTemp.getIdAgrupadorMenu() == menu)
			{
				tieneAcceso = true;
				break;
			}
		}
		return(tieneAcceso);
	}
	
	
	//Acciones para la entidad AccesosPorOpcion
	
	public ArrayList<AccesosPorOpcion> obtenerAccesosPorOpcionObj(int idTipoUsuario)
	{
		ArrayList<AccesosPorOpcion> accesos = AccesosPorOpcionDAO.obtenerAccesosPorOpcionObj(auditoria, idTipoUsuario);
		return(accesos);
	}
	
	//Creamos un método para validar el acceso del usuario a una opción determinado
		public boolean validarAccesoOpcion(String codPantalla, ArrayList<AccesosPorOpcion> accesos )
		{
			boolean tieneAcceso = false;
			AccesosPorOpcion accesoTemp;
			for(int i = 0; i < accesos.size(); i++)
			{
				accesoTemp = accesos.get(i);
				if(accesoTemp.getCodigoPantalla().equals(new String(codPantalla)))
				{
					tieneAcceso = true;
					break;
				}
			}
			return(tieneAcceso);
		}
	
	//Acciones para la Entidad AccesosPorBoton
		
	public ArrayList<AccesosPorBoton> obtenerAccesosPorBotonObj(int idTipUsu, String codPant)
	{
		ArrayList<AccesosPorBoton> accesos = AccesosPorBotonDAO.obtenerAccesosPorBotonObj(auditoria, idTipUsu, codPant);
		return(accesos);
	}
	
	//Creamos un método para validar el acceso del usuario a un botón determinado
			public boolean validarAccesoBoton(String codPantalla, String codBoton,  ArrayList<AccesosPorBoton> accesos )
			{
				boolean tieneAcceso = false;
				AccesosPorBoton accesoTemp;
				for(int i = 0; i < accesos.size(); i++)
				{
					accesoTemp = accesos.get(i);
					if(accesoTemp.getCodPantalla().equals(new String(codPantalla)) && accesoTemp.getCodBoton().equals(new String(codBoton)))
					{
						tieneAcceso = true;
						break;
					}
				}
				return(tieneAcceso);
			}
}
