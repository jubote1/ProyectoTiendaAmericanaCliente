package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;
import capaConexion.ConexionBaseDatos;
import capaModelo.PedidoFormaPago;

public class PedidoFormaPagoDAO {
	
	public static int  InsertarPedidoFormaPago(PedidoFormaPago pedFormaPago)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		int idInsertado = 0;
		try
		{
			Statement stm = con1.createStatement();
			String insertformapago = "insert pedido_forma_pago (idpedidotienda, idforma_pago, valortotal, valorformapago) values (" + pedFormaPago.getIdPedidoTienda() + " , " + pedFormaPago.getIdFormaPago() + " , " + pedFormaPago.getValorTotal() + " , " + pedFormaPago.getValorFormaPago() + ")";
			logger.info(insertformapago);
			stm.executeUpdate(insertformapago);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idInsertado = rs.getInt(1);
				
	        }
			rs.close();
			stm.close();
			con1.close();
		}
		catch (Exception e){
			System.out.println(e.toString());
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

}
