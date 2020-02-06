package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import capaControlador.EmpleadoCtrl;
import capaControlador.OperacionesTiendaCtrl;
import capaControlador.ParametrosCtrl;
import capaControlador.PedidoCtrl;
import capaDAO.GeneralDAO;
import capaModelo.Correo;
import capaModelo.EmpleadoTemporalDia;
import capaModelo.EmpresaTemporal;
import capaModelo.FechaSistema;
import capaModelo.Municipio;
import capaModelo.Parametro;
import capaModelo.Tienda;
import capaModelo.Usuario;
import utilidades.ControladorEnvioCorreo;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class VentPedEmpleadoTemporal extends JDialog {

	private JPanel contentPane;
	private JTextField txtFechaSistema;
	private ParametrosCtrl parCtrl = new ParametrosCtrl(PrincipalLogueo.habilitaAuditoria);
	private OperacionesTiendaCtrl operTiendaCtrl = new OperacionesTiendaCtrl(PrincipalLogueo.habilitaAuditoria);
	private PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	private EmpleadoCtrl empCtrl = new EmpleadoCtrl(PrincipalLogueo.habilitaAuditoria);
	private int tiempoPedido = 0;
	Thread h1;
	private boolean banderaEjecucion = true;
	private JButton btnSalir;
	private JTextField txtIdentificacion;
	boolean estadoTienda;
	//Definición de los estados de producto
	//variable para estado en ruta domicilios
	private final long estEnRutaDom;
	//variable para estado pendiente domicilios
	private final long estPenDomicilio;
	private FechaSistema fecha;
	//Variable booleana que almacenará el cambio de pedido para domicilio o no domicilio, se inicializa en true
	boolean domicilio = true;
	private JTextField txtNombreCompleto;
	private JTextField txtTelefono;
	private JComboBox cmbBoxTemporales;
	JComboBox cmbBoxHoraIngreso;
	JComboBox cmbBoxMinIngreso;
	JComboBox cmbBoxHoraSalida;
	JComboBox cmbBoxMinSalida;
	private JDialog jDialogPadre;
	private JTable tableIngEmpTemp;
	private JTextField txtEmpleado;
	//variable que nos indicará que se está editando y nos demarcará el id
	private int idUsuarioEdicion = 0;
	JComboBox cmbBoxEmpresa;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentPedEmpleadoTemporal frame = new VentPedEmpleadoTemporal(null, true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public void fijarValorTiempoPedido()
	{
		
	}
	
	
	public void actualizarEstadoPedidosTienda()
	{
	}
	
	/**
	 * Create the frame.
	 */
	public VentPedEmpleadoTemporal(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("ADMINISTRACI\u00D3N DE TIEMPOS DE PEDIDO");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);	
		setBounds(100, 100, 859, 740);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 870, 740);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		jDialogPadre = this;
		txtFechaSistema = new JTextField();
		txtFechaSistema.setEditable(false);
		txtFechaSistema.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtFechaSistema.setBounds(635, 11, 209, 41);
		contentPane.add(txtFechaSistema);
		//Mostramos la fecha del sistema
		fecha = pedCtrl.obtenerFechasSistema();
		String fechaSis = fecha.getFechaApertura();
		txtFechaSistema.setText(fechaSis);
		txtFechaSistema.setColumns(20);
		//Inicilizamos las variables con constantes de los valores de pedidos
		Parametro parametro = parCtrl.obtenerParametro("ENRUTADOMICILIO");
		long valNum = 0;
		try
		{
			valNum = (long) parametro.getValorNumerico();
		}catch(Exception e)
		{
			System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE PEDIDOS EN RUTA");
			valNum = 0;
		}
		estEnRutaDom = valNum;
		//
		parametro = parCtrl.obtenerParametro("EMPACADODOMICILIO");
		try
		{
			valNum = (long) parametro.getValorNumerico();
		}catch(Exception e)
		{
			System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE PEDIDOS PENDIENTES");
			valNum = 0;
		}
		estPenDomicilio = valNum;
		fecha = pedCtrl.obtenerFechasSistema();
		
		JButton btnRegistrarPersonal = new JButton("INGRESO PERSONAL TEMPORAL");
		btnRegistrarPersonal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) arg0.getSource());
				if(idUsuarioEdicion == 0)
				{
					
					
					//Creamos un método para validar el ingreso 
					String respValidacion = validarInfIngresoTemporal();
					if(respValidacion.length() == 0)
					{
						EmpleadoTemporalDia empIngreso =  obtenerInformacionDigitada(ventanaPadre);
						Usuario  usuarioSel = (Usuario)cmbBoxTemporales.getSelectedItem();
						//Validamos que se haya seleccionado a alguien
						if(usuarioSel.getIdUsuario() == 0)
						{
							JOptionPane.showMessageDialog(ventanaPadre, "Existen errores en la validación. No ha seleccionado el Domiciliario con el cual trabajará" , "Errores en la Validación", JOptionPane.ERROR_MESSAGE);
							return;
						}
						int resp = JOptionPane.showConfirmDialog(ventanaPadre, "Desea confirmar el ingreso del empleado " + empIngreso.getNombre() + " identificado con " + empIngreso.getIdentificacion() + " con el teléfono " + empIngreso.getTelefono() + " y de la empresa " + empIngreso.getEmpresa() + " COMO " + usuarioSel.getNombreLargo() + ". Está seguro de continuar?", "Confirmación Ingreso Personal Temporal." , JOptionPane.YES_NO_OPTION);
						if (resp == 0)
						{
							//Realizamos la inserción del Ingreso del empleado temporal
							boolean ingreso = empCtrl.InsertarEmpleadoTemporalDia(empIngreso);
							if(ingreso)
							{
								JOptionPane.showMessageDialog(ventanaPadre, "Se realizó correctamente la asociación del personal temporal al usuario " + usuarioSel.getNombreLargo() , "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
								limpiarCamposIngreso();
								pintarReportePersonaTemp();
							}
						}
						
					}else
					{
						
						JOptionPane.showMessageDialog(ventanaPadre, "Existen errores en la validación." + respValidacion , "Errores en la Validación", JOptionPane.ERROR_MESSAGE);
						return;
						
					}
				}else
				{
					JOptionPane.showMessageDialog(ventanaPadre, "No se puede insertar la pantalla está en modo Edición.", "Errores en la Validación", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		btnRegistrarPersonal.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnRegistrarPersonal.setBounds(51, 434, 336, 63);
		contentPane.add(btnRegistrarPersonal);
		
		btnSalir = new JButton("SALIR");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				banderaEjecucion = false;
				dispose();
			}
		});
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnSalir.setBounds(397, 435, 182, 65);
		contentPane.add(btnSalir);
		
		JLabel lblFechaSistema = new JLabel("FECHA DEL SISTEMA");
		lblFechaSistema.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblFechaSistema.setBounds(444, 20, 185, 23);
		contentPane.add(lblFechaSistema);
		
		txtIdentificacion = new JTextField();
		txtIdentificacion.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtIdentificacion.setBounds(186, 78, 209, 41);
		contentPane.add(txtIdentificacion);
		txtIdentificacion.setColumns(10);
		
		JButton btnBuscarEmpleado = new JButton("<html><center>Buscar Identificaci\u00F3n</center></html>");
		btnBuscarEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Realizaremos la búsqueda de la identificación
				String identiBuscar = txtIdentificacion.getText();
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) arg0.getSource());
				if(identiBuscar.length() == 0)
				{
					JOptionPane.showMessageDialog(ventanaPadre, "El campo identificación a buscar está vacíó" , "No hay identificación para buscar", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//Realizamos la búsqueda de si el empleado existe con esa identificación en la historia
				EmpleadoTemporalDia empleadoBuscar = empCtrl.consultarEmpleadoTemporal(identiBuscar);
				if(empleadoBuscar.getId() > 0)
				{
					int resp = JOptionPane.showConfirmDialog(ventanaPadre, "Fue encontrada la persona " + empleadoBuscar.getNombre() + ". Está seguro que es la persona que quiere seleccionar?", "Confirmación selección persona." , JOptionPane.YES_NO_OPTION);
					if (resp == 0)
					{
						//Acciones para selección 
						txtNombreCompleto.setText(empleadoBuscar.getNombre());
						fijarValorComboEmpresa(empleadoBuscar.getIdEmpresa());
						txtTelefono.setText(empleadoBuscar.getTelefono());
					}
					
				}else 
				{
					JOptionPane.showMessageDialog(ventanaPadre, "No se encontró ninguna persona con esa identificación!!" , "No se encontraron personas", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnBuscarEmpleado.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnBuscarEmpleado.setBounds(469, 74, 239, 63);
		contentPane.add(btnBuscarEmpleado);
		
		cmbBoxTemporales = new JComboBox();
		cmbBoxTemporales.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cmbBoxTemporales.setBounds(186, 152, 209, 44);
		contentPane.add(cmbBoxTemporales);
		
		JLabel lblIdentificacin = new JLabel("Identificaci\u00F3n ");
		lblIdentificacin.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblIdentificacin.setBounds(25, 78, 129, 41);
		contentPane.add(lblIdentificacin);
		
		JLabel lblUsuarioSistema = new JLabel("Usuario Sistema");
		lblUsuarioSistema.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblUsuarioSistema.setBounds(25, 152, 138, 41);
		contentPane.add(lblUsuarioSistema);
		
		txtNombreCompleto = new JTextField();
		txtNombreCompleto.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtNombreCompleto.setColumns(10);
		txtNombreCompleto.setBounds(238, 222, 368, 41);
		contentPane.add(txtNombreCompleto);
		
		JLabel lblNombrecompleto = new JLabel("NombreCompleto");
		lblNombrecompleto.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNombrecompleto.setBounds(25, 222, 185, 41);
		contentPane.add(lblNombrecompleto);
		
		txtTelefono = new JTextField();
		txtTelefono.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(238, 297, 154, 41);
		contentPane.add(txtTelefono);
		
		JLabel lblTelfono = new JLabel("Tel\u00E9fono");
		lblTelfono.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTelfono.setBounds(25, 299, 185, 41);
		contentPane.add(lblTelfono);
		
		JLabel lblEmpresa = new JLabel("Empresa");
		lblEmpresa.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblEmpresa.setBounds(444, 297, 185, 41);
		contentPane.add(lblEmpresa);
		
			
		JButton btnRevisarAsignaciones = new JButton("REVISAR ASIGNACIONES");
		btnRevisarAsignaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentPedReportes ventReportePorciones = new VentPedReportes(jDialogPadre,true, fechaSis, 7);
				ventReportePorciones.setVisible(true);
			}
		});
		btnRevisarAsignaciones.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnRevisarAsignaciones.setBounds(589, 436, 255, 63);
		contentPane.add(btnRevisarAsignaciones);
		
		JLabel lblHoraIngreso = new JLabel("Hora Ingreso");
		lblHoraIngreso.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblHoraIngreso.setBounds(25, 370, 129, 41);
		contentPane.add(lblHoraIngreso);
		
		cmbBoxHoraIngreso = new JComboBox();
		cmbBoxHoraIngreso.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cmbBoxHoraIngreso.setBounds(172, 368, 95, 44);
		contentPane.add(cmbBoxHoraIngreso);
		
		cmbBoxMinIngreso = new JComboBox();
		cmbBoxMinIngreso.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cmbBoxMinIngreso.setBounds(277, 367, 95, 44);
		contentPane.add(cmbBoxMinIngreso);
		
		JLabel lblHoraSalida = new JLabel("Hora Salida");
		lblHoraSalida.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblHoraSalida.setBounds(458, 370, 129, 41);
		contentPane.add(lblHoraSalida);
		
		cmbBoxHoraSalida = new JComboBox();
		cmbBoxHoraSalida.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cmbBoxHoraSalida.setBounds(575, 370, 95, 44);
		contentPane.add(cmbBoxHoraSalida);
		
		cmbBoxMinSalida = new JComboBox();
		cmbBoxMinSalida.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cmbBoxMinSalida.setBounds(681, 368, 95, 44);
		contentPane.add(cmbBoxMinSalida);
		
		JScrollPane scrollPanEmpTemp = new JScrollPane();
		scrollPanEmpTemp.setBounds(51, 508, 793, 101);
		contentPane.add(scrollPanEmpTemp);
		
		tableIngEmpTemp = new JTable();
		scrollPanEmpTemp.setViewportView(tableIngEmpTemp);
		JButton btnEditar = new JButton("EDITAR");
		JButton btnConfirmarEdicin = new JButton("CONFIRMAR EDICI\u00D3N");
		btnConfirmarEdicin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Se realiza la revisión de la edición
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) e.getSource());
				if(idUsuarioEdicion > 0)
				{
					//Creamos un método para validar el ingreso 
					String respValidacion = validarInfIngresoTemporal();
					if(respValidacion.length() == 0)
					{
						EmpleadoTemporalDia empIngreso =  obtenerInformacionDigitada(ventanaPadre);
						empIngreso.setId(idUsuarioEdicion);
						
						int resp = JOptionPane.showConfirmDialog(ventanaPadre, "Desea confirmar la edicion del empleado " + empIngreso.getNombre() + " identificado con " + empIngreso.getIdentificacion() + " con el teléfono " + empIngreso.getTelefono() + " y de la empresa " + empIngreso.getEmpresa() + " COMO " + txtEmpleado.getText() + ". Está seguro de continuar?", "Confirmación Ingreso Personal Temporal." , JOptionPane.YES_NO_OPTION);
						if (resp == 0)
						{
							//Realizamos la inserción del Ingreso del empleado temporal
							boolean editar = empCtrl.editarEmpleadoTemporalDia(empIngreso);
							if(editar)
							{
								JOptionPane.showMessageDialog(ventanaPadre, "Se realizó correctamente la edición del personal temporal al usuario " + txtEmpleado.getText() , "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
								limpiarCamposIngreso();
								pintarReportePersonaTemp();
								btnConfirmarEdicin.setEnabled(false);
								btnEditar.setEnabled(true);
								btnRegistrarPersonal.setEnabled(true);
								cmbBoxTemporales.setSelectedIndex(0);
								cmbBoxTemporales.setEnabled(true);
								txtEmpleado.setText("");
								idUsuarioEdicion = 0;
								limpiarCamposIngreso();
								pintarReportePersonaTemp();
							}
						}
						
					}else
					{
						
						JOptionPane.showMessageDialog(ventanaPadre, "Existen errores en la validación." + respValidacion , "Errores en la Validación", JOptionPane.ERROR_MESSAGE);
						return;
						
					}
				}else
				{
					JOptionPane.showMessageDialog(ventanaPadre, "No se puede realizar la Edición, dado que la pantalla está en modo inserción." , "Errores en la Validación", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int filaSeleccionada = tableIngEmpTemp.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar algún registro para editar " , "No ha Seleccionado Registro para edición ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int idEmpleadoSel = Integer.parseInt((String)tableIngEmpTemp.getValueAt(filaSeleccionada, 6));
				//Recuperamos el registro completo de base de datos
				EmpleadoTemporalDia empTempSel = empCtrl.consultarEmpleadoTemporalFecha(idEmpleadoSel, fecha.getFechaApertura());
				//Fijamos los valores en pantalla
				txtIdentificacion.setText(empTempSel.getIdentificacion());
				txtNombreCompleto.setText(empTempSel.getNombre());
				txtTelefono.setText(empTempSel.getTelefono());
				fijarValorComboEmpresa(empTempSel.getIdEmpresa());
				//Nos falta fijar los valores de los combos
				//Constuimos métodos para este fin de fijar los valores
				fijarValoresCombos(empTempSel.getHoraIngreso(), empTempSel.getHoraSalida());
				//Fijamos el valor del usuario que se está editando
				idUsuarioEdicion = idEmpleadoSel; 
				//Desactivamos el comboBox de Empleados y ponemos el empleado en el txtEmpleado
				cmbBoxTemporales.setSelectedIndex(0);
				cmbBoxTemporales.setEnabled(false);
				Usuario usuConsultado = empCtrl.obtenerEmpleado(empTempSel.getId());
				txtEmpleado.setText(usuConsultado.getNombreLargo());
				btnConfirmarEdicin.setEnabled(true);
				btnEditar.setEnabled(false);
				btnRegistrarPersonal.setEnabled(false);
			}
		});
		btnEditar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnEditar.setBounds(97, 620, 182, 65);
		contentPane.add(btnEditar);
		
		
		btnConfirmarEdicin.setEnabled(false);
		btnConfirmarEdicin.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnConfirmarEdicin.setBounds(302, 620, 318, 65);
		contentPane.add(btnConfirmarEdicin);
		
		JButton btnEliminar = new JButton("ELIMINAR");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tableIngEmpTemp.getSelectedRow();
				//Hacemos la validación para decidir si se elimina o no
				String nombre = (String) tableIngEmpTemp.getValueAt(filaSeleccionada, 1);
				int idEmpleadoEli = Integer.parseInt((String)tableIngEmpTemp.getValueAt(filaSeleccionada, 6));
				int resp = JOptionPane.showConfirmDialog(null, "Esta seguro que se desea eliminar el ingreso del personal Temporal " +  nombre , "Eliminacion Ingreso Temporal ", JOptionPane.YES_NO_OPTION);
				if (resp == 0)
				{
					empCtrl.eliminarEmpleadoTemporalFecha(idEmpleadoEli, fecha.getFechaApertura());
					limpiarCamposIngreso();
					pintarReportePersonaTemp();
				}
			}
		});
		btnEliminar.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnEliminar.setBounds(644, 620, 182, 65);
		contentPane.add(btnEliminar);
		
		txtEmpleado = new JTextField();
		txtEmpleado.setEnabled(false);
		txtEmpleado.setEditable(false);
		txtEmpleado.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtEmpleado.setColumns(10);
		txtEmpleado.setBounds(432, 155, 209, 41);
		contentPane.add(txtEmpleado);
		
		cmbBoxEmpresa = new JComboBox();
		cmbBoxEmpresa.setFont(new Font("Tahoma", Font.BOLD, 16));
		cmbBoxEmpresa.setBounds(589, 299, 169, 41);
		contentPane.add(cmbBoxEmpresa);
		inicializarComboTemporales();
		//Pintar el Table con los ingresos del día
		pintarReportePersonaTemp();
	}
	

