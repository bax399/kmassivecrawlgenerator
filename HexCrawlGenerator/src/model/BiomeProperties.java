package model;
import java.awt.Color;

//Anything that properties input, the object is expected to use.
public interface BiomeProperties extends BasicProperties
{
	public Color getColor();
	public int getHeight();
	public int getTravelCost();
	public String getSpotDistance();
	public double getRiverOrigin();
	public double getRiverEnd();
	public String getWeight();
	public boolean isValidStart();
}
