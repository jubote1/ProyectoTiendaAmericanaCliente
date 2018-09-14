package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import capaControlador.AutenticacionCtrl;
import capaControlador.OperacionesTiendaCtrl;
import capaControlador.ParametrosCtrl;
import capaControlador.ParametrosProductoCtrl;
import capaModelo.Impuesto;
import capaModelo.MenuAgrupador;
import capaModelo.Parametro;
import capaModelo.Tienda;

import javax.swing.JComboBox;

public class VentSegTienda extends JDialog {

	private JPanel contentPane;
	private JTextField txtNombreTienda;
	private JTextField txtUrl;
	private JTextField txtFechaApertura;
	private JScrollPane scrollPane;
	private JTextField txtUltimoCierre;
	private JTextField txtDireccion;
	private JTextField txtTelefono;
	private JTextField txtRazonSocial;
	private JTextField txtResolucion;
	private JTextField txtFechaResolucion;
	private JTextField txtUbicacion;
	private JTextField txtIdentificacion;
	private JComboBox cmbTipoContribuyente;
	private JTextField txtNumInicial;
	private JTextField txtNumFinal;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentSegTienda frame = new VentSegTienda(null, true);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	/**
	 * Create the frame.
	 * Se documentan todas las acciones  a seguir cuando se instancia el frame para el CRUD de impuestor.
	 */
	public VentSegTienda(java.awt.Frame frame, boolean modal) {
		super(frame, modal);
		setTitle("ADMINISTRACI\u00D3N DE PAR\u00C1METROS DE TIENDA");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 745, 526);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelDatos = new JPanel();
		panelDatos.setBounds(22, 11, 697, 466);
		contentPane.add(panelDatos);
		panelDatos.setLayout(null);
		
		JLabel lblNombreTienda = new JLabel("Nombre Tienda");
		lblNombreTienda.setBounds(61, 29, 114, 14);
		panelDatos.add(lblNombreTienda);
		
		JLabel lblFechaApertura = new JLabel("Fecha Apertura");
		lblFechaApertura.setBounds(61, 113, 93, 14);
		panelDatos.add(lblFechaApertura);
		
		JLabel lblUrlContact = new JLabel("URL Contact Center");
		lblUrlContact.setBounds(61, 71, 114, 14);
		panelDatos.add(lblUrlContact);
		
		txtFechaApertura = new JTextField();
		txtFechaApertura.setEditable(false);
		txtFechaApertura.setBounds(210, 110, 171, 20);
		panelDatos.add(txtFechaApertura);
		txtFechaApertura.setColumns(100);
		
		txtUrl = new JTextField();
		txtUrl.setBounds(210, 68, 344, 20);
		panelDatos.add(txtUrl);
		txtUrl.setColumns(100);
		
		txtNombreTienda = new JTextField();
		txtNombreTienda.setBounds(210, 26, 171, 20);
		panelDatos.add(txtNombreTienda);
		txtNombreTienda.setColumns(50);
		txtUrl.setText("");
		txtFechaApertura.setText("");
		//Adicionar manejo para el evento de seleccion
		
		
		
		//Adicionamos los botones para las acciones del GRID
		JButton btnActualizarInf = new JButton("Actualizar Informaci\u00F3n");
		btnActualizarInf.setBounds(210, 432, 187, 23);
		panelDatos.add(btnActualizarInf);
		
		JLabel lblUltimoCierre = new JLabel("Fecha Apertura");
		lblUltimoCierre.setBounds(61, 157, 93, 14);
		panelDatos.add(lblUltimoCierre);
		
		txtUltimoCierre = new JTextField();
		txtUltimoCierre.setText("");
		txtUltimoCierre.setEditable(false);
		txtUltimoCierre.setColumns(100);
		txtUltimoCierre.setBounds(210, 154, 171, 20);
		panelDatos.add(txtUltimoCierre);
		
		JLabel lblDireccion = new JLabel("Direccion");
		lblDireccion.setBounds(61, 194, 93, 14);
		panelDatos.add(lblDireccion);
		
		txtDireccion = new JTextField();
		txtDireccion.setBounds(210, 191, 171, 20);
		panelDatos.add(txtDireccion);
		txtDireccion.setColumns(50);
		
		JLabel lblTelefono = new JLabel("Tel\u00E9fono");
		lblTelefono.setBounds(61, 227, 93, 14);
		panelDatos.add(lblTelefono);
		
		txtTelefono = new JTextField();
		txtTelefono.setColumns(50);
		txtTelefono.setBounds(210, 224, 171, 20);
		panelDatos.add(txtTelefono);
		
		JLabel lblRazonSocial = new JLabel("Raz\u00F3n Social");
		lblRazonSocial.setBounds(61, 262, 93, 14);
		panelDatos.add(lblRazonSocial);
		
