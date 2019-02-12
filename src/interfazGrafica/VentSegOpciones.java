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
import capaControlador.EmpleadoCtrl;
import capaControlador.ParametrosDireccionCtrl;
import capaModelo.AccesosPorMenu;
import capaModelo.AgrupadorMenu;
import capaModelo.Municipio;
import capaModelo.TipoEmpleado;

import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JScrollBar;
import java.awt.Font;
import javax.swing.JComboBox;

public class VentSegOpciones extends JDialog {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JComboBox cmbBoxTipoEmpleado;
	private JComboBox cmbBoxAgrupadorMenu;
	EmpleadoCtrl empCtrl = new EmpleadoCtrl(PrincipalLogueo.habilitaAuditoria);
	AutenticacionCtrl autCtrl = new AutenticacionCtrl(PrincipalLogueo.habilitaAuditoria);
	private JTable jTableAccesosPorMenu;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentSegOpciones frame = new VentSegOpciones(null, false);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Método que se encarga de retornar los menus agrupadores y de pintarlos en el Jtable correspondiente la información
	 * retornada de base de datos.
	 */
	public void pintarAccesos()
	{
		Object[] columnsName = new Object[3];
        
        columnsName[0] = "Id Acceso Menu ";
        columnsName[1] = "Tipo Empleado";
        columnsName[2] = "Agrupador Menu";
        ArrayList accesos = autCtrl.obtenerAccesosAgrupador();
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < accesos.size();y++)
		{
			String[] fila =(String[]) accesos.get(y);
			modelo.addRow(fila);
		}
		jTableAccesosPorMenu.setModel(modelo);
		
	}
	
	/**
	 * Create the frame.
	 * Se documentan todas las acciones  a seguir cuando se instancia el frame para el CRUD de menú Agrupador.
	 */
	public VentSegOpciones(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("ACCESO A MEN\u00DA POR ROL");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
			}
		});
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0,0, 773, 392);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 773, 392);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
		//Object[] columnsName = new Object[3];
        
        //columnsName[0] = "Id ";
        //columnsName[1] = "Menu";
        //columnsName[2] = "Descripcion";
        //AutenticacionCtrl aut = new AutenticacionCtrl();
		//ArrayList<Object> menusAgrupador = aut.obtenerMenusAgrupador();
		//DefaultTableModel modelo = new DefaultTableModel();
		//modelo.setColumnIdentifiers(columnsName);
		//for(int y = 0; y < menusAgrupador.size();y++)
		//{
		//	String[] fila =(String[]) menusAgrupador.get(y);
		//	modelo.addRow(fila);
		//}
		
		
		
		JPanel panelDatos = new JPanel();
		panelDatos.setBounds(22, 11, 699, 212);
		contentPane.add(panelDatos);
		panelDatos.setLayout(null);
		
		cmbBoxTipoEmpleado = new JComboBox();
		cmbBoxTipoEmpleado.setBounds(268, 18, 142, 20);
		panelDatos.add(cmbBoxTipoEmpleado);
		
		JLabel lblSeleccioneElTipo = new JLabel("Seleccione el Tipo de Empleado");
		lblSeleccioneElTipo.setBounds(65, 21, 180, 14);
		panelDatos.add(lblSeleccioneElTipo);
		
		JLabel lblMenAgrupador = new JLabel("Men\u00FA Agrupador");
		lblMenAgrupador.setBounds(65, 61, 142, 14);
		panelDatos.add(lblMenAgrupador);
		
		cmbBoxAgrupadorMenu = new JComboBox();
		cmbBoxAgrupadorMenu.setBounds(268, 58, 142, 20);
		panelDatos.add(cmbBoxAgrupadorMenu);
		
		JScrollPane scrollPaneAccesos = new JScrollPane();
		scrollPaneAccesos.setBounds(124, 97, 422, 90);
		panelDatos.add(scrollPaneAccesos);
		
		jTableAccesosPorMenu = new JTable();
		scrollPaneAccesos.setViewportView(jTableAccesosPorMenu);
		//Adicionar manejo para el evento de seleccion
		
		
		
		//Adicionamos los botones para las acciones del GRID
		JButton btnInsertarAccesos = new JButton("Insertar Accesos Por Menu");
		btnInsertarAccesos.setBounds(466, 43, 186, 23);
		panelDatos.add(btnInsertarAccesos);
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(574, 125, 89, 23);
		panelDatos.add(btnEliminar);
		//Evento para definir las acciones para eliminar
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int filaSeleccionada = jTableAccesosPorMenu.getSelectedRow();
				//Hacemos la validación para decidir si se elimina o no
				String tipoEmpleado = (String) jTableAccesosPorMenu.getValueAt(filaSeleccionada, 1);
				String menu = (String) jTableAccesosPorMenu.getValueAt(filaSeleccionada, 2);
				int idAccesoMenu = Integer.parseInt((String)jTableAccesosPorMenu.getValueAt(filaSeleccionada, 0));
				JOptionPane.showMessageDialog(null, "Esta seguro que se desea eliminar el Acceso para el tipo de empleado " +  tipoEmpleado + " sobre el menú " + menu , "Eliminacion Acceso Menú ", JOptionPane.YES_NO_OPTION);
				autCtrl.eliminarAccesosPorMenu(idAccesoMenu);
				pintarAccesos();
			}
		});
		btnInsertarAccesos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int idTipoEmpleado = ((TipoEmpleado)cmbBoxTipoEmpleado.getSelectedItem()).getIdTipoEmpleado();
				int idAgrupadorMenu = ((AgrupadorMenu)cmbBoxAgrupadorMenu.getSelectedItem()).getIdmenuagrupador();
				if(!(idTipoEmpleado > 0))
				{
					JOptionPane.showMessageDialog(null, "Debe seleciconar el tipo de empleado", "Falta Información", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(!(idAgrupadorMenu> 0))
				{
					JOptionPane.showMessageDialog(null, "Debe seleccionar un agrupador menú", "Falta Información", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				AccesosPorMenu acceso = new AccesosPorMenu(0,idTipoEmpleado, idAgrupadorMenu);
				int idAccesoIns = autCtrl.insertarAccesosPorMenu(acceso);
				pintarAccesos();
				cmbBoxTipoEmpleado.setSelectedIndex(0);
				cmbBoxAgrupadorMenu.setSelectedIndex(0);
			}
		});
		
		//Se crea Panel que  contendrá el Jtable y los botones
		JPanel panelJtable = new JPanel();
		panelJtable.setBounds(22, 264, 701, 79);
		contentPane.add(panelJtable);
		panelJtable.setLayout(null);
		pintarAccesos();
		
		JButton btnSalir = new JButton("SALIR");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSalir.setBounds(498, 25, 140, 31);
		panelJtable.add(btnSalir);
		
				
				
		
		initCmbAgrupadorMenu();
		initCmbBoxTipoEmpleado();
	}
	
	

public void initCmbBoxTipoEmpleado()
{
	
	ArrayList<TipoEmpleado> tipoEmpleados = empCtrl.obtenerTipoEmpleadoObj();
	for(int i = 0; i<tipoEmpleados.size();i++)
	{
		TipoEmpleado fila = (TipoEmpleado)  tipoEmpleados.get(i);
		cmbBoxTipoEmpleado.addItem(fila);
	}
}

public void initCmbAgrupadorMenu()
{
	
	ArrayList<AgrupadorMenu> agrMenus = autCtrl.obtenerMenusAgrupadorObj();
	for(int i = 0; i<agrMenus.size();i++)
	{
		AgrupadorMenu fila = (AgrupadorMenu)  agrMenus.get(i);
		cmbBoxAgrupadorMenu.addItem(fila);
	}
}
}
