package viewtesting;
import java.util.ArrayList;

import javafx.scene.Node;
/**
 * @author Keeley
 *	Layered pane object that stores multiple canvas within.
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
	
//	public void redrawAll()
//	{
//		for(int ii = 0; ii <ARRAYSIZE; ii++)
//		{
//			layers[ii].setCache(true);			
//		}
//	}
//	
//	public void redrawLayer(int index)
//	{
//		layers[index].setCache(true);
//	}
	
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
