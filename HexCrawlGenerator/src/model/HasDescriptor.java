package model;
import java.util.Collection;
import java.util.Set;
public abstract class HasDescriptor 
{
	private WorldDescriptor descriptor;
	public HasDescriptor(WorldDescriptor wd)
	{
		descriptor = wd;
	}
	
	public WorldDescriptor getDescriptor()
	{
		return descriptor;
	}
	
	public void setDescriptor(WorldDescriptor wd)
	{
		descriptor = wd;
	}
	
	public void setName(String name)
	{
		descriptor.name=name;
	}
	
	public void setTags(Collection<String> t)
	{
		descriptor.tags.addAll(t);
	}
	
	public void setTags(String t)
	{
		descriptor.tags.add(t);
	}
	
	public void setDescription(String desc)
	{
		descriptor.description = desc;
	}
	
	public void setImportance(int imp)
	{
		descriptor.importance=imp;
	}
	
	public String getName()
	{
		return descriptor.name;
	}

	public Set<String> getTags()
	{
		return descriptor.tags;
	}
	
	public String getDescription()
	{
		return descriptor.description;
	}
	
	public int getImportance()
	{
		return descriptor.importance;
	}	
}
