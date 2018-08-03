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
		List<FilledHex> riverstarts = new ArrayList<>();

		List<FilledHex> riverends = new ArrayList<>();
		
		RiverNetwork rn;
		Set<RiverNetwork> networks = new HashSet<>();
		
		Iterator<FilledHex> it = chm.getHexes().values().iterator();
		while(it.hasNext())
		{
			FilledHex fh =it.next();
			
			//Start
			if (rollRiver(fh.getBiome().getRiverOrigin()))
			{
				riverstarts.add(fh);
			}
			else if (fh.getBiome().getRiverOrigin()>0d)
			{
				riverends.add(fh);
			}
		}
		
		Iterator<FilledHex> it2 = riverstarts.iterator();
		while(it2.hasNext())
		{
			FilledHex fh2 = it2.next();
			rn = new RiverNetwork(networks);
			rn.createRiver(chm, riverends.get(rand.nextInt(riverends.size())), fh2);
			networks.add(rn);
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
