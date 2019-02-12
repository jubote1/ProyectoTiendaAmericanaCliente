package capaModelo;

public class MotivoAnulacionPedido {
	
	private int idMotivoAnulacion;
	private String descripcion;
	private String descuentaInventario;
	public int getIdMotivoAnulacion() {
		return idMotivoAnulacion;
	}
	public void setIdMotivoAnulacion(int idMotivoAnulacion) {
		this.idMotivoAnulacion = idMotivoAnulacion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDescuentaInventario() {
		return descuentaInventario;
	}
	public void setDescuentaInventario(String descuentaInventario) {
		this.descuentaInventario = descuentaInventario;
	}
	public MotivoAnulacionPedido(int idMotivoAnulacion, String descripcion, String descuentaInventario) {
		super();
		this.idMotivoAnulacion = idMotivoAnulacion;
		this.descripcion = descripcion;
		this.descuentaInventario = descuentaInventario;
	}
	
	public String toString()
	{
		return(descripcion);
	}
	

}
