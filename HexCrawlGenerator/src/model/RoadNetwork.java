package model;

import java.util.HashSet;
import java.util.Set;

import model.graphresource.Edge;
import model.stats.StatsModifierBiome;
import model.worldplaces.HexTown;
import model.worldplaces.NetworkNode;

public class RoadNetwork extends Network{
	private Set<HexTown> towns;
	
	public RoadNetwork()
	{
		super();
		towns=new HashSet<>();
	}
	
	public NetworkNode createNode(ConnectedHexMap chm, FilledHex origin)
	{
		NetworkNode roadNode;
		if (origin.getTowns().size() > 0) 
		{
			roadNode = new NetworkNode(this, origin.getLargestTown().getPosition());
		} 
		else
		{
			roadNode = new NetworkNode(this, chm.getRandomPoint(origin));
		}
		
		return roadNode;
	}
	
	public void addNode(NetworkNode node, FilledHex origin)
	{
		super.addNode(node, origin);
		origin.setRoadNode(node);
		
		if (origin.getTowns().size() > 0) 
		{
			// Add towns from fh to the network.
			towns.addAll(origin.getTowns());
		}
	}

	@Override
	public Network createNetwork(ConnectedHexMap chm, Set<Edge<FilledHex>> path,Set<Network> networks) 
	{
		//add all hexes to set (get from connections)
		Network delNetwork=null;
		
		for(Edge<FilledHex> cc : path) 
		{
			//create river nodes and add to set,
			FilledHex fh;
			
			//if added to the set, create a river node at a random point
			for(int ii=0; ii<2; ii++)
			{
				fh = cc.getVertexes().get(ii);		
				if(!super.containsHex(fh)) //if isn't already in Network
				{
					NetworkNode roadNode=fh.getRoadNode();

					if (roadNode != null) //It has a roadnode currently JOIN THE TWO NETWORKS TOGETHER
					{
						joinNetworks(fh.getRoadNode().getNetwork(),networks);
					}
					else
					{
						roadNode=createNode(chm,fh);
					}
					addNode(roadNode,fh);
				}
	
			}
			
			NetworkConnection roadLink = new NetworkConnection(cc.getVertexes().get(0).getRoadNode(),cc.getVertexes().get(1).getRoadNode(),1);
			NetworkConnection roadLink2 = new NetworkConnection(cc.getVertexes().get(1).getRoadNode(),cc.getVertexes().get(0).getRoadNode(),1);
			addConnection(roadLink);
			addConnection(roadLink2);							
		}
		return delNetwork; 
	}
	

}
