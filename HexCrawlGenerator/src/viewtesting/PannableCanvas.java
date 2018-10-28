package viewtesting;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import model.Point;
import model.redblob.Hex;
import model.redblob.Layout;
import model.redblob.LayoutFX;

class PannableCanvas extends Pane {

    DoubleProperty myScale = new SimpleDoubleProperty(1.0);

    public PannableCanvas() {
        setPrefSize(6000, 6000);
        setStyle("-fx-background-color: lightgrey; -fx-border-color: blue;");

        // add scale transform
        scaleXProperty().bind(myScale);
        scaleYProperty().bind(myScale);
    }
    
    public PannableCanvas(int width, int height)
    {
        setPrefSize(width,height);
        setStyle("-fx-background-color: lightgrey; -fx-border-color: blue;");

        // add scale transform     
        scaleXProperty().bind(myScale);
        scaleYProperty().bind(myScale);    	
    }

    public double getScale() {
        return myScale.get();
    }

    public void setScale( double scale) {
        myScale.set(scale);
    }

    public void setPivot( double x, double y) {
        setTranslateX(getTranslateX()-x);
        setTranslateY(getTranslateY()-y);

    }

    public void addPolygonGrid(int map_radius)
    {
    	List<Polygon> list = initializeSpiralMap(map_radius);
//    	Canvas map = new Canvas(getPrefWidth(),getPrefHeight());
    	Canvas map = new Canvas(6000d,6000d);
    	GraphicsContext gc = map.getGraphicsContext2D();
    	for(Polygon p : list)
    	{
    		drawCanvasPolygon(gc,p);    		
    	}
    	
    	getChildren().add(map);
    }
    
    public void drawCanvasPolygon(GraphicsContext gc, Polygon shape)
    {
    	List<Double> points = new ArrayList<>(); 
    	points.addAll(shape.getPoints());
    	gc.setFill(Color.BLACK);
    	gc.setLineWidth(5);
    	
    	int size = points.size();
    	
    	gc.strokeLine(points.get(10), points.get(11), points.get(8), points.get(9));
    	for(int ii=0;ii<points.size() - 4; ii+=4)
    	{
    		gc.strokeLine(points.get(ii), points.get(ii+1), points.get(ii+2), points.get(ii+3));		
    	}    	
    	
    }
    
	public List<Polygon> initializeSpiralMap(int map_radius)
	{	
		LayoutFX layout = new LayoutFX(Layout.flat, new Point(10d,10d), new Point(getPrefWidth()/2,getPrefHeight()/2));
		List<Polygon> polylist = new ArrayList<>();
		int count=0;
		for (int q = -map_radius; q <= map_radius; q++) 
		{
		    int r1 = Math.max(-map_radius, -q - map_radius);
		    int r2 = Math.min(map_radius, -q + map_radius);
		    for (int r = r1; r <= r2; r++) 
		    {
		    	Hex hh = new Hex(q,r);
				Polygon a = layout.polygonFXCorners(hh);
				polylist.add(a);
				getChildren().add(a);
				count++;
		    }
		}
		System.out.println(count);
		return polylist;
	}	
    
	public void addGrid() {
	
	  double w = getBoundsInLocal().getWidth();
	  double h = getBoundsInLocal().getHeight();
	
	  // add grid
	  Canvas grid = new Canvas(w, h);
	
	  // don't catch mouse events
	  grid.setMouseTransparent(true);
	
	  GraphicsContext gc = grid.getGraphicsContext2D();
	
	  gc.setStroke(Color.GRAY);
	  gc.setLineWidth(1);
	
	  // draw grid lines
	  double offset = 50;
	  for( double i=offset; i < w; i+=offset) {
	      gc.strokeLine( i, 0, i, h);
	      gc.strokeLine( 0, i, w, i);
	  }
	
	  getChildren().add(grid);
	
	  grid.toFront();
	}    
}


