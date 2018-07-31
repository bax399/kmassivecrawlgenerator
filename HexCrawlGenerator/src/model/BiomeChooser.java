package model;
import java.util.*;
import model.frorcommon.RandomCollection;
public class BiomeChooser 
{
	
	Map<Biome,RandomCollection<Biome>> weights;
	Map<String,Biome> biomenames;
	
	private final Random rand;

	public BiomeChooser(ArrayList<Biome> biomes, Random random)
	{
		rand = random;
		weights = new HashMap<>();
		biomenames = new HashMap<>();
		
		//Add all true-names to list
		for(Biome b: biomes)
		{
			biomenames.put(b.getBiomeName(),b);
		}
		
		//process weights now we have all.
		for(Biome b: biomes)
		{
			initializeWeights(b);			
		}
		
	}
	
	//Needed so that all is put at the end of the string.
	public static String[] putAllEnd(String[] weightarray) throws IllegalArgumentException
	{
		String swap;
		int size=weightarray.length;
		boolean foundend=false,found=false;
		
		if(weightarray[size-1].startsWith("all:"))
		{
			foundend=true;
		}
		
		for(int ii=0;ii<size-1;ii++)
		{		
			if (weightarray[ii].startsWith("all:"))
			{
				if(!foundend && !found)
				{
					//swap with the last index
					swap = weightarray[ii];
					weightarray[ii] = weightarray[size-1];
					weightarray[size-1] = swap;
					found=true;
				}
				else
				{
					throw new IllegalArgumentException("Cannot have two 'all' keywords in weights");
				}
				
			}
		}
			
		return weightarray;
	}
	
	//TODO COMPLETED: allow 'all' keyword to appear anywhere, seach in advance for exactly "all", then initialize all others first.
	public void initializeWeights(Biome origin)
	{
		RandomCollection<Biome> bb = new RandomCollection<>(rand);
		
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
				if (weight > 0)
				{
					for(Map.Entry<String,Biome> map : biomenames.entrySet())
					{
						bb.add(weight, map.getValue());
					}
				}
			}
			else
			{
				Biome bfound=biomenames.get(bname);
				if(bfound==null) throw new IllegalArgumentException("Biome weight invalid in: " +origin.getBiomeName());	
				bb.add(weight,bfound);				
			}
		}
		
		weights.put(origin,bb);
	}
	
	public Biome rollBiome(Biome previous)
	{
		return weights.get(previous).next();
	}
	
	//TODO this rolls for a biome after randomizing a biome, this can potentially get a Desert->Abyss into a point that shouldnt be allowed
	//TODO this shouldn't select rare biomes to potentially roll from, or is that OK?
	public Biome rollBiome()
	{
		int index = rand.nextInt(weights.size());
		Iterator<Biome> it = weights.keySet().iterator();
		for(int ii=0;ii<index;ii++)
		{
			it.next();
		}
		return it.next();
	}	
	
	public Biome rollBiome(String name)
	{
		return weights.get(biomenames.get(name)).next();
	}	
	

}
