package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import capaControlador.InventarioCtrl;
import capaControlador.ParametrosDireccionCtrl;
import capaControlador.ParametrosProductoCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.FechaSistema;
import capaModelo.ItemInventario;
import capaModelo.Municipio;
import capaModelo.PedidoEspecial;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

public class VentInvPedidoEspecial extends JDialog {

	private JPanel contentPanePrincipal;
	private JTextField txtCantidad;
	private JTable tableSolPedido;
	private JComboBox comboBoxItemInv;
	private String fechaSistema;
	private Date datFechaSistema;
	private JDateChooser fechaSolPedido;
	private JDateChooser fechaConsultaSolicitudes;
	InventarioCtrl invCtrl = new InventarioCtrl(PrincipalLogueo.habilitaAuditoria);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentInvPedidoEspecial frame = new VentInvPedidoEspecial(null, true);
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
	public VentInvPedidoEspecial(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("PEDIDOS ESPECIALES");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0,0, 800, 476);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 800, 476);
		contentPanePrincipal = new JPanel();
		contentPanePrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanePrincipal);
		contentPanePrincipal.setLayout(null);
		
		fechaSolPedido = new JDateChooser();
		fechaSolPedido.setBounds(314, 49, 142, 20);
		contentPanePrincipal.add(fechaSolPedido);
		
		JLabel lblFechaSolicitud = new JLabel("Fecha Solicitud");
		lblFechaSolicitud.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFechaSolicitud.setBounds(79, 55, 136, 14);
		contentPanePrincipal.add(lblFechaSolicitud);
		
		JLabel lblItemInventario = new JLabel("Item Inventario");
		lblItemInventario.setBounds(79, 97, 108, 14);
		contentPanePrincipal.add(lblItemInventario);
		
		comboBoxItemInv = new JComboBox();
		comboBoxItemInv.setBounds(314, 94, 171, 20);
		contentPanePrincipal.add(comboBoxItemInv);
		
		JLabel lblCantidad = new JLabel("Cantidad");
		lblCantidad.setBounds(79, 137, 125, 14);
		contentPanePrincipal.add(lblCantidad);
		
		txtCantidad = new JTextField();
		txtCantidad.setBounds(314, 137, 171, 20);
		contentPanePrincipal.add(txtCantidad);
		txtCantidad.setColumns(10);
		
		JLabel lblUnidad = new JLabel("-");
		lblUnidad.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblUnidad.setBounds(588, 141, 125, 14);
		contentPanePrincipal.add(lblUnidad);
		
		JButton btnRealizarSolicitud = new JButton("Realizar Solicitud");
		btnRealizarSolicitud.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(validarDatos())
				{
					String formato = "yyyy/MM/dd";
					SimpleDateFormat sdf = new SimpleDateFormat(formato);
					Date selDateConsulta = fechaSolPedido.getDate();
					String fechaSol =  sdf.format(selDateConsulta);
					double cantidad = Double.parseDouble(txtCantidad.getText());
					ItemInventario itemSel = (ItemInventario)comboBoxItemInv.getSelectedItem();
					int idItem = itemSel.getIdItem();
					PedidoEspecial pedEsp = new PedidoEspecial(0,idItem,cantidad,fechaSol);
					int idPedEspIns = invCtrl.insertarPedidoEspecial(pedEsp);
					if(idPedEspIns > 0)
					{
						JOptionPane.showMessageDialog(null, "Se he insertado el pedido especial " + idPedEspIns , "Ingreso Exitoso", JOptionPane.INFORMATION_MESSAGE);
						txtCantidad.setText("");
						consultarPedidosEspeciales();
					}if(idPedEspIns == -1)
					{
						JOptionPane.showMessageDialog(null, "Se he actualizado el pedido especial "  , "Actualizaci�n Exitosa", JOptionPane.INFORMATION_MESSAGE);
						txtCantidad.setText("");
						consultarPedidosEspeciales();
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Se presentaron errores al insertar el pedido Especial." , "Error", JOptionPane.ERROR_MESSAGE);
					}
					
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Se presentaron errores en la validaci�n de los datos." , "Validaci�n de Datos", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		});
		btnRealizarSolicitud.setBounds(207, 184, 171, 23);
		contentPanePrincipal.add(btnRealizarSolicitud);
		
		JScrollPane scrollPaneSolicitudes = new JScrollPane();
		scrollPaneSolicitudes.setBounds(142, 276, 507, 128);
		contentPanePrincipal.add(scrollPaneSolicitudes);
		
		tableSolPedido = new JTable();
		scrollPaneSolicitudes.setViewportView(tableSolPedido);
		scrollPaneSolicitudes.setColumnHeaderView(tableSolPedido);
		
		JLabel lblSolicitudesEspecialesDe = new JLabel("SOLICITUDES ESPECIALES DE PEDIDO");
		lblSolicitudesEspecialesDe.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSolicitudesEspecialesDe.setBounds(79, 244, 327, 14);
		contentPanePrincipal.add(lblSolicitudesEspecialesDe);
		
		fechaConsultaSolicitudes = new JDateChooser();
		fechaConsultaSolicitudes.addInputMethodListener(new InputMethodListener() {
			public void caretPositionChanged(InputMethodEvent arg0) {
			}
			public void inputMethodTextChanged(InputMethodEvent arg0) {
				
				System.out.println("Hola");
			}
		});
		fechaConsultaSolicitudes.setBounds(437, 244, 114, 20);
		contentPanePrincipal.add(fechaConsultaSolicitudes);
		
		JButton btnEliminarSolicitud = new JButton("Eliminar Solicitud");
		btnEliminarSolicitud.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int filaSeleccionada = tableSolPedido.getSelectedRow();
				//Hacemos la validaci�n para decidir si se elimina o no
				String itemEliminar = (String) tableSolPedido.getValueAt(filaSeleccionada, 2);
				int idPedEspELi = Integer.parseInt((String)tableSolPedido.getValueAt(filaSeleccionada, 0));
				JOptionPane.showMessageDialog(null, "Esta seguro que se desea eliminar el Pedido Especial " +  itemEliminar , "Eliminacion Pedido Especial ", JOptionPane.YES_NO_OPTION);
				boolean resEli  = invCtrl.eliminarItemInventario(idPedEspELi);
				if(resEli)
				{
					JOptionPane.showMessageDialog(null, "Fue eliminado el Pedido Especial Seleccionado " +  itemEliminar , "Eliminacion Pedido Especial ", JOptionPane.INFORMATION_MESSAGE);
					consultarPedidosEspeciales();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Se present� error al intentar eliminar el Pedido Especial  " +  itemEliminar , "Eliminacion Pedido Especial ", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnEliminarSolicitud.setBounds(323, 415, 150, 23);
		contentPanePrincipal.add(btnEliminarSolicitud);
		PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
		FechaSistema fechSistema = pedCtrl.obtenerFechasSistema();
		String fechTemporal = fechSistema.getFechaApertura();
		fechaSistema = fechTemporal.substring(8,10)+"/"+fechTemporal.substring(5,7)+"/"+fechTemporal.substring(1,4);
		
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
		try {

			datFechaSistema = formatoFecha.parse(fechTemporal);

			} catch (ParseException ex) {

			ex.printStackTrace();

			}
		//Con la fecha sistema llenamos los valores de los campos fecha como campos sugeridos
		fechaSolPedido.setDate(datFechaSistema);
		fechaConsultaSolicitudes.setDate(datFechaSistema);
		
		JLabel lblImagen = new JLabel("");
		lblImagen.setBounds(543, 43, 198, 126);
		ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/LogoPizzaAmericana.png"));
		Image imagen = icon.getImage();
		ImageIcon iconoEscalado = new ImageIcon (imagen.getScaledInstance(198,126,Image.SCALE_SMOOTH));
		lblImagen.setIcon(iconoEscalado);
		contentPanePrincipal.add(lblImagen);
		initComboBoxItemInv();
		String formato = "yyyy/MM/dd";
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		Date selDateConsulta = fechaConsultaSolicitudes.getDate();
		consultarPedidosEspeciales();
		System.out.println(sdf.format(selDateConsulta));
		
		
		
	}
	
	public void initComboBoxItemInv()
	{
		ParametrosProductoCtrl proCtrl = new ParametrosProductoCtrl(PrincipalLogueo.habilitaAuditoria);
		ArrayList<ItemInventario> items = proCtrl.obtenerItemsInventariosObj();
		for(int i = 0; i<items.size();i++)
		{
			ItemInventario fila = (ItemInventario)  items.get(i);
			comboBoxItemInv.addItem(fila);
		}
	}
	
	public boolean validarDatos()
	{
		boolean respuesta = false;
		Date fechaSeleccionada = fechaSolPedido.getDate();
		int res = datFechaSistema.compareTo(fechaSeleccionada);
		if(res > 0)
		{
			JOptionPane.showMessageDialog(null, "La fecha de solicitud no puede ser menor a la fecha del sistema " , "Error en fecha seleccionada ", JOptionPane.ERROR_MESSAGE);
			return respuesta;
		}
		double cantidad;
		try
		{
			cantidad = Double.parseDouble(txtCantidad.getText());
		}
		catch(Exception e)
		{
			return respuesta;
		}
		
		return(true);
	}
	
	public void consultarPedidosEspeciales()
	{
		
		String formato = "yyyy/MM/dd";
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		Date selDateConsulta = fechaConsultaSolicitudes.getDate();
		String fecha = sdf.format(selDateConsulta);
		DefaultTableModel model = pintarProductosEspeciales( fecha);
		tableSolPedido.setModel(model);
	}
	
	public DefaultTableModel pintarProductosEspeciales( String fecha)
	{
		Object[] columnsName = new Object[6];
        
		columnsName[0] = "Id Pedido Especial";
		columnsName[1] = "Id Item";
        columnsName[2] = "Item";
        columnsName[3] = "Cantidad";
        columnsName[4] = "Unidad";
        columnsName[5] = "Fecha Solicitud";
        ArrayList pedidosEspeciales = invCtrl.obtenerPedidosEspeciales(fecha);
        DefaultTableModel modeloPedidos= new DefaultTableModel();
        modeloPedidos .setColumnIdentifiers(columnsName);
		for(int y = 0; y < pedidosEspeciales.size();y++)
		{
			String[] fila =(String[]) pedidosEspeciales.get(y);
			modeloPedidos.addRow(fila);
		}
		return(modeloPedidos);
		
	}
}
