package controller;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import functions.KFunctions;
import model.ConnectedHexMap;
import model.FilledHex;
import model.redblob.Hex;
import model.redblob.Layout;
import model.redblob.Tuple;
public class MapController
{
	PropertiesReader pstorage;
	ConnectedHexMap hexmap;
	//size,origin
	Layout layout;// = new Layout(Layout.flat, new Point(20,20), new Point(200,200));	

	public MapController(int h, int w,PropertiesController pc , Layout lt,Random rand)
	{

		hexmap = new ConnectedHexMap(w,h,lt,rand);
		layout=lt;		
		//createRectangleMap(w,h, bweight);
		initializeRectangleMap(h,w);
		
		initializeMapGenerators(pc,rand);

	}

	public MapController(int r,PropertiesController pc, Layout lt, Random rand)
	{
		hexmap = new ConnectedHexMap(r,r,lt,rand);
		layout=lt;		
		//createRectangleMap(w,h, bweight);
		initializeSpiralMap(r);
		
		initializeMapGenerators(pc,rand);
	}
	
	public void initializeMapGenerators(PropertiesController pc,Random rand)
	{
		getPolygons();		
		
		BiomeGenerator bg = new BiomeGenerator(hexmap,pc.getBiomeList(),rand);
		bg.wormWrapper();
		hexmap.initializeNeighbours();
		KFunctions.outputString(this,"Finished Generating");
		
		RegionGenerator reg = new RegionGenerator(hexmap,rand);
		hexmap.setRegions(reg.initializeSameRegions());		
		KFunctions.outputString(this,"Finished Regions");
		
		RiverGenerator rg = new RiverGenerator(hexmap,rand);
		hexmap.setRiverNetworks(rg.generateRivers());
		KFunctions.outputString(this,"Finished Rivers");
		
		TownGenerator tg = new TownGenerator(hexmap,pc.getTownList(),bg.getBiomeMap(),rand);
		hexmap.setTowns(tg.generateTowns());
		KFunctions.outputString(this,"Finished Towns");
		
		RoadGenerator rag = new RoadGenerator(hexmap,rand);
		hexmap.setRoadNetworks(rag.generateRoads());
		KFunctions.outputString(this,"Finished Roads");				
	}
		
	
	public void getPolygons()
	{
		Map<Tuple,FilledHex> hexes = hexmap.getHexes();
		Set<Tuple> ss = hexes.keySet();
		Iterator<Tuple> it = ss.iterator();
		while(it.hasNext())
		{
			FilledHex hh = hexmap.getHex(it.next());
			
			hh.setShape(layout.polygonCorners(hh));
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
	
	public ConnectedHexMap getMap()
	{
		return hexmap;
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
