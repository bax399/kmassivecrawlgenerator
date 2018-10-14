package model;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javafx.util.Pair;
import model.merowech.ConcaveHull;
import model.merowech.ConcaveHull.Point;
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
	
//	public boolean tryaddhex(FilledHex hh)
//	{
//		boolean added = false;
//		
//		if (hh.getRegion()==null)
//		{
//			//If valid biomes and hex's biomes have anything in common.
//			if ((getValidBiomes().contains("all")) || (!Collections.disjoint(getValidBiomes(),hh.getBiome().getStrBiomes())))
//			{
//				addhex(hh);
//				added = true;	
//			}
//		}
//		
//		return added;
//	}
	
//	public void addhex(FilledHex hh)
//	{
//		hh.setRegion(this);		
//		//update neighbour hexes - remove hex.
//		neighbourhexes.remove(hh);
//
//		addBiomes(hh.getBiomes());
//		
//		//update region - add hex.		
//		regionhexes.add(hh);
//
//		
//		updateAll();
//
//	}
	
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
	
	public HexRegion mergeSmallest(HexRegion other)
	{
		HexRegion merger;
		HexRegion mergee;
		if (this.getRegionSize() < other.getRegionSize())
		{
			merger = other;
			mergee = this;
		}
		else
		{
			merger = this;
			mergee = other;
		}
		
		merger.mergeRegions(mergee);
		
		return merger;
	}
	
	public void mergeRegions(HexRegion mergee)
	{
		
//		Set<FilledHex> overlapHexes = new HashSet<>(getEdgeHexes());
//		overlapHexes.retainAll(mergee.getNeighbourHexes());
		
//		//Not correctly adding all? Or adding very few...
//		regionhexes.addAll(mergee.getRegionHexes());
//		
//		edgehexes.addAll(mergee.getEdgeHexes());
//		edgehexes.removeAll(overlapHexes);
//		neighbourhexes.addAll(mergee.getNeighbourHexes());
//		neighbourhexes.removeAll(overlapHexes);
//		
//		for(FilledHex hh : mergee.getRegionHexes())
//		{
//			hh.setRegion(this);
//		}
				
		for(FilledHex hh : mergee.getRegionHexes())
		{
			addHex(hh);
		}
		
	}
	
	public Polygon getShape(Layout lt)
	{
		if (shape == null) {calculateShape(lt);}
		return shape;
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
	
	//Should only call this method after finishing all regions
	public void calculateShape(Layout lt) 
	{
		ConcaveHull ch = new ConcaveHull();
		ArrayList<Point> edgepoints = new ArrayList<>();
		for(FilledHex h : edgehexes)
		{
			for(int ii=0;ii<6;ii++)
			{
				Point pp = lt.pointCorner(h,ii);
				edgepoints.add(pp);
			}
		}
		ArrayList<Point> points = ch.calculateConcaveHull(edgepoints, 10);
		shape = new Polygon();
		for(int ii = 0; ii < points.size();ii++)
		{
			shape.addPoint((int)Math.round(points.get(ii).x), (int)Math.round(points.get(ii).y));
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
