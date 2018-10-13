package model.worldobjects;
import model.BasicProperties;
//Anything that properties input, the object is expected to use.
public interface WorldProperties extends BasicProperties
{
	public int getVisibility();
	public int getMax();
}
