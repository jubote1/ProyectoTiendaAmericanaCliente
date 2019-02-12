package capaModelo;

public class DetallePedidoImpuesto {

	private int idPedido;
	private int idDetallePedido;
	private int idImpuesto;
	private double valorImpuesto;
	public int getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}
	public int getIdDetallePedido() {
		return idDetallePedido;
	}
	public void setIdDetallePedido(int idDetallePedido) {
		this.idDetallePedido = idDetallePedido;
	}
	public int getIdImpuesto() {
		return idImpuesto;
	}
	public void setIdImpuesto(int idImpuesto) {
		this.idImpuesto = idImpuesto;
	}
	public double getValorImpuesto() {
		return valorImpuesto;
	}
	public void setValorImpuesto(double valorImpuesto) {
		this.valorImpuesto = valorImpuesto;
	}
	public DetallePedidoImpuesto(int idPedido, int idDetallePedido, int idImpuesto, double valorImpuesto) {
		super();
		this.idPedido = idPedido;
		this.idDetallePedido = idDetallePedido;
		this.idImpuesto = idImpuesto;
		this.valorImpuesto = valorImpuesto;
	}
	
	
}
