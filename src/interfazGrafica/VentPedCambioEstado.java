package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import capaControlador.ParametrosCtrl;
import capaControlador.ParametrosDireccionCtrl;
import capaControlador.ReportesCtrl;
import capaDAO.ImprimirAdmDAO;
import capaControlador.PedidoCtrl;
import capaModelo.Estado;
import capaModelo.EstadoAnterior;
import capaModelo.EstadoPosterior;
import capaModelo.Municipio;
import capaModelo.Parametro;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class VentPedCambioEstado extends JDialog {

	private JPanel panelPrincipal;
	private JTextField txtIdPedido;
	private JTextField txtEstadoActual;
	private int idPedidoTienda = 0;
	Estado estadoPedido;
	private JTextField txtTipoPedido;
	JComboBox cmbEstadoObjetivo;
	private boolean anterior;
	private boolean posterior;
	private JLabel lblImgEstObj;
	private JLabel lblImgEstActual;
	private PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	private ParametrosCtrl parCtrl = new ParametrosCtrl(PrincipalLogueo.habilitaAuditoria);
	//variable para estado en ruta domicilios
	private final long estEnRutaDom;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentPedCambioEstado frame = new VentPedCambioEstado(0, false, false, null, true,0,0);
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
	public VentPedCambioEstado(int idPedido, boolean anterior, boolean posterior, java.awt.Frame parent, boolean modal , int idDomiciliario, int idEstEnRuta) {
		super(parent, modal);
		this.anterior = anterior;
		this.posterior = posterior;
		setTitle("CAMBIO ESTADO DE PEDIDOS");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0,0, 544, 320);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		//setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 544, 406);
	    setBounds(20, (alto / 2) - (this.getHeight() / 2), 544, 275);
		panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelPrincipal);
		panelPrincipal.setLayout(null);
		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
		JLabel lblIdPedido = new JLabel("Id Pedido");
		lblIdPedido.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblIdPedido.setBounds(20, 26, 100, 14);
		panelPrincipal.add(lblIdPedido);
		long valNum = 0;
		Parametro parametro = parCtrl.obtenerParametro("ENRUTADOMICILIO");
		try
		{
			valNum = (long) parametro.getValorNumerico();
		}catch(Exception e)
		{
			System.out.println("SE TUVO ERROR TOMANDO LA CONSTANTE DE PEDIDOS EN RUTA");
			valNum = 0;
		}
		estEnRutaDom = valNum;
		
		txtIdPedido = new JTextField();
		txtIdPedido.setForeground(Color.RED);
		txtIdPedido.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtIdPedido.setEditable(false);
		txtIdPedido.setBounds(156, 23, 113, 20);
		panelPrincipal.add(txtIdPedido);
		txtIdPedido.setColumns(10);
		
		JLabel lblEstadoActual = new JLabel("Estado Actual");
		lblEstadoActual.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblEstadoActual.setBounds(83, 88, 147, 14);
		panelPrincipal.add(lblEstadoActual);
		
		txtEstadoActual = new JTextField();
		txtEstadoActual.setEditable(false);
		txtEstadoActual.setBounds(279, 85, 221, 20);
		panelPrincipal.add(txtEstadoActual);
		txtEstadoActual.setColumns(10);
		
		JLabel lblEstadoObjetivo = new JLabel("Siguiente Estado");
		lblEstadoObjetivo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblEstadoObjetivo.setBounds(85, 141, 155, 14);
		panelPrincipal.add(lblEstadoObjetivo);
		
		cmbEstadoObjetivo = new JComboBox();
		cmbEstadoObjetivo.setBounds(279, 138, 221, 20);
		panelPrincipal.add(cmbEstadoObjetivo);
		
		JButton btnContinuar = new JButton("OK");
		btnContinuar.setFont(new Font("Tahoma", Font.BOLD, 40));
		btnContinuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EstadoAnterior estAnt;
				EstadoPosterior estPos;
				boolean respuesta = false;
				if (anterior)
				{
					estAnt = (EstadoAnterior) cmbEstadoObjetivo.getSelectedItem();
					respuesta = pedCtrl.ActualizarEstadoPedido(idPedidoTienda,estadoPedido.getIdestado() , estAnt.getIdEstadoAnterior(),Sesion.getUsuario(),false, idDomiciliario, false);
					if(estadoPedido.getIdestado() == estEnRutaDom)
					{
						//Tenemos que desasignar el domiciliario al pedido
						pedCtrl.desasignarDomiciliarioPedido(idPedidoTienda);
					}
				}else if(posterior)
				{
					estPos = (EstadoPosterior) cmbEstadoObjetivo.getSelectedItem();
					boolean salidaDomiciliario = false;
					if(estPos.getIdEstadoPosterior() == idEstEnRuta)
					{
						salidaDomiciliario = true;
					}
					respuesta = pedCtrl.ActualizarEstadoPedido(idPedidoTienda,estadoPedido.getIdestado() , estPos.getIdEstadoPosterior(),Sesion.getUsuario(),true, idDomiciliario,salidaDomiciliario);
					if(estPos.isImprimeEstPosterior())
					{
						//Vamos a realizar la impresión de la comanda
						//Antes de generar la comanda preguntamos si lo debemos de hacer por parametrizacion
						if(Sesion.isImprimirComandaPedido())
						{
							String strComanda = pedCtrl.generarStrImpresionComanda(idPedidoTienda);
							
							if(Sesion.getModeloImpresion() != 1)
							{
								//Cambiar esta comunicación porque estamos en capa de presentación
								ImprimirAdmDAO.insertarImpresion(strComanda, PrincipalLogueo.habilitaAuditoria);
							}
							else
							{
								Impresion.main(strComanda);
							}
						}//En caso de que NO, no se debería hacer nada

						//Obtenemos el string de la factura que se imprimirá 2 veces
						String strFactura = pedCtrl.generarStrImpresionFactura(idPedido);
						if(Sesion.getModeloImpresion() != 1)
						{
							ImprimirAdmDAO.insertarImpresion(strFactura, PrincipalLogueo.habilitaAuditoria);
							ImprimirAdmDAO.insertarImpresion(strFactura, PrincipalLogueo.habilitaAuditoria);
						}
						else
						{
							//Primera Impresión
							Impresion.main(strFactura);
							//Segunda Impresión
							Impresion.main(strFactura);
						}
						pedCtrl.actualizarImpresionPedido(idPedidoTienda);
					}
				}
				if(!respuesta)
				{
					JOptionPane.showMessageDialog(null, "Se tuvo Error al momento de actualizar el estado del pedido " , "Error al Actualizar Estado Pedido", JOptionPane.ERROR_MESSAGE);
				}
				//Valido si el padre es la pantalla de cocina
				dispose();
			}
		});
		btnContinuar.setBounds(289, 169, 189, 56);
		panelPrincipal.add(btnContinuar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(140, 192, 110, 32);
		panelPrincipal.add(btnCancelar);
		//Lógica para la inicialización
		
		
		JLabel lblTipoPedido = new JLabel("Tipo Pedido");
		lblTipoPedido.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTipoPedido.setBounds(279, 28, 110, 14);
		panelPrincipal.add(lblTipoPedido);
		
		txtTipoPedido = new JTextField();	
		txtTipoPedido.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtTipoPedido.setEditable(false);
		txtTipoPedido.setBounds(387, 25, 141, 20);
		panelPrincipal.add(txtTipoPedido);
		txtTipoPedido.setColumns(10);
		
		idPedidoTienda = idPedido;
		estadoPedido = pedCtrl.obtenerEstadoPedido(idPedidoTienda);
		txtIdPedido.setText(Integer.toString(idPedidoTienda));
		txtEstadoActual.setText(estadoPedido.getDescripcion());
		txtTipoPedido.setText(estadoPedido.getTipoPedido());
		
		JButton btnVerPedido = new JButton("Ver Pedido");
		btnVerPedido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentPedPintar ventPedidoPintar = new VentPedPintar(null, true,idPedidoTienda, false, null);
				ventPedidoPintar.setVisible(true);
			}
		});
		btnVerPedido.setBounds(2, 192, 118, 32);
		panelPrincipal.add(btnVerPedido);
		
		lblImgEstActual = new JLabel("");
		lblImgEstActual.setBounds(2, 51, 75, 56);
		panelPrincipal.add(lblImgEstActual);
		
		lblImgEstObj = new JLabel("");
		lblImgEstObj.setBounds(2, 118, 75, 56);
		panelPrincipal.add(lblImgEstObj);
		ArrayList<EstadoAnterior> estadosAnt = new ArrayList();
		ArrayList<EstadoPosterior> estadosPost = new ArrayList();
		if(anterior)
		{
			
			estadosAnt = pedCtrl.obtenerEstadosAnteriores(estadoPedido.getIdestado());
			initComboBoxEstadosAnt(estadosAnt);
		}
		else if(posterior)
		{
			estadosPost = pedCtrl.obtenerEstadosPosteriores(estadoPedido.getIdestado());
			initComboBoxEstadosPost(estadosPost);
		}
		initImagenesEstado();
	}
	
	public void initComboBoxEstadosAnt(ArrayList<EstadoAnterior> estados)
	{
		
		for(int i = 0; i<estados.size();i++)
		{
			EstadoAnterior fila = (EstadoAnterior)  estados.get(i);
			cmbEstadoObjetivo.addItem(fila);
		}
	}
	
	public void initComboBoxEstadosPost(ArrayList<EstadoPosterior> estados)
	{
		for(int i = 0; i<estados.size();i++)
		{
			EstadoPosterior fila = (EstadoPosterior)  estados.get(i);
			cmbEstadoObjetivo.addItem(fila);
		}
	}
	
	public void initImagenesEstado()
	{
		BufferedImage image = null;
		InputStream in = null;
		ImageIcon imgi = null;
		try
		{
			image = null;
			in = new ByteArrayInputStream(estadoPedido.getImagen());
			image = ImageIO.read(in);
			imgi = new ImageIcon(image.getScaledInstance(60, 60, 0));
			lblImgEstActual.setIcon(imgi);
		}catch(Exception e)
		{
			lblImgEstActual.setText("NO IMAGEN");
		}
		
		if (anterior)
		{
			EstadoAnterior estAnt = (EstadoAnterior) cmbEstadoObjetivo.getSelectedItem();
			if(estAnt == null)
			{
				JOptionPane.showMessageDialog(null, "El estado actual no tiene parametrizado un estado anterior." , "OJO  Operación Inválida", JOptionPane.ERROR_MESSAGE);
				return; 
			}
			Estado est = pedCtrl.obtenerEstado(estAnt.getIdEstadoAnterior());
			try
			{
				image = null;
				in = new ByteArrayInputStream(est.getImagen());
				image = ImageIO.read(in);
				imgi = new ImageIcon(image.getScaledInstance(60, 60, 0));
				lblImgEstObj.setIcon(imgi);
			}catch(Exception e)
			{
				lblImgEstObj.setText("NO IMAGEN");
			}
			
		}else if(posterior)
		{
			EstadoPosterior estPos = (EstadoPosterior) cmbEstadoObjetivo.getSelectedItem();
			Estado est = pedCtrl.obtenerEstado(estPos.getIdEstadoPosterior());
			try
			{
				image = null;
				in = new ByteArrayInputStream(est.getImagen());
				image = ImageIO.read(in);
				imgi = new ImageIcon(image.getScaledInstance(60, 60, 0));
				lblImgEstObj.setIcon(imgi);
			}catch(Exception e)
			{
				lblImgEstObj.setText("NO IMAGEN");
			}
			
			
		}
	}
}
