package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.mysql.jdbc.ResultSetMetaData;

import capaConexion.ConexionBaseDatos;

public class IngresoInventarioDetalleTmpDAO {
	
	/**
	 * Método que se encarga de retornar los preingreso de bodega para una fecha determinada, y desplegarlos en pantalla para
	 * que sean confirmados y sean incluidos en la tienda.
	 * @param auditoria
	 * @return Se retorna un arrayList con los valores.
	 */
	public static ArrayList obtenerInventarioPreIngresar(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList itemsInventario = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.iditem,a.nombre_item,a.unidad_medida,a.cantidadxcanasta,a.nombrecontenedor,IFNULL(b.cantidad,0),IFNULL(b.cantidad,0) from item_inventario a LEFT OUTER JOIN ingreso_inventario_detalle_tmp b ON a.iditem = b.iditem INNER JOIN ingreso_inventario_tmp c ON b.iddespacho = c.iddespacho and c.estado = 'PREINGRESADO'  order by a.orden";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
			int numeroColumnas = rsMd.getColumnCount();
			
			while(rs.next()){
				String [] fila = new String[numeroColumnas];
				for(int y = 0; y < numeroColumnas; y++)
				{
					fila[y] = rs.getString(y+1);
				}
				itemsInventario.add(fila);
				
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			System.out.println(e.toString());
			logger.info(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(itemsInventario);
		
	}
	
	
	/**
	 * Método que se encarga del borrado del detalle de un preingreso de inventario de bodega
	 * @param idDespacho
	 * @param auditoria
	 * @return
	 */
	public static boolean borrarIngresoInventarioDetalleTmp(int idDespacho, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from ingreso_inventario_detalle_tmp where iddespacho = '" + idDespacho + "'";
			if(auditoria)
			{
				logger.info(delete);
			}
			stm.executeUpdate(delete);
			respuesta = true;
			stm.close();
			con1.close();
		}catch (Exception e){
			respuesta = false;
			logger.info(e.toString());
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
