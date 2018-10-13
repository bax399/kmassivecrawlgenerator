package model;
import java.awt.Color;
import java.util.Set;
public interface Biome extends BiomeProperties
{
	public String getWeight();
	public String getBiomeName();
	public String getPrintName();
	public Color getColor();
	public int getTravelCost();
	public int getHeight();
	public String getSpotDistance();
	public String getDescription();
	public double getRiverEnd();
	public double getRiverOrigin(); 
	public Biome getConcreteBiome(); //Gets bottom concretebiome
	public Set<Biome> getBiomes();	//Gets all biomes and modifiers from current biome (decorator)
	public Set<String> getStrBiomes(); //Gets all biomes and modifiers as names
	public Set<String> getValidRegionBiomes(); //Gets all valid biomes as a set
}
