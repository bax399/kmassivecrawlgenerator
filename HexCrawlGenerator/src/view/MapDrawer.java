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
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		Font font = new Font("Serif",Font.PLAIN,1);
		Set<Integer> ss = hexes.keySet();
		Iterator<Integer> it = ss.iterator();
		while (it.hasNext())
		{
			FilledHex hh = hexes.get(it.next());
			
			//Change offset to be relative to layout size
			g.setColor(hh.getColor());
			g.fillPolygon(hh.shape);
			g.setColor(Color.BLACK);
			//g.drawString(hh.getName(), (int)hh.center.x-15, (int)hh.center.y+5);
			//g.drawString(""+hh.q+":"+hh.r,(int)hh.center.x-15, (int)hh.center.y+5);
			g.drawPolygon(hh.shape);
			
		}
		
		
	}
}
