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
import javax.swing.SwingUtilities;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import capaControlador.EmpleadoCtrl;
import capaControlador.ParametrosDireccionCtrl;
import capaControlador.AutenticacionCtrl;
import capaControlador.BiometriaCtrl;
import capaModelo.AgrupadorMenu;
import capaModelo.Municipio;
import capaModelo.TipoEmpleado;
import capaModelo.Usuario;

import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Component;

import javax.swing.border.LineBorder;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JScrollBar;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;
import javax.swing.border.CompoundBorder;

/**
 * Esta clase permitir� la administraci�n desde el POS de los Usuarios del Sistema.
 * @author 57314
 *
 */
public class VentSegCrearEmpleado extends JDialog {

	private JPanel contentPane;
	private JTextField jTextIdUsuario;
	private JTextField jTextUsuario;
	private JScrollPane scrollPane;
	private JTable jTableEmpleado;
	private JTextField txtNombreLargo;
	private JComboBox comboBoxTipoUsuario;
	private JComboBox comboBoxTipoInicio;
	private JLabel lblModo;
	Window ventanaPadre;
	EmpleadoCtrl empCtrl = new EmpleadoCtrl(PrincipalLogueo.habilitaAuditoria);
	private JTextField txtFiltroNomEmpleado;
	JButton btnAsignarPassword;
	JButton btnIdentificarEmpleado;
	JButton btnEnrolarBiometria;
	JButton btnAsignarClaveRapida;
	BiometriaCtrl bioCtrl = new BiometriaCtrl(PrincipalLogueo.habilitaAuditoria);
	VentSegCrearEmpleado framePrincipal;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentSegCrearEmpleado frame = new VentSegCrearEmpleado(null, false);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * M�todo que se encarga de retornar los menus agrupadores y de pintarlos en el Jtable correspondiente la informaci�n
	 * retornada de base de datos.
	 */
	public void pintarEmpleados()
	{
		Object[] columnsName = new Object[5];
		String filtroNombre = txtFiltroNomEmpleado.getText();
		columnsName[0] = "Id ";
        columnsName[1] = "Nombre";
        columnsName[2] = "Nombre Largo";
        columnsName[3] = "Administrador";
        columnsName[4] = "Tipo Inicio";
        ArrayList<Object> empleados;
        //validamos si hay o no filtro
        if(filtroNombre.equals(new String("")))
        {
        	empleados = empCtrl.obtenerEmpleadosGeneral();
        }else
        {
        	empleados = empCtrl.obtenerEmpleadosGeneralFiltroNom(filtroNombre);
        }
        
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < empleados.size();y++)
		{
			String[] fila =(String[]) empleados.get(y);
			modelo.addRow(fila);
		}
		
