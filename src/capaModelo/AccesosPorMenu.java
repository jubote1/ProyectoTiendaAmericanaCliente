package capaModelo;

public class AccesosPorMenu {
	
	private int idAccesoMenu;
	private int idTipoEmpleado;
	private int idAgrupadorMenu;
	
	public AccesosPorMenu(int idAccesoMenu, int idTipoEmpleado, int idAgrupadorMenu) {
		super();
		this.idAccesoMenu = idAccesoMenu;
		this.idTipoEmpleado = idTipoEmpleado;
		this.idAgrupadorMenu = idAgrupadorMenu;
	}

	public int getIdAccesoMenu() {
		return idAccesoMenu;
	}

	public void setIdAccesoMenu(int idAccesoMenu) {
		this.idAccesoMenu = idAccesoMenu;
	}

	public int getidTipoEmpleado() {
		return idTipoEmpleado;
	}

	public void setidTipoEmpleado(int idTipoEmpleado) {
		this.idTipoEmpleado = idTipoEmpleado;
	}

	public int getIdAgrupadorMenu() {
		return idAgrupadorMenu;
	}

	public void setIdAgrupadorMenu(int idAgrupadorMenu) {
		this.idAgrupadorMenu = idAgrupadorMenu;
	}
	
	

}
