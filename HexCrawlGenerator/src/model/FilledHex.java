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
	public static Biome basic = new Biome("basic",new int[] {0,40,255},0,0,1,0,0); //default biome.
	//Points are stored to place worldobjects in exact locations
	private Map<Point,Lair> lairs;
	private Map<Point,Dungeon> dungeons;
	private Map<Point,Town> towns;
	private Map<Point,Landmark> landmarks;
	private Map<Point,Nomad> nomads;
	
	public FilledHex(Biome b, int q, int r, int s)
	{
		super(q, r);
		biome = b;
	}

	public FilledHex(Biome b, int q, int r)
	{
		super(q, r);
		biome = b;
	}
	
	//Can create FilledHexes using default biome values.
	public FilledHex(int q, int r)
	{
		super(q,r);
		biome = new Biome(basic);
	}

	//TODO setup structure storing in hexes, search all for object o.
	public boolean contains(Object o)
	{
		
		return false;
		
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
