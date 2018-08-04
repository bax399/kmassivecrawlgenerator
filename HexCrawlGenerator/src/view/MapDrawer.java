package view;
import java.util.*;

import model.redblob.Tuple;
import model.redblob.Point;
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
<<<<<<< HEAD
	
=======
	Set<Set<Connection>> ccs;
>>>>>>> refs/heads/hexstorage
	static int size = 10;
	
	public MapDrawer(ConnectedHexMap h)
	{
		hexes=h;
	}
<<<<<<< HEAD
=======

	public MapDrawer(ConnectedHexMap h, Set<Set<Connection>> cs)
	{
		hexes=h;
		ccs=cs;
	}	
>>>>>>> refs/heads/hexstorage
	
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

			//**Outline**//
			//g.setColor(Color.BLACK);
<<<<<<< HEAD
			g.drawString(hh.getName(), (int)hh.center.x-15, (int)hh.center.y+5);
=======
			//g.drawPolygon(hh.shape);
			
			//**Name**//
			//g.setColor(Color.BLACK);
			//g.drawString(hh.getBiome().getBiomeName().substring(0,2), (int)hh.center.x-5, (int)hh.center.y+5);
			
			//**Coords**//
>>>>>>> refs/heads/hexstorage
			//g.drawString(""+hh.q,(int)hh.center.x-3, (int)hh.center.y-3);
			//g.drawString(""+hh.r,(int)hh.center.x-3, (int)hh.center.y+5);
			
		}
<<<<<<< HEAD
		
		/*Draws connections between hexes
		Set<Connection> cc = hexes.getConnections();
		Iterator<Connection> ic = cc.iterator();
		g.setColor(Color.WHITE);
		while(ic.hasNext())
=======
		Iterator<Set<Connection>> isc = ccs.iterator();
		g2d.setColor(Color.CYAN);
		g2d.setStroke(new BasicStroke(2.0f));
		while(isc.hasNext())
>>>>>>> refs/heads/hexstorage
		{
<<<<<<< HEAD
			Connection edge = ic.next();
			g.drawLine((int)edge.getVertexes().get(0).center.x, (int)edge.getVertexes().get(0).center.y, (int)edge.getVertexes().get(1).center.x, (int)edge.getVertexes().get(1).center.y);
		}*/

=======
			//**RANDOMIZE COLOURS**//
			//g2d.setColor(new Color(rand.nextInt(100)+1,rand.nextInt(100)+1,rand.nextInt(100)+156));
			
			//**Draw from Center**//
			/*for(Connection edge : isc.next())
			{
				Point st = edge.getVertexes().get(0).center;
				Point fn = edge.getVertexes().get(1).center;
				g2d.drawLine((int)st.x, (int)st.y, (int)fn.x, (int)fn.y);
			}*/
			
			//**Draw from Point**//
			for(Connection edge: isc.next())
			{
				g2d.setColor(Color.CYAN);
				Point st = edge.getVertexes().get(0).getRiverNode().getPosition();
				Point fn = edge.getVertexes().get(1).getRiverNode().getPosition();
				g2d.drawLine((int)st.x, (int)st.y, (int)fn.x, (int)fn.y);	
				g2d.setColor(Color.BLUE);
				
				//**Draw Points**//
				//g2d.drawOval((int)st.x, (int)st.y, 1, 1);
				//g2d.drawOval((int)fn.x, (int)fn.y, 1, 1);
			}
		}
>>>>>>> refs/heads/hexstorage
		
	}
}
