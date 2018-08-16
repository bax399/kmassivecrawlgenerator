package model;
import java.awt.Color;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
public class BiomeModifier extends HasDescriptor implements Biome,BiomeModifierProperties {
	public static final String[] setvalues = {"biomemodifier"};
	public static final Set<String> tags = new HashSet<>(Arrays.asList(setvalues));	
	
											//modname,color,height,travel,spotd,rivero,rivere
	public static BiomeModifier river = new BiomeModifier("river",new int[]{1,2,3},-2,2,"",0d,0.5d); //river runs through here
	public static BiomeModifier road =  new BiomeModifier("road",new int[]{1,2,3},0,-1,"",0d,0d);
	
	private Biome next;

	private final Color color;
	private final int height;
	private final int travelcost;
	private final String spotdistance;
	private final double riverorigin;
	private final double riverend;
	private final String modname;	

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
	public boolean isValidStart()
	{
		return next.isValidStart();
	}
	
	@Override
	public Color parseColor(String rgb)
	{
		return next.parseColor(rgb);
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
	public Biome getBiome()
	{
		return next.getBiome();
	}
	
	@Override
	public Set<Biome> getBiomes()
	{
		Set<Biome> me = next.getBiomes();
		me.add(this);
		return me;
	}
}
