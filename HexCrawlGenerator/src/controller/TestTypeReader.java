package controller;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
public class TestTypeReader {
	public static void main(String[] args)
	{
		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		System.out.println("RootPath: "+rootPath);
		
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		System.out.println("Current relative path is: " + s);			
		
		FileProcessor fp = new FileProcessor();
		PropertyTypeReader ptr = new PropertyTypeReader();
		fp.processFile("inputTest.txt", ptr);
	}
}
