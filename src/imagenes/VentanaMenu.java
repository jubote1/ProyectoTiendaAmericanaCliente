package imagenes;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import capaControlador.ParametrosProductoCtrl;
import capaModelo.Producto;


import java.awt.Color;
import java.awt.Component;

public class VentanaMenu extends JFrame {

	private JPanel contentPane;
	private JTable tableMenu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaMenu frame = new VentanaMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaMenu() {
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelComandos = new JPanel();
		panelComandos.setBackground(Color.LIGHT_GRAY);
		panelComandos.setBounds(10, 11, 269, 332);
		contentPane.add(panelComandos);
		setExtendedState(MAXIMIZED_BOTH);//otro metodo
		setUndecorated(true);
		
		tableMenu = new JTable();
		tableMenu.setCellSelectionEnabled(true);
		tableMenu.setDefaultRenderer(Object.class, new ButtonRenderer());
		tableMenu.setCellEditor(new ButtonEditor());
		tableMenu.setRowHeight(60);
		JButton btn;
				
		// Creación botones 
		Object[][] botones = new Object[6][6];
		int numBoton = 1;
		for (int i = 0; i<6; i++)
		{
			for (int j = 0; j <6 ; j++)
			{
				btn = new JButton("Menú " + numBoton);
				botones[i][j] = btn;
				numBoton++;
			}
		}
		
		MyTableModel model = new MyTableModel(new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column"
			},botones);
//		tableMenu.setModel(new DefaultTableModel(
//			botones,
//			new String[] {
//				"New column", "New column", "New column", "New column", "New column", "New column"
//			}
//		)
//				
//				)
		tableMenu.setModel(model);
		tableMenu.setDefaultRenderer(JButton.class, new ButtonRenderer());
		tableMenu.setDefaultEditor(JButton.class, new ButtonEditor());
		;
		
				
		tableMenu.setBounds(289, 11, 944, 483);
		contentPane.add(tableMenu);
		
			
		
	}
	
	
}


class ButtonRenderer extends JButton implements TableCellRenderer
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

class ButtonEditor extends AbstractCellEditor implements TableCellEditor
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


class MyTableModel extends AbstractTableModel {
	 
	 /** Nombre de las columnas. */
	 private String[] columnNames;
	 /** Datos. */
	 private Object[][] data;
	 
	 /**
	  * Constructor.
	  * @param columnNames Nombres de las columnas
	  * @param data Datos de la tabla
	  */
	 public MyTableModel(String[] columnNames, Object[][] data) {
	  this.columnNames = columnNames;
	  this.data = data;
	 }
	 
	 @Override
	 public String getColumnName(int column) {
	  // Nombre de las columnas para la cabecera
	  return columnNames[column];
	 }
	 
	 @Override
	 public int getRowCount() {
	  // Devuelve el número de filas
	  return data != null ? data.length : 0;
	 }
	 
	 @Override
	 public int getColumnCount() {
	  // Devuelve el número de columnas
	  return columnNames.length;
	 }
	 
	 /**
	  * Nos devolverá la clase que contiene cada columna,
	  * es necesario para trabajar correctamente con los componentes
	  * que mostraremos en la tabla.
	  */
	 @Override
	 public Class getColumnClass(int columnIndex) {
	  Class clazz = Object.class;
	 
	  Object aux = getValueAt(0, columnIndex);
	  if (aux != null) {
	   clazz = aux.getClass();
	  }
	 
	  return clazz;
	 }
	 
	 @Override
	 public Object getValueAt(int rowIndex, int columnIndex) {
	  // Devuelve el valor que se debe mostrar en la tabla en cada celda
	  return data[rowIndex][columnIndex];
	 }
	 
	    @Override
	 public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	     // Si queremos que la tabla sea editable deberemos establecer estos valores
	     data[rowIndex][columnIndex] = aValue;
	     fireTableCellUpdated(rowIndex, columnIndex);
	    }
	 
	 @Override
	 public boolean isCellEditable(int rowIndex, int columnIndex) {
	  // Permitimos editar todas las celdas de la tabla
	  return true;
	 }
	 
	 /**
	  * Nos servira para limpiar la información de una fila
	  * @param row
	  */
	 public void reset(int row) {
	 
	  for (int i = 0; i < data[row].length - 1; i++) {
	   // Para las columnas con String
	   if (getColumnClass(i) == String.class) {
	    setValueAt("", row, i);
	   } else if(getColumnClass(i) == Boolean.class) {
	    setValueAt(false, row, i);
	   }
	  }
	 
	 }
	 
	}