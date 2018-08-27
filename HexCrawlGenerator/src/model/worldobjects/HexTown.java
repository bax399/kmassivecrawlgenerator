package model.worldobjects;
import model.FilledHex;
import model.HasDescriptor;
import model.WorldDescriptor;
import model.merowech.ConcaveHull.Point;
public class HexTown extends HasDescriptor 
{
	public final Town stats; //what this monster references whenever it is ask for anything.
	private final FilledHex origin; //originating hex
	private final Point pos; //final point.
	private int connectivity;
	
	public HexTown(Town type, FilledHex ohex, Point rpoint)
	{
		super(new WorldDescriptor(type.getName(), Town.tags, type.getDescription(), 10));
		stats = type;
		origin=ohex;
		pos=rpoint;
		ohex.add(this);
		connectivity=type.getConnectivity();
	}
	
	public Point getPosition()
	{
		return pos;
	}
	
	public int getConnectivity()
	{
		return connectivity;
	}
	
	public void setConnectivity(int con)
	{
		connectivity = con;
	}
}
