package capaModelo;

public class Impuesto {
	
	private int idImpuesto;
	private String descripcion;
	private double valorPorcentaje;
	public int getIdImpuesto() {
		return idImpuesto;
	}
	public void setIdImpuesto(int idImpuesto) {
		this.idImpuesto = idImpuesto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public double getValorPorcentaje() {
		return valorPorcentaje;
	}
	public void setValorPorcentaje(double valorPorcentaje) {
		this.valorPorcentaje = valorPorcentaje;
	}
	public Impuesto(int idImpuesto, String descripcion, double valorPorcentaje) {
		super();
		this.idImpuesto = idImpuesto;
		this.descripcion = descripcion;
		this.valorPorcentaje = valorPorcentaje;
	}
	
	

}
