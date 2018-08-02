package interfazGrafica;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sun.javafx.geom.Ellipse2D;

import capaControlador.PedidoCtrl;
import capaModelo.DetallePedido;

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
	
	public static void main(String[] args) {
		try {
			VentPedPintar dialog = new VentPedPintar(null, false, 151);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * M�todo general que recibe el idPedido con base se realizar� el pintado del pedido
	 * @param idPedido
	 */
	public VentPedPintar(java.awt.Frame parent, boolean modal, int idPedido) {
		super(parent, modal);
		this.idPedido = idPedido;
		setTitle("DETALLE DEL PEDIDO");
		setBounds(100, 100, 532, 448);
		getContentPane().setLayout(new BorderLayout());
		//Se realiza el llenado del ArrayList una sola vez
		//Instanciamos objeto de la capa controladora
		PedidoCtrl pedCtrl = new PedidoCtrl ();
		//Retornamos los detalles pedidos con la informaci�n que requerimos para pintar el pedido.
		detPedidos = pedCtrl.obtenerDetallePedidoPintar(this.idPedido);
		panelPintar = new JPanel();
		panelPintar.setPreferredSize(new Dimension(200, 3050));
		PanelPrincipal = new JPanel();
		PanelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		PanelPrincipal.setLayout(null);
		scrollPane = new JScrollPane(panelPintar);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(20, 34, 486, 332);
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
				okButton.setActionCommand("REGRESAR");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			
		}
		
	}

	public void paint(Graphics g) {
		//Agregando super.paint(g) se solucionaron la mayor�a de problemas
		super.paintComponents(g);
		pintarPedido();

						
	}
	

	public void pintarPedido()
	{
		//Fijamos el n�mero de pedido en el encabezado gr�fico
		txtNumPedido.setText(Integer.toString(this.idPedido));
		//Definimos tama�os de ancho y alto para los diferentes tamanos
		int anchoXL = 200;
		int anchoGD = 150;
		int anchoMD = 100;
		int anchoPZ = 50;
		//Retornamos el graphics con el cual vamos a pintar el pedido
		Graphics2D g2d = (Graphics2D) panelPintar.getGraphics();
		g2d.setColor(Color.BLACK);
		//Fijamos un ancho a las lineas 
		g2d.setStroke( new BasicStroke( 2 ) );
		//Con este tomamos el idDetallePedidoMaster del detalle que estamos procesando en el momento
		int idMasterActual = 0;
		//Con este tomamos el idDetallePedidoMaster del detalle que procesamos en el detalle anterior
		//esto con el objetivo si ya hay un cambio del detalle padre delpedido.
		int idMasterAnterior = 0;
		//Tomamos el tipo de producto del Detalle Master
		String tipProdMaster = "";
		//Con esta variable tomamos el tamano para el caso de que sea un producto que lo tiene.
		String tamano = "";
		//Con las variables booleanas div1 y div2, controlamos si estamos procesando que mitad para los tipos de producto Principal
		boolean div1 = false;
		boolean div2 = false;
		//Variable en la que se llevar� el control del ancho actual del producto
		int anchoActual = 0;
		//Variable que controla los items pedidos a pintar.
		int itemPintar = 0;
		//Por intermedio de este ciclo recorremos todos los detalles pedidos que vamos a pintar
		//Variables donde almacenaremos los string de las adiciones de la pizza
		String adicion1 = "";
		String adicion2 = "";
		for(int i = 0; i < detPedidos.size(); i++)
		{
			//Obtenemos el detalle pedido que estamos recorriendo.
			DetallePedido detTem = detPedidos.get(i);
			//Obtenemos el tipo de producto del detalle que estamos procesando
			String tipPro = detTem.getTipoProducto();
			
			//Tomamos el iddetalleMaster del detalle pedido que estamos procesando 
			idMasterActual = detTem.getIdDetallePedidoMaster();
			//Si se da la condici�n de que idMasterActual = 0 es poruqe el item es un master
			if(idMasterActual == 0)
			{
				idMasterActual = detTem.getIdDetallePedido();
			}
			//Almacenamos el tamano del producto en caso de requerirse m�s adelante
			tamano = detTem.getTamano();
			//Comparamos si el Detalle Master actual es igual al anterior, significa qeu estamos procesando los detalles del pedido
			if(idMasterActual == idMasterAnterior)
			{
				//Preguntamos si el tipo de producto es "D" Detalle en este punto vendr�a el sabor de la pizza
				if(tipPro.trim().equals(new String("D")))
				{
					// Si la div1 es verdadero signfica que vamos para la segunda divisi�n, prendemos la segunda divisi�n
					if(div1)
					{
						div2 = true;
					}
					else
					{
						//Si es un tipo de producto divisi�n y no estaba prendido div1, signfica que se debe de prender.
						div1 = true;
						//Debemos de pintar la especialidad 1
					}
					if(div1 & !div2)
					{
						//Pintamos el sabor en la primera parte PERO PARA SABER EL PUNTO ES IMPORTANTE SABER EL TAMA�O
						g2d.setFont( new Font( "Tahoma", Font.BOLD, 20 ) );
						g2d.setColor(Color.BLACK);
						g2d.drawString(detTem.getDescCortaProducto(), 220, (100*(itemPintar - 1)+40));
					}
					else if(div1 & div2)
					{
						//Pintamos el sabor en la segunda parte
						g2d.setFont( new Font( "Tahoma", Font.BOLD, 20 ) );
						g2d.setColor(Color.BLACK);
						g2d.drawString(detTem.getDescCortaProducto(), 220 + (anchoActual/2), (100*(itemPintar - 1)+40));
					}
				}else if(tipPro.trim().equals(new String("A")))
				{
					//DEBEMOS DE PINTAR LA ADICI�N
					if(div1 & !div2)
					{
						//Pintamos la adici�n en la primera parte
						adicion1 = adicion1 + "+" + detTem.getDescCortaProducto();
					}
					else if(div1 & div2)
					{
						//Pintamos la adicion en la segunda parte
						adicion2 = adicion2 + "+" + detTem.getDescCortaProducto();
					}
				}
				else if(tipPro.trim().equals(new String("M")))
				{
					//DEBEMOS DE PINTAR EL MODIFICADOR
					if(div1 & !div2)
					{
						//Pintamos el sabor en la primera parte
					}
					else if(div1 & div2)
					{
						//Pintamos el sabor en la segunda parte
					}
				}
				
			}
			else//Por este camino deber�a de irse cuando hay una especie de quiebre en id Detalle pedido Master
			{
				//Antes de hacer los cambios preguntamos si hay adiciones en cuyo caso las pintamos
				// SI se cumple esta condici�n es porque hay una adici�n
				if(!adicion1.equals(new String(""))||!adicion2.equals(new String("")))
				{
					//pintamos las adicines
					g2d.setFont( new Font( "Tahoma", Font.BOLD, 10 ) );
					g2d.setColor(Color.BLACK);
					g2d.drawString(adicion1, 220 , (100*(itemPintar - 1)+60));
					g2d.drawString(adicion2, 220 + (anchoActual/2), (100*(itemPintar - 1)+60));
					adicion1="";
					adicion2="";
				}
			
				itemPintar++;
				//Pintar la linea separadora del pedido
//				g2d.setColor(Color.BLACK);
//				g2d.drawLine(0, 200*(itemPintar-1), 500, 200*(itemPintar-1));
				//Aumentamos los items a pintar
				tipProdMaster = tipPro;
				div1 = false;
				div2 = false;
				if(tipPro.trim().equals(new String("P")))
				{
					//Antes de cualquier drawString deberemos tener el setfont y el setcolor
					g2d.setFont( new Font( "Tahoma", Font.BOLD, 46 ) );
					g2d.setColor(Color.BLACK);
					g2d.drawString(tamano, 50, (200*(itemPintar - 1)+40));
					if(tamano.trim().equals(new String("XL")))
					{
						anchoActual = anchoXL;
						//Pintamos el c�rculo, teniendo en cuenta itemPintar que nos da el credimiento en la coordenanda Y para deliminar el item que estamos pintando
						//Tenemos unas variables fijas de ancho por tama�o
						g2d.setColor(Color.BLUE);
						g2d.drawOval(200, 200*(itemPintar-1), anchoXL,  anchoXL);
						g2d.drawLine(200+(anchoXL/2), 200*(itemPintar-1), 200+(anchoXL/2), 200*(itemPintar-1) + anchoXL);
					}else if(tamano.trim().equals(new String("GD")))
					{
						anchoActual = anchoGD;
						g2d.setColor(Color.RED);
						g2d.drawOval(200, 200*(itemPintar-1), anchoGD,  anchoGD);
						g2d.drawLine(200+(anchoGD/2), 200*(itemPintar-1), 200+(anchoGD/2), 200*(itemPintar-1) + anchoGD);
					}else if(tamano.trim().equals(new String("MD")))
					{
						anchoActual = anchoMD;
						g2d.setColor(Color.red);
						g2d.drawOval(200, 200*(itemPintar-1), anchoMD,  anchoMD);
						g2d.drawLine(200+(anchoMD/2), 200*(itemPintar-1), 200+(anchoMD/2), 200*(itemPintar-1) + anchoMD);
					}else if(tamano.trim().equals(new String("PZ")))
					{
						anchoActual = anchoPZ;
						g2d.setColor(Color.ORANGE);
						g2d.drawOval(200, 200*(itemPintar-1), anchoPZ,  anchoPZ);
						g2d.drawLine(200+(anchoPZ/2), 200*(itemPintar-1), 200+(anchoPZ/2), 200*(itemPintar-1) + anchoPZ);
					}
					
				}else if(tipPro.trim().equals(new String("O")))
				{
					//Pintamos con la imagen que tambien se almacenar�a en base de datos
				}
				//PINTAMOS LA CANTIDAD DEL PRODUCTO
				g2d.setFont( new Font( "Tahoma", Font.BOLD, 15 ) );
				g2d.setColor(Color.BLACK);
				g2d.drawString("CANTIDAD " + Double.toString(detTem.getCantidad()), 50, (200*(itemPintar - 1)+70));
			}
			//Independinete del camino debemos de tomar cual es  id Detalle Pedido Master anterior
			idMasterAnterior = idMasterActual;
		}
	}
}
