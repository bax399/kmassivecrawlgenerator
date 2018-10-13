package model;
import java.util.Set;

//Anything that properties input, the object is expected to use.
public interface RegionProperties extends BasicProperties
{
	public int getMax();
	public int getMin();
}
