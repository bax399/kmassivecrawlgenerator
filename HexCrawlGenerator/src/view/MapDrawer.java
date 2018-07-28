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
	
	static int size = 10;
	
	public MapDrawer(ConnectedHexMap h)
	{
		hexes=h;
	}
	
	//Needed method, draws to screen
	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g);
		this.setBackground(Color.WHITE); 
		Font font = new Font("Serif",Font.PLAIN,12); 
		Set<Tuple> ss = hexes.getHexes().keySet();
		Iterator<Tuple> it = ss.iterator();
		g.setFont(font);
		while (it.hasNext())
		{
			FilledHex hh = hexes.getHex(it.next());
			
			//Change offset to be relative to layout size 
			g.setColor(hh.getColor());
			g.fillPolygon(hh.shape);

			g.setColor(Color.BLACK);
			g.drawPolygon(hh.shape);
			//g.setColor(Color.BLACK);
			g.drawString(hh.getName(), (int)hh.center.x-15, (int)hh.center.y+5);
			//g.drawString(""+hh.q,(int)hh.center.x-3, (int)hh.center.y-3);
			//g.drawString(""+hh.r,(int)hh.center.x-3, (int)hh.center.y+5);
			
		}
		
		/*Draws connections between hexes
		Set<Connection> cc = hexes.getConnections();
		Iterator<Connection> ic = cc.iterator();
		g.setColor(Color.WHITE);
		while(ic.hasNext())
		{
			Connection edge = ic.next();
			g.drawLine((int)edge.getVertexes().get(0).center.x, (int)edge.getVertexes().get(0).center.y, (int)edge.getVertexes().get(1).center.x, (int)edge.getVertexes().get(1).center.y);
		}*/

		
	}
}
