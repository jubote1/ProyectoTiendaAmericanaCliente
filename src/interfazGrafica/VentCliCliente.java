package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FocusTraversalPolicy;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import capaControlador.AutenticacionCtrl;
import capaControlador.ClienteCtrl;
import capaControlador.ParametrosDireccionCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.Cliente;
import capaModelo.Municipio;
import capaModelo.NomenclaturaDireccion;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextArea;


/**
 * Clase que tiene como objetivo la fijación de un cliente existente o la creación de un nuevo.
 * @author JuanDavid
 *
 */
public class VentCliCliente extends JFrame {

	/**
	 * Definición de todas las variables globales.
	 */
	private JPanel PanelContenedor;
	private JTextField textIdCliente;
	private JTextField textNombre;
	private JTextField textApellido;
	private JTextField textCompania;
	private JComboBox comboMunicipio;
	private JLabel lblTelfono;
	private JFormattedTextField textTelefono;
	private JComboBox comboNomenclatura;
	private JTextField textNumNomen1;
	private JLabel label;
	private JTextField textNumNomen2;
	private JLabel label_1;
	private JTextField textNum3;
	private JLabel lblNewLzabel;
	private JTextField textZona;
	private JLabel lblObservacin;
	private JButton btnBuscar;
	private JButton btnSeleccionar;
	
