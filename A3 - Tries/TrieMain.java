//////////////////////////////////////
// author: Stephan Johnson			//
// date: 10/08/2023					//
// desc: A3 - Tries					//
//////////////////////////////////////

import java.io.*;
import java.util.*;

// Node class
class Node {
	private Node[] children; // an array of Node pointers covering all children the node could have
	private boolean isTerminal; // keeps tracks on whether the node is a terminal node or not
	
	// Node constructor
	public Node() {
		children = new Node[Trie.ALPHABET]; // makes the children array to be the same size as the alphabet
		for (int i = 0; i < children.length; i++) {
			children[i] = null; // initializes each children to be null
		}
		isTerminal = false; // initially sets the node to not be a terminal node
	}
	
	// Accessor for the node's children
	public Node[] getChildren() {
		return children;
	}
	
	// Mutator for the node's children
	public void setChildren(Node[] new_children) {
		this.children = new_children;
	}
	
	// Accessor for the node's terminal value
	public boolean getIsTerminal() {
		return isTerminal;
	}
	
	// Mutator for the node's terminal value
	public void setIsTerminal(boolean new_isTerminal) {
		this.isTerminal = new_isTerminal;
	}
}

// Trie class
class Trie {
	private Node root; // pointer to the root node
	public static final int ALPHABET = 128; // Alphabet size
	
	// Trie constructor
	public Trie() {
		this.root = null; // initializes the root to be null
	}
	
	// Insert method
	public void insert(String word) {
		if (root == null) { // initializes the root as a new node when the root is null
			root = new Node(); 
		}
		
		Node curr = root; // Node pointer that points to the root initially
		for (int i = 0; i < word.length(); i++) { // iterates through the word
			int character = word.charAt(i); // get the ASCII value of each character of the word
			
			if (curr.getChildren()[character] == null) { // attempts to find the current character in the children of the node through the ASCII value
				Node newNode = new Node();
				curr.getChildren()[character] = newNode; // makes it a new node if the current character is not one of the children of the node
			}
			curr = curr.getChildren()[character]; // sets the Node pointer as the newly initialized Node to find the next character of the word
		}
		curr.setIsTerminal(true); // indicates that this is the end of the word
	}
	
	// Search method
	public boolean search(String word) {
		if (root == null) { // indicates that the word is not in the trie to begin with
			return false;
		}
		
		Node curr = root; // Node pointer that points to the root initially

		for (int l = 0; l < word.length(); l++) { // iterates through the word
			for (int i = 0; i < curr.getChildren().length; i++) { // iterates through the children of the node
				char character = (char) i; // the current character that is one of the node's children
				
				if ((curr.getChildren()[i] == null) && (word.charAt(l)) == character) { // sees if the current character exists as one of the node's children | sees if the current character matches with the character of the word
					return false; // indicates that the word is not in the trie
				} else if ((curr.getChildren()[i] != null) && (word.charAt(l)) == character) { // sees if the current character exists as one of the node's children | sees if the current character matches with the character of the word
					curr = curr.getChildren()[i]; // sets the Node pointer as the node that was just found that represents as the character of the word to find the next character of the word
				}
			}	
		}
	
		return curr.getIsTerminal(); // indicates that the word is in the trie
	}
	
	// printAllWords method
	public void printAllWords() {
		StringBuilder word = new StringBuilder(); // prints all the words in the trie
		Print(root, word); // helper method for the printing process
	}
	
	// handles the printing for the printAllWords method
	private void Print(Node node, StringBuilder word) {
		if (node == null) { // does nothing if the node doesn't exist
			return;
		} else {
			for (int i = 0; i < node.getChildren().length; i++) { // iterates through the children of the node
				if (node.getChildren()[i] != null) { // sees if the current character exists as one of the node's children
					char character = (char) i; // the current character that is one of the node's children
					word.append(character); // add the character into the StringBuilder since it does exist
					if (node.getChildren()[i].getIsTerminal()) { // sees if the current character exists as a terminal node as one of the node's children			
						System.out.print("'" + word.toString() + "', "); // prints out the entire word
					}
					Print(node.getChildren()[i], word); // recursively calls the node that was just found that represents as the character of the word to find the next character of the word
					word.setLength(word.length() - 1); // removes the last character in the StringBuilder when going back to the parent from the recursive call to find more children to print
				}
			}
		}
	}
	
