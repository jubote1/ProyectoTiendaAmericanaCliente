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
import javax.swing.JScrollBar;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;

import capaControlador.MenuCtrl;
import capaControlador.ParametrosProductoCtrl;
import capaModelo.ConfiguracionMenu;
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

public class TomarPedidos extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable tableMenu;
	//Creamos una variable Arreglo Global que contendr� la Configuraci�n Men�
	private ConfiguracionMenu[][] confiMenu;
	// El arreglo con los botones del men� activo se manejar� como variable global
	private Object[][] botones;
	//Par�metros que se tendr�n en Tomador Pedidos para la selecci�n de clientes
	public static int idCliente = 0;
	public static String nombreCliente = "";
	JLabel lblIdCliente;
	JLabel lblNombreCliente;
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
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(103, 236, 112, 20);
		panelPedido.add(textPane);
		
		JLabel lblValorTotal = new JLabel("Valor Total ");
		lblValorTotal.setBounds(10, 236, 83, 14);
		panelPedido.add(lblValorTotal);
		
		JButton btnAnularItem = new JButton("Anular Item");
		btnAnularItem.setBounds(51, 265, 112, 36);
		panelPedido.add(btnAnularItem);
		
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
				//Se deber�a crear una nueva ventana para la asignaci�n y creaci�n de clientes
				//Al momento de instanciar VentCliente se le pasar� como par�metro el idCliente del pedido
				VentCliente cliente = new VentCliente(idCliente);
				cliente.setVisible(true);
			}
		});
		btnAsignarCliente.setBounds(263, 11, 116, 47);
		panelAcciones.add(btnAsignarCliente);
		
		JButton btnNewButton_1 = new JButton("Anular Pedido");
		btnNewButton_1.setBounds(389, 11, 106, 47);
		panelAcciones.add(btnNewButton_1);
		
		JButton btnDescuento = new JButton("Descuento");
		
		btnDescuento.setBounds(505, 11, 97, 47);
		panelAcciones.add(btnDescuento);
		
		JButton btnFinalizarPedido = new JButton("Finalizar Pedido");
		btnFinalizarPedido.setBounds(612, 11, 89, 47);
		panelAcciones.add(btnFinalizarPedido);
		
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
					ParametrosProductoCtrl parPro = new ParametrosProductoCtrl();
					Producto prodBoton = parPro.obtenerProducto(objConfMenu.getIdProducto());
					btn = new JButton(prodBoton.getIdProducto() + " - " + prodBoton.getDescripcion());
				}
				if (noMenu)
				{
					botones[i][j] = btn;
				}else
				{
					botones[i][j] = btn;
				}
				numBoton++;
			}
		}
		//finalizamos de llenar el arreglo
		MyTableModel model = new MyTableModel(new String[] {
				"col1", "col2", "col3", "col4", "col5", "col6"
			},botones);
		tableMenu.setModel(model);
	}
		
}
