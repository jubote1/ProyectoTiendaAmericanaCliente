package capaModelo;

public class ImpuestoProducto {
	
	private int idImpuestoProducto;
	private int idProducto;
	private int idImpuesto;
	public int getIdImpuestoProducto() {
		return idImpuestoProducto;
	}
	public void setIdImpuestoProducto(int idImpuestoProducto) {
		this.idImpuestoProducto = idImpuestoProducto;
	}
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public int getIdImpuesto() {
		return idImpuesto;
	}
	public void setIdImpuesto(int idImpuesto) {
		this.idImpuesto = idImpuesto;
	}
	public ImpuestoProducto(int idImpuestoProducto, int idProducto, int idImpuesto) {
		super();
		this.idImpuestoProducto = idImpuestoProducto;
		this.idProducto = idProducto;
		this.idImpuesto = idImpuesto;
	}
	
	

}
