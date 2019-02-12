package capaModelo;

public class InventariosTemporal {
	
	
	private String fechaSistema;
	private String tipoInventario;
	private int idItem;
	private double cantidad;
	public String getFechaSistema() {
		return fechaSistema;
	}
	public void setFechaSistema(String fechaSistema) {
		this.fechaSistema = fechaSistema;
	}
	public String getTipoInventario() {
		return tipoInventario;
	}
	public void setTipoInventario(String tipoInventario) {
		this.tipoInventario = tipoInventario;
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
	public InventariosTemporal(String fechaSistema, String tipoInventario, int idItem, double cantidad) {
		super();
		this.fechaSistema = fechaSistema;
		this.tipoInventario = tipoInventario;
		this.idItem = idItem;
		this.cantidad = cantidad;
	}
	
	

}
