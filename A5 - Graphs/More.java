import java.io.*;
import java.util.*;

public class More {
	public static HashMap<String, Integer> letters = new HashMap<>();
	static {
		letters.put("a", 0);
		letters.put("b", 1);
		letters.put("c", 2);
		letters.put("d", 3);
		letters.put("e", 4);
		letters.put("f", 5);
		letters.put("g", 6);
		letters.put("h", 7);
		letters.put("i", 8);
	}
	
	private static int makeAdjMatSize(String file) {
		int lineCount = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			while (reader.readLine() != null) {
				lineCount++;
			}
		} catch (IOException e) { // error handling
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return lineCount;
	}
	
	private static void setUpAdjMat_UnDirected(int[][] numbers) {
		try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
			for (int i = 0; i < numbers.length; i++) {
				String line = reader.readLine(); // this contains the contents of the input text file
				if (line != null) {
					int colonIndex = line.indexOf(':');
					String afterColon = line.substring(colonIndex + 1).trim();
					if (!afterColon.isEmpty()) {
						String[] characters = afterColon.split(",");
						for (int j = 0; j < numbers[0].length; j++) {
							for (int k = 0; k < characters.length; k++) {
								boolean found = false;
								if (letters.containsKey(characters[k])) {
									if (j == letters.get(characters[k])) {
										numbers[i][j] = 1;
										found = true;
										break;
									} 
								}
							}
						}
					}
				} else {
					for (int j = 0; j < numbers[0].length; j++) {
						numbers[i][j] = 0;
					}
				}
			}
		} catch (IOException e) { // error handling
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}	
	
