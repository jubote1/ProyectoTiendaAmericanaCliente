package capaModelo;

public class PorcionesControlDiario {
	
	private String fechaControl;
	private int porcion;
	private int porcionGaseosa;
	private int porcionEmpleado;
	private int porcionDesecho;
	private int porcionTemporal;
	private boolean facturado;
	
	public int getPorcionTemporal() {
		return porcionTemporal;
	}
	public void setPorcionTemporal(int porcionTemporal) {
		this.porcionTemporal = porcionTemporal;
	}
	public String getFechaControl() {
		return fechaControl;
	}
	public void setFechaControl(String fechaControl) {
		this.fechaControl = fechaControl;
	}
	public int getPorcion() {
		return porcion;
	}
	public void setPorcion(int porcion) {
		this.porcion = porcion;
	}
	public int getPorcionGaseosa() {
		return porcionGaseosa;
	}
	public void setPorcionGaseosa(int porcionGaseosa) {
		this.porcionGaseosa = porcionGaseosa;
	}
	public int getPorcionEmpleado() {
		return porcionEmpleado;
	}
	public void setPorcionEmpleado(int porcionEmpleado) {
		this.porcionEmpleado = porcionEmpleado;
	}
	public int getPorcionDesecho() {
		return porcionDesecho;
	}
	public void setPorcionDesecho(int porcionDesecho) {
		this.porcionDesecho = porcionDesecho;
	}
	public boolean isFacturado() {
		return facturado;
	}
	public void setFacturado(boolean facturado) {
		this.facturado = facturado;
	}
	public PorcionesControlDiario(String fechaControl, int porcion, int porcionGaseosa, int porcionEmpleado,
			int porcionDesecho, int porcionTemporal, boolean facturado) {
		super();
		this.fechaControl = fechaControl;
		this.porcion = porcion;
		this.porcionGaseosa = porcionGaseosa;
		this.porcionEmpleado = porcionEmpleado;
		this.porcionDesecho = porcionDesecho;
		this.porcionTemporal = porcionTemporal;
		this.facturado = facturado;
	}

	
	
	

}
