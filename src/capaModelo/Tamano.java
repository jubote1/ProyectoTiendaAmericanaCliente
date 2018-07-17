package capaModelo;

public class Tamano {
	
	private String tamano;
	private String descripcionTamano;
	
	
	
	
	public String getTamano() {
		return tamano;
	}




	public void setTamano(String tamano) {
		this.tamano = tamano;
	}




	public String getDescripcionTamano() {
		return descripcionTamano;
	}




	public void setDescripcionTamano(String descripcionTamano) {
		this.descripcionTamano = descripcionTamano;
	}


	


	public Tamano(String tamano, String descripcionTamano) {
		super();
		this.tamano = tamano;
		this.descripcionTamano = descripcionTamano;
	}




	public String toString()
	{
		return(descripcionTamano);
	}

}
