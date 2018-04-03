package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import capaConexion.ConexionBaseDatos;

public class PedidoDAO {
	
	public static int InsertarEncabezadoPedido(int idtienda, int idcliente, String fechaPedido, String user)
	{
		Logger logger = Logger.getLogger("log_file");
		int idPedidoInsertado = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		Date fechaTemporal = new Date();
		DateFormat formatoFinal = new SimpleDateFormat("yyyy-MM-dd");
		String fechaPedidoFinal ="";
		try
		{
			fechaTemporal = new SimpleDateFormat("dd/MM/yyyy").parse(fechaPedido);
			fechaPedidoFinal = formatoFinal.format(fechaTemporal);
			
		}catch(Exception e){
			logger.error(e.toString());
			return(0);
		}
		
		
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into pedido (idtienda,idcliente,fechapedido, usuariopedido) values (" + idtienda + ", " + idcliente + ", '" + fechaPedidoFinal  + "' , '" + user + "')"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idPedidoInsertado=rs.getInt(1);
				System.out.println(idPedidoInsertado);
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
		return(idPedidoInsertado);
	}

}
