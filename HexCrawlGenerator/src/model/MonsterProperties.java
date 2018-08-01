package model;
import java.awt.Color;
import java.util.*;

//Anything that properties input, the object is expected to use.
public interface MonsterProperties extends HasProperties
{
	public int getVisibility();
	public double getNomadChance();
	public int getRoamRadius();
	public Map<Biome,Double> getSpawnChance();
	public int getRoamType();
	public double getEncounterChance();
}
