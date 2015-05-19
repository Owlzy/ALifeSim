import java.util.Random;

public abstract class LifeForm {
	//all variables common to life forms
	//life form details
	String species;
	char symbol;
	int energy;
	int ID;
	boolean isDead = false;
	//life forms need to know the grid size. 
	int gridSize;
	//grid location
	int x;
	int y;
	// related to collision detection, all life forms require this
	boolean nBlock = false;
	boolean eBlock = false;
	boolean sBlock = false;
	boolean wBlock = false;
	boolean skipMove = false;
	public boolean isScanTwo = false;
	
	public LifeForm(int _gridSize, int _id) {
		gridSize = _gridSize;
		//RandomStart();
	}
	
	public void RandomStart() {
	//	Random randomGen = new Random();
	//	int maxRange = gridSize - 1;
	//	int minRange = 1;

	//	int ranIntX = randomGen.nextInt((maxRange - minRange) + 1) + minRange;
	//	int ranIntY = randomGen.nextInt((maxRange - minRange) + 1) + minRange;

	//	x = ranIntX;
	//	y = ranIntY;
	}

	public char GetSymbol() {
		return this.symbol;
	}
	
	public void SetSymbol(char sym){
		symbol = sym;
	}

	public void ResetBools() {
		nBlock = false;
		eBlock = false;
		sBlock = false;
		wBlock = false;
		skipMove = false;
	}
}
