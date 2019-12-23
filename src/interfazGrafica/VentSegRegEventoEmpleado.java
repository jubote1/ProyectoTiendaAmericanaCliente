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
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.GroupLayout.Alignment;

import capaControlador.AutenticacionCtrl;
import capaControlador.BiometriaCtrl;
import capaControlador.OperacionesTiendaCtrl;
import capaControlador.ParametrosCtrl;
import capaModelo.EmpleadoEvento;
import capaModelo.Parametro;
import capaModelo.Usuario;


import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTextField;

public class VentSegRegEventoEmpleado extends javax.swing.JDialog {
	
	BiometriaCtrl bioCtrl = new BiometriaCtrl(PrincipalLogueo.habilitaAuditoria);
	OperacionesTiendaCtrl operTiendaCtrl = new OperacionesTiendaCtrl(PrincipalLogueo.habilitaAuditoria);
	ParametrosCtrl parCtrl = new ParametrosCtrl(PrincipalLogueo.habilitaAuditoria);
	int idTienda;
	//Variable donde quedará almacenado si la tienda o sucursal es la bodega
	boolean esBodega = false;

	
	//Vamos a adicionar un control de excepciones que nos servirá de modelo
	private DPFPCapture Lector;
	private DPFPEnrollment Reclutador;
	private DPFPVerification Verificador;
	//Variable que para crear el template de la huella luego de que se hallan creado las caracteriticas
	//necesarias de la huella si no ha ocurrido ningun problema
	private DPFPTemplate template;
	
	public static String TEMPLATE_PROPERTY = "template";
	//Variable para controlar biometria remota
	String biometriaRemota = "";
    /** Creates new form CapturaHuella */
    public VentSegRegEventoEmpleado(JDialog parent, boolean modal) {
    	super(parent, modal);
    	//Inicializar objetos de Digital Persona
    	try {
    		//Varible que permite iniciar el dispositivo de lector de huella conectado
    		//con sus distintos metodos.
    		Lector = DPFPGlobal.getCaptureFactory().createCapture();
    		
    		//Varible que permite establecer las capturas de la huellas, para determina sus caracteristicas
    		//y poder estimar la creacion de un template de la huella para luego poder guardarla
    		Reclutador = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    		
    		//Esta variable tambien captura una huella del lector y crea sus caracteristcas para auntetificarla
    		//o verificarla con alguna guarda en la BD
    		Verificador = DPFPGlobal.getVerificationFactory().createVerification();
    		
    		
    	}catch(Exception e)
    	{
    		System.out.println(e.toString());
    	}
    	initComponents();
        ventanaPadre = this;
        //Mostrar mensaje ilustrativo para el enrolamiento
        txtArea.setEditable(false);
        idTienda = operTiendaCtrl.obtenerIdTienda();
        jDialogPadre = this;
    }
    
    //INFORME DE VARIABLE DONDE SE ALMACENA LA FOTO
    ByteArrayInputStream datosHuella;
    Integer sizeHuella;
    Window ventanaPadre;
    Usuario usuarioIdentificado;
    String accionRegistro;
    boolean salidaDiaAnterior = false;
    JDialog jDialogPadre;
    JLabel lblReco = new JLabel("");
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panHuellas = new javax.swing.JPanel();
        panHuellas.setBounds(0, 0, 473, 270);
        jPanResultadoHuella = new javax.swing.JPanel();
        lblImagenHuella = new javax.swing.JLabel();
        lblImagenHuella.setIcon(new ImageIcon(VentSegRegEventoEmpleado.class.getResource("/icons/capturaHuella.jpg")));
        panBtns = new javax.swing.JPanel();
        panBtns.setBounds(0, 274, 473, 257);
        jPanAcciones = new javax.swing.JPanel();
        jPanAcciones.setBounds(6, 16, 461, 145);
        btnSalir = new javax.swing.JButton();
        btnSalir.setBounds(176, 104, 83, 30);
        jPanel4 = new javax.swing.JPanel();
        jPanel4.setBounds(6, 172, 461, 74);
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane1.setBounds(0, 0, 461, 71);
        txtArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("REGISTRO EVENTO ENTRADA/SALIDA JORNADA");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        panHuellas.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Huella Digital Capturada", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        panHuellas.setPreferredSize(new java.awt.Dimension(400, 270));
        panHuellas.setLayout(new java.awt.BorderLayout());

