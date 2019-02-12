package interfazGrafica;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class ShapeTabbedPaneUI extends BasicTabbedPaneUI{
    
    //Para almacenar la forma del tabs
    private Polygon shape;
    //Para almacenar los puntos de la forma poligonal del tabs o.O
    private int xp[] = null; 
    private int yp[] = null;
    
    @Override
 protected void paintTabBackground( Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected ) 
 {
    Graphics2D g2D = (Graphics2D)g;
    //colores degradados para los tabs
    GradientPaint gradientSel = new GradientPaint( 0, 0,  new Color(242,249,242), 0, y+h/2, new Color(217,237,246) );
    GradientPaint gradientUnsel = new GradientPaint( 0, 0,  new Color(232,232,232), 0, y+h/2, new Color(205,205,205) );    
    
    switch( tabPlacement )
    {
        case LEFT: 
        case RIGHT:        
        case BOTTOM:
            /* codigo para estos tabs */
            break;
        case TOP:
        default:            
            xp = new int[]{ x, 
                            x,
                            x+4,
                            x+w+5,
                            x+w+5,
                            x+w,
                            x+w,
                            x
            };
            yp = new int[]{ y+h,
                            y+4,
                            y,
                            y,
                            y+5,
                            y+10,
                            y+h,
                            y+h
            };
          break;
    }
    
    shape = new Polygon( xp, yp, xp.length );
    
    if ( isSelected ) 
    {         
         g2D.setPaint( gradientSel );
    }
    else
    {        
        g2D.setPaint( gradientUnsel );
    }
    
     g2D.fill( shape );
 }
    
 @Override
  protected void paintTabBorder( Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) 
  {
      
    Graphics2D g2D = (Graphics2D)g;    
    
     switch( tabPlacement )
    {
        case LEFT: 
        case RIGHT:        
        case BOTTOM:
            /* codigo para estos tabs */
            break;
            
        case TOP:
        default:           
            xp = new int[]{ x, 
                            x,
                            x+4,
                            x+w+5,
                            x+w+5,
                            x+w,
                            x+w,
                            x
            };
            yp = new int[]{ y+h,
                            y+4,
                            y,
                            y,
                            y+5,
                            y+10,
                            y+h,
                            y+h
            };
          break;
    }
     
     shape = new Polygon( xp, yp, xp.length );
    
    if ( isSelected )
    {
         g2D.setColor( new Color(60,127,177) );
         g2D.drawPolyline( xp , yp , xp.length - 1 );
    }
    else
    {
        g2D.setColor( new Color(137,140,149) );
        g2D.drawPolyline( xp , yp , xp.length );
    }     
     
  } 
 
 @Override
 protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
     return 75; // manipulate this number however you please.
 }
 
 protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
     return 150; // manipulate this number however you please.
 }
 
}//--> fin clase UI
