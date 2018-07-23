package controller;
import java.util.*;

import model.*;
public class MapController{
	HexMap<FilledHex> hexmap;
	Layout layout = new Layout(Layout.pointy, new Point(20,20), new Point(100,100));	
	
	public MapController(int w, int h)
	{
		hexmap = new HexMap<>();
		createRectangleMap(w,h);
		getPositions();
		hexmap.initializeNeighbours();
	}
	
	public void getPositions()
	{
		Map<Integer,FilledHex> hexes = hexmap.getHexes();
		Set<Integer> ss = hexes.keySet();
		Iterator<Integer> it = ss.iterator();
		while(it.hasNext())
		{
			FilledHex hh = hexes.get(it.next());
			hh.shape = layout.polygonCorners(hh);
			hh.center = layout.hexToPixel(hh);
		}
	}
	
	public void createRectangleMap(int map_height, int map_width)
	{
		for (int r = 0; r < map_height; r++) 
		{
		    int r_offset = (int)Math.floor(r/2); // or r>>1
		    for (int q = -r_offset; q < map_width - r_offset; q++) 
		    {
		        //hexes.(Hex(q, r, -q-r));
		        hexmap.addHex(new FilledHex(q,r));
		    }
		}		
	}
	
	public Map<Integer,FilledHex> getHexes()
	{
		return Collections.unmodifiableMap(hexmap.getHexes());
	}
	
	public String printString()
	{
		return hexmap.toString();
	}
}
