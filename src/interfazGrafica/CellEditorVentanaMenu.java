package interfazGrafica;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class CellEditorVentanaMenu extends AbstractCellEditor implements TableCellEditor {

	 JComponent component = new JButton();
	 
	 public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
		      int rowIndex, int vColIndex) {

//		    ((JTextField) component).setText((String) value);
//
//		    return component;
		    
		 	JButton btn = new JButton("");
		    if (value instanceof JButton) {
				btn = (JButton)value;
				System.out.println("hola 2");
				return btn;
			}
		    
		    return btn;
		  }

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void actionPerformed(ActionEvent e) { 
        // Lo que hacer al pinchar en el botón. 
        JOptionPane.showMessageDialog(null, "Será Borrada");        
    } 
}
