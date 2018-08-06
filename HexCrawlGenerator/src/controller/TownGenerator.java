package controller;
import java.util.*;
import model.*;
import model.worldobjects.*;
public class TownGenerator extends Generator
{
	Map<Town,Set<Biome>> validbiomes;
	List<Town> townlist;
	Map<String,Biome> allbiomes;
	ConnectedHexMap hexmap;	
	
	public TownGenerator(ConnectedHexMap chm, ArrayList<Properties> townprops, Map<String,Biome> biomes, Random r)
	{
		super(r);
		validbiomes = new HashMap<>();
		townlist = new ArrayList<>();
		allbiomes = biomes;
		hexmap=chm;
		
		PropertiesFactory pf = new PropertiesFactory();
		townlist = pf.processProperties(townprops);
		rand = r;
		
		for(Town t : townlist)
		{
			Set<Biome> validtbiomes = processValidBiomes(t.getVBiomes());
			validbiomes.put(t, validtbiomes);
		}
	}
	
	public void generateTowns()
	{
		Iterator<FilledHex> it = hexmap.getHexes().values().iterator();
		//TODO must change this so it randomly places them anywhere.
		List<FilledHex> list = new ArrayList<FilledHex>(hexmap.getHexes().values());
		
		while(list.size() > 0)
		{
			int randomindex = rand.nextInt(list.size());
			FilledHex curr = list.get(randomindex);
			list.remove(randomindex);

			for(Town t:townlist)
			{
				if (!t.limitReached())
				{
					if (validbiomes.get(t).contains(curr.getBiome()))
					{
						if(rollChance(t.getChance()))
						{
							t.createTown(curr, hexmap.getRandomPoint(curr));
							//System.out.println("created " + t.getName() + " @ " + curr.getBiome());
						}
					}
				}
			}
		}
		
	}
	
	public Set<Biome> processValidBiomes(String weight)
	{
		Set<Biome> bset = new HashSet<>();		
		if (!weight.contains("all"))
		{
		
			String[] eachname = weight.split(",");
			
			for(int ii=0;ii<eachname.length;ii++)
			{
				bset.add(allbiomes.get(eachname[ii]));
			}
		}
		else //if it contains keyword all
		{
			for(Map.Entry<String,Biome> entry : allbiomes.entrySet())
			{
				bset.add(entry.getValue());
			}
			
		}
		
		return bset;
	}
}
