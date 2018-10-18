package controller;
import java.util.Random;

import model.ConnectedHexMap;
import model.FilledHex;
public abstract class Generator 
{
	private Random rand;
	
	public Generator(Random rd)
	{
		rand=rd;
	}
	
	public Random getRand()
	{
		return rand;
	}
	
	public boolean rollChance(double chance)
	{
		boolean succeeds = false;
		double randd = rand.nextDouble();
		
		if(Double.compare(chance,0d)<0)
		{
			succeeds=true; //Auto success if chance is negative.
		}
		if(Double.compare(chance,1d)>=0)
		{
			succeeds=true; //auto success if chance is >1.0 (100%)
		}
		else
		if(Double.compare(chance,0d)>0 && Double.compare(chance, 1d)<0) 
		{
			succeeds = (Double.compare(randd,chance) < 0); //if random int is within 0->chance range, succeeds.
		}
		
		return succeeds;
	}	
}
