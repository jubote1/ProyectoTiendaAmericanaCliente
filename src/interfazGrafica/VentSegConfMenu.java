package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import capaControlador.MenuCtrl;
import capaControlador.ParametrosProductoCtrl;
import capaModelo.ConfiguracionMenu;
import capaModelo.Producto;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.AbstractAction;
import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.Font;

public class VentSegConfMenu extends JDialog {

	private JPanel contentPane;
	private JPanel panelBotonesMenu;
	private JComboBox comboBoxProducto;
	private JComboBox comboBoxMenu;
	private JComboBox comboBoxMultiMenu;
	private JTable tableMenu;
	//Creamos una variable Arreglo Global que contendr� la Configuraci�n Men�
	private ConfiguracionMenu[][] confiMenu;
	// El arreglo con los botones del men� activo se manejar� como variable global
	private Object[][] botones;
	private JButton btnEliminarRelacinProducto;
	private ParametrosProductoCtrl parCtrl = new ParametrosProductoCtrl(PrincipalLogueo.habilitaAuditoria);
	private JButton btnSalir;
	int multimenuActivo;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentSegConfMenu frame = new VentSegConfMenu(null, false);
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
	public VentSegConfMenu(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("CONFIGURACI\u00D3N DE MEN\u00DAS");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
		contentPane.setLayout(null);
		this.setSize(this.getToolkit().getScreenSize()); 
		//setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 864, 504);
		panelBotonesMenu = new JPanel();
		panelBotonesMenu.setBounds(200, 0, 769, 315);
		contentPane.add(panelBotonesMenu);
		panelBotonesMenu.setLayout(null);
		
		JPanel panelComandos = new JPanel();
		panelComandos.setBounds(0, 0, 200, 315);
		contentPane.add(panelComandos);
		panelComandos.setLayout(null);
		
		comboBoxProducto = new JComboBox();
		comboBoxProducto.setBounds(10, 197, 180, 20);
		panelComandos.add(comboBoxProducto);
		
		JLabel lblProducto = new JLabel("Producto");
		lblProducto.setBounds(20, 172, 46, 14);
		panelComandos.add(lblProducto);
		
		comboBoxMenu = new JComboBox();
		comboBoxMenu.setBounds(10, 122, 180, 20);
		panelComandos.add(comboBoxMenu);
		
		comboBoxMultiMenu = new JComboBox();
		comboBoxMultiMenu.setBounds(10, 48, 180, 20);
		panelComandos.add(comboBoxMultiMenu);
		
		InicializarCombos();
		
		//Adicionamos evento de cambio para el 
		
		comboBoxMultiMenu.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		    	String multime = (String) comboBoxMultiMenu.getSelectedItem();
				StringTokenizer StrMulti = new StringTokenizer(multime,"-");
				StrMulti.nextToken();
				int intMultimenu = Integer.parseInt(StrMulti.nextToken().trim());
				cargarConfiguracionMenu();
				//
				comboBoxProducto.setSelectedIndex(0);
				comboBoxMenu.setSelectedIndex(0);
			}
		});
		
		tableMenu = new JTable();
		tableMenu.setBounds(31, 21, 684, 240);
		// Definici�n de condiciones del JTable
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
		
				
		JLabel lblMen = new JLabel("Men\u00FA");
		lblMen.setBounds(20, 97, 46, 14);
		panelComandos.add(lblMen);
		
		JButton btnAsociarProductoMen = new JButton("Asociar Producto Men\u00FA");
		btnAsociarProductoMen.addActionListener(new ActionListener() {
			// Definimos las acciones a realizar cuando se desee adicionar un men� a un menu determinado
			public void actionPerformed(ActionEvent arg0) {
				//Obtenemos el multimenu
				String multime = (String) comboBoxMultiMenu.getSelectedItem();
				StringTokenizer StrMulti = new StringTokenizer(multime,"-");
				StrMulti.nextToken();
				int intMultimenu = Integer.parseInt(StrMulti.nextToken().trim());
				String menu = (String) comboBoxMenu.getSelectedItem();
				StringTokenizer StrMenu = new StringTokenizer(menu," ");
				StrMenu.nextToken();
				int intMenu = Integer.parseInt(StrMenu.nextToken().trim());
				// Se requiere otro tratamiento para sacar la fila y la columna
				int columna, fila;
				int divisor = 6;
				// Se deja l�gica para ubicar los men�s nuevos.
				if((intMenu % divisor) == 0)
				{
					columna = divisor - 1;
					fila = ((int) (intMenu / divisor)) - 1;
				}
				else
				{
					columna = (intMenu % divisor) -1;
					fila = ((int) (intMenu / divisor));
				}
				
				String producto = (String) comboBoxProducto.getSelectedItem();
				StringTokenizer StrProducto = new StringTokenizer(producto,"-");
				int intProducto = Integer.parseInt(StrProducto.nextToken().trim());
				MenuCtrl menuCtrl = new MenuCtrl();
				ConfiguracionMenu confMenuNuevo = new ConfiguracionMenu(0,intMultimenu,intMenu, fila, columna, intProducto);
				int idConfMenuIns = menuCtrl.insertarConfiguracionMenu(confMenuNuevo);
				//Hacemos la modificaci�n sobre los arreglos que se cargan y deben de tener esta informaci�n.
				//ser�a Volver a llamar al m�todo 
				cargarConfiguracionMenu();
				//
				comboBoxProducto.setSelectedIndex(0);
				comboBoxMenu.setSelectedIndex(0);
				//comboBoxMultiMenu.setSelectedIndex(0);
			}
		});
		btnAsociarProductoMen.setBounds(35, 240, 143, 23);
		panelComandos.add(btnAsociarProductoMen);
		
		JLabel lblMultimen = new JLabel("Multimen\u00FA");
		lblMultimen.setBounds(20, 23, 74, 14);
		panelComandos.add(lblMultimen);
		
		btnEliminarRelacinProducto = new JButton("Eliminar Relaci\u00F3n Producto Men\u00FA");
		btnEliminarRelacinProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Obtenemos el multimenu
				String multime = (String) comboBoxMultiMenu.getSelectedItem();
				StringTokenizer StrMulti = new StringTokenizer(multime,"-");
				StrMulti.nextToken();
				int intMultimenu = Integer.parseInt(StrMulti.nextToken().trim());
				String menu = (String) comboBoxMenu.getSelectedItem();
				StringTokenizer StrMenu = new StringTokenizer(menu," ");
				StrMenu.nextToken();
				int intMenu = Integer.parseInt(StrMenu.nextToken().trim());
				// Se requiere otro tratamiento para sacar la fila y la columna
				int columna, fila;
				int divisor = 6;
				// Se deja l�gica para ubicar los men�s nuevos.
				if((intMenu % divisor) == 0)
				{
					columna = divisor - 1;
					fila = ((int) (intMenu / divisor)) - 1;
				}
				else
				{
					columna = (intMenu % divisor) -1;
					fila = ((int) (intMenu / divisor));
				}
				
				MenuCtrl menuCtrl = new MenuCtrl();
				ConfiguracionMenu confMenuEli = new ConfiguracionMenu(0,intMultimenu,intMenu, fila, columna, 0);
				boolean respuesta = menuCtrl.eliminarConfiguracionMenu(confMenuEli);
				//Hacemos la modificaci�n sobre los arreglos que se cargan y deben de tener esta informaci�n.
				//ser�a Volver a llamar al m�todo 
				cargarConfiguracionMenu();
				//
				comboBoxProducto.setSelectedIndex(0);
				comboBoxMenu.setSelectedIndex(0);
				comboBoxMultiMenu.setSelectedIndex(0);
			}
		});
		btnEliminarRelacinProducto.setFont(new Font("Tahoma", Font.BOLD, 8));
		btnEliminarRelacinProducto.setBounds(20, 274, 170, 23);
		panelComandos.add(btnEliminarRelacinProducto);
		
		btnSalir = new JButton("SALIR");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSalir.setBounds(380, 338, 143, 36);
		contentPane.add(btnSalir);
		
		
