package model;
import java.awt.Color;
import java.util.*;

//Anything that properties input, the object is expected to use.
public interface BiomeProperties extends HasProperties
{
	public Color getColor();
	public int getHeight();
	public int getTravelCost();
	public String getSpotDistance();
	public double getRiverOrigin();
	public double getRiverEnd();
	public String getWeight();
	public int getValidStart();
}
