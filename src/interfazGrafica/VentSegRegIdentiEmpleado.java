package interfazGrafica;
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
import java.awt.Image;
import java.awt.Window;
import java.io.ByteArrayInputStream;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.GroupLayout.Alignment;
import capaControlador.BiometriaCtrl;
import capaControlador.ParametrosCtrl;
import capaModelo.Parametro;
import capaModelo.Usuario;

import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

public class VentSegRegIdentiEmpleado extends javax.swing.JDialog {
	
	BiometriaCtrl bioCtrl = new BiometriaCtrl(PrincipalLogueo.habilitaAuditoria);
	ParametrosCtrl parCtrl = new ParametrosCtrl(PrincipalLogueo.habilitaAuditoria);

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
    /** Creates new form CapturaHuella */
	//Variable para controlar biometria remota
	String biometriaRemota = "";
	
    public VentSegRegIdentiEmpleado(JDialog parent, boolean modal) {
    	super(parent, modal);
//        try {
//         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//         } catch (Exception e) {
//         JOptionPane.showMessageDialog(null, "Imposible modificar el tema visual", "Lookandfeel inválido.",
//         JOptionPane.ERROR_MESSAGE);
//         }
        initComponents();
        ventanaPadre = this;
        //Mostrar mensaje ilustrativo para el enrolamiento
        txtArea.setEditable(false);
    }
    
    //INFORME DE VARIABLE DONDE SE ALMACENA LA FOTO
    ByteArrayInputStream datosHuella;
    Integer sizeHuella;
    Window ventanaPadre;
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panHuellas = new javax.swing.JPanel();
        jPanResultadoHuella = new javax.swing.JPanel();
        lblImagenHuella = new javax.swing.JLabel();
        panBtns = new javax.swing.JPanel();
        jPanAcciones = new javax.swing.JPanel();
        btnSalir = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtArea = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Captura Huella Dactilar Pizza Americana");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        panHuellas.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Huella Digital Capturada", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        panHuellas.setPreferredSize(new java.awt.Dimension(400, 270));
        panHuellas.setLayout(new java.awt.BorderLayout());

        jPanResultadoHuella.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanResultadoHuella.setLayout(new java.awt.BorderLayout());
        jPanResultadoHuella.add(lblImagenHuella, java.awt.BorderLayout.CENTER);

        panHuellas.add(jPanResultadoHuella, java.awt.BorderLayout.CENTER);

        getContentPane().add(panHuellas, java.awt.BorderLayout.NORTH);

        panBtns.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Acciones", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        panBtns.setPreferredSize(new java.awt.Dimension(400, 190));
        panBtns.setLayout(new java.awt.BorderLayout());

        jPanAcciones.setPreferredSize(new java.awt.Dimension(366, 90));

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout gl_jPanAcciones = new javax.swing.GroupLayout(jPanAcciones);
        gl_jPanAcciones.setHorizontalGroup(
        	gl_jPanAcciones.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_jPanAcciones.createSequentialGroup()
        			.addGap(190)
        			.addComponent(btnSalir)
        			.addContainerGap(165, Short.MAX_VALUE))
        );
        gl_jPanAcciones.setVerticalGroup(
        	gl_jPanAcciones.createParallelGroup(Alignment.TRAILING)
        		.addGroup(gl_jPanAcciones.createSequentialGroup()
        			.addGap(49)
        			.addComponent(btnSalir, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        );
        jPanAcciones.setLayout(gl_jPanAcciones);

        panBtns.add(jPanAcciones, java.awt.BorderLayout.NORTH);

        jPanel4.setLayout(new java.awt.BorderLayout());

        txtArea.setColumns(20);
        txtArea.setFont(new java.awt.Font("Lucida Sans", 1, 10));
        txtArea.setRows(5);
        jScrollPane1.setViewportView(txtArea);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panBtns.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel3.setPreferredSize(new java.awt.Dimension(366, 20));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3Layout.setHorizontalGroup(
        	jPanel3Layout.createParallelGroup(Alignment.TRAILING)
        		.addGap(0, 441, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
        	jPanel3Layout.createParallelGroup(Alignment.LEADING)
        		.addGap(0, 20, Short.MAX_VALUE)
        );
        jPanel3.setLayout(jPanel3Layout);

        panBtns.add(jPanel3, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBtns, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-459)/2, (screenSize.height-499)/2, 459, 499);
        
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
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
    	stop();
        dispose();
}//GEN-LAST:event_btnSalirActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        Iniciar();
	    start();
        EstadoHuellas();
        btnSalir.grabFocus();
    }//GEN-LAST:event_formWindowOpened

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        stop();
    }//GEN-LAST:event_formWindowClosing


