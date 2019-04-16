package capaModelo;

/**
 * Clase que implementa la entidad pedido.
 * @author JuanDavid
 *
 */
public class Pedido {
	
	private int idpedidotienda;
	private int idtienda;
	private double valorbruto;
	private double impuesto;
	private double valorneto;
	private int idcliente;
	private String nombreCliente;
	private String dirCliente;
	private String fechapedido;
	private int idpedidocontact;
	private String fechainsercion;
	private String usuariopedido;
	private int tiempopedido;
	private int idTipoPedido;
	private String tipoPedido;
	private int idMotivoAnulacion;
	private String telefono;
	private String zona;
	private String observacion;
	private double cambio;
	private double totalFormaPago;
	private String nombreFormaPago;
	private String domiciliario;
	private String nombreCompania;
	
	
		
	public String getNombreCompania() {
		return nombreCompania;
	}
	public void setNombreCompania(String nombreCompania) {
		this.nombreCompania = nombreCompania;
	}
	public String getDomiciliario() {
		return domiciliario;
	}
	public void setDomiciliario(String domiciliario) {
		this.domiciliario = domiciliario;
	}
	public double getTotalFormaPago() {
		return totalFormaPago;
	}
	public void setTotalFormaPago(double totalFormaPago) {
		this.totalFormaPago = totalFormaPago;
	}
	public String getNombreFormaPago() {
		return nombreFormaPago;
	}
	public void setNombreFormaPago(String nombreFormaPago) {
		this.nombreFormaPago = nombreFormaPago;
	}
	public double getCambio() {
		return cambio;
	}
	public void setCambio(double cambio) {
		this.cambio = cambio;
	}
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public int getIdMotivoAnulacion() {
		return idMotivoAnulacion;
	}
	public void setIdMotivoAnulacion(int idMotivoAnulacion) {
		this.idMotivoAnulacion = idMotivoAnulacion;
	}
	public String getDirCliente() {
		return dirCliente;
	}
	public void setDirCliente(String dirCliente) {
		this.dirCliente = dirCliente;
	}
	public String getTipoPedido() {
		return tipoPedido;
	}
	public void setTipoPedido(String tipoPedido) {
		this.tipoPedido = tipoPedido;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public int getIdTipoPedido() {
		return idTipoPedido;
	}
	public void setIdTipoPedido(int idTipoPedido) {
		this.idTipoPedido = idTipoPedido;
	}
	public int getIdpedidotienda() {
		return idpedidotienda;
	}
	public void setIdpedidotienda(int idpedidotienda) {
		this.idpedidotienda = idpedidotienda;
	}
	public int getIdtienda() {
		return idtienda;
	}
	public void setIdtienda(int idtienda) {
		this.idtienda = idtienda;
	}
	public double getValorbruto() {
		return valorbruto;
	}
	public void setValorbruto(double valorbruto) {
		this.valorbruto = valorbruto;
	}
	public double getImpuesto() {
		return impuesto;
	}
	public void setImpuesto(double impuesto) {
		this.impuesto = impuesto;
	}
	public double getValorneto() {
		return valorneto;
	}
	public void setValorneto(double valorneto) {
		this.valorneto = valorneto;
	}
	public int getIdcliente() {
		return idcliente;
	}
	public void setIdcliente(int idcliente) {
		this.idcliente = idcliente;
	}
	public String getFechapedido() {
		return fechapedido;
	}
	public void setFechapedido(String fechapedido) {
		this.fechapedido = fechapedido;
	}
	public int getIdpedidocontact() {
		return idpedidocontact;
	}
	public void setIdpedidocontact(int idpedidocontact) {
		this.idpedidocontact = idpedidocontact;
	}
	public String getFechainsercion() {
		return fechainsercion;
	}
	public void setFechainsercion(String fechainsercion) {
		this.fechainsercion = fechainsercion;
	}
	public String getUsuariopedido() {
		return usuariopedido;
	}
	public void setUsuariopedido(String usuariopedido) {
		this.usuariopedido = usuariopedido;
	}
	public int getTiempopedido() {
		return tiempopedido;
	}
	public void setTiempopedido(int tiempopedido) {
		this.tiempopedido = tiempopedido;
	}
	public Pedido(int idpedidotienda, int idtienda, double valorbruto, double impuesto, double valorneto, int idcliente,
			String fechapedido, int idpedidocontact, String fechainsercion, String usuariopedido, int tiempopedido, int idTipoPedido) {
		super();
		this.idpedidotienda = idpedidotienda;
		this.idtienda = idtienda;
		this.valorbruto = valorbruto;
		this.impuesto = impuesto;
		this.valorneto = valorneto;
		this.idcliente = idcliente;
		this.fechapedido = fechapedido;
		this.idpedidocontact = idpedidocontact;
		this.fechainsercion = fechainsercion;
		this.usuariopedido = usuariopedido;
		this.tiempopedido = tiempopedido;
		this.idTipoPedido = idTipoPedido;
	}
	
	public Pedido()
	{
		
	}
	
	
	
	
	
	

}
