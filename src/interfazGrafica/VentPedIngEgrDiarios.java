	package interfazGrafica;
	
	import java.awt.BorderLayout;
	import java.awt.Color;
	import java.awt.EventQueue;
	
	import javax.swing.JFrame;
	import javax.swing.JPanel;
	import javax.swing.border.EmptyBorder;
	import javax.swing.table.DefaultTableModel;
	
	import capaControlador.AutenticacionCtrl;
	import capaControlador.OperacionesTiendaCtrl;
	import capaControlador.ParametrosDireccionCtrl;
	import capaControlador.PedidoCtrl;
	import capaModelo.AgrupadorMenu;
	import capaModelo.Egreso;
	import capaModelo.FechaSistema;
	import capaModelo.Ingreso;
	import capaModelo.Municipio;
	
	import javax.swing.JTabbedPane;
	import javax.swing.JLabel;
	import javax.swing.JOptionPane;
	
	import java.awt.Font;
	import javax.swing.JTextField;
	import javax.swing.JTextArea;
	import javax.swing.JScrollPane;
	import javax.swing.JButton;
	import javax.swing.JDialog;
	
	import java.awt.event.ActionListener;
	import java.util.ArrayList;
	import java.awt.event.ActionEvent;
	import javax.swing.JTable;
	
	public class VentPedIngEgrDiarios extends JDialog {
	
		private JPanel contentPaneIngEgr;
		private JTextField txtEgreso;
		private JTextField txtIngreso;
		private JTable tableIng;
		private JTable tableEgr;
		private JTextField txtFechaSistema;
		JTextArea textAreaIngreso;
		JTextArea textAreaEgreso;
		String fechaSis;
		int idIngresoActual;
		int idEgresoActual;
		JDialog ventanaActual;
		OperacionesTiendaCtrl operTiendaCtrl = new OperacionesTiendaCtrl(PrincipalLogueo.habilitaAuditoria);
		PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
		/**
		 * Launch the application.
		 */
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						VentPedIngEgrDiarios frame = new VentPedIngEgrDiarios(null, false);
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
		public VentPedIngEgrDiarios(JDialog parent, boolean modal) {
			super(parent, modal);
			ventanaActual = this;
			setTitle("INGRESOS Y EGRESOS DIARIOS");
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setBounds(100, 100, 556, 405);
			contentPaneIngEgr = new JPanel();
			contentPaneIngEgr.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPaneIngEgr);
			contentPaneIngEgr.setLayout(null);
			
			JTabbedPane tabbedPaneIngEgr = new JTabbedPane(JTabbedPane.TOP);
			tabbedPaneIngEgr.setBounds(26, 25, 504, 331);
			contentPaneIngEgr.add(tabbedPaneIngEgr);
			
			JPanel panelIngresos = new JPanel();
			tabbedPaneIngEgr.addTab("Panel Ingresos"
					+ "", null, panelIngresos, null);
			panelIngresos.setLayout(null);
			
			JLabel lblValorDelIngreso = new JLabel("Valor del Ingreso");
			lblValorDelIngreso.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblValorDelIngreso.setBounds(55, 39, 115, 23);
			panelIngresos.add(lblValorDelIngreso);
			
			txtIngreso = new JTextField();
			txtIngreso.setColumns(15);
			txtIngreso.setBounds(236, 41, 138, 20);
			panelIngresos.add(txtIngreso);
			
			JLabel descripcionIng = new JLabel("Descripci\u00F3n");
			descripcionIng.setFont(new Font("Tahoma", Font.BOLD, 12));
			descripcionIng.setBounds(55, 93, 115, 23);
			panelIngresos.add(descripcionIng);
			
			textAreaIngreso = new JTextArea();
			textAreaIngreso.setRows(5);
			textAreaIngreso.setLineWrap(true);
			textAreaIngreso.setColumns(100);
			textAreaIngreso.setBounds(236, 93, 198, 64);
			panelIngresos.add(textAreaIngreso);
			
			JButton btnAgregarIngreso = new JButton("Agregar Ingreso");
			btnAgregarIngreso.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					boolean validar = validarDatosIngreso();
					if (validar)
					{
						double valorIngreso = Double.parseDouble(txtIngreso.getText());
						String descripcion = textAreaIngreso.getText();
						int idIngresoIns = operTiendaCtrl.insertarIngreso(new Ingreso(0,valorIngreso, fechaSis, descripcion));
						pintarIngresos(fechaSis);
						limpiarCamposIngreso();
					}
				}
			});
			btnAgregarIngreso.setBounds(40, 150, 148, 23);
			panelIngresos.add(btnAgregarIngreso);
			
			JScrollPane scrollPaneIngreso = new JScrollPane();
			scrollPaneIngreso.setBounds(55, 188, 356, 70);
			panelIngresos.add(scrollPaneIngreso);
			
			tableIng = new JTable();
			scrollPaneIngreso.setViewportView(tableIng);
			tableIng.setForeground(Color.black);
			JButton btnEliminarIng = new JButton("Eliminar");
			btnEliminarIng.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					int filaSeleccionada = tableIng.getSelectedRow();
					//Hacemos la validación para decidir si se elimina o no
					int idIngreso = Integer.parseInt((String)tableIng.getValueAt(filaSeleccionada, 0));
					JOptionPane.showMessageDialog(ventanaActual, "Esta seguro que se desea eliminar el Ingreso con id " +  idIngreso , "Eliminacion Ingreso ", JOptionPane.YES_NO_OPTION);
					operTiendaCtrl.eliminarIngreso(idIngreso);
					pintarIngresos(fechaSis);
					limpiarCamposIngreso();
				}
			});
			btnEliminarIng.setBounds(10, 269, 107, 23);
			panelIngresos.add(btnEliminarIng);
			
			JButton btnEditarIng = new JButton("Editar");
			
			btnEditarIng.setBounds(127, 269, 107, 23);
			panelIngresos.add(btnEditarIng);
			
			JButton btnConfEdicionIng = new JButton("Confirmar Edici\u00F3n");
			btnConfEdicionIng.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					boolean validar = validarDatosIngreso();
					if (validar)
					{
						double valorIngreso = Double.parseDouble(txtIngreso.getText());
						String descripcion = textAreaIngreso.getText();
						boolean respuesta = operTiendaCtrl.editarIngreso(new Ingreso(idIngresoActual,valorIngreso, fechaSis, descripcion ));
						if (respuesta)
						{
							JOptionPane.showMessageDialog(ventanaActual, "Se ha editado correctamente el Ingreso " , "Confirmación Edición", JOptionPane.OK_OPTION);
							pintarIngresos(fechaSis);
							limpiarCamposIngreso();
							btnEliminarIng.setEnabled(true);
							btnAgregarIngreso.setEnabled(true);
							btnConfEdicionIng.setEnabled(false);
						}
					}
				}
			});
			btnConfEdicionIng.setBounds(244, 269, 148, 23);
			btnConfEdicionIng.setEnabled(false);
			panelIngresos.add(btnConfEdicionIng);
			
			JButton btnSalirIng = new JButton("SALIR");
			btnSalirIng.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnSalirIng.setFont(new Font("Tahoma", Font.BOLD, 11));
			btnSalirIng.setBounds(402, 269, 87, 23);
			panelIngresos.add(btnSalirIng);
			
			btnEditarIng.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					int filaSeleccionada = tableIng.getSelectedRow();
					if(filaSeleccionada == -1)
					{
						JOptionPane.showMessageDialog(ventanaActual, "Debe Seleccionar algún Ingreso para editar " , "No ha Seleccionado para edición ", JOptionPane.ERROR_MESSAGE);
						return;
					}
					int idIngreso = Integer.parseInt((String)tableIng.getValueAt(filaSeleccionada, 0));
					idIngresoActual = idIngreso;
					txtIngreso.setText((String)tableIng.getValueAt(filaSeleccionada, 1));
					textAreaIngreso.setText((String)tableIng.getValueAt(filaSeleccionada, 3));
					btnEliminarIng.setEnabled(false);
					btnAgregarIngreso.setEnabled(false);
					btnConfEdicionIng.setEnabled(true);
				}
			});
			
			JPanel panelEgresos = new JPanel();
			tabbedPaneIngEgr.addTab("Panel Egresos"
					+ "", null, panelEgresos, null);
			panelEgresos.setLayout(null);
			
			JLabel lblValorDelEgreso = new JLabel("Valor del Egreso");
			lblValorDelEgreso.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblValorDelEgreso.setBounds(55, 43, 115, 23);
			panelEgresos.add(lblValorDelEgreso);
			
			txtEgreso = new JTextField();
			txtEgreso.setBounds(236, 45, 138, 20);
			panelEgresos.add(txtEgreso);
			txtEgreso.setColumns(15);
			
			JLabel lblDescripcionEgr = new JLabel("Descripci\u00F3n");
			lblDescripcionEgr.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblDescripcionEgr.setBounds(55, 97, 115, 23);
			panelEgresos.add(lblDescripcionEgr);
			
			textAreaEgreso = new JTextArea();
			textAreaEgreso.setLineWrap(true);
			textAreaEgreso.setRows(5);
			textAreaEgreso.setColumns(100);
			textAreaEgreso.setBounds(236, 97, 198, 64);
			panelEgresos.add(textAreaEgreso);
			
			JScrollPane scrollPaneEgresos = new JScrollPane();
			scrollPaneEgresos.setBounds(55, 192, 356, 70);
			panelEgresos.add(scrollPaneEgresos);
			
			tableEgr = new JTable();
			scrollPaneEgresos.setViewportView(tableEgr);
			tableEgr.setForeground(Color.black);
			JButton btnAgregarEgr = new JButton("Agregar Egreso");
			btnAgregarEgr.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					boolean validar = validarDatosEgreso();
					if (validar)
					{
						double valorEgreso = Double.parseDouble(txtEgreso.getText());
						String descripcion = textAreaEgreso.getText();
						int idEgresoIns = operTiendaCtrl.insertarEgreso(new Egreso(0,valorEgreso, fechaSis, descripcion));
						pintarEgresos(fechaSis);
						limpiarCamposEgreso();
					}
					
				}
			});
			btnAgregarEgr.setBounds(81, 154, 107, 23);
			panelEgresos.add(btnAgregarEgr);
			
			JButton btnEliminarEgr = new JButton("Eliminar");
			btnEliminarEgr.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
	
					int filaSeleccionada = tableEgr.getSelectedRow();
					//Hacemos la validación para decidir si se elimina o no
					int idEgreso = Integer.parseInt((String)tableEgr.getValueAt(filaSeleccionada, 0));
					JOptionPane.showMessageDialog(ventanaActual, "Esta seguro que se desea eliminar el Egreso con id " +  idEgreso , "Eliminacion Egreso ", JOptionPane.YES_NO_OPTION);
					operTiendaCtrl.eliminarEgreso(idEgreso);
					pintarEgresos(fechaSis);
					limpiarCamposEgreso();
				}
			});
			btnEliminarEgr.setBounds(10, 273, 107, 23);
			panelEgresos.add(btnEliminarEgr);
			
			JButton btnEditarEgr = new JButton("Editar");
			
			btnEditarEgr.setBounds(127, 273, 107, 23);
			panelEgresos.add(btnEditarEgr);
			
			JButton btnConfEdicionEgr = new JButton("Confirmar Edici\u00F3n");
			btnConfEdicionEgr.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					boolean validar = validarDatosEgreso();
					if (validar)
					{
						double valorEgreso = Double.parseDouble(txtEgreso.getText());
						String descripcion = textAreaEgreso.getText();
						boolean respuesta = operTiendaCtrl.editarEgreso(new Egreso(idEgresoActual,valorEgreso, fechaSis, descripcion ));
						if (respuesta)
						{
							JOptionPane.showMessageDialog(ventanaActual, "Se ha editado correctamente el Egreso " , "Confirmación Edición", JOptionPane.OK_OPTION);
							pintarEgresos(fechaSis);
							limpiarCamposEgreso();
							btnEliminarEgr.setEnabled(true);
							btnAgregarEgr.setEnabled(true);
							btnConfEdicionEgr.setEnabled(false);
						}
					}
				}
			});
			btnConfEdicionEgr.setBounds(244, 273, 148, 23);
			panelEgresos.add(btnConfEdicionEgr);
			
			JButton btnSalirEgr = new JButton("SALIR");
			btnSalirEgr.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			
			btnEditarEgr.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					int filaSeleccionada = tableEgr.getSelectedRow();
					if(filaSeleccionada == -1)
					{
						JOptionPane.showMessageDialog(ventanaActual, "Debe Seleccionar algún Egreso para editar " , "No ha Seleccionado para edición ", JOptionPane.ERROR_MESSAGE);
						return;
					}
					int idEgreso = Integer.parseInt((String)tableEgr.getValueAt(filaSeleccionada, 0));
					idEgresoActual = idEgreso;
					txtEgreso.setText((String)tableEgr.getValueAt(filaSeleccionada, 1));
					textAreaEgreso.setText((String)tableEgr.getValueAt(filaSeleccionada, 3));
					btnEliminarEgr.setEnabled(false);
					btnAgregarEgr.setEnabled(false);
					btnConfEdicionEgr.setEnabled(true);
				}
			});
			
			btnSalirEgr.setFont(new Font("Tahoma", Font.BOLD, 11));
			btnSalirEgr.setBounds(402, 273, 87, 23);
			panelEgresos.add(btnSalirEgr);
			
			JLabel lblFechaDelSistema = new JLabel("FECHA DEL SISTEMA");
			lblFechaDelSistema.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblFechaDelSistema.setBounds(222, 11, 138, 21);
			contentPaneIngEgr.add(lblFechaDelSistema);
			
			
			//Organizamos el tema de la fecha del sistema
			
			FechaSistema fecha = pedCtrl.obtenerFechasSistema();
			fechaSis = fecha.getFechaApertura();
			txtFechaSistema = new JTextField();
			txtFechaSistema.setEditable(false);
			txtFechaSistema.setBounds(371, 12, 138, 20);
			contentPaneIngEgr.add(txtFechaSistema);
			txtFechaSistema.setColumns(10);
			txtFechaSistema.setText(fechaSis);
			pintarIngresos( fechaSis);
			pintarEgresos( fechaSis);
		}
		
		//Método para pintar el Jtable de Ingresos
		public void pintarIngresos( String fecha)
		{
			Object[] columnsName = new Object[4];
	        
			columnsName[0] = "Id Ingreso";
			columnsName[1] = "Valor Ingreso";
	        columnsName[2] = "Fecha";
	        columnsName[3] = "Descripcion";
	        ArrayList ingresos = operTiendaCtrl.obtenerIngresos(fecha);
			//Se crea el default table model y allí esperamos poder digitar los valores
	        
			DefaultTableModel modelo = new DefaultTableModel(){
				
				public boolean isCellEditable(int rowIndex,int columnIndex){
	       	    	return(false);
	       	    }
				
			};
	        modelo.setColumnIdentifiers(columnsName);
	        
			for(int y = 0; y < ingresos.size();y++)
			{
				String[] fila =(String[]) ingresos.get(y);
				String[] filaponer = {fila[0],fila[1],fila[2], fila[3]}; 
				modelo.addRow(filaponer);
			}
			
	        
	        
	        
	        tableIng.setModel(modelo);
			
			
		}
		
		//Método para pintar el Jtable de Egresos
			public void pintarEgresos( String fecha)
			{
				Object[] columnsName = new Object[4];
		        
				columnsName[0] = "Id Egreso";
				columnsName[1] = "Valor Egreso";
		        columnsName[2] = "Fecha";
		        columnsName[3] = "Descripcion";
		        ArrayList egresos = operTiendaCtrl.obtenerEgresos(fecha);
		        
				//Se crea el default table model y allí esperamos poder digitar los valores
				DefaultTableModel modelo = new DefaultTableModel(){
					
					
					public boolean isCellEditable(int rowIndex,int columnIndex){
		       	    	return(false);
		       	    }
					
				};
		        modelo.setColumnIdentifiers(columnsName);
				for(int y = 0; y < egresos.size();y++)
				{
					String[] fila =(String[]) egresos.get(y);
					modelo.addRow(fila);
				}
				
		        
		        
		        
		        tableEgr.setModel(modelo);
				
				
			}
			
			public void limpiarCamposIngreso()
			{
				txtIngreso.setText("");
				textAreaIngreso.setText("");
			}
			public void limpiarCamposEgreso()
			{
				txtEgreso.setText("");
				textAreaEgreso.setText("");
			}
			
			public boolean validarDatosIngreso()
			{
				String valorIngreso = txtIngreso.getText();
				String descripcion = textAreaIngreso.getText();
				if(valorIngreso == "")
				{
					JOptionPane.showMessageDialog(ventanaActual, "Debe ingresar el valor del Ingreso", "Falta Información", JOptionPane.ERROR_MESSAGE);
					return(false);
				}
				if(descripcion == "")
				{
					JOptionPane.showMessageDialog(ventanaActual, "Debe ingresar el valor de la descripción del Ingreso", "Falta Información", JOptionPane.ERROR_MESSAGE);
					return(false);
				}
				try{
					double valor = Double.parseDouble(valorIngreso);
				}catch(Exception e)
				{
					System.out.println(e.toString());
					JOptionPane.showMessageDialog(ventanaActual, "El valor del ingreso debe ser numérico", "Falta Información", JOptionPane.ERROR_MESSAGE);
					return(false);
				}
				
				return(true);
			}
			
			public boolean validarDatosEgreso()
			{
				String valorEgreso = txtEgreso.getText();
				String descripcion = textAreaEgreso.getText();
				if(valorEgreso == "")
				{
					JOptionPane.showMessageDialog(ventanaActual, "Debe ingresar el valor del Egreso", "Falta Información", JOptionPane.ERROR_MESSAGE);
					return(false);
				}
				if(descripcion == "")
				{
					JOptionPane.showMessageDialog(ventanaActual, "Debe ingresar el valor de la descripción del Egreso", "Falta Información", JOptionPane.ERROR_MESSAGE);
					return(false);
				}
				try{
					double valor = Double.parseDouble(valorEgreso);
				}catch(Exception e)
				{
					System.out.println(e.toString());
					JOptionPane.showMessageDialog(ventanaActual, "El valor del Egreso debe ser numérico", "Falta Información", JOptionPane.ERROR_MESSAGE);
					return(false);
				}
				
				return(true);
			}
		
	}
