package view;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.JPanel;

import model.ConnectedHexMap;
import model.Connection;
import model.FilledHex;
import model.HexRegion;
import model.merowech.ConcaveHull.Point;
import model.redblob.Layout;
import model.redblob.Tuple;
import model.worldobjects.HexTown;
//Panel draws to screen.
public class MapDrawer extends JPanel 
{

	ConnectedHexMap hexes;
	Layout lt;
	int size = 10;
	
	public MapDrawer(ConnectedHexMap h, Layout lt, int size)
	{
		hexes=h;
		this.lt = lt;
		this.size=size;
	}

	//Needed method, draws to screen
	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g);
		this.setBackground(Color.WHITE); 
		Font font = new Font("Courier New",Font.PLAIN,11); 

		g.setFont(font);
		
		
		
		//** REGIONS **//
		if (hexes.getRegions() !=null)
		{
			Random rand = new Random();
			for(HexRegion hr : hexes.getRegions())
			{
				//g.setColor(hr.getMajorityBiome().getColor());				
				g.setColor(new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255)));				
				for(FilledHex eh : hr.getRegionHexes())
				{
					//g.setColor(new Color(0,0,0,50));
					//**RANDOMIZE COLOURS**//
					
					g.fillPolygon(eh.getShape());		
				}
				//g.fillPolygon(hr.getShape(lt));
			}
		}
		
		//** HEXES **//
		for(Map.Entry<Tuple,FilledHex> entry : hexes.getHexes().entrySet())
		{
			FilledHex hh = hexes.getHex(entry.getValue());
			
			//Change offset to be relative to layout size 
			//g.setColor(hh.getBiome().getColor());
			//g.fillPolygon(hh.getShape());

			//**Outline**//
			if (hexes.getHexes().size() < 2000)
			{
				g.setColor(new Color(0,0,0,50));
				g.drawPolygon(hh.getShape());
			}
			//**Name**//
			g.setColor(Color.BLACK);
			if(size > 30)
			{
				g.drawString(hh.getBiome().getPrintName(), hh.center.x.intValue()-size/2, hh.center.y.intValue()+5);
			}
			else
			{
				g.drawString(hh.getBiome().getConcreteBiomeName().substring(0, 3), hh.center.x.intValue()-size/2, hh.center.y.intValue()+5);
			}
			
			//**Coords**//
			//g.setColor(Color.BLACK);
			//g.drawString(""+hh.q,hh.center.x.intValue()-3, hh.center.y.intValue()-3);
			//g.drawString(""+hh.r,hh.center.x.intValue(), hh.center.y.intValue()+5);
			
			//**Coords**//
			//g.setColor(Color.BLACK);
			//g.drawString(""+hh.getBiome().getHeight(),(int)hh.center.x-10, (int)hh.center.y+5);

		}

		
		//** RIVERS **//
		Iterator<Set<Connection>> isc = hexes.getRiverConnections().iterator();
		g2d.setColor(Color.BLUE);
		g2d.setStroke(new BasicStroke(2.0f));
		while(isc.hasNext())
		{
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
				g2d.setColor(Color.BLUE);
				Point st = edge.getVertexes().get(0).getRiverNode().getPosition();
				Point fn = edge.getVertexes().get(1).getRiverNode().getPosition();
				g2d.drawLine(st.x.intValue(), st.y.intValue(), fn.x.intValue(), fn.y.intValue());	
				g2d.setColor(Color.BLUE);
				
				//**Draw Points**//
				//g2d.drawOval((int)st.x, (int)st.y, 1, 1);
				//g2d.drawOval((int)fn.x, (int)fn.y, 1, 1);
			}
		}
		
		//** ROADS **//
		Iterator<Set<Connection>> irc = hexes.getRoadConnections().iterator();
		g2d.setColor(Color.CYAN);
		g2d.setStroke(new BasicStroke(2.0f));
		while(irc.hasNext())
		{
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
			for(Connection edge: irc.next())
			{
				g2d.setColor(Color.BLACK);
				g2d.setStroke(new BasicStroke(2.0f));				
				Point st = edge.getVertexes().get(0).getRoadNode().getPosition();
				Point fn = edge.getVertexes().get(1).getRoadNode().getPosition();
				g2d.drawLine(st.x.intValue(), st.y.intValue(), fn.x.intValue(), fn.y.intValue());	
				
				//**Draw Points**//
				//g2d.drawOval((int)st.x, (int)st.y, 1, 1);
				//g2d.drawOval((int)fn.x, (int)fn.y, 1, 1);
			}
		}
				
		//**Towns**//		
		for(Map.Entry<Tuple,FilledHex> entry : hexes.getHexes().entrySet())
		{
			FilledHex hh = hexes.getHex(entry.getValue());

			for(HexTown t:hh.getTowns())
			{
				g.setColor(Color.RED);
				g2d.fillOval(t.getPosition().x.intValue()-3, t.getPosition().y.intValue()-3, 6, 6);
				g.setColor(Color.BLACK);
				g.drawString("*", t.getPosition().x.intValue()-3, t.getPosition().y.intValue()+5);
				g.drawString(t.getConnectivity()+"", t.getPosition().x.intValue()+5, t.getPosition().y.intValue());
			}			
		}
		

		
	}
}
