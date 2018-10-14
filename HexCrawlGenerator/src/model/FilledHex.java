package model;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.merowech.ConcaveHull.Point;
import model.properties.BaseBiome;
import model.redblob.Hex;
import model.stats.StatsCoreBiome;
import model.worldplaces.HexTown;
import model.worldplaces.RiverNode;
import model.worldplaces.RoadNode;
import model.worldplaces.WorldPlace;

public class FilledHex extends Hex 
{
	private BaseBiome biome;
	public Point center;
	
	public int priority=0; //Editable field for pathfinding priority
	public int rivertype=-1; //Editable field for creating rivers
	
	
	private Polygon shape;	
	//Points are stored to place worldobjects in exact locations
	//Need a better way to store Points.
	//These are ONLY ITEMS THAT EXIST WITHIN THE HEX

	private Set<HexTown> towns = new HashSet<>();
	private RiverNode river = null;
	private RoadNode road=null; 
	 
	private HexRegion region;
	
	//Stores the random points for each item it contains, rivers and roads store their own.
	private Map<WorldPlace, Point> points;
	
	//TODO make a way to roll for random points within hex that isn't cpu intensive
	//thoughts: this should be done in hexmap, as then we just add the hex's center to the random point and we're in luck.
	
	//TODO addWorldObject methods to FilledHexes, randomize point then add it to the map.
	public FilledHex(BaseBiome b, int q, int r, int s)
	{
		super(q,r);
		biome = b;
		region = null;
	}

	public FilledHex(BaseBiome b, int q, int r)
	{
		super(q,r);
		biome = b;
		region = null;
	}
	
	//Can create FilledHexes using default biome values.
	public FilledHex(int q, int r)
	{
		super(q,r);
		biome = StatsCoreBiome.basic;
		region = null;
	}

	public void add(HexTown t)
	{
		towns.add(t);
	}
	
	public Set<HexTown> getTowns()
	{
		return Collections.unmodifiableSet(towns);
	}
	
	
	//TODO setup structure storing in hexes, search all for object o.
	public boolean contains(Object o)
	{
		
		return false;
		
	}
	
	public void add(RoadNode rn)
	{
		road=rn;
	}
	
	public RoadNode getRoadNode()
	{
		return road;
	}
	
	public HexTown getLargestTown()
	{
		int c=0;
		HexTown largest=null;
		for(HexTown t: towns)
		{
			if (t.getConnectivity() > c)
			{
				c = t.getConnectivity();
				largest=t;
			}
		}
		return largest;
	}
	
	public void add(RiverNode rn)
	{
		river = rn;
	}
	
	public HexRegion getRegion()
	{
		return region;
	}
	
	public void setRegion(HexRegion region)
	{
		this.region = region;
	}
	
	public RiverNode getRiverNode()
	{
		return river;
	}
	
	public void setBiome(BaseBiome b)
	{
		biome = b;
	}
	
	public BaseBiome getBiome()
	{
		return biome;
	}
	
	public Set<BaseBiome> getBiomes()
	{
		return biome.getBiomes();
	}
	
	public List<FilledHex> getNeighbours(ConnectedHexMap chm)
	{
		List<FilledHex> neighbours = new ArrayList<>();
		for(int ii = 0; ii < 6; ii++)
		{
			if (getNeighbour(chm,ii) != null) 
			{
				neighbours.add(getNeighbour(chm,ii)); 
			}
		}
		return neighbours; 
	}
	
	public FilledHex getNeighbour(ConnectedHexMap chm, int index)
	{
		return chm.getHex(this.neighbor(index)); 
	}	
	
	public void setShape(Polygon shape)
	{
		this.shape = shape;
	}
	
	public Polygon getShape()
	{
		return this.shape;
	}
}
