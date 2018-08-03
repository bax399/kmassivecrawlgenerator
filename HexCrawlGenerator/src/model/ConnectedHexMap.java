package model;
import java.util.*;
import model.redblob.*;
import model.graphresource.Graph;
public class ConnectedHexMap extends HexMap<FilledHex> {

	Graph<FilledHex,Connection> neighbours; // Stores each hex's neighbouring cells in hexagonal coordinates
	Set<RiverNetwork> rivernetworks;
	public final int height;
	public final int width;
	
	public ConnectedHexMap(int w, int h)
	{
		super();
		neighbours = new Graph<>();
		rivernetworks = new HashSet<>();
		width = w;
		height = h;		
	}
	
	public void setNetworks(Set<RiverNetwork> rivers)
	{
		rivernetworks = rivers;
	}
	
	public void initializeNeighbours()
	{	
		Iterator<FilledHex> it = hexes.values().iterator();
		while(it.hasNext())
		{
			FilledHex each = it.next();
			neighbours.addVertex(each);
			for(int ii = 0; ii<6;ii++)
			{
				FilledHex n = getHex(each.neighbor(ii));
				
				if (n!=null && super.containsHex(n))
				{
					neighbours.addEdge(new Connection(each, n,(int)(each.getBiome().getTravelCost()+n.getBiome().getTravelCost())/2));
				}
				
				
			}
		}
	}			
	
	public Set<Set<Connection>> getRiverConnections()
	{
		Set<Set<Connection>> riverconnections = new HashSet<>();
		for(RiverNetwork rn : rivernetworks)
		{
			riverconnections.add(rn.getConnections());
		}
		
		return riverconnections;
	}
	
	public Point getRandomPoint(FilledHex origin)
	{
		return origin.center;
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
