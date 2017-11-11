package es.beltazhor.hyperspin.utils.romUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author beltazhor
 * @version 0.1
 * 
 *          Takes a MAME database file and generates a text file with one line
 *          per ROM in the format ROM_CODE#<"description" line>
 *
 */

public class GenerateCorrespondencyFile {

	private static final String PATH_ACTUAL_DB_FILE = "D:\\Software\\Hyperspin\\hyperlists\\MAME.xml";

	private static final String PATH_OUTPUT_FILE = "D:\\Software\\Hyperspin\\hyperlists\\correspondency.txt";

	/**
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
		String line1 = null, line2 = null;
		boolean exit = false;

		try {
			fDBFile = new File(PATH_ACTUAL_DB_FILE);
			frDBFile = new FileReader(fDBFile);
			brDBFile = new BufferedReader(frDBFile);

			fOut = new File(PATH_OUTPUT_FILE);
			fwOut = new FileWriter(fOut);
			bwOut = new BufferedWriter(fwOut);

			while (!exit) {
				line1 = brDBFile.readLine();

				if (line1 != null) {
					// if line2 != null, I have previous data. Let's see if we
					// find patterns
					if (line2 != null) {
						findPatternsAndAdd(line2, line1, bwOut);
					}

					line2 = brDBFile.readLine();
					if (line2 == null) {
						// no more data, nothing to do
						exit = true;
					}
				} else {
					// no more data, nothing to do
					exit = true;
				}

				// let's try do some stuff if no exit
				if (!exit) {
					findPatternsAndAdd(line1, line2, bwOut);
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
	 * Checks if we find the expected patterns, retrieves the useful info and adds a new line in the output
	 * file The pattern expected for line1 is "<game name=..." The pattern
	 * expected for line1 is "<description>..."
	 * 
	 * @param line1
	 *            The line 1
	 * @param line2
	 *            The line 2
	 * @param bOut
	 *            The output file
	 * @throws IOException 
	 */
	private static void findPatternsAndAdd(String line1, String line2,
			BufferedWriter bOut) throws IOException {
		if (line1.trim().startsWith("<game")
				&& line2.trim().startsWith("<description>")) {
			String code = HyperspinUtils.extractCode (line1);
			System.out.println(code);
			bOut.write(code + "#" + line2 + "\n");
			bOut.flush();
		}

	}
	

}
