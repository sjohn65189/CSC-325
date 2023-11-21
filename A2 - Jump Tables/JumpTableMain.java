//////////////////////////////////////
// author: Stephan Johnson			//
// date: 09/25/2023					//
// desc: A2 - Jump Tables			//
//////////////////////////////////////

import java.io.*;
import java.util.*;

// interface for the stateEnterMeths HashMap
interface StateEnterExitMeth {
	public void invoke();
}

// interface for the stateStayMeths HashMap
interface StateStayMeth {
	public boolean invoke();
}

// initializes the states 
enum State {
	IDLE, // The IDLE State
	STACK, // The STACK State
	QUEUE, // The QUEUE State
	LIST; // The LIST State
}

// Screen class
class Screen {
	private State currentState; // keeps track of the current State
	private HashMap<State, StateEnterExitMeth> stateEnterMeths; // HashMap for the State Enter Methods (initialization)
	private HashMap<State, StateStayMeth> stateStayMeths; // HashMap for the State Stay Methods (initialization)
	private HashMap<State, StateEnterExitMeth> stateExitMeths; // HashMap for the State Exit Methods (initialization)
	
	private ArrayList<Character> list; // List Data Structure (initialization)
	private Stack<Character> stack; // Stack Data Structure (initialization)
	private Queue<Character> queue; // Queue Data Structure (initialization)

	private Scanner myObj; // this implements user inputting for the StayIDLE() method (initialization)
	
	private File StackFile = new File("stack.txt"); // The Stack text file for file reading and file writing 
	private File QueueFile = new File("queue.txt"); // The Queue text file for file reading and file writing 
	private File ListFile = new File("list.txt"); // The List text file for file reading and file writing 
	
	// Screen constructor
	public Screen() {
		currentState = State.IDLE; // initially sets the State to the IDLE state 
		stateEnterMeths = new HashMap<>(); // HashMap for the State Enter Methods (declaration)
		stateStayMeths = new HashMap<>(); // HashMap for the State Stay Methods (declaration)
		stateExitMeths = new HashMap<>(); // HashMap for the State Exit Methods (declaration)
		
		list = new ArrayList<>(); // List Data Structure (declaration)
		stack = new Stack<>(); // Stack Data Structure (declaration)
		queue = new LinkedList<>(); // Queue Data Structure (declaration)
		
		myObj = new Scanner(System.in); // this implements user inputting for the StayIDLE() method (declaration)
		
		stateEnterMeths.put(State.IDLE, () -> { StateEnterIdle();  }); //  puts the IDLE state along with its corresponding method (StateEnterIdle()) in the StateEnterMethods HashMap 
		stateEnterMeths.put(State.STACK, () -> { StateEnterStack();  }); //  puts the STACK state along with its corresponding method (StateEnterStack()) in the StateEnterMethods HashMap 
		stateEnterMeths.put(State.QUEUE, () -> { StateEnterQueue();  }); //  puts the QUEUE state along with its corresponding method (StateEnterQueue()) in the StateEnterMethods HashMap 
		stateEnterMeths.put(State.LIST, () -> { StateEnterList();  }); //  puts the LIST state along with its corresponding method (StateEnterList()) in the StateEnterMethods HashMap 
		
		stateStayMeths.put(State.IDLE, () -> StateStayIdle()); //  puts the IDLE state along with its corresponding method (StateStayIdle()) in the StateStayMethods HashMap
		stateStayMeths.put(State.STACK, () -> StateStayStack()); //  puts the STACK state along with its corresponding method (StateStayStack()) in the StateStayMethods HashMap
		stateStayMeths.put(State.QUEUE, () -> StateStayQueue()); //  puts the QUEUE state along with its corresponding method (StateStayQueue()) in the StateStayMethods HashMap
		stateStayMeths.put(State.LIST, () -> StateStayList()); //  puts the LIST state along with its corresponding method (StateStayList()) in the StateStayMethods HashMap

		stateExitMeths.put(State.IDLE, () -> { StateExitIdle();  }); //  puts the IDLE state along with its corresponding method (StateExitIdle()) in the StateExitMethods HashMap
		stateExitMeths.put(State.STACK, () -> { StateExitStack();  }); //  puts the STACK state along with its corresponding method (StateExitStack()) in the StateExitMethods HashMap
		stateExitMeths.put(State.QUEUE, () -> { StateExitQueue();  }); //  puts the QUEUE state along with its corresponding method (StateExitQueue()) in the StateExitMethods HashMap
		stateExitMeths.put(State.LIST, () -> { StateExitList();  }); //  puts the LIST state along with its corresponding method (StateExitList()) in the StateExitMethods HashMap
	}
	
