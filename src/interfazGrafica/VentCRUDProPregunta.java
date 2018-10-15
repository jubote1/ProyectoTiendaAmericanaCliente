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
import capaModelo.EleccionForzada;
import capaModelo.ImpuestoProducto;
import capaModelo.ItemInventario;
import capaModelo.ItemInventarioProducto;
import capaModelo.Pregunta;
import capaModelo.Producto;
import capaModelo.ProductoIncluido;

import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JScrollBar;
import javax.swing.JCheckBox;

public class VentCRUDProPregunta extends JFrame {

	private JPanel contentPane;
	private JTextField textIdPregunta;
	private JTextField textTituloPregunta;
	private JTextField textDescripcion;
	private JScrollPane scrollPane;
	private JTable jTablePreguntas;
	private JTable tableRespuestas;
	private JComboBox comboProducto;
	//Definimos la variable que nos permitirá indicar si estamos creando o editando un producto
	private int idPregunta;
	private JTextField textNumMaxElecciones;
	private JCheckBox checkPermDividir;
	private JCheckBox checkObligaEleccion;
	private JCheckBox checkEstadoPregunta;
	private JComboBox comboBoxPrecio; 
	private JCheckBox checkEstadoEleccion;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentCRUDProPregunta frame = new VentCRUDProPregunta();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Método que se encarga de retornar las preguntas y de pintarlos en el Jtable correspondiente la información
	 * retornada de base de datos.
	 */
	public DefaultTableModel pintarPreguntas()
	{
		Object[] columnsName = new Object[7];
        
        columnsName[0] = "Id Pregunta";
        columnsName[1] = "Titulo";
        columnsName[2] = "Obliga Eleccion";
        columnsName[3] = "Numero Maximo Eleccion";
        columnsName[4] = "Estado";
        columnsName[5] = "Permite Dividir";
        columnsName[6] = "Descripcion";
        ParametrosProductoCtrl par = new ParametrosProductoCtrl();
		ArrayList<Object> items = par.obtenerPreguntas();
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
	 * Método que se encarga de retornar las elecciones Forzadas y de pintarlos en el Jtable correspondiente la información
	 * retornada de base de datos.
	 */
	public DefaultTableModel pintarEleccionesForzadas()
	{
		Object[] columnsName = new Object[6];
        
		columnsName[0] = "Id Eleccion Forzada";
        columnsName[1] = "Id Pregunta";
        columnsName[2] = "Id Producto";
        columnsName[3] = "Producto";
        columnsName[4] = "Precio";
        columnsName[5] = "Estado";
        ParametrosProductoCtrl par = new ParametrosProductoCtrl();
		ArrayList<Object> items = par.obtenerEleccionForzadas(idPregunta);
		DefaultTableModel modeloPreguntas = new DefaultTableModel();
		modeloPreguntas.setColumnIdentifiers(columnsName);
		for(int y = 0; y < items.size();y++)
		{
			String[] fila =(String[]) items.get(y);
			modeloPreguntas.addRow(fila);
		}
		return(modeloPreguntas);
		
	}
	
	
	
		
	/**
	 * Create the frame.
	 * Se documentan todas las acciones  a seguir cuando se instancia el frame para el CRUD de impuestor.
	 */
	public VentCRUDProPregunta() {
		setTitle("MAESTRO ELECCI\u00D3N FORZADA");
		idPregunta = 0;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0,0, 833, 646);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 833, 646);
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
		jTablePreguntas = new JTable();
		jTablePreguntas.setForeground(Color.black);
		jTablePreguntas.setBounds(53, 37, 692, 71);
		panelJtable.add(jTablePreguntas);
		jTablePreguntas.setBorder(new LineBorder(new Color(0, 0, 0)));
		jTablePreguntas.setBackground(Color.WHITE);
		DefaultTableModel modelo = pintarPreguntas();
		this.jTablePreguntas.setModel(modelo);
		//Adicionar manejo para el evento de seleccion
		
		
		
