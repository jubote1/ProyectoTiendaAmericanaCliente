package capaControlador;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import capaDAO.ItemInventarioHistoricoDAO;
import capaDAO.PedidoDAO;
import capaDAO.TiendaDAO;
import capaModelo.FechaSistema;
import capaModelo.Tienda;

public class OperacionesTiendaCtrl {
	
//APERTURA DEL D�A
	public boolean AvanzarFechaSistemaApertura(String fecha)
	{
		boolean respuesta = TiendaDAO.ActualizarFechaSistemaApertura(fecha);
		return(respuesta);
	}

//VALIDAR SI EL D�A ESTA ABIERTO O HAY QUE ABRIRLO
public String validarEstadoFechaSistema()
{
	PedidoCtrl pedCtrl = new PedidoCtrl();
	FechaSistema fechasSistema = pedCtrl.obtenerFechasSistema();
	String fechaPOS = fechasSistema.getFechaApertura();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Date fechaHoy = new Date();
	Date fechaPosDate = new Date();
	try {
		fechaPosDate = sdf.parse(fechaPOS);
		fechaHoy = sdf.parse(fechaHoy.toString());
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//Se realiza resta en d�as para saber la diferencia
	int dias =(int) (fechaHoy.getTime() - fechaPosDate.getTime())/86400000;;
	if(dias >= 2)
	{
		return(sdf.format(fechaHoy));
	}
	else
	{
		return("");
	}
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
	ItemInventarioHistoricoDAO.realizarInventarioHistorico(fecha);
}

//FINALIZAR EL D�A

/**
 * M�todo principal en la capa de Negocio que se encarga de coordinar el cierre de d�a para un d�a determinado.
 * @param fecha Se recibe la fecha a partir de la cual se realizar� el cierre.
 * @return se recibe un String con el resultado del proceso.
 */
	public String finalizarDia(String fecha)
	{
		String respuesta = "";
		boolean respValEstPed = PedidoDAO.validarEstadosFinalesPedido(fecha);
		if (respValEstPed)
		{
			respuesta = respuesta + "RESULTADO NO EXITOSO DEL PROCESO  Se tienen pedidos con estados no finales.";
		}
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
		boolean respuesta = TiendaDAO.actualizarFechaUltimoCierre();
		return(respuesta);
	}

	public Tienda obtenerTienda()
	{
		Tienda tienda = TiendaDAO.obtenerTienda();
		return(tienda);
	}
	
	public boolean actualizarTienda(Tienda tienda)
	{
		boolean respuesta = TiendaDAO.actualizarTienda(tienda);
		return(respuesta);
	}
	
	public String actualizarNumResolucion(int numInicial, int numFinal)
	{
		//Validar si no hay numeraci�n de facturas en el rango dado para la facturaci�n
		String respuesta = TiendaDAO.verificarNumResolucion( numInicial, numFinal);
		if(respuesta.equals(new String("OK")))
		{
			respuesta = TiendaDAO.actualizarResolucionTienda(numInicial, numFinal);
		}
		return(respuesta);
	}

}
