package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.StringTokenizer;

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

import capaControlador.ParametrosCtrl;
import capaControlador.ParametrosProductoCtrl;
import capaModelo.ImpuestoProducto;
import capaModelo.ItemInventario;
import capaModelo.ItemInventarioProducto;
import capaModelo.Producto;
import capaModelo.ProductoIncluido;

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
	private JTable jTableProductos;
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
	private JTable tableImpuestos;
	private JComboBox comboBoxImpuesto;
	private JComboBox comboBoxItem;
	private JComboBox comboProductoIncluir;
	private JComboBox comboPrecioProductoIncluir;
	//Definimos la variable que nos permitir� indicar si estamos creando o editando un producto
	private int idProducto;
	private JTable table;
	private JTable tableItemsInventario;
	private JTextField textCantidadItem;
	private JTextField textCantidadIncluir;
	private JTable tableProductoIncluir;
	private JComboBox comboBoxPreForzada1;
	private JComboBox comboBoxPreForzada2;
	private JComboBox comboBoxPreForzada3;
	private JComboBox comboBoxPreForzada4;
	private JComboBox comboBoxPreForzada5;
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
	 * M�todo que se encarga de retornar los productos y de pintarlos en el Jtable correspondiente la informaci�n
	 * retornada de base de datos.
	 */
	public DefaultTableModel pintarProducto()
	{
		Object[] columnsName = new Object[4];
        
        columnsName[0] = "Id Producto";
        columnsName[1] = "Descripcion";
        columnsName[2] = "Impresion";
        columnsName[3] = "Texto Bot�n";
        ParametrosProductoCtrl par = new ParametrosProductoCtrl();
		ArrayList<Object> items = par.obtenerProductos();
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < items.size();y++)
		{
			String[] fila =(String[]) items.get(y);
			modelo.addRow(fila);
		}
		return(modelo);
		
	}
	
	/**
	 * M�todo que se encarga de retornar los impuestos_producto y de pintarlos en el Jtable correspondiente la informaci�n
	 * retornada de base de datos.
	 */
	public DefaultTableModel pintarImpuestosProducto()
	{
		Object[] columnsName = new Object[5];
        
		columnsName[0] = "Id Impuesto Producto";
        columnsName[1] = "Id Producto";
        columnsName[2] = "Producto";
        columnsName[3] = "Id Impuesto";
        columnsName[4] = "Descripci�n Impuesto";
        ParametrosProductoCtrl par = new ParametrosProductoCtrl();
		ArrayList<Object> items = par.obtenerImpuestosProducto(idProducto);
		DefaultTableModel modeloImpuestos = new DefaultTableModel();
		modeloImpuestos.setColumnIdentifiers(columnsName);
		for(int y = 0; y < items.size();y++)
		{
			String[] fila =(String[]) items.get(y);
			modeloImpuestos.addRow(fila);
		}
		return(modeloImpuestos);
		
	}
	
	/*
	 * M�todo que se encarga de retornar un objeto de tipo DefaultTableModel con los productos incluidos con base en un producto seleccionado en la pantalla
	 */
	public DefaultTableModel pintarProductosIncluidos()
	{
		Object[] columnsName = new Object[5];
        
		columnsName[0] = "Id Producto Incluido";
        columnsName[1] = "Producto Incluye";
        columnsName[2] = "Producto Incluido";
        columnsName[3] = "Cantidad";
        columnsName[4] = "Precio";
        ParametrosProductoCtrl par = new ParametrosProductoCtrl();
		ArrayList<Object> items = par.obtenerProductosIncluidos(idProducto);
		DefaultTableModel modeloProductosIncluidos = new DefaultTableModel();
		modeloProductosIncluidos.setColumnIdentifiers(columnsName);
		for(int y = 0; y < items.size();y++)
		{
			String[] fila =(String[]) items.get(y);
			modeloProductosIncluidos .addRow(fila);
		}
		return(modeloProductosIncluidos );
		
	}
	
	/**
	 * M�todo que se encarga de retornar los itemsInventarioProducto y de pintarlos en el Jtable correspondiente la informaci�n
	 * retornada de base de datos.
	 */
	public DefaultTableModel pintarItemsInventarioProducto()
	{
		Object[] columnsName = new Object[5];
        
		columnsName[0] = "Id Item Producto";
        columnsName[1] = "Producto";
        columnsName[2] = "Id Item";
        columnsName[3] = "Nombre Item";
        columnsName[4] = "Cantidad";
        ParametrosProductoCtrl par = new ParametrosProductoCtrl();
		ArrayList<Object> items = par.obtenerItemsInventarioProducto(idProducto);
		DefaultTableModel modeloItems = new DefaultTableModel();
		modeloItems .setColumnIdentifiers(columnsName);
		for(int y = 0; y < items.size();y++)
		{
			String[] fila =(String[]) items.get(y);
			modeloItems.addRow(fila);
		}
		return(modeloItems);
		
	}
	
	/**
	 * Create the frame.
	 * Se documentan todas las acciones  a seguir cuando se instancia el frame para el CRUD de impuestor.
	 */
	public VentCRUDProducto() {
		setTitle("MAESTRO DE ITEMS DE PRODUCTOS");
		idProducto = 0;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 833, 646);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Se crea Panel que  contendr� el Jtable y los botones
		JPanel panelJtable = new JPanel();
		panelJtable.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panelJtable.setBounds(20, 430, 771, 167);
		contentPane.add(panelJtable);
		panelJtable.setLayout(null);
		// Instanciamos el jtable
		jTableProductos = new JTable();
		jTableProductos.setForeground(Color.black);
		jTableProductos.setBounds(52, 25, 692, 58);
		panelJtable.add(jTableProductos);
		jTableProductos.setBorder(new LineBorder(new Color(0, 0, 0)));
		jTableProductos.setBackground(Color.WHITE);
		DefaultTableModel modelo = pintarProducto();
		this.jTableProductos.setModel(modelo);
		//Adicionar manejo para el evento de seleccion
		
		
		
		
		//Adicionamos los botones para las acciones del GRID
		JButton btnInsertar = new JButton("Insertar");
		/**
		 * M�todo que implementar� la acci�n cuando se de click sobre el bot�n Insertar
		 */
		
		btnInsertar.setBounds(52, 133, 89, 23);
		panelJtable.add(btnInsertar);
		JButton btnEliminar = new JButton("Eliminar");
		//Evento para definir las acciones para eliminar
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int filaSeleccionada = jTableProductos.getSelectedRow();
				//Hacemos la validaci�n para decidir si se elimina o no
				String productoEliminar = (String) jTableProductos.getValueAt(filaSeleccionada, 1);
				int idProducto = Integer.parseInt((String)jTableProductos.getValueAt(filaSeleccionada, 0));
				JOptionPane.showMessageDialog(null, "Esta seguro que se desea eliminar el Producto " +  productoEliminar , "Eliminacion Producto ", JOptionPane.YES_NO_OPTION);
				ParametrosProductoCtrl parEliminar = new ParametrosProductoCtrl();
				parEliminar.eliminarProducto(idProducto);
				DefaultTableModel modelo = pintarProducto();
				jTableProductos.setModel(modelo);
				jTextDescripcion.setText("");
				jTextImpresion.setText("");
				idProducto = 0;
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
		
		comboBoxPreForzada1 = new JComboBox();
		comboBoxPreForzada1.setBounds(160, 37, 182, 20);
		panelPreguntasForzadas.add(comboBoxPreForzada1);
		
		comboBoxPreForzada2 = new JComboBox();
		comboBoxPreForzada2.setBounds(160, 63, 182, 20);
		panelPreguntasForzadas.add(comboBoxPreForzada2);
		
		comboBoxPreForzada3 = new JComboBox();
		comboBoxPreForzada3.setBounds(160, 89, 182, 20);
		panelPreguntasForzadas.add(comboBoxPreForzada3);
		
		comboBoxPreForzada4 = new JComboBox();
		comboBoxPreForzada4.setBounds(160, 113, 182, 20);
		panelPreguntasForzadas.add(comboBoxPreForzada4);
		
		comboBoxPreForzada5 = new JComboBox();
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
		
