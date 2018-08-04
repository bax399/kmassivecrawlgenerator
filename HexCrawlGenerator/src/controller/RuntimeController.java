package controller;
import model.*;
import model.redblob.*;
import view.MapDrawer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javax.swing.JFrame;
public class RuntimeController 
{	
	final static int screenwidth = 1920;
	final static int screenheight = 1080;
	final static int size = 2;
	
	
	public static void main(String[] args)
	{ 
		Random rand = new Random();
		
		
		FileProcessor fp = new FileProcessor();
		PropertiesReader ptr = new PropertiesReader();
		fp.processFile("inputTest.txt", ptr);
		
		PropertiesFactory pf = new PropertiesFactory();
		//BWeight bw = new BWeight(ptr.getTypeList("bweight").get(0),pf.processBiomes(ptr.getTypeList("biome")));
		BiomeChooser bc = new BiomeChooser(pf.processBiomes(ptr.getTypeList("biome")),rand);

		Layout lt = new Layout(Layout.pointy,new Point(size,size),new Point(screenwidth/2,screenheight/2)); //Spiral Map
		//Layout lt = new Layout(Layout.pointy,new Point(size,size),new Point(size,size));//Rectangle Map
		
		//h,w
		MapController mc = new MapController(150, bc, lt); 
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
		
	}
}
