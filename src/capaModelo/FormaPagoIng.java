package capaModelo;

public class FormaPagoIng
{
	 
	private int idFormaPago;
	private String nombreFormaPago;
	private double valorPago;
	private boolean seleccionado;
	public int getIdFormaPago() {
		return idFormaPago;
	}
	public void setIdFormaPago(int idFormaPago) {
		this.idFormaPago = idFormaPago;
	}
	public double getValorPago() {
		return valorPago;
	}
	public void setValorPago(double valorPago) {
		this.valorPago = valorPago;
	}
	public boolean isSeleccionado() {
		return seleccionado;
	}
	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}
	public String getNombreFormaPago() {
		return nombreFormaPago;
	}
	public void setNombreFormaPago(String nombreFormaPago) {
		this.nombreFormaPago = nombreFormaPago;
	}
	public FormaPagoIng(int idFormaPago, double valorPago, boolean seleccionado, String nombreFormaPago) {
		super();
		this.idFormaPago = idFormaPago;
		this.valorPago = valorPago;
		this.seleccionado = seleccionado;
		this.nombreFormaPago = nombreFormaPago;
	}
	
	
}
