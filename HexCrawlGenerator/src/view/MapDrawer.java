package view;
import java.util.*;

import model.redblob.Tuple;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import model.FilledHex;
import model.ConnectedHexMap;
import model.HexMap;
import model.*;
//Panel draws to screen.
public class MapDrawer extends JPanel 
{

	ConnectedHexMap hexes;
	Set<Connection> cc;
	static int size = 10;
	
	public MapDrawer(ConnectedHexMap h)
	{
		hexes=h;
	}

	public MapDrawer(ConnectedHexMap h, Set<Connection> c)
	{
		hexes=h;
		cc=c;
	}	
	
	//Needed method, draws to screen
	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g);
		this.setBackground(Color.WHITE); 
		Font font = new Font("Courier New",Font.PLAIN,11); 
		Set<Tuple> ss = hexes.getHexes().keySet();
		Iterator<Tuple> it = ss.iterator();
		g.setFont(font);
		while (it.hasNext())
		{
			FilledHex hh = hexes.getHex(it.next());
			
			//Change offset to be relative to layout size 
			g.setColor(hh.getBiome().getColor());
			g.fillPolygon(hh.shape);

			g.setColor(Color.BLACK);
			g.drawPolygon(hh.shape);
			g.setColor(Color.BLACK);
			g.drawString(hh.getBiome().getName().substring(0,2), (int)hh.center.x-5, (int)hh.center.y+5);
			//g.drawString(""+hh.q,(int)hh.center.x-3, (int)hh.center.y-3);
			//g.drawString(""+hh.r,(int)hh.center.x-3, (int)hh.center.y+5);
			
		}
		

		
		Iterator<Connection> ic = cc.iterator();
		g.setColor(Color.RED);
		while(ic.hasNext())
		{
			Connection edge = ic.next();
			g.drawLine((int)edge.getVertexes().get(0).center.x, (int)edge.getVertexes().get(0).center.y, (int)edge.getVertexes().get(1).center.x, (int)edge.getVertexes().get(1).center.y);
		}
		
		//Drawing the two pathing hexes
		/*FilledHex h1 = hexes.getHexes().get(new Tuple(1,1));
		FilledHex h2 = hexes.getHexes().get(new Tuple(1,0));
		g.setColor(Color.RED);
		g.drawLine((int)h1.center.x, (int)h1.center.y, (int)h2.center.x,(int)h2.center.y);
		*/
		
	}
}
