package controller;
import java.util.Comparator;
import java.util.HashSet;
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
import model.worldplaces.HexTown;
public class RoadGenerator extends Generator
{
	ConnectedHexMap hexmap;	

	public static Comparator<HexTown> sizeComparator = new Comparator<HexTown>()
	{
		@Override
		public int compare(HexTown h1, HexTown h2)
		{
			return (int)(h1.getConnectivity()-h2.getConnectivity());
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
		PriorityQueue<HexTown> towns = new PriorityQueue<>(sizeComparator);
	
		towns.addAll(hexmap.getTowns());
		
		Pathfinder pf = new Roadfinder(getRand());
		while(towns.size() > 0)
		{
			FilledHex originHex = towns.poll().getHex();
			boolean successfulRoad=true;
			
			if (originHex.getRoadNode() == null)
			{
				rn=new RoadNetwork();
				rn.addNode(rn.createNode(hexmap, originHex), originHex);
			}
			else
			{
				rn=(RoadNetwork)originHex.getRoadNode().getNetwork();
			}
				
			while(successfulRoad)
			{

				successfulRoad=false;

				
				int resource = originHex.getLargestTown().getConnectivity();
				MutableInt cost = new MutableInt(resource);				
				//road from fh outwards.
				FilledHex goal = pf.terminatingDijkstra(hexmap, originHex,cost);

				if (goal != null && !goal.equals(originHex))
				{
					//rn.addTownNode(hexmap, goal, networks);
					// create BFS path from origin to destination
					MutableInt rr = new MutableInt(resource);

					Set<Edge<FilledHex>> path = pf.AStar(hexmap, goal, originHex, rr);
					
					originHex.getLargestTown().setConnectivity(rr.value);
					
					rn.createNetwork(hexmap,path,networks);		
					networks.add(rn);
					successfulRoad=true;
				}
			}
		}
		
		KFunctions.outputString(this,"NumRoadsNetworks:"+networks.size());
		return networks;		
	}
}
