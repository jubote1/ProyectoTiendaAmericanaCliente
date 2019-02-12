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

import capaControlador.EmpleadoCtrl;
import capaControlador.AutenticacionCtrl;
import capaControlador.PedidoCtrl;
import capaDAO.EstadoDAO;
import capaModelo.Estado;
import capaModelo.EstadoAnterior;
import capaModelo.EstadoPosterior;
import capaModelo.AgrupadorMenu;
import capaModelo.TipoEmpleado;

import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JScrollBar;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JRadioButton;
import javax.swing.JList;
import java.awt.Font;

public class VentSegTipoEmpleado extends JDialog {

	private JPanel contentPane;
	private JTextField jTextIdTipo;
	private JTextField jTextDescTipoEmpleado;
	private JScrollPane scrollPane;
	private JTable jTableTipEmpleado;
	private int idTipoEmpleado = 0;
	EstadoListModel estListModel1 = new EstadoListModel();
	EstadoListModel estListModel2 = new EstadoListModel();
	JList listEstDisponibles;
	JList listAsignados;
	private PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentSegTipoEmpleado frame = new VentSegTipoEmpleado(null, false);
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
	public DefaultTableModel pintarTipoEmpleado()
	{
		Object[] columnsName = new Object[7];
        
        columnsName[0] = "Id ";
        columnsName[1] = "Descripcion";
        columnsName[2] = "Cajero";
        columnsName[3] = "Domiciliario";
        columnsName[4] = "Administrador";
        columnsName[5] = "Hornero";
        columnsName[6] = "Cocinero";
        EmpleadoCtrl empCtrl = new EmpleadoCtrl(PrincipalLogueo.habilitaAuditoria);
		ArrayList<Object> tiposEmpleado = empCtrl.obtenerTipoEmpleado();
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < tiposEmpleado.size();y++)
		{
			String[] fila =(String[]) tiposEmpleado.get(y);
			System.out.println(fila);
			modelo.addRow(fila);
		}
		return(modelo);
		
	}
	
	/**
	 * Create the frame.
	 * Se documentan todas las acciones  a seguir cuando se instancia el frame para el CRUD de menú Agrupador.
	 */
	public VentSegTipoEmpleado(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("TIPO EMPLEADO");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
			}
		});
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0,0, 703, 457);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 703, 457);

		setBounds(100, 100, 703, 457);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
		JPanel panelDatos = new JPanel();
		panelDatos.setBorder(new LineBorder(new Color(0, 0, 0), 5));
		panelDatos.setBounds(22, 11, 655, 241);
		contentPane.add(panelDatos);
		panelDatos.setLayout(null);
		
		JLabel lblTipoEmpleado = new JLabel("Id Tipo Empleado");
		lblTipoEmpleado.setBounds(29, 11, 93, 14);
		panelDatos.add(lblTipoEmpleado);
		
		JLabel lblDescTipEmpleado = new JLabel("Desc Tipo Empleado");
		lblDescTipEmpleado.setBounds(29, 46, 114, 14);
		panelDatos.add(lblDescTipEmpleado);
		
		jTextDescTipoEmpleado = new JTextField();
		jTextDescTipoEmpleado.setBounds(142, 43, 114, 20);
		panelDatos.add(jTextDescTipoEmpleado);
		jTextDescTipoEmpleado.setColumns(50);
		
		jTextIdTipo = new JTextField();
		jTextIdTipo.setBounds(142, 8, 114, 20);
		panelDatos.add(jTextIdTipo);
		jTextIdTipo.setEnabled(false);
		jTextIdTipo.setColumns(10);
		
		//Se crea Panel que  contendrá el Jtable y los botones
		JPanel panelJtable = new JPanel();
		panelJtable.setBorder(new LineBorder(new Color(0, 0, 0), 5));
		panelJtable.setBounds(22, 263, 655, 156);
		contentPane.add(panelJtable);
		panelJtable.setLayout(null);
		DefaultTableModel modelo = pintarTipoEmpleado();
		//Adicionar manejo para el evento de seleccion
		
		
		
		//Adicionamos los botones para las acciones del GRID
		JButton btnInsertar = new JButton("Insertar");
		/**
		 * Método que implementará la acción cuando se de click sobre el botón Insertar
		 */
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(133, 121, 89, 23);
		panelJtable.add(btnEliminar);
		
		JButton btnEditar = new JButton("Editar");
		
		btnEditar.setBounds(247, 121, 89, 23);
		panelJtable.add(btnEditar);
		
		JButton btnGrabarEdicion = new JButton("Grabar Edicion");
		
		btnGrabarEdicion.setBounds(356, 121, 123, 23);
		panelJtable.add(btnGrabarEdicion);
		btnGrabarEdicion.setEnabled(false);
		
		JScrollPane scrollPaneTipoEmp = new JScrollPane();
		scrollPaneTipoEmp.setBounds(62, 33, 518, 77);
		panelJtable.add(scrollPaneTipoEmp);
		// Instanciamos el jtable
		jTableTipEmpleado = new JTable();
		scrollPaneTipoEmp.setViewportView(jTableTipEmpleado);
		jTableTipEmpleado.setForeground(Color.black);
		jTableTipEmpleado.setBorder(new LineBorder(new Color(0, 0, 0)));
		jTableTipEmpleado.setBackground(Color.WHITE);
		this.jTableTipEmpleado.setModel(modelo);
		jTextDescTipoEmpleado.setText("");
		
		JRadioButton rdbtnEsCajero = new JRadioButton("Es Cajero");
		rdbtnEsCajero.setBounds(288, 7, 109, 23);
		panelDatos.add(rdbtnEsCajero);
		
		JRadioButton rdbtnEsDomiciliario = new JRadioButton("Es Domiciliario");
		rdbtnEsDomiciliario.setBounds(402, 7, 109, 23);
		panelDatos.add(rdbtnEsDomiciliario);
		
		JRadioButton rdbtnEsAdministrador = new JRadioButton("Es Administrador");
		rdbtnEsAdministrador.setBounds(540, 7, 109, 23);
		panelDatos.add(rdbtnEsAdministrador);
		
		JRadioButton rdbtnEsHornero = new JRadioButton("Es Hornero");
		rdbtnEsHornero.setBounds(316, 42, 109, 23);
		panelDatos.add(rdbtnEsHornero);
		
		JRadioButton rdbtnEsCocinero = new JRadioButton("Es Cocinero");
		rdbtnEsCocinero.setBounds(470, 42, 109, 23);
		panelDatos.add(rdbtnEsCocinero);
		
		ButtonGroup grupoTipEmp = new ButtonGroup();
		grupoTipEmp.add(rdbtnEsCajero);
		grupoTipEmp.add(rdbtnEsDomiciliario);
		grupoTipEmp.add(rdbtnEsAdministrador);
		grupoTipEmp.add(rdbtnEsHornero);
		grupoTipEmp.add(rdbtnEsCocinero);
		
		JButton btnQuitarEstado = new JButton("<<<<<");
		btnQuitarEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int selection = listAsignados.getSelectedIndex();
				if (selection!=-1) 
				{
				     //Obtenemos el objeto estado seleccionado 
					 Estado p = estListModel2.getEstado(selection);
					 //Hacemos la inserción
				      //Realizamos la insercion en base de datos
				     boolean resEli = pedCtrl.eliminarTipoEmpleadoEstado(idTipoEmpleado, p.getIdestado());
				     Estado estEli= pedCtrl.obtenerEstado(p.getIdestado());
				     if(resEli)
				     {
				    	 //Si la inserción en base de datos es correcta se realiza el movimiento en los JList
				    	 estListModel1.addEstado(estEli);
				    	 estListModel2.eliminarEstado(selection);
				     }
				     
				     
				 }
				else
				{
					JOptionPane.showMessageDialog(null, "Debe seleccionar un Estado de la lista de Estados  Asignados ", "Error en Selección", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
			
			}
		});
		btnQuitarEstado.setBounds(286, 129, 72, 23);
		panelDatos.add(btnQuitarEstado);
		
		JButton btnAsignarEstado = new JButton(">>>>>");
		btnAsignarEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int selection = listEstDisponibles.getSelectedIndex();
				if (selection!=-1) 
				{
				     //Obtenemos el objeto estado seleccionado 
					 Estado p = estListModel1.getEstado(selection);
					 //Hacemos la inserción
					 //Realizamos la insercion en base de datos
				     boolean resIns = pedCtrl.insertarTipoEmpleadoEstado(idTipoEmpleado, p.getIdestado());
				     Estado estAdi = pedCtrl.obtenerEstado(p.getIdestado());
				     if(resIns)
				     {
				    	 //Si la inserción en base de datos es correcta se realiza el movimiento en los JList
				    	 estListModel2.addEstado(estAdi);
				    	 estListModel1.eliminarEstado(selection);
				     }
				     
				     
				 }
				else
				{
					JOptionPane.showMessageDialog(null, "Debe seleccionar un Estado de la lista de Estados  Disponibles ", "Error en Selección", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
			}
		});
		btnAsignarEstado.setBounds(286, 163, 72, 23);
		panelDatos.add(btnAsignarEstado);
		
		JLabel lblEstadosDisponibles = new JLabel("ESTADOS DISPONIBLES");
		lblEstadosDisponibles.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEstadosDisponibles.setBounds(111, 85, 134, 14);
		panelDatos.add(lblEstadosDisponibles);
		
		JLabel lblEstadosAVisualizar = new JLabel("ESTADOS A VISUALIZAR");
		lblEstadosAVisualizar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEstadosAVisualizar.setBounds(393, 85, 134, 14);
		panelDatos.add(lblEstadosAVisualizar);
		
		JScrollPane scrollPaneEstDis = new JScrollPane();
		scrollPaneEstDis.setBounds(98, 103, 158, 112);
		panelDatos.add(scrollPaneEstDis);
		
		listEstDisponibles = new JList();
		scrollPaneEstDis.setViewportView(listEstDisponibles);
		listEstDisponibles.setBorder(new LineBorder(new Color(0, 0, 0)));
		listEstDisponibles.setModel(estListModel1);
		
		JScrollPane scrollPaneEstAsi = new JScrollPane();
		scrollPaneEstAsi.setBounds(383, 104, 144, 111);
		panelDatos.add(scrollPaneEstAsi);
		
		listAsignados = new JList();
		scrollPaneEstAsi.setViewportView(listAsignados);
		listAsignados.setBorder(new LineBorder(new Color(0, 0, 0)));
		listAsignados.setModel(estListModel2);
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				boolean validar = validarDatos();
				if (validar)
				{
					String descripcion = jTextDescTipoEmpleado.getText();
					boolean cajero = false,domiciliario = false, administrador = false, hornero = false, cocinero = false;
					if(rdbtnEsCajero.isSelected())
					{
						cajero = true;
					}
					if(rdbtnEsDomiciliario.isSelected())
					{
						domiciliario = true;
					}
					if(rdbtnEsAdministrador.isSelected())
					{
						administrador = true;
					}
					if(rdbtnEsHornero.isSelected())
					{
						hornero = true;
					}
					if(rdbtnEsCocinero.isSelected())
					{
						cocinero = true;
					}
					TipoEmpleado tipEmp = new TipoEmpleado(0, descripcion,cajero, domiciliario, administrador, hornero, cocinero);
					EmpleadoCtrl empCtrl = new EmpleadoCtrl(PrincipalLogueo.habilitaAuditoria);
					int idTipoEmp = empCtrl.insertarTipoEmpleado(tipEmp);
					DefaultTableModel modelo = pintarTipoEmpleado();
					jTableTipEmpleado.setModel(modelo);
					jTextDescTipoEmpleado.setText("");
					rdbtnEsCajero.setSelected(false);
					rdbtnEsDomiciliario.setSelected(false);
					rdbtnEsAdministrador.setSelected(false);
					rdbtnEsHornero.setSelected(false);
					rdbtnEsCocinero.setSelected(false);
				}
			}
		});
		btnInsertar.setBounds(23, 121, 89, 23);
		panelJtable.add(btnInsertar);
		
		JButton btnSalir = new JButton("SALIR");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSalir.setBounds(502, 121, 123, 23);
		panelJtable.add(btnSalir);
		
		//Evento para definir las acciones para eliminar
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int filaSeleccionada = jTableTipEmpleado.getSelectedRow();
				//Hacemos la validación para decidir si se elimina o no
				String descTipEmpleado = (String) jTableTipEmpleado.getValueAt(filaSeleccionada, 1);
				int idTipEmpleado = Integer.parseInt((String)jTableTipEmpleado.getValueAt(filaSeleccionada, 0));
				JOptionPane.showMessageDialog(null, "Esta seguro que se desea eliminar el Tipo Empleado " +  descTipEmpleado , "Eliminacion Tipo Empleado ", JOptionPane.YES_NO_OPTION);
				EmpleadoCtrl empCtrl = new EmpleadoCtrl(PrincipalLogueo.habilitaAuditoria);
				empCtrl.eliminarTipoEmpleado(idTipEmpleado);
				DefaultTableModel modelo = pintarTipoEmpleado();
				jTableTipEmpleado.setModel(modelo);
			}
		});
		
		
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = jTableTipEmpleado.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar algún Menú Agrupador para editar " , "No ha Seleccionado para edición ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				limpiarElementosListas();
				filaSeleccionada = jTableTipEmpleado.getSelectedRow();
				jTextIdTipo.setText((String)jTableTipEmpleado.getValueAt(filaSeleccionada, 0));
				jTextDescTipoEmpleado.setText((String)jTableTipEmpleado.getValueAt(filaSeleccionada, 1));
				btnEliminar.setEnabled(false);
				btnInsertar.setEnabled(false);
				btnGrabarEdicion.setEnabled(true);
				idTipoEmpleado = Integer.parseInt((String)jTableTipEmpleado.getValueAt(filaSeleccionada, 0));
				llenarEstadosAsignados();
				llenarEstadosNoAsignados();
				if(Integer.parseInt((String)jTableTipEmpleado.getValueAt(filaSeleccionada, 2))==1)
				{
					rdbtnEsCajero.setSelected(true);
				}
				if((Integer.parseInt((String)jTableTipEmpleado.getValueAt(filaSeleccionada, 3))==1))
				{
					rdbtnEsDomiciliario.setSelected(true);
				}
				if((Integer.parseInt((String)jTableTipEmpleado.getValueAt(filaSeleccionada, 4))==1))
				{
					rdbtnEsAdministrador.setSelected(true);
				}
				if((Integer.parseInt((String)jTableTipEmpleado.getValueAt(filaSeleccionada, 5))==1))
				{
					rdbtnEsHornero.setSelected(true);
				}
				if((Integer.parseInt((String)jTableTipEmpleado.getValueAt(filaSeleccionada, 6))==1))
				{
					rdbtnEsCocinero.setSelected(true);
				}
			}
		});
		
		btnGrabarEdicion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean validar = validarDatos();
				if (validar)
				{
					int idTipEmp = Integer.parseInt(jTextIdTipo.getText());
					String descripcion = jTextDescTipoEmpleado.getText();
					boolean cajero = false,domiciliario = false, administrador = false, hornero = false, cocinero = false;
					if(rdbtnEsCajero.isSelected())
					{
						cajero = true;
					}
					if(rdbtnEsDomiciliario.isSelected())
					{
						domiciliario = true;
					}
					if(rdbtnEsAdministrador.isSelected())
					{
						administrador = true;
					}
					if(rdbtnEsHornero.isSelected())
					{
						hornero = true;
					}
					if(rdbtnEsCocinero.isSelected())
					{
						cocinero = true;
					}
					TipoEmpleado tipEmp = new TipoEmpleado(idTipEmp, descripcion,cajero, domiciliario, administrador, hornero, cocinero);
					EmpleadoCtrl empCtrl = new EmpleadoCtrl(PrincipalLogueo.habilitaAuditoria);
					boolean respuesta = empCtrl.editarTipoEmpleado(tipEmp);
					if (respuesta)
					{
						limpiarElementosListas();
						JOptionPane.showMessageDialog(null, "Se ha editado correctamente el registro " , "Confirmación Edición", JOptionPane.OK_OPTION);
						DefaultTableModel modelo = pintarTipoEmpleado();
						jTableTipEmpleado.setModel(modelo);
						jTextDescTipoEmpleado.setText("");
						jTextIdTipo.setText("");
						btnEliminar.setEnabled(true);
						btnInsertar.setEnabled(true);
						btnGrabarEdicion.setEnabled(false);
						idTipoEmpleado = 0;
						rdbtnEsCajero.setSelected(false);
						rdbtnEsDomiciliario.setSelected(false);
						rdbtnEsAdministrador.setSelected(false);
						rdbtnEsHornero.setSelected(false);
						rdbtnEsCocinero.setSelected(false);
					}
				}
				
			}
		});
		
	}
	
	
