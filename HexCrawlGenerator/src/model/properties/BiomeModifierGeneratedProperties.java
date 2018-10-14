package model.properties;
import java.util.Set;
//Anything that properties input, the object is expected to use.
public interface BiomeModifierGeneratedProperties
{
	public String getVBiomes();	
	public String getOBiomes();
	public double getOriginChance();
	public Set<BaseBiome> getValidBiomes();
	public Set<BaseBiome> getOriginBiomes();
}
