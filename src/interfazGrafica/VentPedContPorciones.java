package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import capaControlador.OperacionesTiendaCtrl;
import capaControlador.ParametrosCtrl;
import capaControlador.ParametrosProductoCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.DetallePedido;
import capaModelo.FechaSistema;
import capaModelo.Parametro;
import capaModelo.PedidoDescuento;
import capaModelo.PorcionesControlDiario;

import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class VentPedContPorciones extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtPorciones;
	private JTextField txtPorcionesGaseosa;
	private JTextField txtPorcionEmpleado;
	private String fechaSistema;
	JButton btnFacturarParaFinalizar;
	JButton btnPorcion;
	JButton btnPorcionGaseosa;
	JButton btnPorcionEmpleado;
	JButton btnDesecho;
	JButton btnDisminuirPorcion;
	JButton btnDisminuirPorcionGaseosa;
	JButton btnDisminuirPorcionEmpleado;
	JButton btnDisminuirDesecho;
	private PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	private OperacionesTiendaCtrl operTiendaCtrl = new OperacionesTiendaCtrl(PrincipalLogueo.habilitaAuditoria);
	private ParametrosCtrl parCtrl = new ParametrosCtrl(PrincipalLogueo.habilitaAuditoria);
	private ParametrosProductoCtrl parProductoCtrl = new ParametrosProductoCtrl(PrincipalLogueo.habilitaAuditoria);
	private PorcionesControlDiario estActual;
	private JTextField txtPorcionDesecho;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentPedContPorciones dialog = new VentPedContPorciones(null, false);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentPedContPorciones(java.awt.Frame frame, boolean modal) {
		super(frame, modal);
		setTitle("CONTROL DE VENTA DE PORCIONES");
		setBounds(100, 100, 911, 429);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		//Fijamos la fecha actual del sistema
		FechaSistema fechasSistema = pedCtrl.obtenerFechasSistema();
		fechaSistema =  fechasSistema.getFechaApertura();
		
		btnPorcion = new JButton("Porci\u00F3n");
		btnPorcion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean respuesta = operTiendaCtrl.aumentarPorcion(fechaSistema);
				if(respuesta)
				{
					int cantidad;
					try
					{
						cantidad = Integer.parseInt(txtPorciones.getText());
					}catch(Exception exc)
					{
						cantidad = 0;
					}
					cantidad = cantidad + 1;
					txtPorciones.setText(Integer.toString(cantidad));
				}
			}
		});
		btnPorcion.setIcon(new ImageIcon(VentPedContPorciones.class.getResource("/icons/Porcion.jpg")));
		btnPorcion.setBounds(23, 26, 195, 112);
		contentPanel.add(btnPorcion);
		
		btnPorcionGaseosa = new JButton("Porci\u00F3n Gaseosa");
		btnPorcionGaseosa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean respuesta = operTiendaCtrl.aumentarPorcionGaseosa(fechaSistema);
				if(respuesta)
				{
					int cantidad;
					try
					{
						cantidad = Integer.parseInt(txtPorcionesGaseosa.getText());
					}catch(Exception exc)
					{
						cantidad = 0;
					}
					cantidad = cantidad + 1;
					txtPorcionesGaseosa.setText(Integer.toString(cantidad));
				}
			}
		});
		btnPorcionGaseosa.setIcon(new ImageIcon(VentPedContPorciones.class.getResource("/icons/PorcionGaseosa.jpg")));
		btnPorcionGaseosa.setBounds(254, 26, 195, 112);
		contentPanel.add(btnPorcionGaseosa);
		
		JLabel lblPorcion = new JLabel("PORCION");
		lblPorcion.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPorcion.setBounds(79, 11, 83, 14);
		contentPanel.add(lblPorcion);
		
		JLabel lblPorcionYGaseosa = new JLabel("PORCION Y GASEOSA");
		lblPorcionYGaseosa.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPorcionYGaseosa.setBounds(254, 11, 195, 14);
		contentPanel.add(lblPorcionYGaseosa);
		
		JLabel lblCantidadPorciones = new JLabel("PORCIONES");
		lblCantidadPorciones.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblCantidadPorciones.setBounds(23, 237, 101, 14);
		contentPanel.add(lblCantidadPorciones);
		
		JLabel lblCantidadPorGaseosa = new JLabel("PORCIONES/GASEOSA");
		lblCantidadPorGaseosa.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCantidadPorGaseosa.setBounds(254, 237, 154, 14);
		contentPanel.add(lblCantidadPorGaseosa);
		
		txtPorciones = new JTextField();
		txtPorciones.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtPorciones.setEditable(false);
		txtPorciones.setBounds(175, 234, 43, 20);
		contentPanel.add(txtPorciones);
		txtPorciones.setColumns(10);
		
		txtPorcionesGaseosa = new JTextField();
		txtPorcionesGaseosa.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtPorcionesGaseosa.setEditable(false);
		txtPorcionesGaseosa.setColumns(10);
		txtPorcionesGaseosa.setBounds(406, 234, 43, 20);
		contentPanel.add(txtPorcionesGaseosa);
		
		btnFacturarParaFinalizar = new JButton("Facturar Para Finalizar D\u00EDa");
		btnFacturarParaFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Window ventanaPadre = SwingUtilities.getWindowAncestor(
                        (Component) e.getSource());
				int opcion = JOptionPane.YES_NO_OPTION; 
			    JOptionPane.showConfirmDialog (null, "Esta seguro de que desea facturar las porciones?","CUIDADO", opcion); 
			    if (opcion == JOptionPane.YES_OPTION) 
			    {
			    	Parametro parametro = parCtrl.obtenerParametro("PRODUCTOPORCION");
					long valNum = 0;
					try
					{
						valNum = (long) parametro.getValorNumerico();
					}catch(Exception exc)
					{
						System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE PRODUCTO PORCIÓN");
						JOptionPane.showMessageDialog(ventanaPadre, "Debe tener parametrizada la variable PRODUCTOPORCION para poder facturar" , "No se tiene variable para poder facturar", JOptionPane.ERROR_MESSAGE);
						return;
					}
					//Asignamos el producto de porciones para con este facturar
					int idProductoPorcion =(int) valNum;
					int cantPorciones;
					int cantDescuento;
					double precioPorcion = parProductoCtrl.obtenerPrecioPilaProducto(idProductoPorcion);
					//Repetimos el proceso para el producto gaseosa
					parametro = parCtrl.obtenerParametro("PRODUCTOGASEOSA");
					valNum = 0;
					try
					{
						valNum = (long) parametro.getValorNumerico();
					}catch(Exception exc)
					{
						System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE PRODUCTO GASEOSA PORCIÓN");
						JOptionPane.showMessageDialog(ventanaPadre, "Debe tener parametrizada la variable PRODUCTOGASEOSA para poder facturar" , "No se tiene variable para poder facturar", JOptionPane.ERROR_MESSAGE);
						return;
					}
					int idProductoGaseosa =(int) valNum;
					int cantGaseosas;
					double precioGaseosa = parProductoCtrl.obtenerPrecioPilaProducto(idProductoGaseosa);
					//Actualizamos el estado de las porciones en bd 
					estActual = operTiendaCtrl.obtenerPorcionesControlDiario(fechaSistema);
					cantPorciones = estActual.getPorcion() + estActual.getPorcionGaseosa() + estActual.getPorcionEmpleado() + estActual.getPorcionDesecho();
					cantGaseosas = estActual.getPorcionGaseosa();
					cantDescuento = estActual.getPorcionEmpleado();
					
					//Obtenemos el id de la tienda
					int idTienda = operTiendaCtrl.obtenerIdTienda();
					//Existe un cliente tienda el cual es el cliente con el id 0, con ese mandamos la facturación
					//A continuación debemos realizar la inserción del pedido, el detalle pedido, la forma de pago y el descuento.
					
					//Realizamos la inserción del encabezado pedido
					int idPedidoTienda = pedCtrl.InsertarEncabezadoPedido(idTienda, 0, fechaSistema, Sesion.getUsuario());
					
					//Comenzamos la adición de detalles al pedido, validamos que si existan porciones para facturar
					DetallePedido detPedido;
					int contadorDetallePedido = 1;
					if(cantPorciones > 0)
					{
						detPedido = new DetallePedido(0,idPedidoTienda,idProductoPorcion,cantPorciones, precioPorcion, cantPorciones*precioPorcion, "",0, "N","", contadorDetallePedido);
						pedCtrl.insertarDetallePedido(detPedido);
						contadorDetallePedido++;
					}
					if(cantGaseosas > 0)
					{
						detPedido = new DetallePedido(0,idPedidoTienda,idProductoGaseosa,cantGaseosas, precioGaseosa, cantGaseosas*precioGaseosa, "",0, "N","", contadorDetallePedido);
						pedCtrl.insertarDetallePedido(detPedido);
						contadorDetallePedido++;
					}
					//Insertar la forma de pago en efectivo
					double valorPago = ((cantPorciones - (estActual.getPorcionEmpleado() + estActual.getPorcionDesecho()))*precioPorcion) + (cantGaseosas * precioGaseosa);
					pedCtrl.insertarPedidoFormaPago(	valorPago, 0, valorPago, 0, idPedidoTienda);
					
					//Insertamos el descuento producto de los desechos
					double valorDescuento = (estActual.getPorcionEmpleado() + estActual.getPorcionDesecho())*precioPorcion; 
					PedidoDescuento descuento = new PedidoDescuento(idPedidoTienda, valorDescuento , 0, "DESCUENTO PORCIONES EMPLEADO Y DESECHOS" );
					boolean resp = pedCtrl.insertarPedidoDescuento(descuento);
					
					//Finalizamos el pedido -- el tipo pedido se quema mientras tanto
					boolean respuesta = pedCtrl.finalizarPedido(idPedidoTienda, 0, 2);
					//Si se finaliza bien el pedido cambiamos el estado de las porciones para el día
					if(respuesta)
					{
						operTiendaCtrl.facturarPorciones(fechaSistema);
						JOptionPane.showMessageDialog(ventanaPadre, "Se ha ingresado el pedido # " + idPedidoTienda , "Confirmación de Ingreso de Pedido", JOptionPane.OK_OPTION);
						dispose();
					}
			    }
			}
		});
		btnFacturarParaFinalizar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnFacturarParaFinalizar.setBounds(216, 322, 233, 23);
		contentPanel.add(btnFacturarParaFinalizar);
		
		btnDisminuirPorcion = new JButton("Disminuir Porci\u00F3n");
		btnDisminuirPorcion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int cantidad;
				try
				{
					cantidad = Integer.parseInt(txtPorciones.getText());
				}catch(Exception exc)
				{
					cantidad = 0;
				}
				if(cantidad >= 1)
				{
					boolean respuesta = operTiendaCtrl.disminuirPorcion(fechaSistema);
					if(respuesta)
					{
						cantidad = cantidad -1;
						txtPorciones.setText(Integer.toString(cantidad));
					}
				}
				
			}
		});
		btnDisminuirPorcion.setBounds(23, 186, 195, 23);
		contentPanel.add(btnDisminuirPorcion);
		
		btnDisminuirPorcionGaseosa = new JButton("Disminuir Porci\u00F3n/Gaseosa");
		btnDisminuirPorcionGaseosa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int cantidad;
				try
				{
					cantidad = Integer.parseInt(txtPorcionesGaseosa.getText());
				}catch(Exception exc)
				{
					cantidad = 0;
				}
				if(cantidad >= 1)
				{
					boolean respuesta = operTiendaCtrl.disminuirPorcionGaseosa(fechaSistema);
					if(respuesta)
					{
						cantidad = cantidad -1;
						txtPorcionesGaseosa.setText(Integer.toString(cantidad));
					}
				}
			}
		});
		btnDisminuirPorcionGaseosa.setBounds(254, 186, 195, 23);
		contentPanel.add(btnDisminuirPorcionGaseosa);
		
		JLabel lblConsumoEmpleado = new JLabel("CONSUMO EMPLEADO");
		lblConsumoEmpleado.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblConsumoEmpleado.setBounds(480, 47, 195, 14);
		contentPanel.add(lblConsumoEmpleado);
		
		btnPorcionEmpleado = new JButton("Consumo Empleado");
		btnPorcionEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean respuesta = operTiendaCtrl.aumentarPorcionEmpleado(fechaSistema);
				if(respuesta)
				{
					int cantidad;
					try
					{
						cantidad = Integer.parseInt(txtPorcionEmpleado.getText());
					}catch(Exception exc)
					{
						cantidad = 0;
					}
					cantidad = cantidad + 1;
					txtPorcionEmpleado.setText(Integer.toString(cantidad));
				}
			}
		});
		btnPorcionEmpleado.setIcon(new ImageIcon(VentPedContPorciones.class.getResource("/icons/porcionEmpleado.jpg")));
		btnPorcionEmpleado.setBounds(473, 71, 195, 67);
		contentPanel.add(btnPorcionEmpleado);
		
		btnDisminuirPorcionEmpleado = new JButton("Disminuir Porci\u00F3n Empleado");
		btnDisminuirPorcionEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int cantidad;
				try
				{
					cantidad = Integer.parseInt(txtPorcionEmpleado.getText());
				}catch(Exception exc)
				{
					cantidad = 0;
				}
				if(cantidad >= 1)
				{
					boolean respuesta = operTiendaCtrl.disminuirPorcionEmpleado(fechaSistema);
					if(respuesta)
					{
						cantidad = cantidad -1;
						txtPorcionEmpleado.setText(Integer.toString(cantidad));
					}
				}
				
			}
		});
		btnDisminuirPorcionEmpleado.setBounds(473, 186, 195, 23);
		contentPanel.add(btnDisminuirPorcionEmpleado);
		
		JLabel lblPorcionEmpleado = new JLabel("PORCION EMPLEADO");
		lblPorcionEmpleado.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPorcionEmpleado.setBounds(476, 237, 164, 14);
		contentPanel.add(lblPorcionEmpleado);
		
		txtPorcionEmpleado = new JTextField();
		txtPorcionEmpleado.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtPorcionEmpleado.setEditable(false);
		txtPorcionEmpleado.setColumns(10);
		txtPorcionEmpleado.setBounds(639, 234, 36, 20);
		contentPanel.add(txtPorcionEmpleado);
		
		btnDesecho = new JButton("DESECHO");
		btnDesecho.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean respuesta = operTiendaCtrl.aumentarPorcionDesecho(fechaSistema);
				if(respuesta)
				{
					int cantidad;
					try
					{
						cantidad = Integer.parseInt(txtPorcionDesecho.getText());
					}catch(Exception exc)
					{
						cantidad = 0;
					}
					cantidad = cantidad + 1;
					txtPorcionDesecho.setText(Integer.toString(cantidad));
				}
				
			}
		});
		btnDesecho.setIcon(new ImageIcon(VentPedContPorciones.class.getResource("/icons/porcionDesecho.jpg")));
		btnDesecho.setBounds(690, 71, 195, 67);
		contentPanel.add(btnDesecho);
		
		JLabel lblDesecho = new JLabel("DESECHO");
		lblDesecho.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblDesecho.setBounds(735, 47, 112, 14);
		contentPanel.add(lblDesecho);
		
		btnDisminuirDesecho = new JButton("Disminuir Desecho");
		btnDisminuirDesecho.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int cantidad;
				try
				{
					cantidad = Integer.parseInt(txtPorcionDesecho.getText());
				}catch(Exception exc)
				{
					cantidad = 0;
				}
				if(cantidad >= 1)
				{
					boolean respuesta = operTiendaCtrl.disminuirPorcionDesecho(fechaSistema);
					if(respuesta)
					{
						cantidad = cantidad -1;
						txtPorcionDesecho.setText(Integer.toString(cantidad));
					}
				}
				
			}
		});
		btnDisminuirDesecho.setBounds(690, 186, 195, 23);
		contentPanel.add(btnDisminuirDesecho);
		
		JLabel lblPorcionDesecho = new JLabel("PORCION DESECHO");
		lblPorcionDesecho.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblPorcionDesecho.setBounds(690, 238, 164, 14);
		contentPanel.add(lblPorcionDesecho);
		
		txtPorcionDesecho = new JTextField();
		txtPorcionDesecho.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtPorcionDesecho.setEditable(false);
		txtPorcionDesecho.setColumns(10);
		txtPorcionDesecho.setBounds(836, 235, 36, 20);
		contentPanel.add(txtPorcionDesecho);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("REGRESAR A MEN\u00DA");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setFont(new Font("Tahoma", Font.BOLD, 13));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		//Método para poblar la pantalla
		llenarPantallaControlPorciones();
	}
	
	public void llenarPantallaControlPorciones()
	{
		estActual = operTiendaCtrl.obtenerPorcionesControlDiario(fechaSistema);
		txtPorciones.setText(Integer.toString(estActual.getPorcion()));
		txtPorcionesGaseosa.setText(Integer.toString(estActual.getPorcionGaseosa()));
		txtPorcionEmpleado.setText(Integer.toString(estActual.getPorcionEmpleado()));
		txtPorcionDesecho.setText(Integer.toString(estActual.getPorcionDesecho()));
		if(estActual.isFacturado())
		{
			btnFacturarParaFinalizar.setEnabled(false);
			btnPorcion.setEnabled(false);
			btnPorcionGaseosa.setEnabled(false);
			btnPorcionEmpleado.setEnabled(false);
			btnDesecho.setEnabled(false);
			btnDisminuirPorcion.setEnabled(false);
			btnDisminuirPorcionGaseosa.setEnabled(false);
			btnDisminuirPorcionEmpleado.setEnabled(false);
			btnDisminuirDesecho.setEnabled(false);
		}
	}
}
