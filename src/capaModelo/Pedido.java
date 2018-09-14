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
