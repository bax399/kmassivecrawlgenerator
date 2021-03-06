//https://stackoverflow.com/questions/6409652/random-weighted-selection-in-java/30362366
package model.frorcommon;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

public class RandomCollection<E> {
    private final NavigableMap<Double, E> map = new TreeMap<Double, E>();
    private final Set<E> processed = new HashSet<>();
    private final Random random;
    private double total = 0;

    public RandomCollection() {
        this(new Random());
    }

    public RandomCollection(Random random) {
        this.random = random;
    }

    public RandomCollection<E> add(double weight, E result) {
    	if (processed.add(result))
    	{
	        if (weight <= 0) return this;
	        if(!map.containsValue(result))
	        {
		        total += weight;
		        map.put(total, result);
	        }
    	}
        return this;
    	
    }

    public E next() {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }
    
    public Set<Entry<Double,E>> getValues()
    {
    	return map.entrySet();
    }
}