package capaControlador;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import capaDAO.EmpresaTemporalDAO;
import capaDAO.FormaPagoDAO;
import capaDAO.GeneralDAO;
import capaDAO.ParametrosDAO;
import capaDAO.PedidoDAO;
import capaDAO.TiempoPedidoDAO;
import capaDAO.TiendaDAO;
import capaModelo.Correo;
import capaModelo.EmpresaTemporal;
import capaModelo.FormaPago;
import capaModelo.Parametro;
import capaModelo.Tienda;
import utilidades.ControladorEnvioCorreo;

public class ParametrosCtrl {
	
	private boolean auditoria;
	public ParametrosCtrl(boolean auditoria)
	{
		this.auditoria = auditoria;
	}
	public String retornarTiemposPedido()
	{
		JSONArray listJSON = new JSONArray();
		JSONObject Respuesta = new JSONObject();
		int tiempo = TiempoPedidoDAO.retornarTiempoPedido( auditoria);
		Respuesta.put("tiempopedido", tiempo);
		listJSON.add(Respuesta);
		return(Respuesta.toString());
	}
	
	public String actualizarTiempoPedido(int nuevotiempo, String user)
	{
		JSONArray listJSON = new JSONArray();
		JSONObject Respuesta = new JSONObject();
		Tienda tienda = TiendaDAO.obtenerTienda(auditoria);
		boolean respues = TiempoPedidoDAO.actualizarTiempoPedido(nuevotiempo, tienda.getIdTienda(), user, auditoria);
		if(respues)
		{
			if (nuevotiempo > 70)
			{
				
				Correo correo = new Correo();
				correo.setAsunto("ALERTA TIEMPOS PEDIDO");
				ArrayList correos = GeneralDAO.obtenerCorreosParametro("TIEMPOPEDIDO", auditoria);
				correo.setContrasena("Pizzaamericana2017");
				correo.setUsuarioCorreo("alertaspizzaamericana@gmail.com");
				correo.setMensaje("La tienda " + tienda.getNombretienda() + " está aumentando el tiempo de entrega a " + nuevotiempo + " minutos");
				ControladorEnvioCorreo contro = new ControladorEnvioCorreo(correo, correos);
				contro.enviarCorreo();
			}
		}
		Respuesta.put("resultado", respues);
		listJSON.add(Respuesta);
		return(Respuesta.toString());
	}
	
	public String obtenerTienda(){
		JSONArray listJSON = new JSONArray();
		Tienda tienda = TiendaDAO.obtenerTienda(auditoria);
		JSONObject objTienda = new JSONObject();
		objTienda.put("idtienda", tienda.getIdTienda());
		objTienda.put("nombre", tienda.getNombretienda());
		objTienda.put("urlcontact", tienda.getUrlContact());
		listJSON.add(objTienda);
		return listJSON.toJSONString();
	}
	
//	public String obtenerFormaPagoPedido(int idpedido)
//	{
//		FormaPago formapago = PedidoDAO.obtenerFormaPagoPedido(idpedido);
//		JSONArray listJSON = new JSONArray();
//		JSONObject Respuesta = new JSONObject();
//		Respuesta.put("idformapago", formapago.getIdformapago());
//		Respuesta.put("valortotal", formapago.get);
//		Respuesta.put("valorformapago", formapago.getValorformapago());
//		Respuesta.put("nombre", formapago.getNombre());
//		listJSON.add(Respuesta);
//		return(listJSON.toJSONString());
//	}

	//FORMAPAGO
	
		/**
		 * Método en la capa controlador que se encarga de retornar la información de las formas de pago en formato JSON
		 * @return
		 */
		public String retornarFormasPago(){
			JSONArray listJSON = new JSONArray();
			ArrayList<FormaPago> formasPago = FormaPagoDAO.obtenerFormasPago(auditoria);
			for (FormaPago forma : formasPago) 
			{
				JSONObject cadaFormaPagoJSON = new JSONObject();
				cadaFormaPagoJSON.put("idformapago", forma.getIdformapago());
				cadaFormaPagoJSON.put("nombre", forma.getNombre());
				cadaFormaPagoJSON.put("tipoformapago", forma.getTipoforma());
				listJSON.add(cadaFormaPagoJSON);
			}
			return listJSON.toJSONString();
		}
		
		public ArrayList<FormaPago> obtenerFormasPago()
		{
			ArrayList<FormaPago> formasPago = FormaPagoDAO.obtenerFormasPago(auditoria);
			return(formasPago);
		}
		
		public FormaPago retornarFormaPago(int idFormaPago)
		{
			FormaPago forPago = FormaPagoDAO.retornarFormaPago(idFormaPago, auditoria);
			return(forPago);
		}
	
		public boolean EditarParametro(Parametro parametro)
		{
			boolean respuesta = ParametrosDAO.EditarParametro(parametro, auditoria);
			return(respuesta);
		}
		
		public boolean eliminarParametro(String valorParametro)
		{
			boolean respuesta = ParametrosDAO.eliminarParametro(valorParametro, auditoria);
			return(respuesta);
		}
		
		public boolean insertarParametro(Parametro parametro)
		{
			boolean respuesta = ParametrosDAO.insertarParametro(parametro, auditoria);
			return(respuesta);
		}
		
		public Parametro obtenerParametro(String valorParametro)
		{
			Parametro parametro = ParametrosDAO.obtenerParametro(valorParametro, auditoria);
			return parametro;
		}
		
		public ArrayList obtenerParametros()
		{
			ArrayList parametros = ParametrosDAO.obtenerParametros(auditoria);
			return parametros;
		}
		
		public Tienda obtenerTiendaObj()
		{
			Tienda tienda = TiendaDAO.obtenerTienda( auditoria);
			return(tienda);
		}
		
		public ArrayList<EmpresaTemporal> retornarEmpresasTemporales(String bdGeneral)
		{
			ArrayList<EmpresaTemporal> empTemporales = EmpresaTemporalDAO.retornarEmpresasTemporales(bdGeneral, auditoria);
			return(empTemporales);
		}
}
