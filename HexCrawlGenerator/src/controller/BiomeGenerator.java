package controller;
import java.util.ArrayList;
import static functions.PFunctions.outputString;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import model.Biome;
import model.BiomeChooser;
import model.ConnectedHexMap;
import model.FilledHex;
public class BiomeGenerator extends Generator
{
	ConnectedHexMap hexmap;
	BiomeChooser bchoose;
	ArrayList<Biome> biomes;
	
	public BiomeGenerator(ConnectedHexMap chm, ArrayList<Properties> bprops, Random r)
	{
		super(r);
		PropertiesFactory pf = new PropertiesFactory();
		biomes = pf.processProperties(bprops);
		bchoose = new BiomeChooser(biomes,r);
		hexmap=chm;
		
	}
	
	public ArrayList<Biome> getBiomeList()
	{
		return biomes;
	}
	
	public Map<String,Biome> getBiomeMap()
	{
		return bchoose.getBMap();
	}
	
	public void frontierWrapper()
	{
		FilledHex center = hexmap.getHex(0,0);
		frontierGenerate(center,biomes.get(0));
		
	}

	public void frontierGenerate(FilledHex startHex, Biome startBiome)
	{
		Map<FilledHex, Biome> came_from = new HashMap<>();
		List<FilledHex> frontier = new ArrayList<>();
		Set<FilledHex> visited = new HashSet<>();
		
		FilledHex currHex, nextHex;
		Biome currBiome=startBiome;
		
		frontier.add(startHex);
		came_from.put(startHex, startBiome);
		visited.add(startHex);
		
		while(!frontier.isEmpty())
		{
			int randomindex =rand.nextInt(frontier.size());
			
			currHex = frontier.get(randomindex);
			frontier.remove(randomindex);			
			currBiome = bchoose.rollBiome(came_from.get(currHex));
			currHex.setBiome(currBiome);
			
			int dir;
			ArrayList<Integer> dirs = new ArrayList<>(Arrays.asList(0,1,2,3,4,5));
			do
			{
				dir = dirs.get(rand.nextInt(dirs.size()));
				dirs.remove(Integer.valueOf(dir));
				nextHex=hexmap.getHex(currHex.neighbor(dir));
				if ( (nextHex!=null) && (!visited.contains(nextHex)) )
				{
					frontier.add(nextHex);
					came_from.put(nextHex, currHex.getBiome());
					visited.add(nextHex);
				}
			}
			while(dirs.size()>0);			
			
		}
		
	}	
	
	//Noise generators? 
	//Voronoi polygons?

	public void wormWrapper()
	{
		ArrayList<FilledHex> nullhexes = new ArrayList<>(hexmap.getHexes().values());
		Iterator<FilledHex> it = nullhexes.iterator();
		while(it.hasNext())
		{
			wormStart(it.next());
		}		
	}
	
	private void wormStart(FilledHex start)
	{
		//Need to find a nearby hex to pull its biome from.
		if (start.getBiome() == null || start.getBiome().getBiomeName().equals("basic"))
		{
			Biome type = bchoose.rollBiome();
			FilledHex neighb;
			int dir;
			boolean done = false;
			ArrayList<Integer> dirs = new ArrayList<>(Arrays.asList(0,1,2,3,4,5));
			do
			{
				dir = dirs.get(rand.nextInt(dirs.size()));
				dirs.remove(Integer.valueOf(dir));
				neighb = hexmap.getHex(start.neighbor(dir));
				if (neighb != null && ((neighb.getBiome() != null) && !neighb.getBiome().getBiomeName().equals("basic")))
				{
					type = neighb.getBiome();
					done=true;
					//type = bc.rollBiome(neighb.getBiome()); //This rolls from the neighbors biome, sometimes getting something unexpected
				}
			}
			while(dirs.size()>0 && !done);
			start.setBiome(type);
			iterativeWormGenerate(start,type);
		}
	}
	
	private void iterativeWormGenerate(FilledHex initial,Biome type)
	{
		FilledHex curr = initial;
		FilledHex next = null, prev=null;
		ArrayList<Integer> dirs = new ArrayList<>(Arrays.asList(0,1,2,3,4,5));
		type = bchoose.rollBiome(type);
		int dir;
		do
		{
			prev = curr;
			//Choose a valid random direction
			do
			{		
				dir = dirs.get(rand.nextInt(dirs.size()));
				dirs.remove(Integer.valueOf(dir));
				next = hexmap.getHex(curr.neighbor(dir));
			}while(dirs.size()>0 && (next == null || ( next.getBiome()!=null && !next.getBiome().getBiomeName().equals("basic"))));			
			
			//if next is empty, replace.
			if(next !=null && (next.getBiome() != null && next.getBiome().getBiomeName().equals("basic")))
			{
				curr = next;
				curr.setBiome(type);
				type = bchoose.rollBiome(type);
				dirs.clear();
				dirs.addAll(Arrays.asList(0,1,2,3,4,5));
				int remove_straight = dir+3;
				if(remove_straight >5)
				{
					remove_straight-=6;
				}
				dirs.remove(Integer.valueOf(remove_straight));	
				dirs.remove(Integer.valueOf(dir));			
			}
		}while(!curr.equals(prev)); //Only happens if we get entirely through the empty loop.
	}
}


//Deprecated biome generators
/*

				int remove_straight = dir+3;

				if(remove_straight >5)
				{
					remove_straight-=6;
				}
				outputString(this, remove_straight + " from " + dir);

				dirs.remove(remove_straight);	

//Not recommended, creates straight line generation due to nature of queue...

*/