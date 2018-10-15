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
import capaModelo.ProductoModificadorSin;
import capaConexion.ConexionBaseDatos;

/**
 * Clase que se encarga de la implementación de toda la interacción con la base de datos para la entidad Municipio.
 * @author JuanDavid
 *
 */
public class ProductoModificadorSinDAO {
	
	
	public static ArrayList obtenerProdModificadorSin(int idProducto)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList proModSin = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idproducto, b.descripcion, a.idproductosin, c.descripcion from producto_modificador_sin a, producto b, producto c where a.idproducto = b.idproducto and a.idproductosin = c.idproducto and a.idproducto = " + idProducto;
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
				proModSin.add(fila);
				
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
		return(proModSin);
		
	}
	
	public static ArrayList<ProductoModificadorSin> obtenerProdModificadoresSin(int idProducto)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList proModSin = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idproducto,  a.idproductosin, c.descripcion from producto_modificador_sin a ,producto c where a.idproductosin = c.idproducto and a.idproducto = " + idProducto;
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idProductoSin;
			String descProductoSin;
			ProductoModificadorSin prodModTemp;
			while(rs.next()){
				idProductoSin = rs.getInt("idproductosin");
				descProductoSin = rs.getString("descripcion");
				prodModTemp = new ProductoModificadorSin(idProducto, idProductoSin);
				prodModTemp.setNombreProductoSin(descProductoSin );
				proModSin.add(prodModTemp);
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
		return(proModSin);
		
	}

	public static ArrayList<Producto> obtenerProdModificadorSinFalta(int idProd)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<Producto> prodModSinFalta = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.idproducto, a.descripcion from producto a where a.tipo_producto = 'MS' and a.idproducto not in (select idproductosin from producto_modificador_sin b where b.idproducto = "+ idProd +")";
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idProducto;
			String descripcion;
			while(rs.next()){
				idProducto = rs.getInt("idproducto");
				descripcion = rs.getString("descripcion");
				Producto prod = new Producto(idProducto, descripcion);
				prodModSinFalta.add(prod);
				
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
		return(prodModSinFalta);
		
	}
	


	public static void insertarProdModificadoSin(ProductoModificadorSin prodMod)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into producto_modificador_sin (idproducto, idproductosin) values (" + prodMod.getIdProducto() + "," + prodMod.getIdProductoSin() +")"; 
			logger.info(insert);
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
	
	

	public static boolean eliminarProdModifidadorSin(ProductoModificadorSin prodSinEli)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from producto_modificador_sin where idproducto = " + prodSinEli.getIdProducto() + " and idproductocon=" + prodSinEli.getIdProductoSin(); 
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
	
	public static boolean tieneModificadorSin(int idProducto)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		boolean respuesta = false;
		try
		{
			Statement stm = con1.createStatement();
			String select = "select * from producto_modificador_sin where idproducto =" + idProducto ;
			logger.info(select);
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
