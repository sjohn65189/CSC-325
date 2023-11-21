import java.io.*;
import java.util.*; 
public class StackInput  
{  
	public static void main(String[] args)   
	{  
		Stack<Character> stack = new Stack<>();
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
		
		
		System.out.println("|   |");
		System.out.println("|---|");

		for (int i = stack.size() - 1; i >= 0; i--) {
			System.out.println("| " + stack.get(i) + " |");
			System.out.println("|---|");
		}	

		System.out.println("1. Push");
		System.out.println("2. Pop");
		System.out.println("3. Save & Move to Queue");
		System.out.println("4. Save & Move to List");
		System.out.println("5. Quit");
		System.out.print("? ");
		Scanner myObj = new Scanner(System.in);
		String line = myObj.nextLine();
		char[] characters = line.toCharArray();
		int args_one = Character.getNumericValue(characters[0]);
		boolean result = false;
		switch (args_one) {
			case 1:
				if (characters.length >= 3) {
					stack.push(characters[2]);
				}
				result = true;
				break;
			case 2:
				stack.pop();
				result = true;
				break;
			case 3:
	//			currentState = State.QUEUE;
				result = true;
				break;
			case 4:
	//			currentState = State.LIST;
				result = true;
				break;
			case 5:
				result = false;
				break;
		}
		myObj.close();
		System.out.println();
		System.out.println(result);
		System.out.println();
		
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

		System.out.println(stack);

	}
} 