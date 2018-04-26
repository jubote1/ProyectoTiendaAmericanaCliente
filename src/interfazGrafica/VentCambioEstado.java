package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class VentCambioEstado extends JFrame {

	private JPanel panelPrincipal;
	private JTextField txtIdPedido;
	private JTextField txtEstadoActual;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentCambioEstado frame = new VentCambioEstado();
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
	public VentCambioEstado() {
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
		lblEstadoObjetivo.setBounds(136, 148, 100, 14);
		panelPrincipal.add(lblEstadoObjetivo);
		
		JComboBox cmbEstadoObjetivo = new JComboBox();
		cmbEstadoObjetivo.setBounds(258, 145, 106, 20);
		panelPrincipal.add(cmbEstadoObjetivo);
		
		JButton btnContinuar = new JButton("Continuar");
		btnContinuar.setBounds(111, 235, 89, 23);
		panelPrincipal.add(btnContinuar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(328, 235, 89, 23);
		panelPrincipal.add(btnCancelar);
	}
}
