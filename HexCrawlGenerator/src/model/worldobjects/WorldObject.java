package model.worldobjects;
import model.HasDescriptor;
import model.WorldDescriptor;
public abstract class WorldObject extends HasDescriptor implements WorldProperties
{
	private int visibility;
	private int max;
	
	public WorldObject(WorldDescriptor wd, int visibility, int max)
	{
		super(wd);
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