		this.jTableEmpleado.setModel(modelo);
		
		
	}
	
	/**
	 * Create the frame.
	 * Se documentan todas las acciones  a seguir cuando se instancia el frame para el CRUD de men� Agrupador.
	 */
	public VentSegCrearEmpleado(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("DEFINICI\u00D3N  DE EMPLEADOS");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
			}
		});
		ventanaPadre = this;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0,0, 800, 547);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 800, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		framePrincipal = this;
		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
		JPanel panelDatos = new JPanel();
		panelDatos.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panelDatos.setBounds(10, 11, 764, 224);
		contentPane.add(panelDatos);
		panelDatos.setLayout(null);
		
		JLabel lblIdUsuario = new JLabel("Id Usuario");
		lblIdUsuario.setBounds(101, 39, 93, 14);
		panelDatos.add(lblIdUsuario);
		
		JLabel lblUsuario = new JLabel("Usuario(C\u00E9dula)");
		lblUsuario.setBounds(101, 77, 114, 14);
		panelDatos.add(lblUsuario);
		
		jTextUsuario = new JTextField();
		jTextUsuario.setToolTipText("C\u00E9dulad el usuario/empleado");
		jTextUsuario.setBounds(238, 71, 114, 20);
		panelDatos.add(jTextUsuario);
		jTextUsuario.setColumns(50);
		
		jTextIdUsuario = new JTextField();
		jTextIdUsuario.setToolTipText("Id interno del usuario");
		jTextIdUsuario.setBounds(238, 36, 114, 20);
		panelDatos.add(jTextIdUsuario);
		jTextIdUsuario.setEnabled(false);
		jTextIdUsuario.setColumns(10);
		
		//Se crea Panel que  contendr� el Jtable y los botones
		JPanel panelJtable = new JPanel();
		panelJtable.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panelJtable.setBounds(10, 246, 764, 257);
		contentPane.add(panelJtable);
		panelJtable.setLayout(null);
		
		
		JButton btnEditar = new JButton("Editar");
		
		btnEditar.setBounds(199, 177, 146, 23);
		panelJtable.add(btnEditar);
		
		JButton btnGrabarEdicion = new JButton("Grabar Edicion");
		
		btnGrabarEdicion.setBounds(403, 177, 146, 23);
		panelJtable.add(btnGrabarEdicion);
		btnGrabarEdicion.setEnabled(false);
		
		JScrollPane scrollPaneTipoEmp = new JScrollPane();
		scrollPaneTipoEmp.setBounds(10, 44, 744, 122);
		panelJtable.add(scrollPaneTipoEmp);
		// Instanciamos el jtable
		jTableEmpleado = new JTable();
		scrollPaneTipoEmp.setViewportView(jTableEmpleado);
		jTableEmpleado.setForeground(Color.black);
		jTableEmpleado.setBorder(new LineBorder(new Color(0, 0, 0)));
		jTableEmpleado.setBackground(Color.WHITE);
		jTextUsuario.setText("");
		
		comboBoxTipoUsuario = new JComboBox();
		comboBoxTipoUsuario.setToolTipText("Tipo de Empleado");
		comboBoxTipoUsuario.setBounds(238, 104, 114, 20);
		initComboBoxTipoUsuario();
		panelDatos.add(comboBoxTipoUsuario);
		
		JLabel lblTipoUsuario = new JLabel("Tipo Empleado");
		lblTipoUsuario.setBounds(101, 110, 93, 14);
		panelDatos.add(lblTipoUsuario);
		
		JLabel lblNombreLargo = new JLabel("Nombre Largo");
		lblNombreLargo.setBounds(101, 148, 93, 14);
		panelDatos.add(lblNombreLargo);
		
		txtNombreLargo = new JTextField();
		txtNombreLargo.setToolTipText("Nombre completo del Empleado");
		txtNombreLargo.setBounds(238, 142, 229, 20);
		panelDatos.add(txtNombreLargo);
		txtNombreLargo.setColumns(10);
		
		JCheckBox ckbxEsAdm = new JCheckBox("Es Administrador o Auxiliar Adm");
		ckbxEsAdm.setToolTipText("Es administrador?");
		ckbxEsAdm.setBounds(429, 35, 248, 23);
		panelDatos.add(ckbxEsAdm);
		
		comboBoxTipoInicio = new JComboBox();
		comboBoxTipoInicio.setToolTipText("Tipo de inicio del usuario");
		comboBoxTipoInicio.setBounds(511, 81, 119, 20);
		panelDatos.add(comboBoxTipoInicio);
		llenarComboTipoInicio();
		
		JLabel lblTipoInicio = new JLabel("Tipo Inicio");
		lblTipoInicio.setBounds(413, 84, 73, 14);
		panelDatos.add(lblTipoInicio);
		
		lblModo = new JLabel("MODO: INSERCI�N");
		lblModo.setBounds(23, 11, 170, 14);
		panelDatos.add(lblModo);
		//Adicionar manejo para el evento de seleccion
		
		
		
		//Adicionamos los botones para las acciones del GRID
		JButton btnInsertar = new JButton("Insertar");
		btnInsertar.setBounds(90, 173, 146, 40);
		panelDatos.add(btnInsertar);
		
		JButton btnSalir = new JButton("SALIR");
		btnSalir.setBounds(307, 173, 146, 40);
		panelDatos.add(btnSalir);
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		btnIdentificarEmpleado = new JButton("Identificar Empleado");
		btnIdentificarEmpleado.setBounds(519, 175, 187, 36);
		panelDatos.add(btnIdentificarEmpleado);
		btnIdentificarEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentSegRegIdentiEmpleado ventRegEventoEmp = new VentSegRegIdentiEmpleado((JDialog)ventanaPadre, true);
				ventRegEventoEmp.setVisible(true);
			}
		});
		btnIdentificarEmpleado.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnIdentificarEmpleado.setEnabled(true);
		
		JCheckBox ckbxEsEmpleado = new JCheckBox("Es Empleado");
		ckbxEsEmpleado.setSelected(true);
		ckbxEsEmpleado.setToolTipText("Es administrador?");
		ckbxEsEmpleado.setBounds(429, 7, 248, 23);
		panelDatos.add(ckbxEsEmpleado);
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) arg0.getSource());
				boolean validar = validarDatos();
				if (validar)
				{
					String usuario = jTextUsuario.getText();
					String nombreLargo = txtNombreLargo.getText();
					boolean administrador = ckbxEsAdm.isSelected();
					int esEmpleado = 0;
					if(ckbxEsEmpleado.isSelected())
					{
						esEmpleado = 1;
					}
					String tipoInicio = (String) comboBoxTipoInicio.getSelectedItem();
					TipoEmpleado tipEmp = (TipoEmpleado) comboBoxTipoUsuario.getSelectedItem();
					Usuario empleado = new Usuario(0, usuario, usuario, nombreLargo, tipEmp.getIdTipoEmpleado(),
							tipoInicio, administrador);
					empleado.setEsEmpleado(esEmpleado);
					int idTipoEmp = empCtrl.insertarEmpleadoGeneral(empleado);
					if(idTipoEmp > 0 )
					{
						JOptionPane.showMessageDialog(ventanaPadre, "Se ha creado correctamente el usuario, recuerde que si es Domiciliario, administrador o auxiliar debe asignarle clave r�pida." , "Confirmaci�n Creaci�n", JOptionPane.OK_OPTION);
					}
					pintarEmpleados();
					jTextIdUsuario.setText("");
					jTextUsuario.setText("");
					txtNombreLargo.setText("");
					ckbxEsAdm.setSelected(false);
					ckbxEsEmpleado.setSelected(true);
					comboBoxTipoInicio.setSelectedIndex(0);
					comboBoxTipoUsuario.setSelectedIndex(0);
					
				}
			}
		});
		
		ButtonGroup grupoTipEmp = new ButtonGroup();
		
		JButton btnFiltrarEmpleado = new JButton("Filtrar por Nombre Largo Empleado");
		btnFiltrarEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Realizamos las acciones correspondientes a filtrar una vez seleccione el bot�n
				pintarEmpleados();
			}
		});
		btnFiltrarEmpleado.setBounds(461, 11, 293, 23);
		panelJtable.add(btnFiltrarEmpleado);
		
		txtFiltroNomEmpleado = new JTextField();
		txtFiltroNomEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pintarEmpleados();
				
			}
		});
		txtFiltroNomEmpleado.setBounds(344, 12, 107, 20);
		panelJtable.add(txtFiltroNomEmpleado);
		txtFiltroNomEmpleado.setColumns(10);
		
		btnAsignarPassword = new JButton("ASIGNAR PASSWORD");
		btnAsignarPassword.setBounds(92, 211, 187, 36);
		panelJtable.add(btnAsignarPassword);
		btnAsignarPassword.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAsignarPassword.setEnabled(false);
		
		btnEnrolarBiometria = new JButton("ENROLAR BIOMETRIA");
		btnEnrolarBiometria.setBounds(315, 211, 187, 36);
		panelJtable.add(btnEnrolarBiometria);
		btnEnrolarBiometria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//En este punto realizamos el llamado a la pantalla de enrolamiento
				boolean estaEnrolado = bioCtrl.estaEnroladoEmpleado(Integer.parseInt(jTextIdUsuario.getText()));
		        if(estaEnrolado)
		        {
		        	int resp = JOptionPane.showConfirmDialog(ventanaPadre, "EL EMPLEADO YA SE ENCUENTRA ENROLADO, desea eliminar el actual enrolamiento y repetir el proceso?", "Confirmaci�n reescribir Enrolamiento." , JOptionPane.YES_NO_OPTION);
		        	if(resp == 0)
		        	{
		        		bioCtrl.eliminarEnroladoEmpleado(Integer.parseInt(jTextIdUsuario.getText()));
		        		VentSegCapturaHuella ventCapHuella = new VentSegCapturaHuella((JDialog) ventanaPadre, true, Integer.parseInt(jTextIdUsuario.getText()));
						ventCapHuella.setVisible(true);
		        	}else
		        	{
		        		
		        	}
		        }else
		        {
		        	VentSegCapturaHuella ventCapHuella = new VentSegCapturaHuella((JDialog) ventanaPadre, true, Integer.parseInt(jTextIdUsuario.getText()));
					ventCapHuella.setVisible(true);
		        }
				
			}
		});
		btnEnrolarBiometria.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEnrolarBiometria.setEnabled(false);
		
		btnAsignarClaveRapida = new JButton("Asignar Clave R\u00E1pida");
		btnAsignarClaveRapida.setBounds(527, 211, 187, 36);
		panelJtable.add(btnAsignarClaveRapida);
		btnAsignarClaveRapida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) arg0.getSource());
				JOptionPane opt = new JOptionPane("Recuerde que la clave r�pida solo es generada para Administradores, auxiliares y Domiciliarios.", JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}); // no buttons
			     final JDialog dlg = opt.createDialog("CAMBIO DE CLAVE R�PIDA");
			     new Thread(new Runnable()
			           {
			             public void run()
			             {
			               try
			               {
			                 Thread.sleep(2500);
			                 dlg.dispose();

			               }
			               catch ( Throwable th )
			               {
			                 
			               }
			             }
			           }).start();
			     dlg.setVisible(true);
				//Tomamos el idUsuario al cual le vamos a asignar la clave r�pida
				int idUsuario = Integer.parseInt(jTextIdUsuario.getText());
				Usuario usuarioIdentificado = empCtrl.obtenerEmpleado(idUsuario);
				//En este punto llamaremos la nueva pantalla para el ingreso de la clave
		    	VentSegAsignacionClaveRapida ventCambioClave = new VentSegAsignacionClaveRapida(null, usuarioIdentificado ,true);
		    	ventCambioClave.setVisible(true);
			}
		});
		btnAsignarClaveRapida.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAsignarClaveRapida.setEnabled(false);
		
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = jTableEmpleado.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar alg�n Men� Agrupador para editar " , "No ha Seleccionado para edici�n ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				filaSeleccionada = jTableEmpleado.getSelectedRow();
				int idEmpleado = Integer.parseInt((String)jTableEmpleado.getValueAt(filaSeleccionada, 0));
				Usuario usuarioEditar = empCtrl.obtenerEmpleadoGeneral(idEmpleado);
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
				btnInsertar.setEnabled(false);
				btnGrabarEdicion.setEnabled(true);
				btnAsignarPassword.setEnabled(true);
				btnEnrolarBiometria.setEnabled(true);
				btnAsignarClaveRapida.setEnabled(true);
				lblModo.setText("MODO: EDICI�N");
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
					
					boolean respuesta = empCtrl.editarEmpleadoGeneral(empEditar);
					if (respuesta)
					{
						JOptionPane.showMessageDialog(null, "Se ha editado correctamente el registro " , "Confirmaci�n Edici�n", JOptionPane.OK_OPTION);
						pintarEmpleados();
						jTextIdUsuario.setText("");
						jTextUsuario.setText("");
						txtNombreLargo.setText("");
						ckbxEsAdm.setSelected(false);
						comboBoxTipoInicio.setSelectedIndex(0);
						comboBoxTipoUsuario.setSelectedIndex(0);
						btnInsertar.setEnabled(true);
						btnGrabarEdicion.setEnabled(false);
						btnAsignarPassword.setEnabled(false);
						btnEnrolarBiometria.setEnabled(false);
						btnAsignarClaveRapida.setEnabled(false);
						lblModo.setText("MODO: INSERCI�N");
					}
				}
				
			}
		});
		pintarEmpleados();
		
	}
	
	