public void inicializarComboTemporales()
{
	//Inicializamos el combo con las empresas temporales
	Parametro parBase = parCtrl.obtenerParametro("BDGENERAL");
	ArrayList<EmpresaTemporal> empresasTemporales = parCtrl.retornarEmpresasTemporales(parBase.getValorTexto());
	cmbBoxEmpresa.removeAllItems();
	cmbBoxEmpresa.addItem(new EmpresaTemporal(0, "", 0,0));
	for(EmpresaTemporal empTempo: empresasTemporales)
	{
		cmbBoxEmpresa.addItem(empTempo);
	}
	
	ArrayList<Usuario> usuariosTemporales = empCtrl.obtenerUsuariosTemporalesDisponibles(fecha.getFechaApertura());
	cmbBoxTemporales.removeAllItems();
	//Adicionamos un usuario en blanco
	cmbBoxTemporales.addItem(new Usuario(0, "", "", "", 0,
			"", false));
	for(Usuario usuario: usuariosTemporales)
	{
		cmbBoxTemporales.addItem(usuario);
	}
	//Inicializamos la horas
	cmbBoxHoraIngreso.addItem("");
	cmbBoxHoraIngreso.addItem("12");
	cmbBoxHoraIngreso.addItem("13");
	cmbBoxHoraIngreso.addItem("14");
	cmbBoxHoraIngreso.addItem("15");
	cmbBoxHoraIngreso.addItem("16");
	cmbBoxHoraIngreso.addItem("17");
	cmbBoxHoraIngreso.addItem("18");
	cmbBoxHoraIngreso.addItem("19");
	cmbBoxHoraIngreso.addItem("20");
	cmbBoxHoraIngreso.addItem("21");
	cmbBoxHoraIngreso.addItem("22");
	cmbBoxHoraIngreso.addItem("23");
	cmbBoxHoraIngreso.addItem("24");
	//Hora Salida
	cmbBoxHoraSalida.addItem("");
	cmbBoxHoraSalida.addItem("12");
	cmbBoxHoraSalida.addItem("13");
	cmbBoxHoraSalida.addItem("14");
	cmbBoxHoraSalida.addItem("15");
	cmbBoxHoraSalida.addItem("16");
	cmbBoxHoraSalida.addItem("17");
	cmbBoxHoraSalida.addItem("18");
	cmbBoxHoraSalida.addItem("19");
	cmbBoxHoraSalida.addItem("20");
	cmbBoxHoraSalida.addItem("21");
	cmbBoxHoraSalida.addItem("22");
	cmbBoxHoraSalida.addItem("23");
	cmbBoxHoraSalida.addItem("24");
	//MinutosIngreso
	cmbBoxMinIngreso.addItem("");
	cmbBoxMinIngreso.addItem("00");
	cmbBoxMinIngreso.addItem("05");
	cmbBoxMinIngreso.addItem("10");
	cmbBoxMinIngreso.addItem("15");
	cmbBoxMinIngreso.addItem("20");
	cmbBoxMinIngreso.addItem("25");
	cmbBoxMinIngreso.addItem("30");
	cmbBoxMinIngreso.addItem("35");
	cmbBoxMinIngreso.addItem("40");
	cmbBoxMinIngreso.addItem("45");
	cmbBoxMinIngreso.addItem("50");
	cmbBoxMinIngreso.addItem("55");
	//MinutosSalida
	cmbBoxMinSalida.addItem("");
	cmbBoxMinSalida.addItem("00");
	cmbBoxMinSalida.addItem("05");
	cmbBoxMinSalida.addItem("10");
	cmbBoxMinSalida.addItem("15");
	cmbBoxMinSalida.addItem("20");
	cmbBoxMinSalida.addItem("25");
	cmbBoxMinSalida.addItem("30");
	cmbBoxMinSalida.addItem("35");
	cmbBoxMinSalida.addItem("40");
	cmbBoxMinSalida.addItem("45");
	cmbBoxMinSalida.addItem("50");
	cmbBoxMinSalida.addItem("55");
}

