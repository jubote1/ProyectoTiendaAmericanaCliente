package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import capaControlador.ParametrosProductoCtrl;
import capaModelo.Impuesto;
import capaModelo.ItemInventario;

public class VentCRUDItemInventario extends JFrame {

	private JPanel contentPane;
	private JTextField jTextIDItem;
	private JTextField jTextNombre;
	private JTextField jTextUnidadMedida;
	private JScrollPane scrollPane;
	private JTable jTableItemInventario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentCRUDItemInventario frame = new VentCRUDItemInventario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Método que se encarga de retornar los menus agrupadores y de pintarlos en el Jtable correspondiente la información
	 * retornada de base de datos.
	 */
	public DefaultTableModel pintarItemInventario()
	{
		Object[] columnsName = new Object[3];
        
        columnsName[0] = "Id ";
        columnsName[1] = "Nombre Item";
        columnsName[2] = "Unidad Medida";
        ParametrosProductoCtrl par = new ParametrosProductoCtrl();
		ArrayList<Object> items = par.obtenerItemsInventarios();
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < items.size();y++)
		{
			String[] fila =(String[]) items.get(y);
			System.out.println(fila);
			modelo.addRow(fila);
		}
		return(modelo);
		
	}
	
	/**
	 * Create the frame.
	 * Se documentan todas las acciones  a seguir cuando se instancia el frame para el CRUD de impuestor.
	 */
	public VentCRUDItemInventario() {
		setTitle("MAESTRO DE ITEMS DE INVENTARIOS");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 773, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
			
		JPanel panelDatos = new JPanel();
		panelDatos.setBounds(22, 11, 699, 125);
		contentPane.add(panelDatos);
		panelDatos.setLayout(null);
		
		JLabel lblItem = new JLabel("Id Item");
		lblItem.setBounds(29, 11, 93, 14);
		panelDatos.add(lblItem);
		
		JLabel lblUnidadMedida = new JLabel("Unida Medida");
		lblUnidadMedida.setBounds(29, 78, 93, 14);
		panelDatos.add(lblUnidadMedida);
		
		JLabel lblNombreItem = new JLabel("Nombre Item");
		lblNombreItem.setBounds(29, 46, 114, 14);
		panelDatos.add(lblNombreItem);
		
		jTextUnidadMedida = new JTextField();
		jTextUnidadMedida.setBounds(130, 75, 164, 20);
		panelDatos.add(jTextUnidadMedida);
		jTextUnidadMedida.setColumns(100);
		
		jTextNombre = new JTextField();
		jTextNombre.setBounds(132, 43, 162, 20);
		panelDatos.add(jTextNombre);
		jTextNombre.setColumns(50);
		
		jTextIDItem = new JTextField();
		jTextIDItem.setBounds(132, 8, 86, 20);
		panelDatos.add(jTextIDItem);
		jTextIDItem.setEnabled(false);
		jTextIDItem.setColumns(10);
		
		//Se crea Panel que  contendrá el Jtable y los botones
		JPanel panelJtable = new JPanel();
		panelJtable.setBounds(20, 147, 701, 167);
		contentPane.add(panelJtable);
		panelJtable.setLayout(null);
		// Instanciamos el jtable
		jTableItemInventario = new JTable();
		jTableItemInventario.setForeground(Color.black);
		jTableItemInventario.setBounds(52, 25, 512, 58);
		panelJtable.add(jTableItemInventario);
		jTableItemInventario.setBorder(new LineBorder(new Color(0, 0, 0)));
		jTableItemInventario.setBackground(Color.WHITE);
		DefaultTableModel modelo = pintarItemInventario();
		this.jTableItemInventario.setModel(modelo);
		//Adicionar manejo para el evento de seleccion
		
		
		
		//Adicionamos los botones para las acciones del GRID
		JButton btnInsertar = new JButton("Insertar");
		/**
		 * Método que implementará la acción cuando se de click sobre el botón Insertar
		 */
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Se pulso botón para adicionar un nuevo Menú Agrupador
				validarDatos();
				String nombreItem = jTextNombre.getText();
				String unidadMedida = jTextUnidadMedida.getText();
				ItemInventario itemNuevo = new ItemInventario(0,nombreItem, unidadMedida); 
				ParametrosProductoCtrl parCtrl = new ParametrosProductoCtrl();
				int idItem = parCtrl.insertarItemInventario(itemNuevo);
				DefaultTableModel modelo = pintarItemInventario();
				jTableItemInventario.setModel(modelo);
				//Limpiamos el contenido de los campos
				
			
			}
		});
		btnInsertar.setBounds(52, 133, 89, 23);
		panelJtable.add(btnInsertar);
		JButton btnEliminar = new JButton("Eliminar");
		//Evento para definir las acciones para eliminar
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int filaSeleccionada = jTableItemInventario.getSelectedRow();
				//Hacemos la validación para decidir si se elimina o no
				String itemEliminar = (String) jTableItemInventario.getValueAt(filaSeleccionada, 1);
				int idItem = Integer.parseInt((String)jTableItemInventario.getValueAt(filaSeleccionada, 0));
				JOptionPane.showMessageDialog(null, "Esta seguro que se desea eliminar el item Inventario " +  itemEliminar , "Eliminacion Item Inventario ", JOptionPane.YES_NO_OPTION);
				ParametrosProductoCtrl parEliminar = new ParametrosProductoCtrl();
				parEliminar.eliminarItemInventario(idItem);
				DefaultTableModel modelo = pintarItemInventario();
				jTableItemInventario.setModel(modelo);
				jTextNombre.setText("");
				jTextUnidadMedida.setText("");
			}
		});
		btnEliminar.setBounds(175, 133, 89, 23);
		panelJtable.add(btnEliminar);
		
		JButton btnEditar = new JButton("Editar");
		
		btnEditar.setBounds(298, 133, 89, 23);
		panelJtable.add(btnEditar);
		
		JButton btnGrabarEdicion = new JButton("Grabar Edicion");
		
		btnGrabarEdicion.setBounds(427, 133, 123, 23);
		panelJtable.add(btnGrabarEdicion);
		btnGrabarEdicion.setEnabled(false);
		jTextNombre.setText("");
		jTextUnidadMedida.setText("");
		
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = jTableItemInventario.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar algún Item Inventario para editar " , "No ha Seleccionado para edición ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				filaSeleccionada = jTableItemInventario.getSelectedRow();
				jTextIDItem.setText((String)jTableItemInventario.getValueAt(filaSeleccionada, 0));
				jTextNombre.setText((String)jTableItemInventario.getValueAt(filaSeleccionada, 1));
				jTextUnidadMedida.setText((String)jTableItemInventario.getValueAt(filaSeleccionada, 2));
				btnEliminar.setEnabled(false);
				btnInsertar.setEnabled(false);
				btnGrabarEdicion.setEnabled(true);
			}
		});
		
		btnGrabarEdicion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean validar = validarDatos();
				if (validar)
				{
					ItemInventario itemEditado = new ItemInventario(Integer.parseInt(jTextIDItem.getText()),jTextNombre.getText(),jTextUnidadMedida.getText()); 
					ParametrosProductoCtrl parCtrl = new ParametrosProductoCtrl();
					boolean respuesta = parCtrl.editarItemInventario(itemEditado);
					if (respuesta)
					{
						JOptionPane.showMessageDialog(null, "Se ha editado correctamente el registro " , "Confirmación Edición", JOptionPane.OK_OPTION);
						DefaultTableModel modelo = pintarItemInventario();
						jTableItemInventario.setModel(modelo);
						jTextNombre.setText("");
						jTextUnidadMedida.setText("");
						jTextIDItem.setText("");
						btnEliminar.setEnabled(true);
						btnInsertar.setEnabled(true);
						btnGrabarEdicion.setEnabled(false);
					}
				}
				
			}
		});
		
	}
	
	
public boolean validarDatos()
{
	String nombreItem = jTextNombre.getText();
	String unidadMedida = jTextUnidadMedida.getText();
	if(nombreItem == "")
	{
		JOptionPane.showMessageDialog(null, "Valor del campo NOMBRE ITEM es necesario", "Falta Información", JOptionPane.ERROR_MESSAGE);
		return(false);
	}
	if(unidadMedida == "")
	{
		JOptionPane.showMessageDialog(null, "Valor del campo UNIDAD MEDIDA  es necesario", "Falta Información", JOptionPane.ERROR_MESSAGE);
		return(false);
	}
	
	return(true);
}

}
