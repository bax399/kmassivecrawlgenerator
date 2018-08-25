package model;
import java.util.*;

import model.worldobjects.WorldObject;
public class Region extends WorldObject implements RegionProperties 
{

	
	public static final String[] setvalues = {"region"};
	public static final Set<String> tags = new HashSet<>(Arrays.asList(setvalues));

	private final int min;
	private final int max;
	
	public Region(Properties pp)
	{
		super(new WorldDescriptor(pp.getProperty("name"), Region.tags, pp.getProperty("name"), 10),
				Integer.parseInt(pp.getProperty("visibility")),Integer.parseInt(pp.getProperty("max")));	
		min = Integer.parseInt(pp.getProperty("min"));
		max = Integer.parseInt(pp.getProperty("max"));
	}
	
	public Region(String n, int vis, int m, int min, int max)
	{
		super(new WorldDescriptor(n, Region.tags, n, 10),vis,m);
		
		this.min = min;
		this.max = max;
	}	
	
	@Override
	public int getMax() {
		// TODO Auto-generated method stub
		return max;
	}

	@Override
	public int getMin() {
		// TODO Auto-generated method stub
		return min;
	}
	
}
