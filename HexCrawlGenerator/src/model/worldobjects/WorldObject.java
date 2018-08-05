package model.worldobjects;
import java.util.*;
import model.*;
public abstract class WorldObject extends HasDescriptor implements WorldProperties
{
	private int visibility;
	private int max;
	
	public WorldObject(WorldDescriptor wd, int vis, int m)
	{
		super(wd);
		visibility = vis;
		max = m;
	}
	
	public int getMax()
	{
		return max;
	}
	
	public int getVisibility()
	{
		return visibility;
	}
	
	public void setMax(int m)
	{
		max = m;
	}
	
	public void setVisibility(int vis)
	{
		visibility = vis;
	}
}