		txtRazonSocial = new JTextField();
		txtRazonSocial.setColumns(50);
		txtRazonSocial.setBounds(210, 259, 171, 20);
		panelDatos.add(txtRazonSocial);
		
		JLabel lblResolucion = new JLabel("Resoluci\u00F3n");
		lblResolucion.setBounds(61, 318, 93, 14);
		panelDatos.add(lblResolucion);
		
		txtResolucion = new JTextField();
		txtResolucion.setColumns(50);
		txtResolucion.setBounds(210, 315, 171, 20);
		panelDatos.add(txtResolucion);
		
		JLabel lblFechaResolucion = new JLabel("Fecha Resoluci\u00F3n");
		lblFechaResolucion.setBounds(61, 349, 114, 14);
		panelDatos.add(lblFechaResolucion);
		
		txtFechaResolucion = new JTextField();
		txtFechaResolucion.setColumns(50);
		txtFechaResolucion.setBounds(210, 346, 171, 20);
		panelDatos.add(txtFechaResolucion);
		
		txtUbicacion = new JTextField();
		txtUbicacion.setColumns(50);
		txtUbicacion.setBounds(210, 381, 171, 20);
		panelDatos.add(txtUbicacion);
		
		JLabel lblUbicacion = new JLabel("Ubicaci\u00F3n");
		lblUbicacion.setBounds(61, 384, 114, 14);
		panelDatos.add(lblUbicacion);
		
		JLabel lblIdentificacion = new JLabel("Identificaci\u00F3n");
		lblIdentificacion.setBounds(391, 29, 114, 14);
		panelDatos.add(lblIdentificacion);
		
		txtIdentificacion = new JTextField();
		txtIdentificacion.setBounds(527, 26, 142, 20);
		panelDatos.add(txtIdentificacion);
		txtIdentificacion.setColumns(50);
		
		cmbTipoContribuyente = new JComboBox();
		cmbTipoContribuyente.setBounds(210, 290, 171, 20);
		panelDatos.add(cmbTipoContribuyente);
		
		JLabel lblTipoContribuyente = new JLabel("Tipo Contribuyente");
		lblTipoContribuyente.setBounds(61, 293, 93, 14);
		panelDatos.add(lblTipoContribuyente);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				dispose();
			}
		});
		btnSalir.setBounds(416, 432, 138, 23);
		panelDatos.add(btnSalir);
		
		JLabel lblNurInicial = new JLabel("Num Inicial");
		lblNurInicial.setBounds(401, 318, 71, 14);
		panelDatos.add(lblNurInicial);
		
		JLabel lblNumFinal = new JLabel("Num Final");
		lblNumFinal.setBounds(549, 318, 79, 14);
		panelDatos.add(lblNumFinal);
		
		txtNumInicial = new JTextField();
		txtNumInicial.setBounds(482, 315, 57, 20);
		panelDatos.add(txtNumInicial);
		txtNumInicial.setColumns(10);
		
		txtNumFinal = new JTextField();
		txtNumFinal.setColumns(10);
		txtNumFinal.setBounds(630, 315, 57, 20);
		panelDatos.add(txtNumFinal);
		
		JButton btnActualizarNmeros = new JButton("Actualizar N\u00FAmeros");
		btnActualizarNmeros.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				validarNumerosResolucion();
				OperacionesTiendaCtrl operTiendaCtrl = new OperacionesTiendaCtrl();
				String numeroInicial = txtNumInicial.getText();
				String numeroFinal = txtNumFinal.getText();
				int numInicial;
				int numFinal;
				numInicial = Integer.parseInt(numeroInicial);
				numFinal = Integer.parseInt(numeroFinal);
				String respuesta = operTiendaCtrl.actualizarNumResolucion(numInicial, numFinal);
				if(respuesta.equals(new String("OK")))
				{
					JOptionPane.showMessageDialog(null, "La actualización fue correcta." , "Actualización Resolución Exitosa", JOptionPane.OK_OPTION);
				}else
				{
					JOptionPane.showMessageDialog(null, respuesta , "Error Fijando Números de resolución", JOptionPane.ERROR_MESSAGE);
				}
		
			}
		});
		btnActualizarNmeros.setBounds(492, 345, 177, 23);
		panelDatos.add(btnActualizarNmeros);
		btnActualizarInf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Se pulso botón para adicionar un nuevo Menú Agrupador
				validarDatos();
				String nombreTienda = txtNombreTienda.getText();
				String url = txtUrl.getText();
				String direccion =	txtDireccion.getText();
				String telefono =  txtTelefono.getText();
				String razonSocial = txtRazonSocial.getText();
				String tipoContribuyente = (String) cmbTipoContribuyente.getSelectedItem();
				String resolucion = txtResolucion.getText();
				String fechaResolucion = txtFechaResolucion.getText();
				String ubicacion = txtUbicacion.getText();
				String identificacion = txtIdentificacion.getText();
				Tienda tienda = new Tienda(0,nombreTienda, url, direccion, telefono, razonSocial, tipoContribuyente, resolucion, fechaResolucion, ubicacion, identificacion, "", "");
				OperacionesTiendaCtrl opertiendaCtrl = new OperacionesTiendaCtrl();
				boolean respuesta = opertiendaCtrl.actualizarTienda(tienda);
				if(respuesta)
				{
					JOptionPane.showMessageDialog(null, "Se ha actualizado Correctamente la información." , "Actualizacion Información Tienda", JOptionPane.YES_OPTION);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Error e la actualización de la información" , "Error", JOptionPane.ERROR_MESSAGE);
				}
			
			}
		});
		cargarDatosTienda();
	}
	
	
