package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import capaControlador.AutenticacionCtrl;
import capaControlador.EmpleadoCtrl;
import capaControlador.ParametrosDireccionCtrl;
import capaModelo.MenuAgrupador;
import capaModelo.Municipio;
import capaModelo.TipoEmpleado;
import capaModelo.Usuario;

import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JScrollBar;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;

public class VentSegEmpleado extends JFrame {

	private JPanel contentPane;
	private JTextField jTextIdUsuario;
	private JTextField jTextUsuario;
	private JScrollPane scrollPane;
	private JTable jTableEmpleado;
	private JTextField txtNombreLargo;
	private JComboBox comboBoxTipoUsuario;
	private JComboBox comboBoxTipoInicio;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentSegEmpleado frame = new VentSegEmpleado();
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
	public DefaultTableModel pintarEmpleados()
	{
		Object[] columnsName = new Object[5];
        
        columnsName[0] = "Id ";
        columnsName[1] = "Nombre";
        columnsName[2] = "Nombre Largo";
        columnsName[3] = "Administrador";
        columnsName[4] = "Tipo Inicio";
        EmpleadoCtrl empCtrl = new EmpleadoCtrl(PrincipalLogueo.habilitaAuditoria);
		ArrayList<Object> empleados = empCtrl.obtenerEmpleados();
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < empleados.size();y++)
		{
			String[] fila =(String[]) empleados.get(y);
			System.out.println(fila);
			modelo.addRow(fila);
		}
		return(modelo);
		
	}
	
	/**
	 * Create the frame.
	 * Se documentan todas las acciones  a seguir cuando se instancia el frame para el CRUD de menú Agrupador.
	 */
	public VentSegEmpleado() {
		setTitle("DEFINICI\u00D3N  DE EMPLEADOS");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
			}
		});
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0,0, 667, 392);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 667, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	
		JPanel panelDatos = new JPanel();
		panelDatos.setBounds(22, 11, 601, 154);
		contentPane.add(panelDatos);
		panelDatos.setLayout(null);
		
		JLabel lblIdUsuario = new JLabel("Id Usuario");
		lblIdUsuario.setBounds(29, 11, 93, 14);
		panelDatos.add(lblIdUsuario);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(29, 46, 114, 14);
		panelDatos.add(lblUsuario);
		
		jTextUsuario = new JTextField();
		jTextUsuario.setBounds(142, 43, 114, 20);
		panelDatos.add(jTextUsuario);
		jTextUsuario.setColumns(50);
		
		jTextIdUsuario = new JTextField();
		jTextIdUsuario.setBounds(142, 8, 114, 20);
		panelDatos.add(jTextIdUsuario);
		jTextIdUsuario.setEnabled(false);
		jTextIdUsuario.setColumns(10);
		
		//Se crea Panel que  contendrá el Jtable y los botones
		JPanel panelJtable = new JPanel();
		panelJtable.setBounds(22, 176, 601, 167);
		contentPane.add(panelJtable);
		panelJtable.setLayout(null);
		DefaultTableModel modelo = pintarEmpleados();
		//Adicionar manejo para el evento de seleccion
		
		
		
		//Adicionamos los botones para las acciones del GRID
		JButton btnInsertar = new JButton("Insertar");
		/**
		 * Método que implementará la acción cuando se de click sobre el botón Insertar
		 */
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(175, 133, 89, 23);
		panelJtable.add(btnEliminar);
		
		JButton btnEditar = new JButton("Editar");
		
		btnEditar.setBounds(298, 133, 89, 23);
		panelJtable.add(btnEditar);
		
		JButton btnGrabarEdicion = new JButton("Grabar Edicion");
		
		btnGrabarEdicion.setBounds(427, 133, 123, 23);
		panelJtable.add(btnGrabarEdicion);
		btnGrabarEdicion.setEnabled(false);
		
		JScrollPane scrollPaneTipoEmp = new JScrollPane();
		scrollPaneTipoEmp.setBounds(62, 33, 518, 77);
		panelJtable.add(scrollPaneTipoEmp);
		// Instanciamos el jtable
		jTableEmpleado = new JTable();
		scrollPaneTipoEmp.setViewportView(jTableEmpleado);
		jTableEmpleado.setForeground(Color.black);
		jTableEmpleado.setBorder(new LineBorder(new Color(0, 0, 0)));
		jTableEmpleado.setBackground(Color.WHITE);
		this.jTableEmpleado.setModel(modelo);
		jTextUsuario.setText("");
		
		comboBoxTipoUsuario = new JComboBox();
		comboBoxTipoUsuario.setBounds(142, 76, 114, 20);
		initComboBoxTipoUsuario();
		panelDatos.add(comboBoxTipoUsuario);
		
		JLabel lblTipoUsuario = new JLabel("Tipo Usuario");
		lblTipoUsuario.setBounds(29, 79, 93, 14);
		panelDatos.add(lblTipoUsuario);
		
		JLabel lblNombreLargo = new JLabel("Nombre Largo");
		lblNombreLargo.setBounds(29, 117, 93, 14);
		panelDatos.add(lblNombreLargo);
		
		txtNombreLargo = new JTextField();
		txtNombreLargo.setBounds(142, 114, 229, 20);
		panelDatos.add(txtNombreLargo);
		txtNombreLargo.setColumns(10);
		
		JCheckBox ckbxEsAdm = new JCheckBox("Es Administrador");
		ckbxEsAdm.setBounds(415, 18, 119, 23);
		panelDatos.add(ckbxEsAdm);
		
		comboBoxTipoInicio = new JComboBox();
		comboBoxTipoInicio.setBounds(415, 53, 119, 20);
		panelDatos.add(comboBoxTipoInicio);
		llenarComboTipoInicio();
		
		JLabel lblTipoInicio = new JLabel("Tipo Inicio");
		lblTipoInicio.setBounds(317, 56, 73, 14);
		panelDatos.add(lblTipoInicio);
		
		ButtonGroup grupoTipEmp = new ButtonGroup();
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				boolean validar = validarDatos();
				if (validar)
				{
					String usuario = jTextUsuario.getText();
					String nombreLargo = txtNombreLargo.getText();
					boolean administrador = ckbxEsAdm.isSelected();
					String tipoInicio = (String) comboBoxTipoInicio.getSelectedItem();
					TipoEmpleado tipEmp = (TipoEmpleado) comboBoxTipoUsuario.getSelectedItem();
					Usuario empleado = new Usuario(0, usuario, "", nombreLargo, tipEmp.getIdTipoEmpleado(),
							tipoInicio, administrador);
					EmpleadoCtrl empCtrl = new EmpleadoCtrl(PrincipalLogueo.habilitaAuditoria);
					int idTipoEmp = empCtrl.insertarEmpleado(empleado);
					DefaultTableModel modelo = pintarEmpleados();
					jTableEmpleado.setModel(modelo);
					jTableEmpleado.setModel(modelo);
					jTextIdUsuario.setText("");
					jTextUsuario.setText("");
					txtNombreLargo.setText("");
					ckbxEsAdm.setSelected(false);
					comboBoxTipoInicio.setSelectedIndex(0);
					comboBoxTipoUsuario.setSelectedIndex(0);
					
				}
			}
		});
		btnInsertar.setBounds(52, 133, 89, 23);
		panelJtable.add(btnInsertar);
		
		//Evento para definir las acciones para eliminar
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int filaSeleccionada = jTableEmpleado.getSelectedRow();
				//Hacemos la validación para decidir si se elimina o no
				String nombreUsuario = (String) jTableEmpleado.getValueAt(filaSeleccionada, 1);
				int idEmpleado = Integer.parseInt((String)jTableEmpleado.getValueAt(filaSeleccionada, 0));
				JOptionPane.showMessageDialog(null, "Esta seguro que se desea eliminar el Empleado con usuario  " +  nombreUsuario , "Eliminacion de Empleado ", JOptionPane.YES_NO_OPTION);
				EmpleadoCtrl empCtrl = new EmpleadoCtrl(PrincipalLogueo.habilitaAuditoria);
				empCtrl.eliminarEmpleado(idEmpleado);
				DefaultTableModel modelo = pintarEmpleados();
				jTableEmpleado.setModel(modelo);
			}
		});
		
		
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = jTableEmpleado.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar algún Menú Agrupador para editar " , "No ha Seleccionado para edición ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				filaSeleccionada = jTableEmpleado.getSelectedRow();
				int idEmpleado = Integer.parseInt((String)jTableEmpleado.getValueAt(filaSeleccionada, 0));
				EmpleadoCtrl empCtrl = new EmpleadoCtrl(PrincipalLogueo.habilitaAuditoria);
				Usuario usuarioEditar = empCtrl.obtenerEmpleado(idEmpleado);
				jTextIdUsuario.setText(Integer.toString(usuarioEditar.getIdUsuario()));
				jTextUsuario.setText(usuarioEditar.getNombreUsuario());
				txtNombreLargo.setText(usuarioEditar.getNombreLargo());
				if (usuarioEditar.isAdministrador())
				{
					ckbxEsAdm.setSelected(true);
				}
				for (int i = 0; i < comboBoxTipoUsuario.getModel().getSize(); i++) {
					TipoEmpleado object = (TipoEmpleado)comboBoxTipoUsuario.getModel().getElementAt(i);
					if(object.getIdTipoEmpleado()==usuarioEditar.getidTipoEmpleado()){
						comboBoxTipoUsuario.setSelectedItem(object);
					}
				}
				for (int i = 0; i < comboBoxTipoInicio.getModel().getSize(); i++) {
					String object = (String)comboBoxTipoInicio.getModel().getElementAt(i);
					if(object.equals(usuarioEditar.getTipoInicio())){
						comboBoxTipoInicio.setSelectedItem(object);
					}
				}
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
					int idUsuario = Integer.parseInt(jTextIdUsuario.getText());
					String usuario = jTextUsuario.getText();
					String nombreLargo = txtNombreLargo.getText();
					boolean administrador = ckbxEsAdm.isSelected();
					String tipoInicio = (String) comboBoxTipoInicio.getSelectedItem();
					TipoEmpleado tipEmp = (TipoEmpleado) comboBoxTipoUsuario.getSelectedItem();
					Usuario empEditar = new Usuario(idUsuario, usuario, "", nombreLargo, tipEmp.getIdTipoEmpleado(),
							tipoInicio, administrador);
					EmpleadoCtrl empCtrl = new EmpleadoCtrl(PrincipalLogueo.habilitaAuditoria);
					boolean respuesta = empCtrl.editarEmpleado(empEditar);
					if (respuesta)
					{
						JOptionPane.showMessageDialog(null, "Se ha editado correctamente el registro " , "Confirmación Edición", JOptionPane.OK_OPTION);
						DefaultTableModel modelo = pintarEmpleados();
						jTableEmpleado.setModel(modelo);
						jTextIdUsuario.setText("");
						jTextUsuario.setText("");
						txtNombreLargo.setText("");
						ckbxEsAdm.setSelected(false);
						comboBoxTipoInicio.setSelectedIndex(0);
						comboBoxTipoUsuario.setSelectedIndex(0);
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
	String usuario = jTextUsuario.getText();
	if(usuario == "")
	{
		JOptionPane.showMessageDialog(null, "Valor del campo usuario de Empleado es necesario", "Falta Información", JOptionPane.ERROR_MESSAGE);
		return(false);
	}
//	if(comboBoxTipoUsuario.getSelectedIndex() <= 0)
//	{
//		JOptionPane.showMessageDialog(null, "Debe seleccionar el tipo de Empleado", "Falta Información", JOptionPane.ERROR_MESSAGE);
//		return(false);
//	}
	String nombreLargo = txtNombreLargo.getText();
	if(nombreLargo == "")
	{
		JOptionPane.showMessageDialog(null, "Valor del campo nombre largo de Empleado es necesario", "Falta Información", JOptionPane.ERROR_MESSAGE);
		return(false);
	}
	
//	if(comboBoxTipoInicio.getSelectedIndex() <= 0)
//	{
//		JOptionPane.showMessageDialog(null, "Debe seleccionar el tipo de inicio del empleado", "Falta Información", JOptionPane.ERROR_MESSAGE);
//		return(false);
//	}
	return(true);
}

public void llenarComboTipoInicio()
{
	//Ingresamos los items que va a tener disponible para configurar el inicio
	comboBoxTipoInicio.addItem("Ventana Menús");
	comboBoxTipoInicio.addItem("Maestro Pedidos");
	comboBoxTipoInicio.addItem("Comanda Pedidos");
	comboBoxTipoInicio.setSelectedIndex(0);
}

public void initComboBoxTipoUsuario()
{
	EmpleadoCtrl empCtrl = new EmpleadoCtrl(PrincipalLogueo.habilitaAuditoria);
	ArrayList<TipoEmpleado> tiposEmpleado = empCtrl.obtenerTipoEmpleadoObj();
	for(int i = 0; i<tiposEmpleado.size();i++)
	{
		TipoEmpleado fila = (TipoEmpleado)  tiposEmpleado.get(i);
		comboBoxTipoUsuario.addItem(fila);
	}
	comboBoxTipoUsuario.setSelectedIndex(0);
}

}


