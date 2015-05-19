import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.Interpolator;
import javafx.animation.KeyValue;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.WritableValue;

public class Simulation {
	// /GLOBAL VARS
	GBug[] bugArray;
	Vegetable[] vegArray;
	double startTime;
	double startFoodTime;
	Timeline anim;
	int numBugs = 5;
	int numVeg = 10;
	static double screenWidth;
	static double screenHeight;
	double x = screenWidth / 2;
	double y = screenHeight / 2;
	double x2 = screenWidth / 2;
	double y2 = screenHeight / 2;
	int ballRad = 5;
	int bugRad = 5;
	float deltaX = -1.5f, deltaY = -1.5f;
	float deltaX2 = 1.5f, deltaY2 = 1.5f;
	boolean timeElapse = false;
	boolean isPause = false;
	double startX;
	double startY;
	double startX2;
	double startY2;
	// Timeline timeLine;
	KeyFrame frame;

	public Simulation(Stage stage, Scene scene, int sHeight, int sWidth) {
		// TODO constructor with scene from main menu
		bugArray = new GBug[numBugs];
		vegArray = new Vegetable[numVeg];
		screenWidth = sWidth;
		screenHeight = sHeight;
		// /get start time
		startTime = System.currentTimeMillis();
		startFoodTime = System.currentTimeMillis();
		start(stage, scene);
	}

	public void start(Stage primaryStage, Scene scene) {
		final GBug singleBug = new GBug();
		final StackPane root = new StackPane();
		// instaniate all bugs in array and add to stackpane
		for (int i = 0; i < numBugs; i++) {
			bugArray[i] = new GBug();
			root.getChildren().addAll(bugArray[i].GetCircle());
		}
		// now do same for vegetables
		for (int i = 0; i < numVeg; i++) {
			vegArray[i] = new Vegetable();
			root.getChildren().addAll(vegArray[i].GetCircle());
		}
		((VBox) scene.getRoot()).getChildren().addAll(root);
		frame = new KeyFrame(Duration.millis(16),
				new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent t) {
						// main sim loop
						if (!isPause) {
							// /first of all lets make sure all bugs are in
							// bounds
							// of window
							for (int i = 0; i < numBugs; i++) {
								if (bugArray[i].GetCircle().getCenterX()
										+ bugArray[i].GetCircle()
												.getTranslateX() < bugArray[i]
										.GetCircle().getRadius()
										|| bugArray[i].GetCircle().getCenterX()
												+ bugArray[i].GetCircle()
														.getTranslateX() > screenWidth
												- bugArray[i].GetCircle()
														.getRadius()) {
									float temp = bugArray[i].GetDX();
									temp *= -1;
									bugArray[i].SetDX(temp);

								}
								if (bugArray[i].GetCircle().getCenterY()
										+ bugArray[i].GetCircle()
												.getTranslateY() < bugArray[i]
										.GetCircle().getRadius() + 5
										|| bugArray[i].GetCircle().getCenterY()
												+ bugArray[i].GetCircle()
														.getTranslateY() > screenHeight
												- bugArray[i].GetCircle()
														.getRadius() - 25) {
									float temp = bugArray[i].GetDY();
									temp *= -1;
									bugArray[i].SetDY(temp);
								}
							}
							// loop to check pos of all bugs and all food
							for (int i = 0; i < numBugs; i++) {
								// first use some temp vars to grab the x y
								// coords
								double bugX = bugArray[i].GetCircle()
										.getTranslateX()
										+ bugArray[i].GetCircle().getCenterX();
								double bugY = bugArray[i].GetCircle()
										.getTranslateY()
										+ bugArray[i].GetCircle().getCenterY();
								// now lets check the distance of these against
								// all
								// veg
								for (int j = 0; j < numVeg; j++) {
									double foodX = vegArray[j].GetCircle()
											.getTranslateX()
											+ vegArray[j].GetCircle()
													.getCenterX();
									double foodY = vegArray[j].GetCircle()
											.getTranslateY()
											+ vegArray[j].GetCircle()
													.getCenterY();
									if (dist(bugX, foodX, bugY, foodY) < bugArray[i].scanRange
											&& vegArray[j].isAlive) {
										// food is within scan range and food is
										// alive
										// first stop random move
										singleBug.isRandMove = false;
										// then adjust velovity accordingly
										if (bugX > foodX) {
											bugArray[i].deltaX -= 0.05f;
										} else {
											bugArray[i].deltaX += 0.05f;
										}
										if (bugY > foodY) {
											bugArray[i].deltaY -= 0.05f;
										} else {
											bugArray[i].deltaY += 0.05f;
										}
									} else {
										bugArray[i].isRandMove = true;
									}

									if (dist(bugX, foodX, bugY, foodY) < bugArray[i]
											.GetCircle().getRadius() * 2
											&& vegArray[j].isAlive) {
										// collision with food
										vegArray[j].isAlive = false;
										// vegArray[j].GetCircle().setFill(Color.TRANSPARENT);
										root.getChildren().remove(
												vegArray[j].GetCircle());
										// here after eating food, the bug
										// stands still till next random move
										bugArray[i].SetDX(0f);
										bugArray[i].SetDY(0f);
									}
								}
							}
				
							// choose random direction and speed every 5 seconds
							if (System.currentTimeMillis() > startTime + 5000) {
								startTime = System.currentTimeMillis();
								for (int i = 0; i < numBugs; i++) {
									if (bugArray[i].isRandMove) {
										bugArray[i].RandomMoveX();
										bugArray[i].RandomMoveY();
									}
								}
							}

				
							// set translate x y for all bugs
							for (int i = 0; i < numBugs; i++) {
								bugArray[i].GetCircle().setTranslateX(
										bugArray[i].GetCircle().getTranslateX()
												+ bugArray[i].GetDX());
								bugArray[i].GetCircle().setTranslateY(
										bugArray[i].GetCircle().getTranslateY()
												+ bugArray[i].GetDY());
							}
						}
					}

					private double dist(double posBall1x, double posBall2x,
							double posBall1y, double posBall2y) {
						// calc euclidean distance
						double diffX = posBall1x - posBall2x;
						double diffY = posBall1y - posBall2y;

						double distance = (diffX * diffX) + (diffY * diffY);
						distance = Math.sqrt(distance);
						// return euclidean distance
						return distance;
					}
					// private boolean CheckCollision(){
					// if (dist(posBugX, posFoodX, posBugY, posFoodY) <
					// singleBug.scanRange) {

					// }
					// }
				});
		/*
		 * btn3.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent click) { //toggle gravity if
		 * (!isGravity){ isGravity = true; } else { isGravity = false; } } });
		 */
		WritableValue<Number> startXVal = new SimpleDoubleProperty(100.0);
		anim = TimelineBuilder
				.create()
				.autoReverse(false)
				.keyFrames(
						frame,
						new KeyFrame(new Duration(13), new KeyValue(startXVal,
								300.0, Interpolator.LINEAR)))
				.cycleCount(Timeline.INDEFINITE).build();
		anim.play();
		primaryStage.setTitle("Simulation Running");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false); // don't allow user to resize (wall
											// reflections are hard coded!)

		primaryStage.show();
	}
	
	public void ClearStack(){
		
	}

}
