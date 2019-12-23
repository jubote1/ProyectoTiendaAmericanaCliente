package capaControlador;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.mail.Session;

import com.digitalpersona.onetouch.DPFPFeatureSet;

import capaDAO.EmpleadoBiometriaDAO;
import capaDAO.EmpleadoEventoDAO;
import capaModelo.EmpleadoEvento;
import capaModelo.Parametro;
import capaModelo.Usuario;
import interfazGrafica.Sesion;

public class BiometriaCtrl {
	
	
	private boolean auditoria;
	public BiometriaCtrl(boolean auditoria)
	{
		this.auditoria = auditoria;
	}
	public boolean InsertarBiometriaEmpleado(int idEmpleado, ByteArrayInputStream datosHuella, Integer sizeHuella )
	{
		ParametrosCtrl parCtrl = new ParametrosCtrl(auditoria);
		Parametro parBase = parCtrl.obtenerParametro("BDGENERAL");
		boolean respuesta = EmpleadoBiometriaDAO.InsertarBiometriaEmpleado(idEmpleado, datosHuella, sizeHuella, parBase.getValorTexto(), auditoria);
		return(respuesta);
	}
	
	public String verificacionBiometria (ByteArrayInputStream datosHuella) {
		ParametrosCtrl parCtrl = new ParametrosCtrl(auditoria);
		Parametro parBase = parCtrl.obtenerParametro("BDGENERAL");
		String respuesta = EmpleadoBiometriaDAO.verificacionBiometria(datosHuella, parBase.getValorTexto(), auditoria);
		return(respuesta);
	}
	
	public Usuario identificarBiometriaEmpleado( DPFPFeatureSet featureserificacion, String biometriaRemota) 
	{
		ParametrosCtrl parCtrl = new ParametrosCtrl(auditoria);
		Parametro parBase;
		String strConexion = "";
		if(biometriaRemota.equals(new String("S")))
		{
			parBase = parCtrl.obtenerParametro("BDGENERAL");
			strConexion = parBase.getValorTexto();
		}
		else
		{
			parBase = parCtrl.obtenerParametro("BDGENERALLOCAL");
			strConexion = Sesion.getHost() + parBase.getValorTexto();
		}
		Usuario usuario = new Usuario(0, "", "", "", 0,"", false);
		try
		{
			usuario = EmpleadoBiometriaDAO.identificarBiometriaEmpleado(strConexion,featureserificacion, auditoria);
		}catch(Exception e)
		{
			System.out.println(e.toString());
		}
		return(usuario);
	}
	
	public Usuario identificarSinBiometriaEmpleado( Long cedulaEmpleado, String biometriaRemota) 
	{
		ParametrosCtrl parCtrl = new ParametrosCtrl(auditoria);
		Parametro parBase;
		String strConexion = "";
		if(biometriaRemota.equals(new String("S")))
		{
			parBase = parCtrl.obtenerParametro("BDGENERAL");
			strConexion = parBase.getValorTexto();
		}
		else
		{
			parBase = parCtrl.obtenerParametro("BDGENERALLOCAL");
			strConexion = Sesion.getHost() + parBase.getValorTexto();
		}
		Usuario usuario = new Usuario(0, "", "", "", 0,"", false);
		try
		{
			usuario = EmpleadoBiometriaDAO.identificarSinBiometriaEmpleado(cedulaEmpleado,strConexion, auditoria);
		}catch(Exception e)
		{
			System.out.println(e.toString());
		}
		return(usuario);
	}
	
	public boolean estaEnroladoEmpleado(int idEmpleado)
	{
		ParametrosCtrl parCtrl = new ParametrosCtrl(auditoria);
		Parametro parBase = parCtrl.obtenerParametro("BDGENERAL");
		boolean respuesta = EmpleadoBiometriaDAO.estaEnroladoEmpleado(idEmpleado, parBase.getValorTexto(), auditoria);
		return(respuesta);
	}
	
	public boolean eliminarEnroladoEmpleado(int idEmpleado)
	{
		ParametrosCtrl parCtrl = new ParametrosCtrl(auditoria);
		Parametro parBase = parCtrl.obtenerParametro("BDGENERAL");
		boolean respuesta = EmpleadoBiometriaDAO.eliminarEnroladoEmpleado(idEmpleado, parBase.getValorTexto(), auditoria);
		return(respuesta);
	}
	
