package capaModelo;

public class ProductoIncluido {
	
	private int idproducto_incluido;
	private int idproductoincluido;
	private int idproductoincluye;
	private double cantidad;
	private String precio;
	public int getIdproducto_incluido() {
		return idproducto_incluido;
	}
	public void setIdproducto_incluido(int idproducto_incluido) {
		this.idproducto_incluido = idproducto_incluido;
	}
	public int getIdproductoincluido() {
		return idproductoincluido;
	}
	public void setIdproductoincluido(int idproductoincluido) {
		this.idproductoincluido = idproductoincluido;
	}
	public int getIdproductoincluye() {
		return idproductoincluye;
	}
	public void setIdproductoincluye(int idproductoincluye) {
		this.idproductoincluye = idproductoincluye;
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public ProductoIncluido(int idproducto_incluido, int idproductoincluido, int idproductoincluye, double cantidad,
			String precio) {
		super();
		this.idproducto_incluido = idproducto_incluido;
		this.idproductoincluido = idproductoincluido;
		this.idproductoincluye = idproductoincluye;
		this.cantidad = cantidad;
		this.precio = precio;
	}
	
	
	
	

}
