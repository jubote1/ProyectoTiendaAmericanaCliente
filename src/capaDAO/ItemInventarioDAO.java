package capaDAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import capaModelo.Usuario;
import capaConexion.ConexionBaseDatos;
import capaModelo.Impuesto;
import capaModelo.ItemInventario;
import capaModelo.AgrupadorMenu;
import capaModelo.Tienda;
import org.apache.log4j.Logger;
import com.mysql.jdbc.ResultSetMetaData;
/**
 * Clase que se encarga de implementar todo lo relacionado con la base de datos de la entidad item inventario
 * @author JuanDavid
 *
 */
public class ItemInventarioDAO {
	
/**
 * Método que se encarga de retonar todos los items inventario parametrizado en el sistema.
 * @return Retorna un arrayList con tipos de datos genéricos.
 */
	public static ArrayList obtenerItemInventario(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList itemsInventario = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from item_inventario";
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
	
	public static ArrayList obtenerItemInventarioResumen(String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList itemsInventarioResumen = new ArrayList();
		
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.iditem, a.nombre_item, ifnull((select b.cantidad from item_inventario_historico b "
					+ "where b.iditem = a.iditem and b.fecha = '" + fecha + "'),0)as inicio, ifnull( (select sum(c.cantidad) from retiro_inventario d, "
					+ "retiro_inventario_detalle c where c.idretiro_inventario = d.idretiro_inventario and c.iditem = a.iditem  "
					+ "and d.fecha_sistema ='"+fecha+"' ),0) as retiro, ifnull((select sum(f.cantidad) "
					+ "from ingreso_inventario e, ingreso_inventario_detalle f where e.idingreso_inventario = f.idingreso_inventario "
					+ "and f.iditem = a.iditem  and e.fecha_sistema ='"+ fecha +"' ) ,0)as ingreso, ifnull((select sum(g.cantidad) "
					+ "from consumo_inventario_pedido g, pedido h where g.iditem = a.iditem  and g.idpedido = h.idpedidotienda "
					+ "and h.fechapedido ='"+ fecha +"'  ) ,0)as consumo from item_inventario a order by a.orden";
			System.out.println("OJO" + consulta);
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
				itemsInventarioResumen.add(fila);
				
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.info(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(itemsInventarioResumen);
		
	}
	
	public static ArrayList obtenerItemInventarioVarianza(String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList itemsInventarioResumen = new ArrayList();
		
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.iditem, a.nombre_item, ifnull((select b.cantidad from item_inventario_historico b "
					+ "where b.iditem = a.iditem and b.fecha = '" + fecha + "'),0)as inicio, ifnull( (select sum(c.cantidad) from retiro_inventario d, "
					+ "retiro_inventario_detalle c where c.idretiro_inventario = d.idretiro_inventario and c.iditem = a.iditem  "
					+ "and d.fecha_sistema ='"+fecha+"' ),0) as retiro, ifnull((select sum(f.cantidad) "
					+ "from ingreso_inventario e, ingreso_inventario_detalle f where e.idingreso_inventario = f.idingreso_inventario "
					+ "and f.iditem = a.iditem  and e.fecha_sistema = '" + fecha + "' ) ,0)as ingreso, ifnull((select sum(g.cantidad) "
					+ "from consumo_inventario_pedido g, pedido h where g.iditem = a.iditem  and g.idpedido = h.idpedidotienda "
					+ "and h.fechapedido ='"+ fecha +"'  ) ,0)as consumo, 0, a.cantidad, a.cantidad,0 from item_inventario a order by a.orden";
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
				itemsInventarioResumen.add(fila);
				
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.info(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(itemsInventarioResumen);
		
	}
	
	public static ArrayList obtenerItemInventarioVarianzaTemp(String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList itemsInventarioResumen = new ArrayList();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.iditem, a.nombre_item, ifnull((select b.cantidad from item_inventario_historico b "
					+ "where b.iditem = a.iditem and b.fecha = '" + fecha + "'),0)as inicio, ifnull( (select sum(c.cantidad) from retiro_inventario d, "
					+ "retiro_inventario_detalle c where c.idretiro_inventario = d.idretiro_inventario and c.iditem = a.iditem  "
					+ "and d.fecha_sistema ='"+fecha+"' ),0) as retiro, ifnull((select sum(f.cantidad) "
					+ "from ingreso_inventario e, ingreso_inventario_detalle f where e.idingreso_inventario = f.idingreso_inventario "
					+ "and f.iditem = a.iditem  and e.fecha_sistema = '" + fecha + "' ) ,0)as ingreso, ifnull((select sum(g.cantidad) "
					+ "from consumo_inventario_pedido g, pedido h where g.iditem = a.iditem  and g.idpedido = h.idpedidotienda "
					+ "and h.fechapedido ='"+ fecha +"'  ) ,0)as consumo, 0, a.cantidad, b.cantidad,(a.cantidad - b.cantidad) from item_inventario a, inventarios_temporal b where a.iditem = b.iditem and b.fecha_sistema = '" + fecha + "' and b.tipo_inventario = 'V' order by a.orden";
			System.out.println("OJO" + consulta);
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
				itemsInventarioResumen.add(fila);
				
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.info(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(itemsInventarioResumen);
		
	}
	
	public static ArrayList obtenerInventarioVarianza(String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList itemsInventarioResumen = new ArrayList();
		
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.iditem, a.nombre_item, ifnull((select b.cantidad from item_inventario_historico b "
					+ "where b.iditem = a.iditem and b.fecha = '" + fecha + "'),0)as inicio, ifnull( (select sum(c.cantidad) from retiro_inventario d, "
					+ "retiro_inventario_detalle c where c.idretiro_inventario = d.idretiro_inventario and c.iditem = a.iditem  "
					+ "and d.fecha_sistema ='"+fecha+"' ),0) as retiro, ifnull((select sum(f.cantidad) "
					+ "from ingreso_inventario e, ingreso_inventario_detalle f where e.idingreso_inventario = f.idingreso_inventario "
					+ "and f.iditem = a.iditem  and e.fecha_sistema = '" + fecha + "' ) ,0)as ingreso, ifnull((select sum(g.cantidad) "
					+ "from consumo_inventario_pedido g, pedido h where g.iditem = a.iditem  and g.idpedido = h.idpedidotienda "
					+ "and h.fechapedido ='"+ fecha +"'  ) ,0)as consumo, ifnull((select z.cantidad from inventario_varianza y , item_inventario_varianza z "
					+ "where y.idinventario_varianza = z.idinventario_varianza and a.iditem = z.iditem and y.fecha_sistema = '" + fecha + "' and y.idinventario_varianza = (select max(t.idinventario_varianza) from inventario_varianza t where t.fecha_sistema = '" + fecha + "')),0)as varianza  from item_inventario a";
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
				itemsInventarioResumen.add(fila);
				
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.info(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(itemsInventarioResumen);
		
	}
	
	public static ArrayList obtenerInventarioVarianzaRes(String fecha, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList itemsInventarioResumen = new ArrayList();
		
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.iditem, a.nombre_item, ifnull((select b.cantidad from item_inventario_historico b "
					+ "where b.iditem = a.iditem and b.fecha = '" + fecha + "'),0)as inicio, ifnull( (select sum(c.cantidad) from retiro_inventario d, "
					+ "retiro_inventario_detalle c where c.idretiro_inventario = d.idretiro_inventario and c.iditem = a.iditem  "
					+ "and d.fecha_sistema ='"+fecha+"' ),0) as retiro, ifnull((select sum(f.cantidad) "
					+ "from ingreso_inventario e, ingreso_inventario_detalle f where e.idingreso_inventario = f.idingreso_inventario "
					+ "and f.iditem = a.iditem  and e.fecha_sistema = '" + fecha + "' ) ,0)as ingreso, ifnull((select sum(g.cantidad) "
					+ "from consumo_inventario_pedido g, pedido h where g.iditem = a.iditem  and g.idpedido = h.idpedidotienda "
					+ "and h.fechapedido ='"+ fecha +"'  ) ,0)as consumo, ifnull((select z.cantidad from inventario_varianza y , item_inventario_varianza z "
					+ "where y.idinventario_varianza = z.idinventario_varianza and a.iditem = z.iditem and y.fecha_sistema = '" + fecha + "' and y.idinventario_varianza = (select max(t.idinventario_varianza) from inventario_varianza t where t.fecha_sistema = '" + fecha + "')),0)as varianza  from item_inventario a where a.varianza_resumida = 1";
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
				itemsInventarioResumen.add(fila);
				
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
			logger.info(e.toString());
			try
			{
				con1.close();
			}catch(Exception e1)
			{
			}
		}
		return(itemsInventarioResumen);
		
	}
	
	
	public static ArrayList<ItemInventario> obtenerItemInventarioObj(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<ItemInventario> itemsInventario = new ArrayList();
		int idItem;
		String nombreItem,unidadMedida,manejaCanastas,cantidadCanasta,nombreContenedor, categoria;
		double cantidad;
		int varRes;
		boolean varianzaResumida;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from item_inventario";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			while(rs.next()){
				idItem = rs.getInt("iditem");
				nombreItem = rs.getString("nombre_item");
				unidadMedida = rs.getString("unidad_medida");
				cantidad = rs.getDouble("cantidad");
				manejaCanastas = rs.getString("manejacanastas");
				cantidadCanasta = rs.getString("cantidadxcanasta");
				nombreContenedor = rs.getString("nombrecontenedor");
				categoria = rs.getString("categoria");
				varRes = rs.getInt("varianza_resumida");
				if(varRes == 1)
				{
					varianzaResumida = true;
				}else
				{
					varianzaResumida = false;
				}
				ItemInventario itemTemp = new ItemInventario(idItem, nombreItem, unidadMedida,cantidad, manejaCanastas, cantidadCanasta, nombreContenedor,categoria,varianzaResumida);
				itemsInventario.add(itemTemp);
				
			}
			rs.close();
			stm.close();
			con1.close();
		}catch (Exception e){
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
	
	public static ArrayList obtenerItemInventarioIngresar(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList itemsInventario = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select iditem,nombre_item,unidad_medida,cantidadxcanasta,nombrecontenedor,0 from item_inventario order by orden";
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
	
	
	public static ArrayList obtenerItemInventarioIngresar(String fechaSistema, String tipo, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList itemsInventario = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "";
			if(tipo == "I")
			{
				consulta = "select a.iditem, a.nombre_item, a.unidad_medida,a.cantidadxcanasta,a.nombrecontenedor,b.cantidad from item_inventario a, inventarios_temporal b where a.iditem = b.iditem and b.fecha_sistema = '" + fechaSistema + "' and b.tipo_inventario = 'I' order by a.orden";
				
			}else if (tipo == "R")
			{
				consulta = "select a.iditem, a.nombre_item, a.unidad_medida,a.cantidadxcanasta,a.nombrecontenedor,b.cantidad from item_inventario a, inventarios_temporal b where a.iditem = b.iditem and b.fecha_sistema = '" + fechaSistema + "' and b.tipo_inventario = 'R' order by a.orden";
				
			}
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
	 * Método de la capa DAO que se encarga de insertar una entidad item inventario.
	 * @param impuesto Recibe un objeto de tipo item inventario del cual se extrae la información para la inserción.
	 * @return Se retorna un valor entero con el iditem creado en la base de datos
	 */
	public static int insertarItemInventario(ItemInventario item, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		int idImpuestoIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			int varRes;
			if(item.isVarianzaResumida())
			{
				varRes = 1;
			}else
			{
				varRes = 0;
			}
			String insert = "insert into item_inventario (nombre_item, unidad_medida,manejacanastas,cantidadxcanasta,nombrecontenedor, categoria, varianza_resumida) values ('" + item.getNombreItem() + "', '" + item.getUnidadMedida() + "' , '" + item.getManejaCanastas() + "' , " + item.getCantidadCanasta() + " , '" + item.getNombreContenedor() + "' , '" + item.getCategoria() + "' , " + varRes + ")"; 
			if(auditoria)
			{
				logger.info(insert);
			}
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idImpuestoIns=rs.getInt(1);
				if(auditoria)
				{
					logger.info("id item insertada en bd " + idImpuestoIns);
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
		return(idImpuestoIns);
	}
	
	/**
	 * Método que se encarga de eliminar un determinado en item inventario, teniendo en cuenta el idItem pasado como parámetro
	 * @param idItem Se recibe como parámetro el iditem que se desea eliminar, teniendo en cuenta que es la 
	 * clave primaría de la tabla.
	 * @return Se retorna un valor booleano que indica si el resultado del proceso fue satisfactorio o no.
	 */
	public static boolean eliminarItemInventario(int idItem, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from item_inventario where iditem = " + idItem; 
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
	 * Método de la capa DAO que se encarga de editar un item inventario ya existente.
	 * @param impuesto Recibe como parámetro un objeto de la entidad item inventario con base en el cual se realiza la modificación
	 * @return Se retorna un valor booleano indicando si el proceso fue o no satisfactorio
	 */
	public static boolean EditarItemInventario(ItemInventario item, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			int varRes;
			if(item.isVarianzaResumida())
			{
				varRes = 1;
			}else
			{
				varRes = 0;
			}
			Statement stm = con1.createStatement();
			String update = "update item_inventario set nombre_item = '" + item.getNombreItem() + "' , unidad_medida = '" + item.getUnidadMedida() + " ' , manejacanastas = '" + item.getManejaCanastas() + "' , cantidadxcanasta =" + item.getCantidadCanasta() + " , nombrecontenedor = '" + item.getNombreContenedor() + "' , categoria = '" + item.getCategoria() + "' , varianza_resumida = " + varRes  + " where iditem = " + item.getIdItem() ; 
			if(auditoria)
			{
				logger.info(update);
			}
			stm.executeUpdate(update);
			
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
			return(false);
		}
		return(true);
	}
	
	
	/**
	 * Método que se encarga de retornar la cantidad de items inventario que se tienen en el sistema, pricipalmente con 
	 * el objetivo de poder manejar JProgressBar entre otras funciones
	 * @param auditoria
	 * @return
	 */
	public static int obtenerCantItemInventario( boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		int cantItems = 0;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select count(*) as resultado from item_inventario";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			if (rs.next()){
				cantItems = rs.getInt("resultado");
				
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
		return(cantItems);
	}
	
	/**
	 * Método que se encarga de generar la información del inventario consumido en un día determinado
	 * @param fechaActual
	 * @param auditoria
	 * @return
	 */
	public static ArrayList obtenerInventarioConsumido(String fechaActual,  boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList itemsInvConsumido = new ArrayList();
		int cantItems = 0;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.iditem, a.nombre_item,  ifnull((select sum(g.cantidad) from consumo_inventario_pedido g, pedido h where g.iditem = a.iditem  and g.idpedido = h.idpedidotienda and h.fechapedido =  '"+ fechaActual +"' ) ,0)as consumo, a.unidad_medida " + 
					"from item_inventario a";
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
				itemsInvConsumido.add(fila);
				
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
		return(itemsInvConsumido);
	}
	
	/**
	 * Método que se encarga de recibir la información de los items de inventarios resumidos para ser presentados en el reporte de pantalla
	 * @param fechaActual
	 * @param auditoria
	 * @return
	 */
	public static ArrayList obtenerInventarioActualReporte(String fechaActual,  boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList itemsInvActual = new ArrayList();
		int cantItems = 0;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.iditem, a.nombre_item,  a.cantidad, a.unidad_medida " + 
					"from item_inventario a";
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
				itemsInvActual.add(fila);
				
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
		return(itemsInvActual);
	}
	
	//Crearemos método que obtendrá la información base para desplegar en el informe
	public static ArrayList obtenerCierreSemanalInsumos(String fechaActual, String fechaAnterior, String tipoItemInventario, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList itemsInvCierre = new ArrayList();
		int cantItems = 0;
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "SELECT a.nombre_item, a.unidad_medida," + 
					"(SELECT b.cantidad FROM item_inventario_historico b WHERE a.iditem = b.iditem AND B.fecha = '" + fechaAnterior + "') AS INVENTARIO_INICIAL " +
					" ,(SELECT SUM(d.cantidad) FROM ingreso_inventario c, ingreso_inventario_detalle d WHERE c.idingreso_inventario = d.idingreso_inventario AND d.iditem = a.iditem AND c.fecha_real >= '" + fechaAnterior + "' AND c.fecha_real <= '" + fechaActual + "')  AS ENVIADO_A_TIENDA" +
					" ,ifnull( (select sum(c.cantidad) from retiro_inventario d, retiro_inventario_detalle c where c.idretiro_inventario = d.idretiro_inventario and c.iditem = a.iditem and d.fecha_sistema >= '" + fechaAnterior +"' AND d.fecha_sistema <= '" + fechaActual + "' ),0) AS RETIROS_OTRAS_TIENDAS " + 
					" , a.cantidad AS INVENTARIO_FINAL   from item_inventario a WHERE " +
					" a.categoria LIKE '" + tipoItemInventario + "%'";
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
				itemsInvCierre.add(fila);
				
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
		return(itemsInvCierre);
	}
	
}
