package controller;
import java.util.*;

import model.*;
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
			iterativeWormThrough(start,type);
		}
	}
	
	private void iterativeWormThrough(FilledHex initial,Biome type)
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
			}while((next == null || ( next.getBiome()!=null && !next.getBiome().getBiomeName().equals("basic"))) && dirs.size()>0);			
			
			//if next is empty, replace.
			if(next !=null && (next.getBiome() != null && next.getBiome().getBiomeName().equals("basic")))
			{
				curr = next;
				curr.setBiome(type);
				type = bchoose.rollBiome(type);
				dirs.clear();
				dirs.addAll(Arrays.asList(0,1,2,3,4,5));
			}
		}while(!curr.equals(prev)); //Only happens if we get entirely through the empty loop.
	}
}
