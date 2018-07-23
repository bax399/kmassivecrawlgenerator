package model;
import model.graphresource.*;

import java.util.*;
//Citation: http://keekerdc.com/2011/03/hexagon-grids-coordinate-systems-and-distance-calculations/
//Assuming corner is bottom-left (0,0,0)
public class HexMap 
{
	
	Map<Integer, FilledHex> hexes; // Stores coordinates for each hex
	Layout layout = new Layout(Layout.pointy, new Point(20,20), new Point(100,100));
	Graph<FilledHex,Connection> neighbours; // Stores each hex's neighbouring cells in hexagonal coordinates
	Graph<FilledHex,Road> roads; // Stores road connections
	Graph<FilledHex,River> rivers; // Stores river connections
	
	private int height;
	private int width;
	
	public HexMap(int w, int h)
	{
		hexes = new LinkedHashMap<>();
		
		height = Math.max(1, h);
		width = Math.max(1, w);
		createRectangleMap(height,width);
		System.out.println("done");		
	}
	
    private static int hashCode(FilledHex h)
    {
        return 31 * Arrays.hashCode(new int[]{h.q, h.r});
    }
    
    private static int hashCode(int q, int r)
    {
    	return 31 * Arrays.hashCode(new int[] {q,r});
    }
	
    public void addHex(FilledHex h)
    {
    	hexes.put(hashCode(h), h);
    }
    
    public void addHex(int q, int r)
    {
    	hexes.put(hashCode(q,r), new FilledHex(q,r));
    }
    
    public FilledHex getHex(int q, int r)
    {
    	return hexes.get(hashCode(q,r));
    }
    
    public FilledHex getHex(int hash)
    {
    	return hexes.get(hash);
    }
    
	public int getTotal()
	{
		return hexes.size();
	}
	
	public void createRectangleMap(int map_height, int map_width)
	{
		for (int r = 0; r < map_height; r++) 
		{
		    int r_offset = (int)Math.floor(r/2); // or r>>1
		    for (int q = -r_offset; q < map_width - r_offset; q++) 
		    {
		        //hexes.(Hex(q, r, -q-r));
		        addHex(q,r);
		    }
		}		
	}
	
	public void getPositions()
	{
		Set<Integer> ss = hexes.keySet();
		Iterator<Integer> it = ss.iterator();
		while(it.hasNext())
		{
			FilledHex hh = hexes.get(it.next());
			hh.shape = layout.polygonCorners(hh);
			hh.center = layout.hexToPixel(hh);
		}
	}
	
	@Override
	public String toString()
	{
		String output ="";
		Set<Integer> ss = hexes.keySet();
		Iterator<Integer> it = ss.iterator();
		while(it.hasNext())
		{
			FilledHex hh = hexes.get(it.next());
			output+=hh.toString() + " | ";
		}
		return output;
	}
	
	public Map<Integer,FilledHex> getHexes()
	{
		getPositions();
		return Collections.unmodifiableMap(hexes);
	}
}
