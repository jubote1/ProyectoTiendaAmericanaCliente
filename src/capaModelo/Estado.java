package capaModelo;

public class Estado {
	
	private int idestado;
	private String descripcion;
	private String descripcionCorta;
	private int idTipoPedido;
	public Estado(int idestado, String descripcion, String descripcionCorta, int idTipoPedido) {
		super();
		this.idestado = idestado;
		this.descripcion = descripcion;
		this.descripcionCorta = descripcionCorta;
		this.idTipoPedido = idTipoPedido;
	}
	public int getIdestado() {
		return idestado;
	}
	public void setIdestado(int idestado) {
		this.idestado = idestado;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDescripcionCorta() {
		return descripcionCorta;
	}
	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}
	
	
	
	public int getIdTipoPedido() {
		return idTipoPedido;
	}
	public void setIdTipoPedido(int idTipoPedido) {
		this.idTipoPedido = idTipoPedido;
	}
	public Estado()
	{
		
	}

}
