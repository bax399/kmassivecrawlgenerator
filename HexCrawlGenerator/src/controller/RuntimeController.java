package controller;
import model.*;
import model.redblob.*;
import view.MapDrawer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javax.swing.JFrame;
public class RuntimeController {
	public static void main(String[] args)
	{
		
		FileProcessor fp = new FileProcessor();
		PropertiesReader ptr = new PropertiesReader();
		fp.processFile("inputTest.txt", ptr);
		
		PropertiesFactory pf = new PropertiesFactory();
		
		BWeight bw = new BWeight(ptr.getTypeList("bweight").get(0),pf.processBiomes(ptr.getTypeList("biome")));
		
		Layout lt = new Layout(Layout.pointy,new Point(5,5),new Point(300,300));
		MapController mc = new MapController(15,10, bw, lt);
		
		JFrame f = new JFrame("HexMap");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		MapDrawer ui = new MapDrawer(mc.getHexes());
		f.add(ui);
		f.setSize(1920,1080);
		f.setVisible(true);
		
	}
}