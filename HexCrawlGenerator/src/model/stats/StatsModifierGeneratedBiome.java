package model.stats;
import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import model.properties.BiomeModifierGeneratedProperties;
public class StatsModifierGeneratedBiome extends StatsModifierBiome implements BiomeModifierGeneratedProperties
{
	public static StatsModifierGeneratedBiome coastal = new StatsModifierGeneratedBiome("coastal", Color.BLUE,-2,0,"",0d,0d,0.1d,
													"ocean,reef","all");
	public static StatsModifierGeneratedBiome raised = new StatsModifierGeneratedBiome("mountainous",Color.GRAY,2,5,"",0d,0d,0.1d,
													"mountains,hills","all");
	public static StatsModifierGeneratedBiome forested = new StatsModifierGeneratedBiome("forested",Color.GREEN,0,2,"",0d,0d,0.1d,
													"forest","all");
	
	//How are these defined? Functionally these can be input via files instead.
	
	private Set<StatsCoreBiome> validbiomes = new HashSet<>();
	private Set<StatsCoreBiome> originbiomes = new HashSet<>();
	
	private final String vbiomes; //Biomes that can be applied to
	private final String obiomes; //Biomes that can apply
	private final double originchance;

	public StatsModifierGeneratedBiome(String inName, Color inColour, int inHeight, int inTCost, String inSpotDistance, double inRiverOrigin, double inRiverEnd,
										double inOriginChance, String inValidBiomes, String inOtherBiomes)
										
	{
		super(inName,inColour, inHeight, inTCost,  inSpotDistance, inRiverOrigin, inRiverEnd,null);
		originchance =inOriginChance;
		vbiomes= inValidBiomes;
		obiomes= inOtherBiomes;
	}
	
	@Override
	public String getOBiomes() {
		return obiomes;
	}
	
	@Override
	public String getVBiomes() {
		return vbiomes;
	}	
	
	@Override
	public double getOriginChance()
	{
		return originchance;
	}

	@Override
	public Set<StatsCoreBiome> getOriginBiomes()
	{
		return originbiomes;
	}	
	
	@Override
	public Set<StatsCoreBiome> getValidBiomes()
	{
		return validbiomes;
	}
		
}
