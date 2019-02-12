package interfazGrafica;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor
{
	/** Componente que estamos editando. */
	 private Component currentValue;
	 
	 @Override
	 public Component getTableCellEditorComponent(final JTable table, Object value, boolean isSelected, final int row, int column) {
	 
	  JButton button = null;
	 
	  if (value instanceof JButton) {
	   button = (JButton) value;
	   // Action que permite "limpiar" los valores de una fila
	   button.setAction(new AbstractAction(((JButton) value).getText()) {
	 
	    @Override
	    public void actionPerformed(ActionEvent e) {
	     ((MyTableModel) table.getModel()).reset(row);
	 
	    }
	   });
	  }
	 
	  currentValue = button;
	 
	  return button;
	 }
	 
	 @Override
	 public Object getCellEditorValue() {
	  return currentValue;
	 }
}
