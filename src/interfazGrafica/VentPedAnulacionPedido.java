package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import capaControlador.PedidoCtrl;
import capaModelo.MotivoAnulacionPedido;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentPedAnulacionPedido extends JDialog {

	private final JPanel contentPanel = new JPanel();
	JComboBox cmbMotivoAnulacion;
	JLabel lblAlerta;
	JTextArea textAreaObservacion;
	private PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	private JDialog ventanaPadre;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentPedAnulacionPedido dialog = new VentPedAnulacionPedido(null, false,0,false);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentPedAnulacionPedido(VentPedTomarPedidos parent, boolean modal, int idDetalleEliminar, boolean salirVentana) {
		super(parent, modal);
		setBounds(100, 100, 450, 325);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 450, 325);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		ventanaPadre = this;
		cmbMotivoAnulacion = new JComboBox();
		cmbMotivoAnulacion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MotivoAnulacionPedido motAnu = (MotivoAnulacionPedido)cmbMotivoAnulacion.getSelectedItem();
				if (motAnu.getDescuentaInventario().equals(new String("S")))
				{
					lblAlerta.setText("Se reintegrará al inventario");
				}else
				{
					lblAlerta.setText("NO SE REINTEGRARÁ AL INVENTARIO");
				}
			}
		});
		cmbMotivoAnulacion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cmbMotivoAnulacion.setBounds(40, 39, 360, 40);
		contentPanel.add(cmbMotivoAnulacion);
		
		JLabel lblMotivoDeAnulacin = new JLabel("MOTIVO DE ANULACI\u00D3N");
		lblMotivoDeAnulacin.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMotivoDeAnulacin.setBounds(38, 14, 362, 14);
		contentPanel.add(lblMotivoDeAnulacin);
		
		JLabel lblObservacin = new JLabel("Observaci\u00F3n");
		lblObservacin.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblObservacin.setBounds(40, 98, 362, 14);
		contentPanel.add(lblObservacin);
		
		textAreaObservacion = new JTextArea();
		textAreaObservacion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textAreaObservacion.setLineWrap(true);
		textAreaObservacion.setBounds(42, 123, 360, 94);
		contentPanel.add(textAreaObservacion);
		
		lblAlerta = new JLabel("");
		lblAlerta.setForeground(Color.RED);
		lblAlerta.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblAlerta.setBounds(10, 228, 414, 25);
		contentPanel.add(lblAlerta);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Confirmar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String validacion =  validarAnulacionPedido();
						if(!validacion.equals(new String("")))
						{
							JOptionPane.showMessageDialog(ventanaPadre, validacion, "Error en Anulación Pedido", JOptionPane.ERROR_MESSAGE);
							return;
						}
						MotivoAnulacionPedido motAnu = (MotivoAnulacionPedido)cmbMotivoAnulacion.getSelectedItem();
						String observacion = textAreaObservacion.getText();
						//Del formulario padre ejecutamos la anulación del item de inventario
						if(idDetalleEliminar > 0)
						{
							parent.frameAnulaItemPedido(motAnu, observacion, idDetalleEliminar);
						}else
						{
							parent.frameAnulaPedido(motAnu, observacion, salirVentana);
						}
						
						dispose();
						
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		initcmbMotivoAnulacion();
	}
	
	public void initcmbMotivoAnulacion()
	{
		ArrayList<MotivoAnulacionPedido> motAnulacion = pedCtrl.obtenerMotivosAnulacion();
		for(int i = 0; i < motAnulacion.size();i++)
		{
			MotivoAnulacionPedido fila = (MotivoAnulacionPedido)  motAnulacion.get(i);
			cmbMotivoAnulacion.addItem(fila);
		}
	}
	
	public String validarAnulacionPedido()
	{
		String respuesta = ""; 
		MotivoAnulacionPedido motAnu = (MotivoAnulacionPedido)cmbMotivoAnulacion.getSelectedItem();
		if(!motAnu.getDescripcion().equals(new String("")))
		{
			
		}else
		{
			respuesta = "No seleccionó un motivo de Anulación del Pedido.";
		}
		if (textAreaObservacion.getText().contentEquals(new String("")))
		{
			respuesta = respuesta + " Se debe incluir una observación de la anulación del Pedido.";
		}
		if (textAreaObservacion.getText().length()>100)
		{
			respuesta = respuesta + " La observación no puede sobrepasar los 100 caracteres.";
		}
		return(respuesta);
	}
}
