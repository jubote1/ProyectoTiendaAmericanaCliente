package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import capaControlador.AutenticacionCtrl;
import capaControlador.ParametrosCtrl;
import capaControlador.PedidoCtrl;
import capaControlador.ReportesCtrl;

import javax.swing.JTabbedPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Window;
import javax.swing.JLabel;

public class VentPrincipalModificada extends JFrame {

	private JPanel contentPane;
	ParametrosCtrl parCtrl = new ParametrosCtrl(PrincipalLogueo.habilitaAuditoria);
	AutenticacionCtrl autCtrl = new AutenticacionCtrl(PrincipalLogueo.habilitaAuditoria);
	PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	JFrame ventPrincipal;
	JLabel lblInformacionUsuario;
	VentPrincipalModificada framePrincipal;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentPrincipalModificada frame = new VentPrincipalModificada();
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
	public VentPrincipalModificada() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(MAXIMIZED_BOTH);
		setBounds(100, 100, 1024, 770);
		contentPane = new JPanel();
		ventPrincipal = this;
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		framePrincipal = this;
		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
		JTabbedPane tabbedPaneModulos = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneModulos.setBounds(10, 29, 1004, 663);
		contentPane.add(tabbedPaneModulos);
		tabbedPaneModulos.setUI(new ShapeTabbedPaneUI());
		
		JPanel tuspedidos = new JPanel();
		tuspedidos.setBackground(Color.WHITE);
		tuspedidos.setToolTipText("");
		tabbedPaneModulos.addTab("Tus Pedidos", null, tuspedidos, null);
		tuspedidos.setLayout(null);
		
