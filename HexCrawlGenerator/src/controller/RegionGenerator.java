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
	public static int min = 50;
	public static int max = 20000;
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
				nh.addAll(hh.getNeighbours(hexmap));
				
				Iterator<FilledHex> it2 = nh.iterator();
				while(it2.hasNext() && !found) //Look for neighbouring hex that has a region and isn't max size!
				{
					FilledHex neighbourhex = it2.next();
					HexRegion neighbourregion = neighbourhex.getRegion();
					if (neighbourregion != null && neighbourregion.getRegionSize() < neighbourregion.stats.getMax())
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
		
		List<HexRegion> smallRegionList = new ArrayList<>(allregions);
		boolean merged;
		do
		{
			HexRegion currregion = smallRegionList.remove(0);
			merged = false;
			List<HexRegion> neighbouringregions = new ArrayList<>(currregion.getNeighbourRegions());
			PFunctions.outputString(this,currregion.getRegionSize()+"");			
			Collections.sort(neighbouringregions,regionComparator);
			
			
		
			
			//Merging for regions that are too small.			

			//Merge two regions together if their majority biomes are identical, regardless of sizes.
			if (currregion.getRegionSize() < currregion.stats.getMax())
			{
				for(int jj = 0; jj < neighbouringregions.size() && !merged; jj++)
				{
					if(allregions.contains(neighbouringregions.get(jj)))
					{
						if (neighbouringregions.get(jj).getMajorityBiome().getConcreteBiomeName().equals(currregion.getMajorityBiome().getConcreteBiomeName()))
						{
							neighbouringregions.get(jj).mergeRegion(currregion);
							merged = true;
							allregions.remove(neighbouringregions.get(jj));
							allregions.add(neighbouringregions.get(jj));		
							smallRegionList.remove(neighbouringregions.get(jj));
							smallRegionList.add(neighbouringregions.get(jj));
						}
					}
				}
			}							
			
			if (currregion.getRegionSize() < currregion.stats.getMin() && !merged)
			{	
				//First Try: find smallest region that has the same majority biome as this region's majority biome
				for(int jj = 0; jj < neighbouringregions.size() && !merged; jj++)
				{
					if(allregions.contains(neighbouringregions.get(jj)))
					{
						if (neighbouringregions.get(jj).getMajorityBiome().getConcreteBiomeName().equals(currregion.getMajorityBiome().getConcreteBiomeName()))
						{
							neighbouringregions.get(jj).mergeRegion(currregion);
							merged = true;
							allregions.remove(neighbouringregions.get(jj));
							allregions.add(neighbouringregions.get(jj));		
							smallRegionList.remove(neighbouringregions.get(jj));
							smallRegionList.add(neighbouringregions.get(jj));						
						}
					}				
				
				}
				//2nd Try: find smallest region that has a valid biome as this region's majority biome
				for(int jj = 0; jj < neighbouringregions.size() && !merged; jj++)
				{
					if(allregions.contains(neighbouringregions.get(jj)))
					{
				
						if ((neighbouringregions.get(jj).getValidBiomes().contains("all"))||
						   (neighbouringregions.get(jj).getValidBiomes().contains(currregion.getMajorityBiome().getConcreteBiomeName())))
						{
							neighbouringregions.get(jj).mergeRegion(currregion);
							merged = true;
							allregions.remove(neighbouringregions.get(jj));
							allregions.add(neighbouringregions.get(jj));	
							smallRegionList.remove(neighbouringregions.get(jj));
							smallRegionList.add(neighbouringregions.get(jj));						
						}
					}
				}
				//TODO this generation doesn't always destroy smallest neighbouring regions
				//3rd Try: find smallest region that has any overlap of valid biomes, to this region's valid biomes.
				for(int jj = 0; jj < neighbouringregions.size() && !merged; jj++)
				{
					if(allregions.contains(neighbouringregions.get(jj)))
					{					
						if ((neighbouringregions.get(jj).getValidBiomes().contains("all"))|| 
						   (!Collections.disjoint(currregion.getValidBiomes(),neighbouringregions.get(jj).getValidBiomes())))
						{
							neighbouringregions.get(jj).mergeRegion(currregion);
							merged = true;					
							allregions.remove(neighbouringregions.get(jj));
							allregions.add(neighbouringregions.get(jj));		
							smallRegionList.remove(neighbouringregions.get(jj));
							smallRegionList.add(neighbouringregions.get(jj));			
						}
					}
				}
				
				//4th Try: merge to smallest neighbouring region. THAT EXISTS IN ALL REGIONS
				for(int jj = 0; jj < neighbouringregions.size() && !merged; jj++)
				{
					if(allregions.contains(neighbouringregions.get(jj)))
					{
						if(allregions.contains(neighbouringregions.get(jj)))
						{
							neighbouringregions.get(jj).mergeRegion(currregion);		
							merged=true;
							allregions.remove(neighbouringregions.get(jj));
							allregions.add(neighbouringregions.get(jj));	
							smallRegionList.remove(neighbouringregions.get(jj));
							smallRegionList.add(neighbouringregions.get(jj));
						}
					}
				}
			}

			if(merged)
			{
				PFunctions.outputString(this,"Merged regions");				
				allregions.remove(currregion);
				smallRegionList.remove(currregion);
			}
			
			if(currregion.getRegionSize() < currregion.stats.getMin() && !merged)
			{
				smallRegionList.add(currregion);
				currregion.updateAll();
			}
		}while(smallRegionList.size() > 0);
		
		

		PFunctions.outputString(this,""+allregions.size());
		Set<HexRegion> uniqueregions = new HashSet<HexRegion>(allregions);
		PFunctions.outputString(this, ""+uniqueregions.size());		
		
		Iterator<HexRegion> itRegion = uniqueregions.iterator();
		int ii=0,totalsize=0;
		while(itRegion.hasNext())
		{
			ii++;
			HexRegion eachregion = itRegion.next();
			eachregion.updateAll();
			eachregion.calculateEdgeLines();
			
			PFunctions.outputString(this,"Region Details for "+ii);
//			PFunctions.outputString(this,"Edge:"+eachregion.getEdgeHexes().size());
//			PFunctions.outputString(this,"Edgelines: "+ eachregion.getEdgeLines().size());
//			
//			PFunctions.outputString(this,"NeighbourRegions:"+eachregion.getNeighbourRegions().size());			
//			PFunctions.outputString(this,"Neighbour:"+eachregion.getNeighbourHexes().size());
			PFunctions.outputString(this,"Size:"+eachregion.getRegionSize());
			totalsize+=eachregion.getRegionSize();			
		}
		PFunctions.outputString(this,"Count Region Size:"+totalsize);
		return uniqueregions;
	}

}
