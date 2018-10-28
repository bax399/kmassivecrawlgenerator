package ztests;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import model.Point;
import model.redblob.Hex;
import model.redblob.Layout;


public class MainWorker
{
	final static double screenwidth = 1920d;
	final static double screenheight = 1080d;
	class HexagonASyncTask extends SwingWorker<List<Polygon>, Polygon> 
	{

			TestPane testpane;
			HexagonASyncTask(TestPane testpane) 
		     {
		         //initialize
				this.testpane = testpane;
		     }
		
			
		     @Override
		     public List<Polygon> doInBackground() 
		     {
		        Layout layout = new Layout(Layout.flat, new Point(2d,2d),new Point(screenwidth/2,screenheight/2));		    	 
		    	int map_radius=200; 
		    	List<Polygon> shapes = new ArrayList<>();
		    	for (int q = -map_radius; q <= map_radius; q++) 
	    		{
	    		    int r1 = Math.max(-map_radius, -q - map_radius);
	    		    int r2 = Math.min(map_radius, -q + map_radius);
	    		    for (int r = r1; r <= r2; r++) 
	    		    {
	    		    	//Simulated longer process than it actually is.
//	    		    	try
//	    		    	{
//	    		    	Thread.sleep(1);
//	    		    	}catch(Exception e) {}
		  		    	Hex hh = new Hex(q,r);
		  				Polygon a = layout.polygonCorners(hh);
		  				shapes.add(a);
		  				publish(a);

	    		    }
	    		}
		    	
		    	return shapes;
		     }
		
		     @Override
		     protected void process(List<Polygon> chunks) 
		     {
		         for (Polygon shape : chunks) 
		         {
		             testpane.addShape(shape);
		         }
		     }
	}
	
	class TestPane extends JPanel 
	{
	
	 	
	    private List<Polygon> shapes;   
	 	public TestPane() 
	 	{
		    shapes = new ArrayList<>();
	 	}
	 	
	 	public void addShape(Polygon polygon)
	 	{
	 		shapes.add(polygon);
	 		repaint();
	 	}
	 	
	    @Override
	    public Dimension getPreferredSize() {
	    	return new Dimension((int)screenwidth,(int)screenheight);
	    }
	
	    @Override
	    protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g.create();
			for (Polygon poly : shapes) {
			    g2d.drawPolygon(poly);
			}
			g2d.dispose();
	    } 		
	}
		     
	 
	public static void main(String[] args)
	{	
		 MainWorker m = new MainWorker();
		 JFrame f = new JFrame("Ouch");
		 TestPane testpane = m.new TestPane();
		 f.add(testpane);
		 //f.add(progressBar);
		 f.setVisible(true);
		 f.setSize((int)screenwidth, (int)screenheight);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		 HexagonASyncTask task = m.new HexagonASyncTask(testpane);

		 task.execute();
		 try
		 {
		 System.out.println(task.get()); 
		 }
		 catch (Exception e)
		 {
			 System.out.println("ouch");
		 }
	 }
}