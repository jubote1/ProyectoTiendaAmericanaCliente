package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import JTable.CellRenderNormal;
import JTable.CellRenderTransaccional;
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
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import java.awt.Font;

public class VentProEstado extends JDialog{

	private JPanel panelGeneral;
	private JTextField txtIdEstado;
	private JTextField txtDescripcion;
	private JTextField txtDescCorta;
	private JTable jTableEstados;
	private int idEstado;
	private JLabel lblColorEstado;
	JList listEstAnteriores;
	JList listEstados1;
	JList listEstados2;
	JList listEstPosteriores;
	EstadoListModel estListModel = new EstadoListModel();
	EstadoListModel estListModel2 = new EstadoListModel();
	EstadoPosteriorListModel modeloListaPosteriores = new EstadoPosteriorListModel();
	EstadoAnteriorListModel modeloListaAnteriores = new EstadoAnteriorListModel();
	JComboBox comboTipoPedido;
	JCheckBox chckbxEstadoInicial;
	JCheckBox chckbxEstadoFinal;
	JCheckBox chckbxReqImpresion;
	JCheckBox chckbxEnRutaDomicilio;
	JCheckBox chckbxEntregaDomicilio;
	int colorr;
	int colorg;
	int colorb;
	private JTextField txtRuta;
	private byte[] icono;
	private JLabel lblImagen;
	private PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentProEstado frame = new VentProEstado(null, false);
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
	public VentProEstado(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("MAESTRO DE ESTADOS");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0,0, 903, 617);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 903, 617);
		panelGeneral = new JPanel();
		panelGeneral.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelGeneral);
		panelGeneral.setLayout(null);
		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
		JLabel lblIdEstado = new JLabel("Id Estado");
		lblIdEstado.setBounds(90, 27, 109, 14);
		panelGeneral.add(lblIdEstado);
		
		txtIdEstado = new JTextField();
		txtIdEstado.setEditable(false);
		txtIdEstado.setBounds(220, 24, 178, 20);
		panelGeneral.add(txtIdEstado);
		txtIdEstado.setColumns(10);
		
		JLabel lblDescripcionEstado = new JLabel("Descripcion Estado");
		lblDescripcionEstado.setBounds(90, 66, 146, 14);
		panelGeneral.add(lblDescripcionEstado);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(220, 63, 178, 20);
		panelGeneral.add(txtDescripcion);
		txtDescripcion.setColumns(10);
		
		JLabel lblDescripcionCorta = new JLabel("Descripcion Corta");
		lblDescripcionCorta.setBounds(90, 102, 146, 14);
		panelGeneral.add(lblDescripcionCorta);
		
		txtDescCorta = new JTextField();
		txtDescCorta.setBounds(220, 102, 178, 20);
		panelGeneral.add(txtDescCorta);
		txtDescCorta.setColumns(10);
		
		JPanel panelTable = new JPanel();
		panelTable.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panelTable.setBounds(10, 357, 854, 207);
		panelGeneral.add(panelTable);
		panelTable.setLayout(null);
		
		//Agregamos el JColorChooser
		//final JColorChooser selColorEstado = new JColorChooser();
		
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
				boolean estFinal = false;
				boolean estInicial = false;
				boolean impresion = false;
				boolean rutaDomicilio = false;
				boolean entregaDomicilio = false;
				if (idEstado == 0)
				{
					estInicial = chckbxEstadoInicial.isSelected();
					estFinal = chckbxEstadoFinal.isSelected();
					impresion = chckbxReqImpresion.isSelected();
					descripcion = txtDescripcion.getText();
					descripcionCorta = txtDescCorta.getText();
					rutaDomicilio = chckbxEnRutaDomicilio.isSelected();
					entregaDomicilio = chckbxEntregaDomicilio.isSelected();
					capturarColor();
					TipoPedido tipPedSel = (TipoPedido) comboTipoPedido.getSelectedItem();
					Estado estInsertar = new Estado(0,descripcion, descripcionCorta, tipPedSel.getIdTipoPedido(),tipPedSel.getDescripcion(), estInicial, estFinal, colorr, colorg, colorb, impresion,rutaDomicilio, entregaDomicilio);
					estInsertar.setImagen(icono);
					pedCtrl.insertarEstado(estInsertar);
					DefaultTableModel modelo = pintarEstado();
					jTableEstados.setModel(modelo);
					setCellRender(jTableEstados);
					limpiarPantalla();
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
				pedCtrl.eliminarEstado(idEstadoEl);
				DefaultTableModel modelo = pintarEstado();
				jTableEstados.setModel(modelo);
				setCellRender(jTableEstados);
				txtIdEstado.setText("");
				txtDescripcion.setText("");
				txtDescCorta.setText("");
				chckbxEstadoInicial.setSelected(false);
				chckbxEstadoFinal.setSelected(false);
				idEstado = 0;
			}
		});
		
		JButton btnGrabarEdicion = new JButton("Grabar Edici\u00F3n");
		btnGrabarEdicion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean validar = validarDatos();
				boolean estInicial;
				boolean estFinal;
				boolean impresion;
				boolean rutaDomicilio;
				boolean entregaDomicilio;
				if (validar)
				{
					estInicial = chckbxEstadoInicial.isSelected();
					estFinal = chckbxEstadoFinal.isSelected();
					impresion = chckbxReqImpresion.isSelected();
					rutaDomicilio = chckbxEnRutaDomicilio.isSelected();
					entregaDomicilio = chckbxEntregaDomicilio.isSelected();
					String descripcion = txtDescripcion.getText();
					String descripcionCorta = txtDescCorta.getText();
					capturarColor();
					TipoPedido tipPedSel = (TipoPedido) comboTipoPedido.getSelectedItem();
					Estado estadoEditado = new Estado(idEstado, descripcion, descripcionCorta,tipPedSel.getIdTipoPedido(),tipPedSel.getDescripcion(), estInicial, estFinal, colorr, colorg, colorb, impresion,rutaDomicilio, entregaDomicilio);
					estadoEditado.setImagen(icono);
					boolean respuesta = pedCtrl.editarEstado(estadoEditado);
					if(respuesta)
					{
						JOptionPane.showMessageDialog(null, "Se ha editado correctamente el registro " , "Confirmación Edición", JOptionPane.OK_OPTION);
						DefaultTableModel modelo = pintarEstado();
						setCellRender(jTableEstados);
						txtDescripcion.setText("");
						txtDescCorta.setText("");
						txtIdEstado.setText("");
						btnEliminar.setEnabled(true);
						btnInsertar.setEnabled(true);
						btnGrabarEdicion.setEnabled(false);
						idEstado = 0;
						jTableEstados.setModel(modelo);
						limpiarElementosListas();
						chckbxEstadoInicial.setSelected(false);
						chckbxEstadoFinal.setSelected(false);
						chckbxReqImpresion.setSelected(false);;
						chckbxEnRutaDomicilio.setSelected(false);
						chckbxEntregaDomicilio.setSelected(false);
						limpiarPantalla();
					}
				}
			}
		});
		btnGrabarEdicion.setBounds(522, 160, 127, 23);
		panelTable.add(btnGrabarEdicion);
		
		btnEliminar.setBounds(194, 160, 127, 23);
		panelTable.add(btnEliminar);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarElementosListas();
				limpiarPantalla();
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
				Estado estadoEditar = pedCtrl.obtenerEstado(idEstadoEdi);
				colorr= estadoEditar.getColorr();
				colorg= estadoEditar.getColorg();
				colorb= estadoEditar.getColorb();
				fijarColor();
				//Obtenemos el idProducto y con este retornamos el valor recuperarmos el valor para editar.
				txtDescripcion.setText(estadoEditar.getDescripcion());
				txtDescCorta.setText(estadoEditar.getDescripcionCorta());
				try
				{
					BufferedImage image = null;
					InputStream in = new ByteArrayInputStream(estadoEditar.getImagen());
					image = ImageIO.read(in);
					ImageIcon imgi = new ImageIcon(image.getScaledInstance(60, 60, 0));
					lblImagen.setIcon(imgi);
					icono = estadoEditar.getImagen();
				}catch(Exception exImagen)
				{
					lblImagen.setText("NO HAY IMAGEN");
				}
				if(estadoEditar.isEstadoInicial())
				{
					chckbxEstadoInicial.setSelected(true);
				}
				if(estadoEditar.isEstadoFinal())
				{
					chckbxEstadoFinal.setSelected(true);
				}
				if(estadoEditar.isImpresion())
				{
					chckbxReqImpresion.setSelected(true);
				}
				if(estadoEditar.isRutaDomicilio())
				{
					chckbxEnRutaDomicilio.setSelected(true);
				}
				if(estadoEditar.isEntregaDomicilio())
				{
					chckbxEntregaDomicilio.setSelected(true);
				}
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
		btnEditar.setBounds(344, 160, 127, 23);
		panelTable.add(btnEditar);
		
		JLabel lblEstadosAnteriores = new JLabel("Estados Anteriores");
		lblEstadosAnteriores.setBounds(39, 188, 97, 14);
		panelGeneral.add(lblEstadosAnteriores);
		
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
				     Estado estAdi = new Estado(p.getIdEstado(),p.getDescEstadoAnterior(),p.getDescEstadoAnterior(), 0,"", false, false, 0,0,0, false, false, false);
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
				     EstadoPosterior estPosAdi = new EstadoPosterior(idEstado, p.getIdestado(), p.getDescripcion(), false);
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
				     Estado estAdi = new Estado(p.getIdEstado(),p.getDescEstadoPosterior(),p.getDescEstadoPosterior(),0,"", false, false,0,0,0, false,false,false);
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
		setCellRender(jTableEstados);
		
		JButton btnSalir = new JButton("SALIR");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSalir.setBounds(676, 160, 145, 23);
		panelTable.add(btnSalir);
		
		JLabel lblTipoPedido = new JLabel("Tipo Pedido");
		lblTipoPedido.setBounds(90, 139, 109, 14);
		panelGeneral.add(lblTipoPedido);
		
		comboTipoPedido = new JComboBox();
		comboTipoPedido.setBounds(220, 136, 178, 20);
		panelGeneral.add(comboTipoPedido);
		
		chckbxEstadoInicial = new JCheckBox("Estado Inicial");
		chckbxEstadoInicial.setBounds(589, 11, 97, 23);
		panelGeneral.add(chckbxEstadoInicial);
		
		chckbxEstadoFinal = new JCheckBox("Estado Final");
		chckbxEstadoFinal.setBounds(589, 38, 97, 23);
		panelGeneral.add(chckbxEstadoFinal);
		
		chckbxReqImpresion = new JCheckBox("Requiere Impresi\u00F3n");
		chckbxReqImpresion.setBounds(589, 64, 124, 23);
		panelGeneral.add(chckbxReqImpresion);
		
		chckbxEnRutaDomicilio = new JCheckBox("En ruta Domicilio");
		chckbxEnRutaDomicilio.setBounds(717, 11, 124, 23);
		panelGeneral.add(chckbxEnRutaDomicilio);
		
		chckbxEntregaDomicilio = new JCheckBox("Entrega Domicilio");
		chckbxEntregaDomicilio.setBounds(717, 38, 124, 23);
		panelGeneral.add(chckbxEntregaDomicilio);
		
		lblColorEstado = new JLabel("Color Estado");
		lblColorEstado.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblColorEstado.setBounds(750, 142, 114, 14);
		panelGeneral.add(lblColorEstado);
		
		
		JButton btnColorEstado = new JButton("Color Estado");
		btnColorEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Color nuevoColor = lblColorEstado.getForeground();
				final JColorChooser selectorColor = new JColorChooser(nuevoColor);
				
				ActionListener escuchadorOk = new ActionListener()
						{
							@Override
							public void actionPerformed(ActionEvent e)
							{
								Color colorSeleccionado = selectorColor.getColor();
								if(colorSeleccionado.equals(lblColorEstado.getForeground()))
								{
									JOptionPane.showMessageDialog(selectorColor, "Este color ya está establecido como Color");
								}
								else
								{
									lblColorEstado.setForeground(colorSeleccionado);
								}
							}
						};
						
						ActionListener escuchadorCancel = new ActionListener()
						{
							@Override
							public void actionPerformed(ActionEvent e)
							{
								lblColorEstado.setForeground(Color.RED);
							}
						};
						
						final JDialog cuadroDialogo = JColorChooser.createDialog(panelGeneral, "Seleccione Color para el Estado y Tipo de Pedido", true, selectorColor, escuchadorOk, escuchadorCancel);
						cuadroDialogo.setVisible(true);
				
			}
		});
		btnColorEstado.setBounds(751, 108, 124, 23);
		panelGeneral.add(btnColorEstado);
		
		JScrollPane scrPanelEstAntDis = new JScrollPane();
		scrPanelEstAntDis.setBounds(296, 213, 146, 114);
		panelGeneral.add(scrPanelEstAntDis);
		
		listEstados1 = new JList();
		scrPanelEstAntDis.setViewportView(listEstados1);
		listEstados1.setBorder(new LineBorder(new Color(0, 0, 0)));
		listEstados1.setModel(estListModel);
		
		JScrollPane scrPanelEstAnt = new JScrollPane();
		scrPanelEstAnt.setBounds(10, 212, 162, 114);
		panelGeneral.add(scrPanelEstAnt);
		
		
		
		listEstAnteriores = new JList();
		scrPanelEstAnt.setViewportView(listEstAnteriores);
		listEstAnteriores.setBorder(new LineBorder(new Color(0, 0, 0)));
		listEstAnteriores.setModel(modeloListaAnteriores);
		
		JScrollPane scrPanelEstPos = new JScrollPane();
		scrPanelEstPos.setBounds(483, 213, 139, 114);
		panelGeneral.add(scrPanelEstPos);
		
		listEstPosteriores = new JList();
		scrPanelEstPos.setViewportView(listEstPosteriores);
		listEstPosteriores.setBorder(new LineBorder(new Color(0, 0, 0)));
		listEstPosteriores.setModel(modeloListaPosteriores);
		
		JScrollPane scrPanelEstPosDis = new JScrollPane();
		scrPanelEstPosDis.setBounds(738, 213, 149, 114);
		panelGeneral.add(scrPanelEstPosDis);
		
		listEstados2 = new JList();
		scrPanelEstPosDis.setViewportView(listEstados2);
		listEstados2.setBorder(new LineBorder(new Color(0, 0, 0)));
		listEstados2.setModel(estListModel2);
		
		lblImagen = new JLabel("");
		lblImagen.setBounds(467, 11, 103, 83);
		panelGeneral.add(lblImagen);
		
		JButton btnCargarImagen = new JButton("Cargar Imagen");
		btnCargarImagen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser j = new JFileChooser();
		        FileNameExtensionFilter fil = new FileNameExtensionFilter("JPG, PNG & GIF","jpg","png","gif");
		        j.setFileFilter(fil);
		        
		        int s = j.showOpenDialog(null);
		        if(s == JFileChooser.APPROVE_OPTION){
		            String ruta = j.getSelectedFile().getAbsolutePath();
		            txtRuta.setText(ruta);
		            File rutaArchivo = new File(txtRuta.getText());
		            try{
		                icono = new byte[(int) rutaArchivo.length()];
		                InputStream input = new FileInputStream(ruta);
		                input.read(icono);
		                BufferedImage image = null;
		                InputStream in = new ByteArrayInputStream(icono);
						image = ImageIO.read(in);
						ImageIcon imgi = new ImageIcon(image.getScaledInstance(60, 60, 0));
						lblImagen.setIcon(imgi);
		            }catch(Exception ex){
		            	lblImagen.setText("NO IMAGEN");
		            }
		        }
			}
		});
		btnCargarImagen.setBounds(467, 135, 104, 23);
		panelGeneral.add(btnCargarImagen);
		
		txtRuta = new JTextField();
		txtRuta.setEditable(false);
		txtRuta.setBounds(427, 109, 214, 20);
		panelGeneral.add(txtRuta);
		txtRuta.setColumns(10);
		
			
		
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
				     EstadoAnterior estAntAdi = new EstadoAnterior(idEstado, p.getIdestado(), p.getDescripcion());
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
		ArrayList<EstadoAnterior> estadosAnteriores = pedCtrl.obtenerEstadosAnteriores(idEstado);
		for(int i = 0; i < estadosAnteriores.size(); i++)
		{
			EstadoAnterior estAntTemp = estadosAnteriores.get(i);
			modeloListaAnteriores.addEstadoAnterior(estAntTemp);
		}
	}
	
	public void llenarEstadosAnterioresNoAsignados()
	{
		ArrayList<Estado> estadosAnterioresNo = pedCtrl.obtenerEstadosAnterioresFaltantes(idEstado);
		for(int i = 0; i < estadosAnterioresNo.size(); i++)
		{
			Estado estAntNoTemp = estadosAnterioresNo.get(i);
			estListModel.addEstado(estAntNoTemp);
		}
	}
	
	public void llenarEstadosPosteriores()
	{
		ArrayList<EstadoPosterior> estadosPosteriores = pedCtrl.obtenerEstadosPosteriores(idEstado);
		
		for(int i = 0; i < estadosPosteriores.size(); i++)
		{
			EstadoPosterior estPosTemp = estadosPosteriores.get(i);
			modeloListaPosteriores.addEstadoPosterior(estPosTemp);
		}
	}
	
	public void llenarEstadosPosterioresNoAsignados()
	{
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
		boolean estInicial = chckbxEstadoInicial.isSelected();
		boolean estFinal = chckbxEstadoFinal.isSelected();
		if(estInicial & estFinal)
		{
			JOptionPane.showMessageDialog(null, "Un estado no debería ser inicial y y final al mismo tiempo", "Información Incorrecto", JOptionPane.ERROR_MESSAGE);
			return(false);
		}
		TipoPedido tipPedSel = (TipoPedido) comboTipoPedido.getSelectedItem();
		if(estInicial)
		{
			
			boolean hayEstadoInicial = pedCtrl.tieneEstadoInicial(tipPedSel.getIdTipoPedido(), idEstado);
			if(hayEstadoInicial)
			{
				JOptionPane.showMessageDialog(null, "Ya hay un estado inicial para el tipo de pedido, debe deschequear la opción de estado inicial", "Información Incorrecto", JOptionPane.ERROR_MESSAGE);
				return(false);
			}
		}
		
		if(estFinal)
		{
			
			boolean hayEstadoFinal = pedCtrl.tieneEstadoFinal(tipPedSel.getIdTipoPedido(), idEstado);
			if(hayEstadoFinal)
			{
				JOptionPane.showMessageDialog(null, "Ya hay un estado final para el tipo de pedido, debe deschequear la opción de estado inicial", "Información Incorrecto", JOptionPane.ERROR_MESSAGE);
				return(false);
			}
		}
		
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
		ArrayList<TipoPedido> tiposPedido = pedCtrl.obtenerTiposPedidoNat();
		for(int i = 0; i<tiposPedido.size();i++)
		{
			TipoPedido fila = (TipoPedido)  tiposPedido.get(i);
			comboTipoPedido.addItem(fila);
		}
	}
	
	public void capturarColor()
	{
		Color colorSel = lblColorEstado.getForeground();
		colorr = colorSel.getRed();
		colorg = colorSel.getGreen();
		colorb = colorSel.getBlue();
	}
	
	public void fijarColor()
	{
		Color colorSel = new Color(colorr, colorg, colorb);
		lblColorEstado.setForeground(colorSel);
		
	}
	
	public void setCellRender(JTable table) {
        Enumeration<TableColumn> en = table.getColumnModel().getColumns();
        while (en.hasMoreElements()) {
            TableColumn tc = en.nextElement();
            tc.setCellRenderer(new CellRenderNormal());
        }
    }
	
	public void limpiarPantalla()
	{
		txtDescripcion.setText("");
		txtDescCorta.setText("");
		txtIdEstado.setText("");
		chckbxEstadoInicial.setSelected(false);
		chckbxEstadoFinal.setSelected(false);
		chckbxReqImpresion.setSelected(false);;
		chckbxEnRutaDomicilio.setSelected(false);
		chckbxEntregaDomicilio.setSelected(false);
		lblImagen.setText("");
		lblImagen.setIcon(null);
	}
}
