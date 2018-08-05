package model;
import java.util.*;
import java.lang.*;
public abstract class Pathfinder {
	
	public static Comparator<FilledHex> costComparator = new Comparator<FilledHex>()
	{
		@Override
		public int compare(FilledHex h1, FilledHex h2)
		{
			return (int)( h1.priority-h2.priority);
		}
	};
	
	public abstract FilledHex earlyTermination(ConnectedHexMap chm, FilledHex goal, FilledHex curr);
	
	public abstract int getCost(ConnectedHexMap chm, FilledHex current, FilledHex next);
	
	public abstract int heuristic(ConnectedHexMap chm, FilledHex goal, FilledHex current, FilledHex next);
	
	public Set<Connection> GreedyBFS(ConnectedHexMap chm, FilledHex goal, FilledHex start)
	{
		PriorityQueue<FilledHex> frontier = new PriorityQueue<>(costComparator);
		frontier.add(start);
		Map<FilledHex,FilledHex> came_from = new HashMap<>();
		came_from.put(start,start);
		FilledHex current,next;
		Connection startconnection = new Connection(start,start,0);		
		while (frontier.size() > 0)
		{
			current = frontier.poll();
			
			goal = earlyTermination(chm,goal,current);
			
		    if (current.equals(goal))
		    {
			   break;
		    }
		    
		    Iterator<FilledHex> it = chm.neighbours(current).iterator();
		    while(it.hasNext())
		    {
		    	next = it.next();
			    if (!came_from.containsKey(next))
			    {
			    	 next.priority = heuristic(chm,goal,current,next); //+ heuristic(goal,next); //The heuristic
			         frontier.add(next);
			         came_from.put(next, current);	    	 
			    }
			         				
		    }
		}
		
		//TODO add this to a method
		Set<Connection> path_from = new HashSet<>();
		boolean foundstart = false;
		current = goal;
		while(!foundstart)
		{
			next = came_from.get(current);
			path_from.add(new Connection(current,next,getCost(chm,current,next)));
			if(next.equals(start))
			{
				foundstart = true;
				path_from.add(startconnection);
			}
			current = next;
		}
		
	    return path_from;		
	
	}
	
	//Tests if can reach goal in < resource distance.
	public Set<Connection> AStar(ConnectedHexMap chm, FilledHex goal, FilledHex start, int resource)
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
		    	new_cost = cost_so_far.get(current) + getCost(chm, current, next);
		    	if ((!cost_so_far.containsKey(next) || new_cost < cost_so_far.get(next)) && new_cost < resource) // stop if travelled too far.
		    	{
		    		cost_so_far.put(next, new_cost);
				    next.priority = new_cost + heuristic(chm,goal,current,next);
				    frontier.add(next);
				    came_from.put(next,current);						   
		    	}
		    }
		}
		
		
		Set<Connection> path_from = new HashSet<>();		
		if (came_from.containsKey(goal))
		{
			boolean foundstart = false;
			current = goal;
			
			while(!foundstart)
			{
				next = came_from.get(current);
				path_from.add(new Connection(current,next,getCost(chm, current, next)));
				if(next.equals(start))
				{
					foundstart = true;
					path_from.add(startconnection);
				}
				current = next;
			}
		}
		else //did not find its goal.
		{
			path_from=null;
		}
	
	    return path_from;		
	}	
	
	//Gets earliest termination within resource.
	public FilledHex Dijkstra(ConnectedHexMap chm, FilledHex start, int resource, Integer totalcost)
	{
		PriorityQueue<FilledHex> frontier = new PriorityQueue<>(costComparator);
		frontier.add(start);
		Map<FilledHex,FilledHex> came_from = new HashMap<>();
		Map<FilledHex,Integer> cost_so_far = new HashMap<>();
		came_from.put(start,start);
		cost_so_far.put(start, 0);
		
		FilledHex goal = null;
		FilledHex current,next;
		int new_cost;
		Connection startconnection = new Connection(start,start,0);
		while (frontier.size() > 0)
		{
		    current = frontier.poll();
		
		    goal = earlyTermination(chm,goal,current);
		    
		    if (current.equals(goal))
		    {
			   break; //found a roadnode.
		    }
		   
		    Iterator<FilledHex> it = chm.neighbours(current).iterator();
		    while(it.hasNext())
		    {
		    	next = it.next();
		    	new_cost = cost_so_far.get(current) + getCost(chm, current, next);
		    	if ((!cost_so_far.containsKey(next) || new_cost < cost_so_far.get(next)) && new_cost < resource)
		    	{
		    		cost_so_far.put(next, new_cost);
				    next.priority = new_cost;
				    frontier.add(next);
				    came_from.put(next,current);						   
		    	}
		    }
		}
		
		if (goal !=null && came_from.containsKey(goal))
		{
			boolean foundstart = false;
			current = goal;
			
			while(!foundstart)
			{
				next = came_from.get(current);
				if(next.equals(start))
				{
					foundstart = true;
				}
				current = next;
			}
			
			totalcost = cost_so_far.get(goal);
				
		}
		
	    return goal;
	    
	}

}