	public boolean doState() {
		stateEnterMeths.get(currentState).invoke(); //  Key: the current State   Value: the current State's corresponding method for the StateEnterMethods HashMap 
		boolean continueState = stateStayMeths.get(currentState).invoke(); //  Key: the current State   Value: the current State's corresponding method for the StateStayMethods HashMap 
		stateExitMeths.get(currentState).invoke(); //  Key: the current State   Value: the current State's corresponding method for the StateExitMethods HashMap 
		return continueState;
	}
	
	// changes the current State to the new State
	public void changeState(State newState) { 
		if (currentState != newState) {
			currentState = newState;
		}
	}
	
	// handles the file reading for every data Structure (stack, queue, list)
	private void FileReading(File dataStructure) {
		switch (dataStructure.getName()) {
			case "stack.txt":
				try (BufferedReader reader = new BufferedReader(new FileReader("stack.txt"))) {
					String line = reader.readLine(); // this contains the contents of the stack text file
					if (line != null) {
						String[] characters = line.split(","); // removes the commas from the stack text file and puts the contents into a String array
						for (int i = 0; i < characters.length; i++) { // iterates through the String array
							stack.push(characters[i].charAt(0)); // puts the contents into the stack
						}
					}
				} catch (IOException e) { // error handling
					System.out.println("An error occurred.");
					e.printStackTrace();
				}
				break;
			case "queue.txt":
				try (BufferedReader reader = new BufferedReader(new FileReader("queue.txt"))) {
					String line = reader.readLine(); // this contains the contents of the queue text file
					if (line != null) {
						String[] characters = line.split(","); // removes the commas from the queue text file and puts the contents into a String array
						for (String charStr : characters) { // iterates through the String array
							queue.add(charStr.charAt(0)); // puts the contents into the queue
						}
					}
				} catch (IOException e) { // error handling
					System.out.println("An error occurred.");
					e.printStackTrace();
				}
				break;
			case "list.txt":
				try (BufferedReader reader = new BufferedReader(new FileReader("list.txt"))) {
					String line = reader.readLine(); // this contains the contents of the list text file
					if (line != null) {
						String[] characters = line.split(","); // removes the commas from the list text file and puts the contents into a String array
						for (String charStr : characters) { // iterates through the String array
							list.add(charStr.charAt(0)); // puts the contents into the list
						}
					}
				} catch (IOException e) { // error handling
					System.out.println("An error occurred.");
					e.printStackTrace();
				}
				break;
		}
	}
	
	// ENTER METHODS
	private void StateEnterIdle() {
		FileReading(StackFile); // reads the Stack File
		FileReading(QueueFile);	// reads the Queue File	
		FileReading(ListFile); // reads the List File
	}
	private void StateEnterStack() {
		FileReading(StackFile); // reads the Stack File
	}
	private void StateEnterQueue() {
		FileReading(QueueFile); // reads the Queue File	
	}
	private void StateEnterList() {
		FileReading(ListFile); // reads the List File
	}
	
	// STAY METHODS
	private boolean StateStayIdle() {
		// asks the user what data strucuture they want to try
		System.out.print("\u001B[33m"); // makes the menu turn yellow (Bonus) 
		System.out.println("1. Stack");
		System.out.println("2. Queue");
		System.out.println("3. List");
		System.out.println("4. Quit");
		System.out.print("? ");
		System.out.print("\u001B[0m"); // resets the color
		int select = myObj.nextInt(); // takes the user input
		switch (select) { 
			case 1:
				changeState(State.STACK); // changes the state to the STACK State
				break;
			case 2:
				changeState(State.QUEUE); // changes the state to the QUEUE State
				break;
			case 3:
				changeState(State.LIST); // changes the state to the LIST State
				break;
			case 4:
				return false; // exits out of the doState() cycle
		}
		return true; // continues the doState() cycle
	}
	
