import java.io.*;
import java.util.*;

public class Playground {
	public static void main(String [] args) {
/*
		int[][] adjMat = {
			{ (int)'a', (int)'b', (int)'c', (int)'d', (int)'e', (int)'f', (int)'g', (int)'h', (int)'i' }, 
			{ (int)'a', (int)'b', (int)'c', (int)'d', (int)'e', (int)'f', (int)'g', (int)'h', (int)'i' },  
			{ (int)'a', (int)'b', (int)'c', (int)'d', (int)'e', (int)'f', (int)'g', (int)'h', (int)'i' },  
			{ (int)'a', (int)'b', (int)'c', (int)'d', (int)'e', (int)'f', (int)'g', (int)'h', (int)'i' },  
			{ (int)'a', (int)'b', (int)'c', (int)'d', (int)'e', (int)'f', (int)'g', (int)'h', (int)'i' },   
			{ (int)'a', (int)'b', (int)'c', (int)'d', (int)'e', (int)'f', (int)'g', (int)'h', (int)'i' },  
			{ (int)'a', (int)'b', (int)'c', (int)'d', (int)'e', (int)'f', (int)'g', (int)'h', (int)'i' },   
			{ (int)'a', (int)'b', (int)'c', (int)'d', (int)'e', (int)'f', (int)'g', (int)'h', (int)'i' },   
			{ (int)'a', (int)'b', (int)'c', (int)'d', (int)'e', (int)'f', (int)'g', (int)'h', (int)'i' }
		};	

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
								if (adjMat[i][j] == (int)characters[k].charAt(0)) {
									adjMat[i][j] = 1;
									found = true;
									break;
								} 
							}
						}
					} else {
						for (int j = 0; j < adjMat[0].length; j++) {
							adjMat[i][j] = 0;
						}
					}
				}
			}
		} catch (IOException e) { // error handling
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		for (int i = 0; i < adjMat.length; i++) {
			for (int j = 0; j < adjMat[0].length; j++) {
				if (adjMat[i][j] > 1) {
					adjMat[i][j] = 0;
				}
			}
		}			

		for (int i = 0; i < adjMat.length; i++) {
			for (int j = 0; j < adjMat[0].length; j++) {
				System.out.print(adjMat[i][j] + "\t");
			}
			System.out.println();
		}
*/
        String input = "b_3,e_2,f_3,i_9";
        String[] pairs = input.split("[,\\_]");
		System.out.println(Arrays.toString(pairs));

/*		
        String input = "b_3,e_2,f_3,i_9";
        String[] pairs = input.split(",");
		System.out.println(Arrays.toString(pairs));
		System.out.println();
        String[][] result = new String[pairs.length][2];
		
        for (int i = 0; i < pairs.length; i++) {
            String[] pair = pairs[i].split("_");
            if (pair.length == 2) {
                result[i][0] = pair[0];
                result[i][1] = pair[1];
            } else {
                // Handle cases where the pair is not formatted correctly
                System.err.println("Error: Invalid pair format - " + pairs[i]);
            }
        }

        // Print the 2D array
        for (String[] pair : result) {
            System.out.println("[" + pair[0] + ", " + pair[1] + "]");
        }
*/
	}
}