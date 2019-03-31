package capaConexion;
import java.sql.*;

import interfazGrafica.Sesion;

/**
 * M�todo que implementa la conexi�n a base de datos desde la aplicaci�n de Servicios Tienda
 * @author JuanDavid
 *
 */
public class ConexionBaseDatos {
	
	public static void main(String args[]){
		
		ConexionBaseDatos cn = new ConexionBaseDatos();
		cn.obtenerConexionBDTienda("PixelSqlbase");
	}

	/**
	 * M�todo que se encarga de retornar la conexi�n al sistema de Contact Center Web
	 * @return Retorna un objeto de tipo conexi�n para la base de datos de Contact Center Web.
	 */
	public Connection obtenerConexionBDPrincipal(){
		try {
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
		    

		} catch (Exception e) {

		    System.out.println(e.toString());

		}
		
		Connection con = null;
		//...

		try {

			//con = DriverManager.getConnection(
		    //        "jdbc:mysql://localhost/pizzaamericana?"
		    //        + "user=root&password=naillive");
			
			con = DriverManager.getConnection(
		            "jdbc:mysql://192.168.0.25/pizzaamericana?"
		            + "user=root&password=4m32017");

		    // Otros y operaciones sobre la base de datos...

		} catch (SQLException ex) {

		    // Mantener el control sobre el tipo de error
		    System.out.println("SQLException: " + ex.getMessage());

		}
		return(con);
	}
	
	public Connection obtenerConexionBDLocal(){
		try {
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
		    

		} catch (Exception e) {

		    System.out.println(e.toString());

		}
		
		Connection con = null;
		//...

		try {

			//con = DriverManager.getConnection(
		    //        "jdbc:mysql://localhost/pizzaamericana?"
		    //        + "user=root&password=naillive");
			
			con = DriverManager.getConnection(
		            "jdbc:mysql://"+Sesion.getHost()+"/tiendaamericana?"
		            + "user=root&password=4m32017");

		    // Otros y operaciones sobre la base de datos...

		} catch (SQLException ex) {

		    // Mantener el control sobre el tipo de error
		    System.out.println("SQLException: " + ex.getMessage());

		}
		return(con);
	}
	
	/**
	 * M�todo que se encarga de tener conexi�n al sistema principal de temas generales
	 * @return
	 */
	public Connection obtenerConexionBDGeneral(){
		try {
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
		    

		} catch (Exception e) {

		    System.out.println(e.toString());

		}
		
		Connection con = null;
		//...

		try {

			//con = DriverManager.getConnection(
		    //        "jdbc:mysql://localhost/general?"
		    //        + "user=root&password=4m32017");
			
			con = DriverManager.getConnection(
		            "jdbc:mysql://192.168.0.25/general?"
		            + "user=root&password=4m32017");

		    // Otros y operaciones sobre la base de datos...

		} catch (SQLException ex) {

		    // Mantener el control sobre el tipo de error
		    System.out.println("SQLException: " + ex.getMessage());

		}
		return(con);
	}
	
	/**
	 * M�todo que se encarga de retornar la conexi�n para el sistema de Tienda
	 * @param dsn Se recibe como par�metro el nombre del DataSource Name con el cual se establecer� la conexi�n.
	 * @return Se retorna un objeto de tipo conexi�n a la base de datos Tienda, en la cual se insertar� el pedido.
	 */
	public Connection obtenerConexionBDTienda(String dsn){
		
		Connection con = null;
		try {

			 //Class.forName("sybase.jdbc.sqlanywhere.IDriver");
			 //con = DriverManager.getConnection("jdbc:sqlanywhere:dsn="+dsn+";uid=admin;pwd=xxx");//SystemPos
			
			//Cambiamos para la versi�n 12 del driver en teoria no es necesario registrar el driver lo comentamos
			
			DriverManager.registerDriver( (Driver)
					 Class.forName( "sybase.jdbc.sqlanywhere.IDriver" ).newInstance() );
			
			con = DriverManager.getConnection("jdbc:sqlanywhere:dsn="+dsn+";uid=admin;pwd=xxx");//SystemPos
			//con = DriverManager.getConnection("jdbc:sqlanywhere:dns=pixel;uid=admin;pwd=xxx;port=2638;LINKS=tcpip(PORT=2638)");//SystemPos

		} catch (Exception ex) {

		    // Mantener el control sobre el tipo de error
		    System.out.println("SQLException: " + ex.getMessage());

		}
		return(con); 
	}
	
}
