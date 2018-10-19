package model;

import java.util.HashSet;
import java.util.Set;

import functions.KFunctions;
import model.graphresource.Edge;
import model.graphresource.Graph;
import model.worldplaces.NetworkNode;

public abstract class Network
{
	
	private Graph<NetworkNode,NetworkConnection> graph;
	private Set<FilledHex> hexes;
	
	public Network()
	{
		graph=new Graph<>();
		hexes=new HashSet<>();
	}
	
	public abstract Network createNetwork(ConnectedHexMap chm, Set<Edge<FilledHex>> path,Set<Network> networks);
	
	public void joinNetworks(Network otherNetwork,Set<Network> networks)
	{	
		//Change the pointers to the river network from rne to THIS
		for(NetworkConnection connect : otherNetwork.getNetworkConnections())
		{
			graph.addEdge(connect);
			connect.getVertexes().get(1).setNetwork(this);
			connect.getVertexes().get(0).setNetwork(this);	
		}
		
		//Deletes the old network. 
		networks.remove(otherNetwork);		
	}
	
	public boolean areNeighbours(NetworkNode n1, NetworkNode n2)
	{
		boolean neighbours = false;

		if (graph.containsEdge(new NetworkConnection(n1,n2,1)))
		{
				neighbours=true;
		}
	
		
		return neighbours;
	}
	
	public boolean containsHex(FilledHex origin)
	{
		return hexes.contains(origin);
	}
	
	public void addNode(NetworkNode node,FilledHex origin)
	{
		node.setNetwork(this);
		hexes.add(origin);
		graph.addVertex(node);
	}
	
	public void addConnection(NetworkConnection connect)
	{
		graph.addEdge(connect);
	}
	
	public Graph<NetworkNode,NetworkConnection> getGraph()
	{
		return graph;
	}
	
	public Set<FilledHex> getHexes()
	{
		return hexes;
	}
	
	public Set<NetworkNode> getNodes()
	{
		return graph.getVertices();
	}
	
	public Set<NetworkConnection> getNetworkConnections()
	{
		return graph.getEdges();
	}
	
//	public Set<Network> getNetworks()
//	{
//		return allNetworks;
//	}
//	
//	public void setNetworks(Set<Network> networks)
//	{
//		allNetworks=networks;
//	}
}
