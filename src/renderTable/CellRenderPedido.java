/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package renderTable;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import capaDAO.EstadoDAO;
import capaModelo.Estado;
import interfazGrafica.VentPedTomarPedidos;


public class CellRenderPedido extends DefaultTableCellRenderer implements TableCellRenderer {

	 final Color colorFila1 = new Color (255, 255, 0);
     final Color colorFila2 = new Color (255, 0, 255);
     final Color colorFila3 = new Color (0, 255, 255);
     public static int colorDeta = 0;
     static int idDetalleAnterior = 0;
    	
//Debemos definir 3 colores para irlos graduando y alternando en el pintado de los iddetallepedidomaster
	
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
    	//establecemos el fondo blanco o vacío
        setBackground(null);
        //COnstructor de la clase DefaultTableCellRenderer
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        int filas = modelo.getRowCount();
        Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        int idDetalleMasTmp = 0;
        int idDetalleActual = 0;
        try{
        	idDetalleMasTmp = Integer.parseInt((String)table.getValueAt(row, 4));
        	idDetalleActual = Integer.parseInt((String)table.getValueAt(row, 0));
        	
        }catch(Exception e)
        {
        	System.out.println("Error casteando el valor");
        }
        
        if((idDetalleMasTmp  == 0)&&(idDetalleActual != idDetalleAnterior))
        {
        	//idDetPedMasterAct = idDetalleTmp;
        	colorDeta++;
        	if(colorDeta == 4)
        	{
        		colorDeta = 1;
        	}
        }
       
        	if(colorDeta == 1)
            {
            	setBackground(colorFila1);
            	
            }else if(colorDeta == 2)
            {
            	setBackground(colorFila2);
            	
            }else if(colorDeta == 3)
            {
            	setBackground(colorFila3);
            	
            }
           	idDetalleAnterior = idDetalleActual;
        return this;
    }

}