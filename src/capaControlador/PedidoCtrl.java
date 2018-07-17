package capaControlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import capaDAO.DetallePedidoDAO;
import capaDAO.DetallePedidoImpuestoDAO;
import capaDAO.EstadoAnteriorDAO;
import capaDAO.EstadoDAO;
import capaDAO.EstadoPosteriorDAO;
import capaDAO.PedidoDAO;
import capaDAO.PedidoDescuentoDAO;
import capaDAO.PedidoFormaPagoDAO;
import capaDAO.PedidoPixelDAO;
import capaDAO.PreguntaDAO;
import capaDAO.TiendaDAO;
import capaDAO.TipoPedidoDAO;
import capaModelo.Cliente;
import capaModelo.DetallePedido;
import capaModelo.DetallePedidoPixel;
import capaModelo.Estado;
import capaModelo.EstadoAnterior;
import capaModelo.EstadoPedidoTienda;
import capaModelo.EstadoPosterior;
import capaModelo.FechaSistema;
import capaModelo.PedidoDescuento;
import capaModelo.PedidoFormaPago;
import capaModelo.Pregunta;
import capaModelo.RespuestaPedidoPixel;
import capaModelo.TipoPedido;

public class PedidoCtrl {
	
	public PedidoCtrl()
	{
		
	}
	
	
	/**
	 * Método que tiene toda la lógica para descomponer el json entrante, insertar el pedido en la tienda y entregar
	 * un objeto tipo Respuesta Pedido y entregar finalmente un json de respuesta.
	 * @param datos Se recibe un string en formato JSON con todos los datos para la inserción del pedido.
	 * @return Se retorna un string en formato JSON con la respuesta de la inserción del pedido.
	 */
	
	
	/**
	 * Método de la capa controladora que se encarga de retornar en formato Json los estados de los pedidos tipo 
	 * domicilio que tiene la tienda, recopilando los que están pendientes de preparación, los que están en ruta
	 * y los que están en estado finalizado.
	 * @param dsnODBC Se recibe el datasourcename para la conexión a la base de datos.
	 * @return
	 */
	public String ConsultarEstadoPedidoTienda(String dsnODBC)
	{
		JSONArray listJSON = new JSONArray();
		ArrayList<EstadoPedidoTienda> estPedidos = PedidoPixelDAO.ConsultarEstadoPedidoTienda(dsnODBC);
		for (EstadoPedidoTienda estPed : estPedidos) {
			JSONObject cadaEstadoPedidoJSON = new JSONObject();
			cadaEstadoPedidoJSON.put("domiciliario", estPed.getDomiciliario());
			cadaEstadoPedidoJSON.put("estatus", estPed.getEstatus());
			cadaEstadoPedidoJSON.put("transaccion", estPed.getTransaccion());
			cadaEstadoPedidoJSON.put("horapedido", estPed.getHoraPedido());
			cadaEstadoPedidoJSON.put("tiempototal", estPed.getTiempoTotal());
			cadaEstadoPedidoJSON.put("direccion", estPed.getDireccion());
			cadaEstadoPedidoJSON.put("telefono", estPed.getTelefono());
			cadaEstadoPedidoJSON.put("nombrecompleto", estPed.getNombreCompleto());
			cadaEstadoPedidoJSON.put("tiempoenruta", estPed.getTiempoEnRuta());
			cadaEstadoPedidoJSON.put("tomadordepedido", estPed.getTomadorDePedido());
			cadaEstadoPedidoJSON.put("formapago", estPed.getFormaPago());
			listJSON.add(cadaEstadoPedidoJSON);
		}
		
		return listJSON.toJSONString();
		
	}
	
	public ArrayList<Pregunta> obtenerPreguntaProducto(int idProducto)
	{
		ArrayList<Pregunta> preguntaProducto = PreguntaDAO.obtenerPreguntaProducto(idProducto);
		return(preguntaProducto);
	}
	
