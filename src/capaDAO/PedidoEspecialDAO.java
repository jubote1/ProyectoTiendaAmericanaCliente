package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.mysql.jdbc.ResultSetMetaData;

import capaConexion.ConexionBaseDatos;
import capaModelo.ItemInventario;
import capaModelo.PedidoEspecial;

public class PedidoEspecialDAO {
	
	public static int insertarPedidoEspecial(PedidoEspecial ped)
	{
		Logger logger = Logger.getLogger("log_file");
		int idPedidoEspecial = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from pedido_especial where iditem = " + ped.getIdItem() + " and fecha_solicitud = '" + ped.getFecha() + "'";
			ResultSet rs = stm.executeQuery(consulta);
			boolean existe = false;
			double cantidadInicial = 0;
			while(rs.next())
			{
				existe = true;
				double cantidadTemp = rs.getDouble("cantidad");
				cantidadInicial = cantidadInicial +  cantidadTemp;
				
			}
			if (existe)
			{
				idPedidoEspecial = -1;
				double cantidadFinal = cantidadInicial + ped.getCantidad();
				String update = "update pedido_especial set cantidad = " + cantidadFinal + " where iditem = " + ped.getIdItem() + " and fecha_solicitud = '" + ped.getFecha() + "'";
				stm.executeUpdate(update);
			}
			else
			{
				String insert = "insert into pedido_especial (iditem,cantidad, fecha_solicitud) values (" + ped.getIdItem() + ", " + ped.getCantidad() + " , '" + ped.getFecha() + "')"; 
				logger.info(insert);
				stm.executeUpdate(insert);
				rs = stm.getGeneratedKeys();
				if (rs.next()){
					idPedidoEspecial=rs.getInt(1);
					logger.info("id pedido especial en bd " + idPedidoEspecial);
		        }
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
			return(0);
		}
		return(idPedidoEspecial);
	}
	
	/**
	 * Método que se encarga de eliminar un determinado en item inventario, teniendo en cuenta el idItem pasado como parámetro
	 * @param idItem Se recibe como parámetro el iditem que se desea eliminar, teniendo en cuenta que es la 
	 * clave primaría de la tabla.
	 * @return Se retorna un valor booleano que indica si el resultado del proceso fue satisfactorio o no.
	 */
	public static boolean eliminarItemInventario(int idPedidoEspecial)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from pedido_especial where idpedidoespecial = " + idPedidoEspecial; 
			logger.info(delete);
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
	
	
	public static ArrayList obtenerPedidosEspeciales(String fecha)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList pedidosEspeciales = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idpedidoespecial,a.iditem, b.nombre_item, a.cantidad, b.unidad_medida, a.fecha_solicitud from pedido_especial a, item_inventario b where a.iditem = b.iditem and fecha_solicitud = '" + fecha + "'";
			System.out.println(consulta);
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
			int numeroColumnas = rsMd.getColumnCount();
			
			while(rs.next()){
				String [] fila = new String[numeroColumnas];
				for(int y = 0; y < numeroColumnas; y++)
				{
					fila[y] = rs.getString(y+1);
				}
				pedidosEspeciales.add(fila);
				
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.info(e.toString());
			System.out.println(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(pedidosEspeciales);
		
	}
	

}
