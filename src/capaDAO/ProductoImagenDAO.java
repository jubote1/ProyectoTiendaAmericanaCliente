package capaDAO;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import capaConexion.ConexionBaseDatos;
import capaModelo.Producto;


public class ProductoImagenDAO {
	
	
	public boolean insertarProductoImagen(int idProducto, String ruta){
		Logger logger = Logger.getLogger("log_file");
		 String insert = "insert into producto_imagen(idproducto,imagen) values(?,?)";
		 FileInputStream fis = null;
		 PreparedStatement ps = null;
		 boolean respuesta = false;
		 ConexionBaseDatos con = new ConexionBaseDatos();
		 Connection con1 = con.obtenerConexionBDLocal();
		 try {
			 File file = new File(ruta);
			 fis = new FileInputStream(file);
			 ps = con1.prepareStatement(insert);
			 ps.setBinaryStream(1,fis,(int)file.length());
			 ps.setInt(2, idProducto);
			 ps.executeUpdate();
			 return true;
		 } catch (Exception e) {
			 logger.error(e.toString());
		 }finally{
			 try {
				 ps.close();
				 fis.close();
			 } catch (Exception ex) {
			 
			 }
		 }        
		 return false;
	}

	
	
}
