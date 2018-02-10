package capaModelo;

public class MenuAgrupador {
	
	private int idmenuagrupador;
	private String menuAgrupador;
	private String descripcion;
	public int getIdmenuagrupador() {
		return idmenuagrupador;
	}
	public void setIdmenuagrupador(int idmenuagrupador) {
		this.idmenuagrupador = idmenuagrupador;
	}
	public String getMenuAgrupador() {
		return menuAgrupador;
	}
	public void setMenuAgrupador(String menuAgrupador) {
		this.menuAgrupador = menuAgrupador;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public MenuAgrupador(int idmenuagrupador, String menuAgrupador, String descripcion) {
		super();
		this.idmenuagrupador = idmenuagrupador;
		this.menuAgrupador = menuAgrupador;
		this.descripcion = descripcion;
	}
	
	
	
	

}
