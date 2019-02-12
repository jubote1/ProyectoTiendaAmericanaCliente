package capaModelo;

public class ConfiguracionMesa {
	
	private int idConfiguracion;
	private int fila;
	private int columna;
	private int mesa;
	public int getIdConfiguracion() {
		return idConfiguracion;
	}
	public void setIdConfiguracion(int idConfiguracion) {
		this.idConfiguracion = idConfiguracion;
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
	public int getMesa() {
		return mesa;
	}
	public void setMesa(int mesa) {
		this.mesa = mesa;
	}
	public ConfiguracionMesa(int idConfiguracion, int fila, int columna, int mesa) {
		super();
		this.idConfiguracion = idConfiguracion;
		this.fila = fila;
		this.columna = columna;
		this.mesa = mesa;
	}
	
		
	

}
