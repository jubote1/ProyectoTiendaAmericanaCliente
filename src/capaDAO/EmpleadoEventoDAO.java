package capaDAO;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.mysql.jdbc.ResultSetMetaData;

import capaConexion.ConexionBaseDatos;
import capaModelo.EmpleadoEvento;

public class EmpleadoEventoDAO {
	
	public static boolean InsertarEventoRegistroEmpleado(EmpleadoEvento empEvento,  String bdGeneral, boolean auditoria )
	{
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDGeneral(bdGeneral);
		String insert = "insert into empleado_evento (id,tipo_evento,fecha,idtienda,uso_biometria) values (" + empEvento.getId() + " , '" + empEvento.getTipoEvento() + "' , '" + empEvento.getFecha() + "' , " + empEvento.getIdTienda() + " , '" + empEvento.getUsoBiometria() + "')";
		Statement stm;
		try
		{
			stm = con1.createStatement();
			stm.executeUpdate(insert);
			return(true);
		}
		catch (Exception e){
			System.out.println(e.toString());
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
	 * Método creado en la reestructuración de la forma de mostrar la información en donde se vuelve complejo retornarlo en un solo query
	 * y se hace necesario la devolución de un arrayList con la información para procesarla y mostarla de la manera correcta.
	 * @param idTienda
	 * @param fecha
	 * @param bdGeneral
	 * @param auditoria
	 * @return
	 */
	public static ArrayList<EmpleadoEvento> obtenerEntradasSalidasEmpleadosEventos(int idTienda, String fecha,  String bdGeneral, boolean auditoria )
	{
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDGeneral(bdGeneral);
		String select  = "SELECT * FROM (select b.nombre_largo, a.* from empleado_evento a , empleado b where a.idtienda = " + idTienda + " and a.fecha = '" + fecha + "' and a.id = b.id "
				+ "UNION distinct " + 
				"SELECT d.nombre_largo, c.* from empleado_evento c , empleado d WHERE  c.fecha = '"+ fecha +"' AND c.id = d.id AND c.id IN ( SELECT distinct(e.id) from empleado_evento e WHERE e.idtienda = " + idTienda + " AND e.fecha = '"+ fecha +"')) AS eventos " + 
				"order BY id,fecha_hora_log ASC";
		Statement stm;
		ArrayList<EmpleadoEvento> eventosEmpleado = new ArrayList();
		try
		{
			stm = con1.createStatement();
			ResultSet rs = stm.executeQuery(select);
			int id;
			String tipoEvento, fechaHoraLog, usoBiometria;
			String nombreEmpleado;
			EmpleadoEvento empEvento;
			while(rs.next())
			{
				id = rs.getInt("id");
				tipoEvento = rs.getString("tipo_evento");
				fechaHoraLog = rs.getString("fecha_hora_log");
				usoBiometria = rs.getString("uso_biometria");
				nombreEmpleado = rs.getString("nombre_largo");
				empEvento = new EmpleadoEvento(id, tipoEvento, fecha, fechaHoraLog, idTienda, usoBiometria);
				empEvento.setNombreEmpleado(nombreEmpleado);
				eventosEmpleado.add(empEvento);
			}
		}
		catch (Exception e){
			System.out.println(e.toString());
			try
			{
				con1.close();
				
			}catch(Exception e1)
			{
				
			}
			
		}	
		return(eventosEmpleado);
	}
	
	public static ArrayList obtenerEntradasSalidasEmpleados(int idTienda, String fechaReporte,  String bdGeneral, boolean auditoria )
	{
		ArrayList repEntradasSalidas = new ArrayList();
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDGeneral(bdGeneral);
		String select  = "SELECT EMPLEADO, FECHA, INGRESO, SALIDA, IFNULL(TIMESTAMPDIFF(minute, INGRESO, SALIDA),0) AS HORAS  FROM (SELECT DISTINCT(b.nombre_largo) AS EMPLEADO, a.fecha AS FECHA, " + 
				"IFNULL((SELECT fecha_hora_log c FROM empleado_evento c WHERE c.id = a.id AND c.tipo_evento = 'INGRESO' AND c.fecha = a.fecha),0) AS INGRESO, " + 
				"IFNULL((SELECT fecha_hora_log c FROM empleado_evento c WHERE c.id = a.id AND c.tipo_evento = 'SALIDA' AND c.fecha = a.fecha),0) AS SALIDA " + 
				"FROM empleado_evento a, empleado b  WHERE a.id = b.id AND a.fecha = '" + fechaReporte +  "' AND idtienda = " + idTienda + ") AS reporte";
		Statement stm;
		System.out.println(select);
		try
		{
			stm = con1.createStatement();
			ResultSet rs = stm.executeQuery(select);
			ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
			int numeroColumnas = rsMd.getColumnCount();
			while(rs.next()){
				String [] fila = new String[numeroColumnas];
				for(int y = 0; y < numeroColumnas; y++)
				{
					fila[y] = rs.getString(y+1);
				}
				repEntradasSalidas.add(fila);
			}
			rs.close();
			stm.close();
			con1.close();
		}catch(Exception e)
		{
			System.out.println(e.toString());
			try
			{
				con1.close();
				
			}catch(Exception e1)
			{
				
			}
		}
		return(repEntradasSalidas);
	}
	
	
	/**
	 * Método que se encargará de retornará un ArrayList con los eventos del empleado en un día determinado para revisar el estado del mismo
	 * para su registro actual
	 * @param idEmpleado
	 * @param fecha
	 * @param calendarioActual
	 * @param bdGeneral
	 * @param auditoria
	 * @return
	 */
	public static ArrayList<EmpleadoEvento> recuperarEventosEmpleado(int idEmpleado, String fecha,  String bdGeneral, boolean auditoria )
	{
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDGeneral(bdGeneral);
		String select  = "select * from empleado_evento where id = " + idEmpleado + " and fecha = '" + fecha + "' order by fecha_hora_log asc";
		Statement stm;
		ArrayList<EmpleadoEvento> eventosEmpleado = new ArrayList();
		try
		{
			stm = con1.createStatement();
			ResultSet rs = stm.executeQuery(select);
			int id, idTienda;
			String tipoEvento, fechaHoraLog, usoBiometria;
			EmpleadoEvento empEvento;
			while(rs.next())
			{
				id = rs.getInt("id");
				tipoEvento = rs.getString("tipo_evento");
				fechaHoraLog = rs.getString("fecha_hora_log");
				idTienda = rs.getInt("idtienda");
				usoBiometria = rs.getString("uso_biometria");
				empEvento = new EmpleadoEvento(id, tipoEvento, fecha, fechaHoraLog, idTienda, usoBiometria);
				eventosEmpleado.add(empEvento);
			}
		}
		catch (Exception e){
			System.out.println(e.toString());
			try
			{
				con1.close();
				
			}catch(Exception e1)
			{
				
			}
			
		}	
		return(eventosEmpleado);
	}
	
	
	public static String consultarEstadoRegistroEvento(int idEmpleado, String fecha, Calendar calendarioActual,  String bdGeneral, boolean auditoria )
	{
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDGeneral(bdGeneral);
		String select  = "select * from empleado_evento where id = " + idEmpleado + " and fecha = '" + fecha + "' order by fecha_hora_log asc";
		Statement stm;
		String respuesta = "";
		boolean entrada = false, salida = false, salidaDiaAnterior = false;
		boolean entradaAnterior = false, salidaAnterior = false;
		int horaActual = calendarioActual.get(Calendar.HOUR_OF_DAY);
		try
		{
			stm = con1.createStatement();
			ResultSet rs = stm.executeQuery(select);
			String evento = "";
			//Cantidad de registros 
			int cantidadRegistros = 0;
			//Nos ideamos forma de saber cuantos registros tiene el resultSet
			if(rs.last())
			{
				cantidadRegistros = rs.getRow();
				rs.first();
			}
			while(rs.next())
			{
				evento = rs.getString("tipo_evento");
				if(evento.equals(new String("INGRESO")))
				{
					entrada = true;
				}
				if(evento.equals(new String("SALIDA")))
				{
					salida = true;
				}
			}
			rs.close();
			stm.close();
			//Revisamos el estado del día anterior - esto se debe hacer en caso de que no se haya marcado ni entrada ni salida
			//Ponemos condición de qeu la hora actual sea menor o igual a 3 en cuyo caso puede ser la salida del día anterior
			if(!entrada && !salida && horaActual <= 3)
			{
				stm = con1.createStatement();
				//Volvemos a conformar el select restando uno a la fecha del día anterior.
				Date datFechaAnterior;
				String fechaAnterior = "";
				calendarioActual.add(Calendar.DAY_OF_YEAR, -1);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				datFechaAnterior = calendarioActual.getTime();
				fechaAnterior = dateFormat.format(datFechaAnterior);
				select = "select * from empleado_evento where id = " + idEmpleado + " and fecha = '" + fechaAnterior + "' order by fecha_hora_log asc";
				rs = stm.executeQuery(select);
				while(rs.next())
				{
					evento = rs.getString("tipo_evento");
					if(evento.equals(new String("INGRESO")))
					{
						entradaAnterior = true;
					}
					if(evento.equals(new String("SALIDA")))
					{
						salidaAnterior = true;
					}
				}
				rs.close();
				stm.close();
			}
			con1.close();
			if(entradaAnterior && !salidaAnterior)
			{
				respuesta = "SALIDA-ANTERIOR";
				return(respuesta);
			}
			
		}
		catch (Exception e){
			System.out.println(e.toString());
			try
			{
				con1.close();
				
			}catch(Exception e1)
			{
				
			}
			
		}
		//Realizamos las validaciones de lo que retornamos
		if(!entrada && !salida)
		{
			respuesta = "INGRESO";
		}else if(entrada && !salida)
		{
			respuesta ="SALIDA";
		}else if(entrada && salida)
		{
			respuesta ="VACIO";
		}
		return(respuesta);
	}

}
