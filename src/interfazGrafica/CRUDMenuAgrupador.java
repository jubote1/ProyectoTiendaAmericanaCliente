package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import capaControlador.AutenticacionCtrl;
import capaModelo.MenuAgrupador;

import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CRUDMenuAgrupador extends JFrame {

	private JPanel contentPane;
	private JTextField jTextIDAgrupador;
	private JTextField jTextMenu;
	private JTextField jTextDescripcion;
	private JScrollPane scrollPane;
	private JTable jTableMenuAgrupador;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CRUDMenuAgrupador frame = new CRUDMenuAgrupador();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * Se documentan todas las acciones  a seguir cuando se instancia el frame para el CRUD de menú Agrupador.
	 */
		public CRUDMenuAgrupador() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 773, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Object[] columnsName = new Object[3];
        
        columnsName[0] = "Id ";
        columnsName[1] = "Menu";
        columnsName[2] = "Descripcion";
        
        
		
		System.out.println("hola 1");
		AutenticacionCtrl aut = new AutenticacionCtrl();
		System.out.println("hola 2");
		ArrayList<Object> menusAgrupador = aut.obtenerMenusAgrupador();
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(columnsName);
		//this.jTableMenuAgrupador.setModel(modelo);
		System.out.println(menusAgrupador.size());
		for(int y = 0; y < menusAgrupador.size();y++)
		{
			String[] fila =(String[]) menusAgrupador.get(y);
			System.out.println(fila[2]);
			modelo.addRow(fila);
		}
		
		JPanel panelDatos = new JPanel();
		panelDatos.setBounds(22, 11, 699, 125);
		contentPane.add(panelDatos);
		panelDatos.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Id Men\u00FA Agrupador");
		lblNewLabel.setBounds(29, 11, 93, 14);
		panelDatos.add(lblNewLabel);
		
		JLabel lblDescripcion = new JLabel("Descripcion");
		lblDescripcion.setBounds(29, 78, 74, 14);
		panelDatos.add(lblDescripcion);
		
		JLabel lblMenAgrupador = new JLabel("Men\u00FA Agrupador");
		lblMenAgrupador.setBounds(29, 46, 114, 14);
		panelDatos.add(lblMenAgrupador);
		
		jTextDescripcion = new JTextField();
		jTextDescripcion.setBounds(130, 75, 201, 20);
		panelDatos.add(jTextDescripcion);
		jTextDescripcion.setColumns(100);
		
		jTextMenu = new JTextField();
		jTextMenu.setBounds(132, 43, 114, 20);
		panelDatos.add(jTextMenu);
		jTextMenu.setColumns(50);
		
		jTextIDAgrupador = new JTextField();
		jTextIDAgrupador.setBounds(132, 8, 114, 20);
		panelDatos.add(jTextIDAgrupador);
		jTextIDAgrupador.setEnabled(false);
		jTextIDAgrupador.setColumns(10);
		
		JPanel panelJtable = new JPanel();
		panelJtable.setBounds(20, 147, 701, 167);
		contentPane.add(panelJtable);
		panelJtable.setLayout(null);
		
		
		
		jTableMenuAgrupador = new JTable();
		jTableMenuAgrupador.setForeground(Color.GRAY);
		jTableMenuAgrupador.setBounds(372, 59, -264, -54);
		panelJtable.add(jTableMenuAgrupador);
		jTableMenuAgrupador.setColumnSelectionAllowed(true);
		jTableMenuAgrupador.setCellSelectionEnabled(true);
		jTableMenuAgrupador.setBorder(new LineBorder(new Color(0, 0, 0)));
		jTableMenuAgrupador.setBackground(Color.GRAY);
		
		this.jTableMenuAgrupador.setModel(modelo);
		
		
		JButton btnInsertar = new JButton("Insertar");
		/**
		 * Método que implementará la acción cuando se de click sobre el botón Insertar
		 */
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Se pulso botón para adicionar un nuevo Menú Agrupador
				String menu = jTextMenu.getText();
				String descripcionMenu = jTextDescripcion.getText();
				if(menu == "")
				{
					JOptionPane.showMessageDialog(null, "Valor del campo Menú es necesario", "Falta Información", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(descripcionMenu == "")
				{
					JOptionPane.showMessageDialog(null, "Valor del campo descripción Menú es necesario", "Falta Información", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				//MenuAgrupador menuAgrNuevo = 
			}
		});
		btnInsertar.setBounds(52, 133, 89, 23);
		panelJtable.add(btnInsertar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(175, 133, 89, 23);
		panelJtable.add(btnEliminar);
	}
}
