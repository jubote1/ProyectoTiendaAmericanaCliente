/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JTable;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import capaControlador.PedidoCtrl;
import capaDAO.EstadoDAO;
import capaModelo.Estado;
import interfazGrafica.PrincipalLogueo;


public class CellRenderTransaccional extends DefaultTableCellRenderer implements TableCellRenderer {

	
	static boolean tipoPedido= false;
	static boolean estado = false;
	static ArrayList<Estado>arregloEstados = (new PedidoCtrl(PrincipalLogueo.habilitaAuditoria)).obtenerTodosEstado();
	
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	 //COnstructor de la clase DefaultTableCellRenderer
        Component renderer = (Component) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    	//establecemos el fondo blanco o vacío
        setBackground(null);
        Color colorFila = new Color (255, 255, 255);
        for(int i = 0; i< arregloEstados.size();i++)
        {
        	Estado estadoTemp = arregloEstados.get(i);
        	//System.out.println(table.getValueAt(row, 6));
        	if (((long)table.getValueAt(row, 7)== estadoTemp.getIdTipoPedido())&&((long)table.getValueAt(row, 8)== estadoTemp.getIdestado()))
        	{
        		colorFila = new Color(estadoTemp.getColorr(), estadoTemp.getColorg(), estadoTemp.getColorb());
        		setBackground(colorFila);
        		break;
        	}
        }

        return(renderer);

    }
	
  
}