public boolean validarDatos()
{
	String usuario = jTextUsuario.getText().trim();
	if(usuario.equals(new String("")))
	{
		JOptionPane.showMessageDialog(null, "Valor del campo usuario de Empleado es necesario", "Falta Informaci�n", JOptionPane.ERROR_MESSAGE);
		return(false);
	}
//	if(comboBoxTipoUsuario.getSelectedIndex() <= 0)
//	{
//		JOptionPane.showMessageDialog(null, "Debe seleccionar el tipo de Empleado", "Falta Informaci�n", JOptionPane.ERROR_MESSAGE);
//		return(false);
//	}
	String nombreLargo = txtNombreLargo.getText();
	if(nombreLargo.equals(new String("")))
	{
		JOptionPane.showMessageDialog(null, "Valor del campo nombre largo de Empleado es necesario", "Falta Informaci�n", JOptionPane.ERROR_MESSAGE);
		return(false);
	}
	
//	if(comboBoxTipoInicio.getSelectedIndex() <= 0)
//	{
//		JOptionPane.showMessageDialog(null, "Debe seleccionar el tipo de inicio del empleado", "Falta Informaci�n", JOptionPane.ERROR_MESSAGE);
//		return(false);
//	}
	return(true);
}

public void llenarComboTipoInicio()
{
	//Ingresamos los items que va a tener disponible para configurar el inicio
	comboBoxTipoInicio.addItem("Ventana Men�s");
	comboBoxTipoInicio.addItem("Maestro Pedidos");
	comboBoxTipoInicio.addItem("Comanda Pedidos");
	comboBoxTipoInicio.setSelectedIndex(0);
}

public void initComboBoxTipoUsuario()
{
	ArrayList<TipoEmpleado> tiposEmpleado = empCtrl.obtenerTipoEmpleadoGeneralObj();
	for(int i = 0; i<tiposEmpleado.size();i++)
	{
		TipoEmpleado fila = (TipoEmpleado)  tiposEmpleado.get(i);
		comboBoxTipoUsuario.addItem(fila);
	}
	comboBoxTipoUsuario.setSelectedIndex(0);
}
}


