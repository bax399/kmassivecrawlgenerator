package view;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import model.FilledHex;
import model.HexMap;
//Panel draws to screen.
public class MapDrawer extends JPanel 
{

	HexMap<FilledHex> hexes;
	
	static int size = 10;
	
	public MapDrawer(HexMap<FilledHex> h)
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
		Font font = new Font("Serif",Font.PLAIN,9); 
		Font smallfont = font.deriveFont(0.5f);
		Set<Integer> ss = hexes.getHexes().keySet();
		Iterator<Integer> it = ss.iterator();
		g.setFont(font);
		while (it.hasNext())
		{
			FilledHex hh = hexes.getHex(it.next());
			
			//Change offset to be relative to layout size 
			g.setColor(hh.getColor());
			g.fillPolygon(hh.shape);

			g.setColor(Color.WHITE);
			g.drawPolygon(hh.shape);
			g.setColor(Color.BLACK);
			//g.drawString(hh.getName(), (int)hh.center.x-15, (int)hh.center.y+5);
			g.drawString(""+hh.q,(int)hh.center.x-3, (int)hh.center.y-3);
			g.drawString(""+hh.r,(int)hh.center.x-3, (int)hh.center.y+5);
			
			
		}
		

		
	}
}
