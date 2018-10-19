package controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import model.ConnectedHexMap;
import model.FilledHex;
import model.stats.StatsCoreBiome;
import model.stats.StatsTown;
import model.worldplaces.HexTown;
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
		
		for(StatsTown t : townList)
		{
			Set<StatsCoreBiome> townBiomes = processValidBiomes(t.getVBiomes());
			validTownBiomes.put(t, townBiomes);
		}
	}
	
	public Set<HexTown> generateTowns()
	{
		
		List<FilledHex> list = new ArrayList<FilledHex>(hexmap.getHexes().values());
		Set<HexTown> towns=new HashSet<>();
		while(list.size() > 0)
		{
			int randomindex = getRand().nextInt(list.size());
			FilledHex curr = list.get(randomindex);
			list.remove(randomindex);

			for(StatsTown t:townList)
			{
				HexTown town = rollTown(t,curr);
				if (town!=null)
				{
					towns.add(town);
				}
			}
		}
		
		return towns;
		
	}
		
	public HexTown rollTown(StatsTown t, FilledHex curr)
	{
		HexTown town=null;
		if (!t.limitReached())
		{
			//getBiome().getBiome() will get the base biomes, ignoring modifiers.
			if (validTownBiomes.get(t).contains(curr.getHabitat().getCoreBiome()))
			{
				if(rollChance(t.getChance()))
				{
					if(t.needsRiver() && (curr.getRiverNode() != null))
					{
						town=t.createTown(curr, hexmap.getRandomPoint(curr));
					}
					else if (!t.needsRiver())
					{
						town=t.createTown(curr, hexmap.getRandomPoint(curr));
					}
				}
			}
		}
		return town;
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
