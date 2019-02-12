package capaModelo;

public class Estado {
	
	private int idestado;
	private String descripcion;
	private String descripcionCorta;
	private int idTipoPedido;
	private String tipoPedido;
	private boolean estadoInicial;
	private boolean estadoFinal;
	private boolean rutaDomicilio;
	private boolean entregaDomicilio;
	private int colorr;
	private int colorg;
	private int colorb;
	private boolean impresion;
	private byte[] imagen;
	
	
	
	public boolean isRutaDomicilio() {
		return rutaDomicilio;
	}
	public void setRutaDomicilio(boolean rutaDomicilio) {
		this.rutaDomicilio = rutaDomicilio;
	}
	public boolean isEntregaDomicilio() {
		return entregaDomicilio;
	}
	public void setEntregaDomicilio(boolean entregaDomicilio) {
		this.entregaDomicilio = entregaDomicilio;
	}
	public byte[] getImagen() {
		return imagen;
	}
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
	public boolean isImpresion() {
		return impresion;
	}
	public void setImpresion(boolean impresion) {
		this.impresion = impresion;
	}
	public int getColorr() {
		return colorr;
	}
	public void setColorr(int colorr) {
		this.colorr = colorr;
	}
	public int getColorg() {
		return colorg;
	}
	public void setColorg(int colorg) {
		this.colorg = colorg;
	}
	public int getColorb() {
		return colorb;
	}
	public void setColorb(int colorb) {
		this.colorb = colorb;
	}
	public int getIdestado() {
		return idestado;
	}
	public void setIdestado(int idestado) {
		this.idestado = idestado;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDescripcionCorta() {
		return descripcionCorta;
	}
	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}
	
	
	
	
	
	public boolean isEstadoInicial() {
		return estadoInicial;
	}
	public void setEstadoInicial(boolean estadoInicial) {
		this.estadoInicial = estadoInicial;
	}
	public boolean isEstadoFinal() {
		return estadoFinal;
	}
	public void setEstadoFinal(boolean estadoFinal) {
		this.estadoFinal = estadoFinal;
	}
	public String getTipoPedido() {
		return tipoPedido;
	}
	public void setTipoPedido(String tipoPedido) {
		this.tipoPedido = tipoPedido;
	}
	public int getIdTipoPedido() {
		return idTipoPedido;
	}
	public void setIdTipoPedido(int idTipoPedido) {
		this.idTipoPedido = idTipoPedido;
	}
	public Estado()
	{
		
	}
	
	
	
	public Estado(int idestado, String descripcion, String descripcionCorta, int idTipoPedido, String tipoPedido,
			boolean estadoInicial, boolean estadoFinal, int colorr, int colorg, int colorb, boolean impresion, boolean rutaDomicilio, boolean entregaDomicilio) {
		super();
		this.idestado = idestado;
		this.descripcion = descripcion;
		this.descripcionCorta = descripcionCorta;
		this.idTipoPedido = idTipoPedido;
		this.tipoPedido = tipoPedido;
		this.estadoInicial = estadoInicial;
		this.estadoFinal = estadoFinal;
		this.colorr = colorr;
		this.colorg = colorg;
		this.colorb = colorb;
		this.impresion = impresion;
		this.rutaDomicilio = rutaDomicilio;
		this.entregaDomicilio = entregaDomicilio;
	}

	
	
}
