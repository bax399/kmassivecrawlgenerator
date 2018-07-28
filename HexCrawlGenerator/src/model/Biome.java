package model;
import java.awt.Color;
import java.util.Properties;

public class Biome {
	String name;
	Color color;
	int height;
	int travelcost;
	int spotdistance;
	double riverorigin;
	double riverend;

	
	public Biome(Properties pp)
	{
		name = pp.getProperty("name");
		color = parseColor(pp.getProperty("color"));
		height = Integer.parseInt(pp.getProperty("height"));
		travelcost = Integer.parseInt(pp.getProperty("travelcost"));
		spotdistance = Integer.parseInt(pp.getProperty("spotdistance"));
		riverorigin = Double.parseDouble(pp.getProperty("riverorigin"));
		riverend = Double.parseDouble(pp.getProperty("riverend"));
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
	
	public Biome(String n, int[] c, int h, int tc, int sd, double ro, double re)
	{
		name = n;
		color = new Color(c[0],c[1],c[2]);
		height = h;
		travelcost = tc;
		riverorigin = ro;
		riverend = re;
		spotdistance = sd;
	}
	
	public Biome(Biome biome)
	{
		this.name = biome.name;
		this.color = biome.color;
		this.height = biome.height;
		this.travelcost = biome.travelcost;
		this.riverorigin = biome.riverorigin;
		this.riverend = biome.riverend;		
	}
	
	public String toString()
	{
		return name;
	}
}
