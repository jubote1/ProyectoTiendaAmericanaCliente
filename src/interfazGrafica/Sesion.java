package interfazGrafica;

import java.util.ArrayList;

import capaModelo.AccesosPorMenu;

public class Sesion {
	
	private static String usuario = "indefinido";
	private static int idUsuario = 0;
	private static ArrayList<AccesosPorMenu> accesosMenus;
	
	
	
	
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