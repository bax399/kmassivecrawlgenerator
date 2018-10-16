//Code modified from: https://gist.github.com/smddzcy/bf8fc17dedf4d40b0a873fc44f855a58
package model.graphresource;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Graph<V extends Vertex,E extends Edge<V>> 
{
    private Set<V> vertices;
    private Set<E> edges;
    private Map<V, Set<E>> adjList;

    public Graph() {
        vertices = new HashSet<V>();
        edges = new HashSet<E>();
        adjList = new HashMap<V,Set<E>>();
    }

    public boolean addVertex(V v) {
        return vertices.add(v);
    }

    public boolean addVertices(Collection<V> vertices) {
        return this.vertices.addAll(vertices);
    }

    public boolean removeVertex(Vertex v) {
        return vertices.remove(v);
    }

    public boolean addEdge(E e) {
        if (!edges.add(e)) return false;

        adjList.putIfAbsent(e.v1, new HashSet<>());
        adjList.putIfAbsent(e.v2, new HashSet<>());

        adjList.get(e.v1).add(e);
        adjList.get(e.v2).add(e);

        vertices.add(e.v1);
        vertices.add(e.v2);
        
        return true;
    }

    public boolean removeEdge(E e) {
        if (!edges.remove(e)) return false;
        Set<E> edgesOfV1 = adjList.get(e.v1);
        Set<E> edgesOfV2 = adjList.get(e.v2);

        if (edgesOfV1 != null) edgesOfV1.remove(e);
        if (edgesOfV2 != null) edgesOfV2.remove(e);

        return true;
    }

    public Set<V> getAdjVertices(V v) {
        return adjList.get(v).stream()
                      .map(e -> e.v1.equals(v) ? e.v2 : e.v1)
                      .collect(Collectors.toSet());
    }

    public Set<V> getVertices() {
        return Collections.unmodifiableSet(vertices);
    }

    public Set<E> getEdges() {
        return Collections.unmodifiableSet(edges);
    }

    public Map<V, Set<E>> getAdjList() {
        return Collections.unmodifiableMap(adjList);
    }
    
    public boolean containsVertex(V v)
    {
    	return vertices.contains(v);
    }
    
    public boolean containsEdge(E e)
    {
    	return edges.contains(e);
    }
}
