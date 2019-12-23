package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import capaControlador.ParametrosCtrl;
import capaModelo.Parametro;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Window;
import java.awt.Color;

public class VentSegConfSeguridad extends JDialog {
	
	ParametrosCtrl parCtrl = new ParametrosCtrl(PrincipalLogueo.habilitaAuditoria);
	JCheckBox chckbxManejarSeguridadLocal;
	JLabel lblConfSeguridad;
	private JPanel contentPane;
	Window ventanaPadre;
	private JCheckBox chckbxManejarBiomeRemo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentSegConfSeguridad frame = new VentSegConfSeguridad(null, true);
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
	public VentSegConfSeguridad(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("MANEJO DE SEGURIDAD");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
	    setBounds(100, 100, 300, 237);
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 300, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ventanaPadre = this;
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBounds(10, 11, 264, 150);
		contentPane.add(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Completamos lógica para fijar el valor de la variable
				//Tomamos el valor del chequeo
				String chkSegActualizar = "";
				String chkConfBiometria = "";
				if(chckbxManejarSeguridadLocal.isSelected())
				{
					chkSegActualizar = "S";
				}else
				{
					chkSegActualizar = "N";
				}
				//Revisar tema de configuración biometría
				if(chckbxManejarBiomeRemo.isSelected())
				{
					chkConfBiometria  = "S";
				}else
				{
					chkConfBiometria  = "N";
				}
								
				//Creamos parámetro que nos servirá para la actualización
				Parametro parametroEditado, parametroEditado1 ;
				parametroEditado = new Parametro("CONFSEGURIDAD", 0, chkSegActualizar);
				parametroEditado1 = new Parametro("CONFBIOMEREMOTA", 0, chkConfBiometria);
				//Realizamos la actualización en base de datos
				boolean respuesta = parCtrl.EditarParametro(parametroEditado);
				boolean respuesta1 = parCtrl.EditarParametro(parametroEditado1);
				if(respuesta && respuesta1)
				{
					dispose();
				}else {
					JOptionPane.showMessageDialog(ventanaPadre, "Error al actualización la variable CONFSEGURIDAD en la base de datos.", "Inconvenientes actualizando", JOptionPane.ERROR_MESSAGE);
					
				}
				
			}
		});
		btnConfirmar.setBounds(22, 116, 89, 23);
		panelPrincipal.add(btnConfirmar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(153, 116, 89, 23);
		panelPrincipal.add(btnCancelar);
		
		chckbxManejarSeguridadLocal = new JCheckBox("Manejar Seguridad Local");
		chckbxManejarSeguridadLocal.setBounds(22, 7, 220, 23);
		panelPrincipal.add(chckbxManejarSeguridadLocal);
		
		lblConfSeguridad = new JLabel("");
		lblConfSeguridad.setForeground(Color.RED);
		lblConfSeguridad.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblConfSeguridad.setBounds(22, 74, 232, 31);
		panelPrincipal.add(lblConfSeguridad);
		
		chckbxManejarBiomeRemo = new JCheckBox("Manejar Biometria Remota");
		chckbxManejarBiomeRemo.setBounds(22, 33, 220, 23);
		panelPrincipal.add(chckbxManejarBiomeRemo);
		cargarConfiguracionSeguridad();
	}
	
	public void cargarConfiguracionSeguridad() {
		//Traemos de base de datos el valor del parametro de auditoria
		Parametro parametroAud = parCtrl.obtenerParametro("CONFSEGURIDAD");
		//Extraemos el valor del campo de ValorTexto
		String strParam = parametroAud.getValorTexto();
		//Intentamos realizar el parseo para un dato tipo boolean sino se puede se deja como false
		try
		{
			if(strParam.equals(new String("S")))
			{
				chckbxManejarSeguridadLocal.setSelected(true);
				 lblConfSeguridad.setText("BD LOCAL");
			}else if(strParam.equals(new String("N")))
			{
				chckbxManejarSeguridadLocal.setSelected(false);
				lblConfSeguridad.setText("BD REMOTA");
			}else
			{
				chckbxManejarSeguridadLocal.setSelected(false);
				lblConfSeguridad.setText("BD REMOTA");
				JOptionPane.showMessageDialog(ventanaPadre, "Aparentemente la variable de configuración CONFSEGURIDAD No pudo ser encontrada", "Inconvenientes recuperar Variable", JOptionPane.ERROR_MESSAGE);
			}
			
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(ventanaPadre, "Error al recuperar la variable CONFSEGURIDAD, comuniquese con el administrador.", "Inconvenientes recuperar Variable", JOptionPane.ERROR_MESSAGE);
			chckbxManejarSeguridadLocal.setSelected(false);
		}		
		//Realizamos el cargue de la configuración de biometría local o remota
		
		Parametro parametrBio = parCtrl.obtenerParametro("CONFBIOMEREMOTA");
		//Extraemos el valor del campo de ValorTexto
		strParam = parametrBio.getValorTexto();
		//Intentamos realizar el parseo para un dato tipo boolean sino se puede se deja como false
		try
		{
			if(strParam.equals(new String("S")))
			{
				chckbxManejarBiomeRemo.setSelected(true);
			}else if(strParam.equals(new String("N")))
			{
				chckbxManejarBiomeRemo.setSelected(false);
			}else
			{
				chckbxManejarBiomeRemo.setSelected(false);
				JOptionPane.showMessageDialog(ventanaPadre, "Aparentemente la variable de configuración CONFBIOMEREMOTA No pudo ser encontrada", "Inconvenientes recuperar Variable", JOptionPane.ERROR_MESSAGE);
			}
			
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(ventanaPadre, "Error al recuperar la variable CONFBIOMEREMOTA, comuniquese con el administrador.", "Inconvenientes recuperar Variable", JOptionPane.ERROR_MESSAGE);
			chckbxManejarBiomeRemo.setSelected(false);
		}		
		
		
	}
}
