package model.worldplaces;
import model.Point;
import model.RiverNetwork;
public class RiverNode 
{
	private Point pos;
	private RiverNetwork owner;
	
	public RiverNode(RiverNetwork rn, Point p)
	{
		owner = rn;
		pos = p;
	}

	public void setNetwork(RiverNetwork rn)
	{
		owner = rn;
	}
	
	public RiverNetwork getNetwork()	
	{
		return owner;
	}
	
	public Point getPosition()
	{
		return pos;
	}
	
	
}
