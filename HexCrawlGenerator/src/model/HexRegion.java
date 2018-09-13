package model;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import functions.PFunctions;
import model.merowech.ConcaveHull;
import model.merowech.ConcaveHull.Point;
import model.redblob.Layout;

public class HexRegion 
{
	
	public final Region stats; //what this monster references whenever it is ask for anything.

	private Polygon shape = null;	
	
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

	private ConnectedHexMap chm;
	public HexRegion(Region type, FilledHex origin,ConnectedHexMap chm)
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
		this.chm = chm;
		addhex(origin);
	}
	
	public boolean tryaddhex(FilledHex hh)
	{
		boolean added = false;
		
		if (hh.getRegion() == null)
		{
			//If valid biomes and hex's biomes have anything in common.
			if ((getValidBiomes().contains("all")) || (hh.getBiome().getStrBiomes().contains("all")) ||
			(!Collections.disjoint(getValidBiomes(),hh.getBiome().getStrBiomes())))
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
		Set<FilledHex> newfrontier = new HashSet<>();
		newfrontier.addAll(hh.getNeighbours(chm));
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
		return neighbourregions;
	}
	
	public void updateNeighbourRegions()
	{
		neighbourregions.clear();
		for(FilledHex nh : neighbourhexes)
		{
			neighbourregions.add(nh.getRegion());
		}
	}
	
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
		PFunctions.outputString(this,regionhexes + "" + mergee.getRegionHexes());
		regionhexes.addAll(mergee.regionhexes);
		PFunctions.outputString(this,regionhexes +"");
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
			mh.setRegion(this);
		}
		
		updateEdges();
		updateNeighbourHexes();
		updateNeighbourRegions();
	}
	
	public void updateAll()
	{
		updateEdges();
		updateNeighbourHexes();
		updateNeighbourRegions();	
	}
}
