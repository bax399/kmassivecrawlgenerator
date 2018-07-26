package controller;
import java.util.*;

import model.*;
import model.redblob.Layout;
import model.redblob.Point;
public class MapController{
	HexMap<FilledHex> hexmap;
	BWeight bweights;
	//size,origin
	Layout layout = new Layout(Layout.flat, new Point(20,20), new Point(200,200));	

	public MapController(int w, int h, BWeight bweight)
	{
		hexmap = new HexMap<>();
		//createRectangleMap(w,h, bweight);
		createSpiralMap(w,bweight);
		getPositions();
		hexmap.initializeNeighbours();	
	}	
	
	public MapController(int w, int h, BWeight bweight, Layout lt)
	{
		hexmap = new HexMap<>();
		//createRectangleMap(w,h, bweight);
		createSpiralMap(w,bweight);
		getPositions();
		hexmap.initializeNeighbours();
		layout=lt;
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
	
	public void createRectangleMap(int map_height, int map_width, BWeight bweight)
	{
		Biome type;
    	type = bweight.rollBiome();		
		for (int r = 0; r < map_height; r++) 
		{
		    int r_offset = (int)Math.floor(r/2); // or r>>1
		    for (int q = -r_offset; q < map_width - r_offset; q++) 
		    {
		        //hexes.(Hex(q, r, -q-r));
		    	type = bweight.rollBiome(type);
		        hexmap.addHex(new FilledHex(type,q,r));
		    }
		}		
	}	
	
	public void createSpiralMap(int map_radius, BWeight bweight)
	{
		Biome type;
    	type = bweight.rollBiome();		
		for (int q = -map_radius; q <= map_radius; q++) 
		{
		    int r1 = Math.max(-map_radius, -q - map_radius);
		    int r2 = Math.min(map_radius, -q + map_radius);
		    for (int r = r1; r <= r2; r++) 
		    {
		    	type = bweight.rollBiome(type);
		        hexmap.addHex(new FilledHex(type,q, r));
		    }
		}
	}
	
	/*public void createRectangleMap(int map_height, int map_width)
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
	}*/
	
	
	
	public Map<Integer,FilledHex> getHexes()
	{
		return Collections.unmodifiableMap(hexmap.getHexes());
	}
	
	public String printString()
	{
		return hexmap.toString();
	}
}
