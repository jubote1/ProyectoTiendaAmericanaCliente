package capaModelo;

public class TipoPedido {
	
	
	private int idTipoPedido;
	private String descripcion;
	private boolean valorDefecto;
	private String icono;
	boolean esDomicilio;
	
	
	
	
	public boolean isEsDomicilio() {
		return esDomicilio;
	}
	public void setEsDomicilio(boolean esDomicilio) {
		this.esDomicilio = esDomicilio;
	}
	public boolean isValorDefecto() {
		return valorDefecto;
	}
	public void setValorDefecto(boolean valorDefecto) {
		this.valorDefecto = valorDefecto;
	}
	public int getIdTipoPedido() {
		return idTipoPedido;
	}
	public void setIdTipoPedido(int idTipoPedido) {
		this.idTipoPedido = idTipoPedido;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getIcono() {
		return icono;
	}
	public void setIcono(String icono) {
		this.icono = icono;
	}
	public TipoPedido(int idTipoPedido, String descripcion, boolean valorDefecto, String icono, boolean esDomicilio) {
		super();
		this.idTipoPedido = idTipoPedido;
		this.descripcion = descripcion;
		this.valorDefecto = valorDefecto;
		this.icono = icono;
		this.esDomicilio = esDomicilio;
	}
	
	public String toString()
	{
		return(descripcion);
	}
	

}
