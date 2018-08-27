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
public class BiomeChooser 
{
	
	Map<Biome,RandomCollection<Biome>> weights;
	Map<String,Biome> biomenames;
	Set<Biome> validbiomes;
	
	Map<Biome, Set<Biome>> validregionbiomes; // TODO implement similar to weights, but use biome valid string instead.
	
	Random rand;

	public BiomeChooser(ArrayList<Biome> biomes, Random random)
    {
		rand = random; 
		weights = new HashMap<>();
		biomenames = new HashMap<>();
		validbiomes = new HashSet<>();
		//Add all true-names to list
		for(Biome b: biomes)
		{
			biomenames.put(b.getConcreteBiomeName(),b); 
		}
		
		//process weights now we have all.
		for(Biome b: biomes)
		{
			initializeWeights(b);
			if (b.isValidStart()) validbiomes.add(b);
		}
		
		outputString(this, validbiomes.toString());
	}
	
	public Map<String,Biome> getBMap()
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
	
	public void initializeWeights(Biome origin)
	{
		RandomCollection<Biome> bb = new RandomCollection<>(rand);
		
		String weightstring = origin.getWeight();
		weightstring=weightstring.replaceAll("\\s+", "");
		String[] each = weightstring.split(",");
		try
		{
		each = putAllEnd(each);
		} catch(IllegalArgumentException e) {throw new IllegalArgumentException(e.getMessage()+" for: " + origin.getConcreteBiomeName());}
		
		for(String wv: each)
		{
			double weight;
			String bname=wv.substring(0, wv.lastIndexOf(":"));
			try {weight=Double.parseDouble(wv.substring(wv.lastIndexOf(":")+1));} 
			catch(NumberFormatException e) {throw new IllegalArgumentException("Biome weight invalid in: " +origin.getConcreteBiomeName());}
			
			if(bname.equals("all"))
			{
				//Add all with >=1 weight.
				//if (weight > 0)
				//{
					for(Map.Entry<String,Biome> map : biomenames.entrySet())
					{
						bb.add(weight, map.getValue());
					}
				//}
			}
			else
			{
				Biome bfound=biomenames.get(bname);
				if(bfound==null) outputString(this,"Biome weight invalid in: " +origin.getConcreteBiomeName());//throw new IllegalArgumentException("Biome weight invalid in: " +origin.getBiomeName());	
				if(bfound!=null) bb.add(weight,bfound);				
			}
		}
		
		weights.put(origin,bb);
	}
	
	public Biome rollBiome(Biome previous)
	{
		if (previous !=null) 
		{
			
			Biome next = weights.get(previous).next();
			//System.out.println(previous.getBiomeName() +" into "+ next.getBiomeName());
			return next;
		}
		else return rollBiome();
	}
	
	//This just gets a random valid biome to start the head.
	public Biome rollBiome()
	{
		int index = rand.nextInt(validbiomes.size());
		Iterator<Biome> it = validbiomes.iterator();
		Biome found = null;
		for(int ii=-1;ii<index;ii++)
		{
			found = it.next();
		}
		return found;
	}	
	
	public Biome rollBiome(String name)
	{
		return weights.get(biomenames.get(name)).next();
	}	
	

}
