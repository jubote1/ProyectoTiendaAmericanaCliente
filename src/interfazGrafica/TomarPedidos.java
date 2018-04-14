package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;

import capaControlador.MenuCtrl;
import capaControlador.ParametrosProductoCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.ConfiguracionMenu;
import capaModelo.DetallePedido;
import capaModelo.Pregunta;
import capaModelo.Producto;

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

public class TomarPedidos extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable tableMenu;
	//Creamos una variable Arreglo Global que contendrï¿½ la Configuraciï¿½n Menï¿½
	private ConfiguracionMenu[][] confiMenu;
	// El arreglo con los botones del menï¿½ activo se manejarï¿½ como variable global
	private Object[][] botones;
	//Parï¿½metros que se tendrï¿½n en Tomador Pedidos para la selecciï¿½n de clientes
	public static int idCliente = 0;
	public static int idPedido = 0;
	public static int idTienda = 0;
	public static double totalPedido = 0;
	public static String usuario = "";
	public static String nombreCliente = "";
	public static int idDetallePedidoMaster = 0;
	public static ArrayList<DetallePedido> detallesPedido = new ArrayList();
	JLabel lblIdCliente;
	JLabel lblNombreCliente;
	private JTable tableDetallePedido;
	private JTextField txtValorPedido;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TomarPedidos frame = new TomarPedidos();
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
	public TomarPedidos() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				fijarCliente();
				txtValorPedido.setText(Double.toString(totalPedido));
				pintarDetallePedido();
			}
		});
		
		
		
		
		setTitle("TOMADOR DE PEDIDOS");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1024, 770);
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
		
		JLabel lblValorTotal = new JLabel("Valor Total ");
		lblValorTotal.setBounds(10, 236, 83, 14);
		panelPedido.add(lblValorTotal);
		
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
				int idDetalleEliminar = (int)tableDetallePedido.getValueAt(filaSeleccionada, 0);
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
				boolean respEliminar = pedCtrl.eliminarDetallePedido(idDetalleEliminar);
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
						detallesPedido.remove(j);
						// j se reduce en uno teniendo en cuenta que se eliminó un elemento
						j--;
						totalPedido = totalPedido - detCadaPedido.getValorTotal();
						txtValorPedido.setText(Double.toString(totalPedido));
					}
				}
				pintarDetallePedido();
				
			}
		});
		btnAnularItem.setBounds(51, 265, 112, 36);
		panelPedido.add(btnAnularItem);
		
		tableDetallePedido = new JTable();
		tableDetallePedido.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		tableDetallePedido.setShowVerticalLines(false);
		tableDetallePedido.setShowHorizontalLines(false);
		tableDetallePedido.setShowGrid(false);
		tableDetallePedido.setBounds(10, 11, 205, 214);
		panelPedido.add(tableDetallePedido);
		
		txtValorPedido = new JTextField();
		txtValorPedido.setBounds(77, 233, 118, 20);
		panelPedido.add(txtValorPedido);
		txtValorPedido.setColumns(10);
		
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
		panelAcciones.setBounds(0, 394, 1004, 69);
		contentPane.add(panelAcciones);
		panelAcciones.setLayout(null);
		
		JButton btnAsignarCliente = new JButton("Asignar Cliente");
		btnAsignarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Se deberï¿½a crear una nueva ventana para la asignaciï¿½n y creaciï¿½n de clientes
				//Al momento de instanciar VentCliente se le pasarï¿½ como parï¿½metro el idCliente del pedido
				VentCliente cliente = new VentCliente(idCliente);
				cliente.setVisible(true);
			}
		});
		btnAsignarCliente.setBounds(145, 11, 140, 47);
		panelAcciones.add(btnAsignarCliente);
		
		JButton btnAnularPedido = new JButton("Anular Pedido");
		btnAnularPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int resp = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea Eliminar el Pedido que se está tomando?");
				if (resp == 0)
				{
					PedidoCtrl pedCtrl = new PedidoCtrl();
					boolean eliPedido = pedCtrl.anularPedidoEliminar(idPedido);
					if(eliPedido)
					{
						idCliente = 0;
						idPedido = 0;
						idTienda = 0;
						totalPedido = 0;
						usuario = "";
						nombreCliente = "";
						detallesPedido = new ArrayList();
						pintarDetallePedido();
						txtValorPedido.setText("0");
					}
					else
					{
						
					}
					
				}
			}
		});
		btnAnularPedido.setBounds(295, 11, 140, 47);
		panelAcciones.add(btnAnularPedido);
		
		JButton btnDescuento = new JButton("Descuento");
		
		btnDescuento.setBounds(445, 11, 140, 47);
		panelAcciones.add(btnDescuento);
		
		JButton btnFinalizarPedido = new JButton("Finalizar Pedido");

		btnFinalizarPedido.setBounds(595, 11, 140, 47);

		btnFinalizarPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FinPago Finalizar = new FinPago();
				Finalizar.setVisible(true);
			}
		});
		
		panelAcciones.add(btnFinalizarPedido);
		
		JButton btnMaestroPedidos = new JButton("Maestro Pedidos");
		btnMaestroPedidos.setBounds(745, 11, 140, 47);
		panelAcciones.add(btnMaestroPedidos);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 317, 225, 62);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblTipoPedido = new JLabel("Tipo Pedido");
		lblTipoPedido.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTipoPedido.setBounds(76, 0, 71, 14);
		panel.add(lblTipoPedido);
		
		JRadioButton rdbtnPuntoDeVenta = new JRadioButton("Punto de Venta");
		rdbtnPuntoDeVenta.setBounds(16, 17, 148, 23);
		panel.add(rdbtnPuntoDeVenta);
		
		JRadioButton rdbtnDomicilio = new JRadioButton("Domicilio");
		rdbtnDomicilio.setBounds(16, 39, 109, 23);
		panel.add(rdbtnDomicilio);
		
		lblIdCliente = new JLabel("Id Cliente");
		lblIdCliente.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblIdCliente.setBounds(77, 471, 108, 20);
		contentPane.add(lblIdCliente);
		
		lblNombreCliente = new JLabel("Nombre Cliente");
		lblNombreCliente.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNombreCliente.setBounds(206, 474, 390, 17);
		contentPane.add(lblNombreCliente);
		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
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
		//Capturamos el detalle pedido creado, validaremos si fue exitoso para agregarlo al contenedor y pantalla
		int idDetalle = pedCtrl.insertarDetallePedido(detPedido);
		detPedido.setIdDetallePedido(idDetalle);
		idDetallePedidoMaster = idDetalle;
		if(idDetalle > 0)
		{
			detallesPedido.add(detPedido);
			totalPedido = totalPedido + detPedido.getValorTotal();
			txtValorPedido.setText(Double.toString(totalPedido));
			pintarDetallePedido();
			
		}
		if (preguntasProducto.size() == 0)
		{
			
			
		}
		else
		{
			
			VentEleccionForzada ElForzada = new VentEleccionForzada(preguntasProducto, idProducto);
			ElForzada.setVisible(true);
		}
	}
	
	public void pintarDetallePedido()
	{
		//Clareamos el jtable
		DefaultTableModel tb = (DefaultTableModel) tableDetallePedido.getModel();
		tb.setRowCount(0);
		//Recorremos el ArrayList de DetallePedido para agregarlo al Jtable
		DefaultTableModel modeloDetalle = new DefaultTableModel();
		tableDetallePedido.setModel(modeloDetalle);
		modeloDetalle.setColumnIdentifiers(new Object [] {"idDetalle", "Cantidad", "Descripción",  "Valor", "idDetalleMaster"});
		tableDetallePedido.getColumnModel().getColumn(0).setMaxWidth(0);
		tableDetallePedido.getColumnModel().getColumn(0).setMinWidth(0);
		tableDetallePedido.getColumnModel().getColumn(1).setMaxWidth(20);
		tableDetallePedido.getColumnModel().getColumn(1).setMinWidth(20);
		tableDetallePedido.getColumnModel().getColumn(3).setMaxWidth(50);
		tableDetallePedido.getColumnModel().getColumn(3).setMinWidth(50);
		tableDetallePedido.getColumnModel().getColumn(4).setMaxWidth(0);
		tableDetallePedido.getColumnModel().getColumn(4).setMinWidth(0);
		tableDetallePedido.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
		tableDetallePedido.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
		tableDetallePedido.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(20);
		tableDetallePedido.getTableHeader().getColumnModel().getColumn(1).setMinWidth(20);
		tableDetallePedido.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(50);
		tableDetallePedido.getTableHeader().getColumnModel().getColumn(3).setMinWidth(50);
		tableDetallePedido.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(0);
		tableDetallePedido.getTableHeader().getColumnModel().getColumn(4).setMinWidth(0);
		ParametrosProductoCtrl parProducto = new ParametrosProductoCtrl();
		for(int i = 0; i < detallesPedido.size();i++)
		{
			DetallePedido det = detallesPedido.get(i);
			Producto proDet = parProducto.obtenerProducto(det.getIdProducto());
			Object [] object = new Object[]{det.getIdDetallePedido(),det.getCantidad(),proDet.getDescripcion(),det.getValorTotal(), det.getIdDetallePedidoMaster()};
			modeloDetalle.addRow(object);
		}
	}
	
	public void crearEncabezadoPedido()
	{
		PedidoCtrl pedCtrl = new PedidoCtrl();
		java.util.Date fechaActual = new java.util.Date();
		Calendar hoy = Calendar.getInstance();
		String fechaPedido = Integer.toString(hoy.get(Calendar.DAY_OF_MONTH)) + "/" + Integer.toString(hoy.get(Calendar.MONTH)) + "/" + Integer.toString(hoy.get(Calendar.YEAR));
		idPedido = pedCtrl.InsertarEncabezadoPedido(idTienda, idCliente, fechaPedido, usuario);
	}
}
