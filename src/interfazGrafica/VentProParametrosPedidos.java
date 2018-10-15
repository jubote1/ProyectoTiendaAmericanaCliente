package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import capaControlador.ParametrosProductoCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.Impuesto;
import capaModelo.TipoPedido;

import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class VentProParametrosPedidos extends JFrame {

	private JPanel ContenedorPrincipal;
	private JTextField txtIdTipoPedido;
	private JTextField txtDescripcion;
	private JTable tableTipoPedido;
	int idTipoPedido;
	JCheckBox chValorDefecto;
	JCheckBox chEsDomicilio;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentProParametrosPedidos frame = new VentProParametrosPedidos();
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
	public VentProParametrosPedidos() {
		setTitle("PARAMETROS PEDIDOS");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0,0,  557, 482);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2),  557, 482);
		ContenedorPrincipal = new JPanel();
		ContenedorPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(ContenedorPrincipal);
		ContenedorPrincipal.setLayout(null);
		
		JTabbedPane tabParametros = new JTabbedPane(JTabbedPane.TOP);
		tabParametros.setBounds(41, 47, 464, 386);
		ContenedorPrincipal.add(tabParametros);
		
		JPanel panel = new JPanel();
		tabParametros.addTab("Tipos Pedido", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblIdTipoPedido = new JLabel("Id Tipo Pedido");
		lblIdTipoPedido.setBounds(77, 30, 86, 14);
		panel.add(lblIdTipoPedido);
		
		txtIdTipoPedido = new JTextField();
		txtIdTipoPedido.setBounds(191, 27, 139, 20);
		panel.add(txtIdTipoPedido);
		txtIdTipoPedido.setColumns(10);
		txtIdTipoPedido.setEnabled(false);
		
		JLabel lblDescripcin = new JLabel("Descripci\u00F3n ");
		lblDescripcin.setBounds(77, 73, 86, 14);
		panel.add(lblDescripcin);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(191, 70, 139, 20);
		panel.add(txtDescripcion);
		txtDescripcion.setColumns(10);
		
		JScrollPane scrollPaneTipoPedido = new JScrollPane();
		scrollPaneTipoPedido.setBounds(66, 194, 311, 89);
		panel.add(scrollPaneTipoPedido);
		
		tableTipoPedido = new JTable();
		scrollPaneTipoPedido.setViewportView(tableTipoPedido);
		
		chValorDefecto = new JCheckBox("");
		chValorDefecto.setBounds(200, 115, 97, 23);
		panel.add(chValorDefecto);
		
		chEsDomicilio = new JCheckBox("");
		chEsDomicilio.setBounds(200, 151, 109, 23);
		panel.add(chEsDomicilio);
		
		JButton btnInsertarTipoPedido = new JButton("Insertar");
		btnInsertarTipoPedido.setBounds(12, 306, 89, 23);
		btnInsertarTipoPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Se pulso botón para adicionar un nuevo Menú Agrupador
				if(validarDatosTipoPedido())
				{
					String descripcion = txtDescripcion.getText();
					boolean valDefecto = false;
					boolean esDomicilio = false;
					if(chValorDefecto.isSelected())
					{
						valDefecto = true;
					}
					if(chEsDomicilio.isSelected())
					{
						esDomicilio = true;
					}
					TipoPedido tipPedido = new TipoPedido(0, descripcion, valDefecto,"", esDomicilio);
					PedidoCtrl pedCtrl = new PedidoCtrl();
					int idTipInsertado = pedCtrl.insertarTipoPedido(tipPedido);
					if (idTipInsertado > 0)
					{
						DefaultTableModel modelo = pintarTipoPedido();
						tableTipoPedido.setModel(modelo);
						//Limpiamos el contenido de los campos
						txtDescripcion.setText("");
						chValorDefecto.setSelected(false);
					}
				}
				
			
			}
		});
		panel.add(btnInsertarTipoPedido);
		
		JButton btnEliminarTipoPedido = new JButton("Eliminar");
		btnEliminarTipoPedido.setBounds(122, 306, 89, 23);
		panel.add(btnEliminarTipoPedido);
		btnEliminarTipoPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int filaSeleccionada = tableTipoPedido.getSelectedRow();
				//Hacemos la validación para decidir si se elimina o no
				String TipPedidoEliminar = (String) tableTipoPedido.getValueAt(filaSeleccionada, 1);
				int idtTipoEliminar = Integer.parseInt((String)tableTipoPedido.getValueAt(filaSeleccionada, 0));
				JOptionPane.showMessageDialog(null, "Esta seguro que se desea eliminar el Tipo Pedido " +  TipPedidoEliminar , "Eliminacion Tipo Pedido ", JOptionPane.YES_NO_OPTION);
				PedidoCtrl pedEliminar = new PedidoCtrl();
				pedEliminar.eliminarTipoPedido(idtTipoEliminar);
				DefaultTableModel modelo = pintarTipoPedido();
				tableTipoPedido.setModel(modelo);
			}
		});
		
		JButton btnGrabarEdicionTipoPedido = new JButton("Grabar Edicion");
		btnGrabarEdicionTipoPedido.setBounds(320, 306, 129, 23);
		panel.add(btnGrabarEdicionTipoPedido);
		
		
		
		JLabel lblValorPorDefecto = new JLabel("Valor por Defecto");
		lblValorPorDefecto.setBounds(77, 119, 99, 14);
		panel.add(lblValorPorDefecto);
		
		JButton btnEditarTipoPedido = new JButton("Editar");
		btnEditarTipoPedido.setBounds(221, 306, 89, 23);
		panel.add(btnEditarTipoPedido);
		btnEditarTipoPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = tableTipoPedido.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar algún Tipo Pedido para editar " , "No ha Seleccionado para edición ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				filaSeleccionada = tableTipoPedido.getSelectedRow();
				int idTipoEditar = Integer.parseInt((String)tableTipoPedido.getValueAt(filaSeleccionada, 0));
				txtIdTipoPedido.setText((String)tableTipoPedido.getValueAt(filaSeleccionada, 0));
				PedidoCtrl pedCtrl = new PedidoCtrl();
				TipoPedido tipPedEditar = pedCtrl.obtenerTipoPedido(idTipoEditar);
				txtDescripcion.setText(tipPedEditar.getDescripcion());
				if(tipPedEditar.isValorDefecto())
				{
					chValorDefecto.setSelected(true);
				}
				else
				{
					chValorDefecto.setSelected(false);
				}
				if(tipPedEditar.isEsDomicilio())
				{
					chEsDomicilio.setSelected(true);
				}
				else
				{
					chEsDomicilio.setSelected(false);
				}
				btnEliminarTipoPedido.setEnabled(false);
				btnInsertarTipoPedido.setEnabled(false);
				btnGrabarEdicionTipoPedido.setEnabled(true);
				idTipoPedido = idTipoEditar;
			}
		});
		
		
		btnGrabarEdicionTipoPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean validar = validarDatosTipoPedido();
				if (validar)
				{
					boolean valDefecto = chValorDefecto.isSelected();
					boolean valEsDomicilio = chEsDomicilio.isSelected();
					TipoPedido tipoPedidoEditado = new TipoPedido(idTipoPedido,txtDescripcion.getText(),valDefecto,"", valEsDomicilio); 
					PedidoCtrl pedCtrl = new PedidoCtrl();
					boolean respuesta = pedCtrl.EditarTipoPedido(tipoPedidoEditado);
					if (respuesta)
					{
						JOptionPane.showMessageDialog(null, "Se ha editado correctamente el registro " , "Confirmación Edición", JOptionPane.OK_OPTION);
						DefaultTableModel modelo = pintarTipoPedido();
						tableTipoPedido.setModel(modelo);
						txtDescripcion.setText("");
						txtIdTipoPedido.setText("");
						chValorDefecto.setSelected(false);;
						btnEliminarTipoPedido.setEnabled(true);
						btnInsertarTipoPedido.setEnabled(true);
						btnGrabarEdicionTipoPedido.setEnabled(false);
						idTipoPedido = 0;
					}
				}
				
			}
		});
		btnGrabarEdicionTipoPedido.setEnabled(false);
		DefaultTableModel modelo = pintarTipoPedido();
		tableTipoPedido.setModel(modelo);
		
		
		
		JLabel lblEsDomicilio = new JLabel("Es Domicilio");
		lblEsDomicilio.setBounds(77, 151, 99, 14);
		panel.add(lblEsDomicilio);
	}
	
	
	public boolean validarDatosTipoPedido()
	{
		String descripcion = txtDescripcion.getText();
		
		if(descripcion == "")
		{
			JOptionPane.showMessageDialog(null, "Valor del campo DESCRIPCIÓN es necesario", "Falta Información", JOptionPane.ERROR_MESSAGE);
			return(false);
		}
				
		return(true);
	}
	
	public DefaultTableModel pintarTipoPedido()
	{
		Object[] columnsName = new Object[3];
        
        columnsName[0] = "Id Tipo Pedido ";
        columnsName[1] = "Descripcion Tipo Pedido";
        columnsName[2] = "Valor por Defecto";
        PedidoCtrl par = new PedidoCtrl();
		ArrayList<Object> tiposPedido = par.obtenerTiposPedido();
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < tiposPedido.size();y++)
		{
			String[] fila =(String[]) tiposPedido.get(y);
			System.out.println(fila);
			modelo.addRow(fila);
		}
		return(modelo);
		
	}
}
