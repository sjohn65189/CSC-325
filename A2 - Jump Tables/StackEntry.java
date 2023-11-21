import java.io.*;
import java.util.*; 
public class StackEntry {
	public static void main(String [] args) {
		Stack<Character> stack = new Stack<>();
		
		// EnterIDLE()
		try (BufferedReader reader = new BufferedReader(new FileReader("stack.txt"))) {
			String line = reader.readLine();
			if (line != null) {
				String[] characters = line.split(",");
				for (int i = 0; i < characters.length; i++) {
					stack.push(characters[i].charAt(0));
				}
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		
		System.out.println("EnterIDLE()");
		System.out.println();		
		System.out.println(stack);
		System.out.println();
		
		
		// changeState(stack)
		System.out.println("ChangeToStack()");
		System.out.println();
	


	
		// ExitStack()
		
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("stack.txt"))) {
			for (int i = 0; i < stack.size(); i++) {
				char character = stack.get(i);
				writer.write(character);
				writer.write(",");
			}	
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		stack.clear();
		
		System.out.println("ExitIDLE()");
		System.out.println();	
		System.out.println(stack);
		System.out.println();
		
		// EnterStack()
		try (BufferedReader reader = new BufferedReader(new FileReader("stack.txt"))) {
			String line = reader.readLine();
			if (line != null) {
				String[] characters = line.split(",");
				for (int i = 0; i < characters.length; i++) {
					stack.push(characters[i].charAt(0));
				}
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	
		System.out.println("EnterStack()");
		System.out.println();	
		System.out.println(stack);
		System.out.println();
	}
}