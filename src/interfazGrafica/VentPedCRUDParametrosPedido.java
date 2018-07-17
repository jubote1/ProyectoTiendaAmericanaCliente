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
import capaControlador.ParametrosDireccionCtrl;
import capaControlador.ParametrosProductoCtrl;
import capaModelo.ImpuestoProducto;
import capaModelo.ItemInventario;
import capaModelo.ItemInventarioProducto;
import capaModelo.Municipio;
import capaModelo.Producto;
import capaModelo.ProductoIncluido;

import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JScrollBar;

public class VentPedCRUDParametrosPedido extends JFrame {

	private JPanel contentPane;
	private JTextField jTextIDMunicipio;
	private JTextField jTextNombre;
	private JTable jTableMunicipios;
	private JComboBox comboProductoIncluir;
	private JComboBox comboPrecioProductoIncluir;
	//Definimos la variable que nos permitirá indicar si estamos creando o editando un producto
	private int idMunicipio;
	private JTable table;
	private JTextField textCantidadIncluir;
	private JTable tableProductoIncluir;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentPedCRUDParametrosPedido frame = new VentPedCRUDParametrosPedido();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Método que se encarga de retornar los productos y de pintarlos en el Jtable correspondiente la información
	 * retornada de base de datos.
	 */
	public DefaultTableModel pintarMunicipio()
	{
		Object[] columnsName = new Object[2];
        
        columnsName[0] = "Id Municipio";
        columnsName[1] = "Nombre";
        ParametrosDireccionCtrl par = new ParametrosDireccionCtrl();
		ArrayList<Object> municipios = par.obtenerMunicipios();
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < municipios.size();y++)
		{
			String[] fila =(String[]) municipios.get(y);
			modelo.addRow(fila);
		}
		return(modelo);
		
	}
	
		
	
	
	/**
	 * Create the frame.
	 * Se documentan todas las acciones  a seguir cuando se instancia el frame para el CRUD de impuestor.
	 */
	public VentPedCRUDParametrosPedido() {
		setTitle("PAR\u00C1METROS PEDIDO");
		idMunicipio = 0;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 617, 474);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		DefaultTableModel modelo = pintarMunicipio();
		
		JTabbedPane tabPanelParametros = new JTabbedPane(JTabbedPane.TOP);
		tabPanelParametros.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		tabPanelParametros.setBounds(20, 11, 580, 408);
		contentPane.add(tabPanelParametros);
		
		JPanel panelParametrosPedido = new JPanel();
		tabPanelParametros.addTab("Municipio", null, panelParametrosPedido, null);
		panelParametrosPedido.setLayout(null);
		
		JLabel lblMunicipio = new JLabel("Id Municipio");
		lblMunicipio.setBounds(45, 34, 93, 14);
		panelParametrosPedido.add(lblMunicipio);
		
		jTextIDMunicipio = new JTextField();
		jTextIDMunicipio.setBounds(148, 31, 162, 20);
		panelParametrosPedido.add(jTextIDMunicipio);
		jTextIDMunicipio.setEnabled(false);
		jTextIDMunicipio.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(45, 69, 114, 14);
		panelParametrosPedido.add(lblNombre);
		
		jTextNombre = new JTextField();
		jTextNombre.setBounds(148, 66, 162, 20);
		panelParametrosPedido.add(jTextNombre);
		jTextNombre.setColumns(50);
		jTextNombre.setText("");
		// Instanciamos el jtable
		jTableMunicipios = new JTable();
		jTableMunicipios.setBounds(124, 142, 360, 58);
		panelParametrosPedido.add(jTableMunicipios);
		jTableMunicipios.setForeground(Color.black);
		jTableMunicipios.setBorder(new LineBorder(new Color(0, 0, 0)));
		jTableMunicipios.setBackground(Color.WHITE);
		this.jTableMunicipios.setModel(modelo);
		//Adicionar manejo para el evento de seleccion
		
		
		
		
		//Adicionamos los botones para las acciones del GRID
		JButton btnInsertar = new JButton("Insertar");
		btnInsertar.setBounds(45, 274, 89, 23);
		panelParametrosPedido.add(btnInsertar);
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(169, 274, 89, 23);
		panelParametrosPedido.add(btnEliminar);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.setBounds(302, 274, 89, 23);
		panelParametrosPedido.add(btnEditar);
		
		JButton btnGrabarEdicion = new JButton("Grabar Edicion");
		btnGrabarEdicion.setBounds(424, 274, 123, 23);
		panelParametrosPedido.add(btnGrabarEdicion);
		btnGrabarEdicion.setEnabled(false);
		
		btnGrabarEdicion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean validar = validarDatos();
				if (validar)
				{
					String nombre = jTextNombre.getText();
					int idMunicipio = Integer.parseInt(jTextIDMunicipio.getText());
					Municipio municipio = new Municipio(idMunicipio, nombre);
					ParametrosDireccionCtrl parCtrl = new ParametrosDireccionCtrl();
					boolean respuesta = parCtrl.editarMunicipio(municipio);
					if (respuesta)
					{
						JOptionPane.showMessageDialog(null, "Se ha editado correctamente el registro " , "Confirmación Edición", JOptionPane.OK_OPTION);
						DefaultTableModel modelo = pintarMunicipio();
						jTextNombre.setText("");
						jTextIDMunicipio.setText("");
						btnEliminar.setEnabled(true);
						btnInsertar.setEnabled(true);
						btnGrabarEdicion.setEnabled(false);
					}
				}
				
			}
		});
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = jTableMunicipios.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar algún Municipio para editar " , "No ha Seleccionado para edición ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				idMunicipio = Integer.parseInt((String)jTableMunicipios.getValueAt(filaSeleccionada, 0));
				jTextIDMunicipio.setText((String)jTableMunicipios.getValueAt(filaSeleccionada, 0));
				ParametrosDireccionCtrl parDirCtrl = new ParametrosDireccionCtrl();
				Municipio municipioEditar = parDirCtrl.obtenerMunicipio(idMunicipio);
				jTextNombre.setText(municipioEditar.getNombre());
				//jTextIDMunicipio.setText(Integer.toString(productoEditar.getIdProducto()));
				btnEliminar.setEnabled(false);
				btnInsertar.setEnabled(false);
				btnGrabarEdicion.setEnabled(true);
				
			}
		});
		//Evento para definir las acciones para eliminar
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int filaSeleccionada = jTableMunicipios.getSelectedRow();
				//Hacemos la validación para decidir si se elimina o no
				String MunicipioEliminar = (String) jTableMunicipios.getValueAt(filaSeleccionada, 1);
				int idMunicipio = Integer.parseInt((String)jTableMunicipios.getValueAt(filaSeleccionada, 0));
				JOptionPane.showMessageDialog(null, "Esta seguro que se desea eliminar el Municipio " +  MunicipioEliminar , "Eliminacion Producto ", JOptionPane.YES_NO_OPTION);
				ParametrosDireccionCtrl parEliminar = new ParametrosDireccionCtrl();
				parEliminar.eliminarMunicipio(idMunicipio);
				DefaultTableModel modelo = pintarMunicipio();
				jTableMunicipios.setModel(modelo);
				jTextNombre.setText("");
				idMunicipio = 0;
			}
		});
		
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Validamos si idProducto = 0, en cuyo caso si podemos insertar el Producto
				if(idMunicipio != 0)
				{
					JOptionPane.showMessageDialog(null, "No se debe puede insertar el Municipio al parecer se estaba editando un municipio", "Confirmación de Operación", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//Se pulso botón para adicionar un nuevo Menú Agrupador
				validarDatos();
				String nombre = jTextNombre.getText();
				Municipio municipio = new Municipio(0, nombre);
				ParametrosDireccionCtrl parCtrl = new ParametrosDireccionCtrl();
				int idMunicipio = parCtrl.insertarMunicipio(municipio);
				DefaultTableModel modelo = pintarMunicipio();
				jTableMunicipios.setModel(modelo);
				jTextNombre.setText("");
				jTextIDMunicipio.setText("");
				//Limpiamos el contenido de los campos
				
			
			}
		});
		//Inicializar ComboBox
					
		
		//PANEL PRODUCTO INCLUIDO
		
		JPanel panelProductoIncluido = new JPanel();
		tabPanelParametros.addTab("Productos Incluidos", null, panelProductoIncluido, null);
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
		tableProductoIncluir = new JTable();
		tableProductoIncluir.setBounds(116, 237, 546, 89);
		panelProductoIncluido.add(tableProductoIncluir);
		
		JButton btnAgregarProductoIncluido = new JButton("Agregar Producto Incluido");
		btnAgregarProductoIncluido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnAgregarProductoIncluido.setBounds(573, 181, 166, 23);
		panelProductoIncluido.add(btnAgregarProductoIncluido);
		
		JButton btnEliminarProductoIncluido = new JButton("Eliminar Producto Incluido");
		btnEliminarProductoIncluido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		btnEliminarProductoIncluido.setBounds(278, 337, 196, 23);
		panelProductoIncluido.add(btnEliminarProductoIncluido);
		
		
		
		
		/**
		 * Se definen las acciones a realizar cuando se seleccione un producto y se le de la opción de editar
		 */
		
	}
	
	
public boolean validarDatos()
{
	String nombreMunicipio = jTextNombre.getText();
	if(nombreMunicipio == "")
	{
		JOptionPane.showMessageDialog(null, "Valor del campo NOMBRE municipio", "Falta Información", JOptionPane.ERROR_MESSAGE);
		return(false);
	}
	
	return(true);
}



}

