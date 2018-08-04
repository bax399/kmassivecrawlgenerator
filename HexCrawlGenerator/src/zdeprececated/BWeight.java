package zdeprececated;
import java.util.*;

import model.Biome;

public class BWeight 
{
	Map<Biome,Integer> ordering;
	Map<Integer,Biome> biomeid;
	
	Map<Integer,ArrayList<Integer>> weights;
	public Map<Integer,ArrayList<Integer>> rollweights;
	int total;
	
	Map<String,Biome> biomenames;
	Random rand = new Random();
	
	public BWeight(Properties bweights, ArrayList<Biome> biomes)
	{
		ordering = new HashMap<>();
		weights = new HashMap<>();
		rollweights = new HashMap<>();
		biomenames = new HashMap<>();
		biomeid = new HashMap<>();
		
		//Temp vars.
		Integer biomenum;
		String biomename;
		ArrayList<Integer> weightings;
		//get ordering from bweights.name
		ArrayList<String> nameorder = parseOrdering(bweights.getProperty("name"));
			
		//Initialise quick access of biomes based on name
		for(int ii = 0; ii<biomes.size();ii++)
		{
			biomenames.put(biomes.get(ii).name,biomes.get(ii));
		}
		
		//put biomes into order based on the nameorder
		for(int ii = 0; ii<nameorder.size();ii++)
		{
			ordering.put(biomenames.get(nameorder.get(ii)),ii);
			biomeid.put(ii,biomenames.get(nameorder.get(ii)));			
		}
		
		//Using ordering, get weights of biomes.
		for(int ii = 0; ii<ordering.size();ii++)
		{
			biomenum = ordering.get(biomes.get(ii));
			biomename = biomes.get(ii).name;
			weightings = parseWeights(bweights.getProperty(biomename));
			weights.put(biomenum, weightings);
			rollweights.put(biomenum, incrementWeights(weightings));
		}
	}

	
	public ArrayList<String> parseOrdering(String csv)
	{
		String[] each = csv.split(",");
		ArrayList<String> output = new ArrayList<>();
		for(int ii = 0; ii< each.length;ii++)
		{
			output.add(each[ii]);
		}
		
		return output;	
	}	
	
	public ArrayList<Integer> parseWeights(String csv)
	{
		String[] each = csv.split(",");
		ArrayList<Integer> output = new ArrayList<>();
		for(int ii = 0; ii< each.length;ii++)
		{
			output.add(Integer.parseInt(each[ii]));
		}
		
		return output;
	}
	
	public static ArrayList<Integer> incrementWeights(ArrayList<Integer> weights)
	{
		ArrayList<Integer> incremented = new ArrayList<>();
		
		incremented.add(0,weights.get(0));
		for(int ii = 1; ii < weights.size();ii++)
		{
			incremented.add(ii,weights.get(ii)+incremented.get(ii-1));
		}
		
		for(int ii = incremented.size()-1; ii > 0; ii--)
		{
			if (incremented.get(ii).equals(incremented.get(ii-1)))
			{
				incremented.set(ii,0);
			}
		}
		
		return incremented;
	}
	
	public int rollWeights(ArrayList<Integer> incremented)
	{
		int min = 1;
		int max = 1;
		for(int ii = incremented.size()-1;ii>0;ii--)
		{
			if (incremented.get(ii) > 0)
			{
				max = incremented.get(ii);
				break;
			}
		}
		
		return randInt(min,max);
	}
	
	public static int indexWeight(ArrayList<Integer> incremented, int weight)
	{
		int index = 0;
		for(int ii = 0; ii < incremented.size();ii++)
		{
			if (incremented.get(ii) >= weight)
			{
				index = ii;
				break;
			}
		}
		return index;
	}
	
	public Biome rollBiome(Biome previous)
	{
		int prevnum = ordering.get(previous);
		int nextnum = indexWeight(rollweights.get(prevnum), rollWeights(rollweights.get(prevnum)));
		return biomeid.get(nextnum);
	}
	
	//TODO this rolls for a biome after reandomizing a biome, this can potentially get a Desert->Abyss into a point that shouldnt be allowed
	public Biome rollBiome()
	{
		int index = rand.nextInt(biomeid.size());
		Biome first = biomeid.get(index);
		int prevnum = ordering.get(first);
		int nextnum = indexWeight(rollweights.get(prevnum), rollWeights(rollweights.get(prevnum)));
		
		return biomeid.get(nextnum);
	}	
	
	public int randInt(int min, int max)
	{		
		return rand.nextInt((max-min)+1)+min;		
	}
}
