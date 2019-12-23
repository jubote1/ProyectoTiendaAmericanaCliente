/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JTable;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import capaControlador.PedidoCtrl;
import capaDAO.EstadoDAO;
import capaModelo.Estado;
import interfazGrafica.PrincipalLogueo;
import interfazGrafica.Sesion;


/**
 * Clase creada para manejar el renderizado de la columna de tiempos en la ventana Transaccional de pedidos, con el objetivo
 * de pintar y dar una impresi�n de como est� los tiempos
 * @author 57314
 *
 */
public class CellRenderTransaccionalTiempos extends DefaultTableCellRenderer implements TableCellRenderer {

	
	static boolean tipoPedido= false;
	static boolean estado = false;
	static ArrayList<Estado>arregloEstados = (new PedidoCtrl(PrincipalLogueo.habilitaAuditoria)).obtenerTodosEstado();
	
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	 //COnstructor de la clase DefaultTableCellRenderer
        Component renderer = (Component) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    	//establecemos el fondo blanco o vac�o
        setBackground(null);
        Color colorFila = new Color (255, 255, 255);
        //Tomamos el valor del tiempo prometido que es base para todo
        int tiempoPrometido = Integer.parseInt((String)table.getValueAt(row, 9));
        //Tomamos el string con el tiempo del pedido
        String strTiempoPedido = (String)table.getValueAt(row, 10);
        //Realizamos un StringTokenizer para con : separar el pedido
        StringTokenizer strTokens = new StringTokenizer(strTiempoPedido, ":");
        String strEstPedido = "";
        String strTiempoPed = "";
        int tiempoPedido = 0;
        while(strTokens.hasMoreElements())
        {
        	strEstPedido = strTokens.nextToken();
        	strTiempoPed = strTokens.nextToken();
        	try {
        		strTiempoPed = strTiempoPed.replace("Act", "");
        		tiempoPedido = Integer.parseInt(strTiempoPed.trim());
        		
        	}catch(Exception e)
        	{
        		tiempoPedido = 0;
        	}
        	break;
        }
        int diferenciaTiempo = tiempoPedido - tiempoPrometido;
        System.out.println("tiempo pedido " + tiempoPedido + " tiempo prometido " + tiempoPrometido + " total " + diferenciaTiempo);
        //Hacemos la verificaci�n si el pedido ya est� finalizado, dado en que este punto TOTAL aparece en may�scula
        if(strEstPedido.trim().equals(new String("TOT")))
        {
        	if(diferenciaTiempo <= 20)
        	{
        		setBackground(Color.green);
        	}else if(diferenciaTiempo > 20 && diferenciaTiempo <= 30)
        	{
        		setBackground(Color.yellow);
        	}else
        	{
        		setBackground(Color.red);
        	}
        //Fijamos el comportamiento cuando el pedido no est� finalizado	
        }else if(strEstPedido.trim().equals(new String("Tot")))
        {
        	//Con base en el significado y en que el c�lculo es Tiempo Pedido � tiempo prometido,  el comportamiento es desde menos 
        	//infinito hasta -20 Color verde. De -19 hasta -10 es amarillo y mayor a este si es rojo, pues no deber�a haber un pedido 
        	//con 10 minutos para ser entregado en tienda.
        	if((long)table.getValueAt(row, 7) == Sesion.getEstEmpDom())
        	{
        		if(diferenciaTiempo >  -10)
            	{
            		setBackground(Color.red);
            	}else if(diferenciaTiempo > -20 && diferenciaTiempo < -10)
            	{
            		setBackground(Color.yellow);
            	}else if(diferenciaTiempo <= -20)
            	{
            		setBackground(Color.green);
            	}
        		//Con base en el significado y en que el c�lculo es Tiempo Pedido � tiempo prometido, en verde hasta m�ximo 20 minutos despu�s, 
        		//entre 20 y 30 sale amarillo y m�s de 30 rojo.
        	}else if((long)table.getValueAt(row, 7) == Sesion.getEstEnRutaDom())
        	{
        		if(diferenciaTiempo >  30)
            	{
            		setBackground(Color.red);
            	}else if(diferenciaTiempo > 20 && diferenciaTiempo <= 30)
            	{
            		setBackground(Color.yellow);
            	}else if(diferenciaTiempo <= 20)
            	{ 
            		setBackground(Color.green);
            	}
        	}
        }
        return(renderer);

    }
	
  
}