//		tableImpuestosProducto = new JTable();
//		tableImpuestosProducto.setBounds(111, 259, 279, -58);
//		JScrollPane barraTableImpuestos =  new JScrollPane(tableImpuestosProducto,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		//PANEL DE IMPUESTOS
		
		JPanel panelImpuestos = new JPanel();
		tabPanelProductos.addTab("Impuestos", null, panelImpuestos, null);
		panelImpuestos.setLayout(null);
		
		comboBoxImpuesto = new JComboBox();
		comboBoxImpuesto.setToolTipText("Selecciona el impuesto a aplicar al producto");
		comboBoxImpuesto.setBounds(314, 41, 142, 20);
		panelImpuestos.add(comboBoxImpuesto);
		//Inicializar ComboBox
		initComboBoxImpuesto();
		
		JLabel lblSeleccioneElImpuesto = new JLabel("Seleccione el Impuesto");
		lblSeleccioneElImpuesto.setBounds(111, 44, 151, 14);
		panelImpuestos.add(lblSeleccioneElImpuesto);
		
		JLabel lblImpuestosQueAplican = new JLabel("IMPUESTOS QUE APLICAN AL PRODUCTO");
		lblImpuestosQueAplican.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblImpuestosQueAplican.setBounds(234, 111, 306, 14);
		panelImpuestos.add(lblImpuestosQueAplican);
		
		tableImpuestos = new JTable();
		tableImpuestos.setForeground(Color.black);
		panelImpuestos.add(tableImpuestos);
		tableImpuestos.setBounds(102, 179, 518, 100);
		tableImpuestos.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableImpuestos.setBackground(Color.WHITE);
		DefaultTableModel modeloImpuestos = pintarImpuestosProducto();
		this.tableImpuestos.setModel(modeloImpuestos);
		JButton btnAgregarImpuesto = new JButton("Agregar Impuesto");
		
		btnAgregarImpuesto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String seleccionImpuesto = (String) comboBoxImpuesto.getSelectedItem();
				System.out.println(seleccionImpuesto);
				//Tratamos de extraer el idImpuesto
				StringTokenizer StrTokenImpuesto = new StringTokenizer(seleccionImpuesto,"-");
				String strIdImpuesto = StrTokenImpuesto.nextToken();
				int idImpuesto = 0;
				if (strIdImpuesto != "")
				{
					idImpuesto = Integer.parseInt(strIdImpuesto);
				}
				System.out.println("id impuesto a adicionar " + idImpuesto);
				ImpuestoProducto impProdu = new ImpuestoProducto(0, idImpuesto, idProducto);
				ParametrosProductoCtrl parCtrl = new ParametrosProductoCtrl();
				parCtrl.insertarImpuestoProducto(impProdu);
				DefaultTableModel modeloImpuesto = pintarImpuestosProducto();
				//tableImpuestos.setModel(modeloImpuesto);
				tableImpuestos.setModel(modeloImpuesto);
			}
		});
		btnAgregarImpuesto.setBounds(486, 40, 134, 23);
		panelImpuestos.add(btnAgregarImpuesto);
		
		JButton btnEliminarImpuesto = new JButton("Eliminar Impuesto");
		btnEliminarImpuesto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int filaSeleccionada = tableImpuestos.getSelectedRow();
				//Hacemos la validaci�n para decidir si se elimina o no
				String impuestoEliminar = (String) tableImpuestos.getValueAt(filaSeleccionada, 2) + (String) tableImpuestos.getValueAt(filaSeleccionada, 4);
				int idImpuesto = Integer.parseInt((String)tableImpuestos.getValueAt(filaSeleccionada, 0));
				JOptionPane.showMessageDialog(null, "Esta seguro que se desea eliminar el impuesto " +  impuestoEliminar , "Eliminacion Impuesto de Producto ", JOptionPane.YES_NO_OPTION);
				ParametrosProductoCtrl parEliminar = new ParametrosProductoCtrl();
				parEliminar.eliminarImpuestoProducto(idImpuesto);
				DefaultTableModel modelo = pintarImpuestosProducto();
				tableImpuestos.setModel(modelo);
			}
		});
		btnEliminarImpuesto.setBounds(276, 340, 125, 23);
		panelImpuestos.add(btnEliminarImpuesto);
		
		
				
		
		//PANEL PRODUCTO INCLUIDO
		
		JPanel panelProductoIncluido = new JPanel();
		tabPanelProductos.addTab("Productos Incluidos", null, panelProductoIncluido, null);
		panelProductoIncluido.setLayout(null);
		
		JLabel lblProductoAIncluir = new JLabel("Producto a Incluir");
		lblProductoAIncluir.setBounds(173, 66, 109, 14);
		panelProductoIncluido.add(lblProductoAIncluir);
		
		comboProductoIncluir = new JComboBox();
		comboProductoIncluir.setBounds(345, 63, 187, 20);
		panelProductoIncluido.add(comboProductoIncluir);
		
		JLabel lblCantidad = new JLabel("Cantidad");
		lblCantidad.setBounds(173, 124, 70, 14);
		panelProductoIncluido.add(lblCantidad);
		
		textCantidadIncluir = new JTextField();
		textCantidadIncluir.setBounds(345, 121, 187, 20);
		panelProductoIncluido.add(textCantidadIncluir);
		textCantidadIncluir.setColumns(10);
		
		JLabel lblPrecioAUtilizar = new JLabel("Precio a Utilizar");
		lblPrecioAUtilizar.setBounds(173, 185, 109, 14);
		panelProductoIncluido.add(lblPrecioAUtilizar);
		
		comboPrecioProductoIncluir = new JComboBox();
		comboPrecioProductoIncluir.setBounds(346, 182, 186, 20);
		panelProductoIncluido.add(comboPrecioProductoIncluir);
		initComboProductoIncluir();
		initCombosPreguntas();
		tableProductoIncluir = new JTable();
		tableProductoIncluir.setBounds(116, 237, 546, 89);
		panelProductoIncluido.add(tableProductoIncluir);
		
		JButton btnAgregarProductoIncluido = new JButton("Agregar Producto Incluido");
		btnAgregarProductoIncluido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(validarAgregarProductoIncluido())
				{
					
				}else
				{
					JOptionPane.showMessageDialog(null, "No es posible insertar el Producto Incluido debido a que falta informaci�n  "  , "Error Adicionando Producto Incluido ", JOptionPane.OK_OPTION);
				}
				String seleccionProductoIncluido = (String) comboProductoIncluir.getSelectedItem();
				String precioIncluir =  (String) comboPrecioProductoIncluir.getSelectedItem();
				double cantidadIncluir= Double.parseDouble(textCantidadIncluir.getText());
				//Tratamos de extraer el idImpuesto
				StringTokenizer StrTokenProductoIncluido = new StringTokenizer(seleccionProductoIncluido,"-");
				String strIdProductoIncluido =  StrTokenProductoIncluido.nextToken();
				int idproducto_incluido = 0;
				if (strIdProductoIncluido != "")
				{
					idproducto_incluido = Integer.parseInt(strIdProductoIncluido);
				}
				ProductoIncluido prodIncluido = new ProductoIncluido(0,idProducto,idproducto_incluido,cantidadIncluir, precioIncluir);
				ParametrosProductoCtrl parCtrl = new ParametrosProductoCtrl();
				parCtrl.insertarProductoIncluido(prodIncluido);
				DefaultTableModel modeloProductoIncluido = pintarProductosIncluidos();
				tableProductoIncluir.setModel(modeloProductoIncluido);
			}
		});
		btnAgregarProductoIncluido.setBounds(573, 181, 166, 23);
		panelProductoIncluido.add(btnAgregarProductoIncluido);
		
		JButton btnEliminarProductoIncluido = new JButton("Eliminar Producto Incluido");
		btnEliminarProductoIncluido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int filaSeleccionada = tableProductoIncluir.getSelectedRow();
				//Hacemos la validaci�n para decidir si se elimina o no
				String productoIncluido = " Producto Incluye " +  (String) tableProductoIncluir.getValueAt(filaSeleccionada, 1) + " Producto Incluido " +(String) tableProductoIncluir.getValueAt(filaSeleccionada, 2);
				int idproducto_incluido = Integer.parseInt((String)tableProductoIncluir.getValueAt(filaSeleccionada, 0));
				JOptionPane.showMessageDialog(null, "Esta seguro que se desea eliminar el Producto Incluido " +  productoIncluido , "Eliminacion Impuesto de Producto ", JOptionPane.YES_NO_OPTION);
				ParametrosProductoCtrl parEliminar = new ParametrosProductoCtrl();
				parEliminar.eliminarProductoIncluido(idproducto_incluido);
				DefaultTableModel modeloProductoIncluido = pintarProductosIncluidos();
				tableProductoIncluir.setModel(modeloProductoIncluido);
			}
		});
		btnEliminarProductoIncluido.setBounds(278, 337, 196, 23);
		panelProductoIncluido.add(btnEliminarProductoIncluido);
		
		//PANEL PARA ITEM INVENTARIO PRODUCTO
		JPanel panelItemsInventario = new JPanel();
		tabPanelProductos.addTab("ITEMS INVENTARIO", null, panelItemsInventario, null);
		panelItemsInventario.setLayout(null);
		
		tableItemsInventario = new JTable();
		tableItemsInventario.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableItemsInventario.setBounds(75, 179, 611, 97);
		panelItemsInventario.add(tableItemsInventario);
		
		JLabel lblItemInventario = new JLabel("Item Inventario");
		lblItemInventario.setBounds(125, 39, 135, 14);
		panelItemsInventario.add(lblItemInventario);
		
		comboBoxItem = new JComboBox();
		comboBoxItem.setBounds(270, 36, 191, 20);
		panelItemsInventario.add(comboBoxItem);
		
		JLabel lblCantidadItems = new JLabel("Cantidad Items Inventario");
		lblCantidadItems.setBounds(124, 80, 136, 14);
		panelItemsInventario.add(lblCantidadItems);
		initComboBoxItems();
		textCantidadItem = new JTextField();
		textCantidadItem.setBounds(268, 77, 193, 20);
		panelItemsInventario.add(textCantidadItem);
		textCantidadItem.setColumns(10);
		
				
		JButton btnAgregarItemInventario = new JButton("Agregar Item Inventario");
		btnAgregarItemInventario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String seleccionItem = (String) comboBoxItem.getSelectedItem();
				String strCantidad = textCantidadItem.getText();
				double cantidad = 0.0;
				if(strCantidad == "")
				{
					
				}
				cantidad = Double.parseDouble(strCantidad);
				//Tratamos de extraer el idImpuesto
				StringTokenizer StrTokenItem = new StringTokenizer(seleccionItem,"-");
				String strIdItem = StrTokenItem.nextToken();
				int idItem = 0;
				if (strIdItem != "")
				{
					idItem = Integer.parseInt(strIdItem);
				}
				ItemInventarioProducto impItemProducto = new ItemInventarioProducto(0, idItem, idProducto,cantidad);
				ParametrosProductoCtrl parCtrl = new ParametrosProductoCtrl();
				parCtrl.insertarItemInventarioProducto(impItemProducto);
				DefaultTableModel modeloItem = pintarItemsInventarioProducto();
				//tableImpuestos.setModel(modeloImpuesto);
				tableItemsInventario.setModel(modeloItem);
			}
		});
		btnAgregarItemInventario.setBounds(269, 130, 173, 23);
		panelItemsInventario.add(btnAgregarItemInventario);
		
		JButton btnEliminarItemInventario = new JButton("Eliminar Item Inventario");
		btnEliminarItemInventario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int filaSeleccionada = tableItemsInventario.getSelectedRow();
				//Hacemos la validaci�n para decidir si se elimina o no
				String itemsEliminar = (String) tableItemsInventario.getValueAt(filaSeleccionada, 2) + (String) tableItemsInventario.getValueAt(filaSeleccionada, 4);
				int idItem = Integer.parseInt((String)tableItemsInventario.getValueAt(filaSeleccionada, 0));
				JOptionPane.showMessageDialog(null, "Esta seguro que se desea eliminar el item de Inventario " +  itemsEliminar , "Eliminacion Item de Inventario ", JOptionPane.YES_NO_OPTION);
				ParametrosProductoCtrl parEliminar = new ParametrosProductoCtrl();
				parEliminar.eliminarItemInventarioProducto(idItem);
				DefaultTableModel modelo = pintarItemsInventarioProducto();
				tableItemsInventario.setModel(modelo);
			}
		});
		btnEliminarItemInventario.setBounds(270, 317, 172, 23);
		panelItemsInventario.add(btnEliminarItemInventario);
		
		
		
		/**
		 * Se definen las acciones a realizar cuando se seleccione un producto y se le de la opci�n de editar
		 */
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = jTableProductos.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar alg�n Producto para editar " , "No ha Seleccionado para edici�n ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				idProducto = Integer.parseInt((String)jTableProductos.getValueAt(filaSeleccionada, 0));
				//Teniendo el producto y estando en la fase de carga podemos cargar los impuestos por producto
				jTextIDProducto.setText((String)jTableProductos.getValueAt(filaSeleccionada, 0));
				//obtenemos el Objeto producto con el base en el idproducto recuperado del evento anterior
				ParametrosProductoCtrl productoCtrl = new ParametrosProductoCtrl();
				Producto productoEditar = productoCtrl.obtenerProducto(idProducto);
				//Obtenemos el idProducto y con este retornamos el valor recuperarmos el valor para editar.
				jTextDescripcion.setText(productoEditar.getDescripcion());
				jTextImpresion.setText(productoEditar.getImpresion());
				jTextIDProducto.setText(Integer.toString(productoEditar.getIdProducto()));
				textTextoBoton.setText(productoEditar.getTextoBoton());
				textPrecio1.setText(Double.toString(productoEditar.getPrecio1()));
				textPrecio2.setText(Double.toString(productoEditar.getPrecio2()));
				textPrecio3.setText(Double.toString(productoEditar.getPrecio3()));
				textPrecio4.setText(Double.toString(productoEditar.getPrecio4()));
				textPrecio5.setText(Double.toString(productoEditar.getPrecio5()));
				textPrecio6.setText(Double.toString(productoEditar.getPrecio6()));
				textPrecio7.setText(Double.toString(productoEditar.getPrecio7()));
				textPrecio8.setText(Double.toString(productoEditar.getPrecio8()));
				textPrecio9.setText(Double.toString(productoEditar.getPrecio9()));
				textPrecio10.setText(Double.toString(productoEditar.getPrecio10()));
				btnEliminar.setEnabled(false);
				btnInsertar.setEnabled(false);
				btnGrabarEdicion.setEnabled(true);
				//En la edici�n y selecci�n de un producto pintamos los JTABLE
				DefaultTableModel modelo = pintarImpuestosProducto();
				tableImpuestos.setModel(modelo);
				DefaultTableModel modeloItems = pintarItemsInventarioProducto();
				tableItemsInventario.setModel(modeloItems);
				DefaultTableModel modeloProductoIncluido = pintarProductosIncluidos();
				tableProductoIncluir.setModel(modeloProductoIncluido);
			}
		});
		
		btnGrabarEdicion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean validar = validarDatos();
				if (validar)
				{
					String descripcion = jTextDescripcion.getText();
					String impresion = jTextImpresion.getText();
					String textoBoton = textTextoBoton.getText() ;
					String colorBoton = "";
					int idPreguntaForzada1 = 0;
					int idPreguntaForzada2 = 0;
					int idPreguntaForzada3 = 0;
					int idPreguntaForzada4 = 0;
					int idPreguntaForzada5 = 0;
					Double precio1,precio2,precio3, precio4, precio5, precio6, precio7, precio8, precio9, precio10;
					try
					{
						precio1= Double.parseDouble(textPrecio1.getText());
					}catch(Exception e)
					{
						precio1= 0.0;
					}
					try
					{
						precio2= Double.parseDouble(textPrecio1.getText());
					}catch(Exception e)
					{
						precio2= 0.0;
					}
					try
					{
						precio3= Double.parseDouble(textPrecio1.getText());
					}catch(Exception e)
					{
						precio3= 0.0;
					}
					try
					{
						precio4= Double.parseDouble(textPrecio1.getText());
					}catch(Exception e)
					{
						precio4= 0.0;
					}
					try
					{
						precio5= Double.parseDouble(textPrecio1.getText());
					}catch(Exception e)
					{
						precio5= 0.0;
					}
					try
					{
						precio6= Double.parseDouble(textPrecio1.getText());
					}catch(Exception e)
					{
						precio6= 0.0;
					}
					try
					{
						precio7= Double.parseDouble(textPrecio1.getText());
					}catch(Exception e)
					{
						precio7= 0.0;
					}
					try
					{
						precio8= Double.parseDouble(textPrecio1.getText());
					}catch(Exception e)
					{
						precio8= 0.0;
					}
					try
					{
						precio9= Double.parseDouble(textPrecio1.getText());
					}catch(Exception e)
					{
						precio9= 0.0;
					}
					try
					{
						precio10= Double.parseDouble(textPrecio1.getText());
					}catch(Exception e)
					{
						precio10= 0.0;
					}
					String impresionComanda = ""; 
					Producto productoEditar = new Producto(0,descripcion, impresion, textoBoton, colorBoton, idPreguntaForzada1, idPreguntaForzada2, idPreguntaForzada3, idPreguntaForzada4, idPreguntaForzada5, precio1, precio2, precio3, precio4, precio5, precio6, precio7, precio8, precio9, precio10, impresionComanda); 
					ParametrosProductoCtrl parCtrl = new ParametrosProductoCtrl();
					boolean respuesta = parCtrl.editarProducto(productoEditar);
					if (respuesta)
					{
						JOptionPane.showMessageDialog(null, "Se ha editado correctamente el registro " , "Confirmaci�n Edici�n", JOptionPane.OK_OPTION);
						DefaultTableModel modelo = pintarProducto();
						jTextDescripcion.setText("");
						jTextImpresion.setText("");
						jTextIDProducto.setText("");
						textTextoBoton.setText("");
						textPrecio1.setText("");
						textPrecio2.setText("");
						textPrecio3.setText("");
						textPrecio4.setText("");
						textPrecio5.setText("");
						textPrecio6.setText("");
						textPrecio7.setText("");
						textPrecio8.setText("");
						textPrecio9.setText("");
						textPrecio10.setText("");
						btnEliminar.setEnabled(true);
						btnInsertar.setEnabled(true);
						btnGrabarEdicion.setEnabled(false);
					}
				}
				
			}
		});
		
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Validamos si idProducto = 0, en cuyo caso si podemos insertar el Producto
				if(idProducto != 0)
				{
					JOptionPane.showMessageDialog(null, "No se debe puede insertar el producto al parecer se estaba editando un producto", "Confirmaci�n de Operaci�n", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//Se pulso bot�n para adicionar un nuevo Men� Agrupador
				validarDatos();
				String descripcion = jTextDescripcion.getText();
				String impresion = jTextImpresion.getText();
				String textoBoton = textTextoBoton.getText() ;
				String colorBoton = "";
				int idPreguntaForzada1 = 0;
				int idPreguntaForzada2 = 0;
				int idPreguntaForzada3 = 0;
				int idPreguntaForzada4 = 0;
				int idPreguntaForzada5 = 0;
				Double precio1,precio2,precio3, precio4, precio5, precio6, precio7, precio8, precio9, precio10;
				try
				{
					precio1= Double.parseDouble(textPrecio1.getText());
				}catch(Exception e)
				{
					precio1= 0.0;
				}
				try
				{
					precio2= Double.parseDouble(textPrecio1.getText());
				}catch(Exception e)
				{
					precio2= 0.0;
				}
				try
				{
					precio3= Double.parseDouble(textPrecio1.getText());
				}catch(Exception e)
				{
					precio3= 0.0;
				}
				try
				{
					precio4= Double.parseDouble(textPrecio1.getText());
				}catch(Exception e)
				{
					precio4= 0.0;
				}
				try
				{
					precio5= Double.parseDouble(textPrecio1.getText());
				}catch(Exception e)
				{
					precio5= 0.0;
				}
				try
				{
					precio6= Double.parseDouble(textPrecio1.getText());
				}catch(Exception e)
				{
					precio6= 0.0;
				}
				try
				{
					precio7= Double.parseDouble(textPrecio1.getText());
				}catch(Exception e)
				{
					precio7= 0.0;
				}
				try
				{
					precio8= Double.parseDouble(textPrecio1.getText());
				}catch(Exception e)
				{
					precio8= 0.0;
				}
				try
				{
					precio9= Double.parseDouble(textPrecio1.getText());
				}catch(Exception e)
				{
					precio9= 0.0;
				}
				try
				{
					precio10= Double.parseDouble(textPrecio1.getText());
				}catch(Exception e)
				{
					precio10= 0.0;
				}
				String impresionComanda = ""; 
				Producto productoNuevo = new Producto(0,descripcion, impresion, textoBoton, colorBoton, idPreguntaForzada1, idPreguntaForzada2, idPreguntaForzada3, idPreguntaForzada4, idPreguntaForzada5, precio1, precio2, precio3, precio4, precio5, precio6, precio7, precio8, precio9, precio10, impresionComanda); 
				ParametrosProductoCtrl parCtrl = new ParametrosProductoCtrl();
				int idProducto = parCtrl.insertarProducto(productoNuevo);
				DefaultTableModel modelo = pintarProducto();
				jTableProductos.setModel(modelo);
				
				//Limpiamos el contenido de los campos
				
			
			}
		});
		
	}
	
	
