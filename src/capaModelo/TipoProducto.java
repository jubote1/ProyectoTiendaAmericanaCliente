package capaModelo;

public class TipoProducto {
	
	private String tipoProducto;
	private String descripcionTipo;
	public String getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	public String getDescripcionTipo() {
		return descripcionTipo;
	}
	public void setDescripcionTipo(String descripcionTipo) {
		this.descripcionTipo = descripcionTipo;
	}
	public TipoProducto(String tipoProducto, String descripcionTipo) {
		super();
		this.tipoProducto = tipoProducto;
		this.descripcionTipo = descripcionTipo;
	}
	
	
	public String toString()
	{
		return(descripcionTipo);
	}

}