	public String consultarEstadoRegistroEvento(int idEmpleado, String fecha, Calendar calendarioActual, String biometriaRemota )
	{
		ParametrosCtrl parCtrl = new ParametrosCtrl(auditoria);
		Parametro parBase;
		String strConexion = "";
		String resultado = "VACIO";
		if(biometriaRemota.equals(new String("S")))
		{
			parBase = parCtrl.obtenerParametro("BDGENERAL");
			strConexion = parBase.getValorTexto();
		}
		else
		{
			parBase = parCtrl.obtenerParametro("BDGENERALLOCAL");
			strConexion = Sesion.getHost() + parBase.getValorTexto();
		}
		//Realizamos rediseño del método y retornamos un ArrayList con los eventos y de allí se revisará que hacer
		ArrayList<EmpleadoEvento> eventosEmpleado = EmpleadoEventoDAO.recuperarEventosEmpleado(idEmpleado, fecha,  strConexion, auditoria);
		//De este punto en adelante está la lógica para saber que retornamos.
		//Calculamos la hora para manejar una validación posterior
		int horaActual = calendarioActual.get(Calendar.HOUR_OF_DAY);
		//Si no hay evento y la hora actual es menor a 3 se toman ciertas revisiones
		if(eventosEmpleado.size() == 0 && horaActual <= 3)
		{
			//Calculamos la fecha anterior para luego llamar el evento con el día anterior
			Date datFechaAnterior;
			String fechaAnterior = "";
			calendarioActual.add(Calendar.DAY_OF_YEAR, -1);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			datFechaAnterior = calendarioActual.getTime();
			fechaAnterior = dateFormat.format(datFechaAnterior);
			ArrayList<EmpleadoEvento> eventosEmpleadoAnterior = EmpleadoEventoDAO.recuperarEventosEmpleado(idEmpleado, fechaAnterior, strConexion, auditoria);
			//Validamos si solo hay VARIOS EVENTOS y el último es un ingreso
			if(eventosEmpleadoAnterior.size() >=1 && eventosEmpleadoAnterior.get(eventosEmpleadoAnterior.size() -1).getTipoEvento().equals(new String("INGRESO")))
			{
				resultado = "SALIDA-ANTERIOR";
				return(resultado);
			}
		}
		//Validamos si hay 2 o mas eventos es porque se repetió, por lo que retornamos la palabra anterior para que el front lo detecte
		if(eventosEmpleado.size() >= 2)
		{
			resultado = eventosEmpleado.get(eventosEmpleado.size()-1).getTipoEvento() + "-REPETIDO";
			
		}else if(eventosEmpleado.size() == 1 )
		{
			resultado = eventosEmpleado.get(eventosEmpleado.size()-1).getTipoEvento();
			
		}
		return(resultado);
	}
	
	public boolean InsertarEventoRegistroEmpleado(EmpleadoEvento empEvento, String biometriaRemota )
	{
		ParametrosCtrl parCtrl = new ParametrosCtrl(auditoria);
		Parametro parBase;
		String strConexion = "";
		if(biometriaRemota.equals(new String("S")))
		{
			parBase = parCtrl.obtenerParametro("BDGENERAL");
			strConexion = parBase.getValorTexto();
		}
		else
		{
			parBase = parCtrl.obtenerParametro("BDGENERALLOCAL");
			strConexion = Sesion.getHost() + parBase.getValorTexto();
		}
		boolean respuesta = EmpleadoEventoDAO.InsertarEventoRegistroEmpleado(empEvento, strConexion ,auditoria);
		return(respuesta);
	}
	
