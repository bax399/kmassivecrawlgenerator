package model;
import static functions.PFunctions.outputString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import model.frorcommon.RandomCollection;
import model.properties.BaseBiome;
public class BiomeChooser 
{
	
	Map<BaseBiome,RandomCollection<BaseBiome>> weights;
	Map<String,BaseBiome> biomenames;
	Set<BaseBiome> validbiomes;
	
	Map<BaseBiome, Set<BaseBiome>> validregionbiomes; // TODO implement similar to weights, but use biome valid string instead.
	
	Random rand;

	public BiomeChooser(ArrayList<BaseBiome> biomes, Random random)
    {
		rand = random; 
		weights = new HashMap<>();
		biomenames = new HashMap<>();
		validbiomes = new HashSet<>();
		//Add all true-names to list
		for(BaseBiome b: biomes)
		{
			biomenames.put(b.getBiomeName(),b); 
		}
		
		//process weights now we have all.
		for(BaseBiome b: biomes)
		{
			initializeWeights(b);
			if (b.isValidStart()) validbiomes.add(b);
		}
		
		outputString(this, validbiomes.toString());
	}
	
	public Map<String,BaseBiome> getBMap()
	{
		return Collections.unmodifiableMap(biomenames);
	}
	
	//Needed so that all is put at the end of the string.
	public static String[] putAllEnd(String[] stringarray) throws IllegalArgumentException
	{
		String swap;
		int size=stringarray.length;
		boolean foundend=false,found=false;
		
		if(stringarray[size-1].startsWith("all:"))
		{
			foundend=true;
		}
		
		for(int ii=0;ii<size-1;ii++)
		{		
			if (stringarray[ii].startsWith("all:"))
			{
				if(!foundend && !found)
				{
					//swap with the last index
					swap = stringarray[ii];
					stringarray[ii] = stringarray[size-1];
					stringarray[size-1] = swap;
					found=true;
				}
				else
				{
					throw new IllegalArgumentException("Cannot have two 'all' keywords in weights");
				}
				
			}
		}
			
		return stringarray;
	}
	
	public void initializeWeights(BaseBiome origin)
	{
		RandomCollection<BaseBiome> bb = new RandomCollection<>(rand);
		
		String weightstring = origin.getWeight();
		weightstring=weightstring.replaceAll("\\s+", "");
		String[] each = weightstring.split(",");
		try
		{
		each = putAllEnd(each);
		} catch(IllegalArgumentException e) {throw new IllegalArgumentException(e.getMessage()+" for: " + origin.getBiomeName());}
		
		for(String wv: each)
		{
			double weight;
			String bname=wv.substring(0, wv.lastIndexOf(":"));
			try {weight=Double.parseDouble(wv.substring(wv.lastIndexOf(":")+1));} 
			catch(NumberFormatException e) {throw new IllegalArgumentException("Biome weight invalid in: " +origin.getBiomeName());}
			
			if(bname.equals("all"))
			{
				//Add all with >=1 weight.
				//if (weight > 0)
				//{
					for(Map.Entry<String,BaseBiome> map : biomenames.entrySet())
					{
						bb.add(weight, map.getValue());
					}
				//}
			}
			else
			{
				BaseBiome bfound=biomenames.get(bname);
				if(bfound==null) outputString(this,"Biome weight invalid in: " +origin.getBiomeName());//throw new IllegalArgumentException("Biome weight invalid in: " +origin.getBiomeName());	
				if(bfound!=null) bb.add(weight,bfound);				
			}
		}
		
		weights.put(origin,bb);
	}
	
	public BaseBiome rollBiome(BaseBiome previous)
	{
		if (previous !=null) 
		{
			
			BaseBiome next = weights.get(previous).next();
			//System.out.println(previous.getBiomeName() +" into "+ next.getBiomeName());
			return next;
		}
		else return rollBiome();
	}
	
	//This just gets a random valid biome to start the head.
	public BaseBiome rollBiome()
	{
		int index = rand.nextInt(validbiomes.size());
		Iterator<BaseBiome> it = validbiomes.iterator();
		BaseBiome found = null;
		for(int ii=-1;ii<index;ii++)
		{
			found = it.next();
		}
		return found;
	}	
	
	public BaseBiome rollBiome(String name)
	{
		return weights.get(biomenames.get(name)).next();
	}	
	

}
