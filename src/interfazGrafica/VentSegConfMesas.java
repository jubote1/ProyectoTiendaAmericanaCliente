package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import capaControlador.MenuCtrl;
import capaControlador.ParametrosProductoCtrl;
import capaModelo.ConfiguracionMenu;
import capaModelo.ConfiguracionMesa;
import capaModelo.Producto;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.Font;
import javax.swing.table.DefaultTableModel;

public class VentSegConfMesas extends JFrame {

	private JPanel contentPane;
	private JPanel panelBotonesMenu;
	private JComboBox comboBoxMesa;
	private JComboBox comboBoxPunto;
	private JTable tableMenu;
	//Creamos una variable Arreglo Global que contendrá la Configuración Menú
	private ConfiguracionMesa[][] confiMesa;
	// El arreglo con los botones del menú activo se manejará como variable global
	private Object[][] botones;
	private JButton btnEliminarMesaPunto;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentSegConfMesas frame = new VentSegConfMesas();
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
	public VentSegConfMesas() {
		
		setTitle("CONFIGURACIÓN DE MESAS");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 864, 504);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setExtendedState(MAXIMIZED_BOTH);
		
		panelBotonesMenu = new JPanel();
		panelBotonesMenu.setBounds(200, 0, 769, 315);
		contentPane.add(panelBotonesMenu);
		panelBotonesMenu.setLayout(null);
		
		JPanel panelComandos = new JPanel();
		panelComandos.setBounds(0, 0, 200, 315);
		contentPane.add(panelComandos);
		panelComandos.setLayout(null);
		
		comboBoxMesa = new JComboBox();
		comboBoxMesa.setBounds(10, 134, 180, 20);
		panelComandos.add(comboBoxMesa);
		
		JLabel lblMesa = new JLabel("Mesa");
		lblMesa.setBounds(20, 109, 46, 14);
		panelComandos.add(lblMesa);
		
		comboBoxPunto = new JComboBox();
		comboBoxPunto.setBounds(10, 59, 180, 20);
		panelComandos.add(comboBoxPunto);
		
		InicializarCombos();
		
		tableMenu = new JTable();
		tableMenu.setBounds(31, 21, 684, 240);
		// Definición de condiciones del JTable
		tableMenu.setCellSelectionEnabled(true);
		tableMenu.setDefaultRenderer(Object.class, new ButtonRenderer());
		tableMenu.setCellEditor(new ButtonEditor());
		tableMenu.setRowHeight(40);
		JButton btn;
			
		botones = new Object[6][6];
		cargarConfiguracionMenu();
		tableMenu.setDefaultRenderer(JButton.class, new ButtonRenderer());
		tableMenu.setDefaultEditor(JButton.class, new ButtonEditor());
		panelBotonesMenu.add(tableMenu);
		
				
		JLabel lblPunto = new JLabel("Punto/Ubicacaci\u00F3n");
		lblPunto.setBounds(20, 34, 107, 14);
		panelComandos.add(lblPunto);
		
		JButton btnAsociarMesaPunto = new JButton("Asociar Mesa Punto");
		btnAsociarMesaPunto.addActionListener(new ActionListener() {
			/**
			 * Método que define las acciones a realizar al momento de tratar de asociar a  un punto una determinada mesa
			 */
			public void actionPerformed(ActionEvent arg0) {
				//Extraemos el número del punto que se desea asignar
				String strPunto = (String) comboBoxPunto.getSelectedItem();
				StringTokenizer StrTokPunto = new StringTokenizer(strPunto,"-");
				StrTokPunto.nextToken();
				int intPunto = Integer.parseInt(StrTokPunto.nextToken().trim());
				//Este punto obtenido anteriormente deberemos de pasarlo a una fila y columna
				int columna, fila;
				int divisor = 8;
				// Se deja lógica para ubicar los menús nuevos.
				if((intPunto % divisor) == 0)
				{
					columna = divisor - 1;
					fila = ((int) (intPunto / divisor)) - 1;
				}
				else
				{
					columna = (intPunto % divisor) -1;
					fila = ((int) (intPunto / divisor));
				}
				
				
				//Extraemos el número de mesa que se desea asignar
				String strMesa = (String) comboBoxMesa.getSelectedItem();
				StringTokenizer StrToMesa = new StringTokenizer(strMesa,"-");
				StrToMesa.nextToken();
				int intMesa = Integer.parseInt(StrToMesa.nextToken().trim());
				
				//Instanaciamos la capa controladora para realizar la inserción de la configuración Mesa
				MenuCtrl menuCtrl = new MenuCtrl();
				ConfiguracionMesa confMesaNuevo = new ConfiguracionMesa(0,fila,columna, intMesa);
				int idConfMesaIns = menuCtrl.insertarConfiguracionMesa(confMesaNuevo);
				//Hacemos la modificación sobre los arreglos que se cargan y deben de tener esta información.
				//sería Volver a llamar al método 
				cargarConfiguracionMenu();
				//
				comboBoxMesa.setSelectedIndex(0);
				comboBoxPunto.setSelectedIndex(0);
				
			}
		});
		btnAsociarMesaPunto.setBounds(25, 178, 143, 23);
		panelComandos.add(btnAsociarMesaPunto);
		
