package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import capaControlador.ParametrosDireccionCtrl;
import capaControlador.ParametrosProductoCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.Estado;
import capaModelo.EstadoAnterior;
import capaModelo.EstadoPosterior;
import capaModelo.ImpuestoProducto;
import capaModelo.Municipio;
import capaModelo.Producto;
import capaModelo.TipoPedido;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;

public class VentCRUDEstado extends JFrame {

	private JPanel panelGeneral;
	private JTextField txtIdEstado;
	private JTextField txtDescripcion;
	private JTextField txtDescCorta;
	private JTable jTableEstados;
	private int idEstado;
	JList listEstAnteriores;
	JList listEstados1;
	JList listEstados2;
	JList listEstPosteriores;
	EstadoListModel estListModel = new EstadoListModel();
	EstadoListModel estListModel2 = new EstadoListModel();
	EstadoPosteriorListModel modeloListaPosteriores = new EstadoPosteriorListModel();
	EstadoAnteriorListModel modeloListaAnteriores = new EstadoAnteriorListModel();
	JComboBox comboTipoPedido;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentCRUDEstado frame = new VentCRUDEstado();
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
	public VentCRUDEstado() {
		setTitle("MAESTRO DE ESTADOS");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 903, 617);
		panelGeneral = new JPanel();
		panelGeneral.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelGeneral);
		panelGeneral.setLayout(null);
		
		JLabel lblIdEstado = new JLabel("Id Estado");
		lblIdEstado.setBounds(200, 27, 109, 14);
		panelGeneral.add(lblIdEstado);
		
		txtIdEstado = new JTextField();
		txtIdEstado.setEditable(false);
		txtIdEstado.setBounds(369, 24, 178, 20);
		panelGeneral.add(txtIdEstado);
		txtIdEstado.setColumns(10);
		
		JLabel lblDescripcionEstado = new JLabel("Descripcion Estado");
		lblDescripcionEstado.setBounds(200, 66, 146, 14);
		panelGeneral.add(lblDescripcionEstado);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(368, 63, 351, 20);
		panelGeneral.add(txtDescripcion);
		txtDescripcion.setColumns(10);
		
		JLabel lblDescripcionCorta = new JLabel("Descripcion Corta");
		lblDescripcionCorta.setBounds(200, 102, 146, 14);
		panelGeneral.add(lblDescripcionCorta);
		
		txtDescCorta = new JTextField();
		txtDescCorta.setBounds(369, 99, 178, 20);
		panelGeneral.add(txtDescCorta);
		txtDescCorta.setColumns(10);
		
		JPanel panelTable = new JPanel();
		panelTable.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panelTable.setBounds(10, 357, 854, 207);
		panelGeneral.add(panelTable);
		panelTable.setLayout(null);
		
		JButton btnInsertar = new JButton("Insertar");
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean validar = validarDatos();
				if (validar)
				{
				
				}else
				{
					return;
				}
				String descripcion, descripcionCorta;
				if (idEstado == 0)
				{
					descripcion = txtDescripcion.getText();
					descripcionCorta = txtDescCorta.getText();
					TipoPedido tipPedSel = (TipoPedido) comboTipoPedido.getSelectedItem();
					Estado estInsertar = new Estado(0,descripcion, descripcionCorta, tipPedSel.getIdTipoPedido());
					PedidoCtrl pedCtrl = new PedidoCtrl();
					pedCtrl.insertarEstado(estInsertar);
					DefaultTableModel modelo = pintarEstado();
					jTableEstados.setModel(modelo);
					txtDescripcion.setText("");
					txtDescCorta.setText("");
					txtIdEstado.setText("");
				}
				
			}
		});
		btnInsertar.setBounds(39, 160, 104, 23);
		panelTable.add(btnInsertar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int filaSeleccionada = jTableEstados.getSelectedRow();
				//Hacemos la validación para decidir si se elimina o no
				String estadoEliminar = (String) jTableEstados.getValueAt(filaSeleccionada, 1);
				int idEstadoEl = Integer.parseInt((String)jTableEstados.getValueAt(filaSeleccionada, 0));
				JOptionPane.showMessageDialog(null, "Esta seguro que se desea eliminar el Estado " +  estadoEliminar , "Eliminacion Estado ", JOptionPane.YES_NO_OPTION);
				PedidoCtrl pedCtrl = new PedidoCtrl();
				pedCtrl.eliminarEstado(idEstadoEl);
				DefaultTableModel modelo = pintarEstado();
				jTableEstados.setModel(modelo);
				txtIdEstado.setText("");
				txtDescripcion.setText("");
				txtDescCorta.setText("");
				idEstado = 0;
			}
		});
		
		JButton btnGrabarEdicion = new JButton("Grabar Edici\u00F3n");
		btnGrabarEdicion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean validar = validarDatos();
				if (validar)
				{
					String descripcion = txtDescripcion.getText();
					String descripcionCorta = txtDescCorta.getText();
					TipoPedido tipPedSel = (TipoPedido) comboTipoPedido.getSelectedItem();
					Estado estadoEditado = new Estado(idEstado, descripcion, descripcionCorta,tipPedSel.getIdTipoPedido());
					PedidoCtrl pedCtrl = new PedidoCtrl();
					boolean respuesta = pedCtrl.editarEstado(estadoEditado);
					if(respuesta)
					{
						JOptionPane.showMessageDialog(null, "Se ha editado correctamente el registro " , "Confirmación Edición", JOptionPane.OK_OPTION);
						DefaultTableModel modelo = pintarEstado();
						txtDescripcion.setText("");
						txtDescCorta.setText("");
						txtIdEstado.setText("");
						btnEliminar.setEnabled(true);
						btnInsertar.setEnabled(true);
						btnGrabarEdicion.setEnabled(false);
						idEstado = 0;
						jTableEstados.setModel(modelo);
						limpiarElementosListas();
					}
				}
			}
		});
		btnGrabarEdicion.setBounds(665, 160, 127, 23);
		panelTable.add(btnGrabarEdicion);
		
		btnEliminar.setBounds(194, 160, 127, 23);
		panelTable.add(btnEliminar);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarElementosListas();
				int filaSeleccionada = jTableEstados.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar algún Estado para editar " , "No ha Seleccionado para edición ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int idEstadoEdi = Integer.parseInt((String)jTableEstados.getValueAt(filaSeleccionada, 0));
				idEstado = idEstadoEdi;
				//Teniendo el producto y estando en la fase de carga podemos cargar los impuestos por producto
				txtIdEstado.setText((String)jTableEstados.getValueAt(filaSeleccionada, 0));
				PedidoCtrl pedCtrl = new PedidoCtrl();
				Estado estadoEditar = pedCtrl.obtenerEstado(idEstadoEdi);
				//Obtenemos el idProducto y con este retornamos el valor recuperarmos el valor para editar.
				txtDescripcion.setText(estadoEditar.getDescripcion());
				txtDescCorta.setText(estadoEditar.getDescripcionCorta());
				for (int i = 0; i < comboTipoPedido.getModel().getSize(); i++) {
					TipoPedido object = (TipoPedido)comboTipoPedido.getModel().getElementAt(i);
					if(object.getIdTipoPedido() == estadoEditar.getIdTipoPedido()){
						comboTipoPedido.setSelectedItem(object);
					}
				}
				btnEliminar.setEnabled(false);
				btnInsertar.setEnabled(false);
				btnGrabarEdicion.setEnabled(true);
				//Debemos de cargar la información de los JList con los estados
				llenarEstadosAnteriores();
				llenarEstadosPosteriores();
				llenarEstadosPosterioresNoAsignados();
				llenarEstadosAnterioresNoAsignados();
			}
		});
		btnEditar.setBounds(489, 160, 127, 23);
		panelTable.add(btnEditar);
		
		
		
		listEstAnteriores = new JList();
		listEstAnteriores.setBorder(new LineBorder(new Color(0, 0, 0)));
		listEstAnteriores.setBounds(40, 213, 139, 114);
		listEstAnteriores.setModel(modeloListaAnteriores);
		panelGeneral.add(listEstAnteriores);
		
		JLabel lblEstadosAnteriores = new JLabel("Estados Anteriores");
		lblEstadosAnteriores.setBounds(68, 188, 97, 14);
		panelGeneral.add(lblEstadosAnteriores);
		
		listEstados1 = new JList();
		listEstados1.setBorder(new LineBorder(new Color(0, 0, 0)));
		listEstados1.setBounds(296, 213, 139, 114);
		listEstados1.setModel(estListModel);
		panelGeneral.add(listEstados1);
		
		JLabel lblEstados1 = new JLabel("Estados Anteriores Disponibles");
		lblEstados1.setBounds(286, 188, 166, 14);
		panelGeneral.add(lblEstados1);
		
		JButton btnAdiEstAnteriores = new JButton("<<<<<");
		
		btnAdiEstAnteriores.setBounds(200, 240, 72, 23);
		panelGeneral.add(btnAdiEstAnteriores);
		
		JButton btnEliEstAnteriores = new JButton(">>>>>");
		btnEliEstAnteriores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Capturamos la seleccion
				int selection = listEstAnteriores.getSelectedIndex();
				if (selection!=-1) 
				{
				     //Obtenemos el objeto estado seleccionado 
					 EstadoAnterior p = modeloListaAnteriores.getEstadoAnterior(selection);
					 //Creamos el estado anterior a insertar
				     Estado estAdi = new Estado(p.getIdEstado(),p.getDescEstadoAnterior(),p.getDescEstadoAnterior(), 0);
				     PedidoCtrl pedCtrl = new PedidoCtrl();
				     //Realizamos la insercion en base de datos
				     boolean resIns = pedCtrl.eliminarEstadoAnterior(p);
				     if(resIns)
				     {
				    	 //Si la inserción en base de datos es correcta se realiza el movimiento en los JList
				    	 modeloListaAnteriores.eliminarEstadoAnterior(selection);
				    	 estListModel.addEstado(estAdi);
				     }
				     
				     
				 }
				else
				{
					JOptionPane.showMessageDialog(null, "Debe seleccionar un Estado de la lista de Estados Anteriores", "Error en Selección", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		btnEliEstAnteriores.setBounds(200, 274, 72, 23);
		panelGeneral.add(btnEliEstAnteriores);
		
		listEstPosteriores = new JList();
		listEstPosteriores.setBorder(new LineBorder(new Color(0, 0, 0)));
		listEstPosteriores.setBounds(490, 213, 139, 114);
		listEstPosteriores.setModel(modeloListaPosteriores);
		panelGeneral.add(listEstPosteriores);
		
		JButton btnAdiEstPosteriores = new JButton("<<<<<");
		btnAdiEstPosteriores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Capturamos la seleccion
				int selection = listEstados2.getSelectedIndex();
				if (selection!=-1) 
				{
				     //Obtenemos el objeto estado seleccionado 
					 Estado p = estListModel2.getEstado(selection);
					 //Creamos el estado anterior a insertar
				     EstadoPosterior estPosAdi = new EstadoPosterior(idEstado, p.getIdestado(), p.getDescripcionCorta());
				     PedidoCtrl pedCtrl = new PedidoCtrl();
				     //Realizamos la insercion en base de datos
				     boolean resIns = pedCtrl.insertarEstadoPosterior(estPosAdi);
				     if(resIns)
				     {
				    	 //Si la inserción en base de datos es correcta se realiza el movimiento en los JList
				    	 modeloListaPosteriores.addEstadoPosterior(estPosAdi);
				    	 estListModel2.eliminarEstado(selection);
				     }
				     
				     
				 }
				else
				{
					JOptionPane.showMessageDialog(null, "Debe seleccionar un Estado de la lista de Estados Posteriores Disponibles ", "Error en Selección", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		btnAdiEstPosteriores.setBounds(647, 240, 72, 23);
		panelGeneral.add(btnAdiEstPosteriores);
		
		JButton btnEliEstPosteriores = new JButton(">>>>>");
		btnEliEstPosteriores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Capturamos la seleccion
				int selection = listEstPosteriores.getSelectedIndex();
				if (selection!=-1) 
				{
				     //Obtenemos el objeto estado seleccionado 
					 EstadoPosterior p = modeloListaPosteriores.getEstadoPosterior(selection);
					 //Creamos el estado anterior a insertar
				     Estado estAdi = new Estado(p.getIdEstado(),p.getDescEstadoPosterior(),p.getDescEstadoPosterior(),0);
				     PedidoCtrl pedCtrl = new PedidoCtrl();
				     //Realizamos la insercion en base de datos
				     boolean resIns = pedCtrl.eliminarEstadoPosterior(p);
				     if(resIns)
				     {
				    	 //Si la inserción en base de datos es correcta se realiza el movimiento en los JList
				    	 modeloListaPosteriores.eliminarEstadoPosterior(selection);
				    	 estListModel2.addEstado(estAdi);
				     }
				     
				     
				 }
				else
				{
					JOptionPane.showMessageDialog(null, "Debe seleccionar un Estado de la lista de Estados Posteriores", "Error en Selección", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		btnEliEstPosteriores.setBounds(647, 274, 72, 23);
		panelGeneral.add(btnEliEstPosteriores);
		
		listEstados2 = new JList();
		listEstados2.setBorder(new LineBorder(new Color(0, 0, 0)));
		listEstados2.setBounds(735, 213, 139, 114);
		listEstados2.setModel(estListModel2);
		panelGeneral.add(listEstados2);
		
		JLabel lblEstadosPosterioresDisponibles = new JLabel("Estados Posteriores Disponibles");
		lblEstadosPosterioresDisponibles.setBounds(728, 188, 159, 14);
		panelGeneral.add(lblEstadosPosterioresDisponibles);
		
		JLabel lblEstadosPosteriores = new JLabel("Estados Posteriores");
		lblEstadosPosteriores.setBounds(512, 188, 97, 14);
		panelGeneral.add(lblEstadosPosteriores);
		DefaultTableModel modelo = pintarEstado();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(45, 27, 775, 100);
		panelTable.add(scrollPane);
		
		jTableEstados = new JTable();
		scrollPane.setViewportView(jTableEstados);
		jTableEstados.setModel(modelo);
		
		JLabel lblTipoPedido = new JLabel("Tipo Pedido");
		lblTipoPedido.setBounds(200, 139, 109, 14);
		panelGeneral.add(lblTipoPedido);
		
		comboTipoPedido = new JComboBox();
		comboTipoPedido.setBounds(369, 136, 178, 20);
		panelGeneral.add(comboTipoPedido);
		
		//Adición de las acciones de los botones para los estados
		
		btnAdiEstAnteriores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Capturamos la seleccion
				int selection = listEstados1.getSelectedIndex();
				if (selection!=-1) 
				{
				     //Obtenemos el objeto estado seleccionado 
					 Estado p = estListModel.getEstado(selection);
					 //Creamos el estado anterior a insertar
				     EstadoAnterior estAntAdi = new EstadoAnterior(idEstado, p.getIdestado(), p.getDescripcionCorta());
				     PedidoCtrl pedCtrl = new PedidoCtrl();
				     //Realizamos la insercion en base de datos
				     boolean resIns = pedCtrl.insertarEstadoAnterior(estAntAdi);
				     if(resIns)
				     {
				    	 //Si la inserción en base de datos es correcta se realiza el movimiento en los JList
				    	 modeloListaAnteriores.addEstadoAnterior(estAntAdi);
				    	 estListModel.eliminarEstado(selection);
				     }
				     
				     
				 }
				else
				{
					JOptionPane.showMessageDialog(null, "Debe seleccionar un Estado de la lista de Estados Anteriores Disponibles ", "Error en Selección", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
			}
		});
		initComboTipoPedido();
	}
	
	public void llenarEstadosAnteriores()
	{
		PedidoCtrl pedCtrl = new PedidoCtrl();
		ArrayList<EstadoAnterior> estadosAnteriores = pedCtrl.obtenerEstadosAnteriores(idEstado);
		for(int i = 0; i < estadosAnteriores.size(); i++)
		{
			EstadoAnterior estAntTemp = estadosAnteriores.get(i);
			modeloListaAnteriores.addEstadoAnterior(estAntTemp);
		}
	}
	
	public void llenarEstadosAnterioresNoAsignados()
	{
		PedidoCtrl pedCtrl = new PedidoCtrl();
		ArrayList<Estado> estadosAnterioresNo = pedCtrl.obtenerEstadosAnterioresFaltantes(idEstado);
		for(int i = 0; i < estadosAnterioresNo.size(); i++)
		{
			Estado estAntNoTemp = estadosAnterioresNo.get(i);
			estListModel.addEstado(estAntNoTemp);
		}
	}
	
	public void llenarEstadosPosteriores()
	{
		PedidoCtrl pedCtrl = new PedidoCtrl();
		ArrayList<EstadoPosterior> estadosPosteriores = pedCtrl.obtenerEstadosPosteriores(idEstado);
		
		for(int i = 0; i < estadosPosteriores.size(); i++)
		{
			EstadoPosterior estPosTemp = estadosPosteriores.get(i);
			modeloListaPosteriores.addEstadoPosterior(estPosTemp);
		}
	}
	
	public void llenarEstadosPosterioresNoAsignados()
	{
		PedidoCtrl pedCtrl = new PedidoCtrl();
		ArrayList<Estado> estadosPosterioresNo = pedCtrl.obtenerEstadosPosterioresFaltantes(idEstado);
		
		for(int i = 0; i < estadosPosterioresNo.size(); i++)
		{
			Estado estPosNoTemp = estadosPosterioresNo.get(i);
			estListModel2.addEstado(estPosNoTemp);
		}
	}
	
	public DefaultTableModel pintarEstado()
	{
		Object[] columnsName = new Object[5];
        
        columnsName[0] = "Id Estado";
        columnsName[1] = "Descripcion";
        columnsName[2] = "Tipo Pedido";
        columnsName[3] = "Id Tipo Pedido";
        columnsName[4] = "Descripción Corta";
        PedidoCtrl pedCtrl = new  PedidoCtrl();
        ArrayList<Object> estados =  pedCtrl.obtenerEstado();
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < estados.size();y++)
		{
			String[] fila =(String[]) estados.get(y);
			modelo.addRow(fila);
		}
		return(modelo);
		
	}
	
	public boolean validarDatos()
	{
		String descripcion = txtDescripcion.getText();
		String descripcionCorta = txtDescCorta.getText();
		if(descripcion == "")
		{
			JOptionPane.showMessageDialog(null, "Valor del campo DESCRIPCION es necesario", "Falta Información", JOptionPane.ERROR_MESSAGE);
			return(false);
		}
		if(descripcionCorta == "")
		{
			JOptionPane.showMessageDialog(null, "Valor del campo Descripcion Corta es necesario", "Falta Información", JOptionPane.ERROR_MESSAGE);
			return(false);
		}
		
		return(true);
	}
	
	public void limpiarElementosListas()
	{
		
		for(int i = 0; i < estListModel.getSize(); i++)
		{
			estListModel.eliminarEstado(i);
			i--;
		}
		
		for(int i = 0; i < estListModel2.getSize(); i++)
		{
			estListModel2.eliminarEstado(i);
			i--;
		}
		
		for(int i = 0; i < modeloListaPosteriores.getSize(); i++)
		{
			modeloListaPosteriores.eliminarEstadoPosterior(i);
			i--;
		}
		
		for(int i = 0; i < modeloListaAnteriores.getSize(); i++)
		{
			modeloListaAnteriores.eliminarEstadoAnterior(i);
			i--;
		}
	}
	
	public void initComboTipoPedido()
	{
		PedidoCtrl pedCtrl = new PedidoCtrl();
		ArrayList<TipoPedido> tiposPedido = pedCtrl.obtenerTiposPedidoNat();
		for(int i = 0; i<tiposPedido.size();i++)
		{
			TipoPedido fila = (TipoPedido)  tiposPedido.get(i);
			comboTipoPedido.addItem(fila);
		}
	}
}
