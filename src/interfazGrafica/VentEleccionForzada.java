package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import capaModelo.EleccionForzada;
import capaModelo.Pregunta;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.LineBorder;

import capaControlador.ParametrosProductoCtrl;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentEleccionForzada extends JFrame {

	private JPanel contenedorPrincipal;
	private static int numPreguntas;
	private static int preguntaActual = 0;
	private ArrayList<Pregunta> preguntasPantalla;
	JLabel lblDescripcionPregunta;
	private JPanel panelRespuestas1;
	private JPanel panelRespuestas2;
	JButton jButElecciones1;
	JButton jButElecciones2;
	private JLabel lblRespuetas1;
	private JLabel lblRespuestas2;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//No tiene sentido instanciarlos de por si mismo.
					//VentEleccionForzada frame = new VentEleccionForzada();
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentEleccionForzada(ArrayList<Pregunta> preguntas) {
		setTitle("ELECCI\u00D3N");
		numPreguntas = preguntas.size();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 684, 610);
		contenedorPrincipal = new JPanel();
		contenedorPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contenedorPrincipal);
		contenedorPrincipal.setLayout(null);
		
		panelRespuestas1 = new JPanel();
		panelRespuestas1.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panelRespuestas1.setBounds(25, 41, 621, 224);
		contenedorPrincipal.add(panelRespuestas1);
		panelRespuestas1.setLayout(new GridLayout(0, 4, 0, 0));
		
		lblDescripcionPregunta = new JLabel("New label");
		lblDescripcionPregunta.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblDescripcionPregunta.setBounds(10, 540, 318, 32);
		contenedorPrincipal.add(lblDescripcionPregunta);
		
		panelRespuestas2 = new JPanel();
		panelRespuestas2.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		panelRespuestas2.setBounds(25, 299, 621, 241);
		contenedorPrincipal.add(panelRespuestas2);
		panelRespuestas2.setLayout(new GridLayout(0, 4, 0, 0));
		
		lblRespuetas1 = new JLabel("New label");
		lblRespuetas1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRespuetas1.setBounds(25, 11, 621, 14);
		contenedorPrincipal.add(lblRespuetas1);
		
		lblRespuestas2 = new JLabel("New label");
		lblRespuestas2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblRespuestas2.setBounds(25, 274, 621, 14);
		contenedorPrincipal.add(lblRespuestas2);
		
		JButton btnConfirmarPregunta = new JButton("Confirmar Pregunta");
		btnConfirmarPregunta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CargarEleccionForzada(preguntaActual);
			}
		});
		btnConfirmarPregunta.setBounds(342, 547, 304, 23);
		contenedorPrincipal.add(btnConfirmarPregunta);
		//Debemos de definir las cosas para el cargue de la primera pregunta
		numPreguntas = preguntas.size();
		preguntasPantalla = preguntas;
		CargarEleccionForzada(preguntaActual);
	}
	
	public void CargarEleccionForzada(int pregunta)
	{
		//En caso de cumplirse esto deberemos de pasar el pedido a la pantalla tomador de pedidos
		if (preguntaActual == numPreguntas)
		{
			//el último paso a realizar es liberar el objeto
			dispose();
		}
		//Se realizan las acciones de display para elección de la información
		Pregunta pregActual = preguntasPantalla.get(preguntaActual);
		//Personalizamos el comportamiento de la pantalla
		//Titulo
		setTitle(pregActual.getTituloPregunta());
		lblDescripcionPregunta.setText(pregActual.getDescripcion());
		//Tomamos lógica para validar si la Pregunta permite Division o no
		int intPermiteDividir = pregActual.getPermiteDividir();
		boolean permiteDividir;
		if(intPermiteDividir == 1)
		{
			permiteDividir = true;
		}else
		{
			permiteDividir = false;
		}
		ArrayList<EleccionForzada> elecciones = new ArrayList();
		ParametrosProductoCtrl parProductoCtrl = new ParametrosProductoCtrl();
		elecciones = parProductoCtrl.obtEleccionesForzadas(pregActual.getIdPregunta());
		//Adicionamos los botones con las preguntas forzadas
		if(permiteDividir)
		{
			for(int j = 0; j < elecciones.size(); j++)
			{
				EleccionForzada eleccion = elecciones.get(j);
				jButElecciones1 = new JButton(eleccion.getDescripcion());
				jButElecciones2 = new JButton(eleccion.getDescripcion());
				jButElecciones1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						
						//Actividad cuando se selecciona la primera mitad;
						
					}
				});
				jButElecciones2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						
						//Actividad cuando se selecciona la primera mitad;
						
					}
				});
				panelRespuestas1.add(jButElecciones1);
				panelRespuestas2.add(jButElecciones2);
			}
			preguntaActual++;
		}
		else
		{
			for(int j = 0; j < elecciones.size(); j++)
			{
				EleccionForzada eleccion = elecciones.get(j);
				jButElecciones1 = new JButton(eleccion.getDescripcion());
				jButElecciones1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						
						//Actividad cuando se selecciona la primera mitad;
						
					}
				});
				panelRespuestas1.add(jButElecciones1);
			}
			preguntaActual++;
		}
		
	}
}
