package interfazGrafica;

import java.awt.BorderLayout;
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

public class VentanaPrincipal extends JFrame {

	private JPanel PanelPrincipal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
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
	public VentanaPrincipal() {
		setTitle("MEN\u00DA PRINCIPAL");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 775, 444);
		PanelPrincipal = new JPanel();
		PanelPrincipal.setBackground(Color.WHITE);
		PanelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(PanelPrincipal);
		PanelPrincipal.setLayout(null);
		
		JButton btnSeguridadMenAgrupador = new JButton("Seguridad Men\u00FA Agrupador");
		btnSeguridadMenAgrupador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CRUDMenuAgrupador MenuAgrupa = new CRUDMenuAgrupador();
				MenuAgrupa.setVisible(true);
			}
		});
		btnSeguridadMenAgrupador.setBounds(24, 124, 180, 48);
		PanelPrincipal.add(btnSeguridadMenAgrupador);
		
		JLabel lblSeguridad = new JLabel("Seguridad");
		ImageIcon icono = new ImageIcon("iconos\\seguridad.jpg");
		lblSeguridad.setIcon(icono);
		lblSeguridad.setBounds(734, 24, 15, 93);
		PanelPrincipal.add(lblSeguridad);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(10, 24, 134, 75);
		ImageIcon icono2 = new ImageIcon("iconos\\LogoPizzaAmericana.png");
		lblLogo.setIcon(icono2);
		PanelPrincipal.add(lblLogo);
		
		JButton btnEleccionForzada = new JButton("Eleccion Forzada");
		btnEleccionForzada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentCRUDEleccionForzada EleccionForzada = new VentCRUDEleccionForzada();
				EleccionForzada.setVisible(true);
			}
		});
		btnEleccionForzada.setBounds(276, 124, 188, 48);
		PanelPrincipal.add(btnEleccionForzada);
		
		JButton btnImpuestos = new JButton("Impuestos");
		btnImpuestos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentCRUDImpuesto impuesto = new VentCRUDImpuesto();
				impuesto.setVisible(true);
			}
		});
		btnImpuestos.setBounds(539, 124, 188, 48);
		PanelPrincipal.add(btnImpuestos);
		
		JButton btnCreacionClientes = new JButton("Creacion Clientes");
		btnCreacionClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentCliente cliente  = new VentCliente(0);
				cliente.setVisible(true);
			}
		});
		btnCreacionClientes.setBounds(24, 197, 180, 48);
		PanelPrincipal.add(btnCreacionClientes);
		
		JButton btnItemsInventario = new JButton("Items Inventario");
		btnItemsInventario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentCRUDItemInventario itemInv = new VentCRUDItemInventario();
				itemInv.setVisible(true);
			}
		});
		btnItemsInventario.setBounds(275, 196, 189, 48);
		PanelPrincipal.add(btnItemsInventario);
		
		JButton btnProductos = new JButton("Productos");
		btnProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentCRUDProducto producto = new VentCRUDProducto();
				producto.setVisible(true);
			}
		});
		btnProductos.setBounds(539, 195, 188, 48);
		PanelPrincipal.add(btnProductos);
		
		JButton btnParmetrosDireccin = new JButton("Par\u00E1metros Direcci\u00F3n");
		btnParmetrosDireccin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentParametrosDireccion parDir = new VentParametrosDireccion();
				parDir.setVisible(true);
			}
		});
		btnParmetrosDireccin.setBounds(24, 269, 180, 48);
		PanelPrincipal.add(btnParmetrosDireccin);
		
		JButton btnConfiguracionMenu = new JButton("Configuracion Menu");
		btnConfiguracionMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentConfMenu confMenu = new VentConfMenu();
				confMenu.setVisible(true);
			}
		});
		btnConfiguracionMenu.setBounds(276, 269, 188, 48);
		PanelPrincipal.add(btnConfiguracionMenu);
		
		JButton btnTomaPedidos = new JButton("Toma Pedidos");
		btnTomaPedidos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TomarPedidos tomaPedido = new TomarPedidos();
				tomaPedido.setVisible(true);
			}
		});
		btnTomaPedidos.setBounds(539, 269, 188, 48);
		PanelPrincipal.add(btnTomaPedidos);
	}

}
