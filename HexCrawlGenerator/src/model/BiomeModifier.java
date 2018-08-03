package model;
import java.awt.Color;
import java.util.*;
public class BiomeModifier extends Biome implements BiomeModifierProperties {
	public static final String[] setvalues = {"biomemodifier"};
	public static final Set<String> tags = new HashSet<>(Arrays.asList(setvalues));	
	
	public static BiomeModifier river = new BiomeModifier(); //default biome.	
	
	private Biome next;

	private final Color color;
	private final int height;
	private final int travelcost;
	private final String spotdistance;
	private final double riverorigin;
	private final double riverend;
	private final String modname;	
	
	public BiomeModifier()
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return modname + next.getName();
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTravelCost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getSpotDistance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getRiverOrigin() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getRiverEnd() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<Biome> getValidBiomes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Biome, Integer> getOriginBiomesChance() {
		// TODO Auto-generated method stub
		return null;
	}

}
