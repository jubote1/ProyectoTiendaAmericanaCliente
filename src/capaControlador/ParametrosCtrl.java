package capaControlador;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import capaDAO.FormaPagoDAO;
import capaDAO.GeneralDAO;
import capaDAO.ParametrosDAO;
import capaDAO.PedidoDAO;
import capaDAO.TiempoPedidoDAO;
import capaDAO.TiendaDAO;
import capaModelo.Correo;
import capaModelo.FormaPago;
import capaModelo.Parametro;
import capaModelo.Tienda;
import utilidades.ControladorEnvioCorreo;

public class ParametrosCtrl {
	
	public String retornarTiemposPedido()
	{
		JSONArray listJSON = new JSONArray();
		JSONObject Respuesta = new JSONObject();
		int tiempo = TiempoPedidoDAO.retornarTiempoPedido();
		Respuesta.put("tiempopedido", tiempo);
		listJSON.add(Respuesta);
		return(Respuesta.toString());
	}
	
	public String actualizarTiempoPedido(int nuevotiempo, String user)
	{
		JSONArray listJSON = new JSONArray();
		JSONObject Respuesta = new JSONObject();
		Tienda tienda = TiendaDAO.obtenerTienda();
		boolean respues = TiempoPedidoDAO.actualizarTiempoPedido(nuevotiempo, tienda.getIdTienda(), user);
		if(respues)
		{
			if (nuevotiempo > 70)
			{
				
				Correo correo = new Correo();
				correo.setAsunto("ALERTA TIEMPOS PEDIDO");
				ArrayList correos = GeneralDAO.obtenerCorreosParametro("TIEMPOPEDIDO");
				correo.setContrasena("Pizzaamericana2017");
				correo.setUsuarioCorreo("alertaspizzaamericana@gmail.com");
				correo.setMensaje("La tienda " + tienda.getNombretienda() + " est� aumentando el tiempo de entrega a " + nuevotiempo + " minutos");
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
		Tienda tienda = TiendaDAO.obtenerTienda();
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
		 * M�todo en la capa controlador que se encarga de retornar la informaci�n de las formas de pago en formato JSON
		 * @return
		 */
		public String retornarFormasPago(){
			JSONArray listJSON = new JSONArray();
			ArrayList<FormaPago> formasPago = FormaPagoDAO.obtenerFormasPago();
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
	
		public boolean EditarParametro(Parametro parametro)
		{
			boolean respuesta = ParametrosDAO.EditarParametro(parametro);
			return(respuesta);
		}
		
		public boolean eliminarParametro(String valorParametro)
		{
			boolean respuesta = ParametrosDAO.eliminarParametro(valorParametro);
			return(respuesta);
		}
		
		public boolean insertarParametro(Parametro parametro)
		{
			boolean respuesta = ParametrosDAO.insertarParametro(parametro);
			return(respuesta);
		}
		
		public Parametro obtenerParametro(String valorParametro)
		{
			Parametro parametro = ParametrosDAO.obtenerParametro(valorParametro);
			return parametro;
		}
		
		public ArrayList obtenerParametros()
		{
			ArrayList parametros = ParametrosDAO.obtenerParametros();
			return parametros;
		}
}
