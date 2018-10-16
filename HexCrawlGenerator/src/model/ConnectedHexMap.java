package model;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import functions.KFunctions;
import model.graphresource.Edge;
import model.graphresource.Graph;
import model.redblob.Layout;
public class ConnectedHexMap extends HexMap<FilledHex> {

	Graph<FilledHex,Edge<FilledHex>> neighbours; // Stores each hex's neighbouring cells in hexagonal coordinates
	Set<Network> rivernetworks, roadnetworks;
	Set<HexRegion> regions;
	public final int height;
	public final int width;
	
	Random rand;
	Layout ly;
	
	public ConnectedHexMap(int w, int h, Layout l, Random r)
	{
		super();
		neighbours = new Graph<>();
		width = w;
		height = h;		
		ly = l;
		rand = r; 
	}
	
	public void setRiverNetworks(Set<Network> rivers)
	{
		rivernetworks = rivers;
	}
	
	public void setRoadNetworks(Set<Network> roads)
	{
		roadnetworks = roads;
	}
	
	public void setRegions(Set<HexRegion> regions)
	{
		this.regions = regions;
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
						neighbours.addEdge(new Edge<FilledHex>(each, n,(int)(each.getHabitat().getTravelCost()+n.getHabitat().getTravelCost())/2));
					}
					
					
				}	
			}
		}
	}			
	
	public Set<HexRegion> getRegions()
	{
		return regions;
	}
	
	public Set<Set<NetworkConnection>> getRoadConnections()
	{
		if(roadnetworks !=null)
		{
		Set<Set<NetworkConnection>> roadconnections = new HashSet<>();
		for(Network rn : roadnetworks)
		{
			 roadconnections.add(rn.getNetworkConnections());
		}

		return  roadconnections;
		}
		return null;
	}	
	
	public Set<Set<NetworkConnection>> getRiverConnections()
	{

		Set<Set<NetworkConnection>> riverconnections = new HashSet<>();
		for(Network rn : rivernetworks)
		{
			riverconnections.add(rn.getNetworkConnections());
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

	public Set<Edge> getConnections()
	{
		return Collections.unmodifiableSet(neighbours.getEdges());
	}
	
	public Set<FilledHex> neighbours(FilledHex curr)
	{
		return neighbours.getAdjVertices(curr);
	}
	
	public int adjTravelCost(FilledHex curr, FilledHex next)
	{
		int cost = 1000000;
		if (neighbours(curr).contains(next))
		{
			cost = (int) (curr.getHabitat().getTravelCost()+next.getHabitat().getTravelCost())/2;
		}
		return cost;
	}
}
