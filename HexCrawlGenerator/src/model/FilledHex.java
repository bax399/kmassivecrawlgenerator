package model;

import java.util.*;
import model.worldobjects.*;
import java.awt.Color;
import java.awt.Polygon;
import model.redblob.Hex;
import model.redblob.Point;

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
	private Set<RoadNode> roads;
	
	//Stores the random points for each item it contains, rivers and roads store their own.
	private Map<WorldObject, Point> points;
	
	//TODO make a way to roll for random points within hex that isn't cpu intensive
	//thoughts: this should be done in hexmap, as then we just add the hex's center to the random point and we're in luck.
	
	//TODO addWorldObject methods to FilledHexes, randomize point then add it to the map.
	public FilledHex(Biome b, int q, int r, int s)
	{
		super(q,r);
		biome = b;
	}

	public FilledHex(Biome b, int q, int r)
	{
		super(q,r);
		biome = b;
	}
	
	//Can create FilledHexes using default biome values.
	public FilledHex(int q, int r)
	{
		super(q,r);
		biome = BiomeConcrete.basic;
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
	
	public void add(RiverNode rn)
	{
		river = rn;
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
}
