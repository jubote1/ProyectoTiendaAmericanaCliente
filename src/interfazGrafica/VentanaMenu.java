package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;

public class VentanaMenu extends JFrame {

	private JPanel contentPane;
	private JTable tableMenu;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaMenu frame = new VentanaMenu();
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
	public VentanaMenu() {
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 11, 269, 340);
		contentPane.add(panel);
		setExtendedState(MAXIMIZED_BOTH);//otro metodo
		setUndecorated(true);
		
		tableMenu = new JTable();
		tableMenu.setDefaultRenderer(Object.class, new RenderVentanaMenu());
		
		JButton btn1 = new JButton("Alitas");
		JButton btn2 = new JButton("Nugetts");
		
		tableMenu.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, btn1, btn2},
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
		tableMenu.setBounds(289, 11, 944, 483);
		contentPane.add(tableMenu);
		
			
		
	}
}
