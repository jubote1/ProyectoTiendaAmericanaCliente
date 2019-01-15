package capaModelo;

public class AnulacionPedido {
	
	private int idPedido;
	private String fecha;
	private String producto;
	private double valor;
	private String usuario;
	private String tipoAnulacion;
	public int getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getTipoAnulacion() {
		return tipoAnulacion;
	}
	public void setTipoAnulación(String tipoAnulacion) {
		this.tipoAnulacion = tipoAnulacion;
	}
	public AnulacionPedido(int idPedido, String fecha, String producto, double valor, String usuario,
			String tipoAnulacion) {
		super();
		this.idPedido = idPedido;
		this.fecha = fecha;
		this.producto = producto;
		this.valor = valor;
		this.usuario = usuario;
		this.tipoAnulacion = tipoAnulacion;
	}
	
	
	

}