	// printAllWords(prefix) method
	public void printAllWords(String prefix) {
		StringBuilder word = new StringBuilder(); // prints all the words in the trie
		prefixPrint(prefix); // helper method for the printing process
	}
	
	// handles the printing for the printAllWords(prefix) method
	private void prefixPrint(String prefix) {
		Node curr = root; // Node pointer that points to the root initially
		StringBuilder s = new StringBuilder(); // prints all the words in the trie
		for (int i = 0; i < prefix.length(); i++) { // iterates through the prefix word
			int character = prefix.charAt(i); // get the ASCII value of each character of the prefix word
			
			if (curr.getChildren()[character] == null) { // attempts to find the current character in the children of the node through the ASCII value along with seeing if it matches with the character of the prefix word
				return; // does nothing if the node doesn't exist
			} else { // if the current character does exist and if it matches
				curr = curr.getChildren()[character]; // sets the Node pointer as the node that was just found that represents as the character of the word to find the next character of the prefix word
				s.append((char) character); // add the character into the StringBuilder since it does exist
				if (curr.getIsTerminal()) { // sees if the current character exists as a terminal node as one of the node's children	
					System.out.print("'" + s.toString() + "', "); // prints out the entire word
				}
			}
		}
		Print(curr, s); // does the printing for the rest of the words since the prefix word has been matched
	}

	// Delete method (Bonus)
	public void delete(String word) {
		iterateDelete(root, word); // helper method for the deleting process
	}

	// handles the deleting 
	private void iterateDelete(Node curr, String word) {		
		if (curr == null) { // does nothing if the node doesn't exist
			return;
		}
		
		for (int i = 0; i < word.length(); i++) { // iterates through the word
			int character = word.charAt(i); // get the ASCII value of each character of the word
			
			if (curr.getChildren()[character] == null) { // attempts to find the current character in the children of the node through the ASCII value
				return; // does nothing if the node doesn't exist
			} else {
				curr = curr.getChildren()[character]; // sets the Node pointer as the node that was just found that represents as the character of the word to find the next character of the word
			}
		}	

		if (curr.getIsTerminal()) { // checks to see if the node is a terminal Node or not
			curr.setIsTerminal(false); // makes the node as not a terminal Node in order to delete it
		}
		
	}
}