		//Adicionamos los botones para las acciones del GRID
		JButton btnInsertar = new JButton("Insertar");
		/**
		 * Método que implementará la acción cuando se de click sobre el botón Insertar
		 */
		
		btnInsertar.setBounds(120, 119, 89, 23);
		panelJtable.add(btnInsertar);
		JButton btnEliminar = new JButton("Eliminar");
		//Evento para definir las acciones para eliminar
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int filaSeleccionada = jTablePreguntas.getSelectedRow();
				//Hacemos la validación para decidir si se elimina o no
				String preguntaEliminar = (String) jTablePreguntas.getValueAt(filaSeleccionada, 1);
				int idPregunEli = Integer.parseInt((String)jTablePreguntas.getValueAt(filaSeleccionada, 0));
				JOptionPane.showMessageDialog(null, "Esta seguro que se desea eliminar la Pregunta " +  preguntaEliminar , "Eliminacion Pregunta ", JOptionPane.YES_NO_OPTION);
				ParametrosProductoCtrl parEliminar = new ParametrosProductoCtrl();
				parEliminar.eliminarPregunta(idPregunEli);
				DefaultTableModel modelo = pintarPreguntas();
				jTablePreguntas.setModel(modelo);
				textTituloPregunta.setText("");
				textDescripcion.setText("");
				textNumMaxElecciones.setText("");
				checkPermDividir.setSelected(false);
				checkObligaEleccion.setSelected(false);
				checkEstadoPregunta.setSelected(false);
				idPregunta = 0;
			}
		});
		btnEliminar.setBounds(243, 119, 89, 23);
		panelJtable.add(btnEliminar);
		
		JButton btnEditar = new JButton("Editar");
		
		btnEditar.setBounds(366, 119, 89, 23);
		panelJtable.add(btnEditar);
		
		JButton btnGrabarEdicion = new JButton("Grabar Edicion");
		
		btnGrabarEdicion.setBounds(495, 119, 123, 23);
		panelJtable.add(btnGrabarEdicion);
		btnGrabarEdicion.setEnabled(false);
		
		JTabbedPane tabPanelProductos = new JTabbedPane(JTabbedPane.TOP);
		tabPanelProductos.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		tabPanelProductos.setBounds(20, 11, 771, 408);
		contentPane.add(tabPanelProductos);
		
		JPanel panelPreguntas = new JPanel();
		tabPanelProductos.addTab("Info General", null, panelPreguntas, null);
		panelPreguntas.setLayout(null);
		
		JLabel lblPregunta = new JLabel("Id Pregunta");
		lblPregunta.setBounds(158, 37, 93, 14);
		panelPreguntas.add(lblPregunta);
		
		textIdPregunta = new JTextField();
		textIdPregunta.setBounds(408, 37, 162, 20);
		panelPreguntas.add(textIdPregunta);
		textIdPregunta.setEnabled(false);
		textIdPregunta.setColumns(10);
		
		JLabel lblTituloPregunta = new JLabel(" Titulo Pregunta");
		lblTituloPregunta.setBounds(158, 75, 114, 14);
		panelPreguntas.add(lblTituloPregunta);
		
		textTituloPregunta = new JTextField();
		textTituloPregunta.setBounds(408, 72, 162, 20);
		panelPreguntas.add(textTituloPregunta);
		textTituloPregunta.setColumns(50);
		textTituloPregunta.setText("");
		
		JLabel lblDescripcion = new JLabel("Descripci\u00F3n");
		lblDescripcion.setBounds(158, 104, 93, 14);
		panelPreguntas.add(lblDescripcion);
		
		textDescripcion = new JTextField();
		textDescripcion.setBounds(408, 104, 164, 20);
		panelPreguntas.add(textDescripcion);
		textDescripcion.setColumns(100);
		textDescripcion.setText("");
		
		JLabel lblNmeroMximoElecciones = new JLabel("N\u00FAmero M\u00E1ximo Elecciones");
		lblNmeroMximoElecciones.setBounds(158, 142, 142, 14);
		panelPreguntas.add(lblNmeroMximoElecciones);
		
		textNumMaxElecciones = new JTextField();
		textNumMaxElecciones.setBounds(407, 139, 163, 20);
		panelPreguntas.add(textNumMaxElecciones);
		textNumMaxElecciones.setColumns(10);
		
		JLabel lblPermiteDividir = new JLabel("Permite Dividir");
		lblPermiteDividir.setBounds(158, 179, 142, 14);
		panelPreguntas.add(lblPermiteDividir);
		
		checkPermDividir = new JCheckBox("");
		checkPermDividir.setBounds(405, 175, 165, 23);
		panelPreguntas.add(checkPermDividir);
		
		JLabel lblObligaEleccion = new JLabel("Obliga Elecci\u00F3n");
		lblObligaEleccion.setBounds(158, 215, 93, 14);
		panelPreguntas.add(lblObligaEleccion);
		
		checkObligaEleccion = new JCheckBox("");
		checkObligaEleccion.setBounds(405, 206, 97, 23);
		panelPreguntas.add(checkObligaEleccion);
		
		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setBounds(158, 250, 69, 14);
		panelPreguntas.add(lblEstado);
		
		checkEstadoPregunta = new JCheckBox("");
		checkEstadoPregunta.setBounds(405, 246, 97, 23);
		panelPreguntas.add(checkEstadoPregunta);
		

		//PANEL DE EleccionForzada
		
		JPanel panelRespuestas = new JPanel();
		tabPanelProductos.addTab("Respuestas", null, panelRespuestas, null);
		panelRespuestas.setLayout(null);
		
		comboProducto = new JComboBox();
		comboProducto.setToolTipText("Selecciona el impuesto a aplicar al producto");
		comboProducto.setBounds(314, 41, 142, 20);
		panelRespuestas.add(comboProducto);
		//Inicializar ComboBox
		
		
		JLabel lblSeleccionProducto = new JLabel("Seleccione Producto");
		lblSeleccionProducto.setBounds(111, 44, 151, 14);
		panelRespuestas.add(lblSeleccionProducto);
		
		JLabel lblRespuestas = new JLabel("ELECCI\u00D3N FORZADA");
		lblRespuestas.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRespuestas.setBounds(288, 183, 151, 14);
		panelRespuestas.add(lblRespuestas);
		
		tableRespuestas = new JTable();
		tableRespuestas.setForeground(Color.black);
		panelRespuestas.add(tableRespuestas);
		tableRespuestas.setBounds(102, 215, 518, 100);
		tableRespuestas.setBorder(new LineBorder(new Color(0, 0, 0)));
		tableRespuestas.setBackground(Color.WHITE);
		DefaultTableModel modeloRespuestas = pintarEleccionesForzadas();
		this.tableRespuestas.setModel(modeloRespuestas);
		JButton btnAgregarEleccion = new JButton("Agregar Eleccion");
		
		btnAgregarEleccion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String seleccionProducto = (String) comboProducto.getSelectedItem();
				String precio = (String) comboBoxPrecio.getSelectedItem();
				StringTokenizer StrTokenProducto = new StringTokenizer(seleccionProducto,"-");
				String strIdProducto = StrTokenProducto.nextToken();
				int estado = 0;
				if(checkEstadoEleccion.isSelected())
				{
					estado = 1;
				}
				int idProducto = 0;
				if (strIdProducto != "")
				{
					idProducto = Integer.parseInt(strIdProducto);
				}
				EleccionForzada eleccionFor = new EleccionForzada(0, idProducto, "", idPregunta, precio,estado);
				ParametrosProductoCtrl parCtrl = new ParametrosProductoCtrl();
				parCtrl.insertarEleccionForzada(eleccionFor);
				DefaultTableModel modeloEleccion = pintarEleccionesForzadas();
				tableRespuestas.setModel(modeloEleccion);
			}
		});
		btnAgregarEleccion.setBounds(486, 90, 134, 23);
		panelRespuestas.add(btnAgregarEleccion);
		
		JButton btnEliminarEleccion = new JButton("Eliminar Elecci\u00F3n");
		btnEliminarEleccion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int filaSeleccionada = tableRespuestas.getSelectedRow();
				//Hacemos la validación para decidir si se elimina o no
				String eleccionEliminar = (String) tableRespuestas.getValueAt(filaSeleccionada, 2) + (String) tableRespuestas.getValueAt(filaSeleccionada, 4);
				int idEleccion = Integer.parseInt((String)tableRespuestas.getValueAt(filaSeleccionada, 0));
				JOptionPane.showMessageDialog(null, "Esta seguro que se desea eliminar el impuesto " +  eleccionEliminar , "Eliminacion Elección Forzada ", JOptionPane.YES_NO_OPTION);
				ParametrosProductoCtrl parEliminar = new ParametrosProductoCtrl();
				parEliminar.eliminarEleccionForzada(idEleccion);
				DefaultTableModel modelo = pintarEleccionesForzadas();
				tableRespuestas.setModel(modelo);
			}
		});
		btnEliminarEleccion.setBounds(276, 340, 125, 23);
		panelRespuestas.add(btnEliminarEleccion);
		
		comboBoxPrecio = new JComboBox();
		comboBoxPrecio.setBounds(314, 91, 142, 20);
		panelRespuestas.add(comboBoxPrecio);
		
		initComboBoxProducto();
		JLabel lblPrecio = new JLabel("Precio");
		lblPrecio.setBounds(111, 94, 108, 14);
		panelRespuestas.add(lblPrecio);
		
		JLabel lblEstadoEleccion = new JLabel("Estado Elecci\u00F3n");
		lblEstadoEleccion.setBounds(111, 139, 108, 14);
		panelRespuestas.add(lblEstadoEleccion);
		
		checkEstadoEleccion = new JCheckBox("");
		checkEstadoEleccion.setBounds(314, 135, 97, 23);
		panelRespuestas.add(checkEstadoEleccion);
				
		
		/**
		 * Se definen las acciones a realizar cuando se seleccione un producto y se le de la opción de editar
		 */
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = jTablePreguntas.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar algún Producto para editar " , "No ha Seleccionado para edición ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				idPregunta = Integer.parseInt((String)jTablePreguntas.getValueAt(filaSeleccionada, 0));
				textIdPregunta.setText((String)jTablePreguntas.getValueAt(filaSeleccionada, 0));
				//obtenemos el Objeto producto con el base en el idproducto recuperado del evento anterior
				ParametrosProductoCtrl productoCtrl = new ParametrosProductoCtrl();
				Pregunta preguntaEditar = productoCtrl.obtenerPregunta(idPregunta);
				textTituloPregunta.setText(preguntaEditar.getTituloPregunta());
				textDescripcion.setText(preguntaEditar.getDescripcion());
				textIdPregunta.setText(Integer.toString(preguntaEditar.getIdPregunta()));
				textNumMaxElecciones.setText(Integer.toString(preguntaEditar.getNumeroMaximoEleccion()));
				if(preguntaEditar.getEstado() == 1)
				{
					checkEstadoPregunta.setSelected(true);
				}
				if(preguntaEditar.getObligaEleccion() == 1)
				{
					checkObligaEleccion.setSelected(true);
				}
				if(preguntaEditar.getPermiteDividir() == 1)
				{
					checkPermDividir.setSelected(true);
				}
				btnInsertar.setEnabled(false);
				btnGrabarEdicion.setEnabled(true);
				DefaultTableModel modelo = pintarEleccionesForzadas();
				tableRespuestas.setModel(modelo);
			}
		});
		
		btnGrabarEdicion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean validar = validarDatos();
				if (validar)
				{
					String tituloPregunta = textTituloPregunta.getText();
					String descripcion = textDescripcion.getText();
					int numeroMaximoElecciones = Integer.parseInt(textNumMaxElecciones.getText());
					int estado = 0;
					int obligaEleccion = 0;
					int permiteDividir = 0;
					if(checkEstadoPregunta.isSelected())
					{
						estado = 1;
					}
					if(checkObligaEleccion.isSelected())
					{
						obligaEleccion = 1;
					}
					if(checkPermDividir.isSelected())
					{
						permiteDividir = 1;
					}
					Pregunta preguntaEditar = new Pregunta(idPregunta,tituloPregunta,obligaEleccion,numeroMaximoElecciones,estado,permiteDividir,descripcion); 
					ParametrosProductoCtrl parCtrl = new ParametrosProductoCtrl();
					boolean respuesta = parCtrl.editarPregunta(preguntaEditar);
					if (respuesta)
					{
						JOptionPane.showMessageDialog(null, "Se ha editado correctamente el registro " , "Confirmación Edición", JOptionPane.OK_OPTION);
						DefaultTableModel modelo = pintarPreguntas();
						textTituloPregunta.setText("");
						textDescripcion.setText("");
						textIdPregunta.setText("");
						textNumMaxElecciones.setText("");
						checkEstadoPregunta.setSelected(false);
						checkObligaEleccion.setSelected(false);
						checkPermDividir.setSelected(false);
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
				if(idPregunta != 0)
				{
					JOptionPane.showMessageDialog(null, "No se debe  insertar la pregunta al parecer se estaba editando una pregunta", "Confirmación de Operación", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//Se pulso botón para adicionar un nuevo Menú Agrupador
				validarDatos();
				String tituloPregunta = textTituloPregunta.getText();
				String descripcion = textDescripcion.getText();
				int numeroMaximoElecciones = Integer.parseInt(textNumMaxElecciones.getText());
				int estado = 0;
				int obligaEleccion = 0;
				int permiteDividir = 0;
				if(checkEstadoPregunta.isSelected())
				{
					estado = 1;
				}
				if(checkObligaEleccion.isSelected())
				{
					obligaEleccion = 1;
				}
				if(checkPermDividir.isSelected())
				{
					permiteDividir = 1;
				} 
				Pregunta preguntaNueva = new Pregunta(idPregunta,tituloPregunta,obligaEleccion,numeroMaximoElecciones,estado,permiteDividir,descripcion); 
				ParametrosProductoCtrl parCtrl = new ParametrosProductoCtrl();
				int idPre = parCtrl.insertarPregunta(preguntaNueva);
				DefaultTableModel modelo = pintarPreguntas();
				jTablePreguntas.setModel(modelo);
				idPregunta = idPre;
				//Limpiamos el contenido de los campos
				
			
			}
		});
		
	}
	
	
