package capaModelo;

public class EstadoPedido {
	
	private int idPedidoTienda;
	private int idEstado;
	private boolean esFinal;
	public int getIdPedidoTienda() {
		return idPedidoTienda;
	}
	public void setIdPedidoTienda(int idPedidoTienda) {
		this.idPedidoTienda = idPedidoTienda;
	}
	public int getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}
	public boolean isEsFinal() {
		return esFinal;
	}
	public void setEsFinal(boolean esFinal) {
		this.esFinal = esFinal;
	}
	public EstadoPedido(int idPedidoTienda, int idEstado, boolean esFinal) {
		super();
		this.idPedidoTienda = idPedidoTienda;
		this.idEstado = idEstado;
		this.esFinal = esFinal;
	}
	
	

}
