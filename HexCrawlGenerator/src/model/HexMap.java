package model;
import model.graphresource.*;
import model.redblob.Hex;
import model.redblob.Tuple;
import java.util.*;
//Citation: http://keekerdc.com/2011/03/hexagon-grids-coordinate-systems-and-distance-calculations/
//Assuming corner is bottom-left (0,0,0)
public class HexMap<V extends Hex>
{
	
	Map<Tuple, V> hexes; // Stores coordinates for each hex

	Graph<Hex,Connection> neighbours; // Stores each hex's neighbouring cells in hexagonal coordinates
	Graph<Hex,Road> roads; // Stores road connections
	Graph<Hex,River> rivers; // Stores river connections
	
	private int height;
	private int width;
	
	public HexMap()
	{
		hexes = new LinkedHashMap<>();
		neighbours = new Graph<>();
		roads = new Graph<>();
		rivers = new Graph<>();
	}
	 
	private static int hashCode(int q, int r)
	{
		int hq = Integer.hashCode(q);
		int hr = Integer.hashCode(r);

		return (hq ^ (hr + 0x9e3779b9 + (hq << 6) + (hq >> 2)));

	}
	
	private static int hashCode(Hex h)
	{
		return hashCode(h.q,h.r);
	}
	
    public void addHex(V h)
    {
    	hexes.put(new Tuple(new int[] {h.q,h.r}), h);
    }
    
    /* Can't work with generics.
    public void addHex(int q, int r)
    {
    	hexes.put(hashCode(q,r), new Hex(q,r));
    }*/
    
    public boolean containsHex(Hex h)
    {
    	return hexes.containsKey(hashCode(h));
    }
    
    public boolean containsHex(Integer hash)
    {
    	return hexes.containsKey(hash);
    }
    
    public V getHex(Hex h)
    {
    	return getHex(h.q,h.r);    	
    }
    
    public V getHex(int q, int r)
    {
    	return hexes.get(new Tuple(new int[] {q,r}));
    }
    
    public V getHex(Tuple tuple)
    {
    	return hexes.get(tuple);
    }
    
	public int getTotal()
	{
		return hexes.size();
	}
	
	public void initializeNeighbours()
	{	
		Set<Tuple> s = hexes.keySet();
		Iterator<Tuple> it = s.iterator();
		while(it.hasNext())
		{
			Hex each = hexes.get(it.next());
			neighbours.addVertex(each);
			
			for(int ii = 0; ii<6;ii++)
			{
				Hex n = each.neighbor(ii);
				if (hexes.containsValue(n))
				{
					neighbours.addEdge(new Connection(each, n));
				}
				
				
			}
			
		}
	}		
	
	@Override
	public String toString()
	{
		String output ="";
		Set<Tuple> ss = hexes.keySet();
		Iterator<Tuple> it = ss.iterator();
		while(it.hasNext())
		{
			Hex hh = hexes.get(it.next());
			output+=hh.toString() + " | ";
		}
		return output;
	}
	
	public Map<Tuple,V> getHexes()
	{
		return hexes;
	}
}
