package model;

import model.worldobjects.*;
import java.util.*;
public class RoadNetwork
{
	private Set<Connection> connects;
	private Set<FilledHex> hexes;
	private Set<RoadNode> nodes;
	private Set<RoadNetwork> allroads;
	private Set<HexTown> towns;
	
	public RoadNetwork(Set<RoadNetwork> ar)
	{
		connects = new HashSet<>();
		hexes = new HashSet<>();
		nodes = new HashSet<>();
		allroads = ar;
	}
	
	public Set<Connection> getConnections()
	{
		return connects;
	}
	
	public void addNode(RoadNode rn)
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
	
	public void initialRoad(ConnectedHexMap chm, Set<Connection> path)
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
					if(fh.getRoadNode()==null) //No roads currently on it.
					{
						RoadNode rn = new RoadNode(this, fh.center);						
						
						fh.add(rn); //add road node to the hex
						
						nodes.add(rn);
					}
					
					if (fh.getTowns().size() > 0)
					{
						towns.addAll(fh.getTowns());
					}
				}				
			}
		}
		
		connects = tempset;
		 
	}	
	
	public void createRoad(ConnectedHexMap chm, FilledHex destination, FilledHex origin)
	{
		//create BFS path from origin to destination
		Pathfinder rf = new Roadfinder();

		//add all connections to set
		int resource = destination.getLargestTown().stats.getConnectivity()+origin.getLargestTown().stats.getConnectivity();
		connects = rf.AStar(chm, destination, origin,resource);
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
					if(fh.getRoadNode()==null) //No roads currently on it.
					{
						RoadNode rn = new RoadNode(this, fh.center);						
						
						fh.add(rn); //add road node to the hex
						
						nodes.add(rn);
					}
					else //It has a rivernode currently JOIN THE TWO NETWORKS TOGETHER
					{
						if(!nodes.contains(fh.getRoadNode())) tempset = joinNetworks(fh.getRoadNode().getNetwork(), tempset);
					}
					
					
					if (fh.getTowns().size() > 0)
					{
						towns.addAll(fh.getTowns());
					}
				}				
			}
		}
		
		connects = tempset;
		 
	}
	
	public Set<Connection> joinNetworks(RoadNetwork rne, Set<Connection> tempset)
	{
		tempset.addAll(rne.connects);
		hexes.addAll(rne.hexes);
		
		//Change the pointers to the river network from rne to THIS
		for(RoadNode rn : rne.nodes)
		{
			rn.setNetwork(this);
		}
		nodes.addAll(rne.nodes);
		
		//Deletes the old network. 
		allroads.remove(rne);
		return tempset;
	}
	
	/*Future stuff 
 
	//check if a crossing of road->river exists at this hex
	public boolean getCrossing(FilledHex origin)
	{
		
		return false;
	}
	
	public int getIntersectionNumber(FilledHex origin)
	{
		//TODO change this to accessing the hex's RoadNodes, and counting its size.
		//return number of connections with this hex
		int count=0;
		Iterator<Connection> it = roads.iterator();
		while(it.hasNext())
		{
			if (it.next().containsHex(origin))
			{
				count++;
			}
		}
		return count;
	}
	
	public Set<Town> getStructuresInHex(FilledHex origin)
	{
		Iterator<Town> it = towns.iterator();
		Set<Town> townsFound = new HashSet<>();
		while(it.hasNext())
		{
			Town ss = it.next();
			if (origin.contains(ss))
			{
				townsFound.add(ss);
			}
		}
		
		return townsFound;
	}
	
	public Set<Town> getStructuresAlongRoad(FilledHex origin, Connection direction)
	{
		//Follow connection, whenever it splits, send recursive call to follow
		//Any 
		
		//if it only has one, return all towns in the set except the ones in the origin hex.
		return null;
	}
	*/
	
}
