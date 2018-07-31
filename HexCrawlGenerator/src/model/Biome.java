package model;
import java.awt.Color;
import java.util.*;
public class Biome extends HasDescriptor implements BiomeProperties{
	public static final String[] setvalues = {"biome"};
	public static final Set<String> tags = new HashSet<>(Arrays.asList(setvalues));
	
	public static Biome basic = new Biome("basic",new int[] {0,40,255},0,0,1,0,0); //default biome.
	
	private final Color color;
	private final int height;
	private final int travelcost;
	private final int spotdistance;
	private final double riverorigin;
	private final double riverend;
	private final String biomename; //the unmodifiable name of the biome, must refer to this, not getName, as that can change.
	private final String weight;
	
	public Biome(Properties pp)
	{
		super(new WorldDescriptor(pp.getProperty("name"), Biome.tags, pp.getProperty("name"), 10));
		color = parseColor(pp.getProperty("color"));
		height = Integer.parseInt(pp.getProperty("height"));
		travelcost = Integer.parseInt(pp.getProperty("travelcost"));
		spotdistance = Integer.parseInt(pp.getProperty("spotdistance"));
		riverorigin = Double.parseDouble(pp.getProperty("riverorigin"));
		riverend = Double.parseDouble(pp.getProperty("riverend"));
		biomename=pp.getProperty("name");
		weight=pp.getProperty("weight").toLowerCase();
	}
	
	public Biome(String n, int[] c, int h, int tc, int sd, double ro, double re)
	{
		super(new WorldDescriptor(n,Biome.tags,n,0));
		color = new Color(c[0],c[1],c[2]);
		height = h;
		travelcost = tc;
		riverorigin = ro;
		riverend = re;
		spotdistance = sd;
		biomename=n;
		weight=n+":"+"1";
	}
	
	public Color parseColor(String rgb)
	{
		String[] cc = rgb.split(",");
		int[] Irgb = new int[3];
		
		for(int ii=0;ii<3;ii++)
		{
			Irgb[ii]=Integer.parseInt(cc[ii]);
		}
		return new Color(Irgb[0],Irgb[1],Irgb[2]);
	}
	
	public String getWeight()
	{
		return weight;
	}
	
	//returns the "true" biome name, the one made at creation. this can't be modified!
	public String getBiomeName()
	{
		return biomename;
	}
	
	public Color getColor()
	{
		return color;
	}

	public int getTravelCost()
	{
		return travelcost;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getSpotDistance()
	{
		return spotdistance;
	}
	
	public String getDescription()
	{
		return "nodescriptionaddedtobiomes";
	}
	
	public int getRiverEnd()
	{
		return 1;
	}	
	
	public int getRiverOrigin()
	{
		return 1;
	}
	
	@Override
	public String toString()
	{
		return super.getName();
	}
	
	
}
