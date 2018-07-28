package model;
import model.graphresource.*;
import model.redblob.Hex;
import model.redblob.Point;
public class Connection extends Edge<FilledHex>{
	public Connection(FilledHex v1, FilledHex v2)
	{
		super(v1, v2);
	}
}
