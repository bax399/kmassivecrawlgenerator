package model;
import model.graphresource.Edge;
public class Connection extends Edge<FilledHex>
{
	public Connection(FilledHex v1, FilledHex v2, int weight)
	{
		super(v1, v2,weight);
	}
}
