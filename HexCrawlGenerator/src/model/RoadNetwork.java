package model;

import model.worldobjects.*;
import java.util.*;
public class RoadNetwork{

	Set<Structure> structures;
	Set<Connection> roads;
	
	public int getIntersectionNumber(FilledHex origin)
	{
		//return number of connections with this hex
		int count=0;
		Iterator<Connection> it = roads.iterator();
		while(it.hasNext())
		{
			if (it.next().containsHex(origin))
			{
				count++;
			}
		}
		return count;
	}
	
	public Set<Structure> getStructuresInHex(FilledHex origin)
	{
		Iterator<Structure> it = structures.iterator();
		Set<Structure> structuresFound = new HashSet<>();
		while(it.hasNext())
		{
			Structure ss = it.next();
			if (origin.contains(ss))
			{
				structuresFound.add(ss);
			}
		}
		
		return structuresFound;
	}
	
	public Set<Structure> getStructuresAlongRoad(FilledHex origin, Connection direction)
	{
		//Follow connection, whenever it splits, send recursive call to follow
		//Any 
		
		//if it only has one, return all towns in the set except the ones in the origin hex.
		return null;
	}
	
	
}
