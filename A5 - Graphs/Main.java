//////////////////////////////////////
// author: Stephan Johnson			//
// date: 10/30/2023					//
// desc: A5 - Graphs				//
//////////////////////////////////////
import java.io.*;
import java.util.*;

public class Main {
	public static HashMap<String, Integer> letters = new HashMap<>(); // HashMap for the letters [a,b,c,d,e,f,g,h,i] (initialization)
	
	// handles the file reading to calculate the length for the adjacency matrix
	private static int makeAdjMatSize(String file) { 
		int lineCount = 0; // initializes the line count to 0
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			while (reader.readLine() != null) { // iterates through the file 
				lineCount++; // increments the line count
			}
		} catch (IOException e) { // error handling
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return lineCount;
	}
	
	// handles the file reading to set the undirected adjacency matrix
	private static void setUpAdjMat_UnDirected(int[][] numbers) {
		try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) { // reads from the input file that represents the undirected vertices
			for (int i = 0; i < numbers.length; i++) { // iterates through the double int array (adjacency matrix)
				String line = reader.readLine(); // this contains the contents of the input text file
				if (line != null) { // checks to see if the line does exist
					int colonIndex = line.indexOf(':'); // contains the index of the colon
					String afterColon = line.substring(colonIndex + 1).trim(); // contains the characters after the colon
					if (!afterColon.isEmpty()) { // checks to see if the string that contains the characters after the colon is not empty
						String[] characters = afterColon.split(","); // splits it by comma
						for (int j = 0; j < numbers[0].length; j++) { // iterates through the double int array by row (adjacency matrix)
							for (int k = 0; k < characters.length; k++) { // iterates the character array that contains the characters after the colon
								if (letters.containsKey(characters[k])) { // checks to see if the character is in the letters HashMap
									if (j == letters.get(characters[k])) { // checks to see if the current index matches with the character's value in the letters HashMap
										numbers[i][j] = 1; // initializes this spot in the adjacency matrix as 1
										numbers[j][i] = 1; // initializes this spot in the adjacency matrix as 1
										// the two lines (40 and 41) above represents the vertex pairs in their respective spots
										break;
									} 
								}
							}
						}
					}
				} else { // checks to see if the line does not exist
					for (int j = 0; j < numbers[0].length; j++) { // iterates through the double int array by row (adjacency matrix)
						numbers[i][j] = 0; // initializes this spot in the adjacency matrix as 0
					}
				}
			}
		} catch (IOException e) { // error handling
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}	
	
