package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import capaControlador.ParametrosDireccionCtrl;
import capaControlador.PedidoCtrl;
import capaControlador.ReportesCtrl;
import capaModelo.Estado;
import capaModelo.EstadoAnterior;
import capaModelo.EstadoPosterior;
import capaModelo.Municipio;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JButton;
import java.awt.event.ActionListener;
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
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentPedCambioEstado frame = new VentPedCambioEstado(0, false, false, null, true);
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
	public VentPedCambioEstado(int idPedido, boolean anterior, boolean posterior, java.awt.Frame parent, boolean modal ) {
		super(parent, modal);
		this.anterior = anterior;
		this.posterior = posterior;
		setTitle("CAMBIO ESTADO");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 480, 375);
		panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		JLabel lblIdPedido = new JLabel("Id Pedido");
		lblIdPedido.setBounds(136, 46, 85, 14);
		panelPrincipal.add(lblIdPedido);
		
		txtIdPedido = new JTextField();
		txtIdPedido.setEditable(false);
		txtIdPedido.setBounds(258, 43, 106, 20);
		panelPrincipal.add(txtIdPedido);
		txtIdPedido.setColumns(10);
		
		JLabel lblEstadoActual = new JLabel("Estado Actual");
		lblEstadoActual.setBounds(136, 96, 100, 14);
		panelPrincipal.add(lblEstadoActual);
		
		txtEstadoActual = new JTextField();
		txtEstadoActual.setEditable(false);
		txtEstadoActual.setBounds(258, 93, 106, 20);
		panelPrincipal.add(txtEstadoActual);
		txtEstadoActual.setColumns(10);
		
		JLabel lblEstadoObjetivo = new JLabel("Estado Objetivo");
		lblEstadoObjetivo.setBounds(136, 181, 100, 14);
		panelPrincipal.add(lblEstadoObjetivo);
		
		cmbEstadoObjetivo = new JComboBox();
		cmbEstadoObjetivo.setBounds(258, 178, 106, 20);
		panelPrincipal.add(cmbEstadoObjetivo);
		
		JButton btnContinuar = new JButton("Continuar");
		btnContinuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EstadoAnterior estAnt;
				EstadoPosterior estPos;
				PedidoCtrl pedCtrl = new PedidoCtrl();
				boolean respuesta = false;
				if (anterior)
				{
					estAnt = (EstadoAnterior) cmbEstadoObjetivo.getSelectedItem();
					respuesta = pedCtrl.ActualizarEstadoPedido(idPedidoTienda,estadoPedido.getIdestado() , estAnt.getIdEstadoAnterior());
				}else if(posterior)
				{
					estPos = (EstadoPosterior) cmbEstadoObjetivo.getSelectedItem();
					respuesta = pedCtrl.ActualizarEstadoPedido(idPedidoTienda,estadoPedido.getIdestado() , estPos.getIdEstadoPosterior());
					if(estPos.isImprimeEstPosterior())
					{
						ReportesCtrl repCtrl = new ReportesCtrl();
						repCtrl.generarFactura(idPedido);
					}
				}
				if(!respuesta)
				{
					JOptionPane.showMessageDialog(null, "Se tuvo Error al momento de actualizar el estado del pedido " , "Error al Actualizar Estado Pedido", JOptionPane.ERROR_MESSAGE);
				}
				dispose();
			}
		});
		btnContinuar.setBounds(36, 233, 113, 33);
		panelPrincipal.add(btnContinuar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(319, 235, 110, 32);
		panelPrincipal.add(btnCancelar);
		//L�gica para la inicializaci�n
		
		
		JLabel lblTipoPedido = new JLabel("Tipo Pedido");
		lblTipoPedido.setBounds(136, 132, 100, 14);
		panelPrincipal.add(lblTipoPedido);
		
		txtTipoPedido = new JTextField();	
		txtTipoPedido.setEditable(false);
		txtTipoPedido.setBounds(258, 129, 106, 20);
		panelPrincipal.add(txtTipoPedido);
		txtTipoPedido.setColumns(10);
		
		idPedidoTienda = idPedido;
		PedidoCtrl pedCtrl = new PedidoCtrl();
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
		btnVerPedido.setBounds(174, 234, 118, 32);
		panelPrincipal.add(btnVerPedido);
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
}