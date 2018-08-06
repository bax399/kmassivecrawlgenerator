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
	
	//TODO add minor roads to generation
	public Set<RoadNetwork> generateRoads()
	{	

		Set<RoadNetwork> networks = new HashSet<>();
		RoadNetwork	rn;
		//Possible starting points.
		PriorityQueue<FilledHex> alltowns = new PriorityQueue<>(sizeComparator);
		
		Iterator<FilledHex> it = hexmap.getHexes().values().iterator();
		while(it.hasNext())
		{
			FilledHex fh =it.next();
			
			if (fh.getLargestTown() !=null)
			{
				alltowns.add(fh);
			}
		}

		FilledHex starttry;
		FilledHex endtry;
		Pathfinder pf = new Roadfinder();
		ArrayList<FilledHex> testtowns = new ArrayList<>();
		boolean found = false;
		testtowns.addAll(alltowns);
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
		}

		//initial road between two biggest cities.
		if (found)
		{
			System.out.println("Initial Road Constructed " + initialroad);
			rn = new RoadNetwork(networks);	
			rn.initialRoad(hexmap,initialroad);
			networks.add(rn);

			

			while(alltowns.size() > 0)
			{
				FilledHex fh = alltowns.poll();
				
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
					alltowns.add(fh); //Re-add hex to townlist to go again.	
				}
				else
				{
					//System.out.println("ended town-road building");
				}
				
			}
			
			//Town finder, finds neighbouring towns and shores up shoddy connections from before.
			Pathfinder tf = new Townfinder();
			it = hexmap.getHexes().values().iterator();
			while(it.hasNext())
			{
				FilledHex fh =it.next();
				
				if (fh.getLargestTown() !=null && fh.getLargestTown().getConnectivity() > 0)
				{
					alltowns.add(fh);
				}
			}
			System.out.println("starting town finder");
			while(alltowns.size() > 0)
			{
				FilledHex fh = alltowns.poll();
				
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
					alltowns.add(fh); //Re-add hex to townlist to go again.	
				}
				else
				{
					//System.out.println("ended town-neighbour building");
				}			
			}
			
		}	
						
		return networks;
	}
}
