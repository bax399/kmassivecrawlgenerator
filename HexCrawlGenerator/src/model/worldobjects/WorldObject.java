package model.worldobjects;
import java.util.*;
import model.*;
public abstract class WorldObject extends HasDescriptor
{
	public abstract int getVisibility();
	public WorldObject(WorldDescriptor wd)
	{
		super(wd);
	}
}
