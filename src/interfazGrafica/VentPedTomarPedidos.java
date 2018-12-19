package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import JTable.CellRenderPedido;
import JTable.CellRenderTransaccional;

import java.awt.Component;
import java.awt.Window;
import javax.swing.SwingUtilities;

import capaControlador.AutenticacionCtrl;
import capaControlador.InventarioCtrl;
import capaControlador.MenuCtrl;
import capaControlador.ParametrosDireccionCtrl;
import capaControlador.ParametrosProductoCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.AccesosPorBoton;
import capaModelo.ConfiguracionMenu;
import capaModelo.DetallePedido;
import capaModelo.FechaSistema;
import capaModelo.MotivoAnulacionPedido;
import capaModelo.Municipio;
import capaModelo.Pregunta;
import capaModelo.Producto;
import capaModelo.ProductoIncluido;
import capaModelo.TipoPedido;

import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.sql.Date;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import java.awt.SystemColor;

public class VentPedTomarPedidos extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable tableMenu;
	//Creamos una variable Arreglo Global que contendr� la Configuraci�n Men�
	private ConfiguracionMenu[][] confiMenu;
	// El arreglo con los botones del men� activo se manejar� como variable global
	private Object[][] botones;
	static JButton btnFinalizarPedido;
	static JButton btnDescuento;
	private JTextField txtValorPedidoSD;
	private JTextField txtDescuento;
	private JTextField txtValorTotal;
	private JTextField txtNroPedido;
	private JButton btnTipoPedido;
	JButton btnProductoCon;
	JButton btnProductoSin;
	JLabel lblNombreCliente;
	JLabel lDireccion;
	private JTable tableDetallePedido;
	//TODAS LAS VARIABLES EST�TICAS DEL ESTE JFRAME
	//Par�metros que se tendr�n en Tomador Pedidos para la selecci�n de clientes
	//VARIABLES EST�TICAS
	public static int idCliente = 0;
	public static int idPedido = 0;
	public static int idTienda = 0;
	public static double totalPedido = 0;
	public static double descuento = 0;
	public static String usuario = "";
	public static String nombreCliente = "";
	public static int idDetallePedidoMaster = 0;
	public static ArrayList<DetallePedido> detallesPedido = new ArrayList();
	public static String direccion;
	public static String zona;
	public static String observacion;
	static int numTipoPedido = 0;
	static int numTipoPedidoAct = 0;
	static ArrayList<TipoPedido> tiposPedidos;
	static boolean tieneFormaPago = false;
	static boolean tieneDescuento = false;
	public static int colorDetalle = 0;
	//Manejamos variable booleana que nos indicar� si el pedido es no es reabierto
	public static boolean esReabierto = false;
	//Manejamos variable booleanda que nos indic� si al pedido completo se le realiz� anulaci�n completa
	public static boolean esAnulado = false;
	//Variable que indica que el pedido es anulado sin haber descontado alg�n item de inventario
	public static boolean esAnuladoSinDescontar = false;
	private JComboBox comboMotivoAnulacion;
	public static int contadorDetallePedido = 1;
	private PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	private ParametrosProductoCtrl parPro = new ParametrosProductoCtrl(PrincipalLogueo.habilitaAuditoria);
	private InventarioCtrl invCtrl = new InventarioCtrl(PrincipalLogueo.habilitaAuditoria);
	AutenticacionCtrl autCtrl = new AutenticacionCtrl(PrincipalLogueo.habilitaAuditoria);
	private final String codPantalla = "PED_013";
	ArrayList<AccesosPorBoton> accesosBoton = autCtrl.obtenerAccesosPorBotonObj(Sesion.getIdTipoEmpleado(), codPantalla);
	private MenuCtrl menuCtrl = new MenuCtrl();
	ArrayList<Producto> productos =parPro.obtenerProductosCompleto();
	String[] nombresMultimenus;
	JFrame framePrincipal;
	/**
	 * Launch the application.
	 */
	public static void clarearVarEstaticas()
	{
		idCliente = 0;
		nombreCliente = "";
		direccion = "";
		zona = "";
		observacion = "";
		idPedido = 0;
		totalPedido = 0;
		descuento = 0;
		usuario = "";
		nombreCliente = "";
		idDetallePedidoMaster = 0;
		detallesPedido = new ArrayList();
		numTipoPedido = 0;
		numTipoPedidoAct = 0;
		tieneFormaPago = false;
		tieneDescuento = false;
		btnFinalizarPedido.setBackground(Color.LIGHT_GRAY);
		btnDescuento.setBackground(null);
		esAnulado = false;
		esReabierto = false;

	}
	
	public static void clarearVarEstaticasNoSalir()
	{
		totalPedido = 0;
		descuento = 0;
		idDetallePedidoMaster = 0;
		//SI es un pedido anulado completo continua con su situaci�n de reabierto o no por lo tanto se coloca comentario sobre la linea
		//esReabierto = false;
	}
	
	public void clarearVarNoEstaticas(boolean cerrarVentana)
	{
		txtValorPedidoSD.setText("0");
		txtDescuento.setText("0");
		txtValorTotal.setText("0");
		txtNroPedido.setText("");
		esAnulado = false;
		lblNombreCliente.setText("");
		lDireccion.setText("");
		if(cerrarVentana)
		{
			dispose();
		}
	}
	
	public void clarearVarNoEstaticasNoSalir()
	{
		txtValorPedidoSD.setText("0");
		txtDescuento.setText("0");
		txtValorTotal.setText("0");
		//Comentamos lo siguiente porque se continuar� en pantalla de pedidos
		//lblNombreCliente.setText("");
		//lDireccion.setText("");
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentPedTomarPedidos frame = new VentPedTomarPedidos();
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
	public VentPedTomarPedidos() {
		
		/**
		 * Se define la acci�n al momento de cerrar la ventana para eliminar un pedido que se este tomando
		 * sino se est� tomando pedido no se preguntar� nada
		 * 
		 */
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				//Validamos la variable idPedido si esta es mayor a cero es porque el pedido ya comenz� a tomarse
				if(idPedido > 0)
				{
					anularPedido(true);
				}
			}
		});
		setTitle("TOMADOR DE PEDIDOS");
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1024, 770);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		this.setExtendedState(MAXIMIZED_BOTH);
		framePrincipal = this;
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelPedido = new JPanel();
		panelPedido.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0), 3), null));
		panelPedido.setBounds(0, 0, 247, 431);
		contentPane.add(panelPedido);
		panelPedido.setLayout(null);
		
		table = new JTable();
		table.setBounds(10, 272, 205, -260);
		panelPedido.add(table);
		
		JLabel lblValorTotalSD = new JLabel("Valor Total SD");
		lblValorTotalSD.setBounds(10, 312, 83, 14);
		panelPedido.add(lblValorTotalSD);
		
		JButton btnAnularItem = new JButton("Anular Item");
		//M�todo donde se define la acci�n del Anular item en la ventana de tomador de pedidos.
		btnAnularItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int filaSeleccionada = tableDetallePedido.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar alg�n item del pedido para Eliminar " , "No ha Seleccionado item para Eliminar ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// Se captura el valor del idDetalle que se desea eliminar
				int idDetalleEliminar = Integer.parseInt((String)tableDetallePedido.getValueAt(filaSeleccionada, 0));
				//Se obtiene en un boolean si el idDetalle es maestro o no, sino lo es, no se puede eliminar, pues se debe
				//eliminar el master
				boolean esMaster = pedCtrl.validarDetalleMaster(idDetalleEliminar);
				if(!esMaster)
				{
					JOptionPane.showMessageDialog(null, "Debe seleccionar un item Master " , "Error al Eliminar", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//Realizamos la validaci�n si el pedido es reabierto no
				if(!esReabierto)
				{
					boolean respEliDetPedido = pedCtrl.eliminarDetallePedido(idDetalleEliminar);
					//Recorremos el arrayList de detallePedido para eliminar con base en el detallePedido eliminado
					for(int j = 0; j < detallesPedido.size(); j++)
					{
						DetallePedido detCadaPedido = detallesPedido.get(j);
						// Cambiamos para la eliminaci�n que se tenga el iddetalle_pedido o el iddetalle_pedido_master
						if(detCadaPedido.getIdDetallePedido() == idDetalleEliminar || detCadaPedido.getIdDetallePedidoMaster() == idDetalleEliminar)
						{
							double valorItem = detCadaPedido.getValorTotal();
							detallesPedido.remove(j);
							// j se reduce en uno teniendo en cuenta que se elimin� un elemento
							j--;
							totalPedido = totalPedido - valorItem;
							txtValorPedidoSD.setText(Double.toString(totalPedido));
							txtValorTotal.setText(Double.toString(totalPedido - descuento));
						}
					}
				}else
				{
					//AQUI TENDREMOS QUE INCLUIR LA L�GICA IMPORTANTE PARA CAPTURAR MOTIVO DE ANULACI�N, ELIMINAR Y DESCONTAR INVENTARIO SI ES EL CASO
					int resp = JOptionPane.showConfirmDialog(null, comboMotivoAnulacion, "Selecciona Motivo de Anulaci�n.",JOptionPane.YES_NO_OPTION);
					if (resp == 0)
					{
						//Capturamos en caso de aceptar la anulaci�n el motivo de anulaci�n seleccionado
						MotivoAnulacionPedido motAnu = (MotivoAnulacionPedido)comboMotivoAnulacion.getSelectedItem();
						//Se realiza la anulaci�n del pedido incluyendo el motivo de anulaci�n
						boolean reinAnPedido = invCtrl.reintegrarInventarioDetallePedido(idDetalleEliminar, idPedido);
						boolean anuDetalle = false;
						if(reinAnPedido)
						{
							anuDetalle = pedCtrl.anularDetallePedido(idDetalleEliminar, motAnu.getIdMotivoAnulacion());
						}
					}
					//Se realizar un recorrido del arrayList de detalle pedidos para poner negativo los detalles de pedido
					for(int j = 0; j < detallesPedido.size(); j++)
					{
						DetallePedido detCadaPedido = detallesPedido.get(j);
						// Cambiamos para la eliminaci�n que se tenga el iddetalle_pedido o el iddetalle_pedido_master
						if(detCadaPedido.getIdDetallePedido() == idDetalleEliminar || detCadaPedido.getIdDetallePedidoMaster() == idDetalleEliminar)
						{
							double valorItem = detCadaPedido.getValorTotal();
							detCadaPedido.setEstado("A");
							detallesPedido.set(j, detCadaPedido);
							totalPedido = totalPedido - valorItem;
							txtValorPedidoSD.setText(Double.toString(totalPedido));
							txtValorTotal.setText(Double.toString(totalPedido - descuento));
						}
					}
				}
				pintarDetallePedido();
				
			}
		});
		btnAnularItem.setBounds(51, 384, 147, 36);
		panelPedido.add(btnAnularItem);
		
		txtValorPedidoSD = new JTextField();
		txtValorPedidoSD.setEditable(false);
		txtValorPedidoSD.setBounds(97, 309, 140, 20);
		panelPedido.add(txtValorPedidoSD);
		txtValorPedidoSD.setColumns(10);
		
		JLabel lblDescuento = new JLabel("Descuento");
		lblDescuento.setBounds(10, 337, 83, 14);
		panelPedido.add(lblDescuento);
		
		txtDescuento = new JTextField();
		txtDescuento.setEditable(false);
		txtDescuento.setBounds(97, 334, 140, 20);
		panelPedido.add(txtDescuento);
		txtDescuento.setColumns(10);
		
		JLabel lblValorTotal = new JLabel("Valor Total");
		lblValorTotal.setBounds(10, 362, 83, 14);
		panelPedido.add(lblValorTotal);
		
		txtValorTotal = new JTextField();
		txtValorTotal.setEditable(false);
		txtValorTotal.setColumns(10);
		txtValorTotal.setBounds(97, 362, 140, 20);
		panelPedido.add(txtValorTotal);
		
		txtDescuento.setText(Double.toString(descuento));
		txtValorTotal.setText(Double.toString(totalPedido - descuento));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 227, 290);
		panelPedido.add(scrollPane);
		
		
		
		tableDetallePedido = new JTable();
		scrollPane.setViewportView(tableDetallePedido);
		
		tableDetallePedido.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		tableDetallePedido.setShowVerticalLines(false);
		tableDetallePedido.setShowHorizontalLines(false);
		tableDetallePedido.setShowGrid(false);
		tableDetallePedido.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int filaSeleccionada = tableDetallePedido.getSelectedRow();
				int idDetalleTratar = Integer.parseInt((String)tableDetallePedido.getValueAt(filaSeleccionada, 0));
				//Validaremos si el detallePedido tiene un producto que tiene productos con y sin.
				boolean hayModCon = pedCtrl.detalleTieneModificadorCon(idDetalleTratar);
				if(hayModCon)
				{
					btnProductoCon.setEnabled(true);
				}
				else
				{
					btnProductoCon.setEnabled(false);
				}
				boolean hayModSin = pedCtrl.detalleTieneModificadorSin(idDetalleTratar);
				if(hayModSin)
				{
					btnProductoSin.setEnabled(true);
				}
				else
				{
					btnProductoSin.setEnabled(false);
				}
			}
		});
		
		JPanel panelMenu = new JPanel();
		panelMenu.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panelMenu.setBounds(257, 0, 747, 431);
		contentPane.add(panelMenu);
		panelMenu.setLayout(null);
		
		tableMenu = new JTable();
		tableMenu.setShowGrid(false);
		tableMenu.setBorder(null);
		tableMenu.setBounds(10, 11, 732, 409);
		tableMenu.setCellSelectionEnabled(true);
		tableMenu.setDefaultRenderer(Object.class, new ButtonRenderer());
		tableMenu.setCellEditor(new ButtonEditor());
		tableMenu.setRowHeight(67);
		tableMenu.setOpaque(false);
		JButton btn;
			
		botones = new Object[6][6];
		cargarConfiguracionMenu(1);
		tableMenu.setDefaultRenderer(JButton.class, new ButtonRenderer());
		tableMenu.setDefaultEditor(JButton.class, new ButtonEditor());
		panelMenu.add(tableMenu);
		
		
		JPanel panelAgrupadorMenu = new JPanel();
		panelAgrupadorMenu.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panelAgrupadorMenu.setBounds(235, 441, 769, 69);
		contentPane.add(panelAgrupadorMenu);
		panelAgrupadorMenu.setLayout(new GridLayout(1, 0, 0, 0));
		comboMotivoAnulacion = new JComboBox();
		initComboMotivoAnulacion(); 
		//Recuperamos si hay men�s en cada opci�n para el pintar
		boolean[] siHayMenu = menuCtrl.retornarSihayMultimenu();
		nombresMultimenus = menuCtrl.retornarNombresMultimenu();
		JButton btnMultimenu_1;
		JButton btnMultimenu_2;
		JButton btnMultimenu_3;
		JButton btnMultimenu_4;
		JButton btnMultimenu_5;
		JButton btnMultimenu_6;
		//Multimen� 1
		if(siHayMenu[1])
		{
			btnMultimenu_1 = new JButton(nombresMultimenus[1]);
		}else
		{
			btnMultimenu_1 = new JButton("");
			btnMultimenu_1.setOpaque(false);
			btnMultimenu_1.setBorderPainted(false);
			btnMultimenu_1.setContentAreaFilled(false);
		}
		
		//Multimen� 2
		if(siHayMenu[2])
		{
			btnMultimenu_2 = new JButton(nombresMultimenus[2]);
		}else
		{
			btnMultimenu_2 = new JButton("");
			btnMultimenu_2.setOpaque(false);
			btnMultimenu_2.setBorderPainted(false);
			btnMultimenu_2.setContentAreaFilled(false);
		}
		//Multimen� 3
		if(siHayMenu[3])
		{
			btnMultimenu_3 = new JButton(nombresMultimenus[3]);
		}else
		{
			btnMultimenu_3 = new JButton("");
			btnMultimenu_3.setOpaque(false);
			btnMultimenu_3.setBorderPainted(false);
			btnMultimenu_3.setContentAreaFilled(false);
		}
		//Multimen� 4
		if(siHayMenu[4])
		{
			btnMultimenu_4 = new JButton(nombresMultimenus[4]);
		}else
		{
			btnMultimenu_4 = new JButton("");
			btnMultimenu_4.setOpaque(false);
			btnMultimenu_4.setBorderPainted(false);
			btnMultimenu_4.setContentAreaFilled(false);
		}
		//Multimen� 5
		if(siHayMenu[5])
		{
			btnMultimenu_5 = new JButton(nombresMultimenus[5]);
		}else
		{
			btnMultimenu_5 = new JButton("");
			btnMultimenu_5.setOpaque(false);
			btnMultimenu_5.setBorderPainted(false);
			btnMultimenu_5.setContentAreaFilled(false);
		}
		//Multimen� 6
		if(siHayMenu[6])
		{
			btnMultimenu_6 = new JButton(nombresMultimenus[6]);
		}else
		{
			btnMultimenu_6 = new JButton("");
			btnMultimenu_6.setOpaque(false);
			btnMultimenu_6.setBorderPainted(false);
			btnMultimenu_6.setContentAreaFilled(false);
		}
		btnMultimenu_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnMultimenu_1.setBackground(Color.YELLOW);
				btnMultimenu_2.setBackground(null);
				btnMultimenu_3.setBackground(null);
				btnMultimenu_4.setBackground(null);
				btnMultimenu_5.setBackground(null);
				btnMultimenu_6.setBackground(null);
				cargarConfiguracionMenu(1);
			}
		});
		btnMultimenu_1.setBackground(Color.YELLOW);
		panelAgrupadorMenu.add(btnMultimenu_1);
		
		
		btnMultimenu_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				btnMultimenu_2.setBackground(Color.YELLOW);
				btnMultimenu_1.setBackground(null);
				btnMultimenu_3.setBackground(null);
				btnMultimenu_4.setBackground(null);
				btnMultimenu_5.setBackground(null);
				btnMultimenu_6.setBackground(null);
				cargarConfiguracionMenu(2);
			}
		});
		panelAgrupadorMenu.add(btnMultimenu_2);
		
		
		btnMultimenu_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnMultimenu_3.setBackground(Color.YELLOW);
				btnMultimenu_1.setBackground(null);
				btnMultimenu_2.setBackground(null);
				btnMultimenu_4.setBackground(null);
				btnMultimenu_5.setBackground(null);
				btnMultimenu_6.setBackground(null);
				cargarConfiguracionMenu(3);
			}
		});
		panelAgrupadorMenu.add(btnMultimenu_3);
		
		
		btnMultimenu_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnMultimenu_4.setBackground(Color.YELLOW);
				btnMultimenu_1.setBackground(null);
				btnMultimenu_2.setBackground(null);
				btnMultimenu_3.setBackground(null);
				btnMultimenu_5.setBackground(null);
				btnMultimenu_6.setBackground(null);
				cargarConfiguracionMenu(4);
			}
		});
		panelAgrupadorMenu.add(btnMultimenu_4);
		
		
		
		btnMultimenu_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnMultimenu_5.setBackground(Color.YELLOW);
				btnMultimenu_1.setBackground(null);
				btnMultimenu_2.setBackground(null);
				btnMultimenu_3.setBackground(null);
				btnMultimenu_4.setBackground(null);
				btnMultimenu_6.setBackground(null);
				cargarConfiguracionMenu(5);
			}
		});
		panelAgrupadorMenu.add(btnMultimenu_5);
		
		
		btnMultimenu_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnMultimenu_6.setBackground(Color.YELLOW);
				btnMultimenu_2.setBackground(null);
				btnMultimenu_3.setBackground(null);
				btnMultimenu_4.setBackground(null);
				btnMultimenu_5.setBackground(null);
				btnMultimenu_1.setBackground(null);
				cargarConfiguracionMenu(6);
			}
		});
		panelAgrupadorMenu.add(btnMultimenu_6);
		
		JPanel panelAcciones = new JPanel();
		panelAcciones.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panelAcciones.setBounds(0, 569, 1004, 69);
		contentPane.add(panelAcciones);
		panelAcciones.setLayout(null);
		
		JButton btnAsignarCliente = new JButton("Asignar Cliente");
		btnAsignarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Se deber�a crear una nueva ventana para la asignaci�n y creaci�n de clientes
				//Al momento de instanciar VentCliente se le pasar� como par�metro el idCliente del pedido
				boolean tienePermiso = autCtrl.validarAccesoOpcion("CLI_002", Sesion.getAccesosOpcion());
				if (tienePermiso)
				{
					VentCliCliente cliente = new VentCliCliente(idCliente,framePrincipal, true);
					cliente.setVisible(true);
				}else
				{
					JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opci�n/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnAsignarCliente.setBounds(10, 11, 140, 47);
		panelAcciones.add(btnAsignarCliente);
		
		JButton btnAnularPedido = new JButton("Anular Pedido");
		// A continuacion definiremos la acci�n para la actividad de anular pedido
		btnAnularPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				boolean tienePermiso = autCtrl.validarAccesoBoton(codPantalla, "BOT_001", accesosBoton);
				if (tienePermiso)
				{
					if(idPedido > 0)
					{
						//Invocamamos el m�todo qeu se encarga de realizar la verificacion para la anulaci�n del pedido
						anularPedido(false);
					}
				}else
				{
					JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opci�n/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnAnularPedido.setBounds(160, 11, 140, 47);
		panelAcciones.add(btnAnularPedido);
		
		btnDescuento = new JButton("Descuento");
		btnDescuento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) e.getSource());
				if (idPedido > 0)
				{
					boolean hayDescuentos = pedCtrl.existePedidoDescuento(idPedido);
					if (hayDescuentos)
					{
						int seleccion = JOptionPane.showOptionDialog(
								null,
								"El producto ya presenta descuentos, desea borrar el descuento actual e ingresar uno nuevo",
								"Descuento ya Registrado",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE,
								null,
								new Object[]{"Borrar e Ingresar Nuevo Descuento","Cancelar"},
								"Borrar e Ingresar Nuevo Descuento");
						if(seleccion == 0)
						{
							boolean respuesta = pedCtrl.eliminarPedidoDescuento(idPedido);
							if(respuesta)
							{
								boolean tienePermiso = autCtrl.validarAccesoOpcion("PED_006", Sesion.getAccesosOpcion());
								if (tienePermiso)
								{
									VentPedDescuento ventDescuento = new VentPedDescuento((JFrame) ventanaPadre,true);
									ventDescuento.setVisible(true);
									descuento = 0;
								}else
								{
									JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opci�n/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
								}
								
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Se present� un inconveniente por favor contacte al administrador del sistema " , "Error Eliminando descuento ", JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
					}
					else
					{
						boolean tienePermiso = autCtrl.validarAccesoOpcion("PED_006", Sesion.getAccesosOpcion());
						if (tienePermiso)
						{
							VentPedDescuento ventDescuento = new VentPedDescuento((JFrame) ventanaPadre,true);
							ventDescuento.setVisible(true);
						}else
						{
							JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opci�n/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
						}
						
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "No se ha agregado ning�n item al pedido, no debe haber descuento " , "Error Agregando Descuento ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				
			}
		});
		
		btnDescuento.setBounds(310, 11, 140, 47);
		panelAcciones.add(btnDescuento);
		
		btnFinalizarPedido = new JButton("Finalizar Pedido");
		btnFinalizarPedido.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnFinalizarPedido.setBackground(Color.LIGHT_GRAY);

		btnFinalizarPedido.setBounds(460, 11, 196, 47);

		btnFinalizarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//traemos la ventana padre para el JDialog de FinalizarPedido
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) arg0.getSource());
				//Al momento de finalizar pedido validaremos que si el tipo de pedido es domicilio se tenga un cliente asociado
				TipoPedido tipPedido = VentPedTomarPedidos.tiposPedidos.get(numTipoPedidoAct);
				if(tipPedido.isEsDomicilio())
				{
					if(VentPedTomarPedidos.idCliente == 0)
					{
						JOptionPane.showMessageDialog(ventanaPadre, "No se puede finalizar pedido, dado que es tipo domicilio y no tiene asociado un cliente. es domicilio " + tipPedido.isEsDomicilio() + " id tipo  " + tipPedido.getIdTipoPedido() , "Falta Cliente para Pedido Tipo Domicilio", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				if (idPedido > 0)
				{
					boolean hayFormaPago = pedCtrl.existeFormaPago(idPedido);
					if (hayFormaPago)
					{
						boolean tienePermiso = autCtrl.validarAccesoOpcion("PED_008", Sesion.getAccesosOpcion());
						if (tienePermiso)
						{
							VentPedFinPago Finalizar = new VentPedFinPago(true, (JFrame) ventanaPadre, true);
							Finalizar.setVisible(true);
						}else
						{
							JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opci�n/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
						}
						
					}
					else
					{
						boolean tienePermiso = autCtrl.validarAccesoOpcion("PED_008", Sesion.getAccesosOpcion());
						if (tienePermiso)
						{
							VentPedFinPago Finalizar = new VentPedFinPago(false, (JFrame) ventanaPadre, true);
							Finalizar.setVisible(true);
						}else
						{
							JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opci�n/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
						}
						
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "No se ha agregado ning�n item al pedido, no debe haber Forma de Pago " , "Error Agregando Forma Pago ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
			}
		});
		
		panelAcciones.add(btnFinalizarPedido);
		
		
		
		JButton btnMaestroPedidos = new JButton("Maestro Pedidos");
		btnMaestroPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(idPedido == 0)
				{
					boolean tienePermiso = autCtrl.validarAccesoOpcion("PED_014", Sesion.getAccesosOpcion());
					if (tienePermiso)
					{
						VentPedTransaccional transacciones = new VentPedTransaccional();
						transacciones.setVisible(true);
						dispose();
					}else
					{
						JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opci�n/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
					}
					
				}
				else
				{
					int idPedidoTemp = idPedido;
					anularPedido(true);
					if(idPedido == 0)
					{
						boolean tienePermiso = autCtrl.validarAccesoOpcion("PED_014", Sesion.getAccesosOpcion());
						if (tienePermiso)
						{
							VentPedTransaccional transacciones = new VentPedTransaccional();
							transacciones.setVisible(true);
							idCliente = 0;
							//Preguntamos si es anulado sin descontar signfica que si entro y debe anular el pedido
							//Se valida aca porque normalmente cuando se invoca desde el bot�n anular que no es donde se sale la idea es que no anule todav�a el pedido, mientras ac� si 
							// lo deber� anular.
							if(esAnuladoSinDescontar)
							{
								//Anulamos el pedido con el detalle de cambio de opinion del cliente
								pedCtrl.anularPedidoSinDetalle(idPedidoTemp);
								esAnuladoSinDescontar = false;
							}
							dispose();
						}else
						{
							JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opci�n/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
						}
						
					}
				}
			}
		});
		btnMaestroPedidos.setBounds(666, 11, 140, 47);
		panelAcciones.add(btnMaestroPedidos);
		
		JButton btnSalirSistema = new JButton("Salir");
		btnSalirSistema.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Validamos la variable idPedido si esta es mayor a cero es porque el pedido ya comenz� a tomarse
				int idPedidoTemp = idPedido;
				if(idPedido > 0)
				{
					anularPedido(true);
				}
				//Es porque se dijo que si en la anulaci�n, si saldriamos en esta situaci�n
				if(idPedido == 0)
				{
					VentPrincipal ventPrincipal = new VentPrincipal();
					ventPrincipal.setVisible(true);
					idCliente = 0;
					//Preguntamos si es anulado sin descontar signfica que si entro y debe anular el pedido
					if(esAnuladoSinDescontar)
					{
						//Anulamos el pedido con el detalle de cambio de opinion del cliente
						pedCtrl.anularPedidoSinDetalle(idPedidoTemp);
						esAnuladoSinDescontar = false;
					}
					dispose();
				}
			}
		});
		btnSalirSistema.setBounds(816, 11, 140, 47);
		panelAcciones.add(btnSalirSistema);
		
		JButton btnTerminarPedido = new JButton("Terminar Pedido");
		btnTerminarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Ingresamos l�gica para tomar el tipo de pedido 
				int idTipoPedido;
				try
				{
					TipoPedido tipPedido = VentPedTomarPedidos.tiposPedidos.get(numTipoPedidoAct);
					idTipoPedido = tipPedido.getIdTipoPedido();
					
				}catch(Exception e1)
				{
					idTipoPedido = 0;
					System.out.println(e1.toString());
				}
				
				int confirmado = JOptionPane.showConfirmDialog(
						   null,"<html><center><b>A continuaci�n la informacion para Confirmaci�n del Pedido.</b><br>"
					                 + "<p>CLIENTE: "+ VentPedTomarPedidos.nombreCliente+" </p>" +
								   "<p>Por un valor TOTAL: " + totalPedido  +"</p>" +
								   "<p>Con un Descuento: " + VentPedTomarPedidos.descuento  +"</p>" +
								   "<p>N�MERO DE PEDIDO: " + VentPedTomarPedidos.idPedido +"</p>" 
								   
						   );
				if (JOptionPane.OK_OPTION == confirmado)
				{
				
					// En este punto finalizamos el pedido
					//VentPedConfirmarPedido ventConfirmarPedido = new VentPedConfirmarPedido(nombreCliente, totalPedido, descuento, idPedido, new javax.swing.JFrame(), true);
					//	ventConfirmarPedido.setVisible(true);
					boolean resFinPedido = pedCtrl.finalizarPedido(idPedido, 30/*tiempoPedido*/, idTipoPedido);
					if (resFinPedido)
					{
						//En este punto es cuando clareamos las variables del tipo de pedido que son est�ticas y sabiendo qeu se finaliz�
						//el pedido es neceseario clarear las variables del jFrame de TomarPedidos
						boolean reintInv = invCtrl.descontarInventarioPedido(idPedido);
						if(!reintInv)
						{
							JOptionPane.showMessageDialog(null, "Se presentaron inconvenientes en el descuento de los inventarios " , "Error en Descuento de Inventarios ", JOptionPane.ERROR_MESSAGE);
						}
						clarearVarEstaticas();
						pintarDetallePedido();
						clarearVarNoEstaticas(false);
					}
				}
			}
		});
		btnTerminarPedido.setBounds(816, 11, 140, 47);
		//panelAcciones.add(btnTerminarPedido);
		
		
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 442, 225, 116);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblTipoPedido = new JLabel("Tipo Pedido");
		lblTipoPedido.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTipoPedido.setBounds(76, 0, 71, 14);
		panel.add(lblTipoPedido);
		
		btnTipoPedido = new JButton("");
		btnTipoPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Acciones relacionadas con el bot�n de tipo de pedido
				numTipoPedidoAct++;
				if(numTipoPedidoAct == numTipoPedido)
				{
					numTipoPedidoAct = 0;
				}
				TipoPedido tipoPed = tiposPedidos.get(numTipoPedidoAct);
				btnTipoPedido.setText(tipoPed.getDescripcion());
				btnTipoPedido.setHorizontalAlignment(SwingConstants.TRAILING);
				btnTipoPedido.setIcon(new ImageIcon(VentPedTomarPedidos.class.getResource("/icons/"+tipoPed.getIcono())));
				
			}
		});
		btnTipoPedido.setBounds(10, 21, 205, 56);
		panel.add(btnTipoPedido);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				fijarCliente();
				txtValorPedidoSD.setText(Double.toString(totalPedido));
				pintarDetallePedido();
				txtDescuento.setText(Double.toString(descuento));
				txtValorTotal.setText(Double.toString(totalPedido - descuento));
				txtNroPedido.setText(Integer.toString(idPedido));
				if(tieneFormaPago)
				{
					btnFinalizarPedido.setBackground(Color.YELLOW);
				}
				else
				{
					btnFinalizarPedido.setBackground(Color.LIGHT_GRAY);
					
				}
				if(tieneDescuento)
				{
					btnDescuento.setBackground(Color.YELLOW);
				}
				else
				{
					btnDescuento.setBackground(null);
				}
				if(numTipoPedidoAct == numTipoPedido)
				{
					numTipoPedidoAct = 0;
				}
				TipoPedido tipoPed = tiposPedidos.get(numTipoPedidoAct);
				btnTipoPedido.setText(tipoPed.getDescripcion());
				btnTipoPedido.setHorizontalAlignment(SwingConstants.TRAILING);
				btnTipoPedido.setIcon(new ImageIcon(VentPedTomarPedidos.class.getResource("/icons/"+tipoPed.getIcono())));
				
			}
		});
		
		lblNombreCliente = new JLabel("Nombre Cliente");
		lblNombreCliente.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNombreCliente.setBounds(208, 649, 390, 17);
		contentPane.add(lblNombreCliente);
		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
		
		//Acciones relacionadas con el bot�n de tipo de pedido
		tiposPedidos = pedCtrl.obtenerTiposPedidoNat();
		numTipoPedido = tiposPedidos.size();
		//Inicializamos el bot�n con el tipo de pedido inicial
		TipoPedido tipoPed = tiposPedidos.get(numTipoPedidoAct);
		btnTipoPedido.setText(tipoPed.getDescripcion());
		
		JLabel lblNroPedido = new JLabel("NRO PEDIDO");
		lblNroPedido.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNroPedido.setBounds(10, 88, 80, 14);
		panel.add(lblNroPedido);
		
		txtNroPedido = new JTextField();
		txtNroPedido.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtNroPedido.setEditable(false);
		txtNroPedido.setBounds(100, 85, 115, 20);
		panel.add(txtNroPedido);
		txtNroPedido.setColumns(10);
		
		JPanel panelModificadores = new JPanel();
		panelModificadores.setBounds(235, 521, 769, 37);
		contentPane.add(panelModificadores);
		panelModificadores.setLayout(null);
		
		btnProductoCon = new JButton("PRODUCTO CON");
		btnProductoCon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int filaSeleccionada = tableDetallePedido.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					
				}else
				{
					boolean tienePermiso = autCtrl.validarAccesoOpcion("PED_011", Sesion.getAccesosOpcion());
					if (tienePermiso)
					{
						int idDetalleTratar = Integer.parseInt((String)tableDetallePedido.getValueAt(filaSeleccionada, 0));
						int contDetPedido = Integer.parseInt((String)tableDetallePedido.getValueAt(filaSeleccionada, 7));
						VentPedModificador ventMod = new VentPedModificador(null, true, idDetalleTratar, true, false, contDetPedido);
						ventMod.setVisible(true);
					}else
					{
						JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opci�n/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
					}
					
				}
				
			}
		});
		btnProductoCon.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnProductoCon.setBounds(168, 0, 124, 37);
		panelModificadores.add(btnProductoCon);
		
		btnProductoSin = new JButton("PRODUCTO SIN");
		btnProductoSin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int filaSeleccionada = tableDetallePedido.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					
				}else
				{
					boolean tienePermiso = autCtrl.validarAccesoOpcion("PED_011", Sesion.getAccesosOpcion());
					if (tienePermiso)
					{
						int idDetalleTratar = Integer.parseInt((String)tableDetallePedido.getValueAt(filaSeleccionada, 0));
						int contDetPedido = Integer.parseInt((String)tableDetallePedido.getValueAt(filaSeleccionada, 7));
						VentPedModificador ventMod = new VentPedModificador(null, true, idDetalleTratar, false, true, contDetPedido);
						ventMod.setVisible(true);
					}else
					{
						JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opci�n/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}
		});
		btnProductoSin.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnProductoSin.setBounds(371, 0, 124, 37);
		panelModificadores.add(btnProductoSin);
		//Deshabilitamos los botones de modificadores
		btnProductoCon.setEnabled(false);
		btnProductoSin.setEnabled(false);
		
		JLabel lblDireccion = new JLabel("Direcci\u00F3n");
		lblDireccion.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDireccion.setBounds(79, 677, 108, 20);
		contentPane.add(lblDireccion);
		
		lDireccion = new JLabel("direccion");
		lDireccion.setFont(new Font("Tahoma", Font.BOLD, 14));
		lDireccion.setBounds(208, 677, 390, 17);
		contentPane.add(lDireccion);
		
		JLabel lblNombreCliente_1 = new JLabel("Nombre Cliente");
		lblNombreCliente_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNombreCliente_1.setBounds(79, 647, 119, 20);
		contentPane.add(lblNombreCliente_1);
		//Inicializamos la variable idTienda para la toma de pedidos
		if(idTienda == 0)
		{
			try{
				idTienda = PrincipalLogueo.idTienda;
				if(idTienda == 0)
				{
					idTienda = 1;
				}
			}catch(Exception exc)
			{
				idTienda = 1;
			}
		}
		
	}
	
	public void fijarCliente()
	{
		lblNombreCliente.setText(nombreCliente);
		lDireccion.setText(direccion);
	}
	
	public void cargarConfiguracionMenu(int intMultimenu)
	{
		//Realizamos optimizaci�n sobre este m�todo para no ir por cada producto sino
		//que los traemos todos en un ArrayList y de esta manera interactuamos con ellos
		confiMenu = menuCtrl.obtenerConfMenu(intMultimenu);
		//Aqui deber�a pintarse el men�
		JButton btn = new JButton();
		ConfiguracionMenu objConfMenu;
		Object objGenerico;
		int numBoton = 1;
		for (int i = 0; i<6; i++)
		{
			for (int j = 0; j <6 ; j++)
			{
				objGenerico = confiMenu[i][j];
				boolean noMenu = false;
				if (objGenerico == null)
				{
					//btn = new JButton("Men� " + numBoton);
					btn = new JButton("");
					noMenu = true;
					btn.setOpaque(false);
					btn.setBorderPainted(false);
					btn.setContentAreaFilled(false);
				}
				else
				{
					objConfMenu = (ConfiguracionMenu) objGenerico;
					//Realizamos optimizacion para evitar la ida  base de datos por cada producto
					//definimos el objeto Receptor
					Producto prodBoton = new Producto();
					//Recorremos el arrayList con los productos tra�dos de la base de datos
					for(int z = 0; z < productos.size(); z++)
					{
						Producto proTemp = productos.get(z);
						//Si se encuentra el objeto en el arrelgo tomamos el objeto y salimos del ciclo for
						if(proTemp.getIdProducto() == objConfMenu.getIdProducto())
						{
							prodBoton = proTemp;
							break;
						}
					}
					//prodBoton = parPro.obtenerProducto(objConfMenu.getIdProducto());
					btn = new JButton();
					btn.setText("<html><center>"+ prodBoton.getIdProducto() + "- <br> " + prodBoton.getDescripcion()+"</center></html>");
					btn.setActionCommand(prodBoton.getIdProducto() + "-" + prodBoton.getDescripcion());
				}
				if (noMenu)
				{
					botones[i][j] = btn;
				}else
				{
					botones[i][j] = btn;
				}
				//Adicionamos para que tenga accion al dar dobleclick
				btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						capturarPreguntas(arg0.getSource());
					
					}
				});
				
				numBoton++;
			}
		}
		//finalizamos de llenar el arreglo
		MyTableModel model = new MyTableModel(new String[] {
				"col1", "col2", "col3", "col4", "col5", "col6"
			},botones);
		tableMenu.setModel(model);
	}

	
	public void capturarPreguntas(Object fuente)
	{
		
		JButton boton = (JButton) fuente;
		String texto = boton.getText();
		texto = texto.replaceAll("<html>", "");
		texto = texto.replaceAll("</html>", "");
		texto = texto.replaceAll("<center>", "");
		texto = texto.replaceAll("</center>", "");
		texto = texto.replaceAll("<br>", "");
		texto = texto.trim();
		StringTokenizer StrTokenProducto = new StringTokenizer(texto,"-");
		String strIdProducto = StrTokenProducto.nextToken();
		int idProducto = Integer.parseInt(strIdProducto);
		ArrayList<Pregunta> preguntasProducto = pedCtrl.obtenerPreguntaProducto(idProducto);
		//Si el producto no tiene preguntas forzadas se podr� facturar inmediatamente
		// Si es cierto lo anterior no se ha creado el pedido, por lo tanto deberemos de crear el pedido
		if (idPedido == 0)
		{
			crearEncabezadoPedido();
		}
		//Esto significa que la elecci�n no tiene preguntas Forzadas por lo tanto la adici�n del producto es directa
		double precioProducto = parPro.obtenerPrecioPilaProducto(idProducto);
		double cantidad = 1;
		DetallePedido detPedido = new DetallePedido(0,idPedido,idProducto,cantidad,precioProducto, cantidad*precioProducto, "",0, "N","", contadorDetallePedido);
		//En estepunto debemos de validar si el producto adicionado tiene productos incluidos
		//Agrupamos esta informaci�n en proIncluido
		ArrayList<ProductoIncluido> proIncluido = parPro.obtenerProductosIncluidos(idProducto, cantidad);
		//Capturamos el detalle pedido creado, validaremos si fue exitoso para agregarlo al contenedor y pantalla
		int idDetalle = pedCtrl.insertarDetallePedido(detPedido);
		detPedido.setIdDetallePedido(idDetalle);
		idDetallePedidoMaster = idDetalle;
		colorDetalle++;
		if(colorDetalle == 4)
		{
			colorDetalle = 1;
		}
		
		if(idDetalle > 0)
		{
			detallesPedido.add(detPedido);
			totalPedido = totalPedido + detPedido.getValorTotal();
			//En esta parte del c�digo realizamos el recorrido
			for(int i = 0; i< proIncluido.size(); i++)
			{
				
				ProductoIncluido proTem = proIncluido.get(i);
				double precioProTem = parPro.obtenerPrecioProducto(proTem.getIdproductoincluido(), proTem.getPrecio());
				detPedido = new DetallePedido(0, idPedido, proTem.getIdproductoincluido(),proTem.getCantidad(),precioProTem,proTem.getCantidad()*precioProTem,"",idDetallePedidoMaster, "N","", contadorDetallePedido);
				int idDetProInc = pedCtrl.insertarDetallePedido(detPedido);
				detPedido.setIdDetallePedido(idDetProInc);
				totalPedido = totalPedido + detPedido.getValorTotal();
				detallesPedido.add(detPedido);
			}
			txtValorPedidoSD.setText(Double.toString(totalPedido));
			txtValorTotal.setText(Double.toString(totalPedido - descuento));
			pintarDetallePedido();
			
		}
		if (preguntasProducto.size() == 0)
		{
			contadorDetallePedido++;
						
		}
		else
		{
			boolean tienePermiso = autCtrl.validarAccesoOpcion("PRO_005", Sesion.getAccesosOpcion());
			if (tienePermiso)
			{
				VentProEleccionForzada ElForzada = new VentProEleccionForzada(null, true,preguntasProducto, idProducto);
				ElForzada.setVisible(true);
			}else
			{
				JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opci�n/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}
	
	public void pintarDetallePedido()
	{
		//Clareamos el jtable
		DefaultTableModel tb = (DefaultTableModel) tableDetallePedido.getModel();
		tb.setRowCount(0);
		//Recorremos el ArrayList de DetallePedido para agregarlo al Jtable
		DefaultTableModel modeloDetalle = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	return false;
       	    }
       	    
       	    
       	};
		tableDetallePedido.setModel(modeloDetalle);
		modeloDetalle.setColumnIdentifiers(new Object [] {"idDetalle", "Cantidad", "Descripci�n",  "Valor", "idDetalleMaster","idDetalleModificador", "Estado","Contador"});
		tableDetallePedido.getColumnModel().getColumn(0).setMaxWidth(0);
		tableDetallePedido.getColumnModel().getColumn(0).setMinWidth(0);
		tableDetallePedido.getColumnModel().getColumn(1).setMaxWidth(20);
		tableDetallePedido.getColumnModel().getColumn(1).setMinWidth(20);
		tableDetallePedido.getColumnModel().getColumn(3).setMaxWidth(50);
		tableDetallePedido.getColumnModel().getColumn(3).setMinWidth(50);
		tableDetallePedido.getColumnModel().getColumn(4).setMaxWidth(0);
		tableDetallePedido.getColumnModel().getColumn(4).setMinWidth(0);
		tableDetallePedido.getColumnModel().getColumn(5).setMaxWidth(0);
		tableDetallePedido.getColumnModel().getColumn(5).setMinWidth(0);
		//Este me permitir� si un detallePedido est� anulado o no
		tableDetallePedido.getColumnModel().getColumn(6).setMaxWidth(0);
		tableDetallePedido.getColumnModel().getColumn(6).setMinWidth(0);
		//Este ser� el contador con el que controlaremos el color del detallePedido master
		tableDetallePedido.getColumnModel().getColumn(7).setMaxWidth(0);
		tableDetallePedido.getColumnModel().getColumn(7).setMinWidth(0);
		tableDetallePedido.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
		tableDetallePedido.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
		tableDetallePedido.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(20);
		tableDetallePedido.getTableHeader().getColumnModel().getColumn(1).setMinWidth(20);
		tableDetallePedido.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(50);
		tableDetallePedido.getTableHeader().getColumnModel().getColumn(3).setMinWidth(50);
		tableDetallePedido.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(0);
		tableDetallePedido.getTableHeader().getColumnModel().getColumn(4).setMinWidth(0);
		tableDetallePedido.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(0);
		tableDetallePedido.getTableHeader().getColumnModel().getColumn(5).setMinWidth(0);
		//Este me permitir� si un detallePedido est� anulado o no
		tableDetallePedido.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
		tableDetallePedido.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);
		//Este ser� el contador con el que controlaremos el color del detallePedido master
		tableDetallePedido.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(0);
		tableDetallePedido.getTableHeader().getColumnModel().getColumn(7).setMinWidth(0);
		for(int i = 0; i < detallesPedido.size();i++)
		{
			DetallePedido det = detallesPedido.get(i);
			Producto proDet = new Producto();
			//Recorremos el arrayList con los productos tra�dos de la base de datos
			for(int z = 0; z < productos.size(); z++)
			{
				Producto proTemp = productos.get(z);
				//Si se encuentra el objeto en el arrelgo tomamos el objeto y salimos del ciclo for
				if(proTemp.getIdProducto() == det.getIdProducto())
				{
					proDet = proTemp;
					break;
				}
			}
			//Producto proDet = parPro.obtenerProducto(det.getIdProducto());
			String [] object = new String[]{Integer.toString(det.getIdDetallePedido()),Double.toString(det.getCantidad()),proDet.getDescripcion(),Double.toString(det.getValorTotal()), Integer.toString(det.getIdDetallePedidoMaster()), Integer.toString(det.getIdDetalleModificador()), det.getEstado(), Integer.toString(det.getContadorDetallePedido())};
			modeloDetalle.addRow(object);
		}
		int cantFilas = tableDetallePedido.getRowCount() -1;
		setCellRender(tableDetallePedido);
	}
	
	public void setCellRender(JTable table) {
        Enumeration<TableColumn> en = table.getColumnModel().getColumns();
        while (en.hasMoreElements()) {
            TableColumn tc = en.nextElement();
            tc.setCellRenderer(new CellRenderPedido());
        }
    }
	
	public void crearEncabezadoPedido()
	{
		FechaSistema fecha = pedCtrl.obtenerFechasSistema();
		String fechaPedido = fecha.getFechaApertura();
		idPedido = pedCtrl.InsertarEncabezadoPedido(idTienda, idCliente, fechaPedido, Sesion.getUsuario());
		if(idPedido == 0)
		{
			JOptionPane.showMessageDialog(null, "Error al insertar el encabezado de un pedido " , "Error Inserci�n Encabezado", JOptionPane.ERROR_MESSAGE);
			return;
		}
		txtNroPedido.setText(Integer.toString(idPedido));
	}
	
	public void validarSalidaControlada()
	{
		
	}
	
	public void initComboMotivoAnulacion()
	{
		ArrayList<MotivoAnulacionPedido> motAnulacion = pedCtrl.obtenerMotivosAnulacion();
		for(int i = 0; i < motAnulacion.size();i++)
		{
			MotivoAnulacionPedido fila = (MotivoAnulacionPedido)  motAnulacion.get(i);
			comboMotivoAnulacion.addItem(fila);
		}
	}
	
	/**
	 * M�todo que se encarga de la anulaci�n de un pedido validando si es o no un pedido reabierto, para lo cual tambien se recibe un par�metro
	 * @param salirVentana, el par�metro en cuesti�n valida si anulando el pedido se debe o no salir de la ventana de pedidos
	 */
	public void anularPedido(boolean salirVentana)
	{
		// Validamos si el pedido es nuevo y no es reabierto
		if(!esReabierto)
		{
			//Mostramos la pantalla de confirmaci�n para validar si deseamos o no la anulaci�n
			int resp = JOptionPane.showConfirmDialog(null, "�Est� seguro que desea Eliminar el Pedido que se est� tomando?", "Confirmaci�n de Salida de Pantalla" , JOptionPane.YES_NO_OPTION);
			if (resp == 0)
			{
				// Si selecciona que si se desea anular procedemos a realizar la eliminaci�n del detalle pedido, no realizamos todav�a la anulaci�n del pedido
				boolean eliDetallePedido = pedCtrl.anularBorrarDetallePedido(idPedido);
				//Prendremos la variable que indica qeu se anulo el pedido completo sin descontar
				esAnuladoSinDescontar = true;
				//En este punto se debe realizar la reacomodaci�n de la pantalla con la informaci�n eliminada
				//La l�gica seguida es CUANDO EL PEDIDO NO ES REABIERTO
				//Con este ciclo FOR se realiza el borrado y ajuste en pantala
				for(int j = 0; j < detallesPedido.size(); j++)
				{
					DetallePedido detCadaPedido = detallesPedido.get(j);
					// Cambiamos para la eliminaci�n que se tenga el iddetalle_pedido o el iddetalle_pedido_master
					double valorItem = detCadaPedido.getValorTotal();
					detallesPedido.remove(j);
					// j se reduce en uno teniendo en cuenta que se elimin� un elemento
					j--;
					totalPedido = totalPedido - valorItem;
					txtValorPedidoSD.setText(Double.toString(totalPedido));
					txtValorTotal.setText(Double.toString(totalPedido - descuento));
					}
				//Validamos si se debe salir o no de la ventana, que viene en la variable que se recibe como para�mtro
				if(salirVentana)
				{
					clarearVarEstaticas();
					clarearVarNoEstaticas(false);
					VentPedTransaccional transacciones = new VentPedTransaccional();
					transacciones.setVisible(true);
					dispose();
					return;
				}
			}else
			{
				return;
			}
			
			//En caso del que el pedido hay sido reabierto
		}else if(esReabierto)
		{	
			//Desplegamos para que el usuario seleccione el motivo de anulaci�n
			int resp = JOptionPane.showConfirmDialog(null, comboMotivoAnulacion, "Selecciona Motivo de Anulaci�n.",JOptionPane.YES_NO_OPTION);
			if (resp == 0)
			{
				//Capturamos en caso de aceptar la anulaci�n el motivo de anulaci�n seleccionado
				MotivoAnulacionPedido motAnu = (MotivoAnulacionPedido)comboMotivoAnulacion.getSelectedItem();
				//Se realiza la anulaci�n del pedido incluyendo el motivo de anulaci�n
				boolean anuDetallePedido = pedCtrl.anularPedido(idPedido, motAnu.getIdMotivoAnulacion());
				//validamos si la anulaci�n fue correcta y el tipo de anulaci�n descuenta pedido
				if((anuDetallePedido) &&(motAnu.getDescuentaInventario().equals(new String("S"))))
				{
					//Realizamos el descuento de inventarios
					boolean reintInv = invCtrl.reintegrarInventarioPedido(idPedido);
					if(!reintInv)
					{
						JOptionPane.showMessageDialog(null, "Se presentaron inconvenientes en el reintegro de los inventarios " , "Error en reintegro de Inventarios ", JOptionPane.ERROR_MESSAGE);
					}
				
				}
				//Realizamos el limpiado de las variables de la pantalla de pedidos
				if(salirVentana)
				{
					clarearVarEstaticas();
					clarearVarNoEstaticas(false);
				}else
				{
					clarearVarEstaticasNoSalir();
					clarearVarNoEstaticasNoSalir();
					for(int j = 0; j < detallesPedido.size(); j++)
					{
						DetallePedido detCadaPedido = detallesPedido.get(j);
						detCadaPedido.setEstado("A");
						detallesPedido.set(j, detCadaPedido);
					}
					//Marcamos que fue anulado el pedido y se quitar� esta marca en caso de agregar un producto
					esAnulado = true;
					pintarDetallePedido();
				}
				
			}
		}
	}
}
