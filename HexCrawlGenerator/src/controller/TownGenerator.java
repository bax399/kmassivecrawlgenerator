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
import model.properties.BaseBiome;
import model.stats.StatsTown;
public class TownGenerator extends Generator
{
	Map<StatsTown,Set<BaseBiome>> validbiomes;
	List<StatsTown> townlist;
	Map<String,BaseBiome> allbiomes;
	ConnectedHexMap hexmap;	
	
	public TownGenerator(ConnectedHexMap chm, ArrayList<Properties> townprops, Map<String,BaseBiome> biomes, Random r)
	{
		super(r);
		validbiomes = new HashMap<>();
		townlist = new ArrayList<>();
		allbiomes = biomes;
		hexmap=chm;
		
		PropertiesFactory pf = new PropertiesFactory();
		townlist = pf.processProperties(townprops);
		rand = r;
		
		for(StatsTown t : townlist)
		{
			Set<BaseBiome> validtbiomes = processValidBiomes(t.getVBiomes());
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


			
			for(StatsTown t:townlist)
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
			if (validbiomes.get(t).contains(curr.getBiome().getConcreteBiome()))
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
	
	public Set<BaseBiome> processValidBiomes(String weight)
	{
		Set<BaseBiome> bset = new HashSet<>();		
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
			for(Map.Entry<String,BaseBiome> entry : allbiomes.entrySet())
			{
				bset.add(entry.getValue());
			}
			
		}
		
		return bset;
	}
}
