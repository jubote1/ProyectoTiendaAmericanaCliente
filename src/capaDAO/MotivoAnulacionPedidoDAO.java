package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import capaConexion.ConexionBaseDatos;
import capaModelo.DetallePedido;
import capaModelo.MotivoAnulacionPedido;

public class MotivoAnulacionPedidoDAO {
	
	
	
	public static ArrayList<MotivoAnulacionPedido> obtenerMotivosAnulacion(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		ArrayList<MotivoAnulacionPedido> motivosAnulacion = new ArrayList();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from motivo_anulacion_pedido order by idmotivoanulacion desc" ;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idMotivoAnulacion;
			String descripcion, descuentoInventario;
			MotivoAnulacionPedido motAnuTemp;
			while (rs.next())
			{
				idMotivoAnulacion = rs.getInt("idmotivoanulacion");
				descripcion = rs.getString("descripcion");
				descuentoInventario = rs.getString("descuento_inventario");
				motAnuTemp = new MotivoAnulacionPedido(idMotivoAnulacion, descripcion, descuentoInventario);
				motivosAnulacion.add(motAnuTemp);
				
			}
			
			rs.close();
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
		return(motivosAnulacion);
		
	}
	
	
	
}
