package controller;
import java.util.*;
import model.*;
import view.*;
import javax.swing.*;

public class TestHexMap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HexMap map = new HexMap(3,4);
		JFrame f = new JFrame("Title");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MapDrawer ui = new MapDrawer();
		f.add(ui);
		f.setSize(400,250);
		f.setVisible(true);
		
		System.out.println(map.toString() + map.getTotal());
		


	}

}
