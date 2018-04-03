package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import capaConexion.ConexionBaseDatos;
import capaModelo.DetallePedido;

public class DetallePedidoDAO {
	
	public static int insertarDetallePedido(DetallePedido detPedido)
	{
		Logger logger = Logger.getLogger("log_file");
		int idDetalleInsertado = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into detalle_pedido (idpedidotienda,idproducto,cantidad, valorunitario,valortotal,observacion) values (" + detPedido.getIdPedidoTienda() + ", " + detPedido.getIdProducto() + ", " + detPedido.getCantidad() + " , " + detPedido.getValorUnitario() + " , " + detPedido.getValorTotal() + " , '" + detPedido.getObservacion() + "')"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idDetalleInsertado=rs.getInt(1);
				
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
			return(0);
		}
		return(idDetalleInsertado);
	}

	public static boolean eliminarDetallePedido(int idDetallePedido)
	{
		Logger logger = Logger.getLogger("log_file");
		int idDetalleInsertado = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from detalle_pedido  where iddetalle_pedido = " + idDetallePedido ;
			logger.info(delete);
			stm.executeUpdate(delete);
			stm.close();
			con1.close();
			return(true);
		}
		catch (Exception e){
			logger.error(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
			return(false);
		}
		
	}
	
}
