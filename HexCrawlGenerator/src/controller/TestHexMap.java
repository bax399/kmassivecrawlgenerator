package controller;
import java.util.*;
import model.*;
import view.*;
import javax.swing.*;

public class TestHexMap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HexMap map = new HexMap(8,16);
		JFrame f = new JFrame("Title");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MapDrawer ui = new MapDrawer(map.getHexes());
		f.add(ui);
		f.setSize(800,800);
		f.setVisible(true);
		
		System.out.println(map.toString() + map.getTotal());
		


	}

}
