package model;
import model.graphresource.*;
import model.redblob.Hex;
import model.redblob.Point;
public class Connection extends Edge<FilledHex>{
	public Connection(FilledHex v1, FilledHex v2, int weight)
	{
		super(v1, v2,weight);
	}
	
	public boolean containsHex(FilledHex h1)
	{
		return getVertexes().contains(h1);
	}
}
