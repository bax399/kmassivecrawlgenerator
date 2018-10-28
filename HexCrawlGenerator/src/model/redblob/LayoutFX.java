package model.redblob;
import javafx.scene.shape.Polygon;
import model.Point;

public class LayoutFX extends Layout {
	   public LayoutFX(Orientation orientation, Point size, Point origin)
	    { 
	        super(orientation,size,origin);
	    }
	   
	    public Polygon polygonFXCorners(Hex h)
	    {
	        Polygon corners = new Polygon();
	        Point center = hexToPixel(h);
	        for (int i = 0; i < 6; i++)
	        {
	        	double xi = hexCornerOffset(i).x;
	        	double yi = hexCornerOffset(i).y;
	        	
	            corners.getPoints().addAll(new Double[] {center.x+xi,center.y+yi});
	        }
	        return corners;
	    }
	    
	    public Polygon polygonFXCorners(Point origin)
	    {
	        Polygon corners = new Polygon();
	        Point center = hexToPixel(new Hex(origin.x.intValue(),origin.y.intValue()));
	        for (int i = 0; i < 6; i++)
	        {
	        	double xi = hexCornerOffset(i).x;
	        	double yi = hexCornerOffset(i).y;
	        	
	            corners.getPoints().addAll(new Double[] {center.x+xi,center.y+yi});
	        }
	        return corners;	    	
	    }
	    
	    public Point pointCorner(Hex h, int ii)
	    {
	    	Point center = hexToPixel(h);
	    	Point offset = hexCornerOffset(ii);
	    	return new Point(center.x+offset.x, center.y+offset.y);
	    }
}
