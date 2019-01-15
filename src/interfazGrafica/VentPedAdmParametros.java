package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
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

import capaControlador.ParametrosProductoCtrl;
import capaDAO.GeneralDAO;
import capaControlador.AutenticacionCtrl;
import capaControlador.ParametrosCtrl;
import capaModelo.Impuesto;
import capaModelo.AgrupadorMenu;
import capaModelo.Correo;
import capaModelo.Parametro;
import capaModelo.Tienda;
import utilidades.ControladorEnvioCorreo;

import java.awt.Font;

public class VentPedAdmParametros extends JDialog {

	private JPanel contentPane;
	private JTextField jTextValorParametro;
	private JTextField jTextValorNumerico;
	private JTextField jTextValorTexto;
	private JScrollPane scrollPane;
	private JTable jTableParametro;
	ParametrosCtrl parCtrl = new ParametrosCtrl(PrincipalLogueo.habilitaAuditoria);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentPedAdmParametros frame = new VentPedAdmParametros(null, true);
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
	public DefaultTableModel pintarParametro()
	{
		Object[] columnsName = new Object[3];
        
        columnsName[0] = "Valor Parametro";
        columnsName[1] = "Valor Numerico";
        columnsName[2] = "Valor Texto";
        ArrayList<Object> parametros = parCtrl.obtenerParametros();
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < parametros.size();y++)
		{
			String[] fila =(String[]) parametros.get(y);
			modelo.addRow(fila);
		}
		return(modelo);
		
	}
	
	/**
	 * Create the frame.
	 * Se documentan todas las acciones  a seguir cuando se instancia el frame para el CRUD de impuestor.
	 */
	public VentPedAdmParametros(java.awt.Frame frame, boolean modal) {
		super(frame, modal);
		setTitle("ADMINISTRACI\u00D3N DE PAR\u00C1METROS");
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		//setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 773, 392);
		this.setSize(this.getToolkit().getScreenSize());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
		JPanel panelDatos = new JPanel();
		panelDatos.setBounds(22, 11, 699, 125);
		contentPane.add(panelDatos);
		panelDatos.setLayout(null);
		
		JLabel lblParametro = new JLabel("Valor Par\u00E1metro");
		lblParametro.setBounds(29, 11, 93, 14);
		panelDatos.add(lblParametro);
		
		JLabel lblValorTexto = new JLabel("Valor Texto");
		lblValorTexto.setBounds(29, 78, 93, 14);
		panelDatos.add(lblValorTexto);
		
		JLabel lblValorNumerico = new JLabel("Valor Num\u00E9rico");
		lblValorNumerico.setBounds(29, 46, 114, 14);
		panelDatos.add(lblValorNumerico);
		
		jTextValorTexto = new JTextField();
		jTextValorTexto.setBounds(132, 75, 171, 20);
		panelDatos.add(jTextValorTexto);
		jTextValorTexto.setColumns(100);
		
		jTextValorNumerico = new JTextField();
		jTextValorNumerico.setBounds(132, 43, 171, 20);
		panelDatos.add(jTextValorNumerico);
		jTextValorNumerico.setColumns(50);
		
		jTextValorParametro = new JTextField();
		jTextValorParametro.setBounds(132, 8, 171, 20);
		panelDatos.add(jTextValorParametro);
		jTextValorParametro.setColumns(10);
		
		//Se crea Panel que  contendrá el Jtable y los botones
		JPanel panelJtable = new JPanel();
		panelJtable.setBounds(20, 147, 701, 167);
		contentPane.add(panelJtable);
		panelJtable.setLayout(null);
		DefaultTableModel modelo = pintarParametro();
		//Adicionar manejo para el evento de seleccion
		
		
		
		//Adicionamos los botones para las acciones del GRID
		JButton btnInsertar = new JButton("Insertar");
		/**
		 * Método que implementará la acción cuando se de click sobre el botón Insertar
		 */
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Se pulso botón para adicionar un nuevo Menú Agrupador
				validarDatos();
				String valorParametro = jTextValorParametro.getText();
				String valorTexto = jTextValorTexto.getText();
				int valorNumerico = Integer.parseInt(jTextValorNumerico.getText());
				Parametro par = new Parametro(valorParametro,valorNumerico, valorTexto);
				parCtrl.insertarParametro(par);
				DefaultTableModel modelo = pintarParametro();
				jTableParametro.setModel(modelo);
				//Limpiamos el contenido de los campos
				
			
			}
		});
		btnInsertar.setBounds(52, 133, 89, 23);
		panelJtable.add(btnInsertar);
		JButton btnEliminar = new JButton("Eliminar");
		//Evento para definir las acciones para eliminar
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int filaSeleccionada = jTableParametro.getSelectedRow();
				//Hacemos la validación para decidir si se elimina o no
				String parametroEliminar = (String) jTableParametro.getValueAt(filaSeleccionada, 0);
				JOptionPane.showMessageDialog(null, "Esta seguro que se desea eliminar el parámetro " +  parametroEliminar, "Eliminacion Impuesto ", JOptionPane.YES_NO_OPTION);
				parCtrl.eliminarParametro(parametroEliminar);
				DefaultTableModel modelo = pintarParametro();
				jTableParametro.setModel(modelo);
			}
		});
		btnEliminar.setBounds(175, 133, 89, 23);
		panelJtable.add(btnEliminar);
		
		JButton btnEditar = new JButton("Editar");
		
		btnEditar.setBounds(298, 133, 89, 23);
		panelJtable.add(btnEditar);
		
		JButton btnGrabarEdicion = new JButton("Grabar Edicion");
		
		btnGrabarEdicion.setBounds(427, 133, 123, 23);
		panelJtable.add(btnGrabarEdicion);
		btnGrabarEdicion.setEnabled(false);
		
		JScrollPane scrollPaneParametros = new JScrollPane();
		scrollPaneParametros.setBounds(52, 30, 512, 92);
		panelJtable.add(scrollPaneParametros);
		// Instanciamos el jtable
		jTableParametro = new JTable();
		scrollPaneParametros.setViewportView(jTableParametro);
		jTableParametro.setForeground(Color.black);
		jTableParametro.setBorder(new LineBorder(new Color(0, 0, 0)));
		jTableParametro.setBackground(Color.WHITE);
		this.jTableParametro.setModel(modelo);
		
		JButton btnSalir = new JButton("SALIR");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSalir.setBounds(576, 133, 115, 23);
		panelJtable.add(btnSalir);
		jTextValorNumerico.setText("");
		jTextValorTexto.setText("");
		
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaSeleccionada = jTableParametro.getSelectedRow();
				if(filaSeleccionada == -1)
				{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar algún Parametro para editar " , "No ha Seleccionado para edición ", JOptionPane.ERROR_MESSAGE);
					return;
				}
				filaSeleccionada = jTableParametro.getSelectedRow();
				jTextValorParametro.setText((String)jTableParametro.getValueAt(filaSeleccionada, 0));
				jTextValorNumerico.setText((String)jTableParametro.getValueAt(filaSeleccionada, 1));
				jTextValorTexto.setText((String)jTableParametro.getValueAt(filaSeleccionada, 2));
				btnEliminar.setEnabled(false);
				btnInsertar.setEnabled(false);
				btnGrabarEdicion.setEnabled(true);
			}
		});
		
		btnGrabarEdicion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean validar = validarDatos();
				if (validar)
				{
					Parametro parametroEditado = new Parametro(jTextValorParametro.getText(), Integer.parseInt(jTextValorNumerico.getText()), jTextValorTexto.getText());
					boolean respuesta = parCtrl.EditarParametro(parametroEditado);
					
					if(jTextValorParametro.getText().equals(new String("TIEMPOPEDIDO")))
					{
						//Buscamos la URL que nos servirá como buscaador
						//Inicializamos la variable de habilitaAuditoria
						//Traemos de base de datos el valor del parametro de auditoria
						Tienda tienda = parCtrl.obtenerTiendaObj();
						//Extraemos el valor del campo de ValorTexto
						String rutaURL = tienda.getUrlContact() + "CRUDTiempoPedido?idoperacion=1&nuevotiempo=" + jTextValorNumerico.getText()+ "&idtienda=" + tienda.getIdTienda();
//						//Obtenemos es un String los pedido qeu deseamos geolocalizar
//						String pedidosJSON  = pedCtrl.obtenerPedidosEmpacadosDomicilio();
						//Habilitamos la consola del navegador para ver los posibles errores
						URL url=null;
						try {
						    url = new URL(rutaURL);
						    try {
						        Desktop.getDesktop().browse(url.toURI());
						    } catch (IOException e) {
						        e.printStackTrace();
						    } catch (URISyntaxException e) {
						        e.printStackTrace();
						    }
						} catch (MalformedURLException e1) {
						    e1.printStackTrace();
						}
						//Verificamos el nuevo tiempo si esté está por encima de un parámetro se envía correo eletrónico
						int valNuevoTiempo = Integer.parseInt(jTextValorNumerico.getText());
						//Recuperamos el valor de la variable máximo tiempo para alertar tiempos pedidos
						Parametro parametro = parCtrl.obtenerParametro("TIEMPOMAXIMOALERTA");
						long valNum = 0;
						try
						{
							valNum = (long) parametro.getValorNumerico();
						}catch(Exception e)
						{
							System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE TIEMPOMAXIMOALERTA");
							valNum = 0;
						}
						if(valNuevoTiempo >= valNum)
						{
							Correo correo = new Correo();
							correo.setAsunto("ALERTA TIEMPOS PEDIDO");
							ArrayList correos = GeneralDAO.obtenerCorreosParametro("TIEMPOPEDIDO", PrincipalLogueo.habilitaAuditoria);
							correo.setContrasena("Pizzaamericana2017");
							correo.setUsuarioCorreo("alertaspizzaamericana@gmail.com");
							correo.setMensaje("La tienda " + tienda.getNombretienda() + " está aumentando el tiempo de entrega a " + valNuevoTiempo + " minutos");
							ControladorEnvioCorreo contro = new ControladorEnvioCorreo(correo, correos);
							contro.enviarCorreo();
						}
					}
					
					if (respuesta)
					{
						JOptionPane.showMessageDialog(null, "Se ha editado correctamente el registro " , "Confirmación Edición", JOptionPane.OK_OPTION);
						DefaultTableModel modelo = pintarParametro();
						jTableParametro.setModel(modelo);
						jTextValorNumerico.setText("");
						jTextValorTexto.setText("");
						jTextValorParametro.setText("");
						btnEliminar.setEnabled(true);
						btnInsertar.setEnabled(true);
						btnGrabarEdicion.setEnabled(false);
					}
					
				}
				
			}
		});
		
	}
	
	
public boolean validarDatos()
{
	String valorNumerico = jTextValorNumerico.getText();
	if(valorNumerico.length() > 0)
	{
		try{
			Integer.parseInt(valorNumerico);
			return(true);
		}catch(Exception e)
		{
			return(false);
		}
	}
	return(true);
}
}