public boolean validarDatos()
{
	String descripcion = jTextDescripcion.getText();
	String impresion = jTextImpresion.getText();
	if(descripcion == "")
	{
		JOptionPane.showMessageDialog(null, "Valor del campo DESCRIPCION es necesario", "Falta Informaci�n", JOptionPane.ERROR_MESSAGE);
		return(false);
	}
	if(impresion == "")
	{
		JOptionPane.showMessageDialog(null, "Valor del campo IMPRESION  es necesario", "Falta Informaci�n", JOptionPane.ERROR_MESSAGE);
		return(false);
	}
	
	return(true);
}

public void initComboBoxImpuesto()
{
	ParametrosProductoCtrl parCtrl = new ParametrosProductoCtrl();
	ArrayList impuestos = parCtrl.obtenerImpuestos();
	for(int i = 0; i<impuestos.size();i++)
	{
		String[] fila =  (String[]) impuestos.get(i);
		comboBoxImpuesto.addItem(fila[0]+"-"+fila[1]);
	}
}

public void initComboBoxItems()
{
	ParametrosProductoCtrl parCtrl = new ParametrosProductoCtrl();
	ArrayList items = parCtrl.obtenerItemsInventarios();
	for(int i = 0; i<items.size();i++)
	{
		String[] fila =  (String[]) items.get(i);
		comboBoxItem.addItem(fila[0]+"-"+fila[1]);
	}
}

