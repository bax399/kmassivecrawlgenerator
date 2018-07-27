package model;
import java.util.*;

import model.graphresource.Graph;
import model.redblob.Hex;
import model.redblob.Tuple;
public class ConnectedHexMap extends HexMap<FilledHex> {

	Graph<Hex,Connection> neighbours; // Stores each hex's neighbouring cells in hexagonal coordinates
	Graph<Hex,Road> roads; // Stores road connections
	Graph<Hex,River> rivers; // Stores river connections
	
	public ConnectedHexMap()
	{
		super();
		neighbours = new Graph<>();
		roads = new Graph<>();
		rivers = new Graph<>();		
	}
	
	public void initializeNeighbours()
	{	
		Set<Tuple> s = hexes.keySet();
		Iterator<Tuple> it = s.iterator();
		while(it.hasNext())
		{
			Hex each = hexes.get(it.next());
			neighbours.addVertex(each);
			for(int ii = 0; ii<6;ii++)
			{
				Hex n = each.neighbor(ii);
				if (hexes.containsValue(n))
				{
					neighbours.addEdge(new Connection(each, n));
				}
				
				
			}
			
		}
	}			
}
