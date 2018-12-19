package capaDAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import capaModelo.Usuario;
import capaConexion.ConexionBaseDatos;
import capaModelo.Impuesto;
import capaModelo.AgrupadorMenu;
import capaModelo.Tienda;
import capaModelo.TipoPedido;

import org.apache.log4j.Logger;
import com.mysql.jdbc.ResultSetMetaData;
/**
 * Clase que se encarga de implementar todo lo relacionado con la base de datos de la entidad impuestos
 * @author JuanDavid
 *
 */
public class TipoPedidoDAO {
	
/**
 * Método que se encarga de retonar todos los impuestos parametrizado en el sistema.
 * @return Retorna un arrayList con tipos de datos genéricos.
 */
	public static ArrayList obtenerTiposPedido(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList tiposPedido = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from tipo_pedido";
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
				tiposPedido.add(fila);
				
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
		return(tiposPedido);
		
	}
	
	public static ArrayList<TipoPedido> obtenerTiposPedidoNat(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<TipoPedido> tiposPedido = new ArrayList();
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from tipo_pedido order by valordefecto desc";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			TipoPedido tipoPedidoRet = new TipoPedido(0, "", false, "", false);
			int valDef = 0;
			String descripcion = "";
			boolean valorDefecto = false;
			int idTipoPedido = 0;
			String icono = "";
			boolean esDomicilio = false;
			int intEsDomi;
			while(rs.next()){
				idTipoPedido = rs.getInt("idtipopedido");
				descripcion = rs.getString("descripcion");
				valDef = rs.getInt("valordefecto");
				icono = rs.getString("icono");
				intEsDomi = rs.getInt("esdomicilio");
				if(intEsDomi == 1)
				{
					esDomicilio = true;
				}
				else
				{
					esDomicilio = false;
				}
				if(valDef == 1)
				{
					valorDefecto = true;
				}else
				{
					valorDefecto = false;
				}
				tipoPedidoRet= new TipoPedido(idTipoPedido, descripcion, valorDefecto, icono, esDomicilio);
				tiposPedido.add(tipoPedidoRet);
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
		return(tiposPedido);
		
	}
	
	public static TipoPedido obtenerTipoPedido(int idTipoPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		TipoPedido TipoPedidoRet = new TipoPedido(0, "", false, "", false);
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from tipo_pedido where idtipoPedido = " + idTipoPedido;
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int valDef = 0;
			String descripcion = "";
			boolean valorDefecto = false;
			String icono = "";
			boolean esDomicilio = false;
			int intEsDomi;
			while(rs.next()){
				descripcion = rs.getString("descripcion");
				valDef = rs.getInt("valordefecto");
				icono = rs.getString("icono");
				intEsDomi = rs.getInt("esdomicilio");
				if(intEsDomi == 1)
				{
					esDomicilio = true;
				}
				if(valDef == 1)
				{
					valorDefecto = true;
				}
				
			}
			TipoPedidoRet= new TipoPedido(idTipoPedido, descripcion, valorDefecto, icono, esDomicilio);
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
		return(TipoPedidoRet);
		
	}
	
	/**
	 * Método de la capa DAO que se encarga de insertar una entidad impuesto.
	 * @param impuesto Recibe un objeto de tipo impuesto del cual se extrae la información para la inserción.
	 * @return Se retorna un valor entero con el idimpuesto creado en la base de datos
	 */
	public static int insertarTipoPedido(TipoPedido tipPed, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		int idTipoIns = 0;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			int esDomi = 0;
			int valDef = 0;
			if(tipPed.isValorDefecto())
			{
				valDef = 1;
			}
			if(tipPed.isEsDomicilio())
			{
				esDomi = 1;
			}
			Statement stm = con1.createStatement();
			String insert = "insert into tipo_pedido (descripcion, valordefecto, esdomicilio) values ('" + tipPed.getDescripcion() + "' ," + valDef + ","+ esDomi +")"; 
			if(auditoria)
			{
				logger.info(insert);
			}
			stm.executeUpdate(insert);
			ResultSet rs = stm.getGeneratedKeys();
			if (rs.next()){
				idTipoIns=rs.getInt(1);
				if(auditoria)
				{
					logger.info("id Tipo Pedido insertada en bd " + idTipoIns);
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
		return(idTipoIns);
	}
	
	/**
	 * Método que se encarga de eliminar un determinado en impuesto, teniendo en cuenta el idimpuesto pasado como parámetro
	 * @param idImpuesto Se recibe como parámetro el idimpuesto que se desea eliminar, teniendo en cuenta que es la 
	 * clave primaría de la tabla.
	 * @return Se retorna un valor booleano que indica si el resultado del proceso fue satisfactorio o no.
	 */
	public static boolean eliminarTipoPedido(int idTipoPedido, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta = true;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			Statement stm = con1.createStatement();
			String delete = "delete from tipo_pedido where idtipopedido = " + idTipoPedido; 
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
	 * Método de la capa DAO que se encarga de editar un impuesto ya existente.
	 * @param impuesto Recibe como parámetro un objeto de la entidad impuesto con base en el cual se realiza la modificación
	 * @return Se retorna un valor booleano indicando si el proceso fue o no satisfactorio
	 */
	public static boolean EditarTipoPedido(TipoPedido tipPedidoEditar, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		boolean respuesta;
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		try
		{
			int valDef = 0;
			int esDomi = 0;
			if(tipPedidoEditar.isValorDefecto())
			{
				valDef = 1;
			}
			if(tipPedidoEditar.isEsDomicilio())
			{
				esDomi = 1;
			}
			Statement stm = con1.createStatement();
			String update = "update tipo_pedido set descripcion = '" + tipPedidoEditar.getDescripcion() + "' , valordefecto =" + valDef + " , esdomicilio = " + esDomi + " where idtipopedido = " + tipPedidoEditar.getIdTipoPedido() ; 
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
	
	//Método que se encarga de retornar cual es el tipo de pedido asociado a domicilio
	
	public static int obtenerTipoPedidoDomicilio(boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		int idTipoDomicilio = 0;
		
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select idtipopedido from tipo_pedido where esdomicilio = 1";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
		
			while(rs.next()){
				idTipoDomicilio = rs.getInt("idtipopedido");
				break;
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
		return(idTipoDomicilio);
		
	}
}
