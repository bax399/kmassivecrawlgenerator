package model;
import java.awt.Color;
import java.util.*;
public class BiomeModifier extends HasDescriptor implements Biome,BiomeModifierProperties {
	public static final String[] setvalues = {"biomemodifier"};
	public static final Set<String> tags = new HashSet<>(Arrays.asList(setvalues));	
	
											//modname,color,height,travel,spotd,rivero,rivere
	public static BiomeModifier river = new BiomeModifier("River",new int[]{1,2,3},0,0,"",0d,1.0d); //river runs through here
	//public static BiomeModifier mouth = new BiomeModifier("River Mouth",new int[]{1,2,3},0,5,"",0d,1.0d);
	
	private Biome next;

	private final Color color;
	private final int height;
	private final int travelcost;
	private final String spotdistance;
	private final double riverorigin;
	private final double riverend;
	private final String modname;	
	private final String biomes;
	private final double originchance;
	
	private Set<Biome> validbiomes;

	public BiomeModifier(BiomeModifier bm, Biome nextb)
	{
		super(new WorldDescriptor(bm.modname, BiomeConcrete.tags, bm.modname, 0));
		color = bm.color;
		height = bm.height;
		travelcost = bm.travelcost;
		spotdistance = bm.spotdistance;
		riverorigin = bm.riverorigin;
		riverend = bm.riverend;
		modname=bm.modname;
		originchance=bm.originchance;
		biomes=bm.biomes;	
	}
	
	public BiomeModifier(Properties pp)
	{
		super(new WorldDescriptor(pp.getProperty("name"), BiomeConcrete.tags, pp.getProperty("name"), 10));
		color = parseColor(pp.getProperty("color"));
		height = Integer.parseInt(pp.getProperty("height"));
		travelcost = Integer.parseInt(pp.getProperty("travelcost"));
		spotdistance = pp.getProperty("spotdistance");
		riverorigin = Double.parseDouble(pp.getProperty("riverorigin"));
		riverend = Double.parseDouble(pp.getProperty("riverend"));
		modname=pp.getProperty("name");
		originchance=Double.parseDouble(pp.getProperty("originchance"));
		biomes=pp.getProperty("validbiomes");
	}

	public BiomeModifier(String n, int[] c, int h, int tc, String sd, double ro, double re)
	{
		super(new WorldDescriptor(n,tags,n,0));
		color = new Color(c[0],c[1],c[2]);
		height = h;
		travelcost = tc;
		riverorigin = ro;
		riverend = re;
		spotdistance = sd;
		modname = n;
		originchance=0.0;
		biomes=null;
	}
	
	public void setNext(Biome b)
	{
		next = b;
	}
	
	public Biome getNext()
	{
		return next;
	}
	
	@Override
	public int getValidStart()
	{
		return next.getValidStart();
	}
	
	@Override
	public Color parseColor(String rgb)
	{
		return next.parseColor(rgb);
	}
	
	@Override
	public Set<Biome> getValidBiomes()
	{
		return validbiomes;
	}
	
	@Override
	public String getBiomeName()
	{
		return next.getBiomeName();
	}
	
	@Override
	public String getName() {
		return modname + next.getName();
	}

	public String getModName()
	{
		return modname;
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
	public String getBiomes() {
		return biomes;
	}
	
	@Override
	public double getOriginChance()
	{
		return originchance;
	}

	@Override
	public String getWeight()
	{
		return next.getWeight();
	}	
}
