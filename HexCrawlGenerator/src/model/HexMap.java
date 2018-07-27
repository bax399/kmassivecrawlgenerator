package model;
import model.graphresource.*;
import model.redblob.Hex;

import java.util.*;
//Citation: http://keekerdc.com/2011/03/hexagon-grids-coordinate-systems-and-distance-calculations/
//Assuming corner is bottom-left (0,0,0)
public class HexMap<V extends Hex>
{
	
	Map<Integer, V> hexes; // Stores coordinates for each hex

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
		System.out.println("done");		
	}
	
    private static int hashCode(Hex h)
    {
        return 383 * Arrays.hashCode(new int[]{h.q, h.r, -h.q-h.r});
    }
    
    private static int hashCode(int q, int r)
    {
    	return 383 * Arrays.hashCode(new int[] {q,r,-q-r});
    }
	
    public void addHex(V h)
    {
    	hexes.put(hashCode(h), h);
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
    	return hexes.get(hashCode(q,r));
    }
    
    public V getHex(int hash)
    {
    	return hexes.get(hash);
    }
    
	public int getTotal()
	{
		return hexes.size();
	}
	
	public void initializeNeighbours()
	{	
		Set<Integer> s = hexes.keySet();
		Iterator<Integer> it = s.iterator();
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
		Set<Integer> ss = hexes.keySet();
		Iterator<Integer> it = ss.iterator();
		while(it.hasNext())
		{
			Hex hh = hexes.get(it.next());
			output+=hh.toString() + " | ";
		}
		return output;
	}
	
	public Map<Integer,V> getHexes()
	{
		return hexes;
	}
}
