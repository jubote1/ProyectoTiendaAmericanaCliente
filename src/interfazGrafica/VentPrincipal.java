package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import capaModelo.AccesosPorMenu;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.border.LineBorder;

import capaControlador.AutenticacionCtrl;
import capaControlador.ParametrosCtrl;
import capaModelo.Parametro;

public class VentPrincipal extends JFrame {

	private JPanel PanelPrincipal = new JPanel();
	private JPanel PanelFachada = new JPanel();
	private JPanel PanelPedidos = new JPanel();
	private JPanel PanelInventarios = new JPanel();
	private JPanel PanelClientes = new JPanel();
	private JPanel PanelProductos = new JPanel();
	private JPanel PanelSeguridad = new JPanel();
	ParametrosCtrl parCtrl = new ParametrosCtrl(PrincipalLogueo.habilitaAuditoria);
	AutenticacionCtrl autCtrl = new AutenticacionCtrl(PrincipalLogueo.habilitaAuditoria);
	private int menuPedidos;
	private int menuClientes;
	private int menuInventarios;
	private int menuSeguridad;
	private int menuProductos;
	CardLayout cl = new CardLayout();
	JFrame ventPrincipal;


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
		//Inicializamos las contantes de los menús en la tabla de parámetros del sistema
		inicializarConstantesMenus();
		this.setExtendedState(MAXIMIZED_BOTH);
		//PanelPrincipal.setBackground(Color.WHITE);
		PanelPrincipal.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		setContentPane(PanelPrincipal);
		ventPrincipal = this;
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

		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
		//Agregamos al panel al final - primero debemos agregar la Jtoolbar
		
		
		JButton btnSeguridadMenAgrupador = new JButton("Seguridad Men\u00FA Agrupador");
		btnSeguridadMenAgrupador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean tienePermiso = autCtrl.validarAccesoOpcion("SEG_003", Sesion.getAccesosOpcion());
				if (tienePermiso)
				{
					VentSegCRUDMenuAgrupador MenuAgrupa = new VentSegCRUDMenuAgrupador(ventPrincipal, true);
					MenuAgrupa.setVisible(true);
				}else
				{
					JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSeguridadMenAgrupador.setBounds(24, 124, 180, 48);
		PanelSeguridad.add(btnSeguridadMenAgrupador);
		
		JButton btnSegEmpleado = new JButton("Definici\u00F3n de Empleados");
		btnSegEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean tienePermiso = autCtrl.validarAccesoOpcion("SEG_005", Sesion.getAccesosOpcion());
				if (tienePermiso)
				{
					VentSegEmpleado emp = new VentSegEmpleado (ventPrincipal,true);
					emp.setVisible(true);
				}else
				{
					JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		PanelSeguridad.add(btnSegEmpleado);
		
		JButton btnSegTipoEmpleado = new JButton("Definici\u00F3n Tipo Empleado");
		btnSegTipoEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean tienePermiso = autCtrl.validarAccesoOpcion("SEG_008", Sesion.getAccesosOpcion());
				if (tienePermiso)
				{
					VentSegTipoEmpleado tipEmp = new VentSegTipoEmpleado(ventPrincipal, true);
					tipEmp.setVisible(true);
				}else
				{
					JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		PanelSeguridad.add(btnSegTipoEmpleado);
		
		JButton btnParametrosTienda = new JButton("Parametros de Tienda");
		btnParametrosTienda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean tienePermiso = autCtrl.validarAccesoOpcion("SEG_007", Sesion.getAccesosOpcion());
				if (tienePermiso)
				{
					VentSegTienda seguridadTienda = new VentSegTienda(null, true);
					seguridadTienda.setVisible(true);
				}else
				{
					JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JButton btnCambiarDeUsuario_3 = new JButton("CAMBIAR DE USUARIO");
		btnCambiarDeUsuario_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) e.getSource());
				VentPrincipalLogueRapido ventLogueoRapido = new VentPrincipalLogueRapido((JFrame)ventanaPadre, true);
				ventLogueoRapido.setVisible(true);
			}
		});
		btnCambiarDeUsuario_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		PanelSeguridad.add(btnCambiarDeUsuario_3);
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
				boolean tienePermiso = autCtrl.validarAccesoOpcion("PRO_003", Sesion.getAccesosOpcion());
				if (tienePermiso)
				{
					VentProCRUDPregunta EleccionForzada = new VentProCRUDPregunta();
					EleccionForzada.setVisible(true);
				}else
				{
					JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnEleccionForzada.setBounds(276, 124, 188, 48);
		PanelProductos.add(btnEleccionForzada);
		
		JButton btnImpuestos = new JButton("Impuestos");
		btnImpuestos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean tienePermiso = autCtrl.validarAccesoOpcion("PRO_001", Sesion.getAccesosOpcion());
				if (tienePermiso)
				{
					VentProCRUDImpuesto impuesto = new VentProCRUDImpuesto();
					impuesto.setVisible(true);
				}else
				{
					JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnImpuestos.setBounds(539, 124, 188, 48);
		PanelProductos.add(btnImpuestos);
		
		JButton btnCreacionClientes = new JButton("Creacion Clientes");
		btnCreacionClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean tienePermiso = autCtrl.validarAccesoOpcion("CLI_002", Sesion.getAccesosOpcion());
				if (tienePermiso)
				{
					VentCliCliente cliente  = new VentCliCliente(0, null, true);
					cliente.setVisible(true);
				}else
				{
					JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnCreacionClientes.setBounds(24, 197, 180, 48);
		PanelClientes.add(btnCreacionClientes);
		
		JButton btnItemsInventario = new JButton("Items Inventario");
		btnItemsInventario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean tienePermiso = autCtrl.validarAccesoOpcion("INV_001", Sesion.getAccesosOpcion());
				if (tienePermiso)
				{
					VentInvCRUDItemInventario itemInv = new VentInvCRUDItemInventario(null, true);
					itemInv.setVisible(true);
				}else
				{
					JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnItemsInventario.setBounds(275, 196, 189, 48);
		PanelInventarios.add(btnItemsInventario);
		
		JButton btnNewButton = new JButton("Administraci\u00F3n Inventarios");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean tienePermiso = autCtrl.validarAccesoOpcion("INV_004", Sesion.getAccesosOpcion());
				if (tienePermiso)
				{
					VentInvInventarios inv = new VentInvInventarios(null, true);
					inv.setVisible(true);
				}else
				{
					JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JButton btnCambiarDeUsuario_1 = new JButton("CAMBIAR DE USUARIO");
		btnCambiarDeUsuario_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) e.getSource());
				VentPrincipalLogueRapido ventLogueoRapido = new VentPrincipalLogueRapido((JFrame)ventanaPadre, true);
				ventLogueoRapido.setVisible(true);
			}
		});
		btnCambiarDeUsuario_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		PanelInventarios.add(btnCambiarDeUsuario_1);
		PanelInventarios.add(btnNewButton);
		
		JButton btnProductos = new JButton("Productos");
		btnProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean tienePermiso = autCtrl.validarAccesoOpcion("PRO_004", Sesion.getAccesosOpcion());
				if (tienePermiso)
				{
					VentProCRUDProducto producto = new VentProCRUDProducto(ventPrincipal, true);
					producto.setVisible(true);
				}else
				{
					JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnProductos.setBounds(539, 195, 188, 48);
		PanelProductos.add(btnProductos);
		
		JButton btnDefinicinEstadosDe = new JButton("Definici\u00F3n Estados de Productos");
		btnDefinicinEstadosDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean tienePermiso = autCtrl.validarAccesoOpcion("PRO_006", Sesion.getAccesosOpcion());
				if (tienePermiso)
				{
					VentProEstado ventEst = new VentProEstado(ventPrincipal, true);
					ventEst.setVisible(true);
				}else
				{
					JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JButton btnCambiarDeUsuario_4 = new JButton("CAMBIAR DE USUARIO");
		btnCambiarDeUsuario_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) e.getSource());
				VentPrincipalLogueRapido ventLogueoRapido = new VentPrincipalLogueRapido((JFrame)ventanaPadre, true);
				ventLogueoRapido.setVisible(true);
			}
		});
		btnCambiarDeUsuario_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		PanelProductos.add(btnCambiarDeUsuario_4);
		PanelProductos.add(btnDefinicinEstadosDe);
		
		JButton btnParmetrosDireccin = new JButton("Par\u00E1metros Direcci\u00F3n");
		btnParmetrosDireccin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean tienePermiso = autCtrl.validarAccesoOpcion("PRO_002", Sesion.getAccesosOpcion());
				if (tienePermiso)
				{
					VentProCRUDParametrosDireccion parDir = new VentProCRUDParametrosDireccion(ventPrincipal, true);
					parDir.setVisible(true);
				}else
				{
					JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		});
		
		JButton btnCambiarDeUsuario_2 = new JButton("CAMBIAR DE USUARIO");
		btnCambiarDeUsuario_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) e.getSource());
				VentPrincipalLogueRapido ventLogueoRapido = new VentPrincipalLogueRapido((JFrame)ventanaPadre, true);
				ventLogueoRapido.setVisible(true);
			}
		});
		btnCambiarDeUsuario_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		PanelClientes.add(btnCambiarDeUsuario_2);
		btnParmetrosDireccin.setBounds(24, 269, 180, 48);
		PanelClientes.add(btnParmetrosDireccin);
		
		JButton btnConfiguracionMenu = new JButton("Configuracion Menu");
		btnConfiguracionMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean tienePermiso = autCtrl.validarAccesoOpcion("SEG_001", Sesion.getAccesosOpcion());
				if (tienePermiso)
				{
					VentSegConfMenu confMenu = new VentSegConfMenu(ventPrincipal, true);
					confMenu.setVisible(true);
				}else
				{
					JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnConfiguracionMenu.setBounds(276, 269, 188, 48);
		PanelPedidos.add(btnConfiguracionMenu);
		
		JButton btnFinalizarDa = new JButton("Finalizar D\u00EDa");
		btnFinalizarDa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean tienePermiso = autCtrl.validarAccesoOpcion("PED_007", Sesion.getAccesosOpcion());
				if (tienePermiso)
				{
					VentPedFinalizarDia finDia = new VentPedFinalizarDia(ventPrincipal, true);
					finDia.setVisible(true);
				}else
				{
					JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JButton btnTomaPedidos = new JButton("Toma Pedidos");
		btnTomaPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean tienePermiso = autCtrl.validarAccesoOpcion("PED_013", Sesion.getAccesosOpcion());
				if (tienePermiso)
				{
					VentPedTomarPedidos tomaPedido = new VentPedTomarPedidos();
					tomaPedido.setVisible(true);
					dispose();
				}else
				{
					JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnTomaPedidos.setBounds(539, 269, 188, 48);
		PanelPedidos.add(btnTomaPedidos);
		PanelPedidos.add(btnFinalizarDa);
		
		JButton btnMaestroPedidos = new JButton("Maestro Pedidos");
		btnMaestroPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean tienePermiso = autCtrl.validarAccesoOpcion("PED_014", Sesion.getAccesosOpcion());
				if (tienePermiso)
				{
					VentPedTransaccional pedTrans = new VentPedTransaccional();
					pedTrans.setVisible(true);
					dispose();
				}else
				{
					JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JButton btnParmetrosGenerales = new JButton("Par\u00E1metros Generales");
		btnParmetrosGenerales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean tienePermiso = autCtrl.validarAccesoOpcion("PED_001", Sesion.getAccesosOpcion());
				if (tienePermiso)
				{
					VentPedAdmParametros ventParametros = new VentPedAdmParametros(ventPrincipal, true);
					ventParametros.setVisible(true);
				}else
				{
					JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JButton btnControlarPorciones = new JButton("Controlar Porciones");
		btnControlarPorciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				boolean tienePermiso = autCtrl.validarAccesoOpcion("PED_016", Sesion.getAccesosOpcion());
				if (tienePermiso)
				{
					VentPedContPorciones ventPorciones = new VentPedContPorciones(ventPrincipal, true);
					ventPorciones.setVisible(true);
				}else
				{
					JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		PanelPedidos.add(btnControlarPorciones);
		PanelPedidos.add(btnParmetrosGenerales);
		PanelPedidos.add(btnMaestroPedidos);
		
		JButton btnComandaDePedidos = new JButton("Comanda de Pedidos");
		btnComandaDePedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean tienePermiso = autCtrl.validarAccesoOpcion("PED_003", Sesion.getAccesosOpcion());
				if (tienePermiso)
				{
					VentPedComandaPedidos ventComPed = new VentPedComandaPedidos();
					ventComPed.setVisible(true);
					dispose();
				}else
				{
					JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		JButton btnCambiarDeUsuario = new JButton("CAMBIAR DE USUARIO");
		btnCambiarDeUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) arg0.getSource());
				VentPrincipalLogueRapido ventLogueoRapido = new VentPrincipalLogueRapido((JFrame)ventanaPadre, true);
				ventLogueoRapido.setVisible(true);
			}
		});
		btnCambiarDeUsuario.setFont(new Font("Tahoma", Font.BOLD, 11));
		PanelPedidos.add(btnCambiarDeUsuario);
		
		JButton btnAdministracinTiempos = new JButton("Administraci\u00F3n Tiempos");
		btnAdministracinTiempos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentPedFijarTiempoPedidos ventTiempo = new VentPedFijarTiempoPedidos(null, true);
				ventTiempo.setVisible(true);
			}
		});
		PanelPedidos.add(btnAdministracinTiempos);
		PanelPedidos.add(btnComandaDePedidos);
		
		JToolBar toolBarModulos = new JToolBar();
		toolBarModulos.setFloatable(false);
		toolBarModulos.setBounds(10, 11, 773, 73);
		PanelPrincipal.add(toolBarModulos);
		
		JButton btnPedidos = new JButton("PEDIDOS");
		btnPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean acceso = autCtrl.validarAccesoMenu(menuPedidos, Sesion.getAccesosMenus());
				if(acceso)
				{
					cl.show(PanelFachada, "1");
				}
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
				boolean acceso = autCtrl.validarAccesoMenu(menuInventarios, Sesion.getAccesosMenus());
				if(acceso)
				{
					cl.show(PanelFachada, "2");
				}
				
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
				boolean acceso = autCtrl.validarAccesoMenu(menuClientes, Sesion.getAccesosMenus());
				if(acceso)
				{
					cl.show(PanelFachada, "3");
				}
				
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
				
				boolean acceso = autCtrl.validarAccesoMenu(menuSeguridad, Sesion.getAccesosMenus());
				if(acceso)
				{
					cl.show(PanelFachada, "4");
				}
				
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
				boolean acceso = autCtrl.validarAccesoMenu(menuProductos, Sesion.getAccesosMenus());
				if(acceso)
				{
					cl.show(PanelFachada, "5");
				}
				
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
	
	public void inicializarConstantesMenus()
	{
		Parametro parametro = parCtrl.obtenerParametro("MENUPEDIDOS");
		int valNum = 0;
		try
		{
			valNum = parametro.getValorNumerico();
		}catch(Exception e)
		{
			System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE MENUPEDIDOS");
			valNum = 0;
		}
		menuPedidos = valNum;
		parametro = parCtrl.obtenerParametro("MENUCLIENTES");
		try
		{
			valNum = parametro.getValorNumerico();
		}catch(Exception e)
		{
			System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE MENUCLIENTES");
			valNum = 0;
		}
		menuClientes = valNum;
		parametro = parCtrl.obtenerParametro("MENUINVENTARIOS");
		try
		{
			valNum = parametro.getValorNumerico();
		}catch(Exception e)
		{
			System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE MENUINVENTARIOS");
			valNum = 0;
		}
		menuInventarios = valNum;
		parametro = parCtrl.obtenerParametro("MENUSEGURIDAD");
		try
		{
			valNum = parametro.getValorNumerico();
		}catch(Exception e)
		{
			System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE MENUSEGURIDAD");
			valNum = 0;
		}
		menuSeguridad = valNum;
		parametro = parCtrl.obtenerParametro("MENUPRODUCTOS");
		try
		{
			valNum = parametro.getValorNumerico();
		}catch(Exception e)
		{
			System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE MENUPRODUCTOS");
			valNum = 0;
		}
		menuProductos = valNum;
		
	}
	

}
