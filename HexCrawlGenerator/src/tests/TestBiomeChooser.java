package tests;
import java.util.*;
import model.BiomeChooser;
public class TestBiomeChooser 
{
	
	public static void main(String[] args)
	{
		String test = " A  : 2  ,C: 3,A :5 , all: 2 ";
		//String test = " A  : 2  ,B: 3, C: 2,all:5 ";
		//String test = " A  : 2  ,B: 3, all :5 , C: 2 ";		
		System.out.println(test);
		test= test.replaceAll("\\s+", "");
		System.out.println(test);
		String[] s = test.split(",");
		System.out.println(Arrays.toString(s));		
		s = BiomeChooser.putAllEnd(s);
		System.out.println(Arrays.toString(s));
	}
}
