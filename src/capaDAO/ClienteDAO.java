package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import capaModelo.Cliente;
import capaConexion.ConexionBaseDatos;


	
	public class ClienteDAO {
		
		/**
		 * 
		 * @param tel Dado el telef�no de un cliente se encarga de retornar en un array list de objetos tipo liente
		 * la informaci�n de los registros que coincidente con dicho tel�fono.
		 * @return ArrayList de tipo cliente con la informaci�n de clientes que coinciden con el tel�fono dado.
		 */
		public static ArrayList<Cliente> obtenerCliente(String tel, boolean auditoria)
		{
			Logger logger = Logger.getLogger("log_file");
			ArrayList<Cliente> clientes = new ArrayList();
			ConexionBaseDatos con = new ConexionBaseDatos();
			Connection con1 = con.obtenerConexionBDLocal();
			try
			{
				Statement stm = con1.createStatement();
				//String consulta = "select a.idcliente, b.nombretienda nombreTienda, a.idtienda, a.nombre, a.apellido, a.nombrecompania, a.direccion, a.zona, a.observacion, a.telefono, c.nombre nombremunicipio, a.latitud, a.longitud, a.memcode, a.idnomenclatura, a.num_nomencla1, a.num_nomencla2, a.num3, d.nomenclatura from cliente a,tienda b, municipio c, nomenclatura_direccion d where a.idnomenclatura = d.idnomenclatura and a.idtienda = b.idtienda and a.idmunicipio = c.idmunicipio and a.telefono = '" + tel +"'";
				String consulta = "select a.idcliente, b.nombretienda nombreTienda, a.idtienda, a.nombre, a.apellido, a.nombrecompania, a.direccion, a.zona, a.observacion, a.telefono, c.nombre nombremunicipio, a.latitud, a.longitud, a.memcode, a.idnomenclatura, a.num_nomencla1, a.num_nomencla2, a.num3, d.nomenclatura, a.idmunicipio from cliente a,tienda b, municipio c, nomenclatura_direccion d where a.idnomenclatura = d.idnomenclatura and a.idtienda = b.idtienda and a.idmunicipio = c.idmunicipio and a.telefono = '" + tel +"'";
				if(auditoria)
				{
					logger.info(consulta);
				}
				ResultSet rs = stm.executeQuery(consulta);
				int idcliente;
				String nombreTienda;
				String nombreCliente;
				String apellido;
				String nombreCompania;
				String direccion;
				String zona;
				String observacion;
				String telefono;
				String municipio;
				float latitud;
				float longitud;
				int idTienda;
				int memcode;
				int idMunicipio;
				int idnomenclatura;
				String numNomenclatura1;
				String numNomenclatura2;
				String num3;
				String nomenclatura;
				while(rs.next()){
					idcliente = rs.getInt("idcliente");
					nombreTienda = rs.getString("nombreTienda");
					nombreCliente = rs.getString("nombre");
					apellido = rs.getString("apellido");
					nombreCompania = rs.getString("nombrecompania");
					direccion = rs.getString("direccion");
					zona = rs.getString("zona");
					observacion = rs.getString("observacion");
					telefono = rs.getString("telefono");
					municipio = rs.getString("nombremunicipio");
					idMunicipio = rs.getInt("idmunicipio");
					latitud = rs.getFloat("latitud");
					longitud = rs.getFloat("longitud");
					idTienda = rs.getInt("idtienda");
					memcode = rs.getInt("memcode");
					idnomenclatura = rs.getInt("idnomenclatura");
					numNomenclatura1 = rs.getString("num_nomencla1");
					numNomenclatura2 = rs.getString("num_nomencla2");
					num3 = rs.getString("num3");
					nomenclatura = rs.getString("nomenclatura");
					Cliente clien = new Cliente( idcliente, telefono, nombreCliente,apellido, nombreCompania, direccion,municipio,idMunicipio,latitud, longitud, zona, observacion, nombreTienda, idTienda, memcode,idnomenclatura, numNomenclatura1, numNomenclatura2, num3, nomenclatura);
					clientes.add(clien);
				}
				rs.close();
				stm.close();
				con1.close();
			}catch (Exception e){
				logger.error(e.toString());
				try
				{
					con1.close();
				}catch(Exception e1)
				{
				}
			}
			return(clientes);
			
		}
		
		/**
		 * M�todo que se encarga de insertar en la base de datos un cliente
		 * @param clienteInsertar Se recibe como par�metro un objeto Modelo Cliente con base en el cual se inserta el cliente.
		 * @return  Se retorna un int con el valor del idcliente insertado en la base de datos.
		 */
		public static int insertarCliente(Cliente clienteInsertar, boolean auditoria)
		{
			Logger logger = Logger.getLogger("log_file");
			int idClienteInsertado = 0;
			ConexionBaseDatos con = new ConexionBaseDatos();
			Connection con1 = con.obtenerConexionBDLocal();
			try
			{
				Statement stm = con1.createStatement();
				String insert = "insert into cliente (idtienda,nombre, apellido, nombrecompania, direccion, zona, telefono, observacion, idmunicipio, latitud, longitud, idnomenclatura, num_nomencla1, num_nomencla2, num3) values (" + clienteInsertar.getIdtienda() + ", '" +clienteInsertar.getNombres() + "' , '" + clienteInsertar.getApellidos() + "' , '" + clienteInsertar.getNombreCompania() + "' , '" + clienteInsertar.getDireccion() + "' , '" + clienteInsertar.getZonaDireccion() +"' , '" + clienteInsertar.getTelefono() + "' , '" + clienteInsertar.getObservacion() + "' , " + clienteInsertar.getIdMunicipio() + " , " + clienteInsertar.getLatitud() + " , " + clienteInsertar.getLontitud() + " ,  " + clienteInsertar.getIdnomenclatura() + " , '" + clienteInsertar.getNumNomenclatura() + "' , '" + clienteInsertar.getNumNomenclatura2() + "' ,  '" + clienteInsertar.getNum3() + "')"; 
				if(auditoria)
				{
					logger.info(insert);
				}
				stm.executeUpdate(insert);
				ResultSet rs = stm.getGeneratedKeys();
				if (rs.next()){
					idClienteInsertado=rs.getInt(1);
					
		        }
				stm.close();
				rs.close();
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
			return(idClienteInsertado);
		}
		
		/**
		 * M�todo que se encarga de retonar un objeto Modelo CLiente, con base en un id que env�a como par�metro.
		 * @param id Se recibe un id cliente y con base en este se busca en base de datos.
		 * @return Se retorna un objeto Modelo Cliente con la informaci�n del cliente.
		 */
		public static Cliente obtenerClienteporID(int id, boolean auditoria)
		{
			Logger logger = Logger.getLogger("log_file");
			Cliente clienteConsultado = new Cliente(); 
			ConexionBaseDatos con = new ConexionBaseDatos();
			Connection con1 = con.obtenerConexionBDLocal();
			try
			{
				Statement stm = con1.createStatement();
				String consulta = "select a.idcliente, b.nombretienda, a.idtienda, a.nombre, a.apellido, a.nombrecompania, a.direccion, a.zona, a.observacion, a.telefono, c.nombre nombremunicipio, a.latitud, a.longitud, a.memcode, a.idnomenclatura, a.num_nomencla1, a.num_nomencla2, a.num3, d.nomenclatura, c.idmunicipio from cliente a JOIN tienda b ON a.idtienda = b.idtienda JOIN municipio c ON a.idmunicipio = c.idmunicipio left join nomenclatura_direccion d on a.idnomenclatura = d.idnomenclatura  where a.idcliente = " + id +"";
				if(auditoria)
				{
					logger.info(consulta);
				}
				ResultSet rs = stm.executeQuery(consulta);
				int idcliente;
				String nombreTienda;
				String nombreCliente;
				String apellido;
				String nombreCompania;
				String direccion;
				String zona;
				String observacion;
				String telefono;
				String municipio;
				int idMunicipio;
				float latitud;
				float longitud;
				int idTienda;
				int memcode;
				int idnomenclatura;
				String numNomenclatura1;
				String numNomenclatura2;
				String num3;
				String nomenclatura;
				while(rs.next()){
					idcliente = rs.getInt("idcliente");
					nombreTienda = rs.getString("nombretienda");
					nombreCliente = rs.getString("nombre");
					apellido = rs.getString("apellido");
					nombreCompania = rs.getString("nombrecompania");
					direccion = rs.getString("direccion");
					zona = rs.getString("zona");
					observacion = rs.getString("observacion");
					telefono = rs.getString("telefono");
					municipio = rs.getString("nombremunicipio");
					idMunicipio = rs.getInt("idmunicipio");
					latitud = rs.getFloat("latitud");
					longitud = rs.getFloat("longitud");
					idTienda = rs.getInt("idtienda");
					memcode = rs.getInt("memcode");
					idnomenclatura = rs.getInt("idnomenclatura");
					numNomenclatura1 = rs.getString("num_nomencla1");
					numNomenclatura2 = rs.getString("num_nomencla2");
					num3 = rs.getString("num3");
					nomenclatura = rs.getString("nomenclatura");
					idMunicipio = rs.getInt("idmunicipio");
					clienteConsultado = new Cliente( idcliente, telefono, nombreCliente, apellido, nombreCompania, direccion,municipio,idMunicipio,latitud, longitud, zona, observacion, nombreTienda, idTienda,memcode,idnomenclatura, numNomenclatura1, numNomenclatura2, num3, nomenclatura);
					clienteConsultado.setIdMunicipio(idMunicipio);
				}
				rs.close();
				stm.close();
				con1.close();
			}catch (Exception e){
				logger.error(e.toString());
				try
				{
					con1.close();
				}catch(Exception e1)
				{
				}
			}
			return(clienteConsultado);
			
		}
		
		/**
		 * M�todo que busca actualizar la informaci�n de un cliente con base en un objeto Modelo Cliente enviado como par�metro.
		 * @param clienteAct Se env�a como par�metro un tipo de Modelo Cliente con base en el cual se hace la actualizaci�n.
		 * @return Se retorna un valor entero con el valor del id cliente actualizado en el sistema.
		 */
		public static int actualizarCliente(Cliente clienteAct, boolean auditoria)
		{
			Logger logger = Logger.getLogger("log_file");
			int idClienteActualizado = 0;
			ConexionBaseDatos con = new ConexionBaseDatos();
			Connection con1 = con.obtenerConexionBDLocal();
			try
			{
				//Para actualizar el cliente el idcliente debe ser diferente de vac�o.
				Statement stm = con1.createStatement();
				if(clienteAct.getIdcliente() > 0)
				{
					String update = "update cliente set nombre = '" + clienteAct.getNombres() + "' , direccion = '" + clienteAct.getDireccion() + "' , idmunicipio = " + clienteAct.getIdMunicipio() + " , latitud = " + clienteAct.getLatitud() + " , longitud = " + clienteAct.getLontitud() + " , zona = '" + clienteAct.getZonaDireccion() + "' , observacion = '" + clienteAct.getObservacion() +"', apellido = '" + clienteAct.getApellidos() + "' , nombrecompania = '" + clienteAct.getNombreCompania() + "' , idnomenclatura = " + clienteAct.getIdnomenclatura() + " , num_nomencla1 = '" + clienteAct.getNumNomenclatura() + "' , num_nomencla2 = '" + clienteAct.getNumNomenclatura2() + "' , num3 =  '" + clienteAct.getNum3()  + "'  where idcliente = " + clienteAct.getIdcliente(); 
					if(auditoria)
					{
						logger.info(update);
					}
					logger.info(update);
					stm.executeUpdate(update);
					idClienteActualizado = clienteAct.getIdcliente();
				}else
				{
					if(auditoria)
					{
						logger.info("No se pudo hacer actualizaci�n dado que el idCliente venia en ceros o vac�o");
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
			logger.info("id cliente actualizado" + idClienteActualizado);
			return(idClienteActualizado);
		}
		
		/**
		 * El campo memcode corresponde al consecutivo cliente que tiene en la base de datos de la tienda, como la inserci�n
		 * es asincrona, se requiere que luego de insertado el cliente en nuestro sistema y luego de insertado el pedido en la tienda
		 * donde el cliente nuevo tiene un memcode, venimos y actualizamos el memcode en nuestro sistema.
		 * @param idCliente Se recibe como par�metro el idcliente al cual se le va a actualizar el memcode.
		 * @param memcode Valor de memcode a actualizar.
		 * @return Se retorna un entero con el idcliente actualizado.
		 */
		public static int actualizarClienteMemcode(int idCliente, int memcode, boolean auditoria)
		{
			Logger logger = Logger.getLogger("log_file");
			int idClienteActualizado = 0;
			ConexionBaseDatos con = new ConexionBaseDatos();
			Connection con1 = con.obtenerConexionBDLocal();
			try
			{
				//Para actualizar el cliente el idcliente debe ser diferente de vac�o.
				Statement stm = con1.createStatement();
				String update = "update cliente set memcode = " + memcode + "  where idcliente = " + idCliente; 
				if(auditoria)
				{
					logger.info(update);
				}
				stm.executeUpdate(update);
				idClienteActualizado = idCliente;
				
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
			logger.info("id cliente actualizado" + idClienteActualizado);
			return(idClienteActualizado);
		}
		
		
		public static ArrayList<Cliente> ObtenerClientesTienda(int idtienda, boolean auditoria)
		{
			Logger logger = Logger.getLogger("log_file");
			ArrayList<Cliente> clientes = new ArrayList();
			ConexionBaseDatos con = new ConexionBaseDatos();
			Connection con1 = con.obtenerConexionBDLocal();
			try
			{
				Statement stm = con1.createStatement();
				String consulta = "select a.idcliente, b.nombre nombreTienda, a.idtienda, a.nombre, a.apellido, a.nombrecompania, a.direccion, a.zona, a.observacion, a.telefono, c.nombre nombremunicipio, a.latitud, a.longitud, a.memcode, a.idnomenclatura, a.num_nomencla1, a.num_nomencla2, a.num3, d.nomenclatura, c.idmunicipio from cliente a,tienda b, municipio c, nomenclatura_direccion d where a.idnomenclatura = d.idnomenclatura and a.idtienda = b.idtienda and a.idmunicipio = c.idmunicipio and a.idtienda = " + idtienda +" and a.idcliente not in (select b.idcliente from geolocaliza_masivo_tienda b )" ;
				if(auditoria)
				{
					logger.info(consulta);
				}
				ResultSet rs = stm.executeQuery(consulta);
				int idcliente;
				String nombreTienda;
				String nombreCliente;
				String apellido;
				String nombreCompania;
				String direccion;
				String zona;
				String observacion;
				String telefono;
				String municipio;
				int idMunicipio;
				float latitud;
				float longitud;
				int idTienda;
				int memcode;
				int idnomenclatura;
				String numNomenclatura1;
				String numNomenclatura2;
				String num3;
				String nomenclatura;
				while(rs.next()){
					idcliente = rs.getInt("idcliente");
					nombreTienda = rs.getString("nombreTienda");
					nombreCliente = rs.getString("nombre");
					apellido = rs.getString("apellido");
					nombreCompania = rs.getString("nombrecompania");
					direccion = rs.getString("direccion");
					zona = rs.getString("zona");
					observacion = rs.getString("observacion");
					telefono = rs.getString("telefono");
					municipio = rs.getString("nombremunicipio");
					idMunicipio = rs.getInt("idmunicipio");
					latitud = rs.getFloat("latitud");
					longitud = rs.getFloat("longitud");
					idTienda = rs.getInt("idtienda");
					memcode = rs.getInt("memcode");
					idnomenclatura = rs.getInt("idnomenclatura");
					numNomenclatura1 = rs.getString("num_nomencla1");
					numNomenclatura2 = rs.getString("num_nomencla2");
					num3 = rs.getString("num3");
					nomenclatura = rs.getString("nomenclatura");
					Cliente clien = new Cliente( idcliente, telefono, nombreCliente,apellido, nombreCompania, direccion,municipio, idMunicipio,latitud, longitud, zona, observacion, nombreTienda, idTienda, memcode, idnomenclatura, numNomenclatura1, numNomenclatura2, num3, nomenclatura);
					clientes.add(clien);
				}
				rs.close();
				stm.close();
				con1.close();
			}catch (Exception e){
				logger.error(e.toString());
				try
				{
					con1.close();
				}catch(Exception e1)
				{
				}
			}
			return(clientes);
			
		}
		
		public static boolean InsertarClienteGeolocalizado(int idcliente,  String direccion, String municipio, int idtiendaanterior, int idtiendaactual, boolean auditoria)
		{
			Logger logger = Logger.getLogger("log_file");
			ConexionBaseDatos con = new ConexionBaseDatos();
			Connection con1 = con.obtenerConexionBDLocal();
			try
			{
				Statement stm = con1.createStatement();
				String insert = "insert into geolocaliza_masivo_tienda (idcliente,direccionbuscada,municipio,idtiendaanterior,idtiendaactual) values (" + idcliente + ", '" + direccion + "' , '" + municipio  + "' , " + idtiendaanterior + " , " + idtiendaactual + ")"; 
				if(auditoria)
				{
					logger.info(insert);
				}
				stm.executeUpdate(insert);
				ResultSet rs = stm.getGeneratedKeys();
				stm.close();
				rs.close();
				con1.close();
				return(true);
			}
			catch (Exception e){
				logger.error(e.toString());
				try
				{
					con1.close();
					return(false);
				}catch(Exception e1)
				{
					return(false);
				}
				
			}
			
		}
		
		/**
		 * M�todo que se encarga de validar si un cliente dado el tel�fono existe o no en el sistema.
		 * @param telefono par�metro con base en el cual se sabe si un cliente existe o no.
		 * @return Se retorna un valor booleano que indica si el cliente existe o no.
		 */
		public static boolean  existeCliente(String telefono, boolean auditoria)
		{
			Logger logger = Logger.getLogger("log_file");
			ArrayList<Cliente> clientes = new ArrayList();
			ConexionBaseDatos con = new ConexionBaseDatos();
			Connection con1 = con.obtenerConexionBDLocal();
			boolean respuesta = false;
			try
			{
				Statement stm = con1.createStatement();
				String consulta = "select idcliente from cliente  where telefono = '"+ telefono + "'";
				if(auditoria)
				{
					logger.info(consulta);
				}
				ResultSet rs = stm.executeQuery(consulta);
				int idcliente;
				while(rs.next()){
					respuesta = true;
					break;
				}
				rs.close();
				stm.close();
				con1.close();
			}catch (Exception e){
				logger.error(e.toString());
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
