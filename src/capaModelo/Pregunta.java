package capaModelo;

public class Pregunta {
	
	private int idPregunta;
	private String tituloPregunta;
	private int obligaEleccion;
	private int numeroMaximoEleccion;
	private int estado;
	private int permiteDividir;
	private String descripcion;
	
	
	public int getPermiteDividir() {
		return permiteDividir;
	}
	public void setPermiteDividir(int permiteDividir) {
		this.permiteDividir = permiteDividir;
	}
	public int getIdPregunta() {
		return idPregunta;
	}
	public void setIdPregunta(int idPregunta) {
		this.idPregunta = idPregunta;
	}
	public String getTituloPregunta() {
		return tituloPregunta;
	}
	public void setTituloPregunta(String tituloPregunta) {
		this.tituloPregunta = tituloPregunta;
	}
	public int getObligaEleccion() {
		return obligaEleccion;
	}
	public void setObligaEleccion(int obligaEleccion) {
		this.obligaEleccion = obligaEleccion;
	}
	public int getNumeroMaximoEleccion() {
		return numeroMaximoEleccion;
	}
	public void setNumeroMaximoEleccion(int numeroMaximoEleccion) {
		this.numeroMaximoEleccion = numeroMaximoEleccion;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Pregunta(int idPregunta, String tituloPregunta, int obligaEleccion, int numeroMaximoEleccion, int estado,
			int permiteDividir, String descripcion) {
		super();
		this.idPregunta = idPregunta;
		this.tituloPregunta = tituloPregunta;
		this.obligaEleccion = obligaEleccion;
		this.numeroMaximoEleccion = numeroMaximoEleccion;
		this.estado = estado;
		this.permiteDividir = permiteDividir;
		this.descripcion = descripcion;
	}
	
	
	
	

}
