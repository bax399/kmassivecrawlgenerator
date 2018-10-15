package model.stats;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import model.FilledHex;
import model.Point;
import model.properties.TownProperties;
import model.worldplaces.HexTown;
public class StatsTown extends PropertyStats implements TownProperties
{
	public static final String[] setvalues = {"town"};
	public static final Set<String> tags = new HashSet<>(Arrays.asList(setvalues));
	
	public static final Character symbol ='U'; //TODO fix this town symbol...
	
	private final String vbiomes;
	private final int connectivity; //how much road it can make (size of town)
	private final boolean needsriver; //if the town must generate near water
	private final double spawnchance; //if validbiome, chance to spawn.
	private final int max;
	private int counter=0; //Used for ensuring we never get too much.
	
	public StatsTown(String inName, int inVisibility, int inMax, int inConnectivity, String inValidBiomes, boolean inWater,double inSpawnChance)
	{
		max = inMax;
		connectivity=inConnectivity;
		vbiomes=inValidBiomes;
		needsriver=inWater;
		spawnchance=inSpawnChance;
	}
	
	public boolean limitReached()
	{
		return !(counter<getMax());
	}
	
	public HexTown createTown(FilledHex origin, Point randompoint)
	{
		HexTown ht =null;
		if(!limitReached())
		{
			ht=new HexTown(this,origin,randompoint);
			counter++;
		}
		
		return ht;
	}
	public int getMax() {return max;}
	
	
	@Override
	public double getChance()
	{
		return spawnchance;
	}
	
	@Override
	public String getVBiomes() 
	{
		return vbiomes;
	}	
	
	@Override 
	public int getConnectivity()
	{
		return connectivity;
	}
	
	@Override
	public boolean needsRiver()
	{
		return needsriver;
	}
}
