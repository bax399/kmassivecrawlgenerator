package model;
import java.awt.Color;
import java.util.*;

//Anything that properties input, the object is expected to use.
public interface RegionProperties extends HasProperties
{
	public int[] getMinMax();
	public int getMinBiomes();
	public Set<Biome> getValidBiomes();
	
}