	private boolean StateStayStack() {
		System.out.print("\033[H\033[2J"); // Clear the screen 
		
		System.out.print("\u001B[31m"); // makes the stack turn red (Bonus) 
		
		// draws the stack
		System.out.println("|   |");
		System.out.println("|---|");
		for (int i = stack.size() - 1; i >= 0; i--) { // iterates through the stack backwards
			System.out.println("| " + stack.get(i) + " |");
			System.out.println("|---|");
		}	
		System.out.print("\u001B[0m"); // resets the color
		
		System.out.print("\u001B[33m"); // makes the menu turn yellow (Bonus) 
		
		// asks the user what operation they want to try
		System.out.println("1. Push");
		System.out.println("2. Pop");
		System.out.println("3. Save & Move to Queue");
		System.out.println("4. Save & Move to List");
		System.out.println("5. Quit");
		System.out.print("? ");
		
		System.out.print("\u001B[0m"); // resets the color
//        Bonus.check(stack);
		
		Scanner myObj = new Scanner(System.in); // implements user inputting (initialization/declaration)
		String line = myObj.nextLine(); // this contains the contents of the user's input
		char[] characters = line.toCharArray(); // turns the user's input (String) into a Character Array;
		int args_one = Character.getNumericValue(characters[0]); // converts the user's first input entry into an Integer
		boolean result = false; // flag that indicates to continue or to exist the doState() method
		switch (args_one) {
			case 1: // Character Array's format: [{operation_input} {space} {character_input}] 
				if (characters.length >= 3) { 
					stack.push(characters[2]); // pushes the user's second input entry into the stack
				}
				result = true; // continues the doState() cycle
				break;
			case 2: // Character Array's format: [{operation_input}] 
				if (!stack.isEmpty()) {
					stack.pop(); // pops the stack's top element
				}
				result = true; // continues the doState() cycle
				break;
			case 3: // Character Array's format: [{operation_input}] 
				changeState(State.QUEUE); // changes the state to the QUEUE State
				result = true; // continues the doState() cycle
				break;
			case 4: // Character Array's format: [{operation_input}] 
				changeState(State.LIST); // changes the state to the LIST State
				result = true; // continues the doState() cycle
				break;
			case 5: // Character Array's format: [{operation_input}] 
				result = false; // exits out of the doState() cycle
				break;
		}
		return result;
	}

	private boolean StateStayQueue() {
		System.out.print("\033[H\033[2J"); // Clear the screen 
		
		System.out.print("\u001B[32m"); // makes the queue turn green (Bonus) 
		
		// draws the queue
        for (Character c : queue) { // iterates through the queue
            String formatted = String.format("| %s ", c);
            System.out.print(formatted);
        }
        System.out.print("|");
		System.out.println();
		
		System.out.print("\u001B[0m"); // resets the color
		
		System.out.print("\u001B[33m"); // makes the menu turn yellow (Bonus) 
		
		// asks the user what operation they want to try
		System.out.println("1. Enqueue");
		System.out.println("2. Dequeue");
		System.out.println("3. Save & Move to Stack");
		System.out.println("4. Save & Move to List");
		System.out.println("5. Quit");
		System.out.print("? ");		
		
		System.out.print("\u001B[0m"); // resets the color
//		Bonus.check(queue);

		Scanner myObj = new Scanner(System.in); // implements user inputting (initialization/declaration)
		String line = myObj.nextLine(); // this contains the contents of the user's input
		char[] characters = line.toCharArray(); // turns the user's input (String) into a Character Array;
		int args_one = Character.getNumericValue(characters[0]); // converts the user's first input entry into an Integer
		boolean result = false; // flag that indicates to continue or to exist the doState() method
		switch (args_one) {
			case 1: // Character Array's format: [{operation_input} {space} {character_input}] 
				if (characters.length >= 3) {
					queue.add(characters[2]); // pushes the user's second input entry into the queue
				}
				result = true; // continues the doState() cycle
				break;
			case 2: // Character Array's format: [{operation_input}] 
				if (!queue.isEmpty()) {
					queue.remove();	// removes the element at the front of the queue	
				}
				result = true; // continues the doState() cycle
				break;
			case 3: // Character Array's format: [{operation_input}]
				changeState(State.STACK); // changes the state to the STACK State
				result = true; // continues the doState() cycle
				break;
			case 4: // Character Array's format: [{operation_input}] 
				changeState(State.LIST); // changes the state to the LIST State
				result = true; // continues the doState() cycle
				break;
			case 5: // Character Array's format: [{operation_input}] 
				result = false; // exits out of the doState() cycle
				break;
		}
		return result;
	}

