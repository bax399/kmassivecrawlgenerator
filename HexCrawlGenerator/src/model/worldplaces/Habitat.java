package model.worldplaces;
import java.util.HashSet;
import java.util.Set;

import model.properties.BaseBiome;
import model.stats.StatsCoreBiome;
import model.stats.StatsModifierBiome;


/**
 * @author Keeley
 * This Class stores the stats for core-biome and modifier-biomes for a specific hex. This can retrieve all the relevant information from those two 
 * biome classes.
 */
public class Habitat extends WorldElement
{
	private StatsCoreBiome coreStats;
	private Set<StatsModifierBiome> allModifierStats;
	
	public Habitat()
	{
		coreStats = StatsCoreBiome.basic;
		allModifierStats = new HashSet<>();
	}
	
	public StatsCoreBiome getCoreStats()
	{
		return coreStats;
	}
	
	public void setCoreStats(StatsCoreBiome coreStats)
	{
		this.coreStats = coreStats;
	}
	
	public Set<StatsModifierBiome> getModifierStats() 
	{
		return allModifierStats;
	}
	
	public void setModifierStats(Set<StatsModifierBiome> modifierStats) 
	{
		this.allModifierStats = modifierStats;
	}
	
	public void addModifierStats(StatsModifierBiome modifier)
	{
		this.allModifierStats.add(modifier);
	}
	
	public Set<BaseBiome> getAllStats()
	{
		Set<BaseBiome> all = allModifierStats;
		
	}
	
}
