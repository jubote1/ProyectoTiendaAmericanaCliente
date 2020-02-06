package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import capaModelo.EstadoPedido;
import capaModelo.Oferta;
import capaModelo.OfertaCliente;
import capaModelo.Tienda;
import org.apache.log4j.Logger;

import capaConexion.ConexionBaseDatos;
/**
 * Clase que implementa todos los métodos de acceso a la base de datos para la administración de la entidad Excepcion de Precio.
 * @author JuanDavid
 *
 */
public class OfertaClienteDAO {
	
	

	/**
	 * Método que permite editar una excepción Precio con base en la información enviada como parámetro.
	 * @param Esc Recibe como parámetro un objeto Modelo ExcepcionPrecio con base en el cual se realiza la edición.
	 * @return Retorna un string con el resultado del proceso de edición.
	 */
	public static String actualizarUsoOferta(int idOfertaCliente, String usuarioUso)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		String resultado = "";
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm"+":00");
		Date fechaActual = new Date();
		String fechaActualizar = formatoFecha.format(fechaActual);
		try
		{
			Statement stm = con1.createStatement();
			String update = "update oferta_cliente set utilizada = 'S' , uso_oferta = '"+ fechaActualizar +"' , usuario_uso = '"+ usuarioUso +"' where idofertacliente = " + idOfertaCliente; 
			logger.info(update);
			stm.executeUpdate(update);
			resultado = "exitoso";
			stm.close();
			con1.close();
			
		}
		catch (Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
			resultado = "error";
		}
		return(resultado);
	}
	
	
	/**
	 * Método que basado en un codigo promocional pasado como parámetro retorna la oferta asociado al mismo indicando si está existe o no y los datos correspondientes.
	 * @param codigoPromocional
	 * @return
	 */
	public static OfertaCliente retornarOfertaCodigoPromocional(String codigoPromocional)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		OfertaCliente ofertaCliente  = new OfertaCliente(0, 0, 0, "", 0 , "",
				"", "", "");
		try
		{
			Statement stm = con1.createStatement();
			String select = "select * from oferta_cliente  where codigo_promocion = '" + codigoPromocional + "'"; 
			logger.info(select);
			ResultSet rs = stm.executeQuery(select);
			int idOfertaCliente;
			int idOferta;
			int idCliente;
			String utilizada;
			int pqrs;
			String ingresoOferta;
			String usoOferta;
			String observacion;
			String usuarioIngreso = "";
			String fechaCaducidad = "";
			while(rs.next())
			{
				idOfertaCliente = rs.getInt("idofertacliente");
				idOferta = rs.getInt("idoferta");
				idCliente = rs.getInt("idcliente");
				utilizada = rs.getString("utilizada");
				pqrs = rs.getInt("pqrs");
				ingresoOferta = rs.getString("ingreso_oferta");
				usoOferta = rs.getString("uso_oferta");
				observacion = rs.getString("observacion");
				usuarioIngreso = rs.getString("usuario_ingreso");
				fechaCaducidad = rs.getString("fecha_caducidad");
				ofertaCliente = new OfertaCliente(idOfertaCliente, idOferta, idCliente, utilizada, pqrs, ingresoOferta, usoOferta, observacion, usuarioIngreso);
				ofertaCliente.setCodigoPromocion(codigoPromocional);
				ofertaCliente.setFechaCaducidad(fechaCaducidad);
			}
			stm.close();
			rs.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(ofertaCliente);
	}

	}



