/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JTable;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import capaDAO.EstadoDAO;
import capaModelo.Estado;


public class CellRenderIngVarianzaNA extends DefaultTableCellRenderer implements TableCellRenderer {

	
		
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //establecemos el fondo blanco o vacío
        setBackground(null);
        //COnstructor de la clase DefaultTableCellRenderer
        Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      //Se realiza la validación al momento de pintar que sea la columna para editar y se verifica que sea un valor de tipo doble
        if(column == 5)
        {
        	String valPintar = value.toString();
        	double intValPintar = 0;
        	//Intentamos parsear el contenido a un número, si fallamos es porque hay un error y deberemos de corregir, en cuyo
        	//caso mostraremos el valor de cero.
        	try
        	{
        		intValPintar = Double.parseDouble(valPintar);
        		value = Double.toString(intValPintar);
        		
        	}catch(Exception e)
        	{
        		value = new String("0");
        		
        	}	
        	table.setValueAt(value, row, column);
        }
        return this;
    }
    
    

}