package model;
import java.awt.Color;
import java.util.*;

import functions.PFunctions;
public class BiomeConcrete extends HasDescriptor implements Biome,BiomeProperties{
	public static final String[] setvalues = {"biome"};
	public static final Set<String> tags = new HashSet<>(Arrays.asList(setvalues));
	
	public static BiomeConcrete basic = new BiomeConcrete("basic",new int[] {0,40,255},0,0,"1d0",0,0,false); //default biome.
	
	private final Color color;
	private final int height;
	private final int travelcost;
	private final String spotdistance;
	private final double riverorigin;
	private final double riverend;
	private final String biomename; //the unmodifiable name of the biome, must refer to this, not getName, as that can change.
	private final String weight;
	private final boolean validstart;
	
	public BiomeConcrete(Properties pp)
	{
		super(new WorldDescriptor(pp.getProperty("name"), BiomeConcrete.tags, pp.getProperty("name"), 10));
		color = parseColor(pp.getProperty("color"));
		height = Integer.parseInt(pp.getProperty("height"));
		travelcost = Integer.parseInt(pp.getProperty("travelcost"));
		spotdistance = pp.getProperty("spotdistance");
		riverorigin = Double.parseDouble(pp.getProperty("riverorigin"));
		riverend = Double.parseDouble(pp.getProperty("riverend"));
		biomename=pp.getProperty("name");
		weight=pp.getProperty("weight").toLowerCase();
		validstart=PFunctions.convertToBoolean(pp.getProperty("validstart"));
	}
	
	public BiomeConcrete(String n, int[] c, int h, int tc, String sd, double ro, double re,boolean v)
	{
		super(new WorldDescriptor(n,tags,n,0));
		color = new Color(c[0],c[1],c[2]);
		height = h;
		travelcost = tc;
		riverorigin = ro;
		riverend = re;
		spotdistance = sd;
		biomename=n;
		weight=n+":"+"1";
		validstart=v;
	}
	
	public Color parseColor(String rgb)
	{
		String[] cc = rgb.split(",");
		int[] Irgb = new int[3];
		
		for(int ii=0;ii<3;ii++)
		{
			Irgb[ii]=Integer.parseInt(cc[ii]);
		}
		return new Color(Irgb[0],Irgb[1],Irgb[2]);
	}
	
	public String getWeight()
	{
		return weight;
	}
	
	//returns the "true" biome name, the one made at creation. this can't be modified!
	public String getBiomeName()
	{
		return biomename;
	}
	
	public Color getColor()
	{
		return color;
	}

	public int getTravelCost()
	{
		return travelcost;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public String getSpotDistance()
	{
		return spotdistance;
	}
	
	public double getRiverEnd()
	{
		return riverend;
	}	
	
	public boolean isValidStart()
	{
		return validstart;
	}
	
	public double getRiverOrigin()
	{
		return riverorigin;
	}
	
	@Override
	public String toString()
	{
		return super.getName();
	}
	
	@Override
	public Biome getBiome()
	{
		return this;
	}
	
	@Override 
	public Set<Biome> getBiomes()
	{
		Set<Biome> me = new HashSet<>();
		me.add(this);
		return me;
	}
}
