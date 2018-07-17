package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class VentCliCliente extends JFrame {

	private JPanel PanelContenedor;
	private JTextField textIdCliente;
	private JTextField textNombre;
	private JTextField textApellido;
	private JTextField textCompania;
	private JComboBox comboMunicipio;
	private JLabel lblTelfono;
	private JTextField textTelefono;
	private JComboBox comboNomenclatura;
	private JTextField textNumNomen1;
	private JLabel label;
	private JTextField textNumNomen2;
	private JLabel label_1;
	private JTextField textNum3;
	private JLabel lblNewLzabel;
	private JTextField textZona;
	private JLabel lblObservacin;
	private JTextField textObservacion;
	private JButton btnBuscar;
	private JButton btnSeleccionar;
	private VentCliBuscarCliente ventBusCliente;
	private int  idCliente = 0;

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
	 * Create the frame.
	 */
	public VentCliCliente(int idClien) {
		
		idCliente = idClien;
		setTitle("VENTANA DE CLIENTES");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 692, 531);
		PanelContenedor = new JPanel();
		
		PanelContenedor.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(PanelContenedor);
		PanelContenedor.setLayout(null);
		
		JTabbedPane tabbedPaneCliente = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneCliente.setBounds(62, 11, 529, 361);
		PanelContenedor.add(tabbedPaneCliente);
		
		JPanel panelInfoBasica = new JPanel();
		tabbedPaneCliente.addTab("Información Clientes", null, panelInfoBasica, null);
		panelInfoBasica.setLayout(null);
		
		JLabel lblIdCliente = new JLabel("Id Cliente");
		lblIdCliente.setBounds(38, 11, 116, 14);
		panelInfoBasica.add(lblIdCliente);
		
		textIdCliente = new JTextField();
		textIdCliente.setEditable(false);
		textIdCliente.setBounds(167, 8, 205, 20);
		panelInfoBasica.add(textIdCliente);
		textIdCliente.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(38, 61, 116, 14);
		panelInfoBasica.add(lblNombre);
		
		textNombre = new JTextField();
		textNombre.setBounds(167, 58, 205, 20);
		panelInfoBasica.add(textNombre);
		textNombre.setColumns(10);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(38, 86, 116, 14);
		panelInfoBasica.add(lblApellido);
		
		textApellido = new JTextField();
		textApellido.setBounds(166, 83, 206, 20);
		panelInfoBasica.add(textApellido);
		textApellido.setColumns(10);
		
		JLabel lblNombreCompania = new JLabel("Nombre Compa\u00F1ia");
		lblNombreCompania.setBounds(38, 111, 116, 14);
		panelInfoBasica.add(lblNombreCompania);
		
		textCompania = new JTextField();
		textCompania.setBounds(167, 108, 205, 20);
		panelInfoBasica.add(textCompania);
		textCompania.setColumns(10);
		
		JLabel lblMunicipio = new JLabel("Municipio");
		lblMunicipio.setBounds(38, 136, 46, 14);
		panelInfoBasica.add(lblMunicipio);
		
		comboMunicipio = new JComboBox();
		comboMunicipio.setBounds(166, 133, 206, 20);
		panelInfoBasica.add(comboMunicipio);
		
		lblTelfono = new JLabel("Tel\u00E9fono");
		lblTelfono.setBounds(38, 36, 116, 14);
		panelInfoBasica.add(lblTelfono);
		
		textTelefono = new JTextField();
		textTelefono.setBounds(167, 33, 205, 20);
		panelInfoBasica.add(textTelefono);
		textTelefono.setColumns(10);
		
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
		textZona.setColumns(10);
		
		lblObservacin = new JLabel("Observaci\u00F3n");
		lblObservacin.setBounds(38, 294, 78, 14);
		panelInfoBasica.add(lblObservacin);
		
		textObservacion = new JTextField();
		textObservacion.setBounds(167, 291, 264, 20);
		panelInfoBasica.add(textObservacion);
		textObservacion.setColumns(10);
		
		btnBuscar = new JButton("Buscar");
		
		btnBuscar.setBounds(397, 32, 89, 23);
		panelInfoBasica.add(btnBuscar);
		
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
		btnSeleccionar.setBounds(130, 420, 116, 23);
		PanelContenedor.add(btnSeleccionar);
		
		//JDesktopPane desktopPaneCliente = new JDesktopPane();
		//desktopPaneCliente.setBounds(0, 0, 666, 482);
		//PanelContenedor.add(desktopPaneCliente);
		
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String telefono = textTelefono.getText().trim();
				System.out.println(telefono + "esto fue");
				if(telefono.equals(new String("")))
				{
					JOptionPane.showMessageDialog(null, "Se debe de ingresar información el campo teléfono para poder realizar la búsqueda " , "Búsqueda por teléfono", JOptionPane.OK_OPTION);
					return;
				}
				ventBusCliente = new VentCliBuscarCliente(telefono);
				ventBusCliente.setVisible(true);
				dispose();
			}
		});
		initComboBoxMunicipios();
		initcomboNomenclatura();
		if (idCliente == 0)
		{
			
		}
		else
		{
			ClienteCtrl cliCtrl = new ClienteCtrl();
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
