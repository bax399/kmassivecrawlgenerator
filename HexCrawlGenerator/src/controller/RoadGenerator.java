package controller;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

import functions.KFunctions;
import model.ConnectedHexMap;
import model.FilledHex;
import model.MutableInt;
import model.Network;
import model.Pathfinder;
import model.RoadNetwork;
import model.Roadfinder;
import model.graphresource.Edge;
public class RoadGenerator extends Generator
{
	ConnectedHexMap hexmap;	

	public static Comparator<FilledHex> sizeComparator = new Comparator<FilledHex>()
	{
		@Override
		public int compare(FilledHex h1, FilledHex h2)
		{
			return (int)(h1.getLargestTown().getConnectivity()-h2.getLargestTown().getConnectivity());
		}
	};	
	
	public RoadGenerator(ConnectedHexMap chm, Random rd)
	{
		super(rd);
		hexmap = chm;
	}
	
	public Set<Network> generateRoads()
	{	

		Set<Network> networks = new HashSet<>();
		RoadNetwork	rn=null;
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


		Pathfinder pf = new Roadfinder(getRand());
		while(hexHasTowns.size() > 0)
		{
			FilledHex originHex = hexHasTowns.poll();
			boolean successfulRoad=true;
			rn=new RoadNetwork();
			rn.addTownNode(hexmap, originHex,networks);			
			while(successfulRoad)
			{
				successfulRoad=false;

				
				int resource =originHex.getLargestTown().getConnectivity();
				MutableInt cost = new MutableInt(resource);				
				//road from fh outwards.
	
				FilledHex goal = pf.Dijkstra(hexmap, originHex,cost);
	
				//Town finder is too laggy, makes too many networks.
	//			if (goal==null && cost.value > 1 && !townTry.contains(originHex))
	//			{
	//				goal=tf.Dijkstra(hexmap, originHex, cost);
	//				townTry.add(originHex);
	//				
	//				if(goal!=null)
	//				{
	//					KFunctions.outputString(this,"Road from TownFinder");
	//				}
	//			}
	//			
				if (goal != null && !goal.equals(originHex))
				{
					// create BFS path from origin to destination
					MutableInt rr = new MutableInt(resource);
	
					rn.addTownNode(hexmap, goal, networks);
					Set<Edge<FilledHex>> path = pf.AStar(hexmap, goal, originHex, rr);
					
					originHex.getLargestTown().setConnectivity(rr.value);
					// destination.getLargestTown().setConnectivity(destination.getLargestTown().getConnectivity()-rr.value);
					
					rn.createNetwork(hexmap,path,networks);		
					networks.add(rn);
					//System.out.println("After " + fh.getLargestTown().getConnectivity());
					//hexHasTowns.add(originHex); //Re-add hex to townlist to go again.
					successfulRoad=true;
				}
			}
		}
		
		KFunctions.outputString(this,"NumRoadsNetworks:"+networks.size());
		return networks;		
	}
}
