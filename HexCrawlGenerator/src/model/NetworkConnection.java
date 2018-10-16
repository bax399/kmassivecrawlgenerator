package model;
import model.graphresource.Edge;
import model.worldplaces.NetworkNode;
public class NetworkConnection extends Edge<NetworkNode>
{
	public NetworkConnection(NetworkNode v1, NetworkNode v2, int weight)
	{
		super(v1, v2,weight);
	}
}
