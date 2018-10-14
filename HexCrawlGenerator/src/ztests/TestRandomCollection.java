package ztests;
import model.frorcommon.RandomCollection;
public class TestRandomCollection {

	public static void main(String args[])
	{
		RandomCollection<String> rc = new RandomCollection<String>()
                .add(40, "dog").add(1000, "cat").add(25, "horse");

		for (int i = 0; i < 100; i++) 
		{
			System.out.println(rc.next());
		} 
	}
}