public void initComboProductoIncluir()
{
	ParametrosProductoCtrl parCtrl = new ParametrosProductoCtrl();
	ArrayList items = parCtrl.obtenerProductos();
	for(int i = 0; i<items.size();i++)
	{
		String[] fila =  (String[]) items.get(i);
		comboProductoIncluir.addItem(fila[0]+"-"+fila[1]);
	}
	comboPrecioProductoIncluir.addItem("precio1");
	comboPrecioProductoIncluir.addItem("precio2");
	comboPrecioProductoIncluir.addItem("precio3");
	comboPrecioProductoIncluir.addItem("precio4");
	comboPrecioProductoIncluir.addItem("precio5");
	comboPrecioProductoIncluir.addItem("precio6");
	comboPrecioProductoIncluir.addItem("precio7");
	comboPrecioProductoIncluir.addItem("precio8");
	comboPrecioProductoIncluir.addItem("precio9");
	comboPrecioProductoIncluir.addItem("precio10");
}

public void initCombosPreguntas()
{
	ParametrosProductoCtrl parCtrl = new ParametrosProductoCtrl();
	ArrayList preguntas = parCtrl.obtenerPreguntas();
	for(int i = 0; i<preguntas.size();i++)
	{
		String[] fila =  (String[]) preguntas.get(i);
		comboBoxPreForzada1.addItem(fila[0]+"-"+fila[6]);
		comboBoxPreForzada2.addItem(fila[0]+"-"+fila[6]);
		comboBoxPreForzada3.addItem(fila[0]+"-"+fila[6]);
		comboBoxPreForzada4.addItem(fila[0]+"-"+fila[6]);
		comboBoxPreForzada5.addItem(fila[0]+"-"+fila[6]);
	}
	
}

public boolean validarAgregarProductoIncluido()
{
	String seleccionProductoIncluido = (String) comboProductoIncluir.getSelectedItem();
	if(seleccionProductoIncluido == "")
	{
		return(false);
	}
	String seleccionPrecio = (String) comboPrecioProductoIncluir.getSelectedItem();
	if(seleccionPrecio == "")
	{
		return(false);
	}
	String cantidad = textCantidadIncluir.getText();
	if(cantidad == "")
	{
		return(false);
	}
	return(true);
}

}

