package capaModelo;

public class PedidoDescuento {
	
	private int idpedido;
	private double descuentoPesos;
	private double descuentoPorcentaje;
	private String observacion;
	private String fechaDescuento;
	private double valorInicial;
	private double valorFinal;
	private String usuario;
	private String usuarioAutorizo;
	
	
	
	
	public PedidoDescuento(int idpedido, double descuentoPesos, double descuentoPorcentaje, String observacion,
			String fechaDescuento, double valorInicial, double valorFinal, String usuario, String usuarioAutorizo) {
		super();
		this.idpedido = idpedido;
		this.descuentoPesos = descuentoPesos;
		this.descuentoPorcentaje = descuentoPorcentaje;
		this.observacion = observacion;
		this.fechaDescuento = fechaDescuento;
		this.valorInicial = valorInicial;
		this.valorFinal = valorFinal;
		this.usuario = usuario;
		this.usuarioAutorizo = usuarioAutorizo;
	}

	
	


	public String getUsuarioAutorizo() {
		return usuarioAutorizo;
	}





	public void setUsuarioAutorizo(String usuarioAutorizo) {
		this.usuarioAutorizo = usuarioAutorizo;
	}





	public double getValorInicial() {
		return valorInicial;
	}



	public void setValorInicial(double valorInicial) {
		this.valorInicial = valorInicial;
	}



	public double getValorFinal() {
		return valorFinal;
	}



	public void setValorFinal(double valorFinal) {
		this.valorFinal = valorFinal;
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



	public String getUsuario() {
		return usuario;
	}



	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	
	
	
	

}
