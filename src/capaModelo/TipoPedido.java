package capaModelo;

public class TipoPedido {
	
	
	private int idTipoPedido;
	private String descripcion;
	private boolean valorDefecto;
	
	
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
	public TipoPedido(int idTipoPedido, String descripcion, boolean valorDefecto) {
		super();
		this.idTipoPedido = idTipoPedido;
		this.descripcion = descripcion;
		this.valorDefecto = valorDefecto;
	}
	
	public String toString()
	{
		return(descripcion);
	}
	

}
