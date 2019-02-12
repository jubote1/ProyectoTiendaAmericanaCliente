package capaModelo;

public class ItemInventario {
	
	
	private int idItem;
	private String nombreItem;
	private String unidadMedida;
	private double cantidad;
	private String manejaCanastas;
	private String cantidadCanasta;
	private String nombreContenedor;
	private String categoria;
	private boolean varianzaResumida;
	
	
	public ItemInventario(int idItem, String nombreItem, String unidadMedida, double cantidad, String manejaCanastas,
			String cantidadCanasta, String nombreContenedor, String categoria, boolean varianzaResumida) {
		super();
		this.idItem = idItem;
		this.nombreItem = nombreItem;
		this.unidadMedida = unidadMedida;
		this.cantidad = cantidad;
		this.manejaCanastas = manejaCanastas;
		this.cantidadCanasta = cantidadCanasta;
		this.nombreContenedor = nombreContenedor;
		this.categoria = categoria;
		this.varianzaResumida = varianzaResumida;
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	public String getManejaCanastas() {
		return manejaCanastas;
	}
	public void setManejaCanastas(String manejaCanastas) {
		this.manejaCanastas = manejaCanastas;
	}
	public String getCantidadCanasta() {
		return cantidadCanasta;
	}
	public void setCantidadCanasta(String cantidadCanasta) {
		this.cantidadCanasta = cantidadCanasta;
	}
	public String getNombreContenedor() {
		return nombreContenedor;
	}
	public void setNombreContenedor(String nombreContenedor) {
		this.nombreContenedor = nombreContenedor;
	}
	public int getIdItem() {
		return idItem;
	}
	public void setIdItem(int idItem) {
		this.idItem = idItem;
	}
	public String getNombreItem() {
		return nombreItem;
	}
	public void setNombreItem(String nombreItem) {
		this.nombreItem = nombreItem;
	}
	public String getUnidadMedida() {
		return unidadMedida;
	}
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}
	
	public String toString()
	{
		return(nombreItem);
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public boolean isVarianzaResumida() {
		return varianzaResumida;
	}
	public void setVarianzaResumida(boolean varianzaResumida) {
		this.varianzaResumida = varianzaResumida;
	}
	
	
	

}
