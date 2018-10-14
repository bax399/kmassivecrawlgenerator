package model.stats;
import java.awt.Color;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import model.properties.BiomeModifierProperties;
public class StatsModifierBiome extends StatsCoreBiome implements BiomeModifierProperties 
{
	public static final String[] setvalues = {"biomemodifier"};
	public static final Set<String> tags = new HashSet<>(Arrays.asList(setvalues));	
	
	private static String[] rivervalues = {"river"};
	private static Set<String> rivervalidregions = new HashSet<>(Arrays.asList(rivervalues));
	
	
	private static String[] roadvalues = {"road"};
	private static Set<String> roadvalidregions = new HashSet<>(Arrays.asList(roadvalues));
											//modname,color,height,travel,spotd,rivero,rivere 
	public static StatsModifierBiome river = new StatsModifierBiome("river",Color.BLACK,-2,2,"",0d,0.5d,rivervalidregions); //river runs through here
	
	public static StatsModifierBiome road =  new StatsModifierBiome("road",Color.BLUE,0,-2,"",0d,0d,roadvalidregions);
	
	
	public StatsModifierBiome(String inName, Color inColour, int inHeight, int inTCost, String inSpotDistance, double inRiverOrigin, double inRiverEnd,Set<String> inValidRegions)
	
	{
		super(inName,inColour, inHeight, inTCost,  inSpotDistance, inRiverOrigin, inRiverEnd,false,"",inValidRegions);

	}
	
}
