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
public class Habitat extends WorldElement implements BiomeProperties
{
	private StatsCoreBiome coreBiome;
	private Set<StatsModifierBiome> modifierBiomes;
	
	public Habitat()
	{
		coreBiome = StatsCoreBiome.basic;
		modifierBiomes = new HashSet<>();
	}
	
	@Override
	public Color getColor() {
		return coreBiome.getColor();
	}

	@Override
	public int getHeight() {
		int height = coreBiome.getHeight();
		for(StatsModifierBiome mb : modifierBiomes)
		{
			height += mb.getHeight();
		}
		
		return height;
	}

	@Override
	public int getTravelCost() {
		int travel = coreBiome.getTravelCost();
		for(StatsModifierBiome mb : modifierBiomes)
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
		for(StatsModifierBiome mb : modifierBiomes)
		{
			riverOrigin += mb.getRiverOrigin();
		}
		
		return riverOrigin;
	}

	@Override
	public double getRiverEnd() {
		double riverEnd = coreBiome.getRiverEnd();
		for(StatsModifierBiome mb : modifierBiomes)
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
		return modifierBiomes;
	}



	public void setModifierBiomes(Set<StatsModifierBiome> modifierBiomes) {
		this.modifierBiomes = modifierBiomes;
	}

	public void addModifierBiome	(StatsModifierBiome modifierBiome)
	{
		this.modifierBiomes.add(modifierBiome);
	}
	
	public Set<StatsCoreBiome> getAllBiomes()
	{
		Set<StatsCoreBiome> allStats = new HashSet<>();
		allStats.addAll(modifierBiomes);
		allStats.add(coreBiome);
		return allStats;
	}
	
	@Override
	public String toString()
	{
		return coreBiome.getBiomeName();
	}
	
	
	
}
