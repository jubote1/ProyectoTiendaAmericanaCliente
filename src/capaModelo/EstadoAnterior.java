package capaModelo;

public class EstadoAnterior {

	private int idEstado;
	private int idEstadoAnterior;
	private String descEstadoAnterior;
	
	public String getDescEstadoAnterior() {
		return descEstadoAnterior;
	}
	public void setDescEstadoAnterior(String descEstadoAnterior) {
		this.descEstadoAnterior = descEstadoAnterior;
	}
	public int getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}
	public int getIdEstadoAnterior() {
		return idEstadoAnterior;
	}
	public void setIdEstadoAnterior(int idEstadoAnterior) {
		this.idEstadoAnterior = idEstadoAnterior;
	}
	public EstadoAnterior(int idEstado, int idEstadoAnterior, String descEstadoAnterior) {
		super();
		this.idEstado = idEstado;
		this.idEstadoAnterior = idEstadoAnterior;
		this.descEstadoAnterior = descEstadoAnterior;
	}
	
	public String toString()
	{
		return(descEstadoAnterior);
	}
	
}
