package capaModelo;

public class AccesosPorBoton {
	
	private int idAccesoBoton;
	private String codPantalla;
	private String codBoton;
	private String descripcion;
	private int idTipoUsuario;
	public int getIdAccesoBoton() {
		return idAccesoBoton;
	}
	public void setIdAccesoBoton(int idAccesoBoton) {
		this.idAccesoBoton = idAccesoBoton;
	}
	public String getCodPantalla() {
		return codPantalla;
	}
	public void setCodPantalla(String codPantalla) {
		this.codPantalla = codPantalla;
	}
	public String getCodBoton() {
		return codBoton;
	}
	public void setCodBoton(String codBoton) {
		this.codBoton = codBoton;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getIdTipoUsuario() {
		return idTipoUsuario;
	}
	public void setIdTipoUsuario(int idTipoUsuario) {
		this.idTipoUsuario = idTipoUsuario;
	}
	public AccesosPorBoton(int idAccesoBoton, String codPantalla, String codBoton, String descripcion,
			int idTipoUsuario) {
		super();
		this.idAccesoBoton = idAccesoBoton;
		this.codPantalla = codPantalla;
		this.codBoton = codBoton;
		this.descripcion = descripcion;
		this.idTipoUsuario = idTipoUsuario;
	}
	
	
	

}
