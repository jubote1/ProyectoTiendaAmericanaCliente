package capaModelo;

public class PedidoDescuento {
	
	private int idpedido;
	private double descuentoPesos;
	private double descuentoPorcentaje;
	public PedidoDescuento(int idpedido, double descuentoPesos, double descuentoPorcentaje) {
		super();
		this.idpedido = idpedido;
		this.descuentoPesos = descuentoPesos;
		this.descuentoPorcentaje = descuentoPorcentaje;
	}
	public int getIdpedido() {
		return idpedido;
	}
	public void setIdpedido(int idpedido) {
		this.idpedido = idpedido;
	}
	public double getDescuentoPesos() {
		return descuentoPesos;
	}
	public void setDescuentoPesos(double descuentoPesos) {
		this.descuentoPesos = descuentoPesos;
	}
	public double getDescuentoPorcentaje() {
		return descuentoPorcentaje;
	}
	public void setDescuentoPorcentaje(double descuentoPorcentaje) {
		this.descuentoPorcentaje = descuentoPorcentaje;
	}
	
	

}
