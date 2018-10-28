package view;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.List;

/**
 * @author Keeley
 * Class draws the hex and biomes given to screen
 */
public class LineLayer extends LayerPanel<Shape> 
{
	public LineLayer(Layer layer)
	{
		super(layer);
	}
	
	public LineLayer(Layer layer, List<Shape> list)
	{
		super(layer, list);
	}	
	
	@Override
	public void initialDrawSettings(Graphics2D g2d) 
	{
		
	}

	@Override
	public void eachDrawSettings(Graphics2D g2d, Shape v) 
	{
		g2d.setColor(Color.BLACK);
	}

}