	private static void setUpAdjMat_Directed(int[][] numbers) {	
		try (BufferedReader reader = new BufferedReader(new FileReader("input_weights.txt"))) {
			for (int i = 0; i < numbers.length; i++) {
				String line = reader.readLine(); // this contains the contents of the input text file
				if (line != null) {
					int colonIndex = line.indexOf(':');
					String afterColon = line.substring(colonIndex + 1).trim();
					if (!afterColon.isEmpty()) {
						String[] characters = afterColon.split("[,\\_]");
						for (int j = 0; j < numbers[0].length; j++) {
							for (int k = 0; k < characters.length; k++) {
								boolean found = false;
								if (letters.containsKey(characters[k])) {
									if (j == letters.get(characters[k])) {
										int number = Integer.parseInt(characters[k + 1]);
										numbers[i][j] = number;
										found = true;
										break;
									} 
								}
							}
						}
					}
				} else {
					for (int j = 0; j < numbers[0].length; j++) {
						numbers[i][j] = 0;
					}
				}
			}
		} catch (IOException e) { // error handling
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	private static void printAdjMat(int[][] numbers) {
		String letters = "abcdefghi";
		System.out.println(" " + " " + letters);
		for (int i = 0; i < numbers.length; i++) {
			char l = letters.charAt(i);
			System.out.print(l + " ");
			for (int j = 0; j < numbers[0].length; j++) {
				System.out.print(numbers[i][j]);
			}
			System.out.println();
		}
	}
	
	private static void resetAdjMat(int[][] numbers) {
		for (int i = 0; i < numbers.length; i++) {
			for (int j = 0; j < numbers[0].length; j++) {
				numbers[i][j] = 0;
			}
		}
	}

    private static String breadthFirstSearch(int[][] numbers, char startVert) {
        // Implement BFS algorithm and return the result as a string
        // Use 'a' as the starting vertex
		ArrayList<Character> seenList = new ArrayList<>(); // List Data Structure (initialization)
		Queue<Character> queue = new LinkedList<>(); // Queue Data Structure (initialization)
		String result = "";
		
		seenList.add(startVert);
		queue.add(startVert);
		while (!queue.isEmpty()) {
			char neighbor = ' ';
			char curVert = queue.remove();
			result += curVert;		
			int j = letters.get(Character.toString(curVert));
			
			for (int i = 0; i < numbers[j].length; i++) {
				if (numbers[j][i] == 1) {
					for (String k : letters.keySet()) {
						if (letters.get(k) == i) {
							neighbor = k.charAt(0);
							break;
						}
					}
						
					if (!seenList.contains(neighbor)) {
						seenList.add(neighbor);
						queue.add(neighbor);
					}
				}
			}
		}
		
		return result;
    }
	
	
    private static String depthFirstSearch(int[][] numbers, char startVert) {
        // Implement DFS algorithm and return the result as a string
        // Use 'a' as the starting vertex
		ArrayList<Character> seenList = new ArrayList<>(); // List Data Structure (initialization)
		Stack<Character> stack = new Stack<>(); // Stack Data Structure (initialization)
		String result = "";
		
		seenList.add(startVert);
		stack.push(startVert);
		while (!stack.isEmpty()) {
			char neighbor = ' ';
			char curVert = stack.pop();
			result += curVert;		
			int j = letters.get(Character.toString(curVert));
			
			for (int i = 0; i < numbers[j].length; i++) {
				if (numbers[j][i] == 1) {
					for (String k : letters.keySet()) {
						if (letters.get(k) == i) {
							neighbor = k.charAt(0);
							break;
						}
					}
						
					if (!seenList.contains(neighbor)) {
						seenList.add(neighbor);
						stack.push(neighbor);
					}
				}
			}
		}
		
		return result;
    }
	
/* 
	if (!seenList.contains(k.charAt(0))) {
		int pathLength = lettersPathLengths.get(Character.toString(curVert));
		pathLength += numbers[j][i];
		lettersPathLengths.replace(k, pathLength);
		break;
	}
*/

/*	
	if (letters.get(k) == i) {
		int pathLength = lettersPathLengths.get(Character.toString(curVert));
		pathLength += numbers[j][i];
		lettersPathLengths.replace(k, pathLength);
		break;
	}
*/	
	
/*	
	// Perform Dijkstra's algorithm and return the result as a string
    private static String dijkstra(int[][] numbers, char startVert) {
        // Implement Dijkstra's algorithm and return the result as a string
        // Use 'a' as the source vertex
		HashMap<String, Integer> lettersPathLengths = new HashMap<>();
		lettersPathLengths.put("a", 0);
		lettersPathLengths.put("b", 0);
		lettersPathLengths.put("c", 0);
		lettersPathLengths.put("d", 0);
		lettersPathLengths.put("e", 0);
		lettersPathLengths.put("f", 0);
		lettersPathLengths.put("g", 0);
		lettersPathLengths.put("h", 0);
		lettersPathLengths.put("i", 0);
		
		
		ArrayList<Character> seenList = new ArrayList<>(); // List Data Structure (initialization)
		Stack<Character> stack = new Stack<>(); // Stack Data Structure (initialization)
		
		seenList.add(startVert);
		stack.push(startVert);
		while (!stack.isEmpty()) {
			char neighbor = ' ';
			char curVert = stack.pop();		
			int j = letters.get(Character.toString(curVert));
			
			for (int i = 0; i < numbers[j].length; i++) {
				if (numbers[j][i] != 0) {
					for (String k : letters.keySet()) {
						if (!seenList.contains(k.charAt(0))) {
							if (letters.get(k) == i) {
								int pathLength = lettersPathLengths.get(Character.toString(curVert));
								pathLength += numbers[j][i];
								lettersPathLengths.replace(k, pathLength);
								break;
							}
						}
					}
				}
			}
			
			int traversal = -1;
			int target = Integer.MAX_VALUE;
			boolean targetAssigned = false;
			char vertice = ' ';

			for (int i = 0; i < numbers[j].length; i++) {
				if (numbers[j][i] != 0) {
					if ((checkForVertices(numbers, i)) == false) {
						for (String k : letters.keySet()) {
							if (letters.get(k) == i) {
								vertice = k.charAt(0);
								break;
							}
						}

						if (!seenList.contains(vertice)) {
							seenList.add(vertice);
						}
						continue;
					} else {
						int temp = numbers[j][i];
						if (targetAssigned == false) {
							target = temp;
							traversal = i;
							if (targetAssigned == false) {
								targetAssigned = true;
							}
						} else {
							if (temp < target) {
								target = temp;
								traversal = i;
							}
						}
					}
				}
			}
				
			for (int i = 0; i < numbers[j].length; i++) {
				if (target == numbers[j][i] && (traversal == i)) {
					for (String k : letters.keySet()) {
						if (letters.get(k) == i) {
							neighbor = k.charAt(0);
							break;
						}
					}
					break;
				}
			}
			if (neighbor != ' ') {
				if (!seenList.contains(neighbor)) {
					seenList.add(neighbor);
					stack.push(neighbor);
				}
			}
		}

		// Use a StringBuilder to build the result string
		StringBuilder resultBuilder = new StringBuilder();
		for (String k : lettersPathLengths.keySet()) {
			String message = String.format("%s:%d", k, lettersPathLengths.get(k));
			resultBuilder.append(message);
			resultBuilder.append(",");
		}

		// Remove the last comma
		if (resultBuilder.length() > 0) {
			resultBuilder.deleteCharAt(resultBuilder.length() - 1);
		}

		return resultBuilder.toString();			
    }
	
	
	private static boolean checkForVertices(int[][] num, int iterate) {
		boolean result = false;
		for (int i = 0; i < num[iterate].length; i++) {
			if (num[iterate][i] != 0) {
				result = true;
				break;
			}
		}
		return result;
	}
*/



	// below does the correct output
/*
	private static String dijkstra(int[][] numbers, char startVert) {
		// Initialize path lengths for each vertex
		HashMap<String, Integer> lettersPathLengths = new HashMap<>();
		for (String c : letters.keySet()) {
			lettersPathLengths.put(c, Integer.MAX_VALUE);
		}
		lettersPathLengths.put(String.valueOf(startVert), 0);

		// Create a list to track seen vertices
		ArrayList<Character> seenList = new ArrayList<>();

		while (seenList.size() < letters.size()) {
			char curVert = getMinDistanceVertex(lettersPathLengths, seenList);
			if (curVert == ' ') {
				break; // No more reachable vertices
			}

			int j = letters.get(String.valueOf(curVert));
			seenList.add(curVert);

			for (int i = 0; i < numbers[j].length; i++) {
				if (numbers[j][i] != 0 && !seenList.contains(getVertexByIndex(i))) {
					int pathLength = lettersPathLengths.get(String.valueOf(curVert)) + numbers[j][i];
					if (pathLength < lettersPathLengths.get(getVertexByIndex(i))) {
						lettersPathLengths.put(getVertexByIndex(i), pathLength);
					}
				}
			}
		}

		// Use a StringBuilder to build the result string
		StringBuilder resultBuilder = new StringBuilder();
		for (String k : lettersPathLengths.keySet()) {
			String message = String.format("%s:%d", k, lettersPathLengths.get(k));
			resultBuilder.append(message);
			resultBuilder.append(",");
		}

		// Remove the last comma
		if (resultBuilder.length() > 0) {
			resultBuilder.deleteCharAt(resultBuilder.length() - 1);
		}

		return resultBuilder.toString();
	}

	private static char getMinDistanceVertex(HashMap<String, Integer> lettersPathLengths, ArrayList<Character> seenList) {
		int minDistance = Integer.MAX_VALUE;
		char minVertex = ' ';
		for (String c : letters.keySet()) {
			char vertex = c.charAt(0);
			if (!seenList.contains(vertex) && lettersPathLengths.get(c) < minDistance) {
				minDistance = lettersPathLengths.get(c);
				minVertex = vertex;
			}
		}
		return minVertex;
	}

	private static String getVertexByIndex(int index) {
		for (String k : letters.keySet()) {
			if (letters.get(k) == index) {
				return k;
			}
		}
		return " ";
	}
*/

    private static String dijkstra(int[][] numbers, int startVertex) {
		HashMap<String, Integer> lettersPathLengths = new HashMap<>();
		lettersPathLengths.put("a", 0);
		lettersPathLengths.put("b", 1);
		lettersPathLengths.put("c", 2);
		lettersPathLengths.put("d", 3);
		lettersPathLengths.put("e", 4);
		lettersPathLengths.put("f", 5);
		lettersPathLengths.put("g", 6);
		lettersPathLengths.put("h", 7);
		lettersPathLengths.put("i", 8);
		
        int[] dist = new int[numbers.length];
        int[] prev = new int[numbers.length];
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(numbers.length, Comparator.comparingInt(v -> dist[v]));

        // Initialization
        for (int v = 0; v < numbers.length; v++) {
            dist[v] = Integer.MAX_VALUE;
            prev[v] = -1;
            priorityQueue.add(v);
        }

        // Set the distance of the starting vertex to 0
        dist[lettersPathLengths.get("a")] = 0;


        while (!priorityQueue.isEmpty()) {
            int u = priorityQueue.poll();

            for (int v = 0; v < numbers.length; v++) {
                if (numbers[u][v] != 0) {
                    int alt = dist[u] + numbers[u][v];
                    if (alt < dist[v]) {
                        dist[v] = alt;
                        prev[v] = u;
                        priorityQueue.add(v);
                    }
                }
            }
        }
		
		// Use a StringBuilder to build the result string
		StringBuilder resultBuilder = new StringBuilder();
		for (String key : letters.keySet()) {
			String message = String.format("%s:%d", key, dist[lettersPathLengths.get(key)]);
			resultBuilder.append(message);
			resultBuilder.append(",");
		}

		// Remove the last comma
		if (resultBuilder.length() > 0) {
			resultBuilder.deleteCharAt(resultBuilder.length() - 1);
		}

		return resultBuilder.toString();
		
    }


	public static void main(String[] args) {
		String inputFile = "input.txt";
		String inputFileWeighted = "input_weights.txt";
		String outputFile = "output.txt";
		
		int lineCount = makeAdjMatSize(inputFile);
		int[][] adjMat = new int[lineCount][lineCount];
		
		setUpAdjMat_UnDirected(adjMat);
		System.out.println("Adjacency Matrix (undirected):");

		printAdjMat(adjMat);
		System.out.println();
		
		String breadth = breadthFirstSearch(adjMat, 'a');	
		System.out.println("BFS: " + breadth);
		String depth = depthFirstSearch(adjMat, 'a');	
		System.out.println("DFS: " +depth);
		System.out.println();
		
		resetAdjMat(adjMat);
		setUpAdjMat_Directed(adjMat);
		
		System.out.println("Adjacency Matrix (directed w/weights):");
		printAdjMat(adjMat);
		System.out.println();
		
		String dijkstra = dijkstra(adjMat, 'a');	
		System.out.println("Dijkstra's: " + dijkstra);
	}
}

		