	private JTextArea textObservacion;
	private VentCliBuscarCliente ventBusCliente;
	private int  idCliente = 0;
	private ClienteCtrl cliCtrl = new ClienteCtrl(PrincipalLogueo.habilitaAuditoria);
	private PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	AutenticacionCtrl autCtrl = new AutenticacionCtrl(PrincipalLogueo.habilitaAuditoria);
	private MaskFormatter mascTel = new MaskFormatter();
	public JButton btnActualizarCliente;
	public JButton btnCrearCliente;
	public JButton btnAgregarDireccion;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentCliCliente frame = new VentCliCliente(0, null, false);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

/**
 * Método constructor de la clase que se encarga de cargar la información del cliente en la pantalla de Clientes
 * @param idClien
 */
	public VentCliCliente(int idClien, java.awt.Frame parent, boolean modal) {
		//super(parent, modal);
		idCliente = idClien;
		setTitle("VENTANA DE CLIENTES");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0,0, 692, 531);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 692, 531);
		PanelContenedor = new JPanel();
		
		PanelContenedor.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(PanelContenedor);
		PanelContenedor.setLayout(null);
		
		JTabbedPane tabbedPaneCliente = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneCliente.setBounds(62, 11, 529, 390);
		PanelContenedor.add(tabbedPaneCliente);
		
		JPanel panelInfoBasica = new JPanel();
		tabbedPaneCliente.addTab("Información Clientes", null, panelInfoBasica, null);
		panelInfoBasica.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(38, 61, 116, 14);
		panelInfoBasica.add(lblNombre);
		
		textNombre = new JTextField();
		textNombre.setBounds(167, 58, 205, 20);
		panelInfoBasica.add(textNombre);
		textNombre.setColumns(100);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(38, 86, 116, 14);
		panelInfoBasica.add(lblApellido);
		
		textApellido = new JTextField();
		textApellido.setBounds(166, 83, 206, 20);
		panelInfoBasica.add(textApellido);
		textApellido.setColumns(100);
		
		JLabel lblNombreCompania = new JLabel("Nombre Compa\u00F1ia");
		lblNombreCompania.setBounds(38, 111, 116, 14);
		panelInfoBasica.add(lblNombreCompania);
		
		textCompania = new JTextField();
		textCompania.setBounds(167, 108, 205, 20);
		panelInfoBasica.add(textCompania);
		textCompania.setColumns(100);
		
		JLabel lblMunicipio = new JLabel("Municipio");
		lblMunicipio.setBounds(38, 136, 46, 14);
		panelInfoBasica.add(lblMunicipio);
		
		comboMunicipio = new JComboBox();
		comboMunicipio.setBounds(166, 133, 206, 20);
		panelInfoBasica.add(comboMunicipio);
		
		lblTelfono = new JLabel("Tel\u00E9fono");
		lblTelfono.setBounds(38, 36, 116, 14);
		panelInfoBasica.add(lblTelfono);
		
			
		/**
		 * Se realiza la creación del formato para el campo de teléfono
		 */
		mascTel = new MaskFormatter();
		try
		{
		   mascTel = new MaskFormatter("##########");
		   
		}
		catch (Exception e)
		{
		   System.out.println(e.toString() + "ERROR");
		}
		
		textTelefono= new JFormattedTextField(mascTel);
		textTelefono.setBounds(167, 33, 205, 20);
		//Incluiremos la acción cuando el foco sea pérdido para que valide el contenido del botón y si pasa valide el teléfono
		
		
		panelInfoBasica.add(textTelefono);
		textTelefono.setColumns(10);
		textTelefono.setNextFocusableComponent(textNombre);
		comboNomenclatura = new JComboBox();
		comboNomenclatura.setBounds(26, 185, 90, 20);
		panelInfoBasica.add(comboNomenclatura);
		
		textNumNomen1 = new JTextField();
		textNumNomen1.setBounds(130, 185, 86, 20);
		panelInfoBasica.add(textNumNomen1);
		textNumNomen1.setColumns(10);
		
		label = new JLabel("#");
		label.setBounds(226, 188, 13, 14);
		panelInfoBasica.add(label);
		
		textNumNomen2 = new JTextField();
		textNumNomen2.setBounds(249, 185, 86, 20);
		panelInfoBasica.add(textNumNomen2);
		textNumNomen2.setColumns(10);
		
		label_1 = new JLabel("-");
		label_1.setBounds(348, 188, 13, 14);
		panelInfoBasica.add(label_1);
		
		textNum3 = new JTextField();
		textNum3.setBounds(365, 185, 86, 20);
		panelInfoBasica.add(textNum3);
		textNum3.setColumns(10);
		
		lblNewLzabel = new JLabel("Zona");
		lblNewLzabel.setBounds(38, 252, 46, 14);
		panelInfoBasica.add(lblNewLzabel);
		
		textZona = new JTextField();
		textZona.setBounds(167, 249, 264, 20);
		panelInfoBasica.add(textZona);
		textZona.setColumns(100);
		
		lblObservacin = new JLabel("Observaci\u00F3n");
		lblObservacin.setBounds(38, 294, 78, 14);
		panelInfoBasica.add(lblObservacin);
		
		btnBuscar = new JButton("Buscar");
		
		btnBuscar.setBounds(397, 32, 105, 43);
		panelInfoBasica.add(btnBuscar);
		
		textObservacion = new JTextArea();
		textObservacion.setLineWrap(true);
		textObservacion.setBounds(167, 294, 264, 57);
		//Ordenamos mediante setFocusTraversalPolicy para definir el focus del componente
	
		panelInfoBasica.add(textObservacion);
		
		btnAgregarDireccion = new JButton("<html><center>Agregar Direcci\u00F3n</center></html>");
		btnAgregarDireccion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				idCliente = 0;
				btnActualizarCliente.setEnabled(false);
				btnCrearCliente.setEnabled(true);
				btnAgregarDireccion.setEnabled(false);
				textIdCliente.setText("0");
			}
		});
		btnAgregarDireccion.setBounds(397, 102, 105, 48);
		panelInfoBasica.add(btnAgregarDireccion);
		btnAgregarDireccion.setEnabled(false);
		
		btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//actualizamos si es el caso el cliente
				actualizarCliente(false);
				//En este botón seleccionar se instanciará un objeto de la ventana tomador de pedidos y se fijarán los valores de los parámetros de cliente
				VentPedTomarPedidos ventFijarParCliente = new VentPedTomarPedidos();
				int idClienSel = Integer.parseInt(textIdCliente.getText());
				ventFijarParCliente.idCliente = idClienSel;
				ventFijarParCliente.nombreCliente = textNombre.getText() + " " + textApellido.getText();
				ventFijarParCliente.direccion = ((NomenclaturaDireccion)comboNomenclatura.getSelectedItem()).getNomenclatura() + " " + textNumNomen1.getText() + " # " + textNumNomen2.getText() + " - " + textNum3.getText();
				ventFijarParCliente.zona = textZona.getText();
				//Debemos validar si el idPedido de la pantalla VentPedTomarPedidos no es igual a cero, deberemos de actualizar en el pedido el idCliente
				if(ventFijarParCliente.idPedido > 0)
				{
					//Llamamos el método que se encarga de la actualización de la información del cliente en el pedido
					pedCtrl.actualizarClientePedido(ventFijarParCliente.idPedido, idClienSel);
				}
				ventFijarParCliente.dispose();
				dispose();
			}
		});
		btnSeleccionar.setBounds(133, 412, 133, 29);
		PanelContenedor.add(btnSeleccionar);
		
		btnCrearCliente = new JButton("Crear Cliente");
		btnCrearCliente.addActionListener(new ActionListener() {
			/**
			 * Método que define la acción para la creación de un cliente en el sistema
			 */
			public void actionPerformed(ActionEvent arg0) {
				String telefono = textTelefono.getText();
				String nombre = textNombre.getText();
				String apellido = textApellido.getText();
				String compania = textCompania.getText();
				String numNomen1 = textNumNomen1.getText();
				String numNomen2 = textNumNomen2.getText();
				String num3 = textNum3.getText();
				String zona = textZona.getText(); 
				String observacion = textObservacion.getText();
				if(observacion.length() > 200)
				{
					observacion = observacion.substring(0, 200);
				}
				int idMunicipio = ((Municipio)comboMunicipio.getSelectedItem()).getIdmunicipio();
				int idNomemclatura = ((NomenclaturaDireccion)comboNomenclatura.getSelectedItem()).getIdnomemclatura();
				//conformamos el campo dirección con base en la nomenclatura definida
				String direccion = ((NomenclaturaDireccion)comboNomenclatura.getSelectedItem()).getNomenclatura() + " " + numNomen1 + " # " + numNomen2 + " - " + num3;
				Cliente crearCliente = new Cliente(0, telefono, nombre, apellido, compania, direccion, "", idMunicipio,0, 0, zona , observacion, "", 0, 0, idNomemclatura, numNomen1, numNomen2, num3, "");
				//Esto lo realizaremos de manera transitoria mientra hacemos algo estándar en el ingreso de la tienda
				crearCliente.setIdtienda(1);
				ClienteCtrl clienteCtrl = new ClienteCtrl(PrincipalLogueo.habilitaAuditoria);
				int idCliIns = clienteCtrl.insertarCliente(crearCliente);
				if(idCliIns > 0)
				{
					JOptionPane.showMessageDialog(null, "El cliente fue ingresado correctamente " , "Ingreso de Cliente ", JOptionPane.OK_OPTION);
					idCliente = idCliIns;
					textIdCliente.setText(Integer.toString(idCliIns));
					btnActualizarCliente.setEnabled(true);
					btnCrearCliente.setEnabled(false);
				}
			}
		});
		btnCrearCliente.setBounds(297, 412, 133, 29);
		PanelContenedor.add(btnCrearCliente);
		
		JLabel lblIdCliente = new JLabel("Id Cliente");
		lblIdCliente.setBounds(10, 465, 67, 14);
		PanelContenedor.add(lblIdCliente);
		
		textIdCliente = new JTextField();
		textIdCliente.setBounds(62, 462, 67, 20);
		PanelContenedor.add(textIdCliente);
		textIdCliente.setEditable(false);
		textIdCliente.setColumns(10);
		
		btnActualizarCliente = new JButton("ActualizarCliente");
		btnActualizarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Acciones relacionadas con la actualización del un cliente que son muy similares a la inserción
				actualizarCliente(true);
			}
		});
		btnActualizarCliente.setBounds(458, 412, 133, 29);
		PanelContenedor.add(btnActualizarCliente);
		btnActualizarCliente.setEnabled(false);
		
		btnBuscar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				String telefono = "" ;
				boolean pasaVal = validarTelefono();
				if(pasaVal)
				{
					telefono = textTelefono.getValue().toString();
					//Cambiaremos el método existe cliente, para si
					// el valor es 0 el cliente no siste
					// el valor es 1 el cliente si existe y tiene 1 solo asociado
					// el valor es 2 el cliente si existe y tiene varias direcciones
					//Lo mandamos para si es el caso traerse el cliente
					ArrayList<Cliente> cliente = cliCtrl.existeCliente(telefono);
					if(cliente.size() >= 2)
					{
						boolean tienePermiso = autCtrl.validarAccesoOpcion("CLI_001", Sesion.getAccesosOpcion());
						if (tienePermiso)
						{
							ventBusCliente = new VentCliBuscarCliente(telefono);
							ventBusCliente.setVisible(true);
							dispose();
						}else
						{
							JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
						}
						
					}
					else if(cliente.size() == 0)
					{
						JOptionPane.showMessageDialog(null, "El cliente con teléfono " + telefono + " no existe, debe ingresarlo" , "Cliente no existe", JOptionPane.INFORMATION_MESSAGE);
						textIdCliente.setText("");
						textTelefono.setText("");
						textNombre.setText("");
						textApellido.setText("");
						textCompania.setText("");
						textNumNomen1.setText("");
						textNumNomen2.setText("");
						textNum3.setText("");
						textZona.setText("");
						textObservacion.setText("");
						comboMunicipio.setSelectedIndex(0);
						comboNomenclatura.setSelectedIndex(0);
						lblIdCliente.setText("");
					}else if(cliente.size() == 1)
					{
						//Es porque existe un solo cliente en cuyo caso lo vamos desplegar en Pantalla
						seleccionarCliente(cliente.get(0));
						btnActualizarCliente.setEnabled(true);
						btnCrearCliente.setEnabled(false);
					}
				}
			}
		});
		initComboBoxMunicipios();
		initcomboNomenclatura();
		/**
		 * Se realiza la verificación con base en el idCliente recibido como parámetro en caso de recibirlo en cero,podemos
		 * crear un nuevo cliente, sino es así, es porque estamos asignando y modificando uno
		 */
		if (idCliente == 0)
		{
			btnCrearCliente.setEnabled(true);
		}
		else
		{
			btnCrearCliente.setEnabled(false);
			btnActualizarCliente.setEnabled(true);
			Cliente clienteConsultado =  cliCtrl.obtenerClientePorId(idCliente);
			seleccionarCliente(clienteConsultado);
		}
		
		textTelefono.addFocusListener(new FocusListener()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				//Hacemos commit de la edición del valor textTelefono
				try
				{
					textTelefono.commitEdit();
				}catch(Exception exc)
				{
					System.out.println(exc.toString());
				}
				
				boolean pasaVal = validarTelefono();
				if(pasaVal)
				{
					String telefono = textTelefono.getValue().toString();
					//Cambiaremos el método existe cliente, para si
					// el valor es 0 el cliente no siste
					// el valor es 1 el cliente si existe y tiene 1 solo asociado
					// el valor es 2 el cliente si existe y tiene varias direcciones
					//Lo mandamos para si es el caso traerse el cliente
					ArrayList<Cliente> cliente = cliCtrl.existeCliente(telefono);
					if(cliente.size() >= 2)
					{
						boolean tienePermiso = autCtrl.validarAccesoOpcion("CLI_001", Sesion.getAccesosOpcion());
						if (tienePermiso)
						{
							ventBusCliente = new VentCliBuscarCliente(telefono);
							ventBusCliente.setVisible(true);
							dispose();
						}else
						{
							JOptionPane.showMessageDialog(null, "Su perfil de usuario no tiene acceso a esta opción/pantalla" , "Ingreso no permitido", JOptionPane.ERROR_MESSAGE);
						}
						
					}
					else if(cliente.size() == 0)
					{
						JOptionPane.showMessageDialog(null, "El cliente con teléfono " + telefono + " no existe, debe ingresarlo" , "Cliente no existe", JOptionPane.INFORMATION_MESSAGE);
						textIdCliente.setText("");
						textTelefono.setText("");
						textNombre.setText("");
						textApellido.setText("");
						textCompania.setText("");
						textNumNomen1.setText("");
						textNumNomen2.setText("");
						textNum3.setText("");
						textZona.setText("");
						textObservacion.setText("");
						comboMunicipio.setSelectedIndex(0);
						comboNomenclatura.setSelectedIndex(0);
						lblIdCliente.setText("");
					}else if(cliente.size() == 1)
					{
						//Es porque existe un solo cliente en cuyo caso lo vamos desplegar en Pantalla
						seleccionarCliente(cliente.get(0));
						btnActualizarCliente.setEnabled(true);
						btnCrearCliente.setEnabled(false);
						btnAgregarDireccion.setEnabled(true);
					}
				}
			}

			@Override
			public void focusGained(FocusEvent arg0) 
			{
				
				
			}
		
		});
	}
	
	/**
	 * Método de capa gráfica que se encarga de la inicialización del combo con los municipios
	 */
	public void initComboBoxMunicipios()
	{
		ParametrosDireccionCtrl parCtrl = new ParametrosDireccionCtrl();
		ArrayList<Municipio> municipios = parCtrl.obtenerMunicipiosObjeto();
		for(int i = 0; i<municipios.size();i++)
		{
			Municipio fila = (Municipio)  municipios.get(i);
			comboMunicipio.addItem(fila);
		}
	}
	
	/**
	 * Método de capa gráfica que se encarga de la inicialización del combo de nomemclaturas de dirección
	 */
	public void initcomboNomenclatura()
	{
		ParametrosDireccionCtrl parCtrl = new ParametrosDireccionCtrl();
		ArrayList<NomenclaturaDireccion> nomen = parCtrl.obtenerNomenclaturas();
		for(int i = 0; i<nomen.size();i++)
		{
			NomenclaturaDireccion fila = (NomenclaturaDireccion)  nomen.get(i);
			comboNomenclatura.addItem(fila);
		}
		
	}
	
	public boolean validarTelefono()
	{
		/**
		 * Capturamos el valor del JTextField con formato
		 */
		String telefono = "";
		try
		{
			telefono = textTelefono.getValue().toString();
			
			if((telefono.length() < 10) || (telefono.length() > 10))
			{
				JOptionPane.showMessageDialog(null, "El campo telefono tiene una longitud incorrecta diferente a 10 digitos" , "Error en campo teléfono", JOptionPane.ERROR_MESSAGE);
				return(false);
			}
		}catch(Exception ex)
		{
			System.out.println("OJO " + textTelefono.getValue());
			System.out.println(ex.toString());
			JOptionPane.showMessageDialog(null, "El campo telefono es númerico de 10 posiciones " , "Error en campo teléfono", JOptionPane.ERROR_MESSAGE);
			return(false);
		}
		return(true);
	}
	
	public void seleccionarCliente(Cliente clienteConsultado)
	{
		textIdCliente.setText(Integer.toString(clienteConsultado.getIdcliente()));
		textTelefono.setText(clienteConsultado.getTelefono());
		textNombre.setText(clienteConsultado.getNombres());
		textApellido.setText(clienteConsultado.getApellidos());
		textCompania.setText(clienteConsultado.getNombreCompania());
		textNumNomen1.setText(clienteConsultado.getNumNomenclatura());
		textNumNomen2.setText(clienteConsultado.getNumNomenclatura2());
		textNum3.setText(clienteConsultado.getNum3());
		textZona.setText(clienteConsultado.getZonaDireccion());
		textObservacion.setText(clienteConsultado.getObservacion());
		String munClienteEditar =  clienteConsultado.getMunicipio();
		int idNomencla = clienteConsultado.getIdnomenclatura();
		//Conectar selección para municipio
		for (int i = 0; i < comboMunicipio.getModel().getSize(); i++) {
			Municipio object = (Municipio)comboMunicipio.getModel().getElementAt(i);
			if(object.toString().equals(munClienteEditar)){
				comboMunicipio.setSelectedItem(object);
			}
		}
		//Conectar selección para Nomenclatura
		for (int i = 0; i < comboNomenclatura.getModel().getSize(); i++) {
			NomenclaturaDireccion object = (NomenclaturaDireccion)comboNomenclatura.getModel().getElementAt(i);
			if(object.getIdnomemclatura() == idNomencla){
				comboNomenclatura.setSelectedItem(object);
			}
		}
	}
	
	public void actualizarCliente(boolean mostrarMensaje)
	{
		//Acciones relacionadas con la actualización del un cliente que son muy similares a la inserción
		String telefono = textTelefono.getText();
		String nombre = textNombre.getText();
		String apellido = textApellido.getText();
		String compania = textCompania.getText();
		String numNomen1 = textNumNomen1.getText();
		String numNomen2 = textNumNomen2.getText();
		String num3 = textNum3.getText();
		String zona = textZona.getText(); 
		String observacion = textObservacion.getText();
		if(observacion.length() > 200)
		{
			observacion = observacion.substring(0, 200);
		}
		int idMunicipio = ((Municipio)comboMunicipio.getSelectedItem()).getIdmunicipio();
		int idNomemclatura = ((NomenclaturaDireccion)comboNomenclatura.getSelectedItem()).getIdnomemclatura();
		String direccion = ((NomenclaturaDireccion)comboNomenclatura.getSelectedItem()).getNomenclatura() + " " + numNomen1 + " # " + numNomen2 + " - " + num3;
		Cliente actCliente = new Cliente(idCliente, telefono, nombre, apellido, compania, direccion, "", idMunicipio,0, 0, zona , observacion, "", 0, 0, idNomemclatura, numNomen1, numNomen2, num3, "");
		//Esto lo realizaremos de manera transitoria mientra hacemos algo estándar en el ingreso de la tienda
		actCliente.setIdtienda(1);
		ClienteCtrl clienteCtrl = new ClienteCtrl(PrincipalLogueo.habilitaAuditoria);
		int idClienteAct = clienteCtrl.actualizarCliente(actCliente);
		if(idClienteAct > 0)
		{
			if(mostrarMensaje)
			{
				JOptionPane.showMessageDialog(null, "El cliente fue actualizado correctamente " , "Actualización de Cliente ", JOptionPane.OK_OPTION);
			}
		}
	}
}
