package model;
import java.awt.Color;
import java.util.Set;
public interface Biome extends BiomeProperties
{
	public Color parseColor(String rgb);
	public String getWeight();
	public String getBiomeName();
	public Color getColor();
	public int getTravelCost();
	public int getHeight();
	public String getSpotDistance();
	public String getDescription();
	public double getRiverEnd();
	public double getRiverOrigin();
	public Biome getBiome();
	public Set<Biome> getBiomes();
}
