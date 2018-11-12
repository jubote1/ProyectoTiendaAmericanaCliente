package interfazGrafica;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import capaControlador.ClienteCtrl;
import capaControlador.ParametrosDireccionCtrl;
import capaModelo.Cliente;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class VentCliBuscarCliente extends JFrame {
	private JTable tableClienteBusqueda;
	private int idCliente;
	
	public int getIdCliente()
	{
		return(this.idCliente);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentCliBuscarCliente frame = new VentCliBuscarCliente("");
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
	public VentCliBuscarCliente(String Telefono) {
		idCliente = 0;
		setTitle("BUSCAR CLIENTE");
		setBounds(0,0, 493, 300);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 450, 300);
		getContentPane().setLayout(null);
		JButton btnSeleccionar = new JButton("Seleccionar");
		
		btnSeleccionar.setBounds(66, 176, 112, 38);
		getContentPane().add(btnSeleccionar);
		
		JButton btnRegresar = new JButton("Regresar");
		btnRegresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentCliCliente ventCliente = new VentCliCliente(0, null, true);
				ventCliente.setVisible(true);
				dispose();
			}
		});
		btnRegresar.setBounds(248, 176, 105, 38);
		getContentPane().add(btnRegresar);
		
		tableClienteBusqueda = new JTable();
		tableClienteBusqueda.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tableClienteBusqueda.setBounds(10, 38, 414, 100);
		getContentPane().add(tableClienteBusqueda);
		DefaultTableModel modelo = pintarClientes(Telefono);
		this.tableClienteBusqueda.setModel(modelo);
		
		
		//Incluimos el evento de dobleclic sobre el Jtable
		tableClienteBusqueda.addMouseListener(new java.awt.event.MouseAdapter() {
		      public void mouseClicked(java.awt.event.MouseEvent e) {
		      if(e.getClickCount()==2){
		    	  seleccionarCliente();
		        }
		      
		 }
		});
		
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				seleccionarCliente();
			}
		});

	}
	
	public DefaultTableModel pintarClientes(String Telefono)
	{
		Object[] columnsName = new Object[3];
        
        columnsName[0] = "Id Cliente";
        columnsName[1] = "Nombre Cliente";
        columnsName[2] = "Direccion";
        
        ClienteCtrl clie = new ClienteCtrl(PrincipalLogueo.habilitaAuditoria);
		ArrayList<Cliente> clientes = clie.obtenerClientes(Telefono);
		DefaultTableModel modelo = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	return false;
       	    }
       	    
       	    
       	};
		
		modelo.setColumnIdentifiers(columnsName);
		for(int y = 0; y < clientes.size();y++)
		{
			Cliente cliente =(Cliente) clientes.get(y);
			String[] fila = {Integer.toString(cliente.getIdcliente()), cliente.getNombres() + " " + cliente.getApellidos(), cliente.getDireccion()};
			modelo.addRow(fila);
		}
		return(modelo);
		
	}
	
	public void seleccionarCliente()
	{
		//Definici�n de las acciones cuando se seleccione un cliente
		int filaSeleccionada = tableClienteBusqueda.getSelectedRow();
		if(filaSeleccionada == -1)
		{
			JOptionPane.showMessageDialog(null, "Debe Seleccionar alg�n Cliente para regresar a la pantalla de clientes " , "No ha Seleccionado Cliente ", JOptionPane.ERROR_MESSAGE);
			return;
		}
		//Capturamos el idcliente seleccionado
		int idClien =  Integer.parseInt((String)tableClienteBusqueda.getValueAt(filaSeleccionada, 0));
		idCliente = idClien;
		VentCliCliente ventCliente = new VentCliCliente(idClien, null, true);
		ventCliente.setVisible(true);
		ventCliente.btnActualizarCliente.setEnabled(true);
		ventCliente.btnCrearCliente.setEnabled(false);
		ventCliente.btnAgregarDireccion.setEnabled(true);
		dispose();
	}
}
