package controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import model.ConnectedHexMap;
import model.FilledHex;
import model.stats.StatsCoreBiome;
import model.stats.StatsTown;
public class TownGenerator extends Generator
{
	Map<StatsTown,Set<StatsCoreBiome>> validTownBiomes;
	List<StatsTown> townList;
	Map<String,StatsCoreBiome> allBiomes;
	ConnectedHexMap hexmap;	
	
	public TownGenerator(ConnectedHexMap chm, List<StatsTown> townList, Map<String,StatsCoreBiome> biomes, Random r)
	{
		super(r);
		validTownBiomes = new HashMap<>();
		allBiomes = biomes;
		hexmap=chm;
		
		this.townList = townList;
		rand = r;
		
		for(StatsTown t : townList)
		{
			Set<StatsCoreBiome> townBiomes = processValidBiomes(t.getVBiomes());
			validTownBiomes.put(t, townBiomes);
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


			
			for(StatsTown t:townList)
			{
				rollTown(t,curr);
			}
		}
	}
		
	public void rollTown(StatsTown t, FilledHex curr)
	{
		if (!t.limitReached())
		{
			//getBiome().getBiome() will get the base biomes, ignoring modifiers.
			if (validTownBiomes.get(t).contains(curr.getHabitat().getCoreBiome()))
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
	
	public Set<StatsCoreBiome> processValidBiomes(String weight)
	{
		Set<StatsCoreBiome> bset = new HashSet<>();		
		if (!weight.contains("all"))
		{
		
			String[] eachname = weight.split(",");
			
			for(int ii=0;ii<eachname.length;ii++)
			{
				bset.add(allBiomes.get(eachname[ii]));
			}
		}
		else //if it contains keyword all
		{
			for(Map.Entry<String,StatsCoreBiome> entry : allBiomes.entrySet())
			{
				bset.add(entry.getValue());
			}
			
		}
		
		return bset;
	}
}
