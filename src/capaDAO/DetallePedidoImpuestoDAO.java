package capaDAO;

import java.sql.Connection;
import java.sql.Statement;

import org.apache.log4j.Logger;

import capaConexion.ConexionBaseDatos;
import capaModelo.DetallePedidoImpuesto;

public class DetallePedidoImpuestoDAO {
	
	public static boolean eliminarDetallePedidoImpuesto(int idPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from detalle_pedido_impuesto where idpedido = " + idPedido;
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
	
	/**
	 * Método para insertar un detalle Pedido Impuesto de acuerto al objeto recibido como parámetro
	 * @param detPedImpuesto
	 * @return
	 */
	public static boolean insertarDetallePedidoImpuesto(DetallePedidoImpuesto detPedImpuesto, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "insert into detalle_pedido_impuesto (idpedido, iddetalle_pedido, idimpuesto, valor_impuesto) values (" + detPedImpuesto.getIdPedido() + " , " + detPedImpuesto.getIdDetallePedido() + " , " + detPedImpuesto.getIdImpuesto() + " , " + detPedImpuesto.getValorImpuesto() + ")" ; 
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

}
