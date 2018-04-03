package capaModelo;

public class EleccionForzada {
	
	private int idEleccionForzada;
	private int idProducto;
	private String descripcion;
	private int idPregunta;
	private String precio;
	private int estado;
	public int getIdEleccionForzada() {
		return idEleccionForzada;
	}
	public void setIdEleccionForzada(int idEleccionForzada) {
		this.idEleccionForzada = idEleccionForzada;
	}
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public int getIdPregunta() {
		return idPregunta;
	}
	public void setIdPregunta(int idPregunta) {
		this.idPregunta = idPregunta;
	}

	
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public EleccionForzada(int idEleccionForzada, int idProducto, String descripcion, int idPregunta, String precio,
			int estado) {
		super();
		this.idEleccionForzada = idEleccionForzada;
		this.idProducto = idProducto;
		this.descripcion = descripcion;
		this.idPregunta = idPregunta;
		this.precio = precio;
		this.estado = estado;
	}
	
	
	
	
	
}


