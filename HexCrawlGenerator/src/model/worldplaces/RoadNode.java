package model.worldplaces;
import model.Point;
import model.RoadNetwork;

public class RoadNode 
{
	private Point pos;
	private RoadNetwork owner=null;
	
	public RoadNode(RoadNetwork rn, Point p)
	{
		owner = rn;
		pos = p;
	}

	public void setNetwork(RoadNetwork rn)
	{
		owner = rn;
	}
	
	public RoadNetwork getNetwork()	
	{
		return owner;
	}
	
	public Point getPosition()
	{
		return pos;
	}
}
