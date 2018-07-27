package tests;
import model.*;
import view.MapDrawer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import javax.swing.JFrame;

import controller.FileProcessor;
import controller.MapController;
import controller.PropertiesFactory;
import controller.PropertiesReader;
public class TestBiomeTypes {
	public static void main(String[] args)
	{
		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		System.out.println("RootPath: "+rootPath);
		
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		System.out.println("Current relative path is: " + s);			
		
		FileProcessor fp = new FileProcessor();
		PropertiesReader ptr = new PropertiesReader(); 
		fp.processFile("inputTest.txt", ptr);
		
		System.out.println(ptr.getTypeList("biome").get(0));
		
		PropertiesFactory pf = new PropertiesFactory();
		
		BWeight bw = new BWeight(ptr.getTypeList("bweight").get(0),pf.processBiomes(ptr.getTypeList("biome")));
		
		
		MapController mc = new MapController(15,10, bw);
		JFrame f = new JFrame("HexMap");
		

		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		MapDrawer ui = new MapDrawer(mc);
		
		f.add(ui);
		f.setSize(1920,1080);
		f.setVisible(true);

		System.out.println(mc.printString());
		
	}
}
