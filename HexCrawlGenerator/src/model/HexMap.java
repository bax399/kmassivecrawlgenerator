package model;
import model.graphresource.*;

import java.util.*;

public class HexMap 
{
	
	LinkedHashMap<Tuple, Hex> hexes; // Stores coordinates for each hex
	Graph<Hex,Connection> neighbours; // Stores each hex's neighbouring cells in hexagonal coordinates
	Graph<Hex,Road> roads; // Stores road connections
	Graph<Hex,River> rivers; // Stores river connections
	private int height;
	private int width;
	
	public HexMap(int h, int w)
	{
		height = Math.max(1, h);
		width = Math.max(1, w);
	}
	//public LinkedList getNeighbours(Hex origin)
	
	//public Hex moveOneStep(Hex origin, Hex destination)
	//should select between the three directions possible (direct, or 2-step 
	
	public Hex getRelativeHex(Hex origin, Tuple xyz)
	{
		return null;
	}
	
	public Hex getHex(Tuple xyz)
	{
		return null;
	}
	
	//Get next in order, used for generation/looping
	public Hex getNextHex(Hex origin)
	{
		Hex next = null;
		
		return next;
	}
}
