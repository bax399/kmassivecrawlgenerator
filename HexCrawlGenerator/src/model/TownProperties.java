package model;
import java.awt.Color;
import java.util.*;

//Anything that properties input, the object is expected to use.
public interface TownProperties extends HasProperties
{
	public int getVisibility();
	public int getSpawnChance();
	public int getConnectivity();
}
