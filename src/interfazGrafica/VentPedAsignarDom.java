package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

import capaControlador.EmpleadoCtrl;
import capaModelo.Estado;
import capaModelo.EstadoAnterior;
import capaModelo.Usuario;

import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentPedAsignarDom extends JDialog {

	private final JPanel contentPanel = new JPanel();
	DomiciliarioListModel domiListModel = new DomiciliarioListModel();
	ArrayList<Usuario> domiciliarios;
	private EmpleadoCtrl empCtrl = new EmpleadoCtrl(PrincipalLogueo.habilitaAuditoria);
	JList listAsignarDomiciliario;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentPedAsignarDom dialog = new VentPedAsignarDom(null, false);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public VentPedAsignarDom(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("ASIGNAR DOMICILIARIO A PEDIDOS");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Asignar Domiciliario a Pedido");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(87, 11, 218, 14);
		contentPanel.add(lblNewLabel);
		//Vamos  a adicionar los domiciliarios del sistema
		domiciliarios = empCtrl.obtenerDomiciliarios();
		//Realizamos un llenado del modelo con los domiciliarios
		llenarModeloDomiciliarios();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(80, 54, 268, 139);
		contentPanel.add(scrollPane);
		
		listAsignarDomiciliario = new JList();
		scrollPane.setViewportView(listAsignarDomiciliario);
		listAsignarDomiciliario.setBorder(new LineBorder(new Color(0, 0, 0), 6));
		listAsignarDomiciliario.setModel(domiListModel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						//Capturamos la seleccion
						int selection = listAsignarDomiciliario.getSelectedIndex();
						if (selection!=-1) 
						{
						     //Obtenemos el objeto estado seleccionado 
							 Usuario usuTemp = (Usuario)domiListModel.getDomiciliario(selection);
							 VentPedComandaPedidos.idUsuarioTemp = usuTemp.getIdUsuario();
							 VentPedComandaPedidos.usuarioTemp = usuTemp.getNombreUsuario();
							 VentPedComandaPedidos.indUsuarioTemp = true;
							 dispose();
						 			     
						 }
						else
						{
							JOptionPane.showMessageDialog(null, "Debe seleccionar un Domiciliario", "Error en Selección", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	public void llenarModeloDomiciliarios()
	{
		for(int i = 0; i < domiciliarios.size(); i++)
		{
			Usuario domiTemp = domiciliarios.get(i);
			domiListModel.addDomiciliario(domiTemp);;
		}
	}
}
