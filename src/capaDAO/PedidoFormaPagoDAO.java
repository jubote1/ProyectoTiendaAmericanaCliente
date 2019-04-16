package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import capaConexion.ConexionBaseDatos;
import capaModelo.PedidoFormaPago;

public class PedidoFormaPagoDAO {
	
	public static int  InsertarPedidoFormaPago(PedidoFormaPago pedFormaPago, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		int idInsertado = 0;
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert pedido_forma_pago (idpedidotienda, idforma_pago, valortotal, valorformapago) values (" + pedFormaPago.getIdPedidoTienda() + " , " + pedFormaPago.getIdFormaPago() + " , " + pedFormaPago.getValorTotal() + " , " + pedFormaPago.getValorFormaPago() + ")";
			if(auditoria)
			{
				logger.info(insert);
			}
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idInsertado = rs.getInt(1);
				
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
		return(idInsertado);
	}
	
	public static boolean existeFormaPago(int idPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from pedido_forma_pago where idpedidotienda = " + idPedido; 
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next())
			{
				respuesta = true;
			}
			
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
		return(respuesta);
	}
	
	public static boolean eliminarPedidoFormaPago(int idPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from pedido_forma_pago where idpedidotienda = " + idPedido; 
			if(auditoria)
			{
				logger.info(delete);
			}
			stm.executeUpdate(delete);
			respuesta = true;
			stm.close();
			con1.close();
		}
		catch (Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
				respuesta = false;
			}catch(Exception e1)
			{
			}
			
		}
		return(respuesta);
	}
	
	public static String consultarFormaPago(int idPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		String respuesta = "";
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select b.nombre from pedido_forma_pago a, forma_pago b where a.idforma_pago = b.idforma_pago and  a.idpedidotienda = " + idPedido; 
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next())
			{
				respuesta = rs.getString("nombre");
			}
			
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
		return(respuesta);
	}
	
	
	public static ArrayList<String[]> consultarFormaPagoArreglo(int idPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<String[]> respuesta = new ArrayList();
		String[] formaPagoTemp = new String[2];
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select b.nombre, a.valorformapago from pedido_forma_pago a, forma_pago b where a.idforma_pago = b.idforma_pago and  a.idpedidotienda = " + idPedido; 
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			System.out.println(consulta);
			while(rs.next())
			{
				formaPagoTemp = new String[2];
				formaPagoTemp[0] = rs.getString("nombre");
				formaPagoTemp[1] = rs.getString("valorformapago");
				respuesta.add(formaPagoTemp);
			}
			for(int i = 0; i < respuesta.size(); i++)
			{
				System.out.println(respuesta.get(i)[0]);
			}
			
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
		return(respuesta);
	}
	
	public static ArrayList<PedidoFormaPago> consultarFormaPagoPedido(int idPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<PedidoFormaPago> respuesta = new ArrayList();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select b.nombre from pedido_forma_pago a, forma_pago b where a.idforma_pago = b.idforma_pago and  a.idpedidotienda = " + idPedido; 
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next())
			{
				String revisar = rs.getString("nombre");
			}
			
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
		return(respuesta);
	}

}
