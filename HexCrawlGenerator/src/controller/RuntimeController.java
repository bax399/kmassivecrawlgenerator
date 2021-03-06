package controller;
import static functions.PFunctions.outputString;

import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;

import model.merowech.ConcaveHull.Point;
import model.redblob.Layout;
import view.MapDrawerPanel;
public class RuntimeController 
{	
	final static double screenwidth = 1920d;
	final static double screenheight = 1080d;
	final static double size = 30d; //mc 30
	
	public static void main(String[] args)
	{ 
		Random rand = new Random();
		long time1 = System.currentTimeMillis();
		FileProcessor fp = new FileProcessor();
		PropertiesReader ptr = new PropertiesReader();
		fp.processFile("inputTest.txt", ptr);
 
		Layout lt = new Layout(Layout.pointy,new Point(size,size),new Point(screenwidth/2,screenheight/2)); //Spiral Map
		MapController mc = new MapController(100, ptr, lt, rand); 

		long time2 = System.currentTimeMillis();
		long timeTaken = (time2 - time1 );
		outputString("TimeTaken " + timeTaken +" ms");	
		
		JFrame f = new JFrame("HexMap");

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MapDrawerPanel ui = new MapDrawerPanel(mc.hexmap, lt,(int)size/30);
		f.addKeyListener(new KeyListener() {
	        @Override
	        public void keyTyped(KeyEvent e) {
	        }

	        @Override
	        public void keyPressed(KeyEvent e) {
	            ui.opacity = 200;
	            System.out.println(ui.opacity);
	        }

	        @Override
	        public void keyReleased(KeyEvent e) {
	        	ui.opacity=10;
	        	System.out.println(ui.opacity);	        	
	        }
	    });		
		f.add(ui);
		f.setSize((int)screenwidth,(int)screenheight);
		f.setVisible(true);
		
		outputString("total hexes:" + mc.getHexes().size());

		
	}
	
	public static void listAvailableFonts()
	{
	    String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

	    for ( int i = 0; i < fonts.length; i++ )
	    {
	      outputString(fonts[i]);
	    }		
	}
}
