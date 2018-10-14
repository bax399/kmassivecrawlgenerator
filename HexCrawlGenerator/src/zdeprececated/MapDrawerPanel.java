package view;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javafx.util.Pair;
import model.ConnectedHexMap;
import model.Connection;
import model.FilledHex;
import model.HexRegion;
import model.merowech.ConcaveHull.Point;
import model.redblob.Layout;
import model.redblob.Tuple;
import model.worldplaces.HexTown;
//Panel draws to screen.
public class MapDrawerPanel extends MainPanel
{
	ConnectedHexMap hexes;
	Layout lt;
	int size = 10;
	public int opacity=0;
	
	public MapDrawerPanel(ConnectedHexMap h, Layout lt, int size)
	{	
		super(size);
		hexes=h;
		this.lt = lt;
		this.size=size;
	}
	

//	@Override
//	public void drawAll(Graphics2D g2, AffineTransform at)
//	{
//		this.setBackground(Color.WHITE); 
//		Font font = new Font("Courier New",Font.PLAIN,11); 
//
//		g2.setFont(font);
//		
//		//** HEXES **//
//		for(Map.Entry<Tuple,FilledHex> entry : hexes.getHexes().entrySet())
//		{
//			FilledHex hh = hexes.getHex(entry.getValue());
//			
//			//Change offset to be relative to layout size 
//			g2.setColor(hh.getBiome().getColor());
//			
//			Shape scaleHex = at.createTransformedShape(hh.getShape());
//			
//			g2.fill(scaleHex);
//
//			//**Outline**//
//			if (hexes.getHexes().size() < 2000)
//			{
//				g2.setColor(new Color(0,0,0,50));
//				g2.draw(scaleHex);
//			}
//
//		}
//
//	}	
	
	//Needed method, draws to screen
	@Override
	public void drawAll(Graphics2D g2, AffineTransform at)
	{
		this.setBackground(Color.WHITE); 
		Font font = new Font("Courier New",Font.PLAIN,11); 

		g2.setFont(font);
		
		//** HEXES **//
		for(Map.Entry<Tuple,FilledHex> entry : hexes.getHexes().entrySet())
		{
			FilledHex hh = hexes.getHex(entry.getValue());
			
			//Change offset to be relative to layout size 
			g2.setColor(hh.getBiome().getColor());
			g2.fillPolygon(hh.getShape());

			//**Outline**//
			if (hexes.getHexes().size() < 2000)
			{
				g2.setColor(new Color(0,0,0,50));
				g2.drawPolygon(hh.getShape());
			}
			//**Name**//
			g2.setColor(Color.BLACK);
			if(size > 30)
			{
				g2.drawString(hh.getBiome().getPrintName(), hh.center.x.intValue()-size/2, hh.center.y.intValue()+5);
			}
			else if (size > 15)
			{
				g2.drawString(hh.getBiome().getBiomeName().substring(0, 3), hh.center.x.intValue()-size/2, hh.center.y.intValue()+5);
			}
			else if (size > 5)
			{
				g2.drawString(hh.getBiome().getBiomeName().substring(0, 1), hh.center.x.intValue()-size/2, hh.center.y.intValue()+5);				
			}
			else
			{
				//Dont draw anything.
			}
			
			//**Coords**//
//			g.setColor(Color.BLACK);
//			g.drawString(""+hh.q,hh.center.x.intValue()-3, hh.center.y.intValue()-3);
//			g.drawString(""+hh.r,hh.center.x.intValue(), hh.center.y.intValue()+5);
			
			//**Coords**//
//			g.setColor(Color.BLACK);
//			g.drawString(""+hh.getBiome().getHeight(),(int)hh.center.x-10, (int)hh.center.y+5);

		}
		
		//** REGIONS **//
		if (hexes.getRegions() !=null)
		{
			Random rand = new Random();
			int count =0;
			for(HexRegion hr : hexes.getRegions())
			{
				count++;
				//g.setColor(hr.getMajorityBiome().getColor());				
				g2.setColor(new Color(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255),opacity));				
				for(FilledHex eh : hr.getRegionHexes())
				{
					g2.fillPolygon(eh.getShape());
					
					
				}
				
//				for(FilledHex eh : hr.getRegionHexes())
//				{
//					double chance = rand.nextDouble();
//					if (chance <= 0.01f)
//					{
//						g.setColor(Color.BLACK);
//						g.drawString(""+count, eh.center.x.intValue(), eh.center.y.intValue());
//					}
//				}
				//g.fillPolygon(hr.getShape(lt));
			}
			
			
			g2.setColor(Color.CYAN);
			g2.setStroke(new BasicStroke(1.0f));				
			for(HexRegion hr : hexes.getRegions())
			{
				Iterator<Map.Entry<FilledHex,ArrayList<Pair<Point,Point>>>> it = hr.getEdgeLines().entrySet().iterator();
				while(it.hasNext())
				{
					Map.Entry<FilledHex,ArrayList<Pair<Point,Point>>> pointlists = (Map.Entry<FilledHex,ArrayList<Pair<Point,Point>>>) it.next();
					for(Pair<Point,Point> pp : pointlists.getValue())
					{
						g2.drawLine(pp.getKey().x.intValue(), pp.getKey().y.intValue(), pp.getValue().x.intValue(), pp.getValue().y.intValue());
					}
				}
			}
		}
		
		
		//** RIVERS **//
		Iterator<Set<Connection>> isc = hexes.getRiverConnections().iterator();
		g2.setColor(Color.BLUE);
		g2.setStroke(new BasicStroke(2.0f));
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
				g2.setColor(Color.BLUE);
				Point st = edge.getVertexes().get(0).getRiverNode().getPosition();
				Point fn = edge.getVertexes().get(1).getRiverNode().getPosition();
				g2.drawLine(st.x.intValue(), st.y.intValue(), fn.x.intValue(), fn.y.intValue());	
				g2.setColor(Color.BLUE);
				
