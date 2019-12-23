package capaControlador;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import capaConexion.ConexionBaseDatos;
import capaDAO.EgresoDAO;
import capaDAO.GeneralDAO;
import capaDAO.ImprimirAdmDAO;
import capaDAO.IngresoDAO;
import capaDAO.ItemInventarioHistoricoDAO;
import capaDAO.ModificadorInventarioDAO;
import capaDAO.PedidoDAO;
import capaDAO.PorcionesControlDiarioDAO;
import capaDAO.TiendaDAO;
import capaDAO.UsuarioDAO;
import capaModelo.Correo;
import capaModelo.DetallePedido;
import capaModelo.Egreso;
import capaModelo.FechaSistema;
import capaModelo.Ingreso;
import capaModelo.Parametro;
import capaModelo.Pedido;
import capaModelo.PorcionesControlDiario;
import capaModelo.Tienda;
import interfazGrafica.Impresion;
import interfazGrafica.PrincipalLogueo;
import interfazGrafica.Sesion;
import utilidades.ControladorEnvioCorreo;

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
//public String validarEstadoFechaSistema()
//{
//	PedidoCtrl pedCtrl = new PedidoCtrl(auditoria);
//	FechaSistema fechasSistema = pedCtrl.obtenerFechasSistema();
//	String fechaPOS = fechasSistema.getFechaApertura();
//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//	Date fechaHoy = new Date();
//	Date fechaPosDate = new Date();
//	try {
//		fechaPosDate = sdf.parse(fechaPOS);
//		System.out.println("fechaposdate " + fechaPosDate);
//		//NO es necesario ya fechaHoy tiene la fecha actual
//		fechaHoy = sdf.parse(sdf.format(new Date()));
//		System.out.println("fecha hoy"+ fechaHoy);
//	} catch (ParseException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	//Se realiza resta en días para saber la diferencia
//	int dias =Math.abs((int) (fechaHoy.getTime() - fechaPosDate.getTime())/86400000);
//	System.out.println("mostrar días "  + dias );
//	if(dias >= 2)
//	{
//		return(sdf.format(fechaHoy));
//	}
//	else
//	{
//		return("");
//	}
//}
	//22-04-2019 Se simplifica el método ya que simplemente es retornar la fecha actual
	public String validarEstadoFechaSistema()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaHoy = new Date();
		try {
			//NO es necesario ya fechaHoy tiene la fecha actual
			fechaHoy = sdf.parse(sdf.format(new Date()));
			System.out.println("fecha hoy"+ fechaHoy);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return(sdf.format(fechaHoy));
	}
	
	
	

