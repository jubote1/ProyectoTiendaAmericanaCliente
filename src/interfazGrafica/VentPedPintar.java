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

public class VentPedPintar extends JDialog {

	private final JPanel PanelPrincipal = new JPanel();
	private final JScrollPane scrollPane = new JScrollPane();
	private final int idPedido;
	private JTextField txtNumPedido;
	JPanel panelPintar;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentPedPintar dialog = new VentPedPintar(151);
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
	public VentPedPintar(int idPedido) {
		this.idPedido = idPedido;
		setTitle("DETALLE DEL PEDIDO");
		setBounds(100, 100, 532, 448);
		getContentPane().setLayout(new BorderLayout());
		PanelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(PanelPrincipal, BorderLayout.CENTER);
		PanelPrincipal.setLayout(null);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(20, 34, 486, 332);
		scrollPane.getViewport().setPreferredSize(new Dimension(100, 100));
		PanelPrincipal.add(scrollPane);
		
		panelPintar = new JPanel();
		scrollPane.setViewportView(panelPintar);
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
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public void paint(Graphics g) {
		
		pintarPedido();
//		Graphics2D g2d = (Graphics2D) scrollPane.getGraphics();
//		g2d.setColor(Color.RED);
//		g2d.fillOval(0, 0, 30, 30);
//		g2d.drawOval(0, 50, 30, 30);		
//		g2d.fillRect(50, 0, 30, 30);
//		g2d.drawRect(50, 50, 30, 30);
						
	}
	
	public void pintarPedido()
	{
		//Fijamos el número de pedido en el encabezado gráfico
		txtNumPedido.setText(Integer.toString(this.idPedido));
		//Definimos tamaños de ancho y alto para los diferentes tamanos
		int anchoXL = 200;
		int anchoGD = 150;
		int anchoMD = 100;
		int anchoPZ = 50;
		//Retornamos el graphics con el cual vamos a pintar el pedido
		Graphics2D g2d = (Graphics2D) panelPintar.getGraphics();
		g2d.setColor(Color.BLACK);
		//Fijamos un ancho a las lineas 
		g2d.setStroke( new BasicStroke( 2 ) );
		//Instanciamos objeto de la capa controladora
		PedidoCtrl pedCtrl = new PedidoCtrl ();
		//Retornamos los detalles pedidos con la información que requerimos para pintar el pedido.
		ArrayList<DetallePedido> detPedidos = pedCtrl.obtenerDetallePedidoPintar(this.idPedido);
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
			//Si se da la condición de que idMasterActual = 0 es poruqe el item es un master
			if(idMasterActual == 0)
			{
				idMasterActual = detTem.getIdDetallePedido();
			}
			//Almacenamos el tamano del producto en caso de requerirse más adelante
			tamano = detTem.getTamano();
			//Comparamos si el Detalle Master actual es igual al anterior, significa qeu estamos procesando los detalles del pedido
			if(idMasterActual == idMasterAnterior)
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
			else//Por este camino debería de irse cuando hay una especie de quiebre en id Detalle pedido Master
			{
				//Antes de hacer los cambios preguntamos si hay adiciones en cuyo caso las pintamos
				// SI se cumple esta condición es porque hay una adición
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
						//Pintamos el círculo, teniendo en cuenta itemPintar que nos da el credimiento en la coordenanda Y para deliminar el item que estamos pintando
						//Tenemos unas variables fijas de ancho por tamaño
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
					//Pintamos con la imagen que tambien se almacenaría en base de datos
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
