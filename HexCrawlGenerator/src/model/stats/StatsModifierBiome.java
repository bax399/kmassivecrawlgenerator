package model.stats;
import java.awt.Color;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import functions.PFunctions;
import model.WorldDescriptor;
import model.properties.BaseBiome;
import model.properties.BiomeModifierProperties;
public class StatsModifierBiome extends PropertyStats implements BaseBiome,BiomeModifierProperties {
	public static final String[] setvalues = {"biomemodifier"};
	public static final Set<String> tags = new HashSet<>(Arrays.asList(setvalues));	
	
											//modname,color,height,travel,spotd,rivero,rivere 
	public static StatsModifierBiome river = new StatsModifierBiome("river",new int[]{1,2,3},-2,2,"",0d,0.5d); //river runs through here
	public static StatsModifierBiome road =  new StatsModifierBiome("road",new int[]{1,2,3},0,-1,"",0d,0d);
	
	private BaseBiome next; 

	private final Color color;
	private final int height;
	private final int travelcost;
	private final String spotdistance;
	private final double riverorigin;
	private final double riverend;
	private final String modname;	

	
	public StatsModifierBiome(StatsModifierBiome bm, BaseBiome nextb)
	{
		color = bm.color;
		height = bm.height;
		travelcost = bm.travelcost;
		spotdistance = bm.spotdistance;
		riverorigin = bm.riverorigin;
		riverend = bm.riverend;
		modname=bm.modname;
	}
	
	public StatsModifierBiome(Properties pp)
	{
		color = PFunctions.parseColor(pp.getProperty("color"));
		height = Integer.parseInt(pp.getProperty("height"));
		travelcost = Integer.parseInt(pp.getProperty("travelcost"));
		spotdistance = pp.getProperty("spotdistance");
		riverorigin = Double.parseDouble(pp.getProperty("riverorigin"));
		riverend = Double.parseDouble(pp.getProperty("riverend"));
		modname=pp.getProperty("name");

	}

	public StatsModifierBiome(String n, int[] c, int h, int tc, String sd, double ro, double re)
	{
		color = new Color(c[0],c[1],c[2]);
		height = h;
		travelcost = tc;
		riverorigin = ro;
		riverend = re;
		spotdistance = sd;
		modname = n;
	}
	
	public void setNext(BaseBiome b)
	{
		next = b;
	}
	
	public BaseBiome getNext()
	{
		return next;
	}
	
	@Override
	public boolean isValidStart()
	{
		return next.isValidStart();
	}

	@Override
	public String getBiomeName()
	{
		return next.getBiomeName();
	}
	
	@Override
	public String getPrintName() {
		return modname + " " + next.getPrintName();
	}
	
	@Override
	public String getDescription() {
		//TODO possible error here.
		return next.getDescription()+this.getDescription();
	}

	@Override
	public Color getColor() {
		//TODO change this to work additively
		return next.getColor();
	}

	@Override
	public int getHeight() {
		return next.getHeight() + height;
	}

	@Override
	public int getTravelCost() {
		return next.getTravelCost() + travelcost;
	}

	@Override
	public String getSpotDistance() {
		return next.getSpotDistance()+spotdistance;
	}

	@Override
	public double getRiverOrigin() {
		return next.getRiverOrigin()+riverorigin;
	}

	@Override
	public double getRiverEnd() {
		return next.getRiverEnd()+riverend;
	}

	@Override
	public String getWeight()
	{
		return next.getWeight();
	}
	
	@Override
	public BaseBiome getConcreteBiome()
	{
		return next.getConcreteBiome();
	}
	
	@Override
	public Set<BaseBiome> getBiomes()
	{
		Set<BaseBiome> me = next.getBiomes();
		me.add(this);
		return me;
	}
	
	@Override
	public Set<String> getStrBiomes()
	{
		Set<String> me = next.getStrBiomes();
		me.add(this.modname);
		return me;
	}	
	
	@Override
	public Set<String> getValidRegionBiomes() 
	{ 
		return next.getValidRegionBiomes();
	} //TODO create valid biomes category.
}
