package model.worldplaces;
import model.Point;
import model.graphresource.Vertex;
public class NetworkNode<E> extends Vertex
{
	private Point pos;
	private E owner=null;
	private int size=1;
	
	public NetworkNode(E rn, Point p)
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

	public void setNetwork(E rn)
	{
		owner = rn;
	}
	
	public E getNetwork()	
	{
		return owner;
	}
	
	public Point getPosition()
	{
		return pos;
	}
}
