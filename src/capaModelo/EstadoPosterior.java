package capaModelo;

public class EstadoPosterior {
	
	private int idEstado;
	private int idEstadoPosterior;
	private String descEstadoPosterior;
	
	
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
	public EstadoPosterior(int idEstado, int idEstadoPosterior, String descEstadoPosterior) {
		super();
		this.idEstado = idEstado;
		this.idEstadoPosterior = idEstadoPosterior;
		this.descEstadoPosterior = descEstadoPosterior;
	}
	
	public String toString()
	{
		return(descEstadoPosterior);
	}
	
	

}
