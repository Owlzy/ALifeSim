import javax.swing.JOptionPane;

//this ones mine
import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.*;

public class MainMenu extends Application {
	//Text t = new Text(10, 50, "Life Simulator v1.1 with multithreading"); // test
																		// font
	Scene scene;
	Stage stage;
	Simulation sim;
	int screenHeight = 400;
	int screenWidth = 600;
	
	private boolean isPause = false;
	private static int gridSize = 25;
	private boolean quitMenu = false;
	private boolean earlyTerminate = false;
	World world;

	public MainMenu() {
		//world = new World(gridSize);
		//world.SetConfig();
	}

	Service<Void> service = new Service<Void>() {
		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					// Background work
					int tempCycleCount = 0;
					for (int i = 0; i < world.GetCycles(); i++) {
						if (earlyTerminate) {
							tempCycleCount = i;
							break;
						}
						while (isPause) {
							world.PrintScene();
							try {
								Thread.sleep(200); // sleep
							} catch (InterruptedException ex) {
								Thread.currentThread().interrupt();
							}
							if (earlyTerminate) {
								break;
							}
						}
						world.WorldCycle(world);
					}
					world.FinalPrint();
					if (earlyTerminate) {
						System.out
								.println("Simulation terminated early, after  "
										+ tempCycleCount + "  cycles");
						earlyTerminate = false;
					}
					return null;
				}
			};
		}
	};

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(final Stage stage) {
		MenuBar menuBar = new MenuBar();
		
		final VBox vbox = new VBox();
		
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(100, 10, 100, 10));
		//vbox.getChildren().addAll(t);
		stage.setTitle("Menu Sample");
		
		
		scene = new Scene(new VBox(), screenWidth, screenHeight);
		
		
		scene.setFill(Color.LIGHTGREEN);
		
		
		// /--------- file menu
		MenuItem open = new MenuItem("Open");
		open.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				/////open file
				String myConfig = JOptionPane.showInputDialog(null,
						"Please enter file name", "Open File",
						JOptionPane.PLAIN_MESSAGE);
				world.SetConfig();
			}
		});
		MenuItem newC = new MenuItem("New Config");
		newC.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				/////new config
				NewConfig(world);
			}
		});
		MenuItem save = new MenuItem("Save");
		save.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				world.SaveConfig();
			}
		});
		MenuItem saveAs = new MenuItem("Save As");
		saveAs.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				world.SaveConfigAs();
			}
		});
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.exit(0);
			}
		});
		// ---------- view menu
		MenuItem displayConfig = new MenuItem("Display Config");
		displayConfig.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				// print config file contents from filehandler
				FileHandler.DisplayCSVFileContents("config.csv");
			}
		});
		MenuItem editConfig = new MenuItem("Edit Config");
		editConfig.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				// print config file contents from filehandler
				EditConfig(world);
			}
		});
		MenuItem togglePrint = new MenuItem("Toggle Print Lifeform Info");
		togglePrint.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				// display info about life forms at end of sim
				world.ToggleFinalPrint();
			}
		});
		MenuItem mapInfo = new MenuItem("Print map info");
		mapInfo.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				// display info about map
				System.out.println("Grid Size = " + gridSize);
			}
		});
		// -------- edit menu
		MenuItem modLifeParam = new MenuItem("Modify Life Form Params");
		modLifeParam.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				world.ToggleAllBugsScan();
				System.out.println("Toggled bug scan distance");
			}
		});
		MenuItem decBugs = new MenuItem("Decrement Bugs");
		decBugs.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				// // remove life form (increment number of bugs)
				world.DecBugs();
				System.out.println("Num bugs = " + world.GetNumBugs());
			}
		});
		MenuItem incBugs = new MenuItem("Increment Bugs");
		incBugs.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				// // add life form (decrement number of bugs)
				world.IncBugs();
				System.out.println("Num bugs = " + world.GetNumBugs());
			}
		});
		// simulation menu
		MenuItem run = new MenuItem("Run");
		run.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				// //run world here
				//RunSim(world);
				
				sim = new Simulation(stage, scene, screenHeight, screenWidth);
				
				
			}
		});
		MenuItem pause = new MenuItem("Pause");
		pause.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				// //pause world here
				
				if (!sim.isPause) {
					sim.isPause = true;
				} else {
					sim.isPause = false;
				}
			}
		});
		MenuItem stop = new MenuItem("End Sim");
		stop.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				// //end sim
				earlyTerminate = true;
			}
		});
		
		///about menu items
		MenuItem appInfo = new MenuItem("Application");
		appInfo.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				//display app info
				JOptionPane.showMessageDialog(null,
						"Life simulator v1.1 - Multithreaded \nCurrent Status \nSimple scan and move\n*Birds added, and hunt.  But no kill function.",
						"Application Info", JOptionPane.ERROR_MESSAGE);
			}
		});
		MenuItem authorInfo = new MenuItem("Author");
		authorInfo.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				//display author info
				JOptionPane.showMessageDialog(null,
						"Made by Owain Bell, Reading Uni 2014",
						"Author Info", JOptionPane.ERROR_MESSAGE);
			}
		});
		// --- Menu File
		Menu menuFile = new Menu("File");

		// --- Menu Edit
		Menu menuEdit = new Menu("Edit");

		// --- Menu View
		Menu menuView = new Menu("View");

		// --- Menu Simulation
		Menu menuSim = new Menu("Simulation");

		// -- Menu Abour
		Menu menuAbout = new Menu("About");

		

		// /add to file menu
		menuFile.getItems().addAll(newC, open, save, saveAs, exit, new SeparatorMenuItem());

		// add to view menu
		menuView.getItems().addAll(displayConfig, editConfig, togglePrint,
				mapInfo, new SeparatorMenuItem());

		// edit menu
		menuEdit.getItems().addAll(modLifeParam, incBugs, decBugs,
				new SeparatorMenuItem());

		// add to sim menu
		menuSim.getItems().addAll(run, pause, stop, new SeparatorMenuItem());
		
		// add to about menu
		menuAbout.getItems().addAll(appInfo, authorInfo, new SeparatorMenuItem());

		// add to master menu
		menuBar.getMenus().addAll(menuFile, menuView, menuEdit, menuSim,
				menuAbout);

		((VBox) scene.getRoot()).getChildren().addAll(menuBar, vbox);
		stage.setScene(scene);
		stage.show();
	}

	public int GetGridSize() {
		return gridSize;
	}

	public void NewConfig(World world) {
		world.ResetConfig();
	}

	public void EditConfig(World world) {
		String config;
		int configVal;
		// /bugs
		config = JOptionPane.showInputDialog(null,
				"Please enter number of bugs", "Edit Config",
				JOptionPane.PLAIN_MESSAGE);
		configVal = Integer.parseInt(config);
		world.SetNumBugs(configVal);
		// /birds
		config = JOptionPane.showInputDialog(null,
				"Please enter number of birds", "Edit Config",
				JOptionPane.PLAIN_MESSAGE);
		configVal = Integer.parseInt(config);
		world.SetNumBirds(configVal);
		// obs
		config = JOptionPane.showInputDialog(null,
				"Please enter number of obstacles", "Edit Config",
				JOptionPane.PLAIN_MESSAGE);
		configVal = Integer.parseInt(config);
		world.SetNumObs(configVal);
		// bugfood
		config = JOptionPane.showInputDialog(null,
				"Please enter number of food", "Edit Config",
				JOptionPane.PLAIN_MESSAGE);
		configVal = Integer.parseInt(config);
		world.SetNumFood(configVal);
		// pause time
		config = JOptionPane.showInputDialog(null,
				"Please enter length of cycle pause in ms", "Edit Config",
				JOptionPane.PLAIN_MESSAGE);
		configVal = Integer.parseInt(config);
		world.SetPauseTime(configVal);
		// save new config to default config
		world.SaveConfig();
	}

	public void RunSim(World world) {
		// world.SetConfig(); //grabs default config settings
		world.PopulateWorld();// world needs to be populated each time, in case
								// user changes some settings
		service.reset();// ensure service is reset
		service.start();// start service
	}
}
