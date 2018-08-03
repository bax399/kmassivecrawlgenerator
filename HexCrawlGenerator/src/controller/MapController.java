package controller;
import java.util.*;
import model.*;
import model.redblob.*;

public class MapController{
	ConnectedHexMap hexmap;
	//size,origin
	Layout layout;// = new Layout(Layout.flat, new Point(20,20), new Point(200,200));	

	public MapController(int h, int w, BiomeChooser bc, Layout lt)
	{
		hexmap = new ConnectedHexMap();
		layout=lt;		
		//createRectangleMap(w,h, bweight);
		initializeRectangleMap(h,w);
		wormWrapper(bc); 		
		getPolygons();
		hexmap.initializeNeighbours();
	}

	//TODO setup observer pattern.
	public void initializeHexes()
	{
		/*
		ArrayList<FilledHex> nullhexes = new ArrayList<>(hexmap.getHexes().values());
		Iterator<FilledHex> it = nullhexes.iterator();
		Random rand = new Random();
		while(it.hasNext())
		{
			notifiyObservers(it.next());
		}
		*/				
	}
	
	public MapController(int r, BiomeChooser bc, Layout lt)
	{
		hexmap = new ConnectedHexMap();
		layout=lt;		
		//createRectangleMap(w,h, bweight);
		initializeSpiralMap(r);
		wormWrapper(bc);
		getPolygons();
		hexmap.initializeNeighbours();
		
		RiverGenerator rg = new RiverGenerator(new Random());

		hexmap.setNetworks(rg.initializeRivers(hexmap));
	}
	
	
	public void getPolygons()
	{
		Map<Tuple,FilledHex> hexes = hexmap.getHexes();
		Set<Tuple> ss = hexes.keySet();
		Iterator<Tuple> it = ss.iterator();
		while(it.hasNext())
		{
			FilledHex hh = hexmap.getHex(it.next());
			
			hh.shape = layout.polygonCorners(hh);
			hh.center = layout.hexToPixel(hh);
		}
	} 
	
	public void initializeRectangleMap(int map_height, int map_width)
	{
		for (int r = 0; r < map_height; r++) 
		{
		    int r_offset = (int)Math.floor(r/2); // or r>>1
		    for (int q = -r_offset; q < map_width - r_offset; q++) 
		    {
		        hexmap.addHex(new FilledHex(q,r));
		    }
		}	

	}

	public void initializeSpiralMap(int map_radius)
	{	
		for (int q = -map_radius; q <= map_radius; q++) 
		{
		    int r1 = Math.max(-map_radius, -q - map_radius);
		    int r2 = Math.min(map_radius, -q + map_radius);
		    for (int r = r1; r <= r2; r++) 
		    {
		        hexmap.addHex(new FilledHex(q, r));
		    }
		}
	}	
	
	//TODO MOVE GENERATION TYPES INTO A NEW CLASS
	public void wormWrapper(BiomeChooser bc)
	{
		ArrayList<FilledHex> nullhexes = new ArrayList<>(hexmap.getHexes().values());
		Iterator<FilledHex> it = nullhexes.iterator();
		Random rand = new Random();
		while(it.hasNext())
		{
			wormStart(it.next(), bc, rand);
		}		
	}
	
	public void wormStart(FilledHex start, BiomeChooser bc, Random rand)
	{
		if (start.getBiome() == null || start.getBiome().getBiomeName().equals("basic"))
		{
			Biome type = bc.rollBiome();
			FilledHex neighb;
			//TODO randomize this choice
			for(int ii = 0; ii < 6; ii++)
			{
				neighb = hexmap.getHex(start.neighbor(ii));
				if (neighb != null && ((neighb.getBiome() != null) && !neighb.getBiome().getBiomeName().equals("basic")))
				{
					type = neighb.getBiome();
					//type = bweight.rollBiome(type); //This rolls from the neighbors biome, sometimes getting something unexpected
				}
			}
			
			//System.out.println("Starting biome: "+type);
			start.setBiome(type);
			iterativeWormThrough(start,bc,type, rand);
		}
	}
	
	public void iterativeWormThrough(FilledHex initial, BiomeChooser bc, Biome type, Random rand)
	{
		FilledHex curr = initial;
		FilledHex next = null, prev=null;
		ArrayList<Integer> dirs = new ArrayList<>(Arrays.asList(0,1,2,3,4,5));
		type = bc.rollBiome(type);
		int dir;
		do
		{
			prev = curr;
			do
			{		
				dir = dirs.get(rand.nextInt(dirs.size()));
				dirs.remove(Integer.valueOf(dir));
				next = hexmap.getHex(curr.neighbor(dir));
			}while((next == null || ( next.getBiome()!=null && !next.getBiome().getBiomeName().equals("basic"))) && dirs.size()>0);			
			
			if(next !=null && (next.getBiome() != null || next.getBiome().getBiomeName().equals("basic")))
			{
				curr = next;
				curr.setBiome(type);
				type = bc.rollBiome(type);
				dirs.clear();
				dirs.addAll(Arrays.asList(0,1,2,3,4,5));
			}
		}while(!curr.equals(prev));
	}
	

	public Hex getHex(int q,int r)
	{
		return hexmap.getHex(q,r);
	}
	
	public Map<Tuple,FilledHex> getHexes()
	{
		return Collections.unmodifiableMap(hexmap.getHexes());
	}
	
	public String printString()
	{
		return hexmap.toString();
	}
}
