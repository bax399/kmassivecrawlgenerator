package model.stats;
import java.awt.Color;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import functions.PFunctions;
import model.properties.BiomeProperties;
import zdeprececated.BaseBiome;
/**
 * @author Keeley
 *
 */
public class StatsCoreBiome extends PropertyStats implements BiomeProperties{
	public static final String[] setvalues = {"biome"};
	public static final Set<String> tags = new HashSet<>(Arrays.asList(setvalues));
	
	public static StatsCoreBiome basic = new StatsCoreBiome("basic",Color.BLUE,0,0,"1d0",0,0,false,"basic:1",tags); //default biome.
	
	private final Color color;
	private final int height;
	private final int travelCost;
	private final String spotDistance;
	private final double riverOrigin;
	private final double riverEnd;
	private final String biomeName; //the unmodifiable name of the biome, must refer to this, not getName, as that can change.
	private final String weight;
	private final Set<String> validRegion;
	
	private final boolean validstart;
	
	public StatsCoreBiome(Properties pp)
	{
		color = PFunctions.parseColor(pp.getProperty("color"));
		height = Integer.parseInt(pp.getProperty("height"));
		travelCost = Integer.parseInt(pp.getProperty("travelcost")); 
		spotDistance = pp.getProperty("spotdistance");
		riverOrigin = Double.parseDouble(pp.getProperty("riverorigin"));
		riverEnd = Double.parseDouble(pp.getProperty("riverend"));
		biomeName=pp.getProperty("name");
		weight=pp.getProperty("weight").toLowerCase();
		validstart=PFunctions.convertToBoolean(pp.getProperty("validstart"));
		validRegion = PFunctions.processCSVtoSet(pp.getProperty("validregion"));
	}
	
	public StatsCoreBiome(String inName, Color inColour, int inHeight, int inTCost, String inSpotDistance, double inRiverOrigin, double inRiverEnd, boolean inValidStart,
						String inWeight, Set<String> inValidRegions)
	{	
		color = inColour;
		height = inHeight;
		travelCost = inTCost;
		riverOrigin = inRiverOrigin;
		riverEnd = inRiverEnd;
		spotDistance = inSpotDistance;
		biomeName=inName;
		weight=inWeight;
		validstart=inValidStart;
		validRegion = inValidRegions;		
		
	}
	
	public String getWeight()
	{
		return weight;
	}
	
	public String getPrintName()
	{
		return biomeName;
	}
	
	//returns the "true" biome name, the one made at creation. this can't be modified!
	public String getBiomeName()
	{
		return biomeName;
	}
	
	public Color getColor()
	{
		return color;
	}

	public int getTravelCost()
	{
		return travelCost;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public String getSpotDistance()
	{
		return spotDistance;
	}
	
	public double getRiverEnd()
	{
		return riverEnd;
	}	
	
	public boolean isValidStart()
	{
		return validstart;
	}
	
	public double getRiverOrigin()
	{
		return riverOrigin;
	}
	
	public Set<String> getValidRegionBiomes()
	{
		return validRegion;
	}
	
	@Override
	public String toString()
	{
		return getPrintName();
	}
	
}
