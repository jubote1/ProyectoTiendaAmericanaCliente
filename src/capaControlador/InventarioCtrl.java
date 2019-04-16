package capaControlador;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import capaDAO.DetallePedidoDAO;
import capaDAO.GeneralDAO;
import capaDAO.InventariosTemporalDAO;
import capaDAO.ItemInventarioDAO;
import capaDAO.ItemInventarioHistoricoDAO;
import capaDAO.ItemInventarioProductoDAO;
import capaDAO.ModificadorInventarioDAO;
import capaDAO.PedidoEspecialDAO;
import capaDAO.TiendaDAO;
import capaModelo.Correo;
import capaModelo.DetallePedido;
import capaModelo.FechaSistema;
import capaModelo.InventariosTemporal;
import capaModelo.ModificadorInventario;
import capaModelo.PedidoEspecial;
import capaModelo.Tienda;
import utilidades.ControladorEnvioCorreo;

public class InventarioCtrl {
	private boolean auditoria;
	public InventarioCtrl(boolean auditoria)
	{
		this.auditoria = auditoria;
	}
	public ArrayList obtenerItemInventarioIngresar()
	{
		ArrayList itemsIngresar = ItemInventarioDAO.obtenerItemInventarioIngresar(auditoria);
		return(itemsIngresar);
	}
	
	public ArrayList obtenerItemInventarioIngresar(String fechaSistema, String tipo)
	{
		ArrayList itemsIngresar = ItemInventarioDAO.obtenerItemInventarioIngresar(fechaSistema, tipo, auditoria);
		return(itemsIngresar);
	}
	
	public int insertarIngresosInventarios(ArrayList <ModificadorInventario> ingresos, String fecha )
	{
		int idIngreso = ModificadorInventarioDAO.insertarIngresosInventarios(ingresos, fecha, auditoria);
		return(idIngreso);
	}
	
	public boolean insertarInventariosTemp(ArrayList <InventariosTemporal> ingresosTemp)
	{
		boolean respuesta = false;
		InventariosTemporal ingTemp;
		int contadorIng = 0;
		for(int i = 0; i < ingresosTemp.size(); i++)
		{
			ingTemp = ingresosTemp.get(i);
			InventariosTemporalDAO.insertarInventariosTemporal(ingTemp, auditoria);
			contadorIng++;
		}
	if(contadorIng > 0)
	{
		respuesta = true;
	}
		return(respuesta);
	}
	
	public int insertarRetirosInventarios(ArrayList <ModificadorInventario> retiros, String fecha )
	{
		int idRetiro = ModificadorInventarioDAO.insertarRetirosInventarios(retiros, fecha, auditoria);
		return(idRetiro);
	}

	public int insertarPedidoEspecial(PedidoEspecial ped)
	{
		int idPedidoNuevo = PedidoEspecialDAO.insertarPedidoEspecial(ped, auditoria);
		return(idPedidoNuevo);
	}
	
	public boolean eliminarItemInventario(int idPedidoEspecial)
	{
		boolean respuesta = PedidoEspecialDAO.eliminarItemInventario(idPedidoEspecial, auditoria);
		return(respuesta);
	}
	
	public ArrayList obtenerPedidosEspeciales(String fecha)
	{
		ArrayList pedidosEspeciales = PedidoEspecialDAO.obtenerPedidosEspeciales(fecha, auditoria);
		return(pedidosEspeciales);
	}
	
	public  int obtenerCantItemInventario( )
	{
		int cantItems = ItemInventarioDAO.obtenerCantItemInventario(auditoria);
		return(cantItems);
		
	}
	
	public ArrayList obtenerItemInventarioResumen(String fecha)
	{
		ArrayList itemInventarioResumen = ItemInventarioDAO.obtenerItemInventarioResumen(fecha, auditoria);
		return(itemInventarioResumen);
	}
	
	public ArrayList obtenerItemInventarioVarianza(String fecha)
	{
		ArrayList itemInventarioVarianza = ItemInventarioDAO.obtenerItemInventarioVarianza(fecha, auditoria);
		return(itemInventarioVarianza);
	}
	
	public ArrayList obtenerItemInventarioVarianzaTemp(String fecha)
	{
		ArrayList itemInventarioVarianza = ItemInventarioDAO.obtenerItemInventarioVarianzaTemp(fecha, auditoria);
		return(itemInventarioVarianza);
	}
	public int insertarVarianzaInventarios(ArrayList <ModificadorInventario> varianzas, String fecha )
	{
		int idInvVarianza = ModificadorInventarioDAO.insertarVarianzaInventarios(varianzas, fecha, auditoria);
		return(idInvVarianza);
	}
	
	public  boolean seIngresoVarianza(String fecha)
	{
		boolean respuesta = ModificadorInventarioDAO.seIngresoVarianza(fecha, auditoria);
		return(respuesta);
	}
	
