package zdeprececated;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import controller.Generator;
import functions.PFunctions;
import model.ConnectedHexMap;
import model.FilledHex;
import model.HexRegion;
import model.stats.StatsRegion;
public class RegionGeneratorClean extends Generator 
{
	public static int min = 500;
	public static int max = 20000;
	public static StatsRegion defaultregion =  new StatsRegion("default",1,1,min,max);	
	ConnectedHexMap hexmap;
	
	public static Comparator<HexRegion> regionComparator = new Comparator<HexRegion>()
	{
		@Override
		public int compare(HexRegion r1, HexRegion r2)
		{
			return (int)(r1.getRegionSize() - r2.getRegionSize());
		} 
	};		
	
	public RegionGeneratorClean(ConnectedHexMap chm, Random r)
	{
		super(r);
		hexmap=chm;
	}

	//Group Biomes
	//Creates Regions based on majority biome's valid biomes.
	public Set<HexRegion> initializeRegions()
	{
		List<HexRegion> allRegions = new ArrayList<>();;
		
		generateValidRegions(allRegions);
		
		mergeSimilarRegions(allRegions);
		
		Collections.sort(allRegions,regionComparator);
		
		//mergeSmallRegions(allRegions);
		
		int ii=0;
		for(HexRegion eachReg : allRegions)
		{
			eachReg.updateAll();
			eachReg.calculateEdgeLines();
//			PFunctions.outputString(this,"Region Details for "+totalsize);
//			PFunctions.outputString(this,"Edge:"+eachregion.getEdgeHexes().size());
//			PFunctions.outputString(this,"Edgelines: "+ eachregion.getEdgeLines().size());
//			
//			PFunctions.outputString(this,"NeighbourRegions:"+eachregion.getNeighbourRegions().size());			
//			PFunctions.outputString(this,"Neighbour:"+eachregion.getNeighbourHexes().size());
//			PFunctions.outputString(this,"Size:"+eachReg.getRegionSize());
			ii++;			
		}
		PFunctions.outputString(this,"Count Region Size: "+ii);			
		
		return new HashSet<>(allRegions);
	}
	
	public void generateValidRegions(List<HexRegion> allRegions)
	{
		List<FilledHex> notVisited = new ArrayList<>(hexmap.getHexes().values());

		while(!notVisited.isEmpty())
		{
			FilledHex eachHex = notVisited.remove(0);
			if(eachHex.getRegion() == null)
			{
				HexRegion newRegion = generateRegionSeed(eachHex,notVisited);
				allRegions.add(newRegion);	
			}	
		}
	}
	
	public HexRegion generateRegionSeed(FilledHex origin,List<FilledHex> candidates)
	{
		HexRegion newRegion = new HexRegion(defaultregion,origin,hexmap);
		PFunctions.outputString(this,"Creating for: "+origin.q+" "+origin.r);
		
		boolean foundOne=true;
		FilledHex currHex = origin;
		while(foundOne)
		{
			for(FilledHex neighHex : currHex.getNeighbours(hexmap))
			{
				foundOne=false;
				if ((neighHex.getRegion()==null)
					&& (neighHex.getBiome().getConcreteBiome().equals(newRegion.getMajorityBiome().getConcreteBiome())))
				{
					newRegion.addhex(neighHex);
					candidates.remove(neighHex);
					foundOne = true;
					currHex = neighHex;
				}
			}
		}
		return newRegion;
	}
	
	public void mergeSimilarRegions(List<HexRegion> allRegions)
	{
		for(HexRegion eachregion : allRegions)
		{
			eachregion.updateAll();
			PFunctions.outputString(this,eachregion.getNeighbourRegions().size()+"");
		}
	}
	
	public void mergeSmallRegions(List<HexRegion> allRegions)
	{
		
	}
}
