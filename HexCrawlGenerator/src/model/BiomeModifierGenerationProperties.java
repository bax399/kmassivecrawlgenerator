package model;
import java.awt.Color;
import java.util.*;
//Anything that properties input, the object is expected to use.
public interface BiomeModifierGenerationProperties extends HasProperties
{
	public String getVBiomes();	
	public String getOBiomes();
	public double getOriginChance();
	public Set<Biome> getValidBiomes();
	public Set<Biome> getOriginBiomes();
}
