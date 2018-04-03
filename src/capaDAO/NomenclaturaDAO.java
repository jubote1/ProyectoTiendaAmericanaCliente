package capaDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import capaModelo.NomenclaturaDireccion;
import capaConexion.ConexionBaseDatos;

public class NomenclaturaDAO {
	
	public static ArrayList<NomenclaturaDireccion> obtenerNomenclaturaDireccion()
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<NomenclaturaDireccion> nomenclaturas = new ArrayList();
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from nomenclatura_direccion" ; 
			logger.info(consulta);
			ResultSet rs = stm.executeQuery(consulta);
			int idnomenclatura;
			String nomenclatura;
			while(rs.next()){
				idnomenclatura = rs.getInt("idnomenclatura");
				nomenclatura = rs.getString("nomenclatura");
				NomenclaturaDireccion nomen = new NomenclaturaDireccion(idnomenclatura, nomenclatura);
				nomenclaturas.add(nomen);
			}
			rs.close();
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
		return(nomenclaturas);
	}
	

}