protected void Iniciar(){
	
   Lector.addDataListener(new DPFPDataAdapter() {
    @Override public void dataAcquired(final DPFPDataEvent e) {
    SwingUtilities.invokeLater(new Runnable() {	public void run() {
    EnviarTexto("La Huella Digital ha sido Capturada");
    ProcesarCaptura(e.getSample());
    }});}
   });
   Lector.addReaderStatusListener(new DPFPReaderStatusAdapter() {
    @Override public void readerConnected(final DPFPReaderStatusEvent e) {
    SwingUtilities.invokeLater(new Runnable() {	public void run() {
    EnviarTexto("El Sensor de Huella Digital esta Activado o Conectado");
    }});}
    @Override public void readerDisconnected(final DPFPReaderStatusEvent e) {
    SwingUtilities.invokeLater(new Runnable() {	public void run() {
    EnviarTexto("El Sensor de Huella Digital esta Desactivado o no Conectado");
    }});}
   });

   Lector.addSensorListener(new DPFPSensorAdapter() {
    @Override public void fingerTouched(final DPFPSensorEvent e) {
    SwingUtilities.invokeLater(new Runnable() {	public void run() {
    EnviarTexto("El dedo ha sido colocado sobre el Lector de Huella");
    }});}
    @Override public void fingerGone(final DPFPSensorEvent e) {
    SwingUtilities.invokeLater(new Runnable() {	public void run() {
    EnviarTexto("El dedo ha sido quitado del Lector de Huella");
    }});}
   });

   Lector.addErrorListener(new DPFPErrorAdapter(){
    public void errorReader(final DPFPErrorEvent e){
    SwingUtilities.invokeLater(new Runnable() {  public void run() {
    EnviarTexto("Error: "+e.getError());
    }});}
   });
}

 public DPFPFeatureSet featuresinscripcion;
 public DPFPFeatureSet featuresverificacion;

 public  void ProcesarCaptura(DPFPSample sample)
 {

	 // Procesar la muestra de la huella y crear un conjunto de características con el propósito de verificacion.
	 featuresverificacion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
	//Dibuja la huella dactilar capturada.
	 Image image=CrearImagenHuella(sample);
	 DibujarHuella(image);
	 // Comprobar la calidad de la muestra de la huella y lo añade a su reclutador si es bueno
	 Usuario usuario = bioCtrl.identificarBiometriaEmpleado(featuresverificacion, biometriaRemota);
	 if(usuario.getIdUsuario() > 0)
	 {
		 JOptionPane.showMessageDialog(ventanaPadre, "El usuario pudo ser identificado y es " + usuario.getNombreLargo(), "Identificacion Huella Digital", JOptionPane.OK_OPTION);
	 }else
	 {
		 JOptionPane.showMessageDialog(ventanaPadre, "Usuario no identificado " , "Identificacion Huella Digital", JOptionPane.OK_OPTION);
	 }
}
 
 public void guardarHuella() {
	 
	 
	 datosHuella = new ByteArrayInputStream(template.serialize());
	 sizeHuella = template.serialize().length;
	 
	 System.out.println(datosHuella.toString());
	 System.out.println(sizeHuella);
	 //Consultas.InsertarEmpleado("Andres Florez",datosHuella,sizeHuella); 
 }

public void verificarHuella() {
	ByteArrayInputStream datosHuella = new ByteArrayInputStream(template.serialize());
	EnviarTexto(bioCtrl.verificacionBiometria(datosHuella));
}

public  DPFPFeatureSet extraerCaracteristicas(DPFPSample sample, DPFPDataPurpose purpose){
     DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
     try {
      return extractor.createFeatureSet(sample, purpose);
     } catch (DPFPImageQualityException e) {
      return null;
     }
}

  public  Image CrearImagenHuella(DPFPSample sample) {
	return DPFPGlobal.getSampleConversionFactory().createImage(sample);
}

  public void DibujarHuella(Image image) {
        lblImagenHuella.setIcon(new ImageIcon(
        image.getScaledInstance(lblImagenHuella.getWidth(), lblImagenHuella.getHeight(), Image.SCALE_DEFAULT)));
        repaint();
 }

public  void EstadoHuellas(){
	EnviarTexto("Huellas Necesarias para Guardar Template "+ Reclutador.getFeaturesNeeded());
}

public void EnviarTexto(String string) {
        txtArea.append(string + "\n");
}

public  void start(){
	
	try
	{
		Lector.stopCapture();
	}catch(Exception e)
	{
		EnviarTexto("Error intentando realizando Stop de Rutina");
	}
	try
	{
		Lector.startCapture();
		EnviarTexto("Utilizando el Lector de Huella Dactilar ");
	}catch(Exception e)
	{
		EnviarTexto("Error al inicializar Lector de Huella Dactilar");
	}
}

public  void stop(){
	try
	{
		Lector.stopCapture();
        EnviarTexto("No se está usando el Lector de Huella Dactilar ");
	}catch(Exception e)
	{
		EnviarTexto("Error al parar Lector de Huella Dactilar");
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

/*
* @param args the command line arguments
*/

public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentSegRegIdentiEmpleado(null, false).setVisible(true);
            }
        });
}
    private javax.swing.JButton btnSalir;
    private javax.swing.JPanel jPanResultadoHuella;
    private javax.swing.JPanel jPanAcciones;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblImagenHuella;
    private javax.swing.JPanel panBtns;
    private javax.swing.JPanel panHuellas;
    private javax.swing.JTextArea txtArea;
    // End of variables declaration//GEN-END:variables

}
