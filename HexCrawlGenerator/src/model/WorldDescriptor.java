package model;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
public class WorldDescriptor 
{
	public String name;
	public Set<String> tags;
	public String description;
	public int importance; //based on visibility, connectivity and other factors.
	
	public WorldDescriptor(String n, Collection<String> t, String d, int i)
	{
		name = n.toLowerCase();
		tags = new HashSet<>();
		tags.addAll(t);
		description = d;
		importance = i;
	}
}
