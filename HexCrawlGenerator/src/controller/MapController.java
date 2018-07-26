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
		System.out.println("worming");
		for (int r = 0; r < map_height; r++) 
		{
		    int r_offset = (int)Math.floor(r/2); // or r>>1
		    for (int q = -r_offset; q < map_width - r_offset; q++) 
		    {
		        hexmap.addHex(new FilledHex(q,r));
		    }
		}	
		
		ArrayList<Hex> all = new ArrayList<Hex>(hexmap.getHexes().values());
		while(all.size()>0)
		{
			wormThrough(all.get(0).q,all.get(0).r,all,bweight);
		}
		System.out.println("finish worming");
	}
	
	
	//TODO fix stupid polymorphism, why am I referencing FilledHexes?
	public void wormThrough(int qstart, int rstart,ArrayList<Hex> all, BWeight bweight)
	{
		int dir;
		Random rand = new Random();
		FilledHex curr;
		Hex test;
		ArrayList<Integer> dirRem = new ArrayList<>(Arrays.asList(0,1,2,3,4,5));
		Biome type = bweight.rollBiome(); //TODO change this to nearby hex
		
		
		curr = hexmap.getHex(qstart,rstart);
		dir = rand.nextInt(dirRem.size());
		test = curr.add(Hex.directions.get(dir));
		while((hexmap.getHex(test.q,test.r) == null || !hexmap.getHex(test.q,test.r).getName().equals("basic")) && dirRem.size() > 1)
		{
			dirRem.remove(dir);
			dir = rand.nextInt(dirRem.size());
			test = curr.add(Hex.directions.get(dir));
		}
		
		if((hexmap.getHex(test.q,test.r)!=null) && (hexmap.getHex(test.q,test.r).getName().equals("basic")))
		{
			type = bweight.rollBiome(type);
			hexmap.getHex(test.q,test.r).setBiome(type);
			all.remove(new Hex(test.q,test.r));
			dirRem.clear();
			dirRem.addAll(Arrays.asList(0,1,2,3,4,5));
		}
		else {/*End Worm, no where to go.*/}

		
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
