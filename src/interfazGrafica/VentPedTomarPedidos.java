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

import capaControlador.InventarioCtrl;
import capaControlador.MenuCtrl;
import capaControlador.ParametrosDireccionCtrl;
import capaControlador.ParametrosProductoCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.ConfiguracionMenu;
import capaModelo.DetallePedido;
import capaModelo.FechaSistema;
import capaModelo.Municipio;
import capaModelo.Pregunta;
import capaModelo.Producto;
import capaModelo.ProductoIncluido;
import capaModelo.TipoPedido;
import renderTable.CellRenderPedido;
import renderTable.CellRenderTransaccional;

import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JRadioButton;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.sql.Date;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;

public class VentPedTomarPedidos extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable tableMenu;
	//Creamos una variable Arreglo Global que contendrï¿½ la Configuraciï¿½n Menï¿½
	private ConfiguracionMenu[][] confiMenu;
	// El arreglo con los botones del menï¿½ activo se manejarï¿½ como variable global
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
	JLabel lblIdCliente;
	JLabel lblNombreCliente;
	private JTable tableDetallePedido;
	//TODAS LAS VARIABLES ESTÁTICAS DEL ESTE JFRAME
	//Parï¿½metros que se tendrï¿½n en Tomador Pedidos para la selecciï¿½n de clientes
	//VARIABLES ESTÁTICAS
	public static int idCliente = 0;
	public static int idPedido = 0;
	public static int idTienda = 0;
	public static double totalPedido = 0;
	public static double descuento = 0;
	public static String usuario = "";
	public static String nombreCliente = "";
	public static int idDetallePedidoMaster = 0;
	public static ArrayList<DetallePedido> detallesPedido = new ArrayList();
	static int numTipoPedido = 0;
	static int numTipoPedidoAct = 0;
	static ArrayList<TipoPedido> tiposPedidos;
	static boolean tieneFormaPago = false;
	static boolean tieneDescuento = false;
	public static int colorDetalle = 0;
	
	
	/**
	 * Launch the application.
	 */
	public static void clarearVarEstaticas()
	{
		idCliente = 0;
		idPedido = 0;
		idTienda = 0;
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
		btnDescuento.setBackground(Color.LIGHT_GRAY);
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
		
		
		setTitle("TOMADOR DE PEDIDOS");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1024, 770);
		this.setExtendedState(MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelPedido = new JPanel();
		panelPedido.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0), 3), null));
		panelPedido.setBounds(0, 0, 225, 315);
		contentPane.add(panelPedido);
		panelPedido.setLayout(null);
		
		table = new JTable();
		table.setBounds(10, 272, 205, -260);
		panelPedido.add(table);
		
		JLabel lblValorTotalSD = new JLabel("Valor Total SD");
		lblValorTotalSD.setBounds(10, 200, 83, 14);
		panelPedido.add(lblValorTotalSD);
		
		JButton btnAnularItem = new JButton("Anular Item");
		
		//Método donde se define la acción del Anular item en la ventana de tomador de pedidos.
		btnAnularItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int filaSeleccionada = tableDetallePedido.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar algún item del pedido para Eliminar " , "No ha Seleccionado item para Eliminar ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// Se captura el valor del idDetalle que se desea eliminar
				int idDetalleEliminar = Integer.parseInt((String)tableDetallePedido.getValueAt(filaSeleccionada, 0));
				PedidoCtrl pedCtrl = new PedidoCtrl();
				//Se obtiene en un boolean si el idDetalle es maestro o no, sino lo es, no se puede eliminar, pues se debe
				//eliminar el master
				boolean esMaster = pedCtrl.validarDetalleMaster(idDetalleEliminar);
				if(!esMaster)
				{
					JOptionPane.showMessageDialog(null, "Debe seleccionar un item Master " , "Error al Eliminar", JOptionPane.ERROR_MESSAGE);
					return;
				}
				//Este método borra el idDetalle y si es master le borra los detalles.
				boolean respEliminar = pedCtrl.anularDetallePedido(idDetalleEliminar);
				if(!respEliminar)
				{
					JOptionPane.showMessageDialog(null, "No fue posible Eliminar el item de pedido " , "Error al Eliminar", JOptionPane.ERROR_MESSAGE);
					return;
				}
				for(int j = 0; j < detallesPedido.size(); j++)
				{
					DetallePedido detCadaPedido = detallesPedido.get(j);
					// Cambiamos para la eliminación que se tenga el iddetalle_pedido o el iddetalle_pedido_master
					if(detCadaPedido.getIdDetallePedido() == idDetalleEliminar || detCadaPedido.getIdDetallePedidoMaster() == idDetalleEliminar)
					{
						detCadaPedido.setCantidad(detCadaPedido.getCantidad()*-1);
						double valorItem = detCadaPedido.getValorTotal();
						detCadaPedido.setValorTotal(detCadaPedido.getValorTotal()*-1);
						detallesPedido.set(j, detCadaPedido);
						// j se reduce en uno teniendo en cuenta que se eliminó un elemento
						//j--;
						totalPedido = totalPedido - valorItem;
						txtValorPedidoSD.setText(Double.toString(totalPedido));
						txtValorTotal.setText(Double.toString(totalPedido - descuento));
					}
				}
				pintarDetallePedido();
				
			}
		});
		btnAnularItem.setBounds(51, 272, 112, 36);
		panelPedido.add(btnAnularItem);
		
		txtValorPedidoSD = new JTextField();
		txtValorPedidoSD.setEditable(false);
		txtValorPedidoSD.setBounds(97, 197, 118, 20);
		panelPedido.add(txtValorPedidoSD);
		txtValorPedidoSD.setColumns(10);
		
		JLabel lblDescuento = new JLabel("Descuento");
		lblDescuento.setBounds(10, 225, 83, 14);
		panelPedido.add(lblDescuento);
		
		txtDescuento = new JTextField();
		txtDescuento.setEditable(false);
		txtDescuento.setBounds(97, 222, 118, 20);
		panelPedido.add(txtDescuento);
		txtDescuento.setColumns(10);
		
		JLabel lblValorTotal = new JLabel("Valor Total");
		lblValorTotal.setBounds(10, 250, 83, 14);
		panelPedido.add(lblValorTotal);
		
		txtValorTotal = new JTextField();
		txtValorTotal.setEditable(false);
		txtValorTotal.setColumns(10);
		txtValorTotal.setBounds(97, 250, 118, 20);
		panelPedido.add(txtValorTotal);
		
		txtDescuento.setText(Double.toString(descuento));
		txtValorTotal.setText(Double.toString(totalPedido - descuento));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 205, 178);
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
				PedidoCtrl pedCtrlCon = new PedidoCtrl();
				boolean hayModCon = pedCtrlCon.detalleTieneModificadorCon(idDetalleTratar);
				if(hayModCon)
				{
					btnProductoCon.setEnabled(true);
				}
				else
				{
					btnProductoCon.setEnabled(false);
				}
				boolean hayModSin = pedCtrlCon.detalleTieneModificadorSin(idDetalleTratar);
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
		panelMenu.setBounds(235, 0, 769, 315);
		contentPane.add(panelMenu);
		panelMenu.setLayout(null);
		
		tableMenu = new JTable();
		tableMenu.setShowGrid(false);
		tableMenu.setBorder(null);
		tableMenu.setBounds(10, 11, 749, 282);
		tableMenu.setCellSelectionEnabled(true);
		tableMenu.setDefaultRenderer(Object.class, new ButtonRenderer());
		tableMenu.setCellEditor(new ButtonEditor());
		tableMenu.setRowHeight(46);
		tableMenu.setOpaque(false);
		JButton btn;
			
		botones = new Object[6][6];
		cargarConfiguracionMenu(1);
		tableMenu.setDefaultRenderer(JButton.class, new ButtonRenderer());
		tableMenu.setDefaultEditor(JButton.class, new ButtonEditor());
		panelMenu.add(tableMenu);
		
		
		JPanel panelAgrupadorMenu = new JPanel();
		panelAgrupadorMenu.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panelAgrupadorMenu.setBounds(235, 314, 769, 69);
		contentPane.add(panelAgrupadorMenu);
		panelAgrupadorMenu.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnMultimenu_1 = new JButton("Multimenu 1");
		JButton btnMultimenu_2 = new JButton("Multimenu 2");
		JButton btnMultimenu_3 = new JButton("Multimenu 3");
		JButton btnMultimenu_4 = new JButton("Multimenu 4");
		JButton btnMultimenu_5 = new JButton("Multimenu 5");
		JButton btnMultimenu_6 = new JButton("Multimenu 6");
		btnMultimenu_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnMultimenu_1.setBackground(Color.YELLOW);
				btnMultimenu_2.setBackground(new Color(240,240,240));
				btnMultimenu_3.setBackground(new Color(240,240,240));
				btnMultimenu_4.setBackground(new Color(240,240,240));
				btnMultimenu_5.setBackground(new Color(240,240,240));
				btnMultimenu_6.setBackground(new Color(240,240,240));
				cargarConfiguracionMenu(1);
			}
		});
		btnMultimenu_1.setBackground(Color.YELLOW);
		panelAgrupadorMenu.add(btnMultimenu_1);
		
		
		btnMultimenu_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				btnMultimenu_2.setBackground(Color.YELLOW);
				btnMultimenu_1.setBackground(new Color(240,240,240));
				btnMultimenu_3.setBackground(new Color(240,240,240));
				btnMultimenu_4.setBackground(new Color(240,240,240));
				btnMultimenu_5.setBackground(new Color(240,240,240));
				btnMultimenu_6.setBackground(new Color(240,240,240));
				cargarConfiguracionMenu(2);
			}
		});
		panelAgrupadorMenu.add(btnMultimenu_2);
		
		
		btnMultimenu_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnMultimenu_3.setBackground(Color.YELLOW);
				btnMultimenu_1.setBackground(new Color(240,240,240));
				btnMultimenu_2.setBackground(new Color(240,240,240));
				btnMultimenu_4.setBackground(new Color(240,240,240));
				btnMultimenu_5.setBackground(new Color(240,240,240));
				btnMultimenu_6.setBackground(new Color(240,240,240));
				cargarConfiguracionMenu(3);
			}
		});
		panelAgrupadorMenu.add(btnMultimenu_3);
		
		
		btnMultimenu_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnMultimenu_4.setBackground(Color.YELLOW);
				btnMultimenu_1.setBackground(new Color(240,240,240));
				btnMultimenu_2.setBackground(new Color(240,240,240));
				btnMultimenu_3.setBackground(new Color(240,240,240));
				btnMultimenu_5.setBackground(new Color(240,240,240));
				btnMultimenu_6.setBackground(new Color(240,240,240));
				cargarConfiguracionMenu(4);
			}
		});
		panelAgrupadorMenu.add(btnMultimenu_4);
		
		
		
		btnMultimenu_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnMultimenu_5.setBackground(Color.YELLOW);
				btnMultimenu_1.setBackground(new Color(240,240,240));
				btnMultimenu_2.setBackground(new Color(240,240,240));
				btnMultimenu_3.setBackground(new Color(240,240,240));
				btnMultimenu_4.setBackground(new Color(240,240,240));
				btnMultimenu_6.setBackground(new Color(240,240,240));
				cargarConfiguracionMenu(5);
			}
		});
		panelAgrupadorMenu.add(btnMultimenu_5);
		
		
		btnMultimenu_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnMultimenu_6.setBackground(Color.YELLOW);
				btnMultimenu_2.setBackground(new Color(240,240,240));
				btnMultimenu_3.setBackground(new Color(240,240,240));
				btnMultimenu_4.setBackground(new Color(240,240,240));
				btnMultimenu_5.setBackground(new Color(240,240,240));
				btnMultimenu_1.setBackground(new Color(240,240,240));
				cargarConfiguracionMenu(6);
			}
		});
		panelAgrupadorMenu.add(btnMultimenu_6);
		
		JPanel panelAcciones = new JPanel();
		panelAcciones.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panelAcciones.setBounds(0, 433, 1004, 69);
		contentPane.add(panelAcciones);
		panelAcciones.setLayout(null);
		
		JButton btnAsignarCliente = new JButton("Asignar Cliente");
		btnAsignarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Se deberï¿½a crear una nueva ventana para la asignaciï¿½n y creaciï¿½n de clientes
				//Al momento de instanciar VentCliente se le pasarï¿½ como parï¿½metro el idCliente del pedido
				VentCliCliente cliente = new VentCliCliente(idCliente);
				cliente.setVisible(true);
			}
		});
		btnAsignarCliente.setBounds(66, 11, 140, 47);
		panelAcciones.add(btnAsignarCliente);
		
		JButton btnAnularPedido = new JButton("Anular Pedido");
		btnAnularPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea Eliminar el Pedido que se está tomando?");
				if (resp == 0)
				{
					PedidoCtrl pedCtrl = new PedidoCtrl();
					boolean eliPedido = pedCtrl.anularPedido(idPedido);
					if(eliPedido)
					{
						InventarioCtrl invCtrl = new InventarioCtrl();
						boolean reintInv = invCtrl.reintegrarInventarioPedido(idPedido);
						if(!reintInv)
						{
							JOptionPane.showMessageDialog(null, "Se presentaron inconvenientes en el reintegro de los inventarios " , "Error en reintegro de Inventarios ", JOptionPane.ERROR_MESSAGE);
						}
						clarearVarEstaticas();
						pintarDetallePedido();
						txtValorPedidoSD.setText("0");
						txtDescuento.setText("0");
						txtValorTotal.setText("0");
						txtNroPedido.setText("");
					}
					else
					{
						
					}
					
				}
			}
		});
		btnAnularPedido.setBounds(216, 11, 140, 47);
		panelAcciones.add(btnAnularPedido);
		
		btnDescuento = new JButton("Descuento");
		btnDescuento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PedidoCtrl pedCtrl = new PedidoCtrl();
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
								VentPedDescuento ventDescuento = new VentPedDescuento();
								ventDescuento.setVisible(true);
								descuento = 0;
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Se presentó un inconveniente por favor contacte al administrador del sistema " , "Error Eliminando descuento ", JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
					}
					else
					{
						VentPedDescuento ventDescuento = new VentPedDescuento();
						ventDescuento.setVisible(true);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "No se ha agregado ningún item al pedido, no debe haber descuento " , "Error Agregando Descuento ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				
			}
		});
		
		btnDescuento.setBounds(366, 11, 140, 47);
		panelAcciones.add(btnDescuento);
		
		btnFinalizarPedido = new JButton("Finalizar Pedido");

		btnFinalizarPedido.setBounds(516, 11, 140, 47);

		btnFinalizarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				PedidoCtrl pedCtrl = new PedidoCtrl();
				if (idPedido > 0)
				{
					boolean hayFormaPago = pedCtrl.existeFormaPago(idPedido);
					if (hayFormaPago)
					{
						int seleccion = JOptionPane.showOptionDialog(
								null,
								"El producto ya presenta Forma de Pago, desea borrar la Forma de Pago actual e ingresar uno nuevo",
								"Forma Pago ya Registrado",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE,
								null,
								new Object[]{"Borrar e Ingresar Nueva Forma de Pago","Cancelar"},
								"Borrar e Ingresar Nueva Forma Pago");
						if(seleccion == 0)
						{
							boolean respuesta = pedCtrl.eliminarPedidoFormaPago(idPedido);
							if(respuesta)
							{
								VentPedFinPago Finalizar = new VentPedFinPago();
								Finalizar.setVisible(true);
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Se presentó un inconveniente por favor contacte al administrador del sistema " , "Error Eliminando Forma Pago ", JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
					}
					else
					{
						VentPedFinPago Finalizar = new VentPedFinPago();
						Finalizar.setVisible(true);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "No se ha agregado ningún item al pedido, no debe haber Forma de Pago " , "Error Agregando Forma Pago ", JOptionPane.ERROR_MESSAGE);
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
					VentPedTransaccional transacciones = new VentPedTransaccional();
					transacciones.setVisible(true);
					dispose();
				}
				else
				{
					int seleccion = JOptionPane.showOptionDialog(
							null,
							"Actualmente se está tomando un pedido y no ha sido finalizado, desea anular el pedido e ir a la pantalla transaccional?",
							"Pedido sin Finalizar",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							new Object[]{"Anular el Pedido e ir a pantalla transaccional","No Salir de esta Pantalla"},
							"Anular el Pedido e ir a pantalla transaccional");
					if(seleccion == 0)
					{
						PedidoCtrl pedCtrl = new PedidoCtrl();
						boolean eliPedido = pedCtrl.anularPedido(idPedido);
						if(eliPedido)
						{
							clarearVarEstaticas();
							pintarDetallePedido();
							txtValorPedidoSD.setText("0");
							txtDescuento.setText("0");
							txtValorTotal.setText("0");
							txtNroPedido.setText("");
						}
						VentPedTransaccional transacciones = new VentPedTransaccional();
						transacciones.setVisible(true);
						dispose();
					}
				}
				
			}
		});
		btnMaestroPedidos.setBounds(666, 11, 140, 47);
		panelAcciones.add(btnMaestroPedidos);
		
		JButton btnTerminarPedido = new JButton("Terminar Pedido");
		btnTerminarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PedidoCtrl pedCtrl = new PedidoCtrl();
				//Ingresamos lógica para tomar el tipo de pedido 
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
						   null,"<html><center><b>A continuación la informacion para Confirmación del Pedido.</b><br>"
					                 + "<p>CLIENTE: "+ VentPedTomarPedidos.nombreCliente+" </p>" +
								   "<p>Por un valor TOTAL: " + totalPedido  +"</p>" +
								   "<p>Con un Descuento: " + VentPedTomarPedidos.descuento  +"</p>" +
								   "<p>NÚMERO DE PEDIDO: " + VentPedTomarPedidos.idPedido +"</p>" 
								   
						   );
				if (JOptionPane.OK_OPTION == confirmado)
				{
				
					// En este punto finalizamos el pedido
					//VentPedConfirmarPedido ventConfirmarPedido = new VentPedConfirmarPedido(nombreCliente, totalPedido, descuento, idPedido, new javax.swing.JFrame(), true);
					//	ventConfirmarPedido.setVisible(true);
					boolean resFinPedido = pedCtrl.finalizarPedido(idPedido, 30/*tiempoPedido*/, idTipoPedido);
					if (resFinPedido)
					{
						//En este punto es cuando clareamos las variables del tipo de pedido que son estáticas y sabiendo qeu se finalizó
						//el pedido es neceseario clarear las variables del jFrame de TomarPedidos
						InventarioCtrl invCtrl = new InventarioCtrl();
						boolean reintInv = invCtrl.descontarInventarioPedido(idPedido);
						if(!reintInv)
						{
							JOptionPane.showMessageDialog(null, "Se presentaron inconvenientes en el descuento de los inventarios " , "Error en Descuento de Inventarios ", JOptionPane.ERROR_MESSAGE);
						}
						clarearVarEstaticas();
						txtValorPedidoSD.setText(Double.toString(totalPedido));
						pintarDetallePedido();
						txtDescuento.setText(Double.toString(descuento));
						txtValorTotal.setText(Double.toString(totalPedido - descuento));
						txtNroPedido.setText(Integer.toString(idPedido));
					}
				}
			}
		});
		btnTerminarPedido.setBounds(816, 11, 140, 47);
		panelAcciones.add(btnTerminarPedido);
		
		
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 317, 225, 116);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblTipoPedido = new JLabel("Tipo Pedido");
		lblTipoPedido.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTipoPedido.setBounds(76, 0, 71, 14);
		panel.add(lblTipoPedido);
		
		btnTipoPedido = new JButton("");
		btnTipoPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//Acciones relacionadas con el botón de tipo de pedido
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
					btnDescuento.setBackground(Color.LIGHT_GRAY);
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
		
		lblIdCliente = new JLabel("Id Cliente");
		lblIdCliente.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblIdCliente.setBounds(77, 496, 108, 20);
		contentPane.add(lblIdCliente);
		
		lblNombreCliente = new JLabel("Nombre Cliente");
		lblNombreCliente.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNombreCliente.setBounds(206, 499, 390, 17);
		contentPane.add(lblNombreCliente);
		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
		
		//Acciones relacionadas con el botón de tipo de pedido
		PedidoCtrl pedCtrl = new PedidoCtrl();
		tiposPedidos = pedCtrl.obtenerTiposPedidoNat();
		numTipoPedido = tiposPedidos.size();
		//Inicializamos el botón con el tipo de pedido inicial
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
		panelModificadores.setBounds(235, 389, 769, 37);
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
					int idDetalleTratar = Integer.parseInt((String)tableDetallePedido.getValueAt(filaSeleccionada, 0));
					VentPedModificador ventMod = new VentPedModificador(null, true, idDetalleTratar, true, false);
					ventMod.setVisible(true);
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
					int idDetalleTratar = Integer.parseInt((String)tableDetallePedido.getValueAt(filaSeleccionada, 0));
					VentPedModificador ventMod = new VentPedModificador(null, true, idDetalleTratar, false, true);
					ventMod.setVisible(true);
				}
			}
		});
		btnProductoSin.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnProductoSin.setBounds(371, 0, 124, 37);
		panelModificadores.add(btnProductoSin);
		//Deshabilitamos los botones de modificadores
		btnProductoCon.setEnabled(false);
		btnProductoSin.setEnabled(false);
		
	}
	
	public void fijarCliente()
	{
		lblIdCliente.setText(Integer.toString(idCliente));
		lblNombreCliente.setText(nombreCliente);
	}
	
	public void cargarConfiguracionMenu(int intMultimenu)
	{
//		String multime = (String) comboBoxMultiMenu.getSelectedItem();
//		StringTokenizer StrMulti = new StringTokenizer(multime,"-");
//		StrMulti.nextToken();
//		int intMultimenu = Integer.parseInt(StrMulti.nextToken().trim());
		MenuCtrl menuCtrl = new MenuCtrl();
		confiMenu = menuCtrl.obtenerConfMenu(intMultimenu);
		//Aqui deberï¿½a pintarse el menï¿½
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
					//btn = new JButton("Menï¿½ " + numBoton);
					btn = new JButton("");
					noMenu = true;
					btn.setOpaque(false);
					btn.setBorderPainted(false);
					btn.setContentAreaFilled(false);
				}
				else
				{
					objConfMenu = (ConfiguracionMenu) objGenerico;
					ParametrosProductoCtrl parPro = new ParametrosProductoCtrl();
					Producto prodBoton = parPro.obtenerProducto(objConfMenu.getIdProducto());
					btn = new JButton();
					btn.setText("<html><center>"+ prodBoton.getIdProducto() + "- <br> " + prodBoton.getDescripcion()+"</center></html>");
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
		PedidoCtrl pedCtrl = new PedidoCtrl();
		ArrayList<Pregunta> preguntasProducto = pedCtrl.obtenerPreguntaProducto(idProducto);
		//Si el producto no tiene preguntas forzadas se podrá facturar inmediatamente
		// Si es cierto lo anterior no se ha creado el pedido, por lo tanto deberemos de crear el pedido
		if (idPedido == 0)
		{
			crearEncabezadoPedido();
		}
		//Esto significa que la elección no tiene preguntas Forzadas por lo tanto la adición del producto es directa
		ParametrosProductoCtrl parProducto = new ParametrosProductoCtrl();
		double precioProducto = parProducto.obtenerPrecioPilaProducto(idProducto);
		double cantidad = 1;
		DetallePedido detPedido = new DetallePedido(0,idPedido,idProducto,cantidad,precioProducto, cantidad*precioProducto, "",0);
		//En estepunto debemos de validar si el producto adicionado tiene productos incluidos
		//Agrupamos esta información en proIncluido
		ArrayList<ProductoIncluido> proIncluido = parProducto.obtenerProductosIncluidos(idProducto, cantidad);
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
			//En esta parte del código realizamos el recorrido
			for(int i = 0; i< proIncluido.size(); i++)
			{
				
				ProductoIncluido proTem = proIncluido.get(i);
				double precioProTem = parProducto.obtenerPrecioProducto(proTem.getIdproductoincluido(), proTem.getPrecio());
				detPedido = new DetallePedido(0, idPedido, proTem.getIdproductoincluido(),proTem.getCantidad(),precioProTem,proTem.getCantidad()*precioProTem,"",idDetallePedidoMaster);
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
			
			
		}
		else
		{
			
			VentProEleccionForzada ElForzada = new VentProEleccionForzada(null, true,preguntasProducto, idProducto);
			ElForzada.setVisible(true);
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
		modeloDetalle.setColumnIdentifiers(new Object [] {"idDetalle", "Cantidad", "Descripción",  "Valor", "idDetalleMaster","idDetalleModificador"});
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
		
		ParametrosProductoCtrl parProducto = new ParametrosProductoCtrl();
		for(int i = 0; i < detallesPedido.size();i++)
		{
			DetallePedido det = detallesPedido.get(i);
			Producto proDet = parProducto.obtenerProducto(det.getIdProducto());
			String [] object = new String[]{Integer.toString(det.getIdDetallePedido()),Double.toString(det.getCantidad()),proDet.getDescripcion(),Double.toString(det.getValorTotal()), Integer.toString(det.getIdDetallePedidoMaster()), Integer.toString(det.getIdDetalleModificador())};
			modeloDetalle.addRow(object);
		}
		CellRenderPedido.colorDeta = 0;
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
		PedidoCtrl pedCtrl = new PedidoCtrl();
		FechaSistema fecha = pedCtrl.obtenerFechasSistema();
		String fechaPedido = fecha.getFechaApertura();
		idPedido = pedCtrl.InsertarEncabezadoPedido(idTienda, idCliente, fechaPedido, Sesion.getUsuario());
		if(idPedido == 0)
		{
			JOptionPane.showMessageDialog(null, "Error al insertar el encabezado de un pedido " , "Error Inserción Encabezado", JOptionPane.ERROR_MESSAGE);
			return;
		}
		txtNroPedido.setText(Integer.toString(idPedido));
	}
	
	public void validarSalidaControlada()
	{
		
	}
}
