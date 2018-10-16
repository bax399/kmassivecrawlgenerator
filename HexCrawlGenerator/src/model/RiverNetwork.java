package model;
import java.util.HashSet;
import java.util.Set;

import model.graphresource.Edge;
import model.stats.StatsModifierBiome;
import model.worldplaces.NetworkNode;

public class RiverNetwork extends Network
{
	
	public RiverNetwork()
	{
		super();
	}
	
	public void addNode(NetworkNode node, FilledHex origin)
	{
		super.addNode(node, origin);
		origin.setRiverNode(node);
	}
	
	@Override
	public Network createNetwork(ConnectedHexMap chm, Set<Edge<FilledHex>> path,Set<Network> networks)
	{
		Network delNetwork=null;
		//add all hexes to set (get from connections)
		for(Edge<FilledHex> cc : path) 
		{
			
			//if added to the set, create a river node at a random point
			for(int ii=0; ii<2; ii++)
			{
				FilledHex fh = cc.getVertexes().get(ii);		
				if(!super.containsHex(fh)) //if isn't already in Network
				{
					if(fh.getRiverNode()==null)
					{
						NetworkNode riverNode = new NetworkNode(this,chm.getRandomPoint(fh));
						
						addNode(riverNode,fh);
						

						
						//Adds a river modifier to the hex.
						if (!fh.getHabitat().getAllBiomes().contains(StatsModifierBiome.river))
						{
							StatsModifierBiome b = StatsModifierBiome.river;						
							fh.getHabitat().addModifierBiome(b);
						}
					}
					else //It has a rivernode currently JOIN THE TWO NETWORKS TOGETHER
					{
						delNetwork=fh.getRiverNode().getNetwork();
						joinNetworks(delNetwork,networks);
					}
					
				
				}
				if (ii==1)
				{
					NetworkConnection riverLink = new NetworkConnection(cc.getVertexes().get(0).getRiverNode(),cc.getVertexes().get(1).getRiverNode(),1);
					addConnection(riverLink);
				}					
			}
			

			
		}
		 return delNetwork;
	}

}
