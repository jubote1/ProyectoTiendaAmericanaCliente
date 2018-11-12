package JTable;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTable;

public class NextCellActioinRetInventarios extends AbstractAction {

    private JTable table;

    public NextCellActioinRetInventarios(JTable table) {
        this.table = table;
    }
    //Definimos la accion a realizar para cuando se da la acción de siguiente en el Jtable
    @Override
    public void actionPerformed(ActionEvent e) {
        int col = table.getSelectedColumn();
        int row = table.getSelectedRow();

        int colCount = table.getColumnCount();
        int rowCount = table.getRowCount();
        
        //Si la columna es la 5 se deberá avanzar
        if(col == 5)
        {
        	//aumentamos la fila
	        row++;
	        //Si la fila es la mayor a la última iniciamos en la primera
	        if (row >= rowCount) {
	            row = 0;
	        }
	        //Realizamos la seleccion de la fila
	        table.getSelectionModel().setSelectionInterval(row, row);
	        table.getColumnModel().getSelectionModel().setSelectionInterval(col, col);
	        //Limpiamos el contenido de la celda
	        table.setValueAt("", row, col);
	        //Habilitamos la celda para modificación
	        table.editCellAt(row, col);	
        }
    }

}
