import java.io.*;
import java.util.*;

public class Test {
	public static void main(String [] args) {	
	
		HashMap<String, Integer> letters = new HashMap<>();
        letters.put("a", 0);
        letters.put("b", 1);
        letters.put("c", 2);
        letters.put("d", 3);
        letters.put("e", 4);
        letters.put("f", 5);
        letters.put("g", 6);
        letters.put("h", 7);
        letters.put("i", 8);
/*	
		int lineCount = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
			while (reader.readLine() != null) {
				lineCount++;
			}
		} catch (IOException e) { // error handling
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		int[][] adjMat = new int[lineCount][lineCount];

		try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
			for (int i = 0; i < adjMat.length; i++) {
				String line = reader.readLine(); // this contains the contents of the input text file
				if (line != null) {
					int colonIndex = line.indexOf(':');
					String afterColon = line.substring(colonIndex + 1).trim();
					if (!afterColon.isEmpty()) {
						String[] characters = afterColon.split(",");
						for (int j = 0; j < adjMat[0].length; j++) {
							for (int k = 0; k < characters.length; k++) {
								boolean found = false;
								if (letters.containsKey(characters[k])) {
									if (j == letters.get(characters[k])) {
										adjMat[i][j] = 1;
										found = true;
										break;
									} 
								}
							}
						}
					}
				} else {
					for (int j = 0; j < adjMat[0].length; j++) {
						adjMat[i][j] = 0;
					}
				}
			}
		} catch (IOException e) { // error handling
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		for (int i = 0; i < adjMat.length; i++) {
			for (int j = 0; j < adjMat[0].length; j++) {
				System.out.print(adjMat[i][j] + "\t");
			}
			System.out.println();
		}
		
		System.out.println();
*/
	
		int lineCountTwo = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader("input_weights.txt"))) {
			while (reader.readLine() != null) {
				lineCountTwo++;
			}
		} catch (IOException e) { // error handling
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		int[][] adjMatTwo = new int[lineCountTwo][lineCountTwo];



		try (BufferedReader reader = new BufferedReader(new FileReader("input_weights.txt"))) {
			for (int i = 0; i < adjMatTwo.length; i++) {
				String line = reader.readLine(); // this contains the contents of the input text file
				if (line != null) {
					int colonIndex = line.indexOf(':');
					String afterColon = line.substring(colonIndex + 1).trim();
					if (!afterColon.isEmpty()) {
						String[] characters = afterColon.split("[,\\_]");
						for (int j = 0; j < adjMatTwo[0].length; j++) {
							for (int k = 0; k < characters.length; k++) {
								boolean found = false;
								if (letters.containsKey(characters[k])) {
									if (j == letters.get(characters[k])) {
										int number = Integer.parseInt(characters[k + 1]);
										adjMatTwo[i][j] = number;
										found = true;
										break;
									} 
								}
							}
						}
					}
				} else {
					for (int j = 0; j < adjMatTwo[0].length; j++) {
						adjMatTwo[i][j] = 0;
					}
				}
			}
		} catch (IOException e) { // error handling
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
//		char[][] alphabet = { { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i' } };

		for (int i = 0; i < adjMatTwo.length; i++) {
			for (int j = 0; j < adjMatTwo[0].length; j++) {
				System.out.print(adjMatTwo[i][j] + "\t");
			}
			System.out.println();
		}	

	}
}