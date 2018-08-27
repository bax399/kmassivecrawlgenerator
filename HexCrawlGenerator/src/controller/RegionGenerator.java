package controller;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import functions.PFunctions;
import model.ConnectedHexMap;
import model.FilledHex;
import model.HexRegion;
import model.Region;
public class RegionGenerator extends Generator 
{
	public static int min = 5;
	public static int max = 30;
	public static Region defaultregion =  new Region("default",1,1,min,max);	
	ConnectedHexMap hexmap;
	
	public static Comparator<HexRegion> regionComparator = new Comparator<HexRegion>()
	{
		@Override
		public int compare(HexRegion r1, HexRegion r2)
		{
			return (int)(r1.getRegionSize() - r2.getRegionSize());
		} 
	};		
	
	public RegionGenerator(ConnectedHexMap chm, Random r)
	{
		super(r);
		hexmap=chm;
	}

	//Group Biomes
	//Creates Regions based on majority biome's valid biomes.
	public Set<HexRegion> initializeRegions()
	{
		List<HexRegion> allregions = new ArrayList<>();
		
		//Iterate through map, if hex has no region, check nearby neighbours for region, if no region exists, create one.
		Iterator<FilledHex> it = hexmap.getHexes().values().iterator();
		boolean found;
		while(it.hasNext())
		{
			FilledHex hh = it.next();

			if (hh.getRegion() == null) 
			{
				found = false;
				Set<FilledHex> nh = new HashSet<>();
				hh.getNeighbours(hexmap);
				
				Iterator<FilledHex> it2 = nh.iterator();
				while(it2.hasNext() && !found) //Look for neighbouring hex that has a region and isn't max size!
				{
					FilledHex neighbourhex = it2.next();
					HexRegion neighbourregion = neighbourhex.getRegion();
					if (neighbourregion != null && neighbourregion.getRegionSize() <  neighbourregion.stats.getMin())
					{
						//Try and add the hex to the neighbouring region
						if (neighbourregion.tryaddhex(hh))
						{
							found = true;
							hh.setRegion(neighbourregion);
						}
					}
				}
				
				//If looped through and still no region assigned to hh, create one.
				if (hh.getRegion() == null)
				{
					HexRegion newregion = new HexRegion(defaultregion, hh,hexmap); 
					hh.setRegion(newregion);
					allregions.add(newregion);
				}
				
			}
		
			
		}
		for(int ii = 0; ii < allregions.size(); ii++)
		{
			HexRegion currregion = allregions.get(ii);
			currregion.updateAll();
		}
		//Once regions have all been created, iterate through regions.
		//If region < minsize, look to merge to most appropriate nearby region.
			//Appropriate regions sorted by minimum size priority, then the first region to be valid for THIS region's majority biome
			//If this fails, looks for first region that has overlap of valid biome to THIS region's valid biomes
			//If this fails, adds to smallest region.
		for(int ii = 0; ii < allregions.size(); ii++)
		{
			HexRegion currregion = allregions.get(ii);
				
			if (currregion.getRegionSize() < currregion.stats.getMin())
			{
				boolean merged = false;
				List<HexRegion> neighbouringregions = new ArrayList<>(currregion.getNeighbourRegions());
				Collections.sort(neighbouringregions,regionComparator);
				
				//First Try: find smallest region that has a valid biome as this region's majority biome
				for(int jj = 0; jj < neighbouringregions.size() && !merged; jj++)
				{
					
					if ((neighbouringregions.get(jj).getValidBiomes().contains("all"))||
					   (neighbouringregions.get(jj).getValidBiomes().contains(currregion.getMajorityBiome().getConcreteBiomeName())))
					{
						neighbouringregions.get(jj).mergeRegion(currregion);
						merged = true;
					}
				}
				
				//Second Try: find smallest region that has any overlap of valid biomes, to this region's valid biomes.
				for(int jj = 0; jj < neighbouringregions.size() && !merged; jj++)
				{
					if ((neighbouringregions.get(jj).getValidBiomes().contains("all"))|| 
					   (!Collections.disjoint(currregion.getValidBiomes(),neighbouringregions.get(jj).getValidBiomes())))
					{
						neighbouringregions.get(jj).mergeRegion(currregion);
						merged = true;					
					}
				}
				
				//Third Try: merge to smallest neighbouring region.
				if(!merged)
				{
					neighbouringregions.get(0).mergeRegion(currregion);			
				}
				System.out.println("Merged");
			}
		}
		PFunctions.outputString(this,""+allregions.size());
		PFunctions.outputString(this,""+allregions.get(0).getRegionSize());
		return new HashSet<HexRegion>(allregions);
	}

}
