package capaDAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import capaModelo.Usuario;
import capaConexion.ConexionBaseDatos;
import capaModelo.EleccionForzada;
import capaModelo.Impuesto;
import capaModelo.MenuAgrupador;
import capaModelo.Tienda;
import org.apache.log4j.Logger;
import com.mysql.jdbc.ResultSetMetaData;
/**
 * Clase que se encarga de implementar todo lo relacionado con la base de datos de la entidad eleccion_forzada
 * @author JuanDavid
 *
 */
public class EleccionForzadaDAO {
	
/**
 * Método que se encarga de retonar todos las elecciones_forzadas parametrizado en el sistema.
 * @return Retorna un arrayList con tipos de datos genéricos.
 */
	public static ArrayList obtenerEleccionesForzadas(int idPregunta)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList eleccionesForzadas = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.ideleccion_forzada, a.idpregunta, a.idproducto, b.descripcion, a.precio, a.estado from eleccion_forzada a , producto b  where a.idproducto = b.idproducto and  a.idpregunta = " + idPregunta;
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
				eleccionesForzadas.add(fila);
				
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
		return(eleccionesForzadas);
		
	}
	
	public static ArrayList<EleccionForzada> obtEleccionesForzadas(int idPreConsulta)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<EleccionForzada> eleccionesForzadas = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select a.ideleccion_forzada, a.idpregunta, a.idproducto, b.descripcion, a.precio, a.estado from eleccion_forzada a , producto b  where a.idproducto = b.idproducto and  a.idpregunta = " + idPreConsulta;
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idEleccionForzada,idPregunta,idProducto,estado;
			String descripcion, precio;
			while(rs.next()){
				idEleccionForzada = rs.getInt("ideleccion_forzada");
				idPregunta = rs.getInt("idpregunta");
				idProducto = rs.getInt("idproducto");
				estado = rs.getInt("estado");
				descripcion = rs.getString("descripcion");
				precio = rs.getString("precio");
				EleccionForzada eleFor = new EleccionForzada(idEleccionForzada, idProducto, descripcion, idPregunta, precio,estado);
				eleccionesForzadas.add(eleFor);
				
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
		return(eleccionesForzadas);
		
	}
	
	/**
	 * Método de la capa DAO que se encarga de insertar una eleccion_forzadao.
	 * @param Recibe un objeto de eleccion_forzada del cual se extrae la información para la inserción.
	 * @return Se retorna un valor entero con el ideleccion_forzada creado en la base de datos
	 */
	public static int insertarEleccionForzada(EleccionForzada eleccionForzada)
	{
		Logger logger = Logger.getLogger("log_file");
		int idEleccionIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String insert = "insert into eleccion_forzada (idproducto, idpregunta, precio, estado) values (" + eleccionForzada.getIdProducto() + ", " + eleccionForzada.getIdPregunta() + " , '" + eleccionForzada.getPrecio() + "' , " + eleccionForzada.getEstado() + ")"; 
			logger.info(insert);
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idEleccionIns=rs.getInt(1);
				logger.info("id eleccion forzada insertada en bd " + idEleccionIns);
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
		return(idEleccionIns);
	}
	
	/**
	 * Método que se encarga de eliminar una determinada eleccion_Forzada, teniendo en cuenta el ideleccion_forzada pasado como parámetro
	 * @param idEleccionForzada Se recibe como parámetro el idEleccion_Forzada que se desea eliminar, teniendo en cuenta que es la 
	 * clave primaría de la tabla.
	 * @return Se retorna un valor booleano que indica si el resultado del proceso fue satisfactorio o no.
	 */
	public static boolean eliminarEleccionForzada(int idEleccionForzada)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from eleccion_forzada where ideleccion_forzada = " + idEleccionForzada; 
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
	
	/**
	 * Método de la capa DAO que se encarga de editar una EleccionForzada ya existente.
	 * @param EleccionForzada Recibe como parámetro un objeto de la entidad EleccionForzada con base en el cual se realiza la modificación
	 * @return Se retorna un valor booleano indicando si el proceso fue o no satisfactorio
	 */
	public static boolean EditarEleccionForzada(EleccionForzada eleccionForzada)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String update = "update eleccion_forzada set idproducto = " + eleccionForzada.getIdProducto() + " , idpregunta = " + eleccionForzada.getIdPregunta() + " , precio = '" + eleccionForzada.getPrecio() + "' , estado = " + eleccionForzada.getEstado()  + " where ideleccion_forzada = " + eleccionForzada.getIdEleccionForzada() ; 
			logger.info(update);
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
}
