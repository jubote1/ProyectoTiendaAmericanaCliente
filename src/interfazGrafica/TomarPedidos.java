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
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class TomarPedidos extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable tableMenu;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 740, 770);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelPedido = new JPanel();
		panelPedido.setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0), 3), null));
		panelPedido.setBounds(0, 0, 225, 324);
		contentPane.add(panelPedido);
		panelPedido.setLayout(null);
		
		table = new JTable();
		table.setBounds(10, 272, 205, -260);
		panelPedido.add(table);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(103, 250, 112, 20);
		panelPedido.add(textPane);
		
		JLabel lblValorTotal = new JLabel("Valor Total ");
		lblValorTotal.setBounds(10, 250, 83, 14);
		panelPedido.add(lblValorTotal);
		
		JButton btnAnularItem = new JButton("Anular Item");
		btnAnularItem.setBounds(51, 279, 112, 36);
		panelPedido.add(btnAnularItem);
		
		JPanel panelMenu = new JPanel();
		panelMenu.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panelMenu.setBounds(235, 0, 479, 273);
		contentPane.add(panelMenu);
		panelMenu.setLayout(null);
		
		tableMenu = new JTable();
		tableMenu.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		tableMenu.setBounds(31, 241, 352, -210);
		panelMenu.add(tableMenu);
		
		JPanel panelAgrupadorMenu = new JPanel();
		panelAgrupadorMenu.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panelAgrupadorMenu.setBounds(235, 272, 479, 52);
		contentPane.add(panelAgrupadorMenu);
		panelAgrupadorMenu.setLayout(null);
		
		JPanel panelAcciones = new JPanel();
		panelAcciones.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panelAcciones.setBounds(0, 335, 714, 69);
		contentPane.add(panelAcciones);
		panelAcciones.setLayout(null);
		
		JButton btnAsignarCliente = new JButton("Asignar Cliente");
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
		
		JButton btnNewButton = new JButton("New button");
		
	}
}
