package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.parser.ParserDelegator;

import capaControlador.AutenticacionCtrl;
import capaControlador.BiometriaCtrl;
import capaControlador.InventarioCtrl;
import capaControlador.OperacionesTiendaCtrl;
import capaControlador.ParametrosCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.DetallePedido;
import capaModelo.FechaSistema;
import capaModelo.Parametro;
import capaModelo.TipoPedido;
import capaModelo.Usuario;

import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Color;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;

import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

//Tema de Biometria Digital Persona
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPErrorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPErrorEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;

//Implementamos runnable para crear un hilo que al momento de laconfirmación del pedido se cree un hilo paralelo
//que se encargue de agregar la información de consumo de inventario
public class VentSegAutenticacionLogueoRapido extends JDialog {

	private JPanel contentenorFinPago;
	private final Action action = new SwingAction();
	private JPasswordField contraRapida;
	private AutenticacionCtrl autCtrl = new AutenticacionCtrl(PrincipalLogueo.habilitaAuditoria);
	PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	OperacionesTiendaCtrl operCtrl = new OperacionesTiendaCtrl(PrincipalLogueo.habilitaAuditoria);
	ParametrosCtrl parCtrl = new ParametrosCtrl(PrincipalLogueo.habilitaAuditoria);
	Thread hiloValidacion;
	//Variable que almacena el tipo de presnetación qeu tiene actualmente el sistema.
		FechaSistema fechasSistema;
		String fechaMayor;
		Window ventanaPadre;
	//En una variable estática guardaremos el valor de la persona autorizada
	public static Usuario usuarioAutorizado;
	//Varible que permite iniciar el dispositivo de lector de huella conectado
		//con sus distintos metodos.
		private DPFPCapture Lector = DPFPGlobal.getCaptureFactory().createCapture();
		
		//Varible que permite establecer las capturas de la huellas, para determina sus caracteristicas
		//y poder estimar la creacion de un template de la huella para luego poder guardarla
		private DPFPEnrollment Reclutador = DPFPGlobal.getEnrollmentFactory().createEnrollment();
		
		//Esta variable tambien captura una huella del lector y crea sus caracteristcas para auntetificarla
		//o verificarla con alguna guarda en la BD
		private DPFPVerification Verificador = DPFPGlobal.getVerificationFactory().createVerification();
		
		//Variable que para crear el template de la huella luego de que se hallan creado las caracteriticas
		//necesarias de la huella si no ha ocurrido ningun problema
		private DPFPTemplate template;
		
		public static String TEMPLATE_PROPERTY = "template";
		public DPFPFeatureSet featuresinscripcion;
		public DPFPFeatureSet featuresverificacion;
		
