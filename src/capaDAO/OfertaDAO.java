package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import capaModelo.EstadoPedido;
import capaModelo.Oferta;
import capaModelo.Tienda;
import org.apache.log4j.Logger;

import capaConexion.ConexionBaseDatos;
/**
 * Clase que implementa todos los métodos de acceso a la base de datos para la administración de la entidad Excepcion de Precio.
 * @author JuanDavid
 *
 */
public class OfertaDAO {
	


	/**
	 * Método que se encarga de consultar una oferta con base en el parámetro recibido.
	 * @param idOferta Se recibe como parámetro el idexcepcion que desea ser consultado.
	 * @return Se retorna un objeto Modelo Oferta que contiene la información el excepcion Precio consultada.
	 */
	public static Oferta retornarOferta(int idOferta)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDPrincipal();
		Oferta ofertaTemp = new Oferta(0,"",0);
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from  oferta  where idoferta = " + idOferta; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			String nombreOferta = "";
			int idExcepcion = 0;
			int diasCaducidad = 0;
			String tipoCaducidad = "";
			double descuentoFijoPorcentaje = 0, descuentoFijoValor = 0;
			while(rs.next()){
				nombreOferta = rs.getString("nombre_oferta");
				idExcepcion = rs.getInt("idexcepcion");
				descuentoFijoPorcentaje = rs.getDouble("descuento_fijo_porcentaje");
				descuentoFijoValor = rs.getDouble("descuento_fijo_valor");
				try {
					diasCaducidad = rs.getInt("dias_caducidad");
					
				}catch(Exception e)
				{
					diasCaducidad = 0;
				}
				tipoCaducidad = rs.getString("tipo_caducidad");
				break;
			}
			ofertaTemp = new Oferta(idOferta, nombreOferta, idExcepcion);
			ofertaTemp.setDiasCaducidad(diasCaducidad);
			ofertaTemp.setTipoCaducidad(tipoCaducidad);
			ofertaTemp.setDescuentoFijoPorcentaje(descuentoFijoPorcentaje);
			ofertaTemp.setDescuentoFijoValor(descuentoFijoValor);
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
		}
		return(ofertaTemp);
	}

	
}
