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
	private static int modeloImpresion;
	//La inicializamos en true es decir que debe de imprimir 
	private static boolean imprimirComandaPedido = true;
	private static String controlEstrictoPorciones = "S";
	private static int idProductoPorcion = 0;
	private static int idProductoGaseosa = 0;
	private static int estEmpDom;
	private static int estEnRutaDom;;
	private static int estEntregaDom;
	
	
	

	public static int getEstEmpDom() {
		return estEmpDom;
	}

	public static void setEstEmpDom(int estEmpDom) {
		Sesion.estEmpDom = estEmpDom;
	}

	public static int getEstEnRutaDom() {
		return estEnRutaDom;
	}

	public static void setEstEnRutaDom(int estEnRutaDom) {
		Sesion.estEnRutaDom = estEnRutaDom;
	}

	public static int getEstEntregaDom() {
		return estEntregaDom;
	}

	public static void setEstEntregaDom(int estEntregaDom) {
		Sesion.estEntregaDom = estEntregaDom;
	}

	public static int getIdProductoPorcion() {
		return idProductoPorcion;
	}

	public static void setIdProductoPorcion(int idProductoPorcion) {
		Sesion.idProductoPorcion = idProductoPorcion;
	}

	public static int getIdProductoGaseosa() {
		return idProductoGaseosa;
	}

	public static void setIdProductoGaseosa(int idProductoGaseosa) {
		Sesion.idProductoGaseosa = idProductoGaseosa;
	}

	public static String getControlEstrictoPorciones() {
		return controlEstrictoPorciones;
	}

	public static void setControlEstrictoPorciones(String controlEstrictoPorciones) {
		Sesion.controlEstrictoPorciones = controlEstrictoPorciones;
	}

	public static boolean isImprimirComandaPedido() {
		return imprimirComandaPedido;
	}

	public static void setImprimirComandaPedido(boolean imprimirComandaPedido) {
		Sesion.imprimirComandaPedido = imprimirComandaPedido;
	}

	public static int getModeloImpresion() {
		return modeloImpresion;
	}

	public static void setModeloImpresion(int modeloImpresion) {
		Sesion.modeloImpresion = modeloImpresion;
	}

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