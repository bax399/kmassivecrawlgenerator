package model;
import java.util.*;

import model.worldobjects.Town;
public class HexRegion 
{
	
	public final Region stats; //what this monster references whenever it is ask for anything.

	Set<FilledHex> regionhexes;
	Set<FilledHex> edgehexes;
	Set<FilledHex> neighbourhexes;
	HashMap<Biome, Integer> biomeamounts;
	Biome majoritybiome;
	int majoritysize;
	int regionsize;
	
	public HexRegion(Region type, FilledHex origin)
	{
		stats = type;
		
		regionhexes = new HashSet<>();
		edgehexes = new HashSet<>();
		neighbourhexes = new HashSet<>();
		biomeamounts = new HashMap<>();
		
		//Get concrete biome of origin
		majoritybiome = origin.getBiome().getConcreteBiome();
		majoritysize = 1; //Set to 1 to bias original concrete biome as the majority biome.
		regionsize=0;
		addhex(origin);
	}
	
	public boolean tryaddhex(FilledHex hh)
	{
		boolean added = false;
		
		if (neighbourhexes.contains(hh))
		{
			//If valid biomes and hex's biomes have anything in common.
			if (!Collections.disjoint(getValidBiomes(),hh.getBiomes()))
			{
				addhex(hh);
				added = true;	
			}
		}
		
		return added;
	}
	public void addhex(FilledHex hh)
	{
		//update neighbour hexes - remove hex.
		neighbourhexes.remove(hh);

		//update region - add hex.		
		regionhexes.add(hh);
		regionsize+=1;
		for(Biome bb : hh.getBiomes())
		{
			int biomenum = 1;
			if (biomeamounts.get(bb) != null)
			{
				biomenum = biomeamounts.get(bb)+1;
			}
			biomeamounts.put(bb, biomenum);
			if (biomenum > majoritysize)
			{
				majoritybiome = bb;
				majoritysize = biomenum;
			}
		}
		
		//If the hex's neighbours are not all in the regionhex set, this hex is an edge hex, need to add new neighbours too.
		Set<FilledHex> frontier1 = hh.getNeighbours();
		if (!regionhexes.containsAll(frontier1))
		{			
			edgehexes.add(hh);
			
			//update neighbour hexes - add any new based on the edge hex		
			for(FilledHex fh : frontier1)
			{
				if !(regionhexes.contains(fh))
				{
					neighbourhexes.add(fh);
				}
			}
		}
		
		//update edge hexes - iterate through and remove edgehexes that have no foreign neighbours
		Set<FilledHex> testedges = new HashSet<>(edgehexes);
		for(FilledHex eh : testedges)
		{	
			Set<FilledHex> frontier2 = eh.getNeighbours();
			if (regionhexes.containsAll(frontier2))
			{
				edgehexes.remove(eh);
			}
		}
	}
	
	//Get majority biome in region.
	public Set<Biome> getValidBiomes()
	{
		return majoritybiome.getValidRegionBiomes();
	}
	//Get "edge" hexes of region.
		
}
