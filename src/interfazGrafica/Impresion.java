package interfazGrafica;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.print.*;

import capaDAO.GeneralDAO;
import capaDAO.TiendaDAO;
import capaModelo.Correo;
import capaModelo.Tienda;
import utilidades.ControladorEnvioCorreo;



//La clase debe de implementar la impresión implements Printable

//clase pública que se ejecuta donde debe de estar el main que 
// llama a laotra clase.
public class Impresion
{
	
   public static void main (String impresion)
   {
	   String impresoraPrincipal = "Caja";
	   
	 //Cogemos el servicio de impresión por defecto (impresora por defecto)
	   PrintService service = PrintServiceLookup.lookupDefaultPrintService();
	   //Le decimos el tipo de datos que vamos a enviar a la impresora
	   //Tipo: bytes Subtipo: autodetectado
	   DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
	   //Creamos un trabajo de impresión
	   DocPrintJob pj = service.createPrintJob();
	   //Nuestro trabajo de impresión envía una cadena de texto
	   String ss=new String(impresion);
	   byte[] bytes; 
	   //Transformamos el texto a bytes que es lo que soporta la impresora
	   bytes=ss.getBytes();
	   //Creamos un documento (Como si fuese una hoja de Word para imprimir)
	   Doc doc=new SimpleDoc(bytes,flavor,null);
	   //Obligado coger la excepción PrintException
	   try {
	     //Mandamos a impremir el documento
		   
	     //pj.print(doc, null);
	     cortehoja.printer(impresion, impresoraPrincipal);
	   }
	   //catch (PrintException e) {
		   catch (Exception e) {
	     System.out.println("Error al imprimir: "+e.getMessage());
	     ArrayList correos = GeneralDAO.obtenerCorreosParametro("ERRORIMPRESION", false);
		 Tienda tienda = TiendaDAO.obtenerTienda(false);
		 Date fecha = new Date();
		 Correo correo = new Correo();
		 correo.setAsunto("ERROR IMPRESI�N TIENDA " + tienda.getNombretienda() + " " + fecha.toString());
		 correo.setContrasena("Pizzaamericana2017");
		 correo.setUsuarioCorreo("alertaspizzaamericana@gmail.com");
		 correo.setMensaje("En este momento existen problemas de impresi�n en la tienda " + tienda.getNombretienda() + "\n" + e.toString());
		 ControladorEnvioCorreo contro = new ControladorEnvioCorreo(correo, correos);
		 contro.enviarCorreo();
	   }
	      
	   
	   
//	   try {
//		 //Cuando se cambie el m�todo a cortecaja se debe quitar esto
//		cortehoja.printer("", impresoraPrincipal);
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (PrinterException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
	   
   }
   
   
}