package capaModelo;

public class TiempoPedido {
	
	private int idPedidoTienda;
	private String tiempoPedido;
	public int getIdPedidoTienda() {
		return idPedidoTienda;
	}
	public void setIdPedidoTienda(int idPedidoTienda) {
		this.idPedidoTienda = idPedidoTienda;
	}
	public String getTiempoPedido() {
		return tiempoPedido;
	}
	public void setTiempoPedido(String tiempoPedido) {
		this.tiempoPedido = tiempoPedido;
	}
	public TiempoPedido(int idPedidoTienda, String tiempoPedido) {
		super();
		this.idPedidoTienda = idPedidoTienda;
		this.tiempoPedido = tiempoPedido;
	}
	
	

}
