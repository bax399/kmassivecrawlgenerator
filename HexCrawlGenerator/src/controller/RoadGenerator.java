package controller;
import java.util.*;
import model.*;
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
		for(int ii = 0; ii < testtowns.size();ii++)
		{
			for(int jj = ii+1;jj<testtowns.size();jj++)
			{
				starttry=testtowns.get(ii);
				endtry=testtowns.get(jj);
				int resource = endtry.getLargestTown().getConnectivity()+starttry.getLargestTown().getConnectivity();
				initialroad = pf.AStar(hexmap, endtry, starttry,resource);
				if (initialroad != null)
				{
					found = true;
					break; //found, should construct a road between those two.
				}				
			}
		}
		
		//initial road between two biggest cities.
		if (found)
		{
			rn = new RoadNetwork(networks);		
			rn.initialRoad(hexmap,initialroad);
			networks.add(rn);
			

			while(alltowns.size() > 0)
			{
				FilledHex fh = alltowns.poll();
				Integer cost = new Integer(0);
				int resource =fh.getLargestTown().getConnectivity();
				
				//road from fh outwards.
				FilledHex goal = pf.Dijkstra(hexmap, fh, resource,cost);

				if (goal != null)
				{
					fh.getLargestTown().setConnectivity(resource-cost);
					alltowns.add(fh); //Re-add hex to townlist to go again.
					rn.createRoad(hexmap,goal,fh);
				}
				
			}
			
		}	
						
		return networks;
	}
}
