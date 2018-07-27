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
	
	public HexMap()
	{
		hexes = new LinkedHashMap<>();
	}
	
    public void addHex(V h)
    {
    	hexes.put(new Tuple(new int[] {h.q,h.r}), h);
    }
    
    public boolean containsHex(Hex h)
    {
    	return hexes.containsKey(new Tuple(new int[] {h.q,h.r}));
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
