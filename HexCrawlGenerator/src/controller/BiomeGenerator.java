package controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import model.BiomeChooser;
import model.ConnectedHexMap;
import model.FilledHex;
import model.stats.StatsCoreBiome;

public class BiomeGenerator extends Generator
{
	ConnectedHexMap hexmap;
	BiomeChooser bchoose;
	List<StatsCoreBiome> biomes;
	
	public BiomeGenerator(ConnectedHexMap chm, List<StatsCoreBiome> bprops, Random r)
	{
		super(r);
		biomes = bprops;
		bchoose = new BiomeChooser(biomes,r);
		hexmap=chm;
		
	}
	
	public List<StatsCoreBiome> getBiomeList()
	{
		return biomes;
	}
	
	public Map<String,StatsCoreBiome> getBiomeMap()
	{
		return bchoose.getBMap();
	}
	
	public void frontierWrapper()
	{
		FilledHex center = hexmap.getHex(0,0);
		frontierGenerate(center,biomes.get(0));
		
	}

	public void frontierGenerate(FilledHex startHex, StatsCoreBiome startBiome)
	{
		Map<FilledHex, StatsCoreBiome> came_from = new HashMap<>();
		List<FilledHex> frontier = new ArrayList<>();
		Set<FilledHex> visited = new HashSet<>();
		
		FilledHex currHex, nextHex;
		StatsCoreBiome currBiome=startBiome;
		
		frontier.add(startHex);
		came_from.put(startHex, startBiome);
		visited.add(startHex); 
		
		while(!frontier.isEmpty())
		{
			int randomindex =rand.nextInt(frontier.size());
			
			currHex = frontier.get(randomindex);
			frontier.remove(randomindex);			
			currBiome = bchoose.rollBiome(came_from.get(currHex));
			currHex.getHabitat().setCoreBiome(currBiome);
			
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
					came_from.put(nextHex, currHex.getHabitat().getCoreBiome());
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
		if (start.getHabitat().getCoreBiome() == null || start.getHabitat().getCoreBiome().getBiomeName().equals("basic"))
		{
			StatsCoreBiome type = bchoose.rollBiome();
			FilledHex neighb;
			int dir;
			boolean done = false;
			ArrayList<Integer> dirs = new ArrayList<>(Arrays.asList(0,1,2,3,4,5));
			do
			{
				dir = dirs.get(rand.nextInt(dirs.size()));
				dirs.remove(Integer.valueOf(dir));
				neighb = hexmap.getHex(start.neighbor(dir));
				if (neighb != null && ((neighb.getHabitat().getCoreBiome() != null) && !neighb.getHabitat().getCoreBiome().getBiomeName().equals("basic")))
				{
					type = neighb.getHabitat().getCoreBiome();
					done=true;
					//type = bc.rollBiome(neighb.getBiome()); //This rolls from the neighbors biome, sometimes getting something unexpected
				}
			}
			while(dirs.size()>0 && !done);
			start.getHabitat().setCoreBiome(type);
			iterativeWormGenerate(start,type);
		}
	}
	
	private void iterativeWormGenerate(FilledHex initial,StatsCoreBiome type)
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
			}while(dirs.size()>0 && (next == null || ( next.getHabitat().getCoreBiome()!=null && !next.getHabitat().getCoreBiome().getBiomeName().equals("basic"))));			
			
			//if next is empty, replace.
			if(next !=null && (next.getHabitat().getCoreBiome() != null && next.getHabitat().getCoreBiome().getBiomeName().equals("basic")))
			{
				curr = next;
				curr.getHabitat().setCoreBiome(type);
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