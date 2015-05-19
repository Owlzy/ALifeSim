import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.text.*;

public class World {
	char[][] aGrid;
	private int gridSize;
	private int numCycles = 100;
	private int pauseTime = 100; // 1000 milliseconds is one second. ms.
	// population
	private int numBugs = 0;
	private int numBirds = 0;
	private int numCats = 0; // cats cats cats cats cats cats cats
	private int numFood = 0;
	private int numObs = 0;
	Bug[] bugArray;
	Bird[] birdArray;
	private boolean isShowCycles = true;
	private boolean isPrint = true;
	private FileHandler fileHandler;
	private String[] configContent = new String[5];
	private int AMOUNTOFSETTINGS = 5;

	public World() {
		fileHandler = new FileHandler();
	}

	public World(int size) {

		fileHandler = new FileHandler();
		// create world grid
		// gridSize = size;
		// GenerateGrid();
		// SetConfig();
		// populate
		// Config [NumBugs= "5" , NumBirds="2" , NumObs="15" , numObs="15" ,
		// PauseTime="100"]

	}

	public void Kill(int id) {
		bugArray[id].isDead = true;
	}

	public void PopulateWorld() {
		//clear grid first
		
		GenerateGrid();
		
		// bugs first

		bugArray = new Bug[numBugs];
		for (int i = 0; i < numBugs; i++) {
			bugArray[i] = new Bug(gridSize, i);
			if (aGrid[bugArray[i].x][bugArray[i].y] == ' ') {
				aGrid[bugArray[i].x][bugArray[i].y] = bugArray[i].GetSymbol();
			} else {
				i--;
			}
		}
		// next add birds
		birdArray = new Bird[numBirds];
		for (int i = 0; i < numBirds; i++) {
			birdArray[i] = new Bird(gridSize, i);
			if (aGrid[birdArray[i].x][birdArray[i].y] == ' ') {
				aGrid[birdArray[i].x][birdArray[i].y] = birdArray[i]
						.GetSymbol();
			} else {
				i--;
			}
		}
		// next add food to grid, randomise location and value
		for (int i = 0; i < numFood; i++) {

			int ranX = RandomCoord();
			int ranY = RandomCoord();
			if (aGrid[ranX][ranY] == ' ') {
				// randomises the bonus value of food, 1..5
				aGrid[ranX][ranY] = Character.forDigit(
						((RandomCoord() % 8) + 1), 5);
			} else {
				i--;
			}
		}
		for (int i = 0; i < numObs; i++) {
			int ranX = RandomCoord();
			int ranY = RandomCoord();
			if (aGrid[ranX][ranY] == ' ') {
				aGrid[RandomCoord()][RandomCoord()] = 'X';
			} else {
				i--;
			}
		}
	}

	public void GenerateGrid() {
		// passed in from main class, the grid size of world;
		aGrid = new char[gridSize][gridSize];
		// set all to whitespace, row by c
		for (int x = 0; x < gridSize; x++) {
			for (int y = 0; y < gridSize; y++) {
				aGrid[x][y] = ' ';
			}
		}
	}

	public void PrintScene() {

		if (isShowCycles) {
			System.out.print(" ");
			for (int x = 0; x < gridSize; x++) {
				System.out.print("--");
			}
			System.out.println();
			// print grid row by c
			for (int x = 0; x < gridSize; x++) {
				System.out.print("|");
				for (int y = 0; y < gridSize; y++) {
					System.out.print(aGrid[x][y] + " ");
				}
				System.out.print("|");
				System.out.println();
			}
			System.out.print(" ");
			for (int x = 0; x < gridSize; x++) {
				System.out.print("--");
			}
			System.out.println();
		}
	}

	public void PrintGrid() {

		if (isShowCycles) {
			System.out.print(" ");
			for (int x = 0; x < gridSize; x++) {
				System.out.print("--");
			}
			System.out.println();
			// print grid row by c
			for (int x = 0; x < gridSize; x++) {
				System.out.print("|");
				for (int y = 0; y < gridSize; y++) {
					System.out.print(aGrid[x][y] + " ");
				}
				System.out.print("|");
				System.out.println();
			}
			System.out.print(" ");
			for (int x = 0; x < gridSize; x++) {
				System.out.print("--");
			}
			System.out.println();
		}
	}

	public void ClearGrid() {
		for (int i = 0; i < gridSize; i++) { // clears grid to create basic
												// refresh / frames
			System.out.println();
		}
	}

	public int RandomCoord() {
		Random randomGen = new Random();
		int maxRange = gridSize - 1;
		int minRange = 1;
		int ranInt = randomGen.nextInt((maxRange - minRange) + 1) + minRange;
		return ranInt;
	}