	public boolean descontarInventarioPedido(int idPedido)
	{
		ArrayList<DetallePedido> detallesPedido = DetallePedidoDAO.obtenerDetallePedido(idPedido, auditoria);
		ArrayList<ModificadorInventario> descInventario = new ArrayList();
		ArrayList<ModificadorInventario> modsInv = new ArrayList();
		for(int i = 0; i < detallesPedido.size(); i++)
		{
			DetallePedido detTemp = detallesPedido.get(i);
			//Validamos que el detallePedido no haya sido descargado al inventario en cuyo caso realizremos el descargo al inventario
			if(detTemp.getDescargoInventario().equals(new String("N")))
			{
				modsInv = ItemInventarioProductoDAO.obtenerItemsInventarioProducto(detTemp.getIdProducto(), detTemp.getCantidad(), auditoria);
				boolean respuesta = ModificadorInventarioDAO.insertarConsumoInventarios(modsInv, idPedido, auditoria);
				descargarDetallePedido(detTemp.getIdDetallePedido());
			}
		}
		return(true);
	}
	
	
	//Métodos para el manejo de la entidad InventariosTemporal
	
	public void insertarInventariosTemporal(InventariosTemporal invTemp)
	{
		InventariosTemporalDAO.insertarInventariosTemporal(invTemp,auditoria);
	}
	
	public void limpiarTipoInventariosTemporal(String fechaSistema, String tipoInventario)
	{
		InventariosTemporalDAO.limpiarTipoInventariosTemporal(fechaSistema, tipoInventario, auditoria);
	}
	
	public boolean existeInventariosTemporal(String fechaSistema, String tipoInventario)
	{
		boolean respuesta = InventariosTemporalDAO.existeInventariosTemporal(fechaSistema, tipoInventario, auditoria);
		return(respuesta);
	}
	
	public boolean reintegrarInventarioPedido(int idPedido, int idMotivoAnulacion, String observacion)
	{
		ArrayList<DetallePedido> detallesPedido = DetallePedidoDAO.obtenerDetallePedido(idPedido, auditoria);
		ArrayList<ModificadorInventario> descInventario = new ArrayList();
		ArrayList<ModificadorInventario> modsInv = new ArrayList();
		for(int i = 0; i < detallesPedido.size(); i++)
		{
			DetallePedido detTemp = detallesPedido.get(i);
			//Validamos que el detallePedido no haya sido descargado al inventario en cuyo caso realizremos el descargo al inventario
			if(detTemp.getDescargoInventario().equals(new String("S"))&& (!detTemp.getEstado().equals(new String("A"))))
			{
				modsInv = ItemInventarioProductoDAO.obtenerItemsInventarioProducto(detTemp.getIdProducto(), detTemp.getCantidad()*-1, auditoria);
				boolean respuesta = ModificadorInventarioDAO.insertarConsumoInventarios(modsInv, idPedido, auditoria);
				//Devuelve el inventario y lo marca como anulado
				DetallePedidoDAO.anularDetallePedidoPuntual(detTemp.getIdDetallePedido(), idMotivoAnulacion, observacion,  auditoria);
			}
		}
		return(true);
	}
	
	/**
	 * Método que se encargará de reintegrar al inventario dado un idDetallePedido específico, validando si este descontó o no
	 * inventario
	 * @param idDetallePedido Se recibe parámetro el idDetallePedido que posiblemente se va a descontar en inventario
	 * @param idPedido Se recibe el idPedido sobre el cual se va a realizar el reintegro del idDetallePedido
	 * @return
	 */
	public boolean reintegrarInventarioDetallePedido(int idDetallePedido, int idPedido)
	{
		ArrayList<DetallePedido> detallesPedido = DetallePedidoDAO.obtenerDetallePedidoMaster(idDetallePedido, idPedido, auditoria);
		ArrayList<ModificadorInventario> descInventario = new ArrayList();
		ArrayList<ModificadorInventario> modsInv = new ArrayList();
		for(int i = 0; i < detallesPedido.size(); i++)
		{
			DetallePedido detTemp = detallesPedido.get(i);
			//Validamos que el detallePedido no haya sido descargado al inventario en cuyo caso realizremos el descargo al inventario
			if(detTemp.getDescargoInventario().equals(new String("S")))
			{
				modsInv = ItemInventarioProductoDAO.obtenerItemsInventarioProducto(detTemp.getIdProducto(), detTemp.getCantidad()*-1, auditoria);
				boolean respuesta = ModificadorInventarioDAO.insertarConsumoInventarios(modsInv, idPedido, auditoria);
				
			}
		}
		return(true);
	}
	
	public boolean descargarDetallePedido(int idDetallePedido)
	{
		boolean respuesta = DetallePedidoDAO.descargarDetallePedido(idDetallePedido, auditoria);
		return(respuesta);
	}
	
