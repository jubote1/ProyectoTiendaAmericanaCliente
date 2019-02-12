package capaModelo;

public class AccesosPorOpcion {
	
	private int idAccesoOpcion;
	private String codigoPantalla;
	private int idTipoUsuario;
	public int getIdAccesoOpcion() {
		return idAccesoOpcion;
	}
	public void setIdAccesoOpcion(int idAccesoOpcion) {
		this.idAccesoOpcion = idAccesoOpcion;
	}
	public String getCodigoPantalla() {
		return codigoPantalla;
	}
	public void setCodigoPantalla(String codigoPantalla) {
		this.codigoPantalla = codigoPantalla;
	}
	public int getIdTipoUsuario() {
		return idTipoUsuario;
	}
	public void setIdTipoUsuario(int idTipoUsuario) {
		this.idTipoUsuario = idTipoUsuario;
	}
	public AccesosPorOpcion(int idAccesoOpcion, String codigoPantalla, int idTipoUsuario) {
		super();
		this.idAccesoOpcion = idAccesoOpcion;
		this.codigoPantalla = codigoPantalla;
		this.idTipoUsuario = idTipoUsuario;
	}
	
	
	
	
	

}
