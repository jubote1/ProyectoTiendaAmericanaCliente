package capaModelo;

public class ItemInventario {
	
	public ItemInventario(int idItem, String nombreItem, String unidadMedida) {
		super();
		this.idItem = idItem;
		this.nombreItem = nombreItem;
		this.unidadMedida = unidadMedida;
	}
	private int idItem;
	private String nombreItem;
	private String unidadMedida;
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
	
	

}