public String validarInfIngresoTemporal()
{
	String respValidacion = "";
	if(txtIdentificacion.getText().length() == 0)
	{
		respValidacion = respValidacion  + " La identificación está vacía."; 
	}
	if(txtNombreCompleto.getText().length() == 0)
	{
		respValidacion = respValidacion + " El Nombre está vacío.";
	}
	if(txtTelefono.getText().length() == 0)
	{
		respValidacion = respValidacion + " El teléfono está vacío.";
	}
	EmpresaTemporal empSel = (EmpresaTemporal) cmbBoxEmpresa.getSelectedItem();
	if(empSel.getIdEmpresa() == 0)
	{
		respValidacion = respValidacion + " La empresa no ha sido seleccionada.";
	}
	//Recuperamos el valor de Hora y minutos inicial
	String horaIni = (String)cmbBoxHoraIngreso.getSelectedItem();
	String minutosIni = (String)cmbBoxMinIngreso.getSelectedItem();
	if(horaIni.length() == 0 || minutosIni.length() == 0) {
		respValidacion = respValidacion + " La hora inicial de ingreso está vacía.";
	}
	return(respValidacion);
}

public void limpiarCamposIngreso()
{
	txtIdentificacion.setText("");
	txtNombreCompleto.setText("");
	txtTelefono.setText("");
	cmbBoxEmpresa.setSelectedIndex(0);
	cmbBoxHoraIngreso.setSelectedIndex(0);
	cmbBoxMinIngreso.setSelectedIndex(0);
	cmbBoxHoraSalida.setSelectedIndex(0);
	cmbBoxMinSalida.setSelectedIndex(0);
	inicializarComboTemporales();
}

