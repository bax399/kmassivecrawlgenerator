package controller;
import java.util.*;
import model.*;
import model.ConnectedHexMap;
public class RiverGenerator 
{
	Random rand;
	
	public RiverGenerator(Random rd)
	{
		rand = rd;
	}
	
	
	public Set<RiverNetwork> initializeRivers(ConnectedHexMap chm)
	{
		List<FilledHex> riverpoints = new ArrayList<>();
		Queue<FilledHex> queue = new LinkedList<>();
		RiverNetwork rn;
		Set<RiverNetwork> networks = new HashSet<>();
		
		Iterator<FilledHex> it = chm.getHexes().values().iterator();
		while(it.hasNext())
		{
			FilledHex fh =it.next();
			
			//Start
			if (rollRiver(fh.getBiome().getRiverOrigin()))
			{
				fh.rivertype=1;
				riverpoints.add(fh);
			}
			//End
			else if (rollRiver(fh.getBiome().getRiverEnd()))
			{
				fh.rivertype=0;
				riverpoints.add(fh);
			}	
		}
		
		Iterator<FilledHex> it2 = riverpoints.iterator();
		while(it2.hasNext())
		{
			FilledHex fh2 = it2.next();
			if (queue.isEmpty())
			{
				queue.add(fh2);
			}
			else if (queue.peek().rivertype != fh2.rivertype) //found a start or an end, make a river to each other.
			{
				rn = new RiverNetwork(networks);
				if(queue.peek().rivertype<fh2.rivertype)
				{
					rn.createRiver(chm, queue.poll(), fh2);
				}
				else
				{
					rn.createRiver(chm,fh2,queue.poll());
				}
				networks.add(rn);
			}
			else
			{
				queue.add(fh2);
			}
		}
		
		//TODO remove this
		System.out.println(networks.size());
		
		return networks;
	}
	
	public boolean rollRiver(double chance)
	{
		boolean river = false;
		double randd = rand.nextDouble();
		
		if(Double.compare(chance,0d)>0 && Double.compare(chance, 1d)<0)
		{
			river = (Double.compare(randd,chance) < 0);
		}
		
		return river;
	}	
}
