package model.worldplaces;
import model.FilledHex;
import model.Point;
import model.stats.StatsTown;
public class HexTown extends WorldPlace
{
	public final StatsTown stats; //what this object references whenever it is ask for anything.
	private final FilledHex origin; //originating hex
	private final Point pos; //final point.
	private int connectivity;
	
	public HexTown(StatsTown type, FilledHex ohex, Point rpoint)
	{
		stats = type;
		origin=ohex;
		pos=rpoint;
		ohex.add(this);
		connectivity=type.getConnectivity();
	}
	
	public FilledHex getHex()
	{
		return origin;
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
