package view;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HexagonDraw extends JPanel
{
	int x,y,r;
	
	//Colour, thickness etc. 
	
	public HexagonDraw(int xp, int yp, int rr)
	{
		r = rr;
		x = xp;
		y = yp;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
        g.setClip(x,y,r,r);
        g.setColor(Color.RED);
        g.fillOval(x,y,r,r);
	}
}
