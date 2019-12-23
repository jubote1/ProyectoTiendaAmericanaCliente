package capaDAO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.ConnectException;

import capaConexion.ConexionBaseDatos;
import capaModelo.Correo;
import capaModelo.Tienda;
import capaModelo.Usuario;
import utilidades.ControladorEnvioCorreo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;

public class EmpleadoBiometriaDAO {
	
	public static boolean InsertarBiometriaEmpleado(int idEmpleado, ByteArrayInputStream datosHuella, Integer sizeHuella, String bdGeneral, boolean auditoria )
	{
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDGeneral(bdGeneral);
		String insert = "insert into empleado_biometria (id,biometria) values (?,?)";
		PreparedStatement pstm;
		try
		{
			pstm = con1.prepareStatement(insert);
			pstm.setInt(1, idEmpleado);
			pstm.setBinaryStream(2, datosHuella, sizeHuella); 
			pstm.executeUpdate();
			pstm.close();
			con1.close();
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
	
	public static String verificacionBiometria (ByteArrayInputStream datosHuella, String bdGeneral, boolean auditoria) {
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		
		String query = "Select NombreEmpleado from empleado emp where emp.RegistroHuella = ? ";
		String NombreEmpleado = "";
		
		try {
			PreparedStatement ps = con1.prepareStatement(query);
			ps.setBlob(1, datosHuella);
			ResultSet rs = ps.executeQuery();
			rs.next();
			rs.getString(NombreEmpleado);
			rs.close();
			ps.close();
			con1.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return NombreEmpleado;
	}
	
	/**
	  * Identifica a una persona registrada por medio de su huella digital
	  */
	  public static Usuario identificarBiometriaEmpleado(String bdGeneral, DPFPFeatureSet featureserificacion, boolean auditoria) throws IOException
	  {
		  ConexionBaseDatos con = new ConexionBaseDatos();
	 	  Connection con1 = con.obtenerConexionBDGeneral(bdGeneral);
	 	 Usuario usuEncontrado = new Usuario(0, "", "", "", 0,"", false);
		  try {
		       //Establece los valores para la sentencia SQL
		    	
		        
		       //Obtiene todas las huellas de la bd
		       PreparedStatement identificarStmt = con1.prepareStatement("SELECT id,biometria FROM empleado_biometria");
		       ResultSet rs = identificarStmt.executeQuery();
		       DPFPVerification Verificador = DPFPGlobal.getVerificationFactory().createVerification();
		       //Si se encuentra el nombre en la base de datos
		       while(rs.next()){
			       //Lee la plantilla de la base de datos
			       byte templateBuffer[] = rs.getBytes("biometria");
			       int idEmpleado =rs.getInt("id");
			       //Crea una nueva plantilla a partir de la guardada en la base de datos
			       DPFPTemplate templateTemporal = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer);
			       //Envia la plantilla creada al objeto contendor de Template del componente de huella digital
			       
			       // Compara las caracteriticas de la huella recientemente capturda con la
			       // alguna plantilla guardada en la base de datos que coincide con ese tipo
			       DPFPVerificationResult result = Verificador.verify( featureserificacion,templateTemporal);
		
			       //compara las plantilas (actual vs bd)
			       //Si encuentra correspondencia dibuja el mapa
			       //e indica el nombre de la persona que coincidió.
			       if (result.isVerified()){
				       //Se logro encontrar la persona dentro de las personas
			    	   usuEncontrado = UsuarioDAO.obtenerEmpleadoGeneral(idEmpleado,bdGeneral, auditoria);
			    	   break;
		           }
		       }
		       //Realizamos cerrado de las conexiones
		       rs.close();
		       identificarStmt.close();
			   con1.close();

	       } 
		   catch (Exception e) {
			   if(e instanceof NullPointerException)
				{
					usuEncontrado = null;
					//Revisar la excepción que se da y enviar un correo
					 System.out.println("Error en biometria: "+e.getMessage() + e.toString());
				     ArrayList correos = GeneralDAO.obtenerCorreosParametro("ERRORIMPRESION", false);
					 Tienda tienda = TiendaDAO.obtenerTienda(false);
					 Date fecha = new Date();
					 Correo correo = new Correo();
					 correo.setAsunto("ERROR BIOMETRIA TIENDA " + tienda.getNombretienda() + " " + fecha.toString());
					 correo.setContrasena("Pizzaamericana2017");
					 correo.setUsuarioCorreo("alertaspizzaamericana@gmail.com");
					 correo.setMensaje("En este momento existen problemas de BIOMETRIA en la tienda " + tienda.getNombretienda() + "\n" + e.toString() + e.getMessage() + e.getStackTrace().toString());
					 ControladorEnvioCorreo contro = new ControladorEnvioCorreo(correo, correos);
					 contro.enviarCorreo();
				}
	    	   try
				{
					con1.close();
				}catch(Exception e1)
				{
					
				}
		   }
		  return(usuEncontrado);
	   }
	  
	  public static Usuario identificarSinBiometriaEmpleado(long cedulaEmpleado, String bdGeneral, boolean auditoria) throws IOException
	  {
		  ConexionBaseDatos con = new ConexionBaseDatos();
			Connection con1 = con.obtenerConexionBDGeneral(bdGeneral);
			boolean respuesta = false;
			String select = "select * from empleado where nombre = '" + cedulaEmpleado + "'";
			int idEmpleado  = 0;
			Statement stm;
			try
			{
				stm = con1.createStatement();
				ResultSet rs = stm.executeQuery(select);
				while(rs.next())
				{
					idEmpleado = rs.getInt("id");
					break;
				}
				rs.close();
				stm.close();
				con1.close();
				
			}
			catch (Exception e){
				System.out.println("PROBLEMA");
				System.out.println(e.toString() + " "  +  e.getMessage() +  " 3 " +e .getCause());
				if(e instanceof NullPointerException)
				{
					idEmpleado = -1;
				}
				try
				{
					con1.close();
				}catch(Exception e1)
				{
					
				}
				
			}
			Usuario usuario = null;
			if(idEmpleado != -1)
			{
				usuario = UsuarioDAO.obtenerEmpleadoGeneral(idEmpleado,bdGeneral, auditoria);
			}
			return(usuario);
	  }
	  
	  public static boolean estaEnroladoEmpleado(int idEmpleado, String bdGeneral, boolean auditoria )
		{
			ConexionBaseDatos con = new ConexionBaseDatos();
			Connection con1 = con.obtenerConexionBDGeneral(bdGeneral);
			boolean respuesta = false;
			String select = "select * from empleado_biometria where id = " + idEmpleado;
			Statement stm;
			try
			{
				stm = con1.createStatement();
				ResultSet rs = stm.executeQuery(select);
				while(rs.next())
				{
					rs.getInt("id");
					respuesta = true;
					break;
				}
				rs.close();
				stm.close();
				con1.close();
				
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
			return(respuesta);
		}
	  
	  public static boolean eliminarEnroladoEmpleado(int idEmpleado, String bdGeneral, boolean auditoria )
		{
			ConexionBaseDatos con = new ConexionBaseDatos();
			Connection con1 = con.obtenerConexionBDGeneral(bdGeneral);
			boolean respuesta = false;
			String delete = "delete from empleado_biometria where id = " + idEmpleado;
			System.out.println(delete);
			Statement stm;
			try
			{
				stm = con1.createStatement();
				stm.executeUpdate(delete);
				respuesta = true;
				stm.close();
				con1.close();
				
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
			return(respuesta);
		}
	  
	  

}
