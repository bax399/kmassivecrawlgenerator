package model.worldobjects;
import java.util.*;
import model.*;
public abstract class WorldObject extends HasDescriptor
{
	private int visibility;
	
	public WorldObject(WorldDescriptor wd, int vis)
	{
		super(wd);
		visibility = vis;
	}
	
	public int getVisibility()
	{
		return visibility;
	}
	
	public void setVisibility(int vis)
	{
		visibility = vis;
	}
}