	// handles the file reading to set the directed adjacency matrix
	private static void setUpAdjMat_Directed(int[][] numbers) {	
		try (BufferedReader reader = new BufferedReader(new FileReader("input_weights.txt"))) { // reads from the input_weights file that represents the directed vertices
			for (int i = 0; i < numbers.length; i++) { // iterates through the double int array (adjacency matrix)
				String line = reader.readLine(); // this contains the contents of the input_weights text file
				if (line != null) { // checks to see if the line does exist
					int colonIndex = line.indexOf(':'); // contains the index of the colon
					String afterColon = line.substring(colonIndex + 1).trim(); // contains the characters after the colon
					if (!afterColon.isEmpty()) { // checks to see if the string that contains the characters after the colon is not empty
						String[] characters = afterColon.split("[,\\_]"); // splits it by comma as well as by underscore
						for (int j = 0; j < numbers[0].length; j++) { // iterates through the double int array by row (adjacency matrix)
							for (int k = 0; k < characters.length; k++) { // iterates the character array that contains the characters after the colon
								if (letters.containsKey(characters[k])) { // checks to see if the character is in the letters HashMap
									if (j == letters.get(characters[k])) { // checks to see if the current index matches with the character's value in the letters HashMap
										int number = Integer.parseInt(characters[k + 1]); // contains the number that is next to the character 
										numbers[i][j] = number; // initializes this spot in the adjacency matrix as the number
										break;
									} 
								}
							}
						}
					}
				} else { // checks to see if the line does not exist
					for (int j = 0; j < numbers[0].length; j++) { // iterates through the double int array by row (adjacency matrix)
						numbers[i][j] = 0; // initializes this spot in the adjacency matrix as 0
					}
				}
			}
		} catch (IOException e) { // error handling
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
	
	// handles the printing of the adjacency matrix 
	private static String printAdjMat(int[][] numbers) {
		StringBuilder result = new StringBuilder(); // represents the adjacency matrix
		String letters = "abcdefghi"; // represents the letters by column
		result.append(" " + " "); // makes two spaces
		for (int i = 0; i < numbers.length; i++) { // iterates through the adjacency matrix
			char l = letters.charAt(i); // contains the letter by the current index
			result.append(l); // adds the letter to the StringBuilder adjacency matrix representation
		}

		result.append("\n"); // makes a new line
		for (int i = 0; i < numbers.length; i++) { // iterates through the adjacency matrix
			char l = letters.charAt(i); // contains the letter by the current index
			result.append(l + " "); // adds the letter along with a space to the StringBuilder adjacency matrix representation
			for (int j = 0; j < numbers[0].length; j++) { // iterates through the adjacency matrix by row 
				result.append(numbers[i][j]); // adds the current spot in the adjacency matrix to the StringBuilder adjacency matrix representation
			}
			result.append("\n"); // makes a new line
		}
		
		return result.toString();
	}

	// implements BFS algorithm and returns the result as a string
    // uses 'a' as the starting vertex
	// based on the BFS psuedocode in 'L16 - Graphs...again' lecture notes
    private static String breadthFirstSearch(int[][] numbers, char startVert) {
		ArrayList<Character> seenList = new ArrayList<>(); // List Data Structure (initialization)
		Queue<Character> queue = new LinkedList<>(); // Queue Data Structure (initialization)
		String result = ""; // contains the BFS line
		
		seenList.add(startVert); // adds the starting vertex to the seenList
		queue.add(startVert); // adds the starting vertex to the queue
		while (!queue.isEmpty()) { // checks to see if the queue is not empty
			char neighbor = ' '; // contains the neighbor of the current vertex
			char curVert = queue.remove(); // initializes the current vertex by removing the element at the front of the queue
			result += curVert; // adds the current vertex to the BFS line
			int j = letters.get(Character.toString(curVert)); // contains the current vertex's value in the letters HashMap
			
			for (int i = 0; i < numbers[j].length; i++) { // iterates through the adjacency matrix by row 
				if (numbers[j][i] == 1) { // checks to see if the current spot in the adjacency matrix is a 1
					for (String k : letters.keySet()) { // iterates through the letters HashMap by letter
						if (letters.get(k) == i) { // checks to see if the current character's value in the letters HashMap matches with the current index 
							neighbor = k.charAt(0); // makes the neighbor as the current letter in the HashMap
							break;
						}
					}
						
					if (!seenList.contains(neighbor)) { // checks to see if the seenList does not contains the neighbor
						seenList.add(neighbor); // adds the neighbor to the seenList
						queue.add(neighbor); // adds the neighbor to the queue
					}
				}
			}
		}
		
		return result;
    }
	
	// implements DFS algorithm and return the result as a string
    // uses 'a' as the starting vertex
	// based on the DFS psuedocode in 'L16 - Graphs...again' lecture notes
    private static String depthFirstSearch(int[][] numbers, char startVert) {
		ArrayList<Character> seenList = new ArrayList<>(); // List Data Structure (initialization)
		Stack<Character> stack = new Stack<>(); // Stack Data Structure (initialization)
		String result = ""; // contains the DFS line
		
		seenList.add(startVert); // adds the starting vertex to the seenList
		stack.push(startVert); // adds the starting vertex to the stack
		while (!stack.isEmpty()) { // checks to see if the stack is not empty
			char neighbor = ' '; // contains the neighbor of the current vertex
			char curVert = stack.pop(); // initializes the current vertex by removing the element on the top of the stack
			result += curVert; // adds the current vertex to the DFS line	
			int j = letters.get(Character.toString(curVert)); // contains the current vertex's value in the letters HashMap
			
			for (int i = 0; i < numbers[j].length; i++) { // iterates through the adjacency matrix by row 
				if (numbers[j][i] == 1) { // checks to see if the current spot in the adjacency matrix is a 1
					for (String k : letters.keySet()) { // iterates through the letters HashMap by letter
						if (letters.get(k) == i) { // checks to see if the current character's value in the letters HashMap matches with the current index 
							neighbor = k.charAt(0); // makes the neighbor as the current letter in the HashMap
							break;
						}
					}
						
					if (!seenList.contains(neighbor)) { // checks to see if the seenList does not contains the neighbor
						seenList.add(neighbor); // adds the neighbor to the seenList
						stack.push(neighbor); // adds the neighbor to the stack
					}
				}
			}
		}
		
		return result;
    }

	// implements Dijkstra's algorithm and return the result as a string
    // uses 'a' as the starting vertex
	// based on the Dijkstra's psuedocode in https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
    private static String dijkstra(int[][] numbers, int startVertex) {	
        int[] dist = new int[numbers.length]; // contains the current distances from the current vertex to the other vertices
        int[] prev = new int[numbers.length]; // contains the pointers to previous visted vertices on the shortest path from the current vertex to the given vertex
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(numbers.length, Comparator.comparingInt(v -> dist[v])); // PriorityQueue Data Structure (initialization)

        for (int v = 0; v < numbers.length; v++) { // iterates through the adjacency matrix
            dist[v] = Integer.MAX_VALUE; // initializes the current spot in the distance array as "infinity"
            prev[v] = -1; // initializes the current spot in the previous array as "undefined"
            priorityQueue.add(v); // adds the vertex to the PriorityQueue
        }

        dist[letters.get("a")] = 0; // sets the distance of the starting vertex to 0

        while (!priorityQueue.isEmpty()) { // checks to see if the PriorityQueue is not empty
            int u = priorityQueue.poll(); // contains the element at the front of the PriorityQueue

            for (int v = 0; v < numbers.length; v++) { // iterates through the adjacency matrix
                if (numbers[u][v] != 0) { // checks to see if the current spot in the adjacency matrix does not equal 0
                    int alt = dist[u] + numbers[u][v]; // calculates the alt distance to v through u
                    if (alt < dist[v]) { // if the alt distance is shorter than the current distance to v
                        dist[v] = alt; // updates the current spot in the distance array as the alt distance
                        prev[v] = u; // update the current spot in the predecessor array as u
                        priorityQueue.add(v); // adds the vertex to the PriorityQueue
                    }
                }
            }
        }
		
		StringBuilder resultBuilder = new StringBuilder(); // contains the Dijkstra's algorithm line
		for (String key : letters.keySet()) { // iterates through the letters HashMap by letter
			String message = String.format("%s:%d", key, dist[letters.get(key)]); // contains the current letter along with its value
			resultBuilder.append(message); // adds the current letter along with its value in the distance array to the Dijkstra's algorithm StringBuilder result
			resultBuilder.append(","); // adds a comma
		}

		if (resultBuilder.length() > 0) {
			resultBuilder.deleteCharAt(resultBuilder.length() - 1); // removes the last comma
		}

		return resultBuilder.toString();
		
    }

	public static void main(String[] args) {	
		String inputFile = "input.txt"; // the input text file
		String inputFileWeighted = "input_weights.txt"; // the input_weights text file
		String outputFile = "output.txt"; // the output text file
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) { // writes to the output text file
			int lineCount = makeAdjMatSize(inputFile); // initializes the line count for the undirected adjacency matrix
			int[][] adjMat_UnDirected = new int[lineCount][lineCount]; // initializes the double int array (adjacency matrix) with the new line count 
			for (int i = 0; i < lineCount; i++) { // iterates through the letters HashMap using the line count
				char letter = (char) ('a' + i); // initializes the letters
				letters.put(String.valueOf(letter), i); // puts the letters in the letters HashMap
			}
			
			setUpAdjMat_UnDirected(adjMat_UnDirected); // sets up the undirected adjacency matrix 
			String breadth = breadthFirstSearch(adjMat_UnDirected, 'a'); // operates the BFS algorithm with the undirected adjacency matrix
			String depth = depthFirstSearch(adjMat_UnDirected, 'a'); // operates the DFS algorithm with the undirected adjacency matrix

			writer.write("Adjacency Matrix (undirected):"); // writes the undirected adjacency matrix format to the output text file
			writer.newLine(); // writes a new line 
			String printedAdjMat = printAdjMat(adjMat_UnDirected); // contains the undirected adjacency matrix
			writer.write(printedAdjMat); // writes the undirected adjacency matrix
			writer.newLine(); // writes a new line 
			writer.write("BFS: " + breadth); // writes the BFS algorithm output
			writer.newLine(); // writes a new line 
			writer.write("DFS: " + depth); // writes the DFS algorithm output
			writer.newLine(); // writes a new line 
			writer.newLine(); // writes a new line 

			lineCount = makeAdjMatSize(inputFileWeighted); // reinitializes the line count for the directed adjacency matrix
			int[][] adjMat_Directed = new int[lineCount][lineCount]; // reinitializes the double int array (adjacency matrix) with the new line count 
			for (int i = 0; i < lineCount; i++) { // reiterates through the letters HashMap using the line count
				char letter = (char) ('a' + i); // reinitializes the letters
				letters.put(String.valueOf(letter), i); // puts the letters in the letters HashMap
			}
			
			setUpAdjMat_Directed(adjMat_Directed); // sets up the directed adjacency matrix 
			String dijkstra = dijkstra(adjMat_Directed, 'a'); // operates the Dijkstra's algorithm with the directed adjacency matrix
			writer.write("Adjacency Matrix (directed w/weights):"); // writes the directed adjacency matrix format to the output text file
			writer.newLine(); // writes a new line 
			printedAdjMat = printAdjMat(adjMat_Directed); // contains the directed adjacency matrix
			writer.write(printedAdjMat); // writes the directed adjacency matrix
			writer.newLine(); // writes a new line 
			writer.write("Dijkstra's: " + dijkstra); // writes the Dijkstra's algorithm output
		} catch (IOException e) { // error handling
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}