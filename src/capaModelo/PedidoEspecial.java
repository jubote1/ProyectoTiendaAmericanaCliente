package capaModelo;

public class PedidoEspecial {
	
	private int idPedidoEspecial;
	private int idItem;
	private double cantidad;
	private String fecha;
	public int getIdPedidoEspecial() {
		return idPedidoEspecial;
	}
	public void setIdPedidoEspecial(int idPedidoEspecial) {
		this.idPedidoEspecial = idPedidoEspecial;
	}
	public int getIdItem() {
		return idItem;
	}
	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public PedidoEspecial(int idPedidoEspecial, int idItem, double cantidad, String fecha) {
		super();
		this.idPedidoEspecial = idPedidoEspecial;
		this.idItem = idItem;
		this.cantidad = cantidad;
		this.fecha = fecha;
	}
	
	

}
