package ztests;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;


public 	class PrimeNumbersTask extends SwingWorker<List<Integer>, Integer> 
{
	
		JTextArea textArea;
		int numbersToFind=10;
		PrimeNumbersTask(JTextArea textArea, int numbersToFind) 
	     {
	         //initialize
	    	 this.textArea = textArea;
	    	 this.numbersToFind = numbersToFind;
	     }
	
		 public Integer nextPrimeNumber(Integer number)
		 {
			 int counter;
			  number++;   
			  while(true){
			    counter = 0;
			    for(int i = 2; i <= Math.sqrt(number); i ++){
			      if(number % i == 0)  counter++;
			    }
			    if(counter == 0)
			      return number;
			    else{
			      number++;
			      continue;
			    }
			  }
		 }
		
	     @Override
	     public List<Integer> doInBackground() 
	     {
	    	 Integer number=0;
	    	 boolean enough = false;
	    	 List<Integer> numbers = new ArrayList<>();
	         while (!enough && ! isCancelled()) 
	         {
	                 number = nextPrimeNumber(number);
	                 numbers.add(number);	                 
	                 publish(number);
	                 try
	                 {
	                 Thread.sleep(10);
	                 }
	                 catch(Exception e) {}
	                 setProgress(100 * numbers.size() / numbersToFind);

	                 if (number > numbersToFind)
	                 {
	                	 enough=true;
	                 }
	         }
	         return numbers;
	     }
	
	     @Override
	     protected void process(List<Integer> chunks) 
	     {
	         for (int number : chunks) 
	         {
	             textArea.append(number + "\n");
	         }
	     }
	 
	 
	public static void main(String[] args)
	{	
		 JFrame f = new JFrame("Ouch");
		 
		 JTextArea textArea = new JTextArea();
		 textArea.setBounds(0,0,100,1000);
		 final JProgressBar progressBar = new JProgressBar(0, 100);	 
		 f.add(textArea);
		 //f.add(progressBar);
		 f.setVisible(true);
		 f.setSize(1000, 400);
		 
		 int N = 1000;
		 PrimeNumbersTask task = new PrimeNumbersTask(textArea, N);
		 task.addPropertyChangeListener(
		     new PropertyChangeListener() {
		         public  void propertyChange(PropertyChangeEvent evt) {
		             if ("progress".equals(evt.getPropertyName())) {
		                 progressBar.setValue((Integer)evt.getNewValue());
		             }
		         }
		     });
		
		 task.execute();
		 try
		 {
		 System.out.println(task.get()); //prints all prime numbers we have got
		 }
		 catch (Exception e)
		 {
			 System.out.println("ouch");
		 }
	 }
}