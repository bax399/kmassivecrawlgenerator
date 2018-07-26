package controller;
import java.util.*;
import model.*;
import model.redblob.*;

public class MapController{
	HexMap<FilledHex> hexmap;
	BWeight bweights;
	//size,origin
	Layout layout;// = new Layout(Layout.flat, new Point(20,20), new Point(200,200));	

	public MapController(int w, int h, BWeight bweight)
	{
		hexmap = new HexMap<>();
		//createRectangleMap(w,h, bweight);
		createRectangleMap(w,h,bweight);
		getPolygons();
		hexmap.initializeNeighbours();	
	}	

	public MapController(int w, int h, BWeight bweight, Layout lt)
	{
		hexmap = new HexMap<>();
		layout=lt;		
		//createRectangleMap(w,h, bweight);
		initializeRectangleMap(w,h,bweight);
		getPolygons();
		hexmap.initializeNeighbours();
	}

	public MapController(int r, BWeight bweight, Layout lt)
	{
		hexmap = new HexMap<>();
		layout=lt;		
		//createRectangleMap(w,h, bweight);
		createSpiralMap(r,bweight);
		getPolygons();
		hexmap.initializeNeighbours();
	}
	
	
	public void getPolygons()
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
	
	public void initializeRectangleMap(int map_height, int map_width, BWeight bweight)
	{
		for (int r = 0; r < map_height; r++) 
		{
		    int r_offset = (int)Math.floor(r/2); // or r>>1
		    for (int q = -r_offset; q < map_width - r_offset; q++) 
		    {
		        hexmap.addHex(new FilledHex(q,r));
		    }
		}	
		
		ArrayList<Hex> basics = new ArrayList<>(hexmap.getHexes().values());
	
		while(basics.size() > 0)
		{
			wormStart(basics.get(0), basics, bweight);
		}
		
	}
	
	public void wormStart(Hex start, ArrayList<Hex> all, BWeight bweight)
	{
		Biome type = bweight.rollBiome();
		FilledHex neighb;
		for(int ii = 0; ii < 6; ii++)
		{
			neighb = hexmap.getHex(start.neighbor(ii));
			if (neighb != null && !neighb.getName().equals("basic"))
			{
				type = neighb.getBiome();
			}
		}
		all.remove(start);
		hexmap.getHex(start).setBiome(type);
		wormThrough(start,all,bweight,type);
	}
	
	//TODO fix stupid polymorphism, why am I referencing FilledHexes?
	public void wormThrough(Hex prev,ArrayList<Hex> all, BWeight bweight, Biome type)
	{
		//Check nearby hexes for non-null and basic
		
		Random rand = new Random();
		FilledHex next = null;
		ArrayList<Integer> dirs = new ArrayList<>(Arrays.asList(0,1,2,3,4,5));
		Biome nextType = bweight.rollBiome(type);
		int dir;
		do
		{
			dir = rand.nextInt(dirs.size());
			dirs.remove(dir);
			next = hexmap.getHex(prev.neighbor(dir));
		}while((next == null || !next.getName().equals("basic")) && dirs.size()>0);
		
		if((next !=null && next.getName().equals("basic")))
		{
			all.remove(next);
			hexmap.getHex(next).setBiome(nextType);
			wormThrough(next, all, bweight, nextType);
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
