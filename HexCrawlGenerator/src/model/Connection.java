package model;
import model.graphresource.*;
import model.redblob.Hex;
public class Connection extends Edge<Hex>{
	public Connection(Hex v1, Hex v2)
	{
		super(v1, v2);
	}	
}
