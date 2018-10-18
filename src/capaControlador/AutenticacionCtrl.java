package capaControlador;

import capaModelo.MenuAgrupador;
import capaModelo.Usuario;

import java.util.ArrayList;

import capaDAO.MenuAgrupadorDAO;
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
		ArrayList<Object>  menus = MenuAgrupadorDAO.obtenerMenusAgrupador(auditoria);
		return(menus);
	}
	
	public int insertarMenuAgrupador(MenuAgrupador menu)
	{
		int idMenu = MenuAgrupadorDAO.insertarMenuAgrupador(menu, auditoria);
		return(idMenu);
	}
	
	public boolean editarMenuAgrupador(MenuAgrupador menu)
	{
		boolean respuesta  = MenuAgrupadorDAO.EditarMenuAgrupador(menu, auditoria);
		return(respuesta);
	}
	
	public boolean eliminarMenuAgrupador(int idMenu)
	{
		boolean respuesta = MenuAgrupadorDAO.eliminarMenuAgrupador(idMenu, auditoria);
		return(respuesta);
	}

}
