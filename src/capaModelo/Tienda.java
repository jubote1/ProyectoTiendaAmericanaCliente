package capaModelo;

public class Tienda {
	
	private int idTienda;
	private String nombretienda;
	private String urlContact;
	private String direccion;
	private String telefono;
	private String razonSocial;
	private String tipoContribuyente;
	private String resolucion;
	private String fechaResolucion;
	private String ubicacion;
	private String identificacion;
	private String fechaApertura;
	private String fechaUltimoCierre;
	
	
	
	
	public String getFechaApertura() {
		return fechaApertura;
	}
	public void setFechaApertura(String fechaApertura) {
		this.fechaApertura = fechaApertura;
	}
	public String getFechaUltimoCierre() {
		return fechaUltimoCierre;
	}
	public void setFechaUltimoCierre(String fechaUltimoCierre) {
		this.fechaUltimoCierre = fechaUltimoCierre;
	}
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getTipoContribuyente() {
		return tipoContribuyente;
	}
	public void setTipoContribuyente(String tipoContribuyente) {
		this.tipoContribuyente = tipoContribuyente;
	}
	public String getResolucion() {
		return resolucion;
	}
	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}
	public String getFechaResolucion() {
		return fechaResolucion;
	}
	public void setFechaResolucion(String fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public int getIdTienda() {
		return idTienda;
	}
	public void setIdTienda(int idTienda) {
		this.idTienda = idTienda;
	}
	public String getNombretienda() {
		return nombretienda;
	}
	public void setNombretienda(String nombretienda) {
		this.nombretienda = nombretienda;
	}
	public String getUrlContact() {
		return urlContact;
	}
	public void setUrlContact(String urlContact) {
		this.urlContact = urlContact;
	}
	
	
	

	
	public Tienda(int idTienda, String nombretienda, String urlContact, String direccion, String telefono,
			String razonSocial, String tipoContribuyente, String resolucion, String fechaResolucion, String ubicacion,
			String identificacion, String fechaApertura, String fechaUltimoCierre) {
		super();
		this.idTienda = idTienda;
		this.nombretienda = nombretienda;
		this.urlContact = urlContact;
		this.direccion = direccion;
		this.telefono = telefono;
		this.razonSocial = razonSocial;
		this.tipoContribuyente = tipoContribuyente;
		this.resolucion = resolucion;
		this.fechaResolucion = fechaResolucion;
		this.ubicacion = ubicacion;
		this.identificacion = identificacion;
		this.fechaApertura = fechaApertura;
		this.fechaUltimoCierre = fechaUltimoCierre;
	}
	public Tienda()
	{
		
	}
	

}
