package model.worldplaces;
import model.Network;
import model.Point;
import model.graphresource.Vertex;
public class NetworkNode extends Vertex
{
	private Point pos;
	private Network owner=null;
	private int size=1;
	
	public NetworkNode(Network rn, Point p)
	{
		super();
		owner = rn;
		pos = p;
	}

	public void incrementSize(int amount)
	{
		size+=amount;
	}
	
	public int getSize() 
	{
		return size;
	}

	public void setSize(int size) 
	{
		this.size = size;
	}

	public void setPoint(Point p)
	{
		pos = p;
	}
	
	public void setNetwork(Network rn)
	{
		owner = rn;
	}
	
	public Network getNetwork()	
	{
		return owner;
	}
	
	public Point getPosition()
	{
		return pos;
	}
}
