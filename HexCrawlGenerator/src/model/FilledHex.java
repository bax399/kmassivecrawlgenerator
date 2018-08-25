package model;

import java.awt.Polygon;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.redblob.Hex;
import model.redblob.Point;
import model.worldobjects.Dungeon;
import model.worldobjects.HexTown;
import model.worldobjects.Lair;
import model.worldobjects.Landmark;
import model.worldobjects.Nomad;
import model.worldobjects.RoadNode;
import model.worldobjects.WorldObject;

public class FilledHex extends Hex 
{
	private Biome biome;
	public Point center;
	public Polygon shape = new Polygon();
	public int priority=0; //Editable field for pathfinding priority
	public int rivertype=-1; //Editable field for creating rivers
	
	//Points are stored to place worldobjects in exact locations
	//Need a better way to store Points.
	//These are ONLY ITEMS THAT EXIST WITHIN THE HEX
	private Set<Lair> lairs;
	private Set<Dungeon> dungeons;
	private Set<HexTown> towns = new HashSet<>();
	private Set<Landmark> landmarks;
	private Set<Nomad> nomads;
	private RiverNode river = null;
	private RoadNode road=null;
	
	private HexRegion region;
	
	//Stores the random points for each item it contains, rivers and roads store their own.
	private Map<WorldObject, Point> points;
	
	//TODO make a way to roll for random points within hex that isn't cpu intensive
	//thoughts: this should be done in hexmap, as then we just add the hex's center to the random point and we're in luck.
	
	//TODO addWorldObject methods to FilledHexes, randomize point then add it to the map.
	public FilledHex(Biome b, int q, int r, int s)
	{
		super(q,r);
		biome = b;
		region = null;
	}

	public FilledHex(Biome b, int q, int r)
	{
		super(q,r);
		biome = b;
		region = null;
	}
	
	//Can create FilledHexes using default biome values.
	public FilledHex(int q, int r)
	{
		super(q,r);
		biome = BiomeConcrete.basic;
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
	
	public void setBiome(Biome b)
	{
		biome = b;
	}
	
	public Biome getBiome()
	{
		return biome;
	}
	
	public Set<Biome> getBiomes()
	{
		return biome.getBiomes();
	}
	
	public Set<FilledHex> getNeighbours()
	{
		return null;
	}
}
