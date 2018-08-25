package model;
import java.util.*;

import model.worldobjects.Town;
public class HexRegion 
{
	
	public final Region stats; //what this monster references whenever it is ask for anything.

	private Set<FilledHex> regionhexes;
	private Set<FilledHex> edgehexes;
	private Set<FilledHex> neighbourhexes;
	private HashMap<Biome, Integer> biomeamounts;
	private Set<HexRegion> neighbourregions;
	private Biome majoritybiome;
	//private Biome tallestbiome;
	//private int tallestheight;
	private int majoritysize;
	private int regionsize;
	
	public HexRegion(Region type, FilledHex origin)
	{
		stats = type;
		
		regionhexes = new HashSet<>();
		edgehexes = new HashSet<>();
		neighbourhexes = new HashSet<>();
		biomeamounts = new HashMap<>();
		neighbourregions = new HashSet<>();
		//Get concrete biome of origin
		majoritybiome = origin.getBiome().getConcreteBiome();
		majoritysize = 1; //Set to 1 to bias original concrete biome as the majority biome.
		regionsize=0;
		addhex(origin);
	}

	public HexRegion(FilledHex origin)
	{
		stats = null;
		
		regionhexes = new HashSet<>();
		edgehexes = new HashSet<>();
		neighbourhexes = new HashSet<>();
		biomeamounts = new HashMap<>();
		neighbourregions = new HashSet<>();
		
		//Get concrete biome of origin
		majoritybiome = origin.getBiome().getConcreteBiome();
		majoritysize = 1; //Set to 1 to bias original concrete biome as the majority biome.
		regionsize=0;
		addhex(origin);
	}	
	
	public boolean tryaddhex(FilledHex hh)
	{
		boolean added = false;
		
		if (neighbourhexes.contains(hh) && hh.getRegion() == null)
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
		Set<FilledHex> newfrontier = hh.getNeighbours(); //CURRENTLY NULL
		if (!regionhexes.containsAll(newfrontier))
		{			
			edgehexes.add(hh);
			
			//update neighbour hexes - add any new based on the edge hex		
			for(FilledHex fh : newfrontier)
			{
				if (!regionhexes.contains(fh))
				{
					neighbourhexes.add(fh);
				}
			}
		}
		
		//update edge hexes - iterate through and remove edgehexes that have no foreign neighbours
		updateEdges();
		//update neighbour regions
		updateNeighbourRegions();
	}
	
	//Get majority biome in region.
	public Set<Biome> getValidBiomes()
	{
		return majoritybiome.getValidRegionBiomes();
	}
	
	public Biome getMajorityBiome()
	{
		return majoritybiome;
	}
	
	//Get "edge" hexes of region.
	public Set<FilledHex> getEdgeHexes()
	{
		return edgehexes;
	}

	public int getRegionSize()
	{
		return regionsize;
	}

	public Set<HexRegion> getNeighbourRegions()
	{
		return neighbourregions;
	}
	
	public void updateNeighbourRegions()
	{
		neighbourregions.clear();
		for(FilledHex nh : neighbourhexes)
		{
			if (!neighbourregions.contains(nh.getRegion()))
			{
				neighbourregions.add(nh.getRegion());
			}
		}
	}
	
	public void updateEdges()
	{
		for(FilledHex eh : regionhexes)
		{	
			Set<FilledHex> updatedfrontier = eh.getNeighbours();
			if (regionhexes.containsAll(updatedfrontier))
			{
				edgehexes.remove(eh);
			}
			else
			{
				edgehexes.add(eh);
			}
		}		
	}
	
	public void updateNeighbourHexes()
	{
		for(FilledHex eh : edgehexes)
		{
			Set<FilledHex> frontier = eh.getNeighbours();
			for(FilledHex fh : frontier)
			{
				if (!regionhexes.contains(fh))
				{
					neighbourhexes.add(fh);
				}
				else
				{
					neighbourhexes.remove(fh);
				}
			}
		}
	}
	
	//Merge
	//Add all region hexes from one to the other.
	//Clear neighbours and edges
	//Iterate through region and add neighbour + edge hexes.	
	public void mergeRegion(HexRegion mergee)
	{
		regionhexes.addAll(mergee.regionhexes);
		regionsize += mergee.getRegionSize();
		
		for(FilledHex mh : mergee.regionhexes)
		{
			for(Biome bb : mh.getBiomes())
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
		}
		
		mergee = this;
		updateEdges();
		updateNeighbourHexes();
		updateNeighbourRegions();
	}
}
