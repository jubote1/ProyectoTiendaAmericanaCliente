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

import com.sun.javafx.geom.Ellipse2D;

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
			VentPedPintar dialog = new VentPedPintar(null, false, 183);
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
	public VentPedPintar(java.awt.Frame parent, boolean modal, int idPedido) {
		super(parent, modal);
		this.idPedido = idPedido;
		setTitle("DETALLE DEL PEDIDO");
		setBounds(0,0, 632, 448);
		int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	    int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds((ancho / 2) - (this.getWidth() / 2), (alto / 2) - (this.getHeight() / 2), 632, 448);
		getContentPane().setLayout(new BorderLayout());
		ImageIcon img = new ImageIcon("iconos\\LogoPequePizzaAmericana.jpg");
		setIconImage(img.getImage());
		//Se realiza el llenado del ArrayList una sola vez
		//Instanciamos objeto de la capa controladora
		PedidoCtrl pedCtrl = new PedidoCtrl(PrincipalLogueo.habilitaAuditoria);
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
		//Definimos tamaños de ancho y alto para los diferentes tamanos
		int anchoXL = 250;
		int anchoGD = 200;
		int anchoMD = 150;
		int anchoPZ = 120;
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
		//Variable en la que se llevará el control del ancho actual del producto
		int anchoActual = 0;
		//Variable que controla los items pedidos a pintar.
		int itemPintar = 1;
		//Por intermedio de este ciclo recorremos todos los detalles pedidos que vamos a pintar
		//Variables donde almacenaremos los string de las adiciones de la pizza
		String adicion1 = "";
		String adicion2 = "";
		String con1 = "";
		String con2 = "";
		String sin1 = "";
		String sin2 = "";
		for(int i = 0; i < detPedidos.size(); i++)
		{
			//Obtenemos el detalle pedido que estamos recorriendo.
			DetallePedido detTem = detPedidos.get(i);
			//Luego de obtenido del detallePedido debemos validar si este esta anulado para saber qeu no lo debemos de pintar
			if((detTem.getEstado() == null)||(detTem.getEstado().equals(new String(""))))
			{
				//Obtenemos el tipo de producto del detalle que estamos procesando
				String tipPro = detTem.getTipoProducto();
				
				//Tomamos el iddetalleMaster del detalle pedido que estamos procesando 
				idMasterActual = detTem.getIdDetallePedidoMaster();
				//Si se da la condición de que idMasterActual = 0 es poruqe el item es un master
				if(idMasterActual == 0)
				{
					idMasterActual = detTem.getIdDetallePedido();
				}
				//Almacenamos el tamano del producto en caso de requerirse más adelante
				tamano = detTem.getTamano();
				//Comparamos si el Detalle Master actual es igual al anterior, significa qeu estamos procesando los detalles del pedido
				if((idMasterActual == idMasterAnterior)&&(!tipPro.equals(new String("O"))))
				{
					//Preguntamos si el tipo de producto es "D" Detalle en este punto vendría el sabor de la pizza
					if(tipPro.trim().equals(new String("D")))
					{
						// Si la div1 es verdadero signfica que vamos para la segunda división, prendemos la segunda división
						if(div1)
						{
							div2 = true;
						}
						else
						{
							//Si es un tipo de producto división y no estaba prendido div1, signfica que se debe de prender.
							div1 = true;
							//Debemos de pintar la especialidad 1
						}
						if(div1 & !div2)
						{
							//Pintamos el sabor en la primera parte PERO PARA SABER EL PUNTO ES IMPORTANTE SABER EL TAMAÑO
							g2d.setFont( new Font( "Tahoma", Font.BOLD, 10 ) );
							g2d.setColor(Color.BLACK);
							g2d.drawString(detTem.getDescCortaProducto().toUpperCase(), 160, (250*(itemPintar - 1)+40));
						}
						else if(div1 & div2)
						{
							//Pintamos el sabor en la segunda parte
							g2d.setFont( new Font( "Tahoma", Font.BOLD, 10 ) );
							g2d.setColor(Color.BLACK);
							g2d.drawString(detTem.getDescCortaProducto().toUpperCase(), 260 + (anchoActual/2), (250*(itemPintar - 1)+40));
						}
					}else if(tipPro.trim().equals(new String("A")))
					{
						//DEBEMOS DE PINTAR LA ADICIÓN
						if(div1 & !div2)
						{
							//Pintamos la adición en la primera parte
							adicion1 = adicion1 + "+" + detTem.getDescCortaProducto();
						}
						else if(div1 & div2)
						{
							//Pintamos la adicion en la segunda parte
							adicion2 = adicion2 + "+" + detTem.getDescCortaProducto();
						}
					}
					else if(tipPro.trim().equals(new String("MC")))
					{
						//DEBEMOS DE PINTAR EL MODIFICADOR
						if(div1 & !div2)
						{
							//Pintamos el modificador con en la primera parte
							con1 = con1 + " " + detTem.getDescCortaProducto();
						}
						else if(div1 & div2)
						{
							//Pintamos el modificador con en la segunda parte
							con2 = con2 + " " + detTem.getDescCortaProducto();
						}
					}
					else if(tipPro.trim().equals(new String("MS")))
					{
						//DEBEMOS DE PINTAR EL MODIFICADOR
						if(div1 & !div2)
						{
							//Pintamos el modificador sin en la primera parte
							sin1 = sin1 + " " + detTem.getDescCortaProducto();
						}
						else if(div1 & div2)
						{
							//Pintamos el modificador sin en la  segunda parte
							sin2 = sin2 + " " + detTem.getDescCortaProducto();
						}
					}
					
				}
				else//Por este camino debería de irse cuando hay una especie de quiebre en id Detalle pedido Master
				{
					//Antes de hacer los cambios preguntamos si hay adiciones en cuyo caso las pintamos
					// SI se cumple esta condición es porque hay una adición
					if(!adicion1.equals(new String(""))||!adicion2.equals(new String(""))||!con1.equals(new String(""))||!con2.equals(new String(""))||!sin1.equals(new String(""))||!sin2.equals(new String("")))
					{
						//pintamos las adicines
						g2d.setFont( new Font( "Tahoma", Font.BOLD, 10 ) );
						g2d.setColor(Color.BLACK);
						// Se dibujan las adiciones
						g2d.drawString(adicion1, 160 , (100*(itemPintar - 1)+60));
						g2d.drawString(adicion2, 260 + (anchoActual/2), (100*(itemPintar - 1)+60));
						// Se dibujan los modificadores con
						g2d.drawString(con1, 160 , (100*(itemPintar - 1)+80));
						g2d.drawString(con2, 260 + (anchoActual/2), (100*(itemPintar - 1)+80));
						// Se dibujan los modificadore sin
						g2d.drawString(sin1, 160 , (100*(itemPintar - 1)+100));
						g2d.drawString(sin2, 260 + (anchoActual/2), (100*(itemPintar - 1)+100));
						adicion1="";
						adicion2="";
						sin1 = "";
						sin2 = "";
						con1 = "";
						con2 = "";
					}
					
					if (div1 && div2)
					{
						itemPintar++;
					}
					
					//Pintar la linea separadora del pedido
//					g2d.setColor(Color.BLACK);
//					g2d.drawLine(0, 200*(itemPintar-1), 500, 200*(itemPintar-1));
					//Aumentamos los items a pintar
					tipProdMaster = tipPro;
					div1 = false;
					div2 = false;
					if(tipPro.trim().equals(new String("P")))
					{
						//Antes de cualquier drawString deberemos tener el setfont y el setcolor
						g2d.setFont( new Font( "Tahoma", Font.BOLD, 32 ) );
						g2d.setColor(Color.BLACK);
						g2d.drawString(tamano, 10, (250*(itemPintar - 1)+40));
						if(tamano.trim().equals(new String("XL")))
						{
							anchoActual = anchoXL;
							//Pintamos el círculo, teniendo en cuenta itemPintar que nos da el credimiento en la coordenanda Y para deliminar el item que estamos pintando
							//Tenemos unas variables fijas de ancho por tamaño
							g2d.setColor(Color.BLUE);
							g2d.drawOval(250, 250*(itemPintar-1), anchoXL,  anchoXL);
							g2d.drawLine(250+(anchoXL/2), 250*(itemPintar-1), 250+(anchoXL/2), 250*(itemPintar-1) + anchoXL);
						}else if(tamano.trim().equals(new String("GD")))
						{
							anchoActual = anchoGD;
							g2d.setColor(Color.RED);
							g2d.drawOval(250, 250*(itemPintar-1), anchoGD,  anchoGD);
							g2d.drawLine(250+(anchoGD/2), 250*(itemPintar-1), 250+(anchoGD/2), 250*(itemPintar-1) + anchoGD);
						}else if(tamano.trim().equals(new String("MD")))
						{
							anchoActual = anchoMD;
							g2d.setColor(Color.red);
							g2d.drawOval(250, 250*(itemPintar-1), anchoMD,  anchoMD);
							g2d.drawLine(250+(anchoMD/2), 250*(itemPintar-1), 250+(anchoMD/2), 250*(itemPintar-1) + anchoMD);
						}else if(tamano.trim().equals(new String("PZ")))
						{
							anchoActual = anchoPZ;
							g2d.setColor(Color.ORANGE);
							g2d.drawOval(250, 250*(itemPintar-1), anchoPZ,  anchoPZ);
							g2d.drawLine(250+(anchoPZ/2), 250*(itemPintar-1), 250+(anchoPZ/2), 250*(itemPintar-1) + anchoPZ);
						}
						
					}else if(tipPro.trim().equals(new String("O")))
					{
						//Pintamos con la imagen que tambien se almacenaría en base de datos
						//Obtenemos el idProducto
						int idProd = detTem.getIdProducto();
						Producto otroProd = parCtrl.obtenerProducto(idProd);
						
						try
						{
							Image image = null;
							InputStream in = new ByteArrayInputStream(otroProd.getImagen());
							image = ImageIO.read(in);
							//ImageIcon imgi = new ImageIcon(image.getScaledInstance(60, 60, 0));
							g2d.drawImage(image,250, 250*(itemPintar-1) , null);
						}catch(Exception e)
						{
							
						}
						itemPintar++;
					}
					//PINTAMOS LA CANTIDAD DEL PRODUCTO
					g2d.setFont( new Font( "Tahoma", Font.BOLD, 15 ) );
					g2d.setColor(Color.BLACK);
					g2d.drawString("CANTIDAD " + Double.toString(detTem.getCantidad()), 10, (250*(itemPintar - 1)+100));
					
				}
				//Independinete del camino debemos de tomar cual es  id Detalle Pedido Master anterior
				idMasterAnterior = idMasterActual;
			}
			
		}
		//MOD 29/08/2018
		//Se realiza esta modificación para que si solo hubo un producto master al final se pinte las adiciones y los productos CON que se tienen
		//Lo anterior debido a que como no hay cambio de producto master no se cumple la condición y no se está imprimiendo esta información
		if(!adicion1.equals(new String(""))||!adicion2.equals(new String(""))||!con1.equals(new String(""))||!con2.equals(new String(""))||!sin1.equals(new String(""))||!sin2.equals(new String("")))
		{
			//pintamos las adicines
			g2d.setFont( new Font( "Tahoma", Font.BOLD, 10 ) );
			g2d.setColor(Color.BLACK);
			// Se dibujan las adiciones
			g2d.drawString(adicion1, 220 , (100*(itemPintar - 1)+60));
			g2d.drawString(adicion2, 260 + (anchoActual/2), (100*(itemPintar - 1)+60));
			// Se dibujan los modificadores con
			g2d.drawString(con1, 50 , (100*(itemPintar - 1)+80));
			g2d.drawString(con2, 260 + (anchoActual/2), (100*(itemPintar - 1)+80));
			// Se dibujan los modificadore sin
			g2d.drawString(sin1, 220 , (100*(itemPintar - 1)+100));
			g2d.drawString(sin2, 260 + (anchoActual/2), (100*(itemPintar - 1)+100));
			adicion1="";
			adicion2="";
			sin1 = "";
			sin2 = "";
			con1 = "";
			con2 = "";
		}
	}
}
