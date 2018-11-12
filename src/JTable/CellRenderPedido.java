/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JTable;

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
     
    	
//Debemos definir 3 colores para irlos graduando y alternando en el pintado de los iddetallepedidomaster
	
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        
    	//establecemos el fondo blanco o vacío
        setBackground(null);
        //COnstructor de la clase DefaultTableCellRenderer
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        //Se realiza una validación inicial para saber si el detalle pedido esta anulado para pintar de rojo toda la fila
        if(((String)table.getValueAt(row, 6)).equals(new String("A")))
        {
        	setBackground(Color.RED);
        	setForeground(Color.WHITE);
        	return this;
        }
        
        //Realizamos el cálculo del residuo de la división por 3 del valor de la columna 7
        int contDetPedido = Integer.parseInt((String)table.getValueAt(row, 7));
        int resDetPedido = contDetPedido % 3;
        if(resDetPedido == 1)
        {
        	setBackground(colorFila1);
        	setForeground(Color.BLACK);
        }else if(resDetPedido == 2)
        {
        	setBackground(colorFila2);
        	setForeground(Color.BLACK);
        }else if(resDetPedido == 0)
        {
        	setBackground(colorFila3);
        	setForeground(Color.BLACK);
        }
        return this;
    }

}