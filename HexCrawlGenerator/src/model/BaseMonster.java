package model;
import java.awt.Color;
import model.*;
import model.worldobjects.*;
import model.redblob.*;
import java.util.*;
public class BaseMonster implements MonsterProperties {
	public final String[] setvalues = {"monster"};
	public final Set<String> tags = new HashSet<>(Arrays.asList(setvalues));
	
	private final Random rand;

	private Map<Biome,Double> spawnchances; //Only return unmodifiable
	private final String name;
	private final String description;
	private final int roamradius;
	private final int visibility;
	private final double nomadchance;
	private final double encounterchance;
	private final int roamtype;
	
	public BaseMonster(Properties pp, Random r) 
	{
		rand = r;
		spawnchances = new HashMap<>();
		processSpawnChances(pp.getProperty("spawnchance"));
		name=pp.getProperty("name");
		description=pp.getProperty("description");
		roamradius=Integer.parseInt(pp.getProperty("roamradius"));
		roamtype=Integer.parseInt(pp.getProperty("roamtype"));
		visibility=Integer.parseInt(pp.getProperty("visibility"));
		nomadchance=Double.parseDouble(pp.getProperty("nomadchance"));
		encounterchance=Double.parseDouble(pp.getProperty("encounterchance"));
	}

	public HexMonster rollMonster(Biome b, FilledHex origin)
	{
		double weight;
		HexMonster thismonster=null;
		Lair thislair;
		Nomad thisnomad;
		if (spawnchances.containsKey(b))
		{
			weight = spawnchances.get(b);
			if (weight > 0)
			{
				if (rollBiomeSpawn(weight))
				{
				
					thismonster = new HexMonster(this,origin);
					if(rollNomadic(nomadchance))
					{

						thisnomad = new Nomad(thismonster);
						origin.addNomad(thisnomad);
						//TODO add monster to all hexes near origin out to a radius of roamradius						
					}
					else
					{
						thislair = new Lair(thismonster);
						origin.addLair(thislair);
					}
				}
			}
			
		}
		
		return thismonster;
	}
	
	public boolean rollBiomeSpawn(double chance)
	{
		return (Double.compare(rand.nextDouble(),chance) < 0);
	}
	
	public boolean rollNomadic(double chance)
	{
		boolean nomadic = false;
		
		if(Double.compare(chance,0d)>0 && Double.compare(chance, 1d)<0)
		{
			nomadic = (Double.compare(rand.nextDouble(),chance) < 0);
		}
		
		return nomadic;
	}
	
	public void processSpawnChances(String sc)
	{
		
	}
	
	@Override
	public int getVisibility() {
		return visibility;
	}

	@Override
	public double getNomadChance() {
		return nomadchance;
	}

	@Override
	public int getRoamRadius() {
		return roamradius;
	}

	@Override
	public Map<Biome,Double> getSpawnChance() {
		return spawnchances;
	}

	@Override
	public int getRoamType() {
		return roamtype;
	}

	@Override
	public double getEncounterChance() {
		return encounterchance;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public String getDescription()
	{
		return description;
	}	
	
}
