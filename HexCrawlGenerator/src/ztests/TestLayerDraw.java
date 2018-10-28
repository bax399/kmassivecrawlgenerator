 package ztests;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import model.Point;
import model.redblob.Hex;
import model.redblob.Layout;
import view.HexLayer;
import view.Layer;
import view.MainPanel;
import view.UIShape;
public class TestLayerDraw 
{
	final static double screenwidth = 1920d;
	final static double screenheight = 1080d;
	
	public static void main(String[] args)
	{
		TestLayerDraw dd = new TestLayerDraw();
		JFrame f = new JFrame("HexMap");
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize((int)screenwidth,(int)screenheight);
		MainPanel layers = new MainPanel();

		HexLayer hexes = new HexLayer(Layer.HEX, dd.generateMap());

		hexes.setLayerBounds();
		System.out.println(hexes.getSize());
		layers.add(hexes,hexes.getLayer());
//		layers.setSize(new Dimension((int)screenwidth,(int)screenheight));

//		JButton third= new JButton("Third");
//
//        third.setBackground(Color.green);
//        third.setBounds(10,10,100,100);
//        
//        layers.add(third, new Integer(1));
		
		f.add(layers);
		f.setVisible(true);
		f.setMinimumSize(f.getPreferredSize());		
	}
	
	public List<UIShape> generateMap()
	{
        Layout layout = new Layout(Layout.flat, new Point(10d,10d),new Point(0d,0d));		    	 
    	int map_radius=250; 
		List<UIShape> shapes = new ArrayList<>();
    	for (int q = -map_radius; q <= map_radius; q++) 
		{
		    int r1 = Math.max(-map_radius, -q - map_radius);
		    int r2 = Math.min(map_radius, -q + map_radius);
		    for (int r = r1; r <= r2; r++) 
		    {
  		    	Hex hh = new Hex(q,r);
  				Polygon a = layout.polygonCorners(hh);
  				UIShape shape = new UIShape(a,Color.BLACK);
  				shapes.add(shape);

		    }
		}
    
    	return shapes;
	}
	
	
}
