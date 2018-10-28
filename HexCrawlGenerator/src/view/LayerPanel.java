package view;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * @author Keeley
 *  Template class used to draw each layer of the map. Template method overrides how it will draw its layer.
 *  V is a shape that it will draw iteratively. Adding or removing a shape from the list will cause the layer to repaint.
 *  Stores its Layer Integer for adding it to LayeredPane.
 */
public abstract class LayerPanel<V extends Shape> extends JPanel
{
	int offsetX=0,offsetY=0;
	Layer layer;
	List<V> drawList;
	
	public LayerPanel(Layer layer)
	{
		drawList = new ArrayList<>();
		this.layer = layer;
	}
	
	public LayerPanel(Layer layer, List<V> list)
	{
		drawList = list;
		this.layer = layer;
	}	
	
	public List<V> getDrawList()
	{
		return drawList;
	}
	
	public Layer getLayer()
	{
		return layer;
	}
	
 	public boolean addShape(V v)
 	{
 		boolean added = drawList.add(v);
 		repaint();
 		return added;
 	}
 	
 	public boolean removeShape(V v)
 	{
 		boolean found = drawList.remove(v);
 		repaint();
 		return found;
 	}
	
	public abstract void initialDrawSettings(Graphics2D g2d);
	public abstract void eachDrawSettings(Graphics2D g2d, V v);
		

    @Override
    public void paintComponent(Graphics g) 
    {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.translate(offsetX, offsetY);
		
		
		initialDrawSettings(g2d);
		for(V v : drawList)
		{
			eachDrawSettings(g2d, v);
			g2d.draw(v);

		}
		g2d.dispose();
    } 		
}