public boolean validarDatos()
{
	String descripcion = jTextDescTipoEmpleado.getText();
	if(descripcion == "")
	{
		JOptionPane.showMessageDialog(null, "Valor del campo Descripcion de Empleado es necesario", "Falta Información", JOptionPane.ERROR_MESSAGE);
		return(false);
	}
		
	return(true);
}


public void llenarEstadosNoAsignados()
{
	ArrayList<Estado> estadosNoAsignados = pedCtrl.obtenerEstFalTipoEmpleado(idTipoEmpleado);
	for(int i = 0; i < estadosNoAsignados.size(); i++)
	{
		Estado estNoAsi = estadosNoAsignados.get(i);
		estListModel1.addEstado(estNoAsi);
	}
}

public void llenarEstadosAsignados()
{
	ArrayList<Estado> estadosAsignados = pedCtrl.obtenerEstadosTipoEmpleado(idTipoEmpleado);
	for(int i = 0; i < estadosAsignados.size(); i++)
	{
		Estado estAsi = estadosAsignados.get(i);
		estListModel2.addEstado(estAsi);
	}
}

public void limpiarElementosListas()
{
	
	for(int i = 0; i < estListModel1.getSize(); i++)
	{
		estListModel1.eliminarEstado(i);
		i--;
	}
	
	for(int i = 0; i < estListModel2.getSize(); i++)
	{
		estListModel2.eliminarEstado(i);
		i--;
	}
	
}
}
