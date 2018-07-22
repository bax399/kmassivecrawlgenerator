package model;
import model.graphresource.*;

import java.util.*;
//Citation: http://keekerdc.com/2011/03/hexagon-grids-coordinate-systems-and-distance-calculations/
//Assuming corner is bottom-left (0,0,0)
public class HexMap 
{
	
	LinkedHashMap<Integer, Hex> hexes; // Stores coordinates for each hex
	
	Graph<Hex,Connection> neighbours; // Stores each hex's neighbouring cells in hexagonal coordinates
	Graph<Hex,Road> roads; // Stores road connections
	Graph<Hex,River> rivers; // Stores river connections
	private int height;
	private int width;
	
	public HexMap(int w, int h)
	{
		hexes = new LinkedHashMap<>();
		
		height = Math.max(1, h);
		width = Math.max(1, w);
		
		System.out.println("done");		
	}
	
    private static int hashCode(Hex h)
    {
        return 31 * Arrays.hashCode(new int[]{h.q, h.r});
    }
    
    private static int hashCode(int q, int r)
    {
    	return 31 * Arrays.hashCode(new int[] {q,r});
    }
	
    public void addHex(Hex h)
    {
    	hexes.put(hashCode(h), h);
    }
    
    public void addHex(int q, int r)
    {
    	hexes.put(hashCode(q,r), new Hex(q,r));
    }
    
    public Hex getHex(int q, int r)
    {
    	return hexes.get(hashCode(q,r));
    }
    
	public int getTotal()
	{
		return hexes.size();
	}

	//public LinkedList getNeighbours(Hex origin)
	
	//public Hex moveOneStep(Hex origin, Hex destination)
	//should select between the three directions possible (direct, or 2-step 
	
	public Hex getRelativeHex(Hex origin, int[] xzy)
	{	
		Hex rel = new Hex(xzy[0],xzy[1]);
		//Get the tuple corresponding to the origin hex, adds xyz to it then gets the corresponding hex.
		return hexes.get(new int[] {origin.add(rel).q,origin.add(rel).r});
	}
	
	//Get next in order, used for generation/looping
	//Loops from bottom to top, left to right.
	public Hex getNextHex(Hex origin)
	{
		Hex next=null;
		return next;
	}
	
	
	@Override
	public String toString()
	{
		String output ="a";
		return output;
	}
}
