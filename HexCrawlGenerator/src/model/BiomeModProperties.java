package model;
import java.awt.Color;
import java.util.*;
//Anything that properties input, the object is expected to use.
public interface BiomeModProperties extends HasProperties
{
	public Color getColor();
	public int getHeight();
	public int getTravelCost();
	public String getSpotDistance();
	public int getRiverOrigin();
	public int getRiverEnd();
	public Set<Biome> getValidBiomes();
	public Map<Biome,Integer> getOriginBiomesChance();
}