//		for(int i = 1; i <= 20; i++)
//		{
//			JButton boton = new JButton("Men� " + i );
//			panelBotonesMenu.add(boton);
//			botones.add(boton);
//		}
	}
	
	public void InicializarCombos()
	{
		//Inicializamos combo de productos
		ArrayList productos = parCtrl.obtenerProductos();
		for(int i = 0; i<productos.size();i++)
		{
			String[] fila =  (String[]) productos.get(i);
			comboBoxProducto.addItem(fila[0]+"-"+fila[1]);
		}
		
		for(int i = 1; i <= 36; i++)
		{
			comboBoxMenu.addItem("Men� " + i);
		}
		
		for(int i = 1; i <= 6; i++)
		{
			comboBoxMultiMenu.addItem("Multimen� - " + i);
		}
		
		//Inicializamos combo de Men�
		
	}
	
	public void cargarConfiguracionMenu()
	{
		String multime = (String) comboBoxMultiMenu.getSelectedItem();
		StringTokenizer StrMulti = new StringTokenizer(multime,"-");
		StrMulti.nextToken();
		int intMultimenu = Integer.parseInt(StrMulti.nextToken().trim());
		MenuCtrl menuCtrl = new MenuCtrl();
		confiMenu = menuCtrl.obtenerConfMenu(intMultimenu);
		//Aqui deber�a pintarse el men�
		JButton btn = new JButton();
		ConfiguracionMenu objConfMenu;
		Object objGenerico;
		int numBoton = 1;
		for (int i = 0; i<6; i++)
		{
			for (int j = 0; j <6 ; j++)
			{
				objGenerico = confiMenu[i][j];
				if (objGenerico == null)
				{
					btn = new JButton("Men� " + numBoton);
				}
				else
				{
					objConfMenu = (ConfiguracionMenu) objGenerico;
					Producto prodBoton = parCtrl.obtenerProducto(objConfMenu.getIdProducto());
					btn = new JButton(prodBoton.getIdProducto() + " - " + prodBoton.getDescripcion());
				}
				botones[i][j] = btn;
				numBoton++;
			}
		}
		//finalizamos de llenar el arreglo
		MyTableModel model = new MyTableModel(new String[] {
				"col1", "col2", "col3", "col4", "col5", "col6"
			},botones);
		tableMenu.setModel(model);
	}
}








