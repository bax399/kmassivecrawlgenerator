package model;
import java.util.*;

import model.graphresource.Graph;
import model.redblob.Hex;
import model.redblob.Tuple;
public class ConnectedHexMap extends HexMap<FilledHex> {

	Graph<FilledHex,Connection> neighbours; // Stores each hex's neighbouring cells in hexagonal coordinates

	public ConnectedHexMap()
	{
		super();
		neighbours = new Graph<>();
		
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
				FilledHex n = super.getHex(each.neighbor(ii));
				if (hexes.containsValue(n))
				{
					neighbours.addEdge(new Connection(each, n,(int)(each.getBiome().getTravelCost()+n.getBiome().getTravelCost())/2));
				}
				
				
			}
			
		}
	}			
	
	public Set<Connection> getConnections()
	{
		return Collections.unmodifiableSet(neighbours.getEdges());
	}
	
	public Set<FilledHex> neighbours(FilledHex curr)
	{
		return neighbours.getAdjVertices(curr);
	}
	
	public int adjTravelCost(FilledHex curr, FilledHex next)
	{
		int cost = Integer.MAX_VALUE;
		if (neighbours(curr).contains(next))
		{
			cost = (int) (curr.getBiome().getTravelCost()+next.getBiome().getTravelCost())/2;
		}
		return cost;
	}
}
