package interfazGrafica;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import JTable.CellRenderIngInventario;
import JTable.CellRenderTransaccional;
import capaControlador.InventarioCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.FechaSistema;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;

public class VentInvInventarios extends JDialog {

	private JPanel contentPaneInventario;
	private JTable tableItemsInv;
	private JButton btnIngresarInventario;
	private JButton btnRealizarPedidoEspecial;
	private JButton btnRealizarVarianzaCierre;
	private JLabel lblImagen;
	private JButton btnRetornarAMen;
	InventarioCtrl invCtrl = new InventarioCtrl(PrincipalLogueo.habilitaAuditoria);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentInvInventarios frame = new VentInvInventarios(null, true);
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
	public VentInvInventarios(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
		FechaSistema fechSistema = pedCtrl.obtenerFechasSistema();
		String fecha = fechSistema.getFechaApertura();
		/**
		 * Define acciones para cuando se active de nuevo la ventana se refresque el contenido de inventarios
		 */
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {
				pintarResumenInventario(fecha);
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				pintarResumenInventario(fecha);
			}
		});
		setTitle("VENTANA INVENTARIOS");
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setDefaultCloseOperation(0);
		setBounds(0,0, 774, 571);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 774, 571);
		contentPaneInventario = new JPanel();
		contentPaneInventario.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPaneInventario);
		contentPaneInventario.setLayout(null);
		
				
		JScrollPane scrPaneItemsInv = new JScrollPane();
		scrPaneItemsInv.setBounds(10, 11, 537, 270);
		contentPaneInventario.add(scrPaneItemsInv);
		
		tableItemsInv = new JTable();
		scrPaneItemsInv.setColumnHeaderView(tableItemsInv);
		scrPaneItemsInv.setViewportView(tableItemsInv);
		
		
		JButton btnAdministrarItems = new JButton("Adm Items Inventario");
		btnAdministrarItems.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				VentInvCRUDItemInventario itemInv = new VentInvCRUDItemInventario(null, true);
				itemInv.setVisible(true);
			}
		});
		btnAdministrarItems.setBounds(557, 11, 191, 61);
		contentPaneInventario.add(btnAdministrarItems);
		
		btnIngresarInventario = new JButton("Ingresar Inventario");
		btnIngresarInventario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentInvIngresarInventario ingInventario = new VentInvIngresarInventario(null, true);
				ingInventario.setVisible(true);
			}
		});
		btnIngresarInventario.setBounds(557, 83, 191, 61);
		contentPaneInventario.add(btnIngresarInventario);
		
		JButton btnRetirarInventario = new JButton("Retirar Inventario");
		btnRetirarInventario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				VentInvRetirarInventario ingInventario = new VentInvRetirarInventario(null, true);
				ingInventario.setVisible(true);
			}
		});
		btnRetirarInventario.setBounds(557, 155, 191, 61);
		contentPaneInventario.add(btnRetirarInventario);
		
		btnRealizarPedidoEspecial = new JButton("Realizar Pedido Especial");
		btnRealizarPedidoEspecial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentInvPedidoEspecial pedEspecial = new VentInvPedidoEspecial(null, true);
				pedEspecial.setVisible(true);
			}
		});
		btnRealizarPedidoEspecial.setBounds(557, 226, 191, 61);
		contentPaneInventario.add(btnRealizarPedidoEspecial);
		
		btnRealizarVarianzaCierre = new JButton("Realizar Varianza Cierre");
		btnRealizarVarianzaCierre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentIngresarVarianza ingVarianza = new VentIngresarVarianza(null, true);
				ingVarianza.setVisible(true);
			}
		});
		btnRealizarVarianzaCierre.setBounds(10, 292, 191, 61);
		contentPaneInventario.add(btnRealizarVarianzaCierre);
		
		lblImagen = new JLabel("");
		lblImagen.setBounds(328, 295, 198, 126);
		ImageIcon icon = new ImageIcon(getClass().getResource("/imagenes/LogoPizzaAmericana.png"));
		Image imagen = icon.getImage();
		ImageIcon iconoEscalado = new ImageIcon (imagen.getScaledInstance(198,126,Image.SCALE_SMOOTH));
		lblImagen.setIcon(iconoEscalado);
		contentPaneInventario.add(lblImagen);
		
		btnRetornarAMen = new JButton("Retornar a Men\u00FA");
		btnRetornarAMen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnRetornarAMen.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnRetornarAMen.setBounds(10, 360, 191, 61);
		contentPaneInventario.add(btnRetornarAMen);
		pintarResumenInventario(fecha);
	}
	
	public void pintarResumenInventario( String fecha)
	{
		Object[] columnsName = new Object[6];
        
		columnsName[0] = "Id Item";
		columnsName[1] = "Nombre Item";
        columnsName[2] = "Inicio";
        columnsName[3] = "Retiro";
        columnsName[4] = "Ingreso";
        columnsName[5] = "Consumo";
       	ArrayList itemsResumen = invCtrl.obtenerItemInventarioResumen(fecha);
		DefaultTableModel modeloItemResumen = new DefaultTableModel(){
       	    public boolean isCellEditable(int rowIndex,int columnIndex){
       	    	return false;
       	    }
       	};
        modeloItemResumen.setColumnIdentifiers(columnsName);
		for(int y = 0; y < itemsResumen.size();y++)
		{
			String[] fila =(String[]) itemsResumen.get(y);
			modeloItemResumen.addRow(fila);
		}
		tableItemsInv.setModel(modeloItemResumen);
		setCellRender(tableItemsInv);
	}
	
	public void setCellRender(JTable table) {
        Enumeration<TableColumn> en = table.getColumnModel().getColumns();
        while (en.hasMoreElements()) {
            TableColumn tc = en.nextElement();
            tc.setCellRenderer(new CellRenderIngInventario());
        }
    }
}
