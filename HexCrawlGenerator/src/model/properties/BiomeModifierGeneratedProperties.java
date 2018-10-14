package model.properties;
import java.util.Set;

import model.stats.StatsCoreBiome;
//Anything that properties input, the object is expected to use.
public interface BiomeModifierGeneratedProperties
{
	public String getVBiomes();	
	public String getOBiomes();
	public double getOriginChance();
	public Set<StatsCoreBiome> getValidBiomes();
	public Set<StatsCoreBiome> getOriginBiomes();
}
