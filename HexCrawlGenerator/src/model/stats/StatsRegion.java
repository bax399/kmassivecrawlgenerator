package model.stats;
import java.util.*;

import model.properties.RegionProperties;
public class StatsRegion extends PropertyStats implements RegionProperties 
{
	public static final String[] setvalues = {"region"};
	public static final Set<String> tags = new HashSet<>(Arrays.asList(setvalues));

	private final int min;
	private final int max;
	
	public StatsRegion(int min, int max)
	{	
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
