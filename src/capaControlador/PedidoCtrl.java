package capaControlador;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import capaDAO.DetallePedidoDAO;
import capaDAO.DetallePedidoImpuestoDAO;
import capaDAO.EgresoDAO;
import capaDAO.EstadoAnteriorDAO;
import capaDAO.EstadoDAO;
import capaDAO.EstadoPosteriorDAO;
import capaDAO.GeneralDAO;
import capaDAO.ImprimirAdmDAO;
import capaDAO.IngresoDAO;
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
import capaModelo.AnulacionPedido;
import capaModelo.Cliente;
import capaModelo.Correo;
import capaModelo.DetallePedido;
import capaModelo.DetallePedidoPixel;
import capaModelo.Egreso;
import capaModelo.Estado;
import capaModelo.EstadoAnterior;
import capaModelo.EstadoPedidoTienda;
import capaModelo.EstadoPosterior;
import capaModelo.FechaSistema;
import capaModelo.FormaPagoIng;
import capaModelo.ImprimirAdm;
import capaModelo.Ingreso;
import capaModelo.ItemInventario;
import capaModelo.MotivoAnulacionPedido;
import capaModelo.Parametro;
import capaModelo.Pedido;
import capaModelo.PedidoDescuento;
import capaModelo.PedidoFormaPago;
import capaModelo.Pregunta;
import capaModelo.Producto;
import capaModelo.ProductoModificadorCon;
import capaModelo.ProductoModificadorSin;
import capaModelo.RespuestaPedidoPixel;
import capaModelo.TiempoPedido;
import capaModelo.Tienda;
import capaModelo.TipoPedido;
import capaModelo.Usuario;
import interfazGrafica.Impresion;
import interfazGrafica.ImpresionBK;
import interfazGrafica.PrincipalLogueo;
import interfazGrafica.Sesion;
import interfazGrafica.VentPedTomarPedidos;
import utilidades.ControladorEnvioCorreo;

public class PedidoCtrl implements Runnable {
	