		JButton btnUnPedidomas = new JButton("<html><p>PEDIDOS</p></html>");
		btnUnPedidomas.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUnPedidomas.setIcon(new ImageIcon(VentPrincipalModificada.class.getResource("/icons/Porcion.jpg")));
		btnUnPedidomas.setSelectedIcon(new ImageIcon(VentPrincipalModificada.class.getResource("/icons/Botonpedido.jpg")));
		btnUnPedidomas.addActionListener(new ActionListener() {
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
		btnUnPedidomas.setBounds(718, 111, 254, 311);
		tuspedidos.add(btnUnPedidomas);
		
		JButton btnTransacciones = new JButton("<html><center>Todas las </br>transacciones</center></html>");
		btnTransacciones.setIcon(new ImageIcon(VentPrincipalModificada.class.getResource("/icons/Transacciones.jpg")));
		btnTransacciones.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnTransacciones.setHorizontalAlignment(SwingConstants.LEADING);
		btnTransacciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
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
		btnTransacciones.setBounds(45, 111, 161, 121);
		tuspedidos.add(btnTransacciones);
		
		JButton btnControladorPorciones = new JButton("<html><center>Controlador de Porciones</center></html>");
		btnControladorPorciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		btnControladorPorciones.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnControladorPorciones.setIcon(new ImageIcon(VentPrincipalModificada.class.getResource("/icons/pedido.jpg")));
		btnControladorPorciones.setBounds(45, 301, 161, 121);
		tuspedidos.add(btnControladorPorciones);
		
		JButton btnTiempo = new JButton("<html><center>Tiempo y </br>suspensi\u00F3n</center></html>");
		btnTiempo.setIcon(new ImageIcon(VentPrincipalModificada.class.getResource("/icons/Tiempo.jpg")));
		btnTiempo.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnTiempo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentPedFijarTiempoPedidos ventTiempo = new VentPedFijarTiempoPedidos(null, true);
				ventTiempo.setVisible(true);
			}
		});
		btnTiempo.setBounds(244, 111, 161, 121);
		tuspedidos.add(btnTiempo);
		
		JButton btnTusDomicilios = new JButton("Tus Domicilios");
		btnTusDomicilios.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnTusDomicilios.setIcon(new ImageIcon(VentPrincipalModificada.class.getResource("/icons/domicilio.jpg")));
		btnTusDomicilios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		btnTusDomicilios.setBounds(244, 301, 346, 121);
		tuspedidos.add(btnTusDomicilios);
		
		JButton btnMesas = new JButton("<html><center>Mesas</center></html>");
		btnMesas.setEnabled(false);
		btnMesas.setBounds(429, 111, 161, 121);
		tuspedidos.add(btnMesas);
		
		JButton btnCambiarUsuario = new JButton("Cambiar Usuario");
		btnCambiarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) e.getSource());
				VentPrincipalLogueRapido ventLogueoRapido = new VentPrincipalLogueRapido((JFrame)ventanaPadre, true);
				ventLogueoRapido.setVisible(true);
			}
		});
		btnCambiarUsuario.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCambiarUsuario.setBounds(10, 532, 166, 42);
		tuspedidos.add(btnCambiarUsuario);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSalir.setBounds(811, 532, 166, 42);
		tuspedidos.add(btnSalir);
		
		JButton btnCocina = new JButton("Cocina");
		btnCocina.setEnabled(false);
		btnCocina.setIcon(new ImageIcon(VentPrincipalModificada.class.getResource("/icons/cocina.jpg")));
		btnCocina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean tienePermiso = autCtrl.validarAccesoOpcion("PED_017", Sesion.getAccesosOpcion());
				if (tienePermiso)
				{
					VentPedCocina ventPedCocina = new VentPedCocina();
					ventPedCocina.setVisible(true);
					dispose();
				}else
				{
					JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnCocina.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCocina.setBounds(244, 446, 346, 121);
		tuspedidos.add(btnCocina);
		
		JPanel ventas = new JPanel();
		tabbedPaneModulos.addTab("Ventas", null, ventas, null);
		ventas.setLayout(null);
		
		JButton btnFinalizarDia = new JButton("<html><center><p>Ventas Actuales</p> </br><p>y Finalizar D\u00EDa</p></center></html>");
		btnFinalizarDia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentPedFinalizarDia finDia = new VentPedFinalizarDia(ventPrincipal, true);
				finDia.setVisible(true);
			}
		});
		btnFinalizarDia.setIcon(new ImageIcon(VentPrincipalModificada.class.getResource("/icons/ventas.jpg")));
		btnFinalizarDia.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnFinalizarDia.setBounds(258, 116, 360, 238);
		ventas.add(btnFinalizarDia);
		
		JButton button = new JButton("Cambiar Usuario");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) e.getSource());
				VentPrincipalLogueRapido ventLogueoRapido = new VentPrincipalLogueRapido((JFrame)ventanaPadre, true);
				ventLogueoRapido.setVisible(true);
			}
		});
		button.setFont(new Font("Tahoma", Font.BOLD, 12));
		button.setBounds(10, 532, 166, 42);
		ventas.add(button);
		
		JButton button_3 = new JButton("Salir");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		button_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_3.setBounds(823, 532, 166, 42);
		ventas.add(button_3);
		
		JPanel inventarios = new JPanel();
		tabbedPaneModulos.addTab("Inventarios", null, inventarios, null);
		inventarios.setLayout(null);
		
		JButton btnAdministrarInventarios = new JButton("<html><p>Administrar</p> <p>Inventarios</p></html>");
		btnAdministrarInventarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentInvInventarios inv = new VentInvInventarios(null, true);
				inv.setVisible(true);
			}
		});
		btnAdministrarInventarios.setIcon(new ImageIcon(VentPrincipalModificada.class.getResource("/icons/inventario.jpg")));
		btnAdministrarInventarios.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAdministrarInventarios.setBounds(71, 64, 305, 159);
		inventarios.add(btnAdministrarInventarios);
		
		JButton btnadministracinitemsInventario = new JButton("<html><p>Administraci\u00F3n</p> <p>Items Inventario</p></html>");
		btnadministracinitemsInventario.addActionListener(new ActionListener() {
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
		btnadministracinitemsInventario.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnadministracinitemsInventario.setBounds(421, 64, 305, 159);
		inventarios.add(btnadministracinitemsInventario);
		
		JButton btnReporteUsoInv = new JButton("<html><p>Reporte Uso de</p> <p>Inventarios</p></html>");
		btnReporteUsoInv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) e.getSource());
				//Acción para la generación inventario consumido
				//dispose();
				ReportesCtrl repCtrl = new ReportesCtrl(PrincipalLogueo.habilitaAuditoria);
				repCtrl.generarReporteInventarioCon(ventanaPadre);
			}
		});
		btnReporteUsoInv.setIcon(new ImageIcon(VentPrincipalModificada.class.getResource("/icons/reporte.jpg")));
		btnReporteUsoInv.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnReporteUsoInv.setBounds(71, 273, 305, 89);
		inventarios.add(btnReporteUsoInv);
		
		JButton btnReporteInventarioActual = new JButton("<html><p>Reporte </p> <p>Inventario Actual</p></html>");
		btnReporteInventarioActual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) e.getSource());
				//Acción para la generación de reporte inventario actual
				//dispose();
				ReportesCtrl repCtrl = new ReportesCtrl(PrincipalLogueo.habilitaAuditoria);
				repCtrl.generarReporteInventarioAct(ventanaPadre);
			}
		});
		btnReporteInventarioActual.setIcon(new ImageIcon(VentPrincipalModificada.class.getResource("/icons/reporte.jpg")));
		btnReporteInventarioActual.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnReporteInventarioActual.setBounds(71, 373, 305, 89);
		inventarios.add(btnReporteInventarioActual);
		
		JButton button_1 = new JButton("Cambiar Usuario");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) e.getSource());
				VentPrincipalLogueRapido ventLogueoRapido = new VentPrincipalLogueRapido((JFrame)ventanaPadre, true);
				ventLogueoRapido.setVisible(true);
			}
		});
		button_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_1.setBounds(10, 532, 166, 42);
		inventarios.add(button_1);
		
		JButton btnSalir3 = new JButton("Salir");
		btnSalir3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnSalir3.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSalir3.setBounds(823, 532, 166, 42);
		inventarios.add(btnSalir3);
		
		JButton btnrevisinConsumoItems = new JButton("<html><p>Revisi\u00F3n Consumo Items Inventario</p></html>");
		btnrevisinConsumoItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				VentInvRevisionInventario revInventario = new  VentInvRevisionInventario(ventPrincipal, true);
				revInventario.setVisible(true);
			}
		});
		btnrevisinConsumoItems.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnrevisinConsumoItems.setBounds(421, 273, 305, 159);
		inventarios.add(btnrevisinConsumoItems);
		
		JPanel horarios = new JPanel();
		tabbedPaneModulos.addTab("Horarios y personal", null, horarios, null);
		horarios.setLayout(null);
		
		JButton btnSalir4 = new JButton("Salir");
		btnSalir4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnSalir4.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSalir4.setBounds(823, 525, 166, 42);
		horarios.add(btnSalir4);
		
		JButton btnEventoJornada = new JButton("Registrar Entrada/Salida Jornada");
		btnEventoJornada.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnEventoJornada.setIcon(new ImageIcon(VentPrincipalModificada.class.getResource("/icons/capturaHuellaminiOK.jpg")));
		btnEventoJornada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentSegRegEventoEmpleado ventRegEmpleado = new VentSegRegEventoEmpleado(null, true);
				ventRegEmpleado.setVisible(true);
			}
		});
		btnEventoJornada.setBounds(278, 47, 344, 78);
		horarios.add(btnEventoJornada);
		
		JButton btnCambiarClaveRapida = new JButton("Cambiar Clave R\u00E1pida");
		btnCambiarClaveRapida.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnCambiarClaveRapida.setIcon(new ImageIcon(VentPrincipalModificada.class.getResource("/icons/cambioClave.jpg")));
		btnCambiarClaveRapida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane opt = new JOptionPane("Para el cambio de clave se requiere primero el ingreso de la actual clave rápida.", JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}); // no buttons
			     final JDialog dlg = opt.createDialog("CAMBIO DE CLAVE RÁPIDA");
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
			     VentSegAutenticacionLogueoRapido  ventAut = new VentSegAutenticacionLogueoRapido(framePrincipal , true);
			     ventAut.setVisible(true);
			     //Validamos el resultado del ingreso de la clave corta
			     if(VentSegAutenticacionLogueoRapido.usuarioAutorizado.getIdUsuario()>0)
			     {
			    	 //En este punto llamaremos la nueva pantalla para el ingreso de la clave
			    	 VentSegAsignacionClaveRapida ventCambioClave = new VentSegAsignacionClaveRapida(framePrincipal, VentSegAutenticacionLogueoRapido.usuarioAutorizado,true);
			    	 ventCambioClave.setVisible(true);
			     }
			     else // Es porque el logueo no fue correcto
			     {
			    	 JOptionPane optNoAutorizado = new JOptionPane("La clave anterior es incorrecta", JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}); // no buttons
				     final JDialog dlgNoAutorizado = optNoAutorizado.createDialog("CLAVE ACTUAL NO CORRECTA");
				     new Thread(new Runnable()
				           {
				             public void run()
				             {
				               try
				               {
				                 Thread.sleep(2500);
				                 dlgNoAutorizado.dispose();

				               }
				               catch ( Throwable th )
				               {
				                 
				               }
				             }
				           }).start();
				     dlgNoAutorizado.setVisible(true);
				     return;
			     }
			}
		});
		btnCambiarClaveRapida.setBounds(278, 294, 344, 91);
		horarios.add(btnCambiarClaveRapida);
		
		JButton btnRegistrarEntradaTemporal = new JButton("Registrar Entrada Emp Temporal");
		btnRegistrarEntradaTemporal.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnRegistrarEntradaTemporal.setIcon(new ImageIcon(VentPrincipalModificada.class.getResource("/icons/registrarTemporal.jpg")));
		btnRegistrarEntradaTemporal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				VentPedEmpleadoTemporal ventEmpTemporal = new VentPedEmpleadoTemporal(ventPrincipal, true);
				ventEmpTemporal.setVisible(true);
			}
		});
		btnRegistrarEntradaTemporal.setBounds(28, 160, 416, 99);
		horarios.add(btnRegistrarEntradaTemporal);
		
		JPanel administracion = new JPanel();
		tabbedPaneModulos.addTab("Administrador", null, administracion, null);
		administracion.setLayout(null);
		
		JPanel panelPedidos = new JPanel();
		panelPedidos.setBounds(37, 36, 359, 154);
		administracion.add(panelPedidos);
		panelPedidos.setLayout(null);
		
		JButton btnConfMenu = new JButton("Configuraci\u00F3n Men\u00FA");
		btnConfMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
		btnConfMenu.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnConfMenu.setBounds(10, 45, 166, 48);
		panelPedidos.add(btnConfMenu);
		
		JButton btnParametrosGenerales = new JButton("Par\u00E1metros Generales");
		btnParametrosGenerales.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnParametrosGenerales.setBounds(186, 45, 163, 48);
		panelPedidos.add(btnParametrosGenerales);
		
		JLabel lblPedidos = new JLabel("PEDIDOS");
		lblPedidos.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblPedidos.setBounds(10, 0, 128, 23);
		panelPedidos.add(lblPedidos);
		
		JPanel panelInventarios = new JPanel();
		panelInventarios.setBounds(37, 225, 359, 95);
		administracion.add(panelInventarios);
		panelInventarios.setLayout(null);
		
		JLabel lblInventarios = new JLabel("INVENTARIOS");
		lblInventarios.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblInventarios.setBounds(10, 0, 198, 23);
		panelInventarios.add(lblInventarios);
		
		JButton btnAdministrarItemsInventario = new JButton("Administrar Items Inventario");
		btnAdministrarItemsInventario.addActionListener(new ActionListener() {
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
		btnAdministrarItemsInventario.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAdministrarItemsInventario.setBounds(10, 36, 231, 48);
		panelInventarios.add(btnAdministrarItemsInventario);
		
		JPanel panelClientes = new JPanel();
		panelClientes.setLayout(null);
		panelClientes.setBounds(453, 36, 359, 154);
		administracion.add(panelClientes);
		
		JButton btnAdministrarClientes = new JButton("Administrar Clientes");
		btnAdministrarClientes.addActionListener(new ActionListener() {
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
		btnAdministrarClientes.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAdministrarClientes.setBounds(10, 45, 166, 48);
		panelClientes.add(btnAdministrarClientes);
		
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
		btnParmetrosDireccin.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnParmetrosDireccin.setBounds(186, 45, 163, 48);
		panelClientes.add(btnParmetrosDireccin);
		
		JLabel lblClientes = new JLabel("CLIENTES");
		lblClientes.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblClientes.setBounds(10, 0, 128, 23);
		panelClientes.add(lblClientes);
		
		JPanel panelSeguridad = new JPanel();
		panelSeguridad.setLayout(null);
		panelSeguridad.setBounds(37, 343, 359, 231);
		administracion.add(panelSeguridad);
		
		JButton btnDefMenAgrupador = new JButton("Def Men\u00FA Agrupador");
		btnDefMenAgrupador.addActionListener(new ActionListener() {
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
		btnDefMenAgrupador.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDefMenAgrupador.setBounds(10, 45, 166, 48);
		panelSeguridad.add(btnDefMenAgrupador);
		
		JButton btnDefTipoEmpleado = new JButton("Def Tipo Empleado");
		btnDefTipoEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		btnDefTipoEmpleado.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDefTipoEmpleado.setBounds(186, 45, 163, 48);
		panelSeguridad.add(btnDefTipoEmpleado);
		
		JLabel lblSeguridad = new JLabel("SEGURIDAD");
		lblSeguridad.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblSeguridad.setBounds(10, 0, 166, 23);
		panelSeguridad.add(lblSeguridad);
		
		JButton btnDefDeEmpleados = new JButton("Def de Empleados");
		btnDefDeEmpleados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		btnDefDeEmpleados.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDefDeEmpleados.setBounds(10, 114, 166, 48);
		panelSeguridad.add(btnDefDeEmpleados);
		
		JButton btnParmetrosDeTienda = new JButton("Par\u00E1metros de Tienda");
		btnParmetrosDeTienda.addActionListener(new ActionListener() {
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
		btnParmetrosDeTienda.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnParmetrosDeTienda.setBounds(186, 114, 163, 48);
		panelSeguridad.add(btnParmetrosDeTienda);
		
		JButton button_2 = new JButton("Cambiar Usuario");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) e.getSource());
				VentPrincipalLogueRapido ventLogueoRapido = new VentPrincipalLogueRapido((JFrame)ventanaPadre, true);
				ventLogueoRapido.setVisible(true);
			}
		});
		button_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		button_2.setBounds(-27, 189, 166, 42);
		panelSeguridad.add(button_2);
		
		JPanel panelProductos = new JPanel();
		panelProductos.setLayout(null);
		panelProductos.setBounds(453, 343, 359, 231);
		administracion.add(panelProductos);
		
		JButton btnDefinicionDeProductos = new JButton("Definicion de Productos");
		btnDefinicionDeProductos.addActionListener(new ActionListener() {
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
		btnDefinicionDeProductos.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDefinicionDeProductos.setBounds(10, 45, 166, 48);
		panelProductos.add(btnDefinicionDeProductos);
		
		JButton btnDefDeEleccin = new JButton("Def de Elecci\u00F3n Forzada");
		btnDefDeEleccin.addActionListener(new ActionListener() {
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
		btnDefDeEleccin.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDefDeEleccin.setBounds(186, 45, 163, 48);
		panelProductos.add(btnDefDeEleccin);
		
		JLabel lblProductos = new JLabel("PRODUCTOS");
		lblProductos.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblProductos.setBounds(10, 0, 166, 23);
		panelProductos.add(lblProductos);
		
		JButton btnEleccinForzada = new JButton("<html><center>Definici\u00F3n de Estados de Productos</center></html>");
		btnEleccinForzada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		btnEleccinForzada.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEleccinForzada.setBounds(10, 114, 166, 48);
		panelProductos.add(btnEleccinForzada);
		
		JButton btnDefinicionDeImpuestos = new JButton("Definicion de Impuestos");
		btnDefinicionDeImpuestos.addActionListener(new ActionListener() {
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
		btnDefinicionDeImpuestos.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDefinicionDeImpuestos.setBounds(186, 114, 163, 48);
		panelProductos.add(btnDefinicionDeImpuestos);
		
		JButton btnSalir5 = new JButton("Salir");
		btnSalir5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnSalir5.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSalir5.setBounds(822, 532, 166, 42);
		administracion.add(btnSalir5);
		
		JPanel panelSeguridadGeneral = new JPanel();
		panelSeguridadGeneral.setBounds(452, 225, 360, 96);
		administracion.add(panelSeguridadGeneral);
		panelSeguridadGeneral.setLayout(null);
		
		JLabel lblSeguridadGeneral = new JLabel("SEGURIDAD GENERAL");
		lblSeguridadGeneral.setBounds(10, 0, 270, 23);
		panelSeguridadGeneral.add(lblSeguridadGeneral);
		lblSeguridadGeneral.setFont(new Font("Tahoma", Font.BOLD, 22));
		
		JButton btnNewButton = new JButton("Administraci\u00F3n de Usuarios/General");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				boolean tienePermiso = autCtrl.validarAccesoOpcion("SEG_009", Sesion.getAccesosOpcion());
				if (tienePermiso)
				{
					VentSegCrearEmpleado ventCrearEmpleado = new VentSegCrearEmpleado(null, true);
					ventCrearEmpleado.setVisible(true);
				}else
				{
					JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla. \n Si desea crear un empleado o enrolarlo de nuevo por favor tenga en cuenta \n que esta accion se realiza de manera centralizada en la Bodega." , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(10, 34, 270, 51);
		panelSeguridadGeneral.add(btnNewButton);
		
		lblInformacionUsuario = new JLabel("USUARIO");
		lblInformacionUsuario.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblInformacionUsuario.setBounds(26, 11, 461, 14);
		contentPane.add(lblInformacionUsuario);
		
		//Realizamos validación de si el sistema esta cerrado para cerrar el sistema
		boolean estaAperturado = pedCtrl.isSistemaAperturado();
		if(!estaAperturado)
		{
			System.exit(0);
		}		
	}
}
