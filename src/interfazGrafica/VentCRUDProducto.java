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
import capaModelo.ItemInventario;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JScrollBar;

public class VentCRUDProducto extends JFrame {

	private JPanel contentPane;
	private JTextField jTextIDProducto;
	private JTextField jTextDescripcion;
	private JTextField jTextImpresion;
	private JScrollPane scrollPane;
	private JTable jTableItemInventario;
	private JTextField textPrecio1;
	private JTextField textPrecio2;
	private JTextField textPrecio3;
	private JTextField textPrecio4;
	private JTextField textPrecio5;
	private JTextField textPrecio6;
	private JTextField textPrecio7;
	private JTextField textPrecio8;
	private JTextField textPrecio9;
	private JTextField textPrecio10;
	private JTable tableImpuestosProducto;
	private JTable tableImpuestos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentCRUDProducto frame = new VentCRUDProducto();
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
	public VentCRUDProducto() {
		setTitle("MAESTRO DE ITEMS DE PRODUCTOS");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 833, 646);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Se crea Panel que  contendrá el Jtable y los botones
		JPanel panelJtable = new JPanel();
		panelJtable.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panelJtable.setBounds(20, 430, 771, 167);
		contentPane.add(panelJtable);
		panelJtable.setLayout(null);
		// Instanciamos el jtable
		jTableItemInventario = new JTable();
		jTableItemInventario.setForeground(Color.black);
		jTableItemInventario.setBounds(52, 25, 692, 58);
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
				String nombreItem = jTextDescripcion.getText();
				String unidadMedida = jTextImpresion.getText();
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
				jTextDescripcion.setText("");
				jTextImpresion.setText("");
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
		
		JTabbedPane tabPanelProductos = new JTabbedPane(JTabbedPane.TOP);
		tabPanelProductos.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		tabPanelProductos.setBounds(20, 11, 771, 408);
		contentPane.add(tabPanelProductos);
		
		JPanel panelProductos = new JPanel();
		tabPanelProductos.addTab("Info General", null, panelProductos, null);
		panelProductos.setLayout(null);
		
		JLabel lblProducto = new JLabel("Id Producto");
		lblProducto.setBounds(45, 34, 93, 14);
		panelProductos.add(lblProducto);
		
		jTextIDProducto = new JTextField();
		jTextIDProducto.setBounds(148, 31, 86, 20);
		panelProductos.add(jTextIDProducto);
		jTextIDProducto.setEnabled(false);
		jTextIDProducto.setColumns(10);
		
		JLabel lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setBounds(45, 69, 114, 14);
		panelProductos.add(lblDescripcion);
		
		jTextDescripcion = new JTextField();
		jTextDescripcion.setBounds(148, 66, 162, 20);
		panelProductos.add(jTextDescripcion);
		jTextDescripcion.setColumns(50);
		jTextDescripcion.setText("");
		
		JLabel lblImpresion = new JLabel("Impresi\u00F3n");
		lblImpresion.setBounds(45, 101, 93, 14);
		panelProductos.add(lblImpresion);
		
		jTextImpresion = new JTextField();
		jTextImpresion.setBounds(146, 98, 164, 20);
		panelProductos.add(jTextImpresion);
		jTextImpresion.setColumns(100);
		jTextImpresion.setText("");
		
		JTextPane textTextoBoton = new JTextPane();
		textTextoBoton.setBounds(45, 146, 93, 57);
		panelProductos.add(textTextoBoton);
		
		JLabel lblAmarillo = new JLabel("Amarillo");
		lblAmarillo.setBackground(Color.YELLOW);
		lblAmarillo.setBounds(164, 148, 57, 20);
		panelProductos.add(lblAmarillo);
		
		JLabel labelAzul = new JLabel("Azul");
		labelAzul.setBackground(Color.BLUE);
		labelAzul.setBounds(161, 177, 60, 14);
		panelProductos.add(labelAzul);
		
		JLabel lblRojo = new JLabel("Rojo");
		lblRojo.setBackground(Color.RED);
		lblRojo.setBounds(164, 202, 57, 14);
		panelProductos.add(lblRojo);
		
		JLabel lblPrecio = new JLabel("Precio");
		lblPrecio.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPrecio.setBounds(57, 240, 60, 14);
		panelProductos.add(lblPrecio);
		
		JLabel lblPrecio_1 = new JLabel("Precio1");
		lblPrecio_1.setBounds(26, 285, 46, 14);
		panelProductos.add(lblPrecio_1);
		
		textPrecio1 = new JTextField();
		textPrecio1.setBounds(73, 282, 86, 20);
		panelProductos.add(textPrecio1);
		textPrecio1.setColumns(10);
		
