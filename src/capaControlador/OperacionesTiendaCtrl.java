package capaControlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import capaDAO.EgresoDAO;
import capaDAO.IngresoDAO;
import capaDAO.ItemInventarioHistoricoDAO;
import capaDAO.ModificadorInventarioDAO;
import capaDAO.PedidoDAO;
import capaDAO.TiendaDAO;
import capaModelo.Egreso;
import capaModelo.FechaSistema;
import capaModelo.Ingreso;
import capaModelo.Tienda;

public class OperacionesTiendaCtrl {
	
	private boolean auditoria;
	
	public OperacionesTiendaCtrl(boolean auditoria)
	{
		this.auditoria = auditoria;
	}
	
//APERTURA DEL DÍA
	public boolean AvanzarFechaSistemaApertura(String fecha)
	{
		boolean respuesta = TiendaDAO.ActualizarFechaSistemaApertura(fecha, auditoria);
		return(respuesta);
	}


/**
 * Método qeu se encarga de retornar la información de los días que se podrían aperturar en el sistema
 * @return
 */
public String validarEstadoFechaSistema()
{
	PedidoCtrl pedCtrl = new PedidoCtrl(auditoria);
	FechaSistema fechasSistema = pedCtrl.obtenerFechasSistema();
	String fechaPOS = fechasSistema.getFechaApertura();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Date fechaHoy = new Date();
	Date fechaPosDate = new Date();
	try {
		fechaPosDate = sdf.parse(fechaPOS);
		//NO es necesario ya fechaHoy tiene la fecha actual
		fechaHoy = sdf.parse(sdf.format(new Date()));
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//Se realiza resta en días para saber la diferencia
	int dias =Math.abs((int) (fechaHoy.getTime() - fechaPosDate.getTime())/86400000);
	
	if(dias >= 2)
	{
		return(sdf.format(fechaHoy));
	}
	else
	{
		return("");
	}
}

//APERTURA DE DÍA
public String aumentarFecha(String fecha)
{
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Date fechaDate = new Date();
	try {
		fechaDate = sdf.parse(fecha);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(fechaDate); 
	calendar.add(Calendar.DAY_OF_YEAR, 1);
	fechaDate = calendar.getTime();
	return(sdf.format(fechaDate));
}

//APERTURA DEL DÍA
public boolean realizarAperturaDia(String fecha)
{
	AvanzarFechaSistemaApertura(fecha);
	realizarInventarioHistorico(fecha);
	return(true);
}

//APERTURA DEL DÍA
public void realizarInventarioHistorico(String fecha)
{
	ItemInventarioHistoricoDAO.realizarInventarioHistorico(fecha, auditoria);
}

//FINALIZAR EL DÍA

/**
 * Método que se encargará de realizar las validaciones para determinar si se cumplen o no todas las condiciones para realizar
 * cierre de la fecha determinada
 * @param fecha Se recibe como parámetro la fecha indicada del cierre
 * @return Se retornar un mensaje indicando si el proceso es o no satisfactorio en cuanto a las validaciones
 */
	public String validacionesPreCierre(String fecha)
	{
		String respuesta = "";
		boolean respValEstPed = PedidoDAO.validarEstadosFinalesPedido(fecha, auditoria);
		if (respValEstPed)
		{
			respuesta = respuesta + "RESULTADO NO EXITOSO DEL PROCESO  Se tienen pedidos con estados no finales.";
		}
		boolean respInvVarianza = ModificadorInventarioDAO.seIngresoVarianza(fecha, auditoria);
		if (!respInvVarianza)
		{
			respuesta = respuesta + "NO SE HA INGRESADO LA VARIANZA para el día en cuestión.";
		}
		return(respuesta);
	}

/**
 * Método principal en la capa de Negocio que se encarga de coordinar el cierre de día para un día determinado.
 * @param fecha Se recibe la fecha a partir de la cual se realizará el cierre.
 * @return se recibe un String con el resultado del proceso.
 */
	public String finalizarDia(String fecha)
	{
		String respuesta = validacionesPreCierre(fecha);
		if(respuesta.equals(new String("")))
		{
			//Avanzar en uno el último día de cierre
			boolean respAvanFech = AvanzarFechaUltimoCierre();
			if(respAvanFech)
			{
				respuesta = "PROCESO EXITOSO";
			}
			else
			{
				respuesta = respuesta + " Se tuvieron inconvenientes al momento de aumentar la fecha en el sistema";
			}
			
		}
		return(respuesta);
	}

//FINALIZAR EL DÍA
	public boolean AvanzarFechaUltimoCierre()
	{
		boolean respuesta = TiendaDAO.actualizarFechaUltimoCierre(auditoria);
		return(respuesta);
	}

	public Tienda obtenerTienda()
	{
		Tienda tienda = TiendaDAO.obtenerTienda(auditoria);
		return(tienda);
	}
	
	public boolean actualizarTienda(Tienda tienda)
	{
		boolean respuesta = TiendaDAO.actualizarTienda(tienda, auditoria);
		return(respuesta);
	}
	
	public String actualizarNumResolucion(int numInicial, int numFinal)
	{
		//Validar si no hay numeración de facturas en el rango dado para la facturación
		String respuesta = TiendaDAO.verificarNumResolucion( numInicial, numFinal, auditoria);
		if(respuesta.equals(new String("OK")))
		{
			respuesta = TiendaDAO.actualizarResolucionTienda(numInicial, numFinal, auditoria);
		}
		return(respuesta);
	}
	
	public int obtenerIdTienda()
	{
		int idTienda = TiendaDAO.obtenerIdTienda(auditoria);
		return(idTienda);
	}

	
	// Entidad Egreso
	
	public ArrayList obtenerEgresos(String fecha)
	{
		ArrayList egresos = EgresoDAO.obtenerEgresos(fecha, auditoria);
		return(egresos);
		
	}
	
	public ArrayList<Egreso> obtenerEgresosObj(String fecha)
	{
		ArrayList<Egreso> egresos =  EgresoDAO.obtenerEgresosObj(fecha, auditoria);
		return(egresos);
		
	}
	
	public int insertarEgreso(Egreso egrIns)
	{
		int idIns = EgresoDAO.insertarEgreso(egrIns, auditoria);
		return(idIns);
	}
	
	public boolean eliminarEgreso(int idEgreso)
	{
		boolean respuesta = EgresoDAO.eliminarEgreso(idEgreso, auditoria);
		return(respuesta);
	}
	
	public boolean editarEgreso(Egreso egr)
	{
		boolean respuesta =  EgresoDAO.editarEgreso(egr, auditoria);
		return(respuesta);
	}
	
	public double TotalizarEgreso(String fech)
	{
		double respuesta = EgresoDAO.TotalizarEgreso(fech, auditoria);
		return(respuesta);
	}
	
	//Entidad Ingreso
	
	public ArrayList obtenerIngresos(String fecha)
	{
		ArrayList ingresos = IngresoDAO.obtenerIngresos(fecha, auditoria);
		return(ingresos);
		
	}
	
	public ArrayList<Ingreso> obtenerIngresosObj(String fecha)
	{
		ArrayList<Ingreso> ingresos =  IngresoDAO.obtenerIngresosObj(fecha, auditoria);
		return(ingresos);
		
	}
	
	public int insertarIngreso(Ingreso ingIns)
	{
		int idIns = IngresoDAO.insertarIngreso(ingIns, auditoria);
		return(idIns);
	}
	
	public boolean eliminarIngreso(int idIngreso)
	{
		boolean respuesta = IngresoDAO.eliminarIngreso(idIngreso, auditoria);
		return(respuesta);
	}
	
	public boolean editarIngreso(Ingreso ing)
	{
		boolean respuesta =  IngresoDAO.editarIngreso(ing, auditoria);
		return(respuesta);
	}
	
	public double TotalizarIngreso(String fech)
	{
		double respuesta = IngresoDAO.TotalizarIngreso(fech, auditoria);
		return(respuesta);
	}
	
	
}
