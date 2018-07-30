package model;
import java.awt.Color;
import java.util.*;

//Anything that properties input, the object is expected to use.
public interface DungeonProperties extends HasProperties
{
	public int getVisibility();
	public int getSpawnChance();
	public Map<Monster,Integer> getMonsterChance();
	
}
