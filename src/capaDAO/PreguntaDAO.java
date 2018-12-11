package capaDAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import capaModelo.Usuario;
import capaConexion.ConexionBaseDatos;
import capaModelo.Impuesto;
import capaModelo.AgrupadorMenu;
import capaModelo.Pregunta;
import capaModelo.Tienda;
import org.apache.log4j.Logger;
import com.mysql.jdbc.ResultSetMetaData;
/**
 * Clase que se encarga de implementar todo lo relacionado con la base de datos de la entidad pregunta
 * @author JuanDavid
 *
 */
public class PreguntaDAO {
	
/**
 * Método que se encarga de retonar todos las preguntas en el sistema.
 * @return Retorna un arrayList con tipos de datos genéricos.
 */
	public static ArrayList obtenerPreguntas(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList preguntas = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select idpregunta, titulopregunta, obliga_eleccion, numero_maximo_eleccion, estado, permite_dividir, descripcion from pregunta";
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
				preguntas.add(fila);
				
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
		return(preguntas );
		
	}
	
	public static Pregunta obtenerPregunta(int idPregunta, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		Pregunta  pregunta = new Pregunta(0,"",0,0,0,0,"");
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from pregunta where idpregunta = " + idPregunta;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
			int numeroColumnas = rsMd.getColumnCount();
			
			while(rs.next()){
				String tituloPregunta = rs.getString("titulopregunta");
				int obligaEleccion = rs.getInt("obliga_eleccion");
				int numeroMaximoEleccion = rs.getInt("numero_maximo_eleccion");
				int estado = rs.getInt("estado");
				int permiteDividir = rs.getInt("permite_dividir");
				String descripcion = rs.getString("descripcion");
				pregunta = new Pregunta(idPregunta, tituloPregunta, obligaEleccion, numeroMaximoEleccion, estado, permiteDividir, descripcion);
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
		return(pregunta);
		
	}
	
	/**
	 * Método de la capa DAO que se encarga de insertar una entidad pregunta.
	 * @param Pregunta Recibe un objeto de tipo Pregunta del cual se extrae la información para la inserción.
	 * @return Se retorna un valor entero con el idpregunta creado en la base de datos
	 */
	public static int insertarPregunta(Pregunta pregunta, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		int idPreguntaIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into pregunta (titulopregunta, obliga_eleccion, numero_maximo_eleccion, estado, permite_dividir, descripcion) values ('" + pregunta.getTituloPregunta() + "', " + pregunta.getObligaEleccion() +" , " + pregunta.getNumeroMaximoEleccion() + " , " + pregunta.getEstado() + " , " + pregunta.getPermiteDividir() + " , '" + pregunta.getDescripcion() + "')"; 
			if(auditoria)
			{
				logger.info(insert);
			}
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idPreguntaIns=rs.getInt(1);
				if(auditoria)
				{
					logger.info("id impuesto insertada en bd " + idPreguntaIns);
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
		return(idPreguntaIns);
	}
	
	/**
	 * Método que se encarga de eliminar una determinada pregunta, teniendo en cuenta el idpregunta pasado como parámetro
	 * @param idPregunta Se recibe como parámetro el idPregunta que se desea eliminar, teniendo en cuenta que es la 
	 * clave primaría de la tabla.
	 * @return Se retorna un valor booleano que indica si el resultado del proceso fue satisfactorio o no.
	 */
	public static boolean eliminarPregunta(int idPregunta, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from pregunta where idpregunta = " + idPregunta; 
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
	 * Método de la capa DAO que se encarga de editar una pregunta ya existente.
	 * @param impuesto Recibe como parámetro un objeto de la entidad Pregunta con base en el cual se realiza la modificación
	 * @return Se retorna un valor booleano indicando si el proceso fue o no satisfactorio
	 */
	public static boolean EditarPregunta(Pregunta pregunta, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update pregunta set titulopregunta = '" + pregunta.getTituloPregunta() + "' , obliga_eleccion = " + pregunta.getObligaEleccion() + ", numero_maximo_eleccion = " + pregunta.getNumeroMaximoEleccion() + ", estado = " + pregunta.getEstado() + " , permite_dividir = " + pregunta.getPermiteDividir() + " , descripcion = '" + pregunta.getDescripcion() + "'  where idpregunta = " + pregunta.getIdPregunta() ; 
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
	 * Método de la capa DAO que se encarga de retornar las preguntas que tiene asociadas uno producto con el fin de completar el pedido
	 * y realizar la facturación de productos
	 * @param idProducto Se recibe como parámetro el id productoy y por intermedio de unions se realiza la consulta, lo
	 * anterior debido a que las 5 posibles preguntas están dentro de un mismo registro en la tabla producto.
	 * @return
	 */
	public static ArrayList<Pregunta> obtenerPreguntaProducto(int idProducto, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ArrayList<Pregunta> preguntaProducto = new ArrayList();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.* from pregunta a , producto b where a.idpregunta = b.idpreguntaforzada1 and  b.idproducto = " + idProducto + " union "
					+ " select a.* from pregunta a , producto b where a.idpregunta = b.idpreguntaforzada2 and  b.idproducto = " + idProducto + " union "
					+ " select a.* from pregunta a , producto b where a.idpregunta = b.idpreguntaforzada3 and  b.idproducto = " + idProducto + " union "
					+ " select a.* from pregunta a , producto b where a.idpregunta = b.idpreguntaforzada4 and  b.idproducto = " + idProducto + " union "
					+ " select a.* from pregunta a , producto b where a.idpregunta = b.idpreguntaforzada5 and  b.idproducto = " + idProducto;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			
			String tituloPregunta, descripcion;
			int idPregunta, obligaEleccion, numeroMaximoEleccion, estado, permiteDividir;
			Pregunta pregunta;
			while(rs.next()){
				idPregunta = rs.getInt("idpregunta");
				tituloPregunta = rs.getString("titulopregunta");
				obligaEleccion = rs.getInt("obliga_eleccion");
				numeroMaximoEleccion = rs.getInt("numero_maximo_eleccion");
				estado = rs.getInt("estado");
				permiteDividir = rs.getInt("permite_dividir");
				descripcion = rs.getString("descripcion");
				pregunta = new Pregunta(idPregunta, tituloPregunta, obligaEleccion, numeroMaximoEleccion, estado, permiteDividir, descripcion);
				preguntaProducto.add(pregunta);
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
		return(preguntaProducto);
	}
	
	
}
