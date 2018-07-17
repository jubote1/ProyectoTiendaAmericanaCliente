package interfazGrafica;

public class Sesion {
	
	private static String usuario = "indefinido";
	
	public static String getUsuario() {
		return usuario;
	}

	public static void setUsuario(String usuario) {
		Sesion.usuario = usuario;
	}

}