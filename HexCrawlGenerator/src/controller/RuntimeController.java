package controller;
import java.util.Map;
import static functions.PFunctions.outputString;
import java.util.Random;

import javax.swing.JFrame;

import functions.PFunctions;
import model.redblob.Layout;
import model.redblob.Point;
import view.MapDrawer;
public class RuntimeController 
{	
	final static int screenwidth = 1920;
	final static int screenheight = 1080;
	final static int size = 30; //mc 30
	
	public static void main(String[] args)
	{ 
		Random rand = new Random();
		
		PFunctions.outputString("Abc");
		
		FileProcessor fp = new FileProcessor();
		PropertiesReader ptr = new PropertiesReader();
		fp.processFile("inputTest.txt", ptr);

		//Layout lt = new Layout(Layout.pointy,new Point(size,size),new Point(screenwidth/2,screenheight/2)); //Spiral Map
		//MapController mc = new MapController(10, ptr, lt, rand); 
		
		Layout lt = new Layout(Layout.pointy,new Point(size,size),new Point(size,size));//Rectangle Map
		MapController mc = new MapController(10,10, ptr, lt, rand); 
		//h,w
		
		//System.out.println("total connects: " + mc.hexmap.getConnections().size());
		JFrame f = new JFrame("HexMap");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Deprecated
		//Pathfinder pfa = new Riverfinder();
		//Set<Connection> path = pfa.GreedyBFS(mc.hexmap, mc.getHexes().get(new Tuple(0,10)), mc.getHexes().get(new Tuple(0,-10)));		
		
		MapDrawer ui = new MapDrawer(mc.hexmap,mc.hexmap.getRiverConnections());
		f.add(ui);
		f.setSize(screenwidth,screenheight);
		f.setVisible(true);
		
		outputString("total hexes:" + mc.getHexes().size());
		
	}
}
