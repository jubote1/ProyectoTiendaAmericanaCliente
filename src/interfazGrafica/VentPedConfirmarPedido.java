package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

public class VentPedConfirmarPedido extends JDialog {

	private final JPanel panelPrincipal = new JPanel();
	private JTextField txtCliente;
	private JTextField txtTotalPedido;
	private JTextField txtDescuento;
	private JTextField txtNumeroPedido;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			VentPedConfirmarPedido dialog = new VentPedConfirmarPedido("",0,0,0);
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public VentPedConfirmarPedido(String nombreCliente, double totalPed, double totalDesc, int numeroPed, javax.swing.JFrame parent, boolean modal) {
		super(parent, modal);
		setTitle("CONFIRMAR PEDIDO");
		setBounds(100, 100, 651, 418);
		getContentPane().setLayout(new BorderLayout());
		panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(panelPrincipal, BorderLayout.CENTER);
		panelPrincipal.setLayout(null);
		{
			JLabel lblInformacionParaConfirmacin = new JLabel("INFORMACION PARA CONFIRMACI\u00D3N DEL PEDIDO");
			lblInformacionParaConfirmacin.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblInformacionParaConfirmacin.setBounds(167, 21, 314, 14);
			panelPrincipal.add(lblInformacionParaConfirmacin);
		}
		{
			JLabel lblCliente = new JLabel("Cliente");
			lblCliente.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblCliente.setBounds(87, 78, 74, 14);
			panelPrincipal.add(lblCliente);
		}
		{
			txtCliente = new JTextField();
			txtCliente.setEditable(false);
			txtCliente.setBounds(248, 76, 288, 20);
			panelPrincipal.add(txtCliente);
			txtCliente.setColumns(10);
			txtCliente.setText(nombreCliente);
		}
		{
			JLabel lblTotalPedido = new JLabel("Total Pedido");
			lblTotalPedido.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblTotalPedido.setBounds(87, 129, 88, 14);
			
			panelPrincipal.add(lblTotalPedido);
		}
		{
			JLabel lblDescuento = new JLabel("Total Descuento");
			lblDescuento.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblDescuento.setBounds(87, 172, 133, 14);
			panelPrincipal.add(lblDescuento);
		}
		{
			JLabel lblNumeroPedido = new JLabel("Numero Pedido");
			lblNumeroPedido.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblNumeroPedido.setBounds(87, 222, 114, 14);
			panelPrincipal.add(lblNumeroPedido);
		}
		{
			txtTotalPedido = new JTextField();
			txtTotalPedido.setEditable(false);
			txtTotalPedido.setBounds(248, 127, 114, 20);
			panelPrincipal.add(txtTotalPedido);
			txtTotalPedido.setColumns(10);
			txtTotalPedido.setText(Double.toString(totalPed));
		}
		{
			txtDescuento = new JTextField();
			txtDescuento.setEditable(false);
			txtDescuento.setBounds(248, 170, 114, 20);
			panelPrincipal.add(txtDescuento);
			txtDescuento.setColumns(10);
			txtDescuento.setText(Double.toString(totalDesc));
		}
		{
			txtNumeroPedido = new JTextField();
			txtNumeroPedido.setEditable(false);
			txtNumeroPedido.setBounds(248, 220, 114, 20);
			panelPrincipal.add(txtNumeroPedido);
			txtNumeroPedido.setColumns(10);
			txtNumeroPedido.setText(Integer.toString(numeroPed));
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