	/**
	 * Método que se encarga de la inserción del encabezado de un pedido
	 * @param idtienda La identificación de la tienda donde está siendo tomado el pedido.
	 * @param idcliente La identificación del cliente al cual se le está tomando el pedido
	 * @param fechaPedido La fecha del Pedido que se está tomando
	 * @param user Usuario que está tomando el pedido.
	 * @return El identificador del pedido que lo identificará inequivocamente.
	 */
	public int InsertarEncabezadoPedido(int idtienda, int idcliente, String fechaPedido, String user)
	{
		int idPedidoNuevo = PedidoDAO.InsertarEncabezadoPedido(idtienda, idcliente, fechaPedido, user);
		//El estado cero se tiene la convención q
		PedidoDAO.ActualizarEstadoPedido(idPedidoNuevo, 0, 0);
		return(idPedidoNuevo);
		
	}
	
	public int insertarDetallePedido(DetallePedido detPedido)
	{
		int idDetalleoNuevo = DetallePedidoDAO.insertarDetallePedido(detPedido);
		return(idDetalleoNuevo);
	}
	
	public boolean eliminarDetallePedido(int idDetallePedido)
	{
		boolean respuesta = DetallePedidoDAO.eliminarDetallePedido(idDetallePedido);
		return(respuesta);
	}
	
	public boolean anularDetallePedido(int idDetallePedido)
	{
		boolean respuesta = DetallePedidoDAO.anularDetallePedido(idDetallePedido);
		return(respuesta);
	}
	
	public boolean validarDetalleMaster(int idDetallePedido)
	{
		boolean respuesta = DetallePedidoDAO.validarDetalleMaster(idDetallePedido);
		return(respuesta);
	}
	
	public boolean insertarPedidoFormaPago(double efectivo, double tarjeta, double valorTotal, double cambio, int idPedidoTienda)
	{
		int resIdEfe = 0, resIdTar = 0;
		if(efectivo > 0)
		{
			PedidoFormaPago forEfectivo = new PedidoFormaPago(0,idPedidoTienda,1,valorTotal,efectivo);
			resIdEfe = PedidoFormaPagoDAO.InsertarPedidoFormaPago(forEfectivo);
		}
		if(tarjeta > 0)
		{
			PedidoFormaPago forTarjeta = new PedidoFormaPago(0,idPedidoTienda,2,valorTotal,tarjeta);
			resIdTar = PedidoFormaPagoDAO.InsertarPedidoFormaPago(forTarjeta);
		}
		if(resIdEfe > 0 || resIdTar > 0)
		{
			return(true);
		}else
		{
			return(false);
		}
		
	}
	
	public boolean existeFormaPago(int idPedido)
	{
		boolean respuesta = PedidoFormaPagoDAO.existeFormaPago(idPedido);
		return(respuesta);
	}
	
	/**
	 * 
	 * @param idPedido
	 * @param tiempoPedido
	 * @param idTipoPedido
	 * @return
	 */
	public boolean finalizarPedido(int idPedido, double tiempoPedido, int idTipoPedido)
	{
		//Antes de finalizar pedido realizaremos el cálculo de los impuestos
		//Clareamos los impuestos liquidados, si existe con anterioridad
		DetallePedidoImpuestoDAO.eliminarDetallePedidoImpuesto(idPedido);
		//Realizamos la liquidación de los impuestos para el pedido
		ImpuestoCtrl impCtrl = new ImpuestoCtrl();
		impCtrl.liquidarImpuestosPedido(idPedido);
		boolean respuesta = PedidoDAO.finalizarPedido(idPedido, tiempoPedido, idTipoPedido);
		int idEstadoPostIni = EstadoDAO.obtenerEstadoInicial(idTipoPedido);
		PedidoDAO.ActualizarEstadoPedido(idPedido, 0 , idEstadoPostIni);
		return(respuesta);
	}
	
	public boolean anularPedido(int idPedido)
	{
		boolean respuesta = DetallePedidoDAO.AnularDetallesPedido(idPedido);
		if(respuesta)
		{
			respuesta = PedidoDAO.anularPedido(idPedido);
		}
		if(respuesta)
		{
			return(true);
		}
		return(false);
	}
	
	public boolean anularPedidoEliminar(int idPedido)
	{
		boolean respuesta = DetallePedidoDAO.eliminarDetallesPedido(idPedido);
		if(respuesta)
		{
			respuesta = PedidoDAO.eliminarPedido(idPedido);
		}
		if(respuesta)
		{
			return(true);
		}
		return(false);
	}
	
	
	//ESTADOS
	
