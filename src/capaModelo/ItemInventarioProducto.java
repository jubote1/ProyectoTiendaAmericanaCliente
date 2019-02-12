package capaModelo;

public class ItemInventarioProducto {
	
	private int idItemProducto;
	private int idItem;
	private int idProducto;
	private double cantidad;
	public int getIdItemProducto() {
		return idItemProducto;
	}
	public void setIdItemProducto(int idItemProducto) {
		this.idItemProducto = idItemProducto;
	}
	public int getIdItem() {
		return idItem;
	}
	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	public ItemInventarioProducto(int idItemProducto, int idItem, int idProducto, double cantidad) {
		super();
		this.idItemProducto = idItemProducto;
		this.idItem = idItem;
		this.idProducto = idProducto;
		this.cantidad = cantidad;
	}
	
	
	

}