public boolean validarCierreSemanal()
{
	PedidoCtrl pedCtrl = new PedidoCtrl(auditoria);
	FechaSistema fechasSistema = pedCtrl.obtenerFechasSistema();
	String fechaActual = fechasSistema.getFechaUltimoCierre();
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
	//Retormanos el día de la semana actual segun la fecha del calendario
	int diaActual = calendarioActual.get(Calendar.DAY_OF_WEEK);
	//Domingo
	if(diaActual == 1)
	{
		return(true);
	}
	return(false);
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

//reAPERTURA DEL DÍA
public boolean realizarReaperturaDia(String fecha)
{
	//Se fija fecha del sistema 
	AvanzarFechaSistemaApertura(fecha);
	//Se fija fecha de último cierre
	boolean respuesta = disminuirFechaUltimoCierre();
	return(respuesta);
}

public boolean disminuirFechaUltimoCierre()
{
	boolean resultado = TiendaDAO.disminuirFechaUltimoCierre(auditoria);
	return(resultado);
}
//APERTURA DEL DÍA
public boolean realizarAperturaDia(String fecha)
{
	AvanzarFechaSistemaApertura(fecha);
	realizarInventarioHistorico(fecha);
	//Realizamos salida de todos los usuarios 
	UsuarioDAO.darSalidaTodosEmpleados(auditoria);
	return(true);
}

//Método que verifica si el sistema ya fue aperturado a la fecha
public boolean verificarSistemaYaAperturado(String fecha)
{
	boolean sistemaAbierto = ItemInventarioHistoricoDAO.existeItemInventarioHistorico(fecha, auditoria);
	return(sistemaAbierto);
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
		//Obtenemos el estado actual de las porciones con el fin de validar si hay cuentas en caso tal debemos de validar
		// que se haya facturado de la manera correspondiente
		PorcionesControlDiario estActual =  obtenerPorcionesControlDiario(fecha);
		if(estActual.getPorcion() > 0 || estActual.getPorcionGaseosa() > 0 || estActual.getPorcionEmpleado() > 0 || estActual.getPorcionDesecho() > 0 || estActual.getPorcionTemporal() > 0)
		{
			//Si se pasa esta condición es que el dia en cuestión se debería facturar
			boolean estaFacturado =  estaFacturadoPorciones(fecha);
			if(!estaFacturado)
			{
				respuesta = respuesta + " Se tienen porciones en el controlador de Porciones y no se han facturado.";
			}
		}
		return(respuesta);
	}

/**
 * Método principal en la capa de Negocio que se encarga de coordinar el cierre de día para un día determinado.
 * @param fecha Se recibe la fecha a partir de la cual se realizará el cierre.
 * @return se recibe un String con el resultado del proceso.
 */
	public void realizarLimpiezaTemporales(String fecha)
	{
		InventarioCtrl invCtrl = new InventarioCtrl(auditoria);
		invCtrl.limpiarTipoInventariosTemporal(fecha, "I");
		invCtrl.limpiarTipoInventariosTemporal(fecha, "R");
		invCtrl.limpiarTipoInventariosTemporal(fecha, "V");
		//Realizamos limpiado de las tablas temporales de inventario de bodega
		invCtrl.limpiarIngresoInventarioDetalleTmp(fecha);
	}
	
	public String finalizarDia(String fecha)
	{
		String respuesta = validacionesPreCierre(fecha);
		if(respuesta.equals(new String("")))
		{
			//Colocar Fecha base que indica que el sistema está cerradp
			ParametrosCtrl parCtrl = new ParametrosCtrl(PrincipalLogueo.habilitaAuditoria);
			Parametro parametro = parCtrl.obtenerParametro("INDICADORCIERRE");
			String fechaParametro = parametro.getValorTexto();
			boolean respAvanFech = fijarFechaCierreParametro(fechaParametro);
			TiendaDAO.actualizarFechaUltimoCierre(fecha, auditoria);
			//Realizamos la validación de facturación
			validarEstadoNumeracionFacturacion();
			//Realizamos borrado de tablas temporales de ingresos, retiros y varianzas de inventarios
			realizarLimpiezaTemporales(fecha);
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
	public boolean AvanzarFechaUltimoCierre(String fecha)
	{
		boolean respuesta = TiendaDAO.actualizarFechaUltimoCierre(fecha, auditoria);
		return(respuesta);
	}
	
	/**
	 * Método que se encarga de dejar la fecha de sistema el parámetro que indica que el sistema está cerrado
	 * @return
	 */
	public boolean fijarFechaCierreParametro(String fecha)
	{
		boolean respuesta = TiendaDAO.fijarFechaCierreCierre(fecha, auditoria);
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
	
	public ArrayList<Egreso> obtenerEgresosSemana(String fechaInferior, String fechaSuperior)
	{
		ArrayList<Egreso> egresos =  EgresoDAO.obtenerEgresosSemana(fechaInferior, fechaSuperior, auditoria);
		return(egresos);
		
	}
	
	public int insertarEgreso(Egreso egrIns)
	{
		int idIns = EgresoDAO.insertarEgreso(egrIns, auditoria);
		egrIns.setIdEgreso(idIns);
		String comandaEgreso = generarStrImpresionEgreso(egrIns);
		if(Sesion.getModeloImpresion() != 1)
		{
			ImprimirAdmDAO.insertarImpresion(comandaEgreso, auditoria);
		}
		else
		{
			Impresion.main(comandaEgreso);
		}
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
	            + "    Observación: " + egresoImp.getDescripcion() + "   \n"
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
		if(Sesion.getModeloImpresion() != 1)
		{
			ImprimirAdmDAO.insertarImpresion(comandaIngreso, auditoria);
		}
		else
		{
			Impresion.main(comandaIngreso);
		}
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
	            + "    Observación: " + ingresoImp.getDescripcion() + "   \n"
	            + "    ======================================\n"
	            + "\n\n\n\n\n\n\n           ";
		return(ingreso);
	}
	
	
	
	
	//Operaciones de porciones en la tienda
	
	public PorcionesControlDiario obtenerPorcionesControlDiario(String fecha)
	{
		PorcionesControlDiario estActual = PorcionesControlDiarioDAO.obtenerPorcionesControlDiario(fecha, auditoria);
		return(estActual);
	}
	
	//AUMENTO
	
	public boolean aumentarPorcion(String fecha)
	{
		boolean respuesta = PorcionesControlDiarioDAO.aumentarPorcion(fecha, auditoria);
		return(respuesta);
	}
	
	public boolean aumentarPorcionGaseosa(String fecha)
	{
		boolean respuesta = PorcionesControlDiarioDAO.aumentarPorcionGaseosa(fecha, auditoria);
		return(respuesta);
	}
	
	public boolean aumentarPorcionEmpleado(String fecha)
	{
		boolean respuesta = PorcionesControlDiarioDAO.aumentarPorcionEmpleado(fecha, auditoria);
		return(respuesta);
	}

	public boolean aumentarPorcionDesecho(String fecha)
	{
		boolean respuesta = PorcionesControlDiarioDAO.aumentarPorcionDesecho(fecha, auditoria);
		return(respuesta);
	}
	
	public boolean aumentarPorcionTemporal(String fecha)
	{
		boolean respuesta = PorcionesControlDiarioDAO.aumentarPorcionTemporal(fecha, auditoria);
		return(respuesta);
	}
	
	//DEJAR EN CERO
	
		public boolean encerarPorcion(String fecha)
		{
			boolean respuesta = PorcionesControlDiarioDAO.encerarPorcion(fecha, auditoria);
			return(respuesta);
		}
		
		public boolean encerarPorcionGaseosa(String fecha)
		{
			boolean respuesta = PorcionesControlDiarioDAO.encerarPorcionGaseosa(fecha, auditoria);
			return(respuesta);
		}
		
		public boolean encerarPorcionEmpleado(String fecha)
		{
			boolean respuesta = PorcionesControlDiarioDAO.encerarPorcionEmpleado(fecha, auditoria);
			return(respuesta);
		}

		public boolean encerarPorcionDesecho(String fecha)
		{
			boolean respuesta = PorcionesControlDiarioDAO.encerarPorcionDesecho(fecha, auditoria);
			return(respuesta);
		}
		
		public boolean encerarrPorcionTemporal(String fecha)
		{
			boolean respuesta = PorcionesControlDiarioDAO.encerarPorcionTemporal(fecha, auditoria);
			return(respuesta);
		}
	
	//DISMINUIR
	
	public boolean disminuirPorcion(String fecha)
	{
		boolean respuesta = PorcionesControlDiarioDAO.disminuirPorcion(fecha, auditoria);
		return(respuesta);
	}
	
	public boolean disminuirPorcionGaseosa(String fecha)
	{
		boolean respuesta = PorcionesControlDiarioDAO.disminuirPorcionGaseosa(fecha, auditoria);
		return(respuesta);
	}
	
	public boolean disminuirPorcionEmpleado(String fecha)
	{
		boolean respuesta = PorcionesControlDiarioDAO.disminuirPorcionEmpleado(fecha, auditoria);
		return(respuesta);
	}

	public boolean disminuirPorcionDesecho(String fecha)
	{
		boolean respuesta = PorcionesControlDiarioDAO.disminuirPorcionDesecho(fecha, auditoria);
		return(respuesta);
	}
	
	public boolean disminuirPorcionTemporal(String fecha)
	{
		boolean respuesta = PorcionesControlDiarioDAO.disminuirPorcionTemporal(fecha, auditoria);
		return(respuesta);
	}
	
	public boolean facturarPorciones(String fecha)
	{
		boolean respuesta = PorcionesControlDiarioDAO.facturarPorciones(fecha, auditoria);
		return(respuesta);
	}
	
	public boolean estaFacturadoPorciones(String fecha)
	{
		boolean respuesta = PorcionesControlDiarioDAO.estaFacturadoPorciones(fecha, auditoria);
		return(respuesta);
	}
	
	public boolean retornarEstadoTienda()
	{
		boolean estado = TiendaDAO.retornarEstadoTienda(auditoria);
		return(estado);
	}

	public boolean activarEstadoTienda(String nombreTienda)
	{
		boolean respuesta = TiendaDAO.activarEstadoTienda(auditoria);
		if(respuesta)
		{
			//Se envia correo notificando el bloqueo
			Correo correo = new Correo();
			correo.setAsunto("DESBLOQUEO DE TIENDA PARA PEDIDOS");
			ArrayList correos = GeneralDAO.obtenerCorreosParametro("BLOQUEOTIENDA", PrincipalLogueo.habilitaAuditoria);
			correo.setContrasena("Pizzaamericana2017");
			correo.setUsuarioCorreo("alertaspizzaamericana@gmail.com");
			correo.setMensaje("La tienda " + nombreTienda + " HA SIDO DESBLOQUEADA para tomar pedidos ");
			ControladorEnvioCorreo contro = new ControladorEnvioCorreo(correo, correos);
			contro.enviarCorreo();
		}
		return(respuesta);
	}
	
	public boolean desactivarEstadoTienda(String nombreTienda)
	{
		boolean respuesta = TiendaDAO.desactivarEstadoTienda(auditoria);
		if(respuesta)
		{
			//Se envia correo notificando el bloqueo
			Correo correo = new Correo();
			correo.setAsunto("BLOQUEO TIENDA PARA PEDIDOS");
			ArrayList correos = GeneralDAO.obtenerCorreosParametro("BLOQUEOTIENDA", PrincipalLogueo.habilitaAuditoria);
			correo.setContrasena("Pizzaamericana2017");
			correo.setUsuarioCorreo("alertaspizzaamericana@gmail.com");
			correo.setMensaje("La tienda " + nombreTienda + " HA SIDO BLOQUEADA para tomar pedidos ");
			ControladorEnvioCorreo contro = new ControladorEnvioCorreo(correo, correos);
			contro.enviarCorreo();
		}
		return(respuesta);
	}
	
	
	public void validarEstadoNumeracionFacturacion()
	{
		Tienda tienda = TiendaDAO.obtenerTienda(auditoria);
		//Traemos los valores necesario para realizar el control de numéración de facturación
		long secuenciaAct = (long) TiendaDAO.retornarSecuenciaFacturacion(auditoria);
		long deltaNumeracion = (long) tienda.getDeltaNumeracion();
		long numeroSuperior = tienda.getNumeroFinalResolucion();
		if((numeroSuperior-secuenciaAct) <= deltaNumeracion )
		{
			Correo correo = new Correo();
			correo.setAsunto("ALERTA Se están acabando los números de facturación " + tienda.getNombretienda());
			correo.setContrasena("Pizzaamericana2017");
			ArrayList correos = GeneralDAO.obtenerCorreosParametro("REPORTESEMANALES", auditoria);
			correo.setUsuarioCorreo("alertaspizzaamericana@gmail.com");
			correo.setMensaje("La numeración disponible para la actual resolución de numeración está para vencerse : \n" +  "El número final de resolución es " + tienda.getNumeroFinalResolucion() + " y " + " el número actual de numeración de factura es " + secuenciaAct);
			ControladorEnvioCorreo contro = new ControladorEnvioCorreo(correo, correos);
			contro.enviarCorreo();
		}
	}
	
	public int retornarSecuenciaFacturacion()
	{
		int secuenciaAct = TiendaDAO.retornarSecuenciaFacturacion(auditoria);
		return(secuenciaAct);
	}
	
	public String obtenerRutaMapa()
	{
		Tienda tienda =  TiendaDAO.obtenerRutaMapa(auditoria);
		JSONObject respuesta = new JSONObject();
		respuesta.put("rutamapa",  tienda.getRutaMapa());
		respuesta.put("latitud",  tienda.getLatitud());
		respuesta.put("longitud",  tienda.getLongitud());
		return(respuesta.toJSONString());
	}
	
}