	public  ArrayList obtenerEstado()
	{
		ArrayList estados = EstadoDAO.obtenerEstado();
		return(estados);
	}
	
	public Estado obtenerEstado(int idEstado)
	{
		Estado estado = EstadoDAO.obtenerEstado(idEstado);
		return(estado);
	}
	
	public static int insertarEstado(Estado estado)
	{
		int idEstadoIns = EstadoDAO.insertarEstado(estado);
		return(idEstadoIns);
	}
	
	public boolean eliminarEstado(int idestado)
	{
		boolean respuesta = EstadoDAO.eliminarEstado(idestado);
		return(respuesta);
	}
	
	public boolean editarEstado(Estado estado)
	{
		boolean respuesta = EstadoDAO.editarEstado(estado);
		return(respuesta);
	}
	
	//ESTADOS ANTERIORES
	
	public ArrayList<EstadoAnterior> obtenerEstadosAnteriores(int idEstado)
	{
		ArrayList<EstadoAnterior> estadosAnt = EstadoAnteriorDAO.obtenerEstadosAnteriores(idEstado);
		return(estadosAnt);
	}
	
	public boolean insertarEstadoAnterior(EstadoAnterior estadoAnt)
	{
		boolean respuesta = EstadoAnteriorDAO.insertarEstadoAnterior(estadoAnt);
		return(respuesta);
	}
	
	public boolean eliminarEstadoAnterior(EstadoAnterior estadoAnt)
	{
		boolean respuesta = EstadoAnteriorDAO.eliminarEstadoAnterior(estadoAnt);
		return(respuesta);
	}
	
	public ArrayList<Estado> obtenerEstadosAnterioresFaltantes(int idEstado)
	{
		ArrayList<EstadoAnterior> estadosAnt = EstadoAnteriorDAO.obtenerEstadosAnteriores(idEstado);
		ArrayList<Estado> estados = EstadoDAO.obtenerTodosEstado();
		ArrayList<Estado> estadosFaltantes = new ArrayList();
		for(int i = 0; i < estados.size(); i++)
		{
			boolean encontrado = false;
			Estado estadoTemp = estados.get(i);
			for(int j = 0; j < estadosAnt.size(); j++)
			{
				EstadoAnterior estAntTemp = estadosAnt.get(j);
				if(estAntTemp.getIdEstadoAnterior() == estadoTemp.getIdestado() || idEstado == estadoTemp.getIdestado())
				{
					encontrado = true;
					break;
				}
			}
			if (!encontrado)
			{
				estadosFaltantes.add(estadoTemp);
			}
		}
		return(estadosFaltantes);
	}

	
	//ESTADOS POSTERIORES
	
		public ArrayList<EstadoPosterior> obtenerEstadosPosteriores(int idEstado)
		{
			ArrayList<EstadoPosterior> estadosPos = EstadoPosteriorDAO.obtenerEstadosPos(idEstado);
			return(estadosPos);
		}
		
		public boolean insertarEstadoPosterior(EstadoPosterior estadoPos)
		{
			boolean respuesta = EstadoPosteriorDAO.insertarEstado(estadoPos);
			return(respuesta);
		}
		
		public boolean eliminarEstadoPosterior(EstadoPosterior estadoPos)
		{
			boolean respuesta = EstadoPosteriorDAO.eliminarEstadoPosterior(estadoPos);
			return(respuesta);
		}
		
		public ArrayList<Estado> obtenerEstadosPosterioresFaltantes(int idEstado)
		{
			ArrayList<EstadoPosterior> estadosPos = EstadoPosteriorDAO.obtenerEstadosPos(idEstado);
			ArrayList<Estado> estados = EstadoDAO.obtenerTodosEstado();
			ArrayList<Estado> estadosFaltantes = new ArrayList();
			for(int i = 0; i < estados.size(); i++)
			{
				boolean encontrado = false;
				Estado estadoTemp = estados.get(i);
				for(int j = 0; j < estadosPos.size(); j++)
				{
					EstadoPosterior estPosTemp = estadosPos.get(j);
					if(estPosTemp.getIdEstadoPosterior() == estadoTemp.getIdestado() || idEstado == estadoTemp.getIdestado())
					{
						encontrado = true;
						break;
					}
				}
				if (!encontrado)
				{
					estadosFaltantes.add(estadoTemp);
				}
			}
			return(estadosFaltantes);
		}
		
