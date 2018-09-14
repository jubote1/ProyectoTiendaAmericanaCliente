package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import capaControlador.AutenticacionCtrl;
import capaControlador.EmpleadoCtrl;
import capaControlador.OperacionesTiendaCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.FechaSistema;
import capaModelo.Usuario;

public class PrincipalLogueo extends JFrame {

	private JPanel contentPane;
	private JTextField textUsuario;
	private JPasswordField jpassClave;
	static int idUsuario = 0;
	private JTextField txtFechaSistema;
	private JTextField txtFechaUltCierre;
	private JTextField txtEstadoCierre;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrincipalLogueo frame = new PrincipalLogueo();
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
	public PrincipalLogueo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 329);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNombreSistema = new JLabel("SISTEMA TIENDA PIZZA AMERICANA");
		lblNombreSistema.setFont(new Font("Traditional Arabic", Font.BOLD, 17));
		lblNombreSistema.setBounds(64, 11, 338, 40);
		contentPane.add(lblNombreSistema);
		
				
		JLabel lbUsuario = new JLabel("Usuario:");
		lbUsuario.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbUsuario.setBounds(205, 77, 59, 24);
		contentPane.add(lbUsuario);
		
		JLabel lblContrasena = new JLabel("Contrase\u00F1a:");
		lblContrasena.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblContrasena.setBounds(203, 115, 72, 14);
		contentPane.add(lblContrasena);
		
		textUsuario = new JTextField();
		textUsuario.setBounds(303, 76, 121, 20);
		contentPane.add(textUsuario);
		textUsuario.setColumns(10);
		
		jpassClave = new JPasswordField();
		jpassClave.setBounds(303, 112, 121, 19);
		contentPane.add(jpassClave);
		
		JButton btnAutenticar = new JButton("Autenticar");
		PedidoCtrl pedCtrl = new PedidoCtrl();
		FechaSistema fechasSistema = pedCtrl.obtenerFechasSistema();
		boolean estaAperturado = pedCtrl.isSistemaAperturado();
		btnAutenticar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				char[] clave = jpassClave.getPassword();
				String claveFinal = new String(clave);
				String usuario = textUsuario.getText();
				Usuario objUsuario;
				if(usuario.equals(""))
				{
					JOptionPane.showMessageDialog(null, "Usuario en Blanco", "Debes Ingresar el nombre de Usuario", JOptionPane.ERROR_MESSAGE);
				}else
				{
					AutenticacionCtrl aut = new AutenticacionCtrl();
					objUsuario = new Usuario(0,usuario, claveFinal, "", 0,"" , false);
					boolean  resultado = aut.autenticarUsuario(objUsuario);
					
					if(resultado)
					{
						//Cuando se supera el logueo asignamos la variable est�tica de idUsuario.
						idUsuario = objUsuario.getIdUsuario();
						JOptionPane.showConfirmDialog(null,  "Bienvenido al Sistema " + objUsuario.getNombreLargo() , "Ingresaste al Sistema!!", JOptionPane.OK_OPTION);
						if(!estaAperturado)
						{
							//Llamamos m�todo para validar el estado de la fecha respecto a la �ltima apertura.
							OperacionesTiendaCtrl operCtrl = new OperacionesTiendaCtrl();
							String fechaMayor = operCtrl.validarEstadoFechaSistema();
							String fechaAumentada = operCtrl.aumentarFecha(fechasSistema.getFechaApertura());
							Object seleccion = JOptionPane.showInputDialog(
									   null,
									   "�El d�a no ha sido aperturado, desea abrirlo? Seleccione opcion",
									   "Selector de opciones",
									   JOptionPane.QUESTION_MESSAGE,
									   null,  // null para icono defecto
									   new Object[] { fechaAumentada,fechaMayor }, 
									   fechaAumentada);
							if(seleccion == null)
							{
								dispose();
								return;
							}
							else
							{
								//Realizar cambio de la fecha
								operCtrl.realizarAperturaDia(seleccion.toString());
							}
						}
						//Fijamos el usuario que se est� loguendo
						Sesion.setUsuario(usuario);
						if(objUsuario.getTipoInicio().equals("Ventana Men�s"))
						{
							VentPrincipal ventPrincipal = new VentPrincipal();
							ventPrincipal.setVisible(true);
						}else if(objUsuario.getTipoInicio().equals("Maestro Pedidos"))
						{
							VentPedTomarPedidos ventPrincipal = new VentPedTomarPedidos();
							ventPrincipal.setVisible(true);
						}else if(objUsuario.getTipoInicio().equals("Comanda Pedidos"))
						{
							VentPedComandaPedidos ventPrincipal = new VentPedComandaPedidos();
							ventPrincipal.setVisible(true);
						}
						dispose();
					}else
					{
						JOptionPane.showMessageDialog(null, "Usuario y/o Clave Incorrecta", "Usuario y/o Clave Incorrecta", JOptionPane.ERROR_MESSAGE);
						textUsuario.setText("");
						jpassClave.setText("");
					}
				}
			}
		});
		btnAutenticar.setBounds(68, 258, 115, 24);
		contentPane.add(btnAutenticar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(249, 259, 111, 23);
		contentPane.add(btnCancelar);
		
		JLabel lblImagen = new JLabel("");
		lblImagen.setBounds(0, 62, 198, 126);
		//Adicionamos imagen
		ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/LogoPizzaAmericana.png"));
		Image imagen = icon.getImage();
		ImageIcon iconoEscalado = new ImageIcon (imagen.getScaledInstance(198,126,Image.SCALE_SMOOTH));
		lblImagen.setIcon(iconoEscalado);
		contentPane.add(lblImagen); 
		
		JLabel lblFechaSistema = new JLabel("Fecha Sistema");
		lblFechaSistema.setBounds(10, 202, 96, 14);
		contentPane.add(lblFechaSistema);
		
		JLabel lblFechaUltCierre = new JLabel("Fecha Ult Cierre");
		lblFechaUltCierre.setBounds(226, 202, 92, 14);
		contentPane.add(lblFechaUltCierre);
		
		txtFechaSistema = new JTextField();
		txtFechaSistema.setEditable(false);
		txtFechaSistema.setBounds(112, 199, 104, 20);
		contentPane.add(txtFechaSistema);
		txtFechaSistema.setColumns(10);
		txtFechaSistema.setText(fechasSistema.getFechaApertura());
		
		txtFechaUltCierre = new JTextField();
		txtFechaUltCierre.setEditable(false);
		txtFechaUltCierre.setColumns(10);
		txtFechaUltCierre.setBounds(320, 199, 104, 20);
		txtFechaUltCierre.setText(fechasSistema.getFechaUltimoCierre());
		contentPane.add(txtFechaUltCierre);
		
		txtEstadoCierre = new JTextField();
		txtEstadoCierre.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtEstadoCierre.setEditable(false);
		txtEstadoCierre.setBounds(20, 227, 404, 20);
		contentPane.add(txtEstadoCierre);
		txtEstadoCierre.setColumns(10);
		if(estaAperturado)
		{
			txtEstadoCierre.setText("El d�a en cuesti�n ya se encuentra abierto.");
		}else
		{
			txtEstadoCierre.setText("El d�a en cuesti�n no se encuentra abierto.");
		}
	}
	
	
}
	
