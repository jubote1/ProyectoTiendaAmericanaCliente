package JTable;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {

    public CheckBoxRenderer() {
      setHorizontalAlignment(JLabel.CENTER);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {
      if (isSelected) {
        setBackground(Color.YELLOW);
      } else {
        setBackground(table.getBackground());
      }
      setSelected((value != null && ((Boolean) value).booleanValue()));
      return this;
    }
}
