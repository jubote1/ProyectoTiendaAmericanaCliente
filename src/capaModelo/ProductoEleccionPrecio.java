package capaModelo;

public class ProductoEleccionPrecio {
	
	private int idproducto;
	private int idproductoEleccion;
	private String precio;
	public int getIdproducto() {
		return idproducto;
	}
	public void setIdproducto(int idproducto) {
		this.idproducto = idproducto;
	}
	public int getIdproductoEleccion() {
		return idproductoEleccion;
	}
	public void setIdproductoEleccion(int idproductoEleccion) {
		this.idproductoEleccion = idproductoEleccion;
	}
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public ProductoEleccionPrecio(int idproducto, int idproductoEleccion, String precio) {
		super();
		this.idproducto = idproducto;
		this.idproductoEleccion = idproductoEleccion;
		this.precio = precio;
	}
	
	

}
