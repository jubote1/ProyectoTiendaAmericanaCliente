package interfazGrafica;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ButtonRenderer extends JButton implements TableCellRenderer
{

	//CONTRUCTOR
	public ButtonRenderer()
	{
		setOpaque(true);
	}
	@Override
	public Component getTableCellRendererComponent(JTable table , Object obj, boolean selected, boolean focused, int row,
			int col) {
		// Devolvemos el botón tal cual
		  if (obj instanceof JButton) {
		   return (JButton) obj;
		  }
		 
		  return null;
	}
	
}