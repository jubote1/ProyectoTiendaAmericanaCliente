package capaModelo;

import javax.swing.JButton;

public class EleccionForzadaTemporal {
	
	private JButton boton;
	private double precioProducto;
	private double cantidad;
	private int idProducto;
	private int numeroPregunta;
	private int numeroMitad;
	
	
	
	public int getNumeroMitad() {
		return numeroMitad;
	}
	public void setNumeroMitad(int numeroMitad) {
		this.numeroMitad = numeroMitad;
	}
	public int getNumeroPregunta() {
		return numeroPregunta;
	}
	public void setNumeroPregunta(int numeroPregunta) {
		this.numeroPregunta = numeroPregunta;
	}
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public JButton getBoton() {
		return boton;
	}
	public void setBoton(JButton boton) {
		this.boton = boton;
	}
	public double getPrecioProducto() {
		return precioProducto;
	}
	public void setPrecioProducto(double precioProducto) {
		this.precioProducto = precioProducto;
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	public EleccionForzadaTemporal() {
		super();
	}
	
	

}
