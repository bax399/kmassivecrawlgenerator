package controller;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class AbstractDataReader<E> 
{
	protected abstract E processline(String[] line, E storage, int linenum);
	//Format:
	//<ID>,<Strat1>,<Strat2>,<...9>
	public E readFile(String filename, E inStorage)
	{
		FileReader fr=null;
		BufferedReader br=null;
		String line;
		String[] csvline;
		int linenum=-1; 
		
		E storage = inStorage;
		
		try
		{
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
			
			//Assuming not reading header lines.
			line = br.readLine();
			linenum++;
			while(line!=null)
			{				
				//Process 				
				csvline = line.split(",");

				storage = processline(csvline, storage, linenum);
				
				line = br.readLine();	
				linenum++;
			}

		}
		catch(IOException e)
		{
			e.printStackTrace();
			System.out.println("Plant Read Error.");
		}
		finally
		{
			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}			
		}
		
		return storage;
	}
	

}
