package controller;
import static functions.PFunctions.outputString;

import java.awt.GraphicsEnvironment;
import java.util.Random;

import javax.swing.JFrame;

import model.merowech.ConcaveHull.Point;
import model.redblob.Layout;
import view.MapDrawer;
public class RuntimeController 
{	
	final static double screenwidth = 1920d;
	final static double screenheight = 1080d;
	final static double size = 30d; //mc 30
	
	public static void main(String[] args)
	{ 
		Random rand = new Random();
		
		FileProcessor fp = new FileProcessor();
		PropertiesReader ptr = new PropertiesReader();
		fp.processFile("inputTest.txt", ptr);
 
		Layout lt = new Layout(Layout.pointy,new Point(size,size),new Point(screenwidth/2,screenheight/2)); //Spiral Map
		MapController mc = new MapController(10, ptr, lt, rand); 
		
		//Layout lt = new Layout(Layout.pointy,new Point(size,size),new Point(size,size)); //Rectangle Map
		//MapController mc = new MapController(20,30, ptr, lt, rand); //h,w
		//MapController mc = new MapController(300,500, ptr, lt, rand); //h,w
			
		
		//System.out.println("total connects: " + mc.hexmap.getConnections().size());
		JFrame f = new JFrame("HexMap");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Deprecated
		//Pathfinder pfa = new Riverfinder();
		//Set<Connection> path = pfa.GreedyBFS(mc.hexmap, mc.getHexes().get(new Tuple(0,10)), mc.getHexes().get(new Tuple(0,-10)));		
		
		MapDrawer ui = new MapDrawer(mc.hexmap, lt,(int)size);
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
