package controller;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import functions.KFunctions;
import model.ConnectedHexMap;
import model.FilledHex;
import model.HexRegion;
import model.stats.StatsRegion;
public class RegionGenerator extends Generator 
{
	public static int min = 50;
	public static int max = 20000;
	public static StatsRegion defaultregion =  new StatsRegion(min,max);	
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

	/**
	 * This method calculates Edge Lines for regions, and makes sure they're updated.
	 * @param allRegions
	 */
	public void finalizeRegions(List<HexRegion> allRegions)
	{
		Iterator<HexRegion> itRegion = allRegions.iterator();
		int totalsize=0;

		while(itRegion.hasNext())
		{
			HexRegion eachregion = itRegion.next();
			eachregion.updateAll();
			eachregion.calculateEdgeLines();
			totalsize+=eachregion.getRegionSize();			
		}
		
		KFunctions.outputString(this,"Count Region Size: "+totalsize);
		KFunctions.outputString(this,"Total Regions: "+allRegions.size());
	}
	
	public Set<HexRegion> initializeSameRegions()
	{
		List<HexRegion> allRegions = new ArrayList<>();
		
		generateValidRegions(allRegions);

		mergeSameRegions(allRegions);
		
		finalizeRegions(allRegions);
		
		return new HashSet<>(allRegions);		
	}
	
	//Group Biomes
	//Creates Regions based on majority biome's valid biomes.
	public Set<HexRegion> initializeMergedRegions()
	{
		List<HexRegion> allRegions = new ArrayList<>();
		
		generateValidRegions(allRegions);

		mergeSameRegions(allRegions);

		KFunctions.outputString(this,"finished initial Regions");
		mergeSimilarRegions(allRegions);

		KFunctions.outputString(this,"finished similar regions");
		
		mergeSmallRegions(allRegions);

		KFunctions.outputString(this,"finished small Regions");		

		finalizeRegions(allRegions);
		
		return new HashSet<>(allRegions);
	}

	public void generateValidRegions(List<HexRegion> allRegions)
	{
		//Iterate through map, if hex has no region, check nearby neighbours for region & same concrete biome, if no region exists, create one.
		Iterator<FilledHex> itAllHexes = hexmap.getHexes().values().iterator();
		boolean foundRegion;
		while(itAllHexes.hasNext())
		{
			FilledHex currHex = itAllHexes.next();

			if (currHex.getRegion() == null) 
			{
				foundRegion = false;
				Set<FilledHex> nh = new HashSet<>(currHex.getNeighbours(hexmap));
				
				Iterator<FilledHex> itNeighbourHexes = nh.iterator();
				
				while(itNeighbourHexes.hasNext() && !foundRegion) //Look for neighbouring hex that has a region
				{
					FilledHex neighbourHex = itNeighbourHexes.next();
					HexRegion neighbourRegion = neighbourHex.getRegion();
					if (neighbourRegion != null)
					{
						//Try and add the hex to the neighbouring region
						if (neighbourHex.getHabitat().getCoreBiome().equals(currHex.getHabitat().getCoreBiome()))
						{
							foundRegion = true;
							neighbourRegion.addHex(currHex);
							currHex.setRegion(neighbourRegion);
						}
					}
				}
				
				//If looped through and still no region assigned to hh, create one.
				if (currHex.getRegion() == null)
				{
					HexRegion newregion = new HexRegion(defaultregion, currHex,hexmap); 
					currHex.setRegion(newregion);
					allRegions.add(newregion);
				}
				
			}
		}
		
	
	}
	
	public void mergeSameRegions(List<HexRegion> allRegions)
	{
		List<HexRegion> regionList = new ArrayList<>(allRegions);
		do
		{
			HexRegion currRegion = regionList.remove(0);
			//currRegion.updateNeighbourRegions();
			
			List<HexRegion> neighbourRegions = new ArrayList<>(currRegion.getNeighbourRegions());		
			
			for(int jj = 0; jj < neighbourRegions.size(); jj++)
			{
				HexRegion neighbourRegion = neighbourRegions.get(jj);
				if(allRegions.contains(neighbourRegion))
				{
					if (neighbourRegion.getMajorityBiome().getBiomeName().equals(currRegion.getMajorityBiome().getBiomeName()))
					{
						neighbourRegion.mergeRegion(currRegion);
						allRegions.remove(currRegion);
						break;
					}
				}
			}	
			
			
	
			
		}while(regionList.size() > 0);
	}
	
	public void mergeSimilarRegions(List<HexRegion> allRegions)
	{
		List<HexRegion> regionList = new ArrayList<>(allRegions);
		do
		{
			HexRegion currRegion = regionList.remove(0);
			List<HexRegion> neighbourRegions = new ArrayList<>(currRegion.getNeighbourRegions());
			
			for(int jj = 0; jj < neighbourRegions.size(); jj++)
			{
				if(allRegions.contains(neighbourRegions.get(jj)))
				{
					if (neighbourRegions.get(jj).getMajorityBiome().getValidRegionBiomes().contains(currRegion.getMajorityBiome().getBiomeName()))
					{
//						PFunctions.outputString(this,"Merged Similar");
						neighbourRegions.get(jj).mergeRegions(currRegion);
						allRegions.remove(currRegion);
						break;
					}
				}
			}		
	
			
		}while(regionList.size() > 0);
	}
	
	public void mergeSmallRegions(List<HexRegion> allRegions)
	{
		List<HexRegion> regionList = new ArrayList<>(allRegions);
		Collections.sort(regionList,regionComparator);
		do
		{
			HexRegion currRegion = regionList.remove(0);
			if (currRegion.getRegionSize() < currRegion.stats.getMin())
			{
				List<HexRegion> neighbourRegions = new ArrayList<>(currRegion.getNeighbourRegions());
				Collections.sort(neighbourRegions,regionComparator);
				for(int jj = 0; jj < neighbourRegions.size(); jj++)
				{
					if(allRegions.contains(neighbourRegions.get(jj)))
					{
//							PFunctions.outputString(this,"Merged Small");
							neighbourRegions.get(jj).mergeRegions(currRegion);
							allRegions.remove(currRegion);
							break;
					}
				}		
			}
			
		}while(regionList.size() > 0);		
	}
	
	
}