package application;
	
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Main extends Application {
	
	// declare stuff
	public static Pane pane;
	public static Scene scene;
	public static boolean mousePressed, mouseReleased, keyPressed, keyReleased, shiftDown;
	public static int mouseX, mouseY;
	public static String key;
	public static Button myButton;
	public static DragButton myDragButton;
	public static Slider mySlider;
	public static TextBox myTextBox;
	
	@Override
	public void start(Stage primaryStage) {
		
		// instantiate stuff
		pane = new Pane();
		scene = new Scene(pane, 600, 600, Color.WHITE);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		myButton = new Button(100, 100, 100, 80);
		myButton.setColor(250, 200, 150);
		myButton.setText("gucci gang");
		pane.getChildren().add(myButton.components());
		
		myDragButton = new DragButton(300, 100, 100, 80);
		myDragButton.setColor(100, 75, 50);
		myDragButton.setText("gucci gang gucci gang gucci gang");
		pane.getChildren().add(myDragButton.components());
		
		mySlider = new Slider(200, 300, 100, 20, "horizontal");
		pane.getChildren().add(mySlider.components());
		
		myTextBox = new TextBox(50, 400, 200, 40);
		pane.getChildren().add(myTextBox.components());
		
		// run stuff
		EventHandler<ActionEvent> eh = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				draw();
			}
		};
		Timeline timeline = new Timeline(new KeyFrame(Duration.ONE, eh));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
	
	// draw
	public void draw() {
		//
		updateMouse();
		updateKey();
		//
		myButton.draw(mouseX, mouseY, mousePressed);
		if (myButton.clicked(mouseX, mouseY, mouseReleased)) {
			System.out.println("YO!");
		}
		myDragButton.draw(mouseX, mouseY, mousePressed, mouseReleased);
		mySlider.draw(mouseX, mouseY, mousePressed);
		myTextBox.draw(key, keyPressed, keyReleased, shiftDown, mouseX, mouseY, mousePressed);
		//
		resetReleased();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Updates mousePressed, mouseReleased, mouseX, and mouseY variables.
	 */
	public void updateMouse() {
		// mousePressed
		scene.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				mousePressed = true;
			}
		});
		scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				mousePressed = false;
				mouseReleased = true;
			}
		});
		
		// mouseX, mouseY
		scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				mouseX = (int) t.getSceneX();
				mouseY = (int) t.getSceneY();
			}
		});
		scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				mouseX = (int) t.getSceneX();
				mouseY = (int) t.getSceneY();
			}
		});
	}
	
	/**
	 * Resets values of mouseReleased and keyReleased to false at the end of the loop.
	 * Necessary for mouseReleased and keyReleased to function correctly.
	 */
	public void resetReleased() {
		mouseReleased = false;
		keyReleased = false;
	}
	
	/**
	 * Updates the value of keyPressed, key, and keyCode.
	 */
	public void updateKey() {
		// keyPressed + key
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent t) {
				keyPressed = true;
				key = t.getCode().toString();
				if (key.equals("SHIFT")) {
					shiftDown = true;
				}
			}
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent t) {
				keyPressed = false;
				keyReleased = true;
				if (t.getCode().toString().equals("SHIFT")) {
					shiftDown = false;
				}
			}
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
