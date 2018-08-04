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
<<<<<<< HEAD
	 
	public static Biome basic = new Biome("basic",new int[] {0,40,255},0,0,1,0,0);
=======
	public int priority=0; //Editable field for pathfinding priority
	public int rivertype=-1; //Editable field for creating rivers
>>>>>>> refs/heads/hexstorage
	
	//Points are stored to place worldobjects in exact locations
	//Need a better way to store Points.
	//These are ONLY ITEMS THAT EXIST WITHIN THE HEX
	private Set<Lair> lairs;
	private Set<Dungeon> dungeons;
	private Set<Town> towns;
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
	//Blahcomments

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

<<<<<<< HEAD
=======
	//TODO setup structure storing in hexes, search all for object o.
	public boolean contains(Object o)
	{
		
		return false;
		
	}
	
	public void addRiverNode(RiverNode rn)
	{
		river = rn;
	}
	
	public RiverNode getRiverNode()
	{
		return river;
	}
	
>>>>>>> refs/heads/hexstorage
	public void setBiome(Biome b)
	{
		biome = b;
	}
	
	public Biome getBiome()
	{
		return biome;
	}
<<<<<<< HEAD
	
	public Color getColor()
	{
		return biome.color;
	}
	
	public String getName()
	{
		return biome.name;
	}
	
	public int getHeight()
	{
		return biome.height;
	}
	
	public double getRiverOrigin()
	{
		return biome.riverorigin;
	}
	
	public double getRiverEnd()
	{
		return biome.riverend;
	}	
=======
>>>>>>> refs/heads/hexstorage
}