	public char ChecksGrid(int x, int y) {
		return aGrid[x][y];
	}

	public void ChangesGrid(int x, int y, char sym) {
		aGrid[x][y] = sym;
	}

	public void FinalPrint() {
		if (isPrint) {
			for (int i = 0; i < numBugs; i++) {
				System.out.println("Bug num: " + Integer.toString(i)
						+ "       Final energy lvl: "
						+ Integer.toString(bugArray[i].energy));
			}
			System.out.println();
			System.out.println(Integer.toString(numCycles) + " cycles" + "\n"
					+ Integer.toString(numFood) + " init food" + "\n"
					+ Integer.toString(numObs) + " obs");
		}
	}

	public void WorldCycle(World world) {
		// for (int i = 0; i < numCycles; i++) {
		world.PrintGrid();
		for (int j = 0; j < numBugs; j++) { // bugs scan and move
			bugArray[j].ScanEnviroment(world);
		}
		// Sleep();
		for (int j = 0; j < numBirds; j++) { // birds scan and move
			birdArray[j].ScanEnviroment(world);
		}
		Sleep();
		world.ClearGrid(); // refresh grid
		// }
		// FinalPrint(); // prints some final details about simulation
	}

	public int GetCycles() {
		return numCycles;
	}

	private void Sleep() {
		if (isShowCycles) {
			try {
				Thread.sleep(pauseTime); // sleep
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}

	public int GetNumBugs() {
		return numBugs;
	}

	public void SetNumBugs(int nBugs) {
		numBugs = nBugs;
	}

	public void SetNumBirds(int nBirds) {
		numBirds = nBirds;
	}

	public void SetNumObs(int nObs) {
		numObs = nObs;
	}

	public void SetNumFood(int nFood) {
		numFood = nFood;
	}

	public void SetPauseTime(int pTime) {
		pauseTime = pTime;
	}

	public void ResetConfig() {
		numBugs = 0;
		numBirds = 0;
		numCats = 0; // cats cats cats cats cats cats cats
		numFood = 0;
		numObs = 0;
	}

	public void SetCycles(int _cycles) {
		numCycles = _cycles;
	}

	public void SaveConfig() {
		int configValIndex = 0;
		configContent[configValIndex++] = Integer.toString(numBugs);
		configContent[configValIndex++] = Integer.toString(numBirds);
		configContent[configValIndex++] = Integer.toString(numObs);
		configContent[configValIndex++] = Integer.toString(numFood);
		configContent[configValIndex++] = Integer.toString(pauseTime);
		fileHandler.WriteToCsvFile("config.csv", configContent,
				AMOUNTOFSETTINGS);
		JOptionPane.showMessageDialog(null, "Config auto-saved", "Message",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void SaveConfigAs() {
		int configValIndex = 0;
		configContent[configValIndex++] = Integer.toString(numBugs);
		configContent[configValIndex++] = Integer.toString(numBirds);
		configContent[configValIndex++] = Integer.toString(numObs);
		configContent[configValIndex++] = Integer.toString(numFood);
		configContent[configValIndex++] = Integer.toString(pauseTime);
		fileHandler.SaveConfigAs(configContent, AMOUNTOFSETTINGS);
	}

	public void SetConfig() {
		gridSize = 25;
		GenerateGrid();
		List<String> tempConfigVals = new ArrayList<String>();
		tempConfigVals = FileHandler.GetCSVFileContents("config");
		tempConfigVals.toArray(configContent);
		for (int i = 0; i < configContent.length - 1; i++) {
			configContent[i] = configContent[i].substring(1,
					configContent[i].length() - 1);
			;
		}

		numBugs = Integer.parseInt(configContent[0]);
		numBirds = Integer.parseInt(configContent[1]);

		numObs = Integer.parseInt(configContent[2]);
		numFood = Integer.parseInt(configContent[3]);
		pauseTime = 100;
		// System.out.println(numFood);
		// pauseTime = Integer.parseInt(configContent[4] );
		// Config [NumBugs= "5" , NumBirds="2" , NumObs="15" , numObs="15" ,
		// PauseTime="100"]
	}

	public void ToggleShowCycle() {
		if (isShowCycles) {
			isShowCycles = false;
		} else {
			isShowCycles = true;
		}
	}

	public void ToggleFinalPrint() {
		if (isPrint) {
			isPrint = false;
		} else {
			isPrint = true;
		}
	}

	public void IncBugs() {
		numBugs++;
	}

	public void DecBugs() {
		numBugs--;
	}

	public void ToggleAllBugsScan() {
		for (int i = 0; i < numBugs; i++) {
			bugArray[i].ToggleScanTwo();
		}
	}
}
