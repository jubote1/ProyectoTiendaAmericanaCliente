package capaModelo;

public class PedidoFormaPago {
	
	private int idPedidoFormaPago;
	private int idPedidoTienda;
	private int idFormaPago;
	private double valorTotal;
	private double valorFormaPago;
	private double valorDisminuido;
	
		
	public double getValorDisminuido() {
		return valorDisminuido;
	}
	public void setValorDisminuido(double valorDisminuido) {
		this.valorDisminuido = valorDisminuido;
	}
	public int getIdPedidoFormaPago() {
		return idPedidoFormaPago;
	}
	public void setIdPedidoFormaPago(int idPedidoFormaPago) {
		this.idPedidoFormaPago = idPedidoFormaPago;
	}
	public int getIdPedidoTienda() {
		return idPedidoTienda;
	}
	public void setIdPedidoTienda(int idPedidoTienda) {
		this.idPedidoTienda = idPedidoTienda;
	}
	public int getIdFormaPago() {
		return idFormaPago;
	}
	public void setIdFormaPago(int idFormaPago) {
		this.idFormaPago = idFormaPago;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public double getValorFormaPago() {
		return valorFormaPago;
	}
	public void setValorFormaPago(double valorFormaPago) {
		this.valorFormaPago = valorFormaPago;
	}
	public PedidoFormaPago(int idPedidoFormaPago, int idPedidoTienda, int idFormaPago, double valorTotal,
			double valorFormaPago, double valorDisminuido) {
		super();
		this.idPedidoFormaPago = idPedidoFormaPago;
		this.idPedidoTienda = idPedidoTienda;
		this.idFormaPago = idFormaPago;
		this.valorTotal = valorTotal;
		this.valorFormaPago = valorFormaPago;
		this.valorDisminuido = valorDisminuido;
	}
	
	
	

}
