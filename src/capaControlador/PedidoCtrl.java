package capaControlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import capaDAOPixelpos.PedidoPixelDAO;
import capaModelo.Cliente;
import capaModelo.DetallePedidoPixel;
import capaModelo.EstadoPedidoTienda;
import capaModelo.RespuestaPedidoPixel;

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
	public String InsertarPedidoPixel(String datos)
	{
		// Debemos de procesar todo datos para extraer todo los datos requeridos
		 JSONParser parser = new JSONParser();
		 JSONArray respuestaJSON = new JSONArray();
		 try {
			 System.out.println(datos);
			 Object objParser = parser.parse(datos);
			 JSONObject jsonPedido = (JSONObject) objParser;
			 int idpedido;
			 boolean insertado;
			 String dsnTienda;
			 int memcode;
			 String clienteJSON;
			 boolean indicadorAct;
			 double valorformapago;
			 double valortotal;
			 int cantidaditempedido;
			 int idformapagotienda;
			  // Realizamos el parseo inicial de la información que se le envía al servicio
			 Long tempidpedido = new Long((long)jsonPedido.get("idpedido"));
			 idpedido =  (new Integer(tempidpedido.intValue()));
			 System.out.println("ippedido " + idpedido);
			 //insertado = (boolean)jsonPedido.get("insertado");
			 dsnTienda = (String)jsonPedido.get("dsntienda");
			 Long tempmemcode = new Long((long)jsonPedido.get("memcode"));
			 memcode = (new Integer(tempmemcode.intValue()));
			 clienteJSON = (String)jsonPedido.get("cliente");
			 indicadorAct = (boolean)jsonPedido.get("indicadoract");
			 valorformapago = new Double((long)jsonPedido.get("valorformapago"));
			 valortotal = new Double((long)jsonPedido.get("valortotal"));
			 Long idformapagotientmp = new Long((long)jsonPedido.get("idformapagotienda"));
			 idformapagotienda = (new Integer(idformapagotientmp.intValue()));
			  // Realizamos el parseo para el nivel de clientes
			 Object objParserCliente = parser.parse(clienteJSON);
			 JSONObject jsonCliente =(JSONObject) ((JSONArray)objParserCliente).get(0);
			 Long tempidcliente = new Long((long)jsonCliente.get("idcliente"));
			 Long tempclimemcode = new Long((long)jsonCliente.get("memcode"));
			 Cliente cliente = new Cliente((new Integer(tempidcliente.intValue())), (String) jsonCliente.get("telefono") , (String) jsonCliente.get("nombres"), (String) jsonCliente.get("apellidos"), (String) jsonCliente.get("nombrecompania"), (String) jsonCliente.get("direccion"), "", 0,
						0, (String) jsonCliente.get("zonadireccion"), (String) jsonCliente.get("observacion"), "", 0, (new Integer(tempclimemcode.intValue())));
			 //Sacamos la cantidad de detalles pedidos
			 Long tempcantidaditem = new Long((long)jsonPedido.get("cantidaditempedido"));
			 cantidaditempedido = (new Integer(tempcantidaditem.intValue()));
			 // Realizamos el parseo para el pedido
			 ArrayList<DetallePedidoPixel> detPedidoPixel= new ArrayList();
			 Long tempidprod;
			 double tempcantidad;
			 for(int i = 1; i<= cantidaditempedido; i++)
			 {
				 System.out.println("idproductoext"+ i);
				 tempidprod = new Long((long)jsonPedido.get("idproductoext"+ i));
				 tempcantidad = ((Number)jsonPedido.get("cantidad" + i)).doubleValue();
				 detPedidoPixel.add(new DetallePedidoPixel( tempidprod.intValue(), tempcantidad ));
			 }
			 RespuestaPedidoPixel respuesta = PedidoPixelDAO.confirmarPedidoPixelTienda(idpedido,valorformapago, valortotal, cliente,indicadorAct, dsnTienda, detPedidoPixel,idformapagotienda);
			
			 JSONObject cadaResJSON = new JSONObject();
			 cadaResJSON.put("creacliente", respuesta.getClienteCreado());
			 cadaResJSON.put("numerofactura", respuesta.getNumeroFactura());
			 cadaResJSON.put("membercode", respuesta.getMembercode());
			 cadaResJSON.put("idpedido", respuesta.getIdpedido());
			 cadaResJSON.put("idcliente", respuesta.getIdcliente());
			 respuestaJSON.add(cadaResJSON);
			 
			 
		 }catch (Exception e) {
	            e.printStackTrace();
	     } 
		 return(respuestaJSON.toString());
	}
	
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

}
