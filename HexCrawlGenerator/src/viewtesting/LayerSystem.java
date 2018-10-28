package viewtesting;
import java.util.ArrayList;

import javafx.scene.Node;
/**
 * @author Keeley
 *	This class handles which LayerGUI panes are draw above one another, and can remove them from the canvas as ordered to.
 *  Stores the pannableCanvas root class that all layers are drawn to.
 */
public class LayerSystem
{
	private static int ARRAYSIZE=10;
	Node[] layers;
	PannableCanvas paneOwner;
	
	public LayerSystem()
	{
		layers = new Node[ARRAYSIZE];
		paneOwner = new PannableCanvas();	
	}
	
	public LayerSystem(PannableCanvas pc)
	{
		layers = new Node[ARRAYSIZE];
		paneOwner = pc;	
	}
	
	
	/**
	 * Reorders the layers correctly, shouldn't have to be used.
	 */
	public void reorderLayers()
	{
		for(int ii = 1; ii < ARRAYSIZE; ii++)
		{
			if (layers[ii] != null)
				layers[ii].toFront();
		}
	}
	
	public Node addLayer(int index, Node newLayer)
	{
		Node previous = layers[index];
		layers[index] = newLayer;
		paneOwner.getChildren().add(newLayer);
		
		return previous;
	}
	
	public void turnOnAll()
	{
		for(int ii = 1; ii<ARRAYSIZE; ii++)
		{
			if (!paneOwner.getChildren().contains(layers[ii])) 
			{
				paneOwner.getChildren().add(layers[ii]);
			}
		}
		reorderLayers();
	}
	
	public void turnOffLayer(int index)
	{
		paneOwner.getChildren().remove(layers[index]);
	}
	
	public void turnOnLayer(int index)
	{
		paneOwner.getChildren().add(layers[index]);
		reorderLayers();
	}
}
