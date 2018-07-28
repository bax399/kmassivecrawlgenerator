package model;
import java.util.*;

import model.graphresource.Graph;
import model.redblob.Hex;
import model.redblob.Tuple;
public class ConnectedHexMap extends HexMap<FilledHex> {

	Graph<FilledHex,Connection> neighbours; // Stores each hex's neighbouring cells in hexagonal coordinates
	Graph<FilledHex,Road> roads; // Stores road connections
	Graph<FilledHex,River> rivers; // Stores river connections
	
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
			FilledHex each = hexes.get(it.next());
			neighbours.addVertex(each);
			for(int ii = 0; ii<6;ii++)
			{
				Hex n = each.neighbor(ii);
				if (hexes.containsValue(n))
				{
					neighbours.addEdge(new Connection(each, super.getHex(n)));
				}
				
				
			}
			
		}
	}			
	
	public Set<Connection> getConnections()
	{
		return Collections.unmodifiableSet(neighbours.getEdges());
	}
}
