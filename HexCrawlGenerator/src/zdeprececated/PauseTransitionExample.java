package viewtesting;
import javafx.animation.PauseTransition; 
import javafx.animation.ScaleTransition;  
import javafx.animation.SequentialTransition; 
import javafx.animation.TranslateTransition; 

import javafx.application.Application; 
import static javafx.application.Application.launch; 

import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.scene.paint.Color; 
import javafx.scene.shape.Circle; 
 
import javafx.stage.Stage; 
import javafx.util.Duration; 
         
public class PauseTransitionExample extends Application { 
   @Override 
   public void start(Stage stage) {      
      //Drawing a Circle 
      Circle circle = new Circle(); 
      
      //Setting the position of the circle 
      circle.setCenterX(150.0f); 
      circle.setCenterY(135.0f); 
      
      //Setting the radius of the circle 
      circle.setRadius(50.0f); 
      
      //Setting the color of the circle 
      circle.setFill(Color.BROWN); 
      
      //Setting the stroke width of the circle 
      circle.setStrokeWidth(20);       
        
      //Creating a Pause Transition
      PauseTransition pauseTransition = new PauseTransition(); 
      
      //Setting the duration for the transition 
      pauseTransition.setDuration(Duration.millis(1000)); 
       
      //Creating Translate Transition 
      TranslateTransition translateTransition = new TranslateTransition();  
      
      //Setting the duration for the transition 
      translateTransition.setDuration(Duration.millis(1000));       
      
      //Setting the node of the transition 
      translateTransition.setNode(circle); 
      
      //Setting the value of the transition along the x axis 
      translateTransition.setByX(300); 
      
      //Setting the cycle count for the stroke 
      translateTransition.setCycleCount(5);  
      
      //Setting auto reverse value to true 
      translateTransition.setAutoReverse(false);  
       
      //Creating scale Transition 
      ScaleTransition scaleTransition = new ScaleTransition(); 
      
      //Setting the duration for the transition 
      scaleTransition.setDuration(Duration.millis(1000)); 
      
      //Setting the node for the transition 
      scaleTransition.setNode(circle); 
      
      //Setting the dimensions for scaling 
      scaleTransition.setByY(1.5); 
      scaleTransition.setByX(1.5); 
      
      //Setting the cycle count for the translation 
      scaleTransition.setCycleCount(5); 
      
      //Setting auto reverse value to true 
      scaleTransition.setAutoReverse(false);       
       
      //Applying Sequential transition to the circle 
      SequentialTransition sequentialTransition = new SequentialTransition(
         circle, translateTransition, pauseTransition, scaleTransition );
      
      //Playing the animation 
      sequentialTransition.play(); 
         
      //Creating a Group object  
      Group root = new Group(circle); 
         
      //Creating a scene object 
      Scene scene = new Scene(root, 600, 300);  
      
      //Setting title to the Stage 
      stage.setTitle("Pause transition example"); 
         
      //Adding scene to the stage 
      stage.setScene(scene); 
         
      //Displaying the contents of the stage 
      stage.show(); 
   }      
   public static void main(String args[]){ 
      launch(args); 
   } 
}