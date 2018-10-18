package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FocusTraversalPolicy;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import capaControlador.ClienteCtrl;
import capaControlador.ParametrosDireccionCtrl;
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
import javax.swing.JFormattedTextField;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentCliCliente frame = new VentCliCliente(0);
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
	public VentCliCliente(int idClien) {
		
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
		MaskFormatter mascTel = new MaskFormatter();;
		try
		{
		   mascTel = new MaskFormatter("##########");
		   
		}
		catch (Exception e)
		{
		   
		}
		
		textTelefono= new JFormattedTextField(mascTel);
		textTelefono.setBounds(167, 33, 205, 20);
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
		
		btnBuscar.setBounds(397, 32, 89, 23);
		panelInfoBasica.add(btnBuscar);
		
		textObservacion = new JTextArea();
		textObservacion.setLineWrap(true);
		textObservacion.setBounds(167, 294, 264, 57);
		//Ordenamos mediante setFocusTraversalPolicy para definir el focus del componente
	
		panelInfoBasica.add(textObservacion);
		
		btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//En este botón seleccionar se instanciará un objeto de la ventana tomador de pedidos y se fijarán los valores de los parámetros de cliente
				VentPedTomarPedidos ventFijarParCliente = new VentPedTomarPedidos();
				ventFijarParCliente.idCliente = Integer.parseInt(textIdCliente.getText());
				ventFijarParCliente.nombreCliente = textNombre.getText() + " " + textApellido.getText();
				ventFijarParCliente.dispose();
				dispose();
			}
		});
		btnSeleccionar.setBounds(149, 423, 133, 29);
		PanelContenedor.add(btnSeleccionar);
		
		JButton btnCrearCliente = new JButton("Crear Cliente");
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
				Cliente crearCliente = new Cliente(0, telefono, nombre, apellido, compania, "", "", idMunicipio,0, 0, zona , observacion, "", 0, 0, idNomemclatura, numNomen1, numNomen2, num3, "");
				ClienteCtrl clienteCtrl = new ClienteCtrl(PrincipalLogueo.habilitaAuditoria);
				int idCliIns = clienteCtrl.insertarCliente(crearCliente);
				if(idCliIns > 0)
				{
					JOptionPane.showMessageDialog(null, "El cliente fue ingresado correctamente " , "Ingreso de Cliente ", JOptionPane.OK_OPTION);
//					textIdCliente.setText("");
//					textTelefono.setText("");
//					textNombre.setText("");
//					textApellido.setText("");
//					textCompania.setText("");
//					textNumNomen1.setText("");
//					textNumNomen2.setText("");
//					textNum3.setText("");
//					textZona.setText("");
//					textObservacionon.setText("");
//					comboMunicipio.setSelectedIndex(0);
//					comboNomenclatura.setSelectedIndex(0);
					idCliente = idCliIns;
					textIdCliente.setText(Integer.toString(idCliIns));
				}
			}
		});
		btnCrearCliente.setBounds(337, 423, 133, 29);
		PanelContenedor.add(btnCrearCliente);
		
		JLabel lblIdCliente = new JLabel("Id Cliente");
		lblIdCliente.setBounds(10, 465, 67, 14);
		PanelContenedor.add(lblIdCliente);
		
		textIdCliente = new JTextField();
		textIdCliente.setBounds(62, 462, 67, 20);
		PanelContenedor.add(textIdCliente);
		textIdCliente.setEditable(false);
		textIdCliente.setColumns(10);
		
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**
				 * Capturamos el valor del JTextField con formato
				 */
				String telefono;
				try
				{
					telefono = textTelefono.getValue().toString();
				}catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, "El campo telefono es númerico de 10 posiciones " , "Error en campo teléfono", JOptionPane.ERROR_MESSAGE);
					return;
				}
				boolean exisCliente = cliCtrl.existeCliente(telefono);
				if(exisCliente)
				{
					ventBusCliente = new VentCliBuscarCliente(telefono);
					ventBusCliente.setVisible(true);
					dispose();
				}
				else
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
			Cliente clienteConsultado =  cliCtrl.obtenerClientePorId(idCliente);
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
}
