package capaModelo;

public class EstadoPosterior {
	
	private int idEstado;
	private int idEstadoPosterior;
	private String descEstadoPosterior;
	private boolean imprimeEstPosterior;
	
	
	
	public boolean isImprimeEstPosterior() {
		return imprimeEstPosterior;
	}
	public void setImprimeEstPosterior(boolean imprimeEstPosterior) {
		this.imprimeEstPosterior = imprimeEstPosterior;
	}
	public String getDescEstadoPosterior() {
		return descEstadoPosterior;
	}
	public void setDescEstadoPosterior(String descEstadoPosterior) {
		this.descEstadoPosterior = descEstadoPosterior;
	}
	public int getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}
	public int getIdEstadoPosterior() {
		return idEstadoPosterior;
	}
	public void setIdEstadoPosterior(int idEstadoPosterior) {
		this.idEstadoPosterior = idEstadoPosterior;
	}
	public EstadoPosterior(int idEstado, int idEstadoPosterior, String descEstadoPosterior, boolean imprimeEstPosterior) {
		super();
		this.idEstado = idEstado;
		this.idEstadoPosterior = idEstadoPosterior;
		this.descEstadoPosterior = descEstadoPosterior;
		this.imprimeEstPosterior = imprimeEstPosterior;
	}
	
	public String toString()
	{
		return(descEstadoPosterior);
	}
	
	

}
