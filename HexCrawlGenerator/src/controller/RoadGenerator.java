package controller;
import java.util.*;
import model.*;
import model.worldobjects.RoadNode;
public class RoadGenerator extends Generator
{
	ConnectedHexMap hexmap;	

	public static Comparator<FilledHex> sizeComparator = new Comparator<FilledHex>()
	{
		@Override
		public int compare(FilledHex h1, FilledHex h2)
		{
			return (int)(h2.getLargestTown().getConnectivity()-h1.getLargestTown().getConnectivity());
		}
	};	
	
	public RoadGenerator(ConnectedHexMap chm, Random rd)
	{
		super(rd);
		hexmap = chm;
	}
	
	public Set<RoadNetwork> generateRoads()
	{	

		Set<RoadNetwork> networks = new HashSet<>();
		RoadNetwork	rn;
		//Possible starting points.
		PriorityQueue<FilledHex> hexHasTowns = new PriorityQueue<>(sizeComparator);
		
		Iterator<FilledHex> it = hexmap.getHexes().values().iterator();
		while(it.hasNext())
		{
			FilledHex fh =it.next();
			
			if (fh.getLargestTown() !=null)
			{
				hexHasTowns.add(fh);
			}
		}


		Pathfinder pf = new Roadfinder();

		while(hexHasTowns.size() > 0)
		{
			FilledHex fh = hexHasTowns.poll();
			
			rn=new RoadNetwork(networks);
			rn.addTownNode(hexmap, fh);
			
			int resource =fh.getLargestTown().getConnectivity();
			MutableInt cost = new MutableInt(resource);				
			//road from fh outwards.

			FilledHex goal = pf.Dijkstra(hexmap, fh,cost);
		
			if (goal != null && !goal.equals(fh))
			{
			
				
				//System.out.println("Before " + resource);
				rn.createRoad(hexmap,goal,fh);		
				networks.add(rn);
				//System.out.println("After " + fh.getLargestTown().getConnectivity());
				hexHasTowns.add(fh); //Re-add hex to townlist to go again.	
			}
		}
		
		//Town finder, finds neighbouring towns and shores up shoddy connections from before.
		
		
		/*
		Pathfinder tf = new Townfinder();
		it = hexmap.getHexes().values().iterator();
		while(it.hasNext())
		{
			FilledHex fh =it.next();
			
			if (fh.getLargestTown() !=null && fh.getLargestTown().getConnectivity() > 0)
			{
				hexHasTowns.add(fh);
			}
		}
		System.out.println("starting town finder");
		while(hexHasTowns.size() > 0)
		{
			FilledHex fh = hexHasTowns.poll();
			
			rn=new RoadNetwork(networks);
			rn.addTownNode(hexmap, fh);
			
			int resource =fh.getLargestTown().getConnectivity();
			MutableInt cost = new MutableInt(resource);				
			//road from fh outwards.

			FilledHex goal = tf.Dijkstra(hexmap, fh,cost);
		
			if (goal != null && !goal.equals(fh))
			{
			
				rn.createRoad(hexmap,goal,fh);		
				networks.add(rn);
				hexHasTowns.add(fh); //Re-add hex to townlist to go again.	
			}	
		}
		*/
		
		//TODO branch cleaner, 
		//if one connection has nothing (no branches or towns) (simple)
		//and starts and ends in the same place as another connection, destroy it.
		//Therefore, will delete branches that aren't as important.
		//Get each roadnode, if >2 connections, follow each connection
		//step on each connection until a town or branch is reached. If a branch is reached, this is no longer a simple branch and cannot be deleted.
		//	This branch continues until a town OR meets a node on a different branch, but cannot delete itself.
		//	if any two+ connections end in the same node, delete the simple path (with most distance).
						
		return networks;
	}
}

/*
boolean found = false;
FilledHex starttry;
FilledHex endtry;
Set<Connection> initialroad = new HashSet<>();
//Test each town from largest-> smallest, seeing if an initial connection can be made.
for(int ii = 0; ii < testtowns.size() && !found;ii++)
{
	for(int jj = ii+1;jj<testtowns.size() && !found;jj++)
	{
		starttry=testtowns.get(ii);
		endtry=testtowns.get(jj);
		int resource = endtry.getLargestTown().getConnectivity()+starttry.getLargestTown().getConnectivity();
		MutableInt rr = new MutableInt(resource);
		initialroad = pf.AStar(hexmap, endtry, starttry,rr);
		if (initialroad != null)
		{
			found = true; //found, should construct a road between those two.
		}				
	}
}*/



	/*System.out.println("Initial Road Constructed " + initialroad);
	rn = new RoadNetwork(networks);	
	rn.initialRoad(hexmap,initialroad);
	networks.add(rn);*/
