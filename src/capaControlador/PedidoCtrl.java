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
import capaDAO.MotivoAnulacionPedidoDAO;
import capaDAO.PedidoDAO;
import capaDAO.PedidoDescuentoDAO;
import capaDAO.PedidoFormaPagoDAO;
import capaDAO.PedidoPixelDAO;
import capaDAO.PreguntaDAO;
import capaDAO.ProductoDAO;
import capaDAO.ProductoModificadorConDAO;
import capaDAO.ProductoModificadorSinDAO;
import capaDAO.TiendaDAO;
import capaDAO.TipoEmpleadoEstadoDAO;
import capaDAO.TipoPedidoDAO;
import capaDAO.UsuarioDAO;
import capaModelo.Cliente;
import capaModelo.DetallePedido;
import capaModelo.DetallePedidoPixel;
import capaModelo.Estado;
import capaModelo.EstadoAnterior;
import capaModelo.EstadoPedidoTienda;
import capaModelo.EstadoPosterior;
import capaModelo.FechaSistema;
import capaModelo.MotivoAnulacionPedido;
import capaModelo.Pedido;
import capaModelo.PedidoDescuento;
import capaModelo.PedidoFormaPago;
import capaModelo.Pregunta;
import capaModelo.Producto;
import capaModelo.ProductoModificadorCon;
import capaModelo.ProductoModificadorSin;
import capaModelo.RespuestaPedidoPixel;
import capaModelo.Tienda;
import capaModelo.TipoPedido;
import capaModelo.Usuario;
import interfazGrafica.Impresion;
import interfazGrafica.Sesion;
import interfazGrafica.VentPedTomarPedidos;

public class PedidoCtrl {
	
	private boolean auditoria;
	public PedidoCtrl(boolean auditoria)
	{
		this.auditoria = auditoria;
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
		ArrayList<Pregunta> preguntaProducto = PreguntaDAO.obtenerPreguntaProducto(idProducto, auditoria);
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
		int idPedidoNuevo = PedidoDAO.InsertarEncabezadoPedido(idtienda, idcliente, fechaPedido, user, auditoria);
		//El estado cero se tiene la convención q
		PedidoDAO.ActualizarEstadoPedido(idPedidoNuevo, 0, 0,Sesion.getUsuario(), auditoria);
		return(idPedidoNuevo);
		
	}
	
	public int insertarDetallePedido(DetallePedido detPedido)
	{
		int idDetalleoNuevo = DetallePedidoDAO.insertarDetallePedido(detPedido, auditoria);
		return(idDetalleoNuevo);
	}
	
	public boolean eliminarDetallePedido(int idDetallePedido)
	{
		boolean respuesta = DetallePedidoDAO.eliminarDetallePedido(idDetallePedido, auditoria);
		return(respuesta);
	}
	
	public boolean anularDetallePedido(int idDetallePedido, int idMotivoAnulacion)
	{
		boolean respuesta = DetallePedidoDAO.anularDetallePedido(idDetallePedido, idMotivoAnulacion, auditoria);
		return(respuesta);
	}
	
	public boolean validarDetalleMaster(int idDetallePedido)
	{
		boolean respuesta = DetallePedidoDAO.validarDetalleMaster(idDetallePedido, auditoria);
		return(respuesta);
	}
	
