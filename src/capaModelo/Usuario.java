package capaModelo;

/**
 * Clase que implementa la entidad Usuario.
 * @author JuanDavid
 *
 */
public class Usuario {
	
	private int idUsuario;
	private String nombreUsuario;
	private String contrasena;
	private String nombreLargo;
	private int idTipoEmpleado;
	private String tipoInicio;
	private boolean administrador;
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	public String getNombreLargo() {
		return nombreLargo;
	}
	public void setNombreLargo(String nombreLargo) {
		this.nombreLargo = nombreLargo;
	}
	
	
	
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getidTipoEmpleado() {
		return idTipoEmpleado;
	}
	public void idTipoEmpleado(int idTipoEmpleado) {
		this.idTipoEmpleado = idTipoEmpleado;
	}
	public String getTipoInicio() {
		return tipoInicio;
	}
	public void setTipoInicio(String tipoInicio) {
		this.tipoInicio = tipoInicio;
	}
	public boolean isAdministrador() {
		return administrador;
	}
	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}
	
	
	public Usuario(int idUsuario, String nombreUsuario, String contrasena, String nombreLargo, int idTipoEmpleado,
			String tipoInicio, boolean administrador) {
		super();
		this.idUsuario = idUsuario;
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
		this.nombreLargo = nombreLargo;
		this.idTipoEmpleado = idTipoEmpleado;
		this.tipoInicio = tipoInicio;
		this.administrador = administrador;
	}
	public Usuario(String nombreUsuario) {
		super();
		this.nombreUsuario = nombreUsuario;
	}
	
	
	
	

}
