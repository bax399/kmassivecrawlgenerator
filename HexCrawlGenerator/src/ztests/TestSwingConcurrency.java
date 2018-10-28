package ztests;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import model.Point;
import model.redblob.Hex;
import model.redblob.Layout;

public class TestSwingConcurrency {
	final static double screenwidth = 1920d;
	final static double screenheight = 1080d;
    public static void main(String[] args) {
        new TestSwingConcurrency ();
    }

    public TestSwingConcurrency () {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
        		frame.setSize((int)screenwidth,(int)screenheight);                
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class TestPane extends JPanel {

    	
        private List<Polygon> shapes;
        private int yPos = 0;
    	private int map_radius = 100;        
//        private int q;
//        private int r;
//        private int r1,r2;
        public TestPane() {
        	Layout layout = new Layout(Layout.flat, new Point(10d,10d),new Point(screenwidth/2,screenheight/2));

            shapes = new ArrayList<>();
	    

    		for (int q = -map_radius; q <= map_radius; q++) 
    		{
    		    int r1 = Math.max(-map_radius, -q - map_radius);
    		    int r2 = Math.min(map_radius, -q + map_radius);
    		    for (int r = r1; r <= r2; r++) 
    		    {
    		    		final int runQ=q,runR=r;
			    		SwingUtilities.invokeLater(new Runnable() {
						      @Override
						      public void run() {
						  		    	Hex hh = new Hex(runQ,runR);
						  				Polygon a = layout.polygonCorners(hh);
						  				shapes.add(a);
//						                repaint();
						  		    
						      }
			    		});

    		    }
    		}
        }
//                q=-map_radius;
//            	r1 = Math.max(-map_radius, -q - map_radius);
//    		    r2 = Math.min(map_radius, -q + map_radius);
//        		r=r1;	
//            Timer timer = new Timer(10, new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//
//            		if (q<=map_radius) {    
//        		    	if (r<=r2) {
//      		    		
//            		    	Hex hh = new Hex(q,r);
//            				Polygon a = layout.polygonCorners(hh);
//            				shapes.add(a);
//                            repaint();
//                            r++;                              
//
//        		    	}
//        		    	else
//        		    	{
//        		    		q++;        
//
//                        	r1 = Math.max(-map_radius, -q - map_radius);
//                		    r2 = Math.min(map_radius, -q + map_radius);     
//        		    		r=r1;                		    
//        		    	}
//		    	
//        		    	
//            		} 
//            		    
//                }
//            });
//            timer.setInitialDelay(200);
//            timer.start();
//        }

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

}