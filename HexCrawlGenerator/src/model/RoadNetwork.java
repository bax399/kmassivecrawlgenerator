package model;

import model.worldobjects.*;
import java.util.*;
public class RoadNetwork{

	Set<Town> towns;
	Set<Connection> roads;
	//TODO move some of these functions into a road/connectioncontroller
	
	//check if a crossing of road->river exists at this hex
	public boolean getCrossing(FilledHex origin)
	{
		
		return false;
	}
	
	public int getIntersectionNumber(FilledHex origin)
	{
		//TODO change this to accessing the hex's RoadNodes, and counting its size.
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
	
	public Set<Town> getStructuresInHex(FilledHex origin)
	{
		Iterator<Town> it = towns.iterator();
		Set<Town> townsFound = new HashSet<>();
		while(it.hasNext())
		{
			Town ss = it.next();
			if (origin.contains(ss))
			{
				townsFound.add(ss);
			}
		}
		
		return townsFound;
	}
	
	public Set<Town> getStructuresAlongRoad(FilledHex origin, Connection direction)
	{
		//Follow connection, whenever it splits, send recursive call to follow
		//Any 
		
		//if it only has one, return all towns in the set except the ones in the origin hex.
		return null;
	}
	
	
}