	public boolean insertarPedidoFormaPago(double efectivo, double tarjeta, double valorTotal, double cambio, int idPedidoTienda)
	{
		int resIdEfe = 0, resIdTar = 0;
		if(efectivo > 0)
		{
			PedidoFormaPago forEfectivo = new PedidoFormaPago(0,idPedidoTienda,1,valorTotal,efectivo);
			resIdEfe = PedidoFormaPagoDAO.InsertarPedidoFormaPago(forEfectivo, auditoria);
		}
		if(tarjeta > 0)
		{
			PedidoFormaPago forTarjeta = new PedidoFormaPago(0,idPedidoTienda,2,valorTotal,tarjeta);
			resIdTar = PedidoFormaPagoDAO.InsertarPedidoFormaPago(forTarjeta, auditoria);
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
		boolean respuesta = PedidoFormaPagoDAO.existeFormaPago(idPedido, auditoria);
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
		DetallePedidoImpuestoDAO.eliminarDetallePedidoImpuesto(idPedido, auditoria);
		//Realizamos la liquidación de los impuestos para el pedido
		ImpuestoCtrl impCtrl = new ImpuestoCtrl(auditoria);
		impCtrl.liquidarImpuestosPedido(idPedido);
		boolean respuesta = PedidoDAO.finalizarPedido(idPedido, tiempoPedido, idTipoPedido, auditoria);
		Estado estadoIni = EstadoDAO.obtenerEstadoInicial(idTipoPedido, auditoria);
		int idEstadoPostIni = estadoIni.getIdestado();
		PedidoDAO.ActualizarEstadoPedido(idPedido, 0 , idEstadoPostIni,Sesion.getUsuario(), auditoria);
		//En este punto debemos de validar si este estado requiere de impresión
		if(estadoIni.isImpresion())
		{
			String strFactura = generarStrImpresionFactura(idPedido);
			Impresion imp = new Impresion();
			imp.imprimirFactura(strFactura);
		}
		return(respuesta);
	}
	
	public boolean anularPedido(int idPedido, int idMotivoAnulacion)
	{
		//boolean respuesta = DetallePedidoDAO.AnularDetallesPedido(idPedido);
		boolean respuesta = true;
		if(respuesta)
		{
			respuesta = PedidoDAO.anularPedido(idPedido, idMotivoAnulacion, auditoria);
		}
		if(respuesta)
		{
			//Debemos anular todos los detalles pedidos
			ArrayList<DetallePedido> detPedido = DetallePedidoDAO.obtenerDetallePedido(idPedido, auditoria);
			for(int i = 0; i < detPedido.size(); i++)
			{
				DetallePedidoDAO.anularDetallePedido(detPedido.get(i).getIdDetallePedido(), idMotivoAnulacion, auditoria);
			}
			return(true);
		}
		return(false);
	}
	
	public boolean anularPedidoEliminar(int idPedido)
	{
		boolean respuesta = DetallePedidoDAO.eliminarDetallesPedido(idPedido, auditoria);
		if(respuesta)
		{
			respuesta = PedidoDAO.eliminarPedido(idPedido, auditoria);
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
		ArrayList estados = EstadoDAO.obtenerEstado( auditoria);
		return(estados);
	}
	
	public Estado obtenerEstado(int idEstado)
	{
		Estado estado = EstadoDAO.obtenerEstado(idEstado, auditoria);
		return(estado);
	}
	
	public int insertarEstado(Estado estado)
	{
		int idEstadoIns = EstadoDAO.insertarEstado(estado, auditoria);
		return(idEstadoIns);
	}
	
	public boolean eliminarEstado(int idestado)
	{
		boolean respuesta = EstadoDAO.eliminarEstado(idestado, auditoria);
		return(respuesta);
	}
	
	public boolean editarEstado(Estado estado)
	{
		boolean respuesta = EstadoDAO.editarEstado(estado, auditoria);
		return(respuesta);
	}
	
	//ESTADOS ANTERIORES
	
	public ArrayList<EstadoAnterior> obtenerEstadosAnteriores(int idEstado)
	{
		ArrayList<EstadoAnterior> estadosAnt = EstadoAnteriorDAO.obtenerEstadosAnteriores(idEstado, auditoria);
		return(estadosAnt);
	}
	
	public boolean insertarEstadoAnterior(EstadoAnterior estadoAnt)
	{
		boolean respuesta = EstadoAnteriorDAO.insertarEstadoAnterior(estadoAnt, auditoria);
		return(respuesta);
	}
	
	public boolean eliminarEstadoAnterior(EstadoAnterior estadoAnt)
	{
		boolean respuesta = EstadoAnteriorDAO.eliminarEstadoAnterior(estadoAnt, auditoria);
		return(respuesta);
	}
	
	public ArrayList<Estado> obtenerEstadosAnterioresFaltantes(int idEstado)
	{
		ArrayList<EstadoAnterior> estadosAnt = EstadoAnteriorDAO.obtenerEstadosAnteriores(idEstado, auditoria);
		ArrayList<Estado> estados = EstadoDAO.obtenerTodosEstado( auditoria);
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
			ArrayList<EstadoPosterior> estadosPos = EstadoPosteriorDAO.obtenerEstadosPos(idEstado, auditoria);
			return(estadosPos);
		}
		
		public boolean insertarEstadoPosterior(EstadoPosterior estadoPos)
		{
			boolean respuesta = EstadoPosteriorDAO.insertarEstado(estadoPos, auditoria);
			return(respuesta);
		}
		
		public boolean eliminarEstadoPosterior(EstadoPosterior estadoPos)
		{
			boolean respuesta = EstadoPosteriorDAO.eliminarEstadoPosterior(estadoPos, auditoria);
			return(respuesta);
		}
		
		public ArrayList<Estado> obtenerEstadosPosterioresFaltantes(int idEstado)
		{
			ArrayList<EstadoPosterior> estadosPos = EstadoPosteriorDAO.obtenerEstadosPos(idEstado, auditoria);
			ArrayList<Estado> estados = EstadoDAO.obtenerTodosEstado(auditoria);
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
			ArrayList tiposPedido = TipoPedidoDAO.obtenerTiposPedido( auditoria);
			return(tiposPedido);
		}
		
		public int insertarTipoPedido(TipoPedido tipPed)
		{
			int idTipoIns = TipoPedidoDAO.insertarTipoPedido(tipPed, auditoria);
			return(idTipoIns);
		}
		
		public  boolean eliminarTipoPedido(int idTipoPedido)
		{
			boolean respuesta = TipoPedidoDAO.eliminarTipoPedido(idTipoPedido, auditoria);
			return(respuesta);
		}
		
		public  boolean EditarTipoPedido(TipoPedido tipPedidoEditar)
		{
			boolean respuesta = TipoPedidoDAO.EditarTipoPedido(tipPedidoEditar, auditoria);
			return(respuesta);
		}
		public TipoPedido obtenerTipoPedido(int idTipoPedido)
		{
			TipoPedido tipPedCon = TipoPedidoDAO.obtenerTipoPedido(idTipoPedido, auditoria);
			return(tipPedCon);
		}
		
		public ArrayList<TipoPedido> obtenerTiposPedidoNat()
		{
			ArrayList<TipoPedido> tiposPedidoNat = TipoPedidoDAO.obtenerTiposPedidoNat(auditoria);
			return(tiposPedidoNat);
		}
		
		public FechaSistema obtenerFechasSistema()
		{
			FechaSistema fecha = TiendaDAO.obtenerFechasSistema( auditoria);
			return(fecha);
		}
		
		public boolean isSistemaAperturado()
		{
			FechaSistema fecha = TiendaDAO.obtenerFechasSistema( auditoria);
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
			FechaSistema fechaSistema = TiendaDAO.obtenerFechasSistema(auditoria);
			ArrayList pedidos = new ArrayList();
			if(sePuedeFacturar(fechaSistema))
			{
				pedidos = PedidoDAO.obtenerPedidosPorTipo(idTipoPedido, fechaSistema.getFechaApertura(), auditoria);
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
		
		public ArrayList obtenerPedidosTableSinFinales()
		{
			FechaSistema fechaSistema = TiendaDAO.obtenerFechasSistema(auditoria);
			ArrayList pedidos = new ArrayList();
			if(sePuedeFacturar(fechaSistema))
			{
				pedidos = PedidoDAO.obtenerPedidosTable(fechaSistema.getFechaApertura(), auditoria);
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
		
		/**
		 * Método que se encargará de retornar todos los pedidos a una fecha determinada incluyendo los estados finales
		 * @return
		 */
		public ArrayList obtenerPedidosTableConFinales()
		{
			FechaSistema fechaSistema = TiendaDAO.obtenerFechasSistema( auditoria);
			ArrayList pedidos = new ArrayList();
			if(sePuedeFacturar(fechaSistema))
			{
				pedidos = PedidoDAO.obtenerPedidosTableConFinales(fechaSistema.getFechaApertura(), auditoria);
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
			Estado estadoPedido = PedidoDAO.obtenerEstadoPedido(idPedidoTienda, auditoria);
			return(estadoPedido);
		}
		
		public boolean ActualizarEstadoPedido(int idPedido, int idEstadoAnterior, int idEstadoPosterior, String usuario)
		{
			boolean respuesta = PedidoDAO.ActualizarEstadoPedido(idPedido, idEstadoAnterior, idEstadoPosterior, usuario, auditoria);
			return(respuesta);
		}
		
		public boolean tieneEstadoFinal( int idTipoPedido, int idEstado)
		{
			boolean respuesta = EstadoDAO.tieneEstadoFinal(idTipoPedido, idEstado, auditoria);
			return(respuesta);
		}
		
		public boolean tieneEstadoInicial( int idTipoPedido, int idEstado)
		{
			boolean respuesta = EstadoDAO.tieneEstadoInicial(idTipoPedido, idEstado, auditoria);
			return(respuesta);
		}
	
		public int obtenerEstadoInicial( int idTipoPedido)
		{
			Estado estado = EstadoDAO.obtenerEstadoInicial(idTipoPedido, auditoria);
			int respuesta = estado.getIdestado();
			return (respuesta);
		}
		
		public int obtenerEstadoFinal( int idTipoPedido)
		{
			int respuesta = EstadoDAO.obtenerEstadoFinal(idTipoPedido, auditoria);
			return (respuesta);
		}
		
		public ArrayList obtenerHistoriaEstadoPedido(int idPedido)
		{
			ArrayList historiaPedido = EstadoDAO.obtenerHistoriaEstadoPedido(idPedido, auditoria);
			return(historiaPedido);
		}
		
		//Más temas de estados
		
		public boolean esEstadoFinal(int idTipoPedido, int idEstado)
		{
			boolean respuesta = EstadoDAO.esEstadoFinal(idTipoPedido, idEstado, auditoria);
			return(respuesta);
		}
		
		public boolean esEstadoInicial(int idTipoPedido, int idEstado)
		{
			boolean respuesta = EstadoDAO.esEstadoInicial(idTipoPedido, idEstado, auditoria);
			return(respuesta);
		}
		//PedidoDescuento
		
		public boolean insertarPedidoDescuento(PedidoDescuento descuento)
		{
			boolean respuesta = PedidoDescuentoDAO.insertarPedidoDescuento(descuento, auditoria);
			return(respuesta);
		}
		
		public boolean eliminarPedidoDescuento(int idPedido)
		{
			boolean respuesta = PedidoDescuentoDAO.eliminarPedidoDescuento(idPedido, auditoria);
			return(respuesta);
		}
		
		public PedidoDescuento obtenerPedidoDescuento(int idPedido)
		{
			PedidoDescuento descuento = PedidoDescuentoDAO.obtenerPedidoDescuento(idPedido, auditoria);
			return(descuento);
		}
		
		public boolean existePedidoDescuento(int idPedido)
		{
			boolean respuesta = PedidoDescuentoDAO.existePedidoDescuento(idPedido, auditoria);
			return(respuesta);
		}
		
		public double obtenerTotalBrutoPedido(int idpedido)
		{
			double valorBruto = PedidoDAO.obtenerTotalBrutoPedido(idpedido, auditoria);
			return(valorBruto);
		}
		
		public ArrayList<DetallePedido> obtenerDetallePedido(int idPedido)
		{
			ArrayList<DetallePedido> detPedidos = DetallePedidoDAO.obtenerDetallePedido(idPedido, auditoria);
			detPedidos = ordenarDetallePedido(detPedidos);
			return(detPedidos);
		}
		
		public ArrayList<DetallePedido> obtenerDetallePedidoPintar(int idPedido)
		{
			ArrayList<DetallePedido> detPedidos = DetallePedidoDAO.obtenerDetallePedidoPintar(idPedido, auditoria);
			detPedidos = ordenarDetallePedido(detPedidos);
			return(detPedidos);
		}
		
		public Cliente obtenerClientePedido(int idPedido)
		{
			Cliente cliente = PedidoDAO.obtenerClientePedido(idPedido, auditoria);
			return(cliente);
		}
		
		public boolean eliminarPedidoFormaPago(int idPedido)
		{
			boolean respuesta = PedidoFormaPagoDAO.eliminarPedidoFormaPago(idPedido, auditoria);
			return(respuesta);
		}
		
		public int obtenerTipoDePedido(int idPedido)
		{
			int idTipoPedido = PedidoDAO.obtenerTipoPedido(idPedido, auditoria);
			return(idTipoPedido);
			
		}
		
		/**
		 * Método que se encarga de calcular el tiempo del pedido y traerlo como un String para mostrarlos en los maestros
		 * de pedidos
		 * @param idPedido
		 * @return
		 */
		public String calcularTiempoPedido(int idPedido)
		{
			String respuesta = "";
			ArrayList hisEstPedido = EstadoDAO.obtenerHistoriaEstadoPedido(idPedido, auditoria);
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
		public ArrayList obtenerTotalesPedidosPorTipo(String fechaPedido)
		{
			ArrayList resumenTotTipoPedido = PedidoDAO.obtenerTotalesPedidosPorTipo(fechaPedido, auditoria);
			return(resumenTotTipoPedido);
		}
		
		//Información relacionada con los estados para los tipos de empleados
		
		public ArrayList<Estado> obtenerEstFalTipoEmpleado(int idTipoEmpleado)
		{
			ArrayList<Estado> estadosTipEmpleado = TipoEmpleadoEstadoDAO.obtenerEstadosTipoEmpleado(idTipoEmpleado, auditoria);
			ArrayList<Estado> estados = EstadoDAO.obtenerTodosEstado( auditoria);
			ArrayList<Estado> estadosFaltantes = new ArrayList();
			for(int i = 0; i < estados.size(); i++)
			{
				boolean encontrado = false;
				Estado estadoTemp = estados.get(i);
				for(int j = 0; j < estadosTipEmpleado.size(); j++)
				{
					Estado estTipEmp = estadosTipEmpleado.get(j);
					if(estTipEmp.getIdestado() == estadoTemp.getIdestado())
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
		
		public ArrayList<Estado> obtenerEstadosTipoEmpleado(int idTipoEmpleado)
		{
			ArrayList<Estado> estTipEmp = TipoEmpleadoEstadoDAO.obtenerEstadosTipoEmpleado(idTipoEmpleado, auditoria);
			return(estTipEmp);
		}
		
		public boolean insertarTipoEmpleadoEstado(int idTipoEmpleado, int idEstado)
		{
			boolean respuesta = TipoEmpleadoEstadoDAO.insertarTipoEmpleadoEstado(idTipoEmpleado, idEstado, auditoria);
			return(respuesta);
		}
		
		public boolean eliminarTipoEmpleadoEstado(int idTipoEmpleado, int idEstado)
		{
			boolean respuesta = TipoEmpleadoEstadoDAO.eliminarTipoEmpleadoEstado(idTipoEmpleado, idEstado, auditoria);
			return(respuesta);
		}
		
		public ArrayList obtenerPedidosVentanaComanda(int idEmpleado)
		{
			FechaSistema fechaSistema = TiendaDAO.obtenerFechasSistema( auditoria);
			ArrayList pedidos = new ArrayList();
			if(sePuedeFacturar(fechaSistema))
			{
				//Obtenemos el tipo de Usuario del usuario logueado
				Usuario usuActual = UsuarioDAO.obtenerEmpleado(idEmpleado, auditoria);
				int idTipoEmpActual = usuActual.getidTipoEmpleado();
				pedidos = PedidoDAO.obtenerPedidosVentanaComanda(fechaSistema.getFechaApertura(), idTipoEmpActual, auditoria );
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
		
		public ArrayList obtenerPedidosVentanaComandaTipPed(int idEmpleado, int idTipoPedido)
		{
			FechaSistema fechaSistema = TiendaDAO.obtenerFechasSistema( auditoria);
			ArrayList pedidos = new ArrayList();
			if(sePuedeFacturar(fechaSistema))
			{
				//Obtenemos el tipo de Usuario del usuario logueado
				Usuario usuActual = UsuarioDAO.obtenerEmpleado(idEmpleado, auditoria);
				int idTipoEmpActual = usuActual.getidTipoEmpleado();
				pedidos = PedidoDAO.obtenerPedidosVentanaComandaTipPed(fechaSistema.getFechaApertura(), idTipoEmpActual, idTipoPedido, auditoria );
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
		
		public boolean tieneModificadorCon(int idProducto)
		{
			boolean respuesta = ProductoModificadorConDAO.tieneModificadorCon(idProducto, auditoria);
			return(respuesta);
		}
		
		public boolean detalleTieneModificadorCon(int idDetalle)
		{
			DetallePedido detPedido = DetallePedidoDAO.obtenerUnDetallePedido(idDetalle, auditoria);
			boolean respuesta = ProductoModificadorConDAO.tieneModificadorCon(detPedido.getIdProducto(), auditoria);
			return(respuesta);
		}
		
		public boolean tieneModificadorSin(int idProducto)
		{
			boolean respuesta = ProductoModificadorSinDAO.tieneModificadorSin(idProducto, auditoria);
			return(respuesta);
		}
		
		public boolean detalleTieneModificadorSin(int idDetalle)
		{
			DetallePedido detPedido = DetallePedidoDAO.obtenerUnDetallePedido(idDetalle, auditoria);
			boolean respuesta = ProductoModificadorSinDAO.tieneModificadorSin(detPedido.getIdProducto(), auditoria);
			return(respuesta);
		}
		
		//Métodos para retornar los modificadores dado un idDetallePedido
		public ArrayList<ProductoModificadorCon> obtenerModificadoresCon(int idDetalle)
		{
			DetallePedido detPedido = DetallePedidoDAO.obtenerUnDetallePedido(idDetalle, auditoria);
			ArrayList<ProductoModificadorCon> prodMods = ProductoModificadorConDAO.obtenerProdModificadoresCon(detPedido.getIdProducto(), auditoria);
			return(prodMods);
		}
		
		public ArrayList<ProductoModificadorSin> obtenerModificadoresSin(int idDetalle)
		{
			DetallePedido detPedido = DetallePedidoDAO.obtenerUnDetallePedido(idDetalle, auditoria);
			ArrayList<ProductoModificadorSin> prodMods = ProductoModificadorSinDAO.obtenerProdModificadoresSin(detPedido.getIdProducto(), auditoria);
			return(prodMods);
		}
		
		public int obtenerMaxModificadorCon(int idDetalle)
		{
			DetallePedido detPedido = DetallePedidoDAO.obtenerUnDetallePedido(idDetalle, auditoria);
			Producto pro = ProductoDAO.obtenerProducto(detPedido.getIdProducto(), auditoria);
			return(pro.getModificadorCon());
		}
		
		public int obtenerMaxModificadorSin(int idDetalle)
		{
			DetallePedido detPedido = DetallePedidoDAO.obtenerUnDetallePedido(idDetalle, auditoria);
			Producto pro = ProductoDAO.obtenerProducto(detPedido.getIdProducto(), auditoria);
			return(pro.getModificadorSin());
		}
		
		public int obtenerIdDetalleMaster(int idDetallePedido)
		{
			int respuesta = DetallePedidoDAO.obtenerIdDetalleMaster(idDetallePedido, auditoria);
			return(respuesta);
		}
		
		public ArrayList<DetallePedido> ordenarDetallePedido(ArrayList<DetallePedido> detallesPedido)
		{
			ArrayList<DetallePedido> ordenado = new ArrayList();
            ArrayList<DetallePedido> paraOrdenar = (ArrayList) detallesPedido.clone();
            ArrayList<DetallePedido>  mods =  new ArrayList();
            boolean tieneMod = false;
            for(int i = 0; i < paraOrdenar.size(); i++)
            {
            	DetallePedido eleBuscar = paraOrdenar.get(i);
            	int idDetalleBuscarMod = eleBuscar.getIdDetallePedido();
            	for(int j = i + 1;  j < paraOrdenar.size(); j++)
            	{
            		DetallePedido eleBuscarMod = paraOrdenar.get(j);
            		if(eleBuscarMod.getIdDetalleModificador() == idDetalleBuscarMod)
                    {
            			tieneMod = true;
            			mods.add(eleBuscarMod);
            			paraOrdenar.remove(j);
            			j--;
                    }
            	}
            	ordenado.add(eleBuscar);
            	if(tieneMod)
            	{
            		for(int k = 0; k < mods.size(); k++)
            		{
            			 ordenado.add(mods.get(k));
            		}
            		tieneMod = false;
            		mods = new ArrayList();
            	}
            }
            return(ordenado);
		}
		
		public Pedido obtenerPedido(int idPedidoTienda)
		{
			Pedido pedCon = PedidoDAO.obtenerPedido(idPedidoTienda, auditoria);
			return(pedCon);
		}
		
		public String generarStrImpresionFactura(int idPedido)
		{
			
			//Obtenemos la tienda sobre la que estamos trabajando
			Tienda tienda = TiendaDAO.obtenerTienda( auditoria);
			ArrayList<DetallePedido> detPedido = obtenerDetallePedidoPintar(idPedido);
			DetallePedido detTemp;
			String factura = " " + tienda.getNombretienda() +"\n"
					+ tienda.getDireccion() +"\n"
					+ "tel:"+tienda.getTelefono() +"\n"
					+ tienda.getRazonSocial() +"\n"
					+ tienda.getIdentificacion()+ "\n"
					+ tienda.getTipoContribuyente() + "\n"
					+ tienda.getResolucion() + "\n"
					+ tienda.getFechaResolucion()+ "\n"
					+ "    Desde P3 0 Hasta P3 50000\n"
					+ tienda.getUbicacion() + "\n"
					+ "    Mesa\n"
					+ "    Factura de Venta:"+idPedido +"\n"
		            + "    Usuario\n"
		            + "    ======================================\n"
		            + "      Cant    Descripcion            Costo   \n"
		            + "    ======================================\n";
			double cantidad;
			for(int i = 0; i < detPedido.size(); i++)
			{
				detTemp = detPedido.get(i);
				cantidad = detTemp.getValorTotal();
				if ((cantidad - Math.floor(cantidad)) == 0) 
				{
					factura = factura + "  " + detTemp.getCantidad() + "   " + detTemp.getDescCortaProducto() + "    " + "\n";
				}
				else
				{
					factura = factura + "  " + detTemp.getCantidad() + "   " + detTemp.getDescCortaProducto() + "    " + detTemp.getValorTotal() + "\n";
				}
				
			}
			Pedido pedImpFac = obtenerPedido(idPedido);
			factura = factura + "    ======================================\n";
			factura = factura + "    TOTAL BRUTO:      " + pedImpFac.getValorbruto()+"\n";
			factura = factura + "    IMPUESTO   :      " + pedImpFac.getImpuesto()+"\n";
			factura = factura + "    ======================================\n";
			factura = factura  + "   TOTAL : " + pedImpFac.getValorneto() + "\n";
			factura = factura  + "   CAMBIO : " + "\n";
			factura = factura  + "   CLIENTE : " + pedImpFac.getNombreCliente() + "\n";
			factura = factura  + "   DIR CLIENTE : " + pedImpFac.getDirCliente() + "\n";
			factura = factura  + "   !FELICITACIONES! HAZ COMPRADO LA MEJOR " + "\n";
			factura = factura  + "   PIZZA DE LA CIUDAD " + "\n";
			factura = factura  + "   PQRS - pizzaamericanacolombia@gmail.com " + "\n";
			factura = factura  + "    GRACIAS POR SU COMPRA...\n"
		            + "                ******::::::::*******"
		            + "\n\n\n\n\n\n\n           "
		            + "\n           ";
			return(factura);
		}
		
		public ArrayList<MotivoAnulacionPedido> obtenerMotivosAnulacion()
		{
			ArrayList<MotivoAnulacionPedido> motAnu = MotivoAnulacionPedidoDAO.obtenerMotivosAnulacion( auditoria);
			return(motAnu);
		}
		
		public ArrayList<Estado> obtenerTodosEstado()
		{
			ArrayList <Estado> estados = EstadoDAO.obtenerTodosEstado(auditoria);
			return (estados);
		}
		
		
		
}