		JLabel lblPrecio2 = new JLabel("Precio2");
		lblPrecio2.setBounds(26, 320, 46, 14);
		panelProductos.add(lblPrecio2);
		
		textPrecio2 = new JTextField();
		textPrecio2.setBounds(73, 317, 86, 20);
		panelProductos.add(textPrecio2);
		textPrecio2.setColumns(10);
		
		JLabel lblPrecio3 = new JLabel("Precio3");
		lblPrecio3.setBounds(197, 285, 46, 14);
		panelProductos.add(lblPrecio3);
		
		textPrecio3 = new JTextField();
		textPrecio3.setBounds(263, 282, 86, 20);
		panelProductos.add(textPrecio3);
		textPrecio3.setColumns(10);
		
		JLabel lblPrecio_2 = new JLabel("Precio4");
		lblPrecio_2.setBounds(197, 320, 46, 14);
		panelProductos.add(lblPrecio_2);
		
		textPrecio4 = new JTextField();
		textPrecio4.setBounds(263, 317, 86, 20);
		panelProductos.add(textPrecio4);
		textPrecio4.setColumns(10);
		
		JLabel lblPrecio5 = new JLabel("Precio5");
		lblPrecio5.setBounds(386, 285, 46, 14);
		panelProductos.add(lblPrecio5);
		
		JLabel lblPrecio_3 = new JLabel("Precio6");
		lblPrecio_3.setBounds(386, 320, 46, 14);
		panelProductos.add(lblPrecio_3);
		
		textPrecio5 = new JTextField();
		textPrecio5.setBounds(446, 282, 86, 20);
		panelProductos.add(textPrecio5);
		textPrecio5.setColumns(10);
		
		textPrecio6 = new JTextField();
		textPrecio6.setBounds(446, 317, 86, 20);
		panelProductos.add(textPrecio6);
		textPrecio6.setColumns(10);
		
		JLabel lblPrecio7 = new JLabel("Precio7");
		lblPrecio7.setBounds(562, 285, 46, 14);
		panelProductos.add(lblPrecio7);
		
		JLabel lblPrecio_4 = new JLabel("Precio8");
		lblPrecio_4.setBounds(562, 320, 46, 14);
		panelProductos.add(lblPrecio_4);
		
		textPrecio7 = new JTextField();
		textPrecio7.setBounds(620, 282, 86, 20);
		panelProductos.add(textPrecio7);
		textPrecio7.setColumns(10);
		
		textPrecio8 = new JTextField();
		textPrecio8.setBounds(618, 317, 86, 20);
		panelProductos.add(textPrecio8);
		textPrecio8.setColumns(10);
		
		JLabel lblPrecio9 = new JLabel("Precio9");
		lblPrecio9.setBounds(197, 349, 46, 14);
		panelProductos.add(lblPrecio9);
		
		JLabel lblPrecio_5 = new JLabel("Precio10");
		lblPrecio_5.setBounds(386, 349, 46, 14);
		panelProductos.add(lblPrecio_5);
		
		textPrecio9 = new JTextField();
		textPrecio9.setBounds(263, 346, 86, 20);
		panelProductos.add(textPrecio9);
		textPrecio9.setColumns(10);
		
		textPrecio10 = new JTextField();
		textPrecio10.setBounds(446, 348, 86, 20);
		panelProductos.add(textPrecio10);
		textPrecio10.setColumns(10);
		
		JPanel panelPreguntasForzadas = new JPanel();
		panelPreguntasForzadas.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panelPreguntasForzadas.setBounds(365, 22, 368, 169);
		panelProductos.add(panelPreguntasForzadas);
		panelPreguntasForzadas.setLayout(null);
		
		JLabel lblPanelPreguntasForzadas = new JLabel("Preguntas Forzadas");
		lblPanelPreguntasForzadas.setBounds(134, 12, 125, 14);
		panelPreguntasForzadas.add(lblPanelPreguntasForzadas);
		
		JComboBox comboBoxPreForzada1 = new JComboBox();
		comboBoxPreForzada1.setBounds(160, 37, 182, 20);
		panelPreguntasForzadas.add(comboBoxPreForzada1);
		
		JComboBox comboBoxPreForzada2 = new JComboBox();
		comboBoxPreForzada2.setBounds(160, 63, 182, 20);
		panelPreguntasForzadas.add(comboBoxPreForzada2);
		
		JComboBox comboBoxPreForzada3 = new JComboBox();
		comboBoxPreForzada3.setBounds(160, 89, 182, 20);
		panelPreguntasForzadas.add(comboBoxPreForzada3);
		
		JComboBox comboBoxPreForzada4 = new JComboBox();
		comboBoxPreForzada4.setBounds(160, 113, 182, 20);
		panelPreguntasForzadas.add(comboBoxPreForzada4);
		
