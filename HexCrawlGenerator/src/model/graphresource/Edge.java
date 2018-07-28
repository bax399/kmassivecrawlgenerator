//Code modified from: https://gist.github.com/smddzcy/bf8fc17dedf4d40b0a873fc44f855a58
package model.graphresource;

import java.util.Collections;
import java.util.*;

public class Edge<V extends Vertex> {
    private static final int DEFAULT_WEIGHT = 1;

    V v1, v2;
    int weight;

    public Edge(V v1, V v2) {
        this(v1, v2, DEFAULT_WEIGHT);
    }

    public Edge(V v1, V v2, int weight) {
        super();
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    public List<V> getVertexes()
    {
    	List<V> vertexes = new ArrayList<>();
    	vertexes.add(v1);
    	vertexes.add(v2);
    	return Collections.unmodifiableList(vertexes);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Edge)) return false;

        Edge<V> _obj = (Edge<V>) obj;
        return _obj.v1.equals(v1) && _obj.v2.equals(v2) &&
               _obj.weight == weight;
    }

    @Override
    public int hashCode() {
        int result = v1.hashCode();
        result = 31 * result + v2.hashCode();
        result = 31 * result + weight;
        return result;
    }
}
