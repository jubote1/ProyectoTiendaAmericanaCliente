package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentPrincipalWeb extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	boolean pedidos,clientes,inventarios,productos,usuarios;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentPrincipalWeb frame = new VentPrincipalWeb();
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
	public VentPrincipalWeb() {
		setTitle("MEN\u00DA PRINCIPAL");
		pedidos = clientes = inventarios = productos = usuarios = false;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1111, 672);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setExtendedState(MAXIMIZED_BOTH);
		JPanel panelMenu = new JPanel();
		panelMenu.setBounds(0, 0, 243, 455);
		contentPane.add(panelMenu);
		panelMenu.setLayout(null);
		
		JButton btnPedidos = new JButton("New button");
		btnPedidos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if(!pedidos)
				{
					Animacion.Animacion.mover_derecha(-120, 0, 1, 10, btnPedidos);
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(!pedidos)
				{
					Animacion.Animacion.mover_izquierda(0, -120, 1, 10, btnPedidos);
				}
			}
		});
		btnPedidos.setIcon(new ImageIcon(VentPrincipalWeb.class.getResource("/imagenes/btn-pedidos.png")));
		btnPedidos.setBounds(-120, 11, 212, 63);
		panelMenu.add(btnPedidos);
		
		JButton btnClientes = new JButton("New button");
		btnClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if(!clientes)
				{
					Animacion.Animacion.mover_derecha(-120, 0, 1, 10, btnClientes);
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(!clientes)
				{
					Animacion.Animacion.mover_izquierda(0, -120, 1, 10, btnClientes);
				}
			}
		});
		btnClientes.setIcon(new ImageIcon(VentPrincipalWeb.class.getResource("/imagenes/btn-clientes.png")));
		btnClientes.setBounds(-120, 85, 212, 63);
		panelMenu.add(btnClientes);
		
		JButton btnInventarios = new JButton("New button");
		btnInventarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if(!inventarios)
				{
					Animacion.Animacion.mover_derecha(-120, 0, 1, 10, btnInventarios);
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(!inventarios)
				{
					Animacion.Animacion.mover_izquierda(0, -120, 1, 10, btnInventarios);
				}
			}
		});
		btnInventarios.setIcon(new ImageIcon(VentPrincipalWeb.class.getResource("/imagenes/btn-inventario_3.png")));
		btnInventarios.setBounds(-120, 159, 212, 63);
		panelMenu.add(btnInventarios);
		
		JButton btnProductos = new JButton("New button");
		btnProductos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if(!productos)
				{
					Animacion.Animacion.mover_derecha(-120, 0, 1, 10, btnProductos);
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(!productos)
				{
					Animacion.Animacion.mover_izquierda(0, -120, 1, 10, btnProductos);
				}
			}
		});
		btnProductos.setIcon(new ImageIcon(VentPrincipalWeb.class.getResource("/imagenes/btn-productos.png")));
		btnProductos.setBounds(-120, 233, 212, 63);
		panelMenu.add(btnProductos);
		
		JButton btnUsuarios = new JButton("New button");
		btnUsuarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if(!usuarios)
				{
					Animacion.Animacion.mover_derecha(-120, 0, 1, 10, btnUsuarios);
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(!usuarios)
				{
					Animacion.Animacion.mover_izquierda(0, -120, 1, 10, btnUsuarios);
				}
			}
		});
		btnUsuarios.setIcon(new ImageIcon(VentPrincipalWeb.class.getResource("/imagenes/btn-usuarios.png")));
		btnUsuarios.setBounds(-120, 307, 212, 63);
		panelMenu.add(btnUsuarios);
		
		JButton btnSalir = new JButton("New button");
		btnSalir.setIcon(new ImageIcon(VentPrincipalWeb.class.getResource("/imagenes/btn-salir.png")));
		btnSalir.setBounds(21, 381, 212, 63);
		panelMenu.add(btnSalir);
		
		JPanel panelOpciones = new JPanel();
		panelOpciones.setBounds(242, 0, 853, 623);
		contentPane.add(panelOpciones);
		panelOpciones.setLayout(null);
		
		JButton btnOcultarMostrar = new JButton("Ocultar/Mostrar");
		btnOcultarMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int posicion = panelMenu.getX();
				if(posicion == 0)
				{
					Animacion.Animacion.mover_izquierda(0, -243 , 1, 20, panelMenu);
				}else
				{
					Animacion.Animacion.mover_derecha(-243, 0 , 1, 20, panelMenu);
				}
			}
		});
		btnOcultarMostrar.setBounds(10, 11, 120, 35);
		panelOpciones.add(btnOcultarMostrar);
	}
}
