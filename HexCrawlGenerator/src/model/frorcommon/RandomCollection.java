//https://stackoverflow.com/questions/6409652/random-weighted-selection-in-java/30362366
package model.frorcommon;

import java.util.*;

public class RandomCollection<E> {
    private final NavigableMap<Double, E> map = new TreeMap<Double, E>();
    private final Random random;
    private double total = 0;

    public RandomCollection() {
        this(new Random());
    }

    public RandomCollection(Random random) {
        this.random = random;
    }

    public RandomCollection<E> add(double weight, E result) {
        if (weight <= 0) return this;
        if(!map.containsValue(result))
        {
	        total += weight;
	        map.put(total, result);
        }
        return this;
    }

    public E next() {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }
}