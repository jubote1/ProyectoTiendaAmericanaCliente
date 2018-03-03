package interfazGrafica;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.Component;

public class RenderVentanaMenu extends DefaultTableCellRenderer{
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		// TODO Auto-generated method stub
		if (value instanceof JButton) {
			JButton btn = (JButton)value;
			return btn;
		}
		
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}
}