// Main method
public class TrieMain {
	public static void main(String[] args) {
		if (args[0].equals("test")) { // checks to see if the first argument equals 'test'
			Trie trie = new Trie(); // initializes a new Trie object
			
			// inserts the following words as a startup
			trie.insert("banana");
			trie.insert("bandana");
			trie.insert("bandaid");
			trie.insert("bandage");
			trie.insert("letter");
			trie.insert("lettuce");
			trie.insert("let");
			trie.insert("tool");
			trie.insert("toy");
			trie.insert("toilet");
		
			boolean result = true; // flag for infinite loop
			while (result) { // iterates through infinite loop
				System.out.print("\033[H\033[2J"); // Clear the screen 
				
				// asks the user what operation they want to try
				System.out.println("{add WORD}: Add a word");
				System.out.println("{printAll}: Print all words");
				System.out.println("{startsWith PREFIX}: Print words that start with");
				System.out.println("{del WORD}: Delete a word");
				System.out.println("{search WORD}: Match a specific word");
				System.out.println("{quit}: Quit");
				System.out.print("? ");
				
				Scanner myObj = new Scanner(System.in); // implements user inputting
				String line = myObj.nextLine(); // this contains the contents of the user's input
				String[] characters = line.split(" "); // turns the user's input into a String Array;
				
				switch (characters[0]) {
					case "add": // String Array's format: [{operation_input} {string_input}] 
						trie.insert(characters[1]); // inserts the word that the user put in {string_input}
						result = true; // continues the loop
						break;
					case "printAll": // String Array's format: [{operation_input}] 
						System.out.print("All words: "); // String format sentence for 'printAll'
						trie.printAllWords(); // prints all the words in the trie
						System.out.println();
						myObj.nextLine(); // waits for the user to press 'enter'
						result = true; // continues the loop
						break;
					case "startsWith": // String Array's format: [{operation_input} {string_input}] 
						String start = String.format("All words that start with '%s': ", characters[1]); // String format sentence for 'startsWith'
						System.out.print(start); // prints out String format sentence above
						trie.printAllWords(characters[1]); // prints all the words in the trie that starts with what the user put in {string_input}
						System.out.println();
						myObj.nextLine(); // waits for the user to press 'enter'
						result = true; // continues the loop
						break;
					case "del": // String Array's format: [{operation_input} {string_input}] 
						trie.delete(characters[1]); // deletes the word that the user put in {string_input}
						result = true; // continues the loop
						break;
					case "search": // String Array's format: [{operation_input} {string_input}] 
						String searchTrue = String.format("word '%s' was found in the trie ", characters[1]); // String format sentence for 'search' when true
						String searchFalse = String.format("word '%s' was not found in the trie ", characters[1]); // String format sentence for 'search' when false

						if ((trie.search(characters[1])) == true) { // checks if the return value of the search method is true or false
							System.out.print(searchTrue); // prints out this sentence when true
							System.out.println();
						} else {
							System.out.print(searchFalse); // prints out this sentence when false
							System.out.println();
						}
						myObj.nextLine(); // waits for the user to press 'enter'
						result = true; // continues the loop
						break;
					case "quit": // String Array's format: [{operation_input}] 
						result = false; // breaks the loop
						break;

				}
			}
		} else if (args[0].equals("memuse")) { // checks to see if the first argument equals 'memuse'
			List<String> words = new ArrayList<>(); // stores the contents of 'words.dict'
			
			Scanner scanner = new Scanner(System.in); 
			while (scanner.hasNext()){ // iterates through the contents of 'words.dict'
				words.add(scanner.next()); // adds the contents
			}
			scanner.close();

			// replace 900 with whatever the max y value is that you want to
			// display. This is in MB so 900 would be 900MB
			Display.setYMax(900);
			// replace 15 with whatever you want the y increment to be. For 
			// example, at 15 you will see 0mb, 15mb, 30mb, 45mb, 60mb, and so on 
			// on the y axis, up to the y max
			Display.setYInc(15);
			
			Runtime rt = Runtime.getRuntime(); // calculates the runtime
			long heapMemoryInBytes; // stores the number of memory being used
			
			int wordCount = 0; // word counter
			
			Trie trie = new Trie(); // initializes a new Trie object
			for (String word : words) { // iterates through the contents of 'words.dict' through the ArrayList 
				trie.insert(word); // adds the contents into the trie
				heapMemoryInBytes = rt.totalMemory() - rt.freeMemory(); // calculates the memory usage for the trie
				wordCount++; // increments the word counter
				Display.show(trie, heapMemoryInBytes, wordCount); // displays the trie
			}
			
			trie = null; // clear the contents of the trie
			wordCount = 0;
			rt.gc(); // cleans up memory
			
			HashSet<String> hashSet = new HashSet<>(); // initializes a new HashSet object
			for (String word : words) { // iterates through the contents of 'words.dict' through the ArrayList 
				hashSet.add(word); // adds the contents into the HashSet
				heapMemoryInBytes = rt.totalMemory() - rt.freeMemory(); // calculates the memory usage for the hashSet
				wordCount++; // increments the word counter
				Display.show(hashSet, heapMemoryInBytes, wordCount); // displays the hashSet
			}
		}
	}
}