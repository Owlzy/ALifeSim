import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


public class Vegetable extends GLifeForm {
	int vegRad = 7;
	
	public Vegetable(){
		x = screenWidth / 2;
		y = screenHeight / 2;
		circle = new Circle(x, y, vegRad);
		circle.setFill(Color.GREEN);
		energy = (int) ((Math.random() * 9) + 1);
		
		RandomTranslate();
	}	
	public void RandomTranslate(){
		double randX = Math.random() * screenWidth;
		double randY = Math.random() * screenHeight;
		
		randX -= circle.getCenterX() - 25;
		randY -= circle.getCenterY() - 25;
		
		circle.setTranslateX(randX);
		circle.setTranslateY(randY);
	}
}
