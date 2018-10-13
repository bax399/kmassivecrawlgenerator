package model;
import java.util.Map;

import model.worldobjects.Monster;

//Anything that properties input, the object is expected to use.
public interface DungeonProperties extends BasicProperties
{
	public int getVisibility();
	public int getSpawnChance();
	public Map<Monster,Integer> getMonsterChance();
	
}
