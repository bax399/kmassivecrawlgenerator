package tests;
import java.util.*;

import model.frorcommon.*;
public class TestRandomSelector 
{
	static Random random = new Random();	
	public static void main(String[]args)
	{

	  Map<String, Double> stringWeights = new HashMap<>();
	  stringWeights.put("a", 1d);
	  stringWeights.put("b", 8d);
	  stringWeights.put("c", 1d);
	  
	  RandomSelector<String> selector = RandomSelector.weighted(stringWeights.keySet(),s -> stringWeights.get(s));
	  List<String> selection = new ArrayList<>();
	  for (int i = 0; i < 100; i++) {
	    selection.add(selector.next(random));
	  }
	  System.out.println(selection);
	}
}
