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
	
	public HexMap(int w, int h)
	{
		hexes = new LinkedHashMap<>();
		tuples = new LinkedHashMap<>();
		height = Math.max(1, h);
		width = Math.max(1, w);
		int shift = 0, count = 0, xxcounter = 0;
		int zz,xx;
		Tuple tt;
		Hex hh;
		for(int yy=0;yy<height;yy++)
		{

			
			for(int ii=0;ii<width-shift;ii++)
			{
				xx = ii+xxcounter;
				zz = -yy-xx;
				tt = new Tuple(xx,yy,zz);
				hh = new Hex(count);
				hexes.put(tt,hh);
				tuples.put(hh,tt);
				count++;				
			}
			
			if (shift==0) 
			{
				shift = 1;
			}
			else if (shift==1)
			{
				shift = 0;
				xxcounter--;
			}			

		}
		
		System.out.println("done");		
	}
	
	public int getTotal()
	{
		return hexes.size();
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
	
	
	@Override
	public String toString()
	{
		String output ="";
		
		Set<Tuple> keys = hexes.keySet();
		Iterator<Tuple> it = keys.iterator();
		while(it.hasNext())
		{
			output+= it.next().toString() +" | ";
		}
		return output;
	}
}
