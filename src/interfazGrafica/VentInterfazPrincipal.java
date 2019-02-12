package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.JLayeredPane;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentInterfazPrincipal extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentInterfazPrincipal frame = new VentInterfazPrincipal();
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
	public VentInterfazPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(MAXIMIZED_BOTH);
		setBounds(100, 100, 1024, 770);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 22, 1004, 670);
		contentPane.add(tabbedPane);
		tabbedPane.setUI(new ShapeTabbedPaneUI());
		
		JPanel tuspedidos = new JPanel();
		tuspedidos.setBackground(Color.WHITE);
		tuspedidos.setToolTipText("");
		tabbedPane.addTab("Tus Pedidos", null, tuspedidos, null);
		tuspedidos.setLayout(null);
		
		JButton btnUnPedidomas = new JButton("Nuevo Pedido");
		btnUnPedidomas.setBounds(718, 111, 187, 311);
		tuspedidos.add(btnUnPedidomas);
		
		JButton btnTransacciones = new JButton("<html>Todas las </br>transacciones<html>");
		btnTransacciones.setHorizontalAlignment(SwingConstants.LEADING);
		btnTransacciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnTransacciones.setBounds(62, 111, 122, 121);
		tuspedidos.add(btnTransacciones);
		
		JButton button = new JButton("New button");
		button.setBounds(62, 301, 122, 121);
		tuspedidos.add(button);
		
		JButton btnTiempo = new JButton("<html>Tiempo y </br>suspensi\u00F3n</html>");
		btnTiempo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnTiempo.setBounds(244, 111, 122, 121);
		tuspedidos.add(btnTiempo);
		
		JButton btnTusDomicilios = new JButton("Tus Domicilios");
		btnTusDomicilios.setBounds(244, 301, 299, 121);
		tuspedidos.add(btnTusDomicilios);
		
		JButton btnMesas = new JButton("Mesas");
		btnMesas.setBounds(421, 111, 122, 121);
		tuspedidos.add(btnMesas);
		
		JPanel ventas = new JPanel();
		tabbedPane.addTab("Ventas", null, ventas, null);
		
		JPanel inventarios = new JPanel();
		tabbedPane.addTab("Inventarios", null, inventarios, null);
		
		JPanel horarios = new JPanel();
		tabbedPane.addTab("Horarios y personal", null, horarios, null);
		
		JPanel administracion = new JPanel();
		tabbedPane.addTab("Administrador", null, administracion, null);
	}
}
