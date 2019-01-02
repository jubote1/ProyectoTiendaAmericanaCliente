package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import capaConexion.ConexionBaseDatos;
import capaModelo.Tienda;

import org.apache.log4j.Logger;



public class GeneralDAO {
	
	public static ArrayList obtenerCorreosParametro(String parametro, boolean auditoria)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList correos = new ArrayList();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select correo from parametros_correo where valorparametro = '" +parametro+"'";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			
			while(rs.next()){
				String correo = rs.getString("correo");
				correos.add(correo);
				
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
		return(correos);
		
	}

}