		//TIPOS PEDIDO
		
		public  ArrayList obtenerTiposPedido()
		{
			ArrayList tiposPedido = TipoPedidoDAO.obtenerTiposPedido();
			return(tiposPedido);
		}
		
		public int insertarTipoPedido(TipoPedido tipPed)
		{
			int idTipoIns = TipoPedidoDAO.insertarTipoPedido(tipPed);
			return(idTipoIns);
		}
		
		public  boolean eliminarTipoPedido(int idTipoPedido)
		{
			boolean respuesta = TipoPedidoDAO.eliminarTipoPedido(idTipoPedido);
			return(respuesta);
		}
		
		public  boolean EditarTipoPedido(TipoPedido tipPedidoEditar)
		{
			boolean respuesta = TipoPedidoDAO.EditarTipoPedido(tipPedidoEditar);
			return(respuesta);
		}
		public TipoPedido obtenerTipoPedido(int idTipoPedido)
		{
			TipoPedido tipPedCon = TipoPedidoDAO.obtenerTipoPedido(idTipoPedido);
			return(tipPedCon);
		}
		
		public ArrayList<TipoPedido> obtenerTiposPedidoNat()
		{
			ArrayList<TipoPedido> tiposPedidoNat = TipoPedidoDAO.obtenerTiposPedidoNat();
			return(tiposPedidoNat);
		}
		
		public FechaSistema obtenerFechasSistema()
		{
			FechaSistema fecha = TiendaDAO.obtenerFechasSistema();
			return(fecha);
		}
		
		public boolean isSistemaAperturado()
		{
			FechaSistema fecha = TiendaDAO.obtenerFechasSistema();
			String fechaSistema = fecha.getFechaApertura();
			String fechaUltimoCierre = fecha.getFechaUltimoCierre();
			if(fechaSistema.equals(fechaUltimoCierre))
			{
				return(false);
			}
			return(true);
		}
		
		public ArrayList obtenerPedidosPorTipo(int idTipoPedido)
		{
			FechaSistema fechaSistema = TiendaDAO.obtenerFechasSistema();
			ArrayList pedidos = new ArrayList();
			if(sePuedeFacturar(fechaSistema))
			{
				pedidos = PedidoDAO.obtenerPedidosPorTipo(idTipoPedido, fechaSistema.getFechaApertura());
				int idPedido = 0;
				for(int i = 0 ; i < pedidos.size(); i++)
				{
					String[]fila = (String[]) pedidos.get(i);
					idPedido =Integer.parseInt(fila[0]);
					String tiempoPedido = calcularTiempoPedido(idPedido);
					fila[fila.length-1] = tiempoPedido;
					pedidos.set(i, fila);
				}
			}
			return(pedidos);
		}
		
		public ArrayList obtenerPedidosTable()
		{
			FechaSistema fechaSistema = TiendaDAO.obtenerFechasSistema();
			ArrayList pedidos = new ArrayList();
			if(sePuedeFacturar(fechaSistema))
			{
				pedidos = PedidoDAO.obtenerPedidosTable(fechaSistema.getFechaApertura());
				int idPedido = 0;
				for(int i = 0 ; i < pedidos.size(); i++)
				{
					String[]fila = (String[]) pedidos.get(i);
					idPedido =Integer.parseInt(fila[0]);
					String tiempoPedido = calcularTiempoPedido(idPedido);
					fila[fila.length-1] = tiempoPedido;
					pedidos.set(i, fila);
				}
			}
			return(pedidos);
		}
		
		public boolean sePuedeFacturar(FechaSistema fechaSistema)
		{
			
			if(fechaSistema.getFechaApertura() == "" || fechaSistema.getFechaApertura() == null)
			{
				return(false);
			}
			return(true);
		}
		
		/**
		 * Método que dado un identificador de pedido determinado, retorna el estado pedido en una entidad
		 * @param idPedidoTienda identificador del pedido
		 * @return Se retorna el estado del pedido en una entidad tipo Estado.
		 */
		public Estado obtenerEstadoPedido(int idPedidoTienda)
		{
			Estado estadoPedido = PedidoDAO.obtenerEstadoPedido(idPedidoTienda);
			return(estadoPedido);
		}
		
