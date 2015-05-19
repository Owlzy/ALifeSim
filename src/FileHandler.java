import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
	private static String[] configArray;
	public void WriteToCsvFile(String sFileName, String[] contents,
			int valsPerLine) {
		try {
			FileWriter writer = new FileWriter(sFileName);
			if (contents != null) {
				for (int i = 0; i < contents.length; i++) {
				
					if (i % valsPerLine != 0 && (i + 1) % valsPerLine != 0
							|| i == 0)
		
						writer.append('"' + contents[i] + '"' + ',');
					else if ((i + 1) % valsPerLine == 0) {
						writer.append('"' + contents[i] + '"');
					} else {
						writer.append("\n");
						writer.append('"' + contents[i] + '"' + ',');
					}
				}
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void DisplayCSVFileContents(String sFileName) {
		String csvFile = "config.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] configVals = line.split(cvsSplitBy);

				System.out.println("Config [NumBugs= " + configVals[0]
						+ " , NumBirds=" + configVals[1] + " , NumObs="
						+ configVals[2] + " , numObs=" + configVals[3]
						+ " , PauseTime=" + configVals[4] + "]");

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");
	}
	
	public static List<String> GetCSVFileContents(String sFileName) {
		String csvFile = sFileName + ".csv";
				//"config.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		List<String> tempConfigVals = new ArrayList<String>();

		int valIndex = 0;
		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				String[] configVals = line.split(cvsSplitBy);
				tempConfigVals.add(configVals[valIndex]);
				tempConfigVals.add(configVals[valIndex+1]);
				tempConfigVals.add(configVals[valIndex+2]);
				tempConfigVals.add(configVals[valIndex+3]);	
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return tempConfigVals;
	}

	public Boolean IsThereConfig() {
		if (new File("config.csv").isFile()) {
			return true;
		}
		return false;
	}
	
	public void SaveConfig(String[] configContent, int AMOUNTOFSETTINGS) {
		
		WriteToCsvFile("config.csv", configContent, AMOUNTOFSETTINGS);
	}

	public void SaveConfigAs(String[] configContent, int AMOUNTOFSETTINGS) {
		String saveAsName = JOptionPane.showInputDialog(null,
				"Please enter a filename (without csv)", "File",
				JOptionPane.PLAIN_MESSAGE);
		WriteToCsvFile(saveAsName + ".csv", configContent, AMOUNTOFSETTINGS);
	}
	// deletes config file by using the File class, displays a success message
	public void ResetConfig() {
		File file = new File("config.csv");
		file.setWritable(true);
		file.delete();
		JOptionPane.showMessageDialog(null, "Config has been reset", "Message",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static String[] GetFileContents(String sFileName) {
		String csvFile = sFileName;
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		configArray = new String[5];
		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] configVals = line.split(cvsSplitBy);
				
				configArray[0] = configVals[0];
				configArray[1] = configVals[1];
				configArray[2] = configVals[2];
				configArray[3] = configVals[3];
				configArray[4] = configVals[4];
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return configArray;
	
		
	}

}
