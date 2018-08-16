package tests;
import java.nio.file.Path;
import java.nio.file.Paths;

import controller.FileProcessor;
import controller.PropertiesReader;
public class TestTypeReader {
	public static void main(String[] args)
	{
		String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		System.out.println("RootPath: "+rootPath);
		
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		System.out.println("Current relative path is: " + s);			
		
		FileProcessor fp = new FileProcessor();
		PropertiesReader ptr = new PropertiesReader();
		fp.processFile("inputTest.txt", ptr);
		
		System.out.println(ptr.getTypeList("biome").get(0));
	}
}
