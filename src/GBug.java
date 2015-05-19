import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GBug extends GLifeForm {

	int bugRad = 5;
	int scanRange = 75;
	boolean isEvade = false;
	
	public GBug() {
		// TODO create circle for bug body
		x = screenWidth / 2;
		y = screenHeight / 2;
		circle = new Circle(x, y, bugRad);
		circle.setFill(Color.BLUE);
		//bug energy level set to between 50 and 100
		energy = (int) ((Math.random() * 50) + 50);
		RandomMoveX();
		RandomMoveY();
		RandomTranslate();
	}

	public Circle GetCircle(){
		return circle;
	}
	
	///these go here because vegtables dont move etc.
	public void IncEnergy(int nEnergy){
		energy += nEnergy;
	}
	
	public boolean IsRandMove(){
		return isRandMove;
	}
	
	public void RandomMoveX(){
		float randVel = (float) Math.random();
		//randVel = randVel % 1.5f;
		if (deltaX < 0){
			deltaX = -randVel;
		}
		else {
			deltaX = randVel;
		}
	}
	public void RandomMoveY(){
		float randVel = (float) Math.random();
		//randVel = randVel % 1.5f;
		if (deltaY < 0){
			deltaY = -randVel;
		}
		else {
			deltaY = randVel;
		}
	}
	
	public void FoodScan(Vegetable singleVeg){
		//scan for vegetables
		
		
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
