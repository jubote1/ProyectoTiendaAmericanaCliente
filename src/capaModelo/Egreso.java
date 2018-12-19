package capaModelo;

public class Egreso {
	
	private int idEgreso;
	private double valorEgreso;
	private String fecha;
	private String descripcion;
	public int getIdEgreso() {
		return idEgreso;
	}
	public void setIdEgreso(int idEgreso) {
		this.idEgreso = idEgreso;
	}
	public double getValorEgreso() {
		return valorEgreso;
	}
	public void setValorEgreso(double valorEgreso) {
		this.valorEgreso = valorEgreso;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Egreso(int idEgreso, double valorEgreso, String fecha, String descripcion) {
		super();
		this.idEgreso = idEgreso;
		this.valorEgreso = valorEgreso;
		this.fecha = fecha;
		this.descripcion = descripcion;
	}
	
	
	

}
