package model;
import java.awt.Color;
import java.awt.Polygon;

import model.redblob.Hex;
public class FilledHex extends Hex 
{
	private Biome biome;
	public Point center;
	public Polygon shape = new Polygon();
	
	public static Biome basic = new Biome("basic",new int[] {0,40,255},0,1,0,0);
	
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
	
	public void setColor(Color c)
	{
		biome.color = c;
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