public void pintarReportePersonaTemp()
{
	Object[] columnsName = new Object[7];
	columnsName[0] = "Cedula";
    columnsName[1] = "Nombre";
    columnsName[2] = "Empresa";
    columnsName[3] = "Telefono";
    columnsName[4] = "Hora-Ing";
    columnsName[5] = "Hora-Sal";
    columnsName[6] = "IdEmp";
    ArrayList<EmpleadoTemporalDia> empleadosTemp = new ArrayList();
    empleadosTemp = empCtrl.obtenerEmpleadoTemporalFecha(fecha.getFechaApertura());
    DefaultTableModel modelo = new DefaultTableModel(){
   	    public boolean isCellEditable(int rowIndex,int columnIndex){
   	    	return(false);
   	    }
   	};
	modelo.setColumnIdentifiers(columnsName);
	EmpleadoTemporalDia empTemp;
	for(int y = 0; y < empleadosTemp.size();y++)
	{
		empTemp = empleadosTemp.get(y);
		String fila[] = new String[7];
		fila[0] = empTemp.getIdentificacion();
		fila[1] = empTemp.getNombre();
		fila[2] = empTemp.getEmpresa();
		fila[3] = empTemp.getTelefono();
		fila[4] = empTemp.getHoraIngreso();
		fila[5] = empTemp.getHoraSalida();
		fila[6] = Integer.toString(empTemp.getId());
		modelo.addRow(fila);
	}
	tableIngEmpTemp.setModel(modelo);
	
}

