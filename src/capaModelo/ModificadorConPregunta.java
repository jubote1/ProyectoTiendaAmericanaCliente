package capaModelo;

public class ModificadorConPregunta {
	
	private int idProductoPadre;
	private int numeroPregunta;
	private int mitad;
	private int idProductoMod;
	private double precio;
	private double cantidad;
	public int getIdProductoPadre() {
		return idProductoPadre;
	}
	public void setIdProductoPadre(int idProductoPadre) {
		this.idProductoPadre = idProductoPadre;
	}
	public int getNumeroPregunta() {
		return numeroPregunta;
	}
	public void setNumeroPregunta(int numeroPregunta) {
		this.numeroPregunta = numeroPregunta;
	}
	public int getMitad() {
		return mitad;
	}
	public void setMitad(int mitad) {
		this.mitad = mitad;
	}
	public int getIdProductoMod() {
		return idProductoMod;
	}
	public void setIdProductoMod(int idProductoMod) {
		this.idProductoMod = idProductoMod;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	public ModificadorConPregunta(int idProductoPadre, int numeroPregunta, int mitad, int idProductoMod, double precio,
			double cantidad) {
		super();
		this.idProductoPadre = idProductoPadre;
		this.numeroPregunta = numeroPregunta;
		this.mitad = mitad;
		this.idProductoMod = idProductoMod;
		this.precio = precio;
		this.cantidad = cantidad;
	}
	
	public ModificadorConPregunta()
	{
		
	}

}
