package capaControlador;

import capaModelo.MenuAgrupador;
import capaModelo.Usuario;

import java.util.ArrayList;

import capaDAO.MenuAgrupadorDAO;
import capaDAO.UsuarioDAO;

/**
 * Clase AutenticacionCtrl tiene como objetivo hacer las veces de Controlador para la autenticaci�n de usuarios
 * en el aplicatiov
 * @author Juan David Botero Duque
 * @
 */
public class AutenticacionCtrl {
	
	
	private static AutenticacionCtrl instance;
	
	//singleton controlador
	public static AutenticacionCtrl getInstance(){
		if(instance == null){
			instance = new AutenticacionCtrl();
		}
		return instance;
	}
	
	/**
	 * 
	 * @param usuario El usuario de logueo de la aplicaci�n
	 * @param contrasena Contrase�a asociada al usuario que se est� logueando
	 * @return Se retona un valor booleano indicando si el usuario y contrase�a corresponde con alguien logueado
	 * en al aplicaci�n
	 */
	public boolean autenticarUsuario(Usuario usuario){
		int resultado = 0;
		boolean respuesta = false;
		resultado = UsuarioDAO.validarUsuario(usuario);
		if(resultado > 0)
		{
			respuesta = true;
		}
		return(respuesta);
	}
	
	/**
	 * 
	 * @param usuario Se recibe el usuario de aplicaci�n con el fin de validar si el usuario pasado como par�metro est�
	 * o no logueado en la aplicaci�n
	 * @return Se retorna un valor booleano indicando si el usuario se encuentra o no logueado en el aplicativo.
	 */
	public String validarAutenticacion(String usuario)
	{
		Usuario usu = new Usuario(usuario);
		String resultado = UsuarioDAO.validarAutenticacion(usu);
		return(resultado);
	}
	
	/**
	 * M�todo Desarrollo por la capa de presentaci�n
	 * @return
	 */
	public ArrayList<Object> obtenerMenusAgrupador()
	{
		ArrayList<Object>  menus = MenuAgrupadorDAO.obtenerMenusAgrupador();
		return(menus);
	}
	
	public int insertarMenuAgrupador(MenuAgrupador menu)
	{
		int idMenu = MenuAgrupadorDAO.insertarMenuAgrupador(menu);
		return(idMenu);
	}
	
	public boolean editarMenuAgrupador(MenuAgrupador menu)
	{
		boolean respuesta  = MenuAgrupadorDAO.EditarMenuAgrupador(menu);
		return(respuesta);
	}
	
	public boolean eliminarMenuAgrupador(int idMenu)
	{
		boolean respuesta = MenuAgrupadorDAO.eliminarMenuAgrupador(idMenu);
		return(respuesta);
	}

}