	private boolean auditoria;
	//Definimos el hilo que paralelizará la inserción de impuestos
	Thread hiloImpuestos;
	Thread hiloInventarios;
	Thread hiloEstadoPedido;
	Thread hiloObservacion;
	//Tendremos un idPedido definido para poder paralelizar
	private int idPedidoActual;
	private int idTipoPedidoActual;
	private int tipoImpresion;
	private ArrayList<DetallePedido> detPedidoNuevo = new ArrayList();
	OperacionesTiendaCtrl operTiendaCtrl = new OperacionesTiendaCtrl(auditoria);
	ParametrosProductoCtrl parProductoCtrl = new ParametrosProductoCtrl(auditoria);
	public boolean esPedidoReabierto = false;
	public boolean imprimeSiReabierto = false;
	public boolean reabiertoAnulado = false;
	public boolean cambioTipoPedido = false;
	public int idEstadoInicialCambio = 0;
	public PedidoCtrl(boolean auditoria)
	{
		this.auditoria = auditoria;
		hiloImpuestos = new Thread(this);
		hiloInventarios = new Thread(this);
		hiloEstadoPedido = new Thread(this);
		hiloObservacion = new Thread(this);
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
	public int InsertarEncabezadoPedido(int idtienda, int idcliente, String fechaPedido, String user, String estacion)
	{
		int idPedidoNuevo = PedidoDAO.InsertarEncabezadoPedido(idtienda, idcliente, fechaPedido, user, estacion, auditoria);
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
	
	public boolean anularDetallePedido(int idDetallePedido, int idMotivoAnulacion, String observacion, String usuarioAnulacion,  String usuarioAutorizo)
	{
		boolean respuesta = DetallePedidoDAO.anularDetallePedido(idDetallePedido, idMotivoAnulacion, observacion, usuarioAnulacion,  usuarioAutorizo, auditoria);
		return(respuesta);
	}
	
	public boolean validarDetalleMaster(int idDetallePedido)
	{
		boolean respuesta = DetallePedidoDAO.validarDetalleMaster(idDetallePedido, auditoria);
		return(respuesta);
	}
	
	public String consultarFormaPago(int idPedido)
	{
		String respuesta = PedidoFormaPagoDAO.consultarFormaPago(idPedido, auditoria);
		return(respuesta);
	}
	
	public ArrayList<String[]> consultarFormaPagoArreglo(int idPedido)
	{
		ArrayList<String[]> formasPagoArreglo = PedidoFormaPagoDAO.consultarFormaPagoArreglo(idPedido, auditoria);
		return(formasPagoArreglo); 
	}
	
	public boolean insertarPedidoFormaPago(double efectivo, double tarjeta, double pagoOnLine, double valorTotal, double cambio, int idPedidoTienda)
	{
		
		int resIdEfe = 0, resIdTar = 0, resIdOnLine = 0;
		//Realizamos una validación especial si el valor del pedido es cero, entonces realizamos la inserción
		if(valorTotal == 0)
		{
			PedidoFormaPago forEfectivo = new PedidoFormaPago(0,idPedidoTienda,1,valorTotal,efectivo,valorTotal);
			resIdEfe = PedidoFormaPagoDAO.InsertarPedidoFormaPago(forEfectivo, auditoria);
			return(true);
		}
		if(efectivo > 0)
		{
			PedidoFormaPago forEfectivo = new PedidoFormaPago(0,idPedidoTienda,1,valorTotal,efectivo, valorTotal);
			resIdEfe = PedidoFormaPagoDAO.InsertarPedidoFormaPago(forEfectivo, auditoria);
		}
		if(tarjeta > 0)
		{
			PedidoFormaPago forTarjeta = new PedidoFormaPago(0,idPedidoTienda,2,valorTotal,tarjeta, valorTotal);
			resIdTar = PedidoFormaPagoDAO.InsertarPedidoFormaPago(forTarjeta, auditoria);
		}
		if(pagoOnLine > 0)
		{
			PedidoFormaPago forOnLine = new PedidoFormaPago(0,idPedidoTienda,3,valorTotal,pagoOnLine, valorTotal);
			resIdOnLine = PedidoFormaPagoDAO.InsertarPedidoFormaPago(forOnLine, auditoria);
		}
		if(resIdEfe > 0 || resIdTar > 0|| resIdOnLine > 0)
		{
			return(true);
		}else
		{
			return(false);
		}
		
	}
	
	public boolean insertarPedidoFormaPago(ArrayList<FormaPagoIng> formasPagoIng, double valorTotal, double cambio, int idPedidoTienda)
	{
		//Hacemos validación si el pedido tiene un valor de cero igual se inserta como forma de pago efectivo y en cero
		if(valorTotal == 0)
		{
			PedidoFormaPago forEfectivo = new PedidoFormaPago(0,idPedidoTienda,1,valorTotal,0,0);
			PedidoFormaPagoDAO.InsertarPedidoFormaPago(forEfectivo, auditoria);
			return(true);
		}
		//Realizamos la inserción de la forma de pago con base en ArrayList recibido
		for(int i = 0 ; i < formasPagoIng.size(); i++)
		{
			FormaPagoIng formaTemp = formasPagoIng.get(i);
			if(formaTemp.getValorPago() > 0)
			{
				PedidoFormaPago pedFormaPago = new PedidoFormaPago(0, idPedidoTienda,formaTemp.getIdFormaPago(), valorTotal, formaTemp.getValorPago(), formaTemp.getValorDisminuido());
				PedidoFormaPagoDAO.InsertarPedidoFormaPago(pedFormaPago, auditoria);
			}
		}
		return(true);
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
	public boolean finalizarPedido(int idPedido, double tiempoPedido, int idTipoPedido, int tipoImpresion, ArrayList<DetallePedido> detPedidoNuevo,boolean  esPedidoReabierto, boolean imprimeSiReabierto, boolean reabiertoAnulado, boolean cambioTipoPedido, int idEstadoInicialCambio)
	{
		//Llevamos la variable tipo Impresión recibida como parámetro como un valor de la clase
		this.tipoImpresion = tipoImpresion;
		this.detPedidoNuevo = detPedidoNuevo;
		this.esPedidoReabierto = esPedidoReabierto;
		this.imprimeSiReabierto = imprimeSiReabierto;
		this.reabiertoAnulado = reabiertoAnulado;
		this.cambioTipoPedido = cambioTipoPedido;
		this.idEstadoInicialCambio = idEstadoInicialCambio;
		//Antes de finalizar pedido realizaremos el cálculo de los impuestos
		//Clareamos los impuestos liquidados, si existe con anterioridad
		DetallePedidoImpuestoDAO.eliminarDetallePedidoImpuesto(idPedido, auditoria);
		//Realizamos la liquidación de los impuestos para el pedido
		//Podemos paralelizar con otro hilo el descuento de los impuestos, para lo cual aqui lo haremos
		//Definimos para la clase cual es el idPedido bajo  el cual trabajaremos
		this.idPedidoActual = idPedido;
		this.idTipoPedidoActual = idTipoPedido;
		//Arrancamos el hilo que se encarga de descontar inventarios
		hiloImpuestos.start();
		hiloObservacion.start();
		//Arrancamos el hilo que hace descuento de inventarios
		hiloInventarios.start();
		while(hiloImpuestos.isAlive())
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		boolean respuesta = PedidoDAO.finalizarPedido(idPedido, tiempoPedido, idTipoPedido, auditoria);
		//Arrancamos hilo que se encarga de dejar consistente el estado del pedido
		hiloEstadoPedido.start();
		//En este punto debemos de validar si este estado requiere de impresión
		return(true);
	}
	
	/**
	 * Creamos un método de finalizar pedido para la adecuación de ambientes
	 * @param idPedido
	 * @param tiempoPedido
	 * @param idTipoPedido
	 * @return
	 */
	public boolean finalizarPedidoAdecuacion(int idPedido, double tiempoPedido, int idTipoPedido)
	{
		//Antes de finalizar pedido realizaremos el cálculo de los impuestos
		//Clareamos los impuestos liquidados, si existe con anterioridad
		DetallePedidoImpuestoDAO.eliminarDetallePedidoImpuesto(idPedido, auditoria);
		//Realizamos la liquidación de los impuestos para el pedido
		//Podemos paralelizar con otro hilo el descuento de los impuestos, para lo cual aqui lo haremos
		//Definimos para la clase cual es el idPedido bajo  el cual trabajaremos
		this.idPedidoActual = idPedido;
		this.idTipoPedidoActual = idTipoPedido;
		//Arrancamos el hilo que se encarga de descontar inventarios
		hiloImpuestos.start();
		while(hiloImpuestos.isAlive())
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		boolean respuesta = PedidoDAO.finalizarPedido(idPedido, tiempoPedido, idTipoPedido, auditoria);
		//Arrancamos hilo que se encarga de dejar consistente el estado del pedido
		return(true);
	}
	
	/**
	 * Método que realiza anulación de un pedido, teniendo en cuenta que tiene un pedido y detalles de pedido
	 * @param idPedido, se recibe la identificación del pedido que se desea eliminar
	 * @param idMotivoAnulacion Se recibe un motivo de anulación para realizar el descuento o no de inventarios
	 * @return se retorna un valor boolean con el respultado del proceso
	 */
	public boolean anularPedido(int idPedido, int idMotivoAnulacion, String observacion, String usuarioAnulacion,  String usuarioAutorizo)
	{
		//boolean respuesta = DetallePedidoDAO.AnularDetallesPedido(idPedido);
		boolean respuesta = true;
		if(respuesta)
		{
			// 05-04/2019 Realizamos comentario de esta linea, la idea es que el pedido en su completitud nunca se 
			//anule para así no tener problemas con su visualización.
			//respuesta = PedidoDAO.anularPedido(idPedido, idMotivoAnulacion, observacion,  auditoria);
		}
		if(respuesta)
		{
			//Debemos anular todos los detalles pedidos
			ArrayList<DetallePedido> detPedido = DetallePedidoDAO.obtenerDetallePedido(idPedido, auditoria);
			for(int i = 0; i < detPedido.size(); i++)
			{
				//Incluimos una validación para que no repise algo que ya está anulado
				if(!detPedido.get(i).getEstado().equals(new String("A")))
				{
					DetallePedidoDAO.anularDetallePedido(detPedido.get(i).getIdDetallePedido(), idMotivoAnulacion, observacion, usuarioAnulacion,  usuarioAutorizo,   auditoria);
				}
			}
			return(true);
		}
		return(false);
	}
	
	/**
	 * Método que nos servirá para eliminar todos aquellos detalles pedidos que ante una anulacion no han sido descontados del
	 * inventario
	 * @param idPedido
	 * @return
	 */
	public boolean eliminarDetalleAnulacion(int idPedido)
	{
		boolean respuesta = true;
			//Debemos anular todos los detalles pedidos
			ArrayList<DetallePedido> detPedido = DetallePedidoDAO.obtenerDetallePedido(idPedido, auditoria);
			for(int i = 0; i < detPedido.size(); i++)
			{
				//Incluimos una validación para que no repise algo que ya está anulado
				if(!detPedido.get(i).getDescargoInventario().equals(new String("S")))
				{
					eliminarDetallePedido(detPedido.get(i).getIdDetallePedido());
				}
			}
			return(respuesta);
	}
	
	/**
	 * Método qeu se encarga de anular un pedido que no tiene detalle pedido
	 * @param idPedido
	 * @return
	 */
	public boolean anularPedidoSinDetalle(int idPedido)
	{
		boolean respuesta = true;
		if(respuesta)
		{
			//Realizamos solo la eliminación de pedido el idMotivoAnulacion, lo quemamos en este momento deberemos
			//tomada de un variable qeu se carga al tomar pedidos
			respuesta = PedidoDAO.anularPedido(idPedido, 2, "", auditoria);
		}
		return(respuesta);
	}
	
	public boolean quitarAnulacionPedido(int idPedido)
	{
		boolean respuesta = true;
		if(respuesta)
		{
			//Realizamos solo la eliminación de pedido el idMotivoAnulacion, lo quemamos en este momento deberemos
			//tomada de un variable qeu se carga al tomar pedidos
			respuesta = PedidoDAO.quitarAnulacionPedido(idPedido,auditoria);
		}
		return(respuesta);
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
	
	//Método que soporta la anulación del pedido en medio de la primera toma, borra los detalles pero no el encabezado del pedido
	public boolean anularBorrarDetallePedido(int idPedido)
	{
		boolean respuesta = DetallePedidoDAO.eliminarDetallesPedido(idPedido, auditoria);
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
	
	//ESTADOS NORMALES
	
	public ArrayList<Estado> obtenerEstadosDiferentes(int idEstado)
	{
		ArrayList<Estado> estados =  EstadoDAO.obtenerEstadosDiferentes(idEstado, auditoria);
		return(estados);
		
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
			ParametrosCtrl parCtrl = new ParametrosCtrl(PrincipalLogueo.habilitaAuditoria);
			Parametro parametro = parCtrl.obtenerParametro("INDICADORCIERRE");
			String fechaParametro = parametro.getValorTexto();
			if(fechaSistema.equals(fechaParametro))
			{
				return(false);
			}
			return(true);
		}
		
		public ArrayList obtenerPedidosPorHoras(String fechaPedido)
		{
			ArrayList pedidos = PedidoDAO.obtenerPedidosPorHoras(fechaPedido,auditoria);
			return(pedidos);
		}
		
		public ArrayList obtenerPedidosPorTipo(int idTipoPedido)
		{
			FechaSistema fechaSistema = TiendaDAO.obtenerFechasSistema(auditoria);
			ArrayList pedidos = new ArrayList();
			if(sePuedeFacturar(fechaSistema))
			{
				pedidos = PedidoDAO.obtenerPedidosPorTipo(idTipoPedido, fechaSistema.getFechaApertura(), auditoria);
				int idPedido = 0;
				//Obtenemos los tiempos de Pedido
				ArrayList<TiempoPedido> tiemposPedido = calcularTiemposPedidos(fechaSistema.getFechaApertura());
				TiempoPedido tiempoPedidoTemp = new TiempoPedido(0,"");
				String tiempoPedido = "";
				for(int i = 0 ; i < pedidos.size(); i++)
				{
					String[]fila = (String[]) pedidos.get(i);
					idPedido =Integer.parseInt(fila[0]);
					// Recorremos el ArrayList de los tiempos pedidos.
					for(int j = 0; j < tiemposPedido.size(); j++)
					{
						tiempoPedidoTemp = tiemposPedido.get(j);
						// SI el pedido es igual al que estamos procesando extraemos el tiempo del pedido
						if(tiempoPedidoTemp.getIdPedidoTienda() == idPedido)
						{
							tiempoPedido = tiempoPedidoTemp.getTiempoPedido();
							break;
						}
					}
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
				//Obtenemos los tiempos de Pedido
				ArrayList<TiempoPedido> tiemposPedido = calcularTiemposPedidos(fechaSistema.getFechaApertura());
				TiempoPedido tiempoPedidoTemp = new TiempoPedido(0,"");
				String tiempoPedido = "";
				for(int i = 0 ; i < pedidos.size(); i++)
				{
					String[]fila = (String[]) pedidos.get(i);
					idPedido =Integer.parseInt(fila[0]);
					for(int j = 0; j < tiemposPedido.size(); j++)
					{
						tiempoPedidoTemp = tiemposPedido.get(j);
						// SI el pedido es igual al que estamos procesando extraemos el tiempo del pedido
						if(tiempoPedidoTemp.getIdPedidoTienda() == idPedido)
						{
							tiempoPedido = tiempoPedidoTemp.getTiempoPedido();
							break;
						}
					}
					fila[fila.length-1] = tiempoPedido;
					pedidos.set(i, fila);
				}
			}
			return(pedidos);
		}
		
		
		public String obtenerPedidosEmpacadosDomicilio()
		{
			FechaSistema fechaSistema = TiendaDAO.obtenerFechasSistema(auditoria);
			ArrayList pedidos = new ArrayList();
			if(sePuedeFacturar(fechaSistema))
			{
				pedidos = PedidoDAO.obtenerPedidosEmpacadosDomicilio(fechaSistema.getFechaApertura(),auditoria);
				int idPedido = 0;
				//Obtenemos los tiempos de Pedido
				ArrayList<TiempoPedido> tiemposPedido = calcularTiemposPedidos(fechaSistema.getFechaApertura());
				TiempoPedido tiempoPedidoTemp = new TiempoPedido(0,"");
				String tiempoPedido = "";
				//Adicionamos los tiempos pedidos
				for(int i = 0 ; i < pedidos.size(); i++)
				{
					String[]fila = (String[]) pedidos.get(i);
					idPedido =Integer.parseInt(fila[0]);
					for(int j = 0; j < tiemposPedido.size(); j++)
					{
						tiempoPedidoTemp = tiemposPedido.get(j);
						// SI el pedido es igual al que estamos procesando extraemos el tiempo del pedido
						if(tiempoPedidoTemp.getIdPedidoTienda() == idPedido)
						{
							tiempoPedido = tiempoPedidoTemp.getTiempoPedido();
							break;
						}
					}
					fila[fila.length-1] = tiempoPedido;
					pedidos.set(i, fila);
				}
			}
			//Luego de esto recorreremos el ArrayList de pedidos para formatearlo en JSON
			JSONArray listJSON = new JSONArray();
			JSONObject cadaPedidoJSON = new JSONObject();
			for(int i = 0; i < pedidos.size(); i++)
			{
				
				cadaPedidoJSON = new JSONObject();
				String[] fila =(String[]) pedidos.get(i);
				cadaPedidoJSON.put("idpedidotienda", fila[0]);
				cadaPedidoJSON.put("fechapedido", fila[1]);
				cadaPedidoJSON.put("nombres", fila[2]);
				cadaPedidoJSON.put("tipopedido", fila[3]);
				cadaPedidoJSON.put("estadopedido", fila[4]);
				cadaPedidoJSON.put("direccion", fila[5]);
				cadaPedidoJSON.put("idtipopedido", fila[6]);
				cadaPedidoJSON.put("idestado", fila[7]);
				cadaPedidoJSON.put("latitud", fila[8]);
				cadaPedidoJSON.put("longitud", fila[9]);
				cadaPedidoJSON.put("tiempopedido", fila[8]);
				listJSON.add(cadaPedidoJSON);
			}
			return(listJSON.toJSONString());
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
				//Obtenemos los tiempos de Pedido
				ArrayList<TiempoPedido> tiemposPedido = calcularTiemposPedidos(fechaSistema.getFechaApertura());
				TiempoPedido tiempoPedidoTemp = new TiempoPedido(0,"");
				String tiempoPedido = "";
				for(int i = 0 ; i < pedidos.size(); i++)
				{
					String[]fila = (String[]) pedidos.get(i);
					idPedido =Integer.parseInt(fila[0]);
					for(int j = 0; j < tiemposPedido.size(); j++)
					{
						tiempoPedidoTemp = tiemposPedido.get(j);
						// SI el pedido es igual al que estamos procesando extraemos el tiempo del pedido
						if(tiempoPedidoTemp.getIdPedidoTienda() == idPedido)
						{
							tiempoPedido = tiempoPedidoTemp.getTiempoPedido();
							break;
						}
					}
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
		
		/**
		 * Método que se encarga de la actualización de un estado en los pedidos, para estados posteriores o anteriores
		 * @param idPedido La identificación del pedido qeu se va a cambiar de estado.
		 * @param idEstadoAnterior El estado anterior del cual parte el pedido
		 * @param idEstadoPosterior El estado posterior para el cual va el producto
		 * @param usuario Usuario que está realizando el cambio de estado
		 * @param estadoPosterior parámetro booleano que indica si el pedido está avanzando de estado o retrocedieno de estasdo
		 * @return Se retorna un valor booleano indicando como resulto el proceso.
		 */
		public boolean ActualizarEstadoPedido(int idPedido, int idEstadoAnterior, int idEstadoPosterior, String usuario, boolean estadoPosterior, int idDomiciliario, boolean salidaDomiciliario)
		{
			if(estadoPosterior)
			{
				//Debemos de validar si el estado Posterior es Ruta Domicilio, en cuyo caso debe asignar 
				boolean esRutDom = EstadoDAO.esEstadoRutaDomicilio(idEstadoPosterior,auditoria);
				//EN caso de ser ruta domicilio, deberemos de asignarle al pedido el domiciliario
				if(esRutDom)
				{
					boolean pedAct = PedidoDAO.actualizarDomiciliarioPedido(idPedido, idDomiciliario, auditoria);
				}
			}
			else
			{
				//Debemos de validar si el estado del que viene es Ruta Domicilio, en cuyo caso debe asignar 
				boolean esRutDom = EstadoDAO.esEstadoRutaDomicilio(idEstadoAnterior,auditoria);
				//EN caso de ser ruta domicilio, deberemos dequitarle al pedido el domiciliario
				if(esRutDom)
				{
					boolean pedAct = PedidoDAO.quitarDomiciliarioPedido(idPedido, auditoria);
				}
			}
			boolean respuesta = PedidoDAO.ActualizarEstadoPedido(idPedido, idEstadoAnterior, idEstadoPosterior, usuario, auditoria);
			if(salidaDomiciliario)
			{
				imprimirSalidaDomiciliario(idPedido);
			}
			return(respuesta);
		}
		
		public boolean esEstadoRutaDomicilio( int idEstado)
		{
			boolean respuesta = EstadoDAO.esEstadoRutaDomicilio(idEstado, auditoria);
			return(respuesta);
		}
		
		public boolean actualizarDomiciliarioPedido(int idPedido, int idDomiciliario)
		{
			boolean pedAct = PedidoDAO.actualizarDomiciliarioPedido(idPedido, idDomiciliario, auditoria);
			return(pedAct);
		}
		
		public  boolean quitarDomiciliarioPedido(int idPedido)
		{
			boolean pedAct = PedidoDAO.quitarDomiciliarioPedido(idPedido, auditoria);
			return(pedAct);
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
		//Retornar descuentos de un día
		public ArrayList<PedidoDescuento> obtenerPedidoDescuentoFecha(String fecha)
		{
			ArrayList<PedidoDescuento> descuentosFecha =  PedidoDescuentoDAO.obtenerPedidoDescuentoFecha(fecha, auditoria);
			return(descuentosFecha);
		}
		//Retornar descuentos de un rango de días
		public ArrayList<PedidoDescuento> obtenerPedidoDescuentoRango(String fechaAnterior, String fechaActual)
		{
			ArrayList<PedidoDescuento> descuentosFecha =  PedidoDescuentoDAO.obtenerPedidoDescuentoRango(fechaAnterior, fechaActual, auditoria);
			return(descuentosFecha);
		}
		
		//Retornar anulaciones(que descuentan inventario) de un rango de días
		public ArrayList<AnulacionPedido> obtenerAnulacionPedidoRango(String fechaAnterior, String fechaActual)
		{
			ArrayList<AnulacionPedido> anulacionesRangoFechas =  PedidoDAO.obtenerAnulacionPedidoRango(fechaAnterior, fechaActual, auditoria);
			return(anulacionesRangoFechas);
		}
		
		public ArrayList<AnulacionPedido> obtenerAnulacionDPedidoRango(String fechaAnterior, String fechaActual)
		{
			ArrayList<AnulacionPedido> anulacionesRangoFechas =  PedidoDAO.obtenerAnulacionDPedidoRango(fechaAnterior, fechaActual, auditoria);
			return(anulacionesRangoFechas);
		}
		
		public boolean insertarPedidoDescuento(PedidoDescuento descuento)
		{
			boolean respuesta = PedidoDescuentoDAO.insertarPedidoDescuento(descuento, auditoria);
			return(respuesta);
		}
		
		public ArrayList obtenerTotalPizzasDiario(String fecha)
		{
			ArrayList totalPizzas =  PedidoDAO.obtenerTotalPizzasDiario(fecha, auditoria);
			return(totalPizzas);
		}
		
		public ArrayList obtenerTotalTipoDiario(String fecha)
		{
			ArrayList totalPizzasTipo =  PedidoDAO.obtenerTotalTipoDiario(fecha, auditoria);
			return(totalPizzasTipo);
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
		
		public ArrayList<DetallePedido> obtenerDetallePedidoPintarMaster(int idPedido)
		{
			ArrayList<DetallePedido> detPedidos = DetallePedidoDAO.obtenerDetallePedidoPintarMaster(idPedido, auditoria);
			detPedidos = ordenarDetallePedido(detPedidos);
			return(detPedidos);
		}
		
		public ArrayList<DetallePedido> obtenerDetallePedidoAnuladoPintar(int idPedido)
		{
			ArrayList<DetallePedido> detPedidos = DetallePedidoDAO.obtenerDetallePedidoAnuladoPintar(idPedido, auditoria);
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
		 * Método que en la capaDAO se encarga de la actualización del pedido con un cliete determinado esto debido a qu que un cliente puede ser asociado cuando ya el pedido fuera entregado
		 * @param idPedido
		 * @param idCliente
		 * @param auditoria
		 * @return
		 */
		public boolean actualizarClientePedido(int idPedido, int idCliente)
		{
			boolean actCliente = PedidoDAO.actualizarClientePedido(idPedido, idCliente, auditoria);
			return(actCliente);
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
		 * Método que se encarga de retorar un arreglo de retornar un arreglo con los tiempos que llevan los pedidos de una fecha determinada
		 * buscando mayor agilidad y evitando hacer el ca´clulo en base a consultas uno a uno en base de datos
		 * @param fecha Se recibe como parámetro la fecha para hacer el cálculo para todos los pedidos de un día determinado
		 * @return Realiza el retorno de un arreglo de objeto tipo TiempoPedido de los pedidos de un día determinado.
		 */
		public ArrayList<TiempoPedido> calcularTiemposPedidos(String fecha)
		{
			//Instanciamos el arreglo donde tendremos la respuesta
			ArrayList<TiempoPedido> tiemposPedidos = new ArrayList();
			//Obtenemos el arreglo con todos los estados
			ArrayList hisEstadosPedidos = EstadoDAO.obtenerHistoriaEstadoPedidosFecha(fecha, auditoria);
			//el ciclo tendrá vida mientras existe estados de historia de pedido a procesar
			ArrayList hisEstPedido;
			int idPedidoActual = 0;
			boolean indicador = true;
			//Es la variable donde se calcular cada tiempo Pedido
			String respuesta = "";
			while(hisEstadosPedidos.size() > 0)
			{
				//Debemos de extraer la historia pedido de cada uno particular
				//Instanciamos el temporal para extraer la historia de cada pedido particular
				hisEstPedido = new ArrayList();
				//Extraemos el idPedido que vamos a tratar para esta iteración
				String[] filaHistoria = (String[])hisEstadosPedidos.get(0);
				idPedidoActual = Integer.parseInt(filaHistoria[filaHistoria.length - 2]);
				hisEstPedido.add(filaHistoria);
				//Removemos el primer elemento que ya procesamos
				hisEstadosPedidos.remove(0);
				int idPedidoPru = 0;
				while(indicador)
				{
					//Validamos si el arreglo de historia de estado tiene elementos, si no los tiene nos salimos
					if(hisEstadosPedidos.size()== 0)
					{
						break;
					}
					filaHistoria = (String[])hisEstadosPedidos.get(0);
					int idPedidoTemp = Integer.parseInt(filaHistoria[filaHistoria.length - 2]);
					idPedidoPru = idPedidoTemp ;
					if(idPedidoTemp == idPedidoActual)
					{
						hisEstPedido.add(filaHistoria);
						hisEstadosPedidos.remove(0);
					}
					else
					{
						indicador = false;
					}
				}
				indicador = true;
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
					Math.abs(minutos = (int)TimeUnit.MILLISECONDS.toMinutes(difTiempo ));
					respuesta = "Actual: " + minutos + " Total: " + minutos;
				}
				else if(tamEstados > 1)
				{
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
					
					difTiempo =Math.abs((int) (datFecPedido.getTime() - fechaActual.getTime() ));
					Math.abs(minutos = (int)TimeUnit.MILLISECONDS.toMinutes(difTiempo ));
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
						Math.abs(minutos = (int)TimeUnit.MILLISECONDS.toMinutes(difTiempo ));
						respuesta = " TOT: " + minutos;
					}
					else
					{
						respuesta = "Tot: " + minutos;
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
						respuesta = respuesta + " Act: " + minutos;
					}
					
				}
				TiempoPedido tiemPedido = new TiempoPedido(idPedidoActual, respuesta);
				tiemposPedidos.add(tiemPedido);
			}
			return(tiemposPedidos);
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
		
		/**
		 * Método que se encarga de retornar resumen por forma de pago
		 * @param fechaPedido Se recibe un string con la fecha a consultar y resumir los totales.
		 * @return Se retorna un arraylist con los totales resumidos
		 */
		public ArrayList obtenerTotalesPedidosPorFormaPago(String fechaPedido)
		{
			ArrayList resumenTotTipoPedido = PedidoDAO.obtenerTotalesPedidosPorFormaPago(fechaPedido, auditoria);
			return(resumenTotTipoPedido);
		}
		
		public ArrayList obtenerTotalesPedidosPorDomiciliario(String fechaPedido)
		{
			ArrayList resumenTotDomiciliario = PedidoDAO.obtenerTotalesPedidosPorDomiciliario(fechaPedido, auditoria);
			return(resumenTotDomiciliario);
		}
		
		public ArrayList obtenerTotalesPedidosPorDomiciliarioRango(String fechaAnterior, String fechaActual)
		{
			ArrayList resumenTotDomiciliario = PedidoDAO.obtenerTotalesPedidosPorDomiciliarioRango(fechaAnterior, fechaActual, auditoria);
			return(resumenTotDomiciliario);
		}
		
		public ArrayList obtenerResumidoPedidosPorDomiciliarioRango(String fechaAnterior, String fechaActual)
		{
			ArrayList resumenTotDomiciliario = PedidoDAO.obtenerResumidoPedidosPorDomiciliarioRango(fechaAnterior, fechaActual, auditoria);
			return(resumenTotDomiciliario);
		}
		
		//Este método tendrá como objetivo realizar un totalizado de lo que va de la semana en ventas, de igual
		//forma para ser consumido en reportes y por pantalla
		public double obtenerTotalesPedidosSemana()
		{
			//Recuperamos la fecha actual del sistema con la fecha apertura
			FechaSistema fecha = obtenerFechasSistema();
			String fechaActual = fecha.getFechaUltimoCierre();
			//Variables donde manejaremos la fecha anerior con el fin realizar los cálculos de ventas
			Date datFechaAnterior;
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
				calendarioActual.add(Calendar.DAY_OF_YEAR, -6);
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
			//Llevamos a un string la fecha anterior para el cálculo de la venta
			datFechaAnterior = calendarioActual.getTime();
			fechaAnterior = dateFormat.format(datFechaAnterior);
			double totalVenta = PedidoDAO.obtenerTotalesPedidosSemana(fechaAnterior, fechaActual, auditoria);
			return(totalVenta);
		}
		
		
		//Este método tendrá como objetivo realizar un totalizado de lo que va de la semana en ventas, de igual
		//forma para ser consumido en reportes y por pantalla
		public double obtenerTotalesPedidosSemanaEnCurso()
		{
			//Recuperamos la fecha actual del sistema con la fecha apertura
			FechaSistema fecha = obtenerFechasSistema();
			String fechaActual = fecha.getFechaApertura();
			//Variables donde manejaremos la fecha anerior con el fin realizar los cálculos de ventas
			Date datFechaAnterior;
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
				calendarioActual.add(Calendar.DAY_OF_YEAR, -6);
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
			//Llevamos a un string la fecha anterior para el cálculo de la venta
			datFechaAnterior = calendarioActual.getTime();
			fechaAnterior = dateFormat.format(datFechaAnterior);
			double totalVenta = PedidoDAO.obtenerTotalesPedidosSemana(fechaAnterior, fechaActual, auditoria);
			return(totalVenta);
		}
		
		
		
		
		
		
/**
 * Este método se encargará de retornar todos los descuentos de la semana, los retornará en un arreglo para ser enviada posteriormente en correo
 * @return un arrayList con objetos de tipo PedidoDescuento con los descuentos en una semana
 */
		public ArrayList<PedidoDescuento> obtenerDescuentosSemana()
		{
			//Recuperamos la fecha actual del sistema con la fecha apertura
			FechaSistema fecha = obtenerFechasSistema();
			String fechaActual = fecha.getFechaUltimoCierre();
			//Variables donde manejaremos la fecha anerior con el fin realizar los cálculos de ventas
			Date datFechaAnterior;
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
				calendarioActual.add(Calendar.DAY_OF_YEAR, -6);
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
			//Llevamos a un string la fecha anterior para el cálculo de la venta
			datFechaAnterior = calendarioActual.getTime();
			fechaAnterior = dateFormat.format(datFechaAnterior);
			ArrayList<PedidoDescuento> descuentosRangosFecha =  PedidoDescuentoDAO.obtenerPedidoDescuentoRango(fechaAnterior, fechaActual, auditoria);
			return(descuentosRangosFecha);
		}
		
		public ArrayList<AnulacionPedido> obtenerAnulacionesSemana()
		{
			//Recuperamos la fecha actual del sistema con la fecha apertura
			FechaSistema fecha = obtenerFechasSistema();
			String fechaActual = fecha.getFechaUltimoCierre();
			//Variables donde manejaremos la fecha anerior con el fin realizar los cálculos de ventas
			Date datFechaAnterior;
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
				calendarioActual.add(Calendar.DAY_OF_YEAR, -6);
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
			//Llevamos a un string la fecha anterior para el cálculo de la venta
			datFechaAnterior = calendarioActual.getTime();
			fechaAnterior = dateFormat.format(datFechaAnterior);
			ArrayList<AnulacionPedido> anulacionesRangosFecha =  PedidoDAO.obtenerAnulacionPedidoRango(fechaAnterior, fechaActual, auditoria);
			return(anulacionesRangosFecha);
		}
		
		public ArrayList<AnulacionPedido> obtenerAnulacionesDescuentaSemana()
		{
			//Recuperamos la fecha actual del sistema con la fecha apertura
			FechaSistema fecha = obtenerFechasSistema();
			String fechaActual = fecha.getFechaUltimoCierre();
			//Variables donde manejaremos la fecha anerior con el fin realizar los cálculos de ventas
			Date datFechaAnterior;
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
				calendarioActual.add(Calendar.DAY_OF_YEAR, -6);
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
			//Llevamos a un string la fecha anterior para el cálculo de la venta
			datFechaAnterior = calendarioActual.getTime();
			fechaAnterior = dateFormat.format(datFechaAnterior);
			ArrayList<AnulacionPedido> anulacionesRangosFecha =  PedidoDAO.obtenerAnulacionDPedidoRango(fechaAnterior, fechaActual, auditoria);
			return(anulacionesRangosFecha);
		}
		
		
		public ArrayList<Egreso> obtenerEgresosSemana()
		{
			//Recuperamos la fecha actual del sistema con la fecha apertura
			FechaSistema fecha = obtenerFechasSistema();
			String fechaActual = fecha.getFechaUltimoCierre();
			//Variables donde manejaremos la fecha anerior con el fin realizar los cálculos de ventas
			Date datFechaAnterior;
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
				calendarioActual.add(Calendar.DAY_OF_YEAR, -6);
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
			//Llevamos a un string la fecha anterior para el cálculo de la venta
			datFechaAnterior = calendarioActual.getTime();
			fechaAnterior = dateFormat.format(datFechaAnterior);
			ArrayList<Egreso> egresosRangosFecha = EgresoDAO.obtenerEgresosSemana(fechaAnterior, fechaActual, auditoria);
			return(egresosRangosFecha);
		}
		
		public ArrayList<Ingreso> obtenerIngresosSemana()
		{
			//Recuperamos la fecha actual del sistema con la fecha apertura
			FechaSistema fecha = obtenerFechasSistema();
			String fechaActual = fecha.getFechaUltimoCierre();
			//Variables donde manejaremos la fecha anerior con el fin realizar los cálculos de ventas
			Date datFechaAnterior;
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
				calendarioActual.add(Calendar.DAY_OF_YEAR, -6);
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
			//Llevamos a un string la fecha anterior para el cálculo de la venta
			datFechaAnterior = calendarioActual.getTime();
			fechaAnterior = dateFormat.format(datFechaAnterior);
			ArrayList<Ingreso> ingresosRangosFecha = IngresoDAO.obtenerIngresosSemana(fechaAnterior, fechaActual, auditoria);
			return(ingresosRangosFecha);
		}
		
		public ArrayList obtenerDomiciliosSemana()
		{
			//Recuperamos la fecha actual del sistema con la fecha apertura
			FechaSistema fecha = obtenerFechasSistema();
			String fechaActual = fecha.getFechaApertura();
			//Variables donde manejaremos la fecha anerior con el fin realizar los cálculos de ventas
			Date datFechaAnterior;
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
				calendarioActual.add(Calendar.DAY_OF_YEAR, -6);
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
			//Llevamos a un string la fecha anterior para el cálculo de la venta
			datFechaAnterior = calendarioActual.getTime();
			fechaAnterior = dateFormat.format(datFechaAnterior);
			ArrayList domiciliosRangosFecha =  obtenerTotalesPedidosPorDomiciliarioRango(fechaAnterior, fechaActual);
			return(domiciliosRangosFecha);
		}
		
		public ArrayList obtenerDomiciliosResumidosSemana()
		{
			//Recuperamos la fecha actual del sistema con la fecha apertura
			FechaSistema fecha = obtenerFechasSistema();
			String fechaActual = fecha.getFechaUltimoCierre();
			//Variables donde manejaremos la fecha anerior con el fin realizar los cálculos de ventas
			Date datFechaAnterior;
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
				calendarioActual.add(Calendar.DAY_OF_YEAR, -6);
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
			//Llevamos a un string la fecha anterior para el cálculo de la venta
			datFechaAnterior = calendarioActual.getTime();
			fechaAnterior = dateFormat.format(datFechaAnterior);
			ArrayList domiciliosRangosFecha =  obtenerResumidoPedidosPorDomiciliarioRango(fechaAnterior, fechaActual);
			return(domiciliosRangosFecha);
		}
		
		public String reporteSemanalDescuentos()
		{
			String respuesta = "";
			DecimalFormat formatea = new DecimalFormat("###,###");
			//DESCUENTO DE PIZZAS
			ArrayList<PedidoDescuento> descuentosFecha = obtenerDescuentosSemana();
			respuesta = respuesta + "<table border='2'> <tr> DESCUENTOS DE LA SEMANA QUE SE CIERRA </tr>";
			respuesta = respuesta + "<tr>"
					+  "<td><strong>PEDIDO</strong></td>"
					+  "<td><strong>Fecha Descuento</strong></td>"
					+  "<td><strong>Val Inicial</strong></td>"
					+  "<td><strong>Descuento</strong></td>"
					+  "<td><strong>Val Final</strong></td>"
					+  "<td><strong>% Desc</strong></td>"
					+  "<td><strong>Observacion</strong></td>"
					+  "<td><strong>Usuario</strong></td>"
					+  "<td><strong>Usuario Autorizo</strong></td>"
					+  "</tr>";
			PedidoDescuento descTemp;
			for(int y = 0; y < descuentosFecha.size();y++)
			{
				descTemp = descuentosFecha.get(y);
				String desc = "";
				if(descTemp.getDescuentoPesos() > 0 )
				{
					desc = Double.toString(descTemp.getDescuentoPesos());
				}
				else
				{
					desc = Double.toString(descTemp.getDescuentoPorcentaje());
				}
				respuesta = respuesta + "<tr><td>" + descTemp.getIdpedido() + "</td><td> " + descTemp.getFechaDescuento() + "</td><td> " + formatea.format(descTemp.getValorInicial()) +"</td><td> " + formatea.format(Double.parseDouble(desc)) + "</td><td> " + formatea.format(descTemp.getValorFinal()) + "</td><td> " + formatea.format(descTemp.getDescuentoPorcentaje()) + "</td><td> " + descTemp.getObservacion() + "</td><td> " + descTemp.getUsuario() + "</td><td> " + descTemp.getUsuarioAutorizo()  +"</td></tr>";
			}
			respuesta = respuesta + "</table> <br/>";
			return(respuesta);
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
		
		/**
		 * Este médodo se encarga de realizar la consulta de los pedidos, dado un empleado, y con el tipo, se debe diferenciar si es domiciliario.
		 * @param idEmpleado se recibe como parámetro el idEmpleado con base en el cual se va a realizar la consulta de los pedidos
		 * @return Se retorna un arrayList con los pedidos e acuerdo a los parámetros enviados
		 */
		public ArrayList obtenerPedidosVentanaComanda(int idEmpleado)
		{
			//Cuando el empleado es domiciliario, se deberán mostrar los pedidos propios del tipo domiciliario, ddeberán mostrar los pedidos que ha llevado y con los que salío
			//hace poco para llevar.
			FechaSistema fechaSistema = TiendaDAO.obtenerFechasSistema( auditoria);
			ArrayList pedidos = new ArrayList();
			if(sePuedeFacturar(fechaSistema))
			{
				//Obtenemos el tipo de Usuario del usuario logueado
				if(idEmpleado != 0)
				{
					Usuario usuActual = UsuarioDAO.obtenerEmpleado(idEmpleado, auditoria);
					int idTipoEmpActual = usuActual.getidTipoEmpleado();
					//Distinguimos cuando no es domiciliario
					pedidos = PedidoDAO.obtenerPedidosVentanaComanda(fechaSistema.getFechaApertura(), idTipoEmpActual, auditoria );
				}else
				{
					pedidos = PedidoDAO.obtenerPedidosVentanaComanda(fechaSistema.getFechaApertura(), 0, auditoria );
				}
				//Obtenemos los tiempos de Pedido
				ArrayList<TiempoPedido> tiemposPedido = calcularTiemposPedidos(fechaSistema.getFechaApertura());
				
				int idPedido = 0;
				TiempoPedido tiempoPedidoTemp = new TiempoPedido(0,"");
				String tiempoPedido = "";
				//Recorremos el resultado de los pedidos para ir agregando el tiempo de pedido
				for(int i = 0 ; i < pedidos.size(); i++)
				{
					//Sacamos cada fila del pedido
					String[]fila = (String[]) pedidos.get(i);
					//Extraemos el idPedido
					idPedido =Integer.parseInt(fila[1]);
					// Recorremos el ArrayList de los tiempos pedidos.
					for(int j = 0; j < tiemposPedido.size(); j++)
					{
						tiempoPedidoTemp = tiemposPedido.get(j);
						// SI el pedido es igual al que estamos procesando extraemos el tiempo del pedido
						if(tiempoPedidoTemp.getIdPedidoTienda() == idPedido)
						{
							tiempoPedido = tiempoPedidoTemp.getTiempoPedido();
							break;
						}
					}
					fila[fila.length-1] = tiempoPedido;
					pedidos.set(i, fila);
				}
			}
			return(pedidos);
		}
		
		public ArrayList obtenerPedidosVentanaComandaDomEnRuta(int idEmpleado)
		{
			//Cuando el empleado es domiciliario, se deberán mostrar los pedidos propios del tipo domiciliario, ddeberán mostrar los pedidos que ha llevado y con los que salío
			//hace poco para llevar.
			FechaSistema fechaSistema = TiendaDAO.obtenerFechasSistema( auditoria);
			ArrayList pedidos = new ArrayList();
			if(sePuedeFacturar(fechaSistema))
			{
				//Obtenemos el tipo de Usuario del usuario logueado
				Usuario usuActual = UsuarioDAO.obtenerEmpleado(idEmpleado, auditoria);
				int idTipoEmpActual = usuActual.getidTipoEmpleado();
				//Distinguimos cuando no es domiciliario
				pedidos = PedidoDAO.obtenerPedidosVentanaComandaDomEnRuta(fechaSistema.getFechaApertura(), idTipoEmpActual, idEmpleado, auditoria );
				//Obtenemos los tiempos de Pedido
				ArrayList<TiempoPedido> tiemposPedido = calcularTiemposPedidos(fechaSistema.getFechaApertura());
				int idPedido = 0;
				TiempoPedido tiempoPedidoTemp = new TiempoPedido(0,"");
				String tiempoPedido = "";
				for(int i = 0 ; i < pedidos.size(); i++)
				{
					String[]fila = (String[]) pedidos.get(i);
					idPedido =Integer.parseInt(fila[1]);
					for(int j = 0; j < tiemposPedido.size(); j++)
					{
						tiempoPedidoTemp = tiemposPedido.get(j);
						// SI el pedido es igual al que estamos procesando extraemos el tiempo del pedido
						if(tiempoPedidoTemp.getIdPedidoTienda() == idPedido)
						{
							tiempoPedido = tiempoPedidoTemp.getTiempoPedido();
							break;
						}
					}
					fila[fila.length-1] = tiempoPedido;
					pedidos.set(i, fila);
				}
			}
			return(pedidos);
		}
		
		/**
		 * Método espeficamente para Tablet, inicialmente la idea es probar con un método adicional que verifique con el id pedido
		 * y retorne un string para agregar
		 * @param idEmpleado
		 * @return
		 */
		public ArrayList obtenerPedidosVentanaComandaDomEnRutaTablet(int idEmpleado)
		{
			//Cuando el empleado es domiciliario, se deberán mostrar los pedidos propios del tipo domiciliario, ddeberán mostrar los pedidos que ha llevado y con los que salío
			//hace poco para llevar.
			FechaSistema fechaSistema = TiendaDAO.obtenerFechasSistema( auditoria);
			ArrayList pedidos = new ArrayList();
			if(sePuedeFacturar(fechaSistema))
			{
				//Obtenemos el tipo de Usuario del usuario logueado
				Usuario usuActual = UsuarioDAO.obtenerEmpleado(idEmpleado, auditoria);
				int idTipoEmpActual = usuActual.getidTipoEmpleado();
				//Distinguimos cuando no es domiciliario
				pedidos = PedidoDAO.obtenerPedidosVentanaComandaDomEnRutaTablet(fechaSistema.getFechaApertura(), idTipoEmpActual, idEmpleado, auditoria );
				//Obtenemos los tiempos de Pedido
				ArrayList<TiempoPedido> tiemposPedido = calcularTiemposPedidos(fechaSistema.getFechaApertura());
				int idPedido = 0;
				TiempoPedido tiempoPedidoTemp = new TiempoPedido(0,"");
				String tiempoPedido = "";
				for(int i = 0 ; i < pedidos.size(); i++)
				{
					String[]fila = (String[]) pedidos.get(i);
					idPedido =Integer.parseInt(fila[1]);
					for(int j = 0; j < tiemposPedido.size(); j++)
					{
						tiempoPedidoTemp = tiemposPedido.get(j);
						// SI el pedido es igual al que estamos procesando extraemos el tiempo del pedido
						if(tiempoPedidoTemp.getIdPedidoTienda() == idPedido)
						{
							tiempoPedido = tiempoPedidoTemp.getTiempoPedido();
							break;
						}
					}
					fila[fila.length-1] = tiempoPedido;
					pedidos.set(i, fila);
				}
			}
			return(pedidos);
		}
		

				
		public ArrayList obtenerPedidosVentanaComandaDom(int idEmpleado)
		{
			//Cuando el empleado es domiciliario, se deberán mostrar los pedidos propios del tipo domiciliario, ddeberán mostrar los pedidos que ha llevado y con los que salío
			//hace poco para llevar.
			FechaSistema fechaSistema = TiendaDAO.obtenerFechasSistema( auditoria);
			ArrayList pedidos = new ArrayList();
			if(sePuedeFacturar(fechaSistema))
			{
				//Obtenemos el tipo de Usuario del usuario logueado
				Usuario usuActual = UsuarioDAO.obtenerEmpleado(idEmpleado, auditoria);
				int idTipoEmpActual = usuActual.getidTipoEmpleado();
				//Distinguimos cuando no es domiciliario
				pedidos = PedidoDAO.obtenerPedidosVentanaComandaDom(fechaSistema.getFechaApertura(), idTipoEmpActual, idEmpleado, auditoria );
				//Obtenemos los tiempos de Pedido
				ArrayList<TiempoPedido> tiemposPedido = calcularTiemposPedidos(fechaSistema.getFechaApertura());
				int idPedido = 0;
				TiempoPedido tiempoPedidoTemp = new TiempoPedido(0,"");
				String tiempoPedido = "";
				for(int i = 0 ; i < pedidos.size(); i++)
				{
					String[]fila = (String[]) pedidos.get(i);
					idPedido =Integer.parseInt(fila[1]);
					for(int j = 0; j < tiemposPedido.size(); j++)
					{
						tiempoPedidoTemp = tiemposPedido.get(j);
						// SI el pedido es igual al que estamos procesando extraemos el tiempo del pedido
						if(tiempoPedidoTemp.getIdPedidoTienda() == idPedido)
						{
							tiempoPedido = tiempoPedidoTemp.getTiempoPedido();
							break;
						}
					}
					fila[fila.length-1] = tiempoPedido;
					pedidos.set(i, fila);
				}
			}
			return(pedidos);
		}
		
		public ArrayList obtenerPedidosVentanaComandaDomTablet(int idEmpleado)
		{
			//Cuando el empleado es domiciliario, se deberán mostrar los pedidos propios del tipo domiciliario, ddeberán mostrar los pedidos que ha llevado y con los que salío
			//hace poco para llevar.
			FechaSistema fechaSistema = TiendaDAO.obtenerFechasSistema( auditoria);
			ArrayList pedidos = new ArrayList();
			if(sePuedeFacturar(fechaSistema))
			{
				//Obtenemos el tipo de Usuario del usuario logueado
				Usuario usuActual = UsuarioDAO.obtenerEmpleado(idEmpleado, auditoria);
				int idTipoEmpActual = usuActual.getidTipoEmpleado();
				//Distinguimos cuando no es domiciliario
				pedidos = PedidoDAO.obtenerPedidosVentanaComandaDomTablet(fechaSistema.getFechaApertura(), idTipoEmpActual, idEmpleado, auditoria );
				//Obtenemos los tiempos de Pedido
				ArrayList<TiempoPedido> tiemposPedido = calcularTiemposPedidos(fechaSistema.getFechaApertura());
				int idPedido = 0;
				TiempoPedido tiempoPedidoTemp = new TiempoPedido(0,"");
				String tiempoPedido = "";
				for(int i = 0 ; i < pedidos.size(); i++)
				{
					String[]fila = (String[]) pedidos.get(i);
					idPedido =Integer.parseInt(fila[1]);
					for(int j = 0; j < tiemposPedido.size(); j++)
					{
						tiempoPedidoTemp = tiemposPedido.get(j);
						// SI el pedido es igual al que estamos procesando extraemos el tiempo del pedido
						if(tiempoPedidoTemp.getIdPedidoTienda() == idPedido)
						{
							tiempoPedido = tiempoPedidoTemp.getTiempoPedido();
							break;
						}
					}
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
				//Realizamos validación si el idTipoPedido = 100, en cuyo caso traeremos todos los pedidos en estado entregado
				if(idTipoPedido == 100)
				{
					pedidos = PedidoDAO.ObtenerPedidosVentanaComandaHistorial(fechaSistema.getFechaApertura(), auditoria);
				}else
				{
					//Obtenemos el tipo de Usuario del usuario logueado
					if(idEmpleado != 0)
					{
						Usuario usuActual = UsuarioDAO.obtenerEmpleado(idEmpleado, auditoria);
						int idTipoEmpActual = usuActual.getidTipoEmpleado();
						pedidos = PedidoDAO.obtenerPedidosVentanaComandaTipPed(fechaSistema.getFechaApertura(), idTipoEmpActual, idTipoPedido, auditoria );
					}else
					{
						pedidos = PedidoDAO.obtenerPedidosVentanaComandaTipPed(fechaSistema.getFechaApertura(), 0, idTipoPedido, auditoria );
					}
				}
				//Obtenemos los tiempos de Pedido
				ArrayList<TiempoPedido> tiemposPedido = calcularTiemposPedidos(fechaSistema.getFechaApertura());
				
				int idPedido = 0;
				TiempoPedido tiempoPedidoTemp = new TiempoPedido(0,"");
				String tiempoPedido = "";
				for(int i = 0 ; i < pedidos.size(); i++)
				{
					String[]fila = (String[]) pedidos.get(i);
					idPedido =Integer.parseInt(fila[1]);
					for(int j = 0; j < tiemposPedido.size(); j++)
					{
						tiempoPedidoTemp = tiemposPedido.get(j);
						// SI el pedido es igual al que estamos procesando extraemos el tiempo del pedido
						if(tiempoPedidoTemp.getIdPedidoTienda() == idPedido)
						{
							tiempoPedido = tiempoPedidoTemp.getTiempoPedido();
							break;
						}
					}
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
		
		public ArrayList<ProductoModificadorCon> obtenerProdModificadoresConTodos()
		{
			ArrayList<ProductoModificadorCon> modCon =  ProductoModificadorConDAO.obtenerProdModificadoresConTodos(auditoria);
			return(modCon);
			
		}
		
		public ArrayList<ProductoModificadorSin> obtenerProdModificadoresSinTodos()
		{
			ArrayList<ProductoModificadorSin> modSin =  ProductoModificadorSinDAO.obtenerProdModificadoresSinTodos(auditoria);
			return(modSin);
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
		
		public DetallePedido obtenerUnDetallePedido(int idDetalle)
		{
			DetallePedido detPedido = DetallePedidoDAO.obtenerUnDetallePedido(idDetalle, auditoria);
			return(detPedido);
		}
		
		//Métodos para retornar los modificadores dado un idDetallePedido
		public ArrayList<ProductoModificadorCon> obtenerModificadoresCon(int idDetalle)
		{
			DetallePedido detPedido = DetallePedidoDAO.obtenerUnDetallePedido(idDetalle, auditoria);
			ArrayList<ProductoModificadorCon> prodMods = ProductoModificadorConDAO.obtenerProdModificadoresCon(detPedido.getIdProducto(), auditoria);
			return(prodMods);
		}
		
		public ArrayList<ProductoModificadorCon> obtenerModificadoresConProducto(int idProducto)
		{
			ArrayList<ProductoModificadorCon> prodMods = ProductoModificadorConDAO.obtenerProdModificadoresCon(idProducto, auditoria);
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
		
		public int obtenerMaxModificadorConProducto(int idProducto)
		{
			Producto pro = ProductoDAO.obtenerProducto(idProducto, auditoria);
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

//HILOS RELACIONADOS CON LA FINALIZACIÓN DE PEDIDOS
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Thread ct = Thread.currentThread();
			//Validamos si el hilo es de impuestos con el fin de poder arrancar el hilo que descuenta de inventarios
			if(ct == hiloImpuestos) 
			{   
				 ImpuestoCtrl impCtrl = new ImpuestoCtrl(auditoria);
				 impCtrl.liquidarImpuestosPedido(idPedidoActual);
				 
			}else if(ct == hiloInventarios) 
			{
				//En este punto es cuando clareamos las variables del tipo de pedido que son estáticas y sabiendo qeu se finalizó
				//el pedido es neceseario clarear las variables del jFrame de TomarPedidos
				InventarioCtrl invCtrl = new InventarioCtrl(PrincipalLogueo.habilitaAuditoria);
				boolean reintInv = invCtrl.descontarInventarioPedido(idPedidoActual);
				if(!reintInv)
				{
					JOptionPane.showMessageDialog(null, "Se presentaron inconvenientes en el descuento de los inventarios " , "Error en Descuento de Inventarios ", JOptionPane.ERROR_MESSAGE);
				}
			}else if(ct == hiloObservacion)
			{
				PedidoDAO.generarObservacionPedido(idPedidoActual, auditoria);
			}else if(ct == hiloEstadoPedido) 
			{
				//Es decir es un pedido nuevo
				boolean esPedidoPorciones = esPedidoPorciones(Sesion.getIdProductoPorcion(), Sesion.getIdProductoGaseosa(), idPedidoActual);
				if(!esPedidoReabierto)
				{
					Estado estadoIni = EstadoDAO.obtenerEstadoInicial(idTipoPedidoActual, auditoria);
					int idEstadoPostIni = estadoIni.getIdestado();
					PedidoDAO.ActualizarEstadoPedido(idPedidoActual, 0 , idEstadoPostIni,Sesion.getUsuario(), auditoria);
					if(estadoIni.isImpresion())
					{
						//Con el tipo de impresion es 1 se imprime normal
						if (tipoImpresion == 1)
						{
							if(Sesion.getControlEstrictoPorciones().equals("N") || (!esPedidoPorciones))
							{
								//Vamos a realizar la impresión de la comanda
								//Antes de generar la comanda preguntamos si lo debemos de hacer por parametrizacion
								if(Sesion.isImprimirComandaPedido())
								{
									String strComanda = generarStrImpresionComanda(idPedidoActual);
									if(Sesion.getModeloImpresion() != 1)
									{
										ImprimirAdmDAO.insertarImpresion(strComanda, auditoria);
									}
									else
									{
										Impresion.main(strComanda);
									}
								}//En caso de que NO, no se debería hacer nada

								//Obtenemos el string de la factura que se imprimirá 2 veces
								String strFactura = generarStrImpresionFactura(idPedidoActual);
								System.out.println(strFactura);
								if(Sesion.getModeloImpresion() != 1)
								{
									ImprimirAdmDAO.insertarImpresion(strFactura, auditoria);
									ImprimirAdmDAO.insertarImpresion(strFactura, auditoria);
								}
								else
								{
									//Primera Impresión
									Impresion.main(strFactura);
									//Segunda Impresión
									Impresion.main(strFactura);
								}
								actualizarImpresionPedido(idPedidoActual);
							}else if(Sesion.getControlEstrictoPorciones().equals("S") || (esPedidoPorciones))
							{
								//En este punto imprimiremos la factura una sola vez, no imprimiremos comanda e imprimiremos
								// un resumen para que con este reclamen la porción
								String strFactura = generarStrImpresionFactura(idPedidoActual);
								System.out.println(strFactura);
								if(Sesion.getModeloImpresion() != 1)
								{
									ImprimirAdmDAO.insertarImpresion(strFactura, auditoria);
								}
								else
								{
									//Primera Impresión
									Impresion.main(strFactura);
								}
								actualizarImpresionPedido(idPedidoActual);
								//En este punto imprimiremos un comprobante para reclamar la porción
								String reclamaPorcion = generarStrControlPorciones(idPedidoActual);
								if(Sesion.getModeloImpresion() != 1)
								{
									ImprimirAdmDAO.insertarImpresion(reclamaPorcion, auditoria);
								}
								else
								{
									//Primera Impresión
									Impresion.main(reclamaPorcion);
								}
								System.out.println(reclamaPorcion);
							}
							
						} 
					}
				}
				else if(esPedidoReabierto)
				{
					
					if(imprimeSiReabierto)
					{
						//Con el tipo de impresion es 1 se imprime normal
						if (tipoImpresion == 1)
						{
							if(Sesion.getControlEstrictoPorciones().equals("N") || (!esPedidoPorciones))
							{
								//Vamos a realizar la impresión de la comanda
								//Antes de generar la comanda preguntamos si lo debemos de hacer por parametrizacion
								if(Sesion.isImprimirComandaPedido())
								{
									String strComanda = generarStrImpresionComanda(idPedidoActual);
									if(Sesion.getModeloImpresion() != 1)
									{
										ImprimirAdmDAO.insertarImpresion(strComanda, auditoria);
									}
									else
									{
										Impresion.main(strComanda);
									}
								}//En caso de que NO, no se debería hacer nada

								//Obtenemos el string de la factura que se imprimirá 2 veces
								String strFactura = generarStrImpresionFactura(idPedidoActual);
								if(Sesion.getModeloImpresion() != 1)
								{
									ImprimirAdmDAO.insertarImpresion(strFactura, auditoria);
									ImprimirAdmDAO.insertarImpresion(strFactura, auditoria);
								}
								else
								{
									//Primera Impresión
									Impresion.main(strFactura);
									//Segunda Impresión
									Impresion.main(strFactura);
								}
								actualizarImpresionPedido(idPedidoActual);
							}else if(Sesion.getControlEstrictoPorciones().equals("S") || (esPedidoPorciones))
							{
								//Obtenemos el string de la factura que se imprimirá 2 veces
								String strFactura = generarStrImpresionFactura(idPedidoActual);
								if(Sesion.getModeloImpresion() != 1)
								{
									ImprimirAdmDAO.insertarImpresion(strFactura, auditoria);
								}
								else
								{
									//Primera Impresión
									Impresion.main(strFactura);
								}
								actualizarImpresionPedido(idPedidoActual);
								//En este punto imprimiremos un comprobante para reclamar la porción
								String reclamaPorcion = generarStrControlPorciones(idPedidoActual);
								if(Sesion.getModeloImpresion() != 1)
								{
									ImprimirAdmDAO.insertarImpresion(reclamaPorcion, auditoria);
								}
								else
								{
									//Primera Impresión
									Impresion.main(reclamaPorcion);
								}
								System.out.println(reclamaPorcion);
							}
						//SI el tipo de impresión es 0 es porque deberíamos solo de imprimir lo nuevo
						} else if(tipoImpresion == 0)
						{
							if(Sesion.getControlEstrictoPorciones().equals("N") || (!esPedidoPorciones))
							{
								//Vamos a realizar la impresión de la comanda
								//Antes de generar la comanda preguntamos si lo debemos de hacer por parametrizacion
								if(Sesion.isImprimirComandaPedido())
								{
									String strComanda = generarStrImpresionComandaItemsNuevos(idPedidoActual, detPedidoNuevo);
									System.out.println(strComanda );
									if(Sesion.getModeloImpresion() != 1)
									{
										ImprimirAdmDAO.insertarImpresion(strComanda, auditoria);
									}
									else
									{
										Impresion.main(strComanda);
									}
								}//En caso de que NO, no se debería hacer nada

								//Obtenemos el string de la factura que se imprimirá 2 veces
								String strFactura = generarStrImpresionFactura(idPedidoActual);
								System.out.println(strFactura );
								if(Sesion.getModeloImpresion() != 1)
								{
									ImprimirAdmDAO.insertarImpresion(strFactura, auditoria);
									ImprimirAdmDAO.insertarImpresion(strFactura, auditoria);
								}
								else
								{
									//Primera Impresión
									Impresion.main(strFactura);
									//Segunda Impresión
									Impresion.main(strFactura);
								}
							}else if(Sesion.getControlEstrictoPorciones().equals("S") || (esPedidoPorciones))
							{
								//Obtenemos el string de la factura que se imprimirá 2 veces
								String strFactura = generarStrImpresionFactura(idPedidoActual);
								System.out.println(strFactura );
								if(Sesion.getModeloImpresion() != 1)
								{
									ImprimirAdmDAO.insertarImpresion(strFactura, auditoria);
								}
								else
								{
									//Primera Impresión
									Impresion.main(strFactura);
								}
								//En este punto imprimiremos un comprobante para reclamar la porción
								String reclamaPorcion = generarStrControlPorciones(idPedidoActual);
								if(Sesion.getModeloImpresion() != 1)
								{
									ImprimirAdmDAO.insertarImpresion(reclamaPorcion, auditoria);
								}
								else
								{
									//Primera Impresión
									Impresion.main(reclamaPorcion);
								}
								System.out.println(reclamaPorcion);
							}

						}

					}
					//Validamos si el pedido es reabierto anulado en cuyo caso no hay impresión y se pone en el estado final
					//del tipo de pedido que se recibe como parámetro
					if(reabiertoAnulado)
					{
						//Se obtiene el id de estado final
						int idEstadoFin = EstadoDAO.obtenerEstadoFinal(idTipoPedidoActual, auditoria);
						// Se actualiza el pedido
						PedidoDAO.ActualizarEstadoPedido(idPedidoActual, 0 , idEstadoFin ,Sesion.getUsuario(), auditoria);
					}
					if(cambioTipoPedido)
					{
						PedidoDAO.ActualizarEstadoPedido(idPedidoActual, 0 , idEstadoInicialCambio ,Sesion.getUsuario(), auditoria);
						desasignarDomiciliarioPedido(idPedidoActual);
					}
				}

			}
				
		}
		
		//IMPRESIONES EN IMPRESORA POS
		
		public String generarStrImpresionFactura(int idPedido)
		{
			//Obtenemos la fecha y hora actual;
			SimpleDateFormat forFechaHora= new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			String strFechaHora = forFechaHora.format(new Date());
			//Definimos el formato para los valores de moneda
			DecimalFormat formatea = new DecimalFormat("###,###");
			//Obtenemos la tienda sobre la que estamos trabajando
			Tienda tienda = TiendaDAO.obtenerTienda( auditoria);
			ArrayList<DetallePedido> detPedido = obtenerDetallePedidoPintar(idPedido);
			DetallePedido detTemp;
			String factura = tienda.getNombretienda() +"\n"
					+ tienda.getDireccion() +"\n"
					+ "tel:"+tienda.getTelefono() +"\n"
					+ tienda.getRazonSocial() +"\n"
					+ tienda.getIdentificacion()+ "\n"
					+ tienda.getTipoContribuyente() + "\n"
					+ tienda.getResolucion() + "\n"
					+ tienda.getFechaResolucion()+ "\n"
					+ "Desde " + tienda.getPuntoVenta() + " " + tienda.getNumeroInicialResolucion() + " Hasta " + tienda.getPuntoVenta() + " " + tienda.getNumeroFinalResolucion() + " \n"
					+ tienda.getUbicacion() + "\n"
					+ "Factura de Venta: " + tienda.getPuntoVenta() + " - "+idPedido +"\n"
					+ "Fecha/Hora: " + strFechaHora + " \n"
		            + "==================================\n"
		            + "Cant    Descripcion            Costo\n"
		            + "==================================\n";
			double cantidad;
			for(int i = 0; i < detPedido.size(); i++)
			{
				detTemp = detPedido.get(i);
				cantidad = detTemp.getValorTotal();
				//if ((cantidad - Math.floor(cantidad)) == 0) 
				if (detTemp.getValorTotal() == 0) 
				{
					factura = factura + detTemp.getCantidad() + "   " + detTemp.getDescCortaProducto() + "    " + "\n";
				}
				else
				{
					factura = factura +  detTemp.getCantidad() + "   " + detTemp.getDescCortaProducto() + "    " + formatea.format(detTemp.getValorTotal()) + "\n";
				}
				
			}
			Pedido pedImpFac = obtenerPedido(idPedido);
			//Obtenemos los posibles descuentos del pedido
			PedidoDescuento  pedDesc = PedidoDescuentoDAO.obtenerPedidoDescuento(idPedido, auditoria);
			String observacion = pedImpFac.getObservacion();
			String nombreCompania = pedImpFac.getNombreCompania();
			if (nombreCompania == null)
			{
				nombreCompania = "";
			}
			if(observacion == null || observacion.equals(null))
			{
				observacion = "";
			}
			String zonaObser = pedImpFac.getZona();
			if(zonaObser == null || zonaObser.equals(null))
			{
				zonaObser = "";
			}
			//Procesamos la forma de pago para distinguirla
			ArrayList<String[]> formasPagoPedido = consultarFormaPagoArreglo(idPedido);
	        String[] fila;
	        String nombreFormaPago = "";
	        double totalParaCambio = 0;
	        for(int i = 0; i < formasPagoPedido.size(); i++)
	        {
	        	fila = formasPagoPedido.get(i);
	        	nombreFormaPago =  nombreFormaPago + "   " + " " + fila[0] + " : " + formatea.format(Double.parseDouble(fila[1])) + "\n";
	        	totalParaCambio = totalParaCambio + Double.parseDouble(fila[1]);
	        }
			
			factura = factura + "==================================\n";
			factura = factura + "TOTAL BRUTO:      " + formatea.format(pedImpFac.getValorbruto())+"\n";
			factura = factura + "IMP CONS 8%   :      " + formatea.format(pedImpFac.getImpuesto())+"\n";
			//Validamos si hay algún descuento lo mostramos en Factura junto con su observacion
			if(pedDesc.getDescuentoPesos() > 0 || pedDesc.getDescuentoPorcentaje() >0)
			{
				factura = factura + "DESCUENTO   :      " + formatea.format(pedDesc.getDescuentoPesos())+"\n";
				factura = factura + "OBS DESC    :      " + pedDesc.getObservacion() +"\n";
			}
			factura = factura + "==================================\n";
			factura = factura  + "TOTAL : " + formatea.format(pedImpFac.getValorneto()) + "\n";
			factura = factura  + "CAMBIO : " + formatea.format(totalParaCambio - pedImpFac.getValorneto()) +"\n";
			factura = factura  + "==================================\n";
			factura = factura  + nombreFormaPago;
			factura = factura  + "==================================\n";
			factura = factura  + "CLIENTE: " + pedImpFac.getNombreCliente() + " \n";
			factura = factura  + "DIR CLIENTE: " + pedImpFac.getDirCliente() + " \n";
			factura = factura  + "OBS: " + observacion + " " + zonaObser + " \n";
			if((!nombreCompania.equals(new String (""))) && (!nombreCompania.equals(null)))
			{
				factura = factura  + "NOMBRE COMPAÑIA: " + nombreCompania + " \n";
			}
			factura = factura  + "TELEFONO: " + pedImpFac.getTelefono() + " \n";
			factura = factura  +  pedImpFac.getTipoPedido().toUpperCase() + " \n";
			factura = factura  + "!FELICITACIONES! HAZ COMPRADO" + "\n";
			factura = factura  + "LA MEJOR PIZZA DE LA CIUDAD" + "\n";
			factura = factura  + "pizzaamericanacolombia@gmail.com" + "\n";
			factura = factura  + "...GRACIAS POR SU COMPRA...\n"
		            + "           ******::::::::*******"
		            + "\n";
			System.out.println(factura);
			return(factura);
		}
		
		public String generarStrImpresionComanda(int idPedido)
		{
			
			//Obtenemos la tienda sobre la que estamos trabajando
			ArrayList<DetallePedido> detPedido = obtenerDetallePedidoPintar(idPedido);
			Pedido infoPedido = obtenerPedido(idPedido); 
			DetallePedido detTemp;
			Date fechaActual = new Date();
			String factura = "================================\n" 
					+ "Factura de Venta: "+idPedido +"\n"
					+ infoPedido.getTipoPedido().toUpperCase() +"\n"
		            + "Usuario: " + infoPedido.getUsuariopedido() + "\n"
		            + fechaActual.toString() + "\n"
		            + "==================================\n"
		            + "Cant    Descripcion                 \n"
		            + "==================================\n";
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
			//Obtenemos información del pedido para colocar unos pocos datos en la comanda
			Pedido pedImpFac = obtenerPedido(idPedido);
			factura = factura  + "CLIENTE: " + pedImpFac.getNombreCliente() + " \n";
			factura = factura  + "DIR CLIENTE: " + pedImpFac.getDirCliente() + " \n";
			factura = factura  + "OBS: " + pedImpFac.getObservacion() + " " + pedImpFac.getZona() + " \n";
			factura = factura  + "TELEFONO: " + pedImpFac.getTelefono() + " \n";
			factura = factura  + "             ********::::::::*******"
		            + "\n           ";
			System.out.println(factura);
			return(factura);
		}
		
		public String generarStrControlPorciones(int idPedido)
		{
			
			//Obtenemos la tienda sobre la que estamos trabajando
			ArrayList<DetallePedido> detPedido = obtenerDetallePedidoPintar(idPedido);
			Pedido infoPedido = obtenerPedido(idPedido); 
			DetallePedido detTemp;
			Date fechaActual = new Date();
			String factura = "================================\n" 
					+ "RECLAMA PORCIONES:  "+idPedido +"\n"
		            + fechaActual.toString() + "\n"
		            + "==================================\n"
		            + "Cant    Descripcion                 \n"
		            + "==================================\n";
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
			//Obtenemos información del pedido para colocar unos pocos datos en la comanda
			Pedido pedImpFac = obtenerPedido(idPedido);
			factura = factura  + "             ********::::::::*******"
		            + "\n           ";
			System.out.println(factura);
			return(factura);
		}
		
		public String generarStrImpresionComandaItemsNuevos(int idPedido, ArrayList<DetallePedido> detPedidoNuevo)
		{
			
			//Obtenemos la tienda sobre la que estamos trabajando
			ArrayList<DetallePedido> detPedido = obtenerDetallePedidoPintar(idPedido);
			Pedido infoPedido = obtenerPedido(idPedido); 
			DetallePedido detTemp, detTempNuevo;
			Date fechaActual = new Date();
			String factura = "================================\n" 
					+ "Factura de Venta: "+idPedido +"\n"
					+ infoPedido.getTipoPedido().toUpperCase() +"\n"
		            + "Usuario: " + infoPedido.getUsuariopedido() + "\n"
		            + fechaActual.toString() + "\n"
		            + "==================================\n"
		            + "Cant    Descripcion                 \n"
		            + "==================================\n";
			double cantidad;
			for(int i = 0; i < detPedido.size(); i++)
			{
				detTemp = detPedido.get(i);
				for(int j = 0; j < detPedidoNuevo.size(); j++)
				{
					detTempNuevo = detPedidoNuevo.get(j);
					if(detTempNuevo.getIdDetallePedido() == detTemp.getIdDetallePedido())
					{
						cantidad = detTemp.getValorTotal();
						if ((cantidad - Math.floor(cantidad)) == 0) 
						{
							factura = factura + "  " + detTemp.getCantidad() + "   " + detTemp.getDescCortaProducto() + "    " + "\n";
						}
						else
						{
							factura = factura + "  " + detTemp.getCantidad() + "   " + detTemp.getDescCortaProducto() + "    " + detTemp.getValorTotal() + "\n";
						}
						break;
					}
				}
			}
			//Obtenemos información del pedido para colocar unos pocos datos en la comanda
			Pedido pedImpFac = obtenerPedido(idPedido);
			factura = factura  + "CLIENTE: " + pedImpFac.getNombreCliente() + " \n";
			factura = factura  + "DIR CLIENTE: " + pedImpFac.getDirCliente() + " \n";
			factura = factura  + "OBS: " + pedImpFac.getObservacion() + " " + pedImpFac.getZona() + " \n";
			factura = factura  + "TELEFONO: " + pedImpFac.getTelefono() + " \n";
			factura = factura  + "             ********::::::::*******"
		            + "\n           ";
			System.out.println(factura);
			return(factura);
		}
		
		public void imprimirSalidaDomiciliario(int idPedido)
		{
			
			Pedido pedImpFac = obtenerPedido(idPedido);
			Date fechaActual = new Date();
			String comandaSalida = "================================\n" 
					+ "SALIDA DOMICILIO -" + pedImpFac.getDomiciliario() +"\n" + "\n"
					+ "Factura de Venta: "+idPedido +"\n"
		            + "Usuario: " + pedImpFac.getUsuariopedido() + "\n"
		            + fechaActual.toString()+ "\n";
			comandaSalida = comandaSalida  + "CLIENTE: " + pedImpFac.getNombreCliente() + " \n";
			comandaSalida = comandaSalida  + "DIR CLIENTE: " + pedImpFac.getDirCliente() + " \n";
			comandaSalida = comandaSalida  + "OBS: " + pedImpFac.getObservacion() + " " + pedImpFac.getZona() + "  \n";
			comandaSalida = comandaSalida  + "TELEFONO: " + pedImpFac.getTelefono() + "  \n"
		            + "           ********::::::::*******"
		            + "\n           ";
			if(Sesion.getModeloImpresion() != 1)
			{
				ImprimirAdmDAO.insertarImpresion(comandaSalida, auditoria);
			}
			else
			{
				Impresion.main(comandaSalida);
			}
		}
		
		public String resumenGeneralVentasImprimir()
		{
			//Obtenemos la fecha actual del sistema
			//Vamos a recuperar la fecha del sistema y la vamos a mostrar en el campo correspondiente
			FechaSistema fecha = obtenerFechasSistema();
			String fechaSis = fecha.getFechaUltimoCierre();
			String respuesta = "   RESUMEN GENERAL DE VENTAS \n";
			ArrayList totTipoPedido = new ArrayList();
	        totTipoPedido = obtenerTotalesPedidosPorTipo(fechaSis);
	        double totalVendido = 0;
			for(int y = 0; y < totTipoPedido.size();y++)
			{
				String[] fila =(String[]) totTipoPedido.get(y);
				respuesta = respuesta + "  " + fila[0] + "  " + fila[1] + " " + fila[2] +"\n";
				totalVendido = totalVendido + Double.parseDouble(fila[1]);
			}
			double totalIngresos = operTiendaCtrl.TotalizarIngreso(fechaSis);
			respuesta = respuesta + "   TOTAL INGRESOS DEL DÍA " + Double.toString(totalIngresos) + "\n";
			double totalEgresos = operTiendaCtrl.TotalizarEgreso(fechaSis);
			respuesta = respuesta + "   TOTAL EGRESOS DEL DÍA " + Double.toString(totalEgresos) + "\n>";
			respuesta = respuesta + "   TOTAL SIN I/E " + (Double.toString(totalVendido)) + "\n";
			respuesta = respuesta + "   TOTAL CON I/E " + (Double.toString(totalVendido + totalIngresos - totalEgresos)) + "\n";
			return(respuesta);
		}
		
		
		
		// GENERACIÓN DE STRINGS PARA CORREOS ELECTRONICOS
		

		public String resumenGeneralVentas(String fechasSistema)
		{
			//Formato para los valores de moneda
			DecimalFormat formatea = new DecimalFormat("###,###");
			//Obtenemos la fecha actual del sistema
			//Vamos a recuperar la fecha del sistema y la vamos a mostrar en el campo correspondiente
			String fechaSis = fechasSistema;
			String respuesta = "<table border='2'> <tr> RESUMEN DE VENTAS </tr>";
			respuesta = respuesta + "<tr>"
			+  "<td><strong>Concepto</strong></td>"
			+  "<td><strong>Total Venta</strong></td>"
			+  "<td><strong>Cantidad</strong></td>"
			+  "</tr>";
			ArrayList totTipoPedido = new ArrayList();
	        totTipoPedido = obtenerTotalesPedidosPorTipo(fechaSis);
	        double totalVendido = 0;
			for(int y = 0; y < totTipoPedido.size();y++)
			{
				String[] fila =(String[]) totTipoPedido.get(y);
				respuesta = respuesta + "<tr><td>" + fila[0] + "</td><td> " + formatea.format(Double.parseDouble(fila[1])) + "</td><td> " + fila[2] +"</td></tr>";
				totalVendido = totalVendido + Double.parseDouble(fila[1]);
			}
			respuesta = respuesta + "</table> <br/>";
			
			//Inclusión de resumen por Forma de Pago
			respuesta = respuesta +  "<table border='2'> <tr> RESUMEN POR FORMA DE PAGO </tr>";
			respuesta = respuesta + "<tr>"
			+  "<td><strong>Forma pago</strong></td>"
			+  "<td><strong>Total Venta</strong></td>"
			+  "<td><strong>Cantidad</strong></td>"
			+  "</tr>";
			ArrayList totFormaPago = new ArrayList();
			totFormaPago = obtenerTotalesPedidosPorFormaPago(fechaSis);
	        for(int y = 0; y < totFormaPago.size();y++)
			{
				String[] fila =(String[]) totFormaPago.get(y);
				respuesta = respuesta + "<tr><td>" + fila[0] + "</td><td> " + formatea.format(Double.parseDouble(fila[1])) + "</td><td> " + fila[2] +"</td></tr>";
			}
			respuesta = respuesta + "</table> <br/>";
			
			//Final de inclusión por forma de pago
						
			respuesta = respuesta + "<table border='2'><tr><td><strong>ACUMULADO DE VENTA DE LA SEMANA: </strong></td> <td><strong>" + formatea.format(obtenerTotalesPedidosSemana()) + "</strong></td></tr></table><br/> <br/>";
			
			double totalIngresos = operTiendaCtrl.TotalizarIngreso(fechaSis);
			respuesta = respuesta + "<table border='2'> <tr> <td> TOTAL INGRESOS DEL DÍA </td> <td>" + Double.toString(totalIngresos) + "</td></tr>";
			double totalEgresos = operTiendaCtrl.TotalizarEgreso(fechaSis);
			respuesta = respuesta + "<tr> <td> TOTAL EGRESOS DEL DÍA </td> <td>" + formatea.format(totalEgresos) + "</td></tr>";
			respuesta = respuesta + "<tr> <td> TOTAL SIN I/E </td> <td>" + formatea.format(totalVendido) + "\n";
			respuesta = respuesta + "<tr> <td> TOTAL CON I/E </td> <td>" + (formatea.format(totalVendido + totalIngresos - totalEgresos)) + "</td></tr></table> <br/>";
			//Adicionamos la información de resumen domiciliarios
			ArrayList totDomiciliario = obtenerTotalesPedidosPorDomiciliario(fechaSis);
			respuesta = respuesta + "<table border='2'> <tr> RESUMEN DE DOMICILIARIOS </tr>";
			respuesta = respuesta + "<tr>"
					+  "<td><strong>Domiciliario</strong></td>"
					+  "<td><strong>Total Venta</strong></td>"
					+  "<td><strong>Cantidad Pedidos</strong></td>"
					+  "</tr>";
			for(int y = 0; y < totDomiciliario.size();y++)
			{
				String[] fila =(String[]) totDomiciliario.get(y);
				respuesta = respuesta + "<tr><td>" + fila[1] + "</td><td> " + formatea.format(Double.parseDouble(fila[0])) + "</td><td> " + fila[2] +"</td></tr>";
			}
			respuesta = respuesta + "</table> <br/>";
			
			//Inclusión de resumen de pedidos por hora
			respuesta = respuesta +  "<table border='2'> <tr> RESUMEN DE HORA Y PEDIDOS </tr>";
			respuesta = respuesta + "<tr>"
			+  "<td><strong>Hora Pedido</strong></td>"
			+  "<td><strong>Cantidad Pedidos</strong></td>"
			+  "</tr>";
			ArrayList totPedidoHora = new ArrayList();
			totPedidoHora = obtenerPedidosPorHoras(fechaSis);
	        for(int y = 0; y < totPedidoHora.size();y++)
			{
				String[] fila =(String[]) totPedidoHora.get(y);
				respuesta = respuesta + "<tr><td>" + fila[0] + "</td><td> " + fila[1] +"</td></tr>";
			}
			respuesta = respuesta + "</table> <br/>";
			
			//Final de inclusión pedidos por hora
			
			//Adicionamos los ingresos y egresos
			//INGRESOS
			ArrayList<Ingreso> ingresosDia = operTiendaCtrl.obtenerIngresosObj(fechaSis);
			respuesta = respuesta + "<table border='2'> <tr> INGRESOS </tr>";
			respuesta = respuesta + "<tr>"
					+  "<td><strong>Descripción Ingreso</strong></td>"
					+  "<td><strong>Valor Ingreso</strong></td>"
					+  "</tr>";
			Ingreso ingTemp;
			for(int y = 0; y < ingresosDia.size();y++)
			{
				ingTemp = ingresosDia.get(y);
				respuesta = respuesta + "<tr><td>" + ingTemp.getDescripcion() + "</td><td> " + formatea.format(ingTemp.getValorIngreso()) +"</td></tr>";
			}
			respuesta = respuesta + "</table> <br/>";
			//EGRESOS
			ArrayList<Egreso> egresosDia = operTiendaCtrl.obtenerEgresosObj(fechaSis);
			respuesta = respuesta + "<table border='2'> <tr> EGRESOS/VALES </tr>";
			respuesta = respuesta + "<tr>"
					+  "<td><strong>Descripción egreso</strong></td>"
					+  "<td><strong>Valor Egreso</strong></td>"
					+  "</tr>";
			Egreso egrTemp;
			for(int y = 0; y < egresosDia.size();y++)
			{
				egrTemp = egresosDia.get(y);
				respuesta = respuesta + "<tr><td>" + egrTemp.getDescripcion() + "</td><td> " + formatea.format(egrTemp.getValorEgreso()) +"</td></tr>";
			}
			respuesta = respuesta + "</table> <br/>";
			//CAMBIO FINAL DE DESCUENTOS
			//DESCUENTO DE PIZZAS
			ArrayList<PedidoDescuento> descuentosFecha = obtenerPedidoDescuentoFecha(fechaSis);
			respuesta = respuesta + "<table border='2'> <tr> DESCUENTOS </tr>";
			respuesta = respuesta + "<tr>"
					+  "<td><strong>PEDIDO</strong></td>"
					+  "<td><strong>Fecha Descuento</strong></td>"
					+  "<td><strong>Val Inicial</strong></td>"
					+  "<td><strong>Descuento</strong></td>"
					+  "<td><strong>Val Final</strong></td>"
					+  "<td><strong>% Desc</strong></td>"
					+  "<td><strong>Observacion</strong></td>"
					+  "<td><strong>Usuario</strong></td>"
					+  "<td><strong>Usuario Autorizo</strong></td>"
					+  "</tr>";
			PedidoDescuento descTemp;
			for(int y = 0; y < descuentosFecha.size();y++)
			{
				descTemp = descuentosFecha.get(y);
				String desc = "";
				if(descTemp.getDescuentoPesos() > 0 )
				{
					desc = Double.toString(descTemp.getDescuentoPesos());
				}
				else
				{
					desc = Double.toString(descTemp.getDescuentoPorcentaje());
				}
				respuesta = respuesta + "<tr><td>" + descTemp.getIdpedido() + "</td><td> " + descTemp.getFechaDescuento() + "</td><td> " + formatea.format(descTemp.getValorInicial()) +"</td><td> " + formatea.format(Double.parseDouble(desc)) + "</td><td> " + formatea.format(descTemp.getValorFinal()) + "</td><td> " + formatea.format(descTemp.getDescuentoPorcentaje()) + "</td><td> " + descTemp.getObservacion() +  "</td><td> " + descTemp.getUsuario() +  "</td><td> " + descTemp.getUsuarioAutorizo() +"</td></tr>";
			}
			respuesta = respuesta + "</table> <br/>";
			
			//Adicionaremos a la respuesta y al informe diario unas estádisticas de total de pizzas vendidas y total especialidades
			ArrayList resumenPizzasTamano = obtenerTotalPizzasDiario(fechaSis);
			respuesta = respuesta + "<table border='2'> <tr> RESUMEN POR TAMAÑOS DE PIZZA </tr>";
			respuesta = respuesta + "<tr>"
					+  "<td><strong>TAMAÑO</strong></td>"
					+  "<td><strong>CANTIDAD</strong></td>"
					+  "</tr>";
			String[] fila;
			int cantTotalPizzas = 0;
			for(int y = 0; y < resumenPizzasTamano.size();y++)
			{
				fila = (String[]) resumenPizzasTamano.get(y);
				respuesta = respuesta + "<tr><td>" + fila[0] + "</td><td> " + fila[1]  +"</td></tr>";
				cantTotalPizzas = cantTotalPizzas + Integer.parseInt(fila[1]);
			}
			respuesta = respuesta + "<tr><td>" + "TOTAL" + "</td><td> " + Integer.toString(cantTotalPizzas)  +"</td></tr>";
			respuesta = respuesta + "</table> <br/>";
			
			//Adicionaremos un total de pizzas por tipo 
			ArrayList resumenPizzasTipo = obtenerTotalTipoDiario(fechaSis);
			respuesta = respuesta + "<table border='2'> <tr> RESUMEN TIPO DE PIZZA </tr>";
			respuesta = respuesta + "<tr>"
					+  "<td><strong>TIPO PIZZA</strong></td>"
					+  "<td><strong>CANTIDAD</strong></td>"
					+  "</tr>";
			for(int y = 0; y < resumenPizzasTipo.size();y++)
			{
				fila = (String[]) resumenPizzasTipo.get(y);
				respuesta = respuesta + "<tr><td>" + fila[0] + "</td><td> " + fila[1]  +"</td></tr>";
			}
			respuesta = respuesta + "</table> <br/>";
			
			//Agregaremos un resumen diario de las porciones vendidas
			//Obtenemos el nombre del producto y la cantidad de productos vendidos en el día
			
			ArrayList resumenPorciones = obtenerResumenPorciones(fechasSistema, fechasSistema);
			//Con el arrayList lo recorremos para mostrar la tabla
			respuesta = respuesta + "<table border='2'> <tr> RESUMEN DE PORCIONES VENDIDAS</tr>";
			respuesta = respuesta + "<tr>"
					+  "<td><strong>TIPO DE PORCIÓN</strong></td>"
					+  "<td><strong>CANTIDAD</strong></td>"
					+  "<td><strong>FECHA</strong></td>"
					+  "</tr>";
			for(int y = 0; y < resumenPorciones.size();y++)
			{
				fila = (String[]) resumenPorciones.get(y);
				respuesta = respuesta + "<tr><td>" + fila[0] + "</td><td> " + fila[1] + "</td><td> " + fila[2]  +"</td></tr>";
			}
			respuesta = respuesta + "</table> <br/>";
			
			//Agregaremos un resumen diario de las porciones vendidas por hora
			//Obtenemos la hora y porciones vendidas por hora
			
			ArrayList resumenPorcionesHora = obtenerPorcionesPorHoras(fechasSistema);
			//Con el arrayList lo recorremos para mostrar la tabla
			respuesta = respuesta + "<table border='2'> <tr> RESUMEN DE PORCIONES VENDIDAS POR HORAS</tr>";
			respuesta = respuesta + "<tr>"
					+  "<td><strong>HORA</strong></td>"
					+  "<td><strong>CANTIDAD PORCIONES</strong></td>"
					+  "</tr>";
			for(int y = 0; y < resumenPorcionesHora.size();y++)
			{
				fila = (String[]) resumenPorcionesHora.get(y);
				respuesta = respuesta + "<tr><td>" + fila[0] + "</td><td> " + fila[1] +"</td></tr>";
			}
			respuesta = respuesta + "</table> <br/>";
			return(respuesta);
		}
		
		public String resumenGeneralOperacion(String fechasSistema)
		{
			//Formato para los valores de moneda
			DecimalFormat formatea = new DecimalFormat("###,###");
			//Obtenemos la fecha actual del sistema
			//Vamos a recuperar la fecha del sistema y la vamos a mostrar en el campo correspondiente
			String fechaSis = fechasSistema;
			String respuesta = "";
			
			ArrayList totDomiciliario = obtenerTotalesPedidosPorDomiciliario(fechaSis);
			respuesta = respuesta + "<table border='2'> <tr> RESUMEN DE DOMICILIARIOS </tr>";
			respuesta = respuesta + "<tr>"
					+  "<td><strong>Domiciliario</strong></td>"
					+  "<td><strong>Total Venta</strong></td>"
					+  "<td><strong>Cantidad Pedidos</strong></td>"
					+  "</tr>";
			for(int y = 0; y < totDomiciliario.size();y++)
			{
				String[] fila =(String[]) totDomiciliario.get(y);
				respuesta = respuesta + "<tr><td>" + fila[1] + "</td><td> " + formatea.format(Double.parseDouble(fila[0])) + "</td><td> " + fila[2] +"</td></tr>";
			}
			respuesta = respuesta + "</table> <br/>";
			
			//Inclusión de resumen de pedidos por hora
			respuesta = respuesta +  "<table border='2'> <tr> RESUMEN DE HORA Y PEDIDOS </tr>";
			respuesta = respuesta + "<tr>"
			+  "<td><strong>Hora Pedido</strong></td>"
			+  "<td><strong>Cantidad Pedidos</strong></td>"
			+  "</tr>";
			ArrayList totPedidoHora = new ArrayList();
			totPedidoHora = obtenerPedidosPorHoras(fechaSis);
	        for(int y = 0; y < totPedidoHora.size();y++)
			{
				String[] fila =(String[]) totPedidoHora.get(y);
				respuesta = respuesta + "<tr><td>" + fila[0] + "</td><td> " + fila[1] +"</td></tr>";
			}
			respuesta = respuesta + "</table> <br/>";
			
			//Final de inclusión pedidos por hora
			return(respuesta);
		}
		
		public String resumenSemanalPorciones()
		{
			//Formato para los valores de moneda
			DecimalFormat formatea = new DecimalFormat("###,###");
			String respuesta = "";
			//Información resumida
			ArrayList resPorcionesSemana = obtenerPorcionesResumidosSemana();
			respuesta = respuesta + "<table border='2'> <tr> RESUMEN GENERAL SEMANAL DE MANEJO DE PORCIONES </tr>";
			respuesta = respuesta + "<tr>"
					+  "<td><strong>TIPO DE PORCIÓN</strong></td>"
					+  "<td><strong>CANTIDAD</strong></td>"
					+  "<td><strong>FECHA</strong></td>"
					+  "</tr>";
			for(int y = 0; y < resPorcionesSemana.size();y++)
			{
				String[] fila =(String[]) resPorcionesSemana.get(y);
				respuesta = respuesta + "<tr><td>" + fila[0] + "</td><td> " + formatea.format(Double.parseDouble(fila[1])) + "</td><td> " + fila[2] +"</td></tr>";
			}
			respuesta = respuesta + "</table> <br/>";
			return(respuesta);
		}
		
		public String resumenSemanalDomicilios()
		{
			//Formato para los valores de moneda
			DecimalFormat formatea = new DecimalFormat("###,###");
			String respuesta = "";
			//Información resumida
			ArrayList resDomiciliarioSemana = obtenerDomiciliosResumidosSemana();
			respuesta = respuesta + "<table border='2'> <tr> RESUMEN GENERAL SEMANAL DE DOMICILIARIOS </tr>";
			respuesta = respuesta + "<tr>"
					+  "<td><strong>Domiciliario</strong></td>"
					+  "<td><strong>Total Venta</strong></td>"
					+  "<td><strong>Cantidad Pedidos</strong></td>"
					+  "</tr>";
			for(int y = 0; y < resDomiciliarioSemana.size();y++)
			{
				String[] fila =(String[]) resDomiciliarioSemana.get(y);
				respuesta = respuesta + "<tr><td>" + fila[1] + "</td><td> " + formatea.format(Double.parseDouble(fila[0])) + "</td><td> " + fila[2] +"</td></tr>";
			}
			respuesta = respuesta + "</table> <br/>";
			//Información detallada
			ArrayList totDomiciliarioSemana = obtenerDomiciliosSemana();
			respuesta = respuesta + "<table border='2'> <tr> RESUMEN DETALLADO SEMANAL DE DOMICILIARIOS </tr>";
			respuesta = respuesta + "<tr>"
					+  "<td><strong>Domiciliario</strong></td>"
					+  "<td><strong>Total Venta</strong></td>"
					+  "<td><strong>Cantidad Pedidos</strong></td>"
					+  "<td><strong>Fecha</strong></td>"
					+  "</tr>";
			for(int y = 0; y < totDomiciliarioSemana.size();y++)
			{
				String[] fila =(String[]) totDomiciliarioSemana.get(y);
				respuesta = respuesta + "<tr><td>" + fila[1] + "</td><td> " + formatea.format(Double.parseDouble(fila[0])) + "</td><td> " + fila[2] + "</td><td> " + fila[3] +"</td></tr>";
			}
			respuesta = respuesta + "</table> <br/>";
			return(respuesta);
		}
		
		public String resumenSemanalAnulaciones()
		{
			//Formato para los valores de moneda
			DecimalFormat formatea = new DecimalFormat("###,###");
			String respuesta = "";
			ArrayList<AnulacionPedido> anulacionesSemana = obtenerAnulacionesSemana();
			respuesta = respuesta + "<table border='2'> <tr> RESUMEN SEMANAL DE ANULACIONES </tr>";
			respuesta = respuesta + "<tr>"
					+  "<td><strong>idPedido</strong></td>"
					+  "<td><strong>Fecha</strong></td>"
					+  "<td><strong>Producto</strong></td>"
					+  "<td><strong>Valor</strong></td>"
					+  "<td><strong>Usuario-Anulo</strong></td>"
					+  "<td><strong>Usuario-Autorizo</strong></td>"
					+  "<td><strong>Anulación</strong></td>"
					+  "<td><strong>Observacion</strong></td>"
					+  "</tr>";
			AnulacionPedido anulPedido;
			for(int y = 0; y < anulacionesSemana.size();y++)
			{
				anulPedido = anulacionesSemana.get(y);
				respuesta = respuesta + "<tr><td>" + anulPedido.getIdPedido() + "</td><td>" + anulPedido.getFecha() + "</td><td>" + anulPedido.getProducto() + "</td><td>" + formatea.format(anulPedido.getValor()) + "</td><td> " + anulPedido.getUsuario() + "</td><td> " + anulPedido.getUsuarioAutAnulacion()  + "</td><td> " + anulPedido.getTipoAnulacion() + "</td><td> " + anulPedido.getObservacion() +"</td></tr>";
			}
			respuesta = respuesta + "</table> <br/>";
			return(respuesta);
		}
		
		
		public String resumenSemanalAnulacionesDescuento()
		{
			//Formato para los valores de moneda
			DecimalFormat formatea = new DecimalFormat("###,###");
			String respuesta = "";
			ArrayList<AnulacionPedido> anulacionesSemana = obtenerAnulacionesDescuentaSemana();
			respuesta = respuesta + "<table border='2'> <tr> RESUMEN SEMANAL DE ANULACIONES CAMBIO DE OPINIÓN </tr>";
			respuesta = respuesta + "<tr>"
					+  "<td><strong>idPedido</strong></td>"
					+  "<td><strong>Fecha</strong></td>"
					+  "<td><strong>Producto</strong></td>"
					+  "<td><strong>Valor</strong></td>"
					+  "<td><strong>Usuario-Anulo</strong></td>"
					+  "<td><strong>Usuario-Autorizo</strong></td>"
					+  "<td><strong>Anulación</strong></td>"
					+  "<td><strong>Observacion</strong></td>"
					+  "</tr>";
			AnulacionPedido anulPedido;
			for(int y = 0; y < anulacionesSemana.size();y++)
			{
				anulPedido = anulacionesSemana.get(y);
				respuesta = respuesta + "<tr><td>" + anulPedido.getIdPedido() + "</td><td>" + anulPedido.getFecha() + "</td><td>" + anulPedido.getProducto() + "</td><td>" + formatea.format(anulPedido.getValor()) + "</td><td> " + anulPedido.getUsuario() + "</td><td> " + anulPedido.getUsuarioAutAnulacion() + "</td><td> " + anulPedido.getTipoAnulacion() + "</td><td> " + anulPedido.getObservacion() +"</td></tr>";
			}
			respuesta = respuesta + "</table> <br/>";
			return(respuesta);
		}
		
		public String resumenSemanalEgresos()
		{
			//Formato para los valores de moneda
			DecimalFormat formatea = new DecimalFormat("###,###");
			String respuesta = "";
			ArrayList<Egreso> egresosSemana = obtenerEgresosSemana();
			respuesta = respuesta + "<table border='2'> <tr> RESUMEN SEMANAL DE EGRESOS/VALES </tr>";
			respuesta = respuesta + "<tr>"
					+  "<td><strong>Descripción egreso</strong></td>"
					+  "<td><strong>Valor Egreso</strong></td>"
					+  "<td><strong>Fecha Egreso</strong></td>"
					+  "</tr>";
			Egreso egrTemp;
			for(int y = 0; y < egresosSemana.size();y++)
			{
				egrTemp = egresosSemana.get(y);
				respuesta = respuesta + "<tr><td>" + egrTemp.getDescripcion() + "</td><td> " + formatea.format(egrTemp.getValorEgreso()) + "</td><td>" + egrTemp.getFecha()  +"</td></tr>";
			}
			respuesta = respuesta + "</table> <br/>";
			
			
			//Incorporamos la información de los ingresos al reporte 
			//09-05/2019
			ArrayList<Ingreso> ingresosSemana = obtenerIngresosSemana();
			if(ingresosSemana.size() > 0 )
			{
				respuesta = respuesta + "<table border='2'> <tr> RESUMEN SEMANAL DE INGRESOS </tr>";
				respuesta = respuesta + "<tr>"
						+  "<td><strong>Descripción Ingreso</strong></td>"
						+  "<td><strong>Valor Ingreso</strong></td>"
						+  "<td><strong>Fecha Ingreso</strong></td>"
						+  "</tr>";
				Ingreso ingTemp;
				for(int y = 0; y < ingresosSemana.size();y++)
				{
					ingTemp = ingresosSemana.get(y);
					respuesta = respuesta + "<tr><td>" + ingTemp.getDescripcion() + "</td><td> " + formatea.format(ingTemp.getValorIngreso()) + "</td><td>" + ingTemp.getFecha()  +"</td></tr>";
				}
				respuesta = respuesta + "</table> <br/>";
			}
			return(respuesta);
		}
		
		
		public String resumenInventarios()
		{
			//Obtenemos la fecha actual del sistema
			//Vamos a recuperar la fecha del sistema y la vamos a mostrar en el campo correspondiente
			FechaSistema fecha = obtenerFechasSistema();
			String fechaSis = fecha.getFechaApertura();
			String respuesta = "<table border='2'> <tr> RESUMEN DE INVENTARIOS </tr>";
			respuesta = respuesta + "<tr>"
			+  "<td><strong>Nombre Item</strong></td>"
			+  "<td><strong>Unidad</strong></td>"
			+  "<td><strong>Cantidad</strong></td>"
			+  "</tr>";
			
	        ArrayList<ItemInventario>itemsInventario = parProductoCtrl.obtenerItemsInventariosObj();
	        ItemInventario itemTemp;
	        for(int y = 0; y < itemsInventario.size();y++)
			{
	        	itemTemp = itemsInventario.get(y);
				respuesta = respuesta + "<tr><td>" + itemTemp.getNombreItem() + "</td><td> " + itemTemp.getUnidadMedida() + "</td><td> " + itemTemp.getCantidad() +"</td></tr>";
			}
			respuesta = respuesta + "<br/> </table>";
			return(respuesta);
		}
		
		
		public void imprimirResumenGeneralVentas()
		{
			String respuesta = "======================================\n";
			respuesta = respuesta + "  RESUMEN GENERAL DE VENTAS     \n";
			respuesta = respuesta + resumenGeneralVentasImprimir();
			respuesta = respuesta + "======================================\n";
			respuesta = respuesta  + "\n\n\n\n\n\n\n           "
		            + "\n           ";
			if(Sesion.getModeloImpresion() != 1)
			{
				ImprimirAdmDAO.insertarImpresion(respuesta, auditoria);
			}
			else
			{
				Impresion.main(respuesta);
			}
		}
		
		//Impresión de Corte de Caja
		public void imprimirResumenCorteCaja(String fechaActual)
		{
			String respuesta = "======================================\n";
			respuesta = respuesta + "  RESUMEN GENERAL DE VENTAS - CORTE DE CAJA     \n";
			respuesta = respuesta  + fechaActual + "     \n";
			respuesta = respuesta + generarCorteCajaImpresora(fechaActual);
			respuesta = respuesta + "======================================\n";
			respuesta = respuesta  + "\n\n\n\n\n\n\n           "
		            + "\n           ";
			if(Sesion.getModeloImpresion() != 1)
			{
				ImprimirAdmDAO.insertarImpresion(respuesta, auditoria);
			}
			else
			{
				Impresion.main(respuesta);
			}
		}
		
		public String generarCorteCajaImpresora(String fechaActual)
		{
			DecimalFormat formatea = new DecimalFormat("###,###");
			String respuesta = "";
			ArrayList repCaja = new ArrayList();
	        repCaja = obtenerReporteDeCaja(fechaActual);
	        respuesta = respuesta + "  ======================================\n"
            + "    Usuario    Valor     Forma de Pago\n"
            + "  ======================================\n";
	        for(int i = 0; i < repCaja.size(); i++)
	        {
	        	String[] regTemp =(String[]) repCaja.get(i);
	        	respuesta = respuesta + regTemp[1] + "   " + formatea.format(Double.parseDouble(regTemp[0])) + "    " + regTemp[2] + "\n";
	        }
	        respuesta = respuesta + resumenGeneralVentasImprimir();
			return(respuesta);
		}
		
		//ENVIO DE CORREOS
		
		public void enviarCorreoResumenGeneralVentas()
		{
			//Obtenemos la fecha
			FechaSistema fecha = obtenerFechasSistema();
			String fechaSis = fecha.getFechaUltimoCierre();
			//Obtenemos la tienda
			Tienda tiendaReporte = TiendaDAO.obtenerTienda(auditoria);
			//Obtenemos el reporte
			String reporte = resumenGeneralVentas(fechaSis);
			Correo correo = new Correo();
			correo.setAsunto(tiendaReporte.getNombretienda()+" : " + "Reporte Ventas " + fechaSis);
			correo.setContrasena("Pizzaamericana2017");
			ArrayList correos = GeneralDAO.obtenerCorreosParametro("REPORTEGENERALVENTAS", auditoria);
			correo.setUsuarioCorreo("alertaspizzaamericana@gmail.com");
			correo.setMensaje("A continuación el reporte general de ventas: \n" + reporte);
			ControladorEnvioCorreo contro = new ControladorEnvioCorreo(correo, correos);
			contro.enviarCorreo();
		}
		
		public void enviarCorreoResumenGeneralOperacion()
		{
			//Obtenemos la fecha
			FechaSistema fecha = obtenerFechasSistema();
			String fechaSis = fecha.getFechaUltimoCierre();
			//Obtenemos la tienda
			Tienda tiendaReporte = TiendaDAO.obtenerTienda(auditoria);
			//Obtenemos el reporte
			String reporte = resumenGeneralOperacion(fechaSis);
			Correo correo = new Correo();
			correo.setAsunto(tiendaReporte.getNombretienda()+" : " + "Reporte Operación " + fechaSis);
			correo.setContrasena("Pizzaamericana2017");
			ArrayList correos = GeneralDAO.obtenerCorreosParametro("REPORTEOPERACION", auditoria);
			correo.setUsuarioCorreo("alertaspizzaamericana@gmail.com");
			correo.setMensaje("A continuación el reporte general de Operacion: \n" + reporte);
			ControladorEnvioCorreo contro = new ControladorEnvioCorreo(correo, correos);
			contro.enviarCorreo();
		}
		
		public void enviarCorreoResumenInventario()
		{
			//Obtenemos la fecha
			FechaSistema fecha = obtenerFechasSistema();
			String fechaSis = fecha.getFechaUltimoCierre();
			//Obtenemos la tienda
			Tienda tiendaReporte = TiendaDAO.obtenerTienda(auditoria);
			//Obtenemos el reporte
			String reporte = resumenInventarios();
			Correo correo = new Correo();
			correo.setAsunto(tiendaReporte.getNombretienda() + " : " + "Inventario "  + fechaSis);
			correo.setContrasena("Pizzaamericana2017");
			ArrayList correos = GeneralDAO.obtenerCorreosParametro("REPORTEGENERALVENTAS", auditoria);
			correo.setUsuarioCorreo("alertaspizzaamericana@gmail.com");
			correo.setMensaje("A continuación el reporte de Inventarios: \n" + reporte);
			ControladorEnvioCorreo contro = new ControladorEnvioCorreo(correo, correos);
			contro.enviarCorreo();
		}
		
		public void enviarCorreoDomiciliosSemanal()
		{
			//Obtenemos la fecha
			FechaSistema fecha = obtenerFechasSistema();
			String fechaSis = fecha.getFechaUltimoCierre();
			//Obtenemos la tienda
			Tienda tiendaReporte = TiendaDAO.obtenerTienda(auditoria);
			//Obtenemos el reporte
			String reporte = resumenSemanalDomicilios();
			Correo correo = new Correo();
			correo.setAsunto(tiendaReporte.getNombretienda() + " : " + "SEMANAL Resumen de Domicilios " + fechaSis);
			correo.setContrasena("Pizzaamericana2017");
			ArrayList correos = GeneralDAO.obtenerCorreosParametro("REPORTESEMANALES", auditoria);
			correo.setUsuarioCorreo("alertaspizzaamericana@gmail.com");
			correo.setMensaje("A continuación el resumen de pedidos llevados por domiciliarios en la semana que finaliza: \n" + reporte);
			ControladorEnvioCorreo contro = new ControladorEnvioCorreo(correo, correos);
			contro.enviarCorreo();
		}
		
		public void enviarCorreoPorcionesSemanal()
		{
			//Obtenemos la fecha
			FechaSistema fecha = obtenerFechasSistema();
			String fechaSis = fecha.getFechaUltimoCierre();
			//Obtenemos la tienda
			Tienda tiendaReporte = TiendaDAO.obtenerTienda(auditoria);
			//Obtenemos el reporte
			String reporte = resumenSemanalPorciones();
			Correo correo = new Correo();
			correo.setAsunto(tiendaReporte.getNombretienda() + " : " + "SEMANAL Resumen de Porciones " + fechaSis);
			correo.setContrasena("Pizzaamericana2017");
			ArrayList correos = GeneralDAO.obtenerCorreosParametro("REPORTESEMANALES", auditoria);
			correo.setUsuarioCorreo("alertaspizzaamericana@gmail.com");
			correo.setMensaje("A continuación el resumen de las porciones vendidas por tipo y fecha en la semana que finaliza: \n" + reporte);
			ControladorEnvioCorreo contro = new ControladorEnvioCorreo(correo, correos);
			contro.enviarCorreo();
		}
				
		public void enviarCorreoDescuentosSemanal()
		{
			//Obtenemos la fecha del rango del reporte
			FechaSistema fecha = obtenerFechasSistema();
			String fechaSis = fecha.getFechaUltimoCierre();
			//Obtenemos la tienda
			Tienda tiendaReporte = TiendaDAO.obtenerTienda(auditoria);
			//Obtenemos el reporte
			String reporte = reporteSemanalDescuentos();
			Correo correo = new Correo();
			correo.setAsunto(tiendaReporte.getNombretienda() + " : " + "SEMANAL Reporte Descuentos " + fechaSis);
			correo.setContrasena("Pizzaamericana2017");
			ArrayList correos = GeneralDAO.obtenerCorreosParametro("REPORTESEMDESCUENTOS", auditoria);
			correo.setUsuarioCorreo("alertaspizzaamericana@gmail.com");
			correo.setMensaje("A continuación el reporte semanal de descuentos: \n" + reporte);
			ControladorEnvioCorreo contro = new ControladorEnvioCorreo(correo, correos);
			contro.enviarCorreo();
		}
		
		public void enviarCorreoAnulacionesSemanal()
		{
			//Obtenemos la fecha del rango del reporte
			FechaSistema fecha = obtenerFechasSistema();
			String fechaSis = fecha.getFechaUltimoCierre();
			//Obtenemos la tienda
			Tienda tiendaReporte = TiendaDAO.obtenerTienda(auditoria);
			//Obtenemos el reporte
			String reporte = resumenSemanalAnulaciones();
			Correo correo = new Correo();
			correo.setAsunto(tiendaReporte.getNombretienda() + " : " + "SEMANAL Reporte Anulaciones - No Devuelve Inventario " + fechaSis);
			correo.setContrasena("Pizzaamericana2017");
			ArrayList correos = GeneralDAO.obtenerCorreosParametro("REPORTESEMANULA", auditoria);
			correo.setUsuarioCorreo("alertaspizzaamericana@gmail.com");
			correo.setMensaje("A continuación el reporte semanal de Anulaciones: \n" + reporte);
			ControladorEnvioCorreo contro = new ControladorEnvioCorreo(correo, correos);
			contro.enviarCorreo();
		}
		
		public void enviarCorreoAnulacionesDescuentaSemanal()
		{
			//Obtenemos la fecha del rango del reporte
			FechaSistema fecha = obtenerFechasSistema();
			String fechaSis = fecha.getFechaUltimoCierre();
			//Obtenemos la tienda
			Tienda tiendaReporte = TiendaDAO.obtenerTienda(auditoria);
			//Obtenemos el reporte
			String reporte = resumenSemanalAnulacionesDescuento();
			Correo correo = new Correo();
			correo.setAsunto(tiendaReporte.getNombretienda() + " : " + "SEMANAL Reporte Anulaciones Devuelve Inventario " + fechaSis);
			correo.setContrasena("Pizzaamericana2017");
			ArrayList correos = GeneralDAO.obtenerCorreosParametro("REPORTESEMANULADESC", auditoria);
			correo.setUsuarioCorreo("alertaspizzaamericana@gmail.com");
			correo.setMensaje("A continuación el reporte semanal de Anulaciones que provienen de un cambio de opinión y que por lo tanto de devuelven al inventario lo anulado: \n" + reporte);
			ControladorEnvioCorreo contro = new ControladorEnvioCorreo(correo, correos);
			contro.enviarCorreo();
		}
		
		
		public void enviarCorreoEgresosSemanal()
		{
			//Obtenemos la fecha del rango del reporte
			FechaSistema fecha = obtenerFechasSistema();
			String fechaSis = fecha.getFechaUltimoCierre();
			//Obtenemos la tienda
			Tienda tiendaReporte = TiendaDAO.obtenerTienda(auditoria);
			//Obtenemos el reporte
			String reporte = resumenSemanalEgresos();
			Correo correo = new Correo();
			correo.setAsunto(tiendaReporte.getNombretienda() + " : " +"SEMANAL Reporte Egresos/Ingresos " + fechaSis);
			correo.setContrasena("Pizzaamericana2017");
			ArrayList correos = GeneralDAO.obtenerCorreosParametro("REPORTESEMEGRESOS", auditoria);
			correo.setUsuarioCorreo("alertaspizzaamericana@gmail.com");
			correo.setMensaje("A continuación el reporte semanal de los egresos/vales de la semana que acaba de finalizar: \n" + reporte);
			ControladorEnvioCorreo contro = new ControladorEnvioCorreo(correo, correos);
			contro.enviarCorreo();
		}
		
		public String resumenDetalladoVentas()
		{
			return("");
		}
		
		
		public ArrayList obtenerReporteDeCaja(String fechaActual)
		{
			ArrayList reporteCaja = PedidoDAO.obtenerReporteDeCaja(fechaActual,  auditoria);
			return(reporteCaja);
		}
		
		public ArrayList obtenerPorcionesResumidosSemana()
		{
			//Recuperamos la fecha actual del sistema con la fecha apertura
			FechaSistema fecha = obtenerFechasSistema();
			String fechaActual = fecha.getFechaUltimoCierre();
			//Variables donde manejaremos la fecha anerior con el fin realizar los cálculos de ventas
			Date datFechaAnterior;
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
				calendarioActual.add(Calendar.DAY_OF_YEAR, -6);
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
			//Llevamos a un string la fecha anterior para el cálculo de la venta
			datFechaAnterior = calendarioActual.getTime();
			fechaAnterior = dateFormat.format(datFechaAnterior);
			ArrayList resumenPorcionesSemana =  obtenerResumenPorciones(fechaAnterior, fechaActual);
			return(resumenPorcionesSemana);
		}
		
		public ArrayList obtenerPorcionesPorHoras(String fechaPedido)
		{
			//Sacamos el resumen de los productos de porción
			ParametrosCtrl parCtrl = new ParametrosCtrl(auditoria);
			Parametro parametro = parCtrl.obtenerParametro("PRODUCTOPORCION");
			long valNum = 0;
			int idProductoPorcion = 0;
			int idPorcionTemporal = 0;
			int idPorcionEmpleado = 0;
			int idPorcionDesecho = 0;
			int idGaseosaPorcion = 0;
			try
			{
				valNum = (long) parametro.getValorNumerico();
			}catch(Exception exc)
			{
				System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE PRODUCTO PORCIÓN");
			}
			//Asignamos el producto de porciones para con este facturar
			idProductoPorcion =(int) valNum;
			
			//Tomamos el idProducto para porción de temporales
			parametro = parCtrl.obtenerParametro("PORCIONTEMPORALES");
			valNum = 0;
			try
			{
				valNum = (long) parametro.getValorNumerico();
			}catch(Exception exc)
			{
				System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE PRODUCTO PARA PORCIONES DE TERMPORALES");
			}
			idPorcionTemporal = (int) valNum;

			//Capturamos la informacion para la porcion empleado
			parametro = parCtrl.obtenerParametro("PORCIONEMPLEADO");
			valNum = 0;
			try
			{
				valNum = (long) parametro.getValorNumerico();
			}catch(Exception exc)
			{
				System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE PRODUCTO PARA PORCIONES DE EMPLEADO");
			}
			idPorcionEmpleado = (int) valNum;

			
			//Capturamos la información de la porcion desecho
			parametro = parCtrl.obtenerParametro("PORCIONDESECHO");
			valNum = 0;
			try
			{
				valNum = (long) parametro.getValorNumerico();
			}catch(Exception exc)
			{
				System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE PRODUCTO PARA PORCIONES DE EMPLEADO");
			}
			idPorcionDesecho = (int) valNum;
			
			//Capturamos la información de la gaseosa
			parametro = parCtrl.obtenerParametro("PRODUCTOGASEOSA");
			valNum = 0;
			try
			{
				valNum = (long) parametro.getValorNumerico();
			}catch(Exception exc)
			{
				System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE PRODUCTO PARA LA GASEOSA DE PORCIÓN");
			}
			idGaseosaPorcion = (int) valNum;
			//Ya capturados todos los valores de los productos de porciones llamaremos un método que con está información se encargará de extraer toda la 
			//información.
			ArrayList reporteResumenPorcionesHora = PedidoDAO.obtenerPorcionesPorHoras(fechaPedido, idProductoPorcion, idPorcionTemporal, idPorcionEmpleado, idPorcionDesecho, idGaseosaPorcion, auditoria );
			return(reporteResumenPorcionesHora);
		}
		
		public ArrayList obtenerResumenPorciones(String fechaAnterior, String fechaPosterior)
		{
			//Sacamos el resumen de los productos de porción
			ParametrosCtrl parCtrl = new ParametrosCtrl(auditoria);
			Parametro parametro = parCtrl.obtenerParametro("PRODUCTOPORCION");
			long valNum = 0;
			int idProductoPorcion = 0;
			int idPorcionTemporal = 0;
			int idPorcionEmpleado = 0;
			int idPorcionDesecho = 0;
			int idGaseosaPorcion = 0;
			try
			{
				valNum = (long) parametro.getValorNumerico();
			}catch(Exception exc)
			{
				System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE PRODUCTO PORCIÓN");
			}
			//Asignamos el producto de porciones para con este facturar
			idProductoPorcion =(int) valNum;
			
			//Tomamos el idProducto para porción de temporales
			parametro = parCtrl.obtenerParametro("PORCIONTEMPORALES");
			valNum = 0;
			try
			{
				valNum = (long) parametro.getValorNumerico();
			}catch(Exception exc)
			{
				System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE PRODUCTO PARA PORCIONES DE TERMPORALES");
			}
			idPorcionTemporal = (int) valNum;

			//Capturamos la informacion para la porcion empleado
			parametro = parCtrl.obtenerParametro("PORCIONEMPLEADO");
			valNum = 0;
			try
			{
				valNum = (long) parametro.getValorNumerico();
			}catch(Exception exc)
			{
				System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE PRODUCTO PARA PORCIONES DE EMPLEADO");
			}
			idPorcionEmpleado = (int) valNum;

			
			//Capturamos la información de la porcion desecho
			parametro = parCtrl.obtenerParametro("PORCIONDESECHO");
			valNum = 0;
			try
			{
				valNum = (long) parametro.getValorNumerico();
			}catch(Exception exc)
			{
				System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE PRODUCTO PARA PORCIONES DE EMPLEADO");
			}
			idPorcionDesecho = (int) valNum;
			
			//Capturamos la información de la gaseosa
			parametro = parCtrl.obtenerParametro("PRODUCTOGASEOSA");
			valNum = 0;
			try
			{
				valNum = (long) parametro.getValorNumerico();
			}catch(Exception exc)
			{
				System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE PRODUCTO PARA LA GASEOSA DE PORCIÓN");
			}
			idGaseosaPorcion = (int) valNum;
			//Ya capturados todos los valores de los productos de porciones llamaremos un método que con está información se encargará de extraer toda la 
			//información.
			
			ArrayList reporteResumenPorciones = PedidoDAO.obtenerResumenPorciones(fechaAnterior,  fechaPosterior, idProductoPorcion, idPorcionTemporal, idPorcionEmpleado, idPorcionDesecho, idGaseosaPorcion, auditoria );
			
						
			return(reporteResumenPorciones);
		}
		
		public ArrayList obtenerReporteDeCajaDetallado(String fechaActual)
		{
			ArrayList reporteCaja = PedidoDAO.obtenerReporteDeCajaDetallado(fechaActual,  auditoria);
			return(reporteCaja);
		}
		
		/**
		 * Método que se encarga en la capa de Acceso a Datos de desasignar un domiciliario a un pedido en ruta
		 * @param idPedido se recibe el pedido en el cual se desea realizar la labor
		 * @param auditoria se define si se debe o no generar auditorias de logs
		 * @return Se retorna un valor booleano con el resultado del proceso.
		 */
		public boolean desasignarDomiciliarioPedido(int idPedido)
		{
			boolean resultado = PedidoDAO.desasignarDomiciliarioPedido(idPedido, auditoria);
			return(resultado);
		}
		
		public int obtenerCantidadPedidoPorEstado(String fechaSistema, int idEstado )
		{
			int cantidad = PedidoDAO.obtenerCantidadPedidoPorEstado(fechaSistema, idEstado, auditoria);
			return(cantidad);
					
		}
		
		//Pedidos para de ventana de Cocina
		
		public ArrayList obtenerPedidosVentanaCocina(ArrayList<Integer> estadosCocina)
		{
			//Cuando el empleado es domiciliario, se deberán mostrar los pedidos propios del tipo domiciliario, ddeberán mostrar los pedidos que ha llevado y con los que salío
			//hace poco para llevar.
			FechaSistema fechaSistema = TiendaDAO.obtenerFechasSistema( auditoria);
			ArrayList pedidos = new ArrayList();
			if(sePuedeFacturar(fechaSistema))
			{
				//Obtenemos los pedidos
				pedidos = PedidoDAO.obtenerPedidosVentanaCocina(fechaSistema.getFechaApertura(), estadosCocina, auditoria);
				//Obtenemos los tiempos de Pedido
				ArrayList<TiempoPedido> tiemposPedido = calcularTiemposPedidos(fechaSistema.getFechaApertura());
				
				int idPedido = 0;
				TiempoPedido tiempoPedidoTemp = new TiempoPedido(0,"");
				String tiempoPedido = "";
				//Recorremos el resultado de los pedidos para ir agregando el tiempo de pedido
				for(int i = 0 ; i < pedidos.size(); i++)
				{
					//Sacamos cada fila del pedido
					String[]fila = (String[]) pedidos.get(i);
					//Extraemos el idPedido
					idPedido =Integer.parseInt(fila[0]);
					// Recorremos el ArrayList de los tiempos pedidos.
					for(int j = 0; j < tiemposPedido.size(); j++)
					{
						tiempoPedidoTemp = tiemposPedido.get(j);
						// SI el pedido es igual al que estamos procesando extraemos el tiempo del pedido
						if(tiempoPedidoTemp.getIdPedidoTienda() == idPedido)
						{
							tiempoPedido = tiempoPedidoTemp.getTiempoPedido();
							break;
						}
					}
					fila[fila.length-1] = tiempoPedido;
					pedidos.set(i, fila);
				}
			}
			return(pedidos);
		}
		
		//PINTAR PEDIDOS EN DIFERENTES LUGARES DEL SISTEMA
		
		/**
		 * Método que permite pintar un pedido con el fin de quien lo va a montar tenga el detalle de lo que se está preparando
		 */
		public int pintarPedido(int idPedido, Graphics2D g2d)
		{
			//Consultamos los detalles del pedido
			ArrayList<DetallePedido> detPedidos = obtenerDetallePedidoPintar(idPedido);
			//Definimos tamaños de ancho y alto para los diferentes tamanos
			int anchoXL = 200;
			int anchoGD = 180;
			int anchoMD = 130;
			int anchoPZ = 120;
			//Retornamos el graphics con el cual vamos a pintar el pedido
			g2d.setColor(Color.BLACK);
			//Fijamos un ancho a las lineas 
			g2d.setStroke( new BasicStroke( 2 ) );
			//Con este tomamos el idDetallePedidoMaster del detalle que estamos procesando en el momento
			int idMasterActual = 0;
			//Con este tomamos el idDetallePedidoMaster del detalle que procesamos en el detalle anterior
			//esto con el objetivo si ya hay un cambio del detalle padre delpedido.
			int idMasterAnterior = 0;
			//Tomamos el tipo de producto del Detalle Master
			String tipProdMaster = "";
			//Con esta variable tomamos el tamano para el caso de que sea un producto que lo tiene.
			String tamano = "";
			//Con las variables booleanas div1 y div2, controlamos si estamos procesando que mitad para los tipos de producto Principal
			boolean div1 = false;
			boolean div2 = false;
			//Variable en la que se llevará el control del ancho actual del producto
			int anchoActual = 0;
			//Variable que controla los items pedidos a pintar.
			int itemPintar = 1;
			//Por intermedio de este ciclo recorremos todos los detalles pedidos que vamos a pintar
			//Variables donde almacenaremos los string de las adiciones de la pizza
			String adicion1 = "";
			String adicion2 = "";
			String con1 = "";
			String con2 = "";
			String sin1 = "";
			String sin2 = "";
			for(int i = 0; i < detPedidos.size(); i++)
			{
				//Obtenemos el detalle pedido que estamos recorriendo.
				DetallePedido detTem = detPedidos.get(i);
				//Luego de obtenido del detallePedido debemos validar si este esta anulado para saber qeu no lo debemos de pintar
				if((detTem.getEstado() == null)||(detTem.getEstado().equals(new String(""))))
				{
					//Obtenemos el tipo de producto del detalle que estamos procesando
					String tipPro = detTem.getTipoProducto();
					
					//Tomamos el iddetalleMaster del detalle pedido que estamos procesando 
					idMasterActual = detTem.getIdDetallePedidoMaster();
					//Si se da la condición de que idMasterActual = 0 es poruqe el item es un master
					if(idMasterActual == 0)
					{
						idMasterActual = detTem.getIdDetallePedido();
					}
					//Almacenamos el tamano del producto en caso de requerirse más adelante
					tamano = detTem.getTamano();
					//Comparamos si el Detalle Master actual es igual al anterior, significa qeu estamos procesando los detalles del pedido
					if((idMasterActual == idMasterAnterior)&&(!tipPro.equals(new String("O"))))
					{
						//Preguntamos si el tipo de producto es "D" Detalle en este punto vendría el sabor de la pizza
						if(tipPro.trim().equals(new String("D")))
						{
							// Si la div1 es verdadero signfica que vamos para la segunda división, prendemos la segunda división
							if(div1)
							{
								div2 = true;
							}
							else
							{
								//Si es un tipo de producto división y no estaba prendido div1, signfica que se debe de prender.
								div1 = true;
								//Debemos de pintar la especialidad 1
							}
							if(div1 & !div2)
							{
								//Pintamos el sabor en la primera parte PERO PARA SABER EL PUNTO ES IMPORTANTE SABER EL TAMAÑO
								g2d.setFont( new Font( "Tahoma", Font.BOLD, 10 ) );
								g2d.setColor(Color.BLACK);
								g2d.drawString(detTem.getDescCortaProducto().toUpperCase(), 160, (250*(itemPintar - 1)+40));
							}
							else if(div1 & div2)
							{
								//Pintamos el sabor en la segunda parte
								g2d.setFont( new Font( "Tahoma", Font.BOLD, 10 ) );
								g2d.setColor(Color.BLACK);
								g2d.drawString(detTem.getDescCortaProducto().toUpperCase(), 260 + (anchoActual/2), (250*(itemPintar - 1)+40));
							}
						}else if(tipPro.trim().equals(new String("A")))
						{
							//DEBEMOS DE PINTAR LA ADICIÓN
							if(div1 & !div2)
							{
								//Pintamos la adición en la primera parte
								adicion1 = adicion1 + "+" + detTem.getDescCortaProducto();
							}
							else if(div1 & div2)
							{
								//Pintamos la adicion en la segunda parte
								adicion2 = adicion2 + "+" + detTem.getDescCortaProducto();
							}
						}
						else if(tipPro.trim().equals(new String("MC")))
						{
							//DEBEMOS DE PINTAR EL MODIFICADOR
							if(div1 & !div2)
							{
								//Pintamos el modificador con en la primera parte
								con1 = con1 + " " + detTem.getDescCortaProducto();
							}
							else if(div1 & div2)
							{
								//Pintamos el modificador con en la segunda parte
								con2 = con2 + " " + detTem.getDescCortaProducto();
							}
						}
						else if(tipPro.trim().equals(new String("MS")))
						{
							//DEBEMOS DE PINTAR EL MODIFICADOR
							if(div1 & !div2)
							{
								//Pintamos el modificador sin en la primera parte
								sin1 = sin1 + " " + detTem.getDescCortaProducto();
							}
							else if(div1 & div2)
							{
								//Pintamos el modificador sin en la  segunda parte
								sin2 = sin2 + " " + detTem.getDescCortaProducto();
							}
						}
						
					}
					else//Por este camino debería de irse cuando hay una especie de quiebre en id Detalle pedido Master
					{
						//Antes de hacer los cambios preguntamos si hay adiciones en cuyo caso las pintamos
						// SI se cumple esta condición es porque hay una adición
						if(!adicion1.equals(new String(""))||!adicion2.equals(new String(""))||!con1.equals(new String(""))||!con2.equals(new String(""))||!sin1.equals(new String(""))||!sin2.equals(new String("")))
						{
							//pintamos las adicines
							g2d.setFont( new Font( "Tahoma", Font.BOLD, 10 ) );
							g2d.setColor(Color.BLACK);
							// Se dibujan las adiciones
							g2d.drawString(adicion1, 160 , (100*(itemPintar - 1)+60));
							g2d.drawString(adicion2, 260 + (anchoActual/2), (100*(itemPintar - 1)+60));
							// Se dibujan los modificadores con
							g2d.drawString(con1, 160 , (100*(itemPintar - 1)+80));
							g2d.drawString(con2, 260 + (anchoActual/2), (100*(itemPintar - 1)+80));
							// Se dibujan los modificadore sin
							g2d.drawString(sin1, 160 , (100*(itemPintar - 1)+100));
							g2d.drawString(sin2, 260 + (anchoActual/2), (100*(itemPintar - 1)+100));
							adicion1="";
							adicion2="";
							sin1 = "";
							sin2 = "";
							con1 = "";
							con2 = "";
						}
						
						if (div1 && div2)
						{
							itemPintar++;
						}
						
						//Pintar la linea separadora del pedido
						g2d.setColor(Color.BLACK);
						g2d.drawLine(0, 200*(itemPintar-1), 701, 200*(itemPintar-1));
						//Aumentamos los items a pintar
						tipProdMaster = tipPro;
						div1 = false;
						div2 = false;
						if(tipPro.trim().equals(new String("P")))
						{
							//Antes de cualquier drawString deberemos tener el setfont y el setcolor
							g2d.setFont( new Font( "Tahoma", Font.BOLD, 32 ) );
							g2d.setColor(Color.BLACK);
							g2d.drawString(tamano, 10, (250*(itemPintar - 1)+40));
							if(tamano.trim().equals(new String("XL")))
							{
								anchoActual = anchoXL;
								//Pintamos el círculo, teniendo en cuenta itemPintar que nos da el credimiento en la coordenanda Y para deliminar el item que estamos pintando
								//Tenemos unas variables fijas de ancho por tamaño
								g2d.setColor(Color.BLUE);
								g2d.drawOval(250, 250*(itemPintar-1), anchoXL,  anchoXL);
								g2d.drawLine(250+(anchoXL/2), 250*(itemPintar-1), 250+(anchoXL/2), 250*(itemPintar-1) + anchoXL);
							}else if(tamano.trim().equals(new String("GD")))
							{
								anchoActual = anchoGD;
								g2d.setColor(Color.RED);
								g2d.drawOval(250, 250*(itemPintar-1), anchoGD,  anchoGD);
								g2d.drawLine(250+(anchoGD/2), 250*(itemPintar-1), 250+(anchoGD/2), 250*(itemPintar-1) + anchoGD);
							}else if(tamano.trim().equals(new String("MD")))
							{
								anchoActual = anchoMD;
								g2d.setColor(Color.GREEN);
								g2d.drawOval(250, 250*(itemPintar-1), anchoMD,  anchoMD);
								g2d.drawLine(250+(anchoMD/2), 250*(itemPintar-1), 250+(anchoMD/2), 250*(itemPintar-1) + anchoMD);
							}else if(tamano.trim().equals(new String("PZ")))
							{
								anchoActual = anchoPZ;
								g2d.setColor(Color.ORANGE);
								g2d.drawOval(250, 250*(itemPintar-1), anchoPZ,  anchoPZ);
								g2d.drawLine(250+(anchoPZ/2), 250*(itemPintar-1), 250+(anchoPZ/2), 250*(itemPintar-1) + anchoPZ);
							}
							
						}else if(tipPro.trim().equals(new String("O")))
						{
							//Pintamos con la imagen que tambien se almacenaría en base de datos
							//Obtenemos el idProducto
							int idProd = detTem.getIdProducto();
							Producto otroProd = parProductoCtrl.obtenerProducto(idProd);
							
							try
							{
								Image image = null;
								InputStream in = new ByteArrayInputStream(otroProd.getImagen());
								image = ImageIO.read(in);
								//ImageIcon imgi = new ImageIcon(image.getScaledInstance(60, 60, 0));
								g2d.drawImage(image,250, 250*(itemPintar-1) , null);
							}catch(Exception e)
							{
								
							}
							itemPintar++;
						}
						//PINTAMOS LA CANTIDAD DEL PRODUCTO
						g2d.setFont( new Font( "Tahoma", Font.BOLD, 15 ) );
						g2d.setColor(Color.BLACK);
						g2d.drawString("CANTIDAD " + Double.toString(detTem.getCantidad()), 10, (250*(itemPintar - 1)+100));
						
					}
					//Independinete del camino debemos de tomar cual es  id Detalle Pedido Master anterior
					idMasterAnterior = idMasterActual;
				}
				
			}
			//MOD 29/08/2018
			//Se realiza esta modificación para que si solo hubo un producto master al final se pinte las adiciones y los productos CON que se tienen
			//Lo anterior debido a que como no hay cambio de producto master no se cumple la condición y no se está imprimiendo esta información
			if(!adicion1.equals(new String(""))||!adicion2.equals(new String(""))||!con1.equals(new String(""))||!con2.equals(new String(""))||!sin1.equals(new String(""))||!sin2.equals(new String("")))
			{
				//pintamos las adicines
				g2d.setFont( new Font( "Tahoma", Font.BOLD, 10 ) );
				g2d.setColor(Color.BLACK);
				// Se dibujan las adiciones
				g2d.drawString(adicion1, 220 , (100*(itemPintar - 1)+60));
				g2d.drawString(adicion2, 260 + (anchoActual/2), (100*(itemPintar - 1)+70));
				// Se dibujan los modificadores con
				g2d.drawString(con1, 50 , (100*(itemPintar - 1)+80));
				g2d.drawString(con2, 260 + (anchoActual/2), (100*(itemPintar - 1)+90));
				// Se dibujan los modificadore sin
				g2d.drawString(sin1, 220 , (100*(itemPintar - 1)+100));
				g2d.drawString(sin2, 260 + (anchoActual/2), (100*(itemPintar - 1)+110));
				adicion1="";
				adicion2="";
				sin1 = "";
				sin2 = "";
				con1 = "";
				con2 = "";
			}
			return(itemPintar);
		}
		
		/**
		 * Método que se encargaría de pintar los productos principales de un pedido de manera que ayude en el montaje de los mismos.
		 * @param g2d Parámetros de tipo Graphics para pintar en el panel determinado
		 * @param pedidos ArrayList con los pedidos que se van a pintar
		 */
		public int pintarPedidos(Graphics2D g2d, ArrayList pedidos)
		{
			int idPedidoTrabajar = 0;
			ArrayList<DetallePedido> detPedidos;
			int contadorPintar = 0;
			for(int j = 0; j < pedidos.size(); j++)
			{
				String[] infTemp = (String[]) pedidos.get(j);
				idPedidoTrabajar = Integer.parseInt(infTemp[0]);
				//Fijamos el número de pedido en el encabezado gráfico
				g2d.setFont( new Font( "Tahoma", Font.BOLD, 30 ) );
				g2d.setColor(Color.RED);
				g2d.drawString(Integer.toString(idPedidoTrabajar), 0, (60*(contadorPintar))+30);
				contadorPintar++;
				//Retornamos el graphics con el cual vamos a pintar el pedido
				g2d.setColor(Color.BLACK);
				//Fijamos un ancho a las lineas 
				g2d.setStroke( new BasicStroke( 2 ) );
				//Con este tomamos el idDetallePedidoMaster del detalle que estamos procesando en el momento
				//Tomamos el tipo de producto del Detalle Master
				String tipProdMaster = "";
				//Con esta variable tomamos el tamano para el caso de que sea un producto que lo tiene.
				String tamano = "";
				//Con las variables booleanas div1 y div2, controlamos si estamos procesando que mitad para los tipos de producto Principal
				//Variable en la que se llevará el control del ancho actual del producto
				//Por intermedio de este ciclo recorremos todos los detalles pedidos que vamos a pintar
				//Variables donde almacenaremos los string de las adiciones de la pizza
				detPedidos = obtenerDetallePedidoPintarMaster(idPedidoTrabajar);
				for(int i = 0; i < detPedidos.size(); i++)
				{
					//Obtenemos el detalle pedido que estamos recorriendo.
					DetallePedido detTem = detPedidos.get(i);
					//Luego de obtenido del detallePedido debemos validar si este esta anulado para saber qeu no lo debemos de pintar
					if((detTem.getEstado() == null)||(detTem.getEstado().equals(new String(""))))
					{
						//Almacenamos el tamano del producto en caso de requerirse más adelante
						tamano = detTem.getTamano();
						g2d.setFont( new Font( "Tahoma", Font.BOLD, 22 ) );
						g2d.setColor(Color.BLACK);
						g2d.drawString(tamano, 20, ((60*(contadorPintar))+30));
						contadorPintar++;
					}
					
				}
				//Dibujamos la linea separador entre pedidos
				g2d.drawLine(0, ((60*(contadorPintar-1))+35), 231, ((60*(contadorPintar-1))+35));
			}
			return(((60*(contadorPintar-1))+35));
		}
		
		public boolean actualizarImpresionPedido(int idPedido)
		{
			boolean respuesta =  PedidoDAO.actualizarImpresionPedido(idPedido,auditoria);
			return(respuesta);
		}
		
		
		public boolean verificarPedidoReabiertoAnulado(int idPedido)
		{
			boolean respuesta =  DetallePedidoDAO.verificarPedidoReabiertoAnulado(idPedido,auditoria);
			return(respuesta);
		}
		
		public boolean esPedidoPorciones(int idProductoPorcion, int idProductoGaseosa, int idPedido)
		{
			boolean respuesta =  PedidoDAO.esPedidoPorciones(idProductoPorcion, idProductoGaseosa, idPedido,  auditoria);
			return(respuesta);
		}
		
		public boolean multiplicarDetallePedido(int idDetalleTratar, int multiplicadorPed)
		{
			boolean respuesta;
			respuesta = DetallePedidoDAO.multiplicarDetallePedido(idDetalleTratar, multiplicadorPed, auditoria);
			return(respuesta);
		}
}