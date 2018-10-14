package ztests;
import java.util.Arrays;

import model.BiomeChooser;
public class TestBiomeChooser 
{
	
	public static void main(String[] args)
	{
		//String test = " all  : 2  ,C: 3,A :5 , A: 2 ";
		String test = " A:2, C:3, all:5, B:1";
		//String test = " A  : 2  ,B: 3, C: 2,all:5 ";
		//String test = " all  : 2  ,B: 3, all :5 , C: 2 ";		
		//String test = " all  : 2  ,B: 3, C :5 , all: 2 ";		
		System.out.println(test);
		test= test.replaceAll("\\s+", "");
		System.out.println(test);
		String[] s = test.split(",");
		System.out.println(Arrays.toString(s));		
		s = BiomeChooser.putAllEnd(s);
		System.out.println(Arrays.toString(s));
	}
}
