package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.border.LineBorder;

public class VentPrincipal extends JFrame {

	private JPanel PanelPrincipal = new JPanel();
	private JPanel PanelFachada = new JPanel();
	private JPanel PanelPedidos = new JPanel();
	private JPanel PanelInventarios = new JPanel();
	private JPanel PanelClientes = new JPanel();
	private JPanel PanelProductos = new JPanel();
	private JPanel PanelSeguridad = new JPanel();
	CardLayout cl = new CardLayout();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentPrincipal frame = new VentPrincipal();
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
	public VentPrincipal() {
		setTitle("MEN\u00DA PRINCIPAL");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 444);
		this.setExtendedState(MAXIMIZED_BOTH);
		//PanelPrincipal.setBackground(Color.WHITE);
		PanelPrincipal.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setContentPane(PanelPrincipal);
		PanelPrincipal.setLayout(null);
		PanelFachada.setBounds(0, 95, 774, 300);
		PanelFachada.setLayout(cl);
		PanelFachada.add(PanelPedidos,"1");
		PanelPedidos.setLayout(new GridLayout(0, 2, 100, 0));
		PanelFachada.add(PanelInventarios,"2");
		PanelInventarios.setLayout(new GridLayout(0, 2, 100, 0));
		PanelFachada.add(PanelClientes,"3");
		PanelClientes.setLayout(new GridLayout(0, 2, 100, 0));
		PanelFachada.add(PanelSeguridad,"4");
		PanelSeguridad.setLayout(new GridLayout(0, 2, 100, 0));
		PanelFachada.add(PanelProductos,"5");
		PanelProductos.setLayout(new GridLayout(0, 2, 100, 0));
		cl.show(PanelFachada, "1");
		//Agregamos al panel al final - primero debemos agregar la Jtoolbar
		
		
		JButton btnSeguridadMenAgrupador = new JButton("Seguridad Men\u00FA Agrupador");
		btnSeguridadMenAgrupador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentSegCRUDMenuAgrupador MenuAgrupa = new VentSegCRUDMenuAgrupador();
				MenuAgrupa.setVisible(true);
			}
		});
		btnSeguridadMenAgrupador.setBounds(24, 124, 180, 48);
		PanelSeguridad.add(btnSeguridadMenAgrupador);
		
		JButton btnSegEmpleado = new JButton("Definici\u00F3n de Empleados");
		btnSegEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentSegEmpleado emp = new VentSegEmpleado ();
				emp.setVisible(true);
			}
		});
		PanelSeguridad.add(btnSegEmpleado);
		
		JButton btnSegTipoEmpleado = new JButton("Definici\u00F3n Tipo Empleado");
		btnSegTipoEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentSegTipoEmpleado tipEmp = new VentSegTipoEmpleado();
				tipEmp.setVisible(true);
			}
		});
		PanelSeguridad.add(btnSegTipoEmpleado);
		
		JButton btnParametrosTienda = new JButton("Parametros de Tienda");
		btnParametrosTienda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentSegTienda seguridadTienda = new VentSegTienda(null, true);
				seguridadTienda.setVisible(true);
			}
		});
		PanelSeguridad.add(btnParametrosTienda);
		ImageIcon icono = new ImageIcon("iconos\\seguridad.jpg");
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(476, 67, 0, 0);
		ImageIcon icono2 = new ImageIcon("iconos\\LogoPizzaAmericana.png");
		lblLogo.setIcon(icono2);
		PanelPrincipal.add(lblLogo);
		
		JButton btnEleccionForzada = new JButton("Eleccion Forzada");
		btnEleccionForzada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentCRUDProPregunta EleccionForzada = new VentCRUDProPregunta();
				EleccionForzada.setVisible(true);
			}
		});
		btnEleccionForzada.setBounds(276, 124, 188, 48);
		PanelProductos.add(btnEleccionForzada);
		
		JButton btnImpuestos = new JButton("Impuestos");
		btnImpuestos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentCRUDImpuesto impuesto = new VentCRUDImpuesto();
				impuesto.setVisible(true);
			}
		});
		btnImpuestos.setBounds(539, 124, 188, 48);
		PanelProductos.add(btnImpuestos);
		
		JButton btnCreacionClientes = new JButton("Creacion Clientes");
		btnCreacionClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentCliCliente cliente  = new VentCliCliente(0, null, true);
				cliente.setVisible(true);
			}
		});
		btnCreacionClientes.setBounds(24, 197, 180, 48);
		PanelClientes.add(btnCreacionClientes);
		
		JButton btnItemsInventario = new JButton("Items Inventario");
		btnItemsInventario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentInvCRUDItemInventario itemInv = new VentInvCRUDItemInventario(null, true);
				itemInv.setVisible(true);
			}
		});
		btnItemsInventario.setBounds(275, 196, 189, 48);
		PanelInventarios.add(btnItemsInventario);
		
		JButton btnNewButton = new JButton("Administraci\u00F3n Inventarios");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentInvInventarios inv = new VentInvInventarios(null, true);
				inv.setVisible(true);
			}
		});
		PanelInventarios.add(btnNewButton);
		
		JButton btnProductos = new JButton("Productos");
		btnProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentProCRUDProducto producto = new VentProCRUDProducto();
				producto.setVisible(true);
			}
		});
		btnProductos.setBounds(539, 195, 188, 48);
		PanelProductos.add(btnProductos);
		
		JButton btnDefinicinEstadosDe = new JButton("Definici\u00F3n Estados de Productos");
		btnDefinicinEstadosDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentProEstado ventEst = new VentProEstado();
				ventEst.setVisible(true);
			}
		});
		PanelProductos.add(btnDefinicinEstadosDe);
		
		JButton btnParmetrosDireccin = new JButton("Par\u00E1metros Direcci\u00F3n");
		btnParmetrosDireccin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentCRUDParametrosDireccion parDir = new VentCRUDParametrosDireccion();
				parDir.setVisible(true);
			}
		});
		btnParmetrosDireccin.setBounds(24, 269, 180, 48);
		PanelClientes.add(btnParmetrosDireccin);
		
		JButton btnConfiguracionMenu = new JButton("Configuracion Menu");
		btnConfiguracionMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentSegConfMenu confMenu = new VentSegConfMenu();
				confMenu.setVisible(true);
			}
		});
		btnConfiguracionMenu.setBounds(276, 269, 188, 48);
		PanelPedidos.add(btnConfiguracionMenu);
		
		JButton btnFinalizarDa = new JButton("Finalizar D\u00EDa");
		btnFinalizarDa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentPedFinalizarDia finDia = new VentPedFinalizarDia(null, true);
				finDia.setVisible(true);
			}
		});
		
		JButton btnTomaPedidos = new JButton("Toma Pedidos");
		btnTomaPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentPedTomarPedidos tomaPedido = new VentPedTomarPedidos();
				tomaPedido.setVisible(true);
				dispose();
			}
		});
		btnTomaPedidos.setBounds(539, 269, 188, 48);
		PanelPedidos.add(btnTomaPedidos);
		PanelPedidos.add(btnFinalizarDa);
		
		JButton btnMaestroPedidos = new JButton("Maestro Pedidos");
		btnMaestroPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentPedTransaccional pedTrans = new VentPedTransaccional();
				pedTrans.setVisible(true);
				dispose();
			}
		});
		
		JButton btnParmetrosGenerales = new JButton("Par\u00E1metros Generales");
		btnParmetrosGenerales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentPedAdmParametros ventParametros = new VentPedAdmParametros(null, true);
				ventParametros.setVisible(true);
			}
		});
		PanelPedidos.add(btnParmetrosGenerales);
		PanelPedidos.add(btnMaestroPedidos);
		
		JButton btnComandaDePedidos = new JButton("Comanda de Pedidos");
		btnComandaDePedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentPedComandaPedidos ventComPed = new VentPedComandaPedidos();
				ventComPed.setVisible(true);
				dispose();
			}
		});
		PanelPedidos.add(btnComandaDePedidos);
		
		JToolBar toolBarModulos = new JToolBar();
		toolBarModulos.setFloatable(false);
		toolBarModulos.setBounds(10, 11, 773, 73);
		PanelPrincipal.add(toolBarModulos);
		
		JButton btnPedidos = new JButton("PEDIDOS");
		btnPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cl.show(PanelFachada, "1");
			}
		});
		btnPedidos.setBackground(Color.WHITE);
		btnPedidos.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnPedidos.setHorizontalAlignment(SwingConstants.TRAILING);
		btnPedidos.setIcon(new ImageIcon(VentPrincipal.class.getResource("/icons/pedido.jpg")));
		toolBarModulos.add(btnPedidos);
		
		JButton btnInventarios = new JButton("INVENTARIOS");
		btnInventarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cl.show(PanelFachada, "2");
			}
		});
		btnInventarios.setBackground(Color.WHITE);
		btnInventarios.setHorizontalAlignment(SwingConstants.TRAILING);
		btnInventarios.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnInventarios.setIcon(new ImageIcon(VentPrincipal.class.getResource("/icons/inventario.jpg")));
		toolBarModulos.add(btnInventarios);
		
		JButton btnClientes = new JButton("CLIENTES");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(PanelFachada, "3");
			}
		});
		btnClientes.setBackground(Color.WHITE);
		btnClientes.setHorizontalAlignment(SwingConstants.TRAILING);
		btnClientes.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnClientes.setIcon(new ImageIcon(VentPrincipal.class.getResource("/icons/cliente.jpg")));
		toolBarModulos.add(btnClientes);
		
		JButton btnSeguridad = new JButton("SEGURIDAD");
		btnSeguridad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cl.show(PanelFachada, "4");
			}
		});
		btnSeguridad.setBackground(Color.WHITE);
		btnSeguridad.setHorizontalAlignment(SwingConstants.TRAILING);
		btnSeguridad.setIcon(new ImageIcon(VentPrincipal.class.getResource("/icons/seguridad.jpg")));
		btnSeguridad.setFont(new Font("Tahoma", Font.BOLD, 14));
		toolBarModulos.add(btnSeguridad);
		
		JButton btnProductos_1 = new JButton("PRODUCTOS");
		btnProductos_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(PanelFachada, "5");
			}
		});
		btnProductos_1.setHorizontalAlignment(SwingConstants.TRAILING);
		btnProductos_1.setBackground(Color.WHITE);
		btnProductos_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnProductos_1.setIcon(new ImageIcon(VentPrincipal.class.getResource("/icons/producto.jpg")));
		toolBarModulos.add(btnProductos_1);
		//
		PanelPrincipal.add(PanelFachada);
	}
}
