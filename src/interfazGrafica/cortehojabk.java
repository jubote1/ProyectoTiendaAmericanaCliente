package interfazGrafica;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import javax.print.*;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.standard.ColorSupported;


//La clase debe de implementar la impresión implements Printable

//clase pública que se ejecuta donde debe de estar el main que 
// llama a laotra clase.
public class cortehojabk implements Printable
{
	

    private String stringToPrint;

    public cortehojabk(String stringToPrint) {
        this.stringToPrint = stringToPrint;
    }

    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        if (pageIndex >= 1) {
            return Printable.NO_SUCH_PAGE;
        }
        g.setColor(Color.black);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
        g.translate(0, 0);
        int x = 0;
        int y = 0;
        //
        for (String line : stringToPrint.split("\n")) {
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
        }

        return Printable.PAGE_EXISTS;
    }
    public static void printer(String printerData, String designatedPrinter)
        throws IOException, PrinterException {
        try {

            PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
            PrintService designatedService = null;
            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
            AttributeSet aset = new HashAttributeSet();
            aset = new HashAttributeSet();
            aset.add(ColorSupported.NOT_SUPPORTED);
            String printers = "";
            for (int i = 0; i < printServices.length; i++) {
                printers += " service found " + printServices[i].getName() + "\n";
            }
            for (int i = 0; i < printServices.length; i++) {
                System.out.println(" service found " + printServices[i].getName());
                if (printServices[i].getName().equalsIgnoreCase(designatedPrinter)) {
                    System.out.println("I want this one: " + printServices[i].getName());
                    designatedService = printServices[i];
                    break;
                }
            }
            Writer fw = new OutputStreamWriter(new FileOutputStream("printing.txt"), StandardCharsets.UTF_8);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter writer = new PrintWriter(bw);
            writer.print(printers);
            writer.close();
            PrinterJob pj = PrinterJob.getPrinterJob();
            pj.setPrintService(designatedService);
            Printable painter;

            // Specify the painter
            painter = new cortehojabk(printerData);
            pj.setPrintable(painter);
            pj.print();

        } catch (PrinterException e) {
            Writer fw = new OutputStreamWriter(new FileOutputStream("log.txt", true), StandardCharsets.UTF_8);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter writer = new PrintWriter(bw);
            e.printStackTrace(writer);
            writer.close();
        }
    }
}