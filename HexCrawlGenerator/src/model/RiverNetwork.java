package model;
import java.util.HashSet;
import java.util.Set;

import model.stats.StatsModifierBiome;
import model.worldplaces.RiverNode;
public class RiverNetwork {

	private Set<Connection> connects;
	private Set<FilledHex> hexes;
	private Set<RiverNode> nodes;
	private Set<RiverNetwork> allrivers;
	
	public RiverNetwork(Set<RiverNetwork> ar)
	{
		connects = new HashSet<>();
		hexes = new HashSet<>();
		nodes = new HashSet<>();
		allrivers = ar;
	}
	
	public Set<Connection> getConnections()
	{
		return connects;
	}
	
	public void addNode(RiverNode rn)
	{
		nodes.add(rn);
	}
	
	public void addConnection(Connection c)
	{
		connects.add(c);
	}
	
	public void addHex(FilledHex fh)
	{
		hexes.add(fh);
	}	
	
	public void createRiver(ConnectedHexMap chm, Set<Connection> path)
	{
		connects = path;
		Set<Connection> tempset = new HashSet<>();
		tempset.addAll(connects);
		//add all hexes to set (get from connections)
		for(Connection cc : connects) 
		{
			//create river nodes and add to set,
			FilledHex fh;
			
			//if added to the set, create a river node at a random point
			for(int ii=0; ii<2; ii++)
			{
				fh = cc.getVertexes().get(ii);				
				if(hexes.add(fh))
				{
					if(fh.getRiverNode()==null)
					{
						RiverNode rn = new RiverNode(this, chm.getRandomPoint(fh));						
						fh.add(rn);
						//Adds a river modifier to the hex.
						
						if (!fh.getHabitat().getAllBiomes().contains(StatsModifierBiome.river))
						{
							StatsModifierBiome b = StatsModifierBiome.river;						
							fh.getHabitat().addModifierBiome(b);
						}
						nodes.add(rn);
					}
					else //It has a rivernode currently JOIN THE TWO NETWORKS TOGETHER
					{
						if(!nodes.contains(fh.getRiverNode())) tempset = joinNetworks(fh.getRiverNode().getNetwork(), tempset);
					}
					
				}				
			}
		}
		
		connects = tempset;
		 
	}
	
	public Set<Connection> joinNetworks(RiverNetwork rne, Set<Connection> tempset)
	{
		tempset.addAll(rne.connects);
		hexes.addAll(rne.hexes);
		
		//Change the pointers to the river network from rne to THIS
		for(RiverNode rn : rne.nodes)
		{
			rn.setNetwork(this);
		}
		nodes.addAll(rne.nodes);
		
		//Deletes the old network. 
		allrivers.remove(rne);
		return tempset;
	}
}