				//**Draw Points**//
				//g2d.drawOval((int)st.x, (int)st.y, 1, 1);
				//g2d.drawOval((int)fn.x, (int)fn.y, 1, 1);
			}
		}
		
		//** ROADS **//
		Iterator<Set<Connection>> irc = hexes.getRoadConnections().iterator();
		g2.setColor(Color.CYAN);
		g2.setStroke(new BasicStroke(2.0f));
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
				g2.setColor(Color.BLACK);
				g2.setStroke(new BasicStroke(2.0f));				
				Point st = edge.getVertexes().get(0).getRoadNode().getPosition();
				Point fn = edge.getVertexes().get(1).getRoadNode().getPosition();
				g2.drawLine(st.x.intValue(), st.y.intValue(), fn.x.intValue(), fn.y.intValue());	
				
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
				g2.setColor(Color.RED);
				g2.fillOval(t.getPosition().x.intValue()-3, t.getPosition().y.intValue()-3, 6, 6);
				g2.setColor(Color.BLACK);
				g2.drawString("*", t.getPosition().x.intValue()-3, t.getPosition().y.intValue()+5);
				g2.drawString(t.getConnectivity()+"", t.getPosition().x.intValue()+5, t.getPosition().y.intValue());
			}			
		}
		
		//For Landmarks, checking this works!
		/* Problem is,there will always be equidistant hexes between two landmark-flagged hexes.
		int map_radius = 300/size;
		int dist=4;
		for (int q = -map_radius; q <= map_radius; q+=dist) {
		    int r1 = Math.max(-map_radius, -q - map_radius);
		    int r2 = Math.min(map_radius, -q + map_radius);
		    for (int r = r1; r <= r2; r+=dist) {
		    	g.setColor(Color.RED);
				g.fillPolygon(hexes.getHex(q,r).getShape());
		    }
		}
		*/
	}
}
