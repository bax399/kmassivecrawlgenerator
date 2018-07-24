package controller;
import java.util.*;
import java.io.*;
public class TypeReader {

	//Reads default Properties from file, then each type 
	Properties defaultBiome;
	Properties defaultBWeight;
	
	Properties defaultMonsterStat;
	
	Properties defaultLocation;
	//Locations
	Properties defaultDungeon;
	Properties defaultLair;
	Properties defaultLandmark;
	Properties defaultNomad;
	Properties defaultSite;
	
	
	
	public TypeReader() {}
	
	//While line is not empty, read into a WorldObject class
	//this is then passed to the appropriate property readers for each WorldObject.type 
	public void processString(BufferedReader br) {}
	public void processLine(String line) {}
}
