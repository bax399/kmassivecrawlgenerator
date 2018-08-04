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
	
	
	public Set<RiverNetwork> initializeRivers(ConnectedHexMap chm, double mindistance)
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
 
			if (Double.compare(fh.getBiome().getRiverEnd(),1d)>=0)
			{
				riverends.add(fh);
			}
		}
		
		Iterator<FilledHex> it2 = riverstarts.iterator();


		while(it2.hasNext()&&riverends.size()>0)
		{
			rn = new RiverNetwork(networks);								
			FilledHex fh2 = it2.next();
			int random=0;
			
			//Choosing a random ending that isn't too close			
			FilledHex fend=null;
			random = rand.nextInt(riverends.size());
			fend = riverends.get(random);
			riverends.remove(random);	
			/*
			for(int ii = 0;ii<5;ii++)
			{
				

				if (fend.distance(fh2) > (int) mindistance*5) break;
			}*/
		
						
			rn.createRiver(chm, fend, fh2);
			networks.add(rn);
		}
						
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