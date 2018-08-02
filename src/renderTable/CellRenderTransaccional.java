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
import javax.swing.table.TableCellRenderer;

import capaDAO.EstadoDAO;
import capaModelo.Estado;


public class CellRenderTransaccional extends DefaultTableCellRenderer implements TableCellRenderer {

	
	static boolean tipoPedido= false;
	static boolean estado = false;
	static ArrayList<Estado>arregloEstados = EstadoDAO.obtenerTodosEstado();
	
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //establecemos el fondo blanco o vac�o
        setBackground(null);
        //COnstructor de la clase DefaultTableCellRenderer
        Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        Color colorFila = new Color (255, 255, 255);
        for(int i = 0; i< arregloEstados.size();i++)
        {
        	Estado estadoTemp = arregloEstados.get(i);
        	//System.out.println(table.getValueAt(row, 6));
        	if ((Integer.parseInt((String)table.getValueAt(row, 6))== estadoTemp.getIdTipoPedido())&&(Integer.parseInt((String)table.getValueAt(row, 7))== estadoTemp.getIdestado()))
        	{
        		colorFila = new Color(estadoTemp.getColorr(), estadoTemp.getColorg(), estadoTemp.getColorb());
        		setBackground(colorFila);
        		break;
        	}
        }
        
        
        
        return this;
    }

}