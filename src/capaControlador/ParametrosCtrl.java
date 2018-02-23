package capaControlador;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import capaDAO.GeneralDAO;
import capaDAO.TiempoPedidoDAO;
import capaDAO.TiendaDAO;
import capaModelo.Correo;
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

}
