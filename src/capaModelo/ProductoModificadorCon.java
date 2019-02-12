package capaModelo;

public class ProductoModificadorCon {
	
	private int idProducto;
	private int idProductoCon;
	private String nombreProductoCon;
	
	
	
	public String getNombreProductoCon() {
		return nombreProductoCon;
	}
	public void setNombreProductoCon(String nombreProductoCon) {
		this.nombreProductoCon = nombreProductoCon;
	}
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public int getIdProductoCon() {
		return idProductoCon;
	}
	public void setIdProductoCon(int idProductoCon) {
		this.idProductoCon = idProductoCon;
	}
	public ProductoModificadorCon(int idProducto, int idProductoCon) {
		super();
		this.idProducto = idProducto;
		this.idProductoCon = idProductoCon;
	}
	
	
	

}
