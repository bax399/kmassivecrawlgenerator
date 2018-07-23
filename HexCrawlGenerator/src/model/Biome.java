package model;
import java.awt.Color;
public class Biome {
	String name;
	Color color;
	int height;
	int travelcost;
	double riverorigin;
	double riverend;
	
	public Biome(String n, int[] c, int h, int tc, double ro, double re)
	{
		name = n;
		color = new Color(c[0],c[1],c[2]);
		height = h;
		travelcost = tc;
		riverorigin = ro;
		riverend = re;
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
}
