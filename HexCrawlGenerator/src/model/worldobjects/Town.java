package model.worldobjects;
import java.awt.Color;

import java.util.*;
import model.FilledHex;
import model.redblob.Point;
import functions.PFunctions;
import model.BiomeConcrete;
import model.WorldDescriptor;
public class Town extends WorldObject implements TownProperties
{
	public static final String[] setvalues = {"town"};
	public static final Set<String> tags = new HashSet<>(Arrays.asList(setvalues));
	
	public static final Character symbol ='U';
	
	private final String vbiomes;
	private final int connectivity; //how much road it can make (size of town)
	private final boolean water; //if the town must generate near water
	private final double spawnchance; //if validbiome, chance to spawn.
	
	private int counter=0; //Used for ensuring we never get too much.
	
	public Town(Properties pp)
	{
		super(new WorldDescriptor(pp.getProperty("name"), Town.tags, pp.getProperty("name"), 10),
				Integer.parseInt(pp.getProperty("visibility")),Integer.parseInt(pp.getProperty("max")));
		connectivity=Integer.parseInt(pp.getProperty("connectivity"));
		vbiomes=pp.getProperty("validbiomes").toLowerCase();
		water=PFunctions.convertToBoolean(pp.getProperty("nearwater"));
		spawnchance=Double.parseDouble(pp.getProperty("spawnchance"));

	}
	
	public Town(String n, int vis, int m, int con, String vb, boolean wa,double sc)
	{
		super(new WorldDescriptor(n, Town.tags, n, 10),vis,m);
		
		connectivity=con;
		vbiomes=vb;
		water=wa;
		spawnchance=sc;
	}
	
	public boolean limitReached()
	{
		return !(counter<getMax());
	}
	
	public HexTown createTown(FilledHex origin, Point randompoint)
	{
		HexTown ht =null;
		if(!limitReached())
		{
			ht=new HexTown(this,origin,randompoint);
			counter++;
		}
		
		return ht;
	}
	
	@Override
	public double getChance()
	{
		return spawnchance;
	}
	
	@Override
	public String getVBiomes() 
	{
		return vbiomes;
	}	
	
	@Override 
	public int getConnectivity()
	{
		return connectivity;
	}
	
	@Override
	public boolean needsWater()
	{
		return water;
	}
}
