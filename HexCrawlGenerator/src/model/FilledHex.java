package model;
import java.util.*;
import java.awt.Color;
import java.awt.Polygon;
public class FilledHex extends Hex 
{
	private Biome biome;
	public Polygon shape = new Polygon();
	
	public static Biome basic = new Biome("basic",new int[] {255,40,0},0,1,0,0);
	
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
		biome = basic;
	}
	
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
}