	public ArrayList<String[]> obtenerEntradasSalidasEmpleados()
	{
		//Instanciamos la respuesta ArrayList
		ArrayList respuestaReporte = new ArrayList();
		//Modificamos para verificar a que base de datos se debe pagar
		ParametrosCtrl parCtrl = new ParametrosCtrl(auditoria);
		Parametro parBase;
		//Obtenemos como está funcionando la biometria remota
		//Capturamos el valor de la biometria remota
        Parametro parametroAud = parCtrl.obtenerParametro("CONFBIOMEREMOTA");
      	//Extraemos el valor del campo de ValorTexto
      	String biometriaRemota = parametroAud.getValorTexto();
      	//Validamos si no se pudo recuperar valor lo ponemos por defecto en S
      	if(biometriaRemota.equals(new String("")))
      	{
      		biometriaRemota = "S";
      	}
		
		String strConexion = "";
		if(biometriaRemota.equals(new String("S")))
		{
			parBase = parCtrl.obtenerParametro("BDGENERAL");
			strConexion = parBase.getValorTexto();
		}
		else
		{
			parBase = parCtrl.obtenerParametro("BDGENERALLOCAL");
			strConexion = Sesion.getHost() + parBase.getValorTexto();
		}
		//Debemos obtener la fecha con ciertas y la tienda
		OperacionesTiendaCtrl operTiendaCtrl = new OperacionesTiendaCtrl(auditoria);
		int idTienda = operTiendaCtrl.obtenerIdTienda();
		Calendar calendarioActual = Calendar.getInstance();
		Date datFechaReporte = new Date();
		String fechaReporte = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormatHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int horaActual = calendarioActual.get(Calendar.HOUR_OF_DAY);
		//Si la hora es menor a las 3, es porque el reporte se está generando en cambio de día, por lo que mostraremos el día anterior
		if (horaActual < 3)
		{
			//Restamos en 1 el día y lo igualamos al Date de fecha de reprote
			calendarioActual.add(Calendar.DAY_OF_YEAR, -1);
			datFechaReporte = calendarioActual.getTime();
		}
		//Formateamos la fecha del reporte en String
		fechaReporte = dateFormat.format(datFechaReporte);
		//Recuperamos el arreglo con los eventos deberemos reprocesarlos para tener la vista qeu requerimos
		ArrayList<EmpleadoEvento>  repEntradasSalidas = EmpleadoEventoDAO.obtenerEntradasSalidasEmpleadosEventos(idTienda, fechaReporte, strConexion, auditoria);
		//Variables necesarias para el recorrido
		EmpleadoEvento eventoTemp;
		//Arreglo donde iremos dejando cada fila
		String[] filaTemp = new String[5];
		//Variables que nos permitiran saber si hubo error en la conversión de las fechas
		boolean errorInicial = false;
		boolean errorFinal = false;
		//Variables qeu nos permitiran saber en que punto vamos de la formación del registro
		boolean ingreso = false;
		//Salida empezará prendido dado que iniciamos con uno nuevo
		boolean salida = true;
		for(int i = 0; i < repEntradasSalidas.size(); i++)
		{
			//Retomamos el evento que vamos a procesar
			eventoTemp = repEntradasSalidas.get(i);
			//Hacemos la verificación de si el evento es de ingreso o de salida
			if(eventoTemp.getTipoEvento().equals(new String("INGRESO")))
			{
				//Esto quiere decir que solo hay un ingreso por lo que llenamos el arreglo
				if(ingreso)
				{
					filaTemp[3] = "0";
					filaTemp[4] = "0";
					respuestaReporte.add(filaTemp);
					filaTemp = new String[5];
					filaTemp[0] = eventoTemp.getNombreEmpleado();
					filaTemp[1] = eventoTemp.getFecha();
					filaTemp[2] = eventoTemp.getFechaHoraLog();
				}if(salida)
				{
					filaTemp = new String[5];
					filaTemp[0] = eventoTemp.getNombreEmpleado();
					filaTemp[1] = eventoTemp.getFecha();
					filaTemp[2] = eventoTemp.getFechaHoraLog();
				}
				ingreso = true;
				salida = false;
			}else if(eventoTemp.getTipoEvento().equals(new String("SALIDA")))
			{
				filaTemp[3] = eventoTemp.getFechaHoraLog();
				//Hacer la resta de tiempos para lo cual formateamos las fechas
				Date fechaFinal = new Date(), fechaInicial = new Date();
				double horas = 0;
				//Intentamos la conversión de las fechas
				try
				{
					fechaInicial=dateFormatHora.parse(filaTemp[2]);
				}catch(Exception e)
				{
					errorInicial = true;
				}
				try
				{
					fechaFinal=dateFormatHora.parse(filaTemp[3]);
				}catch(Exception e)
				{
					errorFinal = true;
				}
		        if(!errorInicial && !errorFinal)
		        {
		        	  horas = ((fechaFinal.getTime()-fechaInicial.getTime())/1000);
		        	  horas =(horas)/3600;
		        }
		        DecimalFormat df = new DecimalFormat("#.00");
		        filaTemp[4] = df.format(horas);
				respuestaReporte.add(filaTemp);
				//volvemos a iniciarlizar las banderas de inicio y final
				errorInicial = false;
				errorFinal = false;
				//Prendemos la variable de salida
				salida = true;
				ingreso = false;
			}
		}
		//A la salida del for damos una revisa si no hay salida entonces se agrega al arreglo del resultado
		if(ingreso && !salida)
		{
			filaTemp[3] = "0";
			filaTemp[4] = "0";
			respuestaReporte.add(filaTemp);
		}
		return(respuestaReporte);
	}

}
