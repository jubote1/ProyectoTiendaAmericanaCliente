package capaModelo;

/**
 * Clase que implementa la entidad Forma de Pago.
 * @author JuanDavid
 *
 */
public class FormaPago {

	
	private int idformapago;
	private String nombre;
	private String tipoformapago;
	
	
	
	
	
	public int getIdformapago() {
		return idformapago;
	}
	public void setIdformapago(int idformapago) {
		this.idformapago = idformapago;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipoforma() {
		return tipoformapago;
	}
	public void setTipoforma(String tipoforma) {
		this.tipoformapago = tipoforma;
	}
	
	
	
	public FormaPago(int idformapago, String nombre, String tipoformapago) {
		super();
		this.idformapago = idformapago;
		this.nombre = nombre;
		this.tipoformapago = tipoformapago;
	}
	public FormaPago() {
		// TODO Auto-generated constructor stub
	}
	
	
	
}
