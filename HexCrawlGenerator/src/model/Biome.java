package model;
import java.awt.Color;
import java.util.*;
public class Biome extends HasDescriptor implements BiomeProperties{
	public static final String[] setvalues = {"biome"};
	public static final Set<String> tags = new HashSet<>(Arrays.asList(setvalues));
	
	public static Biome basic = new Biome("basic",new int[] {0,40,255},0,0,1,0,0); //default biome.
	
	private Color color;
	private int height;
	private int travelcost;
	private int spotdistance;
	private double riverorigin;
	private double riverend;
	
	public Biome(Properties pp)
	{
		super(new WorldDescriptor(pp.getProperty("name"), Biome.tags, pp.getProperty("name"), 10));
		processProperties(pp);
	}

	
	
	public void processProperties(Properties pp)
	{
		color = parseColor(pp.getProperty("color"));
		height = Integer.parseInt(pp.getProperty("height"));
		travelcost = Integer.parseInt(pp.getProperty("travelcost"));
		spotdistance = Integer.parseInt(pp.getProperty("spotdistance"));
		riverorigin = Double.parseDouble(pp.getProperty("riverorigin"));
		riverend = Double.parseDouble(pp.getProperty("riverend"));
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
	}
	
	public Biome(Biome biome)
	{
		super(biome.getDescriptor());
		this.color = biome.color;
		this.height = biome.height;
		this.travelcost = biome.travelcost;
		this.riverorigin = biome.riverorigin;
		this.riverend = biome.riverend;
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
