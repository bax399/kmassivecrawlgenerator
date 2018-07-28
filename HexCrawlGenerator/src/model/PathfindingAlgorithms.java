package model;
import java.util.*;
import java.lang.*;
public class PathfindingAlgorithms {
	
	public static Comparator<FilledHex> costComparator = new Comparator<FilledHex>()
	{
		@Override
		public int compare(FilledHex h1, FilledHex h2)
		{
			return (int)( h1.priority-h2.priority);
		}
	};
	
	public Set<Connection> Dijkstra(ConnectedHexMap chm, FilledHex goal, FilledHex start)
	{
		PriorityQueue<FilledHex> frontier = new PriorityQueue<>(costComparator);
		frontier.add(start);
		Map<FilledHex,FilledHex> came_from = new HashMap<>();
		Map<FilledHex,Integer> cost_so_far = new HashMap<>();
		came_from.put(start,start);
		cost_so_far.put(start, 0);
		
		FilledHex current,next;
		int new_cost;
		Connection startconnection = new Connection(start,start,0);
		while (frontier.size() > 0)
		{
		    current = frontier.poll();
		
		    if (current.equals(goal))
		    {
			   break;
		    }
		   
		    Iterator<FilledHex> it = chm.neighbours(current).iterator();
		    while(it.hasNext())
		    {
		    	next = it.next();
		    	new_cost = cost_so_far.get(current) + chm.adjCost(current, next);
		    	if (!cost_so_far.containsKey(next) || new_cost < cost_so_far.get(next))
		    	{
		    		cost_so_far.put(next, new_cost);
				    next.priority = new_cost;
				    frontier.add(next);
				    came_from.put(next,current);						   
		    	}
		    	
		    }
		    

		}
		
		Set<Connection> path_from = new HashSet<>();
		boolean foundstart = false;
		current = goal;
		while(!foundstart)
		{
			next = came_from.get(current);
			path_from.add(new Connection(current,next,chm.adjCost(current, next)));
			if(next.equals(start))
			{
				foundstart = true;
				path_from.add(startconnection);
			}
			current = next;
		}
		
	    return path_from;
	    
	}

}
