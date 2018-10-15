package model.worldplaces;
import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import model.properties.BiomeProperties;
import model.stats.StatsCoreBiome;
import model.stats.StatsModifierBiome;


/**
 * @author Keeley
 * This Class stores the stats for core-biome and modifier-biomes for a specific hex. This can retrieve all the relevant information from those two 
 * biome classes.
 */

//TODO allow many of the same modifier to be added? Or support for "adding" a modifier simply increasing the target one by the amount.
public class Habitat extends WorldElement implements BiomeProperties
{
	private StatsCoreBiome coreBiome;
	private Set<StatsModifierBiome> modifierStats;
	//TODO private List<ModifierBiome> modifierBiomes
	public Habitat()
	{
		coreBiome = StatsCoreBiome.basic;
		modifierStats = new HashSet<>();
	}
	
	@Override
	public Color getColor() {
		return coreBiome.getColor();
	}

	@Override
	public int getHeight() {
		int height = coreBiome.getHeight();
		for(StatsModifierBiome mb : modifierStats)
		{
			height += mb.getHeight();
		}
		
		return height;
	}

	@Override
	public int getTravelCost() {
		int travel = coreBiome.getTravelCost();
		for(StatsModifierBiome mb : modifierStats)
		{
			travel += mb.getTravelCost();
		}
		
		return Math.max(travel,1);
	}

	@Override
	public String getSpotDistance() {
		return coreBiome.getSpotDistance();
	}

	@Override
	public double getRiverOrigin() {
		double riverOrigin = coreBiome.getRiverOrigin();
		for(StatsModifierBiome mb : modifierStats)
		{
			riverOrigin += mb.getRiverOrigin();
		}
		
		return riverOrigin;
	}

	@Override
	public double getRiverEnd() {
		double riverEnd = coreBiome.getRiverEnd();
		for(StatsModifierBiome mb : modifierStats)
		{
			riverEnd += mb.getRiverEnd();
		}
		
		return riverEnd;
	}

	@Override
	public String getWeight() {
		return coreBiome.getWeight();
	}

	@Override
	public boolean isValidStart() {
		// TODO Auto-generated method stub
		return coreBiome.isValidStart();
	}

	public StatsCoreBiome getCoreBiome() {
		return coreBiome;
	}

	public void setCoreBiome(StatsCoreBiome coreBiome) {
		this.coreBiome = coreBiome;
	}

	public Set<StatsModifierBiome> getModifierBiomes() {
		return modifierStats;
	}



	public void setModifierBiomes(Set<StatsModifierBiome> modifierBiomes) {
		this.modifierStats = modifierBiomes;
	}

	public void addModifierBiome(StatsModifierBiome modifierBiome)
	{
		this.modifierStats.add(modifierBiome);
	}
	
	public Set<StatsCoreBiome> getAllBiomes()
	{
		Set<StatsCoreBiome> allStats = new HashSet<>();
		allStats.addAll(modifierStats);
		allStats.add(coreBiome);
		return allStats;
	}
	
	@Override
	public String toString()
	{
		return coreBiome.getBiomeName();
	}
	
	
	
}
