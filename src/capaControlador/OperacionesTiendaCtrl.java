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
import capaModelo.DetallePedido;
import capaModelo.Egreso;
import capaModelo.FechaSistema;
import capaModelo.Ingreso;
import capaModelo.Pedido;
import capaModelo.Tienda;
import interfazGrafica.Impresion;

public class OperacionesTiendaCtrl {
	
	private boolean auditoria;
	
	public OperacionesTiendaCtrl(boolean auditoria)
	{
		this.auditoria = auditoria;
	}
	
//APERTURA DEL D�A
	public boolean AvanzarFechaSistemaApertura(String fecha)
	{
		boolean respuesta = TiendaDAO.ActualizarFechaSistemaApertura(fecha, auditoria);
		return(respuesta);
	}


/**
 * M�todo qeu se encarga de retornar la informaci�n de los d�as que se podr�an aperturar en el sistema
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
	//Se realiza resta en d�as para saber la diferencia
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

public boolean validarCierreSemanal()
{
	PedidoCtrl pedCtrl = new PedidoCtrl(auditoria);
	FechaSistema fechasSistema = pedCtrl.obtenerFechasSistema();
	String fechaActual = fechasSistema.getFechaApertura();
	Calendar calendarioActual = Calendar.getInstance();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	try
	{
		//Al objeto calendario le fijamos la fecha actual del sitema
		calendarioActual.setTime(dateFormat.parse(fechaActual));
		
	}catch(Exception e)
	{
		System.out.println(e.toString());
	}
	//Retormanos el d�a de la semana actual segun la fecha del calendario
	int diaActual = calendarioActual.get(Calendar.DAY_OF_WEEK);
	//Domingo
	if(diaActual == 1)
	{
		return(true);
	}
	return(false);
}

//APERTURA DE D�A
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

//APERTURA DEL D�A
public boolean realizarAperturaDia(String fecha)
{
	AvanzarFechaSistemaApertura(fecha);
	realizarInventarioHistorico(fecha);
	return(true);
}

//APERTURA DEL D�A
public void realizarInventarioHistorico(String fecha)
{
	ItemInventarioHistoricoDAO.realizarInventarioHistorico(fecha, auditoria);
}

//FINALIZAR EL D�A

/**
 * M�todo que se encargar� de realizar las validaciones para determinar si se cumplen o no todas las condiciones para realizar
 * cierre de la fecha determinada
 * @param fecha Se recibe como par�metro la fecha indicada del cierre
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
			respuesta = respuesta + "NO SE HA INGRESADO LA VARIANZA para el d�a en cuesti�n.";
		}
		return(respuesta);
	}

/**
 * M�todo principal en la capa de Negocio que se encarga de coordinar el cierre de d�a para un d�a determinado.
 * @param fecha Se recibe la fecha a partir de la cual se realizar� el cierre.
 * @return se recibe un String con el resultado del proceso.
 */
	public String finalizarDia(String fecha)
	{
		String respuesta = validacionesPreCierre(fecha);
		if(respuesta.equals(new String("")))
		{
			//Avanzar en uno el �ltimo d�a de cierre
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

//FINALIZAR EL D�A
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
		//Validar si no hay numeraci�n de facturas en el rango dado para la facturaci�n
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
		egrIns.setIdEgreso(idIns);
		String comandaIngreso = generarStrImpresionEgreso(egrIns);
		Impresion.main(comandaIngreso);
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
	
	public String generarStrImpresionEgreso(Egreso egresoImp)
	{
		
		//Obtenemos la tienda sobre la que estamos trabajando
		Date fechaActual = new Date();                                                                                      
		String ingreso = "======================================\n" 
				+ "    EGRESO GENERADO:"+  egresoImp.getIdEgreso() +"\n"
	            + " " + fechaActual.toString()
	            + "    ======================================\n"
	            + "    Valor Egreso: " + egresoImp.getValorEgreso() + "   \n"
	            + "    Observaci�n: " + egresoImp.getDescripcion() + "   \n"
	            + "    ======================================\n"
	            + "\n\n\n\n\n\n\n           ";
		return(ingreso);
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
		ingIns.setIdIngreso(idIns);
		String comandaIngreso = generarStrImpresionIngreso(ingIns);
		Impresion.main(comandaIngreso);
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
	
	public String generarStrImpresionIngreso(Ingreso ingresoImp)
	{
		
		//Obtenemos la tienda sobre la que estamos trabajando
		Date fechaActual = new Date();
		String ingreso = "======================================\n" 
				+ "    INGRESO GENERADO:"+  ingresoImp.getIdIngreso() +"\n"
	            + " " + fechaActual.toString()
	            + "    ======================================\n"
	            + "    Valor Ingreso: " + ingresoImp.getValorIngreso() + "   \n"
	            + "    Observaci�n: " + ingresoImp.getDescripcion() + "   \n"
	            + "    ======================================\n"
	            + "\n\n\n\n\n\n\n           ";
		return(ingreso);
	}
	
}
