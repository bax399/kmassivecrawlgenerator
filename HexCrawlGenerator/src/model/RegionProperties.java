package model;
import java.util.Set;

//Anything that properties input, the object is expected to use.
public interface RegionProperties extends HasProperties
{
	public int[] getMinMax();
	public int getMinBiomes();
	public Set<Biome> getValidBiomes();
	
}