		public boolean ActualizarEstadoPedido(int idPedido, int idEstadoAnterior, int idEstadoPosterior)
		{
			boolean respuesta = PedidoDAO.ActualizarEstadoPedido(idPedido, idEstadoAnterior, idEstadoPosterior);
			return(respuesta);
		}
		
		public boolean tieneEstadoFinal( int idTipoPedido, int idEstado)
		{
			boolean respuesta = EstadoDAO.tieneEstadoFinal(idTipoPedido, idEstado);
			return(respuesta);
		}
		
		public boolean tieneEstadoInicial( int idTipoPedido, int idEstado)
		{
			boolean respuesta = EstadoDAO.tieneEstadoInicial(idTipoPedido, idEstado);
			return(respuesta);
		}
	
		public int obtenerEstadoInicial( int idTipoPedido)
		{
			int respuesta = EstadoDAO.obtenerEstadoInicial(idTipoPedido);
			return (respuesta);
		}
		
		public int obtenerEstadoFinal( int idTipoPedido)
		{
			int respuesta = EstadoDAO.obtenerEstadoFinal(idTipoPedido);
			return (respuesta);
		}
		
		public ArrayList obtenerHistoriaEstadoPedido(int idPedido)
		{
			ArrayList historiaPedido = EstadoDAO.obtenerHistoriaEstadoPedido(idPedido);
			return(historiaPedido);
		}
		
		//Más temas de estados
		
		public boolean esEstadoFinal(int idTipoPedido, int idEstado)
		{
			boolean respuesta = EstadoDAO.esEstadoFinal(idTipoPedido, idEstado);
			return(respuesta);
		}
		
		public static boolean esEstadoInicial(int idTipoPedido, int idEstado)
		{
			boolean respuesta = EstadoDAO.esEstadoInicial(idTipoPedido, idEstado);
			return(respuesta);
		}
		//PedidoDescuento
		
		public boolean insertarPedidoDescuento(PedidoDescuento descuento)
		{
			boolean respuesta = PedidoDescuentoDAO.insertarPedidoDescuento(descuento);
			return(respuesta);
		}
		
		public boolean eliminarPedidoDescuento(int idPedido)
		{
			boolean respuesta = PedidoDescuentoDAO.eliminarPedidoDescuento(idPedido);
			return(respuesta);
		}
		
		public PedidoDescuento obtenerPedidoDescuento(int idPedido)
		{
			PedidoDescuento descuento = PedidoDescuentoDAO.obtenerPedidoDescuento(idPedido);
			return(descuento);
		}
		
		public boolean existePedidoDescuento(int idPedido)
		{
			boolean respuesta = PedidoDescuentoDAO.existePedidoDescuento(idPedido);
			return(respuesta);
		}
		
		public double obtenerTotalBrutoPedido(int idpedido)
		{
			double valorBruto = PedidoDAO.obtenerTotalBrutoPedido(idpedido);
			return(valorBruto);
		}
		
		public ArrayList<DetallePedido> obtenerDetallePedido(int idPedido)
		{
			ArrayList<DetallePedido> detPedidos = DetallePedidoDAO.obtenerDetallePedido(idPedido);
			return(detPedidos);
		}
		
		public ArrayList<DetallePedido> obtenerDetallePedidoPintar(int idPedido)
		{
			ArrayList<DetallePedido> detPedidos = DetallePedidoDAO.obtenerDetallePedidoPintar(idPedido);
			return(detPedidos);
		}
		
		public Cliente obtenerClientePedido(int idPedido)
		{
			Cliente cliente = PedidoDAO.obtenerClientePedido(idPedido);
			return(cliente);
		}
		
		public boolean eliminarPedidoFormaPago(int idPedido)
		{
			boolean respuesta = PedidoFormaPagoDAO.eliminarPedidoFormaPago(idPedido);
			return(respuesta);
		}
		
		public int obtenerTipoDePedido(int idPedido)
		{
			int idTipoPedido = PedidoDAO.obtenerTipoPedido(idPedido);
			return(idTipoPedido);
			
		}
		
