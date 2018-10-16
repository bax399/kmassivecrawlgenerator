package model;

import java.util.HashSet;
import java.util.Set;

import model.graphresource.Edge;
import model.stats.StatsModifierBiome;
import model.worldplaces.HexTown;
import model.worldplaces.NetworkNode;

public class RoadNetwork extends Network{

	private Set<HexTown> towns;
	
	public RoadNetwork(Set<RoadNetwork> ar)
	{
		super();
		setNetworks(new HashSet<Network>());
		getNetworks().addAll(ar);
		towns=new HashSet<>();
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
	
	public void addTownNode(ConnectedHexMap chm, FilledHex fh) 
	{
		Network roadNetwork=null;
		if (fh.getRoadNode() != null)
		{
			roadNetwork = fh.getRoadNode().getNetwork();
		}
		else 
		{
			NetworkNode roadNode;
			roadNetwork = this;
			if (fh.getLargestTown() != null) 
			{
				roadNode = new NetworkNode(this, fh.getLargestTown().getPosition());
			} 
			else
			{
				roadNode = new NetworkNode(this, chm.getRandomPoint(fh));
			}

			addNode(roadNode,fh);

			if (!fh.getHabitat().getAllBiomes().contains(StatsModifierBiome.road)) {
				StatsModifierBiome b = StatsModifierBiome.road;
				fh.getHabitat().addModifierBiome(b);
			}


		}

	}

	@Override
	public void createNetwork(ConnectedHexMap chm, Set<Edge<FilledHex>> path) 
	{
		//add all hexes to set (get from connections)
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
					if(fh.getRoadNode()==null)
					{
						NetworkNode roadNode = new NetworkNode(this,chm.getRandomPoint(fh));
						addNode(roadNode,fh);
						
						//Adds a river modifier to the hex.
						if (!fh.getHabitat().getAllBiomes().contains(StatsModifierBiome.road))
						{
							StatsModifierBiome b = StatsModifierBiome.road;						
							fh.getHabitat().addModifierBiome(b);
						}
					}
					else //It has a rivernode currently JOIN THE TWO NETWORKS TOGETHER
					{
						joinNetworks(fh.getRoadNode().getNetwork());
					}
					
				}
				if (ii==1)
				{
					NetworkConnection roadLink = new NetworkConnection(cc.getVertexes().get(0).getRoadNode(),cc.getVertexes().get(1).getRoadNode(),1);
					addConnection(roadLink);
				}						
			}
		}
		 
	}

}
