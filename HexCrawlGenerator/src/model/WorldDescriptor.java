package model;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
public class WorldDescriptor implements Serializable
{
	private String name;
	private Set<String> tags;
	private String description;
	private  int importance; //based on visibility, connectivity and other factors.
	
	public WorldDescriptor()
	{
		tags = new HashSet<>();
	}
	
	public WorldDescriptor(String n, Collection<String> t, String d, int i)
	{
		name = n.toLowerCase();
		tags = new HashSet<>();
		tags.addAll(t);
		description = d;
		importance = i;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getImportance() {
		return importance;
	}

	public void setImportance(int importance) {
		this.importance = importance;
	}
}
