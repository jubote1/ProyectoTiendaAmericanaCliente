package capaModelo;

public class PedidoDescuento {
	
	private int idpedido;
	private double descuentoPesos;
	private double descuentoPorcentaje;
	private String observacion;
	private String fechaDescuento;
	
	
	
	public PedidoDescuento(int idpedido, double descuentoPesos, double descuentoPorcentaje, String observacion) {
		super();
		this.idpedido = idpedido;
		this.descuentoPesos = descuentoPesos;
		this.descuentoPorcentaje = descuentoPorcentaje;
		this.observacion = observacion;
	}
	
	public String getFechaDescuento() {
		return fechaDescuento;
	}

	public void setFechaDescuento(String fechaDescuento) {
		this.fechaDescuento = fechaDescuento;
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
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	
	
	

}
