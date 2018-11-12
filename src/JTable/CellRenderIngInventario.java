/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JTable;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import capaDAO.EstadoDAO;
import capaModelo.Estado;


public class CellRenderIngInventario extends DefaultTableCellRenderer implements TableCellRenderer {

	
	private final DecimalFormat formatter = new DecimalFormat("#.00");	
	
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //establecemos el fondo blanco o vacío
        setBackground(null);
        //COnstructor de la clase DefaultTableCellRenderer
        Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (column == 5)
        {
        	String valPintar = value.toString();
        	double intValPintar = 0;
        	//Intentamos parsear el contenido a un número, si fallamos es porque hay un error y deberemos de corregir.
        	try
        	{
        		intValPintar = Double.parseDouble(valPintar);
        		value = Double.toString(intValPintar);
        	}catch(Exception e)
        	{
        		value = new String("0");
        		
        	}
        	if (intValPintar == 0)
        	{
        		setBackground(Color.YELLOW);
        	}else
        	{
        		setBackground(Color.GREEN);
        	}
        	table.setValueAt(value, row, column);
        }
        return this;
    }
    



}