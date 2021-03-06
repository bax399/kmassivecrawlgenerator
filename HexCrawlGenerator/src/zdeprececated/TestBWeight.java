package zdeprececated;
import java.util.ArrayList;
import java.util.Arrays;
public class TestBWeight 
{
	public static void main(String[] args)
	{
		BWeight bweight = new BWeight(null, null);
		ArrayList<Integer> test = new ArrayList<>(Arrays.asList(1,0,0,2,8,5,7,0,0,1,0));
		ArrayList<Integer> test2 = new ArrayList<>(Arrays.asList(2,0,0,2,4,0,8,2,10,0,10,0));
		
		System.out.println(test);
		System.out.println(test2);
		
		test = BWeight.incrementWeights(test);
		test2 = BWeight.incrementWeights(test2);
		System.out.println(test);
		System.out.println(test2);
		
		
		int w1 = bweight.rollWeights(test);
	    int w2 = bweight.rollWeights(test2);
		System.out.println(w1);
		System.out.println(w2);
		
		System.out.println(bweight.indexWeight(test, w1));
		System.out.println(bweight.indexWeight(test2, w2));
		
		ArrayList<Integer> test3 = new ArrayList<>(Arrays.asList(0,0,1,5,0,1));
		System.out.println(bweight.indexWeight(test3, 1));
	}
}
