package capaModelo;

public class FechaSistema {
	
	private String fechaApertura;
	private String fechaUltimoCierre;
	public String getFechaApertura() {
		return fechaApertura;
	}
	public void setFechaApertura(String fechaApertura) {
		this.fechaApertura = fechaApertura;
	}
	public String getFechaUltimoCierre() {
		return fechaUltimoCierre;
	}
	public void setFechaUltimoCierre(String fechaUltimoCierre) {
		this.fechaUltimoCierre = fechaUltimoCierre;
	}
	public FechaSistema(String fechaApertura, String fechaUltimoCierre) {
		super();
		this.fechaApertura = fechaApertura;
		this.fechaUltimoCierre = fechaUltimoCierre;
	}
	public FechaSistema() {
		super();
	}
	
	

}
