package capaModelo;

public class DetallePedido {
	
	
	private int idDetallePedido;
	private int idPedidoTienda;
	private int idProducto;
	private double cantidad;
	private double valorUnitario;
	private double valorTotal;
	private String observacion;
	private int idDetallePedidoMaster;
	private String descripcioProducto;
	private String descCortaProducto;
	private String tipoProducto;
	private String tamano;
	private int idDetalleModificador;
	private String descargoInventario;
	private String estado;
	private int contadorDetallePedido;

	public int getContadorDetallePedido() {
		return contadorDetallePedido;
	}
	public void setContadorDetallePedido(int contadorDetallePedido) {
		this.contadorDetallePedido = contadorDetallePedido;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getDescargoInventario() {
		return descargoInventario;
	}
	public void setDescargoInventario(String descargoInventario) {
		this.descargoInventario = descargoInventario;
	}
	public int getIdDetallePedido() {
		return idDetallePedido;
	}
	public void setIdDetallePedido(int idDetallePedido) {
		this.idDetallePedido = idDetallePedido;
	}
	public int getIdPedidoTienda() {
		return idPedidoTienda;
	}
	public void setIdPedidoTienda(int idPedidoTienda) {
		this.idPedidoTienda = idPedidoTienda;
	}
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	public double getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	
	public int getIdDetallePedidoMaster() {
		return idDetallePedidoMaster;
	}
	public void setIdDetallePedidoMaster(int idDetallePedidoMaster) {
		this.idDetallePedidoMaster = idDetallePedidoMaster;
	}
	
	
	public String getDescripcioProducto() {
		return descripcioProducto;
	}
	public void setDescripcioProducto(String descripcioProducto) {
		this.descripcioProducto = descripcioProducto;
	}
	public String getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	
	public String getTamano() {
		return tamano;
	}
	public void setTamano(String tamano) {
		this.tamano = tamano;
	}
	
	
	
	public String getDescCortaProducto() {
		return descCortaProducto;
	}
	public void setDescCortaProducto(String descCortaProducto) {
		this.descCortaProducto = descCortaProducto;
	}
	
	
	public int getIdDetalleModificador() {
		return idDetalleModificador;
	}
	public void setIdDetalleModificador(int idDetalleModificador) {
		this.idDetalleModificador = idDetalleModificador;
	}
	
	public DetallePedido(int idDetallePedido, int idPedidoTienda, int idProducto, double cantidad, double valorUnitario,
			double valorTotal, String observacion, int idDetallePedidoMaster, String descargoInventario, String estado, int contadorDetallePedido) {
		super();
		this.idDetallePedido = idDetallePedido;
		this.idPedidoTienda = idPedidoTienda;
		this.idProducto = idProducto;
		this.cantidad = cantidad;
		this.valorUnitario = valorUnitario;
		this.valorTotal = valorTotal;
		this.observacion = observacion;
		this.idDetallePedidoMaster = idDetallePedidoMaster;
		this.descargoInventario = descargoInventario;
		this.idDetalleModificador = 0;
		this.estado = estado;
		this.contadorDetallePedido = contadorDetallePedido;
	}
	public DetallePedido(int idDetallePedido, int idPedidoTienda, int idProducto, double cantidad, double valorUnitario,
			double valorTotal, String observacion, int idDetallePedidoMaster, String descripcioProducto,
			String tipoProducto, String tamano, String descCortaProducto) {
		super();
		this.idDetallePedido = idDetallePedido;
		this.idPedidoTienda = idPedidoTienda;
		this.idProducto = idProducto;
		this.cantidad = cantidad;
		this.valorUnitario = valorUnitario;
		this.valorTotal = valorTotal;
		this.observacion = observacion;
		this.idDetallePedidoMaster = idDetallePedidoMaster;
		this.descripcioProducto = descripcioProducto;
		this.tipoProducto = tipoProducto;
		this.tamano = tamano;
		this.descCortaProducto = descCortaProducto;
		this.idDetalleModificador = 0;
	}

	public DetallePedido()
	{
		
	}
	
	

}
