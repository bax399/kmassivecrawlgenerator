package model;
import java.awt.Color;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import functions.PFunctions;
/**
 * @author Keeley
 *
 */
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
	private final Set<String> validregion;
	
	private final boolean validstart;
	
	
	public BiomeConcrete(Properties pp)
	{
		super(new WorldDescriptor(pp.getProperty("name"), BiomeConcrete.tags, pp.getProperty("name"), 10));
		color = PFunctions.parseColor(pp.getProperty("color"));
		height = Integer.parseInt(pp.getProperty("height"));
		travelcost = Integer.parseInt(pp.getProperty("travelcost")); 
		spotdistance = pp.getProperty("spotdistance");
		riverorigin = Double.parseDouble(pp.getProperty("riverorigin"));
		riverend = Double.parseDouble(pp.getProperty("riverend"));
		biomename=pp.getProperty("name");
		weight=pp.getProperty("weight").toLowerCase();
		validstart=PFunctions.convertToBoolean(pp.getProperty("validstart"));
		validregion = PFunctions.processStringtoSet(pp.getProperty("validregion"));
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
		validregion = PFunctions.processStringtoSet(n);		
		
	}
	
	
	public String getWeight()
	{
		return weight;
	}
	
	public String getPrintName()
	{
		return biomename;
	}
	
	//returns the "true" biome name, the one made at creation. this can't be modified!
	public String getConcreteBiomeName()
	{
		return biomename;
	}
	
	public Color getColor()
	{
		return color;
	}

	public int getTravelCost()
	{
		return Math.max(travelcost,0);
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
	public Biome getConcreteBiome()
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
	
	@Override
	public Set<String> getStrBiomes()
	{
		Set<String> me = new HashSet<>();
		me.add(this.getConcreteBiomeName());
		return me;		
	}
	
	@Override
	public Set<String> getValidRegionBiomes() 
	{
		return validregion;
	} //TODO create valid biomes category.
}