		btnEliminarMesaPunto = new JButton("Eliminar Relaci\u00F3n Mesa Punto");
		btnEliminarMesaPunto.addActionListener(new ActionListener() {
			//Método que define la acción para eliminación de la relación entre entre Mesa y punto
			public void actionPerformed(ActionEvent e) {
				//Obtenemos el multimenu
				String strPunto = (String) comboBoxPunto.getSelectedItem();
				StringTokenizer StrTokPunto = new StringTokenizer(strPunto,"-");
				StrTokPunto.nextToken();
				int intPunto = Integer.parseInt(StrTokPunto.nextToken().trim());
				//Este punto obtenido anteriormente deberemos de pasarlo a una fila y columna
				int columna, fila;
				int divisor = 8;
				// Se deja lógica para ubicar los menús nuevos.
				if((intPunto % divisor) == 0)
				{
					columna = divisor - 1;
					fila = ((int) (intPunto / divisor)) - 1;
				}
				else
				{
					columna = (intPunto % divisor) -1;
					fila = ((int) (intPunto / divisor));
				}
				
				//Extraemos el número de mesa que se desea asignar
				String strMesa = (String) comboBoxMesa.getSelectedItem();
				StringTokenizer StrToMesa = new StringTokenizer(strMesa,"-");
				StrToMesa.nextToken();
				int intMesa = Integer.parseInt(StrToMesa.nextToken().trim());
				
				MenuCtrl menuCtrl = new MenuCtrl();
				ConfiguracionMesa confMesaEli = new ConfiguracionMesa(0,fila, columna, intMesa );
				boolean respuesta = menuCtrl.eliminarConfiguracionMesa(confMesaEli);
				//Hacemos la modificación sobre los arreglos que se cargan y deben de tener esta información.
				//sería Volver a llamar al método 
				cargarConfiguracionMenu();
				//
				comboBoxMesa.setSelectedIndex(0);
				comboBoxPunto.setSelectedIndex(0);
				
			}
		});
		btnEliminarMesaPunto.setFont(new Font("Tahoma", Font.BOLD, 8));
		btnEliminarMesaPunto.setBounds(10, 212, 170, 23);
		panelComandos.add(btnEliminarMesaPunto);
		
		
//		for(int i = 1; i <= 20; i++)
//		{
//			JButton boton = new JButton("Menú " + i );
//			panelBotonesMenu.add(boton);
//			botones.add(boton);
//		}
	}
	
	/**
	 * Método que se encarga de llenar los ComboBox con información de los puntos del mapa de las mesas y las mesas.
	 */
	public void InicializarCombos()
	{
		//Inicializamos combo de productos
		
		
		for(int i = 1; i <= 64; i++)
		{
			comboBoxPunto.addItem("Punto- " + i);
		}
		
		for(int i = 1; i <= 20; i++)
		{
			comboBoxMesa.addItem("Mesa-" + i);
		}
		
		//Inicializamos combo de Menú
		
	}
	
	/**
	 * Con este método se cargará la configuración de puntos y mesas y pintarlas en pantalla dentro de una
	 * estructura de 8 x8
	 */
	public void cargarConfiguracionMenu()
	{
		MenuCtrl menuCtrl = new MenuCtrl();
		confiMesa = menuCtrl.obtenerConfMesa();
		//Aqui debería pintarse el menú
		JButton btn = new JButton();
		ConfiguracionMesa objConfMesa;
		Object objGenerico;
		int numPunto = 1;
		//Recorremos el arreglo de 8 x 8 verificando si hay objeto o no
		for (int i = 0; i<8; i++)
		{
			for (int j = 0; j < 8 ; j++)
			{
				objGenerico = confiMesa[i][j];
				if (objGenerico == null)
				{
					btn = new JButton("Punto  " + numPunto);
				}
				else
				{
					objConfMesa = (ConfiguracionMesa) objGenerico;
					btn = new JButton("Mesa "+ " - " + objConfMesa.getMesa());
				}
				botones[i][j] = btn;
				numPunto++;
			}
		}
		//finalizamos de llenar el arreglo
		MyTableModel model = new MyTableModel(new String[] {
				"col1", "col2", "col3", "col4", "col5", "col6"
			},botones);
		tableMenu.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
			},
			new String[] {
				"col1", "col2", "col3", "col4", "col5", "col6", "New column", "New column"
			}
		));
	}
}








