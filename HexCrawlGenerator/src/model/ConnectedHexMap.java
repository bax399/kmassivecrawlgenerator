package model;
import java.util.*;
import model.redblob.*;
import model.graphresource.Graph;
public class ConnectedHexMap extends HexMap<FilledHex> {

	Graph<FilledHex,Connection> neighbours; // Stores each hex's neighbouring cells in hexagonal coordinates
<<<<<<< HEAD
	Graph<FilledHex,Road> roads; // Stores road connections
	Graph<FilledHex,River> rivers; // Stores river connections
	
	public ConnectedHexMap()
=======
	Set<RiverNetwork> rivernetworks;
	public final int height;
	public final int width;
	
	Random rand;
	Layout ly;
	
	public ConnectedHexMap(int w, int h, Layout l, Random r)
>>>>>>> refs/heads/hexstorage
	{
		super();
		neighbours = new Graph<>();
<<<<<<< HEAD
		roads = new Graph<>();
		rivers = new Graph<>();		
		
=======
		rivernetworks = new HashSet<>();
		width = w;
		height = h;		
		ly = l;
		rand = r;
	}
	
	public void setNetworks(Set<RiverNetwork> rivers)
	{
		rivernetworks = rivers;
>>>>>>> refs/heads/hexstorage
	}
	
	public void initializeNeighbours()
	{	
		Iterator<FilledHex> it = hexes.values().iterator();
		while(it.hasNext())
		{
			FilledHex each = it.next();
			if(each != null)
			{
				neighbours.addVertex(each);
				for(int ii = 0; ii<6;ii++)
				{
					FilledHex n = getHex(each.neighbor(ii));
					
					if (n!=null && containsHex(n))
					{
						each.getBiome();
						each.getBiome().getTravelCost();
						n.getBiome();
						n.getBiome().getTravelCost();
						neighbours.addEdge(new Connection(each, n,(int)(each.getBiome().getTravelCost()+n.getBiome().getTravelCost())/2));
					}
					
					
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
		return randomPoint(origin,rand,ly);
	}
	
	public Point randomPoint(FilledHex h,Random rand, Layout ly)
	{
		Point point;
		double a1 = Math.max(rand.nextDouble()-0.3d,0);
		double a2 = Math.max(rand.nextDouble()-0.3d,0);
		Point center = h.center;
		int firstcorner=rand.nextInt(6);
		int secondcorner;
		if (firstcorner==5) secondcorner=0;
		else secondcorner=firstcorner++;
		Point v1 = ly.hexCornerOffset(firstcorner).scalarMultiple(a1);
		Point v2 = ly.hexCornerOffset(secondcorner).scalarMultiple(a2);
		
		point = new Point((v1.x+v2.x)/2,(v1.y+v2.y)/2);
		point = new Point(point.x+center.x,point.y+center.y);
		
		return point;
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
