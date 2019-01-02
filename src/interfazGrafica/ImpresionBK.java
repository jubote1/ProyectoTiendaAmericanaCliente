package interfazGrafica;

import java.io.PrintWriter;
import javax.print.*;

public class ImpresionBK {

	
	public void imprimirFactura(String impresion)
	{

		 //Cogemos el servicio de impresi�n por defecto (impresora por defecto)
		   PrintService service = PrintServiceLookup.lookupDefaultPrintService();
		   //Le decimos el tipo de datos que vamos a enviar a la impresora
		   //Tipo: bytes Subtipo: autodetectado
		   DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		   DocFlavor cutflavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		   //Creamos un trabajo de impresi�n
		   DocPrintJob pj = service.createPrintJob(), jobcut = service.createPrintJob();
		   //Nuestro trabajo de impresi�n env�a una cadena de texto
		   String ss=new String(impresion);
		   byte[] bytes;
		   byte[] cutbytes; 
		   //Transformamos el texto a bytes que es lo que soporta la impresora
		   bytes=ss.getBytes();
		   cutbytes = new byte[] { 0x1B, 'm'}; 
		   //Creamos un documento (Como si fuese una hoja de Word para imprimir)
		   Doc doc=new SimpleDoc(bytes,flavor,null);
		   Doc cutdoc = new SimpleDoc(cutbytes, cutflavor, null);
		   //Obligado coger la excepci�n PrintException
		   try {
		     //Mandamos a impremir el documento
		     pj.print(doc, null);
		     jobcut.print(cutdoc, null);
		   }
		   catch (PrintException e) {
		     System.out.println("Error al imprimir: "+e.getMessage());
		   }
	}
}
