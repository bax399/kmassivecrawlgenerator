package controller;
import model.*;
import model.redblob.*;
import view.MapDrawer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javax.swing.JFrame;
public class RuntimeController {
	final static int screenwidth = 1920/2;
	final static int screenheight = 1080/2;
	public static void main(String[] args)
	{
		
		FileProcessor fp = new FileProcessor();
		PropertiesReader ptr = new PropertiesReader();
		fp.processFile("inputTest.txt", ptr);
		
		PropertiesFactory pf = new PropertiesFactory();
		
		BWeight bw = new BWeight(ptr.getTypeList("bweight").get(0),pf.processBiomes(ptr.getTypeList("biome")));
		
		//Layout lt = new Layout(Layout.pointy,new Point(20,20),new Point(screenwidth/2,screenheight/2)); //Spiral Map
		Layout lt = new Layout(Layout.pointy,new Point(10,10),new Point(10,10));		
		//Use primes for rectangular maps.
		//Max 33 for height???
		//TODO fix height error
		
		MapController mc = new MapController(32,91, bw, lt);
		
		JFrame f = new JFrame("HexMap");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		MapDrawer ui = new MapDrawer(mc.getHexes());
		f.add(ui);
		f.setSize(screenwidth,screenheight);
		f.setVisible(true);
		
	}
}
