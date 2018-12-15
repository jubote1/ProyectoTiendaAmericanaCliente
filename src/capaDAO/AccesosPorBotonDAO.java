package capaDAO;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import capaModelo.Usuario;
import capaConexion.ConexionBaseDatos;
import capaModelo.AccesosPorBoton;
import capaModelo.AccesosPorMenu;
import capaModelo.AccesosPorOpcion;
import capaModelo.AgrupadorMenu;
import capaModelo.Tienda;
import org.apache.log4j.Logger;
import com.mysql.jdbc.ResultSetMetaData;
/**
 * Clase que se encarga de implementar todo lo relacionado con la base de datos de la entidad tienda.
 * @author JuanDavid
 *
 */
public class AccesosPorBotonDAO {
	

	/**
	 * Método que retorna un ArrayList con todos los Agrupadores Menús definidos en el sistema en un arraylist
	 * @param auditoria
	 * @return
	 */
	public static ArrayList<AccesosPorBoton> obtenerAccesosPorBotonObj(boolean auditoria, int idTipUsu, String codPant)
	{
		Logger logger = Logger.getLogger("log_file");
		ConexionBaseDatos con = new ConexionBaseDatos();
		Connection con1 = con.obtenerConexionBDLocal();
		ArrayList<AccesosPorBoton> accesosPorBoton = new ArrayList();
		AccesosPorBoton accPorBoton = new AccesosPorBoton(0,"", "", "", 0);
		try
		{
			Statement stm = con1.createStatement();
			String consulta = "select * from accesos_por_boton where idtipousuario =" + idTipUsu + " and codpantalla = '" + codPant + "'";
			if(auditoria)
			{
				logger.info(consulta);
			}
			ResultSet rs = stm.executeQuery(consulta);
			int idAccesoBoton,idTipoEmpleado;
			String codigoPantalla, codBoton, descripcion;
			while(rs.next()){
				
				idAccesoBoton = rs.getInt("idaccesoboton");
				codigoPantalla = rs.getString("codpantalla");
				idTipoEmpleado = rs.getInt("idtipousuario");
				codBoton = rs.getString("codboton");
				descripcion = rs.getString("descripcion");
				accPorBoton = new AccesosPorBoton(idAccesoBoton, codigoPantalla, codBoton, descripcion, idTipoEmpleado );
				accesosPorBoton.add(accPorBoton);
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
		return(accesosPorBoton);
		
	}
	
	
}
