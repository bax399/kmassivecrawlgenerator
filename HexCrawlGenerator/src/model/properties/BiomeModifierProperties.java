package model.properties;
import java.awt.Color;
//Anything that properties input, the object is expected to use.
public interface BiomeModifierProperties
{
	public Color getColor();
	public int getHeight();
	public int getTravelCost();
	public String getSpotDistance();
	public double getRiverOrigin();
	public double getRiverEnd();	
}
