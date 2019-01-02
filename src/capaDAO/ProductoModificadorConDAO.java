package capaDAO;

import java.sql.Connection;
import org.apache.log4j.Logger;

import com.mysql.jdbc.ResultSetMetaData;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import capaModelo.Impuesto;
import capaModelo.Municipio;
import capaModelo.Producto;
import capaModelo.ProductoModificadorCon;
import capaConexion.ConexionBaseDatos;

/**
 * Clase que se encarga de la implementación de toda la interacción con la base de datos para la entidad Municipio.
 * @author JuanDavid
 *
 */
public class ProductoModificadorConDAO {
	
	
	public static ArrayList obtenerProdModificadorCon(int idProducto, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList proModCon = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idproducto, b.descripcion, a.idproductocon, c.descripcion from producto_modificador_con a, producto b, producto c where a.idproducto = b.idproducto and a.idproductocon = c.idproducto and a.idproducto = " + idProducto;
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
				proModCon.add(fila);
				
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
		return(proModCon);
		
	}
	
	
	public static ArrayList<ProductoModificadorCon> obtenerProdModificadoresCon(int idProducto, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList proModCon = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idproducto,  a.idproductocon, c.descripcion from producto_modificador_con a ,producto c where a.idproductocon = c.idproducto and a.idproducto = " + idProducto;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idProductoCon;
			String descProductoCon;
			ProductoModificadorCon prodModTemp;
			while(rs.next()){
				idProductoCon = rs.getInt("idproductocon");
				descProductoCon = rs.getString("descripcion");
				prodModTemp = new ProductoModificadorCon(idProducto, idProductoCon);
				prodModTemp.setNombreProductoCon(descProductoCon );
				proModCon.add(prodModTemp);
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
		return(proModCon);
		
	}
	
	public static ArrayList<ProductoModificadorCon> obtenerProdModificadoresConTodos( boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList proModCon = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idproducto,  a.idproductocon from producto_modificador_con a ";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idProducto;
			int idProductoCon;
			String descProductoCon;
			ProductoModificadorCon prodModTemp;
			while(rs.next()){
				idProducto = rs.getInt("idproducto");
				idProductoCon = rs.getInt("idproductocon");
				prodModTemp = new ProductoModificadorCon(idProducto, idProductoCon);
				proModCon.add(prodModTemp);
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
		return(proModCon);
		
	}
	

	public static ArrayList<Producto> obtenerProdModificadorConFalta(int idProd, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<Producto> prodModConFalta = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idproducto, a.descripcion from producto a where tipo_producto = 'MC' and a.idproducto not in (select idproductocon from producto_modificador_con b where b.idproducto = "+ idProd +")";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idProducto;
			String descripcion;
			while(rs.next()){
				idProducto = rs.getInt("idproducto");
				descripcion = rs.getString("descripcion");
				Producto prod = new Producto(idProducto, descripcion);
				prodModConFalta.add(prod);
				
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
		return(prodModConFalta);
		
	}
	


	public static void insertarProdModificadoCon(ProductoModificadorCon prodMod, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into producto_modificador_con (idproducto, idproductocon) values (" + prodMod.getIdProducto() + "," + prodMod.getIdProductoCon() +")"; 
			if(auditoria)
			{
				logger.info(insert);
			}
			stm.executeUpdate(insert);
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
			return;
		}
		return;
	}
	
	

	public static boolean eliminarProdModifidadorCon(ProductoModificadorCon prodConEli, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from producto_modificador_con where idproducto = " + prodConEli.getIdProducto() + " and idproductocon=" + prodConEli.getIdProductoCon(); 
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
	
	public static boolean tieneModificadorCon(int idProducto, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		try
		{
			Statement stm = con1.createStatement();
			String select = "select * from producto_modificador_con where idproducto =" + idProducto ;
			if(auditoria)
			{
				logger.info(select);
			}
			ResultSet rs =stm.executeQuery(select);
			while(rs.next())
			{
				respuesta = true;
				break;
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
			return(false);
		}
		return(respuesta);
	}
	

	}
