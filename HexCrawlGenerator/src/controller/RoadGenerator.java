package controller;
import java.awt.event.ActionListener;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

import model.ConnectedHexMap;
import model.FilledHex;
import model.MutableInt;
import model.Network;
import model.Pathfinder;
import model.RoadNetwork;
import model.Roadfinder;
import model.Townfinder;
import model.graphresource.Edge;
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


		Pathfinder pf = new Roadfinder();
		Pathfinder tf = new Townfinder();
		Set<FilledHex> townTry = new HashSet<>();
		while(hexHasTowns.size() > 0)
		{
			FilledHex originHex = hexHasTowns.poll();
			
			rn=new RoadNetwork();
			rn.addTownNode(hexmap, originHex);
			
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
				Pathfinder rf = new Roadfinder();
				MutableInt rr = new MutableInt(resource);

				Set<Edge<FilledHex>> path = rf.AStar(hexmap, goal, originHex, rr);
				originHex.getLargestTown().setConnectivity(rr.value);
				// destination.getLargestTown().setConnectivity(destination.getLargestTown().getConnectivity()-rr.value);
				
				rn.createNetwork(hexmap,path,networks);		
				networks.add(rn);
				//System.out.println("After " + fh.getLargestTown().getConnectivity());
				hexHasTowns.add(originHex); //Re-add hex to townlist to go again.	
			}
		}
		
//		KFunctions.outputString(this,"NumRoadsNetworks:"+networks.size());
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
