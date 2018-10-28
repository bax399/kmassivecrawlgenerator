package view;
import java.awt.Color;
import java.awt.Image;
import java.awt.Polygon;

import model.FilledHex;

/**
 * @author Keeley
 * Immutable Hex object, used for concurrency UI drawing.
 */
public class UIShape extends Polygon
{
	private final Color color;
	private final Image img;
	
	public UIShape(FilledHex hex)
	{
		super(hex.getShape().xpoints,hex.getShape().ypoints,hex.getShape().npoints);
		color = hex.getHabitat().getColor();
		//TODO img = hex.getHabitat().getImage();
		img=null;
	}
	
	public UIShape(Polygon poly, Color color)
	{
		super(poly.xpoints,poly.ypoints,poly.npoints);
		this.color = color;
		img=null;
	
	}
	
	public UIShape(Polygon poly, Color color, Image img)
	{
		super(poly.xpoints,poly.ypoints,poly.npoints);		
		this.color = color;
		this.img = img;
	}

	public Color getColor() 
	{
		return color;
	}

	public Image getImg() 
	{
		return img;
	}
	
	

}