	public ArrayList obtenerInventarioVarianza(String fecha)
	{
		ArrayList itemInventarioVarianza = ItemInventarioDAO.obtenerInventarioVarianza(fecha, auditoria);
		return(itemInventarioVarianza);
	}
	
	public ArrayList obtenerInventarioVarianzaRes(String fecha)
	{
		ArrayList itemInventarioVarianza = ItemInventarioDAO.obtenerInventarioVarianzaRes(fecha, auditoria);
		return(itemInventarioVarianza);
	}
	
	//GENERAR INFORMACIÓN SEMANAL
	
	public String obtenerVarianzaDiasSemana(boolean resumida)
	{
		String respuesta = "";
		PedidoCtrl pedCtrl = new PedidoCtrl(auditoria);
		//Recuperamos la fecha actual del sistema con la fecha apertura
		FechaSistema fecha = pedCtrl.obtenerFechasSistema();
		String fechaActual = fecha.getFechaUltimoCierre();
		//Variables donde manejaremos la fecha anerior con el fin realizar los cálculos de ventas
		Date datFecha;
		String fechaAnterior = "";
		//Creamos el objeto calendario
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
			//Realizamos un ciclo para ir disminuyendo la fecha uno a uno hasta seis
			datFecha = calendarioActual.getTime();
			fechaAnterior = dateFormat.format(datFecha);
			ArrayList varianza;
			if(resumida)
			{
				varianza =  obtenerInventarioVarianzaRes(fechaAnterior);
			}else
			{
				varianza =  obtenerInventarioVarianza(fechaAnterior);
			}
			respuesta = respuesta + resumenVarianzaDiaria(varianza, fechaAnterior);
			for(int i = 1; i <= 6; i++)
			{
				calendarioActual.add(Calendar.DAY_OF_YEAR, -1);
				datFecha = calendarioActual.getTime();
				fechaAnterior = dateFormat.format(datFecha);
				if(resumida)
				{
					varianza =  obtenerInventarioVarianzaRes(fechaAnterior);
				}else
				{
					varianza =  obtenerInventarioVarianza(fechaAnterior);
				}
				respuesta = respuesta + resumenVarianzaDiaria(varianza, fechaAnterior);
			}
			
		}
		else if(diaActual == 2)
		{
			//Si es lunes no se hace nada
		}
		else if(diaActual == 3)
		{
			//Si es martes se resta uno solo
			calendarioActual.add(Calendar.DAY_OF_YEAR, -1);
		}
		else if(diaActual == 4)
		{
			//Si es miercoles se resta dos
			calendarioActual.add(Calendar.DAY_OF_YEAR, -2);
		}
		else if(diaActual == 5)
		{
			//Si es jueves se resta tres
			calendarioActual.add(Calendar.DAY_OF_YEAR, -3);
		}
		else if(diaActual == 6)
		{
			//Si es viernes se resta cuatro
			calendarioActual.add(Calendar.DAY_OF_YEAR, -4);
		}
		else if(diaActual == 7)
		{
			//Si es sabado se resta cinco
			calendarioActual.add(Calendar.DAY_OF_YEAR, -5);
		}
		return(respuesta);
	}
	
	//GENERAR STRING DE CORREO ELECTRÓNICO
	
	public String resumenVarianzaDiaria(ArrayList varianza, String fecha)
	{
		String respuesta  = "";
		DecimalFormat formatea = new DecimalFormat("###,###");
		respuesta = respuesta + "<table border='2'> <tr> RESUMEN VARIANZA " + fecha + " </tr>";
		respuesta = respuesta + "<tr>"
				+  "<td><strong>Nombre Item</strong></td>"
				+  "<td><strong>Inicio</strong></td>"
				+  "<td><strong>Retiro</strong></td>"
				+  "<td><strong>Ingreso</strong></td>"
				+  "<td><strong>Consumo</strong></td>"
				+  "<td><strong>Teor Real</strong></td>"
				+  "<td><strong>TEOR ING</strong></td>"
				+  "<td><strong>Varianza</strong></td>"
				+  "</tr>";
		for(int y = 0; y < varianza.size();y++)
		{
			String[] fila =(String[]) varianza.get(y);
			double teoricoReal = Double.parseDouble(fila[2]) - Double.parseDouble(fila[3]) + Double.parseDouble(fila[4]) - Double.parseDouble(fila[5]);
			//Calculamos la varianza entendiendo que la varianza es la resta de los valores teorico y real
			double varCalculada = Double.parseDouble(fila[6]) - teoricoReal;
			respuesta = respuesta + "<tr><td>" + fila[1] + "</td><td>" + formatea.format(Double.parseDouble(fila[2])) + "</td><td>" + formatea.format(Double.parseDouble(fila[3])) + "</td><td>" + formatea.format(Double.parseDouble(fila[4])) + "</td><td> " + formatea.format(Double.parseDouble(fila[5])) + "</td><td> " + formatea.format(teoricoReal) + "</td><td>" + formatea.format(Double.parseDouble(fila[6])) + "</td><td>" + formatea.format(varCalculada) +"</td></tr>";
		}
		respuesta = respuesta + "</table> <br/>";
		return(respuesta);
	}
	
	//ENVIO DE CORREO ELECTRÓNICO
	
	public void enviarCorreoVarianzaSemanal(boolean resumida)
	{
		PedidoCtrl pedCtrl = new PedidoCtrl(auditoria);
		//Obtenemos la fecha del rango del reporte
		FechaSistema fecha = pedCtrl.obtenerFechasSistema();
		String fechaSis = fecha.getFechaUltimoCierre();
		//Obtenemos la tienda
		Tienda tiendaReporte = TiendaDAO.obtenerTienda(auditoria);
		//Obtenemos el reporte
		String reporte = obtenerVarianzaDiasSemana(resumida);
		Correo correo = new Correo();
		correo.setAsunto("SEMANAL Reporte Varianzas Punto de Venta " + tiendaReporte.getNombretienda() + " " + fechaSis);
		correo.setContrasena("Pizzaamericana2017");
		ArrayList correos = GeneralDAO.obtenerCorreosParametro("REPORTESEMANALES", auditoria);
		correo.setUsuarioCorreo("alertaspizzaamericana@gmail.com");
		correo.setMensaje("A continuación el reporte semanal de Varianzas: \n" + reporte);
		ControladorEnvioCorreo contro = new ControladorEnvioCorreo(correo, correos);
		contro.enviarCorreo();
	}
	
	public ArrayList obtenerInventarioConsumido(String fechaActual)
	{
		ArrayList itemsInvConsumido = ItemInventarioDAO.obtenerInventarioConsumido(fechaActual, auditoria);
		return(itemsInvConsumido);
	}
	public ArrayList obtenerInventarioActualReporte(String fechaActual)
	{
		ArrayList itemsInvConsumido = ItemInventarioDAO.obtenerInventarioActualReporte(fechaActual, auditoria);
		return(itemsInvConsumido);
	}
	
	public double obtenerItemInventarioHistorico(int idItem, String fecha)
	{
		double cantidad = ItemInventarioHistoricoDAO.obtenerItemInventarioHistorico(idItem, fecha, auditoria);
		return(cantidad);
	}
	
	public double ingresadoItemInvenario(int idItem, String fecha)
	{
		double cantidad = ModificadorInventarioDAO.ingresadoItemInvenario(idItem, fecha, auditoria);
		return(cantidad);
	}
	
	public double retiradoItemInvenario(int idItem, String fecha)
	{
		double cantidad = ModificadorInventarioDAO.retiradoItemInvenario(idItem, fecha, auditoria);
		return(cantidad);
	}
	
	public ArrayList obtenerProductoDescuentoItemInventario(int idItem)
	{
		ArrayList prodDescuentan = ModificadorInventarioDAO.obtenerProductoDescuentoItemInventario(idItem, auditoria);
		return(prodDescuentan);
	}
	
	public ArrayList obtenerIngresosItemInventario(int idItem,String fecha)
	{
		ArrayList itemIngreso= ModificadorInventarioDAO.obtenerIngresosItemInventario(idItem, fecha, auditoria);
		return(itemIngreso);
	}
	
	public ArrayList obtenerRetirosItemInventario(int idItem,String fecha)
	{
		ArrayList itemRetiro= ModificadorInventarioDAO. obtenerRetirosItemInventario(idItem, fecha, auditoria);
		return(itemRetiro);
	}
	
	public double obtenerConsumoItemInventario(int idItem,String fecha)
	{
		double consumo = ModificadorInventarioDAO.obtenerConsumoItemInventario(idItem, fecha,auditoria);
		return(consumo);
	}
	
	public  double obtenerVarianzaItemInventario(int idItem,String fecha)
	{
		double cantidad =  ModificadorInventarioDAO.obtenerVarianzaItemInventario(idItem, fecha,auditoria);
		return(cantidad);
	}
	
	public ArrayList obtenerPedidosDescItemInventario(int idItem,String fecha)
	{
		ArrayList pediDesItemInventario = ModificadorInventarioDAO.obtenerPedidosDescItemInventario(idItem,fecha,auditoria);
		return(pediDesItemInventario);
	}
	
	public ArrayList obtenerPedidosAnulItemInventario(int idItem,String fecha)
	{
		ArrayList pedidosConAnulacion = ModificadorInventarioDAO.obtenerPedidosAnulItemInventario(idItem,fecha, auditoria);
		return(pedidosConAnulacion);
	}
	
}
