import java.io.*;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class ReadFile {
	public static void main(String[] args) {
		try (BufferedReader reader = new BufferedReader(new FileReader("stack.txt"))) {
			String line = reader.readLine();
			if (line != null) {
				String[] characters = line.split(",");
				for (String charStr : characters) {
					System.out.println(charStr);
				}
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}