package capaControlador;

import capaModelo.AccesosPorBoton;
import capaModelo.AccesosPorMenu;
import capaModelo.AccesosPorOpcion;
import capaModelo.AgrupadorMenu;
import capaModelo.Parametro;
import capaModelo.Usuario;
import interfazGrafica.PrincipalLogueo;
import interfazGrafica.Sesion;

import java.util.ArrayList;

import javax.swing.JOptionPane;

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
	ParametrosCtrl parCtrl = new ParametrosCtrl(PrincipalLogueo.habilitaAuditoria);
	
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
	 * Método que se encarga de realizar la validación de la clave si cumple o no con los requisitos, retornando un entero que tendrá el valor de 1 sino cumple
	 * con los requisitos mínimos, 2 si es una clave ya utilizada o 0 si la clave si pasa las validaciones
	 * @param idUsuario
	 * @param clave
	 * @return
	 */
	public String validarParametroClave(int idUsuario, String clave)
	{
		ParametrosCtrl parCtrl = new ParametrosCtrl(auditoria);
		Parametro parBase;
		String strConexion = "";
		parBase = parCtrl.obtenerParametro("BDGENERAL");
		strConexion = parBase.getValorTexto();
		//tenemos 4 roles en el sistema, temporal, domiciliario, administrador, caja
		boolean esTemporal = false;
		boolean esDomiciliario = false;
		boolean esAdministrador = false;
		boolean esCaja = false;
		//Si es de empleado temporal no deja cambiar la clave
		esTemporal = UsuarioDAO.validarEsTemporal(idUsuario, strConexion, auditoria);
		if(esTemporal)
		{
			//Se retorna falso dado que a los empleados temporales no se les debe cambiar la clave
			return("El empleado es temporal y a los empleados temporales no se les debe cambiar la clave.");
		}
		//Debemos de validar si es administrador debe ser una clave de 4 y no ser una clave facil
		esAdministrador = UsuarioDAO.validarEsAdministrador(idUsuario, strConexion,  auditoria);
		if(esAdministrador)
		{
			if(clave.length() == 4)
			{
				//Se debe validar que la clave no sea una clave facil, la cual esta catalogada como los mismos números
				String clavesFaciles[] = {"0000", "1111", "2222", "3333", "4444", "5555", "6666", "7777", "8888", "9999"};
				for(int i = 0; i < clavesFaciles.length; i++)
				{
					if(clavesFaciles[i].equals(clave))
					{
						//Se retorna falso dado que se encontró que es una clave facil.
						return("El usuario es de tipo administrador y la clave asignada es una clave fácil, se debe escoger otra clave.");
					}
				}
			}else
			{
				//Se retorna falso debido a que la clave del administrador debe ser igual a 4 caracteres
				return("El usuario es de tipo administrador y debe tener mínimo 4 dígitos.");
			}
		}
		//Si es domiciliario debe ser de 3
		esDomiciliario = UsuarioDAO.validarEsDomiciliario(idUsuario, strConexion,  auditoria);
		if(esDomiciliario)
		{
			if(clave.length() == 3)
			{
				
			}else
			{
				//Se retorna falso debido a que se encontró que la clave no tiene 3 números.
				return("El usuario es de tipo domiciliario y debe tener mínimo 3 dígitos.");
			}
		}
		//Ya nos queda la opción por descarte qeu sea un usuario regular, validaremos que tenga una clave de 4
		if(!esAdministrador && !esDomiciliario && !esTemporal)
		{
			//Se retorna falso debido a que la clave del administrador debe ser igual a 4 caracteres
			return("El empleado no es ni administrador, ni auxiliar ni domicialiario, por lo tanto no se le debe asignar clave rápida.");
			
		}
		//Cumplidas estas validaciones por último se valida que la clave ya no esté asignada
		if(UsuarioDAO.validarSiClaveExiste(clave, strConexion,  esCaja))
		{
			//Se retorna 2 debido a que la clave si existe ya en la base de datos.
			return("La clave asignada ya está siendo usada, por favor seleccione una clave diferente");
		}
		
		return("OK");
	}
	
	/**
	 * 
	 * @param usuario El usuario de logueo de la aplicación
	 * @param contrasena Contraseña asociada al usuario que se está logueando
	 * @return Se retona un valor booleano indicando si el usuario y contraseña corresponde con alguien logueado
	 * en al aplicación
	 */
	public int autenticarUsuario(Usuario usuario, String fechaSistema){
		int idUsuarioEnc = 0;
		boolean respuesta = false;
		//Recuperación de la variable para saber configuración de logueo
		Parametro parametroAud = parCtrl.obtenerParametro("CONFSEGURIDAD");
		String strParam = parametroAud.getValorTexto();
		if(strParam.equals(new String("S")))
		{
			//Se realiza validación 
			idUsuarioEnc = UsuarioDAO.validarUsuario(usuario , fechaSistema, "",  auditoria);
		}else 
		{
			//Recuperar dirección de la base de datos general, para lo cual tendremos una variable para almacenar esto
			parametroAud = parCtrl.obtenerParametro("BDGENERAL");
			idUsuarioEnc = UsuarioDAO.validarUsuario(usuario , fechaSistema, parametroAud.getValorTexto(),  auditoria);
		}	

		if(idUsuarioEnc > 0)
		{
			respuesta = true;
			//Sabiendo que la respuesta es que si hubo autenticación actualizamos la fecha de último logueo
			UsuarioDAO.actualizarUltimoIngreso(usuario, auditoria);
		}
		return(idUsuarioEnc);
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
		String resultado = "";
		Parametro parametroAud = parCtrl.obtenerParametro("CONFSEGURIDAD");
		String strParam = parametroAud.getValorTexto();
		if(strParam.equals(new String("S")))
		{
			resultado = UsuarioDAO.validarAutenticacion(usu, "", auditoria);
		}else
		{
			//Recuperar dirección de la base de datos general, para lo cual tendremos una variable para almacenar esto
			parametroAud = parCtrl.obtenerParametro("BDGENERAL");
			resultado = UsuarioDAO.validarAutenticacion(usu, parametroAud.getValorTexto(), auditoria);
		}
		return(resultado);
	}
	
	public boolean asignarClaveRapida(Usuario usuario, String claveRapida)
	{
		boolean respuesta = UsuarioDAO.asignarClaveRapida(usuario, claveRapida, auditoria);
		return(respuesta);
	}
	
	public boolean asignarClaveRapidaGeneral(Usuario usuario, String claveRapida)
	{
		ParametrosCtrl parCtrl = new ParametrosCtrl(auditoria);
		Parametro parBase;
		String strConexion = "";
		parBase = parCtrl.obtenerParametro("BDGENERAL");
		strConexion = parBase.getValorTexto();
		boolean respuesta = UsuarioDAO.asignarClaveRapidaGeneral(usuario, claveRapida, strConexion,  auditoria);
		return(respuesta);
	}
	
	public Usuario validarAutenticacionRapida(String claveRapida)
	{
		Usuario  usuario;
		Parametro parametroAud = parCtrl.obtenerParametro("CONFSEGURIDAD");
		String strParam = parametroAud.getValorTexto();
		if(strParam.equals(new String("S")))
		{
			usuario = UsuarioDAO.validarAutenticacionRapida(claveRapida, "",auditoria);
		}else
		{
			//Recuperar dirección de la base de datos general, para lo cual tendremos una variable para almacenar esto
			parametroAud = parCtrl.obtenerParametro("BDGENERAL");
			usuario = UsuarioDAO.validarAutenticacionRapida(claveRapida,parametroAud.getValorTexto(), auditoria);
		}
		
		//Realizamos si la validación fue correcta y realizamos la actualización de último logueo del usuario
		if(usuario.getIdUsuario() > 0)
		{
			UsuarioDAO.actualizarUltimoIngresoCR(claveRapida, auditoria);
		}
		
		return(usuario);
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
	
	public void cargarEntornoFinal(String usu, int idUsu, Usuario objUsu, int idTipoEmpleado )
	{
		Sesion.setUsuario(usu);
		Sesion.setIdUsuario(idUsu);
		Sesion.setAccesosMenus(obtenerAccesosPorMenuUsuario(usu));
		Sesion.setAccesosOpcion(obtenerAccesosPorOpcionObj(objUsu.getidTipoEmpleado()));
		Sesion.setIdTipoEmpleado(objUsu.getidTipoEmpleado());
		//Tomamos el valor del parámetro relacionado la interface gráfica
		Parametro parametro = parCtrl.obtenerParametro("PRESENTACION");
		int valPresentacion = 0;
		try
		{
			valPresentacion = parametro.getValorNumerico();
		}catch(Exception e)
		{
			System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE PRESENTACIÓN SISTEMA");
			valPresentacion = 0;
		}
		if(valPresentacion == 0)
		{
			valPresentacion =1;
		}
		Sesion.setPresentacion(valPresentacion);
		int valModeloImpr = 0;
		parametro = parCtrl.obtenerParametro("MODELOIMPRESION");
		try
		{
			valModeloImpr = parametro.getValorNumerico();
		}catch(Exception e)
		{
			System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE PRESENTACIÓN MODELO IMPRESION");
			valModeloImpr = 0;
		}
		Sesion.setModeloImpresion(valModeloImpr);
		boolean imprimeComandaFactura = true;
		parametro = parCtrl.obtenerParametro("IMPRIMECOMANDAFACTURA");
		try
		{
			imprimeComandaFactura = Boolean.parseBoolean(parametro.getValorTexto());
		}catch(Exception e)
		{
			System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE IMPRESIÓN COMANDA PEDIDO");
			imprimeComandaFactura = true;
		}
		Sesion.setImprimirComandaPedido(imprimeComandaFactura);
		//Cargamos el valor de la variable de configuración de control de porciones
		String controlEstPor = "";
		parametro = parCtrl.obtenerParametro("CONTROLESTPORCIONES");
		try
		{
			controlEstPor = parametro.getValorTexto();
		}catch(Exception e)
		{
			System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE CONTROL ESTRICTO PORCIONES");
			controlEstPor = "S";
		}
		Sesion.setControlEstrictoPorciones(controlEstPor);
		//Cargamos las variables de porción y gaseosa
		int idProductoPorcion;
		int idProductoGaseosa;
		parametro = parCtrl.obtenerParametro("PRODUCTOPORCION");
		try
		{
			idProductoPorcion = parametro.getValorNumerico();
		}catch(Exception e)
		{
			System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE PRODUCTO PORCIONES");
			idProductoPorcion = 0;
		}
		Sesion.setIdProductoPorcion(idProductoPorcion);
		parametro = parCtrl.obtenerParametro("PRODUCTOGASEOSA");
		try
		{
			idProductoGaseosa = parametro.getValorNumerico();
		}catch(Exception e)
		{
			System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE PRODUCTO GASEOSA");
			idProductoGaseosa = 0;
		}
		Sesion.setIdProductoGaseosa(idProductoGaseosa);
		//Cargamos los id de los estados de pedidos domiciliario
		int estEmpDom;
		int estEnRutaDom;
		int estEntregaDom;
		parametro = parCtrl.obtenerParametro("EMPACADODOMICILIO");
		try
		{
			estEmpDom = parametro.getValorNumerico();
		}catch(Exception e)
		{
			System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE EMPACADO DOMICILIO");
			estEmpDom = 0;
		}
		Sesion.setEstEmpDom(estEmpDom);
		parametro = parCtrl.obtenerParametro("ENRUTADOMICILIO");
		try
		{
			estEnRutaDom = parametro.getValorNumerico();
		}catch(Exception e)
		{
			System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE ESTADO PEDIDO EN RUTA");
			estEnRutaDom = 0;
		}
		Sesion.setEstEnRutaDom(estEnRutaDom);
		parametro = parCtrl.obtenerParametro("ENTREGADODOMICILIO");
		try
		{
			estEntregaDom = parametro.getValorNumerico();
		}catch(Exception e)
		{
			System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE ESTADO PEDIDO DOMICILIO ENTREGADO");
			estEntregaDom = 0;
		}
		Sesion.setEstEntregaDom(estEntregaDom);
	}
	
	public boolean validarEsAdministrador(int idUsuario)
	{
		ParametrosCtrl parCtrl = new ParametrosCtrl(auditoria);
		Parametro parBase;
		String strConexion = "";
		parBase = parCtrl.obtenerParametro("BDGENERAL");
		strConexion = parBase.getValorTexto();
		boolean respuesta = UsuarioDAO.validarEsAdministrador(idUsuario, strConexion, auditoria);
		return(respuesta);
	}
}
