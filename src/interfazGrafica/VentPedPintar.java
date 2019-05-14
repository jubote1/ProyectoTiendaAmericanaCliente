package interfazGrafica;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import capaControlador.ParametrosProductoCtrl;
import capaControlador.PedidoCtrl;
import capaModelo.DetallePedido;
import capaModelo.Producto;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentPedPintar extends JDialog {

	private JPanel PanelPrincipal;
	private JScrollPane scrollPane;
	private int idPedido;
	private JTextField txtNumPedido;
	private JPanel panelPintar;
	private ParametrosProductoCtrl parCtrl = new ParametrosProductoCtrl(PrincipalLogueo.habilitaAuditoria);
	private PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
	//Definimos la consulta de los datos de manera global
	ArrayList<DetallePedido> detPedidos;
	/**
	 * Launch the application.
	 */
	 private int oldVPos = 0;
     private int oldHPos = 0;
     
	 AdjustmentListener adjustmentListener = new AdjustmentListener() {
		 
	        @Override
	        public void adjustmentValueChanged(AdjustmentEvent e) {
	            int vPos = scrollPane.getVerticalScrollBar().getValue();
	                
	 
	            if (e.getSource().equals(scrollPane.getVerticalScrollBar()) 
	                    && vPos != oldVPos) {
	                oldVPos = vPos;
	                repaint();
	            }
	        }
	    };
	 private JButton btnConfirmarYSiguiente;
	
	public static void main(String[] args) {
		try {
			VentPedPintar dialog = new VentPedPintar(null, false, 183, false, null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método general que recibe el idPedido con base se realizará el pintado del pedido
	 * @param idPedido
	 */
	public VentPedPintar(java.awt.Frame parent, boolean modal, int idPedido, boolean mostrarSiguiente, VentPedCocina ventPedidoCocina) {
		super(parent, modal);
		this.idPedido = idPedido;
		setTitle("DETALLE DEL PEDIDO");
		setBounds(0,0, 632, 448);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
	    setBounds(50, (alto / 2) - (this.getHeight() / 2), 632, 448);
	    //Se realiza cambio para poder tener más a la izquierda y que el montador pueda ver los siguientes pedidos
		//setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 632, 448);
		getContentPane().setLayout(new BorderLayout());
		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
		//Se realiza el llenado del ArrayList una sola vez
		//Instanciamos objeto de la capa controladora
		
		//Retornamos los detalles pedidos con la información que requerimos para pintar el pedido.
		detPedidos = pedCtrl.obtenerDetallePedidoPintar(this.idPedido);
		panelPintar = new JPanel();
		panelPintar.setPreferredSize(new Dimension(250, 3050));
		PanelPrincipal = new JPanel();
		PanelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		PanelPrincipal.setLayout(null);
		scrollPane = new JScrollPane(panelPintar);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(20, 34, 584, 332);
		scrollPane.getViewport().setPreferredSize(new Dimension(300, 3050));
		scrollPane.getVerticalScrollBar().addAdjustmentListener(
                adjustmentListener);
        PanelPrincipal.add(scrollPane);
		getContentPane().add(PanelPrincipal, BorderLayout.CENTER);
		
		scrollPane.setViewportView(panelPintar);
		//scrollPane.add(panelPintar);
		{
			JLabel lblNmeroDePedido = new JLabel("N\u00DAMERO DE PEDIDO:");
			lblNmeroDePedido.setFont(new Font("Tahoma", Font.BOLD, 20));
			lblNmeroDePedido.setBounds(20, 9, 238, 14);
			PanelPrincipal.add(lblNmeroDePedido);
		}
		
		txtNumPedido = new JTextField();
		txtNumPedido.setEditable(false);
		txtNumPedido.setFont(new Font("Tahoma", Font.BOLD, 20));
		txtNumPedido.setBounds(288, 3, 145, 20);
		PanelPrincipal.add(txtNumPedido);
		txtNumPedido.setColumns(10);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("REGRESAR");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				
				btnConfirmarYSiguiente = new JButton("Confirmar y Siguiente Pedido");
				btnConfirmarYSiguiente.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
						ventPedidoCocina.repaint();
						if(ventPedidoCocina.tableMontadorTodos.getRowCount()> 0)
						{
							int idPedidoFP = Integer.parseInt(ventPedidoCocina.tableMontadorTodos.getValueAt(0, 0).toString());
							VentPedPintar ventPedidoPintar = new VentPedPintar(null, true,idPedidoFP, true, ventPedidoCocina);
							ventPedidoPintar.setVisible(true);
						}
					}
				});
				buttonPane.add(btnConfirmarYSiguiente);
				if(!mostrarSiguiente)
				{
					btnConfirmarYSiguiente.setVisible(false);
				}
				okButton.setActionCommand("REGRESAR");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			
		}
		
	}

	public void paint(Graphics g) {
		//Agregando super.paint(g) se solucionaron la mayoría de problemas
		super.paintComponents(g);
		pintarPedido();

						
	}
	

	public void pintarPedido()
	{
		//Fijamos el número de pedido en el encabezado gráfico
		txtNumPedido.setText(Integer.toString(this.idPedido));
		//Retornamos el graphics con el cual vamos a pintar el pedido
		Graphics2D g2d = (Graphics2D) panelPintar.getGraphics();
		pedCtrl.pintarPedido(this.idPedido, g2d);

	}
}