		JComboBox comboBoxPreForzada5 = new JComboBox();
		comboBoxPreForzada5.setBounds(160, 138, 182, 20);
		panelPreguntasForzadas.add(comboBoxPreForzada5);
		
		JLabel lblPreguntaForzada = new JLabel("Pregunta Forzada 1");
		lblPreguntaForzada.setBounds(10, 40, 140, 14);
		panelPreguntasForzadas.add(lblPreguntaForzada);
		
		JLabel lblPreguntaForzada2 = new JLabel("Pregunta Forzada 2 ");
		lblPreguntaForzada2.setBounds(10, 66, 140, 14);
		panelPreguntasForzadas.add(lblPreguntaForzada2);
		
		JLabel lblPreguntaForzada3 = new JLabel("Pregunta Forzada 3");
		lblPreguntaForzada3.setBounds(10, 92, 140, 14);
		panelPreguntasForzadas.add(lblPreguntaForzada3);
		
		JLabel lblPreguntaForzada4 = new JLabel("Pregunta Forzada 4");
		lblPreguntaForzada4.setBounds(10, 116, 140, 14);
		panelPreguntasForzadas.add(lblPreguntaForzada4);
		
		JLabel lblPreguntaForzada5 = new JLabel("Pregunta Forzada 5");
		lblPreguntaForzada5.setBounds(10, 141, 140, 14);
		panelPreguntasForzadas.add(lblPreguntaForzada5);
		
		//PANEL DE IMPUESTOS
		
		JPanel panelImpuestos = new JPanel();
		tabPanelProductos.addTab("Impuestos", null, panelImpuestos, null);
		panelImpuestos.setLayout(null);
		
		JComboBox comboBoxImpuesto = new JComboBox();
		comboBoxImpuesto.setToolTipText("Selecciona el impuesto a aplicar al producto");
		comboBoxImpuesto.setBounds(314, 41, 142, 20);
		panelImpuestos.add(comboBoxImpuesto);
		
		JLabel lblSeleccioneElImpuesto = new JLabel("Seleccione el Impuesto");
		lblSeleccioneElImpuesto.setBounds(111, 44, 151, 14);
		panelImpuestos.add(lblSeleccioneElImpuesto);
		
		JLabel lblImpuestosQueAplican = new JLabel("IMPUESTOS QUE APLICAN AL PRODUCTO");
		lblImpuestosQueAplican.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblImpuestosQueAplican.setBounds(98, 153, 306, 14);
		panelImpuestos.add(lblImpuestosQueAplican);
		
		tableImpuestos = new JTable();
		tableImpuestos.setBounds(98, 335, 419, -118);
		panelImpuestos.add(tableImpuestos);
		
		tableImpuestosProducto = new JTable();
		tableImpuestosProducto.setBounds(111, 259, 279, -58);
		JScrollPane barraTableImpuestos =  new JScrollPane(tableImpuestosProducto,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//panelImpuestos.add(tableImpuestosProducto);
		
		
		
		//PANEL PRODUCTO INCLUIDO
		
		JPanel panelProductoIncluido = new JPanel();
		tabPanelProductos.addTab("Productos Incluidos", null, panelProductoIncluido, null);
		panelProductoIncluido.setLayout(null);
		
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = jTableItemInventario.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar algún Item Inventario para editar " , "No ha Seleccionado para edición ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				filaSeleccionada = jTableItemInventario.getSelectedRow();
				jTextIDProducto.setText((String)jTableItemInventario.getValueAt(filaSeleccionada, 0));
				jTextDescripcion.setText((String)jTableItemInventario.getValueAt(filaSeleccionada, 1));
				jTextImpresion.setText((String)jTableItemInventario.getValueAt(filaSeleccionada, 2));
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
					ItemInventario itemEditado = new ItemInventario(Integer.parseInt(jTextIDProducto.getText()),jTextDescripcion.getText(),jTextImpresion.getText()); 
					ParametrosProductoCtrl parCtrl = new ParametrosProductoCtrl();
					boolean respuesta = parCtrl.editarItemInventario(itemEditado);
					if (respuesta)
					{
						JOptionPane.showMessageDialog(null, "Se ha editado correctamente el registro " , "Confirmación Edición", JOptionPane.OK_OPTION);
						DefaultTableModel modelo = pintarItemInventario();
						jTableItemInventario.setModel(modelo);
						jTextDescripcion.setText("");
						jTextImpresion.setText("");
						jTextIDProducto.setText("");
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
	String nombreItem = jTextDescripcion.getText();
	String unidadMedida = jTextImpresion.getText();
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
