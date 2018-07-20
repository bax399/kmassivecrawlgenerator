package view;
import java.util.*;
import model.Tuple;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

//Panel draws to screen.
public class MapDrawer extends JPanel 
{

	LinkedList<Polygon> polys = new LinkedList<>();
	
	public MapDrawer()
	{
		for(int xx = 0; xx<10; xx++)
		{
			for(int yy=0; yy<10; yy++)
			{
				Polygon h = new Polygon();
				
				for (int i = 0; i < 6; i++){
					h.addPoint((int) (xx*50 + 20 * Math.cos(i * 2 * Math.PI / 6)),
							  (int) (yy*50 + 20 * Math.sin(i * 2 * Math.PI / 6)));
				}
				polys.add(h);
			}
		}

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
		
		Iterator<Polygon> it = polys.iterator();
		while (it.hasNext())
		{
			g.drawPolygon(it.next());
		}
	}
}
