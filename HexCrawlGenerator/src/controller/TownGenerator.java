package controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import model.Biome;
import model.ConnectedHexMap;
import model.FilledHex;
import model.worldobjects.Town;
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
		
		List<FilledHex> list = new ArrayList<FilledHex>(hexmap.getHexes().values());
		
		while(list.size() > 0)
		{
			int randomindex = rand.nextInt(list.size());
			FilledHex curr = list.get(randomindex);
			list.remove(randomindex);


			
			for(Town t:townlist)
			{
				rollTown(t,curr);
			}
		}
	}
		
	public void rollTown(Town t, FilledHex curr)
	{
		if (!t.limitReached())
		{
			//getBiome().getBiome() will get the base biomes, ignoring modifiers.
			if (validbiomes.get(t).contains(curr.getBiome().getBiome()))
			{
				if(rollChance(t.getChance()))
				{
					if(t.needsRiver() && (curr.getRiverNode() != null))
					{
						t.createTown(curr, hexmap.getRandomPoint(curr));
					}
					else if (!t.needsRiver())
					{
						t.createTown(curr, hexmap.getRandomPoint(curr));
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
