package es.beltazhor.hyperspin.utils.romUtils;

/**
 * @author beltazhor
 * @version 0.1
 * 
 *          Utils
 *
 */
public class HyperspinUtils {

	/**
	 * Extracts the code
	 * 
	 * @param line
	 *            the line
	 * @return The code
	 */
	public static String extractCode(String line) {
		String result = null;
		if (line != null && line.trim().startsWith("<game")) {
			int posFirstQuoteL1 = line.indexOf("\"");
			result = line.substring(posFirstQuoteL1 + 1,
					line.substring(posFirstQuoteL1 + 1).indexOf("\"")
							+ posFirstQuoteL1 + 1);
			
		}
		return result;
	}
}
