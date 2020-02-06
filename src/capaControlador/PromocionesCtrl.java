package capaControlador;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import capaDAO.OfertaClienteDAO;
import capaDAO.OfertaDAO;
import capaDAO.ParametrosDAO;
import capaModelo.Oferta;
import capaModelo.OfertaCliente;

public class PromocionesCtrl {
	
	//ENTIDAD OFERTAS CLIENTE
	
	
	public void actualizarUsoOferta(int idOfertaCliente, String usuarioUso)
	{
		OfertaClienteDAO.actualizarUsoOferta(idOfertaCliente, usuarioUso);	
	}
	
	public OfertaCliente retornarOfertaCodigoPromocional(String codigoPromocional)
	{
		String respuesta = "";
		OfertaCliente ofertaCliente = OfertaClienteDAO.retornarOfertaCodigoPromocional(codigoPromocional);
		//Variable en la que marcaremos si la oferta está vigente
		boolean vigente = false;
		if(ofertaCliente.getIdOfertaCliente() == 0)
		{
			respuesta = "NOK";
		}
		else
		{
			//Es necesario validar si la oferta esta vigente o no tiene fecha de caducidad o si esta caducada
			if(ofertaCliente.getFechaCaducidad().equals(null) || ofertaCliente.getFechaCaducidad().equals(new String("")))
			{
				vigente = true;
			}else
			{
				//Es porque hay fecha de caducidad entonces tendremos que transformarla y validarla
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				//Traemos la fecha Actual
				Date fechaActual = new Date();
				Date fechaCaducidad = new Date();
				try
				{
					fechaCaducidad = dateFormat.parse(ofertaCliente.getFechaCaducidad());
				}catch(Exception e)
				{
					
				}
				if(fechaActual.compareTo(fechaCaducidad) > 0)
				{
					vigente = false;
				}else
				{
					vigente = true;
				}
			}
			if(vigente && ofertaCliente.getUtilizada().equals(new String("N")))
			{
				respuesta = "OK";
			}else
			{
				respuesta = "VEN";
			}
		}
		ofertaCliente.setEstadoOferta(respuesta);
		return(ofertaCliente);
	
	}
	
	public Oferta retornarOferta(int idOferta)
	{
		
		Oferta oferta = OfertaDAO.retornarOferta(idOferta);
		return(oferta);
	}
	
}
