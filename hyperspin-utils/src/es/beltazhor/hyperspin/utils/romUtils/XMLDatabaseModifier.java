package es.beltazhor.hyperspin.utils.romUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author beltazhor
 * @version 0.1 Class for modifying ROM names in hyperspin databases It takes as
 *          input the actual database and a file with the correspondency between
 *          rom code names and the updated "description" line for using it as a
 *          replacement, and generates a new copy of the database with the new
 *          understandable names.
 * 
 *          Current version only works for MAME.
 *
 */
public class XMLDatabaseModifier {

	private static final String PATH_ACTUAL_DB_FILE = "D:\\Software\\Hyperspin\\hyperspin\\Databases\\MAME\\MAME_buena_10112017_2.xml";

	private static final String PATH_CORRESPONDENCY_FILE = "D:\\Software\\Hyperspin\\hyperlists\\correspondency.txt";

	private static final String PATH_OUTPUT_DB_FILE = "D:\\Software\\Hyperspin\\hyperspin\\Databases\\MAME\\MAME_generated.xml";

	/**
	 * Main method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// variable definition (read)
		File fDBFile = null;
		FileReader frDBFile = null;
		BufferedReader brDBFile = null;

		// variable definition (write)
		File fOut = null;
		FileWriter fwOut = null;
		BufferedWriter bwOut = null;

		// aux variables
		String currentLine = null, previousLine = null;
		boolean exit = false;
		Map<String, String> correspondencies = new HashMap<String, String>();

		try {
			// 1. the correspondency file is loaded in the map

			loadCorrespondencies(correspondencies);

			// 2. db file generation

			fDBFile = new File(PATH_ACTUAL_DB_FILE);
			frDBFile = new FileReader(fDBFile);
			brDBFile = new BufferedReader(frDBFile);

			fOut = new File(PATH_OUTPUT_DB_FILE);
			fwOut = new FileWriter(fOut);
			bwOut = new BufferedWriter(fwOut);

			while (!exit) {
				currentLine = brDBFile.readLine();
				String lineCandidate = currentLine;

				if (currentLine != null) {
					if (previousLine != null) {
						// we have previous data. Let's extract the code to see
						// if a replacement applies
						String code = HyperspinUtils.extractCode(previousLine);
						if (code != null) {
							String newDesc = correspondencies.get(code);
							if (newDesc != null) {
								lineCandidate = newDesc;
							}
						}
					}

					bwOut.write(lineCandidate + "\n");
					bwOut.flush();
					previousLine = currentLine;
				} else {
					exit = true;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fwOut) {
					fwOut.close();
				}
				if (null != frDBFile) {
					frDBFile.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * Load the correspondency file into a map
	 * 
	 * @param correspondencies
	 *            the map
	 */
	private static void loadCorrespondencies(
			Map<String, String> correspondencies) {
		// variable definition (read)
		File f = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {
			f = new File(PATH_CORRESPONDENCY_FILE);
			fr = new FileReader(f);
			br = new BufferedReader(fr);

			// reading the file
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] data = linea.split("#");
				correspondencies.put(data[0], data[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

}
