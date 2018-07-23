package view;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import model.FilledHex;
//Panel draws to screen.
public class MapDrawer extends JPanel 
{

	Map<Integer,FilledHex> hexes;
	
	static int size = 10;
	
	public MapDrawer(Map<Integer,FilledHex> h)
	{
		hexes=h;
	}
	
	//Needed method, draws to screen
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		
		g.setColor(Color.BLACK);
		g.fillRect(25,25,100,30);
		
		g.setColor(new Color(190,81,215));
		g.fillRect(25, 65, 100, 30);
		
		g.setColor(Color.RED);
		g.drawString("Test", 25,120);
		
		g.drawOval(50, 50, 3, 3);
		
		Set<Integer> ss = hexes.keySet();
		Iterator<Integer> it = ss.iterator();
		while (it.hasNext())
		{
			FilledHex hh = hexes.get(it.next());
			g.setColor(hh.getColor());
			//Change offset to be relative to layout size
			g.drawString(hh.getName(), (int)hh.center.x-15, (int)hh.center.y+5);
			g.drawPolygon(hh.shape);
		}
		
		
	}
}
