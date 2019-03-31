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
		setTitle("CAMBIO ESTADO");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0,0, 544, 406);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 544, 406);
		panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelPrincipal);
		panelPrincipal.setLayout(null);
		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
		JLabel lblIdPedido = new JLabel("Id Pedido");
		lblIdPedido.setBounds(36, 50, 100, 14);
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
		txtIdPedido.setEditable(false);
		txtIdPedido.setBounds(156, 47, 165, 20);
		panelPrincipal.add(txtIdPedido);
		txtIdPedido.setColumns(10);
		
		JLabel lblEstadoActual = new JLabel("Estado Actual");
		lblEstadoActual.setBounds(36, 138, 100, 14);
		panelPrincipal.add(lblEstadoActual);
		
		txtEstadoActual = new JTextField();
		txtEstadoActual.setEditable(false);
		txtEstadoActual.setBounds(156, 135, 165, 20);
		panelPrincipal.add(txtEstadoActual);
		txtEstadoActual.setColumns(10);
		
		JLabel lblEstadoObjetivo = new JLabel("Siguiente Estado");
		lblEstadoObjetivo.setBounds(36, 207, 100, 14);
		panelPrincipal.add(lblEstadoObjetivo);
		
		cmbEstadoObjetivo = new JComboBox();
		cmbEstadoObjetivo.setBounds(156, 204, 165, 20);
		panelPrincipal.add(cmbEstadoObjetivo);
		
		JButton btnContinuar = new JButton("Continuar");
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
//						ReportesCtrl repCtrl = new ReportesCtrl();
//						repCtrl.generarFactura(idPedido);
						String strFactura = pedCtrl.generarStrImpresionFactura(idPedidoTienda);
						ImpresionBK imp = new ImpresionBK();
						imp.imprimirFactura(strFactura);
					}
				}
				if(!respuesta)
				{
					JOptionPane.showMessageDialog(null, "Se tuvo Error al momento de actualizar el estado del pedido " , "Error al Actualizar Estado Pedido", JOptionPane.ERROR_MESSAGE);
				}
				dispose();
			}
		});
		btnContinuar.setBounds(36, 287, 113, 33);
		panelPrincipal.add(btnContinuar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(390, 287, 110, 32);
		panelPrincipal.add(btnCancelar);
		//Lógica para la inicialización
		
		
		JLabel lblTipoPedido = new JLabel("Tipo Pedido");
		lblTipoPedido.setBounds(36, 97, 100, 14);
		panelPrincipal.add(lblTipoPedido);
		
		txtTipoPedido = new JTextField();	
		txtTipoPedido.setEditable(false);
		txtTipoPedido.setBounds(155, 94, 166, 20);
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
				VentPedPintar ventPedidoPintar = new VentPedPintar(null, true,idPedidoTienda);
				ventPedidoPintar.setVisible(true);
			}
		});
		btnVerPedido.setBounds(213, 287, 118, 32);
		panelPrincipal.add(btnVerPedido);
		
		lblImgEstActual = new JLabel("");
		lblImgEstActual.setBounds(341, 138, 75, 56);
		panelPrincipal.add(lblImgEstActual);
		
		lblImgEstObj = new JLabel("");
		lblImgEstObj.setBounds(341, 220, 75, 56);
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
