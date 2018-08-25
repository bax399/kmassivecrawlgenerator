package controller;
import java.util.*;

import model.Region;
import model.ConnectedHexMap;
public class RegionGenerator extends Generator 
{
	int min = 20;
	int max = 200;
	ConnectedHexMap hexmap;
	
	public RegionGenerator(ConnectedHexMap chm, Random r, int min, int max)
	{
		super(r);
		hexmap=chm;
		this.min = min;
		this.max = max;
	}

	//Group Biomes
	//Creates Regions based on majority biome's valid biomes.
	public void initializeRegions()
	{
		
		
		//Region starts in hex without region defined.
		//While region < min size, add VALID biomes based on biome w/ most hexes in this region.
		
			// // //Check each "edge" hex of the region for its neighbours, if they're valid, add.
					//If they're not valid, but surrounded on 5 sides, add.
			//Get set of "neighbouring" hexes, if valid, add and then add all its neighbours to neighbouring set.
				//Remove from set whether it is valid or not.
				
		
			//If region < min size:
				//Merge into 
	}
	
	public void mergeRegion(Region origin)
	{
		
	}
}
