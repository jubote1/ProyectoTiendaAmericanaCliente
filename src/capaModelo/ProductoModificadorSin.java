package capaModelo;

public class ProductoModificadorSin {
	
	private int idProducto;
	private int idProductoSin;
	private String nombreProductoSin;
	
	
	public String getNombreProductoSin() {
		return nombreProductoSin;
	}
	public void setNombreProductoSin(String nombreProductoSin) {
		this.nombreProductoSin = nombreProductoSin;
	}
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public int getIdProductoSin() {
		return idProductoSin;
	}
	public void setIdProductoSin(int idProductoSin) {
		this.idProductoSin = idProductoSin;
	}
	public ProductoModificadorSin(int idProducto, int idProductoSin) {
		super();
		this.idProducto = idProducto;
		this.idProductoSin = idProductoSin;
	}

	
	
	

}
