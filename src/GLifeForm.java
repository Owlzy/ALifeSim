import javafx.scene.shape.Circle;


public abstract class GLifeForm {
	double screenWidth = Simulation.screenWidth;
	double screenHeight = Simulation.screenHeight;
	double x;
	double y;
	//int rad = 5;
	float deltaX = -1.5f, deltaY = -1.5f;
	boolean isRandMove = true;
	double startX;
	double startY;
	Circle circle;
	int energy;
	
	boolean isAlive = true;
	
	public GLifeForm(){
		
	}
	
	public Circle GetCircle(){
		return circle;
	}
	
	public float GetDX(){
		return deltaX;
	}

	public float GetDY(){
		return deltaY;
	}
	
	public void SetDX(float DX){
		deltaX = DX;
	}
	
	public void SetDY(float DY){
		deltaY = DY;
	}
	


}
