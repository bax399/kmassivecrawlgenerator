package viewtesting;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
public class RuntimeController2 extends Application
{
	public static int WINDOWWIDTH = 1024;
	public static int WINDOWHEIGHT = 768;
	
	@Override
	public void init() throws Exception
	{
		System.out.println("init");
	}
	
	public static void main(String[] args)
	{
		Application.launch(args);



	}
	
	public void start(Stage stage)
	{
		System.out.println("start");
		Group root = new Group();
		
		
		PannableCanvas layers = new PannableCanvas();
		LayerSystem map = new LayerSystem(layers);

		root.getChildren().add(layers);
		
		Scene scene = new Scene(root, WINDOWWIDTH,WINDOWHEIGHT);
		SceneGestures sceneGestures = new SceneGestures(layers);
		
        scene.addEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        scene.addEventFilter( MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        scene.addEventFilter( ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());

        
        System.out.println("show");
        stage.setScene(scene);
        stage.show();	
        
	}

	
}

//Group squares = new Group();
//squares.getChildren().add(new Rectangle(100,100));
//squares.getChildren().add(new Rectangle(200,200));		
//map.addLayer(1,squares);
