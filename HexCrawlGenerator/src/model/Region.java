package model;
import java.util.*;

import model.worldobjects.WorldObject;
public class Region extends WorldObject implements RegionProperties 
{

	
	public static final String[] setvalues = {"region"};
	public static final Set<String> tags = new HashSet<>(Arrays.asList(setvalues));

	private final int min;
	private final int max;
	
	public Region(String n, int vis, int m, int min, int max)
	{
		super(vis,m, n, Region.tags, n, 10);
		
		this.min = min;
		this.max = max;
	}	
	
	@Override
	public int getMax() {
		return max;
	}  

	@Override
	public int getMin() {
		return min;
	}
	
	
	
}
