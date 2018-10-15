package capaModelo;

import javax.swing.JButton;

public class EleccionForzadaBoton {
	
	private JButton boton;
	private int numPregunta;
	private int division;
	private int idProducto;
	private String descProducto;
	
	
	
	public String getDescProducto() {
		return descProducto;
	}
	public void setDescProducto(String descProducto) {
		this.descProducto = descProducto;
	}
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public int getDivision() {
		return division;
	}
	public void setDivision(int division) {
		this.division = division;
	}
	public JButton getBoton() {
		return boton;
	}
	public void setBoton(JButton boton) {
		this.boton = boton;
	}
	public int getNumPregunta() {
		return numPregunta;
	}
	public void setNumPregunta(int numPregunta) {
		this.numPregunta = numPregunta;
	}
	public EleccionForzadaBoton(JButton boton, int numPregunta, int division, int idProducto, String descProducto) {
		super();
		this.boton = boton;
		this.numPregunta = numPregunta;
		this.division = division;
		this.idProducto = idProducto;
		this.descProducto = descProducto;
	}
	
	

	
	

}
