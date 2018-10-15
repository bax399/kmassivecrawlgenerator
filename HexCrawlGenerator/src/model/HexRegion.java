package model;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javafx.util.Pair;
import model.redblob.Layout;
import model.stats.StatsCoreBiome;
import model.stats.StatsRegion;

/**
 * @author Keeley
 * Defines Region functionality. Regions generalize hex contents in the area.
 */
public class HexRegion 	
{
	
	public final StatsRegion stats; //what this monster references whenever it is ask for anything.

	private Polygon shape = null;	
	
	private Set<FilledHex> regionhexes;
	private Set<FilledHex> edgehexes;
	private Set<FilledHex> neighbourhexes;
	private HashMap<StatsCoreBiome, Integer> biomeamounts;
	private StatsCoreBiome majoritybiome;
	//private Biome tallestbiome;
	//private int tallestheight;
	private int majoritysize;
	
	private Map<FilledHex,ArrayList<Pair<Point,Point>>> edgelines;

	
	
	private ConnectedHexMap chm;
	public HexRegion(StatsRegion type, FilledHex origin,ConnectedHexMap chm)
	{	
		stats = type;

		regionhexes = new HashSet<>();
		edgehexes = new HashSet<>();
		neighbourhexes = new HashSet<>();
		biomeamounts = new HashMap<>();
		edgelines = new HashMap<>();

		//Get concrete biome of origin

		this.chm = chm;
		//addhex(origin);
		addHex(origin);
//		majoritybiome = origin.getBiome().getConcreteBiome();
//		majoritysize = 1; //Set to 1 to bias original concrete biome as the majority biome.		
	}
	
	public void calculateEdgeLines()
	{
		for(FilledHex ehex : edgehexes)
		{
			ArrayList<Pair<Point,Point>> lines = new ArrayList<>();
			for(int ii=0;ii<6;ii++)
			{
				FilledHex nhex = ehex.getNeighbour(chm, ii);
				if (neighbourhexes.contains(nhex))
				{
					Point a = chm.ly.pointCorner(ehex, ii);
					Point b;
					if (ii<5)
					{
						b = chm.ly.pointCorner(ehex,ii+1);
					}
					else
					{
						b = chm.ly.pointCorner(ehex, 0);
					}
					Pair<Point,Point> ab = new Pair<>(a,b);
					
					lines.add(ab);
				}
			}
			edgelines.put(ehex, lines);	
		}
	}
	
	public Map<FilledHex,ArrayList<Pair<Point,Point>>> getEdgeLines()
	{
		return edgelines;
	}
	
	public void addBiomes(Set<StatsCoreBiome> biomes)
	{
		for(StatsCoreBiome bb : biomes)
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
	
	public void addHex(FilledHex hh)
	{
		
		neighbourhexes.remove(hh);
		
		//Update EdgeHexes
		for(FilledHex hhNeighbour : hh.getNeighbours(chm))
		{
			if (edgehexes.contains(hhNeighbour))
			{
				if(Collections.disjoint(hhNeighbour.getNeighbours(chm), neighbourhexes))
				{
					edgehexes.remove(hhNeighbour);
				}				
			}
			else
			{
				edgehexes.add(hh);
				neighbourhexes.add(hhNeighbour);
			}
			
		}
	
		
		
		regionhexes.add(hh);
		hh.setRegion(this);
		addBiomes(hh.getHabitat().getAllBiomes());

	}
	
	public void mergeRegions(HexRegion mergee)
	{	
		for(FilledHex hh : mergee.getRegionHexes())
		{
			addHex(hh);
		}
	}
	
	//Get majority biome in region.
	public Set<String> getValidBiomes()
	{
		return majoritybiome.getValidRegionBiomes();
	}
	
	public StatsCoreBiome getMajorityBiome()
	{
		return majoritybiome; 
	}
	
	public int getRegionSize()
	{
		return regionhexes.size();
	}
	
	//Get "edge" hexes of region.
	public Set<FilledHex> getEdgeHexes()
	{
		return edgehexes;
	}

	public Set<FilledHex> getRegionHexes()
	{
		return regionhexes;
	}

	public Set<FilledHex> getNeighbourHexes()
	{
		return neighbourhexes;
	}
	
	public Set<HexRegion> getNeighbourRegions()
	{
		Set<HexRegion> neighbourRegions = new HashSet<>();
		for(FilledHex nh : neighbourhexes)
		{
			neighbourRegions.add(nh.getRegion());
		}
		return neighbourRegions;
	}
	
//	public void updateNeighbourRegions()
//	{
//		neighbourregions.clear();
//		for(FilledHex nh : neighbourhexes)
//		{
//			neighbourregions.add(nh.getRegion());
//		}
//	}
//	
	public void updateEdges() // TODO EDGE HEXES NOT WORKING.
	{
		edgehexes.clear();
		for(FilledHex rh : regionhexes)
		{	
			Set<FilledHex> updatedfrontier = new HashSet<>();
			updatedfrontier.addAll(rh.getNeighbours(chm));
			if (!regionhexes.containsAll(updatedfrontier))
			{
				edgehexes.add(rh);
			}
		}		
	}
	
	public void updateNeighbourHexes()
	{
		neighbourhexes.clear();
		for(FilledHex eh : edgehexes)
		{
			Set<FilledHex> frontier = new HashSet<>();
			frontier.addAll(eh.getNeighbours(chm));
			for(FilledHex fh : frontier)
			{
				if (!regionhexes.contains(fh))
				{
					neighbourhexes.add(fh);
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
		//PFunctions.outputString(this,regionhexes + "" + mergee.getRegionHexes());
		regionhexes.addAll(mergee.regionhexes);
		//PFunctions.outputString(this,regionhexes +"");
		
		for(FilledHex mh : mergee.regionhexes)
		{
			for(StatsCoreBiome bb : mh.getHabitat().getAllBiomes())
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
			mh.setRegion(this);
		}
		
		updateEdges();
		updateNeighbourHexes();
		//updateNeighbourRegions();
	}
	
	public void updateAll()
	{
		updateEdges();
		updateNeighbourHexes();
		//updateNeighbourRegions();	
	}
}
