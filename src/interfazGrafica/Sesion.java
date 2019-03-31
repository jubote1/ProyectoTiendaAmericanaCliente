package interfazGrafica;

import java.util.ArrayList;

import capaModelo.AccesosPorMenu;
import capaModelo.AccesosPorOpcion;

public class Sesion {
	
	private static String usuario = "indefinido";
	private static int idUsuario = 0;
	private static ArrayList<AccesosPorMenu> accesosMenus;
	private static ArrayList<AccesosPorOpcion> accesosOpcion;
	private static int idTipoEmpleado;
	private static int presentacion;
	private static String host;
	private static String estacion;
	
	
	
	public static String getEstacion() {
		return estacion;
	}

	public static void setEstacion(String estacion) {
		Sesion.estacion = estacion;
	}

	public static String getHost() {
		return host;
	}

	public static void setHost(String host) {
		Sesion.host = host;
	}

	public static int getPresentacion() {
		return presentacion;
	}

	public static void setPresentacion(int presentacion) {
		Sesion.presentacion = presentacion;
	}

	public static int getIdTipoEmpleado() {
		return idTipoEmpleado;
	}

	public static void setIdTipoEmpleado(int idTipoEmpleado) {
		Sesion.idTipoEmpleado = idTipoEmpleado;
	}

	public static ArrayList<AccesosPorOpcion> getAccesosOpcion() {
		return accesosOpcion;
	}

	public static void setAccesosOpcion(ArrayList<AccesosPorOpcion> accesosOpcion) {
		Sesion.accesosOpcion = accesosOpcion;
	}

	public static ArrayList<AccesosPorMenu> getAccesosMenus() {
		return accesosMenus;
	}

	public static void setAccesosMenus(ArrayList<AccesosPorMenu> accesosMenus) {
		Sesion.accesosMenus = accesosMenus;
	}

	public static String getUsuario() {
		return usuario;
	}

	public static void setUsuario(String usuario) {
		Sesion.usuario = usuario;
	}

	public static int getIdUsuario() {
		return idUsuario;
	}

	public static void setIdUsuario(int idUsuario) {
		Sesion.idUsuario = idUsuario;
	}
	
	

}