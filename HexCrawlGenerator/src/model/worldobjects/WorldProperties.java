package model.worldobjects;
import model.HasProperties;
//Anything that properties input, the object is expected to use.
public interface WorldProperties extends HasProperties
{
	public int getVisibility();
	public int getMax();
}
