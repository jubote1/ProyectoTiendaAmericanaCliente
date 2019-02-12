package capaModelo;

public class Ingreso {
	
	private int idIngreso;
	private double valorIngreso;
	private String fecha;
	private String descripcion;
	public int getIdIngreso() {
		return idIngreso;
	}
	public void setIdIngreso(int idIngreso) {
		this.idIngreso = idIngreso;
	}
	public double getValorIngreso() {
		return valorIngreso;
	}
	public void setValorIngreso(double valorIngreso) {
		this.valorIngreso = valorIngreso;
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
	public Ingreso(int idIngreso, double valorIngreso, String fecha, String descripcion) {
		super();
		this.idIngreso = idIngreso;
		this.valorIngreso = valorIngreso;
		this.fecha = fecha;
		this.descripcion = descripcion;
	}
	
	
	

}