public boolean validarDatos()
{
	String titulo = textTituloPregunta.getText();
	String descripcion= textDescripcion.getText();
	if(descripcion == "")
	{
		JOptionPane.showMessageDialog(null, "Valor del campo DESCRIPCION es necesario", "Falta Información", JOptionPane.ERROR_MESSAGE);
		return(false);
	}
	if(titulo == "")
	{
		JOptionPane.showMessageDialog(null, "Valor del campo TITULO es necesario", "Falta Información", JOptionPane.ERROR_MESSAGE);
		return(false);
	}
	
	return(true);
}

public void initComboBoxProducto()
{
	ParametrosProductoCtrl parCtrl = new ParametrosProductoCtrl();
	ArrayList productos = parCtrl.obtenerProductos();
	for(int i = 0; i<productos.size();i++)
	{
		String[] fila =  (String[]) productos.get(i);
		comboProducto.addItem(fila[0]+"-"+fila[1]);
	}
	comboBoxPrecio.addItem("precio1");
	comboBoxPrecio.addItem("precio2");
	comboBoxPrecio.addItem("precio3");
	comboBoxPrecio.addItem("precio4");
	comboBoxPrecio.addItem("precio5");
	comboBoxPrecio.addItem("precio6");
	comboBoxPrecio.addItem("precio7");
	comboBoxPrecio.addItem("precio8");
	comboBoxPrecio.addItem("precio9");
	comboBoxPrecio.addItem("precio10");
}

}