/**
 * Método que se encarga de retornar la información capturada en Pantalla del empleadoTemporalDia
 * @param ventanaPadre
 * @return
 */
public EmpleadoTemporalDia obtenerInformacionDigitada(Window ventanaPadre)
{
	String identificacion = txtIdentificacion.getText();
	String nombre = txtNombreCompleto.getText();
	String telefono = txtTelefono.getText();
	String horaIngreso = (String)cmbBoxHoraIngreso.getSelectedItem()+":"+(String)cmbBoxMinIngreso.getSelectedItem()+":00";
	String horaSalida = (String)cmbBoxHoraSalida.getSelectedItem()+":"+(String)cmbBoxMinSalida.getSelectedItem()+":00";	
	if(horaSalida.length() != 8)
	{
		horaSalida = "";
	}
	Usuario  usuarioSel = (Usuario)cmbBoxTemporales.getSelectedItem();
	//Tratamos la selección de la empresa
	EmpresaTemporal empTemporal = (EmpresaTemporal)cmbBoxEmpresa.getSelectedItem();
	//Creamos el objeto de tipo EmpleadoTemporalDia y con este realizamos la inserción en base de datos.
	EmpleadoTemporalDia empTempIngresar = new EmpleadoTemporalDia(usuarioSel.getIdUsuario(), identificacion, nombre, telefono, empTemporal.getNombreEmpresa(),
				fecha.getFechaApertura(), empTemporal.getIdEmpresa());
	empTempIngresar.setHoraIngreso(horaIngreso);
	empTempIngresar.setHoraSalida(horaSalida);
	return(empTempIngresar);
}

