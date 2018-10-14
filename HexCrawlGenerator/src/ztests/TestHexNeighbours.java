package ztests;
import javax.swing.JFrame;

import model.FilledHex;
import model.merowech.ConcaveHull.Point;
import model.redblob.Layout;
public class TestHexNeighbours
{
	
	final static int screenwidth = 1920;
	final static int screenheight = 1080;	

	public static void main(String[] args)
	{ 	
		FilledHex h = new FilledHex(2,2);
		
		Layout lt = new Layout(Layout.pointy,new Point(30d,30d),new Point(screenwidth/2d,screenheight/2d)); //Spiral Map
		
		h.setShape(lt.polygonCorners(h));
		
		JFrame f = new JFrame("HexMap");
		TestDrawer td = new TestDrawer(h,lt);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		f.add(td);
		f.setSize(screenwidth,screenheight);
		f.setVisible(true);		
		
	}

	
}
