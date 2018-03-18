package capaModelo;

public class ConfiguracionMenu {
	
	private int idConfiguracionMenu;
	private int multimenu;
	private int menu;
	private int fila;
	private int columna;
	private int idProducto;
	public int getIdConfiguracionMenu() {
		return idConfiguracionMenu;
	}
	public void setIdConfiguracionMenu(int idConfiguracionMenu) {
		this.idConfiguracionMenu = idConfiguracionMenu;
	}
	public int getMultimenu() {
		return multimenu;
	}
	public void setMultimenu(int multimenu) {
		this.multimenu = multimenu;
	}
	public int getMenu() {
		return menu;
	}
	public void setMenu(int menu) {
		this.menu = menu;
	}
	public int getFila() {
		return fila;
	}
	public void setFila(int fila) {
		this.fila = fila;
	}
	public int getColumna() {
		return columna;
	}
	public void setColumna(int columna) {
		this.columna = columna;
	}
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public ConfiguracionMenu(int idConfiguracionMenu, int multimenu, int menu, int fila, int columna, int idProducto) {
		super();
		this.idConfiguracionMenu = idConfiguracionMenu;
		this.multimenu = multimenu;
		this.menu = menu;
		this.fila = fila;
		this.columna = columna;
		this.idProducto = idProducto;
	}
	
	
	
	

}