public void cargarDatosTienda()
{
	OperacionesTiendaCtrl operCtrl = new OperacionesTiendaCtrl();
	Tienda tienda = operCtrl.obtenerTienda();
	txtNombreTienda.setText(tienda.getNombretienda());
	txtUrl.setText(tienda.getUrlContact());
	txtFechaApertura.setText(tienda.getFechaApertura());
	txtUltimoCierre.setText(tienda.getFechaUltimoCierre());
	txtDireccion.setText(tienda.getDireccion());
	txtTelefono.setText(tienda.getTelefono());
	txtRazonSocial.setText(tienda.getRazonSocial());
	txtResolucion.setText(tienda.getResolucion());
	txtFechaResolucion.setText(tienda.getFechaResolucion());
	txtUbicacion.setText(tienda.getUbicacion());
	txtIdentificacion.setText(tienda.getIdentificacion());
	cmbTipoContribuyente.addItem(new String("GRAN CONTRIBUYENTE"));
	cmbTipoContribuyente.addItem(new String("REGIMÉN COMÚN"));
	cmbTipoContribuyente.addItem(new String("REGIMEN SIMPLIFICADO"));
	cmbTipoContribuyente.setSelectedItem(tienda.getTipoContribuyente());
}
	
public boolean validarNumerosResolucion()
{
	String numeroInicial = txtNumInicial.getText();
	String numeroFinal = txtNumFinal.getText();
	int numInicial;
	int numFinal;
	String error = "";
	if(numeroInicial.length() == 0)
	{
		error = error + " Debe ingresar número inicial de numeración.";
	}
	if(numeroFinal.length() == 0)
	{
		error = error + " Debe ingresar número final de numeración.";
	}
	try{
		if(numeroInicial.length() != 0)
		{
			numInicial = Integer.parseInt(numeroInicial);
		}
		
	}catch(Exception e)
	{
		error = error + " El número inicial no es un valor numérico.";
	}
	try{
		if(numeroFinal.length() != 0)
		{
			numFinal = Integer.parseInt(numeroFinal);
		}
		
	}catch(Exception e)
	{
		error = error + " El número final no es un valor numérico.";
	}
	if(error.length() > 0 )
	{
		JOptionPane.showMessageDialog(null, error , "Existen errores en la numeración de la resolución DIAN", JOptionPane.ERROR_MESSAGE);
		return(false);
	}
	else
	{
		return(true);
	}
	
}

public boolean validarDatos()
{
	String nombreTienda = txtNombreTienda.getText();
	boolean respuesta = false;
	String error = "";
	if(nombreTienda.length() == 0)
	{
		error = error + " El nombre de la tienda debe ser diferente de vacío.";
	}
	String url = txtUrl.getText();
	if(url.length() == 0)
	{
		error = error + " La url debe ser diferente de vacío.";
	}
	String direccion =	txtDireccion.getText();
	if(direccion.length() == 0)
	{
		error = error + " La dirección debe ser diferente de vacío.";
	}
	String telefono =  txtTelefono.getText();
	if(telefono.length() == 0)
	{
		error = error + " El teléfono debe ser diferente de vacío.";
	}
	String razonSocial = txtRazonSocial.getText();
	if(razonSocial.length() == 0)
	{
		error = error + " La razón social debe ser diferente de vacío.";
	}
	String resolucion = txtResolucion.getText();
	if(resolucion.length() == 0)
	{
		error = error + " La resolución debe ser diferente de vacío.";
	}
	String fechaResolucion = txtFechaResolucion.getText();
	if(fechaResolucion.length() == 0)
	{
		error = error + " La fecha resolución debe ser diferente de vacío.";
	}
	String ubicacion = txtUbicacion.getText();
	if(ubicacion.length() == 0)
	{
		error = error + " La ubicación debe ser diferente de vacío.";
	}
	String identificacion = txtIdentificacion.getText();
	if(identificacion.length() == 0)
	{
		error = error + " La identificación debe ser diferente de vacío.";
	}
	if(error.length() > 0 )
	{
		JOptionPane.showMessageDialog(null, error , "Faltan Datos para la actualización", JOptionPane.ERROR_MESSAGE);
		return(false);
	}
	else
	{
		return(true);
	}
	
}
}
