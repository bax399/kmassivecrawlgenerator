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
		
		Set<Integer> hexCoords = new HashSet<>(hexmap.getHexes().keySet());
		Iterator<Integer> it = hexCoords.iterator();
		while(it.hasNext())
		{
			wormStart(it.next(), bweight);
		}
	}

	public void wormStart(Integer start, BWeight bweight)
	{
		Biome type = bweight.rollBiome();
		FilledHex neighb, curr;
		curr = hexmap.getHex(start);
		boolean done = false;
				
		//If it is a valid hex in the map that has no biome OR a basic biome, start worming.
		if (curr != null && (curr.getBiome() == null || curr.getName().equals("basic")))
		{
			//Try to find an existing neighbor with a biome.
			ArrayList<Integer> dirs = new ArrayList<>(Arrays.asList(0,1,2,3,4,5));
			Random rand = new Random();
			int dir;
			do
			{
				dir = rand.nextInt(dirs.size());
				dirs.remove(Integer.valueOf(dir));
				neighb = hexmap.getHex(hexmap.getHex(start).neighbor(dir));
				if (hexmap.containsHex(neighb) && neighb.getBiome() != null && !neighb.getName().equals("basic"))
				{
					type = neighb.getBiome();
					type = bweight.rollBiome(type);
					done=true;
				}
			}
			while(dirs.size() > 0 && !done );
			
			//Regardless of if it found a neighbouring biome table, start the worming.
			curr.setBiome(type);	
			wormThrough(curr,bweight,type);	
						
		}
	}
	
	//TODO basic still appears rarely
	public void wormThrough(Hex prev, BWeight bweight, Biome type)
	{
		//Check nearby hexes for non-null and basic

		Random rand = new Random();
		FilledHex next = null;
		ArrayList<Integer> dirs = new ArrayList<>(Arrays.asList(0,1,2,3,4,5));
		type = bweight.rollBiome(type);
		int dir;
		do
		{
			dir = rand.nextInt(dirs.size());
			dirs.remove(Integer.valueOf(dir));
			next = hexmap.getHex(prev.neighbor(dir));	
		}while(next==null || !hexmap.containsHex(next) || (next.getBiome() != null && !next.getName().equals("basic")));
		

		if(next !=null)
		{					
			System.out.println(dir);
			System.out.println(dirs);
			hexmap.getHex(next).setBiome(type);
			wormThrough(next, bweight, type);
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
