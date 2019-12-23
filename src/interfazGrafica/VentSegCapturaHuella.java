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
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;

public class VentSegCapturaHuella extends javax.swing.JDialog {
	
BiometriaCtrl bioCtrl = new BiometriaCtrl(PrincipalLogueo.habilitaAuditoria);


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
	
	//Variable global que almacena de cual empleado es que se está realizando la biometria
	int idEmpleado; 
	
	public static String TEMPLATE_PROPERTY = "template";
    /** Creates new form CapturaHuella */
    public VentSegCapturaHuella(JDialog parent, boolean modal ,int idEmple) {
    	super(parent, modal);
    	this.idEmpleado = idEmple;
//        try {
//         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//         } catch (Exception e) {
//         JOptionPane.showMessageDialog(null, "Imposible modificar el tema visual", "Lookandfeel inválido.",
//         JOptionPane.ERROR_MESSAGE);
//         }
        initComponents();
        ventanaPadre = this;
        //Validamos si el Empleado ya está enrolado
       	//Mostrar mensaje ilustrativo para el enrolamiento
        JOptionPane.showMessageDialog(ventanaPadre, "Va a dar inicio al enrolamiento de la huella digital, Recuerde que se tomarán 4 muestras en este proceso.!!", "Enrolamiento Huella Digital", JOptionPane.INFORMATION_MESSAGE);
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
        btnGuardar = new javax.swing.JButton();
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

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout gl_jPanAcciones = new javax.swing.GroupLayout(jPanAcciones);
        gl_jPanAcciones.setHorizontalGroup(
        	gl_jPanAcciones.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_jPanAcciones.createSequentialGroup()
        			.addGroup(gl_jPanAcciones.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_jPanAcciones.createSequentialGroup()
        					.addGap(148)
        					.addComponent(btnGuardar, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_jPanAcciones.createSequentialGroup()
        					.addGap(190)
        					.addComponent(btnSalir)))
        			.addContainerGap(165, Short.MAX_VALUE))
        );
        gl_jPanAcciones.setVerticalGroup(
        	gl_jPanAcciones.createParallelGroup(Alignment.TRAILING)
        		.addGroup(gl_jPanAcciones.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(btnGuardar, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
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
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
    	stop();
        dispose();
}//GEN-LAST:event_btnSalirActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
    	//La información almacenada en la variable temporal, la almacenamos en base de datos
    	boolean respuesta = bioCtrl.InsertarBiometriaEmpleado(idEmpleado, datosHuella, sizeHuella);
    	if(respuesta)
    	{
    		JOptionPane.showMessageDialog(VentSegCapturaHuella.this, "Se ha enrolado correctamente la huella.", "Creación de Huella para Empleado", JOptionPane.INFORMATION_MESSAGE);
    	}
    	
}//GEN-LAST:event_btnGuardarActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        Iniciar();
	    start();
        EstadoHuellas();
        btnGuardar.setEnabled(false);
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
	 System.out.println("TOME HUELLA");
 // Procesar la muestra de la huella y crear un conjunto de características con el propósito de inscripción.
 featuresinscripcion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);

 // Procesar la muestra de la huella y crear un conjunto de características con el propósito de verificacion.
 featuresverificacion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

 // Comprobar la calidad de la muestra de la huella y lo añade a su reclutador si es bueno
 if (featuresinscripcion != null)
     try{
     System.out.println("Las Caracteristicas de la Huella han sido creada");
     Reclutador.addFeatures(featuresinscripcion);// Agregar las caracteristicas de la huella a la plantilla a crear

     // Dibuja la huella dactilar capturada.
     Image image=CrearImagenHuella(sample);
     DibujarHuella(image);
     
     
     
     }catch (DPFPImageQualityException ex) {
     System.err.println("Error: "+ex.getMessage());
     }

     finally {
     EstadoHuellas();
     // Comprueba si la plantilla se ha creado.
	switch(Reclutador.getTemplateStatus())
        {
            case TEMPLATE_STATUS_READY:	// informe de éxito y detiene  la captura de huellas
	    stop();
            setTemplate(Reclutador.getTemplate());
	    EnviarTexto("La Plantilla de la Huella ha Sido Creada, ya puede Verificarla o Identificarla");
	    guardarHuella();
	    
            btnGuardar.setEnabled(true);
            btnGuardar.grabFocus();
            break;

	    case TEMPLATE_STATUS_FAILED: // informe de fallas y reiniciar la captura de huellas
	    Reclutador.clear();
            stop();
	    EstadoHuellas();
	    setTemplate(null);
	    JOptionPane.showMessageDialog(VentSegCapturaHuella.this, "La Plantilla de la Huella no pudo ser creada, Repita el Proceso", "Inscripcion de Huellas Dactilares", JOptionPane.ERROR_MESSAGE);
	    start();
	    break;
	}
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
                new VentSegCapturaHuella(null, false, 0).setVisible(true);
            }
        });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
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
