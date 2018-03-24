package capaControlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import capaDAO.PedidoPixelDAO;
import capaModelo.Cliente;
import capaModelo.DetallePedidoPixel;
import capaModelo.EstadoPedidoTienda;
import capaModelo.RespuestaPedidoPixel;

public class PedidoCtrl {
	
	public PedidoCtrl()
	{
		
	}
	
	
	/**
	 * M�todo que tiene toda la l�gica para descomponer el json entrante, insertar el pedido en la tienda y entregar
	 * un objeto tipo Respuesta Pedido y entregar finalmente un json de respuesta.
	 * @param datos Se recibe un string en formato JSON con todos los datos para la inserci�n del pedido.
	 * @return Se retorna un string en formato JSON con la respuesta de la inserci�n del pedido.
	 */
	
	
	/**
	 * M�todo de la capa controladora que se encarga de retornar en formato Json los estados de los pedidos tipo 
	 * domicilio que tiene la tienda, recopilando los que est�n pendientes de preparaci�n, los que est�n en ruta
	 * y los que est�n en estado finalizado.
	 * @param dsnODBC Se recibe el datasourcename para la conexi�n a la base de datos.
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

}
