package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import capaControlador.ParametrosProductoCtrl;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentConfMenu extends JFrame {

	private JPanel contentPane;
	private JPanel panelBotonesMenu;
	private JComboBox comboBoxProducto;
	private JComboBox comboBoxMenu;
	private ArrayList<JButton> botones;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentConfMenu frame = new VentConfMenu();
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
	public VentConfMenu() {
		botones = new ArrayList<JButton>();
		setTitle("CONFIGURACI\u00D3N DE MEN\u00DAS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 864, 504);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setExtendedState(MAXIMIZED_BOTH);
		
		panelBotonesMenu = new JPanel();
		panelBotonesMenu.setBounds(200, 0, 648, 315);
		contentPane.add(panelBotonesMenu);
		panelBotonesMenu.setLayout(new GridLayout(6, 6, 0, 0));
		
		JPanel panelComandos = new JPanel();
		panelComandos.setBounds(0, 0, 200, 315);
		contentPane.add(panelComandos);
		panelComandos.setLayout(null);
		
		comboBoxProducto = new JComboBox();
		comboBoxProducto.setBounds(10, 38, 180, 20);
		panelComandos.add(comboBoxProducto);
		
		JLabel lblProducto = new JLabel("Producto");
		lblProducto.setBounds(26, 13, 46, 14);
		panelComandos.add(lblProducto);
		
		comboBoxMenu = new JComboBox();
		comboBoxMenu.setBounds(10, 99, 180, 20);
		panelComandos.add(comboBoxMenu);
		
		InicializarCombos();
		
		JLabel lblMen = new JLabel("Men\u00FA");
		lblMen.setBounds(26, 74, 46, 14);
		panelComandos.add(lblMen);
		
		JButton btnAsociarProductoMen = new JButton("Asociar Producto Men\u00FA");
		btnAsociarProductoMen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		btnAsociarProductoMen.setBounds(26, 159, 143, 23);
		panelComandos.add(btnAsociarProductoMen);
		for(int i = 1; i <= 20; i++)
		{
			JButton boton = new JButton("Menú " + i );
			panelBotonesMenu.add(boton);
			botones.add(boton);
		}
	}
	
	public void InicializarCombos()
	{
		//Inicializamos combo de productos
		ParametrosProductoCtrl parCtrl = new ParametrosProductoCtrl();
		ArrayList productos = parCtrl.obtenerProductos();
		for(int i = 0; i<productos.size();i++)
		{
			String[] fila =  (String[]) productos.get(i);
			comboBoxProducto.addItem(fila[0]+"-"+fila[1]);
		}
		
		for(int i = 1; i <= 36; i++)
		{
			comboBoxMenu.addItem("Menú " + i);
		}
		
		
		
		//Inicializamos combo de Menú
		
	}
}
