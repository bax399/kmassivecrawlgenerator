package zdeprececated;
import java.util.Set;

import model.WorldDescriptor;
public abstract class WorldObject extends WorldDescriptor implements WorldProperties
{
	private int visibility;
	private int max;
	
	public WorldObject(int visibility, int max, String name, Set<String> tags, String description, int priority)
	{
		super(name,tags,description,priority);
		this.visibility = visibility;
		this.max = max;
	}
	
	public int getMax()
	{
		return max;
	}
	
	public int getVisibility()
	{
		return visibility;
	}
	
	public void setMax(int max)
	{
		this.max = max;
	}
	
	public void setVisibility(int visibility)
	{
		this.visibility = visibility;
	}
}
