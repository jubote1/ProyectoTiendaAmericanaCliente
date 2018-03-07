package capaModelo;

public class EleccionForzada {
	
	private int idEleccionForzada;
	private int idProducto;
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
	public EleccionForzada(int idEleccionForzada, int idProducto, int idPregunta, String precio, int estado) {
		super();
		this.idEleccionForzada = idEleccionForzada;
		this.idProducto = idProducto;
		this.idPregunta = idPregunta;
		this.precio = precio;
		this.estado = estado;
	}
	
	
	
}