		//Otras variables para la identificación biométrica
		ByteArrayInputStream datosHuella;
	    Integer sizeHuella;
	    Usuario usuarioIdentificado;
	    String accionRegistro;
	    //Objeto de capa controladora que requerimos
	    BiometriaCtrl bioCtrl = new BiometriaCtrl(PrincipalLogueo.habilitaAuditoria);
	    JLabel lblHuella;
	  //Variable para controlar biometria remota
		String biometriaRemota = "";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentSegAutenticacionLogueoRapido frame = new VentSegAutenticacionLogueoRapido( null, true);
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
	public VentSegAutenticacionLogueoRapido(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		setTitle("INGRESO R\u00C1PIDO APLICACI\u00D3N");
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setDefaultCloseOperation(0);
		//setBounds(0,0, 563, 300);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		//setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 400, 520);
	    setBounds(0, 0, 400, 560);
		contentenorFinPago = new JPanel();
		contentenorFinPago.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentenorFinPago);
		contentenorFinPago.setLayout(null);
		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
		ventanaPadre = this;
		JButton btnNum_1 = new JButton("1");
		btnNum_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contraRapida.setText(contraRapida.getText()+"1");
			}
		});
		btnNum_1.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_1.setBounds(66, 253, 70, 70);
		btnNum_1.setBackground(new Color(45,107,113));
		btnNum_1.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_1);
		
		JButton btnNum_2 = new JButton("2");
		btnNum_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contraRapida.setText(contraRapida.getText()+"2");
			}
		});
		btnNum_2.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_2.setBounds(146, 253, 70, 70);
		btnNum_2.setBackground(new Color(45,107,113));
		btnNum_2.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_2);
		
		JButton btnNum_3 = new JButton("3");
		btnNum_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contraRapida.setText(contraRapida.getText()+"3");
			}
		});
		btnNum_3.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_3.setBounds(226, 253, 70, 70);
		btnNum_3.setBackground(new Color(45,107,113));
		btnNum_3.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_3);
		
		JButton btnNum_4 = new JButton("4");
		btnNum_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contraRapida.setText(contraRapida.getText()+"4");
			}
		});
		btnNum_4.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_4.setBounds(66, 174, 70, 70);
		btnNum_4.setBackground(new Color(45,107,113));
		btnNum_4.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_4);
		
		JButton btnNum_5 = new JButton("5");
		btnNum_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contraRapida.setText(contraRapida.getText()+"5");
			}
		});
		btnNum_5.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_5.setBounds(146, 174, 70, 70);
		btnNum_5.setBackground(new Color(45,107,113));
		btnNum_5.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_5);
		
		JButton btnNum_6 = new JButton("6");
		btnNum_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contraRapida.setText(contraRapida.getText()+"6");
			}
		});
		btnNum_6.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_6.setBounds(226, 174, 70, 70);
		btnNum_6.setBackground(new Color(45,107,113));
		btnNum_6.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_6);
		
		JButton btnNum_7 = new JButton("7");
		btnNum_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				contraRapida.setText(contraRapida.getText()+"7");
			}
		});
		btnNum_7.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_7.setBounds(66, 93, 70, 70);
		btnNum_7.setBackground(new Color(45,107,113));
		btnNum_7.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_7);
		
		JButton btnNum_8 = new JButton("8");
		btnNum_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contraRapida.setText(contraRapida.getText()+"8");
			}
		});
		btnNum_8.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_8.setBounds(146, 93, 70, 70);
		btnNum_8.setBackground(new Color(45,107,113));
		btnNum_8.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_8);
		
		JButton btnNum_9 = new JButton("9");
		btnNum_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contraRapida.setText(contraRapida.getText()+"9");
			}
		});
		btnNum_9.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_9.setBounds(226, 93, 70, 70);
		btnNum_9.setBackground(new Color(45,107,113));
		btnNum_9.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_9);
		
		JButton btnNum_0 = new JButton("0");
		btnNum_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contraRapida.setText(contraRapida.getText()+"0");
			}
		});
		btnNum_0.setFont(new Font("Calibri", Font.BOLD, 24));
		btnNum_0.setBounds(146, 334, 70, 70);
		btnNum_0.setBackground(new Color(45,107,113));
		btnNum_0.setForeground(new Color(255,255,255));
		contentenorFinPago.add(btnNum_0);
		//Instanciamos el hilo que se va a encargar del descuento de inventario
		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String claveRapida = contraRapida.getText();
				Usuario usuLogueado = autCtrl.validarAutenticacionRapida(claveRapida);
				if((usuLogueado.getIdUsuario() > 0) && (usuLogueado.isAdministrador()))
				{
					usuarioAutorizado = usuLogueado;
//					if(usuLogueado.getCaducado() == 1)
//					{
//						JOptionPane.showMessageDialog(ventanaPadre, "El usuario está caducado deberá asignar de nuevo la clave rápida!!", "Renovación de clave rápida", JOptionPane.OK_OPTION);
//						VentSegAsignacionClaveRapida asigClaveRapida = new VentSegAsignacionClaveRapida(null, usuLogueado, true); 
//						asigClaveRapida.setVisible(true);
//						// Se desplegará una nueva pantalla que tomará la asignación de Clave
//					}
					
					
				}else
				{
					usuarioAutorizado = new Usuario(0, "" , "", "", 0,
							"", false);
				}
				stop();
				dispose();
			}
		});
		btnIngresar.setFont(new Font("Calibri", Font.BOLD, 35));
		btnIngresar.setBounds(95, 415, 182, 53);
		contentenorFinPago.add(btnIngresar);
		
				
		contraRapida = new JPasswordField();
		contraRapida.setHorizontalAlignment(SwingConstants.RIGHT);
		contraRapida.setForeground(Color.BLUE);
		contraRapida.setFont(new Font("Calibri", Font.BOLD, 32));
		contraRapida.setEditable(false);
		contraRapida.setColumns(10);
		contraRapida.setBackground(Color.WHITE);
		contraRapida.setBounds(66, 32, 230, 50);
		contentenorFinPago.add(contraRapida);
		
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				usuarioAutorizado = new Usuario(0, "" , "", "", 0,
						"", false);
				stop();
				dispose();
			}
		});
		btnSalir.setFont(new Font("Calibri", Font.BOLD, 35));
		btnSalir.setBounds(10, 477, 182, 53);
		contentenorFinPago.add(btnSalir);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				contraRapida.setText("");
			}
		});
		btnLimpiar.setFont(new Font("Calibri", Font.BOLD, 35));
		btnLimpiar.setBounds(202, 477, 182, 53);
		contentenorFinPago.add(btnLimpiar);
		
		lblHuella = new JLabel("");
		lblHuella.setIcon(new ImageIcon(VentSegAutenticacionLogueoRapido.class.getResource("/icons/capturaHuellamini.jpg")));
		lblHuella.setBounds(10, 345, 51, 50);
		contentenorFinPago.add(lblHuella);
		//Leemos archivos properties para fijar el valor de host
		Properties prop = new Properties();
		InputStream is = null;
		//Iniciamos componentes relacionados con la huella digital
		initComponents();

	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	
	
	private void initComponents() {
		
		addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
		//Inicializamos el valor de la BD Biometría
		//Capturamos el valor de la biometria remota
        Parametro parametroAud = parCtrl.obtenerParametro("CONFBIOMEREMOTA");
      	//Extraemos el valor del campo de ValorTexto
      	biometriaRemota = parametroAud.getValorTexto();
      	//Validamos si no se pudo recuperar valor lo ponemos por defecto en S
      	if(biometriaRemota.equals(new String("")))
      	{
      		biometriaRemota = "S";
      	}
	}
	
	private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        Iniciar();
	    start();
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        stop();
    }//GEN-LAST:event_formWindowClosing
	
    
    protected void Iniciar(){
    	
    	   Lector.addDataListener(new DPFPDataAdapter() {
    	    @Override public void dataAcquired(final DPFPDataEvent e) {
    	    SwingUtilities.invokeLater(new Runnable() {	public void run() {
    	    ProcesarCaptura(e.getSample());
    	    }});}
    	   });
    	   Lector.addReaderStatusListener(new DPFPReaderStatusAdapter() {
    	    @Override public void readerConnected(final DPFPReaderStatusEvent e) {
    	    SwingUtilities.invokeLater(new Runnable() {	public void run() {
    	    }});}
    	    @Override public void readerDisconnected(final DPFPReaderStatusEvent e) {
    	    SwingUtilities.invokeLater(new Runnable() {	public void run() {
    	    }});}
    	   });

    	   Lector.addSensorListener(new DPFPSensorAdapter() {
    	    @Override public void fingerTouched(final DPFPSensorEvent e) {
    	    SwingUtilities.invokeLater(new Runnable() {	public void run() {
    	    }});}
    	    @Override public void fingerGone(final DPFPSensorEvent e) {
    	    SwingUtilities.invokeLater(new Runnable() {	public void run() {
    	    }});}
    	   });

    	   Lector.addErrorListener(new DPFPErrorAdapter(){
    	    public void errorReader(final DPFPErrorEvent e){
    	    SwingUtilities.invokeLater(new Runnable() {  public void run() {
    	    }});}
    	   });
    	}
    
    public  void ProcesarCaptura(DPFPSample sample)
    {
     System.out.println("Se tomo huella");
   	 // Procesar la muestra de la huella y crear un conjunto de características con el propósito de verificacion.
   	 featuresverificacion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
   	 // Comprobar la calidad de la muestra de la huella y lo añade a su reclutador si es bueno
   	 usuarioIdentificado = bioCtrl.identificarBiometriaEmpleado(featuresverificacion, biometriaRemota);
   	 if((usuarioIdentificado.getIdUsuario() > 0) && (usuarioIdentificado.isAdministrador()))
   	 {
   		 
   		 
   		 lblHuella.setIcon(new ImageIcon(VentSegRegEventoEmpleado.class.getResource("/icons/capturaHuellaminiOK.jpg")));
   		 //Paramos la captura
   		 stop();
   		 //Realizamos la acción de registro exitoso
   		usuarioAutorizado = usuarioIdentificado;
   		 
   	 }else
   	 {
   		stop();
   		usuarioAutorizado = new Usuario(0, "" , "", "", 0,
				"", false);
   	 }
   	 stop();
   	 dispose();
   }
    
    public  void start(){
    	try
    	{
    		Lector.stopCapture();
    	}catch(Exception e)
    	{
    		System.out.println("Error intentando realizando Stop de Rutina");
    	}
    	try
    	{
    		Lector.startCapture();
    	}catch(Exception e)
    	{
    		System.out.println(e.toString() + " Arrancando Captura");
    		//JOptionPane.showMessageDialog(ventanaPadre, "Se tienen problemas para iniciar la captura del huellero", "Inicio de huellero", JOptionPane.OK_OPTION);	
    	}
    	
    }

    public  void stop(){
    	try
    	{
    		Lector.stopCapture();
    	}catch(Exception e)
    	{
    		System.out.println(e.toString() +  " Parando Captura");
    		//JOptionPane.showMessageDialog(ventanaPadre, "Se tienen problemas para finalizar la captura del huellero", "Finalizar de huellero", JOptionPane.OK_OPTION);	
    	}
            
    }

    public DPFPTemplate getTemplate() {
            return template;
    }

    public void setTemplate(DPFPTemplate template) {
            DPFPTemplate old = this.template;
    	this.template = template;
    	firePropertyChange(TEMPLATE_PROPERTY, old, template);
    }
    
    public void verificarHuella() {
    	ByteArrayInputStream datosHuella = new ByteArrayInputStream(template.serialize());
    }

    public  DPFPFeatureSet extraerCaracteristicas(DPFPSample sample, DPFPDataPurpose purpose){
         DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
         try {
          return extractor.createFeatureSet(sample, purpose);
         } catch (DPFPImageQualityException e) {
        	 System.out.println(e.toString());
          return null;
         }
    }
	
}
