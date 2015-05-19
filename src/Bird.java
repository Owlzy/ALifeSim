
public class Bird extends LifeForm {
	int numBugs;
	
	public Bird(int _gridSize, int _id) {
		super(_gridSize, _id);
		species = "Bird";
		energy = 20;
		symbol = '^';
		ID = _id;
		numBugs = new World().GetNumBugs();
	}
	public void KillPrey(World world) {
		for (int i = 0; i < numBugs; i++){ //don't think this function works at moment, need to find a way of grabbing life form id
			if (world.ChecksGrid(x, y) == 'b'){
				world.bugArray[i].Death();
			}
		}
	}
	/*
	public void ScanEnviroment2(World world) {  ////note this is not currently in use!! is a poor solution anyway, although it does work to a degree
		// scan north 2
		if (!isDead) {
			if (y > 1 && !skipMove) {
				if (world.ChecksGrid(x, y - 2) != ' '
						&& world.ChecksGrid(x, y - 1) != ' ') {
					if (world.ChecksGrid(x, y - 2) == 'b') {
	
						world.ChangesGrid(x, y, ' ');
						y--;
						world.ChangesGrid(x, y, symbol);
						skipMove = true;
					}

				}
			}
			// east
			if (x < gridSize - 2 && !skipMove) {
				if (world.ChecksGrid(x + 2, y) != ' '
						&& world.ChecksGrid(x + 1, y) != ' ') {
					if (world.ChecksGrid(x + 2, y) == 'b') {

						world.ChangesGrid(x, y, ' ');
						x++;
						world.ChangesGrid(x, y, symbol);
						skipMove = true;
					}

				}
			}
			// scan south 2
			if (y < gridSize - 2 && !skipMove) {
				if (world.ChecksGrid(x, y + 2) != ' '
						&& world.ChecksGrid(x, y + 1) != ' ') {
					if (world.ChecksGrid(x, y + 2) =='b') {

						world.ChangesGrid(x, y, ' ');
						y++;
						world.ChangesGrid(x, y, symbol);
						skipMove = true;
					}

				}
			}
			// west
			if (x > 1 && !skipMove) {
				if (world.ChecksGrid(x - 2, y) != ' '
						&& world.ChecksGrid(x - 1, y) != ' ') {
					if (world.ChecksGrid(x - 2, y) =='b') {

						world.ChangesGrid(x, y, ' ');
						x--;
						world.ChangesGrid(x, y, symbol);
						skipMove = true;
					}

				}
			}
			Move(world);
		}
	}
*/
	public void ScanEnviroment(World world) {
		if (!isDead) {
			// scan north
			if (y > 0 && !skipMove) {
				if (world.ChecksGrid(x, y - 1) != ' ') {
					if (world.ChecksGrid(x, y - 1) =='b') {
						KillPrey(world);
						energy += 5;
						world.ChangesGrid(x, y, ' ');
						y--;
						world.ChangesGrid(x, y, symbol);
						skipMove = true;
					} else {
						nBlock = true;
					}

				}
			}

			// scan east
			if (x < gridSize - 1 && !skipMove) {
				if (world.ChecksGrid(x + 1, y) != ' ') {
					if (world.ChecksGrid(x + 1, y) == 'b') {
						KillPrey(world);
						energy += Character.getNumericValue(world.ChecksGrid(
								x + 1, y));
						world.ChangesGrid(x, y, ' ');
						x++;
						world.ChangesGrid(x, y, symbol);
						skipMove = true;
					}

					else {
						eBlock = true;
					}

				}
			}

			// scan south
			if (y < gridSize - 1 && !skipMove) {
				if (world.ChecksGrid(x, y + 1) != ' ') {
					if (world.ChecksGrid(x, y + 1) =='b') {
						KillPrey(world);
						energy += 5;
						world.ChangesGrid(x, y, ' ');
						y++;
						world.ChangesGrid(x, y, symbol);
						skipMove = true;
					}

					else {
						sBlock = true;
					}

				}
			}

			// scan west
			if (x > 0 && !skipMove) {
				if (world.ChecksGrid(x - 1, y) != ' ') {
					if (world.ChecksGrid(x - 1, y) =='b') {
						KillPrey(world);
						energy += 5;
						world.ChangesGrid(x, y, ' ');
						x--;
						world.ChangesGrid(x, y, symbol);
						skipMove = true;
					}

					else {
						wBlock = true;
					}
				}
			}
			Move(world);
		}
	}

	public void Move(World world) {

		// if (x > 8 && !wBlock) {
		// world.BugChangesGrid(x, y, ' ');
		// x--;
		// world.BugChangesGrid(x, y, symbol);
		// }
		if (!isDead) {
			boolean myMove = true;
			if (!skipMove) {
				while (myMove) {
					double ranFloat = Math.random();
					if (!nBlock && y > 0 && ranFloat < 0.25) {
						world.ChangesGrid(x, y, ' ');
						y--;
						world.ChangesGrid(x, y, symbol);
						myMove = false;

					}
					// west
					else if (!wBlock && x > 0 && ranFloat < 0.5) {
						world.ChangesGrid(x, y, ' ');
						x--;
						world.ChangesGrid(x, y, symbol);
						myMove = false;
					}
					// east
					else if (!eBlock && x < gridSize - 1 && ranFloat < 0.75) {
						world.ChangesGrid(x, y, ' ');
						x++;
						world.ChangesGrid(x, y, symbol);
						myMove = false;
					}
					// south
					else if (!sBlock && y < gridSize - 1 && ranFloat > 0.75) {
						world.ChangesGrid(x, y, ' ');
						y++;
						world.ChangesGrid(x, y, symbol);
						myMove = false;
					} else {
						myMove = false;
					}
				}
			}
			ResetBools();

		}
	}
}