        jPanResultadoHuella.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanResultadoHuella.setLayout(new java.awt.BorderLayout());
        jPanResultadoHuella.add(lblImagenHuella, java.awt.BorderLayout.CENTER);

        panHuellas.add(jPanResultadoHuella, java.awt.BorderLayout.CENTER);

        getContentPane().add(panHuellas);

        panBtns.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Acciones", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        panBtns.setPreferredSize(new java.awt.Dimension(400, 190));

        jPanAcciones.setPreferredSize(new java.awt.Dimension(366, 90));

        btnSalir.setText("Salir");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        panBtns.setLayout(null);
        
        lblEmpleIdentificado = new JLabel("");
        lblEmpleIdentificado.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblEmpleIdentificado.setBounds(10, 11, 235, 27);

        panBtns.add(jPanAcciones);
        jPanAcciones.setLayout(null);
        jPanAcciones.add(lblEmpleIdentificado);
        jPanAcciones.add(btnSalir);
        
        lblFechaHoraEvento = new JLabel("");
        lblFechaHoraEvento.setForeground(Color.BLUE);
        lblFechaHoraEvento.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblFechaHoraEvento.setBounds(255, 11, 196, 27);
        jPanAcciones.add(lblFechaHoraEvento);
        
        btnRegistrar = new JButton();
        btnRegistrar.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnRegistrar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		//Debemos de validar si es un ingreso o un salida
        		Date fechaActual = new Date();
       		 	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
       		    String strFecha = "";
       		    int resp = 0;
       		 	if(!salidaDiaAnterior)
       		 	{
       		 		strFecha = dateFormat.format(fechaActual);
       		 	}else
	        	{
	        		Calendar calendarioActual = Calendar.getInstance();
	        		calendarioActual.add(Calendar.DAY_OF_YEAR, -1);
	        		Date datFechaAnterior = calendarioActual.getTime();
	        		strFecha = dateFormat.format(datFechaAnterior);
	        	}
       		 	resp = JOptionPane.showConfirmDialog(ventanaPadre, "Estas seguro de registrar " + accionRegistro +" para el usuario " + usuarioIdentificado.getNombreLargo() + " a las " + fechaActual.toString() + ", \n para el día " + strFecha + "." , "Confirmación Evento" , JOptionPane.YES_NO_OPTION);
       		 	if(resp == 0)
	        	{
       		 		String usoBiometria = "";
       		 		if(txtCedula.getText().trim().equals(new String("")))
       		 		{
       		 			usoBiometria = "S";
       		 		}else
       		 		{
       		 			usoBiometria = "N";
       		 		}
       		 		EmpleadoEvento empEvento = new EmpleadoEvento(usuarioIdentificado.getIdUsuario(),accionRegistro, strFecha, "", idTienda, usoBiometria);
	        		boolean respInsercion = bioCtrl.InsertarEventoRegistroEmpleado(empEvento, biometriaRemota);
	        		if(respInsercion)
	        		{
	        			//Si la inserción es correcta mostrar mensaje avisando
	        			JOptionPane opt = new JOptionPane("Se registro exitosamente " + accionRegistro  + " para " + usuarioIdentificado.getNombreLargo(), JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}); // no buttons
					     final JDialog dlg = opt.createDialog("REGISTRO EXITOSO");
					     new Thread(new Runnable()
					           {
					             public void run()
					             {
					               try
					               {
					                 Thread.sleep(4000);
					                 dlg.dispose();

					               }
					               catch ( Throwable th )
					               {
					                 
					               }
					             }
					           }).start();
					     dlg.setVisible(true);
	        			
	        			lblEvento.setText("");
	        			lblReco.setText("");
	        			lblEmpleIdentificado.setText("");
	        			lblFechaHoraEvento.setText("");
	        			btnRegistrar.setEnabled(false);
	        			txtCedula.setText("");
	        			EnviarTexto("Pendiente para registrar otra persona.");
	        			lblImagenHuella.setIcon(new ImageIcon(VentSegRegEventoEmpleado.class.getResource("/icons/capturaHuella.jpg")));
	        		}
	        	}else
	        	{
	        		//Si la inserción es correcta mostrar mensaje avisando
        			JOptionPane opt = new JOptionPane("SE TUVO ERROR EN EL REGISTRO " + accionRegistro  + " para " + usuarioIdentificado.getNombreLargo(), JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}); // no buttons
				     final JDialog dlg = opt.createDialog("REGISTRO FALLIDO");
				     new Thread(new Runnable()
				           {
				             public void run()
				             {
				               try
				               {
				                 Thread.sleep(4000);
				                 dlg.dispose();

				               }
				               catch ( Throwable th )
				               {
				                 
				               }
				             }
				           }).start();
				     dlg.setVisible(true);
	        	}
        	}
        });
        btnRegistrar.setText("REGISTRAR");
        btnRegistrar.setBounds(10, 104, 156, 30);
        jPanAcciones.add(btnRegistrar);
        btnRegistrar.setEnabled(false);
        
        lblEvento = new JLabel("");
        lblEvento.setForeground(Color.RED);
        lblEvento.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblEvento.setBounds(20, 38, 190, 30);
        jPanAcciones.add(lblEvento);
        
        btnNewButton = new JButton("Reporte de Ingresos/Salida");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		VentPedReportes ventReporte = new VentPedReportes(jDialogPadre,true, "", 6);
				ventReporte.setVisible(true);
        	}
        });
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 7));
        btnNewButton.setBounds(271, 107, 180, 27);
        jPanAcciones.add(btnNewButton);
        
        JLabel lblIngresarCdula = new JLabel("INGRESAR C\u00C9DULA");
        lblIngresarCdula.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblIngresarCdula.setBounds(10, 66, 156, 27);
        jPanAcciones.add(lblIngresarCdula);
        
        txtCedula = new JTextField();
        txtCedula.setFont(new Font("Tahoma", Font.BOLD, 13));
        txtCedula.setBounds(159, 70, 168, 23);
        jPanAcciones.add(txtCedula);
        txtCedula.setColumns(10);
        
        JButton btnBuscarManual = new JButton("BUSCAR");
        btnBuscarManual.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String strCedulaEmpleado = txtCedula.getText();
        		long cedulaEmpleado = 0;
        		if(strCedulaEmpleado.equals(new String("")))
        		{
        			JOptionPane.showMessageDialog(ventanaPadre, "Debe ingresar una cédula para verificar el Empleado" , "Cédula Incorrecta.", JOptionPane.OK_OPTION);
        		}
        		else
        		{
        			try {
        				cedulaEmpleado = Long.parseLong(strCedulaEmpleado);
        			}catch(Exception exc)
        			{
        				cedulaEmpleado = 0;
        				JOptionPane.showMessageDialog(ventanaPadre, "Debe ingresar un número válido para verificar la cédula." , "Número de Cédula Incorrecta.", JOptionPane.OK_OPTION);
        				System.out.println(exc.toString());
        			}
        			if (cedulaEmpleado != 0)
        			{
        				 usuarioIdentificado = bioCtrl.identificarSinBiometriaEmpleado(cedulaEmpleado, biometriaRemota);
        				 //En este punto debemos de controlar que lo retornado sea igual a null, si es así hay un problema de conexión
        				 if(usuarioIdentificado == null)
        				 {
        					 JOptionPane.showMessageDialog(ventanaPadre, "Usuario no identificado " , "Identificacion Huella Digital", JOptionPane.OK_OPTION);
        				 }
        				 else if(usuarioIdentificado.getIdUsuario() > 0)
        				 {
        					 JOptionPane.showMessageDialog(ventanaPadre, "OJO, tenga en cuenta que si se registra por cédula debe tener el permiso,\n esto dejará auditoría de que se está registrando por cédula y no por huella \n Estos casos deben ser excepcionales." , "Identificacion por Cédula", JOptionPane.ERROR_MESSAGE);
        					 lblEmpleIdentificado.setText(usuarioIdentificado.getNombreLargo());
        					 java.util.Date fechaHoraActual = new java.util.Date();
        					 lblFechaHoraEvento.setText(fechaHoraActual.toString());
        					 btnRegistrar.setEnabled(true);
        					 //Mostramos mensaje del empleado identificado
        					 EnviarTexto("Empleado Identificado " + usuarioIdentificado.getNombreLargo());
        					 lblImagenHuella.setIcon(new ImageIcon(VentSegRegEventoEmpleado.class.getResource("/icons/capturaHuellaOK.jpg")));
        					 //Deberemos de buscar la manera de volver a activar la captura de huellas de otros empleados
        					 Date fechaActual = new Date();
        					 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        					 String strFecha = dateFormat.format(fechaActual);
        					 //Fijamos el calendario para mandarlo al método
        					 Calendar calendarioActual = Calendar.getInstance();
        					 //Realizamos intervención para verificar si el día anterior se dio salida
        					 accionRegistro = bioCtrl.consultarEstadoRegistroEvento(usuarioIdentificado.getIdUsuario(), strFecha, calendarioActual, biometriaRemota);
        					 if(accionRegistro.equals(new String("VACIO")))
        					 {
        						 lblEvento.setText("INGRESO");
        						 accionRegistro = "INGRESO";
        						 //btnRegistrar.setEnabled(false);
        					 }else if(accionRegistro.equals(new String("INGRESO")))
        					 {
        						 lblEvento.setText("SALIDA");
        						 accionRegistro = "SALIDA";
        						 
        					 }else if(accionRegistro.equals(new String("SALIDA-REPETIDO")))
        					 {
        						 JOptionPane.showMessageDialog(ventanaPadre, "OJO.. Ya registraste entrada y salida para este día, vas a volver a ingresar?" , "Reingreso a Jornada Laboral.", JOptionPane.OK_OPTION);
        						 lblEvento.setText("INGRESO");
        						 accionRegistro = "INGRESO";
        						
        					 }else if(accionRegistro.equals(new String("INGRESO-REPETIDO")))
        					 {
        						 lblEvento.setText("SALIDA");
        						 accionRegistro = "SALIDA";
        					 }
        					 else if(accionRegistro.equals(new String("SALIDA-ANTERIOR")))
        					 {
        						 lblEvento.setText("SALIDA");
        						 salidaDiaAnterior = true;
        						 accionRegistro = "SALIDA";
        					 }
        					 lblReco.setText("Recuerde presionar el botón REGISTRAR");
        				 }else
        				 {
        					 JOptionPane.showMessageDialog(ventanaPadre, "Usuario no identificado " , "Identificacion Huella Digital", JOptionPane.OK_OPTION);
        				 }
        			}
        		}
        	}
        });
        btnBuscarManual.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnBuscarManual.setBounds(337, 69, 114, 27);
        jPanAcciones.add(btnBuscarManual);
        
        lblReco = new JLabel("");
        lblReco.setForeground(Color.BLACK);
        lblReco.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblReco.setBounds(220, 38, 231, 30);
        jPanAcciones.add(lblReco);
        jPanel4.setLayout(null);

        txtArea.setColumns(20);
        txtArea.setFont(new java.awt.Font("Lucida Sans", 1, 10));
        txtArea.setRows(5);
        jScrollPane1.setViewportView(txtArea);

        jPanel4.add(jScrollPane1);

        panBtns.add(jPanel4);

        getContentPane().add(panBtns);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-459)/2, (screenSize.height-560)/2, 479, 560);
        
        //Capturamos el valor de la biometria remota
        Parametro parametroAud = parCtrl.obtenerParametro("CONFBIOMEREMOTA");
      	//Extraemos el valor del campo de ValorTexto
      	biometriaRemota = parametroAud.getValorTexto();
      	//Validamos si no se pudo recuperar valor lo ponemos por defecto en S
      	if(biometriaRemota.equals(new String("")))
      	{
      		biometriaRemota = "S";
      	}
      	//Realizamos inicialización de variable esBodega
      	parametroAud = parCtrl.obtenerParametro("ESBODEGA");
      	String strBodega = parametroAud.getValorTexto();
      	if(strBodega.equals(new String("S")))
      	{
      		esBodega = true;
      	}else
      	{
      		esBodega = false;
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
	 usuarioIdentificado = bioCtrl.identificarBiometriaEmpleado(featuresverificacion, biometriaRemota);
	 if(usuarioIdentificado == null)
	 {
		 JOptionPane.showMessageDialog(ventanaPadre, "Usuario no identificado " , "Identificacion Huella Digital", JOptionPane.OK_OPTION);
	 }
	 else if(usuarioIdentificado.getIdUsuario() > 0)
	 {
		 lblEmpleIdentificado.setText(usuarioIdentificado.getNombreLargo());
		 java.util.Date fechaHoraActual = new java.util.Date();
		 lblFechaHoraEvento.setText(fechaHoraActual.toString());
		 //Mostramos mensaje del empleado identificado
		 EnviarTexto("Empleado Identificado " + usuarioIdentificado.getNombreLargo());
		 lblImagenHuella.setIcon(new ImageIcon(VentSegRegEventoEmpleado.class.getResource("/icons/capturaHuellaOK.jpg")));
		 //Paramos la captura
		 //Lector.stopCapture();
		 //Deberemos de buscar la manera de volver a activar la captura de huellas de otros empleados
		 Date fechaActual = new Date();
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 String strFecha = dateFormat.format(fechaActual);
		 //Fijamos el calendario para mandarlo al método
		 Calendar calendarioActual = Calendar.getInstance();
		 //Realizamos intervención para verificar si el día anterior se dio salida
		 accionRegistro = bioCtrl.consultarEstadoRegistroEvento(usuarioIdentificado.getIdUsuario(), strFecha, calendarioActual, biometriaRemota);
		 if(accionRegistro.equals(new String("VACIO")))
		 {
			 lblEvento.setText("INGRESO");
			 accionRegistro = "INGRESO";
			 //btnRegistrar.setEnabled(false);
		 }else if(accionRegistro.equals(new String("INGRESO")))
		 {
			 lblEvento.setText("SALIDA");
			 accionRegistro = "SALIDA";
			 
		 }else if(accionRegistro.equals(new String("SALIDA-REPETIDO")))
		 {
			 if(!esBodega)
			 {
				 JOptionPane.showMessageDialog(ventanaPadre, "OJO.. Ya registraste entrada y salida para este día, vas a volver a ingresar?" , "Reingreso a Jornada Laboral.", JOptionPane.OK_OPTION);
			 }
			 lblEvento.setText("INGRESO");
			 accionRegistro = "INGRESO";
			
		 }else if(accionRegistro.equals(new String("INGRESO-REPETIDO")))
		 {
			 lblEvento.setText("SALIDA");
			 accionRegistro = "SALIDA";
		 }
		 else if(accionRegistro.equals(new String("SALIDA-ANTERIOR")))
		 {
			 lblEvento.setText("SALIDA");
			 salidaDiaAnterior = true;
			 accionRegistro = "SALIDA";
		 }
		 lblReco.setText("Recuerde presionar el botón REGISTRAR");
		 // En este punto incluimos el trato diferencial cuando es bodega
		 if(esBodega)
		 {
     			//Debemos de validar si es un ingreso o un salida
     			fechaActual = new Date();
    		    int resp = 0;
    		 	if(!salidaDiaAnterior)
    		 	{
    		 		strFecha = dateFormat.format(fechaActual);
    		 	}else
	        	{
	        		calendarioActual = Calendar.getInstance();
	        		calendarioActual.add(Calendar.DAY_OF_YEAR, -1);
	        		Date datFechaAnterior = calendarioActual.getTime();
	        		strFecha = dateFormat.format(datFechaAnterior);
	        	}
    		 	resp = JOptionPane.showConfirmDialog(ventanaPadre, "Estas seguro de registrar " + accionRegistro +" para el usuario " + usuarioIdentificado.getNombreLargo() + " a las " + fechaActual.toString() + ", \n para el día " + strFecha + "." , "Confirmación Evento" , JOptionPane.YES_NO_OPTION);
    		 	if(resp == 0)
	        	{
    		 		String usoBiometria = "S";
    		 		EmpleadoEvento empEvento = new EmpleadoEvento(usuarioIdentificado.getIdUsuario(),accionRegistro, strFecha, "", idTienda, usoBiometria);
	        		boolean respInsercion = bioCtrl.InsertarEventoRegistroEmpleado(empEvento, biometriaRemota);
	        		if(respInsercion)
	        		{
	        			//Si la inserción es correcta mostrar mensaje avisando
	        			JOptionPane opt = new JOptionPane("Se registro exitosamente " + accionRegistro  + " para " + usuarioIdentificado.getNombreLargo(), JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}); // no buttons
					     final JDialog dlg = opt.createDialog("REGISTRO EXITOSO");
					     new Thread(new Runnable()
					           {
					             public void run()
					             {
					               try
					               {
					                 Thread.sleep(1000);
					                 dlg.dispose();

					               }
					               catch ( Throwable th )
					               {
					                 
					               }
					             }
					           }).start();
					     dlg.setVisible(true);
					     reinicioInterfaceRegistro();
	        			
	        		}
	        	}else
	        	{
	        		//Si la inserción es correcta mostrar mensaje avisando
     			JOptionPane opt = new JOptionPane("SE TUVO ERROR EN EL REGISTRO " + accionRegistro  + " para " + usuarioIdentificado.getNombreLargo(), JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}); // no buttons
				     final JDialog dlg = opt.createDialog("REGISTRO FALLIDO");
				     new Thread(new Runnable()
				           {
				             public void run()
				             {
				               try
				               {
				                 Thread.sleep(1000);
				                 dlg.dispose();

				               }
				               catch ( Throwable th )
				               {
				                 
				               }
				             }
				           }).start();
				     dlg.setVisible(true);
				     reinicioInterfaceRegistro();
	        	}
		 }else
		 {
			 btnRegistrar.setEnabled(true);
		 }
	 }else
	 {
		 JOptionPane.showMessageDialog(ventanaPadre, "Usuario no identificado " , "Identificacion Huella Digital", JOptionPane.OK_OPTION);
	 }
}
 
 //Método creado para el reinicio visual de la pantalla para el registro de huellas
 public void reinicioInterfaceRegistro()
 {
	 	lblEvento.setText("");
		lblReco.setText("");
		lblEmpleIdentificado.setText("");
		lblFechaHoraEvento.setText("");
		btnRegistrar.setEnabled(false);
		txtCedula.setText("");
		EnviarTexto("Pendiente para registrar otra persona.");
		lblImagenHuella.setIcon(new ImageIcon(VentSegRegEventoEmpleado.class.getResource("/icons/capturaHuella.jpg")));
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
                new VentSegRegEventoEmpleado(null, false).setVisible(true);
            }
        });
}
    private javax.swing.JButton btnSalir;
    private javax.swing.JPanel jPanResultadoHuella;
    private javax.swing.JPanel jPanAcciones;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblImagenHuella;
    private javax.swing.JPanel panBtns;
    private javax.swing.JPanel panHuellas;
    private javax.swing.JTextArea txtArea;
    private JLabel lblEmpleIdentificado;
    private JLabel lblFechaHoraEvento;
    private JButton btnRegistrar;
    private JLabel lblEvento;
    private JButton btnNewButton;
    private JTextField txtCedula;
}
