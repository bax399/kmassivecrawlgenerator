package controller;
import model.*;
import view.*;
import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.Set;

public class TestHexMap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MapController mc = new MapController(10,10);
		JFrame f = new JFrame("HexMap");
		

		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		MapDrawer ui = new MapDrawer(mc.getHexes());
		
		f.add(ui);
		f.setSize(800,800);
		f.setVisible(true);

		System.out.println(mc.printString());



	}
	

}