		public String calcularTiempoPedido(int idPedido)
		{
			String respuesta = "";
			ArrayList hisEstPedido = EstadoDAO.obtenerHistoriaEstadoPedido(idPedido);
			//En este punto ya podemos realizar validación si el estado es final o no y de acuerdo a esto tomar ciertas
			//determinaciones.
			int cantHist  = hisEstPedido.size();
			boolean esEstadoFinal = false;
			if(cantHist > 0 )
			{
				String[] fila = (String[]) hisEstPedido.get(0);
				//Retornamos si es estado final
				esEstadoFinal = Boolean.parseBoolean(fila[fila.length -1]);
				
			}
			Date fechaActual = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd H:m:s");
			int difTiempo = 0;
			int minutos = 0;
			int tamEstados = hisEstPedido.size();
			if(tamEstados == 1)
			{
				//Tomar la fecha
				String[] fila = (String[]) hisEstPedido.get(0);
				String strFecPedido = fila[0];
				Date datFecPedido = new Date();
				try
				{
					datFecPedido = dateFormat.parse(strFecPedido);
					
				}catch(Exception e)
				{
					 datFecPedido = new Date();
				}
				difTiempo =(int) (fechaActual.getTime() - datFecPedido.getTime());
				minutos = (int)TimeUnit.MILLISECONDS.toMinutes(difTiempo );
				respuesta = "Actual: " + minutos + " Total: " + minutos;
			}
			else if(tamEstados > 1)
			{
				String[] fila = (String[]) hisEstPedido.get(0);
				String strFecPedido = fila[0];
				System.out.println("FECHA INICIAL PARA CÁLCULO " + strFecPedido);
				Date datFecPedido = new Date();
				try
				{
					datFecPedido = dateFormat.parse(strFecPedido);
					
				}catch(Exception e)
				{
					 datFecPedido = new Date();
				}
				
				difTiempo =Math.abs((int) (datFecPedido.getTime() - fechaActual.getTime() ));
				minutos = (int)TimeUnit.MILLISECONDS.toMinutes(difTiempo );
				String[] filaAnterior;
				String[] filaFinal;
				if(esEstadoFinal)
				{
					filaAnterior =(String[]) hisEstPedido.get(0);
					filaFinal =(String[]) hisEstPedido.get(tamEstados-1);
					String strFecEstAnterior = filaAnterior[0];
					String strFecEstFinal = filaFinal[0];
					Date datEstAnt, datEstFin;
					try
					{
						datEstAnt = dateFormat.parse(strFecEstAnterior);
						datEstFin = dateFormat.parse(strFecEstFinal);
						
					}catch(Exception e)
					{
						datEstAnt = new Date();
						datEstFin = new Date();
					}
					difTiempo =(int) (datEstFin.getTime() - datEstAnt.getTime());
					minutos = (int)TimeUnit.MILLISECONDS.toMinutes(difTiempo );
					respuesta = respuesta + " TOTAL: " + minutos;
				}
				else
				{
					respuesta = "Total: " + minutos;
					//Para el estado actual
					filaAnterior =(String[]) hisEstPedido.get(tamEstados-2);
					filaFinal =(String[]) hisEstPedido.get(tamEstados-1);
					//String strFecEstAnterior = filaAnterior[0];
					String strFecEstFinal = filaFinal[0];
					Date datEstAnt, datEstFin;
					try
					{
						//datEstAnt = dateFormat.parse(strFecEstAnterior);
						datEstFin = dateFormat.parse(strFecEstFinal);
						
					}catch(Exception e)
					{
						//datEstAnt = new Date();
						datEstFin = new Date();
					}
					difTiempo =Math.abs((int) (fechaActual.getTime() - datEstFin.getTime()));
					minutos = (int)TimeUnit.MILLISECONDS.toMinutes(difTiempo );
					respuesta = respuesta + " Actual: " + minutos;
				}
				
			}
			return(respuesta);
		}
		
		/**
		 * Método que se encarga de retornar resumen por tipo de pedido de los totales de pedido
		 * @param fechaPedido Se recibe un string con la fecha a consultar y resumir los totales.
		 * @return Se retorna un arraylist con los totales resumidos
		 */
		public static ArrayList obtenerTotalesPedidosPorTipo(String fechaPedido)
		{
			ArrayList resumenTotTipoPedido = PedidoDAO.obtenerTotalesPedidosPorTipo(fechaPedido);
			return(resumenTotTipoPedido);
		}
}
