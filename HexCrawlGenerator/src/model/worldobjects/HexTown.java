package model.worldobjects;
import java.util.*;
import model.FilledHex;
import model.HasDescriptor;
import model.WorldDescriptor;
import model.redblob.Point;
public class HexTown extends HasDescriptor 
{
	public final Town stats; //what this monster references whenever it is ask for anything.
	private final FilledHex origin; //originating hex
	private final Point pos; //final point.
	
	public HexTown(Town type, FilledHex ohex, Point rpoint)
	{
		super(new WorldDescriptor(type.getName(), Town.tags, type.getDescription(), 10));
		stats = type;
		origin=ohex;
		pos=rpoint;
		ohex.add(this);
	}
	
	public Point getPosition()
	{
		return pos;
	}	
}
