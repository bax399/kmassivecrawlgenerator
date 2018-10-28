package view;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.List;

/**
 * @author Keeley
 * Class draws the hex and biomes given to screen
 */
public class HexLayer extends LayerPanel<UIShape> 
{
	public HexLayer(Layer layer)
	{
		super(layer);
	}
	
	public HexLayer(Layer layer, List<UIShape> list)
	{
		super(layer, list);
	}	
	
	public void setLayerBounds()
	{
		int minX=Integer.MAX_VALUE,minY=Integer.MAX_VALUE,maxX=Integer.MIN_VALUE,maxY=Integer.MIN_VALUE;
		for(Shape shape : drawList)
		{
			Rectangle bounds = shape.getBounds();
			if (bounds.getMinY() < minY)
				minY=(int)bounds.getMinY();
			
			if (bounds.getMaxY() > maxY)
				maxY=(int)bounds.getMaxY();
			
			
			if (bounds.getMinX() < minX)
				minX=(int)bounds.getMinX();
			
			if (bounds.getMaxX() > maxX)
				maxX=(int)bounds.getMaxX();	
			
		}
		System.out.println(minX +" "+ maxX +" "+ minY +" "+ maxY);
		
		setSize(maxX-minX,maxY-minY);
		setLocation(minX,minY);
		offsetX = -minX;
		offsetY = -minY;
	}
	
	@Override
	public void initialDrawSettings(Graphics2D g2d) 
	{
		
	}

	@Override
	public void eachDrawSettings(Graphics2D g2d, UIShape v) 
	{
		g2d.setColor(v.getColor());
		if (v.getImg() !=null)
		{
			g2d.setClip(v);
			g2d.drawImage(v.getImg(),0,0,null);
		}
	}

}
