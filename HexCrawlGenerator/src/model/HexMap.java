package model;
import model.graphresource.*;

import java.util.*;

//Citation: http://keekerdc.com/2011/03/hexagon-grids-coordinate-systems-and-distance-calculations/
//Assuming corner is bottom-left (0,0,0)
public class HexMap 
{
	
	LinkedHashMap<Tuple, Hex> hexes; // Stores coordinates for each hex
	LinkedHashMap<Hex,Tuple> tuples; // Stores hexes for each coordinate
	
	Graph<Hex,Connection> neighbours; // Stores each hex's neighbouring cells in hexagonal coordinates
	Graph<Hex,Road> roads; // Stores road connections
	Graph<Hex,River> rivers; // Stores river connections
	private int height;
	private int width;
	
	public HexMap(int h, int w)
	{
		height = Math.max(1, h);
		width = Math.max(1, w);
		int shift = 0, count = 0;
		int zz;
		for(int yy=0;yy<width-shift;yy++)
		{
			for(int xx=0;xx<width-shift;xx++)
			{
				zz = -yy-xx;
				hexes.put(new Tuple(xx,yy,zz),new Hex(count));
			}
			
			if (shift==1) {shift =0;}
			if (shift==0) {shift = 1;}
			count++;
		}
	}

	//public LinkedList getNeighbours(Hex origin)
	
	//public Hex moveOneStep(Hex origin, Hex destination)
	//should select between the three directions possible (direct, or 2-step 
	
	public Hex getRelativeHex(Hex origin, Tuple xyz)
	{	
		//Get the tuple corresponding to the origin hex, adds xyz to it then gets the corresponding hex.
		return hexes.get(tuples.get(origin).add(xyz));
	}
	
	public Hex getHex(Tuple xyz)
	{
		return hexes.get(xyz);
	}
	
	//Get next in order, used for generation/looping
	//Loops from bottom to top, left to right.
	public Hex getNextHex(Hex origin)
	{
		Hex next, curr=null;
		
		next = getRelativeHex(origin, new Tuple(1,0,-1));
		
		if (next.equals(null))
		{
			next = getRelativeHex(origin, new Tuple(-1,1,0));
			while(!next.equals(null))
			{
				curr = next;
				next = getRelativeHex(next, new Tuple(-1,0,1));
			}
			next = curr;
		}
		
		return next;
	}
}
