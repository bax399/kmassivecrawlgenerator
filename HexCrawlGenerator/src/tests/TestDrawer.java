package tests;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import model.FilledHex;
import model.redblob.Layout;
import model.redblob.Point;
public class TestDrawer extends JPanel
{
	FilledHex h;
	Layout lt;
	
	public TestDrawer(FilledHex h, Layout lt)
	{
		this.h= h;
		this.lt = lt;
	}
	@Override
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g);
		this.setBackground(Color.WHITE); 
		
		g.drawPolygon(h.getShape());
		for(int ii = 0; ii < 6; ii++)
		{
			Point p = lt.pointCorner(h,ii);
			g.drawString(ii+"", (int)Math.round(p.x), (int)Math.round(p.y));
		}
		
		for(int jj = 0; jj < 6; jj ++)
		{
			Point n = lt.hexToPixel(h.neighbor(jj));	
			g.drawString(jj+"", (int)Math.round(n.x), (int)Math.round(n.y));			
		}
		
	}
}