public void fijarValorComboEmpresa(int idEmpresa)
{
	for (int i = 0; i < cmbBoxEmpresa.getModel().getSize(); i++) {
		EmpresaTemporal empTemporal = (EmpresaTemporal)cmbBoxEmpresa.getModel().getElementAt(i);
		if(empTemporal.getIdEmpresa() == idEmpresa){
			cmbBoxEmpresa.setSelectedItem(empTemporal);
		}
	}
}

public void fijarValoresCombos(String horaIngreso, String horaSalida)
{
	if(horaIngreso.length() == 8)
	{
		String hora = horaIngreso.substring(0, 2);
		String minutos = horaIngreso.substring(3, 5);
		System.out.println(hora + minutos);
		for (int i = 0; i < cmbBoxHoraIngreso.getModel().getSize(); i++) {
			String horaTemp = (String)cmbBoxHoraIngreso.getModel().getElementAt(i);
			if(horaTemp.equals(hora)){
				cmbBoxHoraIngreso.setSelectedItem(hora);
			}
		}
		for (int i = 0; i < cmbBoxMinIngreso.getModel().getSize(); i++) {
			String minTemp = (String)cmbBoxMinIngreso.getModel().getElementAt(i);
			if(minTemp.equals(minutos)){
				cmbBoxMinIngreso.setSelectedItem(minutos);
			}
		}
	}else
	{
		cmbBoxHoraIngreso.setSelectedIndex(0);
		cmbBoxMinIngreso.setSelectedIndex(0);
	}
	//Para la hora de salida si es que aplica
	if(horaSalida.length() == 8)
	{
		String hora = horaSalida.substring(0, 2);
		String minutos = horaSalida.substring(3, 5);
		for (int i = 0; i < cmbBoxHoraSalida.getModel().getSize(); i++) {
			String horaTemp = (String)cmbBoxHoraSalida.getModel().getElementAt(i);
			if(horaTemp.equals(hora)){
				cmbBoxHoraSalida.setSelectedItem(hora);
			}
		}
		for (int i = 0; i < cmbBoxMinSalida.getModel().getSize(); i++) {
			String minTemp = (String)cmbBoxMinSalida.getModel().getElementAt(i);
			if(minTemp.equals(minutos)){
				cmbBoxMinSalida.setSelectedItem(minutos);
			}
		}
	}else
	{
		cmbBoxHoraSalida.setSelectedIndex(0);
		cmbBoxMinSalida.setSelectedIndex(0);
	}
}
}
