package model;
import java.awt.Color;
import java.util.*;

//Anything that properties input, the object is expected to use.
public interface MonsterProperties extends HasProperties
{
	public int getVisibility();
	public int getNomadChance();
	public int getRoamRadius();
	public int getSpawnChance();
	public int getRoamType();
	public int getEncounterChance();
}