	private boolean StateStayList() {
		System.out.print("\033[H\033[2J"); // Clear the screen 
		
		System.out.print("\u001B[34m");  // makes the list turn blue (Bonus) 
		
		// draws the list
		System.out.print("{ ");
		for (Character c : list) { // iterates through the list
			String formatted = String.format(" %s, ", c);
			System.out.print(formatted);
		}
		System.out.print(" }");
		System.out.println();
		
		System.out.print("\u001B[0m"); // resets the color
		
		System.out.print("\u001B[33m"); // makes the menu turn yellow (Bonus) 
		
		// asks the user what operation they want to try
		System.out.println("1. Append");
		System.out.println("2. Remove");
		System.out.println("3. Save & Move to Stack");
		System.out.println("4. Save & Move to Queue");
		System.out.println("5. Quit");
		System.out.print("? ");
		
		System.out.print("\u001B[0m"); // resets the color
//		Bonus.check(list);

		Scanner myObj = new Scanner(System.in); // implements user inputting (initialization/declaration)
		String line = myObj.nextLine(); // this contains the contents of the user's input
		char[] characters = line.toCharArray(); // turns the user's input (String) into a Character Array;
		int args_one = Character.getNumericValue(characters[0]); // converts the user's first input entry into an Integer
		boolean result = false; // flag that indicates to continue or to exist the doState() method
		switch (args_one) {
			case 1: // Character Array's format: [{operation_input} {space} {character_input}] 
				if (characters.length >= 3) { 
					list.add(characters[2]); // pushes the user's second input entry into the list
				}
				result = true; // continues the doState() cycle
				break;
			case 2: // Character Array's format: [{operation_input}] 
				if (!list.isEmpty()) {
					list.remove(list.size() - 1); // removes the element at the front of the list
				}
				result = true; // continues the doState() cycle
				break;
			case 3: // Character Array's format: [{operation_input}] 
				changeState(State.STACK); // changes the state to the STACK State
				result = true; // continues the doState() cycle
				break;
			case 4: // Character Array's format: [{operation_input}] 
				changeState(State.QUEUE); // changes the state to the QUEUE State
				result = true; // continues the doState() cycle
				break;
			case 5: // Character Array's format: [{operation_input}] 
				result = false; // exits out of the doState() cycle
				break;
		}
		return result;
	} 
	
	// handles the file writing for every data Structure (stack, queue, list)
	private void FileWriting(File dataStructure) {
		switch (dataStructure.getName()) {
			case "stack.txt":
				try (BufferedWriter writer = new BufferedWriter(new FileWriter("stack.txt"))) {
					for (int i = 0; i < stack.size(); i++) { // iterates through the stack
						char character = stack.get(i);
						writer.write(character); // puts the contents of the stack into the stack text file
						writer.write(","); // puts a comma after writing the character
					}	
				} catch (IOException e) { // error handling
					System.out.println("An error occurred.");
					e.printStackTrace();
				}
				break;
			case "queue.txt":
				try (BufferedWriter writer = new BufferedWriter(new FileWriter("queue.txt"))) {
					while (!queue.isEmpty()) { // iterates through the queue
						char character = queue.poll();
						writer.write(character); // puts the contents of the queue into the queue text file
						writer.write(","); // puts a comma after writing the character
					}
				} catch (IOException e) { // error handling
					System.out.println("An error occurred.");
					e.printStackTrace();
				}
				break;
			case "list.txt":
				try (BufferedWriter writer = new BufferedWriter(new FileWriter("list.txt"))) {
					for (int i = 0; i < list.size(); i++) { // iterates through the list
						char character = list.get(i);
						writer.write(character); // puts the contents of the list into the list text file
						writer.write(","); // puts a comma after writing the character
					}
				} catch (IOException e) { // error handling
					System.out.println("An error occurred.");
					e.printStackTrace();
				}
				break;
		}
	}
	
	// EXIT METHODS
	private void StateExitIdle() {
		FileWriting(StackFile); // writes the Stack File
		FileWriting(QueueFile); // writes the Queue File
		FileWriting(ListFile); // writes the List File
	}
	private void StateExitStack() {
		FileWriting(StackFile); // writes the Stack File
		stack.clear(); // clears the stack
	} 
	private void StateExitQueue() {	
		FileWriting(QueueFile); // writes the Queue File
		queue.clear(); // clears the queue
	}
	private void StateExitList() {
		FileWriting(ListFile); // writes the List File
		list.clear(); // clears the list
	}
}

public class JumpTableMain {
	public static void main(String[] args) {
		Screen screen = new Screen();
		boolean keepRunning = true;
		while(keepRunning) {
			keepRunning = screen.doState();
		}
	}
}